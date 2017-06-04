package com.doodlegames.air.force.enemy.level5;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.cannon.AlienCannon1;
import com.doodlegames.air.force.enemy.cannon.AlienCannon2;
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

public class AlienBattleShipA extends Enemy {

   private static final Vector2 ALIENCANNON1POSITION;
   private static final Vector2 ALIENCANNON2POSITION;
   private static final Vector2 ALIENCANNON3LPOSITION;
   private static final Vector2 ALIENCANNON3RPOSITION;
   public static final int DEFAULTHEALTHYDEGREE = 10;
   private static  int HEIGHT = 0;
   private static final int PARTSNUM = 4;
   private static final float TIMEOFPLAYCRASHANIMATION = 15.0F;
   private static final int WIDTH;
   private static TextureAtlas.AtlasRegion enemyBroTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_battleship_a_broken");
   private static TextureAtlas.AtlasRegion enemyTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_battleship_a");
   private AlienCannon1 alienCannon1;
   private AlienCannon2 alienCannon2;
   private AlienCannon3L alienCannon3L;
   private AlienCannon3R alienCannon3R;
   private Clock clockPursue = new Clock(1.5F);
   private Clock clockShootingCanon1 = new Clock(4.0F, 5.0F, 0.2F, (byte)1);
   private Clock clockShootingCanon2 = new Clock(2.5F, 5.0F, 0.2F, (byte)1);
   private Clock clockShootingCanon3 = new Clock(1.0F, 5.0F, 0.2F, (byte)1);
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
      ALIENCANNON1POSITION = new Vector2((66.0F + (float)AlienCannon1.WIDTH / 2.0F) / 1.0F, (179.0F + (float)AlienCannon1.HEIGHT / 2.0F) / 1.0F);
      ALIENCANNON2POSITION = new Vector2((63.0F + (float)AlienCannon2.WIDTH / 2.0F) / 1.0F, (109.0F + (float)AlienCannon2.HEIGHT / 2.0F) / 1.0F);
      ALIENCANNON3LPOSITION = new Vector2((22.0F + (float)AlienCannon3L.WIDTH / 2.0F) / 1.0F, (280.0F + (float)AlienCannon3L.HEIGHT / 2.0F) / 1.0F);
      ALIENCANNON3RPOSITION = new Vector2((86.0F + (float)AlienCannon3R.WIDTH / 2.0F) / 1.0F, (280.0F + (float)AlienCannon3R.HEIGHT / 2.0F) / 1.0F);
   }

   public AlienBattleShipA(World var1, float var2, float var3) {
      super(var1, 10, var2, var3 + 0.5F, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.velocity.set(Settings.backgroundVelocity);
      this.hitMap = new Enemy[4];
      this.hitRectangles = new ArrayList(4);
      this.alienCannon1 = new AlienCannon1(var1, this.bounds.x + ALIENCANNON1POSITION.x, this.bounds.y + ALIENCANNON1POSITION.y);
      this.alienCannon2 = new AlienCannon2(var1, this.bounds.x + ALIENCANNON2POSITION.x, this.bounds.y + ALIENCANNON2POSITION.y);
      this.alienCannon3L = new AlienCannon3L(var1, this.bounds.x + ALIENCANNON3LPOSITION.x, this.bounds.y + ALIENCANNON3LPOSITION.y);
      this.alienCannon3R = new AlienCannon3R(var1, this.bounds.x + ALIENCANNON3RPOSITION.x, this.bounds.y + ALIENCANNON3RPOSITION.y);
      this.alienCannon1.setBulletActionVel(200.0F);
      this.alienCannon2.setBulletActionVel(250.0F);
      this.alienCannon3L.setBulletActionVel(200.0F);
      this.alienCannon3R.setBulletActionVel(200.0F);
      this.alienCannon1.setInitHealthyDgree(10);
      this.alienCannon2.setInitHealthyDgree(10);
      this.alienCannon3L.setInitHealthyDgree(10);
      this.alienCannon3R.setInitHealthyDgree(10);
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyBroTextureRegion;
   }

   public static void loadResource() {
      enemyTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_battleship_a");
      enemyBroTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_battleship_a_broken");
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
      boolean var1 = this.alienCannon1.isCrashed();
      int var2 = 0;
      if(!var1) {
         this.hitRectangles.add(this.alienCannon1.bounds);
         Enemy[] var15 = this.hitMap;
         int var16 = 0 + 1;
         var15[0] = this.alienCannon1;
         var2 = var16;
      }

      if(!this.alienCannon2.isCrashed()) {
         this.hitRectangles.add(this.alienCannon2.bounds);
         Enemy[] var12 = this.hitMap;
         int var13 = var2 + 1;
         var12[var2] = this.alienCannon2;
         var2 = var13;
      }

      if(!this.alienCannon3L.isCrashed()) {
         this.hitRectangles.add(this.alienCannon3L.bounds);
         Enemy[] var9 = this.hitMap;
         int var10 = var2 + 1;
         var9[var2] = this.alienCannon3L;
         var2 = var10;
      }

      int var3;
      if(!this.alienCannon3R.isCrashed()) {
         this.hitRectangles.add(this.alienCannon3R.bounds);
         Enemy[] var7 = this.hitMap;
         var3 = var2 + 1;
         var7[var2] = this.alienCannon3R;
      } else {
         var3 = var2;
      }

      while(var3 < 4) {
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
      return this.alienCannon1.isCrashed() && this.alienCannon2.isCrashed() && this.alienCannon3L.isCrashed() && this.alienCannon3R.isCrashed();
   }

   public void render(SpriteBatch var1) {
      super.render(var1);
      this.alienCannon1.render(var1);
      this.alienCannon2.render(var1);
      this.alienCannon3L.render(var1);
      this.alienCannon3R.render(var1);
   }

   public void update(float var1) {
      super.update(var1);
      if(this.clockPursue.isFired()) {
         float var2 = this.world.fighter.getPositionY() - (this.alienCannon1.getPositionY() + this.alienCannon2.getPositionY()) / 2.0F;
         float var3 = this.world.fighter.getPositionX() - (this.alienCannon1.getPositionX() + this.alienCannon2.getPositionX()) / 2.0F;
         if(var2 < 0.0F) {
            float var4 = 90.0F + (float)(57.2957763671875D * Math.atan2((double)var2, (double)var3));
            if(!this.alienCannon2.isCrashed()) {
               this.alienCannon2.rotateTo(var4);
            }

            if(!this.alienCannon1.isCrashed()) {
               this.alienCannon1.rotateTo(var4);
            }
         }
      }

      this.alienCannon1.update(var1);
      this.alienCannon2.update(var1);
      this.alienCannon3L.update(var1);
      this.alienCannon3R.update(var1);
   }

   public void updateDynamicParameter(float var1) {
      super.updateDynamicParameter(var1);
      this.alienCannon1.setPosition(this.bounds.x + ALIENCANNON1POSITION.x, this.bounds.y + ALIENCANNON1POSITION.y);
      this.alienCannon2.setPosition(this.bounds.x + ALIENCANNON2POSITION.x, this.bounds.y + ALIENCANNON2POSITION.y);
      this.alienCannon3L.setPosition(this.bounds.x + ALIENCANNON3LPOSITION.x, this.bounds.y + ALIENCANNON3LPOSITION.y);
      this.alienCannon3R.setPosition(this.bounds.x + ALIENCANNON3RPOSITION.x, this.bounds.y + ALIENCANNON3RPOSITION.y);
   }

   public void updateShooting(float var1) {
      if(this.clockShootingCanon1.isFired()) {
         this.alienCannon1.shooting();
      }

      if(this.clockShootingCanon2.isFired()) {
         this.alienCannon2.shooting();
      }

      if(this.clockShootingCanon3.isFired()) {
         this.alienCannon3L.shooting(EnemyBullet2.alienBulletGen, 0.0F);
         this.alienCannon3R.shooting(EnemyBullet2.alienBulletGen, 0.0F);
      }

   }

   protected boolean usePathAngle() {
      return false;
   }
}
