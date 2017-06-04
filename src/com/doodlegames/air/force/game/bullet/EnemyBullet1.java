package com.doodlegames.air.force.game.bullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.resource.Assets_ShareInAlien;
import com.doodlegames.air.force.resource.Assets_ShareInNazi;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;
import com.doodlegames.air.force.utils.Settings;
import com.doodlegames.air.force.utils.XStack;

public class EnemyBullet1 extends Bullet {

   private static final int POOLINCREASE = 30;
   private static final int POOLLIMIT = 80;
   public static final BulletGenerator alienBulletGen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         return EnemyBullet1.getAAlienBullet1(var1, var3, var4, var5);
      }
   };
   private static XStack<EnemyBullet1> enemyBullet1Pool = new XStack(80);
   public static final BulletGenerator naziBulletGen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         return EnemyBullet1.getAEnemyBullet1(var1, var3, var4, var5, Assets_ShareInNazi.bullet1);
      }
   };
   private Animation bulletAnimation;


   private EnemyBullet1(GameObject var1, float var2, float var3, Vector2 var4, Animation var5) {
      super(var1, var2, var3, (float)RegionUtilFunctions.getRegionWidth(var5) / 1.0F, (float)RegionUtilFunctions.getRegionHeight(var5) / 1.0F, var4);
      this.initEnemyBullet1(var5);
   }

   public static void cycleAEnemyBullet1(EnemyBullet1 var0) {
      if(var0 != null && enemyBullet1Pool.size() < 80) {
         var0.set((GameObject)null, 1.0F, 0.0F, 0.0F, 10.0F, var0.bulletAnimation);
         enemyBullet1Pool.push(var0);
      }
   }

   private static EnemyBullet1 getAAlienBullet1(GameObject var0, float var1, float var2, Vector2 var3) {
      Animation var4 = Assets_ShareInAlien.bullet1;
      if(enemyBullet1Pool.size() == 0) {
         for(int var6 = 0; var6 < 30; ++var6) {
            enemyBullet1Pool.push(new EnemyBullet1((GameObject)null, 0.0F, 0.0F, var3, var4));
         }

         Settings.i("EnemyBullet1 get failed!");
      }

      EnemyBullet1 var5 = (EnemyBullet1)enemyBullet1Pool.pop();
      var5.set(var0, var1, var2, var3.x, var3.y, var4);
      return var5;
   }

   private static EnemyBullet1 getAEnemyBullet1(GameObject var0, float var1, float var2, Vector2 var3, Animation var4) {
      if(enemyBullet1Pool.size() == 0) {
         for(int var6 = 0; var6 < 30; ++var6) {
            enemyBullet1Pool.push(new EnemyBullet1((GameObject)null, 0.0F, 0.0F, var3, var4));
         }

         Settings.i("EnemyBullet1 get failed!");
      }

      EnemyBullet1 var5 = (EnemyBullet1)enemyBullet1Pool.pop();
      var5.set(var0, var1, var2, var3.x, var3.y, var4);
      return var5;
   }

   private void initEnemyBullet1(Animation var1) {
      this.damageValue = 1;
      this.bulletAnimation = var1;
   }

   public static int poolSize() {
      return enemyBullet1Pool.size();
   }

   private void set(GameObject var1, float var2, float var3, float var4, float var5, Animation var6) {
      super.reset(var1, var2, var3, (float)RegionUtilFunctions.getRegionWidth(var6) / 1.0F, (float)RegionUtilFunctions.getRegionHeight(var6) / 1.0F, Vector2.tmp.set(var4, var5));
      this.initEnemyBullet1(var6);
   }

   public void freeToPool() {
      cycleAEnemyBullet1(this);
   }

   public void render(SpriteBatch var1) {
      this.render(var1, this.bulletAnimation.getKeyFrame((float)Clock.getTimeCounter()));
   }

   public void update(float var1) {
      super.update(var1);
   }
}
