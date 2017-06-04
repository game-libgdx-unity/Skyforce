package com.doodlegames.air.force.screen.action;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.doodlegames.air.force.utils.Settings;

public class SlideSlowDownAction extends MoveToAction {


   public static class SlideInterpolation extends Interpolation {

      private static float ACCEL = 2000.0F;
      public float distance;
      private boolean hasInit = false;
      public float iniVel;
      public float time;


      public float apply(float var1) {
         if(!this.hasInit) {
            Settings.e("SlideInterpolation initError");
            return var1;
         } else {
            float var2 = var1 * this.time;
            float var3 = this.iniVel;
            float var4 = var2 * ACCEL;
            byte var5;
            if(this.iniVel > 0.0F) {
               var5 = -1;
            } else {
               var5 = 1;
            }

            return var2 * (var3 + var4 * (float)var5 + this.iniVel) / 2.0F / this.distance;
         }
      }

      public void initInterpolation(float var1) {
         this.iniVel = var1;
         this.time = Math.abs(this.iniVel) / ACCEL;
         this.distance = this.time * this.iniVel / 2.0F;
         this.hasInit = true;
      }
   }
}
