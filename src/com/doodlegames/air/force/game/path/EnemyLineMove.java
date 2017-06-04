package com.doodlegames.air.force.game.path;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.path.AbstractEnemyPath;
import com.doodlegames.air.force.utils.Settings;

import java.io.BufferedReader;

public class EnemyLineMove extends AbstractEnemyPath {

   public Vector2 vel;
   public float velAngle;


   public EnemyLineMove(Enemy var1, float var2, float var3, float var4, float var5, String var6) {
      super(var1, var2, var3, var6);
      this.vel = new Vector2(var4, var5);
      if(var4 == 0.0F && var5 == 0.0F) {
         this.velAngle = 0.0F;
      } else {
         this.velAngle = 90.0F + 57.295776F * MathUtils.atan2(var5, var4);
      }
   }

   protected float computInitAngle() {
      return this.velAngle;
   }

   public float moveForward(float var1) {
      super.moveForward(var1);
      if(!this.isOver()) {
         float var3 = this.getCurrentTimeWithInter();
         float var4 = var3 * this.vel.x;
         float var5 = var3 * this.vel.y;
         this.x = var4 + this.startX;
         this.y = var5 + this.startY;
         if(this.vel.x == 0.0F && this.vel.y == 0.0F) {
            return this.velAngle;
         } else {
            float var6 = this.vel.y - Settings.backgroundVelocity.y;
            float var7 = this.vel.x;
            return 90.0F + 57.295776F * (float)Math.atan2((double)var6, (double)var7);
         }
      } else {
         return this.enemy.getAngle();
      }
   }

   public static class EnemyBGMove extends AbstractEnemyPath {

      public EnemyBGMove(Enemy var1, float var2, float var3, String var4) {
         super(var1, var2, var3, var4);
         this.setLimitTime(-1.0F);
      }

      protected float computInitAngle() {
         return 0.0F;
      }

      public boolean isOver() {
         return false;
      }

      public float moveForward(float var1) {
         super.moveForward(var1);
         if(!this.isOver()) {
            this.y += var1 * Settings.backgroundVelocity.y;
         }

         return 0.0F;
      }
   }

   public static class LineAvgSpeedUpClass extends Interpolation {

      double accel;
      double distance;
      EnemyLineMove.LinePathInfo info;
      double totalTime;
      double velStart;


      public LineAvgSpeedUpClass(EnemyLineMove.LinePathInfo var1) {
         this.info = var1;
         Vector2 var2 = new Vector2(var1.startPoint);
         var2.sub(var1.endPoint);
         this.distance = (double)var2.len();
         this.totalTime = (double)var1.durationTime;
         this.velStart = var1.initVel;
         this.accel = (2.0D * (this.distance / this.totalTime) - this.velStart - this.velStart) / this.totalTime;
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

   public static class LinePathInfo extends AbstractEnemyPath.PathInfo {

      public double initVel;


      public void deSerialize(BufferedReader var1) {
         super.deSerialize(var1);

         try {
            this.initVel = Double.parseDouble(var1.readLine());
         } catch (Exception var3) {
            Settings.w(var3.getMessage());
         }
      }

      public EnemyLineMove getMoveProcessor(Enemy var1) {
         double var8;
         double var10;
         if(this.durationTime > 0.0F) {
            var8 = (double)((this.endPoint.x - this.startPoint.x) / this.durationTime);
            var10 = (double)((this.endPoint.y - this.startPoint.y) / this.durationTime);
         } else {
            double var2 = (double)(this.endPoint.x - this.startPoint.x);
            double var4 = (double)(this.endPoint.y - this.startPoint.y);
            double var6 = Math.sqrt(var2 * var2 + var4 * var4);
            if(var6 == 0.0D) {
               var8 = 0.0D;
               var10 = 0.0D;
            } else {
               var8 = var2 / var6;
               var10 = var4 / var6;
            }
         }

         EnemyLineMove var12 = new EnemyLineMove(var1, this.startPoint.x, this.startPoint.y, (float)var8, (float)var10, this.pathName);
         var12.setLimitTime(this.durationTime);
         var12.setInterpolation(new EnemyLineMove.LineAvgSpeedUpClass(this));
         return var12;
      }
   }
}
