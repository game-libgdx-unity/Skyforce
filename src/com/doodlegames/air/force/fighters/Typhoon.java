package com.doodlegames.air.force.fighters;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.fighters.gun.TyphoonBlueGun;
import com.doodlegames.air.force.fighters.gun.TyphoonRedGun;
import com.doodlegames.air.force.fighters.gun.TyphoonYellowGun;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldBoomer;
import com.doodlegames.air.force.resource.Assets_fighter;
import com.doodlegames.air.force.utils.RegionUtilFunctions;

import java.util.List;

public class Typhoon extends Fighter {

   public static final int DEFAULTLIVES = 0;
   private static final int InitHealthy = 1;
   public static final Vector2[] SHOOTPOINT;
   private static final float timeOfPlayCrashedAnimation = 0.3F;
   WorldBoomer.BoomPoint[] boomPoints;


   static {
      Vector2[] var0 = new Vector2[]{new Vector2(2.0F, 37.0F), new Vector2(9.0F, 46.0F), new Vector2(16.0F, 64.0F), new Vector2(23.0F, 74.0F), new Vector2(30.0F, 80.0F), new Vector2(37.0F, 74.0F), new Vector2(44.0F, 64.0F), new Vector2(51.0F, 46.0F), new Vector2(58.0F, 37.0F)};
      SHOOTPOINT = var0;
   }

   public Typhoon(World var1, float var2, float var3) {
      super(var1, var2, var3, (float)RegionUtilFunctions.getRegionWidth(Assets_fighter.typhoonStand) / 1.0F, (float)RegionUtilFunctions.getRegionHeight(Assets_fighter.typhoonStand) / 1.0F);
      this.initTyphoon();
      this.avalibleGuns[0] = new TyphoonRedGun(this);
      this.avalibleGuns[1] = new TyphoonYellowGun(this);
      this.avalibleGuns[2] = new TyphoonBlueGun(this);
      this.currentGun = this.avalibleGuns[0];
      this.currentGun.reset();
      if(!this.loadFighter()) {
         ;
      }

      this.constructBoomer();
   }

   private void constructBoomer() {
      WorldBoomer.BoomPoint[] var1 = new WorldBoomer.BoomPoint[]{new WorldBoomer.BoomPoint(this.world), new WorldBoomer.BoomPoint(this.world), new WorldBoomer.BoomPoint(this.world)};
      this.boomPoints = var1;
      WorldBoomer.BoomPoint[] var2 = this.boomPoints;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         var2[var4].setClearBulletRadis(185.0F);
      }

      this.boomPoints[0].setBoomVel(0.0F, 700.0F);
      this.boomPoints[1].setBoomVel(-606.2F, -350.0F);
      this.boomPoints[2].setBoomVel(606.2F, -350.0F);
   }

   public void beginCheck() {
      super.beginCheck();
      WorldBoomer.BoomPoint[] var1 = this.boomPoints;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         var1[var3].beginCheck();
      }

   }

   public void boomStart() {
      this.world.getParentScreen().playBoomLaunchAnimation();
      if(!this.isBooming()) {
         WorldBoomer.BoomPoint[] var1 = this.boomPoints;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            var1[var3].boomeStartAt(this.getPositionX(), this.getPositionY(), 0.0F);
         }

         this.world.worldAudio.playNaziBigBoom();
      }
   }

   public void checking(Enemy var1, List<Rectangle> var2) {
      super.checking(var1, var2);
      WorldBoomer.BoomPoint[] var3 = this.boomPoints;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         var3[var5].checking(var1, var2);
      }

   }

   public void endCheck() {
      super.endCheck();
      WorldBoomer.BoomPoint[] var1 = this.boomPoints;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         var1[var3].endCheck();
      }

   }

   public float getTimeOfCrash() {
      return 0.3F;
   }

   public void initTyphoon() {
      this.fightStand = Assets_fighter.typhoonStand;
      this.fightLeft = Assets_fighter.typhoonFlyingToLeft;
      this.fightRight = Assets_fighter.typhoonFlyingToRight;
      this.healthyDegree = 1;
   }

   public boolean isBooming() {
      WorldBoomer.BoomPoint[] var1 = this.boomPoints;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         if(!var1[var3].boomingCompleted()) {
            return true;
         }
      }

      return false;
   }

   public void recovery() {
      float var1 = (float)RegionUtilFunctions.getRegionWidth(Assets_fighter.typhoonStand) / 1.0F;
      float var2 = (float)RegionUtilFunctions.getRegionHeight(Assets_fighter.typhoonStand) / 1.0F;
      super.resetFighter(this.world, 320.0F, var2 / 2.0F, var1, var2);
      this.initTyphoon();
   }

   public void updateBoomer(float var1) {
      WorldBoomer.BoomPoint[] var2 = this.boomPoints;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         var2[var4].updateBoomer(var1);
      }

   }
}
