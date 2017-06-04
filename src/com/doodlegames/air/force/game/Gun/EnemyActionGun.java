package com.doodlegames.air.force.game.Gun;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.Gun.BulletShooter;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.game.bullet.BulletScreen;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.game.bullet.EnemyBullet2;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.Iterator;

public class EnemyActionGun implements BulletShooter {

   protected float bulletAngle = 0.0F;
   protected BulletGenerator bulletGen;
   protected Vector2[] bulletVels;
   protected float[] delayTime;
   protected Enemy enemy;
   protected EnemyActionGun.GunInfoNotifier gunInfo;
   protected int positionAvaNum;
   protected Clock shootClock;
   protected Vector2 shootPoint;
   protected Array<EnemyActionGun.GunSpecialAction> specialActions;
   protected EnemyTools.TurnDataProvider turnData;


   public EnemyActionGun(Enemy var1, Clock var2, float var3, float var4, BulletGenerator var5, float var6, float[][] var7) {
      int var8 = var7.length;
      this.bulletVels = new Vector2[var8];
      this.delayTime = new float[var8];

      for(int var9 = 0; var9 < var8; ++var9) {
         this.bulletVels[var9] = new Vector2();
         this.bulletVels[var9].set(0.0F, -Math.abs(var7[var9][0]));
         this.bulletVels[var9].rotate(var7[var9][1]);
         this.delayTime[var9] = var6;
      }

      this.constructGun(var1, var2, var3, var4, var5, var8);
   }

   public EnemyActionGun(Enemy var1, Clock var2, float var3, float var4, BulletGenerator var5, float ... var6) {
      int var7 = var6.length / 3;
      this.bulletVels = new Vector2[var7];
      this.delayTime = new float[var7];

      for(int var8 = 0; var8 < var7; ++var8) {
         this.bulletVels[var8] = new Vector2();
         this.bulletVels[var8].x = var6[var8 * 3];
         this.bulletVels[var8].y = var6[1 + var8 * 3];
         this.delayTime[var8] = var6[2 + var8 * 3];
      }

      this.constructGun(var1, var2, var3, var4, var5, var7);
   }

   public static void addPursueAction(EnemyActionGun var0, float var1) {
      EnemyActionGun.GunPursue var2 = new EnemyActionGun.GunPursue();
      var2.offset = var1;
      var0.addGunSpecialAction(var2);
   }

   public static void addTurningAction(EnemyActionGun var0, float var1) {
      EnemyActionGun.GunTurning var2 = new EnemyActionGun.GunTurning();
      var2.angleSpeed = var1;
      var0.addGunSpecialAction(var2);
   }

   private void constructGun(Enemy var1, Clock var2, float var3, float var4, BulletGenerator var5, int var6) {
      this.turnData = var1;
      this.enemy = var1;
      this.shootClock = var2;
      this.shootPoint = new Vector2();
      this.shootPoint.set(var3, var4);
      this.bulletGen = var5;
      this.positionAvaNum = var6;
      this.gunInfo = new EnemyActionGun.GunInfoNotifier();
      this.specialActions = new Array(1);
   }

   public static EnemyActionGun getAlienCannonGun(Enemy var0, float var1, float var2, float var3, float var4) {
      BulletGenerator var5 = EnemyBullet1.alienBulletGen;
      float[] var6 = new float[]{0.0F, -Math.abs(var1), var4};
      return new EnemyActionGun(var0, (Clock)null, var2, var3, var5, var6);
   }

   public static EnemyActionGun getAlienMutiScreGun(Enemy var0, Clock var1, float var2, float var3, float[][][] var4) {
      return getMutiScreenGun(var0, EnemyBullet1.alienBulletGen, var1, var2, var3, var4);
   }

   public static EnemyActionGun getGun(BulletGenerator var0, Enemy var1, float var2, float var3, float var4, float var5) {
      float[] var6 = new float[]{0.0F, -Math.abs(var2), var5};
      return new EnemyActionGun(var1, (Clock)null, var3, var4, var0, var6);
   }

