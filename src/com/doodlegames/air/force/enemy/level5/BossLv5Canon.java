package com.doodlegames.air.force.enemy.level5;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.BulletScreen;
import com.doodlegames.air.force.resource.Assets_level5;
import com.doodlegames.air.force.utils.Clock;

public class BossLv5Canon extends Enemy {

   public static final Vector2 BULLETPOSITION;
   private static final float BULLETSPEED = -250.0F;
   public static final int DEFAULTHEALTHYDEGREE = 5;
   public static int HEIGHT = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 0.1F;
   public static final int WIDTH;
   public static TextureAtlas.AtlasRegion canonRegion = Assets_level5.atlas_Enemy.findRegion("lv_5_boss_canon");
   private final int position;
   private Clock shootClock;


   static {
      int var0;
      if(canonRegion.rotate) {
         var0 = canonRegion.getRegionHeight();
      } else {
         var0 = canonRegion.getRegionWidth();
      }

      WIDTH = var0;
      int var1;
      if(canonRegion.rotate) {
         var1 = canonRegion.getRegionWidth();
      } else {
         var1 = canonRegion.getRegionHeight();
      }

      HEIGHT = var1;
      BULLETPOSITION = new Vector2(8.0F, 50.0F);
   }

   public BossLv5Canon(World var1, float var2, float var3, float var4, int var5) {
      super(var1, 5, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.angle = var4;
      this.shootClock = new Clock(5.0F);
      this.position = var5;
      this.enemyRegion = canonRegion;
      this.enemyBroRegion = null;
   }

   public static void loadResource() {
      canonRegion = Assets_level5.atlas_Enemy.findRegion("lv_5_boss_canon");
   }

   public float getTimeOfCrash() {
      return 0.1F;
   }

   public void updateShooting(float var1) {
      if(this.shootClock.isFired()) {
         EnemyTools.alienShootScreen(this, BULLETPOSITION.x, BULLETPOSITION.y, BulletScreen.getPublicScreen(this.position));
      }

   }
}
