package com.doodlegames.air.force.enemy.level2;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.game.path.AbstractEnemyPath;
import com.doodlegames.air.force.game.path.EnemyMutiMove;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.regex.Pattern;

public class Robot extends Enemy {

   private static final Vector2[] ANGLECYCLE;
   private static int ATLASHEIGHT = 0;
   private static int ATLASWIDTH = 0;
   public static final int DEFAULTHEALTHYDEGREE = 5;
   public static int HEIGHT = 0;
   private static final float ORIGINX = 14.0F;
   private static final float ORIGINY = 18.0F;
   private static final float SHOOTPOSITIONX = 35.0F;
   private static final float SHOOTPOSITIONY = 14.0F;
   private static final float SPAN = 0.6F;
   private static final float TIMEOFPLAYCRASHANIMATION = 15.0F;
   public static final int WIDTH;
   public static TextureAtlas.AtlasRegion enemyAtlasBroRegion;
   public static TextureAtlas.AtlasRegion enemyAtlasRegion;
   public static Pattern pattern;
   private Clock clockShooting = new Clock(0.0F, 0.3F);
   private float startShooting;
   private Vector2 temp1 = new Vector2();
   private Vector2 temp2 = new Vector2();


   static {
      Vector2[] var0 = new Vector2[]{new Vector2(0.0F, 0.0F), new Vector2(0.0F, 30.0F), new Vector2(30.0F, 30.0F), new Vector2(30.0F, 60.0F), new Vector2(60.0F, 60.0F), new Vector2(60.0F, 30.0F), new Vector2(30.0F, 30.0F), new Vector2(30.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, -30.0F), new Vector2(-30.0F, -30.0F), new Vector2(-30.0F, -60.0F), new Vector2(-60.0F, -60.0F), new Vector2(-60.0F, -30.0F), new Vector2(-30.0F, -30.0F), new Vector2(-30.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F)};
      ANGLECYCLE = var0;
      enemyAtlasRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("nazi_robot");
      enemyAtlasBroRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("nazi_robot_broken");
      ATLASWIDTH = enemyAtlasRegion.getRegionWidth();
      ATLASHEIGHT = enemyAtlasRegion.getRegionHeight();
      int var1;
      if(enemyAtlasRegion.rotate) {
         var1 = ATLASHEIGHT;
      } else {
         var1 = ATLASWIDTH;
      }

      WIDTH = var1;
      int var2;
      if(enemyAtlasRegion.rotate) {
         var2 = ATLASWIDTH;
      } else {
         var2 = ATLASHEIGHT;
      }

      HEIGHT = var2;
      pattern = Pattern.compile("(.*(stop).*)|(.*(left).*)|(.*(right).*)");
   }

   public Robot(World var1, float var2, float var3) {
      super(var1, 5, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.origin.set(14.0F, 18.0F);
      this.enemyRegion = enemyAtlasRegion;
      this.enemyBroRegion = enemyAtlasBroRegion;
      this.crashUsePath = false;
   }

   public static void loadResource(TextureAtlas var0) {
      enemyAtlasRegion = var0.findRegion("nazi_robot");
      enemyAtlasBroRegion = var0.findRegion("nazi_robot_broken");
   }

   public boolean canShoot() {
      return !this.canTurning();
   }

   public boolean canTurning() {
      AbstractEnemyPath var1 = this.moveProcessor;
      if(this.moveProcessor instanceof EnemyMutiMove) {
         var1 = ((EnemyMutiMove)this.moveProcessor).getCurrentMove();
      }

      return var1 == null?this.velocity.y != Settings.backgroundVelocity.y || this.velocity.x != 0.0F:!pattern.matcher(var1.pathName).find();
   }

   public void freeToPool() {}

   public float getTimeOfCrash() {
      return 15.0F;
   }

   public void reset(World var1, float var2, float var3) {
      super.reset(var1, 5, var2, var3);
      this.startShooting = (float)Clock.getTimeCounter();
      this.clockShooting.resetClock(0.0F, 0.3F, 0.0F, (byte)1);
      this.enemyRegion = enemyAtlasRegion;
      this.enemyBroRegion = enemyAtlasBroRegion;
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
         Vector2 var3 = this.temp1.set(21.0F, -4.0F);
         Enemy.getRotateOffset(var3, this.angle);
         Vector2 var5 = this.temp2.set(0.0F, -300.0F);
         var5.rotate(this.angle);
         Enemy.bullets.add(EnemyBullet1.naziBulletGen.getABullet(this, this.world, this.bounds.x + (35.0F + var3.x) / 1.0F, this.bounds.y + (14.0F + var3.y) / 1.0F, var5));
      }
   }

   protected boolean usePathAngle() {
      return false;
   }
}
