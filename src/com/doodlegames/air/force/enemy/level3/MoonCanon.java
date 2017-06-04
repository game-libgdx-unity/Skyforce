package com.doodlegames.air.force.enemy.level3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.cannon.Cannon1;
import com.doodlegames.air.force.enemy.cannon.Cannon2;
import com.doodlegames.air.force.enemy.cannon.CommonCannon;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.resource.Assets_level3;
import com.doodlegames.air.force.utils.Clock;

import java.util.ArrayList;
import java.util.List;

public class MoonCanon extends Enemy {

   public static final Vector2 CANNON1LEFTPOSITION;
   public static final Vector2 CANNON1RIGHTPOSITION;
   public static final Vector2 CANNON2LEFTPOSITION;
   public static final Vector2 CANNON2RIGHTPOSITION;
   public static final int DEFAULTHEALTHYDEGREE = 10;
   private static final int PARTSNUM = 2;
   private static final float TIMEOFPLAYCRASHANIMATION = 15.0F;
   private static TextureAtlas.AtlasRegion enemyTextureRegion = Assets_level3.atlas_Enemy.findRegion("nazi_moon_canon");
   public static int height;
   public static int width;
   public Cannon1 cannon1;
   public Cannon2 cannon2;
   private Vector2 canon1Position;
   private Vector2 canon2Position;
   private Clock clockPursue1;
   private Clock clockPursue2;
   private Clock clockShootingCanon1;
   private Clock clockShootingCanon2;
   private Enemy[] hitMap;
   private List<Rectangle> hitRectangles;


   static {
      int var0;
      if(enemyTextureRegion.rotate) {
         var0 = enemyTextureRegion.getRegionHeight();
      } else {
         var0 = enemyTextureRegion.getRegionWidth();
      }

      width = var0;
      int var1;
      if(enemyTextureRegion.rotate) {
         var1 = enemyTextureRegion.getRegionWidth();
      } else {
         var1 = enemyTextureRegion.getRegionHeight();
      }

      height = var1;
      CANNON1LEFTPOSITION = new Vector2((8.0F + (float)Cannon1.REGIONWIDTH / 2.0F) / 1.0F, (9.0F + (float)Cannon1.REGIONHEIGHT / 2.0F) / 1.0F);
      CANNON1RIGHTPOSITION = new Vector2((47.0F + (float)Cannon1.REGIONWIDTH / 2.0F) / 1.0F, (9.0F + (float)Cannon1.REGIONHEIGHT / 2.0F) / 1.0F);
      CANNON2LEFTPOSITION = new Vector2((7.0F + (float)Cannon2.WIDTH / 2.0F) / 1.0F, (9.0F + (float)Cannon2.HEIGHT / 2.0F) / 1.0F);
      CANNON2RIGHTPOSITION = new Vector2((46.0F + (float)Cannon2.WIDTH / 2.0F) / 1.0F, (9.0F + (float)Cannon2.HEIGHT / 2.0F) / 1.0F);
   }

   public MoonCanon(World var1, float var2, float var3) {
      this(var1, var2, var3, var1.rand.nextBoolean());
   }

   public MoonCanon(World var1, float var2, float var3, boolean var4) {
      super(var1, 10, var2, var3, (float)width / 1.0F, (float)height / 1.0F);
      this.hitMap = new Enemy[2];
      this.hitRectangles = new ArrayList(2);
      this.canon1Position = new Vector2();
      this.canon2Position = new Vector2();
      if(var4) {
         this.canon1Position.set(CANNON1LEFTPOSITION);
         this.canon2Position.set(CANNON2RIGHTPOSITION);
      } else {
         this.canon1Position.set(CANNON1RIGHTPOSITION);
         this.canon2Position.set(CANNON2LEFTPOSITION);
      }

      this.cannon1 = new Cannon1(var1, this.bounds.x + this.canon1Position.x, this.bounds.y + this.canon1Position.y);
      this.cannon2 = new Cannon2(var1, this.bounds.x + this.canon2Position.x, this.bounds.y + this.canon2Position.y);
      this.cannon1.setInitHealthyDgree(5);
      this.cannon2.setInitHealthyDgree(5);
      this.cannon1.setThisVelBullet(0.0F, -150.0F);
      this.cannon2.setThisVelBullet(0.0F, -150.0F);
      this.clockPursue1 = new Clock(1.5F);
      this.clockPursue2 = new Clock(1.5F);
      this.clockShootingCanon1 = new Clock(9.0F, CommonCannon.clockDelay(-150.0F), (byte)2);
      this.clockShootingCanon2 = new Clock(9.0F, CommonCannon.clockDelay(-150.0F), (byte)2);
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyTextureRegion;
   }

