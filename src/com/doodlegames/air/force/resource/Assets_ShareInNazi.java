package com.doodlegames.air.force.resource;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.doodlegames.air.force.screen.LoadingScreen;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.RegionUtilFunctions;
import com.doodlegames.air.force.utils.Settings;

public class Assets_ShareInNazi {

   public static TextureAtlas atlas;
   private static final String atlasFilePath = "bullets/naziEffect/naziEffect.atlas";
   public static Animation bigBoom2;
   public static Animation bullet1;
   public static Animation bullet2;
   public static Animation gather;
   public static final float gatherOriginX = 31.0F;
   public static final float gatherOriginY = 49.0F;
   public static Animation hitBoom;
   private static int index = 0;
   public static LoadingScreen.LoadingInfo info = new LoadingScreen.LoadingInfo() {
      public void fillAssetManager(AssetManager var1) {
         var1.load("bullets/naziEffect/naziEffect.atlas", TextureAtlas.class);
      }
      public Screen loadingFinished(Game var1, AssetManager var2) {
         try {
            Assets_ShareInNazi.atlas = (TextureAtlas)var2.get("bullets/naziEffect/naziEffect.atlas", TextureAtlas.class);
            return null;
         } catch (Throwable var7) {
            Settings.w(var7.toString());
            Assets_ShareInNazi.atlas = null;
         } finally {
            Assets_ShareInNazi.load();
         }

         return null;
      }
   };
   public static Animation laseAnimationAll;
   public static Animation laseAnimationHalf;
   public static Animation laseAnimationQuart;
   public static Animation laseHeadAnimationAll;
   public static Animation laseHeadAnimationHalf;
   public static Animation laseHeadAnimationQuart;
   public static Animation shatter1;
   public static Animation shatter2;
   public static Animation smallBoom1;
   public static Animation smallBoom2;
   private static float stateTime = 0.0F;


   public static void drawTest(SpriteBatch var0, float var1) {
      Animation[] var2 = new Animation[]{bigBoom2, shatter1, shatter2, smallBoom1, smallBoom2, bullet1, bullet2, gather, hitBoom};
      TextureRegion var3 = var2[index].getKeyFrame(stateTime);
      float var4 = (float)RegionUtilFunctions.getRegionWidth(var3);
      float var5 = (float)RegionUtilFunctions.getRegionHeight(var3);
      RegionUtilFunctions.draw(var0, var3, 200.0F, 200.0F, var4 / 1.0F, var5 / 1.0F);
      stateTime += var1;
      if(stateTime > var2[index].animationTime) {
         ++index;
         stateTime = 0.0F;
         if(index >= var2.length) {
            index = 0;
         }
      }

   }

   public static Animation getAnimation(final NaziAnimation naziAnimation) {

       switch (naziAnimation) {

           case bigBoom2: {

               return Assets_ShareInNazi.bigBoom2;

           }

           case shatter1: {

               return Assets_ShareInNazi.shatter1;

           }

           case shatter2: {

               return Assets_ShareInNazi.shatter2;

           }

           case smallBoom1: {

               return Assets_ShareInNazi.smallBoom1;

           }

           case smallBoom2: {

               return Assets_ShareInNazi.smallBoom2;

           }

           case bullet1: {

               return Assets_ShareInNazi.bullet1;

           }

           case bullet2: {

               return Assets_ShareInNazi.bullet2;

           }

           case gather: {

               return Assets_ShareInNazi.gather;

           }

           case hitBoom: {

               return Assets_ShareInNazi.hitBoom;

           }

           default: {

               return null;

           }

       }

   }

   

   public static Animation getRailGun(final NaziRailGun naziRailGun) {

       switch (naziRailGun) {

           case railGunAll: {

               return Assets_ShareInNazi.laseAnimationAll;

           }

           case railGunHalf: {

               return Assets_ShareInNazi.laseAnimationHalf;

           }

           case railGunQuart: {

               return Assets_ShareInNazi.laseAnimationQuart;

           }

           default: {

               return null;

           }

       }

   }

   public static boolean isNaziGatherAnimation(Animation var0) {
      return var0 == gather;
   }

   public static void load() {
      if(atlas == null) {
         Settings.w("assets load fail");
         atlas = new TextureAtlas(Gdx.files.internal("bullets/naziEffect/naziEffect.atlas"));
      }

      loadBigBoom2();
      loadShatter1();
      loadShatter2();
      loadSmallBoom1();
      loadSmallBoom2();
      loadBullet1();
      loadBullet2();
      loadGather();
      loadHitBoom();
      loadRailGun();
   }

