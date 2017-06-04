package com.doodlegames.air.force.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.doodlegames.air.force.game.BGAction;
import com.doodlegames.air.force.game.SmallBackground;

import java.util.Hashtable;

public interface BGfixer {

   void addActionsAndFix(SmallBackground.SmallBackgroundItem var1);

   public static class swileFixer implements BGfixer {

      public void addActionsAndFix(SmallBackground.SmallBackgroundItem var1) {
         Color var2 = var1.getSpriteDrawable().getSprite().getColor();
         var2.a = 0.7F;
         var1.getSpriteDrawable().getSprite().setColor(var2);
         BGAction.turning(var1, -120.0F);
      }
   }

   public static class comerFixer implements BGfixer {

      public void addActionsAndFix(SmallBackground.SmallBackgroundItem var1) {
         TextureAtlas.AtlasRegion var2 = var1.wThis.atlas.findRegion("comer", 1);
         var1.children = new SmallBackground.SmallBackgroundItem[1];
         SmallBackground.SmallBackgroundItem[] var3 = var1.children;
         SmallBackground var4 = var1.wThis;
         var3[0] = var4.new SmallBackgroundItem(-1);
         var1.children[0].spriteDrawable = new SpriteDrawable(new TextureAtlas.AtlasSprite(var2));
         var1.children[0].parent = var1;
         Sprite var6 = var1.children[0].spriteDrawable.getSprite();
         var6.setOrigin(var6.getWidth() / 2.0F, var6.getHeight() / 2.0F);
         BGAction.hasParent(var1.children[0], var1, 5.0F, 11.0F);
         BGAction.turning(var1.children[0], 120.0F);
      }
   }

   public static class aeroLite implements BGfixer {

      public void addActionsAndFix(SmallBackground.SmallBackgroundItem var1) {
         BGAction.lineMove(var1, -60.0F);
      }
   }

   public static class LevelActionAdder {

      private static Hashtable<String, BGfixer> adderTable = new Hashtable();


      public static BGfixer getBGfixer(String var0) {
         return (BGfixer)adderTable.get(var0);
      }

      public static void level1ActionAdder() {
         adderTable.clear();
         adderTable.put("whale_0", new BGfixer.whale0Fixer());
         adderTable.put("swirl", new BGfixer.swileFixer());
         adderTable.put("driftwood", new BGfixer.driftwoodFixer());
      }

      public static void level2ActionAdder() {
         adderTable.clear();
      }

      public static void level3ActionAdder() {
         adderTable.clear();
         adderTable.put("comer_0", new BGfixer.comerFixer());
      }

      public static void level4ActionAdder() {
         adderTable.clear();
      }

      public static void level5ActionAdder() {
         adderTable.clear();
         BGfixer.galaxyFixer var0 = new BGfixer.galaxyFixer();
         adderTable.put("galaxy_0", var0);
         adderTable.put("galaxy_1", var0);
         adderTable.put("galaxy_2", var0);
         adderTable.put("galaxy_3", var0);
         adderTable.put("galaxy_4", var0);
         adderTable.put("aerolite", new BGfixer.aeroLite());
      }

      public static void level6ActionAdder() {
         adderTable.clear();
      }

      public static void level7ActionAdder() {
         adderTable.clear();
      }

      public static void levelActionAdder(int var0) {
         switch(var0) {
         case 1:
            level1ActionAdder();
            return;
         case 2:
            level2ActionAdder();
            return;
         case 3:
            level3ActionAdder();
            return;
         case 4:
            level4ActionAdder();
            return;
         case 5:
            level5ActionAdder();
            return;
         case 6:
            level6ActionAdder();
            return;
         case 7:
            level7ActionAdder();
            return;
         default:
         }
      }
   }

   public static class galaxyFixer implements BGfixer {

      public void addActionsAndFix(SmallBackground.SmallBackgroundItem var1) {
         if(var1.wThis.getLoopCycle() < 0.0F) {
            var1.wThis.setLoopCycle(6400.0F);
         }

         BGAction.lineMove(var1, 40.0F);
         var1.setActivePoint(6500.0F);
      }
   }

   public static class driftwoodFixer implements BGfixer {

      public void addActionsAndFix(SmallBackground.SmallBackgroundItem var1) {
         BGAction.shake(var1, 6.6666665F);
      }
   }

   public static class whale0Fixer implements BGfixer {

      public void addActionsAndFix(SmallBackground.SmallBackgroundItem var1) {
         Color var2 = var1.getSpriteDrawable().getSprite().getColor();
         var2.a = 0.7F;
         var1.getSpriteDrawable().getSprite().setColor(var2);
         BGAction.lineMove(var1, 8.0F);
         float var3 = var1.getSpriteDrawable().getSprite().getScaleX();
         float var4 = var1.getSpriteDrawable().getSprite().getScaleY();
         TextureAtlas.AtlasRegion var5 = var1.wThis.atlas.findRegion("whale", 1);
         var1.children = new SmallBackground.SmallBackgroundItem[2];
         SmallBackground.SmallBackgroundItem[] var6 = var1.children;
         SmallBackground var7 = var1.wThis;
         var6[0] = var7.new SmallBackgroundItem(-1);
         var1.children[0].spriteDrawable = new SpriteDrawable(new TextureAtlas.AtlasSprite(var5));
         var1.children[0].parent = var1;
         var1.children[0].spriteDrawable.getSprite().setScale(var3, var4);
         Sprite var9 = var1.children[0].spriteDrawable.getSprite();
         var9.setOrigin(var9.getWidth(), var9.getHeight());
         Color var10 = var9.getColor();
         var10.a = 0.7F;
         var9.setColor(var10);
         BGAction.swim(var1.children[0], 30.0F);
         BGAction.hasParent(var1.children[0], var1, -18.0F, 153.0F);
         SmallBackground.SmallBackgroundItem[] var11 = var1.children;
         SmallBackground var12 = var1.wThis;
         var11[1] = var12.new SmallBackgroundItem(-1);
         var1.children[1].spriteDrawable = new SpriteDrawable(new TextureAtlas.AtlasSprite(var5));
         var1.children[1].spriteDrawable.getSprite().flip(true, false);
         var1.children[1].parent = var1;
         var1.children[1].spriteDrawable.getSprite().setScale(var3, var4);
         Sprite var14 = var1.children[1].spriteDrawable.getSprite();
         var14.setOrigin(0.0F, var14.getHeight());
         Color var15 = var14.getColor();
         var15.a = 0.7F;
         var14.setColor(var15);
         BGAction.swim(var1.children[1], 30.0F);
         BGAction.hasParent(var1.children[1], var1, 48.0F, 153.0F);
      }
   }

   public static class asteroidFixer implements BGfixer {

      public void addActionsAndFix(SmallBackground.SmallBackgroundItem var1) {
         BGAction.lineMove(var1, -32.0F);
      }
   }
}
