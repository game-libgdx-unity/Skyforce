package com.doodlegames.air.force.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.path.AbstractEnemyPath;
import com.doodlegames.air.force.game.path.EnemyLineMove;
import com.doodlegames.air.force.game.path.EnemyMutiMove;
import com.doodlegames.air.force.resource.Assets_level1;
import com.doodlegames.air.force.resource.Assets_level2;
import com.doodlegames.air.force.resource.Assets_level3;
import com.doodlegames.air.force.resource.Assets_level4;
import com.doodlegames.air.force.resource.Assets_level5;
import com.doodlegames.air.force.resource.Assets_level6;
import com.doodlegames.air.force.resource.Assets_level7;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Settings;

import java.util.List;

public class EnemyProXY extends Enemy {

   public static final EnemyLineMove.EnemyBGMove BGMARK = new EnemyLineMove.EnemyBGMove((Enemy)null, 0.0F, 0.0F, "");
   final float activeY;
   Enemy enemy;
   List<Enemy> enemySet;
   boolean isActive = false;
   public String pathName;
   boolean shouldRemove = false;
   public boolean sleepRending = false;
   float sleepY;


   public EnemyProXY(World var1, float var2, Enemy var3, List<Enemy> var4) {
      super(var1, 10, var3.getPositionX(), var3.getPositionY(), var3.bounds.width, var3.bounds.height);
      this.activeY = var2;
      this.enemy = var3;
      this.sleepY = var3.bounds.y;
      this.enemySet = var4;
   }

   public static Enemy addAEnemy(Enemy var0, float var1, List<Enemy> var2, boolean var3) {
      EnemyProXY var4 = new EnemyProXY(var0.world, var1, var0, var2);
      var2.add(var4);
      var4.sleepRending = true;
      return var4;
   }

   private static void setPathProcessor(String var0, Enemy var1) {
      int var2 = var1.world.level;
      EnemyMutiMove.IPathInfoSet var3 = null;
      switch(var2) {
      case 1:
         var3 = Assets_level1.pathInfoSet;
         break;
      case 2:
         var3 = Assets_level2.pathInfoSet;
         break;
      case 3:
         var3 = Assets_level3.pathInfoSet;
         break;
      case 4:
         var3 = Assets_level4.pathInfoSet;
         break;
      case 5:
         var3 = Assets_level5.pathInfoSet;
         break;
      case 6:
         var3 = Assets_level6.pathInfoSet;
         break;
      case 7:
         var3 = Assets_level7.pathInfoSet;
      }

      if(var3 != null && var0 != null) {
         AbstractEnemyPath.PathInfo var4 = var3.getPathInfo(var0);
         if(var4 == null) {
            var1.velocity.set(0.0F, 0.0F);
            var1.setMoveProcessor(BGMARK);
            return;
         }

         var1.setMoveProcessor(var4.getMoveProcessor(var1));
      }

   }

   public void beHitByBullet(Bullet var1) {}

   public void beHitByBullet(Bullet var1, int var2) {}

   public boolean canBeHit() {
      return false;
   }

   public void enemyRemoved() {}

   public int getBulletCount() {
      return 0;
   }

   public Animation getCrashBoom() {
      return null;
   }

   public int getHealthyDgree() {
      return 1;
   }

   public Animation getHitAnimation(Bullet var1) {
      return null;
   }

   public List<Rectangle> getHitRectangle() {
      return Enemy.EMPTYRECTANGELS;
   }

   public List<Rectangle> getHitRectangle(int var1) {
      return Enemy.EMPTYRECTANGELS;
   }

   public Animation getShatterAniamtion() {
      return null;
   }

   public float getTimeOfCrash() {
      return 0.0F;
   }

   public boolean isActive() {
      if(this.isActive) {
         return true;
      } else {
         if(this.sleepY < this.activeY) {
            this.isActive = true;
            this.enemy.enemyDropped(this);
         } else if(800.0F + this.world.getViewPosition() >= 6400.0F) {
            if(this.sleepY - this.activeY < 0.9F) {
               float var1 = this.sleepY - 0.9F;
               Settings.i("sleepY:" + this.sleepY + " this.activeY " + this.activeY + " fix sleepY to :" + var1);
               this.sleepY = var1;
            } else {
               Settings.e("enemy that can not be actived appeared....." + this.enemy.toString());
               Settings.e("this.sleepY:" + this.sleepY);
               Settings.e("this.activeY" + this.activeY);
               Settings.flurryLog("EnemyNotActive", "enemy", this.enemy.toString() + "", "sleepY", this.sleepY + "", "activeY", this.activeY + "");
               this.sleepY = this.activeY - 0.01F;
            }
         }

         return this.isActive;
      }
   }

   public boolean isCrashed() {
      return this.shouldRemove;
   }

   public void makeCrash() {}

   public void render(SpriteBatch var1) {
      if(this.sleepRending) {
         this.updateEnemySleepPosition();
         this.enemy.render(var1);
      }

   }

   public void renderShadow(SpriteBatch var1) {
      if((this.activeY >= 800.0F || this.sleepRending) && this.enemy.bounds.y + DynamicGameObject.shadowOffset.y < 800.0F) {
         this.enemy.renderShadow(var1);
      }

   }

   public void reset(World var1, int var2, float var3, float var4) {}

   public void setBoundsHeight(float var1) {
      super.setBoundsHeight(var1);
      this.sleepY = this.bounds.y;
   }

   public void setBroRegion(TextureAtlas.AtlasRegion var1) {}

   public void setBulletCount(int var1) {}

   public void setInitHealthyDgree(int var1) {}

   public void setSleepPosition(float var1, float var2) {
      throw new IllegalArgumentException("enemyProXY\'s setSleepPosition function should not be invoked.");
   }

   public void update(float var1) {
      this.updateEnemySleepPosition();
      super.excuteEnemyActions(var1);
      if(this.isActive()) {
         if(this.enemySet != null) {
            int var2 = this.enemySet.indexOf(this);
            if(var2 >= 0 && var2 < this.enemySet.size()) {
               this.enemySet.set(var2, this.enemy);
               setPathProcessor(this.pathName, this.enemy);
               this.world.fireEnemyDroppedEvent(this.enemy, this);
            } else {
               Settings.w("enemySet error!");
            }
         } else {
            Settings.w("enemySet remove should not be null.");
            this.shouldRemove = true;
         }
      } else {
         this.sleepY += var1 * Settings.backgroundVelocity.y;
         this.setBounds(this.bounds.x, this.sleepY, this.bounds.width, this.bounds.height);
      }
   }

   public void updateEnemySleepPosition() {
      float var1 = this.enemy.getPositionX();
      float var2 = this.sleepY + this.bounds.height / 2.0F;
      this.enemy.setSleepPosition(var1, var2);
   }

   public void updateShooting(float var1) {}

   public static class SmallBossStop extends EnemyTools.EnemyAction {

      public boolean act(float var1) {
         Enemy var2 = this.getEnemy();
         if(var2 instanceof EnemyProXY) {
            EnemyProXY var3 = (EnemyProXY)var2;
            if(var3.sleepY < var3.activeY && Settings.backgroundVelocity.y != 0.0F) {
               Settings.backgroundVelocity.y = 0.0F;
               return true;
            } else {
               return false;
            }
         } else {
            Settings.e("can not add small stop action to a enemy which is not a enemyProXY");
            return true;
         }
      }
   }
}
