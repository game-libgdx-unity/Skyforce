package com.doodlegames.air.force.resource;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.doodlegames.air.force.screen.LoadingScreen;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Settings;

public class Assets_MainMenu {

   public static TextureAtlas atlas;
   public static LoadingScreen.LoadingInfo info = new LoadingScreen.LoadingInfo() {
      public void fillAssetManager(AssetManager var1) {
         var1.load("UI/mainMenu/mainmenuUI.atlas", TextureAtlas.class);
      }
      public Screen loadingFinished(Game var1, AssetManager var2) {
         try {
            Assets_MainMenu.atlas = (TextureAtlas)var2.get("UI/mainMenu/mainmenuUI.atlas", TextureAtlas.class);
            return null;
         } catch (Throwable var7) {
            Assets_MainMenu.atlas = null;
            Settings.w(var7.toString());
         } finally {
            Assets_MainMenu.load();
         }

         return null;
      }
   };
   private static final String pathString = "UI/mainMenu/mainmenuUI.atlas";
   public static Animation uiF22;
   public static Animation uiJ20;
   public static Animation uiMe262;
   public static Animation uiRafale;
   public static Animation uiSu47;
   public static Animation uiTyphoon;


   public static void load() {
      if(atlas == null) {
         Settings.w("Assets Load failed.");
         atlas = new TextureAtlas(Gdx.files.internal("UI/mainMenu/mainmenuUI.atlas"));
      }

      if(uiF22 == null) {
         TextureRegion[] var5 = new TextureRegion[]{atlas.findRegion("air_stand", 0), atlas.findRegion("air_stand", 1)};
         uiF22 = new Animation(0, 0.3F, var5);
      } else {
         uiF22.setKeyFrame(0, atlas.findRegion("air_stand", 0));
         uiF22.setKeyFrame(1, atlas.findRegion("air_stand", 1));
      }

      if(uiJ20 == null) {
         TextureRegion[] var4 = new TextureRegion[]{atlas.findRegion("j20_stand", 0), atlas.findRegion("j20_stand", 1)};
         uiJ20 = new Animation(0, 0.3F, var4);
      } else {
         uiJ20.setKeyFrame(0, atlas.findRegion("j20_stand", 0));
         uiJ20.setKeyFrame(1, atlas.findRegion("j20_stand", 1));
      }

      if(uiRafale == null) {
         TextureRegion[] var3 = new TextureRegion[]{atlas.findRegion("rafal_stand", 0), atlas.findRegion("rafal_stand", 1)};
         uiRafale = new Animation(0, 0.3F, var3);
      } else {
         uiRafale.setKeyFrame(0, atlas.findRegion("rafal_stand", 0));
         uiRafale.setKeyFrame(1, atlas.findRegion("rafal_stand", 1));
      }

      if(uiSu47 == null) {
         TextureRegion[] var2 = new TextureRegion[]{atlas.findRegion("su47_stand", 0), atlas.findRegion("su47_stand", 1)};
         uiSu47 = new Animation(0, 0.3F, var2);
      } else {
         uiSu47.setKeyFrame(0, atlas.findRegion("su47_stand", 0));
         uiSu47.setKeyFrame(1, atlas.findRegion("su47_stand", 1));
      }

      if(uiTyphoon == null) {
         TextureRegion[] var1 = new TextureRegion[]{atlas.findRegion("typhoon_stand", 0), atlas.findRegion("typhoon_stand", 1)};
         uiTyphoon = new Animation(0, 0.3F, var1);
      } else {
         uiTyphoon.setKeyFrame(0, atlas.findRegion("typhoon_stand", 0));
         uiTyphoon.setKeyFrame(1, atlas.findRegion("typhoon_stand", 1));
      }

      if(uiMe262 == null) {
         TextureRegion[] var0 = new TextureRegion[]{atlas.findRegion("me262_stand", 0), atlas.findRegion("me262_stand", 1)};
         uiMe262 = new Animation(0, 0.3F, var0);
      } else {
         uiMe262.setKeyFrame(0, atlas.findRegion("me262_stand", 0));
         uiMe262.setKeyFrame(1, atlas.findRegion("me262_stand", 1));
      }
   }
}
