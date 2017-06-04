package com.doodlegames.air.force.resource;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.doodlegames.air.force.enemy.level7.BossLv7L;
import com.doodlegames.air.force.enemy.level7.BossLv7LSilk;
import com.doodlegames.air.force.enemy.level7.BossLv7LSlaw;
import com.doodlegames.air.force.enemy.level7.BossLv7S;
import com.doodlegames.air.force.enemy.level7.BossLv7STube;
import com.doodlegames.air.force.game.path.EnemyMutiMove;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.screen.LoadingScreen;
import com.doodlegames.air.force.utils.Settings;

public class Assets_level7 {

   public static final String atlas_BGPath = "bg/lv_7/bg_level7.atlas";
   public static TextureAtlas atlas_Enemy;
   public static TextureAtlas atlas_bg;
   private static final String enemyFilePath = "enemy/level7/level7.atlas";
   public static LoadingScreen.LoadingInfo info = new LoadingScreen.LoadingInfo() {
      public void fillAssetManager(AssetManager var1) {
         var1.load("bg/lv_7/bg_level7.atlas", TextureAtlas.class);
         var1.load("levelData/level7/level_7_PathInfo.ini", EnemyMutiMove.IPathInfoSet.class);
         var1.load("enemy/level7/level7.atlas", TextureAtlas.class);
      }
      public Screen loadingFinished(Game var1, AssetManager var2) {
         try {
            Assets_level7.atlas_bg = (TextureAtlas)var2.get("bg/lv_7/bg_level7.atlas", TextureAtlas.class);
            Assets_level7.pathInfoSet = (EnemyMutiMove.IPathInfoSet)var2.get("levelData/level7/level_7_PathInfo.ini", EnemyMutiMove.IPathInfoSet.class);
            Assets_level7.atlas_Enemy = (TextureAtlas)var2.get("enemy/level7/level7.atlas", TextureAtlas.class);
            return null;
         } catch (Throwable var7) {
            Settings.w(var7.toString());
            Assets_level7.atlas_Enemy = null;
         } finally {
            Assets_level7.load();
         }

         return null;
      }
   };
   private static final String pathInfoFile = "levelData/level7/level_7_PathInfo.ini";
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
         Settings.w("Assets Load failed.");
         atlas_Enemy = new TextureAtlas(Gdx.files.internal("enemy/level7/level7.atlas"));
      }

      BossLv7L.loadResource();
      BossLv7LSilk.loadResource();
      BossLv7LSlaw.loadResource();
      BossLv7S.loadResource();
      BossLv7STube.loadResource();
   }
}
