package com.doodlegames.air.force.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.doodlegames.air.force.game.BGAction;

public class BGActionTurning extends BGAction {

   private float angleSpeed = 0.0F;


   public void initalize() {}

   public void reset() {}

   public void setAngleSpeed(float var1) {
      this.angleSpeed = var1;
   }

   public void update(float var1) {
      super.update(var1);
      Sprite var2 = this.item.getSpriteDrawable().getSprite();
      var2.setRotation(var2.getRotation() + var1 * this.angleSpeed);
   }
}
