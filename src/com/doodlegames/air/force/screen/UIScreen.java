package com.doodlegames.air.force.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.doodlegames.air.force.resource.Assets_UI;
import com.doodlegames.air.force.screen.LoadingScreen;
import com.doodlegames.air.force.screen.UIAchieveState;
import com.doodlegames.air.force.screen.UIMainMenuState;
import com.doodlegames.air.force.screen.UIScreenState;
import com.doodlegames.air.force.screen.UISelectLevelState;
import com.doodlegames.air.force.screen.UISettingState;
import com.doodlegames.air.force.screen.UIStoreState;
import com.doodlegames.air.force.screen.XScreen;
import com.doodlegames.air.force.screen.action.XDelayAction;
import com.doodlegames.air.force.screen.screenActor.UIBackground;
import com.doodlegames.air.force.utils.MusicPlayer;
import com.doodlegames.air.force.utils.Settings;
import com.doodlegames.air.force.utils.SoundPlayer;

public class UIScreen extends XScreen {

   public static final int ACHIEVE = 4;
   public static final int MAIN = 0;
   public static final int SELEVEL = 1;
   public static final int SETTING = 3;
   public static final int STORE = 2;
   public static LoadingScreen.LoadingInfo info = new LoadingScreen.LoadingInfo() {
      public void fillAssetManager(AssetManager var1) {
         Assets_UI.info.fillAssetManager(var1);
      }
      public Screen loadingFinished(Game var1, AssetManager var2) {
         Assets_UI.info.loadingFinished(var1, var2);
         return new UIScreen(var1);
      }
   };
   public static int initState = 0;
   public static Skin uiSkin;
   private UIAchieveState achieveState;
   private UIBackground bgGround;
   private Image bottomBG;
   private Group bottomPart;
   public Color[] clsColors;
   private UIScreenState currentState;
   private Image fighterFrame;
   private int gotoState = -1;
   private UIMainMenuState mainMenuState;
   private UISelectLevelState selectLevelState;
   private UISettingState settingState;
   private Group stateControls;
   private UIScreenState[] states;
   private UIStoreState storeState;
   private Image upperBG;
   private Group upperPart;


   public UIScreen(Game var1) {
      super(var1, getUISkin());
      Color[] var2 = new Color[]{new Color(0.19215687F, 0.3529412F, 0.58431375F, 1.0F), new Color(0.5137255F, 0.45490196F, 0.31764707F, 1.0F), new Color(0.1254902F, 0.17254902F, 0.20784314F, 1.0F), new Color(0.14901961F, 0.22352941F, 0.22745098F, 1.0F), new Color(0.0F, 0.0F, 0.0F, 1.0F), new Color(0.4392157F, 0.23529412F, 0.19607843F, 1.0F), new Color(0.27058825F, 0.1764706F, 0.28235295F, 1.0F)};
      this.clsColors = var2;
      MusicPlayer.musicPlayer.clearCurrentMusic();
      MusicPlayer.musicPlayer.playMusic(Assets_UI.bgMusic);
      Settings.backgroundVelocity.y = -53.333332F;
      this.bgGround = new UIBackground();
      this.bgGround.setWidth(this.screenStage.getWidth());
      this.bgGround.setHeight(this.screenStage.getHeight());
      super.clsColor.set(this.clsColors[-1 + Assets_UI.getBgLevel()]);
      this.stateControls = new Group();
      this.stateControls.setWidth(this.screenStage.getWidth());
      this.stateControls.setHeight(this.screenStage.getHeight());
      this.upperPart = new Group();
      this.upperPart.setPosition(0.0F, 761.0F);
      this.upperBG = new Image(this.skin.getDrawable("cockpit_top"));
      this.upperBG.setTouchable(Touchable.disabled);
      this.upperBG.setPosition(0.0F, 0.0F);
      this.fighterFrame = new Image(this.skin.getDrawable("wireframe"));
      this.fighterFrame.setOrigin(37.5F, 37.5F);
      this.fighterFrame.setTouchable(Touchable.disabled);
      this.fighterFrame.setPosition(202.0F, 49.0F);
      this.fighterFrame.addAction(Actions.forever(Actions.rotateBy(-360.0F, 5.0F)));
      this.upperPart.addActor(this.upperBG);
      this.upperPart.addActor(this.fighterFrame);
      this.screenStage.addActor(this.upperPart);
      this.bottomPart = new Group();
      this.bottomPart.setPosition(0.0F, -100.0F);
      this.bottomBG = new Image(this.skin.getDrawable("cockpit_end"));
      this.bottomBG.setPosition(0.0F, 0.0F);
      this.bottomPart.addActor(this.bottomBG);
      this.screenStage.addActor(this.bottomPart);
      this.mainMenuState = new UIMainMenuState(this);
      this.selectLevelState = new UISelectLevelState(this);
      this.settingState = new UISettingState(this);
      this.achieveState = new UIAchieveState(this);
      this.storeState = new UIStoreState(this);
      UIScreenState[] var5 = new UIScreenState[]{this.mainMenuState, this.selectLevelState, this.storeState, this.settingState, this.achieveState};
      this.states = var5;
      this.askForStateChange(initState);
      initState = 0;
   }

