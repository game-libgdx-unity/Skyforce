package com.doodlegames.air.force.enemy.level4;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.level4.TestWeaponLeft;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.resource.Assets_level4;
import com.doodlegames.air.force.utils.RegionUtilFunctions;
import com.doodlegames.air.force.utils.Settings;

public class TestWeaponRight extends Enemy {

   public static final Vector2[] BULLETPOSITION;
   public static final Vector2 BULLETPOSITIONCENTER = new Vector2(44.0F, 19.0F);
   public static final int DEFAULTHEALTHYDEGREE = 40;
   public static int HEIGHT = 0;
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
      Vector2[] var0 = new Vector2[]{new Vector2(35.0F, 19.0F), new Vector2(37.0F, 13.0F), new Vector2(44.0F, 10.0F), new Vector2(50.0F, 13.0F), new Vector2(53.0F, 19.0F), new Vector2(50.0F, 25.0F), new Vector2(44.0F, 28.0F), new Vector2(37.0F, 25.0F)};
      BULLETPOSITION = var0;
      canonTextureRegion = Assets_level4.atlas_Enemy.findRegion("nazi_test_weapon_right");
      canonBroTextureRegion = Assets_level4.atlas_Enemy.findRegion("nazi_test_weapon_right_broken");
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

   public TestWeaponRight(World var1, float var2, float var3) {
      super(var1, Settings.lapFixBossDegree(40), var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.velBullet = new Vector2(VELBULLET);
      this.velAngle = 0.0F;
      this.enemyRegion = canonTextureRegion;
      this.enemyBroRegion = canonBroTextureRegion;
   }

   public static void loadResource() {
      canonTextureRegion = Assets_level4.atlas_Enemy.findRegion("nazi_test_weapon_right");
      canonBroTextureRegion = Assets_level4.atlas_Enemy.findRegion("nazi_test_weapon_right_broken");
   }

   public float getTimeOfCrash() {
      return 1.0F;
   }

   public float getVelAngle() {
      return this.velAngle;
   }

   public void renderShadow(SpriteBatch var1) {
      float var2 = var1.getColor().toFloatBits();
      var1.setColor(0.0F, 0.0F, 0.0F, 0.5F);
      TextureRegion var3;
      if(this.isCrashed()) {
         var3 = this.enemyBroRegion;
      } else {
         var3 = this.enemyRegion;
      }

      RegionUtilFunctions.draw(var1, var3, 33.0F + this.bounds.x, -28.0F + this.bounds.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, 0.8F, 0.8F, this.angle);
      var1.setColor(var2);
   }

   public void setVelAngle(float var1) {
      if(var1 != this.velAngle) {
         this.velBullet.set(VELBULLET).rotate(var1);
         this.velAngle = var1;
      }

   }

   public void shooting(int var1) {
      if(!this.isCrashed()) {
         TestWeaponLeft.armShootBullets(this, var1, BULLETPOSITION, BULLETPOSITIONCENTER);
      }
   }

   public void update(float var1) {
      super.update(var1);
   }

   public void updateShooting(float var1) {}
}
