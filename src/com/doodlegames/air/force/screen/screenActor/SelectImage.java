package com.doodlegames.air.force.screen.screenActor;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.doodlegames.air.force.resource.Assets_fighter;

import java.util.ArrayList;

public class SelectImage extends Image {

   private SelectImage.ChangeAction changeAction = new SelectImage.ChangeAction();
   private SelectImage.IndexChangeEvent changeEvent = new SelectImage.IndexChangeEvent();
   private ArrayList<Drawable> drawables = new ArrayList();


   public SelectImage(Drawable var1, Drawable ... var2) {
      super(var1);
      this.drawables.add(var1);
      int var4 = var2.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Drawable var7 = var2[var5];
         this.drawables.add(var7);
      }

      int var6 = Assets_fighter.fighterIndex;
      if(var6 >= 0 && var6 < this.drawables.size()) {
         this.setDrawable((Drawable)this.drawables.get(var6));
      }

   }

   private void chooseDrawable(int var1) {
      if(this.changeAction.goalIndex == -10) {
         this.changeAction.goalIndex = var1;
         this.addAction(Actions.sequence(Actions.fadeOut(0.2F), this.changeAction, Actions.fadeIn(0.2F)));
      }
   }

   private void setDrawable(int var1) {
      int var2 = this.drawables.size();
      if(var1 < 0) {
         var1 = var2 - 1;
      }

      if(var1 >= var2) {
         var1 = 0;
      }

      this.setDrawable((Drawable)this.drawables.get(var1));
      this.fire(this.changeEvent);
   }

   public void addDrawable(Drawable var1) {
      this.drawables.add(var1);
   }

   public void chooseLastDrawable() {
      Drawable var1 = this.getDrawable();
      this.chooseDrawable(-1 + this.drawables.indexOf(var1));
   }

   public void chooseNextDrawable() {
      Drawable var1 = this.getDrawable();
      this.chooseDrawable(1 + this.drawables.indexOf(var1));
   }

   public int getIndex() {
      return this.drawables.indexOf(this.getDrawable());
   }

   public class IndexChangeEvent extends Event {

   }

   private final class ChangeAction extends Action {

      private static final int COMPLETED = -10;
      private int goalIndex;


      private ChangeAction() {
         this.goalIndex = -10;
      }

      public boolean act(float var1) {
         if(this.goalIndex != -10) {
            SelectImage.this.setDrawable(this.goalIndex);
            this.goalIndex = -10;
         }

         return true;
      }
   }
}