   public static void loadResource() {
      enemyTextureRegion = Assets_level3.atlas_Enemy.findRegion("nazi_moon_canon");
   }

   public void beHitByBullet(Bullet var1, int var2) {
      if(1 != this.state) {
         this.hitMap[var2].beHitByBullet(var1);
         int var3 = var1.damageValue;
         var1.damageValue = 0;
         super.beHitByBullet(var1);
         var1.damageValue = var3;
      }
   }

   public List<Rectangle> getHitRectangle() {
      this.hitRectangles.clear();
      boolean var1 = this.cannon1.isCrashed();
      int var2 = 0;
      if(!var1) {
         this.hitRectangles.add(this.cannon1.bounds);
         Enemy[] var9 = this.hitMap;
         int var10 = 0 + 1;
         var9[0] = this.cannon1;
         var2 = var10;
      }

      int var3;
      if(!this.cannon2.isCrashed()) {
         this.hitRectangles.add(this.cannon2.bounds);
         Enemy[] var7 = this.hitMap;
         var3 = var2 + 1;
         var7[var2] = this.cannon2;
      } else {
         var3 = var2;
      }

      while(var3 < 2) {
         Enemy[] var4 = this.hitMap;
         int var5 = var3 + 1;
         var4[var3] = null;
         var3 = var5;
      }

      return this.hitRectangles;
   }

   public float getTimeOfCrash() {
      return 15.0F;
   }

   public boolean isCrashed() {
      boolean var1 = this.cannon1.isCrashed();
      boolean var2 = false;
      if(var1) {
         boolean var3 = this.cannon2.isCrashed();
         var2 = false;
         if(var3) {
            this.setInitHealthyDgree(0);
            var2 = true;
         }
      }

      return var2;
   }

   public void render(SpriteBatch var1) {
      super.render(var1);
      this.cannon1.render(var1);
      this.cannon2.render(var1);
   }

   public void setSleepPosition(float var1, float var2) {
      super.setSleepPosition(var1, var2);
      this.cannon1.setSleepPosition(this.bounds.x + this.canon1Position.x, this.bounds.y + this.canon1Position.y);
      this.cannon2.setSleepPosition(this.bounds.x + this.canon2Position.x, this.bounds.y + this.canon2Position.y);
   }

   public void update(float var1) {
      super.update(var1);
      if(this.clockPursue1.isFired()) {
         float var5 = this.world.fighter.getPositionY() - this.cannon1.getPositionY();
         float var6 = this.world.fighter.getPositionX() - this.cannon1.getPositionX();
         if(var5 < 0.0F) {
            float var7 = 90.0F + (float)(57.2957763671875D * Math.atan2((double)var5, (double)var6));
            if(!this.cannon1.isCrashed()) {
               this.cannon1.rotateTo(var7);
            }
         }
      }

      if(this.clockPursue2.isFired()) {
         float var2 = this.world.fighter.getPositionY() - this.cannon2.getPositionY();
         float var3 = this.world.fighter.getPositionX() - this.cannon2.getPositionX();
         if(var2 < 0.0F) {
            float var4 = 90.0F + (float)(57.2957763671875D * Math.atan2((double)var2, (double)var3));
            if(!this.cannon2.isCrashed()) {
               this.cannon2.rotateTo(var4);
            }
         }
      }

      this.cannon1.update(var1);
      this.cannon2.update(var1);
   }

   public void updateDynamicParameter(float var1) {
      super.updateDynamicParameter(var1);
      this.cannon1.setPosition(this.bounds.x + this.canon1Position.x, this.bounds.y + this.canon1Position.y);
      this.cannon2.setPosition(this.bounds.x + this.canon2Position.x, this.bounds.y + this.canon2Position.y);
   }

   public void updateShooting(float var1) {
      if(this.clockShootingCanon1.isFired()) {
         this.cannon1.shooting();
      }

      if(this.clockShootingCanon2.isFired()) {
         this.cannon2.shooting();
      }

   }
}
