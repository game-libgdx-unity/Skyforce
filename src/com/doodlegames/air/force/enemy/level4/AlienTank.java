package com.doodlegames.air.force.enemy.level4;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.BulletScreen;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class AlienTank extends Enemy {

   public static final Vector2 BULLETPOSITION1;
   public static final Vector2 BULLETPOSITION2;
   public static final int DEFAULTHEALTHYDEGREE = 15;
   public static int HEIGHT = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 0.1F;
   public static final Vector2 VELBULLET;
   public static final int WIDTH;
   private static TextureAtlas.AtlasRegion enemyTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_tank");
   private static final Rectangle hitRectangle;
   private Clock clockPursue = new Clock(1.5F);
   private Clock clockShooting = new Clock(4.0F, 0.25F, (byte)1);
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
      BULLETPOSITION1 = new Vector2(4.0F, 74.0F);
      BULLETPOSITION2 = new Vector2(30.0F, 74.0F);
      VELBULLET = new Vector2(0.0F, -320.0F);
      hitRectangle = new Rectangle(7.0F, 24.0F, 20.0F, 36.0F);
   }

   public AlienTank(World var1, float var2, float var3) {
      super(var1, 15, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.velocity.set(Settings.backgroundVelocity);
      this.hitRectangles = new ArrayList(1);
      this.pursueAngle = 0.0F;
      this.velBullet = new Vector2(VELBULLET);
      this.hitR = new Rectangle(hitRectangle);
      this.hitR.x = this.bounds.x + hitRectangle.x;
      this.hitR.y = this.bounds.y + hitRectangle.y;
      this.enemyRegion = enemyTextureRegion;
      this.crashUsePath = false;
   }

   public static void loadResource() {
      enemyTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_tank");
   }

   public void freeToPool() {
      if(this.world.level == 4) {
         this.world.AlienTankPool.free((AlienTank)this);
      }

   }

   public List<Rectangle> getHitRectangle() {
      this.hitRectangles.clear();
      this.hitRectangles.add(this.hitR);
      return this.hitRectangles;
   }

   public float getTimeOfCrash() {
      return 0.1F;
   }

   public void reset(World var1, float var2, float var3) {
      super.reset(var1, 15, var2, var3);
      this.enemyRegion = enemyTextureRegion;
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
         if(!Clock.rand.nextBoolean()) {
            EnemyTools.alienShootScreen(this, BULLETPOSITION2.x, BULLETPOSITION2.y, var2);
            return;
         }

         EnemyTools.alienShootScreen(this, BULLETPOSITION1.x, BULLETPOSITION1.y, var2);
      }

   }

   protected boolean usePathAngle() {
      return false;
   }
}
