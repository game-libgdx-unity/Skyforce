package com.doodlegames.air.force.enemy.level7;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.enemy.level4.AlianRobot;
import com.doodlegames.air.force.enemy.level4.AlienTank;
import com.doodlegames.air.force.enemy.level4.ChildEnemyMoveActions;
import com.doodlegames.air.force.enemy.level5.AlienUfoA;
import com.doodlegames.air.force.enemy.level5.AlienUfoB;
import com.doodlegames.air.force.enemy.level6.AlienMech;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldAnimationPlayer;
import com.doodlegames.air.force.game.WorldAudio;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.resource.Assets_level7;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class BossLv7LSlaw extends Enemy implements EnemyTools.EnemyActionCallBack {

   private static  int ARMHEIGHT = 0;
   private static  int ARMNUM = 0;
   private static final Vector2[] ARMPOSITION;
   private static int ARMWIDTH = 0;
   public static final int DEFAULTHEALTHYDEGREE = 500;
   private static final int DELTADEGREE = 100;
   public static int HEIGHT = 0;
   private static final float SLAWINITOFFSETY = 60.0F;
   private static final float TIMEOFPLAYCRASHANIMATION = 0.1F;
   public static int WIDTH = 0;
   private static TextureAtlas.AtlasRegion armRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_arm");
   private static TextureAtlas.AtlasRegion enemyTextureRegion;
   private static final float shadowOffsetX = 30.0F;
   private static final float shadowOffsetY = -30.0F;
   private final float SLAWBACKTIME = 1.0F;
   private final float SLAWOUTBTIME = 0.5F;
   private final float SLAWOUTFTIME = 0.4F;
   private final float SLAWOUTTIME = 0.9F;
   private final float SLAWSTAYTIME = 3.0F;
   private Enemy.EnemyFreeToPoolFunction alienMechFreeFun;
   private Enemy.EnemyFreeToPoolFunction alienRobotFreeFun;
   private Enemy.EnemyFreeToPoolFunction alienTankFreeFun;
   private Enemy.EnemyFreeToPoolFunction alienUfoAFreeFun;
   private Enemy.EnemyFreeToPoolFunction alienUfoBFreeFun;
   private BossLv7LSlaw.armPart[] arms;
   private int backHealthDegree;
   private Clock clockAirCraft;
   private Clock clockLandCraft;
   private BossLv7LSlaw.EnableAction enableAction;
   private Rectangle hitR = new Rectangle();
   private List<Rectangle> hitRects;
   private boolean isActive;
   private BossLv7LSlaw.MakeSleepAction makeSleepAction;
   private BossLv7LSlaw.MakeWorkingAction makeWorkingAction;
   private Pool<AlienMech> mechPool;
   private GameObject owner;
   private Pool<AlianRobot> robotPool;
   private Clock slawAttackClock;
   private BossLv7LSlaw.SlawState slawState;
   private boolean slawStretching;
   private float sleepTime;
   private float sleepTimeLimit = 1.0F;
   private float strechTime;
   private float stringX;
   private float stringY;
   private Pool<AlienTank> tankPool;
   private Pool<AlienUfoA> ufoAPool;
   private Pool<AlienUfoB> ufoBPool;


   static {
      int var0;
      if(armRegion.rotate) {
         var0 = armRegion.getRegionHeight();
      } else {
         var0 = armRegion.getRegionWidth();
      }

      ARMWIDTH = var0;
      int var1;
      if(armRegion.rotate) {
         var1 = armRegion.getRegionWidth();
      } else {
         var1 = armRegion.getRegionHeight();
      }

      ARMHEIGHT = var1;
      Vector2[] var2 = new Vector2[]{new Vector2((-1.0F + (float)ARMWIDTH / 2.0F) / 1.0F, (162.0F + (float)ARMHEIGHT / 2.0F) / 1.0F), new Vector2((-1.0F + (float)ARMWIDTH / 2.0F) / 1.0F, (147.0F + (float)ARMHEIGHT / 2.0F) / 1.0F), new Vector2((-1.0F + (float)ARMWIDTH / 2.0F) / 1.0F, (132.0F + (float)ARMHEIGHT / 2.0F) / 1.0F), new Vector2((-1.0F + (float)ARMWIDTH / 2.0F) / 1.0F, (117.0F + (float)ARMHEIGHT / 2.0F) / 1.0F), new Vector2((-1.0F + (float)ARMWIDTH / 2.0F) / 1.0F, (102.0F + (float)ARMHEIGHT / 2.0F) / 1.0F), new Vector2((-1.0F + (float)ARMWIDTH / 2.0F) / 1.0F, (87.0F + (float)ARMHEIGHT / 2.0F) / 1.0F), new Vector2((-1.0F + (float)ARMWIDTH / 2.0F) / 1.0F, (72.0F + (float)ARMHEIGHT / 2.0F) / 1.0F), new Vector2((-1.0F + (float)ARMWIDTH / 2.0F) / 1.0F, (57.0F + (float)ARMHEIGHT / 2.0F) / 1.0F), new Vector2((-1.0F + (float)ARMWIDTH / 2.0F) / 1.0F, (42.0F + (float)ARMHEIGHT / 2.0F) / 1.0F)};
      ARMPOSITION = var2;
      enemyTextureRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_claw");
      int var3;
      if(enemyTextureRegion.rotate) {
         var3 = enemyTextureRegion.getRegionHeight();
      } else {
         var3 = enemyTextureRegion.getRegionWidth();
      }

      WIDTH = var3;
      int var4;
      if(enemyTextureRegion.rotate) {
         var4 = enemyTextureRegion.getRegionWidth();
      } else {
         var4 = enemyTextureRegion.getRegionHeight();
      }

      HEIGHT = var4;
      ARMNUM = ARMPOSITION.length;
   }

   public BossLv7LSlaw(World var1, GameObject var2, float var3, float var4) {
      super(var1, 500, var3 + var2.bounds.x, var4 + var2.bounds.y, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.owner = var2;
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = null;
      this.arms = new BossLv7LSlaw.armPart[ARMNUM];
      this.arms[0] = new BossLv7LSlaw.armPart(ARMPOSITION[0].x, ARMPOSITION[0].y, this, 0);

      for(int var5 = 1; var5 < ARMNUM; ++var5) {
         this.arms[var5] = new BossLv7LSlaw.armPart(ARMPOSITION[var5].x, ARMPOSITION[var5].y, this, var5);
      }

      this.hitRects = new ArrayList(1 + ARMNUM);

      for(int var6 = 0; var6 < ARMNUM; ++var6) {
         this.hitRects.add(this.arms[var6].bounds);
      }

      this.stringX = this.getPositionX();
      this.stringY = this.getPositionY();
      this.slawState = BossLv7LSlaw.SlawState.Sleep;
      this.slawAttackClock = new Clock(0.0F, 0.0F, Clock.ClockType.FireOnlyOnce);
      this.slawAttackClock.isFired();
      this.backHealthDegree = this.getHealthyDgree();
      this.constructNew();
   }

   private void constructMechPool() {
      this.mechPool = new Pool(1, 3) {
         protected AlienMech newObject() {
            return new AlienMech(BossLv7LSlaw.this.world, 0.0F, 0.0F);
         }
      };
      this.mechPool.free((AlienMech)(new AlienMech(this.world, 0.0F, 0.0F)));
      this.alienMechFreeFun = new Enemy.EnemyFreeToPoolFunction() {
         public void freeToPool(Enemy var1) {
            if(var1 instanceof AlienMech) {
               BossLv7LSlaw.this.mechPool.free((AlienMech)((AlienMech)var1));
            } else {
               Settings.e("freeToPool error.");
            }
         }
      };
   }

   private void constructNew() {
      this.makeSleepAction = new BossLv7LSlaw.MakeSleepAction();
      this.makeWorkingAction = new BossLv7LSlaw.MakeWorkingAction();
      this.enableAction = new BossLv7LSlaw.EnableAction();
      this.isActive = false;
      this.sleepTime = 0.0F;
      this.strechTime = 0.0F;
      this.slawStretching = false;
      this.constructUfoAPool();
      this.constructUfoBPool();
      this.constructTankPool();
      this.constructMechPool();
      this.constructRobotPool();
      this.clockLandCraft = new Clock(0.0F, 1.0F + 4.0F * this.world.rand.nextFloat(), Clock.ClockType.FireOnlyOnce);
      this.clockAirCraft = new Clock(0.0F, 1.0F + 4.0F * this.world.rand.nextFloat(), Clock.ClockType.FireOnlyOnce);
   }

   private void constructRobotPool() {
      this.robotPool = new Pool(1, 3) {
         protected AlianRobot newObject() {
            return new AlianRobot(BossLv7LSlaw.this.world, 0.0F, 0.0F);
         }
      };
      this.robotPool.free((AlianRobot)(new AlianRobot(this.world, 0.0F, 0.0F)));
      this.alienRobotFreeFun = new Enemy.EnemyFreeToPoolFunction() {
         public void freeToPool(Enemy var1) {
            if(var1 instanceof AlianRobot) {
               BossLv7LSlaw.this.robotPool.free((AlianRobot)((AlianRobot)var1));
            } else {
               Settings.e("freeToPool error.");
            }
         }
      };
   }

   private void constructTankPool() {
      this.tankPool = new Pool(1, 3) {
         protected AlienTank newObject() {
            return new AlienTank(BossLv7LSlaw.this.world, 0.0F, 0.0F);
         }
      };
      this.tankPool.free((AlienTank)(new AlienTank(this.world, 0.0F, 0.0F)));
      this.alienTankFreeFun = new Enemy.EnemyFreeToPoolFunction() {
         public void freeToPool(Enemy var1) {
            if(var1 instanceof AlienTank) {
               BossLv7LSlaw.this.tankPool.free((AlienTank)((AlienTank)var1));
            } else {
               Settings.e("freeToPool error.");
            }
         }
      };
   }

   private void constructUfoAPool() {
      this.ufoAPool = new Pool(1, 3) {
         protected AlienUfoA newObject() {
            return new AlienUfoA(BossLv7LSlaw.this.world, 0.0F, 0.0F);
         }
      };
      this.ufoAPool.free((AlienUfoA)(new AlienUfoA(this.world, 0.0F, 0.0F)));
      this.alienUfoAFreeFun = new Enemy.EnemyFreeToPoolFunction() {
         public void freeToPool(Enemy var1) {
            if(var1 instanceof AlienUfoA) {
               BossLv7LSlaw.this.ufoAPool.free((AlienUfoA)((AlienUfoA)var1));
            } else {
               Settings.e("freeToPool error.");
            }
         }
      };
   }

   private void constructUfoBPool() {
      this.ufoBPool = new Pool(1, 3) {
         protected AlienUfoB newObject() {
            return new AlienUfoB(BossLv7LSlaw.this.world, 0.0F, 0.0F);
         }
      };
      this.ufoBPool.free((AlienUfoB)(new AlienUfoB(this.world, 0.0F, 0.0F)));
      this.alienUfoBFreeFun = new Enemy.EnemyFreeToPoolFunction() {
         public void freeToPool(Enemy var1) {
            if(var1 instanceof AlienUfoB) {
               BossLv7LSlaw.this.ufoBPool.free((AlienUfoB)((AlienUfoB)var1));
            } else {
               Settings.e("freeToPool error.");
            }
         }
      };
   }

   private Enemy getAAirEnemy(float var1, float var2) {
      switch(this.world.rand.nextInt(2)) {
      case 0:
         AlienUfoA var4 = (AlienUfoA)this.ufoAPool.obtain();
         var4.reset(this.world, var1, var2, 0.0F, 0.0F);
         var4.setEnemyFreeToPoolFunction(this.alienUfoAFreeFun);
         ChildEnemyMoveActions.addBezierPursue(var4, this.world.fighter);
         return var4;
      case 1:
         AlienUfoB var3 = (AlienUfoB)this.ufoBPool.obtain();
         var3.reset(this.world, var1, var2);
         var3.setEnemyFreeToPoolFunction(this.alienUfoBFreeFun);
         ChildEnemyMoveActions.addBezierPursue(var3, this.world.fighter);
         return var3;
      default:
         return null;
      }
   }

   private Enemy getALandEnemy(float var1, float var2) {
      switch(this.world.rand.nextInt(2)) {
      case 0:
         AlienTank var4 = (AlienTank)this.tankPool.obtain();
         var4.reset(this.world, var1, var2);
         var4.setEnemyFreeToPoolFunction(this.alienTankFreeFun);
         ChildEnemyMoveActions.addDotMoveAction(var4);
         return var4;
      case 1:
         AlienMech var3 = (AlienMech)this.mechPool.obtain();
         var3.reset(this.world, var1, var2);
         var3.setEnemyFreeToPoolFunction(this.alienMechFreeFun);
         ChildEnemyMoveActions.addDotMoveAction(var3);
         return var3;
      default:
         return null;
      }
   }

   public static void loadResource() {
      armRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_arm");
      enemyTextureRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_claw");
   }

   private void updateAirCraft() {
      if(!this.isActive && !this.world.getStopFighting() && this.clockAirCraft.isFired()) {
         Enemy var1 = this.getAAirEnemy(this.getPositionX(), 790.0F);
         this.world.addAAirEnemy(var1);
         this.clockAirCraft.resetClockFireOnce(10.0F + 5.0F * this.world.rand.nextFloat());
      }
   }

   private void updateAttacking(float var1) {
      boolean var2 = this.enemyActions.contains(this.makeSleepAction, true);
      if(!var2) {
         this.updateSlawStrectching(var1);
      }

      boolean var3;
      if(!this.slawStretching) {
         var3 = true;
      } else {
         var3 = false;
      }

      boolean var4;
      if(this.getHealthyDgree() <= this.backHealthDegree) {
         var4 = true;
      } else {
         var4 = false;
      }

      if(var4 && !var2 && var3) {
         this.addEnemyAction(this.makeSleepAction);
      }

   }

   private void updateLandCraft() {
      if(!this.isActive && !this.world.getStopFighting() && this.clockLandCraft.isFired()) {
         float var1 = this.getPositionX();
         float var2 = this.getPositionY() - 150.0F;
         float var3;
         if(var1 < 320.0F) {
            var3 = var1 + 10.0F;
         } else {
            var3 = var1 - 10.0F;
         }

         Enemy var4 = this.getALandEnemy(var3, var2);
         this.world.enemysOnSeaOrLand.add(var4);
         this.clockLandCraft.resetClockFireOnce(10.0F + 5.0F * this.world.rand.nextFloat());
      }
   }

   private void updateSlawStrectching(float var1) {
      if(this.strechTime < 5.4F && !this.slawStretching) {
         this.slawStretching = true;
      }

      if(this.strechTime > 5.4F && this.slawStretching) {
         this.slawStretching = false;
      }

      if(this.strechTime > 1.0F + 5.4F) {
         this.strechTime = 0.0F;
      }

      float var2 = this.getPositionX();
      boolean var3;
      if(var2 < 320.0F) {
         var3 = true;
      } else {
         var3 = false;
      }

      float var4 = this.world.fighter.getPositionX();
      boolean var5;
      if(true && (!var3 || var4 > 320.0F)) {
         var5 = false;
      } else {
         var5 = true;
      }

      boolean var6;
      if(!var5 && (var3 || var4 <= 320.0F)) {
         var6 = false;
      } else {
         var6 = true;
      }

      if(this.strechTime == 0.0F && var6) {
         float var7 = var4;
         if(var4 > var2 && !var3) {
            var7 = var2;
         }

         if(var4 < var2 && var3) {
            var7 = var2;
         }

         if(!var3 && var7 < 100.0F + 320.0F) {
            var7 = 320.0F + 100.0F;
         }

         if(var3 && var7 > 320.0F - 100.0F) {
            var7 = 320.0F - 100.0F;
         }

         float var8 = this.world.fighter.getPositionY();
         if(var8 > this.getPositionY() - 100.0F) {
            var8 = this.getPositionY() - 100.0F;
         }

         if(var8 < this.getPositionY() - 300.0F) {
            var8 = this.getPositionY() - 300.0F;
         }

         EnemyTools.EnemyAction[] var9 = new EnemyTools.EnemyAction[]{EnemyTools.moveTo(this, this.getPositionX(), 50.0F + this.getPositionY(), 0.5F), EnemyTools.moveTo(this, var7, var8, 0.4F), EnemyTools.delay(3.0F), EnemyTools.moveTo(this, this.getPositionX(), this.getPositionY(), 1.0F)};
         this.addEnemyAction(EnemyTools.sequence(var9));
      }

      this.strechTime += var1;
   }

   private void updateSleep(float var1) {
      if(this.sleepTime > this.sleepTimeLimit && !this.enemyActions.contains(this.makeWorkingAction, true)) {
         this.addEnemyAction(this.makeWorkingAction);
         this.sleepTimeLimit = 5.0F;
      }

      this.sleepTime += var1;
   }

   public boolean act(EnemyTools.EnemyAction var1, float var2) {
      EnemyTools.checkAction(var1, this);
      return false;
   }

   public boolean actionCancel(EnemyTools.EnemyAction var1) {
      return false;
   }

   public void beHitByBullet(Bullet var1) {
      int var2 = var1.damageValue;
      if(this.getHealthyDgree() - var1.damageValue < this.backHealthDegree) {
         int var3 = this.getHealthyDgree() - this.backHealthDegree;
         if(var3 <= 0) {
            var3 = 0;
         }

         var1.damageValue = var3;
      }

      super.beHitByBullet(var1);
      var1.damageValue = var2;
   }

   public boolean canBeHit() {
      return this.slawState == BossLv7LSlaw.SlawState.Attacking && super.canBeHit();
   }

   public void enableSlaw() {
      this.addEnemyAction(this.enableAction);
   }

   public int getArmNum() {
      return this.arms.length;
   }

   public float getDeltaX() {
      return this.getPositionX() - this.stringX;
   }

   public float getDeltaY() {
      return this.getPositionY() - this.stringY;
   }

   public List<Rectangle> getHitRectangle() {
      if(this.slawState == BossLv7LSlaw.SlawState.Attacking && !this.isCrashed()) {
         this.hitRects.clear();

         for(int var1 = 0; var1 < ARMNUM; var1 += 2) {
            Rectangle var3 = this.arms[var1].bounds;
            if(var3.y < 435.0F - var3.height) {
               this.hitRects.add(var3);
            }
         }

         this.hitR.x = this.bounds.x;
         this.hitR.y = this.bounds.y + this.bounds.height / 3.0F;
         this.hitR.width = this.bounds.width;
         this.hitR.height = 2.0F * this.bounds.height / 3.0F;
         this.hitRects.add(this.hitR);
         return this.hitRects;
      } else {
         return Enemy.EMPTYRECTANGELS;
      }
   }

   public float getTimeOfCrash() {
      return 0.1F;
   }

   protected void putCrashAniamtion() {
      Animation var1 = this.getShatterAniamtion();
      Animation var2 = this.getCrashBoom(true);
      WorldAudio.PlaySound var3 = this.getCrashSound(true);
      int var4 = this.arms.length;
      float var5 = (float)Clock.getTimeCounter();

      for(int var6 = 0; var6 < var4; var6 += 2) {
         Rectangle var7 = this.arms[var6].bounds;
         float var8 = var7.x + var7.width / 2.0F;
         float var9 = var7.y + var7.height / 2.0F;
         float var10 = 0.1F * this.world.rand.nextFloat();
         WorldAnimationPlayer.addAWorldAnimation(var1, var8, var9, 1, var5 + var10);
         WorldAnimationPlayer.addAWorldAnimation(var2, var8, var9, 1, var5 + var10, var3);
      }

   }

   public void renderEnemy(SpriteBatch var1) {
      float var2 = var1.getColor().toFloatBits();
      if(this.isBeHiting) {
         var1.setColor(Enemy.getHitColor());
      }

      for(int var3 = 0; var3 < this.arms.length; ++var3) {
         this.arms[var3].render(var1);
      }

      var1.setColor(var2);
      super.renderEnemy(var1);
   }

   public void renderSlawShadow(SpriteBatch var1) {
      if(!this.isCrashed() || this.stateTime <= this.getTimeOfCrash()) {
         Color var2 = var1.getColor();
         var1.setColor(0.0F, 0.0F, 0.0F, 0.7F);

         for(int var3 = 0; var3 < this.arms.length; ++var3) {
            this.arms[var3].renderArmShadow(var1);
         }

         var1.draw(this.enemyRegion, 30.0F + this.bounds.x, -30.0F + this.bounds.y, 0.0F, 0.0F, this.bounds.width, this.bounds.height, 1.0F, 1.0F, 0.0F);
         var1.setColor(var2);
      }
   }

   public void setSleepPosition(float var1, float var2) {
      super.setSleepPosition(var1, var2);
      this.stringX = var1;
      this.stringY = var2;

      for(int var3 = 0; var3 < this.arms.length; ++var3) {
         float var4 = this.bounds.x + ARMPOSITION[var3].x;
         float var5 = this.bounds.y + ARMPOSITION[var3].y;
         this.arms[var3].setSleepPosition(var4, var5);
      }

   }

   public void slawDropInit() {
      for(int var1 = 0; var1 < this.arms.length; ++var1) {
         this.arms[var1].dropInit();
      }

   }

   public void update(float var1) {
      super.update(var1);
      this.updateLandCraft();
      this.updateAirCraft();
      if(this.isActive && !this.isCrashed()) {
         for(int var2 = 0; var2 < this.arms.length; ++var2) {
            this.arms[var2].update(var1);
         }

         switch(slawState) {
         case Sleep:
            this.updateSleep(var1);
            return;
         case Attacking:
            this.updateAttacking(var1);
            return;
         default:
         }
      }
   }

   public void updateShooting(float var1) {}

   private static class EnableAction extends EnemyTools.EnemyAction {

      float initY;
      float offsetY;


      private EnableAction() {
         this.offsetY = 0.0F;
         this.initY = 0.0F;
      }

      public boolean act(float var1) {
         BossLv7LSlaw var2 = (BossLv7LSlaw)this.getEnemy();
         if(this.offsetY == 0.0F) {
            this.initY = var2.getPositionY();
         }

         if(this.offsetY > -150.0F) {
            this.offsetY += -150.0F * var1;
            float var4 = var2.getPositionY() + -150.0F * var1;
            var2.setSleepPosition(var2.getPositionX(), var4);
            return false;
         } else {
            var2.setSleepPosition(var2.getPositionX(), this.initY - 150.0F);
            var2.isActive = true;
            return true;
         }
      }
   }

   private class armPart extends GameObject {

      protected final int id;
      protected BossLv7LSlaw slaw;
      protected float springX;
      protected float springY;


      public armPart(float var2, float var3, BossLv7LSlaw var4, int var5) {
         super(var2 + BossLv7LSlaw.this.bounds.x, var3 + BossLv7LSlaw.this.bounds.y, (float)BossLv7LSlaw.ARMWIDTH / 1.0F, (float)BossLv7LSlaw.ARMHEIGHT / 1.0F);
         this.slaw = var4;
         this.id = var5;
         this.springX = this.getPositionX();
         this.springY = this.getPositionY();
      }

      public void dropInit() {
         this.springX = this.getPositionX();
         this.springY = this.getPositionY();
      }

      public void render(SpriteBatch var1) {
         var1.draw(BossLv7LSlaw.armRegion, this.bounds.x, this.bounds.y, 0.0F, 0.0F, this.bounds.width, this.bounds.height, 1.0F, 1.0F, 0.0F);
      }

      public void renderArmShadow(SpriteBatch var1) {
         var1.draw(BossLv7LSlaw.armRegion, 30.0F + this.bounds.x, -30.0F + this.bounds.y, 0.0F, 0.0F, this.bounds.width, this.bounds.height, 1.0F, 1.0F, 0.0F);
      }

      public void setSleepPosition(float var1, float var2) {
         this.setPosition(var1, var2);
         this.springX = var1;
         this.springY = var2;
      }

      public void update(float var1) {
         float var2 = (float)(-3 + this.id) / ((float)this.slaw.getArmNum() - 3.0F);
         float var3 = (float)this.id / (float)this.slaw.getArmNum();
         if(var2 <= 0.0F) {
            var2 = 0.0F;
         }

         float var4 = var2 * this.slaw.getDeltaX();
         float var5 = var3 * this.slaw.getDeltaY();
         this.setPosition(var4 + this.springX, var5 + this.springY);
      }
   }

   private static enum SlawState {

      Attacking,
      Sleep
   }

   private static class MakeWorkingAction extends EnemyTools.EnemyAction {

      float initY;
      float offsetY;


      private MakeWorkingAction() {
         this.offsetY = 0.0F;
         this.initY = 0.0F;
      }

      private void reset() {
         this.offsetY = 0.0F;
         this.initY = 0.0F;
      }

      public boolean act(float var1) {
         BossLv7LSlaw var2 = (BossLv7LSlaw)this.getEnemy();
         if(this.offsetY == 0.0F) {
            this.initY = var2.getPositionY();
         }

         if(var2.slawState == BossLv7LSlaw.SlawState.Sleep) {
            if(this.offsetY < 60.0F) {
               this.offsetY += 60.0F * var1;
               float var6 = var2.getPositionY() - 60.0F * var1;
               var2.setSleepPosition(var2.getPositionX(), var6);
               return false;
            } else {
               var2.setSleepPosition(var2.getPositionX(), this.initY - 60.0F);
               var2.slawState = BossLv7LSlaw.SlawState.Attacking;
               var2.strechTime = 0.0F;
//               BossLv7LSlaw.access$1520(var2, 100);
               this.reset();
               return true;
            }
         } else {
            Settings.e("invalid slaw state in BossLv7LSlaw");
            return true;
         }
      }
   }

   private static class MakeSleepAction extends EnemyTools.EnemyAction {

      float initY;
      float offsetY;


      private MakeSleepAction() {
         this.offsetY = 0.0F;
         this.initY = 0.0F;
      }

      private void reset() {
         this.offsetY = 0.0F;
         this.initY = 0.0F;
      }

      public boolean act(float var1) {
         BossLv7LSlaw var2 = (BossLv7LSlaw)this.getEnemy();
         if(this.offsetY == 0.0F) {
            this.initY = var2.getPositionY();
            if(var2.slawState == BossLv7LSlaw.SlawState.Attacking) {
               var2.slawState = BossLv7LSlaw.SlawState.Sleep;
               var2.sleepTime = 0.0F;
            }
         }

         if(this.offsetY < 60.0F) {
            this.offsetY += 60.0F * var1;
            float var3 = var2.getPositionY() + 60.0F * var1;
            var2.setSleepPosition(var2.getPositionX(), var3);
            return false;
         } else {
            var2.setSleepPosition(var2.getPositionX(), 60.0F + this.initY);
            this.reset();
            return true;
         }
      }
   }
}