   public static EnemyActionGun getMutiScreenGun(Enemy var0, BulletGenerator var1, Clock var2, float var3, float var4, float[][][] var5) {
      EnemyActionGun var6 = new EnemyActionGun(var0, var2, var3, var4, var1, new float[]{0.0F});
      EnemyActionGun.MutiScreenGun var7 = new EnemyActionGun.MutiScreenGun(var5);
      var7.position = 3;
      var6.addGunSpecialAction(var7);
      return var6;
   }

   public static EnemyActionGun getNaziCannonGun(Enemy var0, float var1, float var2, float var3, float var4) {
      BulletGenerator var5 = EnemyBullet1.naziBulletGen;
      float[] var6 = new float[]{0.0F, -Math.abs(var1), var4};
      return new EnemyActionGun(var0, (Clock)null, var2, var3, var5, var6);
   }

   public static EnemyActionGun getNaziRocketGun(Enemy var0, float var1, float var2, float var3, float var4) {
      return getGun(EnemyBullet2.naziBulletGen, var0, var1, var2, var3, var4);
   }

   public static EnemyActionGun getScreenGun(Enemy var0, Clock var1, float var2, float var3, float[][] var4) {
      return new EnemyActionGun(var0, var1, var2, var3, EnemyBullet1.alienBulletGen, 0.0F, var4);
   }

   public void addGunSpecialAction(EnemyActionGun.GunSpecialAction var1) {
      this.specialActions.add(var1);
   }

   public boolean canStop() {
      return this.gunInfo.actionCount == this.gunInfo.bulletCount + this.gunInfo.failedCount;
   }

   public void forceStop() {
      this.gunInfo.shootCanceled = true;
   }

   public float getBulletAngle() {
      return this.bulletAngle;
   }

   public BulletGenerator getBulletGen() {
      return this.bulletGen;
   }

   public Vector2[] getDirectionsVel() {
      return this.bulletVels;
   }

   public Enemy getEnemy() {
      return this.enemy;
   }

   public int getPositionAvaNum() {
      return this.positionAvaNum;
   }

   public int getShootBulletCount() {
      return this.gunInfo.bulletCount;
   }

   public Vector2 getShootingPoint() {
      return this.shootPoint;
   }

   public int getTotalVelNum() {
      return this.bulletVels.length;
   }

   public EnemyTools.TurnDataProvider getTurnDataProvider() {
      return this.turnData;
   }

   public void reset() {
      if(this.gunInfo.actionCount != this.gunInfo.bulletCount + this.gunInfo.failedCount) {
         this.gunInfo.shootCanceled = true;
         this.gunInfo = new EnemyActionGun.GunInfoNotifier();
         Settings.w("Shoot actions do not completed, reset will cause new operator!!!");
      } else {
         this.gunInfo.actionCount = 0;
         this.gunInfo.bulletCount = 0;
         this.gunInfo.failedCount = 0;
         this.gunInfo.shootCanceled = false;
      }

      if(this.shootClock != null) {
         this.shootClock.restartClock();
      }

   }

   public void resetShootClock(boolean var1, float var2, float var3, float var4, byte var5) {
      if(this.shootClock != null) {
         this.shootClock.resetClock(var1, var2, var3, var4, var5);
      } else {
         Settings.e("can not reset a gun\'s clock because it is null!");
      }
   }

   public void setBulletAngle(float var1) {
      this.bulletAngle = var1;
   }

   public void setBulletGen(BulletGenerator var1) {
      this.bulletGen = var1;
   }

   public void setPositionAvaNum(int var1) {
      if(var1 >= 0 && var1 < this.bulletVels.length) {
         this.positionAvaNum = var1;
      } else {
         Settings.e("setAvaNum error, invalid avaNum:" + var1);
      }
   }

   public void setTurnData(EnemyTools.TurnDataProvider var1) {
      if(var1 != null) {
         this.turnData = var1;
      }

   }

