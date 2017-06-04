package com.doodlegames.air.force.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.doodlegames.air.force.resource.Assets_level1;
import com.doodlegames.air.force.resource.Assets_level2;
import com.doodlegames.air.force.resource.Assets_level3;
import com.doodlegames.air.force.resource.Assets_level4;
import com.doodlegames.air.force.resource.Assets_level5;
import com.doodlegames.air.force.resource.Assets_level6;
import com.doodlegames.air.force.resource.Assets_level7;
import com.doodlegames.air.force.screen.XScreen;
import com.doodlegames.air.force.utils.AtlasRegionDrawable;
import com.doodlegames.air.force.utils.Settings;

public class BigBackGround {

   private static String[] bigBack = new String[]{"sea", "land", "purBG", "floor", "purBG", "purBG", "floor"};
   public BigBackGround.BackgroundAction action = new BigBackGround.GridBG();
   public boolean blend = false;
   Drawable drawableItem = (Drawable) new AtlasRegionDrawable();
   public float screenHeight = 800.0F;
   public float worldWidth = 640.0F;


   private static void checkLevel2(int var0, BigBackGround var1) {
      if(var0 == 2) {
         var1.blend = true;
      } else {
         var1.blend = false;
      }
   }

   public static BigBackGround getBigBackGround(int var0) {
      if(var0 >= 1 && var0 <= 7) {
         TextureAtlas[] var1 = new TextureAtlas[]{Assets_level1.atlas_bg, Assets_level2.atlas_bg, Assets_level3.atlas_bg, Assets_level4.atlas_bg, Assets_level5.atlas_bg, Assets_level6.atlas_bg, Assets_level7.atlas_bg};
         BigBackGround var2 = getBigBackGround(new SpriteDrawable(new TextureAtlas.AtlasSprite(var1[var0 - 1].findRegion(bigBack[var0 - 1]))), getClassType(var0));
         checkLevel2(var0, var2);
         return var2;
      } else {
         return null;
      }
   }

   public static BigBackGround getBigBackGround(Drawable var0) {
      BigBackGround var1 = new BigBackGround();
      var1.setDrawable(var0);
      return var1;
   }

   private static BigBackGround getBigBackGround(Drawable var0, Class<? extends BigBackGround.BackgroundAction> var1) {
      try {
         BigBackGround var2 = new BigBackGround();
         var2.action = (BigBackGround.BackgroundAction)var1.getConstructor(new Class[]{BigBackGround.class}).newInstance(new Object[]{var2});
         var2.setDrawable(var0);
         return var2;
      } catch (Exception var4) {
         Settings.w(var4.toString());
         return null;
      }
   }

   private static Class<? extends BigBackGround.BackgroundAction> getClassType(int var0) {
      return var0 != 1 && var0 != 2 && var0 != 4 && var0 != 7?(var0 != 3 && var0 != 5 && var0 != 6?BigBackGround.PurColorBG.class:BigBackGround.PurColorBG.class):BigBackGround.GridBG.class;
   }

   public static BigBackGround getUIBigBackGround(int var0, TextureAtlas var1) {
      SpriteDrawable var2;
      switch(var0) {
      case 1:
         var2 = new SpriteDrawable(new TextureAtlas.AtlasSprite(var1.findRegion("sea")));
         break;
      case 2:
         var2 = new SpriteDrawable(new TextureAtlas.AtlasSprite(var1.findRegion("land")));
         break;
      case 3:
         Sprite var6 = new Sprite(XScreen.getTexture());
         var6.setColor(0.1254902F, 0.17254902F, 0.20784314F, 1.0F);
         var2 = new SpriteDrawable(var6);
         break;
      case 4:
         var2 = new SpriteDrawable(new TextureAtlas.AtlasSprite(var1.findRegion("floor")));
         break;
      case 5:
         Sprite var5 = new Sprite(XScreen.getTexture());
         var5.setColor(0.0F, 0.0F, 0.0F, 1.0F);
         var2 = new SpriteDrawable(var5);
         break;
      case 6:
         Sprite var4 = new Sprite(XScreen.getTexture());
         var4.setColor(0.4392157F, 0.23529412F, 0.19607843F, 1.0F);
         var2 = new SpriteDrawable(var4);
         break;
      case 7:
         var2 = new SpriteDrawable(new TextureAtlas.AtlasSprite(var1.findRegion("floor")));
         break;
      default:
         var2 = new SpriteDrawable(new Sprite(XScreen.getTexture()));
      }

      BigBackGround var3 = getBigBackGround(var2, getClassType(var0));
      checkLevel2(var0, var3);
      return var3;
   }

