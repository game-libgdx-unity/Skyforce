package com.doodlegames.air.force.enemy.level2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.cannon.CommonCannon;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.EnemyBullet2;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.List;

public class RocketVehicle extends Enemy {

   public static final int DEFAULTHEALTHYDEGREE = 10;
   public static int HEIGHT = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 15.0F;
   public static final int WIDTH;
   private static TextureAtlas.AtlasRegion enemyAtlasRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("nazi_tank_rocket");
   public static TextureAtlas.AtlasRegion enemyTextureBroRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("nazi_tank_rocket_broken");
   private CommonCannon cannon4;
   public static final int ATLASHEIGHT = enemyAtlasRegion.getRegionHeight();
   public static final int ATLASWIDTH = enemyAtlasRegion.getRegionWidth();
 

   static {
      int var0;
      if(enemyAtlasRegion.rotate) {
         var0 = ATLASHEIGHT;
      } else {
         var0 = ATLASWIDTH;
      }

      WIDTH = var0;
      int var1;
      if(enemyAtlasRegion.rotate) {
         var1 = ATLASWIDTH;
      } else {
         var1 = ATLASHEIGHT;
      }

      HEIGHT = var1;
   }

   public RocketVehicle(World var1, float var2, float var3) {
      super(var1, 10, var2 + 0.5F, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.cannon4 = CommonCannon.getCannon4(var1, new Clock(5.0F, 0.2F, (byte)1), 10, EnemyBullet2.naziBulletGen, this, -5.0F, 22.0F, 250.0F);
      this.cannon4.setAutoTurn(true);
      this.initRock();
      this.crashUsePath = false;
   }

   private void initRock() {
      this.enemyRegion = enemyAtlasRegion;
      this.enemyBroRegion = enemyTextureBroRegion;
      this.velocity.y = Settings.backgroundVelocity.y;
   }

   public static void loadResource(TextureAtlas var0) {
      enemyAtlasRegion = var0.findRegion("nazi_tank_rocket");
      enemyTextureBroRegion = var0.findRegion("nazi_tank_rocket_broken");
   }

   public void beHitByBullet(Bullet var1, int var2) {
      this.cannon4.beHitByBullet(var1);
   }

   public boolean canBeHit() {
      return this.cannon4.canBeHit();
   }

   public void freeToPool() {
      if(this.world.level == 4) {
         this.world.RocketVehiclePool.free((RocketVehicle)this);
      }

   }

   public List<Rectangle> getHitRectangle() {
      return this.cannon4.getHitRectangle();
   }

   public float getTimeOfCrash() {
      return 15.0F;
   }

   public boolean isCrashed() {
      if(this.cannon4.isCrashed()) {
         if(!super.isCrashed()) {
            this.makeCrash();
         }

         return true;
      } else {
         return false;
      }
   }

   public void render(SpriteBatch var1) {
      super.render(var1);
      this.cannon4.render(var1);
   }

   public void reset(World var1, float var2, float var3) {
      super.reset(var1, 10, var2, var3);
      TextureAtlas var4 = Assets_ShareInAllLevel.atlasSharedEnemy;
      TextureAtlas.AtlasRegion var5 = var4.findRegion("nazi_canon", 4);
      TextureAtlas.AtlasRegion var6 = var4.findRegion("nazi_canon_4_broken");
      this.cannon4.reset(10, var5, var6);
      this.initRock();
   }

   public void update(float var1) {
      super.update(var1);
      this.cannon4.update(var1);
   }

   public void updateShooting(float var1) {}

   protected boolean usePathAngle() {
      return false;
   }
}
