package com.doodlegames.air.force.game.bullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.resource.Assets_ShareInAlien;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.resource.Assets_ShareInNazi;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;

import java.util.LinkedList;
import java.util.Queue;

public class EnemyBullet2 extends Bullet {

   private static final int TURNCOUNTMAX = 2;
   public static final BulletGenerator alienBulletGen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         return EnemyBullet2.getAAlienBullet2(var1, var2, var3, var4, var5);
      }
   };
   private static TextureRegion bulletTextureRegion = Assets_ShareInAllLevel.test_Bullet_3;
   private static Queue<EnemyBullet2> enemyBullet2Pool = new LinkedList();
   public static final BulletGenerator naziBulletGen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         return EnemyBullet2.getAEnemyBullet2(var1, var2, var3, var4, var5);
      }
   };
   private Animation bulletAnimation;
   private EnemyBullet2.FlyingState flyingState;
   private Clock turnClock = new Clock(0.9F, 1.0F, 0.6F, (byte)2);
   private int turnCount;
   private World world;


   private EnemyBullet2(GameObject var1, World var2, float var3, float var4, Vector2 var5, Animation var6) {
      super(var1, var3, var4, (float)RegionUtilFunctions.getRegionWidth(Assets_ShareInNazi.bullet2) / 1.0F, (float)RegionUtilFunctions.getRegionHeight(Assets_ShareInNazi.bullet2) / 1.0F, var5);
      this.initEnemyBullet2(var2, var5, var6);
   }

   public static void cycleAEnemyBullet2(EnemyBullet2 var0) {
      if(var0 != null && enemyBullet2Pool.size() < 30) {
         var0.set((GameObject)null, (World)null, 1.0F, 0.0F, new Vector2(0.0F, 10.0F), var0.bulletAnimation);
         enemyBullet2Pool.offer(var0);
      }
   }

   private static EnemyBullet2 getAAlienBullet2(GameObject var0, World var1, float var2, float var3, Vector2 var4) {
      Animation var5 = Assets_ShareInAlien.bullet2;
      if(enemyBullet2Pool.peek() == null) {
         for(int var7 = 0; var7 < 10; ++var7) {
            enemyBullet2Pool.offer(new EnemyBullet2((GameObject)null, (World)null, 0.0F, 0.0F, var4, var5));
         }
      }

      EnemyBullet2 var6 = (EnemyBullet2)enemyBullet2Pool.remove();
      var6.set(var0, var1, var2, var3, var4, var5);
      return var6;
   }

   public static EnemyBullet2 getAEnemyBullet2(GameObject var0, World var1, float var2, float var3, Vector2 var4) {
      Animation var5 = Assets_ShareInNazi.bullet2;
      if(enemyBullet2Pool.peek() == null) {
         for(int var7 = 0; var7 < 10; ++var7) {
            enemyBullet2Pool.offer(new EnemyBullet2((GameObject)null, (World)null, 0.0F, 0.0F, var4, var5));
         }
      }

      EnemyBullet2 var6 = (EnemyBullet2)enemyBullet2Pool.remove();
      var6.set(var0, var1, var2, var3, var4, var5);
      return var6;
   }

   public static int getBulletHeight() {
      return RegionUtilFunctions.getRegionHeight(bulletTextureRegion);
   }

   public static EnemyBullet2.BulletSpeedUp getSpeedUp(EnemyBullet2 var0, float var1, float var2, float var3) {
      EnemyBullet2.BulletSpeedUp var4 = (EnemyBullet2.BulletSpeedUp)Pools.obtain(EnemyBullet2.BulletSpeedUp.class);
      var4.reset(var0, var1, var2, var3);
      return var4;
   }

   private void initEnemyBullet2(World var1, Vector2 var2, Animation var3) {
      this.world = var1;
      this.damageValue = 1;
      this.angle = 90.0F + 57.295776F * MathUtils.atan2(var2.y, var2.x);
      this.turnCount = 2;
      if(var1 != null) {
         this.turnClock.resetClock(0.8F + 0.2F * var1.rand.nextFloat(), 1.0F, 0.6F, (byte)2);
      }

      this.flyingState = EnemyBullet2.FlyingState.StraightFoward;
      this.bulletAnimation = var3;
   }

   public static void loadResource() {
      bulletTextureRegion = Assets_ShareInAllLevel.test_Bullet_3;
   }

   public static int poolSize() {
      return enemyBullet2Pool.size();
   }

   private void set(GameObject var1, World var2, float var3, float var4, Vector2 var5, Animation var6) {
      super.reset(var1, var3, var4, (float)RegionUtilFunctions.getRegionWidth(var6) / 1.0F, (float)RegionUtilFunctions.getRegionHeight(var6) / 1.0F, var5);
      this.initEnemyBullet2(var2, var5, var6);
      this.clearGameObjectActions();
      if(var1 instanceof EnemyBullet2.MissleFixer) {
         ((EnemyBullet2.MissleFixer)var1).fixMissle(this);
      }

   }

   public boolean TurningChoose() {
      float var1 = this.velocity.x;
      float var2 = this.velocity.y;
      float var3 = this.world.fighter.position.x - this.position.x;
      return var1 * (this.world.fighter.position.y - this.position.y) - var2 * var3 > 0.0F;
   }

   public void freeToPool() {
      cycleAEnemyBullet2(this);
   }

   public void render(SpriteBatch var1) {
      this.render(var1, this.bulletAnimation.getKeyFrame((float)Clock.getTimeCounter()));
   }

   public void setTurnCountMax(int var1) {
      this.turnCount = var1;
   }

   public void update(float var1) {
      super.update(var1);
      if(this.turnClock.isFired()) {
         if(this.flyingState == EnemyBullet2.FlyingState.Turning) {
            this.flyingState = EnemyBullet2.FlyingState.StraightFoward;
         } else if(this.flyingState == EnemyBullet2.FlyingState.StraightFoward && this.turnCount > 0) {
            this.turnCount += -1;
            this.flyingState = EnemyBullet2.FlyingState.Turning;
            return;
         }
      }

   }

   public void updateDynamicParameter(final float n) {

       switch (this.flyingState) {

           case Turning: {

               final float n2 = n * 90.0f;

               if (this.TurningChoose()) {

                   float n3 = n * (45.0f + 100.0f * this.world.rand.nextFloat());

                   if (n3 > n2) {

                       n3 = n2;

                   }

                   this.angle = n3 + this.angle;

                   this.velocity.rotate(n3);

                   break;

               }

               float n4 = n * -(45.0f + 100.0f * this.world.rand.nextFloat());

               if (n4 < -n2) {

                   n4 = -n2;

               }

               this.angle = n4 + this.angle;

               this.velocity.rotate(n4);

               break;

           }

       }

       super.updateDynamicParameter(n);

   }

   public interface MissleFixer {

      void fixMissle(EnemyBullet2 var1);
   }

   public static class BulletSpeedUp extends DynamicGameObject.GameAction {

      float accelV = 100.0F;
      EnemyBullet2 bullet;
      float currentTime;
      float delayTime;
      float speedUpTime;


      public boolean act(float var1) {
         if(this.currentTime > this.delayTime + this.speedUpTime) {
            return true;
         } else {
            if(this.currentTime > this.delayTime) {
               Vector2 var2 = Vector2.tmp.set(0.0F, -Math.abs(this.accelV));
               var2.rotate(this.bullet.getAngle());
               this.bullet.velocity.add(var1 * var2.x, var1 * var2.y);
            }

            this.currentTime += var1;
            return false;
         }
      }

      public void reset(EnemyBullet2 var1, float var2, float var3, float var4) {
         this.currentTime = 0.0F;
         this.delayTime = var2;
         this.speedUpTime = var3;
         this.accelV = var4;
         this.bullet = var1;
      }
   }

   private static enum FlyingState {

      StraightFoward,
      Turning
   }
}
