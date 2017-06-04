package com.doodlegames.air.force.fighters;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.fighters.gun.F22BlueGun;
import com.doodlegames.air.force.fighters.gun.F22RedGun;
import com.doodlegames.air.force.fighters.gun.F22YellowGun;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldBoomer;
import com.doodlegames.air.force.resource.Assets_fighter;
import com.doodlegames.air.force.utils.RandomOrder;
import com.doodlegames.air.force.utils.RegionUtilFunctions;

import java.util.List;

public class F22 extends Fighter {

   public static final int DEFAULTLIVES = 0;
   private static final int InitHealthy = 1;
   public static final Vector2[] SHOOTPOINT;
   private static final float timeOfPlayCrashedAnimation = 0.3F;
   WorldBoomer.BoomPoint[] boomPoints;
   float[] delayTime = new float[]{0.0F, 0.5F, 1.0F, 1.5F};
   RandomOrder randomOrder;
   float[] relX = new float[]{0.0F, 0.0F, -259.8F, 259.8F};
   float[] relY = new float[]{0.0F, 300.0F, -150.0F, -150.0F};


   static {
      Vector2[] var0 = new Vector2[]{new Vector2(4.0F, 45.0F), new Vector2(11.0F, 51.0F), new Vector2(17.0F, 56.0F), new Vector2(24.0F, 62.0F), new Vector2(36.0F, 62.0F), new Vector2(43.0F, 56.0F), new Vector2(49.0F, 51.0F), new Vector2(56.0F, 45.0F)};
      SHOOTPOINT = var0;
   }

   public F22(World var1, float var2, float var3) {
      super(var1, var2, var3, (float)RegionUtilFunctions.getRegionWidth(Assets_fighter.f22Stand) / 1.0F, (float)RegionUtilFunctions.getRegionHeight(Assets_fighter.f22Stand) / 1.0F);
      this.initF22();
      this.avalibleGuns[0] = new F22RedGun(this);
      this.avalibleGuns[1] = new F22YellowGun(this);
      this.avalibleGuns[2] = new F22BlueGun(this);
      this.currentGun = this.avalibleGuns[0];
      this.currentGun.reset();
      if(!this.loadFighter()) {
         ;
      }

      this.constructBoomer();
   }

   private void constructBoomer() {
      WorldBoomer.BoomPoint[] var1 = new WorldBoomer.BoomPoint[]{new WorldBoomer.BoomPoint(this.world), new WorldBoomer.BoomPoint(this.world), new WorldBoomer.BoomPoint(this.world), new WorldBoomer.BoomPoint(this.world)};
      this.boomPoints = var1;
      this.randomOrder = new RandomOrder(-1 + this.boomPoints.length);
   }

   public void beginCheck() {
      super.beginCheck();

      for(int var1 = 0; var1 < this.boomPoints.length; ++var1) {
         if(this.boomPoints[var1].isBooming()) {
            this.boomPoints[var1].beginCheck();
         }
      }

   }

   public void boomStart() {
      this.world.getParentScreen().playBoomLaunchAnimation();
      if(!this.isBooming()) {
         float var1 = this.getPositionX();
         float var2 = this.getPositionY();
         this.boomPoints[0].boomeStartAt(var1 + this.relX[0], var2 + this.relY[0], this.delayTime[0]);
         this.randomOrder.init();

         for(int var3 = 1; var3 < this.boomPoints.length; ++var3) {
            int var4 = this.randomOrder.nextOrder();
            this.boomPoints[var3].boomeStartAt(var1 + this.relX[var3], var2 + this.relY[var3], this.delayTime[var4]);
         }
      }

   }

   public void checking(Enemy var1, List<Rectangle> var2) {
      super.checking(var1, var2);

      for(int var3 = 0; var3 < this.boomPoints.length; ++var3) {
         if(this.boomPoints[var3].isBooming()) {
            this.boomPoints[var3].checking(var1, var2);
         }
      }

   }

   public void endCheck() {
      super.endCheck();

      for(int var1 = 0; var1 < this.boomPoints.length; ++var1) {
         if(this.boomPoints[var1].isBooming()) {
            this.boomPoints[var1].endCheck();
         }
      }

   }

   public float getTimeOfCrash() {
      return 0.3F;
   }

   public void initF22() {
      this.fightStand = Assets_fighter.f22Stand;
      this.fightLeft = Assets_fighter.f22FlyingToLeft;
      this.fightRight = Assets_fighter.f22FlyingToRight;
      this.healthyDegree = 1;
   }

   public boolean isBooming() {
      for(int var1 = 0; var1 < this.boomPoints.length; ++var1) {
         if(!this.boomPoints[var1].boomingCompleted()) {
            return true;
         }
      }

      return false;
   }

   public void recovery() {
      float var1 = (float)RegionUtilFunctions.getRegionWidth(Assets_fighter.f22Stand) / 1.0F;
      float var2 = (float)RegionUtilFunctions.getRegionHeight(Assets_fighter.f22Stand) / 1.0F;
      super.resetFighter(this.world, 320.0F, var2 / 2.0F, var1, var2);
      this.initF22();
   }

   public void updateBoomer(float var1) {
      for(int var2 = 0; var2 < this.boomPoints.length; ++var2) {
         this.boomPoints[var2].updateBoomer(var1);
      }

   }
}
