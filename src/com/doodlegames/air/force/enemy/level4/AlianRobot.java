package com.doodlegames.air.force.enemy.level4;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyProXY;
import com.doodlegames.air.force.enemy.level4.AlianRobotLeft;
import com.doodlegames.air.force.enemy.level4.AlianRobotRight;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.utils.Clock;

import java.util.ArrayList;
import java.util.List;

public class AlianRobot extends Enemy {

   private static final float DEFAULTANGLESPEED = 40.0F;
   public static final int DEFAULTHEALTHYDEGREE = 10;
   public static final Vector2 LEFTPARTPOSITION = new Vector2((0.0F + (float)AlianRobotLeft.WIDTH / 2.0F) / 1.0F, (0.0F + (float)AlianRobotLeft.HEIGHT / 2.0F) / 1.0F);
   private static final int PARTSNUM = 2;
   public static final Vector2 RIGHTPARTPOSITION = new Vector2((33.0F + (float)AlianRobotRight.WIDTH / 2.0F) / 1.0F, (0.0F + (float)AlianRobotRight.HEIGHT / 2.0F) / 1.0F);
   private static final float TIMEOFPLAYCRASHANIMATION = 15.0F;
   public static final float pursueVel = 300.0F;
   public static final float swapVel = 250.0F;
   private AlianRobotLeft alianRobotLeft;
   private AlianRobotRight alianRobotRight;
   private Clock clockModeChangle;
   private Clock clockPursue;
   private Clock clockShooting;
   private Enemy[] hitMap;
   private List<Rectangle> hitRectangles;
   private AlianRobot.ShootingMode sm;
   private float velAngleSpeed;


   public AlianRobot(World var1, float var2, float var3) {
      super(var1, 10, var2, var3, (float)(AlianRobotLeft.WIDTH + AlianRobotRight.WIDTH) / 1.0F, (float)(AlianRobotLeft.HEIGHT + AlianRobotRight.HEIGHT) / 1.0F);
      this.sm = AlianRobot.ShootingMode.shootingMode1;
      this.clockPursue = new Clock(1.5F);
      this.clockShooting = new Clock(2.5F, 5.0F, 0.8F, (byte)2);
      this.clockModeChangle = new Clock(0.0F, 10.0F, Clock.ClockType.FireOnlyOnce);
      this.alianRobotLeft = new AlianRobotLeft(var1, this.bounds.x + LEFTPARTPOSITION.x, this.bounds.y + LEFTPARTPOSITION.y);
      this.alianRobotRight = new AlianRobotRight(var1, this.bounds.x + RIGHTPARTPOSITION.x, this.bounds.y + RIGHTPARTPOSITION.y);
      this.hitMap = new Enemy[2];
      this.hitRectangles = new ArrayList(2);
      this.velAngleSpeed = 40.0F;
      this.switchShootingMode(AlianRobot.ShootingMode.shootingMode2);
      this.crashUsePath = false;
   }

   public void beHitByBullet(Bullet var1, int var2) {
      if(1 != this.state) {
         this.hitMap[var2].beHitByBullet(var1);
         int var3 = var1.damageValue;
         var1.damageValue = 0;
         super.beHitByBullet(var1);
         var1.damageValue = var3;
      }
   }

