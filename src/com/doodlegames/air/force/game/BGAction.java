package com.doodlegames.air.force.game;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.doodlegames.air.force.game.BGActionLineMove;
import com.doodlegames.air.force.game.BGActionShakeVe;
import com.doodlegames.air.force.game.BGActionSwim;
import com.doodlegames.air.force.game.BGActionTurning;
import com.doodlegames.air.force.game.BGActionWithParent;
import com.doodlegames.air.force.game.SmallBackground;

public abstract class BGAction implements Pool.Poolable {

   protected SmallBackground.SmallBackgroundItem item;
   Pool pool;
   float time = 0.0F;


   public static <T extends BGAction> T bgAction(Class<T> var0) {
      Pool var1 = Pools.get(var0);
      BGAction var2 = (BGAction)var1.obtain();
      var2.setPool(var1);
      return (T) var2;
   }

   public static void hasParent(SmallBackground.SmallBackgroundItem var0, SmallBackground.SmallBackgroundItem var1, float var2, float var3) {
      BGActionWithParent var4 = (BGActionWithParent)bgAction(BGActionWithParent.class);
      var4.setBGItem(var0);
      var4.setRelativePositionXY(var2, var3);
      var0.addAction(var4);
      var0.parent = var1;
   }

   public static void lineMove(SmallBackground.SmallBackgroundItem var0, float var1) {
      BGActionLineMove var2 = (BGActionLineMove)bgAction(BGActionLineMove.class);
      var2.setBGItem(var0);
      var2.setVel(0.0F, var1);
      var0.addAction(var2);
   }

   public static void shake(SmallBackground.SmallBackgroundItem var0, float var1) {
      BGActionShakeVe var2 = (BGActionShakeVe)bgAction(BGActionShakeVe.class);
      var2.setCycleAndDistance(1.0F, 2.0F);
      var2.setBGItem(var0);
      var0.addAction(var2);
   }

   public static void swim(SmallBackground.SmallBackgroundItem var0, float var1) {
      BGActionSwim var2 = (BGActionSwim)bgAction(BGActionSwim.class);
      var2.setBGItem(var0);
      var2.setAngleSpeed(var1);
      var0.addAction(var2);
   }

   public static void turning(SmallBackground.SmallBackgroundItem var0, float var1) {
      BGActionTurning var2 = (BGActionTurning)bgAction(BGActionTurning.class);
      var2.setBGItem(var0);
      var2.setAngleSpeed(var1);
      var0.addAction(var2);
   }

   public abstract void initalize();

   public void removed() {
      if(this.pool != null) {
         this.pool.free((Object)this);
         this.pool = null;
      }

   }

   public void reset() {}

   public void setBGItem(SmallBackground.SmallBackgroundItem var1) {
      if(var1 != null && var1.getSpriteDrawable().getSprite() != null) {
         this.item = var1;
      }
   }

   public void setPool(Pool<? extends BGAction> var1) {
      this.pool = var1;
   }

   public void update(float var1) {
      if(this.time == 0.0F) {
         this.initalize();
      }

      this.time += var1;
   }
}
