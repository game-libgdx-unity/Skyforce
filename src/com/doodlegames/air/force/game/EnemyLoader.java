package com.doodlegames.air.force.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyProXY;
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
import com.doodlegames.air.force.enemy.level3.BossLv3Building3;
import com.doodlegames.air.force.enemy.level3.BossLv3Building4;
import com.doodlegames.air.force.enemy.level3.HightRobot;
import com.doodlegames.air.force.enemy.level3.MoonCanon;
import com.doodlegames.air.force.enemy.level3.SpaceBattleShip;
import com.doodlegames.air.force.enemy.level4.AlianRobot;
import com.doodlegames.air.force.enemy.level4.AlianRobotLeft;
import com.doodlegames.air.force.enemy.level4.AlianRobotRight;
import com.doodlegames.air.force.enemy.level4.AlienRailGuner;
import com.doodlegames.air.force.enemy.level4.AlienTank;
import com.doodlegames.air.force.enemy.level4.BossLv4;
import com.doodlegames.air.force.enemy.level4.BossLv4Cannon;
import com.doodlegames.air.force.enemy.level4.TestWeapon;
import com.doodlegames.air.force.enemy.level5.AlienAeroBoatA;
import com.doodlegames.air.force.enemy.level5.AlienAeroBoatB;
import com.doodlegames.air.force.enemy.level5.AlienAirCraft;
import com.doodlegames.air.force.enemy.level5.AlienBattleShipA;
import com.doodlegames.air.force.enemy.level5.AlienBattleShipB;
import com.doodlegames.air.force.enemy.level5.AlienUfoA;
import com.doodlegames.air.force.enemy.level5.AlienUfoB;
import com.doodlegames.air.force.enemy.level5.BigRailGuner;
import com.doodlegames.air.force.enemy.level5.BigRailGunerD;
import com.doodlegames.air.force.enemy.level5.BigRailGunerU;
import com.doodlegames.air.force.enemy.level5.BossLv5;
import com.doodlegames.air.force.enemy.level6.AlienMech;
import com.doodlegames.air.force.enemy.level6.BossLv6;
import com.doodlegames.air.force.enemy.level6.BossLv6Eye;
import com.doodlegames.air.force.enemy.level6.BossLv6Glass;
import com.doodlegames.air.force.enemy.level6.MutiLaserGun;
import com.doodlegames.air.force.enemy.level7.BossLv7Egg;
import com.doodlegames.air.force.enemy.level7.BossLv7L;
import com.doodlegames.air.force.enemy.level7.BossLv7LSilk;
import com.doodlegames.air.force.enemy.level7.BossLv7LSlaw;
import com.doodlegames.air.force.enemy.level7.BossLv7S;
import com.doodlegames.air.force.enemy.level7.BossLv7STube;
import com.doodlegames.air.force.screen.GameScreen;
import com.doodlegames.air.force.utils.Clock;

public class EnemyLoader {

   public static final int ONAIR = 1;
   public static final int ONLAND = 0;
   public static final int UNKOWNENEMY = 2;
   private static HashSet<Class<? extends Enemy>> bossDic = new HashSet();
   private static Hashtable<String, Class<? extends Enemy>> enemyDic = new Hashtable();
   private static String[] enemyInfoPath = new String[]{"levelData/level1/level_1_Enemy", "levelData/level2/level_2_Enemy", "levelData/level3/level_3_Enemy", "levelData/level4/level_4_Enemy", "levelData/level5/level_5_Enemy", "levelData/level6/level_6_Enemy", "levelData/level7/level_7_Enemy"};
   static Pattern enemyUrlPa = Pattern.compile("(([a-zA-Z]|\\d|_)+)\\.png");


   public static boolean enemyIsBoss(Enemy var0) {
      return bossDic.contains(var0.getClass());
   }

