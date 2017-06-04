package com.doodlegames.air.force.resource;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.doodlegames.air.force.enemy.level1.AirCraft;
import com.doodlegames.air.force.enemy.level1.AirCraftOnBoss;
import com.doodlegames.air.force.enemy.level1.BattleShip;
import com.doodlegames.air.force.enemy.level1.BossAircraftCarrier;
import com.doodlegames.air.force.enemy.level1.Destroyer;
import com.doodlegames.air.force.enemy.level1.RailGunOnBoss;
import com.doodlegames.air.force.enemy.level1.Sea_canon;
import com.doodlegames.air.force.game.path.EnemyMutiMove;
import com.doodlegames.air.force.game.path.PathInfoLoader;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.screen.LoadingScreen;
import com.doodlegames.air.force.utils.Settings;

public class Assets_level1 {

   public static final String atlas_BGPath = "bg/lv_1/bg_level1.atlas";
   public static TextureAtlas atlas_Enemy;
   private static final String atlas_EnemyPath = "enemy/level1/level1.atlas";
   public static TextureAtlas atlas_bg;
   public static LoadingScreen.LoadingInfo info = new LoadingScreen.LoadingInfo() {
      public void fillAssetManager(AssetManager var1) {
         var1.load("enemy/level1/level1.atlas", TextureAtlas.class);
         var1.load("levelData/level1/level_1_PathInfo.ini", EnemyMutiMove.IPathInfoSet.class);
         var1.load("bg/lv_1/bg_level1.atlas", TextureAtlas.class);
      }
      public Screen loadingFinished(Game var1, AssetManager var2) {
         try {
            Assets_level1.atlas_Enemy = (TextureAtlas)var2.get("enemy/level1/level1.atlas", TextureAtlas.class);
            Assets_level1.pathInfoSet = (EnemyMutiMove.IPathInfoSet)var2.get("levelData/level1/level_1_PathInfo.ini", EnemyMutiMove.IPathInfoSet.class);
            Assets_level1.atlas_bg = (TextureAtlas)var2.get("bg/lv_1/bg_level1.atlas", TextureAtlas.class);
            return null;
         } catch (Throwable var7) {
            Settings.w(var7.toString());
            Assets_level1.atlas_Enemy = null;
            Assets_level1.pathInfoSet = null;
            Assets_level1.atlas_bg = null;
         } finally {
            Assets_level1.load();
         }

         return null;
      }
   };
   private static final String pathInfoFile = "levelData/level1/level_1_PathInfo.ini";
   public static EnemyMutiMove.IPathInfoSet pathInfoSet = null;


   public static TextureAtlas.AtlasRegion findRegion(String var0) {
      TextureAtlas.AtlasRegion var1 = atlas_Enemy.findRegion(var0);
      if(var1 == null) {
         var1 = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion(var0);
      }

      return var1;
   }

   public static TextureAtlas.AtlasRegion findRegion(String var0, int var1) {
      TextureAtlas.AtlasRegion var2 = atlas_Enemy.findRegion(var0, var1);
      if(var2 == null) {
         var2 = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion(var0, var1);
      }

      return var2;
   }

   public static void load() {
      if(atlas_Enemy == null) {
         atlas_Enemy = new TextureAtlas(Gdx.files.internal("enemy/level1/level1.atlas"));
         Settings.w("Assets Load failed.");
      }

      if(pathInfoSet == null) {
         pathInfoSet = PathInfoLoader.getPathInfoSetByHandle(Gdx.files.internal("levelData/level1/level_1_PathInfo.ini"));
         Settings.w("Assets Load failed.");
      }

      if(atlas_bg == null) {
         atlas_bg = new TextureAtlas(Gdx.files.internal("bg/lv_1/bg_level1.atlas"));
         Settings.w("Assets Load failed.");
      }

      AirCraft.loadResource();
      AirCraftOnBoss.loadResource();
      BattleShip.loadResource();
      BossAircraftCarrier.loadResource();
      Destroyer.loadResource();
      RailGunOnBoss.loadResource();
      Sea_canon.loadResource();
   }
}
