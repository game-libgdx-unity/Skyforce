package com.doodlegames.air.force.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.doodlegames.air.force.XGame;
import com.doodlegames.air.force.resource.Assets_UI;
import com.doodlegames.air.force.resource.Assets_fighter;
import com.doodlegames.air.force.screen.action.ArrowMoveAction;
import com.doodlegames.air.force.screen.action.FontScaleAction;
import com.doodlegames.air.force.screen.action.SetTextAction;
import com.doodlegames.air.force.screen.screenActor.GameEvent;
import com.doodlegames.air.force.screen.screenActor.SelectActor;
import com.doodlegames.air.force.utils.DragListener;
import com.doodlegames.air.force.utils.Settings;

public class UISelectLevelState extends UIScreenState {

   private ImageButton backButton;
   private UISelectLevelState.LevArrowButton buttonLeft;
   private UISelectLevelState.LevelSelectButton buttonLevelA;
   private UISelectLevelState.LevelSelectButton buttonLevelB;
   private UISelectLevelState.LevArrowButton buttonRight;
   private UISelectLevelState.ChangeState changeState = new UISelectLevelState.ChangeState();
   private Group container;
   private UISelectLevelState.DrageChangePanel drageChanger = new UISelectLevelState.DrageChangePanel();
   private SelectActor missionPreview;
   private ImageButton playButton;
   Action restoreAction = new Action() {
      public boolean act(float var1) {
         UISelectLevelState var2 = UISelectLevelState.this;
         var2.bottomControlsFadeIn();
         var2.container.addAction(Actions.moveTo(73.0F, 423.0F, 0.5F, Interpolation.pow2Out));
         var2.buttonLevelA.moveIn();
         var2.buttonLevelB.moveIn();
         var2.slideTipLabel.addAction(Actions.delay(0.6F, Actions.show()));
         var2.tipTable.addAction(Actions.delay(0.6F, Actions.show()));
         return true;
      }
   };
   private Image scenePanel;
   private Label slideTipLabel;
   private UIStoreActorGroup storeActors;
   private ImageButton storeBackButton;
   private ImageButton storeButton;
   private UISelectLevelState.StoreMoveButtonInterface storeButtonClicked;
   private Group storeGroup;
   private Table tipTable;


