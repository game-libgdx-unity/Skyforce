package com.doodlegames.air.force.screen.action;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class ArrowMoveAction extends MoveToAction {

   public static ArrowMoveAction rangeMove(float var0, float var1, float var2) {
      ArrowMoveAction var3 = (ArrowMoveAction)Actions.action(ArrowMoveAction.class);
      var3.setPosition(var0, var1);
      var3.setDuration(var2);
      return var3;
   }

   public static ArrowMoveAction rangeMove(float var0, float var1, float var2, Interpolation var3) {
      ArrowMoveAction var4 = (ArrowMoveAction)Actions.action(ArrowMoveAction.class);
      var4.setPosition(var0, var1);
      var4.setDuration(var2);
      var4.setInterpolation(var3);
      return var4;
   }

   protected void finalize() {
      boolean var1;
      if(!this.isReverse()) {
         var1 = true;
      } else {
         var1 = false;
      }

      this.setReverse(var1);
      this.restart();
   }

   protected void initialize() {
      if(!this.isReverse()) {
//         super.initialize();
      }

   }

   protected void update(float var1) {
      super.update(var1);
      if(this.getTime() >= this.getDuration()) {
         this.finalize();
      }

   }
}
