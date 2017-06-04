package com.doodlegames.air.force.enemy.level4;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pools;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.utils.Bezier;
import com.doodlegames.air.force.utils.Clock;

public class ChildEnemyMoveActions {

   public static void addBezierPursue(Enemy var0, Fighter var1) {
      clearMoveParameters(var0);
      ChildEnemyMoveActions.AirPursue var2 = (ChildEnemyMoveActions.AirPursue)Pools.obtain(ChildEnemyMoveActions.AirPursue.class);
      float var3 = var0.getPositionX();
      float var4 = var0.getPositionY();
      float var5 = var1.getPositionX();
      float var6 = var1.getPositionY();
      float var7 = 0.8F * (Math.abs(var6 - var4) + Math.abs(var5 - var3));
      if(var7 < 20.0F) {
         var7 = 20.0F;
         var5 = var3;
         var6 = var4 - 20.0F;
      }

      float var8 = var7 / Math.abs(-106.666664F);
      var2.p1.set(var3, var4);
      var2.p2.set(var3, var6);
      var2.p3.set(2.0F * var5 - var3, var6);
      var2.p4.set(2.0F * var5 - var3, 2.0F * var6 - var4);
      var2.time = var8 / 2.0F;
      var2.currentTime = 0.0F;
      var0.addEnemyAction(var2);
   }

   public static void addDotMoveAction(Enemy var0) {
      clearMoveParameters(var0);
      float var1 = -(100.0F + 100.0F * Clock.rand.nextFloat());
      float var2 = -(200.0F + 100.0F * Clock.rand.nextFloat());
      float var3 = -(200.0F + 100.0F * Clock.rand.nextFloat());
      float var4 = -(200.0F + 100.0F * Clock.rand.nextFloat());
      float var5 = Math.abs(var1 / -53.333332F);
      float var6 = Math.abs(var2 / -53.333332F);
      float var7 = Math.abs(var3 / -53.333332F);
      float var8 = Math.abs(var4 / -53.333332F);
      ChildEnemyMoveActions.DotMoveAction var9 = (ChildEnemyMoveActions.DotMoveAction)Pools.obtain(ChildEnemyMoveActions.DotMoveAction.class);
      EnemyTools.EnemyAction[] var10 = new EnemyTools.EnemyAction[]{EnemyTools.moveBy(var0, 0.0F, var1, var5), EnemyTools.delay(var5), EnemyTools.moveBy(var0, 0.0F, var2, var6), EnemyTools.delay(var6), EnemyTools.moveBy(var0, 0.0F, var3, var7), EnemyTools.delay(var7), EnemyTools.moveBy(var0, 0.0F, var4, var8), EnemyTools.delay(var8)};
      var9.dotMove = (EnemyTools.SeqAction)EnemyTools.sequence(var10);
      var9.setEnemy(var0);
      var0.addEnemyAction(var9);
   }

   private static void clearMoveParameters(Enemy var0) {
      var0.angularSpeed = 0.0F;
      var0.accel.set(0.0F, 0.0F);
      var0.velocity.set(0.0F, 0.0F);
      var0.setAngle(0.0F);
      var0.clearGameObjectActions();
      var0.clearEnemyActions();
   }

   public static class DotMoveAction extends EnemyTools.EnemyAction {

      EnemyTools.SeqAction dotMove;


      public boolean act(float var1) {
         Enemy var2 = this.getEnemy();
         if(this.dotMove != null && !this.dotMove.act(var1)) {
            if(var2.isCrashed()) {
               Pools.free(this.dotMove.actions);
               Pools.free((Object)this.dotMove);
               this.dotMove.actions.clear();
               this.dotMove = null;
               return true;
            } else {
               return false;
            }
         } else {
            return true;
         }
      }

      public void setEnemy(Enemy var1) {
         super.setEnemy(var1);
         this.dotMove.setEnemy(var1);
      }
   }

   public static class AirPursue extends EnemyTools.EnemyAction {

      public float currentTime;
      public Vector2 p1 = new Vector2();
      public Vector2 p2 = new Vector2();
      public Vector2 p3 = new Vector2();
      public Vector2 p4 = new Vector2();
      public float time;


      public boolean act(float var1) {
         Enemy var2 = this.getEnemy();
         float var3 = this.currentTime / (2.0F * this.time);
         Vector3 var4 = Vector3Utils.tmp;
         Bezier.computerBezier3(var4, this.p1, this.p2, this.p3, this.p4, var3);
         float var5 = var4.x;
         float var6 = var4.y;
         float var7 = var4.z;
         float var12;
         int var8 = (var12 = var3 - 0.5F) == 0.0F?0:(var12 < 0.0F?-1:1);
         boolean var9 = false;
         if(var8 > 0) {
            float var10 = (var5 - var2.getPositionX()) / var1;
            float var11 = (var6 - var2.getPositionY()) / var1;
            var2.velocity.set(var10, var11);
            var9 = true;
         }

         var2.setPosition(var5, var6);
         var2.setAngle(var7);
         this.currentTime += var1;
         return var9;
      }
   }
}
