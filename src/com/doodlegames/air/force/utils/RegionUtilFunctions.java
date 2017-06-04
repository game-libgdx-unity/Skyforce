package com.doodlegames.air.force.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Settings;

public class RegionUtilFunctions {

   public static void draw(SpriteBatch var0, TextureRegion var1, float var2, float var3, float var4, float var5) {
      draw(var0, var1, var2, var3, var4 / 2.0F, var5 / 2.0F, var4, var5, 1.0F, 1.0F, 0.0F);
   }

   public static void draw(SpriteBatch var0, TextureRegion var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      if(!(var1 instanceof TextureAtlas.AtlasRegion)) {
         var0.draw(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
      } else {
         TextureAtlas.AtlasRegion var11 = (TextureAtlas.AtlasRegion)TextureAtlas.AtlasRegion.class.cast(var1);
         float var12 = var11.offsetX;
         float var13 = var11.offsetY;
         if(var11.rotate && (var12 != 0.0F || var13 != 0.0F)) {
            String var14 = "offsetX != 0 or offsetY != 0, in this condition rotate must be false." + "offsetX:" + var12;
            String var15 = var14 + "offsetY:" + var13;
            throw new IllegalArgumentException(var15 + "regionName:" + var11.name);
         } else {
            if(var12 != 0.0F || var13 != 0.0F) {
               var2 += var12;
               var3 += var13;
               var6 -= 2.0F * var12;
               var7 -= 2.0F * var13;
               var4 -= var12;
               var5 -= var13;
            }

            var0.draw(var11, var2, var3, var4, var5, var6, var7, var8, var9, var10);
         }
      }
   }

   public static void draw(SpriteBatch var0, TextureRegion var1, Rectangle var2) {
      draw(var0, var1, var2.x, var2.y, var2.width / 2.0F, var2.height / 2.0F, var2.width, var2.height, 1.0F, 1.0F, 0.0F);
   }

   public static void draw(SpriteBatch var0, TextureRegion var1, Rectangle var2, float var3, float var4, float var5) {
      draw(var0, var1, var2.x, var2.y, var2.width / 2.0F, var2.height / 2.0F, var2.width, var2.height, var3, var4, var5);
   }

   public static void draw(SpriteBatch var0, TextureRegion var1, Rectangle var2, float var3, float var4, float var5, float var6, float var7) {
      draw(var0, var1, var2.x, var2.y, var3, var4, var2.width, var2.height, var5, var6, var7);
   }

   public static void drawShadow(SpriteBatch var0, TextureRegion var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11) {
      if(var2 < 0.0F || var2 > 1.0F) {
         var2 = 1.0F;
         Settings.appLog("invalid alpha in drawShaw");
      }

      Color var12 = var0.getColor();
      var0.setColor(0.0F, 0.0F, 0.0F, var2);
      draw(var0, var1, var3, var4, var5, var6, var7, var8, var9, var10, var11);
      var0.setColor(var12);
   }

   public static final int getRegionHeight(TextureRegion var0) {
      if(var0 != null) {
         return var0 instanceof TextureAtlas.AtlasRegion?((TextureAtlas.AtlasRegion)TextureAtlas.AtlasRegion.class.cast(var0)).originalHeight:var0.getRegionHeight();
      } else {
         Settings.appLog("region is null, invoked error.");
         return -1;
      }
   }

   public static final int getRegionHeight(Animation var0) {
      return getRegionHeight(var0.getKeyFrame(0.0F));
   }

   public static final int getRegionWidth(TextureRegion var0) {
      if(var0 != null) {
         return var0 instanceof TextureAtlas.AtlasRegion?((TextureAtlas.AtlasRegion)TextureAtlas.AtlasRegion.class.cast(var0)).originalWidth:var0.getRegionWidth();
      } else {
         Settings.appLog("region is null, invoked error.");
         return -1;
      }
   }

   public static final int getRegionWidth(Animation var0) {
      return getRegionWidth(var0.getKeyFrame(0.0F));
   }
}
