package com.doodlegames.air.force.screen.action;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.doodlegames.air.force.screen.screenActor.GameEvent;
import com.doodlegames.air.force.screen.screenActor.GameEventInterface;

public class XImageButton extends ImageButton {

   protected GameEvent actingEvent = new GameEvent(this);
   protected GameEvent.ActingEventArg eventArg = new GameEvent.ActingEventArg();


   public XImageButton(Skin var1) {
      super(var1);
   }

   public XImageButton(Skin var1, String var2) {
      super(var1, var2);
   }

   public void act(float var1) {
      super.act(var1);
      this.eventArg.setDeltaTime(var1);
      this.actingEvent.notifyListener(this.eventArg);
   }

   public GameEventInterface.EventHoldAsSubscriber getActingEvent() {
      return this.actingEvent;
   }
}
