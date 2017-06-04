package com.doodlegames.air.force.enemy.level7;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyProXY;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.enemy.HitDistribute;
import com.doodlegames.air.force.enemy.level7.BossLv7STube;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.BulletScreen;
import com.doodlegames.air.force.game.laser.CommonLaser;
import com.doodlegames.air.force.game.laser.LaserGun;
import com.doodlegames.air.force.resource.Assets_ShareInAlien;
import com.doodlegames.air.force.resource.Assets_level7;
import com.doodlegames.air.force.screen.GameScreen;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.List;

public class BossLv7S extends Enemy implements World.RenderObject {

   private static  float BASEHEIGHT = 0.0F;
   private static final Vector2 BASEPOSITION;
   private static  float BASEWIDTH = 0.0F;
   private static final float BULLETVEL = 400.0F;
   public static final int DEFAULTHEALTHYDEGREE = 140;
   private static final Vector2[] HEADSHOOTPOSITION;
   public static int HEIGHT = 0;
   private static final Vector2[] LASERSHOOTERPOSITION;
   private static  float SHADOWHEIGHT = 0.0F;
   private static final Vector2 SHADOWPOSITION;
   private static float SHADOWWIDTH = 0.0F;
   private static final int SHOOTPOINTNUM = 4;
   private static final float TIMEOFPLAYCRASHANIMATION = 15.0F;
   private static final int TUBENUM = 2;
   private static final Vector2[] TUBEPOSITION;
   private static final int WIDTH;
   private static TextureAtlas.AtlasRegion baseRegion;
   private static TextureAtlas.AtlasRegion enemyBroTextureRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_s_head_broken");
   private static TextureAtlas.AtlasRegion enemyTextureRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_s_head");
   private static TextureAtlas.AtlasRegion shadowRegion;
   private Clock clockShoot;
   private HitDistribute hitProcessor;
   private LaserGun laserGun;
   private BossLv7STube[] tube;


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
      Vector2[] var2 = new Vector2[]{new Vector2(33.0F, 92.0F), new Vector2(71.0F, 94.0F)};
      LASERSHOOTERPOSITION = var2;
      Vector2[] var3 = new Vector2[]{new Vector2(58.0F, 37.0F), new Vector2(58.0F, 28.0F), new Vector2(53.0F, 32.0F), new Vector2(63.0F, 32.0F)};
      HEADSHOOTPOSITION = var3;
      shadowRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_s_shadow");
      int var4;
      if(shadowRegion.rotate) {
         var4 = shadowRegion.getRegionHeight();
      } else {
         var4 = shadowRegion.getRegionWidth();
      }

      SHADOWWIDTH = (float)var4 / 1.0F;
      int var5;
      if(shadowRegion.rotate) {
         var5 = shadowRegion.getRegionWidth();
      } else {
         var5 = shadowRegion.getRegionHeight();
      }

      SHADOWHEIGHT = (float)var5 / 1.0F;
      baseRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_s_base");
      int var6;
      if(baseRegion.rotate) {
         var6 = baseRegion.getRegionHeight();
      } else {
         var6 = baseRegion.getRegionWidth();
      }

      BASEWIDTH = (float)var6 / 1.0F;
      int var7;
      if(baseRegion.rotate) {
         var7 = baseRegion.getRegionWidth();
      } else {
         var7 = baseRegion.getRegionHeight();
      }

