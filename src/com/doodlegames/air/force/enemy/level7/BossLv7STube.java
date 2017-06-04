package com.doodlegames.air.force.enemy.level7;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.BulletScreen;
import com.doodlegames.air.force.game.bullet.FighterBullet;
import com.doodlegames.air.force.resource.Assets_level7;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

public class BossLv7STube extends Enemy {

   public static final int DEFAULTHEALTHYDEGREE = 80;
   public static int HEIGHT = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 4.0F;
   public static final int WIDTH;
   private static TextureAtlas.AtlasRegion enemyBroTextureRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_s_tube_broken");
   private static TextureAtlas.AtlasRegion enemyTextureRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_s_tube");
   float[][][] bulletVelAngles;
   private double lastShootTime = 0.0D;


   static {
      int var0;
      if(enemyTextureRegion.rotate) {
         var0 = enemyTextureRegion.getRegionHeight();
      } else {
         var0 = enemyTextureRegion.getRegionWidth();
      }

      WIDTH = var0;
      int var1;
      if(enemyTextureRegion.rotate) {
         var1 = enemyTextureRegion.getRegionWidth();
      } else {
         var1 = enemyTextureRegion.getRegionHeight();
      }

      HEIGHT = var1;
   }

   public BossLv7STube(World var1, float var2, float var3) {
      super(var1, Settings.lapFixBossDegree(80), var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      float[][][] var4 = new float[][][]{BulletScreen.bulletVelAngles[8], BulletScreen.bulletVelAngles[9], BulletScreen.bulletVelAngles[10], BulletScreen.bulletVelAngles[14], BulletScreen.bulletVelAngles[15], BulletScreen.bulletVelAngles[16], BulletScreen.bulletVelAngles[17], BulletScreen.bulletVelAngles[22], BulletScreen.bulletVelAngles[23], BulletScreen.bulletVelAngles[24], BulletScreen.bulletVelAngles[25], BulletScreen.bulletVelAngles[26]};
      this.bulletVelAngles = var4;
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyBroTextureRegion;
   }

   public static void loadResource() {
      enemyTextureRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_s_tube");
      enemyBroTextureRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_s_tube_broken");
   }

   public void beHitByBullet(Bullet var1) {
      super.beHitByBullet(var1);
      if(var1 instanceof FighterBullet) {
         double var2 = Clock.getTimeCounter();
         if(var2 - this.lastShootTime > 3.0D) {
            this.lastShootTime = var2;
            byte var4;
            if(this.getPositionX() < 320.0F) {
               var4 = 0;
            } else {
               var4 = 1;
            }

            EnemyTools.alienShootScreen(this, 12.0F, 44.0F, BulletScreen.getPublicScreen(var4, this.bulletVelAngles));
         }
      }

   }

   public float getTimeOfCrash() {
      return 4.0F;
   }

   public void updateShooting(float var1) {}
}
