package com.doodlegames.air.force.enemy.level3;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.BulletScreen;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.utils.Clock;

import java.util.ArrayList;
import java.util.List;

public class AlienCanon extends Enemy {

   public static final Vector2 BULLETPOSITION1;
   public static final Vector2 BULLETPOSITION2;
   public static final int DEFAULTHEALTHYDEGREE = 10;
   private static int HEIGHT = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 15.0F;
   public static final Vector2 VELBULLET;
   private static final int WIDTH;
   private static TextureAtlas.AtlasRegion enemyBroTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_canon_broken");
   private static TextureAtlas.AtlasRegion enemyTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_canon", -1);
   private static final Rectangle hitRectangle;
   private Clock clockPursue = new Clock(1.5F);
   private Clock clockShooting;
   private Rectangle hitR;
   private List<Rectangle> hitRectangles;
   private float pursueAngle;
   private Vector2 velBullet;


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
      BULLETPOSITION1 = new Vector2(4.0F, 75.0F);
      BULLETPOSITION2 = new Vector2(19.0F, 75.0F);
      VELBULLET = new Vector2(0.0F, -180.0F);
      hitRectangle = new Rectangle(0.0F, 20.0F, 20.0F, 20.0F);
   }

   public AlienCanon(World var1, float var2, float var3) {
      super(var1, 10, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.clockShooting = new Clock(3.0F * var1.rand.nextFloat(), 4.5F, 0.54F, (byte)1);
      this.hitRectangles = new ArrayList(1);
      this.pursueAngle = 0.0F;
      this.velBullet = new Vector2(VELBULLET);
      this.hitR = new Rectangle(hitRectangle);
      this.hitR.x = this.bounds.x + hitRectangle.x;
      this.hitR.y = this.bounds.y + hitRectangle.y;
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyBroTextureRegion;
   }

   public static void loadResource() {
      enemyTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_canon", -1);
      enemyBroTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_canon_broken");
   }

   public List<Rectangle> getHitRectangle() {
      this.hitRectangles.clear();
      this.hitRectangles.add(this.hitR);
      return this.hitRectangles;
   }

   public float getTimeOfCrash() {
      return 15.0F;
   }

   public void update(float var1) {
      super.update(var1);
      if(this.clockPursue.isFired()) {
         float var2 = this.world.fighter.getPositionY() - (this.bounds.y + (BULLETPOSITION1.y + BULLETPOSITION2.y) / 2.0F);
         float var3 = this.world.fighter.getPositionX() - (this.bounds.x + (BULLETPOSITION1.x + BULLETPOSITION2.x) / 2.0F);
         if(var2 < 0.0F) {
            float var4 = 90.0F + (float)(57.2957763671875D * Math.atan2((double)var2, (double)var3));
            if(this.pursueAngle != var4) {
               this.pursueAngle = var4;
               this.velBullet.set(VELBULLET).rotate(this.pursueAngle);
            }
         }
      }

      this.hitR.x = this.bounds.x + hitRectangle.x;
      this.hitR.y = this.bounds.y + hitRectangle.y;
   }

   public void updateShooting(float var1) {
      if(this.clockShooting.isFired()) {
         float[][] var2 = BulletScreen.getSmallVelAngles(2);
         Vector2 var3;
         if(Clock.rand.nextBoolean()) {
            var3 = BULLETPOSITION1;
         } else {
            var3 = BULLETPOSITION2;
         }

         EnemyTools.alienShootScreen(this, var3.x, var3.y, var2);
      }

   }
}
