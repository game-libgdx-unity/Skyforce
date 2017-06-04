package com.doodlegames.air.force.game.Gun;

import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.Gun.BulletGun;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

public class BulletGunAlien extends BulletGun {

   public static Vector2 relXY = new Vector2();
   public boolean autoChangeOffset;
   public Clock clockPursue;
   public Vector2 pursueOffset;
   private Vector2 pursueVector2;
   private float velBullet;


   public BulletGunAlien(GameObject var1, World var2, BulletGenerator var3, Clock var4, Vector2 var5, float ... var6) {
      super(var1, var2, var3, var4, var5, var6);
      Clock var7;
      if(var4 == null) {
         var7 = new Clock(2.0F);
      } else {
         var7 = new Clock(var4.getSpan());
      }

      this.clockPursue = var7;
      if(this.directionsVel.length >= 1) {
         this.velBullet = this.directionsVel[0].len();
      } else {
         this.velBullet = 400.0F;
      }

      this.pursueVector2 = null;
      this.pursueOffset = new Vector2();
      this.autoChangeOffset = false;
   }

   public static BulletGunAlien getNaziOffsetGun(Enemy var0, Clock var1, float var2, float var3, float var4) {
      relXY.set(var2, var3);
      World var6 = var0.world;
      BulletGenerator var7 = EnemyBullet1.naziBulletGen;
      Vector2 var8 = relXY;
      float[] var9 = new float[]{0.0F, -Math.abs(var4)};
      BulletGunAlien var10 = new BulletGunAlien(var0, var6, var7, var1, var8, var9);
      var10.setPursueParameter((Clock)null, (Vector2)null, var0.world.fighter.bounds.width / 2.0F, 0.0F);
      var10.autoChangeOffset = true;
      var10.shootPointIsAbsolute = false;
      var10.rotateAccordEnemy = true;
      return var10;
   }

   public static BulletGunAlien getNaziPursueGun(Enemy var0, Clock var1, float var2, float var3, float var4) {
      relXY.set(var2, var3);
      World var6 = var0.world;
      BulletGenerator var7 = EnemyBullet1.naziBulletGen;
      Vector2 var8 = relXY;
      float[] var9 = new float[]{0.0F, -Math.abs(var4)};
      BulletGunAlien var10 = new BulletGunAlien(var0, var6, var7, var1, var8, var9);
      var10.setPursueParameter((Clock)null, (Vector2)null, 0.0F, 0.0F);
      var10.autoChangeOffset = false;
      var10.shootPointIsAbsolute = false;
      var10.rotateAccordEnemy = true;
      return var10;
   }

   public void pursueObject() {
      if(this.autoChangeOffset && this.world.rand.nextFloat() > 0.5F) {
         this.pursueOffset.x = -this.pursueOffset.x;
         this.pursueOffset.y = -this.pursueOffset.y;
      }

      if(this.pursueVector2 == null) {
         Settings.appLog("setPursueParameter must be invoked first!");
      } else {
         Vector2.tmp2.set(this.getWorldShootXY());
         Vector2.tmp.set(this.pursueVector2).add(this.pursueOffset);
         Vector2[] var3 = this.directionsVel;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Vector2 var6 = var3[var5];
            Enemy.getPursueVel(Vector2.tmp2, Vector2.tmp, this.velBullet, var6);
         }
      }

   }

   public void reset() {
      this.pursueObject();
      super.reset();
      this.clockPursue.restartClock();
   }

   public BulletGunAlien setPursueParameter(Clock var1, Vector2 var2, float var3, float var4) {
      if(var1 != null && var1.getClockType() == Clock.ClockType.LoopFire) {
         this.clockPursue.resetClock(this.world.rand.nextFloat() * var1.getSpan(), var1.getSpan(), var1.getDelay(), var1.getNum());
      }

      if(var1 != null && var1.getClockType() == Clock.ClockType.FireOnlyOnce) {
         this.clockPursue.resetClockFireOnce(var1.getSpan());
      }

      if(var2 == null) {
         this.pursueVector2 = this.world.fighter.position;
      } else {
         this.pursueVector2 = var2;
      }

      this.pursueOffset.x = var3;
      this.pursueOffset.y = var4;
      this.pursueObject();
      return this;
   }

   public void setShootPosition(float var1, float var2) {
      super.setShootPosition(var1, var2);
      this.pursueObject();
   }

   public void updateShooting(float var1) {
      if(this.clockShoot != null && this.clockPursue.isFired(0) && this.getShootBulletCount() / this.directionsVel.length % this.clockShoot.getNum() == 0) {
         this.clockPursue.isFired();
         this.pursueObject();
      }

      super.updateShooting(var1);
   }
}
