package com.doodlegames.air.force.game.Gun;

import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.Gun.BulletGun;
import com.doodlegames.air.force.game.Gun.OwnNaziCannon;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.utils.Clock;

public class BulletGunNazi extends BulletGun {

   private float cachAngle;
   private OwnNaziCannon cannonOwner;
   private final Vector2 iniShootPoint;
   private Vector2 originTemp = new Vector2();
   private float velBulletX;
   private float velBulletY;


   public BulletGunNazi(OwnNaziCannon var1, GameObject var2, World var3, BulletGenerator var4, Clock var5, Vector2 var6, float ... var7) {
      super(var2, var3, var4, var5, var6, var7);
      this.iniShootPoint = new Vector2(var6);
      if(this.directionsVel.length >= 1) {
         this.velBulletX = this.directionsVel[0].x;
         this.velBulletY = this.directionsVel[0].y;
      } else {
         this.velBulletX = 0.0F;
         this.velBulletY = -400.0F;
      }

      this.cannonOwner = var1;
   }

   private void rotateShootPoint(float var1) {
      if(!this.shootPointIsAbsolute) {
         float var2 = this.cannonOwner.getCannonOriginX();
         float var3 = this.cannonOwner.getCannonOriginY();
         this.shootPoint.set(this.iniShootPoint);
         DynamicGameObject.RotatePoint(this.shootPoint, this.originTemp.set(var2, var3), var1);
      }
   }

   protected void shootBullets() {
      float var1 = this.cannonOwner.getCannonAngle();
      if(var1 != this.cachAngle) {
         this.cachAngle = var1;
         Vector2[] var2 = this.directionsVel;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            var2[var4].set(this.velBulletX, this.velBulletY).rotate(this.cachAngle);
         }

         this.rotateShootPoint(this.cachAngle);
      }

      super.shootBullets();
   }
}