   private void debugStateChange(int var1) {
      switch(var1) {
      case 0:
         Settings.i("MAIN");
         return;
      case 1:
         Settings.i("SELEVEL");
         return;
      case 2:
         Settings.i("STORE");
         return;
      case 3:
         Settings.i("SETTING");
         return;
      case 4:
         Settings.i("ACHIEVE");
         return;
      default:
      }
   }

   public static Action getLogoGlitterAction() {
      return Actions.sequence(Actions.delay(1.0F), Actions.repeat(-1, Actions.sequence(Actions.alpha(0.0F, 0.083333336F), Actions.alpha(1.0F, 0.041666668F), Actions.alpha(0.0F, 0.083333336F), Actions.alpha(1.0F, 0.041666668F), XDelayAction.randDelay(5.0F, 3.0F))));
   }

   public static Skin getUISkin() {
      Skin var0 = new Skin();
      var0.addRegions(Assets_UI.atlas);
      UIMainMenuState.addResource(var0);
      UISelectLevelState.addResource(var0);
      UISettingState.addResource(var0);
      UIAchieveState.addResource(var0);
      UIStoreState.addResource(var0);
      uiSkin = var0;
      return uiSkin;
   }

   private void handleStateChange() {
      int var1 = this.gotoState;
      if(var1 >= 0 && var1 < this.states.length) {
         UIScreenState var2 = this.states[var1];
         if(var2 != null && var2 != this.currentState) {
            this.uiScreenInit(var2);
            var2.switchToThisState(this);
         }

         this.gotoState = -1;
      }

   }

   private void uiScreenInit(UIScreenState var1) {
      this.currentState = var1;
      this.bottomPart.clear();
      this.upperPart.clear();
      this.stateControls.clear();
      this.stateControls.getListeners().clear();
      this.screenStage.getRoot().getListeners().clear();
      this.screenStage.clear();
      this.upperPart.addActor(this.upperBG);
      this.upperPart.addActor(this.fighterFrame);
      this.bottomPart.addActor(this.bottomBG);
      this.screenStage.addActor(this.bgGround);
      this.screenStage.addActor(this.stateControls);
      this.screenStage.addActor(this.bottomPart);
      this.screenStage.addActor(this.upperPart);
      this.stateControls.setPosition(0.0F, 0.0F);
//      ////((Lightning)this.game).getPlatFormFunction().closeBanner();
   }

   public static UIScreen.UIStateAction uiState(UIScreen var0, int var1) {
      UIScreen.UIStateAction var2 = (UIScreen.UIStateAction)Actions.action(UIScreen.UIStateAction.class);
      var2.screen = var0;
      var2.state = var1;
      return var2;
   }

   protected void act(float var1) {
      super.act(var1);
      this.handleStateChange();
      if(this.currentState != null) {
         this.currentState.update(var1);
      }

   }

   public void askForStateChange(int var1) {
      this.gotoState = var1;
   }

   public boolean currentFighterLock() {
      return this.mainMenuState.currentFighterIsLock();
   }

   public void dispose() {
      super.dispose();
   }

   public Group getStateGroup() {
      return this.stateControls;
   }

   public Group getUIBottomGroup() {
      return this.bottomPart;
   }

   public Group getUIUpperGroup() {
      return this.upperPart;
   }

   public void pause() {
      super.pause();
      MusicPlayer.musicPlayer.stop();
   }

   public void playBackSound() {
      SoundPlayer.soundPlayer.playSound(Assets_UI.backSound);
   }

   public void playButtonSound() {
      SoundPlayer.soundPlayer.playSound(Assets_UI.buttonSound);
   }

   public void resume() {
      super.resume();
      if(Settings.isFocus()) {
         MusicPlayer.musicPlayer.play();
      }

   }

   public void rotateWireFrame() {
      this.fighterFrame.addAction(Actions.rotateBy(-360.0F, 1.0F));
   }

   public static class UIStateAction extends Action {

      private UIScreen screen;
      public int state;


      public boolean act(float var1) {
         if(this.screen != null) {
            this.screen.askForStateChange(this.state);
         } else {
            Settings.w("WARNNING: screen can not be null!");
         }

         return true;
      }
   }

   public static class UIconst {

      public static final int BOTTOMENDY = 0;
      public static final int BOTTOMSTARTY = -100;
      public static final int BOTTOMX = 0;
      public static final float DELAYBASE = 5.0F;
      public static final float DELAYRAND = 3.0F;
      public static final int FRAMEX = 202;
      public static final int FRAMEY = 49;
      public static final float LOGOGITCYCLE = 0.25F;
      public static final float LOGOGITDOWN = 0.083333336F;
      public static final float LOGOGITUP = 0.041666668F;
      public static final float TIPFADEIN = 0.33333334F;
      public static final float TIPFADEOUT = 0.33333334F;
      public static final float TIPKEEP = 0.33333334F;
      public static final int UPPERENDY = 541;
      public static final int UPPERSTARTY = 761; 


      public static void loadData() {}
   }
}
