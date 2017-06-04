package com.doodlegames.air.force.fighters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector2Utils;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.fighters.gun.J20BlueGun;
import com.doodlegames.air.force.fighters.gun.J20RedGun;
import com.doodlegames.air.force.fighters.gun.J20YellowGun;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.FighterPursueBullet1;
import com.doodlegames.air.force.resource.Assets_fighter;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;

import java.util.List;

public class J20 extends Fighter {

   public static final int DEFAULTLIVES = 0;
   private static final int InitHealthy = 1;
   public static final Vector2[] SHOOTPOINT;
   private static final float timeOfPlayCrashedAnimation = 0.3F;
   private float boomTimeStamp = -1.0F;
   private final float boomtTime = 3.0F;
   private boolean isBooming;
   private float shootTimeStamp = 0.0F;


   static {
      Vector2[] var0 = new Vector2[]{new Vector2(2.0F, 37.0F), new Vector2(9.0F, 46.0F), new Vector2(15.0F, 57.0F), new Vector2(21.0F, 67.0F), new Vector2(39.0F, 67.0F), new Vector2(45.0F, 57.0F), new Vector2(51.0F, 46.0F), new Vector2(58.0F, 37.0F)};
      SHOOTPOINT = var0;
   }

   public J20(World var1, float var2, float var3) {
      super(var1, var2, var3, (float)RegionUtilFunctions.getRegionWidth(Assets_fighter.j20Stand) / 1.0F, (float)RegionUtilFunctions.getRegionHeight(Assets_fighter.j20Stand) / 1.0F);
      this.initJ20();
      this.avalibleGuns[0] = new J20RedGun(this);
      this.avalibleGuns[1] = new J20YellowGun(this);
      this.avalibleGuns[2] = new J20BlueGun(this);
      this.currentGun = this.avalibleGuns[0];
      this.currentGun.reset();
      if(!this.loadFighter()) {
         ;
      }

      this.constructBoomer();
   }

   private void boomEndDoSomething() {}

   private void boomStartDoSomething() {
      this.shootTimeStamp = Clock.getCurrentTime();
   }

   private void constructBoomer() {
      this.isBooming = false;
   }

   private float getBoomDurationTime() {
      return Clock.getCurrentTime(this.boomTimeStamp);
   }

   private void putMissArray() {
      float var1 = this.getPositionX();
      float var2 = this.getPositionY();
      float var3 = this.bounds.width / 2.0F;
      Vector2 var4 = Vector2Utils.tmp.set(0.0F, 0.0F);
      float[] var5 = new float[]{-20.0F, -60.0F, -100.0F, -60.0F, -20.0F};
      float[] var6 = new float[]{-150.0F, -100.0F, 0.0F, 100.0F, 150.0F};

      for(int var7 = 0; var7 < var5.length; ++var7) {
         Bullet var13 = FighterPursueBullet1.j20BoomBulletGen.getABullet(this, this.world, var1 - var3, var2, var4);
         ((FighterPursueBullet1)var13).setOffsetVel(var5[var7], var6[var7], 1000.0F, var7);
         Fighter.bullets.add(var13);
      }

      float[] var8 = new float[]{20.0F, 60.0F, 100.0F, 60.0F, 20.0F};
      float[] var9 = new float[]{-150.0F, -100.0F, 0.0F, 100.0F, 150.0F};

      for(int var10 = 0; var10 < var8.length; ++var10) {
         Bullet var11 = FighterPursueBullet1.j20BoomBulletGen.getABullet(this, this.world, var1 + var3, var2, var4);
         ((FighterPursueBullet1)var11).setOffsetVel(var8[var10], var9[var10], 1000.0F, var10);
         Fighter.bullets.add(var11);
      }

   }

   public void beginCheck() {
      super.beginCheck();
   }

   public void boomStart() {
      if(!this.isBooming()) {
         this.isBooming = true;
         this.boomTimeStamp = Clock.getCurrentTime();
         this.world.getParentScreen().playBoomLaunchAnimation();
         this.boomStartDoSomething();
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

   public void initJ20() {
      this.fightStand = Assets_fighter.j20Stand;
      this.fightLeft = Assets_fighter.j20FlyingToLeft;
      this.fightRight = Assets_fighter.j20FlyingToRight;
      this.healthyDegree = 1;
   }

   protected void initNearestGoalFinder() {
      super.initNearestGoalFinder();
      super.setUpdateTimeSpan(1.0F);
   }

   public boolean isBooming() {
      return this.isBooming;
   }

   public void recovery() {
      float var1 = (float)RegionUtilFunctions.getRegionWidth(Assets_fighter.j20Stand) / 1.0F;
      float var2 = (float)RegionUtilFunctions.getRegionHeight(Assets_fighter.j20Stand) / 1.0F;
      super.resetFighter(this.world, 320.0F, var2 / 2.0F, var1, var2);
      this.initJ20();
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
      if(this.isBooming()) {
         float var2 = this.getBoomDurationTime();
         this.getClass();
         if(var2 >= 3.0F) {
            this.isBooming = false;
            this.boomEndDoSomething();
            return;
         }

         if(Clock.getCurrentTime(this.shootTimeStamp) > 0.0F) {
            this.putMissArray();
            this.shootTimeStamp = 0.5F + Clock.getCurrentTime();
         }
      }

   }
}
