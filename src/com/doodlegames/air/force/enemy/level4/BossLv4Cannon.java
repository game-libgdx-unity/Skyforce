package com.doodlegames.air.force.enemy.level4;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.game.bullet.EnemyBullet2;
import com.doodlegames.air.force.resource.Assets_level4;
import com.doodlegames.air.force.utils.Clock;

public class BossLv4Cannon extends Enemy implements EnemyBullet2.MissleFixer {

   public static final int DEFAULTHEALTHYDEGREE = 40;
   public static int HEIGHT = 0;
   private static final int SPANNUM = 4;
   private static final float TIMEOFPLAYCRASHANIMATION = 0.1F;
   public static final int WIDTH;
   private static final Vector2 bulletPostion = new Vector2(12.0F, 10.0F);
   public static TextureAtlas.AtlasRegion canonTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_canon");
   private static int delayCounter = 0;
   public static final Vector2 velBullet = new Vector2(0.0F, -80.0F);
   private Clock cannonShootingClock;
   private Vector2 thisVelBullet;


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

   public BossLv4Cannon(World var1, float var2, float var3) {
      super(var1, 40, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.thisVelBullet = new Vector2(velBullet);
      this.cannonShootingClock = new Clock(getInitHead(1.0F), 6.0F, 0.5F, (byte)1);
      this.enemyRegion = canonTextureRegion;
      this.enemyBroRegion = null;
   }

   private static float getInitHead(float var0) {
      ++delayCounter;
      int var1;
      if(delayCounter >= 4) {
         var1 = 0;
      } else {
         var1 = delayCounter;
      }

      delayCounter = var1;
      return var0 * (float)delayCounter;
   }

   public static void loadResource() {
      canonTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_canon");
   }

   public void fixMissle(EnemyBullet2 var1) {
      var1.setTurnCountMax(1);
      var1.addGameAction(EnemyBullet2.getSpeedUp(var1, 2.0F, 2.0F, 300.0F));
   }

   public float getTimeOfCrash() {
      return 0.1F;
   }

   public void reset(World var1, float var2, float var3) {
      super.reset(var1, 40, var2, var3);
      this.cannonShootingClock.restartClock();
      this.enemyRegion = canonTextureRegion;
      this.enemyBroRegion = null;
   }

   public void shooting(BulletGenerator var1) {
      if(!this.isCrashed()) {
         Enemy.bullets.add(var1.getABullet(this, this.world, this.bounds.x + bulletPostion.x, this.bounds.y + bulletPostion.y, this.thisVelBullet));
      }
   }

   public void update(float var1) {
      super.update(var1);
   }

   public void updateShooting(float var1) {
      if(this.cannonShootingClock.isFired()) {
         this.shooting(EnemyBullet2.naziBulletGen);
      }

   }
}
