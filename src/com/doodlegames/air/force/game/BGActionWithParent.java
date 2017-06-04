package com.doodlegames.air.force.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.doodlegames.air.force.game.BGAction;

public class BGActionWithParent extends BGAction {

   float relativeX;
   float relativeY;
   float scaleRelativeX;
   float scaleRelativeY;


   public void initalize() {
      if(this.item.parent != null) {
         Sprite var1 = this.item.parent.getSpriteDrawable().getSprite();
         float var2 = var1.getScaleX();
         float var3 = var1.getScaleY();
         if(var2 != 1.0F || var3 != 1.0F) {
            Sprite var4 = this.item.getSpriteDrawable().getSprite();
            float var5 = var1.getOriginX();
            float var6 = var1.getOriginY();
            float var7 = var4.getOriginX() + this.relativeX;
            float var8 = var4.getOriginY() + this.relativeY;
            float var9 = var7 - var5;
            float var10 = var8 - var6;
            var4.setScale(var2, var3);
            this.scaleRelativeX = var5 + var9 * var2 - var4.getOriginX();
            this.scaleRelativeY = var6 + var10 * var3 - var4.getOriginY();
            return;
         }
      }

   }

   public void reset() {}

   public void setRelativePositionXY(float var1, float var2) {
      this.relativeX = var1;
      this.relativeY = var2;
      this.scaleRelativeX = var1;
      this.scaleRelativeY = var2;
   }

   public void update(float var1) {
      super.update(var1);
      float var2 = this.item.parent.getSpriteDrawable().getSprite().getX() + this.scaleRelativeX;
      float var3 = this.item.parent.getSpriteDrawable().getSprite().getY() + this.scaleRelativeY;
      this.item.getSpriteDrawable().getSprite().setX(var2);
      this.item.getSpriteDrawable().getSprite().setY(var3);
   }
}
