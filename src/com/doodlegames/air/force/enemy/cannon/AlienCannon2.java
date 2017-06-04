package com.doodlegames.air.force.enemy.cannon;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.resource.Assets_cannons;

public class AlienCannon2 extends Enemy {

   public static final Vector2[] BULLETPOSITION;
   public static final int BULLETPOSITIONNUM = 2;
   public static final int DEFAULTHEALTHYDEGREE = 5;
   public static int HEIGHT = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 1.0F;
   public static final int WIDTH;
   public static final Vector2 cannonOrigin;
   private static TextureAtlas.AtlasRegion canonBroTextureRegion;
   private static TextureAtlas.AtlasRegion canonTextureRegion;
   public static final Vector2 velBullet = new Vector2(0.0F, -400.0F);
   float bulletActionVel;
   private Vector2 rotateV;
   private Vector2[] thisBulletPosition;
   private Vector2 thisVelBullet;


   static {
      Vector2[] var0 = new Vector2[]{new Vector2(7.0F, 2.0F), new Vector2(28.0F, 2.0F)};
      BULLETPOSITION = var0;
      cannonOrigin = new Vector2(18.0F, 27.0F);
      canonTextureRegion = Assets_cannons.atlas_canons.findRegion("alien_canon", 2);
      canonBroTextureRegion = Assets_cannons.atlas_canons.findRegion("alien_canon_2_broken");
      int var1;
      if(canonTextureRegion.rotate) {
         var1 = canonTextureRegion.getRegionHeight();
      } else {
         var1 = canonTextureRegion.getRegionWidth();
      }

      WIDTH = var1;
      int var2;
      if(canonTextureRegion.rotate) {
         var2 = canonTextureRegion.getRegionWidth();
      } else {
         var2 = canonTextureRegion.getRegionHeight();
      }

      HEIGHT = var2;
   }

   public AlienCannon2(World var1, float var2, float var3) {
      super(var1, 5, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.origin.set(cannonOrigin);
      this.thisBulletPosition = new Vector2[2];

      for(int var5 = 0; var5 < 2; ++var5) {
         this.thisBulletPosition[var5] = new Vector2();
         this.thisBulletPosition[var5].set(BULLETPOSITION[var5]);
      }

      this.thisVelBullet = new Vector2(velBullet);
      this.bulletActionVel = this.thisVelBullet.len();
      this.rotateV = new Vector2();
      this.enemyRegion = canonTextureRegion;
      this.enemyBroRegion = canonBroTextureRegion;
   }

   public static void loadResource() {
      canonTextureRegion = Assets_cannons.atlas_canons.findRegion("alien_canon", 2);
      canonBroTextureRegion = Assets_cannons.atlas_canons.findRegion("alien_canon_2_broken");
   }

   public void changeAngle(float var1) {
      if(this.angle != var1) {
         this.angle = var1;

         for(int var2 = 0; var2 < 2; ++var2) {
            this.rotateV.set(BULLETPOSITION[var2]).sub(this.origin);
            Enemy.getRotateOffset(this.rotateV, this.angle).add(BULLETPOSITION[var2]);
            this.thisBulletPosition[var2].set(this.rotateV);
         }

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
         for(int var2 = 0; var2 < this.thisBulletPosition.length; ++var2) {
            this.addEnemyAction(EnemyTools.enemyShoot(this, var1, this.bulletActionVel, this.thisBulletPosition[var2]));
         }
      }

   }

   public void update(float var1) {
      super.update(var1);
   }

   public void updateShooting(float var1) {}
}
