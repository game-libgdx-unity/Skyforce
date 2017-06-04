package com.doodlegames.air.force.resource;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.doodlegames.air.force.game.BGfixer;
import com.doodlegames.air.force.game.BigBackGround;
import com.doodlegames.air.force.game.SmallBackground;
import com.doodlegames.air.force.screen.LoadingScreen;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

public class Assets_UI {

   public static TextureAtlas.AtlasRegion arrowButtonNormalLeft;
   private static TextureAtlas.AtlasRegion arrowButtonNormalRight;
   public static TextureAtlas.AtlasRegion arrowButtonPressLeft;
   private static TextureAtlas.AtlasRegion arrowButtonPressRight;
   public static Animation asteroidAnimation;
   public static TextureAtlas atlas;
   public static Sound backSound;
   public static final String backSoundPath = "audio/sound/backButton.ogg";
   private static final String[] bgAssetsPath = new String[]{"bg/lv_1/bg_level1.atlas", "bg/lv_2/bg_level2.atlas", "bg/lv_3/bg_level3.atlas", "bg/lv_4/bg_level4.atlas", "bg/lv_5/bg_level5.atlas", "bg/lv_6/bg_level6.atlas", "bg/lv_7/bg_level7.atlas"};
   public static TextureAtlas bgAtlas;
   private static int bgLevel = 1;
   public static Music bgMusic;
   public static final String bgMusicPath = "audio/music/menu.ogg";
   public static BigBackGround bigBackGround;
   public static Sound buttonSound;
   public static Animation earthAnimation;
   public static LoadingScreen.LoadingInfo info = new LoadingScreen.LoadingInfo() {
      public void fillAssetManager(AssetManager var1) {
         var1.load("UI/UI.atlas", TextureAtlas.class);
         int var2 = 1 + Settings.levelPassed;
         if(var2 > 7) {
            var2 = 7;
         }

         if(var2 < 1) {
            var2 = 1;
         }

         Assets_UI.bgLevel = 1 + Clock.rand.nextInt(var2);
         var1.load(Assets_UI.bgAssetsPath[-1 + Assets_UI.bgLevel], TextureAtlas.class);
         var1.load("audio/music/menu.ogg", Music.class);
         var1.load("audio/sound/backButton.ogg", Sound.class);
         var1.load("audio/sound/normalButton.ogg", Sound.class);
      }
      public Screen loadingFinished(Game var1, AssetManager var2) {
         try {
            Assets_UI.atlas = (TextureAtlas)var2.get("UI/UI.atlas", TextureAtlas.class);
            Assets_UI.bgAtlas = (TextureAtlas)var2.get(Assets_UI.bgAssetsPath[-1 + Assets_UI.bgLevel], TextureAtlas.class);
            Assets_UI.bgMusic = (Music)var2.get("audio/music/menu.ogg", Music.class);
            Assets_UI.backSound = (Sound)var2.get("audio/sound/backButton.ogg", Sound.class);
            Assets_UI.buttonSound = (Sound)var2.get("audio/sound/normalButton.ogg", Sound.class);
            return null;
         } catch (Throwable var7) {
            Assets_UI.atlas = null;
            Settings.w(var7.toString());
         } finally {
            Assets_UI.load();
         }

         return null;
      }
   };
   public static Animation marAnimation;
   public static Animation moonAnimation;
   public static final String normalSoundPath = "audio/sound/normalButton.ogg";
   private static final String pathString = "UI/UI.atlas";
   public static SmallBackground smallBackGround;
   public static Animation uiF22;
   public static Animation uiJ20;
   public static Animation uiMe262;
   public static Animation uiRafale;
   public static Animation uiSu47;
   public static Animation uiTyphoon;


   public static int getBgLevel() {
      return bgLevel;
   }

