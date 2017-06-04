package com.doodlegames.air.force.game.prop.propFloatingPath;

import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.prop.propFloatingPath.PropFloating;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.Random;

public class PingPongFloating implements PropFloating {

   public static final float FLYINGVEL = 80.0F;
   public boolean allowAttracking = true;
   private float boomTimeStamp;
   private final DynamicGameObject floatingObject;
   private PingPongFloating.PingPongState pingPongState;
   private final Random rand;


   public PingPongFloating(Random var1, DynamicGameObject var2) {
      this.rand = var1;
      this.floatingObject = var2;
      this.floatingObject.velocity.set(0.0F, 0.0F);
      this.floatingObject.accel.set(0.0F, 0.0F);
      this.pingPongState = PingPongFloating.PingPongState.Sleep;
   }

   public void beAttrackedByFighter(Fighter var1) {}

   public void beginFloating() {
      float var1 = 160.0F;
      if(this.pingPongState == PingPongFloating.PingPongState.Sleep) {
         this.floatingObject.velocity.set(80.0F + 80.0F * this.rand.nextFloat(), 80.0F + 80.0F * this.rand.nextFloat());
         Vector2 var3 = this.floatingObject.velocity;
         var3.x *= (float)(-1 + 2 * this.rand.nextInt(2));
         Vector2 var4 = this.floatingObject.velocity;
         var4.y *= (float)(-1 + 2 * this.rand.nextInt(2));
         Vector2 var5 = this.floatingObject.accel;
         float var6;
         if(this.floatingObject.velocity.x < 0.0F) {
            var6 = var1;
         } else {
            var6 = -160.0F;
         }

         var5.x = var6;
         Vector2 var7 = this.floatingObject.accel;
         if(this.floatingObject.velocity.y >= 0.0F) {
            var1 = -160.0F;
         }

         var7.y = var1;
         this.pingPongState = PingPongFloating.PingPongState.BombOut;
         this.boomTimeStamp = Clock.getCurrentTime();
      } else {
         Settings.appLog("PingPongFloatingError pingPongState should be Sleep, but it is " + this.pingPongState + ". MayBe a invoker error!");
      }
   }

   public boolean canBeAttracked() {
      return false;
   }

   public void stopFloating() {
      if(this.pingPongState != PingPongFloating.PingPongState.BombOut && this.pingPongState != PingPongFloating.PingPongState.Flying) {
         Settings.appLog("PingPongState should be BombOut or Flying, But it is " + this.pingPongState + ". MayBe a invoker error!");
      } else {
         this.floatingObject.velocity.set(0.0F, 0.0F);
         this.floatingObject.accel.set(0.0F, 0.0F);
         this.pingPongState = PingPongFloating.PingPongState.Sleep;
      }
   }

   public void updatePropFloating(final float n) {

       final float x = this.floatingObject.bounds.x;

       final float n2 = x + this.floatingObject.bounds.width;

       final float y = this.floatingObject.bounds.y;

       final float n3 = y + this.floatingObject.bounds.height;

       float n4 = this.floatingObject.velocity.x;

       float n5 = this.floatingObject.velocity.y;

       float n6 = this.floatingObject.accel.x;

       float n7 = this.floatingObject.accel.y;

       if (x < 0.0f && n4 < 0.0f) {

           n4 = Math.abs(n4);

           n6 = -Math.abs(n6);

       }

       if (n2 > 640.0f && n4 > 0.0f) {

           n4 = -Math.abs(n4);

           n6 = Math.abs(n6);

       }

       if (y < 0.0f && n5 < 0.0f) {

           n5 = Math.abs(n5);

           n7 = -Math.abs(n7);

       }

       if (n3 > 800.0f && n5 > 0.0f) {

           n5 = -Math.abs(n5);

           n7 = Math.abs(n7);

       }

       this.floatingObject.velocity.set(n4, n5);

       this.floatingObject.accel.set(n6, n7);

       switch (this.pingPongState) {

           case BombOut: {

               if (Math.abs(this.floatingObject.velocity.x) < 80.0f || Math.abs(this.floatingObject.velocity.y) < 80.0f || Clock.getCurrentTime(this.boomTimeStamp) > 3.0f) {

                   this.floatingObject.accel.set(0.0f, 0.0f);

                   this.pingPongState = PingPongState.Flying;

                   return;

               }

               break;

           }

       }

   }

   private static enum PingPongState {

      BombOut,
      Flying,
      Sleep,
   }
}