   private static void loadBigBoom2() {
      if(bigBoom2 == null) {
         TextureRegion[] var1 = new TextureRegion[]{atlas.findRegion("nazi_big_boom_2", 0), atlas.findRegion("nazi_big_boom_2", 1), atlas.findRegion("nazi_big_boom_2", 2), atlas.findRegion("nazi_big_boom_2", 3), atlas.findRegion("nazi_big_boom_2", 4), atlas.findRegion("nazi_big_boom_2", 5), atlas.findRegion("nazi_big_boom_2", 6), atlas.findRegion("nazi_big_boom_2", 7), atlas.findRegion("nazi_big_boom_2", 8), atlas.findRegion("nazi_big_boom_2", 9)};
         bigBoom2 = new Animation(1, 0.055555556F, var1);
      } else {
         for(int var0 = 0; var0 < 10; ++var0) {
            bigBoom2.setKeyFrame(var0, atlas.findRegion("nazi_big_boom_2", var0));
         }
      }

   }

   private static void loadBullet1() {
      if(bullet1 == null) {
         TextureRegion[] var0 = new TextureRegion[]{atlas.findRegion("nazi_bullet_1", 0), atlas.findRegion("nazi_bullet_1", 1)};
         bullet1 = new Animation(0, 0.083333336F, var0);
      } else {
         bullet1.setKeyFrame(0, atlas.findRegion("nazi_bullet_1", 0));
         bullet1.setKeyFrame(1, atlas.findRegion("nazi_bullet_1", 1));
      }
   }

   private static void loadBullet2() {
      if(bullet2 == null) {
         TextureRegion[] var0 = new TextureRegion[]{atlas.findRegion("nazi_bullet_2", 0), atlas.findRegion("nazi_bullet_2", 1)};
         bullet2 = new Animation(0, 0.083333336F, var0);
      } else {
         bullet2.setKeyFrame(0, atlas.findRegion("nazi_bullet_2", 0));
         bullet2.setKeyFrame(1, atlas.findRegion("nazi_bullet_2", 1));
      }
   }

   private static void loadGather() {
      if(gather == null) {
         TextureRegion[] var1 = new TextureRegion[]{atlas.findRegion("nazi_railgun_gather", 0), atlas.findRegion("nazi_railgun_gather", 1), atlas.findRegion("nazi_railgun_gather", 2), atlas.findRegion("nazi_railgun_gather", 3), atlas.findRegion("nazi_railgun_gather", 4), atlas.findRegion("nazi_railgun_gather", 5)};
         gather = new Animation(0, 0.083333336F, var1);
      } else {
         for(int var0 = 0; var0 < 6; ++var0) {
            gather.setKeyFrame(var0, atlas.findRegion("nazi_railgun_gather", var0));
         }
      }

   }

   private static void loadHitBoom() {
      Animation var0 = hitBoom;
      TextureRegion[] var1 = new TextureRegion[]{atlas.findRegion("nazi_bullet_hit", 0), atlas.findRegion("nazi_bullet_hit", 1), atlas.findRegion("nazi_bullet_hit", 2), atlas.findRegion("nazi_bullet_hit", 3)};
      hitBoom = Animation.setAnimationFrame(var0, 1, 0.055555556F, var1);
   }

