package com.doodlegames.air.force.enemy.level1;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector2Utils;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;

public class Ufo_a extends Enemy {

   private static float BULLETVEL = 200.0F;
  public static final int defaultHealthyDegree = 1;
   private static float timeOfPlayCrashedAnimation = 0.1F;
   private static TextureRegion ufo_aTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("nazi_ufo_a");
   private Clock clockShooting = new Clock(1.0F);
   private static int HEIGHT = RegionUtilFunctions.getRegionHeight(ufo_aTextureRegion);
   private static float VEL = 120.0F;
   private static final int WIDTH = RegionUtilFunctions.getRegionWidth(ufo_aTextureRegion);
  

   public Ufo_a(World var1, float var2, float var3) {
      super(var1, 1, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.enemyRegion = ufo_aTextureRegion;
      this.enemyBroRegion = ufo_aTextureRegion;
      this.velocity.set(0.0F, 0.0F);
      this.angle = 0.0F;
   }

   public Ufo_a(World var1, float var2, float var3, Vector2 var4) {
      super(var1, 1, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.velocity.x = var4.x;
      this.velocity.y = var4.y;
      this.angle = 90.0F + 57.295776F * MathUtils.atan2(this.velocity.y, this.velocity.x);
      this.enemyBroRegion = ufo_aTextureRegion;
      this.enemyRegion = ufo_aTextureRegion;
   }

   public static void loadResource() {
      ufo_aTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("nazi_ufo_a");
   }

   public void freeToPool() {
      if(this.world.level == 4) {
         this.world.Ufo_aPool.free((Ufo_a)this);
      }

   }

   public float getTimeOfCrash() {
      return timeOfPlayCrashedAnimation;
   }

   public void renderShadow(SpriteBatch var1) {
      Color var2 = var1.getColor();
      var1.setColor(0.0F, 0.0F, 0.0F, 0.5F);
      var1.draw(ufo_aTextureRegion, this.position.x - this.bounds.width / 2.0F + shadowOffset.x, this.position.y - this.bounds.height / 2.0F + shadowOffset.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, DynamicGameObject.shadowScale.x, DynamicGameObject.shadowScale.y, this.angle);
      var1.setColor(var2.r, var2.g, var2.b, 1.0F);
   }

   public void reset(World var1, float var2, float var3) {
      super.reset(var1, 1, var2, var3);
      this.enemyBroRegion = ufo_aTextureRegion;
      this.enemyRegion = ufo_aTextureRegion;
   }

   public void update(float var1) {
      super.update(var1);
   }

   public void updateShooting(float var1) {
      if(this.getBulletCount() < 1 && this.clockShooting.isFired()) {
         Vector2Utils.tmp.set(BULLETVEL * MathUtils.sinDeg(this.angle), -BULLETVEL * MathUtils.cosDeg(this.angle));
         Enemy.bullets.add(EnemyBullet1.naziBulletGen.getABullet(this, this.world, this.position.x, this.position.y, Vector2Utils.tmp));
         this.setBulletCount(1 + this.getBulletCount());
      }

   }

   protected boolean usePathAngle() {
      return false;
   }
}
