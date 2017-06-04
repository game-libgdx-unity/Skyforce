package com.doodlegames.air.force.game.Gun;

import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.Gun.BulletShooter;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.List;

public class BulletGun implements BulletShooter {

   public BulletGenerator bulletGen;
   protected List<Bullet> bulletsSet;
   protected final Clock clockShoot;
   protected Vector2[] directionsVel;
   private Vector2 originTmp = new Vector2();
   public GameObject owner;
   public boolean rotateAccordEnemy = false;
   private int shootBulletCount;
   protected Vector2 shootPoint;
   public boolean shootPointIsAbsolute;
   private Vector2 shootPointTmp = new Vector2();
   public World world;


   public BulletGun(GameObject var1, World var2, BulletGenerator var3, Clock var4, Vector2 var5, float ... var6) {
      this.owner = var1;
      this.shootPointIsAbsolute = true;
      this.world = var2;
      this.bulletGen = var3;
      if(var4 != null) {
         this.clockShoot = new Clock(var4);
      } else {
         this.clockShoot = null;
      }

      this.shootPoint = new Vector2(var5);
      int var7 = var6.length / 2;
      this.directionsVel = new Vector2[var7];
      int var8 = 0;

      for(int var9 = 0; var8 < var7; var9 += 2) {
         this.directionsVel[var8] = new Vector2(var6[var9], var6[var9 + 1]);
         ++var8;
      }

      this.shootBulletCount = 0;
      this.bulletsSet = Enemy.bullets;
   }

   public boolean canStop() {
      if(this.clockShoot != null) {
         return this.getShootBulletCount() / this.getDirectionsVel().length % this.clockShoot.getNum() == 0;
      } else {
         Settings.appLog("clockShoot is null, you should ask invoker to know whether bulletGun can stop");
         return true;
      }
   }

   public void forceStop() {
      if(this.clockShoot != null) {
         this.clockShoot.resetClockFireOnce(200000.0F);
      } else {
         Settings.appLog("NOTICE: when forceStop is invoked, the clockShoot is null");
      }
   }

   public Vector2[] getDirectionsVel() {
      return this.directionsVel;
   }

   public int getShootBulletCount() {
      return this.shootBulletCount;
   }

   public Vector2 getShootingPoint() {
      return this.shootPoint;
   }

   protected Vector2 getWorldShootXY() {
      if(this.shootPointIsAbsolute) {
         return this.shootPointTmp.set(this.shootPoint);
      } else if(this.rotateAccordEnemy && this.owner.getAngle() != 0.0F) {
         this.shootPointTmp.set(this.shootPoint);
         this.originTmp.set(this.owner.origin);
         DynamicGameObject.RotatePoint(this.shootPointTmp, this.originTmp, this.owner.getAngle());
         this.shootPointTmp.add(this.owner.bounds.x, this.owner.bounds.y);
         return this.shootPointTmp;
      } else {
         this.shootPointTmp.set(this.shootPoint);
         this.shootPointTmp.add(this.owner.bounds.x, this.owner.bounds.y);
         return this.shootPointTmp;
      }
   }

   public void reset() {
      if(this.clockShoot != null) {
         if(this.clockShoot.getClockType() == Clock.ClockType.FireOnlyOnce) {
            this.clockShoot.resetClockFireOnce(0.5F);
         } else {
            this.clockShoot.resetClock(this.clockShoot.getSpan() - 0.5F, this.clockShoot.getSpan(), this.clockShoot.getDelay(), this.clockShoot.getNum());
         }
      } else {
         Settings.appLog("clockShoot is null, you should not reset it.");
      }
   }

   public void resetShootClock(boolean var1, float var2, float var3, float var4, byte var5) {
      if(this.clockShoot != null) {
         this.clockShoot.resetClock(var1, var2, var3, var4, var5);
         this.shootBulletCount = 0;
      } else {
         Settings.appLog("clockShoot is null, can not be reset");
      }
   }

   public void setShootPosition(float var1, float var2) {
      this.shootPoint.x = var1;
      this.shootPoint.y = var2;
   }

   protected void shootBullets() {
      Vector2 var1 = this.getWorldShootXY();
      Vector2[] var2 = this.directionsVel;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Vector2 var5 = var2[var4];
         this.bulletsSet.add(this.bulletGen.getABullet(this.owner, this.world, var1.x, var1.y, var5));
      }

      this.shootBulletCount += this.directionsVel.length;
   }

   public void shooting() {
      if(this.clockShoot != null) {
         Settings.appLog("if clockShoot is not null, shooting function should not be invoked.");
      } else {
         this.shootBullets();
      }
   }

   public void updateShooting(float var1) {
      if(this.clockShoot != null && this.clockShoot.isFired()) {
         this.shootBullets();
      }

   }
}
