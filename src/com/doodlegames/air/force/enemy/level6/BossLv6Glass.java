package com.doodlegames.air.force.enemy.level6;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.prop.PropBullet;
import com.doodlegames.air.force.resource.Assets_level6;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

public class BossLv6Glass extends Enemy {

   public static final int DEFAULTHEALTHYDEGREE = 100;
   private static final float FLYINGTIME = 0.5F;
   private static int HEIGHT = 0;
   private static final Vector2 LEFTPOSITION;
   private static final Vector2 RIGHTPOSITION;
   private static final float TIMEOFPLAYCRASHANIMATION = 0.1F;
   private static final int WIDTH;
   private static final TextureAtlas.AtlasRegion[] glassRegion;
   private final float RANGE = 13.0F;
   private int cannonState;
   public BossLv6Glass.GlassState glassState;
   private final Vector2 leftStart;
   private float middleX;
   private float middleY;
   private final GameObject owner;
   private final Vector2 rightStart;


   static {
      TextureAtlas.AtlasRegion[] var0 = new TextureAtlas.AtlasRegion[]{Assets_level6.atlas_Enemy.findRegion("lv_6_boss_glass", 1), Assets_level6.atlas_Enemy.findRegion("lv_6_boss_glass", 2), Assets_level6.atlas_Enemy.findRegion("lv_6_boss_glass", 3), Assets_level6.atlas_Enemy.findRegion("lv_6_boss_glass", 4), Assets_level6.atlas_Enemy.findRegion("lv_6_boss_glass", 5), Assets_level6.atlas_Enemy.findRegion("lv_6_boss_glass", 6)};
      glassRegion = var0;
      int var1;
      if(glassRegion[0].rotate) {
         var1 = glassRegion[0].getRegionHeight();
      } else {
         var1 = glassRegion[0].getRegionWidth();
      }

      WIDTH = var1;
      int var2;
      if(glassRegion[0].rotate) {
         var2 = glassRegion[0].getRegionWidth();
      } else {
         var2 = glassRegion[0].getRegionHeight();
      }

      HEIGHT = var2;
      LEFTPOSITION = new Vector2((-13.0F + (float)WIDTH / 2.0F) / 1.0F, (134.0F + (float)HEIGHT / 2.0F) / 1.0F);
      RIGHTPOSITION = new Vector2((98.0F + (float)WIDTH / 2.0F) / 1.0F, (134.0F + (float)HEIGHT / 2.0F) / 1.0F);
   }

