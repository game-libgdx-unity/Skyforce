package com.doodlegames.air.force.enemy.cannon;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.resource.Assets_cannons;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;

public class Cannon5 extends Enemy {

   private static final float BULLETSPEED = 10.0F;
   public static final int DEFAULTHEALTHYDEGREE = 10;
 private static final float TIMEOFPLAYCRASHANIMATION = 1.0F;
   public static final Vector2 bulletPostion1 = new Vector2(13.0F, 14.0F);
   public static final Vector2 bulletPostion2 = new Vector2(56.0F, 14.0F);
   public static final Vector2 cannonOrigin = new Vector2(35.0F, 18.0F);
   public static TextureRegion canonBrokenTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("nazi_high_robot_broken");
   public static TextureRegion canonTextureRegion = Assets_cannons.atlas_canons.findRegion("nazi_canon", 5);
   public static final Vector2 velBullet = new Vector2(0.0F, -400.0F);
   public static final int REGIONHEIGHT = RegionUtilFunctions.getRegionHeight(canonTextureRegion);
   public static final int REGIONWIDTH = RegionUtilFunctions.getRegionWidth(canonTextureRegion);
   float bulletActionVel;
   private Clock clockPursue = new Clock(0.05F);
   Vector2 rotateV1 = new Vector2();
   Vector2 rotateV2 = new Vector2();
   private Vector2 thisBulletPostion1;
   private Vector2 thisBulletPostion2;
   private Vector2 thisVelBullet;


   public Cannon5(World var1, float var2, float var3) {
      super(var1, 10, var2, var3, (float)REGIONWIDTH / 1.0F, (float)REGIONHEIGHT / 1.0F);
      this.origin.set(cannonOrigin);
      this.thisBulletPostion1 = new Vector2(bulletPostion1);
      this.thisBulletPostion2 = new Vector2(bulletPostion2);
      this.thisVelBullet = new Vector2(velBullet);
      this.bulletActionVel = this.thisVelBullet.len();
      this.enemyRegion = canonTextureRegion;
      this.enemyBroRegion = canonBrokenTextureRegion;
   }

   public static void loadResource() {
      canonTextureRegion = Assets_cannons.atlas_canons.findRegion("nazi_canon", 5);
      canonBrokenTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("nazi_high_robot_broken");
   }

   public void changeAngle(float var1) {
      if(this.angle != var1) {
         this.angle = var1;
         this.rotateV1.set(bulletPostion1).sub(this.origin);
         this.rotateV2.set(bulletPostion2).sub(this.origin);
         Enemy.getRotateOffset(this.rotateV1, this.angle).add(bulletPostion1);
         Enemy.getRotateOffset(this.rotateV2, this.angle).add(bulletPostion2);
         this.thisBulletPostion1.set(this.rotateV1);
         this.thisBulletPostion2.set(this.rotateV2);
         this.thisVelBullet.set(velBullet).rotate(this.angle);
      }
   }

   public float getTimeOfCrash() {
      return 1.0F;
   }

   public void setAngle(float var1) {
      super.setAngle(var1);
   }

   public void shooting() {
      if(!this.isCrashed()) {
         this.shooting(EnemyBullet1.naziBulletGen);
      }
   }

   public void shooting(BulletGenerator var1) {
      if(!this.isCrashed()) {
         this.addEnemyAction(EnemyTools.enemyShoot(this, var1, this.bulletActionVel, this.thisBulletPostion1));
         this.addEnemyAction(EnemyTools.enemyShoot(this, var1, this.bulletActionVel, this.thisBulletPostion2));
      }
   }

   public void update(float var1) {
      super.update(var1);
   }

   public void updateShooting(float var1) {}
}
