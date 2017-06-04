package com.doodlegames.air.force.screen.screenActor;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.doodlegames.air.force.utils.Animation;

public class AnimationActor extends Image {

   Animation animation;
   Float playedTime = Float.valueOf(0.0F);


   public static AnimationActor getAnimationActor(Animation var0) {
      if(var0 == null) {
         return null;
      } else {
         AnimationActor var1 = new AnimationActor();
         var1.setAnimatioin(var0);
         return var1;
      }
   }

   public void act(float var1) {
      super.act(var1);
      if(this.animation != null) {
         this.playedTime = Float.valueOf(var1 + this.playedTime.floatValue());
         if(this.animation.mode == 1 && this.animation.playedTime.floatValue() > this.animation.animationTime) {
            this.setDrawable((Drawable)null);
         }
      }

   }

   public void rePlay() {
      this.playedTime = Float.valueOf(0.0F);
      this.setDrawable(this.animation);
   }

   public void setAnimatioin(Animation var1) {
      if(var1 != null) {
         this.playedTime = Float.valueOf(0.0F);
         this.animation = var1;
         this.setDrawable(var1);
         this.rePlay();
         this.animation.setPlayedTime(this.playedTime);
      }

   }
}
