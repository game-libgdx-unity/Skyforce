package com.doodlegames.air.force.enemy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.level6.BossLv6;
import com.doodlegames.air.force.enemy.level6.BossLv6Eye;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.game.bullet.EnemyBullet2;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.Iterator;

public class EnemyTools {

   public static EnemyTools.GunInfo defaultGunInfo = new EnemyTools.GunInfo() {
      public boolean gunCancelShoot() {
         return false;
      }
      public void shootComplete(EnemyTools.DelayShootAction var1, Bullet var2) {}
      public void shootFailed(EnemyTools.DelayShootAction var1) {}
   };
   private static Vector2 velTemp1 = new Vector2();
   private static Vector2 velTemp2 = new Vector2();
   private static Vector2 velTemp3 = new Vector2();


   public static void addAsyBeHitAction(BossLv6 var0, BossLv6Eye var1) {
      EnemyTools.AsyBeHit var2 = (EnemyTools.AsyBeHit)Pools.obtain(EnemyTools.AsyBeHit.class);
      var2.clearEnemyAction();
      var2.bossLv6 = var0;
      var2.bossLv6Eye = var1;
      var1.addEnemyAction(var2);
   }

   public static EnemyTools.DelayShootAction alienShootBullet(Enemy var0, float var1, float var2, float var3) {
      return enemyShoot(var0, 0.0F, EnemyBullet1.alienBulletGen, var0, 0.0F, -Math.abs(var1), var2, var3, 0.0F, defaultGunInfo);
   }

   public static EnemyTools.DelayShootAction alienShootBullet(Enemy var0, float var1, float var2, float var3, float var4) {
      return enemyShoot(var0, var1, EnemyBullet1.alienBulletGen, var0, 0.0F, -Math.abs(var2), var3, var4, 0.0F, defaultGunInfo);
   }

   public static EnemyTools.DelayShootAction alienShootBullet(Enemy var0, float var1, float var2, float var3, float var4, float var5) {
      return enemyShoot(var0, var1, EnemyBullet1.alienBulletGen, var0, 0.0F, -Math.abs(var2), var3, var4, -var5, defaultGunInfo);
   }

   public static EnemyTools.DelayShootAction alienShootMissle(Enemy var0, float var1, float var2, float var3) {
      return enemyShoot(var0, 0.0F, EnemyBullet2.alienBulletGen, var0, 0.0F, -Math.abs(var1), var2, var3, 0.0F, defaultGunInfo);
   }

   public static EnemyTools.DelayShootAction alienShootMissle(Enemy var0, float var1, float var2, float var3, float var4) {
      return enemyShoot(var0, var1, EnemyBullet2.alienBulletGen, var0, 0.0F, -Math.abs(var2), var3, var4, 0.0F, defaultGunInfo);
   }

   public static void alienShootScreen(Enemy var0, float var1, float var2, float var3, float[][] var4) {
      enemyShootScreen(var0, var1, EnemyBullet1.alienBulletGen, var2, var3, var4);
   }

   public static void alienShootScreen(Enemy var0, float var1, float var2, float[][] var3) {
      alienShootScreen(var0, 0.0F, var1, var2, var3);
   }

   public static EnemyTools.DelayShootAction cannonShootBullet(Enemy var0, float var1, float var2, float var3, float var4, float var5) {
      return enemyShoot(var0, var1, EnemyBullet1.naziBulletGen, var0, var2, var3, var4, var5, 0.0F, defaultGunInfo);
   }

   public static void checkAction(EnemyTools.EnemyAction var0, Enemy var1) {
      if(var0 == null) {
         Settings.i("checkOwner function: action is null");
      }

      if(var1 == null) {
         Settings.i("checkOwner function: enemy is null");
      }

      if(var0 != null && var1 != null && var0.getEnemy() != var1) {
         String var2;
         if(var0.getEnemy() == null) {
            var2 = "null";
         } else {
            var2 = var0.getEnemy().toString();
         }

         Settings.i("action\'s owner is " + var2 + ".\n" + " but the enemy is " + var1.toString() + ".\n");
      }
   }

   public static EnemyTools.EnemyAction delay(float var0) {
      return delay(var0, (EnemyTools.EnemyAction)null);
   }

