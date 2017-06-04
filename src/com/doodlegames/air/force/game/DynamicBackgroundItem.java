package com.doodlegames.air.force.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Settings;

public class DynamicBackgroundItem extends DynamicGameObject {

   private final Animation backgroundAnimation;
   private float playTime;


   public DynamicBackgroundItem(Animation var1, float var2, float var3, float var4, float var5) {
      super(var2, var3, var4, var5);
      this.backgroundAnimation = var1;
      this.playTime = 0.0F;
      this.velocity.set(Settings.backgroundVelocity);
   }

   public void render(SpriteBatch var1) {
      var1.draw(this.backgroundAnimation.getKeyFrame(this.playTime), this.position.x - this.bounds.width / 2.0F, this.position.y - this.bounds.height / 2.0F, this.bounds.width, this.bounds.height);
   }

   public void update(float var1) {
      this.velocity.y = Settings.backgroundVelocity.y;
      super.update(var1);
      this.playTime += var1;
   }
}
