package com.doodlegames.air.force.enemy.cannon;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.resource.Assets_cannons;

public class AlienCannon3R extends Enemy {

   public static final Vector2 BULLETPOSITION = new Vector2(50.0F, 10.0F);
   public static final int BULLETPOSITIONNUM = 1;
   public static final int DEFAULTHEALTHYDEGREE = 5;
   public static int HEIGHT = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 1.0F;
   public static final float VELBULLET = 250.0F;
   public static final int WIDTH;
   public static TextureAtlas.AtlasRegion canonBroTextureRegion = Assets_cannons.atlas_canons.findRegion("alien_canon_3_r_broken");
   public static TextureAtlas.AtlasRegion canonTextureRegion = Assets_cannons.atlas_canons.findRegion("alien_canon_3_r");
   public float bulletActionVel = 250.0F;


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

   public AlienCannon3R(World var1, float var2, float var3) {
      super(var1, 5, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.enemyRegion = canonTextureRegion;
      this.enemyBroRegion = canonBroTextureRegion;
   }

   public static void loadResource() {
      canonTextureRegion = Assets_cannons.atlas_canons.findRegion("alien_canon_3_r");
      canonBroTextureRegion = Assets_cannons.atlas_canons.findRegion("alien_canon_3_r_broken");
   }

   public float getTimeOfCrash() {
      return 1.0F;
   }

   public void setBulletActionVel(float var1) {
      this.bulletActionVel = var1;
   }

   public void shooting() {
      if(!this.isCrashed()) {
         this.shooting(EnemyBullet1.alienBulletGen, 0.0F);
      }
   }

   public void shooting(BulletGenerator var1, float var2) {
      if(!this.isCrashed()) {
         this.addEnemyAction(EnemyTools.enemyShoot(this, var2, var1, this.bulletActionVel, BULLETPOSITION));
      }
   }

   public void update(float var1) {
      super.update(var1);
   }

   public void updateShooting(float var1) {}
}
