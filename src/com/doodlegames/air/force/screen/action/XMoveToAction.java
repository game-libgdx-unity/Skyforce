package com.doodlegames.air.force.screen.action;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class XMoveToAction extends MoveToAction {

   protected void initialize() {
      if(!this.isReverse()) {
//         super.initialize();
      }

   }

   protected void update(float var1) {
      Interpolation var2 = this.getInterpolation();
      float var3 = this.getTime() / this.getDuration();
      if(this.isReverse()) {
         var3 = 1.0F - var3;
      }

      if(var2 != null) {
         var3 = var2.apply(var3);
      }

      super.update(var3);
   }
}
