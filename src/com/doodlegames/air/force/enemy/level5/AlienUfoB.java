package com.doodlegames.air.force.enemy.level5;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.utils.Clock;

public class AlienUfoB extends Enemy {

   private static final int BULLETNUM = 1;
   private static final Vector2[] BULLETPOSITION;
   private static final float BULLETVEL = -320.0F;
   public static int HEIGHT = 0;
   private static final Vector2 UFOVEL = new Vector2(70.0F, -70.0F);
   public static int WIDTH = 0;
   public static final int defaultHealthyDegree = 4;
   private static float timeOfPlayCrashedAnimation = 0.1F;
   private static TextureAtlas.AtlasRegion ufo_bTextureRegion;
   private Vector2[] bulletPosition;
   private Clock clockShooting;


   static {
      Vector2[] var0 = new Vector2[]{new Vector2(29.0F, 18.0F)};
      BULLETPOSITION = var0;
      ufo_bTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_ufo_b");
      int var1;
      if(ufo_bTextureRegion.rotate) {
         var1 = ufo_bTextureRegion.getRegionHeight();
      } else {
         var1 = ufo_bTextureRegion.getRegionWidth();
      }

      WIDTH = var1;
      int var2;
      if(ufo_bTextureRegion.rotate) {
         var2 = ufo_bTextureRegion.getRegionWidth();
      } else {
         var2 = ufo_bTextureRegion.getRegionHeight();
      }

      HEIGHT = var2;
   }

   public AlienUfoB(World var1, float var2, float var3) {
      super(var1, 4, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.angle = 90.0F + 57.295776F * MathUtils.atan2(UFOVEL.y, UFOVEL.x);
      this.velocity.set(UFOVEL);
      this.clockShooting = new Clock(1.0F, 0.2F, (byte)2);
      this.bulletPosition = new Vector2[1];
      Vector2 var5 = new Vector2();

      for(int var6 = 0; var6 < 1; ++var6) {
         var5.set(BULLETPOSITION[var6]).sub(this.origin);
         Enemy.getRotateOffset(var5, this.angle).add(BULLETPOSITION[var6]);
         this.bulletPosition[var6] = new Vector2(var5);
      }

      this.enemyRegion = ufo_bTextureRegion;
   }

   public static void loadResource() {
      ufo_bTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_ufo_b");
   }

   public float getTimeOfCrash() {
      return timeOfPlayCrashedAnimation;
   }

   public void renderShadow(SpriteBatch var1) {
      Color var2 = var1.getColor();
      var1.setColor(0.0F, 0.0F, 0.0F, 0.5F);
      var1.draw(ufo_bTextureRegion, this.bounds.x + shadowOffset.x, this.bounds.y + shadowOffset.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, DynamicGameObject.shadowScale.x, DynamicGameObject.shadowScale.y, this.angle);
      var1.setColor(var2.r, var2.g, var2.b, 1.0F);
   }

   public void reset(World var1, float var2, float var3) {
      super.reset(var1, 4, var2, var3);
      this.enemyRegion = ufo_bTextureRegion;
   }

   public void update(float var1) {
      super.update(var1);
   }

   public void updateShooting(float var1) {
      int var2 = this.getBulletCount();
      if(this.clockShooting.isFired() && var2 < 1) {
         Vector2[] var3 = this.bulletPosition;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Vector2 var6 = var3[var5];
            Enemy.bullets.add(EnemyBullet1.alienBulletGen.getABullet(this, this.world, this.bounds.x + var6.x, this.bounds.y + var6.y, Vector2.tmp.set(0.0F, -320.0F).rotate(this.angle)));
         }

         this.setBulletCount(var2 + this.bulletPosition.length);
      }

   }
}