   public BossLv6Glass(GameObject var1, World var2) {
      super(var2, 100, (float)(-WIDTH) / 2.0F / 1.0F, var1.bounds.y + LEFTPOSITION.y, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.owner = var1;
      this.enemyRegion = glassRegion[0];
      this.enemyBroRegion = null;
      this.cannonState = 1;
      this.glassState = BossLv6Glass.GlassState.Sleep;
      this.leftStart = new Vector2((float)(-WIDTH) / 2.0F / 1.0F, var1.bounds.y + LEFTPOSITION.y);
      this.rightStart = new Vector2(480.0F + (float)WIDTH / 2.0F / 1.0F, var1.bounds.y + RIGHTPOSITION.y);
   }

   private void check123FlyingIn() {
      if(this.velocity.x <= 0.0F) {
         Settings.e("123 FlyingIn invalid velocity: " + this.velocity.x + ". change it to 200 already.");
         this.velocity.x = 200.0F;
      }

      this.checkFarAwayFromScreen(0.0F, this.owner.bounds.y + LEFTPOSITION.y);
   }

   private void check123FlyingOut() {
      if(this.velocity.x >= 0.0F) {
         Settings.e("123FlyingOut invalid velocity: " + this.velocity.x + ". change it to -200 already.");
         this.velocity.x = -200.0F;
      }

      this.checkFarAwayFromScreen(0.0F, this.owner.bounds.y + LEFTPOSITION.y);
   }

   private void check456FlyingIn() {
      if(this.velocity.x >= 0.0F) {
         Settings.e("456 FLYINGIN invalid velocity: " + this.velocity.x + ". change it to -200 already.");
         this.velocity.x = -200.0F;
      }

      this.checkFarAwayFromScreen(480.0F, this.owner.bounds.y + RIGHTPOSITION.y);
   }

   private void check456FlyingOut() {
      if(this.velocity.x <= 0.0F) {
         Settings.e("456 FlyingOut invalid velocity: " + this.velocity.x + ". change it to 200 already.");
         this.velocity.x = 200.0F;
      }

      this.checkFarAwayFromScreen(480.0F, this.owner.bounds.y + RIGHTPOSITION.y);
   }

   private void floating() {
      float var1 = this.getPositionX() - this.middleX;
      float var2 = this.getPositionY() - this.middleY;
      if(Math.abs(var1) > 13.0F) {
         if(var1 * this.velocity.x > 0.0F) {
            this.accel.x = -30.0F * (var1 + this.velocity.x);
         } else {
            this.accel.x = -10.0F * var1;
         }
      } else {
         this.accel.x = this.velocity.x * this.world.rand.nextFloat();
         Vector2 var3 = this.accel;
         float var4;
         if(this.world.rand.nextBoolean()) {
            var4 = -this.accel.x;
         } else {
            var4 = this.accel.x;
         }

         var3.x = var4;
      }

      if(Math.abs(var2) > 13.0F) {
         if(var2 * this.velocity.y > 0.0F) {
            this.accel.y = -30.0F * (var2 + this.velocity.y);
         } else {
            this.accel.y = -10.0F * var2;
         }
      } else {
         this.accel.y = (5.0F + this.velocity.y) * this.world.rand.nextFloat();
         Vector2 var5 = this.accel;
         float var6;
         if(this.world.rand.nextBoolean()) {
            var6 = -this.accel.y;
         } else {
            var6 = this.accel.y;
         }

         var5.y = var6;
      }

      if(this.velocity.x < -800.0F) {
         this.velocity.x = -800.0F;
      }

      if(this.velocity.x > 800.0F) {
         this.velocity.x = 800.0F;
      }

      if(this.velocity.y < -800.0F) {
         this.velocity.y = -800.0F;
      }

      if(this.velocity.y > 800.0F) {
         this.velocity.y = 800.0F;
      }

   }

   public static void loadResource() {
      int var0 = glassRegion.length;

      for(int var1 = 0; var1 < var0; ++var1) {
         glassRegion[var1] = Assets_level6.atlas_Enemy.findRegion("lv_6_boss_glass", var1 + 1);
      }

   }

   private void stopGlass() {
      this.accel.set(0.0F, 0.0F);
      this.velocity.set(0.0F, 0.0F);
   }

   public void beHitByBullet(Bullet var1) {
      super.beHitByBullet(var1);
      if(this.isCrashed()) {
         this.glassState = BossLv6Glass.GlassState.Crashed;
         this.stopGlass();
      }

   }

   public boolean canBeHit() {
      return this.glassState == BossLv6Glass.GlassState.Floating;
   }

   public void checkFarAwayFromScreen(float var1, float var2) {
      float var3 = this.getPositionX();
      float var4 = this.getPositionY();
      if(var3 < -480.0F || var3 > 2.0F * 480.0F) {
         Settings.e("invalid positionX:" + var3 + " fix it to :" + var1);
         var3 = var1;
         this.setPosition(var1, var4);
      }

      if(var4 < 0.0F || var4 > 800.0F) {
         Settings.e("invalid positionY:" + var4 + " fix it to :" + var2);
         this.setPosition(var3, var2);
      }

   }

   public boolean flyingOut() {
      if(this.glassState == BossLv6Glass.GlassState.Floating) {
         float var1;
         switch(this.cannonState) {
         case 1:
         case 2:
         case 3:
            var1 = LEFTPOSITION.x + this.owner.bounds.x - this.leftStart.x;
            break;
         case 4:
         case 5:
         case 6:
            var1 = RIGHTPOSITION.x + this.owner.bounds.x - this.rightStart.x;
            break;
         default:
            var1 = 0.0F;
         }

         this.stopGlass();
         this.velocity.set(-var1 / 0.5F, 0.0F);
         this.setInitHealthyDgree(100);
         this.glassState = BossLv6Glass.GlassState.FlyingOut;
         if(Clock.rand.nextFloat() < 0.5F) {
            PropBullet.addABulletProp(this, this.world);
         }

         return true;
      } else if(this.glassState == BossLv6Glass.GlassState.Crashed && this.stateTime > this.getTimeOfCrash()) {
         this.reset(this.world, 100, this.leftStart.x, this.leftStart.y);
         return true;
      } else {
         return false;
      }
   }

   public int getCannonState() {
      return this.isCrashed()?7:this.cannonState;
   }

   public float getTimeOfCrash() {
      return 0.1F;
   }

   public void renderEnemy(SpriteBatch var1) {
      this.renderShadow(var1, shadowOffset.x, shadowOffset.y, shadowScale.x, shadowScale.y);
      super.renderEnemy(var1);
   }

   public void reset(World var1, int var2, float var3, float var4) {
      super.reset(var1, var2, var3, var4);
      this.enemyRegion = glassRegion[0];
      this.glassState = BossLv6Glass.GlassState.Sleep;
      this.stopGlass();
   }

   public boolean setState(int var1) {
      if(var1 > 0 && var1 <= glassRegion.length && this.glassState == BossLv6Glass.GlassState.Sleep) {
         this.cannonState = var1;
         this.enemyRegion = glassRegion[-1 + this.cannonState];
         this.glassState = BossLv6Glass.GlassState.FlyingIn;
         float var3;
         switch(this.cannonState) {
         case 1:
         case 2:
         case 3:
            this.leftStart.set((float)(-WIDTH) / 2.0F / 1.0F, this.owner.bounds.y + LEFTPOSITION.y);
            this.setPosition(this.leftStart.x, this.leftStart.y);
            this.middleX = this.owner.bounds.x + LEFTPOSITION.x;
            this.middleY = this.owner.bounds.y + LEFTPOSITION.y;
            var3 = LEFTPOSITION.x + this.owner.bounds.x - this.leftStart.x;
            break;
         case 4:
         case 5:
         case 6:
            this.rightStart.set(640.0F + (float)WIDTH / 2.0F / 1.0F, this.owner.bounds.y + RIGHTPOSITION.y);
            this.setPosition(this.rightStart.x, this.rightStart.y);
            this.middleX = this.owner.bounds.x + RIGHTPOSITION.x;
            this.middleY = this.owner.bounds.y + RIGHTPOSITION.y;
            var3 = RIGHTPOSITION.x + this.owner.bounds.x - this.rightStart.x;
            break;
         default:
            var3 = 0.0F;
         }

         this.stopGlass();
         this.velocity.set(var3 / 0.5F, 0.0F);
         return true;
      } else {
         return false;
      }
   }

   public void update(final float n) {

       super.update(n);

       if (!this.isCrashed()) {

           switch (this.glassState) {

               case FlyingIn: {

                   if (this.cannonState == 1 || this.cannonState == 2 || this.cannonState == 3) {

                       this.check123FlyingIn();

                       if (this.getPositionX() > this.owner.bounds.x + BossLv6Glass.LEFTPOSITION.x) {

                           this.glassState = GlassState.Floating;

                       }

                   }

                   if (this.cannonState != 4 && this.cannonState != 5 && this.cannonState != 6) {

                       break;

                   }

                   this.check456FlyingIn();

                   if (this.getPositionX() < this.owner.bounds.x + BossLv6Glass.RIGHTPOSITION.x) {

                       this.glassState = GlassState.Floating;

                       return;

                   }

                   break;

               }

               case Floating: {

                   this.floating();

                   return;

               }

               case FlyingOut: {

                   if (this.cannonState == 1 || this.cannonState == 2 || this.cannonState == 3) {

                       this.check123FlyingOut();

                       if (this.getPositionX() < this.leftStart.x) {

                           this.setPosition(this.leftStart.x, this.leftStart.y);

                           this.glassState = GlassState.Sleep;

                           this.stopGlass();

                       }

                   }

                   if (this.cannonState != 4 && this.cannonState != 5 && this.cannonState != 6) {

                       break;

                   }

                   this.check456FlyingOut();

                   if (this.getPositionX() > this.rightStart.x) {

                       this.setPosition(this.rightStart.x, this.rightStart.y);

                       this.glassState = GlassState.Sleep;

                       this.stopGlass();

                       return;

                   }

                   break;

               }

               case Crashed:

               case Sleep: {

                   this.stopGlass();

                   return;

               }

               default: {

                   return;

               }

           }

       }

   }

   public void updateShooting(float var1) {}

   public static enum GlassState {

      Crashed,
      Floating,
      FlyingIn,
      FlyingOut,
      Sleep, 
   }
}
