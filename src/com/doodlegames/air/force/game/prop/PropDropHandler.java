package com.doodlegames.air.force.game.prop;

import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.level1.AirCraft;
import com.doodlegames.air.force.enemy.level1.BattleShip;
import com.doodlegames.air.force.enemy.level1.BossAircraftCarrier;
import com.doodlegames.air.force.enemy.level1.Destroyer;
import com.doodlegames.air.force.enemy.level1.Sea_canon;
import com.doodlegames.air.force.enemy.level1.Ufo_a;
import com.doodlegames.air.force.enemy.level1.Ufo_b;
import com.doodlegames.air.force.enemy.level2.BigUfo;
import com.doodlegames.air.force.enemy.level2.BossL;
import com.doodlegames.air.force.enemy.level2.BossS;
import com.doodlegames.air.force.enemy.level2.LandCannon;
import com.doodlegames.air.force.enemy.level2.Robot;
import com.doodlegames.air.force.enemy.level2.RocketVehicle;
import com.doodlegames.air.force.enemy.level2.Tank;
import com.doodlegames.air.force.enemy.level3.AlienCanon;
import com.doodlegames.air.force.enemy.level3.BossLv3;
import com.doodlegames.air.force.enemy.level3.HightRobot;
import com.doodlegames.air.force.enemy.level3.MoonCanon;
import com.doodlegames.air.force.enemy.level3.SpaceBattleShip;
import com.doodlegames.air.force.enemy.level4.AlianRobot;
import com.doodlegames.air.force.enemy.level4.AlienRailGuner;
import com.doodlegames.air.force.enemy.level4.AlienTank;
import com.doodlegames.air.force.enemy.level4.BossLv4;
import com.doodlegames.air.force.enemy.level4.TestWeapon;
import com.doodlegames.air.force.enemy.level5.AlienAeroBoatA;
import com.doodlegames.air.force.enemy.level5.AlienAeroBoatB;
import com.doodlegames.air.force.enemy.level5.AlienAirCraft;
import com.doodlegames.air.force.enemy.level5.AlienBattleShipA;
import com.doodlegames.air.force.enemy.level5.AlienBattleShipB;
import com.doodlegames.air.force.enemy.level5.AlienUfoA;
import com.doodlegames.air.force.enemy.level5.AlienUfoB;
import com.doodlegames.air.force.enemy.level5.BigRailGuner;
import com.doodlegames.air.force.enemy.level5.BossLv5;
import com.doodlegames.air.force.enemy.level6.AlienMech;
import com.doodlegames.air.force.enemy.level6.BossLv6;
import com.doodlegames.air.force.enemy.level7.BossLv7L;
import com.doodlegames.air.force.enemy.level7.BossLv7S;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldEvents;
import com.doodlegames.air.force.game.prop.Prop;
import com.doodlegames.air.force.game.prop.PropBoom;
import com.doodlegames.air.force.game.prop.PropBullet;
import com.doodlegames.air.force.game.prop.PropCoin;
import com.doodlegames.air.force.game.prop.PropGenerator;
import com.doodlegames.air.force.game.prop.PropLive;
import com.doodlegames.air.force.game.prop.PropMegnet;
import com.doodlegames.air.force.game.prop.PropPursueBullet;
import com.doodlegames.air.force.game.prop.PropWingFighter;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.HashSet;

public class PropDropHandler {