   public static int enemyIsOnSea(Class<? extends Enemy> var0, int var1) {
      if(var1 == 1) {
         boolean var16;
         if(true && var0 != Ufo_a.class) {
            var16 = false;
         } else {
            var16 = true;
         }

         boolean var17;
         if(!var16 && var0 != Ufo_b.class) {
            var17 = false;
         } else {
            var17 = true;
         }

         if(!var17) {
            return 0;
         }
      } else if(var1 == 2) {
         boolean var13;
         if(true && var0 != Ufo_a.class) {
            var13 = false;
         } else {
            var13 = true;
         }

         boolean var14;
         if(!var13 && var0 != Ufo_b.class) {
            var14 = false;
         } else {
            var14 = true;
         }

         boolean var15;
         if(!var14 && var0 != BigUfo.class) {
            var15 = false;
         } else {
            var15 = true;
         }

         if(!var15) {
            return 0;
         }
      } else if(var1 == 3) {
         boolean var10;
         if(true && var0 != Ufo_a.class) {
            var10 = false;
         } else {
            var10 = true;
         }

         boolean var11;
         if(!var10 && var0 != Ufo_b.class) {
            var11 = false;
         } else {
            var11 = true;
         }

         boolean var12;
         if(!var11 && var0 != SpaceBattleShip.class) {
            var12 = false;
         } else {
            var12 = true;
         }

         if(!var12) {
            return 0;
         }
      } else if(var1 == 4) {
         boolean var7;
         if(true && var0 != Ufo_a.class) {
            var7 = false;
         } else {
            var7 = true;
         }

         boolean var8;
         if(!var7 && var0 != Ufo_b.class) {
            var8 = false;
         } else {
            var8 = true;
         }

         boolean var9;
         if(!var8 && var0 != AlienRailGuner.class) {
            var9 = false;
         } else {
            var9 = true;
         }

         if(!var9) {
            return 0;
         }
      } else if(var1 != 5) {
         if(var1 != 6 && var1 != 7) {
            return 2;
         }

         boolean var2;
         if(true && var0 != AlienBattleShipA.class) {
            var2 = false;
         } else {
            var2 = true;
         }

         boolean var3;
         if(!var2 && var0 != AlienBattleShipB.class) {
            var3 = false;
         } else {
            var3 = true;
         }

         boolean var4;
         if(!var3 && var0 != AlienUfoA.class) {
            var4 = false;
         } else {
            var4 = true;
         }

         boolean var5;
         if(!var4 && var0 != AlienUfoB.class) {
            var5 = false;
         } else {
            var5 = true;
         }

         boolean var6;
         if(!var5 && var0 != AlienAirCraft.class) {
            var6 = false;
         } else {
            var6 = true;
         }

         if(!var6) {
            return 0;
         }
      }

      return 1;
   }
   public static <T> void fillEnemySet(final World world, final List<Enemy> list, final List<Enemy> list2, final int n) {

       if (list != null && list2 != null) {

           list.clear();

           list2.clear();

           loadEnemyDic(EnemyLoader.enemyDic, n);

           final int n2 = n - 1;

           if (n2 >= 0 && n2 < EnemyLoader.enemyInfoPath.length) {

               final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Gdx.files.internal(EnemyLoader.enemyInfoPath[n2]).read()));

               int i;

               try {

                   i = Integer.parseInt(bufferedReader.readLine());

               }

               catch (Exception ex) { 

                   return;

               }

               for (; i > 0; --i) {

                   int j = 0;

                   try {

                       bufferedReader.readLine();

                       j = Integer.parseInt(bufferedReader.readLine());

                   }

                   catch (Exception ex) {}

                   while (j > 0) {

                        String readLine = null;

                        float parseFloat = 0;

                        float parseFloat2 = 0;

                        float parseFloat3 = 0;

                       Class clazz = null;

                       try {

                           readLine = bufferedReader.readLine();

                           parseFloat = Float.parseFloat(bufferedReader.readLine());

                           final Object enemyName = getEnemyName(bufferedReader.readLine());

                           parseFloat2 = Float.parseFloat(bufferedReader.readLine());

                           parseFloat3 = Float.parseFloat(bufferedReader.readLine());

                           bufferedReader.readLine();

                           bufferedReader.readLine();

                           bufferedReader.readLine();

                           bufferedReader.readLine();

                           bufferedReader.readLine();

                           clazz = (Class<T>)EnemyLoader.enemyDic.get(enemyName);

                       }

                       catch (Exception ex) {}

                       Constructor<T> constructor;

                       Enemy enemy = null;

                       int n3;

                       int n4;

                       List<Enemy> list3;

                       EnemyProXY enemyProXY;

       

                       {

//                           if (clazz != Tank.class) {
//
//                               clazz = (Class<T>) AirCraft.class;
//
//                           }

                           while (j>0) {

//                               Label_0414:
//
//                               Label_0421:
//
//                               Label_0369:

                               {

                                    {

                                        {

                                           try {

                                               if (!Clock.rand.nextBoolean()) {

                                                   continue;

                                               }

                                           }

                                           catch (Exception ex) {}

                                       if(GameScreen.level == 1){
                                           int a = MathUtils.random(0, 10);
                                           if(a == 0)
                                        	   clazz =  AirCraft.class;
                                           else if(a == 1)
                                        	   clazz =  BattleShip.class;
                                           else if(a == 2)
                                        	   clazz =  Destroyer.class;
                                           else if(a == 3)
                                        	   clazz =  Sea_canon.class;
                                           else if(a == 4)
                                        	   clazz =  Ufo_a.class;
                                           else if(a == 5)
                                        	   clazz =  Ufo_b.class;
                                           else
                                        	   clazz = Tank.class;
                                       } else if(GameScreen.level == 2) {
                                    	   int a = MathUtils.random(0, 10);
                                           if(a == 1)
                                        	   clazz =  Robot.class;
                                           else if(a == 2)
                                        	   clazz =  LandCannon.class;
                                           else if(a == 3)
                                        	   clazz =  RocketVehicle.class;
                                           else if(a == 4)
                                        	   clazz =  Tank.class;
                                           else
                                        	   clazz = Tank.class;
                                       }else if(GameScreen.level == 3) {
                                    	   int a = MathUtils.random(0, 10);
                                           if(a == 0)
                                        	   clazz =  AlienCanon.class;
                                           else if(a == 1)
                                        	   clazz =  HightRobot.class;
                                           else if(a == 2)
                                        	   clazz =  BossLv3Building3.class;
                                           else if(a == 3)
                                        	   clazz =  BossLv3Building4.class;
                                           else if(a == 4)
                                        	   clazz =  MoonCanon.class;
                                           else if(a == 5)
                                        	   clazz =  SpaceBattleShip.class;
                                           else
                                        	   clazz = Tank.class;
                                       }else if(GameScreen.level == 4) {
                                    	   int a = MathUtils.random(0, 10);
                                           if(a == 0)
                                        	   clazz =  AlianRobot.class;
                                           else if(a == 1)
                                        	   clazz =  AlienTank.class;
                                           else if(a == 2)
                                        	   clazz =  AlianRobotLeft.class;
                                           else if(a == 3)
                                        	   clazz =  AlianRobotRight.class;
                                           else if(a == 4)
                                        	   clazz =  AlienRailGuner.class;
                                           else if(a == 5)
                                        	   clazz =  AlienTank.class;
                                           else
                                        	   clazz = BossLv4Cannon.class;
                                           
                                       }else if(GameScreen.level == 5) {
                                    	   int a = MathUtils.random(0, 10);
                                           if(a == 0)
                                        	   clazz =  AlienAeroBoatA.class;
                                           else if(a == 1)
                                        	   clazz =  AlienAeroBoatB.class;
                                           else if(a == 2)
                                        	   clazz =  BigRailGuner.class;
                                           else if(a == 3)
                                        	   clazz =  BigRailGunerD.class;
                                           else if(a == 4)
                                        	   clazz =  BigRailGunerU.class;
                                           else if(a == 5)
                                        	   clazz =  AlienUfoA.class;
                                           else if(a == 6)
                                        	   clazz =  AlienUfoB.class;
                                           else
                                        	   clazz = AlienAirCraft.class;
                                       }else if(GameScreen.level == 6) {
                                    	   int a = MathUtils.random(0, 10);
                                           if(a == 0)
                                        	   clazz =  AlienMech.class;
                                           else if(a == 1)
                                        	   clazz =  BossLv6Eye.class;
                                           else if(a == 2)
                                        	   clazz =  MutiLaserGun.class;
                                           else if(a == 3)
                                        	   clazz =  AlienMech.class;
                                           else if(a == 4)
                                        	   clazz =  MutiLaserGun.class;
                                           else if(a == 5)
                                        	   clazz =  BossLv6Glass.class;
                                           else
                                        	   clazz = AlienMech.class;
                                       }else if(GameScreen.level == 7) {
                                    	   int a = MathUtils.random(0, 100);
                                           if(a == 0)
                                        	   clazz =  BossLv7L.class;
                                           else if(a == 1)
                                        	   clazz =  BossLv7S.class;
                                           else if(a == 2)
                                        	   clazz =  BossLv7Egg.class;
                                           else if(a == 3)
                                        	   clazz =  BossLv7LSilk.class;
                                           else if(a == 4)
                                        	   clazz =  BossLv7LSlaw.class;
                                           else if(a == 5)
                                        	   clazz =  BossLv7STube.class;
                                           else
                                        	   clazz =  AlienMech.class;
                                       }

                                           try {

                                               constructor = clazz.getConstructor(new Class[] { World.class, Float.TYPE, Float.TYPE });

                                               enemy = (Enemy)constructor.newInstance(new Object[] { world, parseFloat2, parseFloat3 });

                                               n3 = (int)enemy.bounds.width;

                                               n4 = (int)enemy.bounds.height;

                                               enemy.setPosition(parseFloat2 - (float)(n3 / 2) + (float)n3 / 2.0f, parseFloat3 - (float)(n4 / 2) + (float)n4 / 2.0f);

//                                               if (enemyIsOnSea((Class<? extends Enemy>) clazz, n) != 0) {
//
//                                                  continue;
//
//                                               }

                                           }

                                           catch (Exception ex) {}

                                           list3 = list;

                                           try {

                                               enemyProXY = new EnemyProXY(world, parseFloat, enemy, list3);

                                               enemyProXY.pathName = readLine;

                                               list3.add(enemyProXY);

                                               enemyProXY.sleepRending = false;

                                           }

                                           catch (Exception ex) {}

                                           --j;

                                           break;

                                       }

//                                       clazz = (Class<T>) RocketVehicle.class;

//                                       break Label_0226;

                                   }

//                                   list3 = list2;

//                                   break Label_0369;

                               }

                           }

                       }

                   }

               }

           }

       }

   }
   private static String getEnemyName(String var0) {
      Matcher var1 = enemyUrlPa.matcher(var0);
      return var1.find()?var1.group(1):null;
   }

   private static void loadEnemyDic(Hashtable<String, Class<? extends Enemy>> var0, int var1) {
      if(var0 != null) {
         var0.clear();
         bossDic.clear();
         if(var1 == 1) {
            var0.put("nazi_ufo_a_0", Ufo_a.class);
            var0.put("nazi_ufo_a", Ufo_a.class);
            var0.put("nazi_ufo_b", Ufo_b.class);
            var0.put("nazi_sea_canon", Sea_canon.class);
            var0.put("nazi_destroyer", Destroyer.class);
            var0.put("nazi_aircraft", AirCraft.class);
            var0.put("nazi_battleship", BattleShip.class);
            bossDic.add(BossAircraftCarrier.class);
            return;
         }

         if(var1 == 2) {
            var0.put("nazi_ufo_a_0", Ufo_a.class);
            var0.put("nazi_ufo_a", Ufo_a.class);
            var0.put("nazi_ufo_b", Ufo_b.class);
            var0.put("nazi_land_canon", LandCannon.class);
            var0.put("nazi_tank_rocket", Tank.class);
            var0.put("nazi_big_ufo", BigUfo.class);
            var0.put("nazi_robot", Robot.class);
            var0.put("nazi_robotV", RocketVehicle.class);
            bossDic.add(BossS.class);
            bossDic.add(BossL.class);
            return;
         }

         if(var1 == 3) {
            var0.put("nazi_moon_canon", MoonCanon.class);
            var0.put("alien_canon", AlienCanon.class);
            var0.put("nazi_trank_rocket", Tank.class);
            var0.put("nazi_robot", Robot.class);
            var0.put("nazi_high_robot", HightRobot.class);
            var0.put("nazi_ufo_a_0", Ufo_a.class);
            var0.put("nazi_ufo_a", Ufo_a.class);
            var0.put("nazi_ufo_b", Ufo_b.class);
            var0.put("nazi_space_battleship", SpaceBattleShip.class);
            bossDic.add(BossLv3.class);
            return;
         }

         if(var1 == 4) {
            var0.put("nazi_trank_rocket", Tank.class);
            var0.put("nazi_high_robot", HightRobot.class);
            var0.put("nazi_robot", Robot.class);
            var0.put("alien_robot", AlianRobot.class);
            var0.put("alien_tank", AlienTank.class);
            var0.put("nazi_ufo_a_0", Ufo_a.class);
            var0.put("nazi_ufo_a", Ufo_a.class);
            var0.put("nazi_ufo_b", Ufo_b.class);
            var0.put("alien_railguner", AlienRailGuner.class);
            bossDic.add(BossLv4.class);
            bossDic.add(TestWeapon.class);
            return;
         }

         if(var1 == 5) {
            var0.put("alien_battleship_a", AlienBattleShipA.class);
            var0.put("alien_battleship_b", AlienBattleShipB.class);
            var0.put("alien_ufo_a", AlienUfoA.class);
            var0.put("alien_ufo_b", AlienUfoB.class);
            var0.put("alien_tank", AlienTank.class);
            var0.put("alien_aeroboat", AlienAeroBoatA.class);
            var0.put("alien_aeroboat_b", AlienAeroBoatB.class);
            var0.put("alienBigRailGuner", BigRailGuner.class);
            bossDic.add(BossLv5.class);
            return;
         }

         if(var1 == 6) {
            var0.put("alien_battleship_a", AlienBattleShipA.class);
            var0.put("alien_battleship_b", AlienBattleShipB.class);
            var0.put("alien_ufo_a", AlienUfoA.class);
            var0.put("alien_ufo_b", AlienUfoB.class);
            var0.put("alien_aircraft", AlienAirCraft.class);
            var0.put("alien_robot", AlianRobot.class);
            var0.put("alien_canon", AlienCanon.class);
            var0.put("alien_mech", AlienMech.class);
            bossDic.add(BossLv6.class);
            return;
         }

         if(var1 == 7) {
            var0.put("alien_ufo_a", AlienUfoA.class);
            var0.put("alien_ufo_b", AlienUfoB.class);
            var0.put("alien_battleship_b", AlienBattleShipB.class);
            var0.put("alien_battleship_a", AlienBattleShipA.class);
            var0.put("alien_aircraft", AlienAirCraft.class);
            var0.put("alien_robot", AlianRobot.class);
            var0.put("alien_canon", AlienCanon.class);
            var0.put("alien_mech", AlienMech.class);
            bossDic.add(BossLv7L.class);
            bossDic.add(BossLv7S.class);
            return;
         }
      }

   }
}
