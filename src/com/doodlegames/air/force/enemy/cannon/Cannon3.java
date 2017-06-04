package com.doodlegames.air.force.enemy.cannon;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.resource.Assets_cannons;
import com.doodlegames.air.force.utils.RegionUtilFunctions;

public class Cannon3 extends Enemy {

   private static final float BULLETSPEED = 300.0F;
   public static final int DEFAULTHEALTHYDEGREE = 10;
  private static final float TIMEOFPLAYCRASHANIMATION = 1.0F;
   public static final Vector2 bulletPostion1 = new Vector2(15.5F, 0.0F);
   public static final Vector2 bulletPostion2 = new Vector2(31.0F, 15.5F);
   public static final Vector2 bulletPostion3 = new Vector2(15.5F, 31.0F);
   public static final Vector2 bulletPostion4 = new Vector2(0.0F, 15.5F);
   public static final Vector2 cannonOrigin = new Vector2(15.5F, 15.5F);
   public static TextureRegion canonBrokenTextureRegion = Assets_cannons.atlas_canons.findRegion("nazi_canon_3_broken");
   public static TextureRegion canonTextureRegion = Assets_cannons.atlas_canons.findRegion("nazi_canon", 3);
   public static final int REGIONHEIGHT = RegionUtilFunctions.getRegionHeight(canonTextureRegion);
   public static final int REGIONWIDTH = RegionUtilFunctions.getRegionWidth(canonTextureRegion);
   public static final Vector2 velBullet = new Vector2(0.0F, -400.0F);
   private float bulletSpeed = 300.0F;
   Vector2 offsetVector1 = new Vector2();
   Vector2 offsetVector2 = new Vector2();
   Vector2 offsetVector3 = new Vector2();
   Vector2 offsetVector4 = new Vector2();
   Vector2 tempVector1 = new Vector2();
   Vector2 tempVector2 = new Vector2();
   private Vector2 thisBulletPostion1;
   private Vector2 thisBulletPostion2;
   private Vector2 thisBulletPostion3;
   private Vector2 thisBulletPostion4;
   Vector2 vel = new Vector2();


   public Cannon3(World var1, float var2, float var3) {
      super(var1, 10, var2, var3, (float)REGIONWIDTH / 1.0F, (float)REGIONHEIGHT / 1.0F);
      this.origin.set(cannonOrigin);
      this.thisBulletPostion1 = new Vector2(bulletPostion1);
      this.thisBulletPostion2 = new Vector2(bulletPostion2);
      this.thisBulletPostion3 = new Vector2(bulletPostion3);
      this.thisBulletPostion4 = new Vector2(bulletPostion4);
      this.enemyRegion = canonTextureRegion;
      this.enemyBroRegion = canonBrokenTextureRegion;
   }

   public static void loadResource() {
      canonTextureRegion = Assets_cannons.atlas_canons.findRegion("nazi_canon", 3);
      canonBrokenTextureRegion = Assets_cannons.atlas_canons.findRegion("nazi_canon_3_broken");
   }

   public float getTimeOfCrash() {
      return 1.0F;
   }

   public void setBulletSpeed(float var1) {
      this.bulletSpeed = Math.abs(var1);
   }

   public void shooting() {
      this.shooting(EnemyBullet1.naziBulletGen);
   }

   public void shooting(BulletGenerator var1) {
      if(!this.isCrashed()) {
         this.tempVector1.set(bulletPostion1);
         this.tempVector2.set(0.0F, 0.0F);
         this.tempVector1.sub(this.origin);
         this.tempVector2.set(this.tempVector1);
         this.offsetVector1.set(this.tempVector1.rotate(this.angle).sub(this.tempVector2));
         this.offsetVector2.set(-this.offsetVector1.y, this.offsetVector1.x);
         this.offsetVector3.set(-this.offsetVector2.y, this.offsetVector2.x);
         this.offsetVector4.set(-this.offsetVector3.y, this.offsetVector3.x);
         this.thisBulletPostion1.set(bulletPostion1).add(this.offsetVector1);
         this.thisBulletPostion2.set(bulletPostion2).add(this.offsetVector2);
         this.thisBulletPostion3.set(bulletPostion3).add(this.offsetVector3);
         this.thisBulletPostion4.set(bulletPostion4).add(this.offsetVector4);
         this.vel.set(this.bulletSpeed * MathUtils.cosDeg(this.angle - 90.0F), this.bulletSpeed * MathUtils.sinDeg(this.angle - 90.0F));
         Enemy.bullets.add(var1.getABullet(this, this.world, this.bounds.x + this.thisBulletPostion1.x, this.bounds.y + this.thisBulletPostion1.y, this.vel));
         this.vel.set(-this.vel.y, this.vel.x);
         Enemy.bullets.add(var1.getABullet(this, this.world, this.bounds.x + this.thisBulletPostion2.x, this.bounds.y + this.thisBulletPostion2.y, this.vel));
         this.vel.set(-this.vel.y, this.vel.x);
         Enemy.bullets.add(var1.getABullet(this, this.world, this.bounds.x + this.thisBulletPostion3.x, this.bounds.y + this.thisBulletPostion3.y, this.vel));
         this.vel.set(-this.vel.y, this.vel.x);
         Enemy.bullets.add(var1.getABullet(this, this.world, this.bounds.x + this.thisBulletPostion4.x, this.bounds.y + this.thisBulletPostion4.y, this.vel));
         this.setBulletCount(4 + this.getBulletCount());
      }
   }

   public void update(float var1) {
      super.update(var1);
   }

   public void updateShooting(float var1) {}
}
