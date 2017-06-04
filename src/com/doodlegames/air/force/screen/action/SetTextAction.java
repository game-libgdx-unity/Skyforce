package com.doodlegames.air.force.screen.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Pools;
import com.doodlegames.air.force.utils.Settings;

public class SetTextAction extends Action {

   private String str = "";


   public static Action setText(String var0) {
      SetTextAction var1 = (SetTextAction)Pools.obtain(SetTextAction.class);
      var1.str = var0;
      return var1;
   }

   public boolean act(float var1) {
      Actor var2 = super.getActor();
      if(var2 instanceof Label) {
         ((Label)var2).setText(this.str);
      } else if(var2 instanceof TextButton) {
         ((TextButton)var2).getLabel().setText(this.str);
      } else {
         Settings.i("Actor must be a label or a textButton. can not set text:" + this.str);
      }

      return true;
   }
}
