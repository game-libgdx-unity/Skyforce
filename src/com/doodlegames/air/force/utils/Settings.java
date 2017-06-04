package com.doodlegames.air.force.utils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.fighters.F22;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.fighters.J20;
import com.doodlegames.air.force.fighters.Me262;
import com.doodlegames.air.force.fighters.Rafale;
import com.doodlegames.air.force.fighters.Su47;
import com.doodlegames.air.force.fighters.Typhoon;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.game.bullet.FighterPursueBullet1;
import com.doodlegames.air.force.screen.UIAchieveState;
import com.doodlegames.air.force.screen.UIMainMenuState;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.MusicPlayer;
import com.doodlegames.air.force.utils.SoundPlayer;

import java.util.HashMap;
import java.util.Map;

public class Settings {

   public static final float BGVEL = -53.333332F;
   public static boolean adFree = false;
   public static boolean allOnceBuy = false;
   public static Vector2 backgroundVelocity = new Vector2(0.0F, 0.0F);
   public static float bigDrop = 0.38F;
   public static boolean blueBulletOnceBuy = false;
   public static long boomCount = 0L;
   public static int boomEffectLevel = 0;
   public static final int[] boomIncrease = new int[]{0, 1, 2, 3, 4, 6};
   public static float bossDrop = 0.7F;
   public static boolean[] bossKilled = new boolean[]{false, false, false, false, false, false, false};
   public static int coinDropLevel = 0;
   public static final float[] dropDelta = new float[]{0.0F, 0.02F, 0.04F, 0.06F, 0.08F, 0.13F};
   public static long enemyKilledCount = 0L;
   public static long fighterCrashCount = 0L;
   public static final int fighterLimit = 6;
   public static int fireEffectLevel = 0;
   private static int isSmallScreen;
   public static int itemDropLevel = 0;
   public static boolean killAllEenmyInOneLevel = false;
   public static int lapCount = 1;
   public static boolean[] levelCompeleted = new boolean[]{false, false, false, false, false, false, false};
   public static long levelMoney = 0L;
   public static int levelPassed = 0;
   public static double[] levelScore = new double[]{0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D};
   public static int[] levelStar = new int[]{0, 0, 0, 0, 0, 0, 0};
   public static int lifeEffectLevel = 0;
   public static final int[] lifeIncrease;
   public static boolean magnetOnceBuy = false;
   public static Map<String, String> map;
   public static int megaEffectLevel = 0;
   public static final float[] megaRadis;
   public static float middleDrop = 0.18F;
   public static float[] minTimeKillBoss = new float[]{7200.0F, 7200.0F, 7200.0F, 7200.0F, 7200.0F, 7200.0F, 7200.0F};
   public static int missleEffectLevel = 0;
   public static final BulletGenerator[] missleGen;
   public static boolean missleOnceBuy = false;
   public static long money = 2000;
   private static boolean musicEnable;
   public static boolean[] passLevelOneLife = new boolean[]{false, false, false, false, false, false, false};
   public static boolean passLevelWithOutProp = false;
   public static final float pixelAlign = 0.5F;
   private static Preferences prefs;
   public static final int priceAllOnce = 1500;
   public static final int[] priceBoomEffectLevelUp = new int[]{1000, 3500, 7000, 15000, '\u9c40'};
   public static final int priceBulletOnce = 800;
   public static final int[] priceCoinDropLevelUp = new int[]{1500, 3500, 8000, 15000, '\u9c40'};
   public static final int[] priceDropLevelUp = new int[]{2000, 5000, 10000, 20000, '\uc350'};
   public static final int[] priceFireEffectLevelUp = new int[]{15000, 25000, '\uc350', 90000, 2000};
   public static final int[] priceLifeEffectLevelUp = new int[]{1500, 4000, 10000, 15000, '\u9c40'};
   public static final int priceMagnetOnce = 300;
   public static final int[] priceMegaEffectLevelUp = new int[]{600, 2000, 4500, 10000, 20000};
   public static final int[] priceMissleEffectLevelUp = new int[]{1000, 3500, 7000, 15000, '\u88b8'};
   public static final int priceMissleOnce = 500;
   public static final int[] priceWingEffectLevelUp = new int[]{1500, 4000, 8000, 15000, '\u88b8'};
   public static final int priceWingManOnce = 500;
   public static final float ratio = 1.0F;
   public static boolean redBulletOnceBuy = false;
   public static final float reveseRatio = 1.0F;
   public static int rewardLapCount = 0;
   public static double score = 0.0D;
   public static final float screenHeight = 800.0F;
   public static final int screenHightPixel = 800;
   public static final Rectangle screenRec = new Rectangle(0.0F, 0.0F, 640.0F, 800.0F);
   public static final float screenWidth = 480.0F;
   public static final int screenWidthPixel = 480;
   public static float smallDrop = 0.08F;
   private static boolean soundEnable;
   public static int unlockFighter;
   public static int wingEffectLevel = 0;
   public static boolean wingManOnceBuy = false;
   public static boolean yellowBulletOnceBuy = false;


