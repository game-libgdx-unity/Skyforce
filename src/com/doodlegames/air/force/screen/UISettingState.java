package com.doodlegames.air.force.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.doodlegames.air.force.screen.action.FontScaleAction;
import com.doodlegames.air.force.utils.Settings;

public class UISettingState extends UIScreenState {

   ImageButton backButton;
   TextButton cancelButton;
   TextButton.TextButtonStyle grayStyle;
   TextButton.TextButtonStyle greenStyle;
   ImageButton infoBackButton;
   Image infoImage;
   TextButton musicButton;
   TextButton okayButton;
   TextButton resetButton;
   TextButton soundButton;


   public UISettingState(UIScreen var1) {
      super(var1);
      this.greenStyle = (TextButton.TextButtonStyle)this.skin.get("settingButtonStyle", TextButton.TextButtonStyle.class);
      this.grayStyle = (TextButton.TextButtonStyle)this.skin.get("grayButtonStyle", TextButton.TextButtonStyle.class);
      this.infoImage = new Image(this.skin.getDrawable("reset"));
      this.infoImage.setPosition(73.0F, -160.0F);
      this.okayButton = new TextButton("okay", this.greenStyle);
      this.okayButton.setPosition(127.0F, -248.0F);
      this.okayButton.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            if(UISettingState.this.okayButton.getTouchable() == Touchable.enabled) {
               UISettingState.this.screen.playButtonSound();
               Settings.resetLap();
               UISettingState.this.backToSettings();
            }
         }
      });
      this.cancelButton = new TextButton("cancel", this.greenStyle);
      this.cancelButton.setPosition(127.0F, -334.0F);
      this.cancelButton.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            if(UISettingState.this.cancelButton.getTouchable() == Touchable.enabled) {
               UISettingState.this.screen.playButtonSound();
               UISettingState.this.backToSettings();
            }
         }
      });
      this.infoBackButton = new ImageButton(this.skin, "SettingBackTo");
      this.infoBackButton.getColor().a = 0.0F;
      this.infoBackButton.setTouchable(Touchable.disabled);
      this.infoBackButton.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            if(UISettingState.this.infoBackButton.getTouchable() == Touchable.enabled) {
               UISettingState.this.screen.playBackSound();
               UISettingState.this.backToSettings();
            }
         }
      });
      this.resetButton = new TextButton("reset", this.skin, "settingButtonStyle");
      this.resetButton.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            UISettingState.this.screen.playButtonSound();
            UISettingState.this.showCheckInfo();
         }
      });
      this.resetButton.setTouchable(Touchable.disabled);
      String var6;
      if(Settings.getMusicEnable()) {
         var6 = "settingButtonStyle";
      } else {
         var6 = "grayButtonStyle";
      }

      String var7;
      if(Settings.getMusicEnable()) {
         var7 = "MUSIC ON";
      } else {
         var7 = "MUSIC OFF";
      }

      this.musicButton = new TextButton(var7, this.skin, var6);
      this.musicButton.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            UISettingState.this.screen.playButtonSound();
            Settings.changeMusicEnable();
            String var3;
            if(Settings.getMusicEnable()) {
               var3 = "MUSIC ON";
            } else {
               var3 = "MUSIC OFF";
            }

            ((TextButton)var2).setText(var3);
            TextButton.TextButtonStyle var4;
            if(Settings.getMusicEnable()) {
               var4 = UISettingState.this.greenStyle;
            } else {
               var4 = UISettingState.this.grayStyle;
            }

            UISettingState.this.musicButton.setStyle(var4);
            UISettingState.this.musicButton.getLabel().setColor(var4.fontColor);
         }
      });
      this.musicButton.setTouchable(Touchable.disabled);
      String var9;
      if(Settings.getSoundEnable()) {
         var9 = "settingButtonStyle";
      } else {
         var9 = "grayButtonStyle";
      }

      String var10;
      if(Settings.getSoundEnable()) {
         var10 = "SOUND ON";
      } else {
         var10 = "SOUND OFF";
      }

      this.soundButton = new TextButton(var10, this.skin, var9);
      this.soundButton.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            UISettingState.this.screen.playButtonSound();
            Settings.changeSoundEnable();
            String var3;
            if(Settings.getSoundEnable()) {
               var3 = "SOUND ON";
            } else {
               var3 = "SOUND OFF";
            }

            ((TextButton)var2).setText(var3);
            TextButton.TextButtonStyle var4;
            if(Settings.getSoundEnable()) {
               var4 = UISettingState.this.greenStyle;
            } else {
               var4 = UISettingState.this.grayStyle;
            }

            UISettingState.this.soundButton.setStyle(var4);
            UISettingState.this.soundButton.getLabel().setColor(var4.fontColor);
         }
      });
      this.soundButton.setTouchable(Touchable.disabled);
      this.backButton = new ImageButton(this.skin, "SettingBackTo");
      this.backButton.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            if(UISettingState.this.backButton.getTouchable() == Touchable.enabled) {
               UISettingState.this.screen.playBackSound();
               UISettingState.this.backToMain();
            }
         }
      });
      this.backButton.getColor().a = 0.0F;
      this.backButton.setTouchable(Touchable.disabled);
   }

   private void addMoveInAction(TextButton var1, float var2, float var3, float var4, float var5) {
      var1.addAction(Actions.sequence(FontScaleAction.fontScaleOut(0.0F), Actions.delay(var2), Actions.moveTo(var3, var4, var5, Interpolation.pow2Out), FontScaleAction.fontScaleIn(0.16666667F), Actions.touchable(Touchable.enabled)));
   }

   private void addMoveOutAction(TextButton var1, float var2, float var3, float var4, float var5) {
      var1.addAction(Actions.sequence(Actions.touchable(Touchable.disabled), Actions.delay(var2), FontScaleAction.fontScaleOut(0.16666667F), Actions.moveTo(var3, var4, var5, Interpolation.pow2Out)));
   }

   public static void addResource(Skin var0) {
      TextButton.TextButtonStyle var1 = new TextButton.TextButtonStyle();
      var1.font = XScreen.getBitmapFont36();
      var1.down = var0.getDrawable("green_button");
      var1.up = var0.getDrawable("green_button");
      var1.downFontColor = UIMainMenuState.MainConst.WHITEFONT;
      var1.fontColor = UIMainMenuState.MainConst.GREENFONT;
      var0.add("settingButtonStyle", var1);
      TextButton.TextButtonStyle var2 = new TextButton.TextButtonStyle();
      var2.font = XScreen.getBitmapFont36();
      var2.down = var0.getDrawable("gray_button");
      var2.up = var0.getDrawable("gray_button");
      var2.downFontColor = UIMainMenuState.MainConst.WHITEFONT;
      var2.fontColor = Color.GRAY;
      var0.add("grayButtonStyle", var2);
      ImageButton.ImageButtonStyle var3 = new ImageButton.ImageButtonStyle();
      var3.imageDown = var0.getDrawable("back_press");
      var3.imageUp = var0.getDrawable("back_normal");
      var0.add("SettingBackTo", var3);
   }

   private void backToMain() {
      this.moveButtonOut();
      this.backButton.addAction(Actions.delay(0.65F, UIScreen.uiState(this.screen, 0)));
      ////((Lightning)////((Lightning)this.screen.game)).getPlatFormFunction().closeBanner();
   }

   private void backToSettings() {
      this.moveInfoActorsOut();
      this.moveButtonIn(0.7F);
   }

   private void moveButtonIn(float var1) {
      this.addMoveInAction(this.resetButton, var1, 133.0F, 504.0F, 0.4F);
      float var2 = 0.083333336F + var1;
      this.addMoveInAction(this.soundButton, var2, 133.0F, 413.0F, 0.4F);
      float var3 = 0.16666667F + var1;
      this.addMoveInAction(this.musicButton, var3, 133.0F, 322.0F, 0.4F);
      this.backButton.addAction(Actions.sequence(Actions.delay(var1), Actions.fadeIn(0.4F), Actions.touchable(Touchable.enabled)));
   }

   private void moveButtonOut() {
      this.addMoveOutAction(this.musicButton, 0.0F, 133.0F, -100.0F, 0.4F);
      this.addMoveOutAction(this.soundButton, 0.083333336F, 133.0F, -100.0F, 0.4F);
      this.addMoveOutAction(this.resetButton, 0.16666667F, 133.0F, -100.0F, 0.4F);
      this.backButton.addAction(Actions.sequence(Actions.touchable(Touchable.disabled), Actions.fadeOut(0.4F)));
   }

   private void showCheckInfo() {
      this.moveButtonOut();
      this.moveInfoActorsIn(0.65F);
   }

   public void backPressed() {
      Touchable var1 = this.backButton.getTouchable();
      Touchable var2 = this.infoBackButton.getTouchable();
      if(var1 == Touchable.enabled && var2 == Touchable.enabled) {
         throw new RuntimeException("backButton and infoBackButton are touchable at same time.");
      } else {
         if(var1 == Touchable.enabled) {
            this.backToMain();
         }

         if(var2 == Touchable.enabled) {
            this.backToSettings();
         }

      }
   }

   public void moveInfoActorsIn(float var1) {
      this.infoImage.addAction(Actions.sequence(Actions.delay(var1), Actions.moveTo(73.0F, 423.0F, 0.5F, Interpolation.pow2Out)));
      float var2 = 0.1F + var1;
      this.addMoveInAction(this.okayButton, var2, 127.0F, 335.0F, 0.4F);
      float var3 = 0.2F + var1;
      this.addMoveInAction(this.cancelButton, var3, 127.0F, 249.0F, 0.4F);
      this.infoBackButton.addAction(Actions.sequence(Actions.delay(var1), Actions.fadeIn(0.4F), Actions.touchable(Touchable.enabled)));
   }

   public void moveInfoActorsOut() {
      this.infoImage.addAction(Actions.sequence(Actions.delay(0.36666667F), Actions.moveTo(73.0F, -160.0F, 0.5F, Interpolation.pow2Out)));
      this.addMoveOutAction(this.okayButton, 0.1F, 127.0F, -248.0F, 0.4F);
      this.addMoveOutAction(this.cancelButton, 0.0F, 127.0F, -334.0F, 0.4F);
      this.infoBackButton.addAction(Actions.sequence(Actions.touchable(Touchable.disabled), Actions.fadeOut(0.4F)));
   }

   public void switchToThisState(UIScreen var1) {
      super.switchToThisState(var1);
      Group var2 = var1.getStateGroup();
      Group var3 = var1.getUIBottomGroup();
      this.backButton.setPosition(25.0F, 97.0F);
      this.infoBackButton.setPosition(25.0F, 97.0F);
      var3.addActor(this.backButton);
      var3.addActor(this.infoBackButton);
      XScreen.getBitmapFont36().setScale(1.0F, 1.0F);
      this.resetButton.setPosition(133.0F, -100.0F);
      this.resetButton.layout();
      if(Settings.lapCount > 1) {
         var2.addActor(this.resetButton);
      }

      this.soundButton.setPosition(133.0F, -100.0F);
      this.soundButton.layout();
      var2.addActor(this.soundButton);
      this.musicButton.setPosition(133.0F, -100.0F);
      this.musicButton.layout();
      var2.addActor(this.musicButton);
      var2.addActor(this.infoImage);
      var2.addActor(this.okayButton);
      var2.addActor(this.cancelButton);
      this.moveButtonIn(0.0F);
      ////((Lightning)////((Lightning)var1.game)).getPlatFormFunction().showTopBanner();
   }

   private static class SetConst {

      public static final int BACKTOX = 25;
      public static final int BACKTOY = 97;
      public static final float BUTTONDELAY = 0.083333336F;
      public static final float BUTTONOUTTIME = 0.4F;
      public static final int MUSICBUTTONENDY = 322;
      public static final int MUSICBUTTONSTARTY = -100;
      public static final int MUSICBUTTONX = 133;
      public static final int RESETBUTTONENDY = 504;
      public static final int RESETBUTTONSTARTY = -100;
      public static final int RESETBUTTONX = 133;
      public static final int SOUNDBUTTONENDY = 413;
      public static final int SOUNDBUTTONSTARTY = -100;
      public static final int SOUNDBUTTONX = 133;


   }
}
