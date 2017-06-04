package com.doodlegames.air.force.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.doodlegames.air.force.game.BGAction;
import com.doodlegames.air.force.utils.Settings;

public class BGActionShakeVe extends BGAction {

   private float cycleTime = 1.0F;
   private float distance;
   private float offsetState = 0.0F;


   public void initalize() {
      this.offsetState = 0.0F;
   }

   public void reset() {
      this.offsetState = 0.0F;
      this.cycleTime = 1.0F;
   }

   public void setCycleAndDistance(float var1, float var2) {
      this.cycleTime = var1;
      this.distance = Math.abs(var2);
      this.offsetState = 0.0F;
      if(this.cycleTime < 1.0E-4F) {
         this.cycleTime = 1.0F;
         Settings.e("invalid cycleTime:" + var1 + ". fix it to 1 already.");
      }

   }

   public void update(float var1) {
      super.update(var1);
      int var2 = (int)(super.time / this.cycleTime) % 2;
      Sprite var3 = this.item.getSpriteDrawable().getSprite();
      float var4 = 0.0F;
      if(var2 == 0) {
         float var6;
         int var5 = (var6 = this.offsetState - this.distance) == 0.0F?0:(var6 < 0.0F?-1:1);
         var4 = 0.0F;
         if(var5 != 0) {
            this.offsetState = this.distance;
            var4 = this.distance;
         }
      }

      if(var2 == 1 && this.offsetState != 0.0F) {
         this.offsetState = 0.0F;
         var4 = -this.distance;
      }

      var3.setY(var4 + var3.getY());
   }
}
