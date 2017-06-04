package com.doodlegames.air.force.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.doodlegames.air.force.game.BGAction;
import com.doodlegames.air.force.utils.Settings;

public class BGActionSwim extends BGAction {

   final float LIMITANGLE = 30.0F;
   final float SLEEPTIME = 1.0F;
   float angleSpeed;
   float swimTime = 2.0F;


   private void rotate(float var1) {
      Sprite var2 = this.item.getSpriteDrawable().getSprite();
      float var3 = var2.getRotation() + var1 * this.angleSpeed;
      if(var3 > 30.0F) {
         var3 = 30.0F;
         this.angleSpeed = -Math.abs(this.angleSpeed);
      }

      if(var3 < -30.0F) {
         var3 = -30.0F;
         this.angleSpeed = Math.abs(this.angleSpeed);
      }

      var2.setRotation(var3);
   }

   public void initalize() {
      if(this.item.getSpriteDrawable().getSprite().isFlipX()) {
         this.angleSpeed = Math.abs(this.angleSpeed);
         this.item.getSpriteDrawable().getSprite().setRotation(-30.0F);
      } else {
         this.angleSpeed = -Math.abs(this.angleSpeed);
         this.item.getSpriteDrawable().getSprite().setRotation(30.0F);
      }
   }

   public void reset() {}

   public void setAngleSpeed(float var1) {
      this.angleSpeed = Math.abs(var1);
      this.swimTime = 30.0F / Math.abs(var1);
      if(this.swimTime < 1.0E-4F) {
         Settings.i("invalid cycleTime:" + this.swimTime + ". fix it to 1 already.");
         this.swimTime = 1.0F;
      }

   }

   public void update(float var1) {
      super.update(var1);
      float var2 = 1.0F + 2.0F * this.swimTime;
      int var3 = (int)(super.time / var2);
      float var4 = super.time - var2 * (float)var3;
      if(var4 <= 2.0F * this.swimTime) {
         this.rotate(var1);
      } else if(var4 > 1.0F + 2.0F * this.swimTime) {
         throw new RuntimeException("Invalid innerCycleTime.");
      }

   }
}
