package com.doodlegames.air.force.game.path;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.path.Path;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

public class PursuePath extends Path {

   public static final int TURNCOUNTMAX = 1;
   private float accelX;
   private float offsetVelX;
   private float offsetVelY;
   private int pursueGroupId;
   private PursuePath.PursueState pursueState;
   private Vector2 pursueV;
   private float shootTimeStamp;
   private Clock turnClock;
   private int turnCount;
   private int turnCountMax;
   private final float velFloat;
   private World world;


   public PursuePath(DynamicGameObject var1, float var2, World var3) {
      super(var1);
      this.pursueV = new Vector2(0.0F, var2);
      this.turnClock = new Clock(0.0F, 1.0F, Clock.ClockType.FireOnlyOnce);
      this.velFloat = var2;
      this.initPursuePath(var1, var3, 1);
   }

   public static float TurningChoose(float var0, float var1, float var2, float var3) {
      double var4 = Math.sqrt((double)(var0 * var0 + var1 * var1));
      double var6 = Math.sqrt((double)(var2 * var2 + var3 * var3));
      return (float)(90.0D - 57.2957763671875D * Math.acos((double)(var0 * var3 - var1 * var2) / (var4 * var6)));
   }

   private float updateStraightForward(float var1) {
      if(this.turnCount == this.turnCountMax) {
         if(Clock.getCurrentTime(this.shootTimeStamp) >= 0.5F) {
            this.pursueV.x = 0.0F;
            this.pursueV.add(0.0F, var1 * this.accelX);
            return 0.0F;
         }

         this.pursueV.x = this.offsetVelX;
         this.pursueV.y = this.offsetVelY;
      }

      return 0.0F;
   }

   private float updateTurning(float var1) {
      Rectangle var2 = this.world.fighter.getNearestRec(this.pursueGroupId);
      if(var2 != null) {
         float var3 = var2.x + var2.width / 2.0F - this.object.getPositionX();
         float var4 = var2.y + var2.height / 2.0F - this.object.getPositionY();
         float var5 = TurningChoose(this.pursueV.x, this.pursueV.y, var3, var4);
         float var6 = var1 * (300.0F + 100.0F * this.world.rand.nextFloat());
         if(var6 > Math.abs(var5)) {
            var6 = Math.abs(var5);
         }

         if(var5 < 0.0F) {
            var6 = -var6;
         }

         this.pursueV.rotate(var6);
         return var6;
      } else {
         return 0.0F;
      }
   }

   protected float MovingForwad(float var1, Vector2 var2) {
      var2.x = var1 * this.pursueV.x;
      var2.y = var1 * this.pursueV.y;
      return 0.0F;
   }

   protected float computerInitAngle() {
      return 0.0F;
   }

   public float getVelX() {
      return this.pursueV.x;
   }

   public float getVelY() {
      return this.pursueV.y;
   }

   public void initPursuePath(DynamicGameObject var1, World var2, int var3) {
      super.initPath(var1);
      this.pursueGroupId = Clock.rand.nextInt(20);
      this.accelX = 800.0F;
      this.offsetVelX = 100.0F;
      this.offsetVelY = 0.0F;
      this.world = var2;
      this.object.velocity.set(0.0F, 0.0F);
      this.pursueV.set(0.0F, this.velFloat);
      this.turnCountMax = var3;
      this.turnCount = var3;
      this.pursueState = PursuePath.PursueState.StraightFoward;
      this.turnClock.resetClockFireOnce(1.0F);
      boolean var6;
      if(this.object.getPositionX() < this.world.fighter.getPositionX()) {
         var6 = true;
      } else {
         var6 = false;
      }

      if(var6) {
         this.offsetVelX = -Math.abs(this.offsetVelX);
      } else {
         this.offsetVelX = Math.abs(this.offsetVelX);
      }

      this.shootTimeStamp = Clock.getCurrentTime();
   }

   public void setOffsetVel(float var1, float var2, float var3, int var4) {
      this.offsetVelX = var1;
      this.offsetVelY = var2;
      this.accelX = var3;
      this.pursueGroupId = var4;
   }
   public float updateTravel(final float n) {

       float n2 = 0.0f;

       if (this.turnClock.isFired()) {

           if (this.pursueState == PursueState.StraightFoward && this.turnCount > 0) {

               this.pursueState = PursueState.Turning;

               this.turnCount = -1 + this.turnCount;

               this.turnClock.resetClockFireOnce(0.7f);

           }

           else if (this.pursueState == PursueState.Turning) {

               this.pursueState = PursueState.StraightFoward;

               this.turnClock.resetClockFireOnce(0.3f);

           }

       }

       switch (this.pursueState) {

           case StraightFoward: {

               n2 = this.updateStraightForward(n);

               break;

           }

           case Turning: {

               n2 = this.updateTurning(n);

               break;

           }

           default: {

               Settings.appLog("invalid State in PursuePath.updateTravel()");

               break;

           }

       }

       super.updateTravel(n);

       return n2;

   }

   private static enum PursueState {

      StraightFoward,
      Turning
   }
}
