package com.doodlegames.air.force.enemy.level3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyProXY;
import com.doodlegames.air.force.enemy.level3.BossLv3Building1;
import com.doodlegames.air.force.enemy.level3.BossLv3Building2;
import com.doodlegames.air.force.enemy.level3.BossLv3Building3;
import com.doodlegames.air.force.enemy.level3.BossLv3Building4;
import com.doodlegames.air.force.enemy.level3.MoonCanon;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.resource.Assets_level3;
import com.doodlegames.air.force.screen.GameScreen;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class BossLv3 extends Enemy {

   public static final Vector2 BUILDING1POSITION;
   public static final Vector2 BUILDING2POSITION;
   public static final Vector2 BUILDING3POSITION;
   public static final Vector2 BUILDING4POSITION;
   public static final int BUILDINGHEIGHT = 36;
   public static final int DEFAULTHEALTHYDEGREE = 10;
   public static final int HEALTHYDELTA = 30;
   public static final int HOUSEHEALTHYDEGREE = 180;
   public static int LAND1HEIGHT = 0;
   private static final Vector2 LAND1POSITION;
   private static int LAND1WIDTH = 0;
   private static int LAND2HEIGHT = 0;
   private static final Vector2 LAND2POSITION;
   private static int LAND2WIDTH = 0;
   private static int LAND3HEIGHT = 0;
   private static final Vector2 LAND3POSITION;
   private static int LAND3WIDTH = 0;
   private static final Vector2 MOONCANNON1POSITION;
   private static final Vector2 MOONCANNON2POSITION;
   private static final Vector2 MOONCANNON3POSITION;
   private static final Vector2 MOONCANNON4POSITION;
   private static final int PARTSNUM = 56;
   public static final float RISINGSPEED = 36.0F;
   private static int SHADOWHEIGHT = 0;
   private static final Vector2 SHADOWPOSITION;
   private static int SHADOWWIDTH = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 18.0F;
   private static TextureAtlas.AtlasRegion land1Region = Assets_level3.atlas_Enemy.findRegion("lv3_boss_land", 1);
   private static TextureAtlas.AtlasRegion land2Region = Assets_level3.atlas_Enemy.findRegion("lv3_boss_land", 2);
   private static TextureAtlas.AtlasRegion land3Region = Assets_level3.atlas_Enemy.findRegion("lv3_boss_land", 3);
   private static TextureAtlas.AtlasRegion shadowRegion = Assets_level3.atlas_Enemy.findRegion("lv3_boss_land_shadow");
   private BossLv3Building1 building1;
   private BossLv3Building2 building2;
   private BossLv3Building3 building3;
   private BossLv3Building4 building4;
   float dropTime;
   private BossLv3.HitMapItem[] hitMap = new BossLv3.HitMapItem[56];
   private List<Rectangle> hitRectangles;
   private int liveCannonNum;
   private BossLv3.BossLv3MoonCannon moonCanon1;
   private BossLv3.BossLv3MoonCannon moonCanon2;
   private BossLv3.BossLv3MoonCannon moonCanon3;
   private BossLv3.BossLv3MoonCannon moonCanon4;


   static {
      int var0;
      if(land1Region.rotate) {
         var0 = land1Region.getRegionHeight();
      } else {
         var0 = land1Region.getRegionWidth();
      }

      LAND1WIDTH = var0;
      int var1;
      if(land1Region.rotate) {
         var1 = land1Region.getRegionWidth();
      } else {
         var1 = land1Region.getRegionHeight();
      }

      LAND1HEIGHT = var1;
      int var2;
      if(land2Region.rotate) {
         var2 = land2Region.getRegionHeight();
      } else {
         var2 = land2Region.getRegionWidth();
      }

      LAND2WIDTH = var2;
      int var3;
      if(land2Region.rotate) {
         var3 = land2Region.getRegionWidth();
      } else {
         var3 = land2Region.getRegionHeight();
      }

      LAND2HEIGHT = var3;
      int var4;
      if(land3Region.rotate) {
         var4 = land3Region.getRegionHeight();
      } else {
         var4 = land3Region.getRegionWidth();
      }

      LAND3WIDTH = var4;
      int var5;
      if(land3Region.rotate) {
         var5 = land3Region.getRegionWidth();
      } else {
         var5 = land3Region.getRegionHeight();
      }

      LAND3HEIGHT = var5;
      int var6;
      if(shadowRegion.rotate) {
         var6 = shadowRegion.getRegionHeight();
      } else {
         var6 = shadowRegion.getRegionWidth();
      }

      SHADOWWIDTH = var6;
      int var7;
      if(shadowRegion.rotate) {
         var7 = shadowRegion.getRegionWidth();
      } else {
         var7 = shadowRegion.getRegionHeight();
      }

      SHADOWHEIGHT = var7;
      LAND1POSITION = new Vector2(0.0F, 0.0F);
      LAND2POSITION = new Vector2(-17.0F, 114.0F);
      LAND3POSITION = new Vector2(46.0F, 265.0F);
      SHADOWPOSITION = new Vector2(58.0F, -41.0F);
      BUILDING1POSITION = new Vector2((96.0F + (float)BossLv3Building1.WIDTH / 2.0F) / 1.0F, (229.0F + (float)BossLv3Building1.HEIGHT / 2.0F) / 1.0F);
      BUILDING2POSITION = new Vector2((237.0F + (float)BossLv3Building2.WIDTH / 2.0F) / 1.0F, (229.0F + (float)BossLv3Building2.HEIGHT / 2.0F) / 1.0F);
      BUILDING3POSITION = new Vector2((237.0F + (float)BossLv3Building3.WIDTH / 2.0F) / 1.0F, (90.0F + (float)BossLv3Building3.HEIGHT / 2.0F) / 1.0F);
      BUILDING4POSITION = new Vector2((96.0F + (float)BossLv3Building4.WIDTH / 2.0F) / 1.0F, (90.0F + (float)BossLv3Building4.HEIGHT / 2.0F) / 1.0F);
      MOONCANNON1POSITION = new Vector2((187.0F + (float)MoonCanon.width / 2.0F) / 1.0F, (81.0F + (float)MoonCanon.height / 2.0F) / 1.0F);
      MOONCANNON2POSITION = new Vector2((187.0F + (float)MoonCanon.width / 2.0F) / 1.0F, (377.0F + (float)MoonCanon.height / 2.0F) / 1.0F);
      MOONCANNON3POSITION = new Vector2((56.0F + (float)MoonCanon.width / 2.0F) / 1.0F, (237.0F + (float)MoonCanon.height / 2.0F) / 1.0F);
      MOONCANNON4POSITION = new Vector2((316.0F + (float)MoonCanon.width / 2.0F) / 1.0F, (237.0F + (float)MoonCanon.height / 2.0F) / 1.0F);
   }

   public BossLv3(World var1, float var2, float var3) {
      super(var1, 10, var2, var3, (float)LAND1WIDTH / 1.0F, (float)LAND1HEIGHT / 1.0F);

      for(int var4 = 0; var4 < 56; ++var4) {
         this.hitMap[var4] = new BossLv3.HitMapItem();
      }

      this.hitRectangles = new ArrayList(56);
      this.moonCanon1 = new BossLv3.BossLv3MoonCannon(var1, this.bounds.x + MOONCANNON1POSITION.x, this.bounds.y + MOONCANNON1POSITION.y, true);
      this.moonCanon2 = new BossLv3.BossLv3MoonCannon(var1, this.bounds.x + MOONCANNON2POSITION.x, this.bounds.y + MOONCANNON2POSITION.y, false);
      this.moonCanon3 = new BossLv3.BossLv3MoonCannon(var1, this.bounds.x + MOONCANNON3POSITION.x, this.bounds.y + MOONCANNON3POSITION.y, true);
      this.moonCanon4 = new BossLv3.BossLv3MoonCannon(var1, this.bounds.x + MOONCANNON4POSITION.x, this.bounds.y + MOONCANNON4POSITION.y, false);
      this.liveCannonNum = 8;
      this.moonCanon1.velocity.set(0.0F, 0.0F);
      this.moonCanon2.velocity.set(0.0F, 0.0F);
      this.moonCanon3.velocity.set(0.0F, 0.0F);
      this.moonCanon4.velocity.set(0.0F, 0.0F);
      this.moonCanon1.cannon1.setInitHealthyDgree(Settings.lapFixBossDegree(20));
      this.moonCanon1.cannon2.setInitHealthyDgree(Settings.lapFixBossDegree(20));
      this.moonCanon2.cannon1.setInitHealthyDgree(Settings.lapFixBossDegree(20));
      this.moonCanon2.cannon2.setInitHealthyDgree(Settings.lapFixBossDegree(20));
      this.moonCanon3.cannon1.setInitHealthyDgree(Settings.lapFixBossDegree(20));
      this.moonCanon3.cannon2.setInitHealthyDgree(Settings.lapFixBossDegree(20));
      this.moonCanon4.cannon1.setInitHealthyDgree(Settings.lapFixBossDegree(20));
      this.moonCanon4.cannon2.setInitHealthyDgree(Settings.lapFixBossDegree(20));
      this.building1 = new BossLv3Building1(var1, this.bounds.x + BUILDING1POSITION.x, this.bounds.y + BUILDING1POSITION.y, this);
      this.building2 = new BossLv3Building2(var1, this.bounds.x + BUILDING2POSITION.x, this.bounds.y + BUILDING2POSITION.y, this);
      this.building3 = new BossLv3Building3(var1, this.bounds.x + BUILDING3POSITION.x, this.bounds.y + BUILDING3POSITION.y, this);
      this.building4 = new BossLv3Building4(var1, this.bounds.x + BUILDING4POSITION.x, this.bounds.y + BUILDING4POSITION.y, this);
      this.building1.velocity.set(0.0F, 0.0F);
      this.building2.velocity.set(0.0F, 0.0F);
      this.building3.velocity.set(0.0F, 0.0F);
      this.building4.velocity.set(0.0F, 0.0F);
      int var13 = Settings.lapFixBossDegree(180);
      this.building1.setInitHealthyDgree(var13);
      this.building2.setInitHealthyDgree(var13);
      this.building3.setInitHealthyDgree(var13);
      this.building4.setInitHealthyDgree(var13);
      this.needShowWarning = true;
   }

   public static Enemy addABossLv3(World var0, float var1, float var2, float var3, List<Enemy> var4) {
      return EnemyProXY.addAEnemy(new BossLv3(var0, var1, var2), var3, var4, true);
   }

   public static void loadResource() {
      land1Region = Assets_level3.atlas_Enemy.findRegion("lv3_boss_land", 1);
      land2Region = Assets_level3.atlas_Enemy.findRegion("lv3_boss_land", 2);
      land3Region = Assets_level3.atlas_Enemy.findRegion("lv3_boss_land", 3);
      shadowRegion = Assets_level3.atlas_Enemy.findRegion("lv3_boss_land_shadow");
   }

   public void beHitByBullet(Bullet var1, int var2) {
      if(1 != this.state) {
         this.hitMap[var2].e.beHitByBullet(var1, this.hitMap[var2].index);
         GameScreen.bossHealthyDegree = this.getHealthyDgree();
      }
   }

   public void enemyDropped(EnemyProXY var1) {
      this.world.askBeginFighting(1.0F);
      this.world.playBossMusic();
      this.dropTime = Clock.getCurrentTime();
   }

   public int getHealthyDgree() {
      int var1;
      if(this.building1.getHealthyDgree() > 0) {
         var1 = this.building1.getHealthyDgree();
      } else {
         var1 = 0;
      }

      int var2 = 0 + var1;
      int var3;
      if(this.building2.getHealthyDgree() > 0) {
         var3 = this.building2.getHealthyDgree();
      } else {
         var3 = 0;
      }

      int var4 = var2 + var3;
      int var5;
      if(this.building3.getHealthyDgree() > 0) {
         var5 = this.building3.getHealthyDgree();
      } else {
         var5 = 0;
      }

      int var6 = var4 + var5;
      int var7 = this.building4.getHealthyDgree();
      int var8 = 0;
      if(var7 > 0) {
         var8 = this.building4.getHealthyDgree();
      }

      return var6 + var8;
   }

   public List<Rectangle> getHitRectangle() {
      this.hitRectangles.clear();
      boolean var1 = this.moonCanon1.isCrashed();
      int var2 = 0;
      if(!var1) {
         List var31 = this.moonCanon1.getHitRectangle();
         int var32 = var31.size();

         for(int var33 = 0; var33 < var32; ++var33) {
            this.hitRectangles.add((Rectangle) var31.get(var33));
            this.hitMap[var2].e = this.moonCanon1;
            this.hitMap[var2].index = var33;
            ++var2;
         }
      }

      if(!this.moonCanon2.isCrashed()) {
         List var27 = this.moonCanon2.getHitRectangle();
         int var28 = var27.size();

         for(int var29 = 0; var29 < var28; ++var29) {
            this.hitRectangles.add((Rectangle) var27.get(var29));
            this.hitMap[var2].e = this.moonCanon2;
            this.hitMap[var2].index = var29;
            ++var2;
         }
      }

      if(!this.moonCanon3.isCrashed()) {
         List var23 = this.moonCanon3.getHitRectangle();
         int var24 = var23.size();

         for(int var25 = 0; var25 < var24; ++var25) {
            this.hitRectangles.add((Rectangle) var23.get(var25));
            this.hitMap[var2].e = this.moonCanon3;
            this.hitMap[var2].index = var25;
            ++var2;
         }
      }

      if(!this.moonCanon4.isCrashed()) {
         List var19 = this.moonCanon4.getHitRectangle();
         int var20 = var19.size();

         for(int var21 = 0; var21 < var20; ++var21) {
            this.hitRectangles.add((Rectangle) var19.get(var21));
            this.hitMap[var2].e = this.moonCanon4;
            this.hitMap[var2].index = var21;
            ++var2;
         }
      }

      this.liveCannonNum = var2;
      if(!this.building1.isCrashed()) {
         List var15 = this.building1.getHitRectangle();
         int var16 = var15.size();

         for(int var17 = 0; var17 < var16; ++var17) {
            this.hitRectangles.add((Rectangle) var15.get(var17));
            this.hitMap[var2].e = this.building1;
            this.hitMap[var2].index = var17;
            ++var2;
         }
      }

      if(!this.building2.isCrashed()) {
         List var11 = this.building2.getHitRectangle();
         int var12 = var11.size();

         for(int var13 = 0; var13 < var12; ++var13) {
            this.hitRectangles.add((Rectangle) var11.get(var13));
            this.hitMap[var2].e = this.building2;
            this.hitMap[var2].index = var13;
            ++var2;
         }
      }

      if(!this.building3.isCrashed()) {
         List var7 = this.building3.getHitRectangle();
         int var8 = var7.size();

         for(int var9 = 0; var9 < var8; ++var9) {
            this.hitRectangles.add((Rectangle) var7.get(var9));
            this.hitMap[var2].e = this.building3;
            this.hitMap[var2].index = var9;
            ++var2;
         }
      }

      if(!this.building4.isCrashed()) {
         List var3 = this.building4.getHitRectangle();
         int var4 = var3.size();

         for(int var5 = 0; var5 < var4; ++var5) {
            this.hitRectangles.add((Rectangle) var3.get(var5));
            this.hitMap[var2].e = this.building4;
            this.hitMap[var2].index = var5;
            ++var2;
         }
      }

      return this.hitRectangles;
   }

   public float getTimeOfCrash() {
      return 18.0F;
   }

   public boolean isCrashed() {
      if(this.moonCanon1.isCrashed() && this.moonCanon2.isCrashed() && this.moonCanon3.isCrashed() && this.moonCanon4.isCrashed() && this.building1.isCrashed() && this.building2.isCrashed() && this.building3.isCrashed() && this.building4.isCrashed()) {
         if(!super.isCrashed()) {
            Settings.bossKilled(3, Clock.getCurrentTime(this.dropTime));
            this.makeCrash();
         }

         return true;
      } else {
         return false;
      }
   }

   public void render(SpriteBatch var1) {
      var1.draw(shadowRegion, this.bounds.x + SHADOWPOSITION.x, this.bounds.y + SHADOWPOSITION.y, 0.0F, 0.0F, (float)SHADOWWIDTH / 1.0F, (float)SHADOWHEIGHT / 1.0F, 1.0F, 1.0F, 0.0F);
      var1.draw(land3Region, this.bounds.x + LAND3POSITION.x, this.bounds.y + LAND3POSITION.y, 0.0F, 0.0F, (float)LAND3WIDTH / 1.0F, (float)LAND3HEIGHT / 1.0F, 1.0F, 1.0F, 0.0F);
      this.building1.render(var1);
      this.building2.render(var1);
      var1.draw(land2Region, this.bounds.x + LAND2POSITION.x, this.bounds.y + LAND2POSITION.y, 0.0F, 0.0F, (float)LAND2WIDTH / 1.0F, (float)LAND2HEIGHT / 1.0F, 1.0F, 1.0F, 0.0F);
      this.moonCanon4.render(var1);
      this.building1.renderShadow(var1);
      this.building2.renderShadow(var1);
      this.building3.render(var1);
      this.building4.render(var1);
      var1.draw(land1Region, this.bounds.x + LAND1POSITION.x, this.bounds.y + LAND1POSITION.y, 0.0F, 0.0F, (float)LAND1WIDTH / 1.0F, (float)LAND1HEIGHT / 1.0F, 1.0F, 1.0F, 0.0F);
      this.moonCanon2.render(var1);
      this.building3.renderShadow(var1);
      this.building4.renderShadow(var1);
      this.moonCanon1.render(var1);
      this.moonCanon3.render(var1);
   }

   public void setSleepPosition(float var1, float var2) {
      super.setSleepPosition(var1, var2);
      this.building1.setSleepPosition(this.bounds.x + BUILDING1POSITION.x, this.bounds.y + BUILDING1POSITION.y);
      this.building2.setSleepPosition(this.bounds.x + BUILDING2POSITION.x, this.bounds.y + BUILDING2POSITION.y);
      this.building3.setSleepPosition(this.bounds.x + BUILDING3POSITION.x, this.bounds.y + BUILDING3POSITION.y);
      this.building4.setSleepPosition(this.bounds.x + BUILDING4POSITION.x, this.bounds.y + BUILDING4POSITION.y);
      this.moonCanon1.setSleepPosition(this.bounds.x + MOONCANNON1POSITION.x, this.bounds.y + MOONCANNON1POSITION.y);
      this.moonCanon2.setSleepPosition(this.bounds.x + MOONCANNON2POSITION.x, this.bounds.y + MOONCANNON2POSITION.y);
      this.moonCanon3.setSleepPosition(this.bounds.x + MOONCANNON3POSITION.x, this.bounds.y + MOONCANNON3POSITION.y);
      this.moonCanon4.setSleepPosition(this.bounds.x + MOONCANNON4POSITION.x, this.bounds.y + MOONCANNON4POSITION.y);
   }

   public void update(float var1) {
      super.update(var1);
      if(this.building1.getBuildState() == BossLv3.BuildingState.SleepWithOutClock && this.building2.getBuildState() == BossLv3.BuildingState.SleepWithOutClock && this.building3.getBuildState() == BossLv3.BuildingState.SleepWithOutClock && this.building4.getBuildState() == BossLv3.BuildingState.SleepWithOutClock && this.liveCannonNum <= 4) {
         this.building1.setBuildState(BossLv3.BuildingState.Rising);
         this.building2.setBuildState(BossLv3.BuildingState.Rising);
         this.building3.setBuildState(BossLv3.BuildingState.Rising);
         this.building4.setBuildState(BossLv3.BuildingState.Rising);
      }

      this.moonCanon1.update(var1);
      this.moonCanon2.update(var1);
      this.moonCanon3.update(var1);
      this.moonCanon4.update(var1);
      this.building1.update(var1);
      this.building2.update(var1);
      this.building3.update(var1);
      this.building4.update(var1);
   }

   public void updateShooting(float var1) {}

   private static class HitMapItem {

      Enemy e;
      int index;


      private HitMapItem() {}
   }

   public static class BossLv3MoonCannon extends MoonCanon {

      public BossLv3MoonCannon(World var1, float var2, float var3, boolean var4) {
         super(var1, var2, var3, var4);
      }
   }

   public static enum BuildingState {

      Falling,
      Rising,
      Shooting,
      SleepWithClock,
      SleepWithOutClock
   }
}
