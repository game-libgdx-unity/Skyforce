package com.doodlegames.air.force.utils;

import com.doodlegames.air.force.utils.Clock;

public class RandomOrder {

   int currentCount = 0;
   final int num;
   final int[] order;


   public RandomOrder(int var1) {
      this.num = var1;
      this.order = new int[var1];
   }

   public int getCurrentCount() {
      return this.currentCount;
   }

   public boolean hasNext() {
      return this.order.length > this.currentCount;
   }

   public void init() {
      for(int var1 = 0; var1 < this.order.length; ++var1) {
         this.order[var1] = var1 + 1;
      }

      this.currentCount = 0;
   }

   public int nextOrder() {
      int var1 = this.order.length - this.currentCount;
      if(var1 <= 0) {
         throw new RuntimeException("nextOrder run out");
      } else {
         int var2 = Clock.rand.nextInt(var1);
         int var3 = this.order[var2];
         this.order[var2] = this.order[var1 - 1];
         this.order[var1 - 1] = var3;
         ++this.currentCount;
         return var3;
      }
   }
}