   public static final int BIGENEMY = 3;
   public static final int BOSSENEMY = 4;
   public static final int MAYBESUBENEMY = -1;
   public static final int MIDDLEENEMY = 2;
   public static final int SMALLENEMY = 1;
   private static final PropDropHandler.coinDropFunction bigDropCoin = new PropDropHandler.coinDropFunction() {
      public void dropCoin(Enemy var1) {
         int var2 = 2 + Settings.coinDropLevel;
         float var3 = var1.getDropX();
         float var4 = var1.getDropY();

         for(int var5 = 0; var5 < var2; ++var5) {
            float var10 = Clock.rand.nextFloat();
            if(var10 >= 0.45F) {
               Prop var11;
               if(var10 < 0.65F) {
                  var11 = PropCoin.propCopperCoinGen.getAProp(var1, var1.world, var3, var4);
               } else if(var10 < 0.84999996F) {
                  var11 = PropCoin.propSilverCoinGen.getAProp(var1, var1.world, var3, var4);
               } else {
                  var11 = PropCoin.propGoldCoinGen.getAProp(var1, var1.world, var3, var4);
               }

               Prop.propSet.add(var11);
            }
         }

         Prop.propSet.add(PropCoin.propCopperCoinGen.getAProp(var1, var1.world, var3, var4));
         Prop.propSet.add(PropCoin.propCopperCoinGen.getAProp(var1, var1.world, var3, var4));
         Prop.propSet.add(PropCoin.propSilverCoinGen.getAProp(var1, var1.world, var3, var4));
         Prop.propSet.add(PropCoin.propSilverCoinGen.getAProp(var1, var1.world, var3, var4));
      }
   };
   private static HashSet<Class<? extends Enemy>> bigEnemySet;
   private static final PropDropHandler.coinDropFunction bossDropCoin = new PropDropHandler.coinDropFunction() {
      public void dropCoin(Enemy var1) {
         int var2 = 5 + (int)(1.5F * (float)Settings.coinDropLevel);
         float var3 = var1.getDropX();
         float var4 = var1.getDropY();
         float var5 = var1.getDropDelayTime();

         for(int var6 = 0; var6 < var2; ++var6) {
            float var17 = Clock.rand.nextFloat();
            if(var17 >= 0.35F) {
               Prop var18;
               if(var17 < 0.45F) {
                  var18 = PropCoin.propCopperCoinGen.getAProp(var1, var1.world, var3, var4);
               } else if(var17 < 0.79999995F) {
                  var18 = PropCoin.propSilverCoinGen.getAProp(var1, var1.world, var3, var4);
               } else {
                  var18 = PropCoin.propGoldCoinGen.getAProp(var1, var1.world, var3, var4);
               }

               if(var5 < 0.0F) {
                  Prop.propSet.add(var18);
               } else {
                  var1.world.addProp(var18, var5);
               }
            }
         }

         if(var5 > 0.0F) {
            var1.world.addProp(PropCoin.propCopperCoinGen.getAProp(var1, var1.world, var3, var4), var5);
            var1.world.addProp(PropCoin.propCopperCoinGen.getAProp(var1, var1.world, var3, var4), var5);
            var1.world.addProp(PropCoin.propCopperCoinGen.getAProp(var1, var1.world, var3, var4), var5);
            var1.world.addProp(PropCoin.propCopperCoinGen.getAProp(var1, var1.world, var3, var4), var5);
            var1.world.addProp(PropCoin.propCopperCoinGen.getAProp(var1, var1.world, var3, var4), var5);
            var1.world.addProp(PropCoin.propCopperCoinGen.getAProp(var1, var1.world, var3, var4), var5);
            var1.world.addProp(PropCoin.propSilverCoinGen.getAProp(var1, var1.world, var3, var4), var5);
            var1.world.addProp(PropCoin.propSilverCoinGen.getAProp(var1, var1.world, var3, var4), var5);
            var1.world.addProp(PropCoin.propGoldCoinGen.getAProp(var1, var1.world, var3, var4), var5);
            var1.world.addProp(PropCoin.propGoldCoinGen.getAProp(var1, var1.world, var3, var4), var5);
         } else {
            Prop.propSet.add(PropCoin.propCopperCoinGen.getAProp(var1, var1.world, var3, var4));
            Prop.propSet.add(PropCoin.propCopperCoinGen.getAProp(var1, var1.world, var3, var4));
            Prop.propSet.add(PropCoin.propCopperCoinGen.getAProp(var1, var1.world, var3, var4));
            Prop.propSet.add(PropCoin.propCopperCoinGen.getAProp(var1, var1.world, var3, var4));
            Prop.propSet.add(PropCoin.propCopperCoinGen.getAProp(var1, var1.world, var3, var4));
            Prop.propSet.add(PropCoin.propCopperCoinGen.getAProp(var1, var1.world, var3, var4));
            Prop.propSet.add(PropCoin.propSilverCoinGen.getAProp(var1, var1.world, var3, var4));
            Prop.propSet.add(PropCoin.propSilverCoinGen.getAProp(var1, var1.world, var3, var4));
            Prop.propSet.add(PropCoin.propGoldCoinGen.getAProp(var1, var1.world, var3, var4));
            Prop.propSet.add(PropCoin.propGoldCoinGen.getAProp(var1, var1.world, var3, var4));
         }
      }
   };
   private static HashSet<Class<? extends Enemy>> bossEnemySet;
   private static final PropDropHandler.coinDropFunction[] dropCoinFunction;
   private static final float[] dropLikely;
   private static final PropDropHandler.coinDropFunction middleDropCoin = new PropDropHandler.coinDropFunction() {
      public void dropCoin(Enemy var1) {
         int var2 = 1 + Settings.coinDropLevel;
         float var3 = var1.getDropX();
         float var4 = var1.getDropY();

         for(int var5 = 0; var5 < var2; ++var5) {
            float var8 = Clock.rand.nextFloat();
            if(var8 >= 0.55F) {
               Prop var9;
               if(var8 < 0.75F) {
                  var9 = PropCoin.propCopperCoinGen.getAProp(var1, var1.world, var3, var4);
               } else if(var8 < 0.95F) {
                  var9 = PropCoin.propSilverCoinGen.getAProp(var1, var1.world, var3, var4);
               } else {
                  var9 = PropCoin.propGoldCoinGen.getAProp(var1, var1.world, var3, var4);
               }

               Prop.propSet.add(var9);
            }
         }

         Prop.propSet.add(PropCoin.propCopperCoinGen.getAProp(var1, var1.world, var3, var4));
         Prop.propSet.add(PropCoin.propCopperCoinGen.getAProp(var1, var1.world, var3, var4));
      }
   };
   private static HashSet<Class<? extends Enemy>> middleEnemySet;
   private static final PropGenerator[] propGens;
   private static final PropDropHandler.coinDropFunction smallDropCoin = new PropDropHandler.coinDropFunction() {
      public void dropCoin(Enemy var1) {
         int var2 = 1 + Settings.coinDropLevel;
         float var3 = var1.getDropX();
         float var4 = var1.getDropY();

         for(int var5 = 0; var5 < var2; ++var5) {
            float var6 = Clock.rand.nextFloat();
            if(var6 >= 0.7F) {
               Prop var7;
               if(var6 < 0.9F) {
                  var7 = PropCoin.propCopperCoinGen.getAProp(var1, var1.world, var3, var4);
               } else if(var6 < 0.97999996F) {
                  var7 = PropCoin.propSilverCoinGen.getAProp(var1, var1.world, var3, var4);
               } else {
                  var7 = PropCoin.propGoldCoinGen.getAProp(var1, var1.world, var3, var4);
               }

               Prop.propSet.add(var7);
            }
         }

      }
   };
   private static HashSet<Class<? extends Enemy>> smallEnemySet;


