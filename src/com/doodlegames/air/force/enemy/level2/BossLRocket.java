package com.doodlegames.air.force.enemy.level2;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.resource.Assets_level2;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

public class BossLRocket extends Enemy {

   private static final float BULLETSPEED = 10.0F;
   public static final int DEFAULTHEALTHYDEGREE = 50;
   private static final float FALLINGDISTANCE = 100.0F;
   private static final float TIMEOFPLAYCRASHANIMATION = 1.0F;
   private static TextureAtlas.AtlasRegion atlasBroRegion = Assets_level2.atlas_Enemy.findRegion("boss_rocket_broken");
   private static TextureAtlas.AtlasRegion atlasRegion = Assets_level2.atlas_Enemy.findRegion("boss_rocket");
   public static final Vector2[] bulletPostion;
   private static final Rectangle hitRectangle;
   public static final int regionHeight;
   public static final int regionWidth;
   public static final Vector2 velBullet;
   private Rectangle hitR;
   private boolean isRecovering;
   private BossLRocket.RecoveryState rs;
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
      velBullet = new Vector2(0.0F, -200.0F);
      Vector2[] var2 = new Vector2[]{new Vector2(27.0F, 16.0F), new Vector2(27.0F, 24.0F), new Vector2(27.0F, 32.0F), new Vector2(35.0F, 16.0F), new Vector2(35.0F, 24.0F), new Vector2(35.0F, 32.0F), new Vector2(43.0F, 16.0F), new Vector2(43.0F, 24.0F), new Vector2(43.0F, 32.0F)};
      bulletPostion = var2;
      hitRectangle = new Rectangle(10.0F, 10.0F, 51.0F, 88.0F);
   }

   public BossLRocket(World var1, float var2, float var3) {
      super(var1, Settings.lapFixBossDegree(50), var2, var3, (float)regionWidth / 1.0F, (float)regionHeight / 1.0F);
      this.thisVelBullet = new Vector2(velBullet);
      this.hitR = new Rectangle(hitRectangle);
      Rectangle var4 = this.hitR;
      var4.x += this.bounds.x;
      Rectangle var5 = this.hitR;
      var5.y += this.bounds.y;
      this.rs = BossLRocket.RecoveryState.Invalid;
      this.enemyRegion = atlasRegion;
      this.enemyBroRegion = atlasBroRegion;
   }

   public static void loadResource() {
      atlasRegion = Assets_level2.atlas_Enemy.findRegion("boss_rocket");
      atlasBroRegion = Assets_level2.atlas_Enemy.findRegion("boss_rocket_broken");
   }

   private void resetRocket() {
      super.reset(this.world, Settings.lapFixBossDegree(50), this.getPositionX(), this.getPositionY());
      this.angle = 0.0F;
      this.enemyRegion = atlasRegion;
      this.enemyBroRegion = atlasBroRegion;
   }

   public boolean getIsRecovering() {
      return this.isRecovering;
   }

   public float getTimeOfCrash() {
      return 1.0F;
   }

   public Vector2 getVelBullet() {
      return this.thisVelBullet;
   }

   public Rectangle gethitR() {
      this.hitR.setX(this.bounds.x + hitRectangle.x);
      this.hitR.setY(this.bounds.y + hitRectangle.y);
      return this.hitR;
   }

   public void setIsRecovering(int var1) {
      if(this.isCrashed()) {
         this.isRecovering = true;
         this.accel.y = -800.0F;
         this.velocity.y = -100.0F;
         this.tempY = this.position.y;
         if(var1 == 1) {
            this.angle = 15.0F;
         }

         if(var1 == 2) {
            this.angle = -15.0F;
         }

         this.rs = BossLRocket.RecoveryState.Disappearing;
      }

   }

   public void shooting(int var1, float var2) {
      if(!this.isCrashed()) {
         float var3 = var2;
         int var4 = Clock.rand.nextInt(bulletPostion.length - var1);
         int var5 = Math.min(var1, bulletPostion.length);
         if(var4 < 0 || var4 >= bulletPostion.length) {
            var4 = 0;
         }

         for(int var6 = 0; var6 < var5; ++var6) {
            Vector2 var7 = bulletPostion[var6 + var4];
            this.addEnemyAction(EnemyTools.naziShootMissle(this, var3, 200.0F, var7.x, var7.y, (float)(-45 + this.world.rand.nextInt(90))));
            var3 += 0.2F;
         }
      }

   }

   public void update(float var1) {
      super.update(var1);
      if(this.isRecovering) {
         if(this.rs == BossLRocket.RecoveryState.Disappearing && this.tempY - this.position.y > 100.0F) {
            this.accel.y = -800.0F;
            this.velocity.y = -100.0F;
            this.position.y = 800.0F + this.bounds.height / 2.0F;
            super.updateDynamicParameter(0.0F);
            this.rs = BossLRocket.RecoveryState.Appearing;
            this.resetRocket();
         }

         if(this.rs == BossLRocket.RecoveryState.Appearing && this.position.y <= this.tempY) {
            this.velocity.y = 0.0F;
            this.accel.y = 0.0F;
            this.position.y = this.tempY;
            this.rs = BossLRocket.RecoveryState.Invalid;
            this.isRecovering = false;
         }
      }

   }

   public void updateShooting(float var1) {}

   private static enum RecoveryState {

      Appearing,
      Disappearing,
      Invalid
   }
}
