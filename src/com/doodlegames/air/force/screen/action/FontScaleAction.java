package com.doodlegames.air.force.screen.action;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class FontScaleAction extends TemporalAction {

   public static FontScaleAction.InterOne interOne = new FontScaleAction.InterOne();
   public static Interpolation interZero = new Interpolation() {
      public float apply(float var1) {
         return 0.0F;
      }
   };
   private BitmapFont font;
   private Label label;


   public static FontScaleAction fontScale(float var0, boolean var1) {
      FontScaleAction var2 = (FontScaleAction)Actions.action(FontScaleAction.class);
      var2.setDuration(var0);
      var2.setReverse(var1);
      return var2;
   }

   public static FontScaleAction fontScale(float var0, boolean var1, Interpolation var2) {
      FontScaleAction var3 = (FontScaleAction)Actions.action(FontScaleAction.class);
      var3.setDuration(var0);
      var3.setReverse(var1);
      var3.setInterpolation(var2);
      return var3;
   }

   public static FontScaleAction fontScaleIn(float var0) {
      return fontScale(var0, false);
   }

   public static FontScaleAction fontScaleOut(float var0) {
      return fontScale(var0, true);
   }

   public static FontScaleAction setFontScale0() {
      return fontScale(0.0F, true);
   }

   public static FontScaleAction setFontScale0(float var0) {
      return fontScale(var0, true);
   }

   public static FontScaleAction setFontScale1() {
      return fontScale(0.0F, false, interOne);
   }

   public static FontScaleAction setFontScale1(float var0) {
      return fontScale(var0, false, interOne);
   }

   protected void initialize() {
      Actor var1 = this.getActor();
      if(var1 instanceof Label) {
         this.label = (Label)var1;
         this.font = this.label.getStyle().font;
      }

      if(var1 instanceof TextButton) {
         this.label = ((TextButton)var1).getLabel();
         this.font = this.label.getStyle().font;
      }

   }

   protected void update(float var1) {
      if(this.label != null) {
         if(var1 == 0.0F) {
            var1 = 0.01F;
         }

         this.font.setScale(1.0F, var1);
         this.label.layout();
         this.font.setScale(1.0F);
      }

   }

   public static class InterOne extends Interpolation {

      public float apply(float var1) {
         return 1.0F;
      }
   }
}
