package com.doodlegames.air.force.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.doodlegames.air.force.screen.XScreen;

import java.util.List;

public class RenderHelper {

   public static void renderRectangle(SpriteBatch var0, Rectangle var1) {
      float[] var2 = new float[]{var1.x, var1.y, Color.WHITE.toFloatBits(), 1.0F, 1.0F, var1.x + var1.width, var1.y, Color.WHITE.toFloatBits(), 1.0F, 1.0F, var1.x + var1.width, var1.y + var1.height, Color.WHITE.toFloatBits(), 1.0F, 1.0F, var1.x, var1.y + var1.height, Color.WHITE.toFloatBits(), 1.0F, 1.0F};
      var0.draw(XScreen.getTexture(), var2, 0, 20);
   }

   public static void renderRectangles(SpriteBatch var0, List<Rectangle> var1) {
      for(int var2 = 0; var2 < var1.size(); ++var2) {
         renderRectangle(var0, (Rectangle)var1.get(var2));
      }

   }
}
