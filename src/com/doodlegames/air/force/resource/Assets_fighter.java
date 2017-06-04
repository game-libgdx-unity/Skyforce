package com.doodlegames.air.force.resource;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.doodlegames.air.force.screen.LoadingScreen;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

public class Assets_fighter {

   private static final String[] atlasBoomFileString = new String[]{"fighter/F22boom.atlas", "fighter/j20boom.atlas", "fighter/Rafaleboom.atlas", "fighter/su47boom.atlas", "fighter/typhoonboom.atlas", "fighter/me262boom.atlas"};
   private static final String[] atlasFileString = new String[]{"fighter/F22.atlas", "fighter/j20.atlas", "fighter/Rafale.atlas", "fighter/su47.atlas", "fighter/typhoon.atlas", "fighter/me262.atlas"};
   public static TextureAtlas atlas_f22;
   public static TextureAtlas atlas_fighter;
   public static TextureAtlas atlas_fighterBoom;
   public static TextureAtlas atlas_j20;
   public static TextureAtlas atlas_me262;
   public static TextureAtlas atlas_rafale;
   public static TextureAtlas atlas_su47;
   public static TextureAtlas atlas_typhoon;
   public static final float bulletFTime = 0.0F;
   public static Animation f22Bullet1;
   public static Animation f22Bullet2;
   public static Animation f22Bullet3;
   public static TextureAtlas.AtlasRegion f22BulletOld1;
   public static TextureAtlas.AtlasRegion f22BulletOld2;
   public static TextureAtlas.AtlasRegion f22BulletOld4;
   public static Animation f22FlyingToLeft;
   public static Animation f22FlyingToRight;
   public static Animation f22Shatter;
   public static Animation f22Stand;
   private static Animation fighterBoomAnimation = null;
   public static final float fighterFrameTime = 0.05F;
   public static int fighterIndex = 0;
   public static final int fighterTurnFrameCount = 4;
   public static LoadingScreen.LoadingInfo info = new LoadingScreen.LoadingInfo() {
      public void fillAssetManager(AssetManager var1) {
         if(Assets_fighter.fighterIndex < 0 || Assets_fighter.fighterIndex >= Assets_fighter.atlasFileString.length) {
            Assets_fighter.fighterIndex = 0;
            Settings.w("invalid fighterIndex: " + Assets_fighter.fighterIndex);
         }

         var1.load(Assets_fighter.atlasFileString[Assets_fighter.fighterIndex], TextureAtlas.class);
         if(Assets_fighter.fighterIndex != 1) {
            var1.load(Assets_fighter.atlasBoomFileString[Assets_fighter.fighterIndex], TextureAtlas.class);
         }

      }
      public Screen loadingFinished(Game var1, AssetManager var2) {
         try {
            Assets_fighter.atlas_fighter = (TextureAtlas)var2.get(Assets_fighter.atlasFileString[Assets_fighter.fighterIndex], TextureAtlas.class);
            if(Assets_fighter.fighterIndex != 1) {
               Assets_fighter.atlas_fighterBoom = (TextureAtlas)var2.get(Assets_fighter.atlasBoomFileString[Assets_fighter.fighterIndex], TextureAtlas.class);
            }

            return null;
         } catch (Throwable var7) {
            Assets_fighter.atlas_fighter = null;
            Settings.w(var7.toString());
         } finally {
            Assets_fighter.load(0);
         }

         return null;
      }
   };
   public static Animation j20BoomAnimation;
   public static Animation j20Bullet2;
   public static Animation j20Bullet3;
   public static Animation j20Bullet6;
   public static TextureAtlas.AtlasRegion j20BulletOld2;
   public static TextureAtlas.AtlasRegion j20BulletOld3;
   public static TextureAtlas.AtlasRegion j20BulletOld5;
   public static Animation j20FlyingToLeft;
   public static Animation j20FlyingToRight;
   public static Animation j20Shatter;
   public static Animation j20Stand;
   private static Animation laseHead;
   public static Animation me262BoomAnimation;
   public static Animation me262Bullet1;
   public static Animation me262Bullet2;
   public static Animation me262Bullet4;
   public static TextureAtlas.AtlasRegion me262BulletOld1;
   public static TextureAtlas.AtlasRegion me262BulletOld2;
   public static TextureAtlas.AtlasRegion me262BulletOld4;
   public static Animation me262FlyingToLeft;
   public static Animation me262FlyingToRight;
   private static Animation me262Lase3;
   private static Animation me262Lase6;
   private static TextureAtlas.AtlasRegion me262RailGun3;
   private static TextureAtlas.AtlasRegion me262RailGun6;
   public static Animation me262Shatter;
   public static Animation me262Stand;
   public static Animation rafaleBoomAnimation;
   public static Animation rafaleBullet1;
   public static Animation rafaleBullet10;
   public static Animation rafaleBullet3;
   public static TextureAtlas.AtlasRegion rafaleBulletOld1;
   public static TextureAtlas.AtlasRegion rafaleBulletOld3;
   public static TextureAtlas.AtlasRegion rafaleBulletOld5;
   public static Animation rafaleFlyingToLeft;
   public static Animation rafaleFlyingToRight;
   public static Animation rafaleShatter;
   public static Animation rafaleStand;
   public static Animation su47BoomAnimation;
   public static Animation su47Bullet1;
   public static Animation su47Bullet3;
   public static Animation su47Bullet5;
   public static TextureAtlas.AtlasRegion su47BulletOld1;
   public static TextureAtlas.AtlasRegion su47BulletOld2;
   public static TextureAtlas.AtlasRegion su47BulletOld5;
   public static Animation su47FlyingToLeft;
   public static Animation su47FlyingToRight;
   public static Animation su47Shatter;
   public static Animation su47Stand;
   public static Animation typhoonBoomAnimation;
   public static Animation typhoonBullet1;
   public static Animation typhoonBullet2;
   public static Animation typhoonBullet5;
   public static TextureAtlas.AtlasRegion typhoonBulletOld1;
   public static TextureAtlas.AtlasRegion typhoonBulletOld2;
   public static TextureAtlas.AtlasRegion typhoonBulletOld6;
   public static Animation typhoonFlyingToLeft;
   public static Animation typhoonFlyingToRight;
   public static Animation typhoonShatter;
   public static Animation typhoonStand;


