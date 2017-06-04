package com.doodlegames.air.force.enemy.level5;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.Gun.BulletGunNazi;
import com.doodlegames.air.force.game.Gun.BulletShooter;
import com.doodlegames.air.force.game.Gun.OwnNaziCannon;
import com.doodlegames.air.force.game.bullet.EnemyBullet2;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.resource.Assets_cannons;
import com.doodlegames.air.force.utils.Clock;

public class BossLv5AlienCanon extends Enemy {

   public static final Vector2 BULLETLPOSITION;
   public static final Vector2 BULLETRPOSITION;
   private static final float BULLETSPEED = -200.0F;
   public static final int DEFAULTHEALTHYDEGREE = 5;
   public static  int LHEIGHT = 0;
   public static  int LWIDTH = 0;
   public static  int RHEIGHT = 0;
   public static  int RWIDTH = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 0.01F;
   public static TextureAtlas.AtlasRegion canonBroLRegion = Assets_cannons.atlas_canons.findRegion("alien_canon_3_l_broken");
   public static TextureAtlas.AtlasRegion canonBroRRegion = Assets_cannons.atlas_canons.findRegion("alien_canon_3_r_broken");
   public static TextureAtlas.AtlasRegion canonLRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_canon_3_l");
   public static TextureAtlas.AtlasRegion canonRRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_canon_3_r");
   public BulletShooter bulletGun;
   private final Vector2 bulletPosition;
   private final TextureAtlas.AtlasRegion canonRegion;


   static {
      int var0;
      if(canonLRegion.rotate) {
         var0 = canonLRegion.getRegionHeight();
      } else {
         var0 = canonLRegion.getRegionWidth();
      }

      LWIDTH = var0;
      int var1;
      if(canonLRegion.rotate) {
         var1 = canonLRegion.getRegionWidth();
      } else {
         var1 = canonLRegion.getRegionHeight();
      }

      LHEIGHT = var1;
      int var2;
      if(canonRRegion.rotate) {
         var2 = canonRRegion.getRegionHeight();
      } else {
         var2 = canonRRegion.getRegionWidth();
      }

      RWIDTH = var2;
      int var3;
      if(canonRRegion.rotate) {
         var3 = canonRRegion.getRegionWidth();
      } else {
         var3 = canonRRegion.getRegionHeight();
      }

      RHEIGHT = var3;
      BULLETLPOSITION = new Vector2(4.0F, 10.0F);
      BULLETRPOSITION = new Vector2(50.0F, 10.0F);
   }

   public BossLv5AlienCanon(World var1, float var2, float var3, boolean var4) {

      super(var1, 5, var2, var3, var4?LWIDTH:RWIDTH, var4?LHEIGHT:RHEIGHT);
      Vector2 var8;
      if(var4) {
         var8 = BULLETLPOSITION;
      } else {
         var8 = BULLETRPOSITION;
      }

      this.bulletPosition = var8;
      this.bulletGun = new BulletGunNazi(new OwnNaziCannon() {
         public float getCannonAngle() {
            return 0.0F;
         }
         public float getCannonOriginX() {
            return 0.0F;
         }
         public float getCannonOriginY() {
            return 0.0F;
         }
      }, this, var1, EnemyBullet2.alienBulletGen, new Clock(5.0F, 0.2F, (byte)6), (new Vector2(this.bulletPosition)).add(this.bounds.x, this.bounds.y), new float[]{0.0F, -200.0F});
      TextureAtlas.AtlasRegion var9;
      if(var4) {
         var9 = canonLRegion;
      } else {
         var9 = canonRRegion;
      }

      this.canonRegion = var9;
      this.enemyRegion = this.canonRegion;
      this.enemyBroRegion = null;
   }

   public static void loadResource() {
      canonLRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_canon_3_l");
      canonRRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_canon_3_r");
      canonBroLRegion = Assets_cannons.atlas_canons.findRegion("alien_canon_3_l_broken");
      canonBroRRegion = Assets_cannons.atlas_canons.findRegion("alien_canon_3_r_broken");
   }

   public float getTimeOfCrash() {
      return 0.01F;
   }

   public void updateShooting(float var1) {
      this.bulletGun.getShootingPoint().set(this.bulletPosition).add(this.bounds.x, this.bounds.y);
      this.bulletGun.updateShooting(var1);
   }
}