   public static EnemyTools.EnemyAction delay(float var0, EnemyTools.EnemyAction var1) {
      EnemyTools.DelayAction var2 = (EnemyTools.DelayAction)Pools.obtain(EnemyTools.DelayAction.class);
      var2.clearEnemyAction();
      var2.currentTime = 0.0F;
      var2.delayTime = var0;
      var2.deleAction = var1;
      return var2;
   }

   public static EnemyTools.EnemyAction enemyRotateBy(Enemy var0, float var1, float var2) {
      return enemyRotateTo(var0, var1 + var0.getAngle(), var2, false);
   }

   public static EnemyTools.EnemyAction enemyRotateTo(Enemy var0, float var1) {
      EnemyTools.TurnningAction var2 = (EnemyTools.TurnningAction)enemyRotateTo(var0, var1, 0.0F, true);
      var2.time = Math.abs(var2.endAngle - var2.startAngle) / 360.0F;
      return var2;
   }

   public static EnemyTools.EnemyAction enemyRotateTo(Enemy var0, float var1, float var2, boolean var3) {
      if(var3) {
         var1 = normalGoalAngle(var0.getAngle(), var1);
      }

      EnemyTools.TurnningAction var4 = (EnemyTools.TurnningAction)Pools.obtain(EnemyTools.TurnningAction.class);
      var4.clearEnemyAction();
      var4.setEnemy(var0);
      var4.startAngle = var0.getAngle();
      var4.endAngle = var1;
      var4.time = var2;
      var4.currentTime = 0.0F;
      return var4;
   }

   public static EnemyTools.DelayShootAction enemyShoot(Enemy var0, float var1, BulletGenerator var2, float var3, float var4, float var5) {
      return enemyShoot(var0, var1, var2, var0, 0.0F, -Math.abs(var3), var4, var5, 0.0F, defaultGunInfo);
   }

   public static EnemyTools.DelayShootAction enemyShoot(Enemy var0, float var1, BulletGenerator var2, float var3, Vector2 var4) {
      return enemyShoot(var0, var1, var2, var0, 0.0F, -Math.abs(var3), var4.x, var4.y, 0.0F, defaultGunInfo);
   }

   public static EnemyTools.DelayShootAction enemyShoot(Enemy var0, float var1, BulletGenerator var2, EnemyTools.TurnDataProvider var3, float var4, float var5, float var6, float var7, float var8, EnemyTools.GunInfo var9) {
      EnemyTools.DelayShootAction var10 = (EnemyTools.DelayShootAction)Pools.obtain(EnemyTools.DelayShootAction.class);
      var10.clearEnemyAction();
      var10.setEnemy(var0);
      var10.shootTime = var1 + (float)Clock.getTimeCounter();
      var10.gen = var2;
      var10.turnData = var3;
      var10.velx = var4;
      var10.vely = var5;
      var10.relShootX = var6;
      var10.relShootY = var7;
      var10.iniAngle = var8;
      var10.gunInfo = var9;
      var10.crashShoot = false;
      var10.velRotateWithEnemy = true;
      if(var0 instanceof EnemyTools.EnemyActionCallBack) {
         var10.callBack = (EnemyTools.EnemyActionCallBack)var0;
         return var10;
      } else {
         var10.callBack = null;
         return var10;
      }
   }

   public static EnemyTools.DelayShootAction enemyShoot(Enemy var0, BulletGenerator var1, float var2, float var3, float var4) {
      return enemyShoot(var0, 0.0F, var1, var0, 0.0F, -Math.abs(var2), var3, var4, 0.0F, defaultGunInfo);
   }

   public static EnemyTools.DelayShootAction enemyShoot(Enemy var0, BulletGenerator var1, float var2, Vector2 var3) {
      return enemyShoot(var0, 0.0F, var1, var0, 0.0F, -Math.abs(var2), var3.x, var3.y, 0.0F, defaultGunInfo);
   }

   public static void enemyShootScreen(Enemy var0, float var1, BulletGenerator var2, float var3, float var4, float[][] var5) {
      for(int var6 = 0; var6 < var5.length; ++var6) {
         float var7 = var5[var6][0];
         float var8 = var5[var6][1];
         EnemyTools.DelayShootAction var9 = enemyShoot(var0, var1, var2, var0, 0.0F, -Math.abs(var7), var3, var4, -var8, defaultGunInfo);
         var9.velRotateWithEnemy = false;
         var0.addEnemyAction(var9);
      }

   }

