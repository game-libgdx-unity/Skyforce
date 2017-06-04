package com.doodlegames.air.force.screen.screenActor;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.doodlegames.air.force.screen.XScreen;
import com.doodlegames.air.force.screen.action.SlideSlowDownAction;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.DragListener;

public class AchiScreenButtonGroup extends Table {

   private static final float ITEMHEIGHT = 60.0F;
   private final int buttonNum;
   Group images;


   public AchiScreenButtonGroup(int var1, int var2, int var3, int var4, int var5, Skin var6) {
      this.buttonNum = var5;
      this.setClip(true);
      this.setBackground((Drawable)var6.get("blue", SpriteDrawable.class));
      this.setBounds((float)var1, (float)var2, (float)var3, (float)var4);
      this.images = new Group();

      for(int var7 = 0; var7 < var5; ++var7) {
         int var9 = var7 % XScreen.pureColorDrawable.length;
         Image var10 = new Image((Drawable)var6.get(XScreen.pureColorDrawable[var9], SpriteDrawable.class));
         var10.setBounds(0.0F, 60.0F * (float)var7, (float)var3, 60.0F);
         this.images.addActor(var10);
      }

      this.addActor(this.images);
      this.addListener(new DragListener() {

         private float[] deltaYArray = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
         private float[] logTimeArray = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
         private MoveToAction slideAction = new MoveToAction();
         private SlideSlowDownAction.SlideInterpolation slideInterpolation = new SlideSlowDownAction.SlideInterpolation();

         public void drag(InputEvent var1, float var2, float var3, int var4) {
            Group var5 = AchiScreenButtonGroup.this.images;
            AchiScreenButtonGroup var6 = AchiScreenButtonGroup.this;
            float var7 = -this.getDeltaY();
            float var8 = var5.getY();
            if(var7 > 0.0F) {
               if(var8 <= 0.0F) {
                  if(-var8 <= var7) {
                     var7 = -var8;
                  }
               } else {
                  var7 = 0.0F;
               }
            } else {
               float var9 = -60.0F * (float)var5.getChildren().size + var6.getHeight();
               if(var8 > var9) {
                  if(Math.abs(var8 - var9) <= Math.abs(var7)) {
                     var7 = -Math.abs(var8 - var9);
                  }
               } else {
                  var7 = 0.0F;
               }
            }

            var5.translate(0.0F, var7);
            int var10 = this.deltaYArray.length;
            int var11 = 0;

            for(int var12 = 1; var12 < var10; ++var12) {
               if(this.logTimeArray[var12] < this.logTimeArray[var11]) {
                  var11 = var12;
               }
            }

            this.logTimeArray[var11] = (float)Clock.getTimeCounter();
            this.deltaYArray[var11] = var7;
         }
         public void dragStart(InputEvent var1, float var2, float var3, int var4) {}
         public void dragStop(InputEvent var1, float var2, float var3, int var4) {
            int var5 = this.deltaYArray.length;
            float var6 = (float)Clock.getTimeCounter();
            float var8 = 0.0F;

            for(int var9 = 0; var9 < var5; ++var9) {
               if(this.logTimeArray[var9] != 0.0F) {
                  if(this.logTimeArray[var9] < var6) {
                     var6 = this.logTimeArray[var9];
                  }

                  var8 += this.deltaYArray[var9];
               }
            }

            this.slideInterpolation.initInterpolation(var8 / (var6 - var6));
            Group var10 = AchiScreenButtonGroup.this.images;
            var10.removeAction(this.slideAction);
            this.slideAction.restart();
            this.slideAction.setDuration(this.slideInterpolation.time);
            this.slideAction.setY(var10.getY() + this.slideInterpolation.distance);
            this.slideAction.setInterpolation(this.slideInterpolation);
            var10.addAction(this.slideAction);
         }
         public boolean touchDown(InputEvent var1, float var2, float var3, int var4, int var5) {
            AchiScreenButtonGroup.this.images.removeAction(this.slideAction);
            return super.touchDown(var1, var2, var3, var4, var5);
         }
      });
   }

   public void act(float var1) {
      super.act(var1);
      float var2 = -60.0F * (float)this.images.getChildren().size + this.getHeight();
      float var3 = this.images.getY();
      if(var3 > 0.0F) {
         this.images.setY(0.0F);
         this.images.clearActions();
      }

      if(var3 < var2) {
         this.images.setY(var2);
         this.images.clearActions();
      }

   }
}
