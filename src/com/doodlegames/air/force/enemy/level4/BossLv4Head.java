package com.doodlegames.air.force.enemy.level4;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.enemy.level4.BossLv4Cannon;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldAnimationPlayer;
import com.doodlegames.air.force.game.WorldAudio;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.BulletScreen;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.game.laser.CommonLaser;
import com.doodlegames.air.force.game.laser.LaserGun;
import com.doodlegames.air.force.resource.Assets_ShareInNazi;
import com.doodlegames.air.force.resource.Assets_level4;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class BossLv4Head extends Enemy {

   private static final int CANNONNUM = 4;
   public static final Vector2[] CANNONPOSITION;
   public static final int DEFAULTHEALTHYDEGREE = 220;
   public static  int EYELEFTHEIGHT = 0;
   public static final Vector2 EYELEFTORIGIN;
   public static final Vector2 EYELEFTPOSTION;
   public static  int EYELEFTWIDTH = 0;
   public static  int EYERIGHTHEIGHT = 0;
   public static final Vector2 EYERIGHTORIGIN;
   public static final Vector2 EYERIGHTPOSTION;
   public static  int EYERIGHTWIDTH = 0;
   public static  int HEIGHT = 0;
   public static final Vector2 ORIGIN;
   private static final int PARTSNUM = 5;
   public static final Vector2 REDEYEORIGIN;
   public static final Vector2 REDEYEPOSITION;
   private static final float TIMEOFPLAYCRASHANIMATION = 1.0F;
   public static final int WIDTH;
   public static TextureAtlas.AtlasRegion eyeLeftTextureRegion;
   public static TextureAtlas.AtlasRegion eyeRightTextureRegion;
   public static final TextureAtlas.AtlasRegion[] headTextureRegion;
   private BossLv4Head.BossState bossState;
   private float[][][] bulletScreens;
   private BossLv4Head.ActionCallBack callBack;
   private BossLv4Cannon[] cannons;
   private BossLv4Head.ChangeHead changeHead;
   private Clock clockShootModeChangeLeft;
   private Clock clockShootModeChangeRight;
   private Clock clockShootingLeft;
   private Clock clockShootingRight;
   private float eyeAlpha;
   private float headCrashTime;
   private Enemy.HitMapItem[] hitMap;
   private Rectangle hitR;
   private float hitRecRelativeX;
   private float hitRecRelativeY;
   private List<Rectangle> hitRectangles;
   private LaserGun laserGunSwap;
   private int shootingModeLeft;
   private int shootingModeRight;
   private Vector2 temp1Vector;
   private Vector2 temp2Vector;
   private Vector2 temp3Vector;
   private Vector2 temp4Vector;


   static {
      TextureAtlas.AtlasRegion[] var0 = new TextureAtlas.AtlasRegion[]{Assets_level4.atlas_Enemy.findRegion("lv_4_boss_head", 1), Assets_level4.atlas_Enemy.findRegion("lv_4_boss_head", 2)};
      headTextureRegion = var0;
      eyeLeftTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_eye", 1);
      eyeRightTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_eye", 2);
      int var1;
      if(headTextureRegion[0].rotate) {
         var1 = headTextureRegion[0].getRegionHeight();
      } else {
         var1 = headTextureRegion[0].getRegionWidth();
      }

      WIDTH = var1;
      int var2;
      if(headTextureRegion[0].rotate) {
         var2 = headTextureRegion[0].getRegionWidth();
      } else {
         var2 = headTextureRegion[0].getRegionHeight();
      }

      HEIGHT = var2;
      int var3;
      if(eyeLeftTextureRegion.rotate) {
         var3 = eyeLeftTextureRegion.getRegionHeight();
      } else {
         var3 = eyeLeftTextureRegion.getRegionWidth();
      }

      EYELEFTWIDTH = var3;
      int var4;
      if(eyeLeftTextureRegion.rotate) {
         var4 = eyeLeftTextureRegion.getRegionWidth();
      } else {
         var4 = eyeLeftTextureRegion.getRegionHeight();
      }

      EYELEFTHEIGHT = var4;
      int var5;
      if(eyeRightTextureRegion.rotate) {
         var5 = eyeRightTextureRegion.getRegionHeight();
      } else {
         var5 = eyeRightTextureRegion.getRegionWidth();
      }

      EYERIGHTWIDTH = var5;
      int var6;
      if(eyeRightTextureRegion.rotate) {
         var6 = eyeRightTextureRegion.getRegionWidth();
      } else {
         var6 = eyeRightTextureRegion.getRegionHeight();
      }

      EYERIGHTHEIGHT = var6;
      EYELEFTPOSTION = new Vector2(37.0F, 79.0F);
      EYERIGHTPOSTION = new Vector2(84.0F, 80.0F);
      REDEYEPOSITION = new Vector2(117.0F, 68.0F);
      Vector2[] var7 = new Vector2[]{new Vector2((-46.0F + (float)BossLv4Cannon.WIDTH / 2.0F) / 1.0F, (-121.0F + (float)BossLv4Cannon.HEIGHT / 2.0F) / 1.0F), new Vector2((-16.0F + (float)BossLv4Cannon.WIDTH / 2.0F) / 1.0F, (-121.0F + (float)BossLv4Cannon.HEIGHT / 2.0F) / 1.0F), new Vector2((140.0F + (float)BossLv4Cannon.WIDTH / 2.0F) / 1.0F, (-121.0F + (float)BossLv4Cannon.HEIGHT / 2.0F) / 1.0F), new Vector2((170.0F + (float)BossLv4Cannon.WIDTH / 2.0F) / 1.0F, (-121.0F + (float)BossLv4Cannon.HEIGHT / 2.0F) / 1.0F)};
      CANNONPOSITION = var7;
      ORIGIN = new Vector2(65.0F, 43.0F);
      EYELEFTORIGIN = new Vector2(ORIGIN.x - EYERIGHTPOSTION.x, ORIGIN.y - EYERIGHTPOSTION.y);
      EYERIGHTORIGIN = new Vector2(ORIGIN.x - EYELEFTPOSTION.x, ORIGIN.y - EYELEFTPOSTION.y);
      REDEYEORIGIN = new Vector2(ORIGIN.x - REDEYEPOSITION.x, ORIGIN.y - REDEYEPOSITION.y);
   }

   public BossLv4Head(World var1, float var2, float var3) {
      super(var1, Settings.lapFixBossDegree(220), var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      float[][][] var4 = new float[][][]{BulletScreen.bulletVelAngles[44], BulletScreen.bulletVelAngles[45], BulletScreen.bulletVelAngles[46], BulletScreen.bulletVelAngles[51]};
      this.bulletScreens = var4;
      this.enemyRegion = headTextureRegion[0];
      this.enemyBroRegion = null;
      this.origin.set(ORIGIN);
      this.bossState = BossLv4Head.BossState.STATE1;
      this.hitRectangles = new ArrayList(5);
      this.hitMap = new Enemy.HitMapItem[5];

      for(int var6 = 0; var6 < 5; ++var6) {
         this.hitMap[var6] = new Enemy.HitMapItem();
      }

      this.cannons = new BossLv4Cannon[4];

      for(int var7 = 0; var7 < 4; ++var7) {
         this.cannons[var7] = new BossLv4Cannon(var1, this.bounds.x + CANNONPOSITION[var7].x, this.bounds.y + CANNONPOSITION[var7].y);
      }

      this.hitRecRelativeX = (float)WIDTH / 4.0F / 1.0F;
      this.hitRecRelativeY = (float)HEIGHT / 4.0F / 1.0F;
      this.hitR = new Rectangle(this.bounds.x + this.hitRecRelativeX, this.bounds.y + this.hitRecRelativeY, (float)WIDTH / 2.0F / 1.0F, (float)HEIGHT / 2.0F / 1.0F);
      this.eyeAlpha = 0.0F;
      this.clockShootModeChangeRight = new Clock(0.0F, 6.0F + 2.0F * var1.rand.nextFloat(), Clock.ClockType.FireOnlyOnce);
      this.clockShootModeChangeLeft = new Clock(0.0F, 6.0F + 2.0F * var1.rand.nextFloat(), Clock.ClockType.FireOnlyOnce);
      this.clockShootingRight = new Clock(10.0F, 10.0F, 0.2F, (byte)4);
      this.clockShootingLeft = new Clock(10.0F, 10.0F, 0.2F, (byte)4);
      this.shootingModeRight = 1;
      this.shootingModeLeft = 1;
      this.temp1Vector = new Vector2();
      this.temp2Vector = new Vector2();
      this.temp3Vector = new Vector2();
      this.temp4Vector = new Vector2();
      this.constructNewAdded();
   }

   private void changeShootModeRightState1() {
      this.shootingModeRight = this.world.rand.nextInt(4);
      ++this.shootingModeRight;
      switch(this.shootingModeRight) {
      case 1:
      case 2:
         this.clockShootingRight.resetClock(10.0F, 10.0F, 0.2F, (byte)1);
         return;
      case 3:
      case 4:
         this.clockShootingRight.resetClockFireOnce(1.0F);
         return;
      default:
      }
   }

   private void changeShootStateLeftState1() {
      this.shootingModeLeft = this.world.rand.nextInt(4);
      ++this.shootingModeLeft;
      if(this.shootingModeLeft == 1 || this.shootingModeLeft == 2) {
         this.clockShootingLeft.resetClock(10.0F, 10.0F, 0.2F, (byte)1);
      }

      if(this.shootingModeLeft == 3 || this.shootingModeLeft == 4) {
         this.clockShootingLeft.resetClockFireOnce(1.0F);
      }

   }

   private void changeShootStateLeftState2() {
      this.shootingModeLeft = this.world.rand.nextInt(4);
      ++this.shootingModeLeft;
      if(this.shootingModeLeft >= 4) {
         ++this.shootingModeLeft;
      }

      switch(this.shootingModeLeft) {
      case 1:
      case 2:
         this.clockShootingLeft.resetClock(10.0F, 10.0F, 0.2F, (byte)1);
         return;
      case 3:
      case 5:
         this.clockShootingLeft.resetClockFireOnce(1.0F);
         return;
      case 4:
      default:
      }
   }

   private void changeShootStateRightState2() {
      this.shootingModeRight = this.world.rand.nextInt(5);
      ++this.shootingModeRight;
      if(this.shootingModeRight >= 3) {
         ++this.shootingModeRight;
      }

      switch(this.shootingModeRight) {
      case 1:
      case 2:
         this.clockShootingRight.resetClock(10.0F, 10.0F, 0.2F, (byte)1);
         return;
      case 3:
      default:
         return;
      case 4:
      case 5:
         this.clockShootingRight.resetClockFireOnce(1.0F);
         return;
      case 6:
         this.clockShootingRight.resetClockFireOnce(0.01F);
      }
   }

   private void changeToState2() {
      this.world.askStopFighting();
      this.putCrashAniamtion();
      EnemyTools.EnemyAction[] var1 = new EnemyTools.EnemyAction[]{EnemyTools.delay(0.5F, this.changeHead), EnemyTools.delay(1.0F), EnemyTools.enemyRotateTo(this, 15.0F, 0.4F, true), EnemyTools.delay(2.0F), this.callBack};
      this.addEnemyAction(EnemyTools.sequence(var1));
   }

   private void constructLaser() {
      this.laserGunSwap = new LaserGun(this, Assets_ShareInNazi.gather, 0.05F, 3.0F, 3.0F, 1.0F, 0.0F, 0.0F, CommonLaser.getWidthOfNaziHalf());
      this.laserGunSwap.setOriginX(31.0F);
      this.laserGunSwap.setOriginY(49.0F);
      this.laserGunSwap.shooterPointIsAbsolute = false;
      this.laserGunSwap.setLoopFire(false);
      float var2 = CommonLaser.getDeltaYOfNaziHalf(0);
      Enemy.lasers.add(CommonLaser.naziLaserGenHalfHead.getALaser(this, this.laserGunSwap, var2));

      for(int var4 = 1; var4 < 60; ++var4) {
         float var8 = CommonLaser.getDeltaYOfNaziHalf(var4);
         Enemy.lasers.add(CommonLaser.naziLaserGenHalf.getALaser(this, this.laserGunSwap, var8));
      }

      this.laserGunSwap.angle = -45.0F;
      LaserGun var5 = this.laserGunSwap;
      LaserGun var6 = this.laserGunSwap;
      var5.updater = var6.new UpdateLaserGun1(-45.0F, 45.0F);
      this.laserGunSwap.startShoot(Float.MAX_VALUE);
   }

   private void constructNewAdded() {
      this.changeHead = (BossLv4Head.ChangeHead)Pools.obtain(BossLv4Head.ChangeHead.class);
      this.changeHead.head = this;
      this.callBack = (BossLv4Head.ActionCallBack)Pools.obtain(BossLv4Head.ActionCallBack.class);
      this.callBack.head = this;
      this.headCrashTime = 0.0F;
      this.constructLaser();
   }

   private void leftShootState2(int var1, Vector2 var2, Vector2 var3, float var4) {
      switch(var1) {
      case 1:
         Enemy.getPursueVel(var2, this.world.fighter.position, var4, var3);
         Enemy.bullets.add(EnemyBullet1.naziBulletGen.getABullet(this, this.world, var2.x, var2.y, var3));
         return;
      case 2:
         Vector2 var5 = this.temp3Vector;
         if((double)this.world.rand.nextFloat() > 0.5D) {
            var5.set(this.world.fighter.bounds.x, this.world.fighter.bounds.y);
         } else {
            var5.set(this.world.fighter.bounds.x + this.world.fighter.bounds.width, this.world.fighter.bounds.y);
         }

         Enemy.getPursueVel(var2, var5, var4, var3);
         Enemy.bullets.add(EnemyBullet1.naziBulletGen.getABullet(this, this.world, var2.x, var2.y, var3));
         return;
      case 3:
         EnemyTools.naziShootScreen(this, EYELEFTPOSTION.x, EYELEFTPOSTION.y, BulletScreen.getPublicMiddleScreen(this.bulletScreens));
         return;
      case 4:
      default:
         return;
      case 5:
         EnemyTools.naziShootScreen(this, EYELEFTPOSTION.x, EYELEFTPOSTION.y, BulletScreen.getPublicMiddleScreen(this.bulletScreens));
      }
   }

   private int limitBulletDamage(Bullet var1) {
      int var2 = var1.damageValue;
      if(this.bossState == BossLv4Head.BossState.STATE1 && this.getHealthyDgree() - var1.damageValue < 20) {
         int var3 = -20 + this.getHealthyDgree();
         if(var3 <= 0) {
            var3 = 0;
         }

         var1.damageValue = var3;
      }

      return var2;
   }

   public static void loadResource() {
      headTextureRegion[0] = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_head", 1);
      headTextureRegion[1] = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_head", 2);
      eyeLeftTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_eye", 1);
      eyeRightTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_eye", 2);
   }

   private void recoverComplete() {
      this.setInitHealthyDgree(200);

      for(int var1 = 0; var1 < 4; ++var1) {
         this.cannons[var1].reset(this.world, this.cannons[var1].position.x, this.cannons[var1].position.y);
      }

      this.bossState = BossLv4Head.BossState.STATE2;
      this.changeShootStateLeftState2();
      this.changeShootStateRightState2();
      this.clockShootModeChangeLeft.resetClockFireOnce(6.0F + 2.0F * this.world.rand.nextFloat());
      this.clockShootModeChangeRight.resetClockFireOnce(7.0F + 2.0F * this.world.rand.nextFloat());
      this.world.askBeginFighting();
   }

   private void rightShootState2(int var1, Vector2 var2, Vector2 var3, float var4) {
      float var5 = var2.x - this.bounds.x;
      float var6 = var2.y - this.bounds.y;
      switch(var1) {
      case 1:
         Enemy.getPursueVel(var2, this.world.fighter.position, var4, var3);
         Enemy.bullets.add(EnemyBullet1.naziBulletGen.getABullet(this, this.world, var2.x, var2.y, var3));
         return;
      case 2:
         Vector2 var9 = this.temp3Vector;
         if((double)this.world.rand.nextFloat() > 0.5D) {
            var9.set(this.world.fighter.bounds.x, this.world.fighter.bounds.y);
         } else {
            var9.set(this.world.fighter.bounds.x + this.world.fighter.bounds.width, this.world.fighter.bounds.y);
         }

         Enemy.getPursueVel(var2, var9, var4, var3);
         Enemy.bullets.add(EnemyBullet1.naziBulletGen.getABullet(this, this.world, var2.x, var2.y, var3));
         return;
      case 3:
      default:
         return;
      case 4:
         EnemyTools.naziShootScreen(this, REDEYEPOSITION.x, REDEYEPOSITION.y, BulletScreen.getPublicMiddleScreen(this.bulletScreens));
         return;
      case 5:
         EnemyTools.naziShootScreen(this, var5, var6, BulletScreen.getPublicMiddleScreen(this.bulletScreens));
         return;
      case 6:
         this.laserGunSwap.setshootPointRelativeX(var5);
         this.laserGunSwap.setshootPointRelativeY(var6);
         this.laserGunSwap.startShoot(0.0F);
      }
   }

   private void shootState1(int var1, Vector2 var2, Vector2 var3, float var4) {
      float var5 = var2.x - this.bounds.x;
      float var6 = var2.y - this.bounds.y;
      switch(var1) {
      case 1:
         Enemy.getPursueVel(var2, this.world.fighter.position, var4, var3);
         Enemy.bullets.add(EnemyBullet1.naziBulletGen.getABullet(this, this.world, var2.x, var2.y, var3));
         return;
      case 2:
         Vector2 var7 = this.temp3Vector;
         float var8 = this.world.fighter.bounds.x;
         float var9 = this.world.fighter.bounds.y;
         if((double)this.world.rand.nextFloat() > 0.5D) {
            var8 += this.world.fighter.bounds.width;
         }

         var7.set(var8, var9);
         Enemy.getPursueVel(var2, var7, var4, var3);
         Enemy.bullets.add(EnemyBullet1.naziBulletGen.getABullet(this, this.world, var2.x, var2.y, var3));
         return;
      case 3:
         EnemyTools.naziShootScreen(this, var5, var6, BulletScreen.getPublicMiddleScreen(this.bulletScreens));
         return;
      case 4:
         EnemyTools.naziShootScreen(this, var5, var6, BulletScreen.getPublicMiddleScreen(this.bulletScreens));
         return;
      default:
      }
   }

   private void updateLaser(float var1) {
      if(!this.isCrashed() && this.bossState == BossLv4Head.BossState.STATE2 && this.shootingModeRight == 6) {
         this.laserGunSwap.updateShooting(var1);
      } else {
         this.laserGunSwap.forceStop();
      }
   }

   public void beHitByBullet(Bullet var1, int var2) {
      if(1 != this.state) {
         if(this.hitMap[var2].e != this) {
            this.hitMap[var2].e.beHitByBullet(var1, this.hitMap[var2].index);
            return;
         }

         int var3 = this.limitBulletDamage(var1);
         super.beHitByBullet(var1);
         var1.damageValue = var3;
         if(this.getHealthyDgree() <= 20 && this.bossState == BossLv4Head.BossState.STATE1) {
            this.bossState = BossLv4Head.BossState.STATECHANGING;
            this.changeToState2();
            return;
         }
      }

   }

   public boolean getAllMissleCannonCrash() {
      for(int var1 = 0; var1 < this.cannons.length; ++var1) {
         if(!this.cannons[var1].isCrashed()) {
            return false;
         }
      }

      return true;
   }

   public int getHeadState() {
      return this.bossState == BossLv4Head.BossState.STATE1?1:(this.bossState == BossLv4Head.BossState.STATE2?2:-1);
   }

   public List<Rectangle> getHitRectangle() {
      int var1 = 0;
      this.hitRectangles.clear();
      if(this.bossState != BossLv4Head.BossState.STATECHANGING) {
         for(int var2 = 0; var2 < 4; ++var2) {
            if(!this.cannons[var2].isCrashed()) {
               List var5 = this.cannons[var2].getHitRectangle();
               int var6 = var5.size();

               for(int var7 = 0; var7 < var6; ++var7) {
                  this.hitRectangles.add((Rectangle)var5.get(var7));
                  this.hitMap[var1].e = this.cannons[var2];
                  this.hitMap[var1].index = var7;
                  ++var1;
               }
            }
         }

         if(!super.isCrashed()) {
            this.hitR.x = this.bounds.x + this.hitRecRelativeX;
            this.hitR.y = this.bounds.y + this.hitRecRelativeY;
            this.hitRectangles.add(this.hitR);
            this.hitMap[var1].e = this;
            this.hitMap[var1].index = 0;
            int var10000 = var1 + 1;
         }
      }

      return this.hitRectangles;
   }

   public float getTimeOfCrash() {
      return 1.0F;
   }

   public boolean isCrashed() {
      if(super.isCrashed() && !this.crashAnimationPutted) {
         this.putCrashAniamtion();
         this.crashAnimationPutted = true;
      }

      if(super.isCrashed() && this.cannons[0].isCrashed() && this.cannons[1].isCrashed() && this.cannons[2].isCrashed() && this.cannons[3].isCrashed()) {
         this.makeCrash();
         return true;
      } else {
         return false;
      }
   }

   public void leftEyeUpdateShooting() {
      if(this.clockShootingLeft.isFired()) {
         int var1 = this.shootingModeLeft;
         BossLv4Head.BossState var2 = this.bossState;
         Vector2 var3 = this.temp1Vector;
         Vector2 var4 = this.temp2Vector;
         var3.set(this.bounds.x + EYELEFTPOSTION.x + (float)EYELEFTWIDTH / 1.0F, this.bounds.y + EYELEFTPOSTION.y + (float)EYELEFTHEIGHT / 1.0F);
         var4.set(0.0F, -250.0F);
         if(var2 == BossLv4Head.BossState.STATE1) {
            this.shootState1(var1, var3, var4, 250.0F);
         }

         Enemy.RotatePoint(var3, this.temp4Vector.set(this.bounds.x + ORIGIN.x, this.bounds.y + ORIGIN.y), 15.0F);
         if(var2 == BossLv4Head.BossState.STATE2) {
            this.leftShootState2(var1, var3, var4, 250.0F);
            return;
         }
      }

   }

   protected void putCrashAniamtion() {
      Animation var1 = this.getShatterAniamtion();
      Animation var2 = this.getCrashBoom(true);
      WorldAudio.PlaySound var3 = this.getCrashSound(true);
      float var4 = (float)Clock.getTimeCounter();
      float var5 = this.bounds.x + this.bounds.width / 5.0F;
      float var6 = this.bounds.y + this.bounds.height / 5.0F;
      WorldAnimationPlayer.addAWorldAnimation(var1, var5, var6, 1, var4);
      WorldAnimationPlayer.addAWorldAnimation(var2, var5, var6, 1, var4, var3);
      float var8 = this.bounds.x + EYELEFTPOSTION.x;
      float var9 = this.bounds.y + EYELEFTPOSTION.y;
      float var10 = var4 + 0.4F;
      WorldAnimationPlayer.addAWorldAnimation(var1, var8, var9, 1, var10);
      WorldAnimationPlayer.addAWorldAnimation(var2, var8, var9, 1, var10, var3);
      float var12 = this.bounds.x + EYERIGHTPOSTION.x;
      float var13 = this.bounds.y + EYERIGHTPOSTION.y;
      float var14 = var10 + 0.4F;
      WorldAnimationPlayer.addAWorldAnimation(var1, var12, var13, 1, var14);
      WorldAnimationPlayer.addAWorldAnimation(var2, var12, var13, 1, var14, var3);
   }

   public void render(SpriteBatch var1) {
      if(!super.isCrashed() || this.headCrashTime < 1.0F) {
         super.render(var1);
         Color var2 = var1.getColor();
         var1.setColor(1.0F, 1.0F, 1.0F, this.eyeAlpha);
         var1.draw(eyeLeftTextureRegion, this.bounds.x + EYELEFTPOSTION.x, this.bounds.y + EYELEFTPOSTION.y, EYERIGHTORIGIN.x, EYERIGHTORIGIN.y, (float)EYELEFTWIDTH / 1.0F, (float)EYELEFTHEIGHT / 1.0F, 1.0F, 1.0F, this.angle);
         if(this.bossState == BossLv4Head.BossState.STATE1) {
            var1.draw(eyeRightTextureRegion, this.bounds.x + EYERIGHTPOSTION.x, this.bounds.y + EYERIGHTPOSTION.y, EYELEFTORIGIN.x, EYELEFTORIGIN.y, (float)EYERIGHTWIDTH / 1.0F, (float)EYERIGHTHEIGHT / 1.0F, 1.0F, 1.0F, this.angle);
         }

         var1.setColor(var2);
      }

      for(int var3 = 0; var3 < 4; ++var3) {
         this.cannons[var3].render(var1);
      }

      if(!this.isCrashed()) {
         this.laserGunSwap.renderLaserAggregation(var1);
      }

   }

   public void rightEyeUpdateShooting() {
      if(this.clockShootingRight.isFired()) {
         int var1 = this.shootingModeRight;
         BossLv4Head.BossState var2 = this.bossState;
         Vector2 var3 = this.temp1Vector;
         Vector2 var4 = this.temp2Vector;
         var3.set(this.bounds.x + EYERIGHTPOSTION.x + (float)EYERIGHTWIDTH / 1.0F, this.bounds.y + EYERIGHTPOSTION.y + (float)EYERIGHTHEIGHT / 1.0F);
         var4.set(0.0F, -250.0F);
         if(var2 == BossLv4Head.BossState.STATE1) {
            this.shootState1(var1, var3, var4, 250.0F);
         }

         Vector2 var7 = this.temp4Vector.set(this.bounds.x + ORIGIN.x, this.bounds.y + ORIGIN.y);
         var3.set(this.bounds.x + REDEYEPOSITION.x, this.bounds.y + REDEYEPOSITION.y);
         Enemy.RotatePoint(var3, var7, 15.0F);
         if(var2 == BossLv4Head.BossState.STATE2) {
            this.rightShootState2(6, var3, var4, 250.0F);
            return;
         }
      }

   }

   public void setSleepPosition(float var1, float var2) {
      super.setSleepPosition(var1, var2);
      int var3 = this.cannons.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         float var5 = this.bounds.x + CANNONPOSITION[var4].x;
         float var6 = this.bounds.y + CANNONPOSITION[var4].y;
         this.cannons[var4].setSleepPosition(var5, var6);
      }

   }

   public void update(final float n) {

       super.update(n);

       switch (this.bossState) {

           case STATE1: {

               if (this.eyeAlpha < 1.0f) {

                   this.eyeAlpha = this.eyeAlpha + 0.2f * n;

                   break;

               }

               this.eyeAlpha = 1.0f;

               break;

           }

       }

       for (int i = 0; i < 4; ++i) {

           this.cannons[i].update(n);

       }

       if (super.isCrashed()) {

           this.headCrashTime = n + this.headCrashTime;

       }

       else {

           this.headCrashTime = 0.0f;

       }

       this.updateLaser(n);

   }

   public void updateShooting(float var1) {
      if(!super.isCrashed() && this.bossState != BossLv4Head.BossState.STATECHANGING) {
         if(this.clockShootModeChangeRight.isFired()) {
            if(this.bossState == BossLv4Head.BossState.STATE1) {
               this.changeShootModeRightState1();
            }

            if(this.bossState == BossLv4Head.BossState.STATE2) {
               this.changeShootStateRightState2();
            }

            this.clockShootModeChangeRight.resetClockFireOnce(7.0F + 2.0F * this.world.rand.nextFloat());
         } else {
            this.rightEyeUpdateShooting();
         }

         if(this.clockShootModeChangeLeft.isFired()) {
            if(this.bossState == BossLv4Head.BossState.STATE1) {
               this.changeShootStateLeftState1();
            }

            if(this.bossState == BossLv4Head.BossState.STATE2) {
               this.changeShootStateLeftState2();
            }

            this.clockShootModeChangeLeft.resetClockFireOnce(6.0F + 2.0F * this.world.rand.nextFloat());
         } else {
            this.leftEyeUpdateShooting();
         }
      }
   }

   public static class ChangeHead extends EnemyTools.EnemyAction {

      BossLv4Head head;


      public boolean act(float var1) {
         this.head.enemyRegion = BossLv4Head.headTextureRegion[1];
         return true;
      }
   }

   private static enum BossState {

      STATE1,
      STATECHANGING,
      STATE2,
   }

   public static class ActionCallBack extends EnemyTools.EnemyAction {

      BossLv4Head head;


      public boolean act(float var1) {
         this.head.recoverComplete();
         return true;
      }
   }
}
