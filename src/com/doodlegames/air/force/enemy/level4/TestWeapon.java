package com.doodlegames.air.force.enemy.level4;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyProXY;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.enemy.level4.TestWeaponCore;
import com.doodlegames.air.force.enemy.level4.TestWeaponLeft;
import com.doodlegames.air.force.enemy.level4.TestWeaponRight;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.resource.Assets_level4;
import com.doodlegames.air.force.screen.GameScreen;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class TestWeapon extends Enemy implements EnemyTools.EnemyActionCallBack {

   public static final Vector2 COREPARTPOSITION;
   public static final int DEFAULTHEALTHYDEGREE = 10;
   public static final Vector2 LEFTPARTPOSITION;
   private static final int PARTSNUM = 3;
   public static final Vector2 RIGHTPARTPOSITION;
   public static  int SHADOWHEIGHT = 0;
   private static  float SHADOWHEIGHTFLOAT = 0.0F;
   public static final int SHADOWOFFSETX = 33;
   public static final int SHADOWOFFSETY = -28;
   public static final Vector2 SHADOWPOSITION;
   public static final float SHADOWSCALE = 0.8F;
   public static  int SHADOWWIDTH = 0;
   private static  float SHADOWWIDTHFLOAT = 0.0F;
   private static final float TIMEOFPLAYCRASHANIMATION = 0.1F;
   public static TextureAtlas.AtlasRegion shadowTextureRegion = Assets_level4.atlas_Enemy.findRegion("nazi_test_weapon_shadow");
   private Clock clockPursue = new Clock(1.5F);
   private Clock clockShootingLeft = new Clock(0.0F, 5.0F, 0.2F, (byte)1);
   private Clock clockShootingRight = new Clock(2.5F, 5.0F, 0.2F, (byte)1);
   private Enemy[] hitMap;
   private List<Rectangle> hitRectangles;
   private float middleX = 0.0F;
   private Clock moveClock;
   private final float range = 100.0F;
   private TestWeaponCore testWeaponCore;
   private TestWeaponLeft testWeaponLeft;
   private TestWeaponRight testWeaponRight;


   static {
      int var0;
      if(shadowTextureRegion.rotate) {
         var0 = shadowTextureRegion.getRegionHeight();
      } else {
         var0 = shadowTextureRegion.getRegionWidth();
      }

      SHADOWWIDTH = var0;
      int var1;
      if(shadowTextureRegion.rotate) {
         var1 = shadowTextureRegion.getRegionWidth();
      } else {
         var1 = shadowTextureRegion.getRegionHeight();
      }

      SHADOWHEIGHT = var1;
      SHADOWWIDTHFLOAT = (float)SHADOWWIDTH / 1.0F;
      SHADOWHEIGHTFLOAT = (float)SHADOWHEIGHT / 1.0F;
      LEFTPARTPOSITION = new Vector2((-50.0F + (float)TestWeaponLeft.WIDTH / 2.0F) / 1.0F, (22.0F + (float)TestWeaponLeft.HEIGHT / 2.0F) / 1.0F);
      RIGHTPARTPOSITION = new Vector2((99.0F + (float)TestWeaponRight.WIDTH / 2.0F) / 1.0F, (22.0F + (float)TestWeaponRight.HEIGHT / 2.0F) / 1.0F);
      COREPARTPOSITION = new Vector2((0.0F + (float)TestWeaponCore.WIDTH / 2.0F) / 1.0F, (0.0F + (float)TestWeaponCore.HEIGHT / 2.0F) / 1.0F);
      SHADOWPOSITION = new Vector2(-33.0F, -28.0F);
   }

   public TestWeapon(World var1, float var2, float var3) {
      super(var1, Settings.lapFixBossDegree(10), var2, var3, (float)TestWeaponCore.WIDTH / 1.0F, (float)TestWeaponCore.HEIGHT / 1.0F);
      this.testWeaponLeft = new TestWeaponLeft(var1, this.bounds.x + LEFTPARTPOSITION.x, this.bounds.y + LEFTPARTPOSITION.y);
      this.testWeaponRight = new TestWeaponRight(var1, this.bounds.x + RIGHTPARTPOSITION.x, this.bounds.y + RIGHTPARTPOSITION.y);
      this.testWeaponCore = new TestWeaponCore(var1, this.bounds.x + COREPARTPOSITION.x, this.bounds.y + COREPARTPOSITION.y);
      this.velocity.set(Settings.backgroundVelocity);
      this.hitMap = new Enemy[3];
      this.hitRectangles = new ArrayList(3);
      this.needShowWarning = true;
      this.warningPosition = 100.0F + this.bounds.y - 106.666664F;
      this.constructMoveFun();
   }

   public static Enemy addATestWeapon(World var0, float var1, float var2, float var3, List<Enemy> var4) {
      EnemyProXY var5 = (EnemyProXY)EnemyProXY.addAEnemy(new TestWeapon(var0, var1, var2), var3, var4, true);
      var5.addEnemyAction((EnemyTools.EnemyAction)Pools.obtain(EnemyProXY.SmallBossStop.class));
      return var5;
   }

   private void constructMoveFun() {
      this.moveClock = new Clock(0.0F, 1.0F, Clock.ClockType.FireOnlyOnce);
   }

   public static void loadResource() {
      shadowTextureRegion = Assets_level4.atlas_Enemy.findRegion("nazi_test_weapon_shadow");
   }

   private void moveBoss() {
      if(this.isCrashed()) {
         this.velocity.y = Settings.backgroundVelocity.y;
      } else if(!this.world.getStopFighting() && this.moveClock.isFired()) {
         float var1;
         if(Math.abs(this.getPositionX() - this.middleX) < 5.0F) {
            if(Clock.rand.nextBoolean()) {
               var1 = this.middleX - 100.0F;
            } else {
               var1 = 100.0F + this.middleX;
            }
         } else {
            var1 = this.middleX;
         }

         this.addEnemyAction(EnemyTools.moveTo(this, var1, 2.0F));
         this.moveClock.resetClockFireOnce(4.0F + 3.0F * Clock.rand.nextFloat());
         return;
      }

   }

   public boolean act(EnemyTools.EnemyAction var1, float var2) {
      EnemyTools.checkAction(var1, this);
      return false;
   }

   public boolean actionCancel(EnemyTools.EnemyAction var1) {
      return this.isCrashed() && var1 instanceof EnemyTools.MoveToAction;
   }

   public void beHitByBullet(Bullet var1, int var2) {
      if(1 != this.state) {
         this.hitMap[var2].beHitByBullet(var1);
         int var3 = var1.damageValue;
         var1.damageValue = 0;
         super.beHitByBullet(var1);
         var1.damageValue = var3;
         GameScreen.bossHealthyDegree = this.getHealthyDgree();
      }
   }

   public void enemyDropped(EnemyProXY var1) {
      this.velocity.y = 0.0F;
      this.middleX = this.getPositionX();
      this.world.askBeginFighting(2.0F);
   }

   public int getHealthyDgree() {
      return this.testWeaponCore.getHealthyDgree();
   }

   public List<Rectangle> getHitRectangle() {
      this.hitRectangles.clear();
      boolean var1 = this.testWeaponLeft.isCrashed();
      int var2 = 0;
      if(!var1) {
         this.hitRectangles.add(this.testWeaponLeft.bounds);
         Enemy[] var12 = this.hitMap;
         int var13 = 0 + 1;
         var12[0] = this.testWeaponLeft;
         var2 = var13;
      }

      if(!this.testWeaponRight.isCrashed()) {
         this.hitRectangles.add(this.testWeaponRight.bounds);
         Enemy[] var9 = this.hitMap;
         int var10 = var2 + 1;
         var9[var2] = this.testWeaponRight;
         var2 = var10;
      }

      int var3;
      if(!this.testWeaponCore.isCrashed()) {
         this.hitRectangles.add(this.testWeaponCore.getHitR());
         Enemy[] var7 = this.hitMap;
         var3 = var2 + 1;
         var7[var2] = this.testWeaponCore;
      } else {
         var3 = var2;
      }

      while(var3 < 3) {
         Enemy[] var4 = this.hitMap;
         int var5 = var3 + 1;
         var4[var3] = null;
         var3 = var5;
      }

      return this.hitRectangles;
   }

   public float getTimeOfCrash() {
      return 0.1F;
   }

   public boolean isCrashed() {
      if(this.testWeaponCore.isCrashed()) {
         this.testWeaponLeft.makeCrash();
         this.testWeaponRight.makeCrash();
         this.makeCrash();
         return true;
      } else {
         return false;
      }
   }

   public void render(SpriteBatch var1) {
      this.testWeaponLeft.render(var1);
      this.testWeaponRight.render(var1);
      this.testWeaponCore.render(var1);
   }

   public void renderShadow(SpriteBatch var1) {
      float var2 = var1.getColor().toFloatBits();
      var1.setColor(0.0F, 0.0F, 0.0F, 0.5F);
      this.testWeaponLeft.renderShadow(var1, 33.0F, -28.0F, 0.8F, 0.8F);
      this.testWeaponCore.renderShadow(var1, 33.0F, -28.0F, 0.8F, 0.8F);
      this.testWeaponRight.renderShadow(var1, 33.0F, -28.0F, 0.8F, 0.8F);
      var1.setColor(var2);
   }

   public void setSleepPosition(float var1, float var2) {
      super.setSleepPosition(var1, var2);
      this.testWeaponLeft.setPosition(this.bounds.x + LEFTPARTPOSITION.x, this.bounds.y + LEFTPARTPOSITION.y);
      this.testWeaponRight.setPosition(this.bounds.x + RIGHTPARTPOSITION.x, this.bounds.y + RIGHTPARTPOSITION.y);
      this.testWeaponCore.setPosition(this.bounds.x + COREPARTPOSITION.x, this.bounds.y + COREPARTPOSITION.y);
   }

   protected void showWarning() {
      this.world.showWarining(0);
   }

   public void update(float var1) {
      super.update(var1);
      this.testWeaponLeft.update(var1);
      this.testWeaponRight.update(var1);
      this.testWeaponCore.update(var1);
      this.moveBoss();
   }

   public void updateDynamicParameter(float var1) {
      super.updateDynamicParameter(var1);
      this.testWeaponLeft.setPosition(this.bounds.x + LEFTPARTPOSITION.x, this.bounds.y + LEFTPARTPOSITION.y);
      this.testWeaponRight.setPosition(this.bounds.x + RIGHTPARTPOSITION.x, this.bounds.y + RIGHTPARTPOSITION.y);
      this.testWeaponCore.setPosition(this.bounds.x + COREPARTPOSITION.x, this.bounds.y + COREPARTPOSITION.y);
   }

   public void updateShooting(float var1) {
      if(this.clockPursue.isFired()) {
         float var2 = 90.0F + 57.295776F * MathUtils.atan2(this.world.fighter.position.y - (this.bounds.y + this.origin.y), this.world.fighter.position.x - (this.bounds.x + this.origin.x));
         if(!this.testWeaponLeft.isCrashed()) {
            this.testWeaponLeft.setVelAngle(var2);
         }

         if(!this.testWeaponRight.isCrashed()) {
            this.testWeaponRight.setVelAngle(var2);
         }
      }

      if(this.clockShootingRight.isFired()) {
         this.testWeaponRight.shooting(Clock.rand.nextInt(2));
      }

      if(this.clockShootingLeft.isFired()) {
         this.testWeaponLeft.shooting(Clock.rand.nextInt(2));
      }

   }
}
