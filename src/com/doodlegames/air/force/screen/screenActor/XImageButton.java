package com.doodlegames.air.force.screen.screenActor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class XImageButton extends ImageButton {

   public XImageButton(ImageButton.ImageButtonStyle var1) {
      super(var1);
   }

   public void draw(SpriteBatch var1, float var2) {
      XImageButton.XImageButtonStyle var3 = (XImageButton.XImageButtonStyle)((XImageButton.XImageButtonStyle)this.getStyle());
      if(this.isChecked()) {
         var3.imageDown = var3.imageDownChecked;
      } else {
         var3.imageDown = var3.imageDownUnChecked;
      }

      super.draw(var1, var2);
   }

   public static class XImageButtonStyle extends ImageButton.ImageButtonStyle {

      public Drawable imageDownChecked;
      public Drawable imageDownUnChecked;


      public XImageButtonStyle(Drawable var1, Drawable var2, Drawable var3, Drawable var4) {
         super((Drawable)null, (Drawable)null, (Drawable)null, var1, var2, var4);
         this.imageDownChecked = var3;
         this.imageDownUnChecked = var2;
      }
   }
}