   public static EnemyTools.EnemyAction funAction(EnemyTools.DeleFunction var0) {
      return funAction(var0, (Object)null);
   }

   public static EnemyTools.EnemyAction funAction(EnemyTools.DeleFunction var0, Object var1) {
      EnemyTools.FunAction var2 = (EnemyTools.FunAction)Pools.obtain(EnemyTools.FunAction.class);
      var2.clearEnemyAction();
      var2.deleFunction = var0;
      var2.parameter = var1;
      return var2;
   }

   public static float getDeltaAngle(float var0, Vector2 var1, Vector2 var2) {
      float var3;
      for(var3 = 90.0F + (float)(57.2957763671875D * Math.atan2((double)(var2.y - var1.y), (double)(var2.x - var1.x))) - var0; var3 > 180.0F; var3 -= 360.0F) {
         ;
      }

      while(var3 < -180.0F) {
         var3 += 360.0F;
      }

      return var3;
   }

   public static EnemyTools.EnemyAction getWarningOverFighter(Enemy var0) {
      EnemyTools.WarningOverFighter var1 = (EnemyTools.WarningOverFighter)Pools.obtain(EnemyTools.WarningOverFighter.class);
      var1.clearEnemyAction();
      var1.setEnemy(var0);
      return var1;
   }

   public static EnemyTools.EnemyAction moveBy(Enemy var0, float var1, float var2, float var3) {
      EnemyTools.MoveByAction var4 = (EnemyTools.MoveByAction)Pools.obtain(EnemyTools.MoveByAction.class);
      var4.clearEnemyAction();
      var4.setEnemy(var0);
      var4.currentTime = 0.0F;
      var4.time = var3;
      var4.deltaX = var1;
      var4.deltaY = var2;
      return var4;
   }

   public static EnemyTools.EnemyAction moveTo(Enemy var0, float var1, float var2) {
      return moveTo(var0, var1, var0.getPositionY(), var2);
   }

   public static EnemyTools.EnemyAction moveTo(Enemy var0, float var1, float var2, float var3) {
      EnemyTools.MoveToAction var4 = (EnemyTools.MoveToAction)Pools.obtain(EnemyTools.MoveToAction.class);
      var4.clearEnemyAction();
      var4.setEnemy(var0);
      var4.currentTime = 0.0F;
      var4.time = var3;
      var4.desX = var1;
      var4.desY = var2;
      if(var0 instanceof EnemyTools.EnemyActionCallBack) {
         var4.callBack = (EnemyTools.EnemyActionCallBack)var0;
      }

      return var4;
   }

   public static EnemyTools.DelayShootAction naziShootBullet(Enemy var0, float var1, float var2, float var3) {
      return enemyShoot(var0, 0.0F, EnemyBullet1.naziBulletGen, var0, 0.0F, -Math.abs(var1), var2, var3, 0.0F, defaultGunInfo);
   }

   public static EnemyTools.DelayShootAction naziShootBullet(Enemy var0, float var1, float var2, float var3, float var4) {
      return enemyShoot(var0, var1, EnemyBullet1.naziBulletGen, var0, 0.0F, -Math.abs(var2), var3, var4, 0.0F, defaultGunInfo);
   }

   public static EnemyTools.DelayShootAction naziShootBullet(Enemy var0, float var1, float var2, float var3, float var4, float var5) {
      return enemyShoot(var0, var1, EnemyBullet1.naziBulletGen, var0, 0.0F, -Math.abs(var2), var3, var4, -var5, defaultGunInfo);
   }

   public static EnemyTools.DelayShootAction naziShootMissle(Enemy var0, float var1, float var2, float var3) {
      return enemyShoot(var0, 0.0F, EnemyBullet2.naziBulletGen, var0, 0.0F, -Math.abs(var1), var2, var3, 0.0F, defaultGunInfo);
   }

