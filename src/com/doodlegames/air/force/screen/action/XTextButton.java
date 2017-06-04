package com.doodlegames.air.force.screen.action;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.doodlegames.air.force.screen.screenActor.GameEvent;
import com.doodlegames.air.force.screen.screenActor.GameEventInterface;

public class XTextButton extends TextButton {

   protected GameEvent actingEvent = new GameEvent(this);
   protected GameEvent.ActingEventArg eventArg = new GameEvent.ActingEventArg();


   public XTextButton(String var1, Skin var2) {
      super(var1, var2);
   }

   public XTextButton(String var1, Skin var2, String var3) {
      super(var1, var2, var3);
   }

   public XTextButton(String var1, TextButton.TextButtonStyle var2) {
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
