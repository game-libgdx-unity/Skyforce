package com.doodlegames.air.force.fighters.gun;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.fighters.Rafale;
import com.doodlegames.air.force.fighters.gun.FighterGun;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.Gun.FighterBulletGun;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.game.bullet.FighterBullet;
import com.doodlegames.air.force.utils.Clock;

public class RafaleBlueGun implements FighterGun {

   private static final float COS0 = 1.0F;
   private static final float COS10 = MathUtils.cosDeg(10.0F);
   private static final float COS20 = MathUtils.cosDeg(20.0F);
   private static final float COS30 = MathUtils.cosDeg(30.0F);
   private static final float COS60 = MathUtils.cosDeg(60.0F);
   private static final float COS90 = MathUtils.cosDeg(90.0F);
   private static final float DELAY = 0.08F;
   private static int GUNNUM = 0;
   private static final float INIAHEAD = 0.0F;
   public static final Vector2[] SHOOTPOINT;
   private static final float SIN0 = 0.0F;
   private static final float SIN10 = MathUtils.sinDeg(10.0F);
   private static final float SIN20 = MathUtils.sinDeg(20.0F);
   private static final float SIN30 = MathUtils.sinDeg(30.0F);
   private static final float SIN60 = MathUtils.sinDeg(60.0F);
   private static final float SIN90 = MathUtils.sinDeg(90.0F);
   private static final float SPAN = 0.2F;
   private static final int[][] avaGunIndex;
   private static final int[][] bulletIndex;
   private static final float[] velX;
   private static final float[] velY;
   private Clock clockShooting = new Clock(0.0F, 0.2F, 0.08F, (byte)1);
   private FighterBulletGun[] guns;
   private int level;


   static {
      Vector2[] var0 = new Vector2[]{new Vector2(-16.0F * SIN90 / 1.0F + Rafale.SHOOTPOINT[0].x, 16.0F * COS90 / 1.0F + Rafale.SHOOTPOINT[0].y), new Vector2(-16.0F * SIN60 / 1.0F + Rafale.SHOOTPOINT[1].x, 16.0F * COS60 / 1.0F + Rafale.SHOOTPOINT[1].y), new Vector2(-16.0F * SIN30 / 1.0F + Rafale.SHOOTPOINT[2].x, 16.0F * COS30 / 1.0F + Rafale.SHOOTPOINT[2].y), new Vector2(0.0F + Rafale.SHOOTPOINT[3].x, 16.0F + Rafale.SHOOTPOINT[3].y), new Vector2(16.0F * SIN30 / 1.0F + Rafale.SHOOTPOINT[4].x, 16.0F * COS30 / 1.0F + Rafale.SHOOTPOINT[4].y), new Vector2(16.0F * SIN60 / 1.0F + Rafale.SHOOTPOINT[5].x, 16.0F * COS60 / 1.0F + Rafale.SHOOTPOINT[5].y), new Vector2(16.0F * SIN90 / 1.0F + Rafale.SHOOTPOINT[6].x, 16.0F * COS90 / 1.0F + Rafale.SHOOTPOINT[6].y), new Vector2(-16.0F * SIN20 / 1.0F + Rafale.SHOOTPOINT[1].x, 16.0F * COS20 / 1.0F + Rafale.SHOOTPOINT[1].y), new Vector2(-16.0F * SIN10 / 1.0F + Rafale.SHOOTPOINT[2].x, 16.0F * COS10 / 1.0F + Rafale.SHOOTPOINT[2].y), new Vector2(16.0F * SIN10 / 1.0F + Rafale.SHOOTPOINT[4].x, 16.0F * COS10 / 1.0F + Rafale.SHOOTPOINT[4].y), new Vector2(16.0F * SIN20 / 1.0F + Rafale.SHOOTPOINT[5].x, 16.0F * COS20 / 1.0F + Rafale.SHOOTPOINT[5].y)};
      SHOOTPOINT = var0;
      GUNNUM = SHOOTPOINT.length;
      float[] var1 = new float[]{-550.0F * SIN90 / 1.0F, -550.0F * SIN60 / 1.0F, -550.0F * SIN30 / 1.0F, 0.0F, 550.0F * SIN30 / 1.0F, 550.0F * SIN60 / 1.0F, 550.0F * SIN90 / 1.0F, -550.0F * SIN20 / 1.0F, -550.0F * SIN10 / 1.0F, 550.0F * SIN10 / 1.0F, 550.0F * SIN20 / 1.0F};
      velX = var1;
      float[] var2 = new float[]{550.0F * COS90 / 1.0F, 550.0F * COS60 / 1.0F, 550.0F * COS30 / 1.0F, 550.0F, 550.0F * COS30 / 1.0F, 550.0F * COS60 / 1.0F, 550.0F * COS90 / 1.0F, 550.0F * COS20 / 1.0F, 550.0F * COS10 / 1.0F, 550.0F * COS10 / 1.0F, 550.0F * COS20 / 1.0F};
      velY = var2;
      avaGunIndex = new int[][]{{0, 3, 6}, {3, 7, 8, 9, 10}, {0, 1, 2, 3, 4, 5, 6}};
      bulletIndex = new int[][]{{0, 0, 0}, {1, 1, 0, 0, 1}, {2, 1, 1, 2, 1, 1, 2}};
   }

   public RafaleBlueGun(Fighter var1) {
      this.guns = new FighterBulletGun[GUNNUM];

      for(int var2 = 0; var2 < GUNNUM; ++var2) {
         BulletGenerator var3 = FighterBullet.getRafaleBulletGen()[0];
         World var4 = var1.world;
         Vector2 var5 = SHOOTPOINT[var2];
         float[] var6 = new float[]{velX[var2], velY[var2]};
         FighterBulletGun var7 = new FighterBulletGun(var1, var4, var3, (Clock)null, var5, var6);
         var7.shootPointIsAbsolute = false;
         this.guns[var2] = var7;
      }

      this.level = 0;
      this.increaseLevel();
   }

   public int getLevel() {
      return this.level;
   }

   public void increaseLevel() {
      if(this.level < 3) {
         ++this.level;
         int[] var1 = avaGunIndex[-1 + this.level];
         int[] var2 = bulletIndex[-1 + this.level];
         int var3 = var1.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = var1[var4];
            int var6 = var2[var4];
            BulletGenerator var7 = FighterBullet.getRafaleBulletGen()[var6];
            this.guns[var5].bulletGen = var7;
         }
      }

   }

   public void reset() {
      this.clockShooting.resetClock(0.0F, 0.2F, 0.08F, (byte)1);
      FighterBulletGun[] var1 = this.guns;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         var1[var3].bulletGen = FighterBullet.getRafaleBulletGen()[0];
      }

      this.level = 0;
      this.increaseLevel();
   }

   public void updateShooting(float var1) {
      if(this.clockShooting.isFired()) {
         int[] var2 = avaGunIndex[-1 + this.level];
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = var2[var4];
            this.guns[var5].shooting();
         }

         ((Fighter)this.guns[0].owner).playSound(this.level);
      }

   }
}