   public static EnemyTools.DelayShootAction naziShootMissle(Enemy var0, float var1, float var2, float var3, float var4) {
      return enemyShoot(var0, var1, EnemyBullet2.naziBulletGen, var0, 0.0F, -Math.abs(var2), var3, var4, 0.0F, defaultGunInfo);
   }

   public static EnemyTools.DelayShootAction naziShootMissle(Enemy var0, float var1, float var2, float var3, float var4, float var5) {
      return enemyShoot(var0, var1, EnemyBullet2.naziBulletGen, var0, 0.0F, -Math.abs(var2), var3, var4, -var5, defaultGunInfo);
   }

   public static void naziShootScreen(Enemy var0, float var1, float var2, float var3, float[][] var4) {
      enemyShootScreen(var0, var1, EnemyBullet1.naziBulletGen, var2, var3, var4);
   }

   public static void naziShootScreen(Enemy var0, float var1, float var2, float[][] var3) {
      naziShootScreen(var0, 0.0F, var1, var2, var3);
   }

   public static float normalGoalAngle(float var0, float var1) {
      float var2;
      for(var2 = var1 - var0; var2 >= 360.0F; var2 -= 360.0F) {
         ;
      }

      while(var2 < 0.0F) {
         var2 += 360.0F;
      }

      if(Math.abs(var2) > 180.0F) {
         if(var2 > 0.0F) {
            var2 -= 360.0F;
         } else {
            var2 += 360.0F;
         }
      }

      return var0 + var2;
   }

   public static EnemyTools.EnemyAction sequence(EnemyTools.EnemyAction ... var0) {
      EnemyTools.SeqAction var1 = (EnemyTools.SeqAction)Pools.obtain(EnemyTools.SeqAction.class);
      var1.clearEnemyAction();
      var1.actions.clear();
      var1.currentIndex = 0;
      int var2 = var0.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EnemyTools.EnemyAction var4 = var0[var3];
         var1.actions.add(var4);
      }

