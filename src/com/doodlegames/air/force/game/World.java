package com.doodlegames.air.force.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyProXY;
import com.doodlegames.air.force.enemy.level1.BossAircraftCarrier;
import com.doodlegames.air.force.enemy.level1.Ufo_a;
import com.doodlegames.air.force.enemy.level1.Ufo_b;
import com.doodlegames.air.force.enemy.level2.BossL;
import com.doodlegames.air.force.enemy.level2.BossS;
import com.doodlegames.air.force.enemy.level2.RocketVehicle;
import com.doodlegames.air.force.enemy.level2.Tank;
import com.doodlegames.air.force.enemy.level3.BossLv3;
import com.doodlegames.air.force.enemy.level4.AlienTank;
import com.doodlegames.air.force.enemy.level4.BossLv4;
import com.doodlegames.air.force.enemy.level4.TestWeapon;
import com.doodlegames.air.force.enemy.level4.TestWeaponCore;
import com.doodlegames.air.force.enemy.level5.BossLv5;
import com.doodlegames.air.force.enemy.level6.BossLv6;
import com.doodlegames.air.force.enemy.level7.BossLv7L;
import com.doodlegames.air.force.enemy.level7.BossLv7S;
import com.doodlegames.air.force.fighters.F22;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.fighters.FighterWorldBoomer;
import com.doodlegames.air.force.fighters.J20;
import com.doodlegames.air.force.fighters.Me262;
import com.doodlegames.air.force.fighters.Rafale;
import com.doodlegames.air.force.fighters.Su47;
import com.doodlegames.air.force.fighters.Typhoon;
import com.doodlegames.air.force.game.BigBackGround;
import com.doodlegames.air.force.game.EnemyLoader;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.SmallBackground;
import com.doodlegames.air.force.game.WorldAnimationPlayer;
import com.doodlegames.air.force.game.WorldAudio;
import com.doodlegames.air.force.game.WorldEvents;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.EnemyBullet2;
import com.doodlegames.air.force.game.bullet.FighterBullet;
import com.doodlegames.air.force.game.laser.Laser;
import com.doodlegames.air.force.game.path.AbstractEnemyPath;
import com.doodlegames.air.force.game.prop.Prop;
import com.doodlegames.air.force.resource.Assets_Audio;
import com.doodlegames.air.force.resource.Assets_ShareInNazi;
import com.doodlegames.air.force.resource.Assets_fighter;
import com.doodlegames.air.force.screen.GameScreen;
import com.doodlegames.air.force.screen.screenActor.GameEvent;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.MusicPlayer;
import com.doodlegames.air.force.utils.OverlapTester;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class World {

   private static final float CameraLeftLimit = 240.0F;
   private static final float CameraRightLimit = 400.0F;
   private static final int REFRESHPARTS = Math.round(640.0F);
   public static final float WORLD_HEIGHT = 6400.0F;
   public static final int WORLD_HEIGHT_PIXEL = 6400;
   public static final int WORLD_STATE_GAME_OVER = 2;
   public static final int WORLD_STATE_NEXT_LEVEL = 1;
   public static final int WORLD_STATE_RUNNING = 0;
   public static final float WORLD_WIDTH = 640.0F;
   public static final int WORLD_WIDTH_PIXEL = 640;
   private static List<Enemy> enemys = new ArrayList();
   public Pool<AlienTank> AlienTankPool;
   public Vector2 CamPosition;
   public Pool<RocketVehicle> RocketVehiclePool;
   public Pool<Tank> TankPool;
   public Pool<Ufo_a> Ufo_aPool;
   public Pool<Ufo_b> Ufo_bPool;
   public BigBackGround bigBG;
   private FighterWorldBoomer boomer;
   private float delayTimeToNextLevel = 1.0F;
   public World.DelayChangeToNextLevel delayToNextLevel;
   private boolean[] enemyAppear;
   public List<Enemy> enemysOnAir;
   public List<Enemy> enemysOnSeaOrLand;
   private GameEvent eventProcessor;
   public Fighter fighter;
   private int fighterLives;
   public ArrayList<World.BulletHitEnemyListener> hitCheckListeners = new ArrayList();
   public int level;
   private Array<World.RenderObject> objectBetweenBG = new Array();
   private final GameScreen parentScreen;
   private World.DelayTimeToNextProvider provider = null;
   public final Random rand;
   public SmallBackground smallBG;
   private World.RenderObject snakeGun = null;
   public int state;
   private boolean stopFighting = false;
   private float viewPosition;
   public Array<World.WorldAction> worldActions = new Array(4);
   public WorldAudio worldAudio;


   public World(GameScreen var1, int var2) {
      this.parentScreen = var1;
      this.eventProcessor = new GameEvent(this);
      WorldEvents.loadListeners(this);
      this.fighter = null;
      this.enemysOnSeaOrLand = new ArrayList();
      this.enemysOnAir = new ArrayList();
      this.level = var2;
      this.rand = new Random();
      this.state = 0;
      this.CamPosition = new Vector2();
      this.viewPosition = 0.0F;
      this.enemyAppear = new boolean[REFRESHPARTS];
      this.Ufo_aPool = new Pool(2, 5) {
         protected Ufo_a newObject() {
            return new Ufo_a(World.this, 0.0F, 0.0F);
         }
      };
      this.Ufo_bPool = new Pool(2, 5) {
         protected Ufo_b newObject() {
            return new Ufo_b(World.this, 0.0F, 0.0F);
         }
      };
      this.TankPool = new Pool(2, 5) {
         protected Tank newObject() {
            return new Tank(World.this, 0.0F, 0.0F);
         }
      };
      this.RocketVehiclePool = new Pool(2, 5) {
         protected RocketVehicle newObject() {
            return new RocketVehicle(World.this, 0.0F, 0.0F);
         }
      };
      this.AlienTankPool = new Pool(2, 5) {
         protected AlienTank newObject() {
            return new AlienTank(World.this, 0.0F, 0.0F);
         }
      };
      this.worldAudio = new WorldAudio(this);
      MusicPlayer.musicPlayer.clearCurrentMusic();
      this.playBGMusic();
      this.generateLevel(this.level);
      this.boomer = this.fighter;
      this.delayToNextLevel = (World.DelayChangeToNextLevel)Pools.obtain(World.DelayChangeToNextLevel.class);
   }

   private void askSetStopFight(Boolean var1) {
      boolean var2 = this.stopFighting;
      this.stopFighting = var1.booleanValue();
      this.fireWorldFightSetEvent(var2, var1.booleanValue());
   }

   private void checkCollisions() {
      this.checkFighterBulletHitEnemy(this.getAllEnemy());
      this.checkFighterBulletHitMissle();
      this.checkEnemyBulletHitFighter();
      this.checkEnemyLaseHitFighter();
      this.checkFighterHitEnemy();
      this.checkFighterHitProp();
   }

   private void checkEnemyBulletHitFighter() {
      if(this.fighter.state != 3) {
         int var1 = Enemy.bullets.size();

         for(int var2 = 0; var2 < var1; ++var2) {
            Bullet var3 = (Bullet)Enemy.bullets.get(var2);
            if(var3.isAvalible()) {
               List var4 = this.fighter.getHitRectangle();
               int var5 = var4.size();
               int var6 = 0;

               boolean var7;
               while(true) {
                  var7 = false;
                  if(var6 >= var5) {
                     break;
                  }

                  if(OverlapTester.pointInRectangle((Rectangle)var4.get(var6), var3.position.x, var3.position.y)) {
                     var7 = true;
                     break;
                  }

                  ++var6;
               }

               if(var7) {
                  this.fighter.hitByBullet(var3);
                  var3.hitSomething();
               }
            }
         }
      }

   }

   private void checkEnemyLaseHitFighter() {
      int var1 = Enemy.lasers.size();

      for(int var2 = 0; var2 < var1; ++var2) {
         Laser var3 = (Laser)Enemy.lasers.get(var2);
         if(var3.isAvalible()) {
            List var4 = this.fighter.getHitRectangle();
            int var5 = var4.size();
            int var6 = 0;

            boolean var7;
            while(true) {
               var7 = false;
               if(var6 >= var5) {
                  break;
               }

               if(OverlapTester.overlapRectangles((Rectangle)var4.get(var6), var3.bounds, (Rectangle)null)) {
                  var7 = true;
                  break;
               }

               ++var6;
            }

            if(var7) {
               this.fighter.hitByLaser(var3);
               var3.hitSomething();
               break;
            }
         }
      }

   }

   private void checkFighterBulletHitEnemy(List<Enemy> var1) {
      Iterator var2 = this.hitCheckListeners.iterator();

      while(var2.hasNext()) {
         ((World.BulletHitEnemyListener)var2.next()).beginCheck();
      }

      Iterator var3 = var1.iterator();

      label58:
      while(var3.hasNext()) {
         Enemy var5 = (Enemy)var3.next();
         if(!var5.isCrashed() && Settings.gameObjectInScreen(var5) == Settings.PositionType.InScreenMiddle) {
            List var6 = var5.getHitRectangle();
            int var7 = var6.size();
            Iterator var8 = this.hitCheckListeners.iterator();

            while(var8.hasNext()) {
               ((World.BulletHitEnemyListener)var8.next()).checking(var5, var6);
            }

            int var9 = 0;

            while(var9 < var7) {
               Rectangle var10 = (Rectangle)var6.get(var9);
               Iterator var11 = Fighter.bullets.iterator();

               while(true) {
                  if(var11.hasNext()) {
                     Bullet var12 = (Bullet)var11.next();
                     if(!var12.isAvalible()) {
                        continue;
                     }

                     Rectangle var13 = var12.getHitRec();
                     if(!OverlapTester.overlapRectangles(var10, var13)) {
                        continue;
                     }

                     this.addHitAnimation(var10, var13, var5.getHitAnimation(var12));
                     var5.beHitByBullet(var12, var9);
                     var12.hitSomething();
                     if(!var5.isCrashed()) {
                        continue;
                     }
                  }

                  if(var5.isCrashed()) {
                     continue label58;
                  }

                  ++var9;
                  break;
               }
            }
         }
      }

      Iterator var4 = this.hitCheckListeners.iterator();

      while(var4.hasNext()) {
         ((World.BulletHitEnemyListener)var4.next()).endCheck();
      }

   }

   private void checkFighterBulletHitMissle() {
      Iterator var1 = Enemy.bullets.iterator();

      while(var1.hasNext()) {
         Bullet var2 = (Bullet)var1.next();
         GameObject var3 = var2.getOwner();
         if(var2 instanceof EnemyBullet2 && var3 != null && var3 instanceof Enemy) {
            Iterator var4 = Fighter.bullets.iterator();

            while(var4.hasNext()) {
               Bullet var5 = (Bullet)var4.next();
               Rectangle var6 = var5.getHitRec();
               Rectangle var7 = var2.getHitRec();
               if(OverlapTester.overlapRectangles(var6, var7)) {
                  Animation var8 = ((Enemy)var3).getHitAnimation(var5);
                  if(var8 != Assets_ShareInNazi.hitBoom) {
                     break;
                  }

                  var2.makeUsed();
                  var5.makeUsed();
                  this.addHitAnimation(var6, var7, var8);
               }
            }
         }
      }

   }

   private void checkFighterHitEnemy() {
      List var1 = this.fighter.getHitRectangle();
      Iterator var2 = this.enemysOnAir.iterator();

      while(var2.hasNext()) {
         Enemy var3 = (Enemy)var2.next();
         if(this.fighter.isCrashed()) {
            break;
         }

         if(!var3.isCrashed()) {
            Iterator var4 = var3.getFighterHitRecs().iterator();

            while(var4.hasNext()) {
               Rectangle var5 = (Rectangle)var4.next();
               if(this.fighter.isCrashed()) {
                  break;
               }

               Iterator var6 = var1.iterator();

               while(var6.hasNext()) {
                  if(OverlapTester.overlapRectangles(var5, (Rectangle)var6.next())) {
                     this.fighter.hitByEnemy();
                     Bullet var7 = FighterBullet.invisibleBullet.getABullet(this.fighter, this, 0.0F, 0.0F, Vector2.tmp);
                     var7.setPosition(var5.x + var5.width / 2.0F, var5.y + var5.height / 2.0F);
                     var7.damageValue = 4;
                     var7.velocity.set(0.0F, -53.333332F);
                     var7.bulletType = 3;
                     Fighter.bullets.add(var7);
                     break;
                  }
               }
            }
         }
      }

   }

   private void checkFighterHitProp() {
      int var1 = Prop.propSet.size();

      for(int var2 = 0; var2 < var1; ++var2) {
         Prop var3 = (Prop)Prop.propSet.get(var2);
         Fighter var4 = this.fighter;
         List var5 = var3.getHitRectangle();
         List var6 = var4.getPropHitRectangle();
         int var7 = var5.size();
         int var8 = var6.size();
         boolean var9 = false;
         int var10 = 0;

         while(var10 < var7) {
            Rectangle var11 = (Rectangle)var5.get(var10);
            int var12 = 0;

            while(true) {
               if(var12 < var8) {
                  if(OverlapTester.overlapRectangles(var11, (Rectangle)var6.get(var12))) {
                     var9 = true;
                  } else if(!var9) {
                     ++var12;
                     continue;
                  }
               }

               ++var10;
               break;
            }
         }

         if(var9) {
            var3.beHitByFighter(var4);
            var4.beHitByProp(var3);
            this.firePropPickedEvent(var3);
         }
      }

   }

   private void clearCommonBullet() {
      Iterator var1 = Enemy.bullets.iterator();

      while(var1.hasNext()) {
         Bullet var2 = (Bullet)var1.next();
         if(var2.bulletType == 0) {
            var2.makeUsed();
         }
      }

   }

   private void clearObjectInBGM() {
      this.objectBetweenBG.clear();
   }

   private boolean enemyShouldRemove(Enemy var1) {
      if(!(var1 instanceof EnemyProXY) && var1.outRemove) {
         if(var1.isCrashed() && var1.stateTime > var1.getTimeOfCrash()) {
            return true;
         }

         AbstractEnemyPath var2 = var1.getMoveProcessor();
         Settings.PositionType var3 = Settings.gameObjectInScreen(var1);
         if(var2 == null && var3 != Settings.PositionType.InScreenMiddle) {
            return true;
         }

         if(var2 == EnemyProXY.BGMARK && var3 != Settings.PositionType.InScreenMiddle) {
            return true;
         }

         if(var2 != null && var2.isOver() && var3 != Settings.PositionType.InScreenMiddle) {
            return true;
         }

         if(var3 == Settings.PositionType.UnderFarScreen || var3 == Settings.PositionType.InScreenFarRight || var3 == Settings.PositionType.InScreenFarLeft) {
            return true;
         }
      }

      return false;
   }

   private void fireBoomEvent() {
      WorldEvents.BoomUsingEvent var1 = (WorldEvents.BoomUsingEvent)Pools.obtain(WorldEvents.BoomUsingEvent.class);
      var1.world = this;
      this.eventProcessor.notifyListener(var1);
      Pools.free((Object)var1);
   }

   private void fireEnemyRemovedEvent(Enemy var1) {
      WorldEvents.EnemyRemovedEvent var2 = (WorldEvents.EnemyRemovedEvent)Pools.obtain(WorldEvents.EnemyRemovedEvent.class);
      var2.world = this;
      var2.enemy = var1;
      this.eventProcessor.notifyListener(var2);
      Pools.free((Object)var2);
   }

   private void fireLevelStartEvent(int var1) {
      WorldEvents.LevelStartEvent var2 = (WorldEvents.LevelStartEvent)Pools.obtain(WorldEvents.LevelStartEvent.class);
      var2.world = this;
      var2.level = var1;
      this.eventProcessor.notifyListener(var2);
      Pools.free((Object)var2);
   }

   private void firePropPickedEvent(Prop var1) {
      WorldEvents.PropPickedEvent var2 = (WorldEvents.PropPickedEvent)Pools.obtain(WorldEvents.PropPickedEvent.class);
      var2.prop = var1;
      var2.world = this;
      this.eventProcessor.notifyListener(var2);
      Pools.free((Object)var2);
   }

   private void fireWorldFightSetEvent(boolean var1, boolean var2) {
      WorldEvents.WorldFightSetEvent var3 = (WorldEvents.WorldFightSetEvent)Pools.obtain(WorldEvents.WorldFightSetEvent.class);
      var3.world = this;
      var3.oldState = var1;
      var3.newState = var2;
      this.eventProcessor.notifyListener(var3);
      Pools.free((Object)var3);
   }

   private void fireWorldRestartEvent() {
      WorldEvents.WorldRestartEvent var1 = (WorldEvents.WorldRestartEvent)Pools.obtain(WorldEvents.WorldRestartEvent.class);
      var1.world = this;
      this.eventProcessor.notifyListener(var1);
      Pools.free((Object)var1);
   }

   private void generateBG(int var1) {
      this.smallBG = SmallBackground.getBackGround(var1);
      if(this.level != 3 && this.level != 5 && this.level != 6) {
         this.bigBG = BigBackGround.getBigBackGround(var1);
      } else {
         this.bigBG = null;
      }
   }

   private void generateEnemy(int var1) {
      EnemyLoader.fillEnemySet(this, this.enemysOnSeaOrLand, this.enemysOnAir, var1);
      if(var1 == 1) {
         BossAircraftCarrier.addABossLv1(this, 320.0F, 127.0F + 5600.0F + 634.0F, 634.0F, this.enemysOnAir);
      }

      if(var1 == 2) {
         float var11 = 560.0F - (float)BossL.regionHeight / 2.0F;
         BossL.addABossLv2(this, 960.0F, 5600.0F + var11 + (float)BossL.regionHeight / 2.0F - 2.0F, var11, this.enemysOnSeaOrLand);
         float var13 = 580.0F - BossS.getRegionHeight() / 2.0F;
         BossS.addABossSLv2(this, 986.0F, 2500.0F + var13 + BossS.getRegionHeight() / 2.0F, var13, this.enemysOnSeaOrLand);
      }

      if(var1 == 3) {
         float var9 = 460.0F - (float)BossLv3.LAND1HEIGHT / 2.0F;
         BossLv3.addABossLv3(this, 317.0F, 5600.0F + var9 + (float)BossLv3.LAND1HEIGHT / 2.0F, var9, this.enemysOnSeaOrLand);
      }

      if(var1 == 4) {
         BossLv4.addABossLv4(this, 450.0F, 6030.0F, 430.0F - (float)(BossLv4.SHADOWHEIGHT / 2), this.enemysOnSeaOrLand);
         TestWeapon.addATestWeapon(this, 320.0F, 3200.0F + 578.0F + (float)TestWeaponCore.HEIGHT / 2.0F - 250.0F, 578.0F, this.enemysOnAir);
      }

      if(var1 == 5) {
         BossLv5.addABossLv5(this, 320.0F, 6100.0F, 335.0F, this.enemysOnSeaOrLand);
      }

      if(var1 == 6) {
         BossLv6.addABossLv6(this, 320.0F, 6240.0F, this.enemysOnSeaOrLand);
      }

      if(var1 == 7) {
         float var2 = 623.0F - (float)BossLv7L.HEIGHT / 2.0F;
         BossLv7L.addABossLv7(this, 320.0F, 5600.0F + var2 + (float)BossLv7L.HEIGHT / 2.0F - 1.0F, var2, this.enemysOnAir);
         BossLv7S.addABossLv7S(this, 313.0F, 3182.0F, 782.0F - (float)BossLv7S.HEIGHT / 2.0F - 130.0F, this.enemysOnSeaOrLand);
      }

   }

   private void generateLevel(int var1) {
      this.clearObjectInBGM();
      this.setSnakeGunRenderInterface((World.RenderObject)null);
      switch(Assets_fighter.fighterIndex) {
      case 0:
         this.fighter = new F22(this, 320.0F, 44.5F);
         break;
      case 1:
         this.fighter = new J20(this, 320.0F, 44.5F);
         break;
      case 2:
         this.fighter = new Rafale(this, 320.0F, 44.5F);
         break;
      case 3:
         this.fighter = new Su47(this, 320.0F, 44.5F);
         break;
      case 4:
         this.fighter = new Typhoon(this, 320.0F, 44.5F);
         break;
      case 5:
         this.fighter = new Me262(this, 320.0F, 44.5F);
      }

      this.boomer = this.fighter;
      this.reFillHitCheckListener();
      this.fighterLives = this.fighter.iniLives;
      this.fighter.setBoomNumFull();
      this.enemysOnSeaOrLand.clear();
      this.enemysOnAir.clear();
      if(this.smallBG != null) {
         this.smallBG.reset();
      }

      if(this.bigBG != null) {
         this.bigBG.reset();
      }

      Fighter.bullets.clear();
      Enemy.bullets.clear();
      Enemy.lasers.clear();
      Prop.propSet.clear();
      Settings.backgroundVelocity.y = -53.333332F;
      this.state = 0;
      this.CamPosition.x = 320.0F;
      this.CamPosition.y = 400.0F;
      this.viewPosition = 0.0F;

      for(int var2 = 0; var2 < REFRESHPARTS; ++var2) {
         this.enemyAppear[var2] = false;
      }

      this.Ufo_aPool.clear();
      this.Ufo_bPool.clear();
      System.gc();
      this.generateBG(var1);
      this.generateEnemy(var1);
      this.fireLevelStartEvent(var1);
   }

   private List<Enemy> getAllEnemy() {
      enemys.clear();
      int var1 = this.enemysOnSeaOrLand.size();

      for(int var2 = 0; var2 < var1; ++var2) {
         enemys.add(this.enemysOnSeaOrLand.get(var2));
      }

      int var3 = this.enemysOnAir.size();

      for(int var4 = 0; var4 < var3; ++var4) {
         enemys.add(this.enemysOnAir.get(var4));
      }

      return enemys;
   }

   public static World.DelayMoveBg getDelayMoveAction(World var0) {
      World.DelayMoveBg var1 = (World.DelayMoveBg)Pools.obtain(World.DelayMoveBg.class);
      var1.reset(var0);
      return var1;
   }

   private void removeAddPropAction() {
      for(int var1 = -1 + this.worldActions.size; var1 >= 0; --var1) {
         World.WorldAction var2 = (World.WorldAction)this.worldActions.get(var1);
         if(var2 instanceof World.AddProp) {
            this.worldActions.removeIndex(var1);
            ((World.AddProp)var2).reset();
            Pools.free((Object)var2);
         } else {
            Settings.i(var2.getClass().toString());
         }
      }

   }

   private void testAEnemy(Enemy var1) {
      this.enemysOnSeaOrLand.clear();
      this.enemysOnAir.clear();
      var1.velocity.set(0.0F, 0.0F);
      this.enemysOnAir.add(var1);
      Settings.backgroundVelocity.y = 0.0F;
   }

   private void updateBullets(float var1) {
      for(int var2 = -1 + Fighter.bullets.size(); var2 >= 0; --var2) {
         Bullet var10 = (Bullet)Fighter.bullets.get(var2);
         if(!var10.isAvalible() || Settings.gameObjectInScreen(var10) != Settings.PositionType.InScreenMiddle) {
            Fighter.bullets.remove(var10);
            var10.freeToPool();
         }
      }

      int var3 = Fighter.bullets.size();

      for(int var4 = 0; var4 < var3; ++var4) {
         ((Bullet)Fighter.bullets.get(var4)).update(var1);
      }

      for(int var5 = -1 + Enemy.bullets.size(); var5 >= 0; --var5) {
         Bullet var8 = (Bullet)Enemy.bullets.get(var5);
         if(var8.position.y > 800.0F || var8.position.y < 0.0F || var8.position.x > 640.0F || var8.position.x < 0.0F || !var8.isAvalible()) {
            Enemy.bullets.remove(var8);
            var8.freeToPool();
         }
      }

      int var6 = Enemy.bullets.size();

      for(int var7 = 0; var7 < var6; ++var7) {
         ((Bullet)Enemy.bullets.get(var7)).update(var1);
      }

   }

   private void updateCamPosition(float var1) {
      float var2 = this.CamPosition.x - 80.0F;
      float var3 = 80.0F + this.CamPosition.x;
      if(this.fighter.position.x < var2) {
         if(this.CamPosition.x > 240.0F) {
            Vector2 var5 = this.CamPosition;
            var5.x -= var2 - this.fighter.position.x;
            if(this.CamPosition.x < 240.0F) {
               this.CamPosition.x = 240.0F;
            }
         } else {
            this.CamPosition.x = 240.0F;
         }
      }

      if(this.fighter.position.x > var3) {
         if(this.CamPosition.x >= 400.0F) {
            this.CamPosition.x = 400.0F;
            return;
         }

         Vector2 var4 = this.CamPosition;
         var4.x -= var3 - this.fighter.position.x;
         if(this.CamPosition.x > 400.0F) {
            this.CamPosition.x = 400.0F;
         }
      }

   }

   private void updateEnemy(float var1) {
      int var2 = this.enemysOnSeaOrLand.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         ((Enemy)this.enemysOnSeaOrLand.get(var3)).update(var1);
      }

      int var4 = this.enemysOnAir.size();

      for(int var5 = 0; var5 < var4; ++var5) {
         ((Enemy)this.enemysOnAir.get(var5)).update(var1);
      }

      int var6 = this.enemysOnSeaOrLand.size();

      for(int var7 = 0; var7 < var6; ++var7) {
         Enemy var12 = (Enemy)this.enemysOnSeaOrLand.get(var7);
         if(this.enemyShouldRemove(var12)) {
            this.enemysOnSeaOrLand.remove(var12);
            var12.enemyRemoved();
            --var7;
            var6 = this.enemysOnSeaOrLand.size();
            this.fireEnemyRemovedEvent(var12);
         }
      }

      int var8 = this.enemysOnAir.size();

      for(int var9 = 0; var9 < var8; ++var9) {
         Enemy var10 = (Enemy)this.enemysOnAir.get(var9);
         if(this.enemyShouldRemove(var10)) {
            this.enemysOnAir.remove(var10);
            var10.enemyRemoved();
            --var9;
            var8 = this.enemysOnAir.size();
            this.fireEnemyRemovedEvent(var10);
         }
      }

   }

   private void updateFighter(float var1, Vector2 var2) {
      if(this.fighter.state == 3) {
         if(this.fighter.stateTime < this.fighter.getTimeOfCrash()) {
            this.clearCommonBullet();
         } else {
            if(this.fighterLives > 0) {
               this.fighter.recovery();
               this.fighterLives += -1;
               this.fireFighterRecovery(this.fighter);
            }

            if(this.fighter.stateTime > 2.0F + this.fighter.getTimeOfCrash() && this.state != 2) {
               this.state = 2;
               this.worldAudio.playGameOver();
               this.stopMusic();
               this.fireLevelEndEvent(false);
            }
         }
      } else if(this.fighter.appearTime > -2.0F) {
         this.fighter.adjustFlyingAttitude(var1, var2);
         if(this.fighter.position.x < this.fighter.bounds.width / 2.0F) {
            this.fighter.position.x = this.fighter.bounds.width / 2.0F;
         }

         if(this.fighter.position.x > 640.0F - this.fighter.bounds.width / 2.0F) {
            this.fighter.position.x = 640.0F - this.fighter.bounds.width / 2.0F;
         }

         if(this.fighter.position.y < this.CamPosition.y - 400.0F + this.fighter.bounds.height / 2.0F) {
            this.fighter.position.y = this.CamPosition.y - 400.0F + this.fighter.bounds.height / 2.0F;
         }

         if(this.fighter.position.y > 400.0F + this.CamPosition.y - this.fighter.bounds.height / 2.0F) {
            this.fighter.position.y = 400.0F + this.CamPosition.y - this.fighter.bounds.height / 2.0F;
         }
      }

      this.fighter.update(var1);
   }

   private void updateLasers(float var1) {
      int var2 = 0;

      while(var2 < Enemy.lasers.size()) {
         Laser var5 = (Laser)Enemy.lasers.get(var2);
         if(var5.shouldRemove()) {
            Enemy.lasers.remove(var5);
         } else {
            ++var2;
         }
      }

      int var3 = Enemy.lasers.size();

      for(int var4 = 0; var4 < var3; ++var4) {
         ((Laser)Enemy.lasers.get(var4)).update(var1);
      }

   }

   private void updateProp(float var1) {
      Iterator var2 = Prop.propSet.iterator();

      while(var2.hasNext()) {
         ((Prop)var2.next()).update(var1);
      }

      for(int var3 = -1 + Prop.propSet.size(); var3 >= 0; --var3) {
         Prop var4 = (Prop)Prop.propSet.get(var3);
         if(var4.getState() == Prop.PropState.Removed) {
            Prop.propSet.remove(var3);
            var4.freeToPool();
         }
      }

   }

   private void updateWorldActions(float var1) {
      for(int var2 = -1 + this.worldActions.size; var2 >= 0; --var2) {
         World.WorldAction var3 = (World.WorldAction)this.worldActions.get(var2);
         if(var3.act(var1)) {
            this.worldActions.removeIndex(var2);

            try {
               Pools.free((Object)var3);
            } catch (IllegalArgumentException var6) {
               Settings.i(var6.getMessage());
            }
         }
      }

   }

   public void addAAirEnemy(Enemy var1) {
      World.AddEnemy var2 = (World.AddEnemy)Pools.obtain(World.AddEnemy.class);
      var2.enemy = var1;
      var2.enemySet = this.enemysOnAir;
      this.addWorldAction(var2);
   }

   protected void addHitAnimation(Rectangle var1, Rectangle var2, Animation var3) {
      WorldAnimationPlayer.addAWorldAnimation(var3, Math.max(var1.x, var2.x), Math.max(var1.y, var2.y), 0, this.worldAudio.playBulletHitEnemy);
   }

   public void addObjectToBGM(World.RenderObject var1) {
      this.objectBetweenBG.add(var1);
   }

   public void addProp(Prop var1, float var2) {
      World.AddProp var3 = (World.AddProp)Pools.obtain(World.AddProp.class);
      var3.prop = var1;
      var3.timeStamp = var2 + Clock.getCurrentTime();
      this.addWorldAction(var3);
   }

   public void addWorldAction(World.WorldAction var1) {
      this.worldActions.add(var1);
   }

   public void addWorldEventListener(GameEvent.GameEventListener var1) {
      this.eventProcessor.addListener(var1);
   }

   public void askBeginFighting() {
      if(this.getStopFighting()) {
         this.askSetStopFight(Boolean.valueOf(false));
      } else {
         Settings.i("begin fighting set failed.");
      }
   }

   public void askBeginFighting(float var1) {
      if(this.getStopFighting()) {
         World.DelayEnableWorldShoot var2 = (World.DelayEnableWorldShoot)Pools.obtain(World.DelayEnableWorldShoot.class);
         var2.reset(this, var1);
         this.worldActions.add(var2);
      }

   }

   public void askStopFighting() {
      if(!this.getStopFighting()) {
         this.askSetStopFight(Boolean.valueOf(true));
      } else {
         Settings.i("stop fighting set failed.");
      }
   }

   public void boomTheWorld(int var1) {
      if(!this.fighter.isCrashed() && !this.getStopFighting() && !this.boomer.isBooming() && this.fighter.useABoom()) {
         this.boomer.boomStart();
         this.fireBoomEvent();
      }
   }

   public boolean checkEnemyClear() {
      if(this.enemysOnAir.size() <= 2 && this.enemysOnSeaOrLand.size() <= 2) {
         Iterator var1 = this.enemysOnAir.iterator();

         do {
            if(!var1.hasNext()) {
               Iterator var2 = this.enemysOnSeaOrLand.iterator();

               do {
                  if(!var2.hasNext()) {
                     return true;
                  }
               } while(((Enemy)var2.next()).isCrashed());

               return false;
            }
         } while(((Enemy)var1.next()).isCrashed());

         return false;
      } else {
         return false;
      }
   }

   public void dispose() {}

   public int evaluateStar() {
      float var1 = (float)WorldEvents.levelEnemyKilled / (float)WorldEvents.levelEnemyCount;
      return var1 > 0.97F?5:(var1 > 0.85F?4:(var1 > 0.65F?3:(var1 > 0.45F?2:1)));
   }

   public void fireEnemyCrashedEvent(Enemy var1) {
      if(var1 != null) {
         WorldEvents.EnemyCrashedEvent var2 = (WorldEvents.EnemyCrashedEvent)Pools.obtain(WorldEvents.EnemyCrashedEvent.class);
         var2.enemy = var1;
         var2.world = this;
         this.eventProcessor.notifyListener(var2);
         Pools.free((Object)var2);
      }
   }

   public void fireEnemyDroppedEvent(Enemy var1, EnemyProXY var2) {
      WorldEvents.EnemyDroppedEvent var3 = (WorldEvents.EnemyDroppedEvent)Pools.obtain(WorldEvents.EnemyDroppedEvent.class);
      var3.enemy = var1;
      var3.enemyProXY = var2;
      var3.world = this;
      this.eventProcessor.notifyListener(var3);
      Pools.free((Object)var3);
   }

   public void fireFighterCrashedEvent(Fighter var1) {
      WorldEvents.FighterCrashedEvent var2 = (WorldEvents.FighterCrashedEvent)Pools.obtain(WorldEvents.FighterCrashedEvent.class);
      var2.world = this;
      var2.fighter = var1;
      this.eventProcessor.notifyListener(var2);
      Pools.free((Object)var2);
   }

   public void fireFighterRecovery(Fighter var1) {
      WorldEvents.FighterRecoveryEvent var2 = (WorldEvents.FighterRecoveryEvent)Pools.obtain(WorldEvents.FighterRecoveryEvent.class);
      var2.world = this;
      var2.fighter = var1;
      this.eventProcessor.notifyListener(var2);
      Pools.free((Object)var2);
   }

   public void fireLevelEndEvent(boolean var1) {
      WorldEvents.LevelEndEvent var2 = (WorldEvents.LevelEndEvent)Pools.obtain(WorldEvents.LevelEndEvent.class);
      var2.world = this;
      var2.level = this.level;
      var2.levelPassed = var1;
      this.eventProcessor.notifyListener(var2);
      Pools.free((Object)var2);
   }

   public void firePropUnuseEvent(Prop var1) {
      WorldEvents.PropUnuseEvent var2 = (WorldEvents.PropUnuseEvent)Pools.obtain(WorldEvents.PropUnuseEvent.class);
      var2.world = this;
      var2.prop = var1;
      this.eventProcessor.notifyListener(var2);
      Pools.free((Object)var2);
   }

   public void generateNextLevel() {
      ++this.level;
      this.state = 0;
      this.generateLevel(this.level);
   }

   public float getDelayTimeToNextLevel() {
      if(this.provider == null) {
         return this.delayTimeToNextLevel;
      } else {
         this.delayTimeToNextLevel = this.provider.getDelayTimeToNextLevel();
         this.provider = null;
         return this.delayTimeToNextLevel;
      }
   }

   public int getLives() {
      return this.fighterLives;
   }

   public Array<World.RenderObject> getObjectsBGM() {
      return this.objectBetweenBG;
   }

   public GameScreen getParentScreen() {
      return this.parentScreen;
   }

   public World.RenderObject getSnakeGun() {
      return this.snakeGun;
   }

   public boolean getStopFighting() {
      return this.stopFighting;
   }

   public float getViewPosition() {
      return this.viewPosition;
   }

   public boolean increaseALive() {
      if(this.fighterLives < this.fighter.livesLimit) {
         ++this.fighterLives;
         return true;
      } else {
         return false;
      }
   }

   public void levelEndDoSomeThing() {
      this.fighter.saveFighter();
   }

   public void playBGMusic() {
      int var1 = this.level;
      Music var2 = null;
      switch(var1) {
      case 1:
      case 2:
         var2 = Assets_Audio.earthMusic;
         break;
      case 3:
      case 4:
         var2 = Assets_Audio.moonMusic;
         break;
      case 5:
         var2 = Assets_Audio.spaceMusic;
         break;
      case 6:
      case 7:
         var2 = Assets_Audio.marMusic;
      }

      MusicPlayer.musicPlayer.playMusic(var2);
   }

   public void playBossMusic() {
      MusicPlayer.musicPlayer.playMusic(Assets_Audio.bossMusic);
   }

   public void reFillHitCheckListener() {
      this.hitCheckListeners.clear();
      this.hitCheckListeners.add(this.fighter);
   }

   public void reStartLevel() {
      if(this.worldActions.size != 0) {
         this.removeAddPropAction();
      }

      this.state = 0;
      this.generateLevel(this.level);
      this.fighterLives = this.fighter.iniLives;
      this.fighter.recovery();
      this.fireFighterRecovery(this.fighter);
      this.fighter.setBoomNumFull();
      this.askBeginFighting();
      this.fireWorldRestartEvent();
      this.playBGMusic();
   }

   public void removeWorldEventListener(GameEvent.GameEventListener var1) {
      this.eventProcessor.removeListenerDelay(var1);
   }

   public void resumeMusic() {
      MusicPlayer.musicPlayer.play();
   }

   public void setDelayTimeToNextLevel(World.DelayTimeToNextProvider var1) {
      this.provider = var1;
   }

   public void setSnakeGunRenderInterface(World.RenderObject var1) {
      this.snakeGun = var1;
   }

   public void showWarining(int var1) {
      this.parentScreen.showWarning(var1);
   }

   public void stopMusic() {
      MusicPlayer.musicPlayer.stop();
   }

   public void update(float var1, Vector2 var2) {
      this.updateWorldActions(var1);
      this.boomer.updateBoomer(var1);
      this.updateProp(var1);
      this.updateFighter(var1, var2);
      this.updateBullets(var1);
      this.updateLasers(var1);
      this.updateEnemy(var1);
      this.updateCamPosition(var1);
      this.checkCollisions();
      if(this.smallBG != null) {
         this.smallBG.update(var1);
      }

      if(this.bigBG != null) {
         this.bigBG.update(var1);
      }

      this.viewPosition += var1 * -Settings.backgroundVelocity.y;
      if(800.0F + this.viewPosition >= 6400.0F) {
         Settings.backgroundVelocity.y = 0.0F;
         this.viewPosition = 5600.0F;
      }

      boolean var3 = this.checkEnemyClear();
      if(var3) {
         if(!this.getStopFighting()) {
            this.askStopFighting();
         }

         if(Enemy.lasers.size() != 0) {
            Enemy.lasers.clear();
         }
      }

      if(this.state == 0 && var3 && Enemy.bullets.size() == 0 && !this.fighter.isCrashed() && Prop.propSet.size() == 0 && !this.worldActions.contains(this.delayToNextLevel, true)) {
         this.delayToNextLevel.reset(this);
         this.worldActions.add(this.delayToNextLevel);
      }

   }

   public boolean warningIsOver() {
      return this.parentScreen.warningIsOver();
   }

   public interface RenderObject {

      void renderObject(SpriteBatch var1);
   }

   public static class DelayChangeToNextLevel extends World.WorldAction {

      float currentTime = 0.0F;


      public boolean act(float var1) {
         if(this.currentTime == 0.0F) {
            this.world.worldAudio.playWin();
         }

         if(this.currentTime > this.world.getDelayTimeToNextLevel()) {
            if(this.world.state == 0) {
               this.world.state = 1;
               this.world.fireLevelEndEvent(true);
               this.world.stopMusic();
               return true;
            } else {
               return false;
            }
         } else {
            this.currentTime += var1;
            return false;
         }
      }

      public void reset(World var1) {
         this.world = var1;
         this.currentTime = 0.0F;
      }
   }

   public interface DelayTimeToNextProvider {

      float getDelayTimeToNextLevel();
   }

   public static class DelayEnableWorldShoot extends World.WorldAction {

      public float currentTime = 0.0F;
      public float delayTime = 0.0F;


      public boolean act(float var1) {
         if(this.currentTime >= this.delayTime) {
            this.world.askBeginFighting();
            return true;
         } else {
            this.currentTime += var1;
            return false;
         }
      }

      public void reset(World var1, float var2) {
         this.world = var1;
         this.currentTime = 0.0F;
         this.delayTime = var2;
      }
   }

   public abstract static class WorldAction {

      World world;


      public abstract boolean act(float var1);
   }

   public interface BulletHitEnemyListener {

      void beginCheck();

      void checking(Enemy var1, List<Rectangle> var2);

      void endCheck();
   }

   public static class AddProp extends World.WorldAction {

      Prop prop;
      float timeStamp;


      private void reset() {
         this.prop = null;
      }

      public boolean act(float var1) {
         if(Clock.getCurrentTime(this.timeStamp) > 0.0F) {
            Prop.propSet.add(this.prop);
            this.reset();
            return true;
         } else {
            return false;
         }
      }
   }

   public static class DelayMoveBg extends World.WorldAction {

      float currentTime = 0.0F;


      public boolean act(float var1) {
         if(this.currentTime > 2.0F) {
            Settings.backgroundVelocity.y = -53.333332F;
            return true;
         } else {
            this.currentTime += var1;
            return false;
         }
      }

      public void reset(World var1) {
         this.world = var1;
         this.currentTime = 0.0F;
      }
   }

   public static class AddEnemy extends World.WorldAction {

      public Enemy enemy;
      public List<Enemy> enemySet;


      private void reset() {
         this.enemy = null;
         this.enemySet = null;
      }

      public boolean act(float var1) {
         if(this.enemy != null && this.enemySet != null) {
            this.enemySet.add(this.enemy);
            this.reset();
            return true;
         } else {
            this.reset();
            return true;
         }
      }
   }
}
