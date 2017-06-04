package com.doodlegames.air.force.fighters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector2Utils;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.fighters.gun.RafaleBlueGun;
import com.doodlegames.air.force.fighters.gun.RafaleRedGun;
import com.doodlegames.air.force.fighters.gun.RafaleYellowGun;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.BoomBullet;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.resource.Assets_fighter;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;

import java.util.List;

public class Rafale extends Fighter {

   public static final int DEFAULTLIVES = 0;
   private static final int InitHealthy = 1;
   public static final Vector2[] SHOOTPOINT;
   static final int boomTime = 3;
   private static final float timeOfPlayCrashedAnimation = 0.3F;
   float boomTimeStamp = -1.0F;
   boolean isBooming = false;


   static {
      Vector2[] var0 = new Vector2[]{new Vector2(2.0F, 37.0F), new Vector2(11.0F, 48.0F), new Vector2(20.0F, 64.0F), new Vector2(30.0F, 77.0F), new Vector2(40.0F, 64.0F), new Vector2(49.0F, 48.0F), new Vector2(58.0F, 37.0F)};
      SHOOTPOINT = var0;
   }

   public Rafale(World var1, float var2, float var3) {
      super(var1, var2, var3, (float)RegionUtilFunctions.getRegionWidth(Assets_fighter.rafaleStand) / 1.0F, (float)RegionUtilFunctions.getRegionHeight(Assets_fighter.rafaleStand) / 1.0F);
      this.initRafale();
      this.avalibleGuns[0] = new RafaleRedGun(this);
      this.avalibleGuns[1] = new RafaleYellowGun(this);
      this.avalibleGuns[2] = new RafaleBlueGun(this);
      this.currentGun = this.avalibleGuns[0];
      this.currentGun.reset();
      if(!this.loadFighter()) {
         ;
      }

   }

   private float getBoomDurationTime() {
      return Clock.getCurrentTime(this.boomTimeStamp);
   }

   public void beginCheck() {
      super.beginCheck();
   }

   public void boomStart() {
      if(!this.isBooming) {
         this.isBooming = true;
         this.boomTimeStamp = Clock.getCurrentTime();
         Vector2 var1 = Vector2Utils.tmp;
         var1.set(0.0F, 300.0F);

         for(int var3 = 0; var3 < 24; ++var3) {
            float var4 = this.getPositionX();
            float var5 = this.getPositionY();
            Bullet var6 = BoomBullet.rafaleBulletGen.getABullet(this, this.world, var4, var5, var1);
            this.angle += 15.0F;
            var1.rotate(15.0F);
            Fighter.bullets.add(var6);
         }
      }

   }

   public void checking(Enemy var1, List<Rectangle> var2) {
      super.checking(var1, var2);
   }

   public void endCheck() {
      super.endCheck();
   }

   public List<Rectangle> getHitRectangle() {
      return (List)(this.isBooming()?Enemy.EMPTYRECTANGELS:super.getHitRectangle());
   }

   public float getTimeOfCrash() {
      return 0.3F;
   }

   public void initRafale() {
      this.fightStand = Assets_fighter.rafaleStand;
      this.fightLeft = Assets_fighter.rafaleFlyingToLeft;
      this.fightRight = Assets_fighter.rafaleFlyingToRight;
      this.healthyDegree = 1;
   }

   public boolean isBooming() {
      return this.isBooming;
   }

   public void recovery() {
      float var1 = (float)RegionUtilFunctions.getRegionWidth(Assets_fighter.rafaleStand) / 1.0F;
      float var2 = (float)RegionUtilFunctions.getRegionHeight(Assets_fighter.rafaleStand) / 1.0F;
      super.resetFighter(this.world, 320.0F, var2 / 2.0F, var1, var2);
      this.initRafale();
   }

   public void render(SpriteBatch var1) {
      Color var2 = var1.getColor();
      float var3 = var2.toFloatBits();
      if(this.isBooming()) {
         float var4;
         if((int)(this.getBoomDurationTime() / 0.05F) % 2 == 0) {
            var4 = 0.8F;
         } else {
            var4 = 0.3F;
         }

         var2.a = var4;
         var1.setColor(var2);
      }

      super.render(var1);
      var1.setColor(var3);
   }

   public void updateBoomer(float var1) {
      if(this.isBooming() && this.getBoomDurationTime() > 3.0F) {
         this.isBooming = false;
      }

   }
}
