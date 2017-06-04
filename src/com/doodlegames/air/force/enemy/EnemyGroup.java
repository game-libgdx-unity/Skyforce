package com.doodlegames.air.force.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnemyGroup extends Enemy {

   public static final int DEFAULTHEALTHYDEGREE = 10;
   private static final int ENEMYMAX = 20;
   private static final float TIMEOFPLAYCRASHANIMATION = 4.0F;
   private ArrayList<Enemy> enemyList;
   private boolean enemyListIsOk;
   private final int enemyNum;
   private EnemyGroup.HitMapItem[] hitMap;
   protected ArrayList<Rectangle> hitRectangles;
   private ArrayList<Vector2> relativePosition;


   public EnemyGroup(World var1, float var2, float var3, int var4) {
      super(var1, 10, var2, var3, 1.0F, 1.0F);
      this.velocity.set(Settings.backgroundVelocity);
      this.relativePosition = new ArrayList(var4);
      this.enemyList = new ArrayList(var4);
      this.hitRectangles = new ArrayList(var4);
      this.enemyNum = var4;
      this.enemyListIsOk = false;
   }

   public boolean addEnemy(Enemy var1, Vector2 var2) {
      if(this.enemyList.size() < this.enemyNum && !this.enemyListIsOk) {
         this.relativePosition.add(new Vector2(var2));
         this.enemyList.add(var1);
         return true;
      } else {
         return false;
      }
   }

   public void beHitByBullet(Bullet var1, int var2) {
      if(1 != this.state && this.isCrashed()) {
         this.stateTime = 0.0F;
         this.state = 1;
      }
   }

   public void fixEnemyList() {
      this.enemyListIsOk = true;
   }

   public List<Rectangle> getHitRectangle() {
      this.hitRectangles.clear();
      Iterator var1 = this.enemyList.iterator();

      while(var1.hasNext()) {
         Enemy var2 = (Enemy)var1.next();
         if(!var2.isCrashed()) {
            int var3 = var2.getHitRectangle().size();

            for(int var4 = 0; var4 < var3; ++var4) {
               ;
            }
         }
      }

      return this.hitRectangles;
   }

   public float getTimeOfCrash() {
      return 4.0F;
   }

   public boolean isCrashed() {
      boolean var1 = true;
      Iterator var2 = this.enemyList.iterator();

      while(var2.hasNext()) {
         if(!((Enemy)var2.next()).isCrashed()) {
            var1 = false;
            break;
         }
      }

      return var1;
   }

   public void render(SpriteBatch var1) {
      Iterator var2 = this.enemyList.iterator();

      while(var2.hasNext()) {
         ((Enemy)var2.next()).render(var1);
      }

   }

   public void update(float var1) {
      super.update(var1);
      this.enemyListIsOk = true;
      Iterator var2 = this.enemyList.iterator();

      while(var2.hasNext()) {
         ((Enemy)var2.next()).update(var1);
      }

   }

   public void updateDynamicParameter(float var1) {
      super.updateDynamicParameter(var1);
      int var2 = this.enemyList.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         ((Enemy)this.enemyList.get(var3)).position.set(this.bounds.x + ((Vector2)this.relativePosition.get(var3)).x, this.bounds.y + ((Vector2)this.relativePosition.get(var3)).y);
      }

   }

   public void updateShooting(float var1) {}

   private static class HitMapItem {

      Enemy e;
      int index;


   }
}
