package com.doodlegames.air.force.screen.action;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.doodlegames.air.force.utils.Clock;

import java.util.Random;

public class XDelayAction extends DelayAction {

   private Random random = null;
   private float randomTime = 0.0F;
   private float randomTimeBase = 0.0F;


   public static XDelayAction randDelay(float var0, float var1) {
      XDelayAction var2 = (XDelayAction)Actions.action(XDelayAction.class);
      var2.setDuration(var0);
      var2.setRandomTime(var1);
      var2.setRandom(Clock.rand);
      return var2;
   }

   public Random getRandom() {
      return this.random;
   }

   public float getRandomTime() {
      return this.randomTime;
   }

   public float getRandomTimeBase() {
      return this.randomTimeBase;
   }

   public void restart() {
      if(this.random != null) {
         super.setDuration(this.randomTimeBase + this.randomTime * this.random.nextFloat());
      }

      super.restart();
   }

   public void setDuration(float var1) {
      super.setDuration(var1);
      this.randomTimeBase = var1;
   }

   public void setRandom(Random var1) {
      this.random = var1;
   }

   public void setRandomTime(float var1) {
      this.randomTime = var1;
   }

   public void setRandomTimeBase(float var1) {
      this.randomTimeBase = var1;
   }
}
