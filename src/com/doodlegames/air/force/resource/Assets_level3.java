package com.doodlegames.air.force.resource;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.doodlegames.air.force.enemy.level2.Robot;
import com.doodlegames.air.force.enemy.level2.RocketVehicle;
import com.doodlegames.air.force.enemy.level2.Tank;
import com.doodlegames.air.force.enemy.level3.BossLv3;
import com.doodlegames.air.force.enemy.level3.BossLv3Building1;
import com.doodlegames.air.force.enemy.level3.BossLv3Building2;
import com.doodlegames.air.force.enemy.level3.BossLv3Building3;
import com.doodlegames.air.force.enemy.level3.BossLv3Building4;
import com.doodlegames.air.force.enemy.level3.BossLv3Cannon;
import com.doodlegames.air.force.enemy.level3.MoonCanon;
import com.doodlegames.air.force.enemy.level3.SpaceBattleShip;
import com.doodlegames.air.force.game.path.EnemyMutiMove;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.screen.LoadingScreen;
import com.doodlegames.air.force.screen.XScreen;
import com.doodlegames.air.force.utils.Settings;

public class Assets_level3 {

   public static final String atlas_BGPath = "bg/lv_3/bg_level3.atlas";
   public static TextureAtlas atlas_Enemy;
   public static TextureAtlas atlas_bg;
   public static Texture buildingShadow;
   private static final String enemyFilePath = "enemy/level3/level3.atlas";
   public static LoadingScreen.LoadingInfo info = new LoadingScreen.LoadingInfo() {
      public void fillAssetManager(AssetManager var1) {
         var1.load("bg/lv_3/bg_level3.atlas", TextureAtlas.class);
         var1.load("levelData/level3/level_3_PathInfo.ini", EnemyMutiMove.IPathInfoSet.class);
         var1.load("enemy/level3/level3.atlas", TextureAtlas.class);
      }
      public Screen loadingFinished(Game var1, AssetManager var2) {
         try {
            Assets_level3.atlas_bg = (TextureAtlas)var2.get("bg/lv_3/bg_level3.atlas", TextureAtlas.class);
            Assets_level3.pathInfoSet = (EnemyMutiMove.IPathInfoSet)var2.get("levelData/level3/level_3_PathInfo.ini", EnemyMutiMove.IPathInfoSet.class);
            Assets_level3.atlas_Enemy = (TextureAtlas)var2.get("enemy/level3/level3.atlas", TextureAtlas.class);
            return null;
         } catch (Throwable var7) {
            Settings.w(var7.toString());
            Assets_level3.atlas_Enemy = null;
         } finally {
            Assets_level3.load();
         }

         return null;
      }
   };
   private static final String pathInfoFile = "levelData/level3/level_3_PathInfo.ini";
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
         atlas_Enemy = new TextureAtlas(Gdx.files.internal("enemy/level3/level3.atlas"));
      }

      Pixmap var0 = new Pixmap(2, 2, Pixmap.Format.RGBA8888);
      var0.setColor(0.07058824F, 0.101960786F, 0.1254902F, 0.8F);
      var0.fill();
      buildingShadow = new Texture(new XScreen.ManagedPixmapTextureData(var0, (Pixmap.Format)null, false, false));
      BossLv3.loadResource();
      BossLv3Building1.loadResource();
      BossLv3Building2.loadResource();
      BossLv3Building3.loadResource();
      BossLv3Building4.loadResource();
      BossLv3Cannon.loadResource();
      MoonCanon.loadResource();
      SpaceBattleShip.loadResource();
      Tank.loadResource(atlas_Enemy);
      RocketVehicle.loadResource(atlas_Enemy);
      Robot.loadResource(atlas_Enemy);
   }
}
