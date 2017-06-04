package com.doodlegames.air.force.enemy.level5;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.cannon.AlienCannon1;
import com.doodlegames.air.force.enemy.cannon.AlienCannon3L;
import com.doodlegames.air.force.enemy.cannon.AlienCannon3R;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.EnemyBullet2;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class AlienBattleShipB extends Enemy {

   private static final Vector2 ALIENCANNON11POSITION;
   private static final Vector2 ALIENCANNON12POSITION;
   private static final Vector2 ALIENCANNON3L1POSITION;
   private static final Vector2 ALIENCANNON3L2POSITION;
   private static final Vector2 ALIENCANNON3L3POSITION;
   private static final Vector2 ALIENCANNON3R1POSITION;
   private static final Vector2 ALIENCANNON3R2POSITION;
   private static final Vector2 ALIENCANNON3R3POSITION;
   public static final int DEFAULTHEALTHYDEGREE = 10;
   private static int HEIGHT = 0;
   private static final int PARTSNUM = 8;
   private static final float TIMEOFPLAYCRASHANIMATION = 15.0F;
   private static final int WIDTH;
   private static TextureAtlas.AtlasRegion enemyBroTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_battleship_b_broken");
   private static TextureAtlas.AtlasRegion enemyTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_battleship_b");
   private AlienCannon1 alienCannon11;
   private AlienCannon1 alienCannon12;
   private AlienCannon3L alienCannon3L1;
   private AlienCannon3L alienCannon3L2;
   private AlienCannon3L alienCannon3L3;
   private AlienCannon3R alienCannon3R1;
   private AlienCannon3R alienCannon3R2;
   private AlienCannon3R alienCannon3R3;
   private Clock clockPursue = new Clock(1.5F);
   private Clock clockShootingCanon1 = new Clock(1.0F, 3.0F, 0.2F, (byte)1);
   private Clock clockShootingCanon3 = new Clock(0.0F, 5.0F, 0.2F, (byte)1);
   private Enemy[] hitMap;
   private List<Rectangle> hitRectangles;


   static {
      int var0;
      if(enemyTextureRegion.rotate) {
         var0 = enemyTextureRegion.getRegionHeight();
      } else {
         var0 = enemyTextureRegion.getRegionWidth();
      }

      WIDTH = var0;
      int var1;
      if(enemyTextureRegion.rotate) {
         var1 = enemyTextureRegion.getRegionWidth();
      } else {
         var1 = enemyTextureRegion.getRegionHeight();
      }

      HEIGHT = var1;
      ALIENCANNON11POSITION = new Vector2((63.0F + (float)AlienCannon1.WIDTH / 2.0F) / 1.0F, (90.0F + (float)AlienCannon1.HEIGHT / 2.0F) / 1.0F);
      ALIENCANNON12POSITION = new Vector2((63.0F + (float)AlienCannon1.WIDTH / 2.0F) / 1.0F, (130.0F + (float)AlienCannon1.HEIGHT / 2.0F) / 1.0F);
      ALIENCANNON3L1POSITION = new Vector2((19.0F + (float)AlienCannon3L.WIDTH / 2.0F) / 1.0F, (196.0F + (float)AlienCannon3L.HEIGHT / 2.0F) / 1.0F);
      ALIENCANNON3L2POSITION = new Vector2((17.0F + (float)AlienCannon3L.WIDTH / 2.0F) / 1.0F, (246.0F + (float)AlienCannon3L.HEIGHT / 2.0F) / 1.0F);
      ALIENCANNON3L3POSITION = new Vector2((15.0F + (float)AlienCannon3L.WIDTH / 2.0F) / 1.0F, (300.0F + (float)AlienCannon3L.HEIGHT / 2.0F) / 1.0F);
      ALIENCANNON3R1POSITION = new Vector2((83.0F + (float)AlienCannon3R.WIDTH / 2.0F) / 1.0F, (196.0F + (float)AlienCannon3R.HEIGHT / 2.0F) / 1.0F);
      ALIENCANNON3R2POSITION = new Vector2((85.0F + (float)AlienCannon3R.WIDTH / 2.0F) / 1.0F, (246.0F + (float)AlienCannon3R.HEIGHT / 2.0F) / 1.0F);
      ALIENCANNON3R3POSITION = new Vector2((87.0F + (float)AlienCannon3R.WIDTH / 2.0F) / 1.0F, (300.0F + (float)AlienCannon3R.HEIGHT / 2.0F) / 1.0F);
   }

   public AlienBattleShipB(World var1, float var2, float var3) {
      super(var1, 10, var2, var3 + 0.5F, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.velocity.set(Settings.backgroundVelocity);
      this.hitMap = new Enemy[8];
      this.hitRectangles = new ArrayList(8);
      this.alienCannon11 = new AlienCannon1(var1, this.bounds.x + ALIENCANNON11POSITION.x, this.bounds.y + ALIENCANNON11POSITION.y);
      this.alienCannon12 = new AlienCannon1(var1, this.bounds.x + ALIENCANNON12POSITION.x, this.bounds.y + ALIENCANNON12POSITION.y);
      this.alienCannon3L1 = new AlienCannon3L(var1, this.bounds.x + ALIENCANNON3L1POSITION.x, this.bounds.y + ALIENCANNON3L1POSITION.y);
      this.alienCannon3L2 = new AlienCannon3L(var1, this.bounds.x + ALIENCANNON3L2POSITION.x, this.bounds.y + ALIENCANNON3L2POSITION.y);
      this.alienCannon3L3 = new AlienCannon3L(var1, this.bounds.x + ALIENCANNON3L3POSITION.x, this.bounds.y + ALIENCANNON3L3POSITION.y);
      this.alienCannon3R1 = new AlienCannon3R(var1, this.bounds.x + ALIENCANNON3R1POSITION.x, this.bounds.y + ALIENCANNON3R1POSITION.y);
      this.alienCannon3R2 = new AlienCannon3R(var1, this.bounds.x + ALIENCANNON3R2POSITION.x, this.bounds.y + ALIENCANNON3R2POSITION.y);
      this.alienCannon3R3 = new AlienCannon3R(var1, this.bounds.x + ALIENCANNON3R3POSITION.x, this.bounds.y + ALIENCANNON3R3POSITION.y);
      this.alienCannon11.setBulletActionVel(200.0F);
      this.alienCannon12.setBulletActionVel(200.0F);
      this.alienCannon3L1.setBulletActionVel(250.0F);
      this.alienCannon3L2.setBulletActionVel(250.0F);
      this.alienCannon3R1.setBulletActionVel(250.0F);
      this.alienCannon3R2.setBulletActionVel(250.0F);
      this.alienCannon11.setInitHealthyDgree(10);
      this.alienCannon12.setInitHealthyDgree(10);
      this.alienCannon3L1.setInitHealthyDgree(10);
      this.alienCannon3L2.setInitHealthyDgree(10);
      this.alienCannon3L3.setInitHealthyDgree(10);
      this.alienCannon3R1.setInitHealthyDgree(10);
      this.alienCannon3R2.setInitHealthyDgree(10);
      this.alienCannon3R3.setInitHealthyDgree(10);
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyBroTextureRegion;
   }

   public static void loadResource() {
      enemyTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_battleship_b");
      enemyBroTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_battleship_b_broken");
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
      boolean var1 = this.alienCannon11.isCrashed();
      int var2 = 0;
      if(!var1) {
         this.hitRectangles.add(this.alienCannon11.bounds);
         Enemy[] var27 = this.hitMap;
         int var28 = 0 + 1;
         var27[0] = this.alienCannon11;
         var2 = var28;
      }

      if(!this.alienCannon12.isCrashed()) {
         this.hitRectangles.add(this.alienCannon12.bounds);
         Enemy[] var24 = this.hitMap;
         int var25 = var2 + 1;
         var24[var2] = this.alienCannon12;
         var2 = var25;
      }

      if(!this.alienCannon3L1.isCrashed()) {
         this.hitRectangles.add(this.alienCannon3L1.bounds);
         Enemy[] var21 = this.hitMap;
         int var22 = var2 + 1;
         var21[var2] = this.alienCannon3L1;
         var2 = var22;
      }

      if(!this.alienCannon3L2.isCrashed()) {
         this.hitRectangles.add(this.alienCannon3L2.bounds);
         Enemy[] var18 = this.hitMap;
         int var19 = var2 + 1;
         var18[var2] = this.alienCannon3L2;
         var2 = var19;
      }

      if(!this.alienCannon3L3.isCrashed()) {
         this.hitRectangles.add(this.alienCannon3L3.bounds);
         Enemy[] var15 = this.hitMap;
         int var16 = var2 + 1;
         var15[var2] = this.alienCannon3L3;
         var2 = var16;
      }

      if(!this.alienCannon3R1.isCrashed()) {
         this.hitRectangles.add(this.alienCannon3R1.bounds);
         Enemy[] var12 = this.hitMap;
         int var13 = var2 + 1;
         var12[var2] = this.alienCannon3R1;
         var2 = var13;
      }

      if(!this.alienCannon3R2.isCrashed()) {
         this.hitRectangles.add(this.alienCannon3R2.bounds);
         Enemy[] var9 = this.hitMap;
         int var10 = var2 + 1;
         var9[var2] = this.alienCannon3R2;
         var2 = var10;
      }

      int var3;
      if(!this.alienCannon3R3.isCrashed()) {
         this.hitRectangles.add(this.alienCannon3R3.bounds);
         Enemy[] var7 = this.hitMap;
         var3 = var2 + 1;
         var7[var2] = this.alienCannon3R3;
      } else {
         var3 = var2;
      }

      while(var3 < 8) {
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
      return this.alienCannon11.isCrashed() && this.alienCannon12.isCrashed() && this.alienCannon3L1.isCrashed() && this.alienCannon3L2.isCrashed() && this.alienCannon3L3.isCrashed() && this.alienCannon3R1.isCrashed() && this.alienCannon3R2.isCrashed() && this.alienCannon3R3.isCrashed();
   }

   public void render(SpriteBatch var1) {
      super.render(var1);
      this.alienCannon11.render(var1);
      this.alienCannon12.render(var1);
      this.alienCannon3L1.render(var1);
      this.alienCannon3L2.render(var1);
      this.alienCannon3L3.render(var1);
      this.alienCannon3R1.render(var1);
      this.alienCannon3R2.render(var1);
      this.alienCannon3R3.render(var1);
   }

   public void update(float var1) {
      super.update(var1);
      if(this.clockPursue.isFired()) {
         float var2 = this.world.fighter.position.y - (this.alienCannon11.position.y + this.alienCannon12.position.y) / 2.0F;
         float var3 = this.world.fighter.position.x - (this.alienCannon11.position.x + this.alienCannon12.position.x) / 2.0F;
         if(var2 < 0.0F) {
            float var4 = (float)(90.0D + 57.2957763671875D * Math.atan2((double)var2, (double)var3));
            if(!this.alienCannon11.isCrashed()) {
               this.alienCannon11.rotateTo(var4);
            }

            if(!this.alienCannon12.isCrashed()) {
               this.alienCannon12.rotateTo(var4);
            }
         }
      }

      this.alienCannon11.update(var1);
      this.alienCannon12.update(var1);
      this.alienCannon3L1.update(var1);
      this.alienCannon3L2.update(var1);
      this.alienCannon3L3.update(var1);
      this.alienCannon3R1.update(var1);
      this.alienCannon3R2.update(var1);
      this.alienCannon3R3.update(var1);
   }

   public void updateDynamicParameter(float var1) {
      super.updateDynamicParameter(var1);
      this.alienCannon11.setPosition(this.bounds.x + ALIENCANNON11POSITION.x, this.bounds.y + ALIENCANNON11POSITION.y);
      this.alienCannon12.setPosition(this.bounds.x + ALIENCANNON12POSITION.x, this.bounds.y + ALIENCANNON12POSITION.y);
      this.alienCannon3L1.setPosition(this.bounds.x + ALIENCANNON3L1POSITION.x, this.bounds.y + ALIENCANNON3L1POSITION.y);
      this.alienCannon3L2.setPosition(this.bounds.x + ALIENCANNON3L2POSITION.x, this.bounds.y + ALIENCANNON3L2POSITION.y);
      this.alienCannon3L3.setPosition(this.bounds.x + ALIENCANNON3L3POSITION.x, this.bounds.y + ALIENCANNON3L3POSITION.y);
      this.alienCannon3R1.setPosition(this.bounds.x + ALIENCANNON3R1POSITION.x, this.bounds.y + ALIENCANNON3R1POSITION.y);
      this.alienCannon3R2.setPosition(this.bounds.x + ALIENCANNON3R2POSITION.x, this.bounds.y + ALIENCANNON3R2POSITION.y);
      this.alienCannon3R3.setPosition(this.bounds.x + ALIENCANNON3R3POSITION.x, this.bounds.y + ALIENCANNON3R3POSITION.y);
   }

   public void updateShooting(float var1) {
      if(this.clockShootingCanon1.isFired()) {
         this.alienCannon12.shooting();
         this.alienCannon11.shooting();
      }

      if(this.clockShootingCanon3.isFired()) {
         this.alienCannon3L1.shooting(EnemyBullet2.alienBulletGen, 0.0F);
         this.alienCannon3L2.shooting(EnemyBullet2.alienBulletGen, 1.0F);
         this.alienCannon3L3.shooting(EnemyBullet2.alienBulletGen, 2.0F);
         this.alienCannon3R1.shooting(EnemyBullet2.alienBulletGen, 0.0F);
         this.alienCannon3R2.shooting(EnemyBullet2.alienBulletGen, 1.0F);
         this.alienCannon3R3.shooting(EnemyBullet2.alienBulletGen, 2.0F);
      }

   }

   protected boolean usePathAngle() {
      return false;
   }
}
