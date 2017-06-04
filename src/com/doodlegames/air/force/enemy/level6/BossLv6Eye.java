package com.doodlegames.air.force.enemy.level6;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.level6.MutiLaserGun;
import com.doodlegames.air.force.game.EnemyGun;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.Gun.BulletGunAlien;
import com.doodlegames.air.force.game.Gun.EnemyActionGun;
import com.doodlegames.air.force.game.bullet.BulletScreen;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.game.bullet.EnemyBullet2;
import com.doodlegames.air.force.game.laser.CommonLaser;
import com.doodlegames.air.force.game.laser.LaserGun;
import com.doodlegames.air.force.resource.Assets_ShareInAlien;
import com.doodlegames.air.force.resource.Assets_level6;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.List;

public class BossLv6Eye extends Enemy {

   private static  float CANNONHEIGHT = 0.0F;
   private static final Vector2 CANNONRELATIVEPOSITION;
   private static float CANNONWIDTH = 0.0F;
   public static final int DEFAULTHEALTHYDEGREE = 5000;
   public static int HEIGHT = 0;
   private static final Vector2 SHOOTPOSITION;
   private static final float TIMEOFPLAYCRASHANIMATION = 4.0F;
   public static final int WIDTH;
   private static final TextureAtlas.AtlasRegion[] cannonRegion;
   private static TextureAtlas.AtlasRegion enemyBroTextureRegion;
   private static TextureAtlas.AtlasRegion enemyTextureRegion;
   private int cannonState;
   private float canonAlpha;
   public EnemyGun currentGun;
   public BossLv6Eye.EyeState eyeState;
   private EnemyGun[] guns;
   private Rectangle hitR = new Rectangle();
   private MutiLaserGun mutiLaserGun;
   private BulletGunAlien overBulletGun;
   private BulletGunAlien pointBulletGun;
   private BulletGunAlien pursueBulletGun;
   float shootDurationTime = 4.0F;
   float shootTimeStamp = -10.0F;
   private Clock stateChange;


   static {
      TextureAtlas.AtlasRegion[] var0 = new TextureAtlas.AtlasRegion[]{Assets_level6.atlas_Enemy.findRegion("lv_6_boss_canon", 1), Assets_level6.atlas_Enemy.findRegion("lv_6_boss_canon", 2), Assets_level6.atlas_Enemy.findRegion("lv_6_boss_canon", 3), Assets_level6.atlas_Enemy.findRegion("lv_6_boss_canon", 4), Assets_level6.atlas_Enemy.findRegion("lv_6_boss_canon", 5), Assets_level6.atlas_Enemy.findRegion("lv_6_boss_canon", 6)};
      cannonRegion = var0;
      enemyTextureRegion = Assets_level6.atlas_Enemy.findRegion("lv_6_boss_eye");
      enemyBroTextureRegion = Assets_level6.atlas_Enemy.findRegion("lv_6_boss_eye_broken");
      CANNONRELATIVEPOSITION = new Vector2(16.0F, 25.0F);
      int var1;
      if(cannonRegion[0].rotate) {
         var1 = cannonRegion[0].getRegionHeight();
      } else {
         var1 = cannonRegion[0].getRegionWidth();
      }

      CANNONWIDTH = 1.0F * (float)var1;
      int var2;
      if(cannonRegion[0].rotate) {
         var2 = cannonRegion[0].getRegionWidth();
      } else {
         var2 = cannonRegion[0].getRegionHeight();
      }

      CANNONHEIGHT = 1.0F * (float)var2;
      SHOOTPOSITION = new Vector2(31.0F, 44.0F);
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
   }

