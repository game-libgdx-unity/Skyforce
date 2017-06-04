package com.doodlegames.air.force.enemy.level7;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.enemy.level7.BossLv7L;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.prop.PropBullet;
import com.doodlegames.air.force.resource.Assets_level7;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;

import java.util.Iterator;

public class BossLv7Egg extends Enemy {

   private static final float EGGHEIGHT;
   private static final float EGGWIDTH;
   private static Animation eggAnimation;
   private static int healthyDegree = 1;
   boolean bulletShooted = false;
   public float crashTime = 2.0F;


   static {
      TextureRegion[] var0 = new TextureRegion[]{Assets_level7.atlas_Enemy.findRegion("lv_7_boss_egg", 0), Assets_level7.atlas_Enemy.findRegion("lv_7_boss_egg", 1), Assets_level7.atlas_Enemy.findRegion("lv_7_boss_egg", 2)};
      eggAnimation = new Animation(0, 0.2F, var0);
      EGGWIDTH = (float)RegionUtilFunctions.getRegionWidth(eggAnimation);
      EGGHEIGHT = (float)RegionUtilFunctions.getRegionHeight(eggAnimation);
   }

   public BossLv7Egg(World var1, float var2, float var3) {
      super(var1, healthyDegree, var2, var3, EGGWIDTH, EGGHEIGHT);
      this.crashTime = (float)(1 + var1.rand.nextInt(12));
      this.initDeath();
   }

   public static BossLv7Egg.EggManager getEggManager(World var0, BossLv7L var1, Vector2 ... var2) {
      int var3 = var2.length;
      BossLv7Egg.EggManager var4 = new BossLv7Egg.EggManager(var0);

      for(int var5 = 0; var5 < var3; ++var5) {
         var4.addAEgg(var1.bounds.x + var2[var5].x + BossLv7L.EGGWIDTH / 2.0F, var1.bounds.y + var2[var5].y + BossLv7L.EGGHEIGHT / 2.0F);
      }

      return var4;
   }

   private void initDeath() {
      if(1 != this.state) {
         this.setInitHealthyDgree(-1);
         this.stateTime = 0.0F;
         this.state = 1;
         this.crashAnimationPutted = true;
         this.bulletShooted = true;
      }
   }

   public static void loadResource() {
      Animation var0 = eggAnimation;
      TextureRegion[] var1 = new TextureRegion[]{Assets_level7.atlas_Enemy.findRegion("lv_7_boss_egg", 0), Assets_level7.atlas_Enemy.findRegion("lv_7_boss_egg", 1), Assets_level7.atlas_Enemy.findRegion("lv_7_boss_egg", 2)};
      eggAnimation = Animation.setAnimationFrame(var0, 0, 0.4F, var1);
   }

   public void beHitByBullet(Bullet var1) {
      super.beHitByBullet(var1);
      if(this.isCrashed() && Clock.rand.nextInt(10) < 3) {
         PropBullet.addABulletProp(this, this.world);
      }

   }

   public float getTimeOfCrash() {
      return this.crashTime;
   }

   public void renderCrash(SpriteBatch var1) {
      this.enemyRegion = null;
      this.enemyBroRegion = null;
      super.renderCrash(var1);
   }

   public void renderEnemy(SpriteBatch var1) {
      this.enemyRegion = eggAnimation.getKeyFrame((float)Clock.getTimeCounter());
      super.renderEnemy(var1);
   }

   public void reset(float var1) {
      float var2 = this.getPositionX();
      float var3 = this.getPositionY();
      super.reset(this.world, healthyDegree, var2, var3);
      this.crashTime = var1;
      this.bulletShooted = false;
   }

   public void update(float var1) {
      super.update(var1);
      if(this.isCrashed() && !this.bulletShooted) {
         for(int var2 = 0; var2 < 4; ++var2) {
            EnemyTools.DelayShootAction var3 = EnemyTools.alienShootBullet(this, 0.0F, 150.0F, this.origin.x, this.origin.y, (float)this.world.rand.nextInt(360));
            var3.crashShoot = true;
            this.addEnemyAction(var3);
            this.bulletShooted = true;
         }
      }

   }

   public void updateShooting(float var1) {}

   public static class EggManager {

      Array<BossLv7Egg> eggs;
      boolean stopReset = false;
      World world;


      public EggManager(World var1) {
         this.world = var1;
         this.eggs = new Array(3);
      }

      public void addAEgg(float var1, float var2) {
         this.eggs.add(new BossLv7Egg(this.world, var1, var2));
      }

      public Array<BossLv7Egg> getEggs() {
         return this.eggs;
      }

      public void makeCrashAndStopGen() {
         Iterator var1 = this.eggs.iterator();

         while(var1.hasNext()) {
            ((BossLv7Egg)var1.next()).makeCrash();
         }

         this.stopGenEggs();
      }

      public void render(SpriteBatch var1) {
         Iterator var2 = this.eggs.iterator();

         while(var2.hasNext()) {
            ((BossLv7Egg)var2.next()).render(var1);
         }

      }

      public void setSleepPostion(BossLv7L var1, Vector2 ... var2) {
         int var3 = var2.length;
         int var4 = Math.min(this.eggs.size, var3);

         for(int var5 = 0; var5 < var4; ++var5) {
            float var6 = var1.bounds.x + var2[var5].x + BossLv7L.EGGWIDTH / 2.0F;
            float var7 = var1.bounds.y + var2[var5].y + BossLv7L.EGGHEIGHT / 2.0F;
            ((BossLv7Egg)this.eggs.get(var5)).setSleepPosition(var6, var7);
         }

      }

      public void stopGenEggs() {
         this.stopReset = true;
      }

      public void update(float var1) {
         Iterator var2 = this.eggs.iterator();

         while(var2.hasNext()) {
            BossLv7Egg var3 = (BossLv7Egg)var2.next();
            var3.update(var1);
            if(!this.stopReset && var3.isCrashed() && var3.stateTime > var3.getTimeOfCrash()) {
               var3.reset((float)(18 + this.world.rand.nextInt(6)));
            }
         }

      }
   }
}
