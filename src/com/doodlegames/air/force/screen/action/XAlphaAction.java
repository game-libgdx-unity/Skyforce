package com.doodlegames.air.force.screen.action;

import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;

public class XAlphaAction extends AlphaAction {

   protected void initialize() {
      this.actor.getColor().a = 0.0F;
      this.setAlpha(1.0F);
//      super.initialize();
   }
}