      return var1;
   }

   public static class WarningOverFighter extends EnemyTools.EnemyAction {

      public boolean act(float var1) {
         if(this.getEnemy().world.warningIsOver()) {
            this.getEnemy().world.askBeginFighting();
            return true;
         } else {
            if(!this.getEnemy().world.getStopFighting()) {
               this.getEnemy().world.askStopFighting();
            }

            return false;
         }
      }
   }

   public static class TurnningAction extends EnemyTools.EnemyAction {

      private float currentTime;
      private float endAngle;
      private float startAngle;
      private float time;


      public boolean act(float var1) {
         if(super.act(var1)) {
            return true;
         } else {
            this.currentTime += var1;
            if(this.currentTime < this.time) {
               float var2 = (this.endAngle - this.startAngle) * this.currentTime / this.time + this.startAngle;
               this.getEnemy().setAngle(var2);
               return false;
            } else {
               this.getEnemy().setAngle(this.endAngle);
               return true;
            }
         }
      }
   }

   public static class AsyBeHit extends EnemyTools.EnemyAction {

      BossLv6 bossLv6;
      BossLv6Eye bossLv6Eye;


      public boolean act(float var1) {
         float var2 = this.bossLv6.lastHitTime;
         float var3 = this.bossLv6Eye.lastHitTime;
         if(var3 > var2) {
            this.bossLv6.lastHitTime = this.bossLv6Eye.lastHitTime;
            this.bossLv6.isBeHiting = this.bossLv6Eye.isBeHiting;
         }

         if(var3 < var2) {
            this.bossLv6Eye.lastHitTime = this.bossLv6.lastHitTime;
            this.bossLv6Eye.isBeHiting = this.bossLv6.isBeHiting;
         }

         return false;
      }
   }

   public static class DelayAction extends EnemyTools.EnemyAction {

      public float currentTime = 0.0F;
      public float delayTime = 0.0F;
      public EnemyTools.EnemyAction deleAction;


      public boolean act(float var1) {
         if(this.currentTime > this.delayTime) {
            if(this.deleAction == null) {
               return true;
            } else if(this.deleAction.act(var1)) {
               this.getEnemy().freeEnemyAction(this.deleAction);
               return true;
            } else {
               return false;
            }
         } else {
            this.currentTime += var1;
            return false;
         }
      }

      public void setEnemy(Enemy var1) {
         super.setEnemy(var1);
         if(this.deleAction != null) {
            this.deleAction.setEnemy(var1);
         }

      }
   }

   public static class MoveToAction extends EnemyTools.EnemyAction {

      float currentTime = 0.0F;
      float desX;
      float desY;
      float time = 1.0F;
      float velX;
      float velY;


      private void init() {
         this.velX = (this.desX - this.getEnemy().getPositionX()) / this.time;
         this.velY = (this.desY - this.getEnemy().getPositionY()) / this.time;
      }

      public boolean act(float var1) {
         if(super.act(var1)) {
            return true;
         } else if((double)this.time <= 1.0E-5D) {
            this.getEnemy().setPosition(this.desX, this.desY);
            return true;
         } else {
            if(this.currentTime == 0.0F) {
               this.init();
            }

            float var4;
            float var5;
            boolean var7;
            label54: {
               this.currentTime += var1;
               float var2 = this.getEnemy().getPositionX();
               float var3 = this.getEnemy().getPositionY();
               var4 = var2 + var1 * this.velX;
               var5 = var3 + var1 * this.velY;
               if(this.velX <= 0.0F || var4 <= this.desX) {
                  float var9;
                  int var6 = (var9 = this.velX - 0.0F) == 0.0F?0:(var9 < 0.0F?-1:1);
                  var7 = false;
                  if(var6 >= 0) {
                     break label54;
                  }

                  float var10;
                  int var8 = (var10 = var4 - this.desX) == 0.0F?0:(var10 < 0.0F?-1:1);
                  var7 = false;
                  if(var8 >= 0) {
                     break label54;
                  }
               }

               var4 = this.desX;
               var7 = true;
            }

            if(this.velY > 0.0F && var5 > this.desY || this.velY < 0.0F && var5 < this.desY) {
               var5 = this.desY;
               var7 = true;
            }

            this.getEnemy().setPosition(var4, var5);
            return var7;
         }
      }
   }

   public static class DelayShootAction extends EnemyTools.EnemyAction {

      public boolean crashShoot;
      BulletGenerator gen;
      EnemyTools.GunInfo gunInfo;
      float iniAngle;
      float relShootX;
      float relShootY;
      float shootTime = 0.0F;
      EnemyTools.TurnDataProvider turnData;
      public boolean velRotateWithEnemy;
      float velx;
      float vely;


      public DelayShootAction() {
         this.gunInfo = EnemyTools.defaultGunInfo;
         this.iniAngle = 0.0F;
         this.crashShoot = false;
         this.velRotateWithEnemy = true;
      }

      private boolean checkShootCanceled() {
         return !this.crashShoot && this.getEnemy().isCrashed() || this.gunInfo.gunCancelShoot();
      }

      public boolean act(float var1) {
         if(this.checkShootCanceled()) {
            this.gunInfo.shootFailed(this);
            return true;
         } else if(super.act(var1)) {
            return true;
         } else if(Clock.getTimeCounter() > (double)this.shootTime) {
            Vector2 var2 = EnemyTools.velTemp1.set(this.relShootX, this.relShootY);
            Vector2 var3 = EnemyTools.velTemp2.set(this.turnData.getOriginX(), this.turnData.getOriginY());
            float var4 = this.turnData.getAngle();
            DynamicGameObject.RotatePoint(var2, var3, var4);
            float var6 = var4 - this.iniAngle;
            if(!this.velRotateWithEnemy) {
               var6 = -this.iniAngle;
            }

            Vector2 var7 = EnemyTools.velTemp3.set(this.velx, this.vely).rotate(var6);
            float var8 = var2.x + this.getEnemy().bounds.x;
            float var9 = var2.y + this.getEnemy().bounds.y;
            Bullet var10 = this.gen.getABullet(this.getEnemy(), this.getEnemy().world, var8, var9, var7);
            Enemy.bullets.add(var10);
            this.gunInfo.shootComplete(this, var10);
            return true;
         } else {
            return false;
         }
      }
   }

   public static class SeqAction extends EnemyTools.EnemyAction {

      public Array<EnemyTools.EnemyAction> actions = new Array(2);
      public int currentIndex = 0;


      public boolean act(float var1) {
         if(super.act(var1)) {
            return true;
         } else if(this.currentIndex >= 0 && this.currentIndex < this.actions.size) {
            if(((EnemyTools.EnemyAction)this.actions.get(this.currentIndex)).act(var1)) {
               ++this.currentIndex;
            }

            return false;
         } else {
            for(int var2 = 0; var2 < this.actions.size; ++var2) {
               this.getEnemy().freeEnemyAction((EnemyTools.EnemyAction)this.actions.get(var2));
            }

            this.actions.clear();
            return true;
         }
      }

      public void setEnemy(Enemy var1) {
         super.setEnemy(var1);
         Iterator var2 = this.actions.iterator();

         while(var2.hasNext()) {
            ((EnemyTools.EnemyAction)var2.next()).setEnemy(var1);
         }

      }
   }

   public interface TurnDataProvider {

      float getAngle();

      float getOriginX();

      float getOriginY();
   }

   public interface GunInfo {

      boolean gunCancelShoot();

      void shootComplete(EnemyTools.DelayShootAction var1, Bullet var2);

      void shootFailed(EnemyTools.DelayShootAction var1);
   }

   public static class MoveByAction extends EnemyTools.EnemyAction {

      float currentTime = 0.0F;
      float deltaX;
      float deltaY;
      float desX;
      float desY;
      float time = 1.0F;
      float velX;
      float velY;


      private void init() {
         this.desX = this.getEnemy().getPositionX() + this.deltaX;
         this.desY = this.getEnemy().getPositionY() + this.deltaY;
         this.velX = (this.desX - this.getEnemy().getPositionX()) / this.time;
         this.velY = (this.desY - this.getEnemy().getPositionY()) / this.time;
      }

      public boolean act(float var1) {
         if(super.act(var1)) {
            return true;
         } else if((double)this.time <= 1.0E-5D) {
            this.getEnemy().setPosition(this.desX, this.desY);
            return true;
         } else {
            if(this.currentTime == 0.0F) {
               this.init();
            }

            float var4;
            float var5;
            boolean var7;
            label54: {
               this.currentTime += var1;
               float var2 = this.getEnemy().getPositionX();
               float var3 = this.getEnemy().getPositionY();
               var4 = var2 + var1 * this.velX;
               var5 = var3 + var1 * this.velY;
               if(this.velX <= 0.0F || var4 <= this.desX) {
                  float var9;
                  int var6 = (var9 = this.velX - 0.0F) == 0.0F?0:(var9 < 0.0F?-1:1);
                  var7 = false;
                  if(var6 >= 0) {
                     break label54;
                  }

                  float var10;
                  int var8 = (var10 = var4 - this.desX) == 0.0F?0:(var10 < 0.0F?-1:1);
                  var7 = false;
                  if(var8 >= 0) {
                     break label54;
                  }
               }

               var4 = this.desX;
               var7 = true;
            }

            if(this.velY > 0.0F && var5 > this.desY || this.velY < 0.0F && var5 < this.desY) {
               var5 = this.desY;
               var7 = true;
            }

            this.getEnemy().setPosition(var4, var5);
            return var7;
         }
      }
   }

   public static class FunAction extends EnemyTools.EnemyAction {

      EnemyTools.DeleFunction deleFunction;
      Object parameter = null;


      public boolean act(float var1) {
         if(this.deleFunction == null) {
            Settings.e("deleFunction can not be null.");
            return true;
         } else {
            this.deleFunction.function(this.parameter);
            return true;
         }
      }
   }

   public interface EnemyActionCallBack {

      boolean act(EnemyTools.EnemyAction var1, float var2);

      boolean actionCancel(EnemyTools.EnemyAction var1);
   }

   public abstract static class EnemyAction {

      protected EnemyTools.EnemyActionCallBack callBack;
      private Enemy enemy;


      public boolean act(float var1) {
         return this.callBack == null?false:(this.callBack.actionCancel(this)?true:this.callBack.act(this, var1));
      }

      public void clearEnemyAction() {
         this.enemy = null;
         this.callBack = null;
      }

      public Enemy getEnemy() {
         return this.enemy;
      }

      public void setEnemy(Enemy var1) {
         this.enemy = var1;
      }
   }

   public interface DeleFunction {

      void function(Object var1);
   }
}