   public static Animation getFighterBoomAnimation() {
      return fighterBoomAnimation;
   }

   public static Animation getLaseHeadAnimation() {
      return laseHead;
   }

   public static TextureRegion getMe262RailGun3() {
      return me262Lase3.getKeyFrame(Clock.getCurrentTime());
   }

   public static TextureRegion getMe262RailGun6() {
      return me262Lase6.getKeyFrame(Clock.getCurrentTime());
   }

   public static void load(int var0) {
      if(atlas_fighter == null) {
         Settings.w("Assets Load failed.");
         atlas_fighter = new TextureAtlas(Gdx.files.internal(atlasFileString[fighterIndex]));
         if(fighterIndex != 1) {
            atlas_fighterBoom = new TextureAtlas(Gdx.files.internal(atlasBoomFileString[fighterIndex]));
         }
      }

      String[] var1 = new String[]{"f22_boom", "j20_boom", "rafal_boom", "su47_boom", "typhoon_boom", "me262_boom"};
      fighterBoomAnimation = null;
      if(fighterIndex == 0 || fighterIndex == 4) {
         Animation var2 = fighterBoomAnimation;
         TextureRegion[] var3 = new TextureRegion[]{atlas_fighterBoom.findRegion(var1[fighterIndex], 0), atlas_fighterBoom.findRegion(var1[fighterIndex], 1), atlas_fighterBoom.findRegion(var1[fighterIndex], 2), atlas_fighterBoom.findRegion(var1[fighterIndex], 3), atlas_fighterBoom.findRegion(var1[fighterIndex], 4), atlas_fighterBoom.findRegion(var1[fighterIndex], 5), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 7), atlas_fighterBoom.findRegion(var1[fighterIndex], 8), atlas_fighterBoom.findRegion(var1[fighterIndex], 9), atlas_fighterBoom.findRegion(var1[fighterIndex], 10), atlas_fighterBoom.findRegion(var1[fighterIndex], 11)};
         fighterBoomAnimation = Animation.setAnimationFrame(var2, 1, 0.041666668F, var3);
      }

      if(fighterIndex == 2) {
         Animation var8 = fighterBoomAnimation;
         TextureRegion[] var9 = new TextureRegion[]{atlas_fighterBoom.findRegion(var1[fighterIndex], 0), atlas_fighterBoom.findRegion(var1[fighterIndex], 1), atlas_fighterBoom.findRegion(var1[fighterIndex], 2)};
         fighterBoomAnimation = Animation.setAnimationFrame(var8, 0, 0.083333336F, var9);
      }