   public static void load() {
      if(atlas == null) {
         Settings.w("Assets Load failed.");
         atlas = new TextureAtlas(Gdx.files.internal("UI/UI.atlas"));
      }

      if(bgAtlas == null) {
         Settings.w("Assets Load failed.");
         bgAtlas = new TextureAtlas(Gdx.files.internal(bgAssetsPath[-1 + bgLevel]));
      }

      BGfixer.LevelActionAdder.levelActionAdder(bgLevel);
      smallBackGround = SmallBackground.getUIBackGround(bgAtlas, bgLevel);
      bigBackGround = BigBackGround.getUIBigBackGround(bgLevel, bgAtlas);
      Animation var0 = uiF22;
      TextureRegion[] var1 = new TextureRegion[]{atlas.findRegion("air_stand", 0), atlas.findRegion("air_stand", 1)};
      uiF22 = Animation.setAnimationFrame(var0, 0, 0.05F, var1);
      Animation var2 = uiJ20;
      TextureRegion[] var3 = new TextureRegion[]{atlas.findRegion("j20_stand", 0), atlas.findRegion("j20_stand", 1)};
      uiJ20 = Animation.setAnimationFrame(var2, 0, 0.05F, var3);
      Animation var4 = uiRafale;
      TextureRegion[] var5 = new TextureRegion[]{atlas.findRegion("rafal_stand", 0), atlas.findRegion("rafal_stand", 1)};
      uiRafale = Animation.setAnimationFrame(var4, 0, 0.05F, var5);
      Animation var6 = uiSu47;
      TextureRegion[] var7 = new TextureRegion[]{atlas.findRegion("su47_stand", 0), atlas.findRegion("su47_stand", 1)};
      uiSu47 = Animation.setAnimationFrame(var6, 0, 0.05F, var7);
      Animation var8 = uiTyphoon;
      TextureRegion[] var9 = new TextureRegion[]{atlas.findRegion("typhoon_stand", 0), atlas.findRegion("typhoon_stand", 1)};
      uiTyphoon = Animation.setAnimationFrame(var8, 0, 0.05F, var9);
      Animation var10 = uiMe262;
      TextureRegion[] var11 = new TextureRegion[]{atlas.findRegion("me262_stand", 0), atlas.findRegion("me262_stand", 1)};
      uiMe262 = Animation.setAnimationFrame(var10, 0, 0.05F, var11);
      arrowButtonNormalRight = atlas.findRegion("arrow_button_normal");
      arrowButtonPressRight = atlas.findRegion("arrow_button_press");
      arrowButtonNormalLeft = new TextureAtlas.AtlasRegion(arrowButtonNormalRight);
      boolean var12 = arrowButtonNormalRight.rotate;
      TextureAtlas.AtlasRegion var13 = arrowButtonNormalLeft;
      boolean var14;
      if(!var12) {
         var14 = true;
      } else {
         var14 = false;
      }

      var13.flip(var14, var12);
      arrowButtonNormalLeft.offsetX = arrowButtonNormalRight.offsetX;
      arrowButtonNormalLeft.offsetY = arrowButtonNormalRight.offsetY;
      arrowButtonPressLeft = new TextureAtlas.AtlasRegion(arrowButtonPressRight);
      boolean var15 = arrowButtonPressRight.rotate;
      TextureAtlas.AtlasRegion var16 = arrowButtonPressLeft;
      boolean var17;
      if(!var15) {
         var17 = true;
      } else {
         var17 = false;
      }

      var16.flip(var17, var15);
      arrowButtonPressLeft.offsetX = arrowButtonNormalRight.offsetX;
      arrowButtonPressLeft.offsetY = arrowButtonNormalRight.offsetY;
      Animation var18 = earthAnimation;
      TextureRegion[] var19 = new TextureRegion[]{atlas.findRegion("earth", 0), atlas.findRegion("earth", 1), atlas.findRegion("earth", 2), atlas.findRegion("earth", 3), atlas.findRegion("earth", 4), atlas.findRegion("earth", 5), atlas.findRegion("earth", 6), atlas.findRegion("earth", 7)};
      earthAnimation = Animation.setAnimationFrame(var18, 0, 0.125F, var19);
      Animation var20 = moonAnimation;
      TextureRegion[] var21 = new TextureRegion[]{atlas.findRegion("moon", 0), atlas.findRegion("moon", 1), atlas.findRegion("moon", 2), atlas.findRegion("moon", 3), atlas.findRegion("moon", 4), atlas.findRegion("moon", 5), atlas.findRegion("moon", 6), atlas.findRegion("moon", 7), atlas.findRegion("moon", 8)};
      moonAnimation = Animation.setAnimationFrame(var20, 0, 0.125F, var21);
      Animation var22 = marAnimation;
      TextureRegion[] var23 = new TextureRegion[]{atlas.findRegion("mars", 0), atlas.findRegion("mars", 1), atlas.findRegion("mars", 2), atlas.findRegion("mars", 3), atlas.findRegion("mars", 4), atlas.findRegion("mars", 5)};
      marAnimation = Animation.setAnimationFrame(var22, 0, 0.125F, var23);
      Animation var24 = asteroidAnimation;
      TextureRegion[] var25 = new TextureRegion[]{atlas.findRegion("asteroid", 0), atlas.findRegion("asteroid", 1), atlas.findRegion("asteroid", 2), atlas.findRegion("asteroid", 3), atlas.findRegion("asteroid", 4), atlas.findRegion("asteroid", 5), atlas.findRegion("asteroid", 6), atlas.findRegion("asteroid", 7), atlas.findRegion("asteroid", 8), atlas.findRegion("asteroid", 9)};
      asteroidAnimation = Animation.setAnimationFrame(var24, 0, 0.125F, var25);
   }
}
