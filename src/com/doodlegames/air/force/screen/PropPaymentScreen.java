package com.doodlegames.air.force.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.doodlegames.air.force.screen.LoadingScreen;
import com.doodlegames.air.force.screen.MainMenuScreen;
import com.doodlegames.air.force.screen.XScreen;
import com.doodlegames.air.force.screen.screenActor.ScrollList;

public class PropPaymentScreen extends XScreen {

   private static Skin propPaymentSkin;
   private ImageButton imageButton;
   public LoadingScreen.LoadingInfo loadingInfo = new LoadingScreen.LoadingInfo() {
      public void fillAssetManager(AssetManager var1) {}
      public Screen loadingFinished(Game var1, AssetManager var2) {
         return null;
      }
   };
   private ScrollList propList;


   public PropPaymentScreen(Game var1) {
      super(var1, getPropPaymentScreen());
      this.imageButton = new ImageButton(this.skin);
      this.imageButton.setBounds(420.0F, 720.0F, 50.0F, 50.0F);
      this.imageButton.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            Game var3 = PropPaymentScreen.this.game;
            var3.setScreen(new LoadingScreen(var3, MainMenuScreen.info, 0.0F, false));
         }
      });
      this.propList = new ScrollList(80.0F, 200.0F, 320.0F, 400.0F, 80.0F);
      this.propList.setBackground((Drawable)this.skin.get("white", SpriteDrawable.class));

      for(int var3 = 0; var3 < 88; ++var3) {
         int var4 = var3 % XScreen.pureColorDrawable.length;
         String var5 = XScreen.pureColorDrawable[var4];
         Image var6 = new Image((Drawable)this.skin.get(var5, SpriteDrawable.class));
         this.propList.addItem(var6);
      }

      this.screenStage.addActor(this.imageButton);
      this.screenStage.addActor(this.propList);
   }

   private static Skin getPropPaymentScreen() {
      return MainMenuScreen.getMainMenuScreenSkin();
   }
}
