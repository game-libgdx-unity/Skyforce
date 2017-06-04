package com.doodlegames.air.force.game.path;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.path.EnemyPathInterface;
import com.doodlegames.air.force.utils.Settings;

import java.io.BufferedReader;

public abstract class AbstractEnemyPath implements EnemyPathInterface {

   public static final float FOREVER = -1.0F;
   private float currentTime;
   protected Enemy enemy;
   private Interpolation interpolation;
   private float limitedTime;
   public String pathName;
   protected float startX;
   protected float startY;
   protected float x;
   protected float y;


   public AbstractEnemyPath(Enemy var1, float var2, float var3, String var4) {
      this.enemy = var1;
      this.x = var2;
      this.y = var3;
      this.startX = var2;
      this.startY = var3;
      this.limitedTime = -1.0F;
      this.currentTime = 0.0F;
      this.interpolation = null;
      this.pathName = var4;
   }

   private void checkLimitTime() {
      if(this.interpolation != null && this.limitedTime <= 0.0F) {
         this.limitedTime = -this.limitedTime;
         float var1;
         if(this.limitedTime == 0.0F) {
            var1 = 1.0F;
         } else {
            var1 = this.limitedTime;
         }

         this.limitedTime = var1;
         Settings.w("invalid state in AbstractEnemyPath!");
      }

   }

   protected abstract float computInitAngle();

   public float getCurrentPercentWithInter() {
      float var1 = this.currentTime / this.limitedTime;
      if(this.interpolation != null) {
         var1 = this.interpolation.apply(var1);
      }

      return var1;
   }

   public float getCurrentTime() {
      return this.currentTime;
   }

   public float getCurrentTimeWithInter() {
      float var1 = this.currentTime;
      if(this.interpolation != null) {
         float var2 = var1 / this.limitedTime;
         var1 = this.interpolation.apply(var2) * this.limitedTime;
      }

      return var1;
   }

   public Interpolation getInterpolation() {
      return this.interpolation;
   }

   public float getLimitTime() {
      return this.limitedTime;
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public boolean isOver() {
      return this.limitedTime != -1.0F && this.currentTime >= this.limitedTime;
   }

   public float moveForward(float var1) {
      if(!this.isOver()) {
         this.currentTime += var1;
         this.x += var1 * Settings.backgroundVelocity.x;
         this.y += var1 * Settings.backgroundVelocity.y;
      } else {
         this.y = -128000.0F;
      }

      return 0.0F;
   }

   public float restartFrom(float var1, float var2) {
      this.x = var1;
      this.y = var2;
      this.startX = var1;
      this.startY = var2;
      this.currentTime = 0.0F;
      return this.computInitAngle();
   }

   public void setInterpolation(Interpolation var1) {
      this.interpolation = var1;
      this.checkLimitTime();
   }

   public void setLimitTime(float var1) {
      if(var1 > 0.0F || var1 == -1.0F) {
         this.limitedTime = var1;
         if(var1 == -1.0F) {
            this.setInterpolation((Interpolation)null);
            return;
         }
      }

   }

   public abstract static class PathInfo {

      public float durationTime = 1.0F;
      public Vector2 endPoint = new Vector2();
      public String pathName = "";
      public Vector2 startPoint = new Vector2();


      public PathInfo() {}

      public PathInfo(Vector2 var1, Vector2 var2, float var3, String var4) {
         this.startPoint.set(var1);
         this.endPoint.set(var2);
         this.durationTime = var3;
         this.pathName = var4;
      }

      public static void vector2Deserialize(BufferedReader var0, Vector2 var1) {
         try {
            var1.x = Float.parseFloat(var0.readLine());
            var1.y = Float.parseFloat(var0.readLine());
         } catch (Exception var3) {
            Settings.w(var3.getMessage());
            var1.x = 0.0F;
            var1.y = 0.0F;
         }
      }

      public void deSerialize(BufferedReader var1) {
         try {
            vector2Deserialize(var1, this.startPoint);
            vector2Deserialize(var1, this.endPoint);
            this.durationTime = Float.parseFloat(var1.readLine());
            this.pathName = var1.readLine();
         } catch (Exception var3) {
            Settings.w(var3.getMessage());
         }
      }

      public abstract AbstractEnemyPath getMoveProcessor(Enemy var1);
   }

   public static class AvgSpeedUpClass extends Interpolation {

      double accel;
      double distance;
      double totalTime;
      double velStart;


      public AvgSpeedUpClass(double var1, double var3, double var5) {
         this.distance = var1;
         this.velStart = var3;
         this.totalTime = var5;
         this.accel = (2.0D * (var1 / var5) - var3 - var3) / var5;
      }

      public float apply(float var1) {
         if(Math.abs(this.distance) <= 1.0E-7D) {
            return var1;
         } else {
            double var2 = this.totalTime * (double)var1;
            return (float)((var2 * this.velStart + var2 * var2 * this.accel / 2.0D) / this.distance);
         }
      }
   }
}
