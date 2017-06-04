package com.doodlegames.air.force.enemy.level2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyProXY;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.enemy.level2.BossLBody;
import com.doodlegames.air.force.enemy.level2.BossLCanon;
import com.doodlegames.air.force.enemy.level2.BossLHead;
import com.doodlegames.air.force.enemy.level2.BossLRocket;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.resource.Assets_level2;
import com.doodlegames.air.force.screen.GameScreen;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RandomOrder;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class BossL extends Enemy {

   public static final Vector2 BOSSBODY;
   public static final Vector2 BOSSCANON1;
   public static final Vector2 BOSSCANON2;
   public static final Vector2 BOSSHEAD;
   public static final Vector2 BOSSROCKET1;
   public static final Vector2 BOSSROCKET2;
   private static final float BULLETSPEED = 10.0F;
   public static final int DEFAULTHEALTHYDEGREE = 10;
   private static final int PARTSNUM = 6;
   private static final float TIMEOFPLAYCRASHANIMATION = 15.0F;
   private static TextureAtlas.AtlasRegion atlasBroRegion = Assets_level2.atlas_Enemy.findRegion("boss_base_broken");
   private static TextureAtlas.AtlasRegion atlasRegion = Assets_level2.atlas_Enemy.findRegion("boss_base");
   private static final float bossVel = 150.0F;
   public static final float centerDelta = 26.0F;
   static final int middleX = 346;
   public static final int regionHeight;
   public static final int regionWidth;
   private static int[][] rocketShootNum;
   private BossLBody body;
   private boolean bossIsRecovering;
   private BossL.BossMoveProcessor bossMoveProcessor;
   private int bossState;
   private BossLCanon canon1;
   private BossLCanon canon2;
   private EnemyTools.DeleFunction childRecoveryFun = new EnemyTools.DeleFunction() {
      public void function(Object var1) {
         BossL.this.childRecovery();
      }
   };
   private Clock clockBodyChangeShootingMode = new Clock(0.0F, 10.0F);
   private Clock clockMove;
   private Clock clockShootingBody = new Clock(10.0F);
   private Clock clockShootingCanon = new Clock(5.0F);
   private boolean comActionAdded = false;
   private EnemyTools.EnemyAction delayChildRecovery;
   private BossL.DelayEnableShoot delayEnableShoot;
   private float dropTime;
   private boolean enableShoot = false;
   private EnemyTools.DeleFunction handleRecoveCompleteFun = new EnemyTools.DeleFunction() {
      public void function(Object var1) {
         BossL.this.handleRecoveComplete();
      }
   };
   private BossLHead head;
   private Enemy[] hitMap;
   private List<Rectangle> hitRectangles;
   private BossL.BossLDroppedInit initAction;
   private RandomOrder randomOrder = new RandomOrder(5);
   private boolean recActionAdded = false;
   private BossLRocket rocket1;
   private BossLRocket rocket2;


   static {
      int var0;
      if(atlasRegion.rotate) {
         var0 = atlasRegion.getRegionHeight();
      } else {
         var0 = atlasRegion.getRegionWidth();
      }

      regionWidth = var0;
      int var1;
      if(atlasRegion.rotate) {
         var1 = atlasRegion.getRegionWidth();
      } else {
         var1 = atlasRegion.getRegionHeight();
      }

      regionHeight = var1;
      BOSSBODY = new Vector2((-96.0F + (float)BossLBody.regionWidth / 2.0F) / 1.0F, (117.0F + (float)BossLBody.regionHeight / 2.0F) / 1.0F);
      BOSSHEAD = new Vector2((40.0F + (float)BossLHead.regionWidth / 2.0F) / 1.0F, (179.0F + (float)BossLHead.regionHeight / 2.0F) / 1.0F);
      BOSSCANON1 = new Vector2((-52.0F + (float)BossLCanon.regionWidth / 2.0F) / 1.0F, (106.0F + (float)BossLCanon.regionHeight / 2.0F) / 1.0F);
      BOSSCANON2 = new Vector2((144.0F + (float)BossLCanon.regionWidth / 2.0F) / 1.0F, (106.0F + (float)BossLCanon.regionHeight / 2.0F) / 1.0F);
      BOSSROCKET1 = new Vector2((-122.0F + (float)BossLRocket.regionWidth / 2.0F) / 1.0F, (106.0F + (float)BossLRocket.regionHeight / 2.0F) / 1.0F);
      BOSSROCKET2 = new Vector2((214.0F + (float)BossLRocket.regionWidth / 2.0F) / 1.0F, (106.0F + (float)BossLRocket.regionHeight / 2.0F) / 1.0F);
      rocketShootNum = new int[][]{{3, 0}, {6, 0}, {6, 0}};
   }

   public BossL(World var1, float var2, float var3) {
      super(var1, 10, var2 + 0.5F, var3, (float)regionWidth / 1.0F, (float)regionHeight / 1.0F);
      this.clockMove = new Clock(0.0F, 14.0F, Clock.ClockType.FireOnlyOnce);
      this.body = new BossLBody(var1, this.bounds.x + BOSSBODY.x, this.bounds.y + BOSSBODY.y);
      this.canon1 = new BossLCanon(var1, this.bounds.x + BOSSCANON1.x, this.bounds.y + BOSSCANON1.y);
      this.canon2 = new BossLCanon(var1, this.bounds.x + BOSSCANON2.x, this.bounds.y + BOSSCANON2.y);
      this.rocket1 = new BossLRocket(var1, this.bounds.x + BOSSROCKET1.x, this.bounds.y + BOSSROCKET1.y);
      this.rocket2 = new BossLRocket(var1, this.bounds.x + BOSSROCKET2.x, this.bounds.y + BOSSROCKET2.y);
      this.head = new BossLHead(var1, this.bounds.x + BOSSHEAD.x, this.bounds.y + BOSSHEAD.y);
      this.hitMap = new Enemy[6];
      this.hitRectangles = new ArrayList(6);
      this.bossState = 0;
      this.increaseState();
      this.bossIsRecovering = false;
      this.bossMoveProcessor = new BossL.BossMoveProcessor();
      this.needShowWarning = true;
      this.warningPosition = 6300.0F;
      this.outRemove = false;
      this.outRender = true;
      this.comActionAdded = false;
      this.recActionAdded = false;
      this.delayChildRecovery = EnemyTools.delay(1.0F, EnemyTools.funAction(this.childRecoveryFun));
      this.enemyRegion = atlasRegion;
      this.enemyBroRegion = atlasBroRegion;
      this.delayEnableShoot = (BossL.DelayEnableShoot)Pools.obtain(BossL.DelayEnableShoot.class);
      this.delayEnableShoot.bossL = this;
      this.delayEnableShoot.setEnemy(this);
      this.initAction = (BossL.BossLDroppedInit)Pools.obtain(BossL.BossLDroppedInit.class);
   }

   public static Enemy addABossLv2(World var0, float var1, float var2, float var3, List<Enemy> var4) {
      return EnemyProXY.addAEnemy(new BossL(var0, var1, var2), var3, var4, true);
   }

   private void childRecovery() {
      this.head.setIsRecovering(0);
      this.canon1.setIsRecovering(1);
      this.canon2.setIsRecovering(2);
      this.rocket1.setIsRecovering(1);
      this.rocket2.setIsRecovering(2);
   }

   private void handleRecoveComplete() {
      this.bossIsRecovering = false;
      this.bossMoveProcessor.resumeMove();
      this.increaseState();
   }

   private void keepRelativePosition() {
      this.canon1.setPosition(this.bounds.x + BOSSCANON1.x, this.bounds.y + BOSSCANON1.y);
      this.canon2.setPosition(this.bounds.x + BOSSCANON2.x, this.bounds.y + BOSSCANON2.y);
      this.rocket1.setPosition(this.bounds.x + BOSSROCKET1.x, this.bounds.y + BOSSROCKET1.y);
      this.rocket2.setPosition(this.bounds.x + BOSSROCKET2.x, this.bounds.y + BOSSROCKET2.y);
      this.head.setPosition(this.bounds.x + BOSSHEAD.x, this.bounds.y + BOSSHEAD.y);
      this.body.setPosition(this.bounds.x + BOSSBODY.x, this.bounds.y + BOSSBODY.y);
   }

   public static void loadResource() {
      atlasRegion = Assets_level2.atlas_Enemy.findRegion("boss_base");
      atlasBroRegion = Assets_level2.atlas_Enemy.findRegion("boss_base_broken");
   }

   public void beHitByBullet(Bullet var1, int var2) {
      this.hitMap[var2].beHitByBullet(var1);
      GameScreen.bossHealthyDegree = this.getHealthyDgree();
   }

   public void enemyDropped(EnemyProXY var1) {
      this.initAction.setEnemy(this);
      EnemyTools.EnemyAction[] var2 = new EnemyTools.EnemyAction[]{this.initAction, this.delayEnableShoot, this.bossMoveProcessor};
      this.addEnemyAction(EnemyTools.sequence(var2));
      this.world.playBossMusic();
      this.dropTime = Clock.getCurrentTime();
   }

   public int getHealthyDgree() {
      int var1;
      if(this.body.getHealthyDgree() > 0) {
         var1 = this.body.getHealthyDgree();
      } else {
         var1 = 0;
      }

      int var2 = 0 + var1;
      int var3;
      if(this.head.getHealthyDgree() > 0) {
         var3 = this.head.getHealthyDgree();
      } else {
         var3 = 0;
      }

      int var4 = var2 + var3;
      int var5;
      if(this.canon1.getHealthyDgree() > 0) {
         var5 = this.canon1.getHealthyDgree();
      } else {
         var5 = 0;
      }

      int var6 = var4 + var5;
      int var7;
      if(this.canon2.getHealthyDgree() > 0) {
         var7 = this.canon2.getHealthyDgree();
      } else {
         var7 = 0;
      }

      int var8 = var6 + var7;
      int var9;
      if(this.rocket1.getHealthyDgree() > 0) {
         var9 = this.rocket1.getHealthyDgree();
      } else {
         var9 = 0;
      }

      int var10 = var8 + var9;
      int var11 = this.rocket2.getHealthyDgree();
      int var12 = 0;
      if(var11 > 0) {
         var12 = this.rocket2.getHealthyDgree();
      }

      return var10 + var12;
   }

   public List<Rectangle> getHitRectangle() {
      this.hitRectangles.clear();
      boolean var1 = this.bossIsRecovering;
      int var2 = 0;
      if(!var1) {
         boolean var4 = this.body.canBeHit();
         var2 = 0;
         if(var4) {
            int var10 = this.bossState;
            var2 = 0;
            if(var10 == 3) {
               this.hitRectangles.add(this.body.gethitR());
               this.hitMap[0] = this.body;
               var2 = 0 + 1;
            }
         }

         if(this.head.canBeHit()) {
            this.hitRectangles.add(this.head.gethitR());
            this.hitMap[var2] = this.head;
            ++var2;
         }

         if(this.canon1.canBeHit()) {
            this.hitRectangles.add(this.canon1.gethitR());
            this.hitMap[var2] = this.canon1;
            ++var2;
         }

         if(this.canon2.canBeHit()) {
            this.hitRectangles.add(this.canon2.gethitR());
            this.hitMap[var2] = this.canon2;
            ++var2;
         }

         if(this.rocket1.canBeHit()) {
            this.hitRectangles.add(this.rocket1.gethitR());
            this.hitMap[var2] = this.rocket1;
            ++var2;
         }

         if(this.rocket2.canBeHit()) {
            this.hitRectangles.add(this.rocket2.gethitR());
            this.hitMap[var2] = this.rocket2;
            ++var2;
         }
      }

      for(int var3 = var2; var3 < 6; ++var3) {
         this.hitMap[var3] = null;
      }

      return this.hitRectangles;
   }

   public float getTimeOfCrash() {
      return 15.0F;
   }

   public void increaseState() {
      ++this.bossState;
      switch(this.bossState) {
      case 1:
         this.clockShootingBody.resetClock(7.5F, 10.0F, 0.0F, (byte)1);
         this.clockShootingCanon.resetClock(7.0F, 8.0F, 0.0F, (byte)1);
         this.canon1.getVelBullet().set(0.0F, -300.0F);
         this.canon2.getVelBullet().set(0.0F, -300.0F);
         this.rocket1.getVelBullet().set(0.0F, -200.0F);
         this.rocket2.getVelBullet().set(0.0F, -200.0F);
         return;
      case 2:
         this.clockShootingBody.resetClock(2.0F, 5.0F, 0.0F, (byte)1);
         this.clockShootingCanon.resetClock(5.0F, 6.0F, 3.4F, (byte)1);
         this.canon1.getVelBullet().set(0.0F, -400.0F);
         this.canon2.getVelBullet().set(0.0F, -400.0F);
         this.rocket1.getVelBullet().set(0.0F, -220.0F);
         this.rocket2.getVelBullet().set(0.0F, -220.0F);
         return;
      case 3:
         this.clockShootingBody.resetClock(2.0F * this.world.rand.nextFloat(), 2.0F, 0.0F, (byte)1);
         return;
      default:
      }
   }

   public boolean isCrashed() {
      if(this.canon1.isCrashed() && this.canon2.isCrashed() && this.body.isCrashed() && this.head.isCrashed() && this.rocket1.isCrashed() && this.rocket2.isCrashed()) {
         if(!super.isCrashed()) {
            Settings.bossKilled(2, Clock.getCurrentTime(this.dropTime));
            this.makeCrash();
         }

         return true;
      } else {
         return false;
      }
   }

   public void render(SpriteBatch var1) {
      super.render(var1);
      this.rocket1.render(var1);
      this.rocket2.render(var1);
      this.canon1.render(var1);
      this.canon2.render(var1);
      this.body.render(var1);
      this.head.render(var1);
   }

   public void setSleepPosition(float var1, float var2) {
      super.setSleepPosition(var1, var2);
      this.keepRelativePosition();
   }

   public void update(float var1) {
      if(this.clockMove.isFired()) {
         boolean var2 = this.bossMoveProcessor.isMoving();
         if(!var2 && !this.bossIsRecovering) {
            this.bossMoveProcessor.resumeMove();
            this.clockMove.resetClockFireOnce(6.0F + 4.0F * this.world.rand.nextFloat());
         } else {
            this.clockMove.resetClockFireOnce(1.0F);
         }

         boolean var3;
         if(var2 && this.bossState < 2) {
            var3 = false;
         } else {
            var3 = true;
         }

         this.enableShoot = var3;
      }

      this.velocity.y = Settings.backgroundVelocity.y;
      super.update(var1);
      if(this.bossState == 1) {
         if(!this.bossIsRecovering && this.head.isCrashed() && this.canon1.isCrashed() && this.canon2.isCrashed() && this.rocket1.isCrashed() && this.rocket2.isCrashed() && !this.recActionAdded) {
            this.bossIsRecovering = true;
            this.bossMoveProcessor.stop();
            this.addEnemyAction(this.delayChildRecovery);
            this.recActionAdded = true;
         }

         if(this.bossIsRecovering && !this.enemyActions.contains(this.delayChildRecovery, true) && !this.head.getIsRecovering() && !this.canon1.getIsRecovering() && !this.canon2.getIsRecovering() && !this.rocket1.getIsRecovering() && !this.rocket2.getIsRecovering() && !this.comActionAdded) {
            this.addEnemyAction(EnemyTools.delay(1.0F, EnemyTools.funAction(this.handleRecoveCompleteFun)));
            this.comActionAdded = true;
         }
      }

      if(this.bossState == 2 && this.head.isCrashed() && this.canon1.isCrashed() && this.canon2.isCrashed() && this.rocket1.isCrashed() && this.rocket2.isCrashed()) {
         this.increaseState();
      }

      if(this.bossState == 3 && this.clockBodyChangeShootingMode.isFired()) {
         if(this.clockShootingBody.getSpan() == 3.0F) {
            this.clockShootingBody.resetClock(0.0F, 2.0F, 0.0F, (byte)1);
         } else if(this.clockShootingBody.getSpan() == 2.0F) {
            this.clockShootingBody.resetClock(0.0F, 3.0F, 0.0F, (byte)1);
         }
      }

      if(!this.bossIsRecovering) {
         this.keepRelativePosition();
      }

      this.body.update(var1);
      this.canon1.update(var1);
      this.canon2.update(var1);
      this.rocket1.update(var1);
      this.rocket2.update(var1);
      this.head.update(var1);
   }

   public void updateShooting(float var1) {
      if(!this.bossIsRecovering && this.enableShoot) {
         if(this.clockShootingBody.isFired()) {
            if(this.bossState == 1 || this.bossState == 2) {
               this.body.shooting(0);
            }

            if(this.bossState == 3) {
               if(this.clockShootingBody.getSpan() == 2.0F) {
                  this.body.shooting(1);
               }

               if(this.clockShootingBody.getSpan() == 3.0F) {
                  this.body.shooting(0);
               }
            }
         }

         if(this.clockShootingCanon.isFired()) {
            RandomOrder var2 = this.randomOrder;
            var2.init();
            float var3 = 0.9F * (float)(-1 + var2.nextOrder());
            this.canon1.shooting(var3);
            float var4 = 0.9F * (float)(-1 + var2.nextOrder());
            this.canon2.shooting(var4);
            float var5 = 0.9F * (float)(-1 + var2.nextOrder());
            this.head.shooting(var5);
            int var6 = -1 + this.bossState;
            int[] var7 = rocketShootNum[var6];
            if(Clock.rand.nextBoolean()) {
               int var10 = var7[0];
               var7[0] = var7[1];
               var7[1] = var10;
            }

            float var8 = 0.9F * (float)(-1 + var2.nextOrder());
            this.rocket1.shooting(var7[0], var8);
            float var9 = 0.9F * (float)(-1 + var2.nextOrder());
            this.rocket2.shooting(var7[1], var9);
            return;
         }
      }

   }

   public class BossMoveProcessor extends EnemyTools.EnemyAction {

      float range = 100.0F;
      float startTime;
      boolean stopAtMiddle = false;
      float vel = 150.0F;


      public BossMoveProcessor() {
         this.stop();
      }

      public boolean act(float var1) {
         if(!BossL.this.isCrashed()) {
            if(this.startTime <= 0.0F) {
               this.startTime += var1;
               return false;
            }

            float var2 = BossL.this.getPositionX() + var1 * this.vel;
            float var3 = BossL.this.getPositionY();
            if(var2 - 346.0F > this.range) {
               var2 = 346.0F + this.range;
               this.stop();
               this.vel = -150.0F;
            }

            if(var2 - 346.0F < -this.range) {
               var2 = 346.0F + -this.range;
               this.stop();
               this.vel = 150.0F;
            }

            if(this.stopAtMiddle && (this.vel > 0.0F && var2 > 346.0F || this.vel < 0.0F && var2 < 346.0F)) {
               this.stop();
               var2 = 346.0F;
               this.stopAtMiddle = false;
            }

            BossL.this.setPosition(var2, var3);
         }

         return false;
      }

      public boolean isMoving() {
         return this.startTime >= 0.0F;
      }

      public float resumeMove() {
         boolean var1;
         if(Math.abs(BossL.this.getPositionX() - 346.0F) > 5.0F) {
            var1 = true;
         } else {
            var1 = false;
         }

         this.stopAtMiddle = var1;
         this.startTime = 0.0F;
         if(!this.stopAtMiddle && Clock.rand.nextBoolean()) {
            this.vel = -this.vel;
         }

         return this.vel;
      }

      public void stop() {
         this.startTime = Float.NEGATIVE_INFINITY;
      }
   }

   private static class DelayEnableShoot extends EnemyTools.EnemyAction {

      BossL bossL;
      float time = 0.0F;


      public boolean act(float var1) {
         if(this.time > 1.0F) {
            this.bossL.enableShoot = true;
            this.bossL.world.askBeginFighting();
            return true;
         } else {
            this.time += var1;
            return false;
         }
      }
   }

   private static class BossLDroppedInit extends EnemyTools.EnemyAction {

      public boolean act(float var1) {
         BossL var2 = (BossL)this.getEnemy();
         if(var2.getPositionX() < 346.0F) {
            var2.outRemove = true;
            var2.outRender = false;
            var2.setPosition(346.0F, var2.getPositionY());
            return true;
         } else {
            float var3 = -Math.abs(150.0F);
            var2.setPosition(var2.getPositionX() + var3 * var1, var2.getPositionY());
            return false;
         }
      }
   }
}
