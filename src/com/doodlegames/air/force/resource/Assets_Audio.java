package com.doodlegames.air.force.resource;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.doodlegames.air.force.screen.LoadingScreen;

import java.lang.reflect.InvocationTargetException;

public class Assets_Audio {

   public static Sound alienBigBoom;
   public static final String alienBigBoomPath = "audio/sound/alienBigBoom.ogg";
   public static final String alienBulletPath = "audio/sound/alienBullet.ogg";
   public static Sound alienLaseGather;
   public static final String alienLaseGatherPath = "audio/sound/alienLaseGather.ogg";
   public static Sound alienLaseShooting;
   public static final String alienLaseShootingPath = "audio/sound/alienLaseShooting.ogg";
   public static Sound alienSmallBoom;
   public static final String alienSmallBoomPath = "audio/sound/alienSmallBoom.ogg";
   public static Sound backButton;
   public static final String backButtonPath = "audio/sound/backButton.ogg";
   public static Music bossMusic;
   public static final String bossMusicPath = "audio/music/boss.ogg";
   public static Sound bulletHitEnemy;
   public static final String bulletHitEnemyPath = "audio/sound/bulletHitEnemy.ogg";
   public static Music earthMusic;
   public static final String earthMusicPath = "audio/music/menu.ogg";
   public static Sound fighterBullet1;
   public static final String fighterBullet1Path = "audio/sound/fighterBullet1.ogg";
   public static Sound fighterBullet2;
   public static final String fighterBullet2Path = "audio/sound/fighterBullet2.ogg";
   public static Sound fighterBullet3;
   public static final String fighterBullet3Path = "audio/sound/fighterBullet3.ogg";
   public static Sound gameOver;
   public static final String gameOverPath = "audio/sound/gameOver.ogg";
   public static Sound getCoin;
   public static final String getCoinPath = "audio/sound/getCoin.ogg";
   public static Sound getLife;
   public static final String getLifePath = "audio/sound/getLife.ogg";
   public static Sound getProp;
   public static final String getPropPath = "audio/sound/getProp.ogg";
   public static Music marMusic;
   public static final String marMusicPath = "audio/music/mars.ogg";
   public static Music moonMusic;
   public static final String moonMusicPath = "audio/music/moon.ogg";
   public static Sound naziBigBoom;
   public static final String naziBigBoomPath = "audio/sound/naziBigBoom.ogg";
   public static final String naziBulletPath = "audio/sound/naziBullet.ogg";
   public static Sound naziLaseGather;
   public static final String naziLaseGatherPath = "audio/sound/naziLaseGather.ogg";
   public static Sound naziLaseShooting;
   public static final String naziLaseShootingPath = "audio/sound/naziLaseShooting.ogg";
   public static Sound naziSmallBoom;
   public static final String naziSmallBoomPath = "audio/sound/naziSmallBoom.ogg";
   public static Sound normalButton;
   public static final String normalButtonPath = "audio/sound/normalButton.ogg";
   public static Music spaceMusic;
   public static final String spaceMusicPath = "audio/music/space.ogg";
   public static Sound warning;
   public static final String warningPath = "audio/sound/warning.ogg";
   public static Sound win;
   public static final String winPath = "audio/sound/win.ogg";


   public static void assignNull() {
      bossMusic = null;
      earthMusic = null;
      moonMusic = null;
      spaceMusic = null;
      marMusic = null;
      alienBigBoom = null;
      alienLaseGather = null;
      alienLaseShooting = null;
      alienSmallBoom = null;
      bulletHitEnemy = null;
      fighterBullet1 = null;
      fighterBullet2 = null;
      fighterBullet3 = null;
      gameOver = null;
      getCoin = null;
      getLife = null;
      getProp = null;
      naziBigBoom = null;
      naziLaseGather = null;
      naziLaseShooting = null;
      naziSmallBoom = null;
      warning = null;
      win = null;
      backButton = null;
      normalButton = null;
   }

