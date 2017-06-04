package com.doodlegames.air.force.enemy.cannon;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.enemy.cannon.Cannon1;
import com.doodlegames.air.force.enemy.cannon.CommonCannon;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.resource.Assets_cannons;

public class Cannon2 extends Enemy {

   private static final float BULLETSPEED = 10.0F;
   public static final int DEFAULTHEALTHYDEGREE = 10;
   public static  int HEIGHT = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 1.0F;
   public static final int WIDTH;
   public static final Vector2 bulletPostion1 = new Vector2(8.0F, 2.0F);
   public static final Vector2 bulletPostion2 = new Vector2(15.0F, 2.0F);
   public static final Vector2 cannonOrigin = new Vector2(12.0F, 20.0F);
   public static TextureAtlas.AtlasRegion canonBrokenTextureRegion = Assets_cannons.atlas_canons.findRegion("nazi_canon_2_broken");
   public static TextureAtlas.AtlasRegion canonTextureRegion = Assets_cannons.atlas_canons.findRegion("nazi_canon", 2);
   public static final Vector2 velBullet = new Vector2(0.0F, -400.0F);
   float bulletActionVel;
   private Vector2 rotateV1;
   private Vector2 rotateV2;
   float secondDelay;
   private Vector2 thisBulletPostion1;
   private Vector2 thisBulletPostion2;
   private Vector2 thisVelBullet;


   static {
      int var0;
      if(canonTextureRegion.rotate) {
         var0 = canonTextureRegion.getRegionHeight();
      } else {
         var0 = canonTextureRegion.getRegionWidth();
      }

      WIDTH = var0;
      int var1;
      if(canonTextureRegion.rotate) {
         var1 = canonTextureRegion.getRegionWidth();
      } else {
         var1 = canonTextureRegion.getRegionHeight();
      }

      HEIGHT = var1;
   }

   public Cannon2(World var1, float var2, float var3) {
      super(var1, 10, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.bulletActionVel = velBullet.y;
      this.secondDelay = 0.0F;
      this.origin.set(cannonOrigin);
      this.thisBulletPostion1 = new Vector2(bulletPostion1);
      this.thisBulletPostion2 = new Vector2(bulletPostion2);
      this.thisVelBullet = new Vector2(velBullet);
      this.rotateV1 = new Vector2();
      this.rotateV2 = new Vector2();
      this.enemyRegion = canonTextureRegion;
      this.enemyBroRegion = canonBrokenTextureRegion;
   }

   public static void loadResource() {
      canonTextureRegion = Assets_cannons.atlas_canons.findRegion("nazi_canon", 2);
      canonBrokenTextureRegion = Assets_cannons.atlas_canons.findRegion("nazi_canon_2_broken");
   }

   public void changeAngle(float var1) {
      if(this.angle != var1) {
         this.angle = var1;
         this.rotateV1.set(Cannon1.bulletPostion1).sub(this.origin);
         this.rotateV2.set(Cannon1.bulletPostion2).sub(this.origin);
         Enemy.getRotateOffset(this.rotateV1, this.angle).add(Cannon1.bulletPostion1);
         Enemy.getRotateOffset(this.rotateV2, this.angle).add(Cannon1.bulletPostion2);
         this.thisBulletPostion1.set(this.rotateV1);
         this.thisBulletPostion2.set(this.rotateV2);
         this.thisVelBullet.set(0.0F, -this.thisVelBullet.len()).rotate(this.angle);
      }
   }

   public float getTimeOfCrash() {
      return 1.0F;
   }

   public void reset(float var1, float var2) {
      super.reset(this.world, 10, var1, var2);
      this.enemyRegion = canonTextureRegion;
      this.enemyBroRegion = canonBrokenTextureRegion;
   }

   public void setAngle(float var1) {
      super.setAngle(var1);
   }

   public void setThisVelBullet(float var1, float var2) {
      this.thisVelBullet.set(var1, var2);
      this.bulletActionVel = this.thisVelBullet.len();
      this.secondDelay = CommonCannon.innerDelay(this.bulletActionVel);
   }

   public void shooting() {
      this.shooting(EnemyBullet1.naziBulletGen);
   }

   public void shooting(BulletGenerator var1) {
      if(!this.isCrashed()) {
         this.addEnemyAction(EnemyTools.enemyShoot(this, var1, this.bulletActionVel, this.thisBulletPostion1));
         this.addEnemyAction(EnemyTools.enemyShoot(this, this.secondDelay, var1, this.bulletActionVel, this.thisBulletPostion2));
      }
   }

   public void update(float var1) {
      super.update(var1);
   }

   public void updateShooting(float var1) {}
}
