package com.doodlegames.air.force.enemy.level4;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.BulletScreen;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;

public class AlianRobotLeft extends Enemy {

   public static final Vector2 BULLETPOSITION = new Vector2(5.0F, 43.0F);
   public static final int DEFAULTHEALTHYDEGREE = 15;
   public static int HEIGHT = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 0.3F;
   public static final Vector2 VELBULLET = new Vector2(0.0F, -200.0F);
   public static final int WIDTH;
   public static TextureAtlas.AtlasRegion canonTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_robot_l");
   private Vector2 bulletPostion;
   private float velAngle;
   private Vector2 velBullet;


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

   public AlianRobotLeft(World var1, float var2, float var3) {
      super(var1, 15, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.bulletPostion = new Vector2(BULLETPOSITION);
      this.velBullet = new Vector2(VELBULLET);
      this.velAngle = 0.0F;
      this.enemyRegion = canonTextureRegion;
   }

   public static void loadResource() {
      canonTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_robot_l");
   }

   public float getTimeOfCrash() {
      return 0.3F;
   }

   public float getVelAngle() {
      return this.velAngle;
   }

   public void setVel(float var1, float var2) {
      if(var1 != this.velAngle) {
         this.velBullet.set(0.0F, -Math.abs(var2)).rotate(var1);
         this.velAngle = var1;
      }

   }

   public void shootBulletScreen() {
      if(!this.isCrashed()) {
         EnemyTools.enemyShootScreen(this, 0.0F, EnemyBullet1.alienBulletGen, this.bulletPostion.x, this.bulletPostion.y, BulletScreen.bulletVelAngles[21]);
      }
   }

   public void shooting() {
      if(!this.isCrashed()) {
         Enemy.bullets.add(EnemyBullet1.alienBulletGen.getABullet(this, this.world, this.bounds.x + this.bulletPostion.x, this.bounds.y + this.bulletPostion.y, this.velBullet));
      }
   }

   public void update(float var1) {
      super.update(var1);
   }

   public void updateShooting(float var1) {}
}
