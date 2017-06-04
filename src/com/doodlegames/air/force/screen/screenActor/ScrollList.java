package com.doodlegames.air.force.screen.screenActor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.doodlegames.air.force.screen.action.SlideSlowDownAction;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.DragListener;

public class ScrollList extends Table {

   private boolean isRolling;
   private final float itemHeight;
   private Group root = new Group();


   public ScrollList(float var1, float var2, float var3, float var4, float var5) {
      this.setBounds(var1, var2, var3, var4);
      this.itemHeight = var5;
      this.setClip(true);
      this.addListener(new ScrollList.ScrollDragListener());
      this.addActor(this.root);
      this.isRolling = false;
   }

   public void act(float var1) {
      super.act(var1);
      float var2 = -this.itemHeight * (float)this.root.getChildren().size + this.getHeight();
      float var3 = this.root.getY();
      if(var2 >= 0.0F) {
         var2 = 0.0F;
      }

      if(var3 > 0.0F) {
         this.root.setY(0.0F);
         this.root.clearActions();
      }

      if(var3 < var2) {
         this.root.setY(var2);
         this.root.clearActions();
      }

   }

   public void addItem(Actor var1) {
      var1.setBounds(0.0F, (float)this.root.getChildren().size * this.itemHeight, this.getWidth(), this.itemHeight);
      this.root.addActor(var1);
   }

   public boolean isRolling() {
      return this.isRolling;
   }

   private class ScrollDragListener extends DragListener {

      private float[] deltaYArray;
      private float[] logTimeArray;
      private MoveToAction slideAction;
      private SlideSlowDownAction.SlideInterpolation slideInterpolation;


      private ScrollDragListener() {
         this.slideInterpolation = new SlideSlowDownAction.SlideInterpolation();
         this.slideAction = new MoveToAction();
         this.deltaYArray = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
         this.logTimeArray = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
      }

      public void drag(InputEvent var1, float var2, float var3, int var4) {
         Group var5 = ScrollList.this.root;
         ScrollList var6 = ScrollList.this;
         float var7 = -this.getDeltaY();
         float var8 = var5.getY();
         float var9 = -var6.itemHeight * (float)var5.getChildren().size + var6.getHeight();
         if(var9 >= 0.0F) {
            var9 = 0.0F;
         }

         if(var7 > 0.0F) {
            if(var8 <= 0.0F) {
               if(-var8 <= var7) {
                  var7 = -var8;
               }
            } else {
               var7 = 0.0F;
            }
         } else if(var8 > var9) {
            if(Math.abs(var8 - var9) <= Math.abs(var7)) {
               var7 = -Math.abs(var8 - var9);
            }
         } else {
            var7 = 0.0F;
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

      public void dragStart(InputEvent var1, float var2, float var3, int var4) {
         ScrollList.this.isRolling = true;
      }

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
         Group var10 = ScrollList.this.root;
         var10.removeAction(this.slideAction);
         this.slideAction.restart();
         this.slideAction.setDuration(this.slideInterpolation.time);
         this.slideAction.setY(var10.getY() + this.slideInterpolation.distance);
         this.slideAction.setInterpolation(this.slideInterpolation);
         var10.addAction(this.slideAction);
         ScrollList.this.isRolling = false;
      }

      public boolean touchDown(InputEvent var1, float var2, float var3, int var4, int var5) {
         ScrollList.this.root.removeAction(this.slideAction);
         return super.touchDown(var1, var2, var3, var4, var5);
      }
   }
}
