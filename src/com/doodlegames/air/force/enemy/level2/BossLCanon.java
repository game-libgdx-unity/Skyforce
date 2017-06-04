package com.doodlegames.air.force.enemy.level2;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.BulletScreen;
import com.doodlegames.air.force.resource.Assets_level2;

public class BossLCanon extends Enemy {

   public static final int BOTTOM = 2;
   private static final float BULLETSPEED = 400.0F;
   public static final int CENTER = 1;
   public static final int DEFAULTHEALTHYDEGREE = 90;
   private static final float FALLINGDISTANCE = 100.0F;
   public static final int LEFT = 3;
   public static final int RIGHT = 4;
   private static final float TIMEOFPLAYCRASHANIMATION = 1.0F;
   private static TextureAtlas.AtlasRegion altasBroRegion = Assets_level2.atlas_Enemy.findRegion("boss_canon_broken");
   private static TextureAtlas.AtlasRegion atlasRegion = Assets_level2.atlas_Enemy.findRegion("boss_canon");
   public static final Vector2[] bulletPostion;
   private static final Rectangle hitRectangle;
   public static final int regionHeight;
   public static final int regionWidth;
   public static final Vector2 velBullet;
   private Rectangle hitR;
   private boolean isRecovering;
   private BossLCanon.RecoveryState rs;
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
      velBullet = new Vector2(0.0F, -400.0F);
      Vector2[] var2 = new Vector2[]{new Vector2(35.0F, 32.0F), new Vector2(35.0F, 24.0F), new Vector2(35.0F, 16.0F), new Vector2(27.0F, 24.0F), new Vector2(43.0F, 24.0F)};
      bulletPostion = var2;
      hitRectangle = new Rectangle(10.0F, 10.0F, 51.0F, 88.0F);
   }

   public BossLCanon(World var1, float var2, float var3) {
      super(var1, 90, var2, var3, (float)regionWidth / 1.0F, (float)regionHeight / 1.0F);
      this.thisVelBullet = new Vector2(velBullet);
      this.hitR = new Rectangle(hitRectangle);
      Rectangle var4 = this.hitR;
      var4.x += this.bounds.x;
      Rectangle var5 = this.hitR;
      var5.y += this.bounds.y;
      this.rs = BossLCanon.RecoveryState.Invalid;
      this.enemyRegion = atlasRegion;
      this.enemyBroRegion = altasBroRegion;
   }

   public static void loadResource() {
      atlasRegion = Assets_level2.atlas_Enemy.findRegion("boss_canon");
      altasBroRegion = Assets_level2.atlas_Enemy.findRegion("boss_canon_broken");
   }

   private void resetCanon() {
      super.reset(this.world, 90, this.getPositionX(), this.getPositionY());
      this.angle = 0.0F;
      this.enemyRegion = atlasRegion;
      this.enemyBroRegion = altasBroRegion;
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
         if(var1 == 1) {
            this.angle = 15.0F;
         }

         if(var1 == 2) {
            this.angle = -15.0F;
         }

         this.tempY = this.position.y;
         this.rs = BossLCanon.RecoveryState.Disappearing;
      }

   }

   public void shooting(float var1) {
      if(!this.isCrashed()) {
         EnemyTools.naziShootScreen(this, var1, bulletPostion[1].x, bulletPostion[1].y, BulletScreen.getPublicNoMatterScreen());
      }
   }

   public void update(float var1) {
      super.update(var1);
      if(this.isRecovering) {
         if(this.rs == BossLCanon.RecoveryState.Disappearing && this.tempY - this.position.y > 100.0F) {
            this.accel.y = -800.0F;
            this.velocity.y = -100.0F;
            this.position.y = 800.0F + this.bounds.height / 2.0F;
            super.updateDynamicParameter(0.0F);
            this.rs = BossLCanon.RecoveryState.Appearing;
            this.resetCanon();
         }

         if(this.rs == BossLCanon.RecoveryState.Appearing && this.position.y <= this.tempY) {
            this.velocity.y = 0.0F;
            this.accel.y = 0.0F;
            this.position.y = this.tempY;
            this.rs = BossLCanon.RecoveryState.Invalid;
            this.isRecovering = false;
         }
      }

   }

   public void updateShooting(float var1) {}

   public void updateVelAngle() {
      float var1 = this.world.fighter.position.x - (this.bounds.x + bulletPostion[1].x);
      float var2 = 57.295776F * MathUtils.atan2(this.world.fighter.position.y - (this.bounds.y + bulletPostion[1].y), var1);
      float var3 = this.thisVelBullet.len();
      this.thisVelBullet.x = var3 * MathUtils.cosDeg(var2);
      this.thisVelBullet.y = var3 * MathUtils.sinDeg(var2);
   }

   private static enum RecoveryState {

      Appearing,
      Disappearing,
      Invalid
   }
}
