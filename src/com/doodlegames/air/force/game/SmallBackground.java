package com.doodlegames.air.force.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.doodlegames.air.force.resource.Assets_level1;
import com.doodlegames.air.force.resource.Assets_level2;
import com.doodlegames.air.force.resource.Assets_level3;
import com.doodlegames.air.force.resource.Assets_level4;
import com.doodlegames.air.force.resource.Assets_level5;
import com.doodlegames.air.force.resource.Assets_level6;
import com.doodlegames.air.force.resource.Assets_level7;
import com.doodlegames.air.force.utils.Settings;

public class SmallBackground {

   private static String[] bgItemInfoPath = new String[]{"levelData/level1/level_1.map", "levelData/level2/level_2.map", "levelData/level3/level_3.map", "levelData/level4/level_4.map", "levelData/level5/level_5.map", "levelData/level6/level_6.map", "levelData/level7/level_7.map"};
   static Pattern pJWithNum = Pattern.compile("_((\\d)+)\\.png");
   static Pattern pWithNum = Pattern.compile("(([a-zA-Z]|\\d|_)+)_(\\d)+\\.png");
   static Pattern pWithoutNum = Pattern.compile("(([a-zA-Z]|\\d|_)+)\\.png");
   private static final float phaseSize = 400.0F;
   TextureAtlas atlas;
   Array<SmallBackground.SmallBackgroundItem> bgItems = new Array(100);
   Array<Array<SmallBackground.SmallBackgroundItem>> bgItemsTable;
   private Object[] bucket = new Object[]{null, null, null, null};
   float currentPosition = 0.0F;
   private int[] indexArray = new int[]{0, 0, 0, 0};
   private float loopCycleY = -1.0F;
   final boolean useHashTable;


   public SmallBackground(boolean var1) {
      this.useHashTable = var1;
      this.bgItemsTable = new Array(17);

      for(int var2 = 0; var2 < 17; ++var2) {
         this.bgItemsTable.add(new Array(10));
      }

   }

   private void addBGItem(SmallBackground.SmallBackgroundItem var1) {
      if(this.useHashTable) {
         int var2 = (int)(var1.staticLBY / 400.0F);
         if(var2 < 0) {
            var2 = 0;
         }

         if(var2 >= -1 + this.bgItemsTable.size) {
            var2 = -1 + this.bgItemsTable.size;
         }

         ((Array)this.bgItemsTable.get(var2)).add(var1);
      } else {
         this.bgItems.add(var1);
      }
   }

   public static SmallBackground getBackGround(int var0) {
      SmallBackground var1;
      if(var0 == 5) {
         var1 = new SmallBackground(false);
         var1.loopCycleY = 6400.0F;
      } else {
         var1 = new SmallBackground(true);
      }

      var1.reset();
      TextureAtlas[] var2 = new TextureAtlas[]{Assets_level1.atlas_bg, Assets_level2.atlas_bg, Assets_level3.atlas_bg, Assets_level4.atlas_bg, Assets_level5.atlas_bg, Assets_level6.atlas_bg, Assets_level7.atlas_bg};
      int var3 = var0 - 1;
      if(var3 >= 0 && var3 < var2.length) {
         var1.deSerialize(new BufferedReader(new InputStreamReader(Gdx.files.internal(bgItemInfoPath[var3]).read())), var2[var3]);
      }

      return var1;
   }

   public static SmallBackground getUIBackGround(TextureAtlas var0, int var1) {
      SmallBackground var2 = new SmallBackground(false);
      var2.reset();
      int var3 = var1 - 1;
      if(var3 >= 0 && var3 < bgItemInfoPath.length) {
         BufferedReader var4 = new BufferedReader(new InputStreamReader(Gdx.files.internal(bgItemInfoPath[var3]).read()));
         var2.loopCycleY = 6400.0F;
         var2.deSerialize(var4, var0);
      }

      return var2;
   }