   static {
      BulletGenerator[] var0 = new BulletGenerator[]{FighterPursueBullet1.bulletGen2, FighterPursueBullet1.bulletGen3, FighterPursueBullet1.bulletGen4, FighterPursueBullet1.bulletGen4, FighterPursueBullet1.bulletGen5, FighterPursueBullet1.bulletGen6};
      missleGen = var0;
      megaRadis = new float[]{120.0F, 150.0F, 180.0F, 210.0F, 240.0F, 300.0F};
      lifeIncrease = new int[]{0, 1, 2, 3, 4, 6};
      musicEnable = true;
      soundEnable = true;
      unlockFighter = 5;
      isSmallScreen = 0;
   }

   public static void ASSERT(boolean var0) {
      if(!var0) {
         if(Gdx.app.getType() != Application.ApplicationType.Android) {
            System.out.println(" file " + Thread.currentThread().getStackTrace()[2].getFileName() + " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "    CurrentTime:" + Clock.getTimeCounter());
            return;
         }

        // Log.e("MyDebugInfo", " file " + Thread.currentThread().getStackTrace()[2].getFileName() + " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "    CurrentTime:" + Clock.getTimeCounter());
      }

   }

   public static void appLog(String var0) {}

   public static void bossKilled(int var0, float var1) {
      int var2 = var0 - 1;
      if(!bossKilled[var2]) {
         bossKilled[var2] = true;
         i("boss in level " + var0 + " is first killed.");
      }

      if(minTimeKillBoss[var2] > var1) {
         i(var1 + " is a new record of killing boss in level " + var0 + ". Old record is " + minTimeKillBoss[var2]);
         minTimeKillBoss[var2] = var1;
      }

      i("boss in level " + var0 + " is killed. The time is " + var1);
   }

   public static void changeMusicEnable() {
      boolean var0;
      if(!musicEnable) {
         var0 = true;
      } else {
         var0 = false;
      }

      musicEnable = var0;
      if(musicEnable) {
         MusicPlayer.musicPlayer.play();
      } else {
         MusicPlayer.musicPlayer.stop();
      }

      saveAudioSettings(true);
   }

   public static void changeSoundEnable() {
      boolean var0;
      if(!soundEnable) {
         var0 = true;
      } else {
         var0 = false;
      }

      soundEnable = var0;
      saveAudioSettings(true);
   }

   public static void checkNextLap() {
      if(levelCompeleted[-1 + levelCompeleted.length]) {
         for(int var0 = 0; var0 < levelCompeleted.length; ++var0) {
            levelCompeleted[var0] = false;
         }

         ++lapCount;
         UIMainMenuState.showNewLap = true;
         if(lapCount == 2) {
            UIMainMenuState.showNewStoreItem = true;
         }

         flurryLog("newCycle", "cycleCount", lapCount + "");
      }
   }

   public static void e(String var0) {
      if(Gdx.app.getType() != Application.ApplicationType.Android) {
         System.out.println("ERROR:" + var0 + " file " + Thread.currentThread().getStackTrace()[2].getFileName() + " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "    CurrentTime:" + Clock.getTimeCounter());
      }
   }

   public static float fixDelta(float var0) {
      if(var0 > 0.1F) {
         var0 = 0.06666667F;
      } else if(var0 > 0.045454547F) {
         var0 = 0.045454547F;
      }

      Clock.update(var0);
      SoundPlayer.soundPlayer.update(var0);
      return var0;
   }

   public static void fixFighter(Fighter var0) {
      Class var1 = var0.getClass();
      byte var2 = 0;
      byte var3 = 0;
      if(var1 == F22.class) {
         var3 = 3;
         var2 = 2;
      }

      if(var0.getClass() == J20.class) {
         var3 = 3;
         var2 = 2;
      }

      if(var0.getClass() == Rafale.class) {
         var3 = 2;
         var2 = 3;
      }

      if(var0.getClass() == Su47.class) {
         var3 = 4;
         var2 = 1;
      }

      if(var0.getClass() == Typhoon.class) {
         var3 = 1;
         var2 = 5;
      }

      if(var0.getClass() == Me262.class) {
         var3 = 2;
         var2 = 1;
      }

      int var4 = var3 + lifeIncrease[lifeEffectLevel];
      int var5 = var2 + boomIncrease[boomEffectLevel];
      var0.livesLimit = var4;
      var0.boomLimit = var5;
      var0.iniLives = var4;
      var0.iniBoom = var5;
   }

   public static void flurryError(String var0) {
      if(Gdx.app.getType() == Application.ApplicationType.Android) {
//         //FlurryAgent.logEvent(var0);
      }

   }

   public static void flurryLog(String var0) {
      if(Gdx.app.getType() == Application.ApplicationType.Android) {
//         //FlurryAgent.logEvent(var0);
      }

   }

   public static void flurryLog(String var0, String var1, String var2) {
      if(map == null) {
         map = new HashMap();
      }

      map.clear();
      map.put(var1, var2);
      flurryLog(var0, map);
   }

   public static void flurryLog(String var0, String var1, String var2, String var3, String var4) {
      if(map == null) {
         map = new HashMap();
      }

      map.clear();
      map.put(var1, var2);
      map.put(var3, var4);
      flurryLog(var0, map);
   }

   public static void flurryLog(String var0, String var1, String var2, String var3, String var4, String var5, String var6) {
      if(map == null) {
         map = new HashMap();
      }

      map.clear();
      map.put(var1, var2);
      map.put(var3, var4);
      map.put(var5, var6);
      flurryLog(var0, map);
   }

   private static void flurryLog(String var0, Map<String, String> var1) {
      if(Gdx.app.getType() == Application.ApplicationType.Android) {
//         //FlurryAgent.logEvent(var0, var1);
      }

      if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
         i(var0 + var1.toString());
      }

   }

   public static Settings.PositionType gameObjectInScreen(GameObject var0) {
      return var0.bounds.y < -(800.0F + var0.bounds.height)?Settings.PositionType.UnderFarScreen:(var0.bounds.y < -var0.bounds.height?Settings.PositionType.UnderScreen:(var0.bounds.y < 800.0F?(var0.bounds.x < -(480.0F + var0.bounds.width)?Settings.PositionType.InScreenFarLeft:(var0.bounds.x < -var0.bounds.width?Settings.PositionType.InScreenLeft:(var0.bounds.x < 640.0F?Settings.PositionType.InScreenMiddle:(var0.bounds.x < 1280.0F?Settings.PositionType.InScreenRight:Settings.PositionType.InScreenFarRight)))):(var0.bounds.y < 1600.0F?Settings.PositionType.AboveScreen:Settings.PositionType.AboveFarScreen)));
   }

   public static int getFireEffectIncrease() {
      return (new int[]{0, 2, 3, 5, 10, 12})[fireEffectLevel];
   }

   public static int getMaxLevelId() {
      return 7;
   }

   public static boolean getMusicEnable() {
      return musicEnable;
   }

   public static boolean getSoundEnable() {
      return soundEnable;
   }

   public static float getXRATIO() {
      return 1.0F * (480.0F / (float)Gdx.graphics.getWidth());
   }

   public static float getYRATIO() {
      return 1.0F * (800.0F / (float)Gdx.graphics.getHeight());
   }

   public static void i(String var0) {
      if(Gdx.app.getType() != Application.ApplicationType.Android) {
         System.out.println("INFO:" + var0 + " file " + Thread.currentThread().getStackTrace()[2].getFileName() + " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "    CurrentTime:" + Clock.getTimeCounter());
      }
   }

   public static boolean isBadPermancePhone() {
      boolean var0 = isSmallScreen();
      if(Clock.getCurrentTime() > 10.0F && (double)((float)Clock.getFrameNo() / Clock.getCurrentTime()) < 40.0D) {
         var0 = true;
      }

      return var0;
   }

   public static boolean isFocus() {
      return true;
   }

   public static boolean isSmallScreen() {
      byte var0 = 1;
      if(isSmallScreen > 0) {
         return true;
      } else if(isSmallScreen < 0) {
         return false;
      } else {
         int var1 = Gdx.graphics.getWidth();
         int var2 = Gdx.graphics.getHeight();
         byte var3 = 0;
         if(var1 < 480) {
            var3 = 0;
            if(var2 < 800) {
               var3 = var0;
            }
         }

         if(var3 == 0) {
            var0 = -1;
         }

         isSmallScreen = var0;
         return var3==0?false:true;
      }
   }

   public static int lapFixBossDegree(int var0) {
      return (int)(1.8F * (float)var0);
   }

   public static float lapRatio() {
      return 1.0F + 0.5F * (float)(-1 + lapCount);
   }

   public static void load() {
      prefs = Gdx.app.getPreferences("My Preferences");
      loadStaticsData();
      loadPropDropData();
      loadOnceBuy();
      loadPropLevel();
      loadAudioSettings();
      loadUnlockFighter();
      UIAchieveState.NO_ADD = prefs.getBoolean("ad_remove", false);
      UIAchieveState.Const.loadAch(prefs);
      backgroundVelocity.y = -53.333332F;
   }

   private static void loadAudioSettings() {
      musicEnable = prefs.getBoolean("musicEnable", true);
      soundEnable = prefs.getBoolean("soundEnable", true);
   }

   private static void loadOnceBuy() {
      missleOnceBuy = prefs.getBoolean("missleOnceBuy", false);
      wingManOnceBuy = prefs.getBoolean("wingManOnceBuy", false);
      magnetOnceBuy = prefs.getBoolean("magnetOnceBuy", false);
      redBulletOnceBuy = prefs.getBoolean("redBulletOnceBuy", false);
      yellowBulletOnceBuy = prefs.getBoolean("yellowBulletOnceBuy", false);
      blueBulletOnceBuy = prefs.getBoolean("blueBulletOnceBuy", false);
      allOnceBuy = prefs.getBoolean("allOnceBuy", false);
   }

   private static void loadPropDropData() {
      itemDropLevel = prefs.getInteger("itemDropLevel", 0);
      coinDropLevel = prefs.getInteger("coinDropLevel", 0);
      smallDrop = prefs.getFloat("smallDrop", 0.1F);
      middleDrop = prefs.getFloat("middleDrop", 0.2F);
      bigDrop = prefs.getFloat("bigDrop", 0.5F);
      bossDrop = prefs.getFloat("bossDrop", 0.7F);
   }

   private static void loadPropLevel() {
      boomEffectLevel = prefs.getInteger("boomEffectLevel", 0);
      missleEffectLevel = prefs.getInteger("missleEffectLevel", 0);
      megaEffectLevel = prefs.getInteger("megaEffectLevel", 0);
      wingEffectLevel = prefs.getInteger("wingEffectLevel", 0);
      lifeEffectLevel = prefs.getInteger("lifeEffectLevel", 0);
      fireEffectLevel = prefs.getInteger("fireEffectLevel", 0);
   }

   private static void loadStaticsData() {
      rewardLapCount = prefs.getInteger("rewardLapCount", 0);
      lapCount = prefs.getInteger("lapCount", 0);
      money = prefs.getLong("money", -1L);
      levelMoney = prefs.getLong("levelMoney", 0L);
      long var0 = Double.doubleToLongBits(0.0D);
      score = Double.longBitsToDouble(prefs.getLong("score", var0));

      for(int var2 = 0; var2 < levelStar.length; ++var2) {
         levelStar[var2] = prefs.getInteger("levelStar" + var2);
      }

      for(int var3 = 0; var3 < levelScore.length; ++var3) {
         levelScore[var3] = Double.longBitsToDouble(prefs.getLong("levelScore" + var3, var0));
      }

      enemyKilledCount = prefs.getLong("enemyKilledCount", 0L);

      for(int var4 = 0; var4 < bossKilled.length; ++var4) {
         bossKilled[var4] = prefs.getBoolean("bossKilled" + var4, false);
      }

      for(int var5 = 0; var5 < minTimeKillBoss.length; ++var5) {
         minTimeKillBoss[var5] = prefs.getFloat("minTimeKillBoss" + var5, 7200.0F);
      }

      levelPassed = prefs.getInteger("levelPassed", 0);

      for(int var6 = 0; var6 < levelCompeleted.length; ++var6) {
         levelCompeleted[var6] = prefs.getBoolean("levelCompeleted" + var6, false);
      }

      for(int var7 = 0; var7 < passLevelOneLife.length; ++var7) {
         passLevelOneLife[var7] = prefs.getBoolean("passLevelOneLife" + var7, false);
      }

      killAllEenmyInOneLevel = prefs.getBoolean("killAllEenmyInOneLevel", false);
      passLevelWithOutProp = prefs.getBoolean("passLevelWithOutProp", false);
      fighterCrashCount = prefs.getLong("fighterCrashCount", 0L);
      boomCount = prefs.getLong("boomCount", 0L);
      adFree = prefs.getBoolean("adFree", false);
   }

   private static void loadUnlockFighter() {
      unlockFighter = prefs.getInteger("unlockFighter", 6);
   }

   public static void resetLap() {
      if(lapCount > 1) {
         rewardLapCount = 0;
         lapCount = 1;

         for(int var0 = 0; var0 < levelCompeleted.length; ++var0) {
            levelCompeleted[var0] = false;
         }

         save();
      }

   }

   public static void save() {
      saveStaticsData(false);
      savePropDropData(false);
      saveOnceBuy(false);
      savePropLevel(false);
      saveAudioSettings(false);
      saveUnlockFighter(false);
      UIAchieveState.Const.saveAch(prefs, false);
      prefs.putBoolean("ad_remove", UIAchieveState.NO_ADD);
      prefs.flush();
   }

   private static void saveAudioSettings(boolean var0) {
      prefs.putBoolean("musicEnable", musicEnable);
      prefs.putBoolean("soundEnable", soundEnable);
      if(var0) {
         prefs.flush();
      }

   }

   private static void saveOnceBuy(boolean var0) {
      prefs.putBoolean("missleOnceBuy", missleOnceBuy);
      prefs.putBoolean("wingManOnceBuy", wingManOnceBuy);
      prefs.putBoolean("magnetOnceBuy", magnetOnceBuy);
      prefs.putBoolean("redBulletOnceBuy", redBulletOnceBuy);
      prefs.putBoolean("yellowBulletOnceBuy", yellowBulletOnceBuy);
      prefs.putBoolean("blueBulletOnceBuy", blueBulletOnceBuy);
      prefs.putBoolean("allOnceBuy", allOnceBuy);
      if(var0) {
         prefs.flush();
      }

   }

   private static void savePropDropData(boolean var0) {
      prefs.putInteger("itemDropLevel", itemDropLevel);
      prefs.putInteger("coinDropLevel", coinDropLevel);
      prefs.putFloat("smallDrop", smallDrop);
      prefs.putFloat("middleDrop", middleDrop);
      prefs.putFloat("bigDrop", bigDrop);
      prefs.putFloat("bossDrop", bossDrop);
      if(var0) {
         prefs.flush();
      }

   }

   private static void savePropLevel(boolean var0) {
      prefs.putInteger("boomEffectLevel", boomEffectLevel);
      prefs.putInteger("missleEffectLevel", missleEffectLevel);
      prefs.putInteger("megaEffectLevel", megaEffectLevel);
      prefs.putInteger("wingEffectLevel", wingEffectLevel);
      prefs.putInteger("lifeEffectLevel", lifeEffectLevel);
      prefs.putInteger("fireEffectLevel", fireEffectLevel);
      if(var0) {
         prefs.flush();
      }

   }

   private static void saveStaticsData(boolean var0) {
      prefs.putInteger("rewardLapCount", rewardLapCount);
      prefs.putInteger("lapCount", lapCount);
      prefs.putLong("money", money);
      prefs.putLong("levelMoney", levelMoney);
      prefs.putLong("score", Double.doubleToLongBits(score));

      for(int var1 = 0; var1 < levelStar.length; ++var1) {
         prefs.putInteger("levelStar" + var1, levelStar[var1]);
      }

      for(int var2 = 0; var2 < levelScore.length; ++var2) {
         prefs.putLong("levelScore" + var2, Double.doubleToLongBits(levelScore[var2]));
      }

      prefs.putLong("enemyKilledCount", enemyKilledCount);

      for(int var3 = 0; var3 < bossKilled.length; ++var3) {
         prefs.putBoolean("bossKilled" + var3, bossKilled[var3]);
      }

      for(int var4 = 0; var4 < minTimeKillBoss.length; ++var4) {
         prefs.putFloat("minTimeKillBoss" + var4, minTimeKillBoss[var4]);
      }

      prefs.putInteger("levelPassed", levelPassed);

      for(int var5 = 0; var5 < levelCompeleted.length; ++var5) {
         prefs.putBoolean("levelCompeleted" + var5, levelCompeleted[var5]);
      }

      for(int var6 = 0; var6 < passLevelOneLife.length; ++var6) {
         prefs.putBoolean("passLevelOneLife" + var6, passLevelOneLife[var6]);
      }

      prefs.putBoolean("killAllEenmyInOneLevel", killAllEenmyInOneLevel);
      prefs.putBoolean("passLevelWithOutProp", passLevelWithOutProp);
      prefs.putLong("fighterCrashCount", fighterCrashCount);
      prefs.putLong("boomCount", boomCount);
      prefs.putBoolean("adFree", adFree);
      if(var0) {
         prefs.flush();
      }

   }

   private static void saveUnlockFighter(boolean var0) {
      prefs.putInteger("unlockFighter", unlockFighter);
      if(var0) {
         prefs.flush();
      }

   }

   public static Vector2 unproject(Vector2 var0) {
      var0.x *= getXRATIO();
      var0.y *= getYRATIO();
      return var0;
   }

   public static void w(String var0) {
      if(Gdx.app.getType() != Application.ApplicationType.Android) {
         System.out.println("WARNING:" + var0 + " file " + Thread.currentThread().getStackTrace()[2].getFileName() + " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "    CurrentTime:" + Clock.getTimeCounter());
      }
   }

   public static enum PositionType {

      AboveFarScreen,
      AboveScreen,
      InScreenFarLeft,
      InScreenFarRight,
      InScreenLeft,
      InScreenMiddle,
      InScreenRight,
      UnderFarScreen,
      UnderScreen,
   }
}
