package com.doodlegames.air.force.screen.screenActor;

import com.badlogic.gdx.utils.Array;
import com.doodlegames.air.force.screen.screenActor.GameEvent;

public class GameEventInterface {


   public interface EventHoldAsSubscriber {

      void addListener(GameEvent.GameEventListener var1);

      void removeListenerDelay(GameEvent.GameEventListener var1);
   }

   public interface EventHoldAsPublisher {

      void clearListeners();

      Array<GameEvent.GameEventListener> getListeners();

      void notifyListener(Object var1);
   }
}
