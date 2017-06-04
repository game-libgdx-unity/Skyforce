package com.doodlegames.air.force.enemy.level3;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.level2.Robot;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.game.path.AbstractEnemyPath;
import com.doodlegames.air.force.game.path.EnemyMutiMove;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

public class HightRobot extends Enemy {

   private static final Vector2[] ANGLECYCLE;
   private static final Vector2 BULLETPOSITION1;
   private static final Vector2 BULLETPOSITION2;
   public static final int DEFAULTHEALTHYDEGREE = 20;
   private static int HEIGHT = 0;
   private static final Vector2 Origin;
   private static final float SPAN = 0.6F;
   private static final float TIMEOFPLAYCRASHANIMATION = 15.0F;
   private static final Vector2 VELBULLET;
   private static final int WIDTH;
   private static TextureAtlas.AtlasRegion enemyBroTextureRegion;
   private static TextureAtlas.AtlasRegion enemyTextureRegion;
   private Vector2 bulletPosition1;
   private Vector2 bulletPosition2;
   private Clock clockShooting;
   private float startShooting;
   private Vector2 tempV;
   private Vector2 velBullet;


   static {
      Vector2[] var0 = new Vector2[]{new Vector2(0.0F, 0.0F), new Vector2(0.0F, 30.0F), new Vector2(30.0F, 30.0F), new Vector2(30.0F, 60.0F), new Vector2(60.0F, 60.0F), new Vector2(60.0F, 30.0F), new Vector2(30.0F, 30.0F), new Vector2(30.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, -30.0F), new Vector2(-30.0F, -30.0F), new Vector2(-30.0F, -60.0F), new Vector2(-60.0F, -60.0F), new Vector2(-60.0F, -30.0F), new Vector2(-30.0F, -30.0F), new Vector2(-30.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F)};
      ANGLECYCLE = var0;
      enemyTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("nazi_high_robot");
      enemyBroTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("nazi_high_robot_broken");
      int var1;
      if(enemyTextureRegion.rotate) {
         var1 = enemyTextureRegion.getRegionHeight();
      } else {
         var1 = enemyTextureRegion.getRegionWidth();
      }

      WIDTH = var1;
      int var2;
      if(enemyTextureRegion.rotate) {
         var2 = enemyTextureRegion.getRegionWidth();
      } else {
         var2 = enemyTextureRegion.getRegionHeight();
      }

      HEIGHT = var2;
      VELBULLET = new Vector2(0.0F, -300.0F);
      Origin = new Vector2(35.0F, 18.0F);
      BULLETPOSITION1 = new Vector2(13.0F, 14.0F);
      BULLETPOSITION2 = new Vector2(56.0F, 14.0F);
   }

   public HightRobot(World var1, float var2, float var3) {
      super(var1, 20, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.origin.set(Origin);
      this.startShooting = 0.0F;
      this.clockShooting = new Clock(0.3F);
      this.velBullet = new Vector2(VELBULLET);
      this.bulletPosition1 = new Vector2(BULLETPOSITION1.x, BULLETPOSITION1.y);
      this.bulletPosition2 = new Vector2(BULLETPOSITION2.x, BULLETPOSITION2.y);
      this.tempV = new Vector2();
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyBroTextureRegion;
      this.crashUsePath = false;
   }

   private boolean canShoot() {
      return !this.canTurning();
   }

   private boolean canTurning() {
      AbstractEnemyPath var1 = this.moveProcessor;
      if(this.moveProcessor instanceof EnemyMutiMove) {
         var1 = ((EnemyMutiMove)this.moveProcessor).getCurrentMove();
      }

      return var1 == null?this.velocity.y != Settings.backgroundVelocity.y || this.velocity.x != 0.0F:!Robot.pattern.matcher(var1.pathName).find();
   }

   public static void loadResource() {
      enemyTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("nazi_high_robot");
      enemyBroTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("nazi_high_robot_broken");
   }

   public float getTimeOfCrash() {
      return 15.0F;
   }

   public void update(float var1) {
      super.update(var1);
      if(!this.isCrashed() && this.canShoot()) {
         float var2 = (float)Clock.getTimeCounter() - this.startShooting;
         int var3 = (int)(var2 / 0.6F);
         int var4 = var3 % ANGLECYCLE.length;
         this.angle = (ANGLECYCLE[var4].y - ANGLECYCLE[var4].x) / 0.6F * (var2 - 0.6F * (float)var3) + ANGLECYCLE[var4].x;
      }

   }

   protected void updateDynamicParameter(float var1) {
      super.updateDynamicParameter(var1);
      if(this.canTurning() && !this.isCrashed()) {
         if(this.angularSpeed > 0.0F && this.angle > 5.0F) {
            this.angularSpeed = -80.0F;
         }

         if(this.angularSpeed < 0.0F && this.angle < -5.0F) {
            this.angularSpeed = 80.0F;
         }

         if(this.angularSpeed == 0.0F) {
            this.angularSpeed = 80.0F;
         }

         this.angle += var1 * this.angularSpeed;
         this.startShooting = (float)Clock.getTimeCounter();
      } else {
         this.angularSpeed = 0.0F;
      }
   }

   public void updateShooting(float var1) {
      int var2 = (int)((Clock.getTimeCounter() - (double)this.startShooting) / 0.6000000238418579D) % ANGLECYCLE.length;
      if(ANGLECYCLE[var2].x == ANGLECYCLE[var2].y && var2 <= 15 && this.clockShooting.isFired() && this.canShoot()) {
         this.tempV.set(BULLETPOSITION1.x - Origin.x, BULLETPOSITION1.y - Origin.y);
         Enemy.getRotateOffset(this.tempV, this.angle);
         this.tempV.add(BULLETPOSITION1.x, BULLETPOSITION1.y);
         this.bulletPosition1.set(this.tempV);
         this.tempV.set(BULLETPOSITION2.x - Origin.x, BULLETPOSITION2.y - Origin.y);
         Enemy.getRotateOffset(this.tempV, this.angle);
         this.tempV.add(BULLETPOSITION2.x, BULLETPOSITION2.y);
         this.bulletPosition2.set(this.tempV);
         this.velBullet.set(VELBULLET).rotate(this.angle);
         Enemy.bullets.add(EnemyBullet1.naziBulletGen.getABullet(this, this.world, this.bounds.x + this.bulletPosition1.x, this.bounds.y + this.bulletPosition1.y, this.velBullet));
         Enemy.bullets.add(EnemyBullet1.naziBulletGen.getABullet(this, this.world, this.bounds.x + this.bulletPosition2.x, this.bounds.y + this.bulletPosition2.y, this.velBullet));
      }
   }

   protected boolean usePathAngle() {
      return false;
   }
}
