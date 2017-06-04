package com.doodlegames.air.force.enemy.level1;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector2Utils;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;

public class Ufo_b extends Enemy {

   private static final int BULLETVEL = -200;
   public static final int defaultHealthyDegree = 1;
   private static final float timeOfPlayCrashedAnimation = 0.1F;
   protected static TextureAtlas.AtlasRegion ufo_bTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("nazi_ufo_b");
   private Clock clockShooting;
   private static int HEIGHT = RegionUtilFunctions.getRegionHeight((TextureRegion)ufo_bTextureRegion);
   private static final float VEL = 120.0F;
   private static final int WIDTH = RegionUtilFunctions.getRegionWidth((TextureRegion)ufo_bTextureRegion);
  

   public Ufo_b(World var1, float var2, float var3) {
      super(var1, 1, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.angle = Enemy.getPursueVel(this.position, var1.fighter.position, 120.0F, this.velocity);
      this.clockShooting = new Clock(1.0F);
      this.enemyRegion = ufo_bTextureRegion;
      this.enemyBroRegion = ufo_bTextureRegion;
   }

   public Ufo_b(World var1, float var2, float var3, Vector2 var4) {
      super(var1, 1, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.velocity.x = var4.x;
      this.velocity.y = var4.y;
      this.clockShooting = new Clock(1.0F);
      this.enemyRegion = ufo_bTextureRegion;
      this.enemyBroRegion = ufo_bTextureRegion;
   }

   public static void loadResource() {
      ufo_bTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("nazi_ufo_b");
   }

   public void freeToPool() {
      if(this.world.level == 4 || this.world.level == 1) {
         this.world.Ufo_bPool.free((Ufo_b)this);
      }

   }

   public float getTimeOfCrash() {
      return 0.1F;
   }

   public void renderShadow(SpriteBatch var1) {
      Color var2 = var1.getColor();
      var1.setColor(0.0F, 0.0F, 0.0F, 0.5F);
      RegionUtilFunctions.draw(var1, ufo_bTextureRegion, this.bounds.x + shadowOffset.x, this.bounds.y + shadowOffset.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, DynamicGameObject.shadowScale.x, DynamicGameObject.shadowScale.y, this.angle);
      var1.setColor(var2.r, var2.g, var2.b, 1.0F);
   }

   public void reset(World var1, float var2, float var3) {
      super.reset(var1, 1, var2, var3);
      this.angle = Enemy.getPursueVel(this.position, var1.fighter.position, 120.0F, this.velocity);
      this.enemyRegion = ufo_bTextureRegion;
      this.enemyBroRegion = ufo_bTextureRegion;
   }

   public void update(float var1) {
      super.update(var1);
      if(this.state == 1) {
         ;
      }

   }

   public void updateShooting(float var1) {
      if(this.getBulletCount() < 1 && this.clockShooting.isFired()) {
         Enemy.bullets.add(EnemyBullet1.naziBulletGen.getABullet(this, this.world, this.position.x, this.position.y, Vector2Utils.tmp.set(0.0F, -200.0F).rotate(this.angle)));
         this.setBulletCount(1 + this.getBulletCount());
      }

   }

   protected boolean usePathAngle() {
      return false;
   }
}
