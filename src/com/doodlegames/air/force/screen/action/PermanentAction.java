package com.doodlegames.air.force.screen.action;

import com.badlogic.gdx.scenes.scene2d.Action;

public abstract class PermanentAction extends Action {

   protected boolean completed;
   protected float time;


   public boolean act(float var1) {
      if(this.time == 0.0F) {
         this.initAction();
      }

      this.time += var1;
      if(!this.completed) {
         this.update(var1);
         if(!this.completed) {
            return false;
         }
      }

      return true;
   }

   protected abstract void initAction();

   protected abstract void update(float var1);
}
