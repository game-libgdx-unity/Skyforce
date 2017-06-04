package com.doodlegames.air.force.screen.action;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

public class MySeqAction extends TemporalAction {

   private Array<TemporalAction> actions = new Array(4);
   private int index;


   public boolean act(float var1) {
      super.act(var1);
      if(this.index < this.actions.size && this.index >= 0) {
         if(!((TemporalAction)this.actions.get(this.index)).act(var1)) {
            return false;
         }

         byte var3;
         if(this.isReverse()) {
            var3 = -1;
         } else {
            var3 = 1;
         }

         this.index += var3;
         if(this.index < this.actions.size && this.index >= 0) {
            return false;
         }
      }

      return true;
   }

   public void addAction(TemporalAction var1) {
      if(var1.getDuration() > 0.0F) {
         this.actions.add(var1);
         this.setDuration(this.getDuration() + var1.getDuration());
      }

   }

   public Array<TemporalAction> getActions() {
      return this.actions;
   }

   protected void initialize() {
      if(this.isReverse()) {
         this.index = -1 + this.actions.size;
      } else {
         this.index = 0;
      }
   }

   public void restart() {
      super.restart();
      Iterator var1 = this.actions.iterator();

      while(var1.hasNext()) {
         ((TemporalAction)var1.next()).restart();
      }

      this.setDuration(0.0F);
   }

   public void setActor(Actor var1) {
      super.setActor(var1);
      Iterator var2 = this.actions.iterator();

      while(var2.hasNext()) {
         ((TemporalAction)var2.next()).setActor(var1);
      }

   }

   public void setDuration(float var1) {
      float var2 = 0.0F;

      for(Iterator var3 = this.actions.iterator(); var3.hasNext(); var2 += ((TemporalAction)var3.next()).getDuration()) {
         ;
      }

      super.setDuration(var2);
   }

   public void setReverse(boolean var1) {
      super.setReverse(var1);
      Iterator var2 = this.actions.iterator();

      while(var2.hasNext()) {
         ((TemporalAction)var2.next()).setReverse(var1);
      }

   }

   protected void update(float var1) {}
}
