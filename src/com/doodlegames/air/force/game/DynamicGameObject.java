package com.doodlegames.air.force.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public abstract class DynamicGameObject extends GameObject implements EnemyTools.TurnDataProvider {

   public static final float shadowAlpha = 0.5F;
   protected static final Vector2 shadowOffset = new Vector2(70.0F, -70.0F);
   protected static final Vector2 shadowScale = new Vector2(0.4F, 0.4F);
   private static Vector2 temp1Vector = new Vector2();
   private static Vector2 temp2Vector = new Vector2();
   public final Vector2 accel = new Vector2();
   public float angularSpeed = 0.0F;
   protected Array<DynamicGameObject.GameAction> gameActions = new Array(1);
   private List<Rectangle> hitRects = new ArrayList();
   public final Vector2 velocity = new Vector2();


   public DynamicGameObject(float var1, float var2, float var3, float var4) {
      super(var1, var2, var3, var4);
      this.hitRects.add(this.bounds);
   }

   public static Vector2 RotatePoint(Vector2 var0, Vector2 var1, float var2) {
      temp1Vector.set(var0).sub(var1);
      temp2Vector.set(temp1Vector);
      temp1Vector.rotate(var2).sub(temp2Vector);
      var0.add(temp1Vector);
      return var0;
   }

   public static DynamicGameObject.GameSeqAction gameSeq(DynamicGameObject.GameAction ... var0) {
      DynamicGameObject.GameSeqAction var1 = (DynamicGameObject.GameSeqAction)Pools.obtain(DynamicGameObject.GameSeqAction.class);
      int var2 = var0.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         DynamicGameObject.GameAction var4 = var0[var3];
         var1.actions.add(var4);
      }

      var1.currentIndex = 0;
      return var1;
   }

   public static float getPursueAngle(float var0, float var1, float var2, float var3) {
      return 90.0F + (float)(57.2957763671875D * Math.atan2((double)(var3 - var1), (double)(var2 - var0)));
   }

   public static float getPursueVel(Vector2 var0, Vector2 var1, float var2, Vector2 var3) {
      float var4 = (float)(57.2957763671875D * Math.atan2((double)(var1.y - var0.y), (double)(var1.x - var0.x)));
      if(var2 <= 0.0F) {
         var2 = -var2;
      }

      var3.set(var2, 0.0F).rotate(var4);
      return 90.0F + var4;
   }

   public static Vector2 getRotateOffset(Vector2 var0, float var1) {
      float var2 = var0.x;
      float var3 = var0.y;
      var0.rotate(var1);
      var0.sub(var2, var3);
      return var0;
   }

   public void addGameAction(DynamicGameObject.GameAction var1) {
      var1.gameObject = this;
      this.gameActions.add(var1);
   }

   public void clearGameObjectActions() {
      Pools.free(this.gameActions);
      this.gameActions.clear();
   }

   public List<Rectangle> getHitRectangle() {
      return this.hitRects;
   }

   public List<Rectangle> getHitRectangle(int var1) {
      return this.getHitRectangle();
   }

   public void update(float var1) {
      this.updateDynamicParameter(var1);

      for(int var2 = -1 + this.gameActions.size; var2 >= 0; --var2) {
         DynamicGameObject.GameAction var3 = (DynamicGameObject.GameAction)this.gameActions.get(var2);
         if(var3.act(var1)) {
            this.gameActions.removeIndex(var2);
            Pools.free((Object)var3);
         }
      }

   }

   protected void updateDynamicParameter(float var1) {
      this.setAngle(this.getAngle() + var1 * this.angularSpeed);
      this.velocity.add(var1 * this.accel.x, var1 * this.accel.y);
      this.position.add(var1 * this.velocity.x, var1 * this.velocity.y);
      this.bounds.x = this.position.x - this.bounds.width / 2.0F;
      this.bounds.y = this.position.y - this.bounds.height / 2.0F;
   }

   public static class GameSeqAction extends DynamicGameObject.GameAction {

      protected Array<DynamicGameObject.GameAction> actions = new Array(2);
      public int currentIndex = 0;


      public boolean act(float var1) {
         if(this.currentIndex >= 0 && this.currentIndex < this.actions.size) {
            if(((DynamicGameObject.GameAction)this.actions.get(this.currentIndex)).act(var1)) {
               ++this.currentIndex;
            }

            return false;
         } else {
            for(int var2 = 0; var2 < this.actions.size; ++var2) {
               try {
                  Pools.free(this.actions.get(var2));
               } catch (IllegalArgumentException var4) {
                  Settings.e(var4.getMessage());
               }
            }

            this.actions.clear();
            return true;
         }
      }
   }

   public abstract static class GameAction {

      protected DynamicGameObject gameObject;


      public abstract boolean act(float var1);
   }
}
