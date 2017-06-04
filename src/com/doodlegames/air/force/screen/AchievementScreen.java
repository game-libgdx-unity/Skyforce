package com.doodlegames.air.force.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.doodlegames.air.force.screen.LoadingScreen;
import com.doodlegames.air.force.screen.MainMenuScreen;
import com.doodlegames.air.force.screen.XScreen;
import com.doodlegames.air.force.screen.screenActor.AchiScreenButtonGroup;

public class AchievementScreen extends XScreen {

   private static Skin achievementScreenSkin;
   AchiScreenButtonGroup achieveGroup;
   ImageButton buttonBackToMain;


   public AchievementScreen(Game var1) {
      super(var1, getAchievementScreenSkin());
      this.buttonBackToMain = new ImageButton(this.skin);
      this.buttonBackToMain.setBounds(440.0F, 740.0F, 40.0F, 40.0F);
      this.buttonBackToMain.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            Game var3 = AchievementScreen.this.game;
            var3.setScreen(new LoadingScreen(var3, MainMenuScreen.info, 0.0F, false));
         }
      });
      this.achieveGroup = new AchiScreenButtonGroup(120, 200, 240, 400, 50, this.skin);
      this.screenStage.addActor(this.achieveGroup);
      this.screenStage.addActor(this.buttonBackToMain);
   }

   private static Skin getAchievementScreenSkin() {
      return MainMenuScreen.getMainMenuScreenSkin();
   }
}
