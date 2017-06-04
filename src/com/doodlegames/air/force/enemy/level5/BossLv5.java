package com.doodlegames.air.force.enemy.level5;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyProXY;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.enemy.level5.BossLv5Shield;
import com.doodlegames.air.force.enemy.level5.BossLv5Stone1;
import com.doodlegames.air.force.enemy.level5.BossLv5Stone2;
import com.doodlegames.air.force.enemy.level5.BossLv5Stone3;
import com.doodlegames.air.force.game.EnemyGun;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldAnimationPlayer;
import com.doodlegames.air.force.game.WorldAudio;
import com.doodlegames.air.force.game.Gun.EnemyActionGun;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.BulletScreen;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.game.laser.CommonLaser;
import com.doodlegames.air.force.game.laser.LaserGun;
import com.doodlegames.air.force.resource.Assets_ShareInAlien;
import com.doodlegames.air.force.resource.Assets_level5;
import com.doodlegames.air.force.screen.GameScreen;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class BossLv5 extends Enemy {

   public static  int COREHEIGHT = 0;
   public static final Vector2 COREPOSTION;
   public static  int COREWIDTH = 0;
   public static final int DEFAULTHEALTHYDEGREE = 1000;
   public static final float HEIGHT = 330.0F;
   private static final int PARTSNUM = 49;
   public static final Vector2 SCATTERPOSITION;
   private static final int SHIELDNUM = 3;
   public static final Vector2[] SHIELDPOSTION;
   public static final Vector2[] SHOOTPOINTS;
   public static final Vector2[] STONEPOSTION;
   private static final float TIMEOFPLAYCRASHANIMATION = 1.0F;
   public static final float WIDTH = 440.0F;
   public static TextureAtlas.AtlasRegion coreRegion = Assets_level5.atlas_Enemy.findRegion("lv_5_boss_core");
   private int attackPosition;
   private LaserGun bossLaserGun1;
   private BossLv5.BossState bossState;
   private Clock clockChangeGun;
   private Clock clockCrash;
   private Clock clockScatter;
   private EnemyGun currentGun;
   float dropTime;
   private BossLv5.EnterState2Action enterState2Action;
   private BossLv5.EnterState3Action enterState3Action;
   private Enemy.HitMapItem[] hitMap;
   private List<Rectangle> hitRectangles;
   private EnemyGun[] leftRightMiddleGuns;
   private Vector2 scatterVEL;
   private BossLv5Shield[] shields = new BossLv5Shield[3];
   private float state3TimeSpan = -1.0F;
   private BossLv5Stone1 stone1;
   private BossLv5Stone2 stone2;
   private BossLv5Stone3 stone3;


   static {
      int var0;
      if(coreRegion.rotate) {
         var0 = coreRegion.getRegionHeight();
      } else {
         var0 = coreRegion.getRegionWidth();
      }

      COREWIDTH = var0;
      int var1;
      if(coreRegion.rotate) {
         var1 = coreRegion.getRegionWidth();
      } else {
         var1 = coreRegion.getRegionHeight();
      }

      COREHEIGHT = var1;
      COREPOSTION = new Vector2(242.0F, 210.0F);
      SCATTERPOSITION = new Vector2(COREPOSTION.x + (float)COREWIDTH / 2.0F / 1.0F, COREPOSTION.y + (float)COREHEIGHT / 2.0F / 1.0F);
      Vector2[] var2 = new Vector2[]{new Vector2(34.0F + (float)BossLv5Shield.LWIDTH / 2.0F / 1.0F, 140.0F + (float)BossLv5Shield.LHEIGHT / 2.0F / 1.0F), new Vector2(281.0F + (float)BossLv5Shield.RWIDTH / 2.0F / 1.0F, 157.0F + (float)BossLv5Shield.RHEIGHT / 2.0F / 1.0F), new Vector2(158.0F + (float)BossLv5Shield.DWIDTH / 2.0F / 1.0F, 58.0F + (float)BossLv5Shield.DHEIGHT / 2.0F / 1.0F)};
      SHIELDPOSTION = var2;
      Vector2[] var3 = new Vector2[]{new Vector2(46.0F + (float)BossLv5Stone1.WIDTH / 2.0F / 1.0F, 100.0F + (float)BossLv5Stone1.HEIGHT / 2.0F / 1.0F), new Vector2(193.0F + (float)BossLv5Stone2.WIDTH / 2.0F / 1.0F, 93.0F + (float)BossLv5Stone2.HEIGHT / 2.0F / 1.0F), new Vector2(101.0F + (float)BossLv5Stone3.WIDTH / 2.0F / 1.0F, 65.0F + (float)BossLv5Stone3.HEIGHT / 2.0F / 1.0F)};
      STONEPOSTION = var3;
      Vector2[] var4 = new Vector2[]{new Vector2(96.0F, 278.0F), new Vector2(407.0F, 213.0F), new Vector2(245.0F, 77.0F)};
      SHOOTPOINTS = var4;
   }

   public BossLv5(World var1, float var2, float var3) {
      super(var1, Settings.lapFixBossDegree(1000), var2, var3, 440.0F, 330.0F);
      this.shields[0] = new BossLv5Shield(var1, this.bounds.x + SHIELDPOSTION[0].x, this.bounds.y + SHIELDPOSTION[0].y, BossLv5Shield.SheildPosition.LeftSheild);
      this.shields[1] = new BossLv5Shield(var1, this.bounds.x + SHIELDPOSTION[1].x, this.bounds.y + SHIELDPOSTION[1].y, BossLv5Shield.SheildPosition.RightSheild);
      this.shields[2] = new BossLv5Shield(var1, this.bounds.x + SHIELDPOSTION[2].x, this.bounds.y + SHIELDPOSTION[2].y, BossLv5Shield.SheildPosition.BottomSheild);
      this.stone1 = new BossLv5Stone1(var1, this.bounds.x + STONEPOSTION[0].x, this.bounds.y + STONEPOSTION[0].y);
      this.stone2 = new BossLv5Stone2(var1, this.bounds.x + STONEPOSTION[1].x, this.bounds.y + STONEPOSTION[1].y);
      this.stone3 = new BossLv5Stone3(var1, this.bounds.x + STONEPOSTION[2].x, this.bounds.y + STONEPOSTION[2].y);
      this.hitRectangles = new ArrayList(49);
      this.hitMap = new Enemy.HitMapItem[49];

      for(int var4 = 0; var4 < 49; ++var4) {
         this.hitMap[var4] = new Enemy.HitMapItem();
      }

      this.bossState = BossLv5.BossState.BossLv5State1;
      this.clockCrash = new Clock(0.0F, 200000.0F, Clock.ClockType.FireOnlyOnce);
      this.clockScatter = new Clock(0.0F, 200000.0F, Clock.ClockType.FireOnlyOnce);
      this.scatterVEL = new Vector2();
      float var5 = SHOOTPOINTS[0].x;
      float var6 = SHOOTPOINTS[0].y;
      this.bossLaserGun1 = new LaserGun(this, Assets_ShareInAlien.gather, 0.2F, 3.0F, 2.0F, 1.0F, var5, var6, CommonLaser.getWidthOfAlienHalf());
      this.bossLaserGun1.setOriginX(31.0F);
      this.bossLaserGun1.setOriginY(49.0F);
      this.bossLaserGun1.shooterPointIsAbsolute = false;
      this.bossLaserGun1.setLoopFire(false);

      for(int var8 = 0; var8 < 50; ++var8) {
         float var17 = CommonLaser.getDeltaYOfAlienHalf(var8);
         if(var8 == 0) {
            Enemy.lasers.add(CommonLaser.alienLaserGenHalfHead.getALaser(this, this.bossLaserGun1, var17));
         } else {
            Enemy.lasers.add(CommonLaser.alienLaserGenHalf.getALaser(this, this.bossLaserGun1, var17));
         }
      }

      this.bossLaserGun1.startShoot(0.0F);
      float[][][] var9 = new float[][][]{BulletScreen.bulletVelAngles[29], BulletScreen.bulletVelAngles[50], BulletScreen.bulletVelAngles[27]};
      float[][][] var10 = new float[][][]{BulletScreen.bulletVelAngles[29], BulletScreen.bulletVelAngles[50], BulletScreen.bulletVelAngles[28]};
      float[][][] var11 = new float[][][]{BulletScreen.bulletVelAngles[29], BulletScreen.bulletVelAngles[50]};
      Clock var12 = new Clock(10.0F, 10.0F, 0.5F, (byte)1);
      EnemyActionGun var13 = EnemyActionGun.getAlienMutiScreGun(this, var12, var5, var6, var9);
      Clock var14 = new Clock(10.0F, 10.0F, 0.5F, (byte)1);
      EnemyActionGun var15 = EnemyActionGun.getAlienMutiScreGun(this, var14, var5, var6, var10);
      Clock var16 = new Clock(10.0F, 10.0F, 0.5F, (byte)1);
      this.leftRightMiddleGuns = new EnemyGun[]{var13, var15, EnemyActionGun.getAlienMutiScreGun(this, var16, var5, var6, var11)};
      this.currentGun = this.leftRightMiddleGuns[0];
      this.attackPosition = 0;
      this.clockChangeGun = new Clock(0.0F, 7.0F);
      this.constructNewAdded();
   }

   public static Enemy addABossLv5(World var0, float var1, float var2, float var3, List<Enemy> var4) {
      return EnemyProXY.addAEnemy(new BossLv5(var0, var1, var2), var3, var4, true);
   }

   private void changeGunPositionUpdating(Enemy[] var1) {
      if(this.clockChangeGun.isFired(0) && (this.currentGun == null || this.currentGun.canStop())) {
         this.attackPosition = (1 + this.attackPosition) % 3;
         this.switchCurrentGun();
         if(var1[this.attackPosition].isCrashed()) {
            this.currentGun = null;
         } else {
            if(this.currentGun != null) {
               float var3 = SHOOTPOINTS[this.attackPosition].x;
               float var4 = SHOOTPOINTS[this.attackPosition].y;
               this.currentGun.getShootingPoint().set(var3, var4);
               if(this.currentGun instanceof LaserGun) {
                  LaserGun var6 = (LaserGun)this.currentGun;
                  var6.forceStop();
                  var6.setshootPointRelativeX(var3);
                  var6.setshootPointRelativeY(var4);
               }

               this.currentGun.reset();
            }

            this.clockChangeGun.isFired();
         }
      }
   }

   private void constructNewAdded() {
      this.enterState2Action = (BossLv5.EnterState2Action)Pools.obtain(BossLv5.EnterState2Action.class);
      this.enterState2Action.reset(this);
      this.enterState3Action = (BossLv5.EnterState3Action)Pools.obtain(BossLv5.EnterState3Action.class);
      this.enterState3Action.reset(this);
      this.needShowWarning = true;
   }

   private void enterState2Begin() {
      this.world.askStopFighting();
      if(this.currentGun != null) {
         this.currentGun.forceStop();
      }

   }

   private void enterState2End() {
      if(this.bossState != BossLv5.BossState.BossLv5State1) {
         Settings.e("invalid state in eneterState2.");
      } else {
         this.bossState = BossLv5.BossState.BossLv5State2;
         this.stone1.enterNewState(this.bossState);
         this.stone2.enterNewState(this.bossState);
         this.stone3.enterNewState(this.bossState);
         this.world.askBeginFighting();
      }
   }

   private void enterState3Begin() {
      this.world.askStopFighting();
      if(this.currentGun != null) {
         this.currentGun.forceStop();
      }

      this.world.worldAudio.playAlienLaseGather();
      this.state3TimeSpan = Clock.getCurrentTime();
   }

   private void enterState3End() {
      this.clockCrash.resetClockFireOnce(1.2F);
      this.clockScatter.resetClockFireOnce(0.1F);
      this.bossState = BossLv5.BossState.BossLv5State3;
      this.world.askBeginFighting();
      this.putCoreAnimation();
   }

   public static void loadResource() {
      coreRegion = Assets_level5.atlas_Enemy.findRegion("lv_5_boss_core");
   }

   private void putCoreAnimation() {
      float var10000 = this.bounds.x + COREPOSTION.x;
      var10000 = this.bounds.y + COREPOSTION.y;
      float var3 = (float)Clock.getTimeCounter();
      Animation var4 = this.getShatterAniamtion();
      Animation var5 = this.getCrashBoom(true);
      WorldAudio.PlaySound var6 = this.getCrashSound(true);

      for(int var7 = 0; var7 < 3; ++var7) {
         float var8 = this.bounds.x + COREPOSTION.x + (float)COREWIDTH * Clock.rand.nextFloat();
         float var9 = this.bounds.y + COREPOSTION.y + (float)COREHEIGHT * Clock.rand.nextFloat();
         WorldAnimationPlayer.addAWorldAnimation(var4, var8, var9, 1, var3);
         WorldAnimationPlayer.addAWorldAnimation(var5, var8, var9, 1, var3, var6);
         var3 += 0.3F + 0.2F * Clock.rand.nextFloat();
      }

   }

   private void switchCurrentGun() {
      EnemyGun[] var1 = new EnemyGun[]{this.bossLaserGun1, this.leftRightMiddleGuns[this.attackPosition]};
      if(Clock.rand.nextFloat() < 0.25F) {
         this.currentGun = var1[0];
      } else {
         this.currentGun = var1[1];
      }
   }

   public void beHitByBullet(Bullet var1, int var2) {
      if(1 != this.state) {
         if(this.hitMap[var2].e == this) {
            super.beHitByBullet(var1);
         } else {
            this.hitMap[var2].e.beHitByBullet(var1, this.hitMap[var2].index);
         }

         GameScreen.bossHealthyDegree = this.getHealthyDgree();
      }
   }

   public void enemyDropped(EnemyProXY var1) {
      this.world.askBeginFighting(0.5F);
      this.world.playBossMusic();
      this.dropTime = Clock.getCurrentTime();
   }

   public float getDropX() {
      return this.bounds.x + COREPOSTION.x;
   }

   public float getDropY() {
      return this.bounds.y + COREPOSTION.y;
   }

   public int getHealthyDgree() {
      int var1 = 0;
      BossLv5Shield[] var2 = this.shields;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         BossLv5Shield var11 = var2[var4];
         int var12;
         if(var11.getHealthyDgree() > 0) {
            var12 = var11.getHealthyDgree();
         } else {
            var12 = 0;
         }

         var1 += var12;
      }

      int var5;
      if(this.stone1.getHealthyDgree() > 0) {
         var5 = this.stone1.getHealthyDgree();
      } else {
         var5 = 0;
      }

      int var6 = var1 + var5;
      int var7;
      if(this.stone2.getHealthyDgree() > 0) {
         var7 = this.stone2.getHealthyDgree();
      } else {
         var7 = 0;
      }

      int var8 = var6 + var7;
      int var9 = this.stone3.getHealthyDgree();
      int var10 = 0;
      if(var9 > 0) {
         var10 = this.stone3.getHealthyDgree();
      }

      return var8 + var10;
   }

   public List<Rectangle> getHitRectangle() {
      this.hitRectangles.clear();
      BossLv5.BossState var1 = this.bossState;
      BossLv5.BossState var2 = BossLv5.BossState.BossLv5State1;
      int var3 = 0;
      if(var1 == var2) {
         for(int var16 = 0; var16 < 3; ++var16) {
            if(!this.shields[var16].isCrashed()) {
               List var17 = this.shields[var16].getHitRectangle();
               int var18 = var17.size();

               for(int var19 = 0; var19 < var18; ++var19) {
                  this.hitRectangles.add((Rectangle) var17.get(var19));
                  this.hitMap[var3].e = this.shields[var16];
                  this.hitMap[var3].index = var19;
                  ++var3;
               }
            }
         }
      }

      if(this.bossState == BossLv5.BossState.BossLv5State2) {
         if(!this.stone1.isCrashed()) {
            List var12 = this.stone1.getHitRectangle();
            int var13 = var12.size();

            for(int var14 = 0; var14 < var13; ++var14) {
               this.hitRectangles.add((Rectangle)var12.get(var14));
               this.hitMap[var3].e = this.stone1;
               this.hitMap[var3].index = var14;
               ++var3;
            }
         }

         if(!this.stone2.isCrashed()) {
            List var8 = this.stone2.getHitRectangle();
            int var9 = var8.size();

            for(int var10 = 0; var10 < var9; ++var10) {
               this.hitRectangles.add((Rectangle)var8.get(var10));
               this.hitMap[var3].e = this.stone2;
               this.hitMap[var3].index = var10;
               ++var3;
            }
         }

         if(!this.stone3.isCrashed()) {
            List var4 = this.stone3.getHitRectangle();
            int var5 = var4.size();

            for(int var6 = 0; var6 < var5; ++var6) {
               this.hitRectangles.add((Rectangle)var4.get(var6));
               this.hitMap[var3].e = this.stone3;
               this.hitMap[var3].index = var6;
               ++var3;
            }
         }
      }

      return this.hitRectangles;
   }

   public float getTimeOfCrash() {
      return 1.0F;
   }

   public boolean isCrashed() {
      if(this.bossState == BossLv5.BossState.BossLv5State4) {
         if(!super.isCrashed()) {
            Settings.bossKilled(5, Clock.getCurrentTime(this.dropTime));
            this.makeCrash();
         }

         return true;
      } else {
         return false;
      }
   }

   public void render(SpriteBatch var1) {
      if(this.clockScatter.getCycleCount() == 0) {
         float var2 = var1.getColor().toFloatBits();
         float var6;
         int var3 = (var6 = this.state3TimeSpan - 0.0F) == 0.0F?0:(var6 < 0.0F?-1:1);
         int var4 = 0;
         int var5 = 0;
         if(var3 > 0) {
            if((int)(Clock.getCurrentTime(this.state3TimeSpan) / 0.2F) % 2 == 0) {
               var1.setColor(Enemy.getHitColor());
            }

            var4 = -5 + Clock.rand.nextInt(11);
            var5 = -5 + Clock.rand.nextInt(11);
         }

         var1.draw(coreRegion, this.bounds.x + COREPOSTION.x + (float)var4, this.bounds.y + COREPOSTION.y + (float)var5, this.origin.x, this.origin.y, (float)COREWIDTH / 1.0F, (float)COREHEIGHT / 1.0F, 1.0F, 1.0F, 0.0F);
         if(this.state3TimeSpan > 0.0F) {
            var1.setColor(var2);
         }
      }

      this.stone1.render(var1);
      this.stone2.render(var1);
      this.stone3.render(var1);
      this.shields[0].render(var1);
      this.shields[1].render(var1);
      this.shields[2].render(var1);
      this.bossLaserGun1.renderLaserAggregation(var1);
   }

   public void setSleepPosition(float var1, float var2) {
      super.setSleepPosition(var1, var2);
      this.shields[0].setSleepPosition(this.bounds.x + SHIELDPOSTION[0].x, this.bounds.y + SHIELDPOSTION[0].y);
      this.shields[1].setSleepPosition(this.bounds.x + SHIELDPOSTION[1].x, this.bounds.y + SHIELDPOSTION[1].y);
      this.shields[2].setSleepPosition(this.bounds.x + SHIELDPOSTION[2].x, this.bounds.y + SHIELDPOSTION[2].y);
      this.stone1.setSleepPosition(this.bounds.x + STONEPOSTION[0].x, this.bounds.y + STONEPOSTION[0].y);
      this.stone2.setSleepPosition(this.bounds.x + STONEPOSTION[1].x, this.bounds.y + STONEPOSTION[1].y);
      this.stone3.setSleepPosition(this.bounds.x + STONEPOSTION[2].x, this.bounds.y + STONEPOSTION[2].y);
      this.bossLaserGun1.getShootingPoint().set(this.bounds.x + SHOOTPOINTS[this.attackPosition].x, this.bounds.y + SHOOTPOINTS[this.attackPosition].y);
   }

   public void update(final float n) {

       super.update(n);

       this.shields[0].update(n);

       this.shields[1].update(n);

       this.shields[2].update(n);

       this.stone1.update(n);

       this.stone2.update(n);

       this.stone3.update(n);

       if (this.bossState == BossState.BossLv5State1 && this.shields[0].isCrashed() && this.shields[1].isCrashed() && this.shields[2].isCrashed() && !this.enterState2Action.actionAdded()) {

           this.addEnemyAction(this.enterState2Action);

       }

       if (this.bossState == BossState.BossLv5State2 && this.shields[0].isCrashed() && this.shields[1].isCrashed() && this.shields[2].isCrashed() && this.stone1.isCrashed() && this.stone2.isCrashed() && this.stone3.isCrashed() && !this.enterState3Action.actionAdded()) {

           this.addEnemyAction(this.enterState3Action);

       }

       if (this.bossState == BossState.BossLv5State3 && this.clockCrash.isFired()) {

           this.bossState = BossState.BossLv5State4;

       }

       final Enemy[] array = { this.stone1, this.stone2, this.stone3 };

       switch (this.bossState) {

           case BossLv5State1: {

               this.changeGunPositionUpdating(array);

               break;

           }

           case BossLv5State2: {

               if (array[this.attackPosition].isCrashed() && this.currentGun != null) {

                   this.currentGun.forceStop();

                   this.currentGun = null;

               }

               if (!this.enterState3Action.actionAdded()) {

                   this.changeGunPositionUpdating(array);

                   break;

               }

               break;

           }

       }

       if (this.bossState == BossState.BossLv5State2) {}

   }

   public void updateShooting(float var1) {
      if(this.clockScatter.isFired()) {
         this.scatterVEL.set(0.0F, -350.0F);

         for(int var3 = 0; var3 < 90; ++var3) {
            for(int var4 = 0; var4 < 4; ++var4) {
               Enemy.bullets.add(EnemyBullet1.alienBulletGen.getABullet(this, this.world, this.bounds.x + SCATTERPOSITION.x, this.bounds.y + SCATTERPOSITION.y, this.scatterVEL));
               this.scatterVEL.set(-this.scatterVEL.y, this.scatterVEL.x);
            }

            this.scatterVEL.rotate(10.0F);
         }
      }

      if(this.currentGun != null) {
         this.currentGun.updateShooting(var1);
      }

   }

   public static enum BossState {

      BossLv5State1,
      BossLv5State2,
      BossLv5State3,
      BossLv5State4,
   }

   private static class EnterState2Action extends EnemyTools.EnemyAction {

      private static final float DELAYTIME = 3.0F;
      BossLv5 bossLv5;
      float currentTime;


      public boolean act(float var1) {
         if(this.currentTime == 0.0F) {
            this.bossLv5.enterState2Begin();
         }

         if(this.currentTime > 3.0F) {
            this.bossLv5.enterState2End();
            return true;
         } else {
            this.currentTime += var1;
            return false;
         }
      }

      public boolean actionAdded() {
         return this.bossLv5.enemyActions.contains(this, true);
      }

      public void reset(BossLv5 var1) {
         this.bossLv5 = var1;
         this.currentTime = 0.0F;
      }
   }

   private static class EnterState3Action extends EnemyTools.EnemyAction {

      private static final float DELAYTIME = 3.0F;
      BossLv5 bossLv5;
      float currentTime;


      public boolean act(float var1) {
         if(this.currentTime == 0.0F) {
            this.bossLv5.enterState3Begin();
         }

         if(this.currentTime > 3.0F) {
            this.bossLv5.enterState3End();
            return true;
         } else {
            this.currentTime += var1;
            return false;
         }
      }

      public boolean actionAdded() {
         return this.bossLv5.enemyActions.contains(this, true);
      }

      public void reset(BossLv5 var1) {
         this.bossLv5 = var1;
         this.currentTime = 0.0F;
      }
   }
}
