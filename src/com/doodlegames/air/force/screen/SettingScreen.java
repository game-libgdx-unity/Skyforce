package com.doodlegames.air.force.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.doodlegames.air.force.screen.LoadingScreen;
import com.doodlegames.air.force.screen.MainMenuScreen;
import com.doodlegames.air.force.screen.XScreen;
import com.doodlegames.air.force.utils.Settings;

public class SettingScreen extends XScreen {

   private static Skin settingsScreenSkin;
   private ImageButton buttonBackToMain;
   public LoadingScreen.LoadingInfo loadingInfo = new LoadingScreen.LoadingInfo() {
      public void fillAssetManager(AssetManager var1) {}
      public Screen loadingFinished(Game var1, AssetManager var2) {
         return null;
      }
   };
   private CheckBox musicSwitch;
   private CheckBox soundSwitch;


   public SettingScreen(Game var1) {
      super(var1, getSettingScreenSkin());
      this.buttonBackToMain = new ImageButton(this.skin);
      this.buttonBackToMain.setBounds(420.0F, 720.0F, 50.0F, 50.0F);
      this.buttonBackToMain.addListener(new SettingScreen.BackButtonListener());
      this.musicSwitch = new CheckBox("  music", this.skin);
      this.musicSwitch.setBounds(200.0F, 360.0F, 100.0F, 35.0F);
      this.soundSwitch = new CheckBox("  sound", this.skin);
      this.soundSwitch.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            Settings.changeSoundEnable();
         }
      });
      this.soundSwitch.setBounds(200.0F, 400.0F, 100.0F, 35.0F);
      this.musicSwitch.setChecked(false);
      this.soundSwitch.setChecked(true);
      this.screenStage.addActor(this.musicSwitch);
      this.screenStage.addActor(this.soundSwitch);
      this.screenStage.addActor(this.buttonBackToMain);
   }

   private static Skin getSettingScreenSkin() {
      if(settingsScreenSkin != null) {
         return settingsScreenSkin;
      } else {
         Skin var0 = new Skin();
         var0.add("white", XScreen.getTexture());
         var0.add("default", XScreen.getBitmapFont48());
         var0.add("gray", var0.newDrawable("white", Color.DARK_GRAY));
         var0.add("red", var0.newDrawable("white", Color.RED));
         var0.add("green", var0.newDrawable("white", Color.GREEN));
         var0.add("yellow", var0.newDrawable("white", Color.YELLOW));
         var0.add("blue", var0.newDrawable("white", Color.BLUE));
         var0.add("black", var0.newDrawable("white", Color.BLACK));
         var0.add("white", var0.newDrawable("white", Color.WHITE));
         var0.add("cyan", var0.newDrawable("white", Color.CYAN));
         CheckBox.CheckBoxStyle var1 = new CheckBox.CheckBoxStyle();
         var1.checkboxOn = (Drawable)var0.get("green", SpriteDrawable.class);
         var1.checkboxOff = (Drawable)var0.get("blue", SpriteDrawable.class);
         var1.up = (Drawable)var0.get("white", SpriteDrawable.class);
         var1.down = (Drawable)var0.get("white", SpriteDrawable.class);
         var1.font = var0.getFont("default");
         var1.fontColor = Color.RED;
         ImageButton.ImageButtonStyle var2 = new ImageButton.ImageButtonStyle();
         var2.up = (Drawable)var0.get("white", SpriteDrawable.class);
         var2.down = (Drawable)var0.get("black", SpriteDrawable.class);
         var2.disabled = (Drawable)var0.get("white", SpriteDrawable.class);
         var2.imageDown = (Drawable)var0.get("green", SpriteDrawable.class);
         var2.imageUp = (Drawable)var0.get("cyan", SpriteDrawable.class);
         var0.add("default", var2);
         var0.add("default", var1);
         settingsScreenSkin = var0;
         return settingsScreenSkin;
      }
   }

   private class BackButtonListener extends ChangeListener {

      SettingScreen wrapThis;


      private BackButtonListener() {
         this.wrapThis = SettingScreen.this;
      }

      public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
         Game var3 = this.wrapThis.game;
         var3.setScreen(new LoadingScreen(var3, MainMenuScreen.info, 0.0F, false));
      }
   }
}