   public UISelectLevelState(UIScreen var1) {
      super(var1);
      this.scenePanel = new Image(this.skin.getDrawable("scene"));
      this.scenePanel.setTouchable(Touchable.disabled);
      Group var2 = new Group();
      var2.setTouchable(Touchable.disabled);
      Image var3 = new Image(Assets_UI.earthAnimation);
      var3.getColor().a = 0.6F;
      Image var4 = new Image(this.skin.getDrawable("earth_logo"));
      Image var5 = new Image(this.skin.getDrawable("earth_word"));
      this.setPositionForSencePanel(var2, this.scenePanel, var3, var4, var5);
      Group var6 = new Group();
      var6.setTouchable(Touchable.disabled);
      Image var7 = new Image(Assets_UI.moonAnimation);
      var7.getColor().a = 0.6F;
      Image var8 = new Image(this.skin.getDrawable("moon_logo"));
      Image var9 = new Image(this.skin.getDrawable("moon_word"));
      this.setPositionForSencePanel(var6, this.scenePanel, var7, var8, var9);
      Group var10 = new Group();
      var10.setTouchable(Touchable.disabled);
      Image var11 = new Image(Assets_UI.marAnimation);
      var11.getColor().a = 0.6F;
      Image var12 = new Image(this.skin.getDrawable("mars_logo"));
      Image var13 = new Image(this.skin.getDrawable("mars_word"));
      this.setPositionForSencePanel(var10, this.scenePanel, var11, var12, var13);
      Group var14 = new Group();
      var14.setTouchable(Touchable.disabled);
      Image var15 = new Image(Assets_UI.asteroidAnimation);
      var15.getColor().a = 0.6F;
      Image var16 = new Image(this.skin.getDrawable("asteroid_logo"));
      Image var17 = new Image(this.skin.getDrawable("asteroid_word"));
      this.setPositionForSencePanel(var14, this.scenePanel, var15, var16, var17);
      this.buttonLeft = new UISelectLevelState.LevArrowButton(true, this.skin, "leftArrow");
      this.buttonLeft.setPosition(-32.0F, 82.0F);
      this.buttonLeft.setVisible(false);
      this.buttonLeft.getColor().a = 0.0F;
      this.buttonLeft.addAction(Actions.delay(0.5F, Actions.fadeIn(0.15F)));
      ArrowMoveAction var18 = new ArrowMoveAction();
      var18.setDuration(0.33333334F);
      var18.setPosition(-37.0F, 82.0F);
      this.buttonLeft.addAction(var18);
      this.buttonRight = new UISelectLevelState.LevArrowButton(false, this.skin, "rightArrow");
      this.buttonRight.setPosition(331.0F, 82.0F);
      this.buttonRight.getColor().a = 0.0F;
      this.buttonRight.addAction(Actions.delay(0.5F, Actions.fadeIn(0.15F)));
      ArrowMoveAction var19 = new ArrowMoveAction();
      var19.setDuration(0.33333334F);
      var19.setPosition(336.0F, 82.0F);
      this.buttonRight.addAction(var19);
      this.container = new Group();
      this.missionPreview = new SelectActor(this.container, new Actor[]{var2, var6, var14, var10});
      this.missionPreview.getActingEvent().addListener(this.buttonLeft);
      this.missionPreview.getActingEvent().addListener(this.buttonRight);
      this.missionPreview.getActingEvent().addListener(new UISelectLevelState.contorlsLockSetter());
      this.container.addActor(this.scenePanel);
      this.container.addActor(this.buttonLeft);
      this.container.addActor(this.buttonRight);
      this.container.setPosition(73.0F, -160.0F);
      this.buttonLevelA = new UISelectLevelState.LevelSelectButton(this.skin, "LevelSelectButton", 1);
      this.buttonLevelA.setPosition(127.0F, -248.0F);
      this.buttonLevelB = new UISelectLevelState.LevelSelectButton(this.skin, "LevelSelectButton", 2);
      this.buttonLevelB.setPosition(127.0F, -334.0F);
      this.missionPreview.getActingEvent().addListener(this.buttonLevelA);
      this.missionPreview.getActingEvent().addListener(this.buttonLevelB);
      this.playButton = new ImageButton(XGame.btnPlay);
      this.playButton.setPosition(282.0F, -66.0F);
      this.playButton.setTouchable(Touchable.disabled);
      this.playButton.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            if(UISelectLevelState.this.playButton.getTouchable() == Touchable.enabled) {
               UISelectLevelState.this.screen.playButtonSound();
               UISelectLevelState.this.enterGame(UISelectLevelState.this.storeButtonClicked.level);
               Settings.flurryLog("chooseFighter", "fighterIndex", Assets_fighter.fighterIndex + "");
            }
         }
      });
      this.backButton = new ImageButton(this.skin, "LevelBackTo");
      this.backButton.setPosition(25.0F, 97.0F);
      this.backButton.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            if(UISelectLevelState.this.backButton.getTouchable() == Touchable.enabled) {
               UISelectLevelState.this.screen.playBackSound();
               UISelectLevelState.this.backToMain();
            }
         }
      });
      this.backButton.getColor().a = 0.0F;
      this.backButton.setTouchable(Touchable.disabled);
      this.storeBackButton = new ImageButton(this.skin, "LevelBackTo");
      this.storeBackButton.setPosition(25.0F, 97.0F);
      this.storeBackButton.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            if(UISelectLevelState.this.storeBackButton.getTouchable() == Touchable.enabled) {
               UISelectLevelState.this.screen.playBackSound();
               UISelectLevelState.this.selectStoreBack();
//               ////((Lightning)////((Lightning)UISelectLevelState.this.screen.game)).getPlatFormFunction().showTopBanner();
            }
         }
      });
      this.storeBackButton.setTouchable(Touchable.disabled);
      this.storeBackButton.setVisible(false);
      this.storeBackButton.getColor().a = 0.0F;
      this.storeButton = new ImageButton(this.skin, "LevelStore");
      this.storeButton.setPosition(391.0F, 98.0F);
      this.storeButton.addListener(new ChangeListener() {

         UISelectLevelState wThis = UISelectLevelState.this;

         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            UISelectLevelState.this.screen.playButtonSound();
            DelayAction var3 = Actions.delay(0.36666667F, Actions.moveTo(73.0F, -160.0F, 0.5F, Interpolation.pow2Out));
            this.wThis.changeState.goalState = 2;
            if(this.wThis.container.getActions().size == 0) {
               this.wThis.container.addAction(Actions.sequence(var3, Actions.after(this.wThis.changeState)));
            }

            this.wThis.buttonLevelA.moveOut();
            this.wThis.buttonLevelB.moveOut();
            ((ImageButton)var2).setTouchable(Touchable.disabled);
            this.wThis.slideTipLabel.setVisible(false);
            this.wThis.tipTable.setVisible(false);
            UISelectLevelState.this.bottomControlsFadeOut();
            ////((Lightning)////((Lightning)UISelectLevelState.this.screen.game)).getPlatFormFunction().closeBanner();
         }
      });
      this.storeButton.getColor().a = 0.0F;
      this.slideTipLabel = new Label("<< FLICK TO CHANGE MISSION >>", this.skin, "slidTipStyle");
      this.slideTipLabel.setColor(UISelectLevelState.LevelConst.TIPCOLOR);
      this.slideTipLabel.setPosition(83.0F, 200.0F);
      this.slideTipLabel.setVisible(false);
      this.tipTable = new Table(this.skin);
      this.tipTable.setBackground(this.skin.getDrawable("line"));
      this.tipTable.setBounds(0.0F, 660.0F, 480.0F, 37.0F);
      this.tipTable.setTouchable(Touchable.disabled);
      String var24 = "cycle " + Settings.lapCount;
      Label var25 = new Label(var24, new Label.LabelStyle(XScreen.getBitmapFont30(), Color.WHITE));
      this.tipTable.add((Actor)var25).center();
      this.tipTable.setVisible(false);
      this.storeButtonClicked = new UISelectLevelState.StoreMoveButtonInterface();
      this.storeActors = new UIStoreActorGroup(this.skin, 1);
      this.storeGroup = new Group();
      this.storeGroup.addActor(this.playButton);
      this.storeActors.addPaneToGroup(this.storeGroup);
      this.storeActors.setMoveButtonInterface(this.storeButtonClicked);
      this.storeGroup.translate(0.0F, -800.0F);
      this.storeActors.setItemSelectedFuns(new UIStoreActorGroup.ItemSelected() {
         public void itemSelected(int var1, int var2, int var3) {
            UISelectLevelState.this.screen.playButtonSound();
            if(var2 != -1) {
               Object var4;
               if(UISelectLevelState.this.storeActors.moveButtonIsOut()) {
                  var4 = Actions.delay(0.0F);
               } else {
                  var4 = UISelectLevelState.this.storeActors.getMoveOutAction(0.0F);
               }

               Action var5 = UISelectLevelState.this.storeActors.getMoveInAction(0.0F);
               UISelectLevelState.this.storeActors.addActionToMovingButton(Actions.sequence((Action)var4, var5));
            } else {
               UISelectLevelState.this.storeActors.addActionToMovingButton(Actions.sequence(UISelectLevelState.this.storeActors.getMoveOutAction(0.0F)));
            }
         }
         public void switchPane(int var1, int var2) {
            this.itemSelected(var1, -1, -1);
         }
      });
   }

   public static void addResource(Skin var0) {
      TextButton.TextButtonStyle var1 = new TextButton.TextButtonStyle();
      var1.font = XScreen.getBitmapFont36();
      var1.down = var0.getDrawable("green_button_small");
      var1.up = var0.getDrawable("green_button_small");
      var1.downFontColor = Color.WHITE;
      var1.fontColor = Color.GREEN;
      var0.add("smallGreenButton", var1);
      TextButton.TextButtonStyle var2 = new TextButton.TextButtonStyle();
      var2.font = XScreen.getBitmapFont30();
      var2.down = var0.getDrawable("green_button");
      var2.up = var0.getDrawable("green_button");
      var2.downFontColor = Color.WHITE;
      var2.fontColor = Color.GREEN;
      var0.add("LevelSelectButton", var2);
      ImageButton.ImageButtonStyle var3 = new ImageButton.ImageButtonStyle();
      var3.imageDown = var0.getDrawable("back_press");
      var3.imageUp = var0.getDrawable("back_normal");
      var0.add("LevelBackTo", var3);
      ImageButton.ImageButtonStyle var4 = new ImageButton.ImageButtonStyle();
      var4.imageDown = var0.getDrawable("store_press");
      var4.imageUp = var0.getDrawable("store_normarl");
      var0.add("LevelStore", var4);
   }

   private void backToMain() {
      if(this.container.getActions().size == 0) {
         DelayAction var1 = Actions.delay(0.36666667F, Actions.moveTo(73.0F, -160.0F, 0.5F, Interpolation.pow2Out));
         this.changeState.goalState = 0;
         this.container.addAction(Actions.sequence(var1, Actions.after(this.changeState)));
         this.buttonLevelA.moveOut();
         this.buttonLevelB.moveOut();
         this.slideTipLabel.setVisible(false);
         this.tipTable.setVisible(false);
         this.backButton.setTouchable(Touchable.disabled);
         this.bottomControlsFadeOut();
         ////((Lightning)////((Lightning)this.screen.game)).getPlatFormFunction().closeBanner();
      }

   }

   private void bottomControlsFadeIn() {
      this.backButton.addAction(Actions.sequence(Actions.fadeIn(1.0F), Actions.touchable(Touchable.enabled)));
      this.storeButton.addAction(Actions.sequence(Actions.fadeIn(1.0F), Actions.touchable(Touchable.enabled)));
   }

   private void bottomControlsFadeOut() {
      this.backButton.addAction(Actions.sequence(Actions.touchable(Touchable.disabled), Actions.fadeOut(1.0F)));
      this.storeButton.addAction(Actions.sequence(Actions.touchable(Touchable.disabled), Actions.fadeOut(1.0F)));
   }

   private void enterGame(final int var1) {
      GameScreen.comFromWhere = 2;
      this.storeGroup.addAction(Actions.sequence(Actions.delay(0.33333334F), Actions.moveTo(0.0F, -800.0F, 0.5F, Interpolation.pow2Out), Actions.delay(0.33333334F), new Action() {
         public boolean act(float var1x) {
            GameScreen.level = var1;
            GameScreen.gameScreenInfo.currentScreen = null;
            Game var2 = UISelectLevelState.this.screen.game;
            var2.setScreen(new LoadingScreen(var2, GameScreen.gameScreenInfo, 0.0F, true));
            return true;
         }
      }));
      this.storeActors.moveMovingButtonOut(0.0F);
      this.playButton.addAction(Actions.sequence(Actions.touchable(Touchable.disabled), FontScaleAction.fontScaleOut(0.16666667F), Actions.moveTo(282.0F, -66.0F, 0.2F, Interpolation.pow2Out)));
      this.screen.getUIUpperGroup().addAction(Actions.moveBy(0.0F, 220.0F, 0.5F, Interpolation.pow2Out));
      this.screen.getUIBottomGroup().addAction(Actions.moveBy(0.0F, -220.0F, 0.5F, Interpolation.pow2Out));
      this.container.addAction(Actions.moveBy(0.0F, -200.0F, 0.5F, Interpolation.pow2Out));
   }

   private void moveSenceOut(boolean var1) {
      float var2 = 553.0F;
      float var3;
      if(var1) {
         var3 = -407.0F;
      } else {
         var3 = var2;
      }

      if(!var1) {
         var2 = -407.0F;
      }

      Group var4 = this.container;
      var4.addAction(Actions.sequence(Actions.moveTo(var3, 423.0F, 0.16666667F * (Math.abs(var4.getX() - var3) / 480.0F), Interpolation.pow2Out), Actions.moveTo(var2, 423.0F), Actions.moveTo(73.0F, 423.0F, 0.16666667F, Interpolation.pow2Out)));
   }

   private void selectStoreBack() {
      MoveToAction var1 = Actions.moveTo(0.0F, -800.0F, 0.5F, Interpolation.pow2Out);
      this.storeGroup.addAction(var1);
      this.storeBackButton.addAction(Actions.sequence(Actions.touchable(Touchable.disabled), Actions.fadeOut(0.5F), Actions.hide()));
      this.storeBackButton.addAction(Actions.delay(0.5F, this.restoreAction));
      this.storeActors.moveMovingButtonOut(0.0F);
      this.playButton.addAction(Actions.sequence(Actions.touchable(Touchable.disabled), FontScaleAction.fontScaleOut(0.16666667F), Actions.moveTo(282.0F, -66.0F, 0.2F, Interpolation.pow2Out)));
      Group var2 = this.screen.getStage().getRoot();
      if(!var2.getListeners().contains(this.drageChanger, true)) {
         var2.addListener(this.drageChanger);
      }

   }

   private void senceMoveBack() {
      this.container.addAction(Actions.moveTo(73.0F, 423.0F, 0.16666667F, Interpolation.swingOut));
      this.buttonLevelA.addAction(Actions.moveTo(127.0F, 335.0F, 0.4F, Interpolation.pow2Out));
      this.buttonLevelB.addAction(Actions.moveTo(127.0F, 249.0F, 0.4F, Interpolation.pow2Out));
      this.buttonLeft.addAction(Actions.fadeIn(0.4F));
      this.buttonRight.addAction(Actions.fadeIn(0.4F));
   }

   private void setPositionForSencePanel(Group var1, Image var2, Image var3, Image var4, Image var5) {
      var3.setPosition(var2.getWidth() / 2.0F - var3.getWidth() / 2.0F, var2.getHeight() / 2.0F - var3.getHeight() / 2.0F);
      var4.setPosition(285.0F, 165.0F);
      var5.setPosition(22.0F, 193.0F - var5.getHeight());
      var1.addActor(var3);
      var1.addActor(var4);
      var1.addActor(var5);
   }

   public void backPressed() {
      Touchable var1 = this.backButton.getTouchable();
      Touchable var2 = this.storeBackButton.getTouchable();
      if(var1 == Touchable.enabled && var2 == Touchable.enabled && Gdx.app.getType() == Application.ApplicationType.Android) {
         Settings.flurryLog("UiselectBackAndStoreBackEnableAtSameTime");
         //((LightningAndroid)Gdx.app).exitHandler.sendEmptyMessage(1);
      }

      if(var1 == Touchable.enabled) {
         this.backToMain();
      }

      if(var2 == Touchable.enabled) {
         this.selectStoreBack();
      }

   }

   public void switchToThisState(UIScreen var1) {
      super.switchToThisState(var1);
      Group var2 = var1.getStateGroup();
      Group var3 = var1.getStage().getRoot();
      Group var4 = var1.getUIBottomGroup();
      var3.addListener(this.drageChanger);
      MoveToAction var6 = Actions.moveTo(73.0F, 423.0F, 0.5F, Interpolation.pow2Out);
      this.container.addAction(var6);
      var2.addActor(this.container);
      this.backButton.setTouchable(Touchable.disabled);
      var4.addActor(this.backButton);
      this.storeBackButton.setTouchable(Touchable.disabled);
      var4.addActor(this.storeBackButton);
      this.storeButton.setTouchable(Touchable.enabled);
      var4.addActor(this.storeButton);
      this.storeActors.moveMovingButton(-66.0F, 0.0F);
      this.storeActors.clearSelectedIndex();
      this.storeGroup.setPosition(0.0F, -800.0F);
      Actor var7 = this.missionPreview.getCurrentActor();
      if(!this.container.getChildren().contains(var7, true)) {
         this.container.addActor(var7);
      }

      this.buttonLevelA.getStyle().font.setScale(1.0F, 0.01F);
      this.buttonLevelA.layout();
      this.buttonLevelA.getActions().clear();
      this.buttonLevelA.moveIn();
      this.buttonLevelA.setTouchable(Touchable.enabled);
      var2.addActor(this.buttonLevelA);
      this.buttonLevelB.getStyle().font.setScale(1.0F, 0.01F);
      this.buttonLevelB.layout();
      this.buttonLevelB.getActions().clear();
      this.buttonLevelB.moveIn();
      this.buttonLevelB.setTouchable(Touchable.enabled);
      var2.addActor(this.buttonLevelB);
      this.slideTipLabel.getActions().clear();
      SequenceAction var8 = Actions.sequence(Actions.alpha(0.0F), Actions.show(), Actions.delay(1.2F), Actions.repeat(-1, Actions.sequence(Actions.alpha(1.0F, 0.33333334F), Actions.alpha(1.0F, 0.33333334F), Actions.alpha(0.0F, 0.6666667F))));
      this.slideTipLabel.addAction(var8);
      var2.addActor(this.slideTipLabel);
      this.tipTable.getActions().clear();
      this.tipTable.addAction(Actions.sequence(Actions.alpha(0.0F), Actions.show(), Actions.delay(1.2F), Actions.forever(Actions.sequence(Actions.fadeIn(0.25F), Actions.delay(0.25F), Actions.fadeOut(0.25F), Actions.delay(0.25F)))));
      if(Settings.lapCount >= 2) {
         var2.addActor(this.tipTable);
      }

      var2.addActor(this.storeGroup);
      this.bottomControlsFadeIn();
      ////((Lightning)////((Lightning)this.screen.game)).getPlatFormFunction().showTopBanner();
   }

   private class LevelSelectButton extends TextButton implements GameEvent.GameEventListener {

      private LevelSelectButton.ButtonChangeAction buttonChange = new LevelSelectButton.ButtonChangeAction();
      private final int buttonId;
      private Drawable completedPic;
      private int currentLevel;
      private Drawable lockPic;


      public LevelSelectButton(Skin var2, String var3, int var4) {
         super("MISSION " + var4, var2, var3);
         this.buttonId = var4;
         this.currentLevel = this.buttonId;
         this.lockPic = var2.getDrawable("lock");
         this.completedPic = var2.getDrawable("completed_press");
         if(UISelectLevelState.LevelConst.checkLevelUnlock(this.currentLevel) == 0) {
            this.setText("MISSION");
         }
         buttonChange.act(0f);

         this.myPadLeft();
         ChangeListener change =new ChangeListener() {

             UISelectLevelState.LevelSelectButton wThis = LevelSelectButton.this;

             public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
                if(this.wThis.getTouchable() == Touchable.enabled) {
                   UISelectLevelState.this.screen.playButtonSound();
                   if(UISelectLevelState.LevelConst.checkLevelUnlock(LevelSelectButton.this.currentLevel) != 0) {
                      this.wThis.setTouchable(Touchable.disabled);
                      DelayAction var3 = Actions.delay(0.36666667F, Actions.moveTo(73.0F, -260.0F, 0.5F, Interpolation.pow2Out));
                      if(UISelectLevelState.this.container.getActions().size == 0) {
                         UISelectLevelState.this.container.addAction(var3);
                      }

                      UISelectLevelState.this.buttonLevelA.moveOut();
                      UISelectLevelState.this.buttonLevelB.moveOut();
                      UISelectLevelState.this.slideTipLabel.setVisible(false);
                      UISelectLevelState.this.tipTable.setVisible(false);
                      UISelectLevelState.this.bottomControlsFadeOut();
                      UISelectLevelState.this.storeBackButton.addAction(Actions.sequence(Actions.delay(1.0F), Actions.show(), Actions.fadeIn(1.0F), Actions.touchable(Touchable.enabled)));
                      UISelectLevelState.this.storeButtonClicked.level = this.wThis.currentLevel;
                      UISelectLevelState.this.container.addAction(Actions.after(Actions.add(UISelectLevelState.this.storeGroup, Actions.moveTo(0.0F, 0.0F, 0.5F, Interpolation.pow2Out))));
                      UISelectLevelState.this.storeActors.clearSelectedIndex();
                      UISelectLevelState.this.storeActors.switchPaneToCoinPane();
                      UISelectLevelState.this.storeActors.updateTitleMoneyLabel();
                      UISelectLevelState.this.storeActors.addActionToMovingButton(SetTextAction.setText("PLAY"));
                      UISelectLevelState.this.playButton.addAction(Actions.parallel(Actions.sequence(Actions.delay(1.5F), Actions.moveTo(282.0F, 180.0F, 0.2F, Interpolation.pow2Out)), Actions.sequence(FontScaleAction.setFontScale0(1.7F), FontScaleAction.fontScaleIn(0.16666667F), Actions.touchable(Touchable.enabled))));
                      Group var4 = UISelectLevelState.this.screen.getStage().getRoot();
                      if(var4.getListeners().contains(UISelectLevelState.this.drageChanger, true)) {
                         var4.removeListener(UISelectLevelState.this.drageChanger);
                      }

                      ////((Lightning)////((Lightning)UISelectLevelState.this.screen.game)).getPlatFormFunction().closeBanner();
                      return;
                   }
                }

             }
          };
         this.addListener(change);
      }

      private Action getMoveInAction() {
         float var1;
         if(this.buttonId == 1) {
            var1 = 0.1F;
         } else {
            var1 = 0.2F;
         }

         if(this.buttonId == 1) {
            ;
         }

         float var2;
         if(this.buttonId == 1) {
            var2 = 335.0F;
         } else {
            var2 = 249.0F;
         }

         return Actions.sequence(Actions.delay(var1), FontScaleAction.setFontScale0(), Actions.moveTo(127.0F, var2, 0.4F, Interpolation.pow2Out), FontScaleAction.fontScale(0.16666667F, false), Actions.touchable(Touchable.enabled));
      }

      private Action getMoveOutAction() {
         float var1;
         if(this.buttonId == 1) {
            var1 = 0.1F;
         } else {
            var1 = 0.0F;
         }

         if(this.buttonId == 1) {
            ;
         }

         float var2;
         if(this.buttonId == 1) {
            var2 = -248.0F;
         } else {
            var2 = -334.0F;
         }

         return Actions.sequence(Actions.touchable(Touchable.disabled), Actions.delay(var1), FontScaleAction.fontScale(0.16666667F, true), Actions.moveTo(127.0F, var2, 0.4F, Interpolation.pow2Out), Actions.delay(0.1F - var1));
      }

      public void draw(SpriteBatch var1, float var2) {
         int var3 = UISelectLevelState.LevelConst.checkLevelUnlock(this.currentLevel);
         if(var3 == 0) {
            super.getStyle().down = UISelectLevelState.this.skin.getDrawable("gray_button");
            super.getStyle().up = UISelectLevelState.this.skin.getDrawable("gray_button");
         } else {
            super.getStyle().down = UISelectLevelState.this.skin.getDrawable("green_button");
            super.getStyle().up = UISelectLevelState.this.skin.getDrawable("green_button");
         }

         super.draw(var1, var2);
         if(var3 == 0) {
            float var9 = this.getX() + this.getWidth() / 2.0F - this.lockPic.getMinWidth() / 2.0F;
            float var10 = this.getY() + this.getHeight() / 2.0F - this.lockPic.getMinHeight() / 2.0F;
            this.lockPic.draw(var1, var9, var10, this.lockPic.getMinWidth(), this.lockPic.getMinHeight());
         }

         if(var3 == 2) {
            float var4 = this.getLabel().getTextBounds().height / 18.0F;
            float var5 = this.completedPic.getMinWidth();
            float var6 = var4 * this.completedPic.getMinHeight();
            float var7 = 20.0F + this.getX();
            float var8 = this.getY() + this.getHeight() / 2.0F - var6 / 2.0F;
            this.completedPic.draw(var1, var7, var8, var5, var6);
         }

      }
      private class ButtonChangeAction extends Action {

          SelectActor.ChangeEventArg arg;


          private ButtonChangeAction() {}

          public boolean act(float var1) {
             if(this.arg != null) {
                currentLevel = LevelSelectButton.this.getLevel(this.arg.choose);
                UISelectLevelState.LevelSelectButton var3 = LevelSelectButton.this;
                boolean var4;
                if(LevelSelectButton.this.currentLevel <= Settings.getMaxLevelId()) {
                   var4 = true;
                } else {
                   var4 = false;
                }

                var3.setVisible(var4);
                TextButton var5 = (TextButton)super.getActor();
                if(UISelectLevelState.LevelConst.checkLevelUnlock(LevelSelectButton.this.currentLevel) == 0) {
                   var5.setText("");
                } else {
                   var5.setText("MISSION " + LevelSelectButton.this.currentLevel);
                }

                LevelSelectButton.this.myPadLeft();
             }

             return true;
          }
       }
      public int getLevel(int var1) {
         int[][] var2 = new int[][]{{1, 2}, {3, 4}, {5, Integer.MAX_VALUE}, {6, 7}};
         return this.buttonId == 1?var2[var1][0]:(this.buttonId == 2?var2[var1][1]:-1);
      }

      public void handle(Object var1, Object var2) {
         if(var1 instanceof SelectActor && var2 instanceof SelectActor.ChangeEventArg) {
            SelectActor.ChangeEventArg var3 = (SelectActor.ChangeEventArg)var2;
            this.buttonChange.arg = var3;
            if(var3.choose != var3.lastChoose) {
               this.moveOutIn(this.buttonChange);
            }
         }

      }

      public void moveIn() {
         this.myPadLeft();
         this.invalidateHierarchy();
         this.addAction(this.getMoveInAction());
      }

      public void moveOut() {
         this.addAction(this.getMoveOutAction());
      }

      public void moveOutIn(Action var1) {
         this.getActions().clear();
         if(this.buttonId == 1) {
            ;
         }

         float var2;
         if(this.buttonId == 1) {
            var2 = -248.0F;
         } else {
            var2 = -334.0F;
         }

         float var3 = 0.4F * ((this.getY() - var2) / 583.0F);
         this.addAction(Actions.sequence(Actions.touchable(Touchable.disabled), Actions.parallel(Actions.moveTo(127.0F, var2, var3, Interpolation.pow2Out), FontScaleAction.fontScale(0.16666667F, true)), var1, this.getMoveInAction()));
      }

      public void myPadLeft() {
         if(UISelectLevelState.LevelConst.checkLevelUnlock(this.currentLevel) == 2) {
            this.getLabelCell().padLeft(30.0F);
         } else {
            this.getLabelCell().padLeft(0.0F);
         }
      }
   }

   private class ChangeState extends Action {

      int goalState;


      private ChangeState() {}

      public boolean act(float var1) {
         UISelectLevelState.this.screen.askForStateChange(this.goalState);
         return true;
      }
   }

   

   public static class LevelConst {

      public static final float ANIMATIONALPHA = 0.6F;
      public static final float ARROWAPPEAR = 0.5F;
      public static final int ARROWBUTTON1X = -32;
      public static final int ARROWBUTTON1Y = 82;
      public static final int ARROWBUTTON2X = 331;
      public static final int ARROWBUTTON2Y = 82;
      public static final int ARROWDELTAX = 5;
      public static final float ARROWFADEIN = 0.15F;
      public static final float ARROWMOVECYCLE = 0.33333334F;
      public static final int BACKTOX = 25;
      public static final int BACKTOY = 97;
      public static final int BUTTONAENDY = 335;
      public static final float BUTTONAPPEARTIME = 0.4F;
      public static final int BUTTONASTARTY = -248;
      public static final int BUTTONAX = 127;
      public static final int BUTTONBENDY = 249;
      public static final int BUTTONBSTARTY = -334;
      public static final int BUTTONBX = 127;
      public static final int BUTTONDELTAY = 583;
      public static final float BUTTONFONTAPPEAR = 0.16666667F;
      public static final int LEVELCOMPLETED = 2;
      public static final int LEVELLOCK = 0;
      public static final int LEVELUNCOMPLETED = 1;
      public static final int LOGOX = 285;
      public static final int LOGOY = 165;
      public static final int PLAYBUTTONENDY = 180;
      public static final int PLAYBUTTONSTARTY = -66;
      public static final int PLAYBUTTONX = 282;
      public static final int SCENCEENDY = 423;
      public static final int SCENCELEFTX = -407;
      public static final int SCENCERIGHTX = 553;
      public static final int SCENCESTARTY = -160;
      public static final int SCENCEX = 73;
      public static final float SCEOUTTIME = 0.5F;
      public static final float SLIDETIME = 0.16666667F;
      public static final int STOREX = 391;
      public static final int STOREY = 98;
      public final static Color TIPCOLOR = UIMainMenuState.MainConst.TIPCOLOR;
      public static final float TIPDELAY = 1.2F;
      public static final float TIPX = 83.0F;
      public static final float TIPY = 200.0F;
      public static final int WORDLEFTTOPX = 22;
      public static final int WORDLEFTTOPY = 193;


      public static int checkLevelUnlock(int var0) {
         return var0 > 1 + Settings.levelPassed?0:(Settings.levelCompeleted[var0 - 1]?2:1);
      }
   }

   private class DrageChangePanel extends DragListener {

      public int threshold;
      UISelectLevelState wThis;


      private DrageChangePanel() {
         this.threshold = 160;
         this.wThis = UISelectLevelState.this;
      }

      public void drag(InputEvent var1, float var2, float var3, int var4) {
         float var5 = this.getDeltaX();
         this.wThis.container.translate(-var5, 0.0F);
         if(var5 < 0.0F) {
            var5 = -var5;
         }

         UISelectLevelState.this.buttonLevelA.translate(0.0F, -var5);
         UISelectLevelState.this.buttonLevelB.translate(0.0F, 1.3F * -var5);
         float var6 = ((float)this.threshold - Math.abs(var2 - this.getTouchDownX())) / (float)this.threshold;
         if(var6 < 0.0F) {
            var6 = 0.0F;
         }

         UISelectLevelState.this.buttonLeft.addAction(Actions.alpha(var6));
         UISelectLevelState.this.buttonRight.addAction(Actions.alpha(var6));
      }

      public void dragStop(InputEvent var1, float var2, float var3, int var4) {
         if(var2 - this.getTouchDownX() > (float)this.threshold) {
            if(this.wThis.missionPreview.chooseLastActor()) {
               UISelectLevelState.this.moveSenceOut(false);
            } else {
               UISelectLevelState.this.senceMoveBack();
            }
         } else if(var2 - this.getTouchDownX() < (float)(-this.threshold)) {
            if(this.wThis.missionPreview.chooseNextActor()) {
               UISelectLevelState.this.moveSenceOut(true);
            } else {
               UISelectLevelState.this.senceMoveBack();
            }
         } else {
            UISelectLevelState.this.senceMoveBack();
         }
      }
   }

   private class contorlsLockSetter implements GameEvent.GameEventListener {

      private contorlsLockSetter() {}

      public void handle(Object var1, Object var2) {
         if(var1 instanceof SelectActor && var2 instanceof SelectActor.ChangeEventArg) {
            ;
         }

      }
   }

   private class LevArrowButton extends ImageButton implements GameEvent.GameEventListener {

      private final boolean isLeft;


      public LevArrowButton(boolean var2, Skin var3, String var4) {
         super(var3, var4);
         this.isLeft = var2;
         this.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
               UISelectLevelState.this.screen.playButtonSound();
               if(UISelectLevelState.this.missionPreview != null) {
                  if(!LevArrowButton.this.isLeft) {
                     if(UISelectLevelState.this.missionPreview.chooseNextActor()) {
                        UISelectLevelState.this.moveSenceOut(true);
                        return;
                     }

                     UISelectLevelState.this.senceMoveBack();
                     return;
                  }

                  if(!UISelectLevelState.this.missionPreview.chooseLastActor()) {
                     UISelectLevelState.this.senceMoveBack();
                     return;
                  }

                  UISelectLevelState.this.moveSenceOut(false);
               }

            }
         });
      }

      public void handle(Object var1, Object var2) {
         if(var1 instanceof SelectActor && var2 instanceof SelectActor.ChangeEventArg) {
            SelectActor.ChangeEventArg var3 = (SelectActor.ChangeEventArg)var2;
            boolean var4;
            if(true && (!this.isLeft || var3.choose <= 0)) {
               var4 = false;
            } else {
               var4 = true;
            }

            boolean var5;
            if(!var4 && (this.isLeft || var3.choose >= -1 + var3.size)) {
               var5 = false;
            } else {
               var5 = true;
            }

            this.setVisible(var5);
            this.addAction(Actions.fadeIn(1.0F));
         }

      }
   }

   private class StoreMoveButtonInterface implements UIStoreActorGroup.MovingButtonInterface {

      public int level;


      private StoreMoveButtonInterface() {}

      public void buttonClicked(int var1, int var2) {
         if(var1 == 0 && var2 == -1) {
            ;
         }

         if(var1 == 0 && var2 != -1) {
            UISelectLevelState.this.storeActors.clearSelectedIndex();
            Action var3 = UISelectLevelState.this.storeActors.getMoveOutAction(0.0F);
            SetTextAction.setText("PLAY");
            UISelectLevelState.this.storeActors.getMoveInAction(0.0F);
            UISelectLevelState.this.storeActors.addActionToMovingButton(Actions.sequence(var3));
         }

      }

      public void playButtonSound() {
         UISelectLevelState.this.screen.playButtonSound();
      }
   }
}
