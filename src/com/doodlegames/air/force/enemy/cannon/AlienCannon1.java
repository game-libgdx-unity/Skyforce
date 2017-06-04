package com.doodlegames.air.force.enemy.cannon;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.resource.Assets_cannons;

public class AlienCannon1 extends Enemy {

   private static final float BULLETSPEED = 10.0F;
   public static final int DEFAULTHEALTHYDEGREE = 5;
   public static int HEIGHT = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 1.0F;
   public static final int WIDTH;
   private static final Vector2 bulletPostion = new Vector2(15.0F, 2.0F);
   public static final Vector2 cannonOrigin = new Vector2(15.0F, 12.0F);
   public static TextureAtlas.AtlasRegion canonBroTextureRegion = Assets_cannons.atlas_canons.findRegion("alien_canon_1_broken");
   public static TextureAtlas.AtlasRegion canonTextureRegion = Assets_cannons.atlas_canons.findRegion("alien_canon", 1);
   public static final Vector2 velBullet = new Vector2(0.0F, -400.0F);
   float bulletActionVel;
   private Vector2 rotateV;
   private Vector2 thisBulletPostion;
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

   public AlienCannon1(World var1, float var2, float var3) {
      super(var1, 5, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.origin.set(cannonOrigin);
      this.thisBulletPostion = new Vector2(bulletPostion);
      this.thisVelBullet = new Vector2(velBullet);
      this.rotateV = new Vector2();
      this.bulletActionVel = this.thisVelBullet.len();
      this.enemyRegion = canonTextureRegion;
      this.enemyBroRegion = canonBroTextureRegion;
   }

   public static void loadResource() {
      canonTextureRegion = Assets_cannons.atlas_canons.findRegion("alien_canon", 1);
      canonBroTextureRegion = Assets_cannons.atlas_canons.findRegion("alien_canon_1_broken");
   }

   public void changeAngle(float var1) {
      if(this.angle != var1) {
         this.angle = var1;
         this.rotateV.set(bulletPostion).sub(this.origin);
         Enemy.getRotateOffset(this.rotateV, this.angle).add(bulletPostion);
         this.thisBulletPostion.set(this.rotateV);
         this.thisVelBullet.set(velBullet).rotate(this.angle);
      }
   }

   public float getTimeOfCrash() {
      return 1.0F;
   }

   public void setAngle(float var1) {
      super.setAngle(var1);
   }

   public void setBulletActionVel(float var1) {
      this.bulletActionVel = var1;
   }

   public void shooting() {
      if(!this.isCrashed()) {
         this.shooting(EnemyBullet1.alienBulletGen);
      }
   }

   public void shooting(BulletGenerator var1) {
      if(!this.isCrashed()) {
         this.addEnemyAction(EnemyTools.enemyShoot(this, var1, this.bulletActionVel, this.thisBulletPostion));
      }
   }

   public void update(float var1) {
      super.update(var1);
   }

   public void updateShooting(float var1) {}
}
