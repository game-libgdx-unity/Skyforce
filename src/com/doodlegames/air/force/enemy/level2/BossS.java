package com.doodlegames.air.force.enemy.level2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyProXY;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.enemy.cannon.Cannon1;
import com.doodlegames.air.force.enemy.cannon.Cannon3;
import com.doodlegames.air.force.enemy.cannon.CommonCannon;
import com.doodlegames.air.force.enemy.level2.BossSLeftArm;
import com.doodlegames.air.force.enemy.level2.BossSRightArm;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.resource.Assets_level2;
import com.doodlegames.air.force.screen.GameScreen;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BossS extends Enemy implements EnemyTools.EnemyActionCallBack {

   public static final Vector2 CANNONLEFTPOSITION = new Vector2((35.0F + (float)Cannon1.canonTextureRegion.getRegionWidth() / 2.0F) / 1.0F, (140.0F + (float)Cannon1.canonTextureRegion.getRegionHeight() / 2.0F) / 1.0F);
   public static final Vector2 CANNONRIGHTPOSITION = new Vector2((100.0F + (float)Cannon1.canonTextureRegion.getRegionWidth() / 2.0F) / 1.0F, (140.0F + (float)Cannon1.canonTextureRegion.getRegionHeight() / 2.0F) / 1.0F);
   public static final Vector2 CANNONTOPPOSITION = new Vector2((65.0F + (float)Cannon3.canonTextureRegion.getRegionWidth() / 2.0F) / 1.0F, (170.0F + (float)Cannon3.canonTextureRegion.getRegionHeight() / 2.0F) / 1.0F);
   public static final int DEFAULTHEALTHYDEGREE = 10;
   private static final int PARTSNUM = 5;
   private static final float TIMEOFPLAYCRASHANIMATION = 15.0F;
   private static TextureAtlas.AtlasRegion enemyTextureBroRegion = Assets_level2.atlas_Enemy.findRegion("boss_body_broken");
   private static TextureAtlas.AtlasRegion enemyTextureRegion = Assets_level2.atlas_Enemy.findRegion("boss_body");
   private static final Vector2 rocket_l_position = new Vector2((-1.0F + (float)BossSLeftArm.regionWidth / 2.0F) / 1.0F, (107.0F + (float)BossSLeftArm.regionHeight / 2.0F) / 1.0F);
   private static final Vector2 rocket_r_position = new Vector2((131.0F + (float)BossSRightArm.regionWidth / 2.0F) / 1.0F, (107.0F + (float)BossSRightArm.regionHeight / 2.0F) / 1.0F);
   private BossS.BossSInit bossInit;
   private BossS.BossSMove bossSMove;
   private CommonCannon cannonLeft;
   private CommonCannon cannonRight;
   private Cannon3 cannonTop;
   private Clock clockPursue;
   private Clock clockShootingArmL;
   private Clock clockShootingArmR;
   private Clock clockShootingCanonTop;
   private Clock clockTurning;
   private Enemy[] hitMap;
   private List<Rectangle> hitRectangles;
   private BossSLeftArm leftArm;
   private BossSRightArm rightArm;


   public BossS(World var1, float var2, float var3) {
	   
	   super(var1, 10, var2, var3, enemyTextureRegion.rotate?(float)enemyTextureRegion.getRegionHeight() / 1.0F:
		   (float)enemyTextureRegion.getRegionWidth() / 1.0F, enemyTextureRegion.rotate?
				   (float)enemyTextureRegion.getRegionWidth() / 1.0F:(float)enemyTextureRegion.getRegionHeight() / 1.0F   );

      
      Clock var6 = CommonCannon.getClock3(300.0F);
      this.cannonLeft = CommonCannon.getAutoCannon1(var1, var6, Settings.lapFixBossDegree(20), this, 35.0F, 140.0F, 300.0F);
      this.cannonRight = CommonCannon.getAutoCannon1(var1, var6, Settings.lapFixBossDegree(20), this, 100.0F, 140.0F, 300.0F);
      this.cannonTop = new Cannon3(var1, this.bounds.x + CANNONTOPPOSITION.x, this.bounds.y + CANNONTOPPOSITION.y);
      this.leftArm = new BossSLeftArm(var1, this.bounds.x + rocket_l_position.x, this.bounds.y + rocket_l_position.y);
      this.rightArm = new BossSRightArm(var1, this.bounds.x + rocket_r_position.x, this.bounds.y + rocket_r_position.y);
      this.cannonTop.setInitHealthyDgree(Settings.lapFixBossDegree(20));
      this.cannonTop.angularSpeed = 90.0F;
      this.hitMap = new Enemy[5];
      this.hitRectangles = new ArrayList(5);
      this.clockShootingArmL = new Clock(5.0F, 10.0F);
      this.clockShootingArmR = new Clock(0.0F, 10.0F);
      this.clockShootingCanonTop = new Clock(15.0F, 16.0F, 1.0F, (byte)8);
      this.clockPursue = new Clock(1.5F);
      this.clockTurning = new Clock(3.25F, 4.0F);
      this.velocity.set(Settings.backgroundVelocity);
      this.needShowWarning = true;
      this.warningPosition = 106.666664F + 100.0F + this.bounds.y;
      this.bossInit = (BossS.BossSInit)Pools.obtain(BossS.BossSInit.class);
      this.bossSMove = (BossS.BossSMove)Pools.obtain(BossS.BossSMove.class);
      this.outRemove = false;
      this.outRender = true;
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyTextureBroRegion;
   }

   public static Enemy addABossSLv2(World var0, float var1, float var2, float var3, List<Enemy> var4) {
      EnemyProXY var5 = (EnemyProXY)((EnemyProXY)EnemyProXY.addAEnemy(new BossS(var0, var1, var2), var3, var4, false));
      EnemyProXY.SmallBossStop var6 = (EnemyProXY.SmallBossStop)Pools.obtain(EnemyProXY.SmallBossStop.class);
      var6.clearEnemyAction();
      var5.addEnemyAction(var6);
      return var5;
   }

   public static float getRegionHeight() {
      return (float)RegionUtilFunctions.getRegionHeight((TextureRegion)enemyTextureRegion) / 1.0F;
   }

   private void keepRelativePosition() {
      this.cannonLeft.setPosition(this.bounds.x + CANNONLEFTPOSITION.x, this.bounds.y + CANNONLEFTPOSITION.y);
      this.cannonRight.setPosition(this.bounds.x + CANNONRIGHTPOSITION.x, this.bounds.y + CANNONRIGHTPOSITION.y);
      this.cannonTop.setPosition(this.bounds.x + CANNONTOPPOSITION.x, this.bounds.y + CANNONTOPPOSITION.y);
      this.leftArm.setPosition(this.bounds.x + rocket_l_position.x, this.bounds.y + rocket_l_position.y);
      this.rightArm.setPosition(this.bounds.x + rocket_r_position.x, this.bounds.y + rocket_r_position.y);
   }

   public static void loadResource() {
      enemyTextureRegion = Assets_level2.atlas_Enemy.findRegion("boss_body");
      enemyTextureBroRegion = Assets_level2.atlas_Enemy.findRegion("boss_body_broken");
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
         GameScreen.bossHealthyDegree = this.getHealthyDgree();
      }
   }

   public void enemyDropped(EnemyProXY var1) {
      float var2 = Math.abs(this.getPositionX() - 346.0F) / 150.0F;
      this.bossInit.setEnemy(this);
      this.bossSMove.bossS = this;
      EnemyTools.EnemyAction[] var3 = new EnemyTools.EnemyAction[]{EnemyTools.moveTo(this, 346.0F, var2), this.bossInit, this.bossSMove};
      this.addEnemyAction(EnemyTools.sequence(var3));
      Settings.i("BossS:\n bossS is dropped:\n x:" + this.getPositionX() + "\n" + " y:" + this.getPositionY() + "\n" + " and adding moveTo Action, initAction, bossSmoveAction.");
   }

   public int getHealthyDgree() {
      int var1;
      if(this.cannonLeft.getHealthyDgree() > 0) {
         var1 = this.cannonLeft.getHealthyDgree();
      } else {
         var1 = 0;
      }

      int var2 = 0 + var1;
      int var3;
      if(this.cannonRight.getHealthyDgree() > 0) {
         var3 = this.cannonRight.getHealthyDgree();
      } else {
         var3 = 0;
      }

      int var4 = var2 + var3;
      int var5;
      if(this.cannonTop.getHealthyDgree() > 0) {
         var5 = this.cannonTop.getHealthyDgree();
      } else {
         var5 = 0;
      }

      int var6 = var4 + var5;
      int var7;
      if(this.leftArm.getHealthyDgree() > 0) {
         var7 = this.leftArm.getHealthyDgree();
      } else {
         var7 = 0;
      }

      int var8 = var6 + var7;
      int var9 = this.rightArm.getHealthyDgree();
      int var10 = 0;
      if(var9 > 0) {
         var10 = this.rightArm.getHealthyDgree();
      }

      return var8 + var10;
   }

   public List<Rectangle> getHitRectangle() {
      this.hitRectangles.clear();
      boolean var1 = this.cannonLeft.isCrashed();
      int var2 = 0;
      if(!var1) {
         this.hitRectangles.add(this.cannonLeft.bounds);
         Enemy[] var18 = this.hitMap;
         int var19 = 0 + 1;
         var18[0] = this.cannonLeft;
         var2 = var19;
      }

      if(!this.cannonRight.isCrashed()) {
         this.hitRectangles.add(this.cannonRight.bounds);
         Enemy[] var15 = this.hitMap;
         int var16 = var2 + 1;
         var15[var2] = this.cannonRight;
         var2 = var16;
      }

      if(!this.cannonTop.isCrashed()) {
         this.hitRectangles.add(this.cannonTop.bounds);
         Enemy[] var12 = this.hitMap;
         int var13 = var2 + 1;
         var12[var2] = this.cannonTop;
         var2 = var13;
      }

      if(!this.leftArm.isCrashed()) {
         this.hitRectangles.add(this.leftArm.gethitR());
         Enemy[] var9 = this.hitMap;
         int var10 = var2 + 1;
         var9[var2] = this.leftArm;
         var2 = var10;
      }

      int var3;
      if(!this.rightArm.isCrashed()) {
         this.hitRectangles.add(this.rightArm.gethitR());
         Enemy[] var7 = this.hitMap;
         var3 = var2 + 1;
         var7[var2] = this.rightArm;
      } else {
         var3 = var2;
      }

      while(var3 < 5) {
         Enemy[] var4 = this.hitMap;
         int var5 = var3 + 1;
         var4[var3] = null;
         var3 = var5;
      }

      return this.hitRectangles;
   }

   public float getTimeOfCrash() {
      return 15.0F;
   }

   public boolean isCrashed() {
      if(this.cannonLeft.isCrashed() && this.cannonRight.isCrashed() && this.cannonTop.isCrashed() && this.leftArm.isCrashed() && this.rightArm.isCrashed()) {
         if(!super.isCrashed()) {
            super.makeCrash();
         }

         return true;
      } else {
         return false;
      }
   }

   protected void putCrashAniamtion() {
      super.putCrashAniamtion();
   }

   public void render(SpriteBatch var1) {
      super.render(var1);
      this.cannonLeft.render(var1);
      this.cannonRight.render(var1);
      this.cannonTop.render(var1);
      this.leftArm.render(var1);
      this.rightArm.render(var1);
   }

   public void setPosition(float var1, float var2) {
      super.setPosition(var1, var2);
      this.keepRelativePosition();
   }

   public void setSleepPosition(float var1, float var2) {
      super.setSleepPosition(var1, var2);
      this.keepRelativePosition();
   }

   protected void showWarning() {
      this.world.showWarining(0);
   }

   public void update(float var1) {
      this.velocity.set(Settings.backgroundVelocity);
      super.update(var1);
      if(this.clockTurning.isFired()) {
         this.cannonTop.angularSpeed = -this.cannonTop.angularSpeed;
      }

      if(this.cannonTop.isCrashed()) {
         this.cannonTop.angularSpeed = 0.0F;
      }

      this.cannonLeft.update(var1);
      this.cannonRight.update(var1);
      this.cannonTop.update(var1);
      this.leftArm.update(var1);
      this.rightArm.update(var1);
   }

   public void updateDynamicParameter(float var1) {
      super.updateDynamicParameter(var1);
      this.keepRelativePosition();
   }

   public void updateShooting(float var1) {
      if(!this.bossSMove.isMoving()) {
         if(this.clockShootingArmL.isFired()) {
            this.leftArm.shooting();
         }

         if(this.clockShootingArmR.isFired()) {
            this.rightArm.shooting();
         }

         if(this.clockShootingCanonTop.isFired()) {
            this.cannonTop.shooting();
            return;
         }
      }

   }

   public static class BossSInit extends EnemyTools.EnemyAction {

      public boolean act(float var1) {
         Enemy var2 = super.getEnemy();
         var2.outRemove = true;
         var2.outRender = false;
         var2.world.askBeginFighting(1.0F);
         Settings.i("BossS:\nBossS is init. set outRemove to true,\n set outRender to false, asking Begin Fighting in 1 second.\n");
         return true;
      }
   }

   public static class BossSMove extends EnemyTools.EnemyAction {

      private static final float bossVel = 150.0F;
      public static final int centerDelta = 26;
      public static final float middleX = 346.0F;
      public static final float range = 100.0F;
      BossS bossS;
      Clock moveClock;
      float vel = 150.0F;


      public BossSMove() {
         this.moveClock = new Clock(0.0F, 4.0F, Clock.ClockType.FireOnlyOnce);
      }

      public boolean act(float var1) {
         if(this.moveClock.isFired() && !this.bossS.isCrashed()) {
            float var2 = this.bossS.getPositionX();
            float var3 = var2;
            if(Math.abs(var2 - 346.0F) < 5.0F) {
               if(Clock.rand.nextBoolean()) {
                  var3 = 246.0F;
               } else {
                  var3 = 446.0F;
               }
            }

            if(Math.abs(var2 - 346.0F - 100.0F) < 5.0F) {
               var3 = 346.0F;
            }

            if(Math.abs(100.0F + (var2 - 346.0F)) < 5.0F) {
               var3 = 346.0F;
            }

            this.bossS.addEnemyAction(EnemyTools.moveTo(this.bossS, var3, 0.6666667F));
            this.moveClock.resetClockFireOnce(4.0F + 6.0F * Clock.rand.nextFloat());
            Settings.i("BossS:\nBossS is adding moving Action,\n and resetMoveClock.\n");
         }

         return false;
      }

      public boolean isMoving() {
         Iterator var1 = this.bossS.enemyActions.iterator();

         do {
            if(!var1.hasNext()) {
               return false;
            }
         } while(!((EnemyTools.EnemyAction)var1.next() instanceof EnemyTools.MoveToAction));

         return true;
      }
   }
}
