package com.doodlegames.air.force.screen.screenActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Pools;
import com.doodlegames.air.force.utils.DragListener;
import com.doodlegames.air.force.utils.Settings;

public class MainScreenBackGround extends Group {

   private static final int MOVETHRESHOLD = 35;
   private int currentPage;
   private Image[] images;
   private InputProcessor inputProcessor;
   private boolean isMoving;
   private DragListener listener;
   private boolean listenerAdded;
   private final int pageNum;
   private MoveToAction springBackAction = new MoveToAction();


   public MainScreenBackGround(Image ... var1) {
      this.pageNum = var1.length;
      this.images = var1;
      this.isMoving = false;
      this.currentPage = 0;

      for(int var2 = 0; var2 < this.pageNum; ++var2) {
         this.images[var2].setWidth(480.0F);
         this.images[var2].setHeight(800.0F);
         this.images[var2].setX((float)(var2 * 480));
         this.images[var2].setY(0.0F);
         this.addActor(this.images[var2]);
      }

      this.listener = new DragListener() {

         float currentOffset;
         MainScreenBackGround wThis = MainScreenBackGround.this;

         public void drag(InputEvent var1, float var2, float var3, int var4) {
            if(!this.wThis.isMoving) {
               this.currentOffset += this.getDeltaX();
               this.wThis.translate(-this.getDeltaX(), 0.0F);
            }

         }
         public void dragStart(InputEvent var1, float var2, float var3, int var4) {
            this.currentOffset = 0.0F;
         }
         public void dragStop(InputEvent var1, float var2, float var3, int var4) {
            if(!this.wThis.isMoving) {
               this.wThis.enableMoving();
               if(Math.abs(this.currentOffset) > 35.0F) {
                  if(this.currentOffset > 0.0F && this.wThis.currentPage < -1 + MainScreenBackGround.this.pageNum) {
//                     MainScreenBackGround.access$208(this.wThis);
                  }

                  if(this.currentOffset < 0.0F && this.wThis.currentPage >= 1) {
//                     MainScreenBackGround.access$210(this.wThis);
                  }
               }

               this.wThis.MoveToCurrentPage();
               this.currentOffset = 0.0F;
            }

         }
      };
      this.listenerAdded = false;
   }

   private void MoveToCurrentPage() {
      MoveToAction var1 = this.springBackAction;
      if(var1 == null) {
         this.springBackAction = new MoveToAction();
         var1 = this.springBackAction;
         Settings.e("this.springBackAction is null!! sever error");
      }

      var1.setX((float)(480 * -this.currentPage));
      var1.setY(0.0F);
      var1.setDuration(0.4F);
      var1.setInterpolation(Interpolation.swingOut);
      this.addAction(var1);
      this.fire((ChangeListener.ChangeEvent)Pools.obtain(ChangeListener.ChangeEvent.class));
   }

   private void disableMoving() {
      if(this.isMoving) {
         this.isMoving = false;
         Gdx.input.setInputProcessor(this.inputProcessor);
      }

   }

   private void enableMoving() {
      if(!this.isMoving) {
         this.isMoving = true;
         this.inputProcessor = Gdx.input.getInputProcessor();
         Gdx.input.setInputProcessor((InputProcessor)null);
      }

   }

   public void act(float var1) {
      super.act(var1);
      if(!this.listenerAdded) {
         this.getStage().addListener(this.listener);
         this.listenerAdded = true;
      }

      if(this.getActions().size == 0 && this.isMoving) {
         this.disableMoving();
      }

   }

   public int getCurrentPage() {
      return this.currentPage;
   }

   public int shiftLeft() {
      if(!this.isMoving && this.currentPage >= 1) {
         this.enableMoving();
         this.currentPage += -1;
         this.MoveToCurrentPage();
      }

      return this.currentPage;
   }

   public int shiftRight() {
      if(!this.isMoving && this.currentPage <= 5) {
         this.enableMoving();
         ++this.currentPage;
         this.MoveToCurrentPage();
      }

      return this.currentPage;
   }
}
