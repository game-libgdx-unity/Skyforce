package com.doodlegames.air.force.utils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class OverlapTester {

   public static boolean overlapRectangles(Rectangle var0, Rectangle var1) {
      return var0.x < var1.x + var1.width && var0.x + var0.width > var1.x && var0.y < var1.y + var1.height && var0.y + var0.height > var1.y;
   }

   public static boolean overlapRectangles(Rectangle var0, Rectangle var1, Rectangle var2) {
      float var3 = Math.max(var0.x, var1.x);
      float var4 = Math.min(var0.x + var0.width, var1.x + var1.width);
      float var5 = Math.max(var0.y, var1.y);
      float var6 = Math.min(var0.y + var0.height, var1.y + var1.height);
      if(var4 - var3 > 0.0F && var6 - var5 > 0.0F) {
         if(var2 != null) {
            var2.x = var3;
            var2.y = var5;
            var2.width = var4 - var3;
            var2.height = var6 - var5;
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean pointInRectangle(Rectangle var0, float var1, float var2) {
      return var0.x <= var1 && var0.x + var0.width >= var1 && var0.y <= var2 && var0.y + var0.height >= var2;
   }

   public static boolean pointInRectangle(Rectangle var0, Vector2 var1) {
      return var0.x <= var1.x && var0.x + var0.width >= var1.x && var0.y <= var1.y && var0.y + var0.height >= var1.y;
   }
}