   public static void getSound(AssetManager var0) {
      alienBigBoom = (Sound)var0.get("audio/sound/alienBigBoom.ogg", Sound.class);
      alienLaseGather = (Sound)var0.get("audio/sound/alienLaseGather.ogg", Sound.class);
      alienLaseShooting = (Sound)var0.get("audio/sound/alienLaseShooting.ogg", Sound.class);
      alienSmallBoom = (Sound)var0.get("audio/sound/alienSmallBoom.ogg", Sound.class);
      bulletHitEnemy = (Sound)var0.get("audio/sound/bulletHitEnemy.ogg", Sound.class);
      fighterBullet1 = (Sound)var0.get("audio/sound/fighterBullet1.ogg", Sound.class);
      fighterBullet2 = (Sound)var0.get("audio/sound/fighterBullet2.ogg", Sound.class);
      fighterBullet3 = (Sound)var0.get("audio/sound/fighterBullet3.ogg", Sound.class);
      gameOver = (Sound)var0.get("audio/sound/gameOver.ogg", Sound.class);
      getCoin = (Sound)var0.get("audio/sound/getCoin.ogg", Sound.class);
      getLife = (Sound)var0.get("audio/sound/getLife.ogg", Sound.class);
      getProp = (Sound)var0.get("audio/sound/getProp.ogg", Sound.class);
      naziBigBoom = (Sound)var0.get("audio/sound/naziBigBoom.ogg", Sound.class);
      naziLaseGather = (Sound)var0.get("audio/sound/naziLaseGather.ogg", Sound.class);
      naziLaseShooting = (Sound)var0.get("audio/sound/naziLaseShooting.ogg", Sound.class);
      naziSmallBoom = (Sound)var0.get("audio/sound/naziSmallBoom.ogg", Sound.class);
      warning = (Sound)var0.get("audio/sound/warning.ogg", Sound.class);
      win = (Sound)var0.get("audio/sound/win.ogg", Sound.class);
      backButton = (Sound)var0.get("audio/sound/backButton.ogg", Sound.class);
      normalButton = (Sound)var0.get("audio/sound/normalButton.ogg", Sound.class);
   }

   public static void loadSound(AssetManager var0) {
      var0.load("audio/sound/alienBigBoom.ogg", Sound.class);
      var0.load("audio/sound/alienLaseGather.ogg", Sound.class);
      var0.load("audio/sound/alienLaseShooting.ogg", Sound.class);
      var0.load("audio/sound/alienSmallBoom.ogg", Sound.class);
      var0.load("audio/sound/bulletHitEnemy.ogg", Sound.class);
      var0.load("audio/sound/fighterBullet1.ogg", Sound.class);
      var0.load("audio/sound/fighterBullet2.ogg", Sound.class);
      var0.load("audio/sound/fighterBullet3.ogg", Sound.class);
      var0.load("audio/sound/gameOver.ogg", Sound.class);
      var0.load("audio/sound/getCoin.ogg", Sound.class);
      var0.load("audio/sound/getLife.ogg", Sound.class);
      var0.load("audio/sound/getProp.ogg", Sound.class);
      var0.load("audio/sound/naziBigBoom.ogg", Sound.class);
      var0.load("audio/sound/naziLaseGather.ogg", Sound.class);
      var0.load("audio/sound/naziLaseShooting.ogg", Sound.class);
      var0.load("audio/sound/naziSmallBoom.ogg", Sound.class);
      var0.load("audio/sound/warning.ogg", Sound.class);
      var0.load("audio/sound/win.ogg", Sound.class);
      var0.load("audio/sound/backButton.ogg", Sound.class);
      var0.load("audio/sound/normalButton.ogg", Sound.class);
   }

   public static class LoadingInfoLevel1 implements LoadingScreen.LoadingInfo {

      public void fillAssetManager(AssetManager var1) {
         Assets_Audio.assignNull();
         var1.load("audio/music/boss.ogg", Music.class);
         var1.load("audio/music/menu.ogg", Music.class);
         Assets_Audio.loadSound(var1);
      }

      public Screen loadingFinished(Game var1, AssetManager var2) {
         Assets_Audio.bossMusic = (Music)var2.get("audio/music/boss.ogg", Music.class);
         Assets_Audio.earthMusic = (Music)var2.get("audio/music/menu.ogg", Music.class);
         Assets_Audio.getSound(var2);
         return null;
      }
   }

   public static class LoadingInfoLevel5 implements LoadingScreen.LoadingInfo {

      public void fillAssetManager(AssetManager var1) {
         var1.load("audio/music/boss.ogg", Music.class);
         var1.load("audio/music/space.ogg", Music.class);
         Assets_Audio.loadSound(var1);
      }

      public Screen loadingFinished(Game var1, AssetManager var2) {
         Assets_Audio.bossMusic = (Music)var2.get("audio/music/boss.ogg", Music.class);
         Assets_Audio.spaceMusic = (Music)var2.get("audio/music/space.ogg", Music.class);
         Assets_Audio.getSound(var2);
         return null;
      }
   }

   public static class LoadingInfoLevel4 implements LoadingScreen.LoadingInfo {

      public void fillAssetManager(AssetManager var1) {
         var1.load("audio/music/boss.ogg", Music.class);
         var1.load("audio/music/moon.ogg", Music.class);
         Assets_Audio.loadSound(var1);
      }