   public BossLv6Eye(World var1, float var2, float var3) {
      super(var1, 5000, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyBroTextureRegion;
      this.eyeState = BossLv6Eye.EyeState.Sleep;
      this.stateChange = new Clock(0.0F, 4.0F, Clock.ClockType.FireOnlyOnce);
      this.cannonState = 0;
      this.canonAlpha = 0.0F;
      this.pointBulletGun = new BulletGunAlien(this, var1, EnemyBullet1.alienBulletGen, new Clock(10.0F, 0.1F, (byte)1), new Vector2(SHOOTPOSITION.x, SHOOTPOSITION.y), new float[]{0.0F, 200.0F});
      this.pointBulletGun.setPursueParameter((Clock)null, var1.fighter.position, 0.0F, 0.0F);
      this.pointBulletGun.shootPointIsAbsolute = false;
      this.overBulletGun = new BulletGunAlien(this, var1, EnemyBullet1.alienBulletGen, new Clock(10.0F, 0.1F, (byte)1), new Vector2(SHOOTPOSITION.x, SHOOTPOSITION.y), new float[]{0.0F, 200.0F});
      this.overBulletGun.setPursueParameter((Clock)null, var1.fighter.position, -var1.fighter.bounds.width, 0.0F);
      this.overBulletGun.autoChangeOffset = true;
      this.overBulletGun.shootPointIsAbsolute = false;
      this.pursueBulletGun = new BulletGunAlien(this, var1, EnemyBullet2.alienBulletGen, new Clock(5.0F, 0.3F, (byte)1), new Vector2(SHOOTPOSITION.x, SHOOTPOSITION.y), new float[]{0.0F, 150.0F});
      this.pursueBulletGun.setPursueParameter((Clock)null, var1.fighter.position, 0.0F, 0.0F);
      this.pursueBulletGun.shootPointIsAbsolute = false;
      this.mutiLaserGun = new MutiLaserGun(this, Assets_ShareInAlien.gather, 0.01F, 3.0F, 1.5F, 0.5F, SHOOTPOSITION.x, SHOOTPOSITION.y, CommonLaser.getWidthOfAlienQuart(), new float[]{0.0F, 10.0F, 20.0F, 30.0F});
      this.mutiLaserGun.setOrigin(31.0F, 49.0F);
      float[][][] var7 = new float[][][]{BulletScreen.bulletVelAngles[6], BulletScreen.bulletVelAngles[7], BulletScreen.bulletVelAngles[11], BulletScreen.bulletVelAngles[12], BulletScreen.bulletVelAngles[13]};
      EnemyActionGun var8 = EnemyActionGun.getMutiScreenGun(this, EnemyBullet1.alienBulletGen, new Clock(100.0F, 100.0F, 0.1F, (byte)1), SHOOTPOSITION.x, SHOOTPOSITION.y, var7);
      EnemyGun[] var9 = new EnemyGun[]{this.pointBulletGun, this.overBulletGun, var8, this.pursueBulletGun, this.mutiLaserGun};
      this.guns = var9;
   }

   public static void loadResource() {
      int var0 = cannonRegion.length;

      for(int var1 = 0; var1 < var0; ++var1) {
         cannonRegion[var1] = Assets_level6.atlas_Enemy.findRegion("lv_6_boss_canon", var1 + 1);
      }

      enemyTextureRegion = Assets_level6.atlas_Enemy.findRegion("lv_6_boss_eye");
      enemyBroTextureRegion = Assets_level6.atlas_Enemy.findRegion("lv_6_boss_eye_broken");
   }

   public boolean canBeHit() {
      return this.eyeState != BossLv6Eye.EyeState.Sleep;
   }

   public List<Rectangle> getHitRectangle() {
      Rectangle var1 = this.bounds;
      this.hitR.x = 2.0F + var1.x;
      this.hitR.y = 16.0F + var1.y;
      this.hitR.width = var1.width - 4.0F;
      this.hitR.height = var1.height - 16.0F;
      List var2 = super.getHitRectangle();
      if(var2.get(0) != this.hitR) {
         var2.clear();
         var2.add(this.hitR);
      }

      return var2;
   }

   public LaserGun getLaserShooter(int var1) {
      return this.mutiLaserGun.laserGuns[var1];
   }

   public MutiLaserGun getMutiLaserGun() {
      return this.mutiLaserGun;
   }

   public Vector2 getShooterPosition() {
      return this.mutiLaserGun.getShootingPoint();
   }

   public float getTimeOfCrash() {
      return 4.0F;
   }

   public void render(SpriteBatch var1) {
      super.render(var1);
      if(this.currentGun == this.mutiLaserGun) {
         this.mutiLaserGun.renderLaserAggregation(var1);
      }

   }

   public void renderEnemy(SpriteBatch var1) {
      super.renderEnemy(var1);
      if(this.eyeState != BossLv6Eye.EyeState.Sleep) {
         Color var2 = var1.getColor();
         var1.setColor(1.0F, 1.0F, 1.0F, this.canonAlpha);
         var1.draw(cannonRegion[-1 + this.cannonState], this.bounds.x + CANNONRELATIVEPOSITION.x, this.bounds.y + CANNONRELATIVEPOSITION.y, this.origin.x, this.origin.y, CANNONWIDTH, CANNONHEIGHT, 1.0F, 1.0F, 0.0F);
         var1.setColor(var2);
      }

   }

   public boolean setState(int var1) {
      if(!this.isCrashed() && (this.currentGun == null || this.currentGun.canStop())) {
         this.cannonState = var1;
         switch(this.cannonState) {
         case 0:
            this.currentGun = null;
            return true;
         case 1:
            this.currentGun = this.guns[0];
            break;
         case 2:
            this.currentGun = this.guns[1];
            break;
         case 3:
            this.currentGun = this.guns[2];
            break;
         case 4:
            this.currentGun = this.guns[3];
            break;
         case 5:
            this.currentGun = this.guns[4];
            break;
         case 6:
            this.currentGun = this.guns[this.world.rand.nextInt(4)];
         case 7:
            break;
         default:
            this.currentGun = null;
         }

         this.eyeState = BossLv6Eye.EyeState.LightingUp;
         this.shootTimeStamp = Clock.getCurrentTime() - 10.0F;
         this.shootDurationTime = 4.0F;
         return true;
      } else {
         return false;
      }
   }

   public boolean setState(int var1, int var2, int var3) {
      boolean var4 = this.setState(var1);
      this.shootTimeStamp = 1.0F + Clock.getCurrentTime() + (float)(var2 * 1);
      this.shootDurationTime = (float)(var3 + 1);
      return var4;
   }

   public void update(final float n) {

       super.update(n);

       if (this.isCrashed()) {

           if (this.currentGun != null) {

               this.currentGun.forceStop();

               this.currentGun = null;

           }

       }

       else {

           switch (this.eyeState) {

               case Sleep: {

                   this.canonAlpha = 0.0f;

                   return;

               }

               case LightingUp: {

                   if (this.canonAlpha < 1.0f) {

                       this.canonAlpha = this.canonAlpha + 1.0f * n;

                   }

                   if (this.canonAlpha >= 1.0f) {

                       this.canonAlpha = 1.0f;

                       this.eyeState = EyeState.Shooting;

                       this.stateChange.resetClockFireOnce(this.shootDurationTime);

                       this.currentGun.reset();

                       return;

                   }

                   break;

               }

               case Shooting: {

                   if (this.currentGun == null) {

                       Settings.e("this.currentGun should not be null! serve error occured!");

                       return;

                   }

                   if (!this.stateChange.isFired()) {

                       break;

                   }

                   if (this.currentGun.canStop()) {

                       this.currentGun = null;

                       this.eyeState = EyeState.FadingDown;

                       return;

                   }

                   this.stateChange.resetClockFireOnce(0.3f);

                   return;

               }

               case FadingDown: {

                   if (this.canonAlpha > 0.0f) {

                       this.canonAlpha = this.canonAlpha - n / 1.0f;

                   }

                   if (this.canonAlpha <= 0.0f) {

                       this.canonAlpha = 0.0f;

                       this.eyeState = EyeState.Sleep;

                       return;

                   }

                   break;

               }

               default: {

                   return;

               }

           }

       }

   }

   public void updateShooting(float var1) {
      if(this.eyeState == BossLv6Eye.EyeState.Shooting && this.currentGun != null && Clock.getCurrentTime(this.shootTimeStamp) > 0.0F) {
         this.currentGun.updateShooting(var1);
      }
   }

   public static enum EyeState {

      FadingDown,
      LightingUp,
      Shooting,
      Sleep,
   }
}
