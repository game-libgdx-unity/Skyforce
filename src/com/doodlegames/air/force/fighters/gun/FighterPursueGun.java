package com.doodlegames.air.force.fighters.gun;

import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.fighters.gun.FighterGun;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.Gun.FighterBulletGun;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

public class FighterPursueGun implements FighterGun {

   private static final float DELAY = 0.08F;
   private static final int GUNNUM = 3;
   private static final float INIAHEAD = 0.0F;
   public static final Vector2[] SHOOTPOINT;
   private static final float SPAN = 0.2F;
   private static final float velY = 10.0F;
   private Clock clockShooting;
   private final Fighter fighter;
   private FighterBulletGun[] guns;
   private int level;
   private World world;


   static {
      Vector2[] var0 = new Vector2[]{new Vector2(20.0F, 57.0F), new Vector2(29.0F, 90.0F), new Vector2(39.0F, 57.0F)};
      SHOOTPOINT = var0;
   }

   public FighterPursueGun(Fighter var1, World var2) {
      this.fighter = var1;
      if(Settings.missleEffectLevel >= 3) {
         this.clockShooting = new Clock(1.0F, 0.3F, (byte)2);
      } else {
         this.clockShooting = new Clock(1.0F, 0.3F, (byte)1);
      }

      this.world = var2;
      BulletGenerator var3 = Settings.missleGen[Settings.missleEffectLevel];
      FighterBulletGun[] var4 = new FighterBulletGun[]{new FighterBulletGun(this.fighter, var2, var3, (Clock)null, SHOOTPOINT[0], new float[]{0.0F, 10.0F}), new FighterBulletGun(this.fighter, var2, var3, (Clock)null, SHOOTPOINT[2], new float[]{0.0F, 10.0F})};
      this.guns = var4;

      for(int var5 = 0; var5 < this.guns.length; ++var5) {
         this.guns[var5].shootPointIsAbsolute = false;
      }

   }

   public int getLevel() {
      return this.level;
   }

   public void increaseLevel() {}

   public void reset() {}

   public void updateShooting(float var1) {
      if(this.clockShooting.isFired() && !this.fighter.isCrashed()) {
         for(int var2 = 0; var2 < this.guns.length; ++var2) {
            this.guns[var2].shooting();
         }
      }

   }
}
