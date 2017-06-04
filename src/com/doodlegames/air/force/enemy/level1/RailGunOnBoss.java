package com.doodlegames.air.force.enemy.level1;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.laser.CommonLaser;
import com.doodlegames.air.force.game.laser.LaserGun;
import com.doodlegames.air.force.resource.Assets_ShareInNazi;
import com.doodlegames.air.force.resource.Assets_level1;
import com.doodlegames.air.force.utils.RegionUtilFunctions;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class RailGunOnBoss extends Enemy {

   private static final int DEFAULTHEALTHYDEGREE = 40;
   private static TextureAtlas.AtlasRegion enemyBroTextureRegion = Assets_level1.findRegion("boss_railgun_broken");
   private static TextureAtlas.AtlasRegion enemyTextureRegion = Assets_level1.findRegion("boss_railgun");
   private Vector2 angleOffset = new Vector2();
   private ArrayList<Rectangle> hitRecs;
   private final float initAngle;
   private LaserGun laserGun;
   private Enemy parent;
   private final Vector2 relative;

   public static final int HEIGHT = RegionUtilFunctions.getRegionHeight((TextureRegion)enemyTextureRegion);
   private static final float TIMEOFPLAYCRASHANIMATION = 0.5F;
   public static final int WIDTH = RegionUtilFunctions.getRegionWidth((TextureRegion)enemyTextureRegion);
 
   public RailGunOnBoss(World var1, Enemy var2, float var3, float var4, float var5) {
      super(var1, Settings.lapFixBossDegree(40), var3 + var2.bounds.x + (float)WIDTH / 1.0F / 2.0F, var4 + var2.bounds.y + (float)HEIGHT / 1.0F / 2.0F, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.parent = var2;
      this.relative = new Vector2(var3, var4);
      this.initAngle = var5;
      this.origin.set(this.bounds.width / 2.0F, this.bounds.height / 2.0F);
      this.angularSpeed = 0.0F;
      this.velocity.x = 0.0F;
      this.velocity.y = 0.0F;
      this.hitRecs = new ArrayList();
      Rectangle var7 = new Rectangle();
      var7.x = this.bounds.x + this.bounds.width / 4.0F;
      var7.y = this.bounds.y + this.bounds.height / 4.0F;
      var7.width = this.bounds.width / 2.0F;
      var7.height = this.bounds.height / 2.0F;
      this.hitRecs.add(var7);
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyBroTextureRegion;
      this.laserGun = new LaserGun(this, Assets_ShareInNazi.gather, 0.2F, 3.0F, 3.0F, 1.0F, 35.0F, 5.0F, CommonLaser.getWidthOfNaziAll());
      this.laserGun.shooterPointIsAbsolute = false;
      this.laserGun.setLoopFire(false);
      this.laserGun.setOriginX(31.0F);
      this.laserGun.setOriginY(49.0F);

      for(int var10 = 0; var10 < 25; ++var10) {
         float var11 = CommonLaser.getDeltaYOfNaziAll(var10);
         if(var10 == 0) {
            Enemy.lasers.add(CommonLaser.naziLaserGenAllHead.getALaser(this, this.laserGun, var11));
         } else {
            Enemy.lasers.add(CommonLaser.naziLaserGenAll.getALaser(this, this.laserGun, var11));
         }
      }

      this.laserGun.startShoot((float)var1.rand.nextInt(3));
   }

   private Vector2 getAngleOffset() {
      this.angleOffset.x = this.origin.x + this.relative.x;
      this.angleOffset.y = this.origin.y + this.relative.y;
      Enemy.getRotateOffset(this.angleOffset.sub(this.parent.origin), this.parent.getAngle());
      return this.angleOffset;
   }

   public static void loadResource() {
      enemyTextureRegion = Assets_level1.findRegion("boss_railgun");
      enemyBroTextureRegion = Assets_level1.findRegion("boss_railgun_broken");
   }

   private void upateLaserGun(float var1) {
      if(this.isCrashed()) {
         this.laserGun.forceStop();
      }

      if(this.parent.angularSpeed >= 0.0F && (this.initAngle == 90.0F && Math.round(90.0F + this.parent.getAngle()) == 0 || this.initAngle == 180.0F && Math.round(180.0F + this.parent.getAngle()) == 0 || this.initAngle == 270.0F && Math.round(270.0F + this.parent.getAngle()) == 0)) {
         this.laserGun.updateShooting(var1);
         if(this.laserGun.getSleepClock().getCycleCount() != 0 && this.laserGun.getLaserGunState() == LaserGun.LaserGunState.Sleep) {
            this.laserGun.getSleepClock().resetClockFireOnce((float)(2 + this.world.rand.nextInt(7)));
            return;
         }
      }

   }

   public boolean canBeHit() {
      return this.getAngle() < 45.0F && this.parent.angularSpeed == 0.0F && super.canBeHit();
   }

   public List<Rectangle> getHitRectangle() {
      ArrayList var1 = this.hitRecs;
      Rectangle var2 = (Rectangle)var1.get(0);
      var2.x = this.bounds.x + this.bounds.width / 4.0F;
      var2.y = this.bounds.y + this.bounds.height / 4.0F;
      var2.width = this.bounds.width / 2.0F;
      var2.height = this.bounds.height / 2.0F;
      return var1;
   }

   public float getTimeOfCrash() {
      return 0.5F;
   }

   public void keepRelativePosition() {
      Vector2 var1 = this.getAngleOffset();
      float var2 = this.parent.bounds.x + this.relative.x + this.bounds.width / 2.0F;
      float var3 = this.parent.bounds.y + this.relative.y + this.bounds.height / 2.0F;
      this.setPosition(var2 + var1.x, var3 + var1.y);
   }

   public void renderEnemy(SpriteBatch var1) {
      super.renderEnemy(var1);
      this.laserGun.renderLaserAggregation(var1);
   }

   public void update(float var1) {
      super.update(var1);
      this.setAngle(this.parent.getAngle() + this.initAngle);
      this.upateLaserGun(var1);
   }

   protected void updateDynamicParameter(float var1) {
      super.updateDynamicParameter(var1);
      this.keepRelativePosition();
   }

   public void updateShooting(float var1) {}
}
