package com.doodlegames.air.force.game.Gun;

import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.Gun.BulletGun;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.utils.Clock;

public class AutoTurningBulletGun extends BulletGun {

   public float angleSpeed;
   protected float currentAngle;


   public AutoTurningBulletGun(GameObject var1, World var2, BulletGenerator var3, Clock var4, Vector2 var5, float var6, float ... var7) {
      super(var1, var2, var3, var4, var5, var7);
      this.angleSpeed = var6;
      this.currentAngle = 0.0F;
   }

   protected void shootBullets() {
      if(this.currentAngle != 0.0F) {
         Vector2[] var1 = this.directionsVel;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            var1[var3].rotate(this.currentAngle);
         }

         this.currentAngle = 0.0F;
      }

      super.shootBullets();
   }

   public void updateShooting(float var1) {
      super.updateShooting(var1);
      this.currentAngle += var1 * this.angleSpeed;
   }
}
