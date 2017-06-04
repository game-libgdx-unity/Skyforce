package com.doodlegames.air.force.game.path;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.path.AbstractEnemyPath;
import com.doodlegames.air.force.utils.Settings;

import java.io.BufferedReader;

public class EnemyArcMove extends AbstractEnemyPath {

   Vector2 arcCurrentPoint;
   Vector2 arcStartPoint;
   float deltaAngle;
   float endAngle;
   boolean isClockWise;
   float radis;
   float startAngle;


   public EnemyArcMove(EnemyArcMove.EnemyArcMoveParameter var1, String var2) {
      super(var1.enemy, 0.0F, 0.0F, var2);
      this.radis = var1.radis;
      this.isClockWise = var1.isClockWise;
      this.startAngle = var1.startAngle;
      this.endAngle = var1.endAngle;
      this.arcStartPoint = (new Vector2(this.radis, 0.0F)).rotate(this.startAngle);
      this.arcCurrentPoint = new Vector2();
      this.deltaAngle = getDeltaAngle(this.startAngle, this.endAngle, this.isClockWise);
      if(this.isClockWise) {
         this.deltaAngle = -this.deltaAngle;
      }

   }

   public static float getDeltaAngle(float var0, float var1, boolean var2) {
      float var3 = normalAngle(var0, false);
      float var4 = normalAngle(normalAngle(var1, false) - var3, false);
      if(var2) {
         var4 = 360.0F - var4;
      }

      return var4;
   }

   public static float normalAngle(float var0, boolean var1) {
      if(var1) {
         var0 = -var0;
      }

      while(var0 >= 360.0F) {
         var0 -= 360.0F;
      }

      while(var0 < 0.0F) {
         var0 += 360.0F;
      }

      return var0;
   }

   protected float computInitAngle() {
      float var1 = this.startAngle;
      float var2;
      if(this.isClockWise) {
         var2 = -90.0F;
      } else {
         var2 = 90.0F;
      }

      return var1 + var2;
   }

   public float moveForward(float var1) {
      super.moveForward(var1);
      if(!this.isOver()) {
         float var3 = this.getCurrentPercentWithInter();
         float var4 = this.startAngle + var3 * this.deltaAngle;
         this.arcCurrentPoint.set(this.radis, 0.0F).rotate(var4);
         float var6 = this.arcCurrentPoint.x - this.arcStartPoint.x;
         float var7 = this.arcCurrentPoint.y - this.arcStartPoint.y;
         this.x = var6 + this.startX;
         this.y = var7 + this.startY;
         byte var8;
         if(this.isClockWise) {
            var8 = -90;
         } else {
            var8 = 90;
         }

         return 90.0F + var4 + (float)var8;
      } else {
         return this.enemy.getAngle();
      }
   }

   public static class ArcAvgSpeedUpClass extends Interpolation {

      EnemyArcMove.ArcPathInfo arcPathInfo;
      AbstractEnemyPath.AvgSpeedUpClass avgModifer;


      public ArcAvgSpeedUpClass(EnemyArcMove.ArcPathInfo var1) {
         this.arcPathInfo = var1;
         double var2 = (double)var1.radis;
         double var4 = var1.angleStart;
         double var6 = var1.angleEnd;
         boolean var8 = var1.isClockWise;
         this.avgModifer = new AbstractEnemyPath.AvgSpeedUpClass(var2 * 3.141592653589793D * 2.0D * ((double)EnemyArcMove.getDeltaAngle((float)var4, (float)var6, var8) / 360.0D), (double)var1.vel, (double)var1.durationTime);
      }

      public float apply(float var1) {
         return this.avgModifer.apply(var1);
      }
   }

   public static class ArcPathInfo extends AbstractEnemyPath.PathInfo {

      public double angleEnd = 0.0D;
      public double angleStart = 0.0D;
      public Vector2 circleCor = new Vector2();
      public boolean isClockWise = false;
      public float radis = 1.0F;
      public float vel = 0.0F;


      public void deSerialize(BufferedReader var1) {
         super.deSerialize(var1);

         try {
            vector2Deserialize(var1, this.circleCor);
            this.radis = Float.parseFloat(var1.readLine());
            this.vel = Float.parseFloat(var1.readLine());
            this.angleStart = (double)Float.parseFloat(var1.readLine());
            this.angleEnd = (double)Float.parseFloat(var1.readLine());
            this.isClockWise = Boolean.parseBoolean(var1.readLine());
         } catch (Exception var3) {
            Settings.w(var3.getMessage());
         }
      }

      public AbstractEnemyPath getMoveProcessor(Enemy var1) {
         EnemyArcMove.EnemyArcMoveParameter var2 = new EnemyArcMove.EnemyArcMoveParameter();
         var2.radis = this.radis;
         var2.isClockWise = this.isClockWise;
         var2.startAngle = (float)this.angleStart;
         var2.endAngle = (float)this.angleEnd;
         var2.enemy = var1;
         EnemyArcMove var3 = new EnemyArcMove(var2, this.pathName);
         var3.setLimitTime(this.durationTime);
         return var3;
      }
   }

   public static class EnemyArcMoveParameter {

      public float endAngle;
      public Enemy enemy;
      public boolean isClockWise;
      public float radis;
      public float startAngle;


   }
}
