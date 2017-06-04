package com.doodlegames.air.force.enemy.level4;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.laser.CommonLaser;
import com.doodlegames.air.force.game.laser.LaserGun;
import com.doodlegames.air.force.resource.Assets_ShareInAlien;
import com.doodlegames.air.force.resource.Assets_level4;
import com.doodlegames.air.force.utils.Settings;

public class AlienRailGuner extends Enemy {

   public static final int DEFAULTHEALTHYDEGREE = 20;
   public static int HEIGHT = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 0.3F;
   public static final int WIDTH;
   public static TextureAtlas.AtlasRegion canonTextureRegion = Assets_level4.atlas_Enemy.findRegion("alien_railguner");
   private static final Rectangle hitRectangle;
   public static final Vector2 shootingPostion = new Vector2(8.0F, 2.0F);
   private Rectangle hitR;
   private LaserGun laserGun;


   static {
      int var0;
      if(canonTextureRegion.rotate) {
         var0 = canonTextureRegion.getRegionHeight();
      } else {
         var0 = canonTextureRegion.getRegionWidth();
      }

      WIDTH = var0;
      int var1;
      if(canonTextureRegion.rotate) {
         var1 = canonTextureRegion.getRegionWidth();
      } else {
         var1 = canonTextureRegion.getRegionHeight();
      }

      HEIGHT = var1;
      hitRectangle = new Rectangle(0.0F, 11.0F, (float)WIDTH / 1.0F, (float)(-11 + HEIGHT) / 1.0F);
   }

   public AlienRailGuner(World var1, float var2, float var3) {
      super(var1, 20, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.hitR = new Rectangle(hitRectangle);
      this.hitR.x = this.bounds.x + hitRectangle.x;
      this.hitR.y = this.bounds.y + hitRectangle.y;
      this.velocity.set(Settings.backgroundVelocity);
      this.laserGun = new LaserGun(this, Assets_ShareInAlien.gather, 0.2F, 3.0F, 3.0F, 1.0F, 26.0F, 16.0F, CommonLaser.getWidthOfAlienQuart());
      this.laserGun.shooterPointIsAbsolute = false;
      this.laserGun.setLoopFire(false);
      this.laserGun.setOriginX(31.0F);
      this.laserGun.setOriginY(49.0F);

      for(int var6 = 0; var6 < 120; ++var6) {
         float var7 = CommonLaser.getDeltaYOfAlienQuart(var6);
         if(var6 == 0) {
            Enemy.lasers.add(CommonLaser.alienLaserGenQuartHead.getALaser(this, this.laserGun, var7));
         } else {
            Enemy.lasers.add(CommonLaser.alienLaserGenQuart.getALaser(this, this.laserGun, var7));
         }
      }

      this.laserGun.startShoot((float)var1.rand.nextInt(3));
      this.enemyRegion = canonTextureRegion;
   }

   public static void loadResource() {
      canonTextureRegion = Assets_level4.atlas_Enemy.findRegion("alien_railguner");
   }

   public void enemyRemoved() {
      super.enemyRemoved();
      this.laserGun.forceStop();
      this.laserGun.setRemoveAllLasers();
   }

   public void freeToPool() {}

   public Rectangle getHitR() {
      this.hitR.x = this.bounds.x + hitRectangle.x;
      this.hitR.y = this.bounds.y + hitRectangle.y;
      return this.hitR;
   }

   public float getTimeOfCrash() {
      return 0.3F;
   }

   public void render(SpriteBatch var1) {
      super.render(var1);
      if(!this.isCrashed()) {
         this.laserGun.renderLaserAggregation(var1);
      }

   }

   public void reset(World var1, float var2, float var3) {
      super.reset(var1, 20, var2, var3);
      this.velocity.set(0.0F, 0.0F);
      this.enemyBroRegion = canonTextureRegion;
      this.enemyRegion = canonTextureRegion;
   }

   public void shooting() {
      if(this.isCrashed()) {
         ;
      }

   }

   public void update(float var1) {
      super.update(var1);
      if(this.isCrashed()) {
         this.laserGun.forceStop();
      } else {
         this.laserGun.updateShooting(var1);
         if(this.laserGun.getSleepClock().getCycleCount() != 0 && this.laserGun.getLaserGunState() == LaserGun.LaserGunState.Sleep) {
            this.laserGun.getSleepClock().resetClockFireOnce((float)(5 + this.world.rand.nextInt(2)));
            return;
         }
      }

   }

   public void updateShooting(float var1) {}

   protected boolean usePathAngle() {
      return false;
   }
}
