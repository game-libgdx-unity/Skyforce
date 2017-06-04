package com.doodlegames.air.force.enemy.level2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.cannon.Cannon2;
import com.doodlegames.air.force.enemy.cannon.CommonCannon;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

public class Tank extends Enemy {

   private static final Vector2 CANNONPOSITION;
   public static final int DEFAULTHEALTHYDEGREE = 10;
   public static int HEIGHT = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 15.0F;
   public static final int WIDTH;
   public static TextureAtlas.AtlasRegion enemyTextureBroRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("nazi_tank_rocket_broken");
   public static TextureAtlas.AtlasRegion enemyTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("nazi_tank_rocket");
   private Cannon2 cannon;
   private Clock clockPursue;
   private Clock clockShooting;


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
      CANNONPOSITION = new Vector2((6.0F + (float)Cannon2.WIDTH / 2.0F) / 1.0F, (15.0F + (float)Cannon2.HEIGHT / 2.0F) / 1.0F);
   }

   public Tank(World var1, float var2, float var3) {
      super(var1, 10, var2 + 0.5F, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.cannon = new Cannon2(var1, this.bounds.x + CANNONPOSITION.x, this.bounds.y + CANNONPOSITION.y);
      this.cannon.setThisVelBullet(0.0F, -250.0F);
      this.clockPursue = new Clock(2.0F);
      this.clockShooting = new Clock(5.0F, CommonCannon.clockDelay(-250.0F), (byte)3);
      this.velocity.y = Settings.backgroundVelocity.y;
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyTextureBroRegion;
      this.crashUsePath = false;
   }

   public static void loadResource(TextureAtlas var0) {
      enemyTextureRegion = var0.findRegion("nazi_tank_rocket");
      enemyTextureBroRegion = var0.findRegion("nazi_tank_rocket_broken");
   }

   public void beHitByBullet(Bullet var1) {
      this.cannon.beHitByBullet(var1);
      if(this.cannon.isCrashed()) {
         this.makeCrash();
      }

   }

   public void freeToPool() {
      if(this.world.level == 4) {
         this.world.TankPool.free((Tank)this);
      }

   }

   public float getTimeOfCrash() {
      return 15.0F;
   }

   public boolean isCrashed() {
      return this.cannon.isCrashed();
   }

   public void render(SpriteBatch var1) {
      super.render(var1);
      this.cannon.render(var1);
   }

   public void reset(World var1, float var2, float var3) {
      super.reset(var1, 10, var2, var3);
      this.velocity.set(0.0F, 0.0F);
      this.cannon.reset(this.bounds.x + CANNONPOSITION.x, this.bounds.y + CANNONPOSITION.y);
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyTextureBroRegion;
   }

   public void update(float var1) {
      super.update(var1);
      this.cannon.update(var1);
      if(this.clockPursue.isFired() && !this.cannon.isCrashed()) {
         float var2 = this.world.fighter.getPositionX() - this.getPositionX();
         float var3 = this.world.fighter.getPositionY() - this.getPositionY();
         if(var3 < 0.0F) {
            float var4 = 90.0F + 57.295776F * MathUtils.atan2(var3, var2);
            this.cannon.rotateTo(var4);
         }
      }

   }

   protected void updateDynamicParameter(float var1) {
      super.updateDynamicParameter(var1);
      this.cannon.setPosition(this.bounds.x + CANNONPOSITION.x, this.bounds.y + CANNONPOSITION.y);
   }

   public void updateShooting(float var1) {
      if(this.clockShooting.isFired()) {
         this.cannon.shooting();
      }

   }

   protected boolean usePathAngle() {
      return false;
   }
}
