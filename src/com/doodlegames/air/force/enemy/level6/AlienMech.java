package com.doodlegames.air.force.enemy.level6;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.utils.Clock;

import java.util.ArrayList;
import java.util.List;

public class AlienMech extends Enemy {

   public static int BULLETNUM = 0;
   public static final Vector2[] BULLETPOSITION;
   public static final int DEFAULTHEALTHYDEGREE = 20;
   private static int HEIGHT = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 0.2F;
   public static final Vector2 VELBULLET;
   private static final int WIDTH;
   private static TextureAtlas.AtlasRegion enemyTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_mech");
   private static final Rectangle hitRectangle;
   private Clock clockShooting = new Clock(5.0F, 0.2F, (byte)1);
   private Rectangle hitR;
   private List<Rectangle> hitRectangles = new ArrayList(1);
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
      Vector2[] var2 = new Vector2[]{new Vector2(5.0F, 37.0F), new Vector2(1.0F, 23.0F), new Vector2(51.0F, 37.0F), new Vector2(55.0F, 23.0F)};
      BULLETPOSITION = var2;
      BULLETNUM = BULLETPOSITION.length;
      VELBULLET = new Vector2(0.0F, -180.0F);
      hitRectangle = new Rectangle(13.0F, 15.0F, 28.0F, 21.0F);
   }

   public AlienMech(World var1, float var2, float var3) {
      super(var1, 20, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.velBullet = new Vector2(VELBULLET);
      this.hitR = new Rectangle(hitRectangle);
      this.hitR.x = this.bounds.x + hitRectangle.x;
      this.hitR.y = this.bounds.y + hitRectangle.y;
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyTextureRegion;
      this.crashUsePath = false;
   }

   public static void loadResource() {
      enemyTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_mech");
   }

   public Rectangle getHitR() {
      this.hitR.x = this.bounds.x + hitRectangle.x;
      this.hitR.y = this.bounds.y + hitRectangle.y;
      return this.hitR;
   }

   public List<Rectangle> getHitRectangle() {
      this.hitRectangles.clear();
      this.hitRectangles.add(this.getHitR());
      return this.hitRectangles;
   }

   public float getTimeOfCrash() {
      return 0.2F;
   }

   public void reset(World var1, float var2, float var3) {
      super.reset(var1, 20, var2, var3);
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyTextureRegion;
   }

   public void update(float var1) {
      super.update(var1);
   }

   public void updateShooting(float var1) {
      if(this.clockShooting.isFired()) {
         for(int var2 = Clock.rand.nextInt(2); var2 < BULLETPOSITION.length; var2 += 2) {
            Vector2 var3 = BULLETPOSITION[var2];
            this.addEnemyAction(EnemyTools.alienShootMissle(this, 0.1F * (float)var2, this.velBullet.y, var3.x, var3.y));
         }
      }

   }

   protected boolean usePathAngle() {
      return false;
   }
}
