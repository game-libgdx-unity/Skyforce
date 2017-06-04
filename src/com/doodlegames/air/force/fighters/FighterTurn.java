package com.doodlegames.air.force.fighters;


public class FighterTurn {

   static final int MIDDLE = 0;
   static final int TOLEFT = -1;
   static final int TORIGHT = 1;
   private float currentValue;
   private int toWhich;
   private final float turnTime;


   public FighterTurn(float var1) {
      this.turnTime = var1;
      this.currentValue = 0.0F;
      this.toWhich = 0;
   }

   public float getCurrentValue() {
      return this.currentValue;
   }

   public void reset() {
      this.currentValue = 0.0F;
      this.toWhich = 0;
   }

   public void setWhichTo(int var1) {
      if(var1 > 0) {
         this.toWhich = 1;
      }

      if(var1 < 0) {
         this.toWhich = -1;
      }

      if(var1 == 0) {
         this.toWhich = 0;
      }

   }

   public void update(float var1) {
      float var10000 = this.currentValue;
      switch(this.toWhich) {
      case -1:
         float var6 = this.currentValue - var1;
         if(var6 < -this.turnTime) {
            this.currentValue = -this.turnTime;
            return;
         }

         this.currentValue = var6;
         return;
      case 0:
         if(this.currentValue != 0.0F) {
            if(this.currentValue < 0.0F) {
               float var4 = var1 + this.currentValue;
               if(var4 > 0.0F) {
                  this.currentValue = 0.0F;
               } else {
                  this.currentValue = var4;
               }
            }

            if(this.currentValue > 0.0F) {
               float var3 = this.currentValue - var1;
               if(var3 < 0.0F) {
                  this.currentValue = 0.0F;
                  return;
               }

               this.currentValue = var3;
               return;
            }
         }
      case 1:
         float var5 = var1 + this.currentValue;
         if(var5 > this.turnTime) {
            this.currentValue = this.turnTime;
            return;
         }

         this.currentValue = var5;
         return;
      default:
      }
   }
}