   static {
      PropDropHandler.coinDropFunction[] var0 = new PropDropHandler.coinDropFunction[]{null, smallDropCoin, middleDropCoin, bigDropCoin, bossDropCoin};
      dropCoinFunction = var0;
      float[] var1 = new float[]{0.0F, Settings.smallDrop, Settings.middleDrop, Settings.bigDrop, Settings.bossDrop};
      dropLikely = var1;
      PropGenerator[] var2 = new PropGenerator[]{PropBullet.propRedGen, PropBullet.propYellowGen, PropBullet.propBlueGen, PropMegnet.propMegnetGen, PropBoom.propBoomGen, PropWingFighter.propWingFighterGen, PropPursueBullet.propPursueBulletGen, PropLive.propLiveGen};
      propGens = var2;
      smallEnemySet = new HashSet();
      middleEnemySet = new HashSet();
      bigEnemySet = new HashSet();
      bossEnemySet = new HashSet();
   }

   private static void dropCoinProp(Enemy var0, int var1) {
      dropCoinFunction[var1].dropCoin(var0);
   }

   private static void dropCommonProp(Enemy var0, int var1) {
      float var2 = getDropLikly(var1);
      int var3 = getDropCount(var1);
      World var4 = var0.world;
      float var5 = var0.getDropX();
      float var6 = var0.getDropY();
      float var7 = var0.getDropDelayTime();

      for(int var8 = 0; var8 < var3; ++var8) {
         if(shouldDrop(var2)) {
            Prop var9 = getPropGenerator(var1).getAProp(var0, var4, var5, var6);
            if(var7 > 0.0F) {
               var4.addProp(var9, var7);
            } else {
               Prop.propSet.add(var9);
            }
         }
      }

   }

