package com.doodlegames.air.force.game.laser;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldAudio;
import com.doodlegames.air.force.game.laser.LaserShooter;
import com.doodlegames.air.force.resource.Assets_ShareInAlien;
import com.doodlegames.air.force.resource.Assets_ShareInNazi;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;
import com.doodlegames.air.force.utils.Settings;

public class LaserGun implements LaserShooter {

   protected final float aggHeight;
   private final float aggTime;
   protected final float aggWidth;
   protected Animation aggregationAnimation;
   public float alpha;
   public float angle;
   private final float disappearTime;
   protected WorldAudio.PlaySound gatherSound;
   public final float iniWidth;
   protected LaserGun.LaserGunState laserGunState;
   private boolean loopFire;
   private float originX;
   private float originY;
   private GameObject owner;
   private final float readyTime;
   private boolean removeAllLasers;
   private float shootPointRelativeX;
   private float shootPointRelativeY;
   public Vector2 shooterPoint;
   public boolean shooterPointIsAbsolute;
   protected WorldAudio.PlaySound shootingSound;
   private final float shootingTime;
   private Clock sleepClock;
   protected Clock stateChangeClock;
   protected float stateTime;
   public LaserGun.LaserGunUpdater updater;
   public float width;


   public LaserGun(GameObject var1, Animation var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      this.owner = var1;
      this.shooterPointIsAbsolute = true;
      this.shootPointRelativeX = var7;
      this.shootPointRelativeY = var8;
      this.shooterPoint = new Vector2(var7, var8);
      this.angle = 0.0F;
      this.iniWidth = var9;
      this.width = var9;
      this.alpha = 0.0F;
      this.removeAllLasers = false;
      this.laserGunState = LaserGun.LaserGunState.Sleep;
      this.stateChangeClock = new Clock(0.0F, 20000.0F, Clock.ClockType.FireOnlyOnce);
      this.readyTime = var3;
      this.aggTime = var4;
      this.shootingTime = var5;
      this.disappearTime = var6;
      this.stateTime = 0.0F;
      this.aggregationAnimation = var2;
      if(this.aggregationAnimation != null) {
         TextureRegion var10 = this.aggregationAnimation.getKeyFrame(0.0F);
         this.aggWidth = (float)RegionUtilFunctions.getRegionWidth(var10) / 1.0F;
         this.aggHeight = (float)RegionUtilFunctions.getRegionHeight(var10) / 1.0F;
      } else {
         this.aggWidth = 0.0F;
         this.aggHeight = 0.0F;
      }

      this.originX = this.aggWidth / 2.0F;
      this.originY = this.aggHeight / 2.0F;
      this.sleepClock = new Clock(0.0F, 6000000.0F, Clock.ClockType.FireOnlyOnce);
      this.loopFire = true;
      this.updater = null;
      this.constructSound();
   }

   private void playGatherSound() {
      if(this.gatherSound != null) {
         this.gatherSound.playSound();
      } else {
         Settings.i("laser gather Sound is null.");
      }
   }

   public boolean canStop() {
      return this.laserGunState == LaserGun.LaserGunState.Sleep;
   }

   protected void constructSound() {
      World var1 = ((Enemy)this.owner).world;
      if(Assets_ShareInAlien.isALienGatherAnimation(this.aggregationAnimation)) {
         this.gatherSound = var1.worldAudio.playAlienLaseGather;
         this.shootingSound = var1.worldAudio.playAlienLaseShooting;
      }

      if(Assets_ShareInNazi.isNaziGatherAnimation(this.aggregationAnimation)) {
         this.gatherSound = var1.worldAudio.playNaziLaseGather;
         this.shootingSound = var1.worldAudio.playNaziLaseShooting;
      }

   }

   public void forceStop() {
      this.stateTime = 0.0F;
      this.laserGunState = LaserGun.LaserGunState.Sleep;
      this.sleepClock.resetClockFireOnce(0.0F);
      this.sleepClock.isFired();
      this.alpha = 0.0F;
      this.width = this.iniWidth;
   }

   public float getAlpha() {
      return this.alpha;
   }

   public float getLaserAngle() {
      return this.angle;
   }

   public LaserGun.LaserGunState getLaserGunState() {
      return this.laserGunState;
   }

   public float getLaserWidth() {
      return this.width;
   }

   public Vector2 getShootingPoint() {
      if(!this.shooterPointIsAbsolute) {
         this.shooterPoint.set(this.owner.bounds.x, this.owner.bounds.y);
         this.shooterPoint.add(this.shootPointRelativeX, this.shootPointRelativeY);
      }

      return this.shooterPoint;
   }

   public Clock getSleepClock() {
      return this.sleepClock;
   }

   public boolean laseSleep() {
      return this.laserGunState == LaserGun.LaserGunState.Sleep || this.laserGunState == LaserGun.LaserGunState.Ready || this.laserGunState == LaserGun.LaserGunState.Aggregation;
   }

   public void renderLaserAggregation(SpriteBatch var1) {
      if(this.laserGunState == LaserGun.LaserGunState.Aggregation && !this.shouldRemove() && this.aggregationAnimation != null) {
         float var2 = this.getShootingPoint().x - this.originX;
         float var3 = this.getShootingPoint().y - this.originY;
         RegionUtilFunctions.draw(var1, this.aggregationAnimation.getKeyFrame(this.stateTime), var2, var3, this.originX, this.originY, this.aggWidth, this.aggHeight, 1.0F, 1.0F, this.getLaserAngle());
      }
   }