      public Screen loadingFinished(Game var1, AssetManager var2) {
         Assets_Audio.bossMusic = (Music)var2.get("audio/music/boss.ogg", Music.class);
         Assets_Audio.moonMusic = (Music)var2.get("audio/music/moon.ogg", Music.class);
         Assets_Audio.getSound(var2);
         return null;
      }
   }

   public static class LoadingInfoLevel3 implements LoadingScreen.LoadingInfo {

      public void fillAssetManager(AssetManager var1) {
         var1.load("audio/music/boss.ogg", Music.class);
         var1.load("audio/music/moon.ogg", Music.class);
         Assets_Audio.loadSound(var1);
      }

      public Screen loadingFinished(Game var1, AssetManager var2) {
         Assets_Audio.bossMusic = (Music)var2.get("audio/music/boss.ogg", Music.class);
         Assets_Audio.moonMusic = (Music)var2.get("audio/music/moon.ogg", Music.class);
         Assets_Audio.getSound(var2);
         return null;
      }
   }

   public static class LoadingInfoLevel2 implements LoadingScreen.LoadingInfo {

      public void fillAssetManager(AssetManager var1) {
         var1.load("audio/music/boss.ogg", Music.class);
         var1.load("audio/music/menu.ogg", Music.class);
         Assets_Audio.loadSound(var1);
      }

      public Screen loadingFinished(Game var1, AssetManager var2) {
         Assets_Audio.bossMusic = (Music)var2.get("audio/music/boss.ogg", Music.class);
         Assets_Audio.earthMusic = (Music)var2.get("audio/music/menu.ogg", Music.class);
         Assets_Audio.getSound(var2);
         return null;
      }
   }

   public static class SoundLoadingInfo implements LoadingScreen.LoadingInfo {

      int level;
      LoadingScreen.LoadingInfo loadingInfo;


      public SoundLoadingInfo(int var1) {
         this.level = var1;
         Class[] var2 = new Class[]{Assets_Audio.LoadingInfoLevel1.class, Assets_Audio.LoadingInfoLevel2.class, Assets_Audio.LoadingInfoLevel3.class, Assets_Audio.LoadingInfoLevel4.class, Assets_Audio.LoadingInfoLevel5.class, Assets_Audio.LoadingInfoLevel6.class, Assets_Audio.LoadingInfoLevel7.class};

         try {
            this.loadingInfo = (LoadingScreen.LoadingInfo)((LoadingScreen.LoadingInfo)var2[-1 + this.level].getConstructor(new Class[0]).newInstance(new Object[0]));
         } catch (IllegalArgumentException var8) {
            var8.printStackTrace();
         } catch (InstantiationException var9) {
            var9.printStackTrace();
         } catch (IllegalAccessException var10) {
            var10.printStackTrace();
         } catch (InvocationTargetException var11) {
            var11.printStackTrace();
         } catch (NoSuchMethodException var12) {
            var12.printStackTrace();
         }
      }

      public void fillAssetManager(AssetManager var1) {
         this.loadingInfo.fillAssetManager(var1);
      }

      public Screen loadingFinished(Game var1, AssetManager var2) {
         return this.loadingInfo.loadingFinished(var1, var2);
      }
   }

   public static class LoadingInfoLevel7 implements LoadingScreen.LoadingInfo {

      public void fillAssetManager(AssetManager var1) {
         var1.load("audio/music/boss.ogg", Music.class);
         var1.load("audio/music/mars.ogg", Music.class);
         Assets_Audio.loadSound(var1);
      }

      public Screen loadingFinished(Game var1, AssetManager var2) {
         Assets_Audio.bossMusic = (Music)var2.get("audio/music/boss.ogg", Music.class);
         Assets_Audio.marMusic = (Music)var2.get("audio/music/mars.ogg", Music.class);
         Assets_Audio.getSound(var2);
         return null;
      }
   }

   public static class LoadingInfoLevel6 implements LoadingScreen.LoadingInfo {

      public void fillAssetManager(AssetManager var1) {
         var1.load("audio/music/boss.ogg", Music.class);
         var1.load("audio/music/mars.ogg", Music.class);
         Assets_Audio.loadSound(var1);
      }

      public Screen loadingFinished(Game var1, AssetManager var2) {
         Assets_Audio.bossMusic = (Music)var2.get("audio/music/boss.ogg", Music.class);
         Assets_Audio.marMusic = (Music)var2.get("audio/music/mars.ogg", Music.class);
         Assets_Audio.getSound(var2);
         return null;
      }
   }
}
