package com.doodlegames.air.force.game;

import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyProXY;
import com.doodlegames.air.force.enemy.ScoreData;
import com.doodlegames.air.force.enemy.level2.BossS;
import com.doodlegames.air.force.enemy.level4.TestWeapon;
import com.doodlegames.air.force.enemy.level7.BossLv7S;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.prop.Prop;
import com.doodlegames.air.force.game.prop.PropCoin;
import com.doodlegames.air.force.game.prop.PropDropHandler;
import com.doodlegames.air.force.game.prop.PropLive;
import com.doodlegames.air.force.screen.screenActor.GameEvent;
import com.doodlegames.air.force.utils.Settings;

public class WorldEvents {

   public static WorldEvents.BoomUsingListener boomEventListener = new WorldEvents.BoomUsingListener() {
      protected void handleBoomUsingEvent(Object var1, WorldEvents.BoomUsingEvent var2) {
         ++WorldEvents.levelBoomCount;
      }
   };
   public static WorldEvents.EnemyCrashedListener enemyCrashedListener = new WorldEvents.EnemyCrashedListener() {
      protected void handleEnemyCrashed(Object var1, WorldEvents.EnemyCrashedEvent var2) {
         WorldEvents.levelScore += ScoreData.getEnemyScore(var2.enemy);
         PropDropHandler.dropProp(var2);
         Class var3 = var2.enemy.getClass();
         if(var3 == BossS.class || var3 == TestWeapon.class || var3 == BossLv7S.class) {
            World var4 = var2.enemy.world;
            var4.addWorldAction(World.getDelayMoveAction(var4));
         }

      }
   };
   public static WorldEvents.EnemyDroppedListener enemyDroppedListener = new WorldEvents.EnemyDroppedListener() {
      protected void handleEnemyDropped(Object var1, WorldEvents.EnemyDroppedEvent var2) {}
   };
   public static WorldEvents.EnemyRemovedListener enemyRemovedListener = new WorldEvents.EnemyRemovedListener() {
      protected void handleEnemyRemoved(Object var1, WorldEvents.EnemyRemovedEvent var2) {
         if(!var2.enemy.isCrashed()) {
            WorldEvents.levelKillAllEnemy = false;
         } else {
            ++WorldEvents.levelEnemyKilled;
         }

         ++WorldEvents.levelEnemyCount;
      }
   };
   public static WorldEvents.FighterCrashedListener fighterCrashListener = new WorldEvents.FighterCrashedListener() {
      protected void handleFighterCrashedEvent(Object var1, WorldEvents.FighterCrashedEvent var2) {
         WorldEvents.levelFighterCrashed = true;
         ++WorldEvents.levelFighterCrashCount;
         var2.fighter.dropProp();
      }
   };
   public static WorldEvents.FighterRecoveryListener fighterRecoveryListener = new WorldEvents.FighterRecoveryListener() {
      protected void handleFighterRecoveryEvent(Object var1, WorldEvents.FighterRecoveryEvent var2) {}
   };
   public static int levelBoomCount = 0;
   public static WorldEvents.LevelEndListener levelEndListener = new WorldEvents.LevelEndListener() {
      protected void handleLevelEnd(Object var1, WorldEvents.LevelEndEvent var2) {
         Settings.score += (double)WorldEvents.levelScore;
         if((long)WorldEvents.levelMoney > Settings.levelMoney) {
            Settings.levelMoney = (long)WorldEvents.levelMoney;
         }

         if((double)WorldEvents.levelScore > Settings.levelScore[-1 + var2.level]) {
            Settings.levelScore[-1 + var2.level] = (double)WorldEvents.levelScore;
         }

         Settings.enemyKilledCount += (long)WorldEvents.levelEnemyKilled;
         Settings.fighterCrashCount += (long)WorldEvents.levelFighterCrashCount;
         Settings.boomCount += (long)WorldEvents.levelBoomCount;
         if(var2.levelPassed) {
            if(Settings.levelPassed < var2.level) {
               Settings.levelPassed = var2.level;
            }

            if(!WorldEvents.levelUseProp) {
               Settings.passLevelWithOutProp = true;
            }

            if(!WorldEvents.levelFighterCrashed) {
               Settings.passLevelOneLife[-1 + var2.level] = true;
            }

            if(WorldEvents.levelKillAllEnemy) {
               Settings.killAllEenmyInOneLevel = true;
            }

            if(var2.level == 7 && Settings.unlockFighter < 6) {
               Settings.unlockFighter = 6;
            }

            int var3 = var2.world.evaluateStar();
            if(Settings.levelStar[-1 + var2.level] < var3) {
               Settings.levelStar[-1 + var2.level] = var3;
            }

            Settings.levelCompeleted[-1 + var2.level] = true;
            Settings.checkNextLap();
         }

         Settings.save();
      }
   };
   public static int levelEnemyCount = 0;
   public static int levelEnemyKilled = 0;
   public static int levelFighterCrashCount = 0;
   public static boolean levelFighterCrashed = false;
   public static boolean levelKillAllEnemy = true;
   public static int levelMoney = 0;
   public static int levelScore = 0;
   public static WorldEvents.LevelStartListener levelStartListener = new WorldEvents.LevelStartListener() {
      protected void handleLevelStart(Object var1, WorldEvents.LevelStartEvent var2) {
         WorldEvents.levelFighterCrashed = false;
         WorldEvents.levelUseProp = false;
         WorldEvents.levelScore = 0;
         WorldEvents.levelMoney = 0;
         WorldEvents.levelEnemyKilled = 0;
         WorldEvents.levelEnemyCount = 0;
         WorldEvents.levelFighterCrashCount = 0;
         WorldEvents.levelBoomCount = 0;
      }
   };
   public static boolean levelUseProp = false;
   public static WorldEvents.PropPickedListener propPickedListener = new WorldEvents.PropPickedListener() {
      protected void handlePropPickedEvent(Object var1, WorldEvents.PropPickedEvent var2) {
         Prop var3 = var2.prop;
         if(var3 instanceof PropCoin) {
            WorldEvents.levelMoney += ((PropCoin)var3).getCoinValue();
         }

         WorldEvents.levelUseProp = true;
         if(var3 instanceof PropCoin) {
            var2.world.worldAudio.playGetCoin();
         } else if(var3 instanceof PropLive) {
            var2.world.worldAudio.playGetLife();
         } else {
            var2.world.worldAudio.playGetProp();
         }
      }
   };
   public static WorldEvents.PropUnuseEventListener propUnuseEventListener = new WorldEvents.PropUnuseEventListener() {
      protected void handlePropUnuseEvent(Object var1, WorldEvents.PropUnuseEvent var2) {
         WorldEvents.levelScore += ScoreData.getPropScore(var2.prop);
      }
   };
   public static WorldEvents.WorldRestartEventListener worldRestartEventListener = new WorldEvents.WorldRestartEventListener() {
      protected void handleWorldRestartEvent(Object var1, WorldEvents.WorldRestartEvent var2) {}
   };


