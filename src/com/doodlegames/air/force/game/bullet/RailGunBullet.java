package com.doodlegames.air.force.game.bullet;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.utils.Clock;

public class RailGunBullet extends Bullet {

   private Clock clockFlyAway;
   private Sprite sprite;


   public RailGunBullet(GameObject var1, float var2, float var3) {
      super(var1, var2, var3, var1.bounds.width, 5.0F * var1.bounds.height, new Vector2(0.0F, 0.0F));
      this.damageValue = 1;
      this.sprite = new Sprite(Assets_ShareInAllLevel.test_Bullet_3);
      this.clockFlyAway = null;
      this.sprite.setBounds(this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
   }

   public RailGunBullet(GameObject var1, float var2, float var3, Vector2 var4, int var5) {
      super(var1, var2, var3, (float)Assets_ShareInAllLevel.test_Bullet_3.getRegionWidth() / 1.0F, (float)Assets_ShareInAllLevel.test_Bullet_3.getRegionHeight() / 1.0F, var4);
      this.damageValue = var5;
   }

   public void freeToPool() {}

   public void hitSomething() {}

   public void render(SpriteBatch var1) {
      if(this.isAvalible()) {
         this.sprite.setColor(Color.BLACK);
         this.sprite.draw(var1);
      }
   }

   public void update(float var1) {
      super.update(var1);
      this.sprite.setY(this.bounds.y);
      if(this.clockFlyAway == null) {
         this.clockFlyAway = new Clock(0.0F, 1.0F);
      } else if(this.clockFlyAway.isFired()) {
         this.velocity.y = -160.0F;
         return;
      }

   }
}
