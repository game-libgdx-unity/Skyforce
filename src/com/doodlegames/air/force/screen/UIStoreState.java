package com.doodlegames.air.force.screen;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.doodlegames.air.force.resource.Assets_UI;
import com.doodlegames.air.force.screen.BackProcessor;
import com.doodlegames.air.force.screen.UIScreen;
import com.doodlegames.air.force.screen.UIScreenState;
import com.doodlegames.air.force.screen.UIStoreActorGroup;

public class UIStoreState extends UIScreenState {

   private ImageButton backButton;
   private ImageButton levelButton;
   private Group levelButtonContainer;
   private UIStoreActorGroup storeActors;
   private Group storeGroup;


   public UIStoreState(UIScreen var1) {
      super(var1);
      this.storeActors = new UIStoreActorGroup(this.skin);
      this.storeGroup = new Group();
      this.storeGroup.translate(0.0F, -800.0F);
      this.constructBackButton();
      this.constructLevelButton();
      this.storeActors.setItemSelectedFuns(new UIStoreActorGroup.ItemSelected() {
         public void itemSelected(int var1, int var2, int var3) {
            UIStoreState.this.screen.playButtonSound();
            if(var2 == -1) {
               UIStoreState.this.storeActors.moveMovingButtonOut(0.0F);
            } else {
               Object var4;
               if(UIStoreState.this.storeActors.moveButtonIsOut()) {
                  var4 = Actions.delay(0.0F);
               } else {
                  var4 = UIStoreState.this.storeActors.getMoveOutAction(0.0F);
               }

               Action var5 = UIStoreState.this.storeActors.getMoveInAction(0.0F);
               UIStoreState.this.storeActors.addActionToMovingButton(Actions.sequence((Action)var4, var5));
            }
         }
         public void switchPane(int var1, int var2) {
            this.itemSelected(var1, var2, var2);
         }
      });
      this.storeActors.setMoveButtonInterface(new UIStoreActorGroup.MovingButtonInterface() {
         public void buttonClicked(int var1, int var2) {}
         public void playButtonSound() {
            UIStoreState.this.screen.playButtonSound();
         }
      });
      this.storeActors.setMoveButtonSelectedText("upgrade");
   }

   public static void addResource(Skin var0) {
      UIStoreActorGroup.addResource(var0, Assets_UI.atlas);
   }

   private void backToMain() {
      Group var1 = this.screen.getStateGroup();
      this.storeGroup.addAction(Actions.moveTo(0.0F, -800.0F, 0.5F, Interpolation.pow2Out));
      var1.addAction(Actions.delay(0.5F, new Action() {
         public boolean act(float var1) {
            UIStoreState.this.screen.askForStateChange(0);
            return true;
         }
      }));
      this.backButton.setTouchable(Touchable.disabled);
      this.backButton.addAction(Actions.fadeOut(0.5F));
      this.levelButtonContainer.setTouchable(Touchable.disabled);
      this.levelButtonContainer.addAction(Actions.fadeOut(0.5F));
   }

   private void constructBackButton() {
      this.backButton = new ImageButton(this.skin, "LevelBackTo");
      this.backButton.setPosition(25.0F, 97.0F);
      this.backButton.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            UIStoreState.this.screen.playBackSound();
            UIStoreState.this.backToMain();
         }
      });
      this.backButton.getColor().a = 0.0F;
      this.backButton.setTouchable(Touchable.disabled);
   }

   private void constructLevelButton() {
      this.levelButtonContainer = new Group();
      this.levelButtonContainer.setPosition(391.0F, 98.0F);
      ImageButton.ImageButtonStyle var1 = new ImageButton.ImageButtonStyle();
      var1.imageUp = this.skin.getDrawable("go_button_normal");
      var1.imageDown = this.skin.getDrawable("go_button_press");
      ImageButton var2 = new ImageButton(var1);
      var2.setPosition(0.0F, 0.0F);
      var2.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            UIStoreState.this.screen.playButtonSound();
            Group var3 = UIStoreState.this.screen.getStateGroup();
            UIStoreState.this.storeGroup.addAction(Actions.moveTo(0.0F, -800.0F, 0.5F, Interpolation.pow2Out));
            var3.addAction(Actions.delay(0.5F, new Action() {
               public boolean act(float var1) {
                  UIStoreState.this.screen.askForStateChange(1);
                  return true;
               }
            }));
            UIStoreState.this.backButton.addAction(Actions.sequence(Actions.touchable(Touchable.disabled), Actions.fadeOut(0.5F)));
            UIStoreState.this.levelButtonContainer.addAction(Actions.sequence(Actions.touchable(Touchable.disabled), Actions.fadeOut(0.5F)));
         }
      });
      this.levelButtonContainer.getColor().a = 0.0F;
      this.levelButtonContainer.setTouchable(Touchable.disabled);
      this.levelButton = var2;
   }

   public void backPressed() {
      if(this.backButton.getTouchable() == Touchable.enabled) {
         this.backToMain();
         BackProcessor.setEmptyBackFun();
      }

   }

   public void switchToThisState(UIScreen var1) {
      super.switchToThisState(var1);
      Group var2 = var1.getStateGroup();
      Group var3 = var1.getUIBottomGroup();
      var3.addActor(this.backButton);
      this.levelButtonContainer.clear();
      if(!var1.currentFighterLock()) {
         this.levelButtonContainer.addActor(this.levelButton);
      }

      var3.addActor(this.levelButtonContainer);
      this.storeActors.addPaneToGroup(this.storeGroup);
      var2.addActor(this.storeGroup);
      this.storeGroup.addAction(Actions.moveTo(0.0F, 0.0F, 0.5F, Interpolation.pow2Out));
      this.backButton.addAction(Actions.sequence(Actions.fadeIn(0.5F), Actions.touchable(Touchable.enabled)));
      this.levelButtonContainer.addAction(Actions.sequence(Actions.fadeIn(0.5F), Actions.touchable(Touchable.enabled)));
      this.storeActors.moveMovingButton(-66.0F, 0.0F);
      this.storeActors.clearSelectedIndex();
      this.storeActors.switchPaneToCoinPane();
      this.storeActors.updateTitleMoneyLabel();
   }
}