   public static void loadListeners(World var0) {
      var0.addWorldEventListener(levelStartListener);
      var0.addWorldEventListener(levelEndListener);
      var0.addWorldEventListener(enemyRemovedListener);
      var0.addWorldEventListener(enemyCrashedListener);
      var0.addWorldEventListener(enemyDroppedListener);
      var0.addWorldEventListener(fighterCrashListener);
      var0.addWorldEventListener(fighterRecoveryListener);
      var0.addWorldEventListener(propPickedListener);
      var0.addWorldEventListener(boomEventListener);
      var0.addWorldEventListener(worldRestartEventListener);
      var0.addWorldEventListener(propUnuseEventListener);
   }

   public static class PropPickedEvent extends WorldEvents.WorldEvent {

      public Prop prop;


   }

   public abstract static class LevelEndListener implements GameEvent.GameEventListener {

      public void handle(Object var1, Object var2) {
         if(var2 instanceof WorldEvents.LevelEndEvent) {
            this.handleLevelEnd(var1, (WorldEvents.LevelEndEvent)var2);
         }

      }

      protected abstract void handleLevelEnd(Object var1, WorldEvents.LevelEndEvent var2);
   }

   public abstract static class EnemyRemovedListener implements GameEvent.GameEventListener {

      public void handle(Object var1, Object var2) {
         if(var2 instanceof WorldEvents.EnemyRemovedEvent) {
            this.handleEnemyRemoved(var1, (WorldEvents.EnemyRemovedEvent)var2);
         }

      }

      protected abstract void handleEnemyRemoved(Object var1, WorldEvents.EnemyRemovedEvent var2);
   }

   public abstract static class FighterCrashedListener implements GameEvent.GameEventListener {

      public void handle(Object var1, Object var2) {
         if(var2 instanceof WorldEvents.FighterCrashedEvent) {
            this.handleFighterCrashedEvent(var1, (WorldEvents.FighterCrashedEvent)var2);
         }

      }

      protected abstract void handleFighterCrashedEvent(Object var1, WorldEvents.FighterCrashedEvent var2);
   }

   public abstract static class EnemyDroppedListener implements GameEvent.GameEventListener {

      public void handle(Object var1, Object var2) {
         if(var2 instanceof WorldEvents.EnemyDroppedEvent) {
            this.handleEnemyDropped(var1, (WorldEvents.EnemyDroppedEvent)var2);
         }

      }

