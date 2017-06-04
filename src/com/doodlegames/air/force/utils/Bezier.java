package com.doodlegames.air.force.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Bezier {

   public static float computerBezier1(float var0, float var1, float var2) {
      return var0 * (1.0F - var2) + var1 * var2;
   }

   public static void computerBezier1(Vector3 var0, Vector2 var1, Vector2 var2, float var3) {
      float var4 = 1.0F - var3;
      var0.x = var4 * var1.x + var3 * var2.x;
      var0.y = var4 * var1.y + var3 * var2.y;
      var0.z = 57.295776F * MathUtils.atan2(var2.y - var1.y, var2.x - var1.x) - 90.0F;
   }

   public static float computerBezier2(float var0, float var1, float var2, float var3) {
      float var4 = var3 * var3;
      float var5 = 1.0F - var3;
      float var6 = var5 * var5;
      float var7 = 2.0F * var5 * var3;
      return var0 * var6 + var1 * var7 + var2 * var4;
   }

   public static void computerBezier2(Vector2 var0, Vector2 var1, Vector2 var2, Vector2 var3, float var4) {
      float var5 = var4 * var4;
      float var6 = 1.0F - var4;
      float var7 = var6 * var6;
      float var8 = 2.0F * var6 * var4;
      var0.x = var7 * var1.x + var8 * var2.x + var5 * var3.x;
      var0.y = var7 * var1.y + var8 * var2.y + var5 * var3.y;
   }

   public static void computerBezier2(Vector3 var0, Vector2 var1, Vector2 var2, Vector2 var3, float var4) {
      float var5 = var4 * var4;
      float var6 = 1.0F - var4;
      float var7 = var6 * var6;
      float var8 = 2.0F * var6 * var4;
      var0.x = var7 * var1.x + var8 * var2.x + var5 * var3.x;
      var0.y = var7 * var1.y + var8 * var2.y + var5 * var3.y;
      var0.z = 57.295776F * MathUtils.atan2(computerBezier1(var2.y, var3.y, var4) - computerBezier1(var1.y, var2.y, var4), computerBezier1(var2.x, var3.x, var4) - computerBezier1(var1.x, var2.x, var4)) - 90.0F;
   }

   public static float computerBezier3(float var0, float var1, float var2, float var3, float var4) {
      float var5 = var4 * var4;
      float var6 = var5 * var4;
      float var7 = 1.0F - var4;
      float var8 = var7 * var7;
      float var9 = var8 * var7;
      float var10 = 3.0F * var8 * var4;
      float var11 = 3.0F * var7 * var5;
      return var0 * var9 + var1 * var10 + var2 * var11 + var3 * var6;
   }

   public static void computerBezier3(Vector3 var0, Vector2 var1, Vector2 var2, Vector2 var3, Vector2 var4, float var5) {
      float var6 = var5 * var5;
      float var7 = var6 * var5;
      float var8 = 1.0F - var5;
      float var9 = var8 * var8;
      float var10 = var9 * var8;
      float var11 = 3.0F * var9 * var5;
      float var12 = 3.0F * var8 * var6;
      var0.x = var10 * var1.x + var11 * var2.x + var12 * var3.x + var7 * var4.x;
      var0.y = var10 * var1.y + var11 * var2.y + var12 * var3.y + var7 * var4.y;
      float var13 = 2.0F * var8 * var5;
      var0.z = 57.295776F * MathUtils.atan2(var9 * var1.y + var13 * var2.y + var6 * var3.y - (var9 * var2.y + var13 * var3.y + var6 * var4.y), var9 * var1.x + var13 * var2.x + var6 * var3.x - (var9 * var2.x + var13 * var3.x + var6 * var4.x)) - 90.0F;
   }
}
