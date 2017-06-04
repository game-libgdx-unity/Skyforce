package com.doodlegames.air.force.game.path;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.path.AbstractEnemyPath;
import com.doodlegames.air.force.utils.Bezier;
import com.doodlegames.air.force.utils.Settings;

import java.io.BufferedReader;

public class EnemyBezierMove extends AbstractEnemyPath {

   Vector3 bezierResult = new Vector3();
   private final float iniAngle;
   private final Vector2[] p;


   public EnemyBezierMove(EnemyBezierMove.EnemyBezierMoveParameters var1, String var2) {
      super(var1.enemy, var1.startP.x, var1.startP.y, var2);
      Vector2[] var3 = new Vector2[]{new Vector2(var1.p1), new Vector2(var1.p2), new Vector2(var1.p3), new Vector2(var1.p4)};
      this.p = var3;
      Vector2 var4 = (new Vector2(var1.p2)).sub(var1.p1);
      this.iniAngle = 90.0F + 57.295776F * MathUtils.atan2(var4.y, var4.x);
      this.setLimitTime(var1.time);
   }

   protected float computInitAngle() {
      return this.iniAngle;
   }

   public float moveForward(float var1) {
      super.moveForward(var1);
      if(!this.isOver()) {
         float var3 = this.getCurrentPercentWithInter();
         Bezier.computerBezier3(this.bezierResult, this.p[0], this.p[1], this.p[2], this.p[3], var3);
         float var4 = this.bezierResult.x - this.p[0].x;
         float var5 = this.bezierResult.y - this.p[0].y;
         this.x = var4 + this.startX;
         this.y = var5 + this.startY;
         return this.bezierResult.z;
      } else {
         return this.enemy.getAngle();
      }
   }

   public static class EnemyBezierMoveParameters {

      public Enemy enemy;
      public Vector2 p1;
      public Vector2 p2;
      public Vector2 p3;
      public Vector2 p4;
      public Vector2 startP;
      public float time;


   }

   public static class BezierPathInfo extends AbstractEnemyPath.PathInfo {

      Vector2 p2 = new Vector2();
      Vector2 p3 = new Vector2();


      public void deSerialize(BufferedReader var1) {
         super.deSerialize(var1);

         try {
            vector2Deserialize(var1, this.p2);
            vector2Deserialize(var1, this.p3);
         } catch (Exception var3) {
            Settings.w(var3.getMessage());
         }
      }

      public AbstractEnemyPath getMoveProcessor(Enemy var1) {
         EnemyBezierMove.EnemyBezierMoveParameters var2 = new EnemyBezierMove.EnemyBezierMoveParameters();
         var2.enemy = var1;
         var2.startP = super.startPoint;
         var2.p1 = super.startPoint;
         var2.p2 = this.p2;
         var2.p3 = this.p3;
         var2.p4 = super.endPoint;
         var2.time = this.durationTime;
         return new EnemyBezierMove(var2, this.pathName);
      }
   }
}
