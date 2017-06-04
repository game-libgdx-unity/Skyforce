package com.doodlegames.air.force.utils;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class DragListener extends InputListener {

   private int button;
   private float deltaX;
   private float deltaY;
   private boolean dragging;
   private int pressedPointer = -1;
   private float tapSquareSize = 14.0F;
   private float touchDownX = -1.0F;
   private float touchDownY = -1.0F;


   public void drag(InputEvent var1, float var2, float var3, int var4) {}

   public void dragStart(InputEvent var1, float var2, float var3, int var4) {}

   public void dragStop(InputEvent var1, float var2, float var3, int var4) {}

   public int getButton() {
      return this.button;
   }

   public float getDeltaX() {
      return this.deltaX;
   }

   public float getDeltaY() {
      return this.deltaY;
   }

   public float getTapSquareSize() {
      return this.tapSquareSize;
   }

   public float getTouchDownX() {
      return this.touchDownX;
   }

   public float getTouchDownY() {
      return this.touchDownY;
   }

   public boolean isDragging() {
      return this.dragging;
   }

   public void setButton(int var1) {
      this.button = var1;
   }

   public void setTapSquareSize(float var1) {
      this.tapSquareSize = var1;
   }

   public boolean touchDown(InputEvent var1, float var2, float var3, int var4, int var5) {
      if(this.pressedPointer == -1 && (var4 != 0 || this.button == -1 || var5 == this.button)) {
         this.pressedPointer = var4;
         this.touchDownX = var2;
         this.touchDownY = var3;
         return true;
      } else {
         return false;
      }
   }

   public void touchDragged(InputEvent var1, float var2, float var3, int var4) {
      if(var4 == this.pressedPointer) {
         if(!this.dragging && (Math.abs(this.touchDownX - var2) > this.tapSquareSize || Math.abs(this.touchDownY - var3) > this.tapSquareSize)) {
            this.dragging = true;
            this.dragStart(var1, var2, var3, var4);
            this.deltaX = var2;
            this.deltaY = var3;
         }

         if(this.dragging) {
            this.deltaX -= var2;
            this.deltaY -= var3;
            this.drag(var1, var2, var3, var4);
            this.deltaX = var2;
            this.deltaY = var3;
            return;
         }
      }

   }

   public void touchUp(InputEvent var1, float var2, float var3, int var4, int var5) {
      if(var4 == this.pressedPointer) {
         if(this.dragging) {
            this.dragStop(var1, var2, var3, var4);
         }

         this.dragging = false;
         this.pressedPointer = -1;
      }

   }
}
