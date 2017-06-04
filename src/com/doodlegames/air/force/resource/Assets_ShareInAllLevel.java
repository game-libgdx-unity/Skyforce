package com.doodlegames.air.force.resource;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.doodlegames.air.force.enemy.level1.Ufo_a;
import com.doodlegames.air.force.enemy.level1.Ufo_b;
import com.doodlegames.air.force.enemy.level2.Robot;
import com.doodlegames.air.force.enemy.level2.RocketVehicle;
import com.doodlegames.air.force.enemy.level2.Tank;
import com.doodlegames.air.force.enemy.level3.AlienCanon;
import com.doodlegames.air.force.enemy.level3.HightRobot;
import com.doodlegames.air.force.enemy.level4.AlianRobotLeft;
import com.doodlegames.air.force.enemy.level4.AlianRobotRight;
import com.doodlegames.air.force.enemy.level4.AlienTank;
import com.doodlegames.air.force.enemy.level5.AlienAirCraft;
import com.doodlegames.air.force.enemy.level5.AlienBattleShipA;
import com.doodlegames.air.force.enemy.level5.AlienBattleShipB;
import com.doodlegames.air.force.enemy.level5.AlienUfoA;
import com.doodlegames.air.force.enemy.level5.AlienUfoB;
import com.doodlegames.air.force.enemy.level5.BigRailGunerD;
import com.doodlegames.air.force.enemy.level5.BigRailGunerU;
import com.doodlegames.air.force.enemy.level5.BossLv5AlienCanon;
import com.doodlegames.air.force.enemy.level6.AlienMech;
import com.doodlegames.air.force.game.bullet.FighterBullet;
import com.doodlegames.air.force.game.laser.EnemyLaser1;
import com.doodlegames.air.force.resource.Assets_cannons;
import com.doodlegames.air.force.screen.LoadingScreen;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Settings;

public class Assets_ShareInAllLevel {

   public static TextureAtlas atlasBullets;
   public static TextureAtlas atlasGameUI;
   public static TextureAtlas atlasSharedEnemy;
   private static final String bulletFilePath = "bullets/bullets.atlas";
   private static final String enemyFilePath = "enemy/allLevel/allLevel.atlas";
   private static final String gameUIFilePath = "UI/gameUI.atlas";
   public static LoadingScreen.LoadingInfo info = new LoadingScreen.LoadingInfo() {
      public void fillAssetManager(AssetManager var1) {
         var1.load("UI/gameUI.atlas", TextureAtlas.class);
         var1.load("enemy/allLevel/allLevel.atlas", TextureAtlas.class);
         var1.load("bullets/bullets.atlas", TextureAtlas.class);
      }
      public Screen loadingFinished(Game var1, AssetManager var2) {
         try {
            Assets_ShareInAllLevel.atlasGameUI = (TextureAtlas)var2.get("UI/gameUI.atlas", TextureAtlas.class);
            Assets_ShareInAllLevel.atlasSharedEnemy = (TextureAtlas)var2.get("enemy/allLevel/allLevel.atlas", TextureAtlas.class);
            Assets_ShareInAllLevel.atlasBullets = (TextureAtlas)var2.get("bullets/bullets.atlas", TextureAtlas.class);
            return null;
         } catch (Throwable var7) {
            Assets_ShareInAllLevel.atlasGameUI = null;
            Assets_ShareInAllLevel.atlasSharedEnemy = null;
            Assets_ShareInAllLevel.atlasBullets = null;
            Settings.w(var7.toString());
         } finally {
            Assets_ShareInAllLevel.load();
         }

         return null;
      }
   };
   public static Animation launchAnimation;
   public static Animation testAnimation;
   public static TextureAtlas.AtlasRegion test_Bullet_1;
   public static TextureAtlas.AtlasRegion test_Bullet_2;
   public static TextureAtlas.AtlasRegion test_Bullet_3;
   public static TextureAtlas.AtlasRegion test_boom;


   public static void load() {
      if(atlasGameUI == null) {
         Settings.w("Assets Load failed");
         atlasGameUI = new TextureAtlas(Gdx.files.internal("UI/gameUI.atlas"));
      }

      if(atlasSharedEnemy == null) {
         Settings.w("Assets Load failed.");
         atlasSharedEnemy = new TextureAtlas(Gdx.files.internal("enemy/allLevel/allLevel.atlas"));
      }

      Assets_cannons.load();
      if(atlasBullets == null) {
         atlasBullets = new TextureAtlas(Gdx.files.internal("bullets/bullets.atlas"));
      }

      test_boom = atlasBullets.findRegion("test_boom");
      test_Bullet_1 = atlasBullets.findRegion("test_Bullet", 1);
      test_Bullet_2 = atlasBullets.findRegion("test_Bullet", 2);
      test_Bullet_3 = atlasBullets.findRegion("test_Bullet", 3);
      TextureRegion[] var0 = new TextureRegion[]{new TextureRegion(test_boom), new TextureRegion(test_Bullet_1)};
      testAnimation = new Animation(0.05F, var0);
      testAnimation.setPlayMode(0);
      Animation var1 = launchAnimation;
      TextureRegion[] var2 = new TextureRegion[]{atlasGameUI.findRegion("launch_press", 0), atlasGameUI.findRegion("launch_press", 1), atlasGameUI.findRegion("launch_press", 2)};
      launchAnimation = Animation.setAnimationFrame(var1, 1, 0.055555556F, var2);
      Ufo_b.loadResource();
      Ufo_a.loadResource();
      Robot.loadResource(atlasSharedEnemy);
      RocketVehicle.loadResource(atlasSharedEnemy);
      Tank.loadResource(atlasSharedEnemy);
      AlienCanon.loadResource();
      HightRobot.loadResource();
      AlianRobotLeft.loadResource();
      AlianRobotRight.loadResource();
      AlienTank.loadResource();
      AlienAirCraft.loadResource();
      AlienBattleShipA.loadResource();
      AlienBattleShipB.loadResource();
      AlienUfoA.loadResource();
      AlienUfoB.loadResource();
      BigRailGunerD.loadResource();
      BigRailGunerU.loadResource();
      BossLv5AlienCanon.loadResource();
      AlienMech.loadResource();
      EnemyLaser1.loadResource();
      FighterBullet.loadResource();
   }
}
