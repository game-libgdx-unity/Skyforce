package com.doodlegames.air.force.screen.screenActor;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.doodlegames.air.force.game.BigBackGround;
import com.doodlegames.air.force.game.SmallBackground;
import com.doodlegames.air.force.resource.Assets_UI;
import com.doodlegames.air.force.screen.XScreen;

public class UIBackground extends Actor {

   BigBackGround bigBackground;
   Camera cam;
   float currentPosition = 0.0F;
   Drawable glass;
   SmallBackground smallBackground;


   public UIBackground() {
      this.smallBackground = Assets_UI.smallBackGround;
      this.bigBackground = Assets_UI.bigBackGround;
      Sprite var1 = new Sprite(XScreen.getTexture());
      var1.setColor(0.16470589F, 0.23921569F, 0.21960784F, 0.2F);
      this.glass = new SpriteDrawable(var1);
      this.setPosition(0.0F, 0.0F);
      this.cam = new OrthographicCamera(480.0F, 800.0F);
      this.cam.position.set(320.0F, 400.0F, 0.0F);
      this.cam.update();
   }

   public void act(float var1) {
      this.currentPosition += -53.333332F * var1;
      if(this.bigBackground != null) {
         this.bigBackground.update(var1, this.currentPosition);
      }

      if(this.smallBackground != null) {
         this.smallBackground.update(var1, this.currentPosition);
      }

   }

   public void draw(SpriteBatch var1, float var2) {
      var1.setColor(1.0F, 1.0F, 1.0F, 1.0F * var2);
      var1.setProjectionMatrix(this.cam.combined);
      if(this.bigBackground != null) {
         this.bigBackground.render(var1);
      }

      if(this.smallBackground != null) {
         this.smallBackground.render(var1);
      }

      var1.setProjectionMatrix(super.getStage().getCamera().combined);
      this.glass.draw(var1, 0.0F, 0.0F, this.getWidth(), this.getHeight());
   }
}