   public void render(SpriteBatch var1) {
      if(this.drawableItem != null && this.action != null) {
         if(!this.blend) {
            var1.disableBlending();
         }

         this.action.render(var1);
         if(!this.blend) {
            var1.enableBlending();
            return;
         }
      }

   }

   public void reset() {
      this.action = null;
      this.drawableItem = null;
   }

   public void setDrawable(Drawable var1) {
      if(var1 != null) {
         this.drawableItem = var1;
      }
   }

   public void update(float var1) {
      if(this.action != null) {
         this.action.update(var1);
      }
   }

   public void update(float var1, float var2) {
      if(this.action != null) {
         this.action.update(var1, var2);
      }
   }

   public class GridBG implements BigBackGround.BackgroundAction {

      float currentPosition = 0.0F;


      private void fixCurrentPosition() {
         if(BigBackGround.this.drawableItem.getMinHeight() != 0.0F && BigBackGround.this.drawableItem.getMinWidth() != 0.0F) {
            float var1 = BigBackGround.this.drawableItem.getMinHeight();
            this.currentPosition += var1 * (float)((int)(this.currentPosition / -var1));
         }
      }

      public void render(SpriteBatch var1) {
         if(BigBackGround.this.drawableItem.getMinWidth() != 0.0F && BigBackGround.this.drawableItem.getMinHeight() != 0.0F) {
            this.fixCurrentPosition();
            float var2 = BigBackGround.this.worldWidth;
            float var3 = BigBackGround.this.screenHeight;
            float var4 = BigBackGround.this.drawableItem.getMinWidth();
            float var5 = BigBackGround.this.drawableItem.getMinHeight();
            int var6 = 0 + (int)(var2 / var4);
            int var7 = 2 + (int)(var3 / var5);
            boolean var8 = BigBackGround.this.drawableItem instanceof SpriteDrawable;
            Texture.TextureFilter var9 = null;
            Texture.TextureFilter var10 = null;
            if(var8) {
               Texture var16 = ((SpriteDrawable)BigBackGround.this.drawableItem).getSprite().getTexture();
               var10 = var16.getMinFilter();
               var9 = var16.getMagFilter();
               var16.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            }

            int var11 = 0;

            for(int var12 = 0; var12 < var6; ++var12) {
               for(int var13 = 0; var13 < var7; ++var13) {
                  float var14 = var4 * (float)var12;
                  float var15 = this.currentPosition + var5 * (float)var13;
                  BigBackGround.this.drawableItem.draw(var1, var14, var15, var4, var5);
                  ++var11;
               }
            }

            if(BigBackGround.this.drawableItem instanceof SpriteDrawable) {
               ((SpriteDrawable)BigBackGround.this.drawableItem).getSprite().getTexture().setFilter(var10, var9);
               return;
            }
         } else {
            Settings.w("must set drawable first before render invoked, in BigBackGround");
         }

      }

      public void update(float var1) {
         this.currentPosition += var1 * Settings.backgroundVelocity.y;
         this.fixCurrentPosition();
      }

      public void update(float var1, float var2) {
         if(var2 <= 0.0F) {
            this.currentPosition = var2;
         }

         this.fixCurrentPosition();
      }
   }

   public interface BackgroundAction {

      void render(SpriteBatch var1);

      void update(float var1);

      void update(float var1, float var2);
   }

   public class PurColorBG implements BigBackGround.BackgroundAction {

      public void render(SpriteBatch var1) {
         BigBackGround.this.drawableItem.draw(var1, 0.0F, 0.0F, BigBackGround.this.worldWidth, BigBackGround.this.screenHeight);
      }

      public void update(float var1) {}

      public void update(float var1, float var2) {}
   }
}
