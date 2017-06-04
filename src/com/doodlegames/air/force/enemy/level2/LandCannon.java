package com.doodlegames.air.force.enemy.level2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.cannon.CommonCannon;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;
import com.doodlegames.air.force.utils.Settings;

import java.util.List;

public class LandCannon extends Enemy {

   private static final float BULLETSPEED = 10.0F;
   public static final int DEFAULTHEALTHYCANNON = 10;
   public static final int DEFAULTHEALTHYDEGREE = 10;
   private static final float TIMEOFPLAYCRASHANIMATION = 15.0F;
   private static TextureAtlas.AtlasRegion enemyTextureRegion = null;
   private CommonCannon cannon1;
   private Clock clockUpdateCanonAngle = new Clock(5.0F);


   public LandCannon(World var1, float var2, float var3) {
      super(var1, 10, var2, var3, (float)RegionUtilFunctions.getRegionWidth((TextureRegion)enemyTextureRegion) / 1.0F, (float)RegionUtilFunctions.getRegionHeight((TextureRegion)enemyTextureRegion) / 1.0F);
      this.cannon1 = CommonCannon.getCannon1(var1, new Clock(9.0F, 3.0F, (byte)2), 10, EnemyBullet1.naziBulletGen, this, 8.0F, 9.0F, 150.0F);
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyTextureRegion;
      this.velocity.set(Settings.backgroundVelocity);
   }

   public static void loadResource(TextureAtlas var0) {
      enemyTextureRegion = var0.findRegion("nazi_land_canon");
   }

   public void beHitByBullet(Bullet var1, int var2) {
      this.cannon1.beHitByBullet(var1, var2);
      if(this.cannon1.isCrashed() && !super.isCrashed()) {
         this.makeCrash();
      }

   }

   public List<Rectangle> getHitRectangle() {
      return this.cannon1.getHitRectangle();
   }

   public float getTimeOfCrash() {
      return 15.0F;
   }

   public void render(SpriteBatch var1) {
      super.render(var1);
      this.cannon1.render(var1);
   }

   public void setSleepPosition(float var1, float var2) {
      super.setSleepPosition(var1, var2);
      float var3 = 8.0F + this.bounds.x + CommonCannon.getWidthOfCannon1() / 2.0F;
      float var4 = 9.0F + this.bounds.y + CommonCannon.getHeightOfCannon1() / 2.0F;
      this.cannon1.setSleepPosition(var3, var4);
   }

   public void update(float var1) {
      super.update(var1);
      this.cannon1.update(var1);
   }

   public void updateShooting(float var1) {
      if(this.clockUpdateCanonAngle.isFired()) {
         float var2 = this.world.fighter.getPositionY() - this.cannon1.getPositionY();
         float var3 = this.world.fighter.getPositionX() - this.cannon1.getPositionX();
         if(var2 < 0.0F) {
            float var4 = 90.0F + (float)(57.2957763671875D * Math.atan2((double)var2, (double)var3));
            this.cannon1.rotateTo(var4);
         }
      }

   }
}
