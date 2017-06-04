package com.doodlegames.air.force.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.utils.Settings;

public class StaticBackgroundItem extends DynamicGameObject {

   private final TextureRegion backgroundItemRegion;
   public final Sprite sprite;


   public StaticBackgroundItem(Sprite var1, float var2, float var3, float var4, float var5) {
      super(var2, var3, var4, var5);
      this.backgroundItemRegion = null;
      this.velocity.set(Settings.backgroundVelocity);
      this.sprite = var1;
      var1.setBounds(this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
   }

   public StaticBackgroundItem(TextureRegion var1, float var2, float var3, float var4, float var5) {
      super(var2, var3, var4, var5);
      this.backgroundItemRegion = var1;
      this.velocity.set(Settings.backgroundVelocity);
      this.sprite = null;
   }

   public void render(SpriteBatch var1) {
      if(this.backgroundItemRegion != null) {
         var1.draw(this.backgroundItemRegion, this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
      }

      if(this.sprite != null) {
         this.sprite.draw(var1);
      }

   }

   public void update(float var1) {
      this.velocity.y = Settings.backgroundVelocity.y;
      super.update(var1);
      if(this.sprite != null) {
         this.sprite.setY(this.bounds.y);
      }

   }

   public void update(float var1, float var2) {
      Vector2 var3 = this.position;
      var3.y += var1;
      this.bounds.y = this.position.y - this.bounds.height / 2.0F;
      if(this.sprite != null) {
         this.sprite.setY(this.bounds.y);
      }

   }
}
