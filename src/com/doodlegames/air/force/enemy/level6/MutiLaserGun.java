package com.doodlegames.air.force.enemy.level6;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.game.EnemyGun;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.laser.LaserGun;
import com.doodlegames.air.force.utils.Animation;

public class MutiLaserGun implements EnemyGun {

   private int inUseNum;
   public LaserGun[] laserGuns;


   public MutiLaserGun(GameObject var1, Animation var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float ... var10) {
      this.laserGuns = new LaserGun[var10.length];

      for(int var11 = 0; var11 < var10.length; ++var11) {
         LaserGun var12 = new LaserGun(var1, var2, var3, var4, var5, var6, var7, var8, var9);
         var12.angle = var10[var11];
         var12.setLoopFire(false);
         var12.shooterPointIsAbsolute = false;
         this.laserGuns[var11] = var12;
      }

      this.inUseNum = 0;
   }

   public boolean canStop() {
      for(int var1 = 0; var1 < this.laserGuns.length; ++var1) {
         if(!this.laserGuns[var1].canStop()) {
            return false;
         }
      }

      return true;
   }

   public void forceStop() {
      for(int var1 = 0; var1 < this.inUseNum; ++var1) {
         this.laserGuns[var1].forceStop();
      }

   }

   public int getInUseNum() {
      return this.inUseNum;
   }

   public Vector2 getShootingPoint() {
      return this.laserGuns[0] != null?this.laserGuns[0].getShootingPoint():null;
   }

   public void renderLaserAggregation(SpriteBatch var1) {
      if(this.laserGuns[0] != null) {
         this.laserGuns[0].renderLaserAggregation(var1);
      }

   }

   public void reset() {
      for(int var1 = 0; var1 < this.inUseNum; ++var1) {
         this.laserGuns[var1].reset();
      }

   }

   public boolean setInUseNum(int var1) {
      if(var1 >= 0 && var1 <= this.laserGuns.length && this.canStop()) {
         this.inUseNum = var1;
         return true;
      } else {
         return false;
      }
   }

   public void setOrigin(float var1, float var2) {
      LaserGun[] var3 = this.laserGuns;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         LaserGun var6 = var3[var5];
         var6.setOriginX(var1);
         var6.setOriginY(var2);
      }

   }

   public void setRemoveAllLasers() {
      for(int var1 = 0; var1 < this.inUseNum; ++var1) {
         this.laserGuns[var1].setRemoveAllLasers();
      }

   }

   public void startShoot(float var1) {
      for(int var2 = 0; var2 < this.inUseNum; ++var2) {
         if(this.laserGuns[var2].getLaserGunState() == LaserGun.LaserGunState.Sleep) {
            this.laserGuns[var2].getSleepClock().resetClockFireOnce(var1);
         }
      }

   }

   public void updateShooting(float var1) {
      for(int var2 = 0; var2 < this.inUseNum; ++var2) {
         this.laserGuns[var2].updateShooting(var1);
      }

   }
}