   private void renderBGItemsTable(SpriteBatch var1) {
      int var2 = (int)(Math.abs(this.currentPosition) / 400.0F);
      int var3 = Math.max(0, var2 - 1);
      int var4 = Math.min(this.bgItemsTable.size, var2 + 3) - var3;

      for(int var5 = 0; var5 < var4; ++var5) {
         this.indexArray[var5] = 0;
         this.bucket[var5] = this.bgItemsTable.get(var5 + var3);
      }

      while(true) {
         SmallBackground.SmallBackgroundItem var6 = null;
         int var7 = Integer.MAX_VALUE;
         int var8 = -1;

         for(int var9 = 0; var9 < var4; ++var9) {
            Array var11 = (Array)this.bucket[var9];
            if(this.indexArray[var9] < var11.size) {
               SmallBackground.SmallBackgroundItem var12 = (SmallBackground.SmallBackgroundItem)var11.get(this.indexArray[var9]);
               if(var12.id < var7) {
                  var6 = var12;
                  var7 = var12.id;
                  var8 = var9;
               }
            }
         }

         if(var6 == null) {
            return;
         }

         var6.render(var1);
         int[] var10 = this.indexArray;
         ++var10[var8];
      }
   }

   public static void setDrawableAndAction(String var0, TextureAtlas var1, SmallBackground.SmallBackgroundItem var2) {
      Matcher var3 = pJWithNum.matcher(var0);
      int var4 = -1;
      String var5 = "";
      if(var3.find()) {
         var4 = Integer.parseInt(var3.group(1));
         Matcher var9 = pWithNum.matcher(var0);
         if(var9.find()) {
            var5 = var9.group(1);
         }
      } else {
         Matcher var6 = pWithoutNum.matcher(var0);
         if(var6.find()) {
            var5 = var6.group(1);
         }
      }

      TextureAtlas.AtlasRegion var7;
      if(var4 != -1) {
         var7 = var1.findRegion(var5, var4);
      } else {
         var7 = var1.findRegion(var5);
      }

      var2.spriteDrawable = new SpriteDrawable(new TextureAtlas.AtlasSprite(var7));
      if(var4 != -1) {
         var5 = var5 + "_" + var4;
      }

      BGfixer var8 = BGfixer.LevelActionAdder.getBGfixer(var5);
      if(var8 != null) {
         var8.addActionsAndFix(var2);
      }

   }

   private void updateBGItemsTable(float var1) {
      int var2 = (int)(Math.abs(this.currentPosition) / 400.0F);
      int var3 = Math.min(this.bgItemsTable.size, var2 + 3);

      for(int var4 = 0; var4 < var3; ++var4) {
         boolean var5 = true;
         Array var6 = (Array)this.bgItemsTable.get(var4);
         Iterator var7 = var6.iterator();

         while(var7.hasNext()) {
            SmallBackground.SmallBackgroundItem var8 = (SmallBackground.SmallBackgroundItem)var7.next();
            var8.update(var1);
            if(var8.indexState != 2) {
               var5 = false;
            }
         }

         if(var6.size != 0 && var5) {
            var6.clear();
         }
      }

   }

   public void deSerialize(final BufferedReader bufferedReader, final TextureAtlas atlas) {

       if (atlas != null && bufferedReader != null) {

           this.atlas = atlas;

           int o = 0;

           int i;

           try {

               this.bgItems.clear();

               i = Integer.parseInt(bufferedReader.readLine());

           }

           catch (Exception ex) {

               final Object o3 = null;

               final Object o2 = o3;

               this.bgItems.clear();

               Settings.w("small background init error!");

               return;

           }

           int o4;

           for (; i > 0; --i, o = o4) {

               int parseInt = 0;

               try {

                   bufferedReader.readLine();

                   parseInt = Integer.parseInt(bufferedReader.readLine());

               }

               catch (Exception ex) {}

               int j = parseInt;

               o4 = 0;

               while (j > 0) {

                   final int o5 = o4 + 1;

                   try {

                       final SmallBackgroundItem smallBackgroundItem = new SmallBackgroundItem(o4);

                       smallBackgroundItem.deSerialize(bufferedReader);

                       this.addBGItem(smallBackgroundItem);

                   }

                   catch (Exception ex) {}

                   --j;

                   o4 = o5;

               }

           }

       }

   }

   

   public float getLoopCycle() {
      return this.loopCycleY;
   }

