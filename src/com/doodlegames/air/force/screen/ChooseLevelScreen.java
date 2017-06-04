package com.doodlegames.air.force.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.doodlegames.air.force.XGame;
import com.doodlegames.air.force.screen.GameScreen;
import com.doodlegames.air.force.screen.LoadingScreen;
import com.doodlegames.air.force.screen.MainMenuScreen;
import com.doodlegames.air.force.screen.XScreen;

public class ChooseLevelScreen extends XScreen {

   private static final int LEVELNUM = 7;
   private static final float[] buttonX = new float[]{280.0F, 280.0F, 200.0F, 200.0F, 300.0F, 280.0F, 280.0F};
   private static final float[] buttonY = new float[]{760.0F, 660.0F, 560.0F, 460.0F, 360.0F, 260.0F, 160.0F};
   public static LoadingScreen.LoadingInfo loadingInfo = new LoadingScreen.LoadingInfo() {
      public void fillAssetManager(AssetManager var1) {}
      public Screen loadingFinished(Game var1, AssetManager var2) {
         return new ChooseLevelScreen(var1);
      }
   };
   private ImageButton buttonBackToMainMenu;
   private ImageButton[] buttonLv = new ImageButton[7];
   Skin chooseLevelScreenSkin;
   @Override
	public void show() {
		System.out.print("Man ChooseLevelScreen");
		super.show();
	}

   public ChooseLevelScreen(Game var1) {
      super(var1, getChooseScreenSkin());

      for(int var2 = 0; var2 < 7; ++var2) {
         this.buttonLv[var2] = new ImageButton(this.skin);
         this.buttonLv[var2].setX(buttonX[var2]);
         this.buttonLv[var2].setY(buttonY[var2]);
         this.buttonLv[var2].setWidth(100.0F);
         this.buttonLv[var2].setHeight(35.0F);
         this.screenStage.addActor(this.buttonLv[var2]);
      }
      
//      buttonLv[0].setBackground(XGame.btnMis1);
//      buttonLv[1].setBackground(XGame.btnMis2);
//      buttonLv[2].setBackground(XGame.btnMis3);
//      buttonLv[3].setBackground(XGame.btnMis4);
//      buttonLv[4].setBackground(XGame.btnMis5);
//      buttonLv[5].setBackground(XGame.btnMis6);
//      buttonLv[6].setBackground(XGame.btnMis7);

      this.buttonLv[0].addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            Game var3 = ChooseLevelScreen.this.game;
            GameScreen.level = 1;
            GameScreen.gameScreenInfo.currentScreen = null;
            var3.setScreen(new LoadingScreen(var3, GameScreen.gameScreenInfo, 0.0F, false));
         }
      });
      this.buttonLv[1].addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            Game var3 = ChooseLevelScreen.this.game;
            GameScreen.level = 2;
            GameScreen.gameScreenInfo.currentScreen = null;
            var3.setScreen(new LoadingScreen(var3, GameScreen.gameScreenInfo, 0.0F, false));
         }
      });
      this.buttonLv[2].addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            Game var3 = ChooseLevelScreen.this.game;
            GameScreen.level = 3;
            GameScreen.gameScreenInfo.currentScreen = null;
            var3.setScreen(new LoadingScreen(var3, GameScreen.gameScreenInfo, 0.0F, false));
         }
      });
      this.buttonLv[3].addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            Game var3 = ChooseLevelScreen.this.game;
            GameScreen.level = 4;
            GameScreen.gameScreenInfo.currentScreen = null;
            var3.setScreen(new LoadingScreen(var3, GameScreen.gameScreenInfo, 0.0F, false));
         }
      });
      this.buttonLv[4].addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            Game var3 = ChooseLevelScreen.this.game;
            GameScreen.level = 5;
            GameScreen.gameScreenInfo.currentScreen = null;
            var3.setScreen(new LoadingScreen(var3, GameScreen.gameScreenInfo, 0.0F, false));
         }
      });
      this.buttonLv[5].addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            Game var3 = ChooseLevelScreen.this.game;
            GameScreen.level = 6;
            GameScreen.gameScreenInfo.currentScreen = null;
            var3.setScreen(new LoadingScreen(var3, GameScreen.gameScreenInfo, 0.0F, false));
         }
      });
      this.buttonLv[6].addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            Game var3 = ChooseLevelScreen.this.game;
            GameScreen.level = 7;
            GameScreen.gameScreenInfo.currentScreen = null;
            var3.setScreen(new LoadingScreen(var3, GameScreen.gameScreenInfo, 0.0F, false));
         }
      });
      this.buttonBackToMainMenu = new ImageButton(this.skin);
      this.buttonBackToMainMenu.setX(410.0F);
      this.buttonBackToMainMenu.setY(730.0F);
      this.buttonBackToMainMenu.setWidth(50.0F);
      this.buttonBackToMainMenu.setHeight(50.0F);
      this.buttonBackToMainMenu.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            Game var3 = ChooseLevelScreen.this.game;
            var3.setScreen(new LoadingScreen(var3, MainMenuScreen.info, 0.0F, false));
         }
      });
      this.screenStage.addActor(this.buttonBackToMainMenu);
   }

   private static Skin getChooseScreenSkin() {
      return MainMenuScreen.getMainMenuScreenSkin();
   }
}
