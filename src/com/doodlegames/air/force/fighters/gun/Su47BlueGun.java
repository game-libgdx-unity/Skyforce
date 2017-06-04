package com.doodlegames.air.force.fighters.gun;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.fighters.Su47;
import com.doodlegames.air.force.fighters.gun.FighterGun;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.Gun.FighterBulletGun;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.game.bullet.FighterBullet;
import com.doodlegames.air.force.utils.Clock;

public class Su47BlueGun implements FighterGun {

   private static final float COS14 = MathUtils.cosDeg(14.0F);
   private static final float COS21 = MathUtils.cosDeg(21.0F);
   private static final float COS7 = MathUtils.cosDeg(7.0F);
   private static final float DELAY = 0.08F;
   private static final int GUNNUM = 7;
   private static final float INIAHEAD = 0.0F;
   public static final Vector2[] SHOOTPOINT;
   private static final float SIN14 = MathUtils.sinDeg(14.0F);
   private static final float SIN21 = MathUtils.sinDeg(21.0F);
   private static final float SIN7 = MathUtils.sinDeg(7.0F);
   private static final float SPAN = 0.2F;
   private static final int[][] avaGunIndex;
   private static final int[][] bulletIndex;
   private static final float[] velX;
   private static final float[] velY;
   private Clock clockShooting = new Clock(0.0F, 0.2F, 0.08F, (byte)1);
   private FighterBulletGun[] guns = new FighterBulletGun[7];
   private int level;


   static {
      Vector2[] var0 = new Vector2[]{new Vector2(-16.0F * SIN21 / 1.0F + Su47.SHOOTPOINT[0].x, 16.0F * COS21 / 1.0F + Su47.SHOOTPOINT[0].y), new Vector2(-16.0F * SIN14 / 1.0F + Su47.SHOOTPOINT[1].x, 16.0F * COS14 / 1.0F + Su47.SHOOTPOINT[1].y), new Vector2(-16.0F * SIN7 / 1.0F + Su47.SHOOTPOINT[2].x, 16.0F * COS7 / 1.0F + Su47.SHOOTPOINT[2].y), new Vector2(30.0F, 86.0F), new Vector2(16.0F * SIN7 / 1.0F + Su47.SHOOTPOINT[5].x, 16.0F * COS7 / 1.0F + Su47.SHOOTPOINT[5].y), new Vector2(16.0F * SIN14 / 1.0F + Su47.SHOOTPOINT[6].x, 16.0F * COS14 / 1.0F + Su47.SHOOTPOINT[6].y), new Vector2(16.0F * SIN21 / 1.0F + Su47.SHOOTPOINT[7].x, 16.0F * COS21 / 1.0F + Su47.SHOOTPOINT[7].y)};
      SHOOTPOINT = var0;
      float[] var1 = new float[]{-550.0F * SIN21 / 1.0F, -550.0F * SIN14 / 1.0F, -550.0F * SIN7 / 1.0F, 0.0F, 550.0F * SIN7 / 1.0F, 550.0F * SIN14 / 1.0F, 550.0F * SIN21 / 1.0F};
      velX = var1;
      float[] var2 = new float[]{550.0F * COS21 / 1.0F, 550.0F * COS14 / 1.0F, 550.0F * COS7 / 1.0F, 550.0F, 550.0F * COS7 / 1.0F, 550.0F * COS14 / 1.0F, 550.0F * COS21 / 1.0F};
      velY = var2;
      avaGunIndex = new int[][]{{0, 2, 4, 6}, {0, 1, 2, 3, 4, 5, 6}, {0, 1, 2, 3, 4, 5, 6}};
      bulletIndex = new int[][]{{0, 0, 0, 0}, {0, 0, 0, 1, 0, 0, 0}, {2, 2, 2, 2, 2, 2, 2}};
   }

   public Su47BlueGun(Fighter var1) {
      for(int var2 = 0; var2 < 7; ++var2) {
         BulletGenerator var3 = FighterBullet.getSu47BulletGen()[0];
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
            BulletGenerator var7 = FighterBullet.getSu47BulletGen()[var6];
            this.guns[var5].bulletGen = var7;
         }
      }

   }

   public void reset() {
      this.clockShooting.resetClock(0.0F, 0.2F, 0.08F, (byte)1);
      FighterBulletGun[] var1 = this.guns;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         var1[var3].bulletGen = FighterBullet.getSu47BulletGen()[0];
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
