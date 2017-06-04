package com.doodlegames.air.force.resource;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.doodlegames.air.force.enemy.level5.AlienAeroBoatA;
import com.doodlegames.air.force.enemy.level5.AlienAeroBoatB;
import com.doodlegames.air.force.enemy.level5.BossLv5;
import com.doodlegames.air.force.enemy.level5.BossLv5Canon;
import com.doodlegames.air.force.enemy.level5.BossLv5Shield;
import com.doodlegames.air.force.enemy.level5.BossLv5Stone1;
import com.doodlegames.air.force.enemy.level5.BossLv5Stone2;
import com.doodlegames.air.force.enemy.level5.BossLv5Stone3;
import com.doodlegames.air.force.game.path.EnemyMutiMove;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.screen.LoadingScreen;
import com.doodlegames.air.force.utils.Settings;

public class Assets_level5 {

   public static final String atlas_BGPath = "bg/lv_5/bg_level5.atlas";
   public static TextureAtlas atlas_Enemy;
   public static TextureAtlas atlas_bg;
   private static final String enemyFilePath = "enemy/level5/level5.atlas";
   public static LoadingScreen.LoadingInfo info = new LoadingScreen.LoadingInfo() {
      public void fillAssetManager(AssetManager var1) {
         var1.load("bg/lv_5/bg_level5.atlas", TextureAtlas.class);
         var1.load("levelData/level5/level_5_PathInfo.ini", EnemyMutiMove.IPathInfoSet.class);
         var1.load("enemy/level5/level5.atlas", TextureAtlas.class);
      }
      public Screen loadingFinished(Game var1, AssetManager var2) {
         try {
            Assets_level5.atlas_bg = (TextureAtlas)var2.get("bg/lv_5/bg_level5.atlas", TextureAtlas.class);
            Assets_level5.pathInfoSet = (EnemyMutiMove.IPathInfoSet)var2.get("levelData/level5/level_5_PathInfo.ini", EnemyMutiMove.IPathInfoSet.class);
            Assets_level5.atlas_Enemy = (TextureAtlas)var2.get("enemy/level5/level5.atlas", TextureAtlas.class);
            return null;
         } catch (Throwable var7) {
            Settings.w(var7.toString());
            Assets_level5.atlas_Enemy = null;
         } finally {
            Assets_level5.load();
         }

         return null;
      }
   };
   private static final String pathInfoFile = "levelData/level5/level_5_PathInfo.ini";
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
         Settings.w("assets load fail");
         atlas_Enemy = new TextureAtlas(Gdx.files.internal("enemy/level5/level5.atlas"));
      }

      AlienAeroBoatA.loadResource();
      AlienAeroBoatB.loadResource();
      BossLv5.loadResource();
      BossLv5Canon.loadResource();
      BossLv5Shield.loadResource();
      BossLv5Stone1.loadResource();
      BossLv5Stone2.loadResource();
      BossLv5Stone3.loadResource();
   }
}