   public void render(SpriteBatch var1) {
      if(!this.useHashTable) {
         Iterator var3 = this.bgItems.iterator();

         while(var3.hasNext()) {
            ((SmallBackground.SmallBackgroundItem)var3.next()).render(var1);
         }
      } else {
         try {
            this.renderBGItemsTable(var1);
         } catch (Exception var4) {
            Settings.i("render smallBackGround has error.........");
            throw new RuntimeException(var4.getMessage());
         }
      }

   }

   public void reset() {
      this.bgItems.clear();
      this.atlas = null;
      this.currentPosition = 0.0F;
   }

   public void setLoopCycle(float var1) {
      this.loopCycleY = var1;
   }

   public void update(float var1) {
      this.currentPosition += var1 * Settings.backgroundVelocity.y;
      if(!this.useHashTable) {
         Iterator var2 = this.bgItems.iterator();

         while(var2.hasNext()) {
            ((SmallBackground.SmallBackgroundItem)var2.next()).update(var1);
         }
      } else {
         this.updateBGItemsTable(var1);
      }

   }

   public void update(float var1, float var2) {
      if(var2 <= 0.0F) {
         this.currentPosition = var2;
      }

      if(!this.useHashTable) {
         Iterator var3 = this.bgItems.iterator();

         while(var3.hasNext()) {
            ((SmallBackground.SmallBackgroundItem)var3.next()).update(var1);
         }
      } else {
         this.updateBGItemsTable(var1);
      }

   }

   private interface ItemAction {

      void render(SpriteBatch var1);

      void update(float var1);
   }

   public class SmallBackgroundItem {

      public static final int ACTIVESTATE = 1;
      public static final int PERFROMTATE = 2;
      Array<BGAction> actions = new Array(3);
      float activePoint = 800.0F;
      private SmallBackground.ItemAction activeState = new SmallBackground.ItemAction() {
         public void render(SpriteBatch var1) {
            if(SmallBackgroundItem.this.spriteDrawable.getSprite().getY() <= 1200.0F) {
               SmallBackgroundItem.this.spriteDrawable.getSprite().draw(var1);
            }
         }
         public void update(float var1) {
            if(SmallBackgroundItem.this.actions.size == 0) {
               SmallBackgroundItem.this.spriteDrawable.getSprite().setY(SmallBackground.this.currentPosition + SmallBackgroundItem.this.staticLBY);
            } else {
               float var2 = SmallBackgroundItem.this.spriteDrawable.getSprite().getY() + var1 * Settings.backgroundVelocity.y;
               SmallBackgroundItem.this.spriteDrawable.getSprite().setY(var2);
            }

            Iterator var3 = SmallBackgroundItem.this.actions.iterator();

            while(var3.hasNext()) {
               ((BGAction)var3.next()).update(var1);
            }

            float var4 = SmallBackgroundItem.this.spriteDrawable.getSprite().getHeight();
            float var5 = SmallBackgroundItem.this.spriteDrawable.getSprite().getOriginY();
            if((var4 - var5) * SmallBackgroundItem.this.spriteDrawable.getSprite().getScaleY() + var5 + SmallBackgroundItem.this.spriteDrawable.getSprite().getY() < 0.0F) {
               SmallBackgroundItem.this.sleepIt();
            }

         }
      };
      public SmallBackground.SmallBackgroundItem[] children;
      protected final int id;
      int indexState = 0;
      private SmallBackground.ItemAction[] itemActions;
      public SmallBackground.SmallBackgroundItem parent;
      private SmallBackground.ItemAction performedState = new SmallBackground.ItemAction() {
         public void render(SpriteBatch var1) {}
         public void update(float var1) {
            if(SmallBackground.this.loopCycleY > 0.0F) {
               float var2 = SmallBackgroundItem.this.spriteDrawable.getSprite().getY();
               SmallBackgroundItem.this.staticLBY = var2 + SmallBackground.this.loopCycleY + Math.abs(SmallBackground.this.currentPosition);
               SmallBackgroundItem.this.indexState = 0;
            }

         }
      };
      private SmallBackground.ItemAction sleepState = new SmallBackground.ItemAction() {
         public void render(SpriteBatch var1) {}
         public void update(float var1) {
            if(SmallBackgroundItem.this.staticLBY + SmallBackground.this.currentPosition < SmallBackgroundItem.this.activePoint) {
               SmallBackgroundItem.this.activeIt();
            }

         }
      };
      SpriteDrawable spriteDrawable;
      float staticLBX;
      float staticLBY;
      public SmallBackground wThis = SmallBackground.this;


