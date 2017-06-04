package com.doodlegames.air.force.screen;

import com.badlogic.gdx.InputAdapter;
import com.doodlegames.air.force.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.doodlegames.air.force.utils.Clock;

public class BackProcessor extends InputAdapter {

   private static BackProcessor.BackFunction backFun = null;
   private static BackProcessor backProcessor;
   public static BackProcessor.BackFunction emptyBackFun = new BackProcessor.BackFunction() {
      public void backPressed() {}
   };
   private static InputMultiplexer inputMultiplexer;
   float lastPressTimeStamp = -10.0F;


   public static InputMultiplexer getProcessor(InputProcessor var0) {
      if(backProcessor == null) {
         backProcessor = new BackProcessor();
      }

      if(inputMultiplexer == null) {
         inputMultiplexer = new InputMultiplexer();
      } else {
         inputMultiplexer.clear();
      }

      inputMultiplexer.addProcessor(var0);
      inputMultiplexer.addProcessor(backProcessor);
      return inputMultiplexer;
   }

   public static void setBackFuntion(BackProcessor.BackFunction var0) {
      backFun = var0;
   }

   public static void setEmptyBackFun() {
      setBackFuntion(emptyBackFun);
   }

   public boolean keyDown(int var1) {
      if(Clock.getCurrentTime(this.lastPressTimeStamp) < 0.2F) {
         return false;
      } else {
         this.lastPressTimeStamp = Clock.getCurrentTime();
         if(var1 == 4) {
            if(backFun == null) {
               System.exit(0);
            } else {
               backFun.backPressed();
            }
         }

         return true;
      }
   }

   public interface BackFunction {

      void backPressed();
   }
}
