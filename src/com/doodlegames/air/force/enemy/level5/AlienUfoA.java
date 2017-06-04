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

import java.util.LinkedList;
import java.util.Queue;

public class AlienUfoA extends Enemy {

   private static final int BULLETNUM = 1;
   private static final Vector2[] BULLETPOSITION;
   private static final Vector2 BULLETVEL = new Vector2(0.0F, -400.0F);
   public static int HEIGHT = 0;
   private static final int POOLSIZE = 20;
   private static final Vector2 UFOVEL = new Vector2(70.0F, -70.0F);
   public static int WIDTH = 0;
   private static Queue<AlienUfoA> alienUfoAPool;
   public static final int defaultHealthyDegree = 4;
   private static float timeOfPlayCrashedAnimation = 0.1F;
   private static TextureAtlas.AtlasRegion ufo_aTextureRegion;
   private Vector2 BulletVel;
   private Vector2[] bulletPosition;
   private Clock clockShooting;
   private Vector2 rotateV;


   static {
      Vector2[] var0 = new Vector2[]{new Vector2(24.0F, 5.0F)};
      BULLETPOSITION = var0;
      ufo_aTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_ufo_a");
      int var1;
      if(ufo_aTextureRegion.rotate) {
         var1 = ufo_aTextureRegion.getRegionHeight();
      } else {
         var1 = ufo_aTextureRegion.getRegionWidth();
      }

      WIDTH = var1;
      int var2;
      if(ufo_aTextureRegion.rotate) {
         var2 = ufo_aTextureRegion.getRegionWidth();
      } else {
         var2 = ufo_aTextureRegion.getRegionHeight();
      }

      HEIGHT = var2;
      alienUfoAPool = new LinkedList();
   }

   public AlienUfoA(World var1, float var2, float var3) {
      super(var1, 4, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.angle = 90.0F + 57.295776F * MathUtils.atan2(UFOVEL.y, UFOVEL.x);
      this.velocity.set(UFOVEL);
      this.clockShooting = new Clock(4.0F, 0.2F, (byte)2);
      this.bulletPosition = new Vector2[1];
      this.BulletVel = new Vector2();
      this.BulletVel.set(BULLETVEL).rotate(this.angle);
      this.rotateV = new Vector2();

      for(int var6 = 0; var6 < 1; ++var6) {
         this.rotateV.set(BULLETPOSITION[var6]).sub(this.origin);
         Enemy.getRotateOffset(this.rotateV, this.angle).add(BULLETPOSITION[var6]);
         this.bulletPosition[var6] = new Vector2(this.rotateV);
      }

      this.enemyRegion = ufo_aTextureRegion;
   }

   public static void cycleAEnemyBullet1(AlienUfoA var0) {
      if(var0 != null && alienUfoAPool.size() < 20) {
         var0.reset((World)null, 1.0F, 0.0F, 0.0F, 0.0F);
         alienUfoAPool.offer(var0);
      }
   }

   public static AlienUfoA getAlienUfoA(World var0, float var1, float var2) {
      return getAlienUfoA(var0, var1, var2, UFOVEL.x, UFOVEL.y);
   }

   public static AlienUfoA getAlienUfoA(World var0, float var1, float var2, float var3, float var4) {
      if(alienUfoAPool.peek() == null) {
         for(int var6 = 0; var6 < 10; ++var6) {
            alienUfoAPool.offer(new AlienUfoA((World)null, 0.0F, 0.0F));
         }
      }

      AlienUfoA var5 = (AlienUfoA)alienUfoAPool.remove();
      var5.reset(var0, var1, var2, var3, var4);
      return var5;
   }

   public static void loadResource() {
      ufo_aTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_ufo_a");
   }

   public static int poolSize() {
      return alienUfoAPool.size();
   }

   public void freeToPool() {
      cycleAEnemyBullet1(this);
   }

   public float getTimeOfCrash() {
      return timeOfPlayCrashedAnimation;
   }

   public void renderShadow(SpriteBatch var1) {
      Color var2 = var1.getColor();
      var1.setColor(0.0F, 0.0F, 0.0F, 0.5F);
      var1.draw(ufo_aTextureRegion, this.bounds.x + shadowOffset.x, this.bounds.y + shadowOffset.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, DynamicGameObject.shadowScale.x, DynamicGameObject.shadowScale.y, this.angle);
      var1.setColor(var2.r, var2.g, var2.b, 1.0F);
   }

   public void reset(World var1, float var2, float var3, float var4, float var5) {
      super.reset(var1, 4, var2, var3);
      this.angle = 90.0F + 57.295776F * MathUtils.atan2(var5, var4);
      this.angularSpeed = 0.0F;
      this.BulletVel.set(BULLETVEL).rotate(this.angle);
      this.velocity.set(var4, var5);

      for(int var8 = 0; var8 < 1; ++var8) {
         this.rotateV.set(BULLETPOSITION[var8]).sub(this.origin);
         Enemy.getRotateOffset(this.rotateV, this.angle).add(BULLETPOSITION[var8]);
         this.bulletPosition[var8].set(this.rotateV);
      }

      this.enemyRegion = ufo_aTextureRegion;
   }

   public void updateShooting(float var1) {
      int var2 = this.getBulletCount();
      if(this.clockShooting.isFired() && this.getBulletCount() < 0) {
         Vector2[] var3 = this.bulletPosition;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Vector2 var6 = var3[var5];
            Enemy.bullets.add(EnemyBullet1.alienBulletGen.getABullet(this, this.world, this.bounds.x + var6.x, this.bounds.y + var6.y, this.BulletVel));
            this.setBulletCount(1 + this.getBulletCount());
         }

         this.setBulletCount(var2 + this.bulletPosition.length);
      }

   }
}