   public List<Rectangle> getHitRectangle() {
      this.hitRectangles.clear();
      boolean var1 = this.alianRobotLeft.isCrashed();
      int var2 = 0;
      if(!var1) {
         this.hitRectangles.add(this.alianRobotLeft.bounds);
         Enemy[] var9 = this.hitMap;
         int var10 = 0 + 1;
         var9[0] = this.alianRobotLeft;
         var2 = var10;
      }

      int var3;
      if(!this.alianRobotRight.isCrashed()) {
         this.hitRectangles.add(this.alianRobotRight.bounds);
         Enemy[] var7 = this.hitMap;
         var3 = var2 + 1;
         var7[var2] = this.alianRobotRight;
      } else {
         var3 = var2;
      }

      while(var3 < 2) {
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
      boolean var1 = this.alianRobotLeft.isCrashed();
      boolean var2 = false;
      if(var1) {
         boolean var3 = this.alianRobotRight.isCrashed();
         var2 = false;
         if(var3) {
            this.setInitHealthyDgree(0);
            var2 = true;
         }
      }

      return var2;
   }

   public void render(SpriteBatch var1) {
      this.alianRobotLeft.render(var1);
      this.alianRobotRight.render(var1);
   }

   public void switchShootingMode(AlianRobot.ShootingMode var1) {
      switch(var1) {
      case shootingMode1:
         this.clockShooting.resetClock(3.0F, 5.0F, 0.6F, (byte)2);
         this.clockModeChangle.resetClockFireOnce(10.0F);
         this.alianRobotLeft.setVel(0.0F, 300.0F);
         this.alianRobotRight.setVel(0.0F, 300.0F);
         break;
      case shootingMode2:
         this.clockShooting.resetClock(5.0F, 7.0F, 0.0F, (byte)1);
         this.clockModeChangle.resetClockFireOnce(5.0F);
         this.alianRobotLeft.setVel(0.0F, 250.0F);
         this.alianRobotRight.setVel(0.0F, 250.0F);
      }

      this.sm = var1;
   }

   public void update(float var1) {
      super.update(var1);
      if(this.alianRobotLeft.isCrashed() || this.alianRobotRight.isCrashed()) {
         this.moveProcessor = EnemyProXY.BGMARK;
      }

      if(this.clockModeChangle.isFired()) {
         if(this.sm == AlianRobot.ShootingMode.shootingMode1) {
            this.switchShootingMode(AlianRobot.ShootingMode.shootingMode2);
         } else if(this.sm == AlianRobot.ShootingMode.shootingMode2) {
            this.switchShootingMode(AlianRobot.ShootingMode.shootingMode1);
         }
      }

      this.alianRobotLeft.update(var1);
      this.alianRobotRight.update(var1);
   }

   public void updateDynamicParameter(float var1) {
      super.updateDynamicParameter(var1);
      this.alianRobotLeft.setPosition(this.bounds.x + LEFTPARTPOSITION.x, this.bounds.y + LEFTPARTPOSITION.y);
      this.alianRobotRight.setPosition(this.bounds.x + RIGHTPARTPOSITION.x, this.bounds.y + RIGHTPARTPOSITION.y);
   }

   public void updateShooting(float var1) {
      if(this.sm == AlianRobot.ShootingMode.shootingMode1) {
         if(this.clockPursue.isFired()) {
            float var4 = this.world.fighter.getPositionY() - (this.bounds.y + this.origin.y);
            float var5 = this.world.fighter.getPositionX() - (this.bounds.x + this.origin.x);
            if(var4 < 0.0F) {
               float var6 = 90.0F + (float)(57.2957763671875D * Math.atan2((double)var4, (double)var5));
               if(!this.alianRobotLeft.isCrashed()) {
                  this.alianRobotLeft.setVel(var6, 300.0F);
               }

               if(!this.alianRobotRight.isCrashed()) {
                  this.alianRobotRight.setVel(var6, 300.0F);
               }
            }
         }

         if(this.clockShooting.isFired()) {
            this.alianRobotLeft.shooting();
            this.alianRobotRight.shooting();
         }
      }

      if(this.sm == AlianRobot.ShootingMode.shootingMode2) {
         float var2 = this.alianRobotLeft.getVelAngle();
         if(var2 < -60.0F) {
            this.velAngleSpeed = 40.0F;
         } else if(var2 > 60.0F) {
            this.velAngleSpeed = -40.0F;
         }

         float var3 = var2 + var1 * this.velAngleSpeed;
         this.alianRobotLeft.setVel(var3, 250.0F);
         this.alianRobotRight.setVel(var3, 250.0F);
         if(this.clockShooting.isFired()) {
            if(!Clock.rand.nextBoolean()) {
               this.alianRobotRight.shootBulletScreen();
               return;
            }

            this.alianRobotLeft.shootBulletScreen();
         }
      }

   }

   private static enum ShootingMode {

      shootingMode1,
      shootingMode2
   }
}
