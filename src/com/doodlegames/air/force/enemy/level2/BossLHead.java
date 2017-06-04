package com.doodlegames.air.force.enemy.level2;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.BulletScreen;
import com.doodlegames.air.force.resource.Assets_level2;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

public class BossLHead extends Enemy {

   private static final float BULLETSPEED = 280.0F;
   public static final int DEFAULTHEALTHYDEGREE = 100;
   private static final float TIMEOFPLAYCRASHANIMATION = 1.0F;
   private static TextureAtlas.AtlasRegion atlasBroRegion = Assets_level2.atlas_Enemy.findRegion("boss_head_broken");
   private static TextureAtlas.AtlasRegion atlasRegion = Assets_level2.atlas_Enemy.findRegion("boss_head");
   public static final Vector2 bulletPostion;
   public static final Rectangle hitRectangle;
   public static final int regionHeight;
   public static final int regionWidth;
   public static final Vector2 velBullet;
   private Clock clockPursue = new Clock(0.05F);
   private Rectangle hitR;
   private boolean isRecovering;
   private float tempY;
   private Vector2 thisVelBullet;


   static {
      int var0;
      if(atlasRegion.rotate) {
         var0 = atlasRegion.getRegionHeight();
      } else {
         var0 = atlasRegion.getRegionWidth();
      }

      regionWidth = var0;
      int var1;
      if(atlasRegion.rotate) {
         var1 = atlasRegion.getRegionWidth();
      } else {
         var1 = atlasRegion.getRegionHeight();
      }

      regionHeight = var1;
      velBullet = new Vector2(280.0F, 280.0F);
      bulletPostion = new Vector2(42.0F, 41.0F);
      hitRectangle = new Rectangle(13.0F, 22.0F, 58.0F, 62.0F);
   }

   public BossLHead(World var1, float var2, float var3) {
      super(var1, Settings.lapFixBossDegree(100), var2, var3, (float)regionWidth / 1.0F, (float)regionHeight / 1.0F);
      this.thisVelBullet = new Vector2(velBullet);
      this.hitR = new Rectangle(hitRectangle);
      Rectangle var4 = this.hitR;
      var4.x += this.bounds.x;
      Rectangle var5 = this.hitR;
      var5.y += this.bounds.y;
      this.isRecovering = false;
      this.enemyRegion = atlasRegion;
      this.enemyBroRegion = atlasBroRegion;
   }

   public static void loadResource() {
      atlasRegion = Assets_level2.atlas_Enemy.findRegion("boss_head");
      atlasBroRegion = Assets_level2.atlas_Enemy.findRegion("boss_head_broken");
   }

   private void resetHead() {
      super.reset(this.world, Settings.lapFixBossDegree(100), this.getPositionX(), this.getPositionY());
      this.enemyRegion = atlasRegion;
      this.enemyBroRegion = atlasBroRegion;
   }

   public boolean getIsRecovering() {
      return this.isRecovering;
   }

   public float getTimeOfCrash() {
      return 1.0F;
   }

   public Rectangle gethitR() {
      this.hitR.setX(this.bounds.x + hitRectangle.x);
      this.hitR.setY(this.bounds.y + hitRectangle.y);
      return this.hitR;
   }

   public void setIsRecovering(int var1) {
      if(this.isCrashed()) {
         this.isRecovering = true;
         this.velocity.y = 354.0F;
         this.tempY = this.position.y;
      }

   }

   public void shooting(float var1) {
      if(!this.isCrashed()) {
         EnemyTools.naziShootScreen(this, var1, bulletPostion.x, bulletPostion.y, BulletScreen.getPublicMiddleScreen());
      }
   }

   public void update(float var1) {
      super.update(var1);
      if(this.isRecovering) {
         if(this.velocity.y > 0.0F) {
            if(Settings.gameObjectInScreen(this) != Settings.PositionType.InScreenMiddle) {
               this.velocity.y = -354.0F;
               this.resetHead();
            }
         } else if(this.position.y <= this.tempY) {
            this.velocity.y = 0.0F;
            this.position.y = this.tempY;
            this.isRecovering = false;
            return;
         }
      }

   }

   public void updateShooting(float var1) {}
}
