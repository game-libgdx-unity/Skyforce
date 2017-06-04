package com.doodlegames.air.force.screen.screenActor;

import com.badlogic.gdx.utils.Array;
import com.doodlegames.air.force.screen.screenActor.GameEventInterface;

public class GameEvent implements GameEventInterface.EventHoldAsPublisher, GameEventInterface.EventHoldAsSubscriber {

   protected Array<GameEvent.GameEventListener> eventListeners = new Array(4);
   private boolean isIterating;
   private Object owner;


   public GameEvent(Object var1) {
      this.owner = var1;
      this.isIterating = false;
   }

   public void addListener(GameEvent.GameEventListener var1) {
      if(var1 != null && !this.eventListeners.contains(var1, true)) {
         this.eventListeners.add(var1);
      }
   }

   public void clearListeners() {
      this.eventListeners.clear();
   }

   public Array<GameEvent.GameEventListener> getListeners() {
      return this.eventListeners;
   }

   public void notifyListener(Object var1) {
      int var2 = this.eventListeners.size;
      this.isIterating = true;

      for(int var3 = 0; var3 < var2; ++var3) {
         GameEvent.GameEventListener var6 = (GameEvent.GameEventListener)this.eventListeners.get(var3);
         if(var6 != null) {
            var6.handle(this.owner, var1);
         }
      }

      this.isIterating = false;

      for(int var4 = var2 - 1; var4 >= 0; --var4) {
         if(this.eventListeners.get(var4) == null) {
            this.eventListeners.removeIndex(var4);
         }
      }

   }

   public void removeListenerDelay(GameEvent.GameEventListener var1) {
      if(!this.isIterating) {
         this.eventListeners.removeValue(var1, true);
      } else {
         int var2 = this.eventListeners.indexOf(var1, true);
         if(var2 >= 0 && var2 < this.eventListeners.size) {
            this.eventListeners.set(var2, (GameEventListener)null);
            return;
         }
      }

   }

   public static class ActingEventArg {

      float deltaTime;


      public float getdeltaTime() {
         return this.deltaTime;
      }

      public void setDeltaTime(float var1) {
         this.deltaTime = var1;
      }
   }

   public interface GameEventListener {

      void handle(Object var1, Object var2);
   }
}
