package com.doodlegames.air.force.fighters.gun;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector2Utils;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.Gun.FighterBulletGun;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.game.bullet.FighterBullet;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

public class WingFighterGun implements FighterGun {

   private static final float DELAY = 0.08F;
   public static final Vector2[] SHOOTPOINT;
   private static final float[] SPAN;
   private static BulletGenerator[] bulletGens;
   private Clock clockShooting;
   private FighterBulletGun[] guns;
   private int level;
   private WingFighter owner;
   private WingFighterGun.WingFighterGuns[] wingFighterGunsAllLevel;
   private WingFighterGun.WingFighterGuns wingFighterGunsLevel1 = new WingFighterGun.WingFighterGuns() {
      public void shooting() {
         if(WingFighterGun.this.guns[0] != null) {
            WingFighterGun.this.guns[0].shooting();
         } else {
            Settings.appLog("wingFighterGun.this.guns[0] == null can not shoot!");
         }

         if(WingFighterGun.this.guns[1] != null) {
            WingFighterGun.this.guns[1].shooting();
         } else {
            Settings.appLog("wingFighterGun.this.guns[1] == null can not shoot!");
         }
      }
   };
   private WingFighterGun.WingFighterGuns wingFighterGunsLevel2 = new WingFighterGun.WingFighterGuns() {
      public void shooting() {
         if(WingFighterGun.this.guns[2] != null) {
            WingFighterGun.this.guns[2].shooting();
         } else {
            Settings.appLog("wingFighterGun.this.guns[2] == null can not shoot!");
         }

         if(WingFighterGun.this.guns[3] != null) {
            WingFighterGun.this.guns[3].shooting();
         } else {
            Settings.appLog("wingFighterGun.this.guns[3] == null can not shoot!");
         }

         if(WingFighterGun.this.guns[4] != null) {
            WingFighterGun.this.guns[4].shooting();
         } else {
            Settings.appLog("wingFighterGun.this.guns[4] == null can not shoot!");
         }

         if(WingFighterGun.this.guns[5] != null) {
            WingFighterGun.this.guns[5].shooting();
         } else {
            Settings.appLog("wingFighterGun.this.guns[5] == null can not shoot!");
         }
      }
   };
   private World world;


   static {
      Vector2[] var0 = new Vector2[]{new Vector2(19.0F, 77.0F), new Vector2(114.0F, 77.0F), new Vector2(8.0F, 60.0F), new Vector2(30.0F, 60.0F), new Vector2(103.0F, 60.0F), new Vector2(125.0F, 60.0F)};
      SHOOTPOINT = var0;
      SPAN = new float[]{1.2F, 1.0F, 0.8F, 0.7F, 0.7F, 0.6F};
      BulletGenerator[] var1 = new BulletGenerator[]{FighterBullet.wingBullet1Gen, FighterBullet.wingBullet1Gen, FighterBullet.wingBullet2Gen, FighterBullet.wingBullet3Gen, FighterBullet.wingBullet3Gen, FighterBullet.wingBullet4Gen};
      bulletGens = var1;
   }

   public WingFighterGun(WingFighter var1, World var2) {
      WingFighterGun.WingFighterGuns[] var3 = new WingFighterGun.WingFighterGuns[]{this.wingFighterGunsLevel1, this.wingFighterGunsLevel1, this.wingFighterGunsLevel1, this.wingFighterGunsLevel1, this.wingFighterGunsLevel2, this.wingFighterGunsLevel2};
      this.wingFighterGunsAllLevel = var3;
      this.iniWingFighterGun(var2, var1);
      this.clockShooting = new Clock(0.0F, SPAN[0], 0.08F, (byte)1);
      this.guns = new FighterBulletGun[SHOOTPOINT.length];

      for(int var4 = 0; var4 < SHOOTPOINT.length; ++var4) {
         Vector2Utils.tmp.set(SHOOTPOINT[var4]);
         Vector2Utils.tmp.add(var1.getWingRelativeXY());
         FighterBulletGun var7 = new FighterBulletGun(this.owner, this.world, FighterBullet.bulletGen, (Clock)null, Vector2Utils.tmp, new float[]{0.0F, 550.0F});
         var7.shootPointIsAbsolute = false;
         this.guns[var4] = var7;
      }

      this.level = 0;
      this.setLevel(this.level);
   }

   private void iniWingFighterGun(World var1, WingFighter var2) {
      this.world = var1;
      this.owner = var2;
      this.level = 0;
   }

   public int getLevel() {
      return this.level;
   }

   public void increaseLevel() {
      if(this.level < -1 + this.wingFighterGunsAllLevel.length) {
         this.setLevel(1 + this.level);
      }

   }

   public void reset() {
      this.setLevel(0);
      this.clockShooting.restartClock();
   }

   public void setLevel(int var1) {
      if(var1 >= 0 && var1 < this.wingFighterGunsAllLevel.length) {
         this.level = var1;
         FighterBulletGun[] var2 = this.guns;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            var2[var4].bulletGen = bulletGens[this.level];
         }

         this.clockShooting.resetClock(0.0F, SPAN[this.level], 0.08F, (byte)1);
      }

   }

   public void updateShooting(float var1) {
      if(this.clockShooting.isFired()) {
         int var2 = this.wingFighterGunsAllLevel.length;
         if(this.level >= 0 && this.level < var2) {
            this.wingFighterGunsAllLevel[this.level].shooting();
         }
      }

   }

   private interface WingFighterGuns {

      void shooting();
   }
}
