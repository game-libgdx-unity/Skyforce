package com.doodlegames.air.force.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.game.BGAction;
import com.doodlegames.air.force.utils.Settings;

public class BGActionLineMove extends BGAction {

   private float deltaY = 0.0F;
   private Vector2 vel = new Vector2();


   public void initalize() {
      this.deltaY = -53.333332F + this.vel.y;
   }

   public void reset() {}

   public void setVel(float var1, float var2) {
      this.vel.set(var1, var2);
   }

   public void update(float var1) {
      super.update(var1);
      float var10000 = this.vel.y;
      float var3 = this.deltaY - Settings.backgroundVelocity.y;
      Sprite var4 = this.item.getSpriteDrawable().getSprite();
      float var5 = var1 * this.vel.x + var4.getX();
      float var6 = var3 * var1 + var4.getY();
      var4.setX(var5);
      var4.setY(var6);
   }
}