   public void reset() {
      if(this.laserGunState == LaserGun.LaserGunState.Sleep) {
         this.stateTime = 0.0F;
         this.laserGunState = LaserGun.LaserGunState.Ready;
         this.stateChangeClock.resetClockFireOnce(this.readyTime);
         this.width = this.iniWidth;
         this.alpha = 0.0F;
      }

   }

   public void setGatherSound(WorldAudio.PlaySound var1) {
      this.gatherSound = var1;
   }

   public boolean setLoopFire(boolean var1) {
      this.loopFire = var1;
      return this.loopFire;
   }

   public void setOriginX(float var1) {
      this.originX = var1;
   }

   public void setOriginY(float var1) {
      this.originY = var1;
   }

   public void setRemoveAllLasers() {
      this.removeAllLasers = true;
   }

   public void setShootingSound(WorldAudio.PlaySound var1) {
      this.shootingSound = var1;
   }

   public boolean setshootPointRelativeX(float var1) {
      if(this.laserGunState != LaserGun.LaserGunState.Ready && this.laserGunState != LaserGun.LaserGunState.Sleep) {
         return false;
      } else {
         this.shootPointRelativeX = var1;
         return true;
      }
   }

   public boolean setshootPointRelativeY(float var1) {
      if(this.laserGunState != LaserGun.LaserGunState.Ready && this.laserGunState != LaserGun.LaserGunState.Sleep) {
         return false;
      } else {
         this.shootPointRelativeY = var1;
         return true;
      }
   }

   public boolean shouldRemove() {
      return this.removeAllLasers;
   }

   public void startShoot(float var1) {
      if(this.laserGunState == LaserGun.LaserGunState.Sleep) {
         this.sleepClock.resetClockFireOnce(var1);
      }

   }

   public void updateShooting(final float n) {

       if (!this.shouldRemove()) {

           final int n2 = this.owner instanceof Enemy?1:0;

           World world = null;

           if (n2 != 0) {

               world = ((Enemy)this.owner).world;

           }

           this.stateTime = n + this.stateTime;

           switch (this.laserGunState) {

               case Sleep: {

                   if (!world.getStopFighting() && this.sleepClock.isFired()) {

                       this.stateTime = 0.0f;

                       this.laserGunState = LaserGunState.Aggregation;

                       this.stateChangeClock.resetClockFireOnce(this.aggTime);

                       this.width = this.iniWidth;

                       this.alpha = 0.0f;

                       this.playGatherSound();

                       break;

                   }

                   break;

               }

               case Ready: {

                   if (this.stateChangeClock.isFired()) {

                       this.stateTime = 0.0f;

                       this.laserGunState = LaserGunState.Aggregation;

                       this.stateChangeClock.resetClockFireOnce(this.aggTime);

                       this.width = this.iniWidth;

                       this.alpha = 0.0f;

                       this.playGatherSound();

                       break;

                   }

                   break;

               }

               case Aggregation: {

                   if (!this.stateChangeClock.isFired()) {

                       break;

                   }

                   this.stateTime = 0.0f;

                   this.laserGunState = LaserGunState.Shooting;

                   this.stateChangeClock.resetClockFireOnce(this.shootingTime);

                   this.width = this.iniWidth;

                   this.alpha = 1.0f;

                   if (this.shootingSound != null) {

                       this.shootingSound.playSound();

                       break;

                   }

                   Settings.i("laser shooting sound is null");

                   break;

               }

               case Shooting: {

                   if (this.stateChangeClock.isFired()) {

                       this.stateTime = 0.0f;

                       this.laserGunState = LaserGunState.Disappearing;

                       this.stateChangeClock.resetClockFireOnce(this.disappearTime);

                       this.width = this.iniWidth;

                       this.alpha = 1.0f;

                       break;

                   }

                   break;

               }

               case Disappearing: {

                   this.alpha = 1.0f - this.stateTime / this.disappearTime;

                   this.width = this.iniWidth * this.alpha;

                   if (this.stateChangeClock.isFired()) {

                       this.stateTime = 0.0f;

                       if (this.loopFire) {

                           this.laserGunState = LaserGunState.Ready;

                           final float n3 = 0.5f * this.readyTime + 0.5f * this.readyTime * Clock.rand.nextFloat();

                           this.stateChangeClock.resetClockFireOnce(n3);

                       }

                       else {

                           this.laserGunState = LaserGunState.Sleep;

                       }

                       this.width = this.iniWidth;

                       this.alpha = 0.0f;

                       break;

                   }

                   break;

               }

           }

           if (this.updater != null) {

               this.updater.update(n);

               return;

           }

       }

   }

   private interface LaserGunUpdater {

      void update(float var1);
   }

   public static enum LaserGunState {

      Aggregation,
      Disappearing,
      Ready,
      Shooting,
      Sleep,
   }

   public class UpdateLaserGun1 implements LaserGun.LaserGunUpdater {

      private float angleEnd;
      private float angleSpeed;
      private float angleStart;


      public UpdateLaserGun1(float var2, float var3) {
         this.angleStart = var2;
         this.angleEnd = var3;
         this.angleSpeed = (this.angleEnd - this.angleStart) / LaserGun.this.shootingTime;
      }

      public void update(float var1) {
         if(LaserGun.this.laserGunState == LaserGun.LaserGunState.Shooting) {
            LaserGun var2 = LaserGun.this;
            var2.angle += var1 * this.angleSpeed;
            if(LaserGun.this.angle < this.angleStart) {
               this.angleSpeed = (this.angleEnd - this.angleStart) / LaserGun.this.shootingTime;
            }

            if(LaserGun.this.angle > this.angleEnd) {
               this.angleSpeed = -(this.angleEnd - this.angleStart) / LaserGun.this.shootingTime;
            }
         }

      }
   }
}
