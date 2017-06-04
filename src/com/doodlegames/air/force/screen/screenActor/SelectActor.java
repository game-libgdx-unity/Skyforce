package com.doodlegames.air.force.screen.screenActor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.doodlegames.air.force.screen.screenActor.GameEvent;
import com.doodlegames.air.force.screen.screenActor.GameEventInterface;
import com.doodlegames.air.force.utils.Settings;

public class SelectActor {

   private Actor[] actors;
   private GameEvent chooseChangeEvent;
   private SelectActor.ChangeEventArg eventArg;
   private int index;
   private boolean loopChoose;
   private final Group parent;


   public SelectActor(Group var1, Actor ... var2) {
      this.parent = var1;
      this.actors = new Actor[var2.length];
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         this.actors[var4] = var2[var4];
      }

      this.loopChoose = false;
      if(var2.length >= 1) {
         this.index = 0;
      }

      this.chooseChangeEvent = new GameEvent(this);
      this.eventArg = new SelectActor.ChangeEventArg();
   }

   public boolean chooseLastActor() {
      if(this.actors != null && this.actors.length != 0 && (this.loopChoose || this.index > 0)) {
         int var1 = this.index;
         int var2 = -1 + this.index;
         if(this.loopChoose && var2 < 0) {
            var2 += this.actors.length;
         }

         Actor var3 = this.getActor(var1);
         Actor var4 = this.getActor(var2);
         if(var3.getActions().size == 0) {
            var4.setPosition(var3.getX(), var3.getY());
            if(!this.parent.getChildren().contains(var3, true)) {
               Settings.e("SelectActor error, parent does not contain currrentActor.");
               return false;
            }

            this.parent.addActorBefore(var3, var4);
            this.parent.removeActor(var3);
            this.index = var2;
            this.eventArg.set(this.index, var1);
            this.chooseChangeEvent.notifyListener(this.eventArg);
            return true;
         }
      }

      return false;
   }

   public boolean chooseNextActor() {
      if(this.actors != null && this.actors.length != 0 && (this.loopChoose || this.index < -1 + this.actors.length)) {
         int var1 = this.index;
         int var2 = 1 + this.index;
         if(this.loopChoose && var2 > -1 + this.actors.length) {
            var2 -= this.actors.length;
         }

         Actor var3 = this.getActor(var1);
         Actor var4 = this.getActor(var2);
         if(var3.getActions().size == 0) {
            var4.setPosition(var3.getX(), var3.getY());
            if(!this.parent.getChildren().contains(var3, true)) {
               Settings.e("SelectActor error, parent does not contain currrentActor.");
               return false;
            }

            this.parent.addActorBefore(var3, var4);
            this.parent.removeActor(var3);
            this.index = var2;
            this.eventArg.set(this.index, var1);
            this.chooseChangeEvent.notifyListener(this.eventArg);
            return true;
         }
      }

      return false;
   }

   public GameEventInterface.EventHoldAsSubscriber getActingEvent() {
      return this.chooseChangeEvent;
   }

   public Actor getActor(int var1) {
      return var1 >= 0 && var1 < this.actors.length?this.actors[var1]:null;
   }

   public Actor getCurrentActor() {
      return this.getActor(this.index);
   }

   public class ChangeEventArg {

      public int choose;
      public int lastChoose;
      public int size;


      public Actor getCurrentActor() {
         return SelectActor.this.getActor(this.choose);
      }

      public Actor getLastActor() {
         return SelectActor.this.getActor(this.lastChoose);
      }

      public SelectActor.ChangeEventArg set(int var1, int var2) {
         this.size = SelectActor.this.actors.length;
         this.choose = var1;
         this.lastChoose = var2;
         return this;
      }
   }
}
