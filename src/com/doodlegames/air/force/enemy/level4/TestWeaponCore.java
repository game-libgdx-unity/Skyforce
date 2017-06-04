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

public class TestWeaponCore extends Enemy {

   public static final Vector2 BULLETPOSITION = new Vector2(59.0F, 37.0F);
   public static final int DEFAULTHEALTHYDEGREE = 100;
   public static  int HEIGHT = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 1.0F;
   public static final int WIDTH;
   public static TextureAtlas.AtlasRegion canonTextureRegion = Assets_level4.atlas_Enemy.findRegion("nazi_test_weapon_core");
   public static final Rectangle hitRectangle;
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
      hitRectangle = new Rectangle((float)WIDTH / 4.0F / 1.0F, (float)HEIGHT / 4.0F / 1.0F, (float)WIDTH / 2.0F / 1.0F, (float)HEIGHT / 2.0F / 1.0F);
   }

   public TestWeaponCore(World var1, float var2, float var3) {
      super(var1, Settings.lapFixBossDegree(100), var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.hitR = new Rectangle(hitRectangle);
      Rectangle var4 = this.hitR;
      var4.x += this.bounds.x;
      Rectangle var5 = this.hitR;
      var5.y += this.bounds.y;
      this.laserGun = new LaserGun(this, Assets_ShareInAlien.gather, 0.2F, 3.0F, 3.0F, 1.0F, 59.0F, 37.0F, CommonLaser.getWidthOfAlienAll());
      this.laserGun.shooterPointIsAbsolute = false;
      this.laserGun.setLoopFire(false);
      this.laserGun.setOriginX(31.0F);
      this.laserGun.setOriginY(49.0F);

      for(int var7 = 0; var7 < 25; ++var7) {
         float var8 = CommonLaser.getDeltaYOfAlienAll(var7);
         if(var7 == 0) {
            Enemy.lasers.add(CommonLaser.alienLaserGenAllHead.getALaser(this, this.laserGun, var8));
         } else {
            Enemy.lasers.add(CommonLaser.alienLaserGenAll.getALaser(this, this.laserGun, var8));
         }
      }

      this.laserGun.startShoot((float)var1.rand.nextInt(3));
      this.enemyRegion = canonTextureRegion;
      this.enemyBroRegion = this.enemyRegion;
   }

   public static void loadResource() {
      canonTextureRegion = Assets_level4.atlas_Enemy.findRegion("nazi_test_weapon_core");
   }

   private void updateLaserGun(float var1) {
      LaserGun.LaserGunState var2 = this.laserGun.getLaserGunState();
      if(this.isCrashed() && var2 != LaserGun.LaserGunState.Sleep) {
         this.laserGun.forceStop();
      } else {
         this.laserGun.updateShooting(var1);
         if(this.laserGun.getSleepClock().getCycleCount() != 0 && var2 == LaserGun.LaserGunState.Sleep) {
            this.laserGun.getSleepClock().resetClockFireOnce((float)(1 + this.world.rand.nextInt(2)));
            return;
         }
      }

   }

   public void enemyRemoved() {
      this.laserGun.setRemoveAllLasers();
   }

   public Rectangle getHitR() {
      this.hitR.x = this.bounds.x + hitRectangle.x;
      this.hitR.y = this.bounds.y + hitRectangle.y;
      return this.hitR;
   }

   public float getTimeOfCrash() {
      return 1.0F;
   }

   public void render(SpriteBatch var1) {
      super.render(var1);
      if(!this.isCrashed()) {
         this.laserGun.renderLaserAggregation(var1);
      }

   }

   public void update(float var1) {
      super.update(var1);
      this.updateLaserGun(var1);
   }

   public void updateShooting(float var1) {}
}