      BASEHEIGHT = (float)var7 / 1.0F;
      SHADOWPOSITION = new Vector2(-47.0F, -121.0F);
      BASEPOSITION = new Vector2(-48.0F, -28.0F);
      Vector2[] var8 = new Vector2[]{new Vector2((-24.0F + (float)BossLv7STube.WIDTH / 2.0F) / 1.0F, (16.0F + (float)BossLv7STube.HEIGHT / 2.0F) / 1.0F), new Vector2((117.0F + (float)BossLv7STube.WIDTH / 2.0F) / 1.0F, (16.0F + (float)BossLv7STube.HEIGHT / 2.0F) / 1.0F)};
      TUBEPOSITION = var8;
   }

   public BossLv7S(World var1, float var2, float var3) {
      super(var1, Settings.lapFixBossDegree(140), var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyBroTextureRegion;
      this.tube = new BossLv7STube[2];

      for(int var4 = 0; var4 < 2; ++var4) {
         this.tube[var4] = new BossLv7STube(var1, this.bounds.x + TUBEPOSITION[var4].x, this.bounds.y + TUBEPOSITION[var4].y);
      }

      this.hitProcessor = new HitDistribute(this, 3);
      this.clockShoot = new Clock(0.0F, 4.0F);
      this.laserGun = new LaserGun(this, Assets_ShareInAlien.gather, 0.2F, 3.0F, 3.0F, 1.0F, LASERSHOOTERPOSITION[0].x, LASERSHOOTERPOSITION[0].y, CommonLaser.getWidthOfAlienQuart());
      this.laserGun.shooterPointIsAbsolute = false;
      this.laserGun.setLoopFire(false);
      this.laserGun.setOriginX(31.0F);
      this.laserGun.setOriginY(49.0F);

      for(int var6 = 0; var6 < 130; ++var6) {
         float var10 = CommonLaser.getDeltaYOfAlienQuart(var6);
         if(var6 == 0) {
            Enemy.lasers.add(CommonLaser.alienLaserGenQuartHead.getALaser(this, this.laserGun, var10));
         } else {
            Enemy.lasers.add(CommonLaser.alienLaserGenQuart.getALaser(this, this.laserGun, var10));
         }
      }

      this.laserGun.angle = -45.0F;
      LaserGun var7 = this.laserGun;
      LaserGun var8 = this.laserGun;
      var7.updater = var8.new UpdateLaserGun1(-45.0F, 45.0F);
      this.laserGun.startShoot((float)var1.rand.nextInt(3));
      this.needShowWarning = true;
      this.warningPosition = 100.0F + this.bounds.y - 106.666664F;
      this.world.addObjectToBGM(this);
   }

   public static Enemy addABossLv7S(World var0, float var1, float var2, float var3, List<Enemy> var4) {
      EnemyProXY var5 = (EnemyProXY)EnemyProXY.addAEnemy(new BossLv7S(var0, var1, var2), var3, var4, false);
      var5.addEnemyAction((EnemyTools.EnemyAction)Pools.obtain(EnemyProXY.SmallBossStop.class));
      return var5;
   }

   private void addShootAction() {
      EnemyTools.alienShootScreen(this, HEADSHOOTPOSITION[1].x, HEADSHOOTPOSITION[1].y, BulletScreen.getPublicMiddleScreen());
   }

   public static void loadResource() {
      enemyTextureRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_s_head");
      enemyBroTextureRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_s_head_broken");
      shadowRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_s_shadow");
      baseRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_s_base");
   }

   private void updateLaserGun(float var1) {
      LaserGun.LaserGunState var2 = this.laserGun.getLaserGunState();
      if(this.isCrashed() && var2 != LaserGun.LaserGunState.Sleep) {
         this.laserGun.forceStop();
      } else {
         if(this.laserGun.getSleepClock().getCycleCount() != 0 && var2 == LaserGun.LaserGunState.Sleep) {
            this.laserGun.getSleepClock().resetClockFireOnce((float)(1 + this.world.rand.nextInt(2)));
            int var3 = this.world.rand.nextInt(2);
            float var4 = LASERSHOOTERPOSITION[var3].x;
            float var5 = LASERSHOOTERPOSITION[var3].y;
            this.laserGun.setshootPointRelativeX(var4);
            this.laserGun.setshootPointRelativeY(var5);
            LaserGun var8 = this.laserGun;
            float var9;
            if(this.world.rand.nextInt(2) == 0) {
               var9 = 45.0F;
            } else {
               var9 = -45.0F;
            }

            var8.angle = var9;
         }

         this.laserGun.updateShooting(var1);
      }
   }

   public void beHitByBullet(Bullet var1, int var2) {
      this.hitProcessor.beHitByBullet(var1, var2);
      if(this.isCrashed()) {
         this.laserGun.setRemoveAllLasers();
      }

      GameScreen.bossHealthyDegree = this.getHealthyDgree();
   }

   public boolean canBeHit() {
      return !super.isCrashed();
   }

   public void enemyDropped(EnemyProXY var1) {
      this.velocity.y = 0.0F;
      this.world.askBeginFighting(2.0F);
   }

   public List<Rectangle> getHitRectangle() {
      this.hitProcessor.beginAdd();
      this.hitProcessor.addEnemyHitRec(this);

      for(int var1 = 0; var1 < 2; ++var1) {
         this.hitProcessor.addEnemyHitRec(this.tube[var1]);
      }

      this.hitProcessor.endAdd();
      return this.hitProcessor.getHitRectangle();
   }

   public List<Rectangle> getHitRectangle(int var1) {
      return super.getHitRectangle();
   }

   public float getTimeOfCrash() {
      return 15.0F;
   }

   public boolean isCrashed() {
      if(super.isCrashed() && !this.tube[0].isCrashed()) {
         this.tube[0].makeCrash();
      }

      if(super.isCrashed() && !this.tube[1].isCrashed()) {
         this.tube[1].makeCrash();
      }

      return super.isCrashed();
   }

   public void render(SpriteBatch var1) {
      var1.draw(baseRegion, this.bounds.x + BASEPOSITION.x, this.bounds.y + BASEPOSITION.y, 0.0F, 0.0F, BASEWIDTH, BASEHEIGHT, 1.0F, 1.0F, 0.0F);
      this.tube[0].render(var1);
      this.tube[1].render(var1);
      super.render(var1);
      if(!this.isCrashed()) {
         this.laserGun.renderLaserAggregation(var1);
      }

   }

   public void renderObject(SpriteBatch var1) {
      var1.draw(shadowRegion, this.bounds.x + SHADOWPOSITION.x, this.bounds.y + SHADOWPOSITION.y, 0.0F, 0.0F, SHADOWWIDTH, SHADOWHEIGHT, 1.0F, 1.0F, 0.0F);
   }

   public void setSleepPosition(float var1, float var2) {
      super.setSleepPosition(var1, var2);

      for(int var3 = 0; var3 < TUBEPOSITION.length; ++var3) {
         float var4 = this.bounds.x + TUBEPOSITION[var3].x;
         float var5 = this.bounds.y + TUBEPOSITION[var3].y;
         this.tube[var3].setSleepPosition(var4, var5);
      }

   }

   protected void showWarning() {
      this.world.showWarining(0);
   }

   public void update(float var1) {
      this.velocity.set(Settings.backgroundVelocity);
      super.update(var1);
      this.tube[0].update(var1);
      this.tube[1].update(var1);
      if(this.clockShoot.isFired() && !this.world.getStopFighting()) {
         this.addShootAction();
      }

      this.updateLaserGun(var1);
   }

   public void updateDynamicParameter(float var1) {
      super.updateDynamicParameter(var1);

      for(int var2 = 0; var2 < TUBEPOSITION.length; ++var2) {
         float var3 = this.bounds.x + TUBEPOSITION[var2].x;
         float var4 = this.bounds.y + TUBEPOSITION[var2].y;
         this.tube[var2].setSleepPosition(var3, var4);
      }

   }

   public void updateShooting(float var1) {}
}