      protected abstract void handleEnemyDropped(Object var1, WorldEvents.EnemyDroppedEvent var2);
   }

   public abstract static class FighterRecoveryListener implements GameEvent.GameEventListener {

      public void handle(Object var1, Object var2) {
         if(var2 instanceof WorldEvents.FighterRecoveryEvent) {
            this.handleFighterRecoveryEvent(var1, (WorldEvents.FighterRecoveryEvent)var2);
         }

      }

      protected abstract void handleFighterRecoveryEvent(Object var1, WorldEvents.FighterRecoveryEvent var2);
   }

   public static class PropUnuseEvent extends WorldEvents.WorldEvent {

      Prop prop;


   }

   public static class WorldRestartEvent extends WorldEvents.WorldEvent {

   }

   public static class BoomUsingEvent extends WorldEvents.WorldEvent {

   }

   public abstract static class LevelStartListener implements GameEvent.GameEventListener {

      public void handle(Object var1, Object var2) {
         if(var2 instanceof WorldEvents.LevelStartEvent) {
            this.handleLevelStart(var1, (WorldEvents.LevelStartEvent)var2);
         }

      }

      protected abstract void handleLevelStart(Object var1, WorldEvents.LevelStartEvent var2);
   }

   public abstract static class EnemyCrashedListener implements GameEvent.GameEventListener {

      public void handle(Object var1, Object var2) {
         if(var2 instanceof WorldEvents.EnemyCrashedEvent) {
            this.handleEnemyCrashed(var1, (WorldEvents.EnemyCrashedEvent)var2);
         }

      }

      protected abstract void handleEnemyCrashed(Object var1, WorldEvents.EnemyCrashedEvent var2);
   }

   public static class LevelEndEvent extends WorldEvents.WorldEvent {

      public int level;
      public boolean levelPassed;


   }

   public static class EnemyCrashedEvent extends WorldEvents.WorldEvent {

      public Enemy enemy;


   }

   public static class FighterCrashedEvent extends WorldEvents.WorldEvent {

      public Fighter fighter;


   }

   public abstract static class PropUnuseEventListener implements GameEvent.GameEventListener {

      public void handle(Object var1, Object var2) {
         if(var2 instanceof WorldEvents.PropUnuseEvent) {
            this.handlePropUnuseEvent(var1, (WorldEvents.PropUnuseEvent)var2);
         }

      }

      protected abstract void handlePropUnuseEvent(Object var1, WorldEvents.PropUnuseEvent var2);
   }

   public static class EnemyRemovedEvent extends WorldEvents.WorldEvent {

      public Enemy enemy;


   }

   public static class EnemyDroppedEvent extends WorldEvents.WorldEvent {

      public Enemy enemy;
      public EnemyProXY enemyProXY;


   }

   public static class WorldEvent {

      public World world;


   }

   public static class LevelStartEvent extends WorldEvents.WorldEvent {

      public int level;


   }

   public abstract static class WorldRestartEventListener implements GameEvent.GameEventListener {

      public void handle(Object var1, Object var2) {
         if(var2 instanceof WorldEvents.WorldRestartEvent) {
            this.handleWorldRestartEvent(var1, (WorldEvents.WorldRestartEvent)var2);
         }

      }

      protected abstract void handleWorldRestartEvent(Object var1, WorldEvents.WorldRestartEvent var2);
   }

   public static class FighterRecoveryEvent extends WorldEvents.WorldEvent {

      public Fighter fighter;


   }

   public abstract static class PropPickedListener implements GameEvent.GameEventListener {

      public void handle(Object var1, Object var2) {
         if(var2 instanceof WorldEvents.PropPickedEvent) {
            this.handlePropPickedEvent(var1, (WorldEvents.PropPickedEvent)var2);
         }

      }

      protected abstract void handlePropPickedEvent(Object var1, WorldEvents.PropPickedEvent var2);
   }

   public abstract static class BoomUsingListener implements GameEvent.GameEventListener {

      public void handle(Object var1, Object var2) {
         if(var2 instanceof WorldEvents.BoomUsingEvent) {
            this.handleBoomUsingEvent(var1, (WorldEvents.BoomUsingEvent)var2);
         }

      }

      protected abstract void handleBoomUsingEvent(Object var1, WorldEvents.BoomUsingEvent var2);
   }

   public static class WorldFightSetEvent extends WorldEvents.WorldEvent {

      boolean newState;
      boolean oldState;


      public boolean getNewState() {
         return this.newState;
      }

      public boolean getOldState() {
         return this.oldState;
      }
   }
}
