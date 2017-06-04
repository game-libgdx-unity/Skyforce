package com.doodlegames.air.force.fighters.gun;

import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.fighters.gun.FighterGun;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.Gun.FighterBulletGun;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.game.bullet.FighterBullet;
import com.doodlegames.air.force.utils.Clock;

public class FighterBlueGun implements FighterGun {

   private static final float DELAY = 0.08F;
   private static final int GUNNUM = 5;
   private static final float INIAHEAD = 0.0F;
   public static final Vector2[] SHOOTPOINT;
   private static final float SPAN = 0.2F;
   private static final float[] velX;
   private static final float[] velY;
   private Clock clockShooting = new Clock(0.0F, 0.2F, 0.08F, (byte)1);
   private FighterBulletGun[] guns = new FighterBulletGun[5];
   private int level;


   static {
      Vector2[] var0 = new Vector2[]{new Vector2(11.0F, 50.0F), new Vector2(20.0F, 57.0F), new Vector2(29.0F, 90.0F), new Vector2(39.0F, 57.0F), new Vector2(49.0F, 50.0F)};
      SHOOTPOINT = var0;
      velX = new float[]{-120.0F, -60.0F, 0.0F, 60.0F, 120.0F};
      velY = new float[]{550.0F, 550.0F, 550.0F, 550.0F, 550.0F};
   }

   public FighterBlueGun(Fighter var1) {
      for(int var2 = 0; var2 < 5; ++var2) {
         World var3 = var1.world;
         BulletGenerator var4 = FighterBullet.bulletGen;
         Vector2 var5 = SHOOTPOINT[var2];
         float[] var6 = new float[]{velX[var2], velY[var2]};
         FighterBulletGun var7 = new FighterBulletGun(var1, var3, var4, (Clock)null, var5, var6);
         var7.shootPointIsAbsolute = false;
         this.guns[var2] = var7;
      }

      this.level = 1;
   }

   public int getLevel() {
      return this.level;
   }

   public void increaseLevel() {
      if(this.level < 3) {
         ++this.level;
         if(this.level == 2) {
            this.clockShooting.resetClock(0.0F, 0.2F, 0.08F, (byte)2);
         }

         if(this.level == 3) {
            this.clockShooting.resetClock(0.0F, 0.3F, 0.08F, (byte)4);
         }
      }

   }

   public void reset() {
      this.level = 1;
      this.clockShooting.resetClock(0.0F, 0.2F, 0.08F, (byte)1);
   }

   public void updateShooting(float var1) {
      if(this.clockShooting.isFired()) {
         switch(this.level) {
         case 1:
            this.guns[1].shooting();
            this.guns[3].shooting();
            return;
         case 2:
            this.guns[0].shooting();
            this.guns[1].shooting();
            this.guns[3].shooting();
            this.guns[4].shooting();
            return;
         case 3:
            this.guns[0].shooting();
            this.guns[1].shooting();
            this.guns[2].shooting();
            this.guns[3].shooting();
            this.guns[4].shooting();
            return;
         }
      }

   }
}
