package com.doodlegames.air.force.game.prop.propFloatingPath;

import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.prop.propFloatingPath.PropFloating;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.Random;

public class CircleFloating implements PropFloating {

   private static final float deltaXY = 15.0F;
   private static final float randomDelta = 15.0F;
   private boolean attrackedInit = false;
   private Vector2 circleCenter;
   private Clock clockBombEnd;
   private Fighter fighter;
   private DynamicGameObject floatingObject;
   private CircleFloating.FloatingState floatingState;
   float flyingTimeStamp = 0.0F;
   private boolean isClockWise;
   private Random rand;


   public CircleFloating(Random var1, DynamicGameObject var2) {
      this.rand = var1;
      this.floatingObject = var2;
      this.circleCenter = new Vector2();
      this.isClockWise = var1.nextBoolean();
      this.floatingState = CircleFloating.FloatingState.Sleep;
      this.clockBombEnd = new Clock(2.0F);
   }

   private void chooseACircleCenter() {
      if(2.0F * this.floatingObject.getPositionX() < 640.0F) {
         this.circleCenter.x = 15.0F + this.floatingObject.getPositionX() + 15.0F * this.rand.nextFloat();
      } else {
         this.circleCenter.x = this.floatingObject.getPositionX() - 15.0F - 15.0F * this.rand.nextFloat();
      }

      if(2.0F * this.floatingObject.getPositionY() < 800.0F) {
         this.circleCenter.y = 15.0F + this.floatingObject.getPositionY() + 15.0F * this.rand.nextFloat();
      } else {
         this.circleCenter.y = this.floatingObject.getPositionY() - 15.0F - 15.0F * this.rand.nextFloat();
      }
   }

   private void propGoDownWithBG(float var1) {
      float var2 = this.floatingObject.getPositionX();
      float var3 = this.floatingObject.getPositionY() + var1 * Settings.backgroundVelocity.y;
      this.floatingObject.setPosition(var2, var3);
   }

   private static void setAttrackAccel(float var0, float var1, float var2, float var3, Vector2 var4) {
      byte var5 = 1;
      byte var6;
      if(var0 > 0.0F) {
         var6 = var5;
      } else {
         var6 = -1;
      }

      if(var1 <= 0.0F) {
         var5 = -1;
      }

      float var7 = Math.abs(var0);
      float var8 = Math.abs(var1);
      float var9 = (1280.0F - var7 - var8) / 120.0F;
      float var10 = var7 * var9 * (float)var6;
      float var11 = var8 * var9 * (float)var5;
      if(var2 * (float)var6 < 0.0F) {
         var10 += -15.0F * var2;
      }

      if(var3 * (float)var5 < 0.0F) {
         var11 += -15.0F * var3;
      }

      var4.set(var10, var11);
   }

   private void setAttractVelAndAccel(float var1, float var2) {
      if(!this.attrackedInit) {
         this.floatingObject.velocity.set(0.0F, 0.0F);
         this.attrackedInit = true;
      }

      setAttrackAccel(var1, var2, this.floatingObject.velocity.x, this.floatingObject.velocity.y, this.floatingObject.accel);
   }

   public void beAttrackedByFighter(Fighter var1) {
      if(this.floatingState == CircleFloating.FloatingState.Flying) {
         this.floatingState = CircleFloating.FloatingState.Attracking;
         this.fighter = var1;
         this.attrackedInit = false;
      } else {
         Settings.appLog("in function beAttrackedByFighter(), this.floatingState should not be " + this.floatingState);
      }
   }

   public void beginFloating() {
      if(this.floatingState == CircleFloating.FloatingState.Sleep) {
         this.floatingState = CircleFloating.FloatingState.BombOut;
         this.floatingObject.velocity.set(30.0F * 15.0F * this.rand.nextFloat(), 30.0F * 15.0F * this.rand.nextFloat());
         Vector2 var2 = this.floatingObject.velocity;
         var2.x *= (float)(-1 + 2 * this.rand.nextInt(2));
         Vector2 var3 = this.floatingObject.velocity;
         var3.y *= (float)(-1 + 2 * this.rand.nextInt(2));
         this.floatingObject.accel.set(-8.0F * this.floatingObject.velocity.x, -8.0F * this.floatingObject.velocity.y);
         this.clockBombEnd.resetClockFireOnce(0.0F);
      } else {
         Settings.appLog("invalid state in CircleFloating.beginFloating() " + this.floatingState);
      }
   }

   public boolean canBeAttracked() {
      return this.floatingState == CircleFloating.FloatingState.Flying && Clock.getCurrentTime(this.flyingTimeStamp) > 0.5F;
   }

   public void stopFloating() {
      if(this.floatingState != CircleFloating.FloatingState.Flying && this.floatingState != CircleFloating.FloatingState.BombOut && this.floatingState != CircleFloating.FloatingState.Attracking) {
         Settings.appLog("invalid state in CircleFloating.stopFloating()" + this.floatingState);
      } else {
         this.floatingObject.velocity.set(0.0F, 0.0F);
         this.floatingObject.accel.set(0.0F, 0.0F);
         this.floatingState = CircleFloating.FloatingState.Sleep;
      }
   }

   

   public void updatePropFloating(final float n) {

       switch (this.floatingState) {

           case BombOut: {

               if (this.clockBombEnd.isFired()) {

                   this.floatingObject.accel.set(0.0f, 0.0f);

                   this.floatingState = FloatingState.Flying;

                   this.flyingTimeStamp = Clock.getCurrentTime();

                   return;

               }

               break;

           }

           case Flying: {

               float y;

               float x;

               if (this.isClockWise) {

                   y = -this.floatingObject.velocity.y;

                   x = this.floatingObject.velocity.x;

               }

               else {

                   y = this.floatingObject.velocity.y;

                   x = -this.floatingObject.velocity.x;

               }

               final float n2 = y * (1.0f + Clock.rand.nextFloat() / 5.0f - 0.1f);

               final float n3 = x * (1.0f + Clock.rand.nextFloat() / 5.0f - 0.1f);

               this.floatingObject.accel.set(n2, n3);

               if (this.floatingObject.accel.len2() > 450.0f) {

                   this.floatingObject.accel.sub(this.floatingObject.velocity);

                   this.floatingObject.accel.sub(this.floatingObject.velocity);

                   this.floatingObject.accel.sub(this.floatingObject.velocity);

               }

               this.propGoDownWithBG(n);

               return;

           }

           case Attracking: {

               if (this.fighter != null && !this.fighter.isCrashed()) {

                   this.setAttractVelAndAccel(this.fighter.getPositionX() - this.floatingObject.getPositionX(), this.fighter.getPositionY() - this.floatingObject.getPositionY());

                   return;

               }

               break;

           }

       }

   }

   private static enum FloatingState {

      Attracking,
      BombOut,
      Flying,
      Sleep
   }
}
