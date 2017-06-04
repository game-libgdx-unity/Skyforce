package com.doodlegames.air.force.resource;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.doodlegames.air.force.enemy.level2.BigUfo;
import com.doodlegames.air.force.enemy.level2.BossL;
import com.doodlegames.air.force.enemy.level2.BossLBody;
import com.doodlegames.air.force.enemy.level2.BossLCanon;
import com.doodlegames.air.force.enemy.level2.BossLHead;
import com.doodlegames.air.force.enemy.level2.BossLRocket;
import com.doodlegames.air.force.enemy.level2.BossS;
import com.doodlegames.air.force.enemy.level2.BossSLeftArm;
import com.doodlegames.air.force.enemy.level2.BossSRightArm;
import com.doodlegames.air.force.enemy.level2.LandCannon;
import com.doodlegames.air.force.enemy.level2.RocketVehicle;
import com.doodlegames.air.force.enemy.level2.Tank;
import com.doodlegames.air.force.game.path.EnemyMutiMove;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.screen.LoadingScreen;
import com.doodlegames.air.force.utils.Settings;

public class Assets_level2 {

   public static TextureAtlas atlas_Enemy;
   public static TextureAtlas atlas_bg;
   public static final String bgFilePath = "bg/lv_2/bg_level2.atlas";
   private static final String enemyFilePath = "enemy/level2/level2.atlas";
   public static LoadingScreen.LoadingInfo info = new LoadingScreen.LoadingInfo() {
      public void fillAssetManager(AssetManager var1) {
         var1.load("levelData/level2/level_2_PathInfo.ini", EnemyMutiMove.IPathInfoSet.class);
         var1.load("enemy/level2/level2.atlas", TextureAtlas.class);
         var1.load("bg/lv_2/bg_level2.atlas", TextureAtlas.class);
      }
      public Screen loadingFinished(Game var1, AssetManager var2) {
         try {
            Assets_level2.pathInfoSet = (EnemyMutiMove.IPathInfoSet)var2.get("levelData/level2/level_2_PathInfo.ini", EnemyMutiMove.IPathInfoSet.class);
            Assets_level2.atlas_Enemy = (TextureAtlas)var2.get("enemy/level2/level2.atlas", TextureAtlas.class);
            Assets_level2.atlas_bg = (TextureAtlas)var2.get("bg/lv_2/bg_level2.atlas", TextureAtlas.class);
            return null;
         } catch (Throwable var7) {
            Settings.w(var7.toString());
            Assets_level2.atlas_Enemy = null;
            Assets_level2.atlas_bg = null;
         } finally {
            Assets_level2.load();
         }

         return null;
      }
   };
   private static final String pathInfoFile = "levelData/level2/level_2_PathInfo.ini";
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
      if(atlas_bg == null) {
         Settings.w("Assets load fail");
         atlas_bg = new TextureAtlas(Gdx.files.internal("bg/lv_2/bg_level2.atlas"));
      }

      if(atlas_Enemy == null) {
         Settings.w("Assets load fail");
         atlas_Enemy = new TextureAtlas(Gdx.files.internal("enemy/level2/level2.atlas"));
      }

      BigUfo.loadResource();
      BossL.loadResource();
      BossLBody.loadResource();
      BossLCanon.loadResource();
      BossLHead.loadResource();
      BossLRocket.loadResource();
      BossSLeftArm.loadResource();
      BossSRightArm.loadResource();
      BossS.loadResource();
      LandCannon.loadResource(atlas_Enemy);
      Tank.loadResource(atlas_Enemy);
      RocketVehicle.loadResource(atlas_Enemy);
   }
}