   public static void dropProp(WorldEvents.EnemyCrashedEvent var0) {
      Enemy var1 = var0.enemy;
      int var2 = judgeBMS(var1);
      if(var2 != -1) {
         dropCommonProp(var1, var2);
         dropCoinProp(var1, var2);
      }

   }

   private static int getDropCount(int var0) {
      byte var1 = 1;
      switch(var0) {
      case 2:
         return 1 + Clock.rand.nextInt(2);
      case 3:
         return 1 + Clock.rand.nextInt(3);
      default:
         var1 = 0;
      case 1:
      case 4:
         return var1;
      }
   }

   private static float getDropLikly(int var0) {
      float var1 = Settings.dropDelta[Settings.itemDropLevel];
      dropLikely[1] = var1 + Settings.smallDrop;
      dropLikely[2] = var1 + Settings.middleDrop;
      dropLikely[3] = var1 + Settings.bigDrop;
      dropLikely[4] = var1 + Settings.bossDrop;
      if(var0 >= 0 && var0 < dropLikely.length) {
         return dropLikely[var0];
      } else {
         Settings.e("invalid dropChoose + " + var0);
         return 0.0F;
      }
   }

   private static PropGenerator getPropGenerator(int var0) {
      propGens[0] = PropBullet.propRedGen;
      propGens[1] = PropBullet.propYellowGen;
      propGens[2] = PropBullet.propBlueGen;
      propGens[3] = PropBoom.propBoomGen;
      propGens[4] = PropWingFighter.propWingFighterGen;
      propGens[5] = PropPursueBullet.propPursueBulletGen;
      propGens[6] = PropMegnet.propMegnetGen;
      propGens[7] = PropLive.propLiveGen;
      int var1 = -1;
      switch(var0) {
      case 1:
         var1 = randChoosePhase(new double[]{0.15D, 0.15D, 0.15D, 0.15D, 0.1D, 0.1D, 0.2D, 0.0D});
         break;
      case 2:
         var1 = randChoosePhase(new double[]{0.1D, 0.1D, 0.1D, 0.15D, 0.15D, 0.15D, 0.1D, 0.15D});
         break;
      case 3:
         var1 = randChoosePhase(new double[]{0.05D, 0.05D, 0.05D, 0.2D, 0.15D, 0.2D, 0.05D, 0.25D});
         break;
      case 4:
         var1 = randChoosePhase(new double[]{0.0D, 0.0D, 0.0D, 0.2D, 0.15D, 0.2D, 0.0D, 0.45D});
      }

      return var1 >= 0 && var1 < propGens.length?propGens[var1]:null;
   }

   public static int judgeBMS(Enemy var0) {
      return smallEnemySet.contains(var0.getClass())?1:(middleEnemySet.contains(var0.getClass())?2:(bigEnemySet.contains(var0.getClass())?3:(bossEnemySet.contains(var0.getClass())?4:-1)));
   }

