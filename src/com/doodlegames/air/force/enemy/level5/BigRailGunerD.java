package com.doodlegames.air.force.enemy.level5;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;

public class BigRailGunerD extends Enemy {

   public static final int DEFAULTHEALTHYDEGREE = 20;
   public static int HEIGHT = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 1.0F;
   public static final int WIDTH;
   public static TextureAtlas.AtlasRegion canonTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_big_railguner_d");


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

   public BigRailGunerD(World var1, float var2, float var3) {
      super(var1, 20, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.velocity.set(0.0F, 0.0F);
      this.enemyRegion = canonTextureRegion;
   }

   public static void loadResource() {
      canonTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_big_railguner_d");
   }

   public float getTimeOfCrash() {
      return 1.0F;
   }

   public void update(float var1) {
      super.update(var1);
   }

   public void updateShooting(float var1) {}
}
