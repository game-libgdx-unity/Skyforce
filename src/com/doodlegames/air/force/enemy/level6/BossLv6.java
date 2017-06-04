package com.doodlegames.air.force.enemy.level6;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyProXY;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.enemy.HitDistribute;
import com.doodlegames.air.force.enemy.level6.BossLv6Eye;
import com.doodlegames.air.force.enemy.level6.BossLv6Glass;
import com.doodlegames.air.force.enemy.level6.MutiLaserGun;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.Gun.BulletGunAlien;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.game.laser.CommonLaser;
import com.doodlegames.air.force.game.laser.LaserShooter;
import com.doodlegames.air.force.resource.Assets_level6;
import com.doodlegames.air.force.screen.GameScreen;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class BossLv6 extends Enemy {

   public static final int DEFAULTHEALTHYDEGREE = 200000;
   private static final Vector2[] EYEPOSITIONS;
   public static final float GLASSFLYINGINTIME = 0.5F;
   public static final float GLASSFLYINGOUTTIME = 0.5F;
   public static int HEIGHT = 0;
   private static final int LASERMAXNUM = 100;
   public static final float LIGHTUPEYETIME = 1.0F;
   private static final Vector2[] SHOOTPOSITION;
   private static final float TIMEOFPLAYCRASHANIMATION = 40.0F;
   public static final int WIDTH;
   private static TextureAtlas.AtlasRegion enemyBroTextureRegion = Assets_level6.atlas_Enemy.findRegion("lv_6_boss_head_broken");
   private static TextureAtlas.AtlasRegion enemyTextureRegion = Assets_level6.atlas_Enemy.findRegion("lv_6_boss_head");
   public Pool<CommonLaser> EnemyLaser1Pool;
   int[] attackOrder;
   private BossLv6.BossLv6State bossState;
   private int bulletCount;
   int[] choosed;
   private Clock clockBossStateChange;
   private int confuseCounter;
   float dropTime;
   private BossLv6Eye[] eyes;
   private BossLv6Glass glass;
   private HitDistribute hitProcessor;
   private LaserShooter laserTemp;
   private BulletGunAlien overBulletGun;
   private BulletGunAlien pointBulletGun;
   protected HashSet<CommonLaser> shootingLaserSet;


   static {
      int var0;
      if(enemyTextureRegion.rotate) {
         var0 = enemyTextureRegion.getRegionHeight();
      } else {
         var0 = enemyTextureRegion.getRegionWidth();
      }

      WIDTH = var0;
      int var1;
      if(enemyTextureRegion.rotate) {
         var1 = enemyTextureRegion.getRegionWidth();
      } else {
         var1 = enemyTextureRegion.getRegionHeight();
      }

      HEIGHT = var1;
      Vector2[] var2 = new Vector2[]{new Vector2((-157.0F + (float)BossLv6Eye.WIDTH / 2.0F) / 1.0F, (-443.5F + (float)BossLv6Eye.HEIGHT / 2.0F) / 1.0F), new Vector2((257.0F + (float)BossLv6Eye.WIDTH / 2.0F) / 1.0F, (-443.5F + (float)BossLv6Eye.HEIGHT / 2.0F) / 1.0F), new Vector2((202.0F + (float)BossLv6Eye.WIDTH / 2.0F) / 1.0F, (-343.5F + (float)BossLv6Eye.HEIGHT / 2.0F) / 1.0F), new Vector2((-105.0F + (float)BossLv6Eye.WIDTH / 2.0F) / 1.0F, (-343.5F + (float)BossLv6Eye.HEIGHT / 2.0F) / 1.0F), new Vector2((-157.0F + (float)BossLv6Eye.WIDTH / 2.0F) / 1.0F, (-84.5F + (float)BossLv6Eye.HEIGHT / 2.0F) / 1.0F), new Vector2((-157.0F + (float)BossLv6Eye.WIDTH / 2.0F) / 1.0F, (-243.5F + (float)BossLv6Eye.HEIGHT / 2.0F) / 1.0F), new Vector2((257.0F + (float)BossLv6Eye.WIDTH / 2.0F) / 1.0F, (-84.5F + (float)BossLv6Eye.HEIGHT / 2.0F) / 1.0F), new Vector2((257.0F + (float)BossLv6Eye.WIDTH / 2.0F) / 1.0F, (-243.5F + (float)BossLv6Eye.HEIGHT / 2.0F) / 1.0F), new Vector2((-105.0F + (float)BossLv6Eye.WIDTH / 2.0F) / 1.0F, (15.5F + (float)BossLv6Eye.HEIGHT / 2.0F) / 1.0F), new Vector2((202.0F + (float)BossLv6Eye.WIDTH / 2.0F) / 1.0F, (15.5F + (float)BossLv6Eye.HEIGHT / 2.0F) / 1.0F), new Vector2((53.0F + (float)BossLv6Eye.WIDTH / 2.0F) / 1.0F, (-84.5F + (float)BossLv6Eye.HEIGHT / 2.0F) / 1.0F), new Vector2((53.0F + (float)BossLv6Eye.WIDTH / 2.0F) / 1.0F, (-243.5F + (float)BossLv6Eye.HEIGHT / 2.0F) / 1.0F)};
      EYEPOSITIONS = var2;
      Vector2[] var3 = new Vector2[]{new Vector2(18.0F, 255.0F), new Vector2(150.0F, 255.0F)};
      SHOOTPOSITION = var3;
   }

   public BossLv6(World var1, float var2, float var3) {
      super(var1, 200000, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.choosed = new int[EYEPOSITIONS.length];
      this.attackOrder = new int[EYEPOSITIONS.length];
      this.bulletCount = 0;
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyBroTextureRegion;
      this.eyes = new BossLv6Eye[EYEPOSITIONS.length];

      for(int var4 = 0; var4 < this.eyes.length; ++var4) {
         this.eyes[var4] = new BossLv6Eye(var1, this.bounds.x + EYEPOSITIONS[var4].x, this.bounds.y + EYEPOSITIONS[var4].y);
         EnemyTools.addAsyBeHitAction(this, this.eyes[var4]);
      }

      this.glass = new BossLv6Glass(this, var1);
      this.hitProcessor = new HitDistribute(this, 20);
      this.bossState = BossLv6.BossLv6State.Ready;
      this.clockBossStateChange = new Clock(0.0F, 1.0F, Clock.ClockType.FireOnlyOnce);
      this.confuseCounter = 0;
      this.pointBulletGun = new BulletGunAlien(this, var1, EnemyBullet1.alienBulletGen, new Clock(3.0F, 0.16F, (byte)1), new Vector2(SHOOTPOSITION[0].x, SHOOTPOSITION[0].y), new float[]{0.0F, 250.0F});
      this.pointBulletGun.setPursueParameter((Clock)null, var1.fighter.position, 0.0F, 0.0F);
      this.pointBulletGun.shootPointIsAbsolute = false;
      this.overBulletGun = new BulletGunAlien(this, var1, EnemyBullet1.alienBulletGen, new Clock(3.0F, 0.16F, (byte)1), new Vector2(SHOOTPOSITION[1].x, SHOOTPOSITION[1].y), new float[]{0.0F, 250.0F});
      this.overBulletGun.setPursueParameter((Clock)null, var1.fighter.position, -var1.fighter.bounds.width, 0.0F);
      this.overBulletGun.shootPointIsAbsolute = false;
      this.overBulletGun.autoChangeOffset = true;
      this.laserTemp = new LaserShooter() {

         private Vector2 shooterPoint = new Vector2(0.0F, 0.0F);

         public boolean canStop() {
            return true;
         }
         public void forceStop() {}
         public float getAlpha() {
            return 0.0F;
         }
         public float getLaserAngle() {
            return 0.0F;
         }
         public float getLaserWidth() {
            return 0.0F;
         }
         public Vector2 getShootingPoint() {
            return this.shooterPoint;
         }
         public boolean laseSleep() {
            return true;
         }
         public void reset() {}
         public boolean shouldRemove() {
            return false;
         }
         public void updateShooting(float var1) {}
      };
      this.EnemyLaser1Pool = new Pool() {
         protected CommonLaser newObject() {
            return (CommonLaser)CommonLaser.class.cast(CommonLaser.alienLaserGenQuart.getALaser(BossLv6.this, BossLv6.this.laserTemp, 0.0F));
         }
      };
      this.shootingLaserSet = new HashSet(100);
      this.shootingLaserSet.clear();

      for(int var7 = 0; var7 < 100; ++var7) {
         this.EnemyLaser1Pool.free(CommonLaser.class.cast(CommonLaser.alienLaserGenQuart.getALaser(this, this.laserTemp, 0.0F)));
      }

      this.needShowWarning = true;
   }

   public static Enemy addABossLv6(World var0, float var1, float var2, List<Enemy> var3) {
      BossLv6 var4 = new BossLv6(var0, var1, var2);
      var4.getPositionY();

      float var6;
      for(var6 = var2 - (float)(HEIGHT / 2) - 431.0F; var6 > 800.0F; var6 -= 800.0F) {
         ;
      }

      EnemyProXY var7 = (EnemyProXY)EnemyProXY.addAEnemy(var4, var6, var3, true);
      var7.setBoundsHeight(862.0F + var7.bounds.height);
      return var7;
   }

   private void addLaserToEye(BossLv6Eye var1, int var2, int var3, float var4) {
      CommonLaser var5 = (CommonLaser)this.EnemyLaser1Pool.obtain();
      this.shootingLaserSet.add(var5);
      var5.setDeltaY(var4);
      var5.setLaserShoot(var1.getMutiLaserGun().laserGuns[var2]);
      if(var3 == 0) {
         CommonLaser.switchToHead(var5);
      } else {
         CommonLaser.switchToBody(var5);
      }

      Enemy.lasers.add(var5);
   }

   private int chooseEye(int var1, int var2, int var3, int[] var4) {
      int var5 = 0;

      for(int var6 = 0; var6 < this.eyes.length && var5 < var3; ++var6) {
         float var7 = 0.25F;
         if(var2 - var5 >= this.eyes.length - var6) {
            Settings.i("fix probablity is happening.");
            var7 = 1.1F;
         }

         if(this.world.rand.nextFloat() <= var7) {
            int var8 = var5 + 1;
            var4[var5] = var6;
            var5 = var8;
         }
      }

      return var5;
   }

   private int countLightUpEye() {
      int var1 = 0;

      for(int var2 = 0; var2 < this.eyes.length; ++var2) {
         if(this.eyes[var2].eyeState != BossLv6Eye.EyeState.Sleep) {
            ++var1;
         }
      }

      return var1;
   }

   private void cycleLaserFromSet() {
      Iterator var1 = this.shootingLaserSet.iterator();

      while(var1.hasNext()) {
         CommonLaser var4 = (CommonLaser)var1.next();
         this.EnemyLaser1Pool.free((CommonLaser)var4);
         Enemy.lasers.remove(var4);
      }

      this.shootingLaserSet.clear();

      for(int var2 = 0; var2 < this.eyes.length; ++var2) {
         this.eyes[var2].getMutiLaserGun().setInUseNum(0);
      }

   }

   private void debugGlass(String var1) {
      String[] var2 = new String[]{"0", "1\u6307\u5411\u5f39", "2\u6aab\u8eab\u5f39", "3\u5f39\u5e55", "4\u8ddf\u8e2a\u5f39", "5\u6fc0\u5149", "6\u968f\u673a", "7\u7d0a\u4e71"};
      Settings.i(var1 + "this.glass.getPositionX()" + this.glass.getPositionX() + "\n" + "this.glass.getPositionY()" + this.glass.getPositionY() + "\n" + "getCannonState:" + var2[this.glass.getCannonState()] + "\n" + "glassState:" + this.glass.glassState.toString() + "\n" + "glassVelX:" + this.glass.velocity.x + " glassVelY:" + this.glass.velocity.y + "\n" + "glassAccelX:" + this.glass.accel.x + " glassVelY:" + this.glass.accel.y + "\n" + "\n\n");
      this.glass.checkFarAwayFromScreen(240.0F, this.glass.getPositionY());
   }

   private void lightUpEyes(int var1, int var2, int[] var3) {
      if(var1 == 5) {
         for(int var10 = 0; var10 < var2; ++var10) {
            this.eyes[var3[var10]].setState(var1);
         }
      } else {
         for(int var4 = 0; var4 < this.attackOrder.length; this.attackOrder[var4] = var4++) {
            ;
         }

         for(int var5 = 0; var5 < var2; ++var5) {
            int var6 = -1 + (var2 - var5);
            int var7 = Clock.rand.nextInt(var2 - var5);
            int var8 = this.attackOrder[var7];
            if(var6 != var7) {
               this.attackOrder[var7] = this.attackOrder[var6];
            }

            this.eyes[var3[var5]].setState(var1, var8, var2);
         }
      }

   }

   public static void loadResource() {
      enemyTextureRegion = Assets_level6.atlas_Enemy.findRegion("lv_6_boss_head");
      enemyBroTextureRegion = Assets_level6.atlas_Enemy.findRegion("lv_6_boss_head_broken");
   }

   public void beHitByBullet(Bullet var1, int var2) {
      this.hitProcessor.beHitByBullet(var1, var2);
      if(this.getHealthyDgree() < 0) {
         Settings.bossKilled(6, Clock.getCurrentTime(this.dropTime));
         this.makeCrash();
      }

      GameScreen.bossHealthyDegree = this.getHealthyDgree();
   }

   public void enemyDropped(EnemyProXY var1) {
      this.world.askBeginFighting(0.5F);
      this.world.playBossMusic();
      this.dropTime = Clock.getCurrentTime();
   }

   public int getHealthyDgree() {
      int var1 = (int)((float)Settings.lapFixBossDegree(800) * Settings.lapRatio());
      int var2 = 0;

      for(int var3 = 0; var3 < this.eyes.length; ++var3) {
         var2 += this.eyes[var3].getHealthyDgree();
      }

      return var1 - ((int)(5000.0F * Settings.lapRatio()) * this.eyes.length - var2);
   }

   public List<Rectangle> getHitRectangle() {
      this.hitProcessor.beginAdd();
      this.hitProcessor.addEnemyHitRec(this.glass);

      for(int var1 = 0; var1 < this.eyes.length; ++var1) {
         this.hitProcessor.addEnemyHitRec(this.eyes[var1]);
      }

      this.hitProcessor.endAdd();
      return this.hitProcessor.getHitRectangle();
   }

   public float getTimeOfCrash() {
      return 40.0F;
   }

   public void makeCrash() {
      super.makeCrash();
      this.glass.makeCrash();

      for(int var1 = 0; var1 < this.eyes.length; ++var1) {
         this.eyes[var1].makeCrash();
      }

   }

   public void render(SpriteBatch var1) {
      super.render(var1);

      for(int var2 = 0; var2 < this.eyes.length; ++var2) {
         this.eyes[var2].render(var1);
      }

      this.glass.render(var1);
   }

   public void setSleepPosition(float var1, float var2) {
      super.setSleepPosition(var1, var2);

      for(int var3 = 0; var3 < this.eyes.length; ++var3) {
         float var4 = this.bounds.x + EYEPOSITIONS[var3].x;
         float var5 = this.bounds.y + EYEPOSITIONS[var3].y;
         this.eyes[var3].setSleepPosition(var4, var5);
      }

   }

   public void update(final float n) {

       super.update(n);

       for (int i = 0; i < this.eyes.length; ++i) {

           this.eyes[i].update(n);

       }

       this.glass.update(n);

       if (!this.world.getStopFighting()) {

           switch (this.bossState) {

               case Ready: {

                   if (!this.clockBossStateChange.isFired()) {

                       break;

                   }

                   final int state = 1 + this.world.rand.nextInt(6);

                   if (this.glass.setState(state)) {

                       this.bossState = BossLv6State.GlassFlyingIn;

                       this.clockBossStateChange.resetClockFireOnce(0.8f);

                       return;

                   }

                   this.clockBossStateChange.resetClockFireOnce(0.5f);

                   this.debugGlass("set glassState failed \n");

                   return;

               }

               case GlassFlyingIn: {

                   if (!this.clockBossStateChange.isFired()) {

                       break;

                   }

                   final int cannonState = this.glass.getCannonState();

                   int n2 = 1;

                   int n3 = 5;

                   final int[] choosed = this.choosed;

                   if (cannonState == 5) {

                       n2 = 2;

                       n3 = 4;

                   }

                   if (cannonState == 7) {
                	   
//                       throw new RuntimeException("choose should not be 7, check please!");

                   }

                   int chooseEye = this.chooseEye(cannonState, n2, n3, choosed);

                   this.lightUpEyes(cannonState, chooseEye, choosed);

                   if (this.glass.getCannonState() == 5) {

                       if (chooseEye > 4) {

                           chooseEye = 4;

                       }

                       for (int j = 0; j < chooseEye; ++j) {

                           int k = j + 1;

                           while (k < chooseEye) {

                               final int n4 = choosed[j];

                               final int n5 = choosed[k];

                               final float n6 = this.eyes[n5].getShooterPosition().x - this.eyes[n4].getShooterPosition().x;

                               final float n7 = this.eyes[n5].getShooterPosition().y - this.eyes[n4].getShooterPosition().y;

                               final int n8 = (int)Math.sqrt((double)(n6 * n6 + n7 * n7));

                               final float angle = 90.0f + 57.295776f * MathUtils.atan2(n7, n6);

                               final float abs = Math.abs(CommonLaser.getDeltaYOfAlienQuart(1) - CommonLaser.getDeltaYOfAlienQuart(0));

                               final int n9 = -1 + (int)(n8 / abs);

                               final int inUseNum = this.eyes[n4].getMutiLaserGun().getInUseNum();

                               final MutiLaserGun mutiLaserGun = this.eyes[n4].getMutiLaserGun();

                               mutiLaserGun.laserGuns[inUseNum].angle = angle;

                               int l = 0;

                               while (l < n9) {

                                   final BossLv6Eye bossLv6Eye = this.eyes[n4];

                                   final float deltaYOfAlienQuart = CommonLaser.getDeltaYOfAlienQuart(l);

                                   this.addLaserToEye(bossLv6Eye, inUseNum, l, deltaYOfAlienQuart);

                                   ++l;

                               }

                               mutiLaserGun.setInUseNum(inUseNum + 1);

                               final int inUseNum2 = this.eyes[n5].getMutiLaserGun().getInUseNum();

                               this.addLaserToEye(this.eyes[n5], inUseNum2, 0, -0.5f * abs);

                               this.addLaserToEye(this.eyes[n5], inUseNum2, 1, -1.5f * abs);

                               this.eyes[n5].getMutiLaserGun().laserGuns[inUseNum2].angle = 180.0f + angle;

                               this.eyes[n5].getMutiLaserGun().setInUseNum(inUseNum2 + 1);

                               ++k;

                           }

                       }

                   }

                   this.bossState = BossLv6State.EyeShooting;

                   this.clockBossStateChange.resetClockFireOnce(3.2f);

                   return;

               }

               case EyeShooting: {

                   if (this.glass.isCrashed()) {

                       this.bossState = BossLv6State.EyeConfusing;

                       this.clockBossStateChange.resetClockFireOnce(0.0f);

                       this.confuseCounter = 0;

                   }

                   if (!this.clockBossStateChange.isFired()) {

                       break;

                   }

                   if (this.countLightUpEye() == 0 && this.glass.flyingOut()) {

                       this.bossState = BossLv6State.GlassFlyingOut;

                       this.clockBossStateChange.resetClockFireOnce(1.5f);

                       return;

                   }

                   this.clockBossStateChange.resetClockFireOnce(0.5f);

                   this.debugGlass("eyeShooting over, make glass flyOut failed.\n");

                   return;

               }

               case GlassFlyingOut: {

                   if (this.glass.getCannonState() == 5 && this.shootingLaserSet.iterator().hasNext()) {

                       this.cycleLaserFromSet();

                   }

                   if (this.clockBossStateChange.isFired()) {

                       this.bossState = BossLv6State.Ready;

                       this.clockBossStateChange.resetClockFireOnce(0.0f);

                       return;

                   }

                   break;

               }

               case EyeConfusing: {

                   if (!this.clockBossStateChange.isFired()) {

                       break;

                   }

                   while (this.confuseCounter < this.eyes.length && this.eyes[this.confuseCounter].eyeState != BossLv6Eye.EyeState.Sleep) {

                       this.confuseCounter = 1 + this.confuseCounter;

                   }

                   if (this.confuseCounter < this.eyes.length) {

                       this.eyes[this.confuseCounter].setState(1 + this.world.rand.nextInt(4));

                       this.clockBossStateChange.resetClockFireOnce(2.0f);

                       return;

                   }

                   if (this.countLightUpEye() == 0 && this.glass.flyingOut()) {

                       this.bossState = BossLv6State.GlassFlyingOut;

                       this.clockBossStateChange.resetClockFireOnce(0.5f);

                       return;

                   }

                   this.debugGlass("confusing over, flying out failed.");

                   this.clockBossStateChange.resetClockFireOnce(0.5f);

                   return;

               }

               default: {

                   return;

               }

           }

       }

   }

   public void updateShooting(float var1) {
      this.pointBulletGun.updateShooting(var1);
      this.overBulletGun.updateShooting(var1);
      if(this.pointBulletGun.canStop() && this.overBulletGun.canStop() && this.pointBulletGun.getShootBulletCount() != this.bulletCount) {
         float var2 = this.pointBulletGun.getShootingPoint().x;
         float var3 = this.pointBulletGun.getShootingPoint().y;
         Vector2 var4 = this.overBulletGun.getShootingPoint();
         this.pointBulletGun.setShootPosition(var4.x, var4.y);
         this.overBulletGun.setShootPosition(var2, var3);
         this.bulletCount = this.pointBulletGun.getShootBulletCount();
      }

   }

   private static enum BossLv6State {

      EyeConfusing,
      EyeShooting,
      GlassFlyingIn,
      GlassFlyingOut,
      Ready,
   }
}
