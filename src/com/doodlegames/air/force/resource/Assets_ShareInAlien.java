package com.doodlegames.air.force.resource;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.doodlegames.air.force.screen.LoadingScreen;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Settings;

public class Assets_ShareInAlien {

   public static TextureAtlas atlas;
   private static final String atlasFilePath = "bullets/alienEffect/alienEffect.atlas";
   public static Animation bigBoom1;
   public static Animation bullet1;
   public static Animation bullet2;
   public static Animation gather;
   public static final int gatherOriginX = 31;
   public static final int gatherOriginY = 49;
   public static Animation hitBoom;
   public static LoadingScreen.LoadingInfo info = new LoadingScreen.LoadingInfo() {
      public void fillAssetManager(AssetManager var1) {
         var1.load("bullets/alienEffect/alienEffect.atlas", TextureAtlas.class);
      }
      public Screen loadingFinished(Game var1, AssetManager var2) {
         try {
            Assets_ShareInAlien.atlas = (TextureAtlas)var2.get("bullets/alienEffect/alienEffect.atlas", TextureAtlas.class);
            return null;
         } catch (Throwable var7) {
            Settings.w(var7.toString());
            Assets_ShareInAlien.atlas = null;
         } finally {
            Assets_ShareInAlien.load();
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
   public static Animation smallBoom2;
   public static Animation smallBoom3;


   public static Animation getAnimation(final AlienAnimation alienAnimation) {

       switch (alienAnimation) {

           case bigBoom1: {

               return Assets_ShareInAlien.bigBoom1;

           }

           case shatter1: {

               return Assets_ShareInAlien.shatter1;

           }

           case shatter2: {

               return Assets_ShareInAlien.shatter2;

           }

           case smallBoom2: {

               return Assets_ShareInAlien.smallBoom2;

           }

           case smallBoom3: {

               return Assets_ShareInAlien.smallBoom3;

           }

           case bullet1: {

               return Assets_ShareInAlien.bullet1;

           }

           case bullet2: {

               return Assets_ShareInAlien.bullet2;

           }

           case gather: {

               return Assets_ShareInAlien.gather;

           }

           case hitBoom: {

               return Assets_ShareInAlien.hitBoom;

           }

           default: {

               return null;

           }

       }

   }

   

   public static Animation getRailGun(final AlienRailGun alienRailGun) {

       switch (alienRailGun) {

           case railGunAll: {

               return Assets_ShareInAlien.laseAnimationAll;

           }

           case railGunHalf: {

               return Assets_ShareInAlien.laseAnimationHalf;

           }

           case railGunQuart: {

               return Assets_ShareInAlien.laseAnimationQuart;

           }

           default: {

               return null;

           }

       }

   }

   public static boolean isALienGatherAnimation(Animation var0) {
      return var0 == gather;
   }

   public static void load() {
      if(atlas == null) {
         Settings.w("assets load fail");
         atlas = new TextureAtlas(Gdx.files.internal("bullets/alienEffect/alienEffect.atlas"));
      }

      loadBigBoom1();
      loadShatter1();
      loadShatter2();
      loadSmallBoom2();
      loadSmallBoom3();
      loadBullet1();
      loadBullet2();
      loadGather();
      loadHitBoom();
      loadRailGun();
   }

   private static void loadBigBoom1() {
      if(bigBoom1 == null) {
         TextureRegion[] var1 = new TextureRegion[]{atlas.findRegion("alien_big_boom_1", 0), atlas.findRegion("alien_big_boom_1", 1), atlas.findRegion("alien_big_boom_1", 2), atlas.findRegion("alien_big_boom_1", 3), atlas.findRegion("alien_big_boom_1", 4), atlas.findRegion("alien_big_boom_1", 5), atlas.findRegion("alien_big_boom_1", 6), atlas.findRegion("alien_big_boom_1", 7), atlas.findRegion("alien_big_boom_1", 8), atlas.findRegion("alien_big_boom_1", 9)};
         bigBoom1 = new Animation(1, 0.055555556F, var1);
      } else {
         for(int var0 = 0; var0 < 10; ++var0) {
            bigBoom1.setKeyFrame(var0, atlas.findRegion("alien_big_boom_1", var0));
         }
      }

   }

   private static void loadBullet1() {
      if(bullet1 == null) {
         TextureRegion[] var0 = new TextureRegion[]{atlas.findRegion("alien_bullet_1", 0), atlas.findRegion("alien_bullet_1", 1)};
         bullet1 = new Animation(0, 0.083333336F, var0);
      } else {
         bullet1.setKeyFrame(0, atlas.findRegion("alien_bullet_1", 0));
         bullet1.setKeyFrame(1, atlas.findRegion("alien_bullet_1", 1));
      }
   }

   private static void loadBullet2() {
      if(bullet2 == null) {
         TextureRegion[] var1 = new TextureRegion[]{atlas.findRegion("alien_bullet_2", 0), atlas.findRegion("alien_bullet_2", 1), atlas.findRegion("alien_bullet_2", 2), atlas.findRegion("alien_bullet_2", 3), atlas.findRegion("alien_bullet_2", 4), atlas.findRegion("alien_bullet_2", 5)};
         bullet2 = new Animation(0, 0.083333336F, var1);
      } else {
         for(int var0 = 0; var0 < 6; ++var0) {
            bullet2.setKeyFrame(var0, atlas.findRegion("alien_bullet_2", var0));
         }
      }

   }

   private static void loadGather() {
      if(gather == null) {
         TextureRegion[] var1 = new TextureRegion[]{atlas.findRegion("alien_railgun_gather", 0), atlas.findRegion("alien_railgun_gather", 1), atlas.findRegion("alien_railgun_gather", 2), atlas.findRegion("alien_railgun_gather", 3), atlas.findRegion("alien_railgun_gather", 4), atlas.findRegion("alien_railgun_gather", 5)};
         gather = new Animation(0, 0.083333336F, var1);
      } else {
         for(int var0 = 0; var0 < 6; ++var0) {
            gather.setKeyFrame(var0, atlas.findRegion("alien_railgun_gather", var0));
         }
      }

   }

   private static void loadHitBoom() {
      if(hitBoom == null) {
         TextureRegion[] var1 = new TextureRegion[]{atlas.findRegion("alien_bullet_hit", 0), atlas.findRegion("alien_bullet_hit", 1), atlas.findRegion("alien_bullet_hit", 2), atlas.findRegion("alien_bullet_hit", 3)};
         hitBoom = new Animation(1, 0.055555556F, var1);
      } else {
         for(int var0 = 0; var0 < 4; ++var0) {
            hitBoom.setKeyFrame(var0, atlas.findRegion("alien_bullet_hit", var0));
         }
      }

   }

   private static void loadRailGun() {
      Animation var0 = laseAnimationAll;
      TextureRegion[] var1 = new TextureRegion[]{atlas.findRegion("alien_railgun_all_body", 0), atlas.findRegion("alien_railgun_all_body", 1)};
      laseAnimationAll = Animation.setAnimationFrame(var0, 0, 0.1F, var1);
      Animation var2 = laseAnimationHalf;
      TextureRegion[] var3 = new TextureRegion[]{atlas.findRegion("alien_railgun_half_body", 0), atlas.findRegion("alien_railgun_half_body", 1)};
      laseAnimationHalf = Animation.setAnimationFrame(var2, 0, 0.1F, var3);
      Animation var4 = laseAnimationQuart;
      TextureRegion[] var5 = new TextureRegion[]{atlas.findRegion("alien_railgun_quarter_body", 0), atlas.findRegion("alien_railgun_quarter_body", 1)};
      laseAnimationQuart = Animation.setAnimationFrame(var4, 0, 0.1F, var5);
      Animation var6 = laseHeadAnimationAll;
      TextureRegion[] var7 = new TextureRegion[]{atlas.findRegion("alien_railgun_all_head", 0), atlas.findRegion("alien_railgun_all_head", 1)};
      laseHeadAnimationAll = Animation.setAnimationFrame(var6, 0, 0.1F, var7);
      Animation var8 = laseHeadAnimationHalf;
      TextureRegion[] var9 = new TextureRegion[]{atlas.findRegion("alien_railgun_half_head", 0), atlas.findRegion("alien_railgun_half_head", 1)};
      laseHeadAnimationHalf = Animation.setAnimationFrame(var8, 0, 0.1F, var9);
      Animation var10 = laseHeadAnimationQuart;
      TextureRegion[] var11 = new TextureRegion[]{atlas.findRegion("alien_railgun_quarter_head", 1), atlas.findRegion("alien_railgun_quarter_head", 0)};
      laseHeadAnimationQuart = Animation.setAnimationFrame(var10, 0, 0.1F, var11);
   }

   private static void loadShatter1() {
      if(shatter1 == null) {
         TextureRegion[] var1 = new TextureRegion[]{atlas.findRegion("alien_shatter_1", 2), atlas.findRegion("alien_shatter_1", 3), atlas.findRegion("alien_shatter_1", 4), atlas.findRegion("alien_shatter_1", 5), atlas.findRegion("alien_shatter_1", 6)};
         shatter1 = new Animation(1, 0.055555556F, var1);
      } else {
         for(int var0 = 0; var0 < 5; ++var0) {
            shatter1.setKeyFrame(var0, atlas.findRegion("alien_shatter_1", var0 + 2));
         }
      }

   }

   private static void loadShatter2() {
      if(shatter2 == null) {
         TextureRegion[] var1 = new TextureRegion[]{atlas.findRegion("alien_shatter_2", 2), atlas.findRegion("alien_shatter_2", 3), atlas.findRegion("alien_shatter_2", 4), atlas.findRegion("alien_shatter_2", 5), atlas.findRegion("alien_shatter_2", 6)};
         shatter2 = new Animation(1, 0.055555556F, var1);
      } else {
         for(int var0 = 0; var0 < 5; ++var0) {
            shatter2.setKeyFrame(var0, atlas.findRegion("alien_shatter_2", var0 + 2));
         }
      }

   }

   private static void loadSmallBoom2() {
      if(smallBoom2 == null) {
         TextureRegion[] var1 = new TextureRegion[]{atlas.findRegion("alien_small_boom_2", 0), atlas.findRegion("alien_small_boom_2", 1), atlas.findRegion("alien_small_boom_2", 2), atlas.findRegion("alien_small_boom_2", 3), atlas.findRegion("alien_small_boom_2", 4), atlas.findRegion("alien_small_boom_2", 5)};
         smallBoom2 = new Animation(1, 0.055555556F, var1);
      } else {
         for(int var0 = 0; var0 < 6; ++var0) {
            smallBoom2.setKeyFrame(var0, atlas.findRegion("alien_small_boom_2", var0));
         }
      }

   }

   private static void loadSmallBoom3() {
      if(smallBoom3 == null) {
         TextureRegion[] var1 = new TextureRegion[]{atlas.findRegion("alien_small_boom_3", 0), atlas.findRegion("alien_small_boom_3", 1), atlas.findRegion("alien_small_boom_3", 2), atlas.findRegion("alien_small_boom_3", 3), atlas.findRegion("alien_small_boom_3", 4), atlas.findRegion("alien_small_boom_3", 5)};
         smallBoom3 = new Animation(1, 0.055555556F, var1);
      } else {
         for(int var0 = 0; var0 < 6; ++var0) {
            smallBoom3.setKeyFrame(var0, atlas.findRegion("alien_small_boom_3", var0));
         }
      }

   }

   public static enum AlienAnimation {

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

   public static enum AlienRailGun {

      railGunAll,
      railGunHalf,
      railGunQuart
   }
}
