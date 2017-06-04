package com.doodlegames.air.force.fighters;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.fighters.gun.Su47BlueGun;
import com.doodlegames.air.force.fighters.gun.Su47RedGun;
import com.doodlegames.air.force.fighters.gun.Su47YellowGun;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldBoomer;
import com.doodlegames.air.force.resource.Assets_fighter;
import com.doodlegames.air.force.utils.RegionUtilFunctions;

import java.util.List;

public class Su47 extends Fighter {

   public static final int DEFAULTLIVES = 0;
   private static final int InitHealthy = 1;
   public static final Vector2[] SHOOTPOINT;
   private static final float timeOfPlayCrashedAnimation = 0.3F;
   WorldBoomer.BoomPoint boomPoint;


   static {
      Vector2[] var0 = new Vector2[]{new Vector2(2.0F, 53.0F), new Vector2(9.0F, 57.0F), new Vector2(16.0F, 63.0F), new Vector2(23.0F, 57.0F), new Vector2(37.0F, 57.0F), new Vector2(44.0F, 63.0F), new Vector2(51.0F, 57.0F), new Vector2(58.0F, 53.0F)};
      SHOOTPOINT = var0;
   }

   public Su47(World var1, float var2, float var3) {
      super(var1, var2, var3, (float)RegionUtilFunctions.getRegionWidth(Assets_fighter.su47Stand) / 1.0F, (float)RegionUtilFunctions.getRegionHeight(Assets_fighter.su47Stand) / 1.0F);
      this.initSu47();
      this.avalibleGuns[0] = new Su47RedGun(this);
      this.avalibleGuns[1] = new Su47YellowGun(this);
      this.avalibleGuns[2] = new Su47BlueGun(this);
      this.currentGun = this.avalibleGuns[0];
      this.currentGun.reset();
      if(!this.loadFighter()) {
         ;
      }

      this.constructBoomer();
   }

   private void constructBoomer() {
      this.boomPoint = new WorldBoomer.BoomPoint(this.world);
      this.boomPoint.setClearBulletRadis(185.0F);
   }

   public void beginCheck() {
      super.beginCheck();
      this.boomPoint.setBoomPosition(this.getPositionX(), this.getPositionY());
      this.boomPoint.beginCheck();
   }

   public void boomStart() {
      this.world.getParentScreen().playBoomLaunchAnimation();
      if(!this.isBooming()) {
         this.boomPoint.boomeStartAt(this.getPositionX(), this.getPositionY(), 0.0F);
         this.world.worldAudio.playNaziBigBoom();
      }
   }

   public void checking(Enemy var1, List<Rectangle> var2) {
      super.checking(var1, var2);
      this.boomPoint.checking(var1, var2);
   }

   public void endCheck() {
      super.endCheck();
      this.boomPoint.endCheck();
   }

   public float getTimeOfCrash() {
      return 0.3F;
   }

   public void initSu47() {
      this.fightStand = Assets_fighter.su47Stand;
      this.fightLeft = Assets_fighter.su47FlyingToLeft;
      this.fightRight = Assets_fighter.su47FlyingToRight;
      this.healthyDegree = 1;
   }

   public boolean isBooming() {
      return !this.boomPoint.boomingCompleted();
   }

   public void recovery() {
      float var1 = (float)RegionUtilFunctions.getRegionWidth(Assets_fighter.su47Stand) / 1.0F;
      float var2 = (float)RegionUtilFunctions.getRegionHeight(Assets_fighter.su47Stand) / 1.0F;
      super.resetFighter(this.world, 320.0F, var2 / 2.0F, var1, var2);
      this.initSu47();
   }

   public void updateBoomer(float var1) {
      this.boomPoint.updateBoomer(var1);
   }
}
