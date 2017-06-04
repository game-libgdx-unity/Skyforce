package com.doodlegames.air.force.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.fighters.FighterWorldBoomer;
import com.doodlegames.air.force.game.EnemyLoader;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldAnimationPlayer;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.FighterBullet;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.resource.Assets_fighter;
import com.doodlegames.air.force.screen.GameScreen;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;
import com.doodlegames.air.force.utils.Settings;

import java.util.Iterator;
import java.util.List;

public class WorldBoomer implements FighterWorldBoomer, World.BulletHitEnemyListener {

   private WorldBoomer.BoomPoint[] points;


   public WorldBoomer(World var1) {
      WorldBoomer.BoomPoint[] var2 = new WorldBoomer.BoomPoint[]{new WorldBoomer.BoomPoint(var1), new WorldBoomer.BoomPoint(var1), new WorldBoomer.BoomPoint(var1), new WorldBoomer.BoomPoint(var1)};
      this.points = var2;
   }

   public void beginCheck() {
      WorldBoomer.BoomPoint[] var1 = this.points;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         var1[var3].beginCheck();
      }

   }

   public void boomStart() {
      Animation var1 = Assets_ShareInAllLevel.launchAnimation;
      WorldAnimationPlayer.addStageAnimation(var1, (float)GameScreen.Const.LAUNCHBUTTONX + (float)RegionUtilFunctions.getRegionWidth(var1) / 2.0F, (float)GameScreen.Const.LAUNCHBUTTONY + (float)RegionUtilFunctions.getRegionHeight(var1) / 2.0F);
      if(!this.isBooming()) {
         float var2 = this.points[0].world.fighter.getPositionX();
         float var3 = this.points[0].world.fighter.getPositionY();

         for(int var4 = 0; var4 < this.points.length; ++var4) {
            this.points[var4].boomeStartAt(var2, var3, (float)var4);
         }

         this.points[0].world.worldAudio.playNaziBigBoom();
      }
   }

   public void checking(Enemy var1, List<Rectangle> var2) {
      WorldBoomer.BoomPoint[] var3 = this.points;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         var3[var5].checking(var1, var2);
      }

   }

   public void endCheck() {
      WorldBoomer.BoomPoint[] var1 = this.points;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         var1[var3].endCheck();
      }

   }

   public boolean isBooming() {
      WorldBoomer.BoomPoint[] var1 = this.points;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         if(!var1[var3].boomingCompleted()) {
            return true;
         }
      }

      return false;
   }

   public void updateBoomer(float var1) {
      WorldBoomer.BoomPoint[] var2 = this.points;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         var2[var4].updateBoomer(var1);
      }

   }

   public static class BoomPoint implements World.BulletHitEnemyListener {

      private WorldAnimationPlayer.EnemyWorldAnimaionInfo animationInfo = new WorldAnimationPlayer.EnemyWorldAnimaionInfo() {
         public float getScaleX() {
            return BoomPoint.this.scale;
         }
         public float getScaleY() {
            return BoomPoint.this.scale;
         }
         public float getX() {
            return BoomPoint.this.boomX;
         }
         public float getY() {
            return BoomPoint.this.boomY;
         }
      };
      private boolean boomCompleted = false;
      float boomDurationTime;
      final float boomRadis = 309.0F;
      float boomTime = 0.8F;
      private float boomVelX = 0.0F;
      private float boomVelY = 0.0F;
      private float boomX;
      private float boomY;
      Enemy[] boomedEnemys;
      private float clearBulletRadis2 = 95481.0F;
      private float delayTime = 0.0F;
      private boolean isBooming;
      private float scale = 1.0F;
      public final World world;


      public BoomPoint(World var1) {
         this.world = var1;
         this.isBooming = false;
         this.boomCompleted = true;
         this.boomedEnemys = new Enemy[]{null, null, null, null, null};
         this.boomX = 0.0F;
         this.boomY = 0.0F;
         this.boomVelX = 0.0F;
         this.boomVelY = 0.0F;
         this.delayTime = 360000.0F;
      }

      private void boomEndDoSomething() {
         this.clearBoomedEnemyTable();
         this.delayTime = 360000.0F;
         this.boomCompleted = true;
      }

      private void boomStart() {
         if(!this.isBooming) {
            this.isBooming = true;
            this.boomDurationTime = 0.0F;
            this.putAnimation();
            this.world.worldAudio.playNaziBigBoom();
         }
      }

      private boolean checkExistEnemy(Enemy var1) {
         int var2 = 0;

         boolean var4;
         while(true) {
            int var3 = this.boomedEnemys.length;
            var4 = false;
            if(var2 >= var3) {
               break;
            }

            if(this.boomedEnemys[var2] == var1) {
               var4 = true;
               break;
            }

            Enemy var5 = this.boomedEnemys[var2];
            var4 = false;
            if(var5 == null) {
               break;
            }

            ++var2;
         }

         return var4;
      }

      private void clearBoomedEnemyTable() {
         for(int var1 = 0; var1 < this.boomedEnemys.length; ++var1) {
            this.boomedEnemys[var1] = null;
         }

      }

      private void putAnimation() {
         Animation var1 = Assets_fighter.getFighterBoomAnimation();
         WorldAnimationPlayer.addAWorldAnimation(var1, this.boomX, this.boomY, 1).setWorldAnimationInfo(this.animationInfo);
         this.boomTime = var1.animationTime;
      }

      private boolean registerEnemy(Enemy var1) {
         int var2 = 0;

         boolean var4;
         while(true) {
            if(var2 >= this.boomedEnemys.length) {
               Settings.i("this.boomedEnemys.length is too small." + this.boomedEnemys.length);
               return false;
            }

            if(this.boomedEnemys[var2] == null) {
               this.boomedEnemys[var2] = var1;
               var4 = true;
               break;
            }

            Enemy var3 = this.boomedEnemys[var2];
            var4 = false;
            if(var3 == var1) {
               break;
            }

            ++var2;
         }

         return var4;
      }

      public void beginCheck() {}

      public void boomeStartAt(float var1, float var2, float var3) {
         if(this.boomCompleted) {
            this.boomX = var1;
            this.boomY = var2;
            this.delayTime = var3;
            this.boomCompleted = false;
         }
      }

      public boolean boomingCompleted() {
         return this.boomCompleted;
      }

      public void checking(Enemy var1, List<Rectangle> var2) {
         if(this.isBooming) {
            float var3 = 309.0F / this.boomTime * this.boomDurationTime;
            float var4 = var3 * var3;
            if(!this.checkExistEnemy(var1)) {
               Iterator var9 = var2.iterator();

               while(var9.hasNext()) {
                  Rectangle var10 = (Rectangle)var9.next();
                  float var11 = var10.x + var10.width / 2.0F;
                  float var12 = var10.y + var10.height / 2.0F;
                  float var13 = var11 - this.boomX;
                  float var14 = var12 - this.boomY;
                  if(var13 * var13 + var14 * var14 < var4) {
                     short var15;
                     if(EnemyLoader.enemyIsBoss(var1)) {
                        if(this.registerEnemy(var1)) {
                           var15 = 10;
                        } else {
                           var15 = 0;
                        }
                     } else {
                        var15 = 5000;
                     }

                     float var16 = var11 + var10.width * (Clock.rand.nextFloat() - 0.5F);
                     float var17 = var12 + var10.height * (Clock.rand.nextFloat() - 0.5F);
                     Bullet var18 = FighterBullet.invisibleBullet.getABullet(this.world.fighter, this.world, var16, var17, Vector2.tmp.set(0.0F, Settings.backgroundVelocity.y));
                     var18.damageValue = var15;
                     Fighter.bullets.add(var18);
                  }
               }
            }

            Iterator var5 = Enemy.bullets.iterator();

            while(var5.hasNext()) {
               Bullet var6 = (Bullet)var5.next();
               if(var6.bulletType == 0) {
                  float var7 = var6.getPositionX() - this.boomX;
                  float var8 = var6.getPositionY() - this.boomY;
                  if(var7 * var7 + var8 * var8 < this.clearBulletRadis2) {
                     var6.makeUsed();
                  }
               }
            }
         }

      }

      public void endCheck() {}

      public float getBoomDurationTime() {
         return this.boomDurationTime;
      }

      public boolean isBooming() {
         return this.isBooming;
      }

      public void setBoomPosition(float var1, float var2) {
         this.boomX = var1;
         this.boomY = var2;
      }

      public void setBoomVel(float var1, float var2) {
         this.boomVelX = var1;
         this.boomVelY = var2;
      }

      public void setClearBulletRadis(float var1) {
         this.clearBulletRadis2 = var1 * var1;
      }

      public void setScale(float var1) {
         this.scale = var1;
      }

      public void updateBoomer(float var1) {
         if(this.delayTime >= 0.0F) {
            this.delayTime -= var1;
            if(this.delayTime < 0.0F) {
               this.boomStart();
            }
         } else {
            if(this.isBooming) {
               this.boomDurationTime += var1;
               this.boomX += var1 * this.boomVelX;
               this.boomY += var1 * this.boomVelY;
            }

            if(this.boomDurationTime > this.boomTime) {
               this.isBooming = false;
               this.boomDurationTime = this.boomTime;
               this.boomEndDoSomething();
               return;
            }
         }

      }
   }
}
