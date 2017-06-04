package com.doodlegames.air.force.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.utils.Clock;

public class AEnemy extends Enemy {

   private static final float BULLETSPEED = 10.0F;
   public static final int DEFAULTHEALTHYDEGREE = 10;
   private static final float TIMEOFPLAYCRASHANIMATION = 4.0F;
   private static TextureRegion canonTextureRegion = null;
   private static TextureRegion enemyTextureRegion = null;
   private Clock clockPursue = new Clock(2.0F);
   private Clock clockShooting = new Clock(5.0F, 0.2F, (byte)4);


   public AEnemy(World var1, float var2, float var3) {
      super(var1, 10, var2, var3, (float)enemyTextureRegion.getRegionWidth() / 1.0F, (float)enemyTextureRegion.getRegionHeight() / 1.0F);
   }

   public float getTimeOfCrash() {
      return 4.0F;
   }

   public void render(SpriteBatch var1) {
      switch(this.state) {
      case 0:
         if(this.isBeHiting) {
            Color var2 = var1.getColor();
            var1.setColor(1.0F, 0.7F, 0.7F, 1.0F);
            var1.draw(enemyTextureRegion, this.position.x - this.bounds.width / 2.0F, this.position.y - this.bounds.height / 2.0F, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, 1.0F, 1.0F, this.angle);
            var1.setColor(var2);
            return;
         }

         var1.draw(enemyTextureRegion, this.position.x - this.bounds.width / 2.0F, this.position.y - this.bounds.height / 2.0F, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, 1.0F, 1.0F, this.angle);
         return;
      default:
      }
   }

   public void renderShadow(SpriteBatch var1) {
      Color var2 = var1.getColor();
      var1.setColor(0.0F, 0.0F, 0.0F, 0.5F);
      var1.draw(enemyTextureRegion, this.position.x - this.bounds.width / 2.0F + shadowOffset.x, this.position.y - this.bounds.height / 2.0F + shadowOffset.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, DynamicGameObject.shadowScale.x, DynamicGameObject.shadowScale.y, this.angle);
      var1.setColor(var2.r, var2.g, var2.b, 1.0F);
   }

   public void update(float var1) {
      super.update(var1);
   }

   protected void updateDynamicParameter(float var1) {
      super.updateDynamicParameter(var1);
   }

   public void updateShooting(float var1) {
      if(this.clockShooting.isFired()) {
         ;
      }

   }
}
