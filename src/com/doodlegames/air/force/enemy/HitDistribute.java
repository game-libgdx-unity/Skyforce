package com.doodlegames.air.force.enemy;

import com.badlogic.gdx.math.Rectangle;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class HitDistribute {

   private Enemy.HitMapItem[] hitMap;
   private List<Rectangle> hitRectangles;
   private int index;
   private boolean isAdding;
   private final GameObject owner;
   private final int partNum;


   public HitDistribute(GameObject var1, int var2) {
      this.owner = var1;
      this.partNum = var2;
      this.hitRectangles = new ArrayList(this.partNum);
      this.hitMap = new Enemy.HitMapItem[this.partNum];

      for(int var3 = 0; var3 < this.partNum; ++var3) {
         this.hitMap[var3] = new Enemy.HitMapItem();
         this.hitMap[var3].e = null;
         this.hitMap[var3].index = -1;
      }

      this.hitRectangles.clear();
      this.index = 0;
      this.isAdding = false;
   }

   public void addEnemyHitRec(Enemy var1) {
      if(!this.isAdding) {
         Settings.appLog("beginAdd must be invoked first!");
      } else {
         if(this.index >= this.partNum) {
            Settings.appLog("hitMap overflow!!!!!!!!");
         }

         if(var1.canBeHit()) {
            List var2;
            if(var1 == this.owner) {
               var2 = var1.getHitRectangle(0);
            } else {
               var2 = var1.getHitRectangle();
            }

            int var3 = var2.size();

            for(int var4 = 0; var4 < var3; ++var4) {
               this.hitRectangles.add((Rectangle) var2.get(var4));
               this.hitMap[this.index].e = var1;
               this.hitMap[this.index].index = var4;
               ++this.index;
            }
         }
      }

   }

   public void beHitByBullet(Bullet var1, int var2) {
      if(this.isAdding) {
         Settings.appLog("beHitByBullet() invoked error!");
      } else if(var2 >= 0 && var2 < this.hitMap.length) {
         Enemy.HitMapItem var3 = this.hitMap[var2];
         if(var3.e != this.owner) {
            var3.e.beHitByBullet(var1, var3.index);
         } else {
            var3.e.beHitByBullet(var1);
         }
      } else {
         Settings.appLog("beHitByBullet() invoked error!");
      }
   }

   public void beginAdd() {
      this.hitRectangles.clear();
      this.index = 0;
      this.isAdding = true;
   }

   public void endAdd() {
      if(this.isAdding) {
         this.isAdding = false;
      } else {
         Settings.appLog("beginAdd must be invoked first!");
      }
   }

   public List<Rectangle> getHitRectangle() {
      if(!this.isAdding) {
         return this.hitRectangles;
      } else {
         Settings.appLog("you can getHitRectangle before endAdd() invoking.");
         return null;
      }
   }
}
