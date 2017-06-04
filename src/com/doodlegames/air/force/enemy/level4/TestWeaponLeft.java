package com.doodlegames.air.force.enemy.level4;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.resource.Assets_level4;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

public class TestWeaponLeft extends Enemy {

   public static final Vector2[] BULLETPOSITION;
   public static final Vector2 BULLETPOSITIONCENTER = new Vector2(24.0F, 19.0F);
   public static final int DEFAULTHEALTHYDEGREE = 40;
   public static  int HEIGHT = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 1.0F;
   public static final Vector2 VELBULLET = new Vector2(0.0F, -400.0F);
   public static final Vector2 VELBULLET1 = new Vector2(-200.0F, -346.41016F);
   public static final Vector2 VELBULLET2 = new Vector2(0.0F, -400.0F);
   public static final Vector2 VELBULLET3 = new Vector2(200.0F, -346.41016F);
   public static final int WIDTH;
   public static TextureAtlas.AtlasRegion canonBroTextureRegion;
   public static TextureAtlas.AtlasRegion canonTextureRegion;
   private float velAngle;
   private Vector2 velBullet;


   static {
      Vector2[] var0 = new Vector2[]{new Vector2(15.0F, 19.0F), new Vector2(17.0F, 13.0F), new Vector2(24.0F, 10.0F), new Vector2(30.0F, 13.0F), new Vector2(33.0F, 19.0F), new Vector2(30.0F, 25.0F), new Vector2(24.0F, 28.0F), new Vector2(17.0F, 25.0F)};
      BULLETPOSITION = var0;
      canonTextureRegion = Assets_level4.atlas_Enemy.findRegion("nazi_test_weapon_left");
      canonBroTextureRegion = Assets_level4.atlas_Enemy.findRegion("nazi_test_weapon_left_broken");
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

   public TestWeaponLeft(World var1, float var2, float var3) {
      super(var1, Settings.lapFixBossDegree(40), var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.velBullet = new Vector2(VELBULLET);
      this.velAngle = 0.0F;
      this.enemyRegion = canonTextureRegion;
      this.enemyBroRegion = canonBroTextureRegion;
   }

   public static void armShootBullets(Enemy var0, int var1, Vector2[] var2, Vector2 var3) {
      switch(var1) {
      case 0:
         float var8 = (float)(-45 + Clock.rand.nextInt(90));
         float var9 = 360.0F / (float)var2.length;

         for(int var10 = 0; var10 < var2.length; ++var10) {
            float var11 = var2[var10].x;
            float var12 = var2[var10].y;
            float var13 = 0.1F * (float)var10;
            float var14 = 150.0F;
            float var15 = var8 + var9 * (float)var10;

            for(int var16 = 0; var16 < 5; ++var16) {
               var14 += 20.0F;
               var0.addEnemyAction(EnemyTools.naziShootBullet(var0, var13, var14, var11, var12, var15));
            }
         }

         return;
      case 1:
         float var4 = 150.0F;
         float var5 = var3.x;
         float var6 = var3.y;

         for(int var7 = 0; var7 < 5; ++var7) {
            var0.addEnemyAction(EnemyTools.naziShootBullet(var0, 0.0F, var4, var5, var6, -60.0F));
            var0.addEnemyAction(EnemyTools.naziShootBullet(var0, 0.0F, var4, var5, var6, -30.0F));
            var0.addEnemyAction(EnemyTools.naziShootBullet(var0, 0.0F, var4, var5, var6, 0.0F));
            var0.addEnemyAction(EnemyTools.naziShootBullet(var0, 0.0F, var4, var5, var6, 30.0F));
            var0.addEnemyAction(EnemyTools.naziShootBullet(var0, 0.0F, var4, var5, var6, 60.0F));
            var4 += 50.0F;
         }
      }

   }

   public static void loadResource() {
      canonTextureRegion = Assets_level4.atlas_Enemy.findRegion("nazi_test_weapon_left");
      canonBroTextureRegion = Assets_level4.atlas_Enemy.findRegion("nazi_test_weapon_left_broken");
   }

   public float getTimeOfCrash() {
      return 1.0F;
   }

   public float getVelAngle() {
      return this.velAngle;
   }

   public void setVelAngle(float var1) {
      if(var1 != this.velAngle) {
         this.velBullet.set(VELBULLET).rotate(var1);
         this.velAngle = var1;
      }

   }

   public void shooting(int var1) {
      if(!this.isCrashed()) {
         armShootBullets(this, var1, BULLETPOSITION, BULLETPOSITIONCENTER);
      }
   }

   public void update(float var1) {
      super.update(var1);
   }

   public void updateShooting(float var1) {}
}