      if(fighterIndex == 3) {
         Animation var6 = fighterBoomAnimation;
         TextureRegion[] var7 = new TextureRegion[]{atlas_fighterBoom.findRegion(var1[fighterIndex], 0), atlas_fighterBoom.findRegion(var1[fighterIndex], 1), atlas_fighterBoom.findRegion(var1[fighterIndex], 2), atlas_fighterBoom.findRegion(var1[fighterIndex], 3), atlas_fighterBoom.findRegion(var1[fighterIndex], 4), atlas_fighterBoom.findRegion(var1[fighterIndex], 5), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 7), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 5), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 7), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 5), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 7), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 5), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 7), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 5), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 7), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 5), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 7), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 5), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 7), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 5), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 7), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 5), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 7), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 5), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 7), atlas_fighterBoom.findRegion(var1[fighterIndex], 8), atlas_fighterBoom.findRegion(var1[fighterIndex], 9), atlas_fighterBoom.findRegion(var1[fighterIndex], 10), atlas_fighterBoom.findRegion(var1[fighterIndex], 11)};
         fighterBoomAnimation = Animation.setAnimationFrame(var6, 1, 0.055555556F, var7);
      }

      if(fighterIndex == 5) {
         Animation var4 = fighterBoomAnimation;
         TextureRegion[] var5 = new TextureRegion[]{atlas_fighterBoom.findRegion(var1[fighterIndex], 0), atlas_fighterBoom.findRegion(var1[fighterIndex], 1), atlas_fighterBoom.findRegion(var1[fighterIndex], 2), atlas_fighterBoom.findRegion(var1[fighterIndex], 3), atlas_fighterBoom.findRegion(var1[fighterIndex], 4), atlas_fighterBoom.findRegion(var1[fighterIndex], 5), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 7), atlas_fighterBoom.findRegion(var1[fighterIndex], 8), atlas_fighterBoom.findRegion(var1[fighterIndex], 9), atlas_fighterBoom.findRegion(var1[fighterIndex], 10), atlas_fighterBoom.findRegion(var1[fighterIndex], 10), atlas_fighterBoom.findRegion(var1[fighterIndex], 9), atlas_fighterBoom.findRegion(var1[fighterIndex], 8), atlas_fighterBoom.findRegion(var1[fighterIndex], 7), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 5), atlas_fighterBoom.findRegion(var1[fighterIndex], 4), atlas_fighterBoom.findRegion(var1[fighterIndex], 3), atlas_fighterBoom.findRegion(var1[fighterIndex], 3), atlas_fighterBoom.findRegion(var1[fighterIndex], 4), atlas_fighterBoom.findRegion(var1[fighterIndex], 5), atlas_fighterBoom.findRegion(var1[fighterIndex], 6), atlas_fighterBoom.findRegion(var1[fighterIndex], 7), atlas_fighterBoom.findRegion(var1[fighterIndex], 8), atlas_fighterBoom.findRegion(var1[fighterIndex], 9), atlas_fighterBoom.findRegion(var1[fighterIndex], 10), atlas_fighterBoom.findRegion(var1[fighterIndex], 11)};
         fighterBoomAnimation = Animation.setAnimationFrame(var4, 1, 0.041666668F, var5);
      }

      switch(fighterIndex) {
      case 0:
         atlas_f22 = atlas_fighter;
         loadF22();
         return;
      case 1:
         atlas_j20 = atlas_fighter;
         loadJ20();
         return;
      case 2:
         atlas_rafale = atlas_fighter;
         loadRafale();
         return;
      case 3:
         atlas_su47 = atlas_fighter;
         loadSu47();
         return;
      case 4:
         atlas_typhoon = atlas_fighter;
         loadTyphoon();
         return;
      case 5:
         atlas_me262 = atlas_fighter;
         loadMe262();
         return;
      default:
         Settings.e("sever internal error: invalid fighterIndex.!!!");
      }
   }

   private static void loadF22() {
      Animation var0 = f22Stand;
      TextureRegion[] var1 = new TextureRegion[]{atlas_f22.findRegion("air_stand", 0), atlas_f22.findRegion("air_stand", 1)};
      f22Stand = Animation.setAnimationFrame(var0, 0, 0.05F, var1);
      Animation var2 = f22FlyingToLeft;
      TextureRegion[] var3 = new TextureRegion[]{atlas_f22.findRegion("air_left", 0), atlas_f22.findRegion("air_left", 1), atlas_f22.findRegion("air_left", 2), atlas_f22.findRegion("air_left", 3)};
      f22FlyingToLeft = Animation.setAnimationFrame(var2, 1, 0.05F, var3);
      Animation var4 = f22FlyingToRight;
      TextureRegion[] var5 = new TextureRegion[]{atlas_f22.findRegion("air_right", 0), atlas_f22.findRegion("air_right", 1), atlas_f22.findRegion("air_right", 2), atlas_f22.findRegion("air_right", 3)};
      f22FlyingToRight = Animation.setAnimationFrame(var4, 1, 0.05F, var5);
      f22BulletOld1 = atlas_f22.findRegion("f22_bullet_force1");
      f22BulletOld2 = atlas_f22.findRegion("f22_bullet_force2");
      f22BulletOld4 = atlas_f22.findRegion("f22_bullet_force4");
      Animation var6 = f22Shatter;
      TextureRegion[] var7 = new TextureRegion[]{atlas_f22.findRegion("f22_shatter", 0), atlas_f22.findRegion("f22_shatter", 1), atlas_f22.findRegion("f22_shatter", 2), atlas_f22.findRegion("f22_shatter", 3), atlas_f22.findRegion("f22_shatter", 4)};
      f22Shatter = Animation.setAnimationFrame(var6, 1, 0.1F, var7);
      Animation var8 = f22Bullet1;
      TextureRegion[] var9 = new TextureRegion[]{atlas_f22.findRegion("f22_lv1_bullet", 0), atlas_f22.findRegion("f22_lv1_bullet", 1)};
      f22Bullet1 = Animation.setAnimationFrame(var8, 0, 0.0F, var9);
      Animation var10 = f22Bullet2;
      TextureRegion[] var11 = new TextureRegion[]{atlas_f22.findRegion("f22_lv2_bullet", 0), atlas_f22.findRegion("f22_lv2_bullet", 1)};
      f22Bullet2 = Animation.setAnimationFrame(var10, 0, 0.0F, var11);
      Animation var12 = f22Bullet3;
      TextureRegion[] var13 = new TextureRegion[]{atlas_f22.findRegion("f22_lv3_bullet", 0), atlas_f22.findRegion("f22_lv3_bullet", 1)};
      f22Bullet3 = Animation.setAnimationFrame(var12, 0, 0.0F, var13);
      if(f22FlyingToLeft.getFrameCount() != 4 || f22FlyingToRight.getFrameCount() != 4) {
         throw new RuntimeException("fighter turn animation\'s frameCount must be the same :4");
      }
   }

   private static void loadJ20() {
      if(j20Stand == null) {
         TextureRegion[] var9 = new TextureRegion[]{atlas_j20.findRegion("j20_stand", 0), atlas_j20.findRegion("j20_stand", 1)};
         j20Stand = new Animation(0, 0.05F, var9);
      } else {
         j20Stand.setKeyFrame(0, atlas_j20.findRegion("j20_stand", 0));
         j20Stand.setKeyFrame(1, atlas_j20.findRegion("j20_stand", 1));
      }

      if(j20FlyingToLeft == null) {
         TextureRegion[] var8 = new TextureRegion[]{atlas_j20.findRegion("j20_left", 0), atlas_j20.findRegion("j20_left", 1), atlas_j20.findRegion("j20_left", 2), atlas_j20.findRegion("j20_left", 3)};
         j20FlyingToLeft = new Animation(1, 0.05F, var8);
      } else {
         j20FlyingToLeft.setKeyFrame(0, atlas_j20.findRegion("j20_left", 0));
         j20FlyingToLeft.setKeyFrame(1, atlas_j20.findRegion("j20_left", 1));
         j20FlyingToLeft.setKeyFrame(2, atlas_j20.findRegion("j20_left", 2));
         j20FlyingToLeft.setKeyFrame(3, atlas_j20.findRegion("j20_left", 3));
      }

      if(j20FlyingToRight == null) {
         TextureRegion[] var7 = new TextureRegion[]{atlas_j20.findRegion("j20_right", 0), atlas_j20.findRegion("j20_right", 1), atlas_j20.findRegion("j20_right", 2), atlas_j20.findRegion("j20_right", 3)};
         j20FlyingToRight = new Animation(1, 0.05F, var7);
      } else {
         j20FlyingToRight.setKeyFrame(0, atlas_j20.findRegion("j20_right", 0));
         j20FlyingToRight.setKeyFrame(1, atlas_j20.findRegion("j20_right", 1));
         j20FlyingToRight.setKeyFrame(2, atlas_j20.findRegion("j20_right", 2));
         j20FlyingToRight.setKeyFrame(3, atlas_j20.findRegion("j20_right", 3));
      }

      j20BulletOld2 = atlas_j20.findRegion("j20_bullet_force2");
      j20BulletOld3 = atlas_j20.findRegion("j20_bullet_force3");
      j20BulletOld5 = atlas_j20.findRegion("j20_bullet_force5");
      if(j20Shatter == null) {
         TextureRegion[] var6 = new TextureRegion[]{atlas_j20.findRegion("j20_shatter", 0), atlas_j20.findRegion("j20_shatter", 1), atlas_j20.findRegion("j20_shatter", 2), atlas_j20.findRegion("j20_shatter", 3), atlas_j20.findRegion("j20_shatter", 4)};
         j20Shatter = new Animation(1, 0.1F, var6);
      } else {
         j20Shatter.setKeyFrame(0, atlas_j20.findRegion("j20_shatter", 0));
         j20Shatter.setKeyFrame(1, atlas_j20.findRegion("j20_shatter", 1));
         j20Shatter.setKeyFrame(2, atlas_j20.findRegion("j20_shatter", 2));
         j20Shatter.setKeyFrame(3, atlas_j20.findRegion("j20_shatter", 3));
         j20Shatter.setKeyFrame(4, atlas_j20.findRegion("j20_shatter", 4));
      }

      Animation var0 = j20Bullet2;
      TextureRegion[] var1 = new TextureRegion[]{atlas_j20.findRegion("j20_lv1_bullet", 0), atlas_j20.findRegion("j20_lv1_bullet", 1)};
      j20Bullet2 = Animation.setAnimationFrame(var0, 0, 0.0F, var1);
      Animation var2 = j20Bullet3;
      TextureRegion[] var3 = new TextureRegion[]{atlas_j20.findRegion("j20_lv2_bullet", 0), atlas_j20.findRegion("j20_lv2_bullet", 1)};
      j20Bullet3 = Animation.setAnimationFrame(var2, 0, 0.0F, var3);
      Animation var4 = j20Bullet6;
      TextureRegion[] var5 = new TextureRegion[]{atlas_j20.findRegion("j20_lv3_bullet", 0), atlas_j20.findRegion("j20_lv3_bullet", 1)};
      j20Bullet6 = Animation.setAnimationFrame(var4, 0, 0.0F, var5);
      if(j20FlyingToLeft.getFrameCount() != 4 || j20FlyingToRight.getFrameCount() != 4) {
         throw new RuntimeException("fighter turn animation\'s frameCount must be the same :4");
      }
   }

   private static void loadMe262() {
      if(me262Stand == null) {
         TextureRegion[] var15 = new TextureRegion[]{atlas_me262.findRegion("me262_stand", 0), atlas_me262.findRegion("me262_stand", 1)};
         me262Stand = new Animation(0, 0.05F, var15);
      } else {
         me262Stand.setKeyFrame(0, atlas_me262.findRegion("me262_stand", 0));
         me262Stand.setKeyFrame(1, atlas_me262.findRegion("me262_stand", 1));
      }

      if(me262FlyingToLeft == null) {
         TextureRegion[] var14 = new TextureRegion[]{atlas_me262.findRegion("me262_left", 0), atlas_me262.findRegion("me262_left", 1), atlas_me262.findRegion("me262_left", 2), atlas_me262.findRegion("me262_left", 3)};
         me262FlyingToLeft = new Animation(1, 0.05F, var14);
      } else {
         me262FlyingToLeft.setKeyFrame(0, atlas_me262.findRegion("me262_left", 0));
         me262FlyingToLeft.setKeyFrame(1, atlas_me262.findRegion("me262_left", 1));
         me262FlyingToLeft.setKeyFrame(2, atlas_me262.findRegion("me262_left", 2));
         me262FlyingToLeft.setKeyFrame(3, atlas_me262.findRegion("me262_left", 3));
      }

      if(me262FlyingToRight == null) {
         TextureRegion[] var13 = new TextureRegion[]{atlas_me262.findRegion("me262_right", 0), atlas_me262.findRegion("me262_right", 1), atlas_me262.findRegion("me262_right", 2), atlas_me262.findRegion("me262_right", 3)};
         me262FlyingToRight = new Animation(1, 0.05F, var13);
      } else {
         me262FlyingToRight.setKeyFrame(0, atlas_me262.findRegion("me262_right", 0));
         me262FlyingToRight.setKeyFrame(1, atlas_me262.findRegion("me262_right", 1));
         me262FlyingToRight.setKeyFrame(2, atlas_me262.findRegion("me262_right", 2));
         me262FlyingToRight.setKeyFrame(3, atlas_me262.findRegion("me262_right", 3));
      }

      me262BulletOld1 = atlas_me262.findRegion("me262_bullet_force1");
      me262BulletOld2 = atlas_me262.findRegion("me262_bullet_force2");
      me262BulletOld4 = atlas_me262.findRegion("me262_bullet_force4");
      if(me262Shatter == null) {
         TextureRegion[] var12 = new TextureRegion[]{atlas_me262.findRegion("me262_shatter", 0), atlas_me262.findRegion("me262_shatter", 1), atlas_me262.findRegion("me262_shatter", 2), atlas_me262.findRegion("me262_shatter", 3), atlas_me262.findRegion("me262_shatter", 4)};
         me262Shatter = new Animation(1, 0.1F, var12);
      } else {
         me262Shatter.setKeyFrame(0, atlas_me262.findRegion("me262_shatter", 0));
         me262Shatter.setKeyFrame(1, atlas_me262.findRegion("me262_shatter", 1));
         me262Shatter.setKeyFrame(2, atlas_me262.findRegion("me262_shatter", 2));
         me262Shatter.setKeyFrame(3, atlas_me262.findRegion("me262_shatter", 3));
         me262Shatter.setKeyFrame(4, atlas_me262.findRegion("me262_shatter", 4));
      }

      me262RailGun3 = atlas_me262.findRegion("me262railgun_force3");
      me262RailGun6 = atlas_me262.findRegion("me262railgun_force6");
      Animation var0 = me262Bullet1;
      TextureRegion[] var1 = new TextureRegion[]{atlas_me262.findRegion("me262_lv1_bullet", 0), atlas_me262.findRegion("me262_lv1_bullet", 1)};
      me262Bullet1 = Animation.setAnimationFrame(var0, 0, 0.0F, var1);
      Animation var2 = me262Bullet2;
      TextureRegion[] var3 = new TextureRegion[]{atlas_me262.findRegion("me262_lv2_bullet", 0), atlas_me262.findRegion("me262_lv2_bullet", 1)};
      me262Bullet2 = Animation.setAnimationFrame(var2, 0, 0.0F, var3);
      Animation var4 = me262Bullet4;
      TextureRegion[] var5 = new TextureRegion[]{atlas_me262.findRegion("me262_lv3_bullet", 0), atlas_me262.findRegion("me262_lv3_bullet", 1)};
      me262Bullet4 = Animation.setAnimationFrame(var4, 0, 0.0F, var5);
      Animation var6 = me262Lase3;
      TextureRegion[] var7 = new TextureRegion[]{atlas_me262.findRegion("me262railgun_force3", 0), atlas_me262.findRegion("me262railgun_force3", 1)};
      me262Lase3 = Animation.setAnimationFrame(var6, 0, 0.1F, var7);
      Animation var8 = me262Lase6;
      TextureRegion[] var9 = new TextureRegion[]{atlas_me262.findRegion("me262railgun_force6", 0), atlas_me262.findRegion("me262railgun_force6", 1)};
      me262Lase6 = Animation.setAnimationFrame(var8, 0, 0.1F, var9);
      Animation var10 = laseHead;
      TextureRegion[] var11 = new TextureRegion[]{atlas_me262.findRegion("railgun_hit", 0), atlas_me262.findRegion("railgun_hit", 1), atlas_me262.findRegion("railgun_hit", 2), atlas_me262.findRegion("railgun_hit", 3)};
      laseHead = Animation.setAnimationFrame(var10, 0, 0.041666668F, var11);
      if(me262FlyingToLeft.getFrameCount() != 4 || me262FlyingToRight.getFrameCount() != 4) {
         throw new RuntimeException("fighter turn animation\'s frameCount must be the same :4");
      }
   }

   private static void loadRafale() {
      if(rafaleStand == null) {
         TextureRegion[] var9 = new TextureRegion[]{atlas_rafale.findRegion("rafal_stand", 0), atlas_rafale.findRegion("rafal_stand", 1)};
         rafaleStand = new Animation(0, 0.05F, var9);
      } else {
         rafaleStand.setKeyFrame(0, atlas_rafale.findRegion("rafal_stand", 0));
         rafaleStand.setKeyFrame(1, atlas_rafale.findRegion("rafal_stand", 1));
      }

      if(rafaleFlyingToLeft == null) {
         TextureRegion[] var8 = new TextureRegion[]{atlas_rafale.findRegion("rafal_left", 0), atlas_rafale.findRegion("rafal_left", 1), atlas_rafale.findRegion("rafal_left", 2), atlas_rafale.findRegion("rafal_left", 3)};
         rafaleFlyingToLeft = new Animation(1, 0.05F, var8);
      } else {
         rafaleFlyingToLeft.setKeyFrame(0, atlas_rafale.findRegion("rafal_left", 0));
         rafaleFlyingToLeft.setKeyFrame(1, atlas_rafale.findRegion("rafal_left", 1));
         rafaleFlyingToLeft.setKeyFrame(2, atlas_rafale.findRegion("rafal_left", 2));
         rafaleFlyingToLeft.setKeyFrame(3, atlas_rafale.findRegion("rafal_left", 3));
      }

      if(rafaleFlyingToRight == null) {
         TextureRegion[] var7 = new TextureRegion[]{atlas_rafale.findRegion("rafal_right", 0), atlas_rafale.findRegion("rafal_right", 1), atlas_rafale.findRegion("rafal_right", 2), atlas_rafale.findRegion("rafal_right", 3)};
         rafaleFlyingToRight = new Animation(1, 0.05F, var7);
      } else {
         rafaleFlyingToRight.setKeyFrame(0, atlas_rafale.findRegion("rafal_right", 0));
         rafaleFlyingToRight.setKeyFrame(1, atlas_rafale.findRegion("rafal_right", 1));
         rafaleFlyingToRight.setKeyFrame(2, atlas_rafale.findRegion("rafal_right", 2));
         rafaleFlyingToRight.setKeyFrame(3, atlas_rafale.findRegion("rafal_right", 3));
      }

      rafaleBulletOld1 = atlas_rafale.findRegion("rafal_bullet_force1");
      rafaleBulletOld3 = atlas_rafale.findRegion("rafal_bullet_force3");
      rafaleBulletOld5 = atlas_rafale.findRegion("rafal_bullet_force5");
      if(rafaleShatter == null) {
         TextureRegion[] var6 = new TextureRegion[]{atlas_rafale.findRegion("rafal_shatter", 0), atlas_rafale.findRegion("rafal_shatter", 1), atlas_rafale.findRegion("rafal_shatter", 2), atlas_rafale.findRegion("rafal_shatter", 3), atlas_rafale.findRegion("rafal_shatter", 4)};
         rafaleShatter = new Animation(1, 0.1F, var6);
      } else {
         rafaleShatter.setKeyFrame(0, atlas_rafale.findRegion("rafal_shatter", 0));
         rafaleShatter.setKeyFrame(1, atlas_rafale.findRegion("rafal_shatter", 1));
         rafaleShatter.setKeyFrame(2, atlas_rafale.findRegion("rafal_shatter", 2));
         rafaleShatter.setKeyFrame(3, atlas_rafale.findRegion("rafal_shatter", 3));
         rafaleShatter.setKeyFrame(4, atlas_rafale.findRegion("rafal_shatter", 4));
      }

      Animation var0 = rafaleBullet1;
      TextureRegion[] var1 = new TextureRegion[]{atlas_rafale.findRegion("rafal_lv1_bullet", 0), atlas_rafale.findRegion("rafal_lv1_bullet", 1)};
      rafaleBullet1 = Animation.setAnimationFrame(var0, 0, 0.0F, var1);
      Animation var2 = rafaleBullet3;
      TextureRegion[] var3 = new TextureRegion[]{atlas_rafale.findRegion("rafal_lv2_bullet", 0), atlas_rafale.findRegion("rafal_lv2_bullet", 1)};
      rafaleBullet3 = Animation.setAnimationFrame(var2, 0, 0.0F, var3);
      Animation var4 = rafaleBullet10;
      TextureRegion[] var5 = new TextureRegion[]{atlas_rafale.findRegion("rafal_lv3_bullet", 0), atlas_rafale.findRegion("rafal_lv3_bullet", 1)};
      rafaleBullet10 = Animation.setAnimationFrame(var4, 0, 0.0F, var5);
      if(rafaleFlyingToLeft.getFrameCount() != 4 || rafaleFlyingToRight.getFrameCount() != 4) {
         throw new RuntimeException("fighter turn animation\'s frameCount must be the same :4");
      }
   }

   private static void loadSu47() {
      if(su47Stand == null) {
         TextureRegion[] var9 = new TextureRegion[]{atlas_su47.findRegion("su47_stand", 0), atlas_su47.findRegion("su47_stand", 1)};
         su47Stand = new Animation(0, 0.05F, var9);
      } else {
         su47Stand.setKeyFrame(0, atlas_su47.findRegion("su47_stand", 0));
         su47Stand.setKeyFrame(1, atlas_su47.findRegion("su47_stand", 1));
      }

      if(su47FlyingToLeft == null) {
         TextureRegion[] var8 = new TextureRegion[]{atlas_su47.findRegion("su47_left", 0), atlas_su47.findRegion("su47_left", 1), atlas_su47.findRegion("su47_left", 2), atlas_su47.findRegion("su47_left", 3)};
         su47FlyingToLeft = new Animation(1, 0.05F, var8);
      } else {
         su47FlyingToLeft.setKeyFrame(0, atlas_su47.findRegion("su47_left", 0));
         su47FlyingToLeft.setKeyFrame(1, atlas_su47.findRegion("su47_left", 1));
         su47FlyingToLeft.setKeyFrame(2, atlas_su47.findRegion("su47_left", 2));
         su47FlyingToLeft.setKeyFrame(3, atlas_su47.findRegion("su47_left", 3));
      }

      if(su47FlyingToRight == null) {
         TextureRegion[] var7 = new TextureRegion[]{atlas_su47.findRegion("su47_right", 0), atlas_su47.findRegion("su47_right", 1), atlas_su47.findRegion("su47_right", 2), atlas_su47.findRegion("su47_right", 3)};
         su47FlyingToRight = new Animation(1, 0.05F, var7);
      } else {
         su47FlyingToRight.setKeyFrame(0, atlas_su47.findRegion("su47_right", 0));
         su47FlyingToRight.setKeyFrame(1, atlas_su47.findRegion("su47_right", 1));
         su47FlyingToRight.setKeyFrame(2, atlas_su47.findRegion("su47_right", 2));
         su47FlyingToRight.setKeyFrame(3, atlas_su47.findRegion("su47_right", 3));
      }

      su47BulletOld1 = atlas_su47.findRegion("su47_bullet_force1");
      su47BulletOld2 = atlas_su47.findRegion("su47_bullet_force2");
      su47BulletOld5 = atlas_su47.findRegion("su47_bullet_force5");
      if(su47Shatter == null) {
         TextureRegion[] var6 = new TextureRegion[]{atlas_su47.findRegion("su47_shatter", 0), atlas_su47.findRegion("su47_shatter", 1), atlas_su47.findRegion("su47_shatter", 2), atlas_su47.findRegion("su47_shatter", 3), atlas_su47.findRegion("su47_shatter", 4)};
         su47Shatter = new Animation(1, 0.1F, var6);
      } else {
         su47Shatter.setKeyFrame(0, atlas_su47.findRegion("su47_shatter", 0));
         su47Shatter.setKeyFrame(1, atlas_su47.findRegion("su47_shatter", 1));
         su47Shatter.setKeyFrame(2, atlas_su47.findRegion("su47_shatter", 2));
         su47Shatter.setKeyFrame(3, atlas_su47.findRegion("su47_shatter", 3));
         su47Shatter.setKeyFrame(4, atlas_su47.findRegion("su47_shatter", 4));
      }

      Animation var0 = su47Bullet1;
      TextureRegion[] var1 = new TextureRegion[]{atlas_su47.findRegion("su47_lv1_bullet", 0), atlas_su47.findRegion("su47_lv1_bullet", 1)};
      su47Bullet1 = Animation.setAnimationFrame(var0, 0, 0.0F, var1);
      Animation var2 = su47Bullet3;
      TextureRegion[] var3 = new TextureRegion[]{atlas_su47.findRegion("su47_lv2_bullet", 0), atlas_su47.findRegion("su47_lv2_bullet", 1)};
      su47Bullet3 = Animation.setAnimationFrame(var2, 0, 0.0F, var3);
      Animation var4 = su47Bullet5;
      TextureRegion[] var5 = new TextureRegion[]{atlas_su47.findRegion("su47_lv3_bullet", 0), atlas_su47.findRegion("su47_lv3_bullet", 1)};
      su47Bullet5 = Animation.setAnimationFrame(var4, 0, 0.0F, var5);
      if(su47FlyingToLeft.getFrameCount() != 4 || su47FlyingToRight.getFrameCount() != 4) {
         throw new RuntimeException("fighter turn animation\'s frameCount must be the same :4");
      }
   }

   private static void loadTyphoon() {
      if(typhoonStand == null) {
         TextureRegion[] var9 = new TextureRegion[]{atlas_typhoon.findRegion("typhoon_stand", 0), atlas_typhoon.findRegion("typhoon_stand", 1)};
         typhoonStand = new Animation(0, 0.05F, var9);
      } else {
         typhoonStand.setKeyFrame(0, atlas_typhoon.findRegion("typhoon_stand", 0));
         typhoonStand.setKeyFrame(1, atlas_typhoon.findRegion("typhoon_stand", 1));
      }

      if(typhoonFlyingToLeft == null) {
         TextureRegion[] var8 = new TextureRegion[]{atlas_typhoon.findRegion("typhoon_left", 0), atlas_typhoon.findRegion("typhoon_left", 1), atlas_typhoon.findRegion("typhoon_left", 2), atlas_typhoon.findRegion("typhoon_left", 3)};
         typhoonFlyingToLeft = new Animation(1, 0.05F, var8);
      } else {
         typhoonFlyingToLeft.setKeyFrame(0, atlas_typhoon.findRegion("typhoon_left", 0));
         typhoonFlyingToLeft.setKeyFrame(1, atlas_typhoon.findRegion("typhoon_left", 1));
         typhoonFlyingToLeft.setKeyFrame(2, atlas_typhoon.findRegion("typhoon_left", 2));
         typhoonFlyingToLeft.setKeyFrame(3, atlas_typhoon.findRegion("typhoon_left", 3));
      }

      if(typhoonFlyingToRight == null) {
         TextureRegion[] var7 = new TextureRegion[]{atlas_typhoon.findRegion("typhoon_right", 0), atlas_typhoon.findRegion("typhoon_right", 1), atlas_typhoon.findRegion("typhoon_right", 2), atlas_typhoon.findRegion("typhoon_right", 3)};
         typhoonFlyingToRight = new Animation(1, 0.05F, var7);
      } else {
         typhoonFlyingToRight.setKeyFrame(0, atlas_typhoon.findRegion("typhoon_right", 0));
         typhoonFlyingToRight.setKeyFrame(1, atlas_typhoon.findRegion("typhoon_right", 1));
         typhoonFlyingToRight.setKeyFrame(2, atlas_typhoon.findRegion("typhoon_right", 2));
         typhoonFlyingToRight.setKeyFrame(3, atlas_typhoon.findRegion("typhoon_right", 3));
      }

      typhoonBulletOld1 = atlas_typhoon.findRegion("typhoon_bullet_force1");
      typhoonBulletOld2 = atlas_typhoon.findRegion("typhoon_bullet_force2");
      typhoonBulletOld6 = atlas_typhoon.findRegion("typhoon_bullet_force6");
      if(typhoonShatter == null) {
         TextureRegion[] var6 = new TextureRegion[]{atlas_typhoon.findRegion("typhoon_shatter", 0), atlas_typhoon.findRegion("typhoon_shatter", 1), atlas_typhoon.findRegion("typhoon_shatter", 2), atlas_typhoon.findRegion("typhoon_shatter", 3), atlas_typhoon.findRegion("typhoon_shatter", 4)};
         typhoonShatter = new Animation(1, 0.1F, var6);
      } else {
         typhoonShatter.setKeyFrame(0, atlas_typhoon.findRegion("typhoon_shatter", 0));
         typhoonShatter.setKeyFrame(1, atlas_typhoon.findRegion("typhoon_shatter", 1));
         typhoonShatter.setKeyFrame(2, atlas_typhoon.findRegion("typhoon_shatter", 2));
         typhoonShatter.setKeyFrame(3, atlas_typhoon.findRegion("typhoon_shatter", 3));
         typhoonShatter.setKeyFrame(4, atlas_typhoon.findRegion("typhoon_shatter", 4));
      }

      Animation var0 = typhoonBullet1;
      TextureRegion[] var1 = new TextureRegion[]{atlas_typhoon.findRegion("typhoon_lv1_bullet", 0), atlas_typhoon.findRegion("typhoon_lv1_bullet", 1)};
      typhoonBullet1 = Animation.setAnimationFrame(var0, 0, 0.0F, var1);
      Animation var2 = typhoonBullet2;
      TextureRegion[] var3 = new TextureRegion[]{atlas_typhoon.findRegion("typhoon_lv2_bullet", 0), atlas_typhoon.findRegion("typhoon_lv2_bullet", 1)};
      typhoonBullet2 = Animation.setAnimationFrame(var2, 0, 0.0F, var3);
      Animation var4 = typhoonBullet5;
      TextureRegion[] var5 = new TextureRegion[]{atlas_typhoon.findRegion("typhoon_lv3_bullet", 0), atlas_typhoon.findRegion("typhoon_lv3_bullet", 1)};
      typhoonBullet5 = Animation.setAnimationFrame(var4, 0, 0.0F, var5);
      if(typhoonFlyingToLeft.getFrameCount() != 4 || typhoonFlyingToRight.getFrameCount() != 4) {
         throw new RuntimeException("fighter turn animation\'s frameCount must be the same :4");
      }
   }
}