   private static void loadRailGun() {
      Animation var0 = laseAnimationAll;
      TextureRegion[] var1 = new TextureRegion[]{atlas.findRegion("nazi_railgun_all_body", 0), atlas.findRegion("nazi_railgun_all_body", 1)};
      laseAnimationAll = Animation.setAnimationFrame(var0, 0, 0.1F, var1);
      Animation var2 = laseAnimationHalf;
      TextureRegion[] var3 = new TextureRegion[]{atlas.findRegion("nazi_railgun_half_body", 0), atlas.findRegion("nazi_railgun_half_body", 1)};
      laseAnimationHalf = Animation.setAnimationFrame(var2, 0, 0.1F, var3);
      Animation var4 = laseAnimationQuart;
      TextureRegion[] var5 = new TextureRegion[]{atlas.findRegion("nazi_railgun_quarter_body", 0), atlas.findRegion("nazi_railgun_quarter_body", 1)};
      laseAnimationQuart = Animation.setAnimationFrame(var4, 0, 0.1F, var5);
      Animation var6 = laseHeadAnimationAll;
      TextureRegion[] var7 = new TextureRegion[]{atlas.findRegion("nazi_railgun_all_head", 0), atlas.findRegion("nazi_railgun_all_head", 1)};
      laseHeadAnimationAll = Animation.setAnimationFrame(var6, 0, 0.1F, var7);
      Animation var8 = laseHeadAnimationHalf;
      TextureRegion[] var9 = new TextureRegion[]{atlas.findRegion("nazi_railgun_half_head", 0), atlas.findRegion("nazi_railgun_half_head", 1)};
      laseHeadAnimationHalf = Animation.setAnimationFrame(var8, 0, 0.1F, var9);
      Animation var10 = laseHeadAnimationQuart;
      TextureRegion[] var11 = new TextureRegion[]{atlas.findRegion("nazi_railgun_quarter_head", 0), atlas.findRegion("nazi_railgun_quarter_head", 1)};
      laseHeadAnimationQuart = Animation.setAnimationFrame(var10, 0, 0.1F, var11);
   }

   private static void loadShatter1() {
      if(shatter1 == null) {
         TextureRegion[] var1 = new TextureRegion[]{atlas.findRegion("nazi_shatter_1", 0), atlas.findRegion("nazi_shatter_1", 1), atlas.findRegion("nazi_shatter_1", 2), atlas.findRegion("nazi_shatter_1", 3), atlas.findRegion("nazi_shatter_1", 4)};
         shatter1 = new Animation(1, 0.055555556F, var1);
      } else {
         for(int var0 = 0; var0 < 5; ++var0) {
            shatter1.setKeyFrame(var0, atlas.findRegion("nazi_shatter_1", var0));
         }
      }

   }

   private static void loadShatter2() {
      if(shatter2 == null) {
         TextureRegion[] var1 = new TextureRegion[]{atlas.findRegion("nazi_shatter_2", 0), atlas.findRegion("nazi_shatter_2", 1), atlas.findRegion("nazi_shatter_2", 2), atlas.findRegion("nazi_shatter_2", 3), atlas.findRegion("nazi_shatter_2", 4)};
         shatter2 = new Animation(1, 0.055555556F, var1);
      } else {
         for(int var0 = 0; var0 < 5; ++var0) {
            shatter2.setKeyFrame(var0, atlas.findRegion("nazi_shatter_2", var0));
         }
      }

   }

   private static void loadSmallBoom1() {
      if(smallBoom1 == null) {
         TextureRegion[] var1 = new TextureRegion[]{atlas.findRegion("nazi_small_boom_1", 0), atlas.findRegion("nazi_small_boom_1", 1), atlas.findRegion("nazi_small_boom_1", 2), atlas.findRegion("nazi_small_boom_1", 3), atlas.findRegion("nazi_small_boom_1", 4), atlas.findRegion("nazi_small_boom_1", 5)};
         smallBoom1 = new Animation(1, 0.055555556F, var1);
      } else {
         for(int var0 = 0; var0 < 6; ++var0) {
            smallBoom1.setKeyFrame(var0, atlas.findRegion("nazi_small_boom_1", var0));
         }
      }

   }

   private static void loadSmallBoom2() {
      if(smallBoom2 == null) {
         TextureRegion[] var1 = new TextureRegion[]{atlas.findRegion("nazi_small_boom_2", 0), atlas.findRegion("nazi_small_boom_2", 1), atlas.findRegion("nazi_small_boom_2", 2), atlas.findRegion("nazi_small_boom_2", 3), atlas.findRegion("nazi_small_boom_2", 4), atlas.findRegion("nazi_small_boom_2", 5)};
         smallBoom2 = new Animation(1, 0.055555556F, var1);
      } else {
         for(int var0 = 0; var0 < 6; ++var0) {
            smallBoom2.setKeyFrame(var0, atlas.findRegion("nazi_small_boom_2", var0));
         }
      }

   }

   public static enum NaziRailGun {

      railGunAll,
      railGunHalf,
      railGunQuart,
   }

   public static enum NaziAnimation {

      bigBoom1,
      bigBoom2,
      bullet1,
      bullet2,
      gather,
      hitBoom,
      shatter1,
      shatter2,
      smallBoom1,
      smallBoom2,
      smallBoom3,
   }
}