      public SmallBackgroundItem(int var2) {
         SmallBackground.ItemAction[] var3 = new SmallBackground.ItemAction[]{this.sleepState, this.activeState, this.performedState};
         this.itemActions = var3;
         this.id = var2;
      }

      private void activeIt() {
         if(this.indexState == 0) {
            this.spriteDrawable.getSprite().setPosition(this.staticLBX, this.staticLBY + SmallBackground.this.currentPosition);
            this.indexState = 1;
            if(this.children != null) {
               SmallBackground.SmallBackgroundItem[] var1 = this.children;
               int var2 = var1.length;

               for(int var3 = 0; var3 < var2; ++var3) {
                  var1[var3].activeIt();
               }
            }
         }

      }

      public SmallBackground.SmallBackgroundItem addAction(BGAction var1) {
         this.actions.add(var1);
         return this;
      }

      public void deSerialize(BufferedReader var1) {
         if(SmallBackground.this.atlas != null) {
            try {
               SmallBackground.setDrawableAndAction(var1.readLine(), SmallBackground.this.atlas, this);
               float var3 = Float.parseFloat(var1.readLine());
               float var4 = Float.parseFloat(var1.readLine());
               float var5 = Float.parseFloat(var1.readLine());
               boolean var6 = Boolean.parseBoolean(var1.readLine());
               boolean var7 = Boolean.parseBoolean(var1.readLine());
               float var8 = Float.parseFloat(var1.readLine());
               float var9 = Float.parseFloat(var1.readLine());
               Sprite var10 = this.spriteDrawable.getSprite();
               int var11 = (int)var10.getWidth();
               int var12 = (int)var10.getHeight();
               var10.setX(var3 - (float)(var11 / 2));
               var10.setY(var4 - (float)(var12 / 2));
               this.staticLBX = var3 - (float)(var11 / 2);
               this.staticLBY = var4 - (float)(var12 / 2);
               var10.setRotation(-var5);
               var10.flip(var6, var7);
               var10.setScale(var8, var9);
               if(this.staticLBY < 800.0F) {
                  this.activeIt();
                  return;
               }
            } catch (Exception var13) {
               Settings.w("small item init failed");
               return;
            }
         }

      }

      public SpriteDrawable getSpriteDrawable() {
         return this.spriteDrawable;
      }

      void render(SpriteBatch var1) {
         if(this.spriteDrawable != null) {
            this.itemActions[this.indexState].render(var1);
            if(this.children != null) {
               SmallBackground.SmallBackgroundItem[] var2 = this.children;
               int var3 = var2.length;

               for(int var4 = 0; var4 < var3; ++var4) {
                  SmallBackground.SmallBackgroundItem var5 = var2[var4];
                  if(var5 != null) {
                     var5.render(var1);
                  }
               }
            }
         }

      }

      public void setActivePoint(float var1) {
         this.activePoint = var1;
      }

      protected void sleepIt() {
         if(this.indexState == 1) {
            this.indexState = 2;
            if(this.children != null) {
               SmallBackground.SmallBackgroundItem[] var1 = this.children;
               int var2 = var1.length;

               for(int var3 = 0; var3 < var2; ++var3) {
                  var1[var3].sleepIt();
               }
            }
         }

      }

      void update(float var1) {
         if(this.spriteDrawable != null) {
            this.itemActions[this.indexState].update(var1);
            if(this.children != null) {
               SmallBackground.SmallBackgroundItem[] var2 = this.children;
               int var3 = var2.length;

               for(int var4 = 0; var4 < var3; ++var4) {
                  SmallBackground.SmallBackgroundItem var5 = var2[var4];
                  if(var5 != null) {
                     var5.update(var1);
                  }
               }
            }
         }

      }
   }
}