   public static void loadSMBData(int var0) {
      smallEnemySet.clear();
      middleEnemySet.clear();
      bigEnemySet.clear();
      bossEnemySet.clear();
      if(var0 == 1) {
         smallEnemySet.add(Ufo_a.class);
         smallEnemySet.add(Ufo_b.class);
         smallEnemySet.add(Sea_canon.class);
         middleEnemySet.add(Destroyer.class);
         bigEnemySet.add(AirCraft.class);
         bigEnemySet.add(BattleShip.class);
         bossEnemySet.add(BossAircraftCarrier.class);
      } else {
         if(var0 == 2) {
            smallEnemySet.add(Ufo_a.class);
            smallEnemySet.add(Ufo_b.class);
            smallEnemySet.add(LandCannon.class);
            smallEnemySet.add(Tank.class);
            smallEnemySet.add(Robot.class);
            smallEnemySet.add(RocketVehicle.class);
            bigEnemySet.add(BossS.class);
            bigEnemySet.add(BigUfo.class);
            bossEnemySet.add(BossL.class);
            return;
         }

         if(var0 == 3) {
            smallEnemySet.add(MoonCanon.class);
            smallEnemySet.add(AlienCanon.class);
            smallEnemySet.add(Tank.class);
            smallEnemySet.add(Robot.class);
            smallEnemySet.add(HightRobot.class);
            smallEnemySet.add(Ufo_a.class);
            smallEnemySet.add(Ufo_b.class);
            bigEnemySet.add(SpaceBattleShip.class);
            bossEnemySet.add(BossLv3.class);
            return;
         }

         if(var0 == 4) {
            smallEnemySet.add(Tank.class);
            smallEnemySet.add(Robot.class);
            smallEnemySet.add(HightRobot.class);
            smallEnemySet.add(AlianRobot.class);
            smallEnemySet.add(AlienTank.class);
            smallEnemySet.add(Ufo_a.class);
            smallEnemySet.add(Ufo_b.class);
            middleEnemySet.add(AlienRailGuner.class);
            bigEnemySet.add(TestWeapon.class);
            bossEnemySet.add(BossLv4.class);
            return;
         }

         if(var0 == 5) {
            smallEnemySet.add(AlienUfoA.class);
            smallEnemySet.add(AlienUfoB.class);
            smallEnemySet.add(AlienTank.class);
            middleEnemySet.add(AlienAeroBoatA.class);
            middleEnemySet.add(AlienAeroBoatB.class);
            middleEnemySet.add(BigRailGuner.class);
            bigEnemySet.add(AlienBattleShipA.class);
            bigEnemySet.add(AlienBattleShipB.class);
            bossEnemySet.add(BossLv5.class);
            return;
         }

         if(var0 == 6) {
            smallEnemySet.add(AlienCanon.class);
            smallEnemySet.add(AlienUfoA.class);
            smallEnemySet.add(AlienUfoB.class);
            smallEnemySet.add(AlianRobot.class);
            smallEnemySet.add(AlienMech.class);
            bigEnemySet.add(AlienBattleShipA.class);
            bigEnemySet.add(AlienBattleShipB.class);
            bigEnemySet.add(AlienAirCraft.class);
            bossEnemySet.add(BossLv6.class);
            return;
         }

         if(var0 == 7) {
            smallEnemySet.add(AlienUfoA.class);
            smallEnemySet.add(AlienUfoB.class);
            smallEnemySet.add(AlianRobot.class);
            smallEnemySet.add(AlienCanon.class);
            smallEnemySet.add(AlienMech.class);
            bigEnemySet.add(AlienBattleShipB.class);
            bigEnemySet.add(AlienBattleShipA.class);
            bigEnemySet.add(AlienAirCraft.class);
            bossEnemySet.add(BossLv7L.class);
            bossEnemySet.add(BossLv7S.class);
            return;
         }
      }

   }

   public static int randChoosePhase(double ... var0) {
      double var1 = 0.0D;

      for(int var3 = 0; var3 < var0.length; ++var3) {
         if(var0[var3] < 0.0D) {
            Settings.e("randChoosePhase error!");
            return -1;
         }

         var1 += var0[var3];
      }

      if(var1 > 1.0D) {
         Settings.e("randChoosePhase error!:" + var1);
         return -1;
      } else {
         double var4 = var0[0];
         int var6 = 0;

         for(float var7 = Clock.rand.nextFloat(); (double)var7 >= var4; var4 += var0[var6]) {
            ++var6;
            if(var6 >= var0.length) {
               return -1;
            }
         }

         return var6;
      }
   }

   private static boolean shouldDrop(float var0) {
      return Clock.rand.nextFloat() < var0;
   }

   private interface coinDropFunction {

      void dropCoin(Enemy var1);
   }
}