   public void shooting() {
      if(!this.gunInfo.shootCanceled) {
         Iterator var1 = this.specialActions.iterator();

         while(var1.hasNext()) {
            ((EnemyActionGun.GunSpecialAction)var1.next()).shooting(this);
         }

         BulletGenerator var2 = this.bulletGen;
         int var3 = Math.min(this.bulletVels.length, this.positionAvaNum);

         for(int var4 = 0; var4 < var3; ++var4) {
            Vector2 var5 = this.bulletVels[var4];
            float var6 = this.delayTime[var4];
            EnemyTools.DelayShootAction var7 = EnemyTools.enemyShoot(this.enemy, var6, var2, this.turnData, var5.x, var5.y, this.shootPoint.x, this.shootPoint.y, -this.bulletAngle, this.gunInfo);
            this.enemy.addEnemyAction(var7);
            EnemyActionGun.GunInfoNotifier var8 = this.gunInfo;
            ++var8.actionCount;
         }
      }

   }

   public void updateShooting(float var1) {
      Iterator var2 = this.specialActions.iterator();

      while(var2.hasNext()) {
         ((EnemyActionGun.GunSpecialAction)var2.next()).updateShooting(this, var1);
      }

      if(this.shootClock != null && this.shootClock.isFired()) {
         this.shooting();
      }

   }

   public static class GunTurning implements EnemyActionGun.GunSpecialAction {

      public float angleSpeed;


      public GunTurning() {
         this(45.0F);
      }

      public GunTurning(float var1) {
         this.angleSpeed = 45.0F;
         this.angleSpeed = var1;
      }

      public void shooting(EnemyActionGun var1) {}

      public void updateShooting(EnemyActionGun var1, float var2) {
         var1.bulletAngle += var2 * this.angleSpeed;
         if(var1.bulletAngle > 360.0F) {
            var1.bulletAngle -= 360.0F;
         }

      }
   }

   public static class GunInfoNotifier implements EnemyTools.GunInfo {

      protected int actionCount = 0;
      protected int bulletCount = 0;
      protected int failedCount = 0;
      protected boolean shootCanceled = false;


      public boolean gunCancelShoot() {
         return this.shootCanceled;
      }

      public void shootComplete(EnemyTools.DelayShootAction var1, Bullet var2) {
         ++this.bulletCount;
      }

      public void shootFailed(EnemyTools.DelayShootAction var1) {
         ++this.failedCount;
      }
   }

   public interface GunSpecialAction {

      void shooting(EnemyActionGun var1);

      void updateShooting(EnemyActionGun var1, float var2);
   }

   public static class GunPursue implements EnemyActionGun.GunSpecialAction {

      float offset;


      public GunPursue() {
         this.offset = 0.0F;
      }

      public GunPursue(float var1) {
         this.offset = var1;
      }

      public void shooting(EnemyActionGun var1) {
         float var2 = var1.getShootingPoint().x;
         float var3 = var1.getShootingPoint().y;
         var1.setBulletAngle(DynamicGameObject.getPursueAngle(var2 + var1.enemy.bounds.x, var3 + var1.enemy.bounds.y, var1.enemy.world.fighter.getPositionX() + this.offset, var1.enemy.world.fighter.getPositionY()));
      }

      public void updateShooting(EnemyActionGun var1, float var2) {}
   }

   public static class MutiScreenGun implements EnemyActionGun.GunSpecialAction {

      public float[][][] bulletScreenDatas;
      public int position = 3;


      public MutiScreenGun(float[][][] var1) {
         this.bulletScreenDatas = var1;
      }

      public void shooting(EnemyActionGun var1) {
         float var2 = var1.getShootingPoint().x;
         float var3 = var1.getShootingPoint().y;
         float[][] var4 = BulletScreen.getPublicScreen(this.position, this.bulletScreenDatas);
         EnemyTools.enemyShootScreen(var1.enemy, 0.0F, var1.bulletGen, var2, var3, var4);
      }

      public void updateShooting(EnemyActionGun var1, float var2) {}
   }
}
