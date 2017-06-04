package com.doodlegames.air.force.enemy;

import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.cannon.AlienCannon1;
import com.doodlegames.air.force.enemy.cannon.AlienCannon2;
import com.doodlegames.air.force.enemy.cannon.AlienCannon3L;
import com.doodlegames.air.force.enemy.cannon.AlienCannon3R;
import com.doodlegames.air.force.enemy.cannon.Cannon1;
import com.doodlegames.air.force.enemy.cannon.Cannon2;
import com.doodlegames.air.force.enemy.cannon.Cannon3;
import com.doodlegames.air.force.enemy.cannon.Cannon5;
import com.doodlegames.air.force.enemy.cannon.CommonCannon;
import com.doodlegames.air.force.enemy.cannon.RailGun;
import com.doodlegames.air.force.enemy.level1.AirCraft;
import com.doodlegames.air.force.enemy.level1.AirCraftOnBoss;
import com.doodlegames.air.force.enemy.level1.BattleShip;
import com.doodlegames.air.force.enemy.level1.BossAircraftCarrier;
import com.doodlegames.air.force.enemy.level1.Destroyer;
import com.doodlegames.air.force.enemy.level1.RailGunOnBoss;
import com.doodlegames.air.force.enemy.level1.Sea_canon;
import com.doodlegames.air.force.enemy.level1.Ufo_a;
import com.doodlegames.air.force.enemy.level1.Ufo_b;
import com.doodlegames.air.force.enemy.level2.BossL;
import com.doodlegames.air.force.enemy.level2.BossLBody;
import com.doodlegames.air.force.enemy.level2.BossLCanon;
import com.doodlegames.air.force.enemy.level2.BossLHead;
import com.doodlegames.air.force.enemy.level2.BossLRocket;
import com.doodlegames.air.force.enemy.level2.BossS;
import com.doodlegames.air.force.enemy.level2.BossSLeftArm;
import com.doodlegames.air.force.enemy.level2.BossSRightArm;
import com.doodlegames.air.force.enemy.level2.LandCannon;
import com.doodlegames.air.force.enemy.level2.Robot;
import com.doodlegames.air.force.enemy.level2.RocketVehicle;
import com.doodlegames.air.force.enemy.level2.Tank;
import com.doodlegames.air.force.enemy.level3.AlienCanon;
import com.doodlegames.air.force.enemy.level3.BossLv3;
import com.doodlegames.air.force.enemy.level3.BossLv3Building1;
import com.doodlegames.air.force.enemy.level3.BossLv3Building2;
import com.doodlegames.air.force.enemy.level3.BossLv3Building3;
import com.doodlegames.air.force.enemy.level3.BossLv3Building4;
import com.doodlegames.air.force.enemy.level3.BossLv3Cannon;
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
import com.doodlegames.air.force.enemy.level4.BossLv4Craft;
import com.doodlegames.air.force.enemy.level4.BossLv4Hand;
import com.doodlegames.air.force.enemy.level4.BossLv4Head;
import com.doodlegames.air.force.enemy.level4.TestWeapon;
import com.doodlegames.air.force.enemy.level4.TestWeaponCore;
import com.doodlegames.air.force.enemy.level4.TestWeaponLeft;
import com.doodlegames.air.force.enemy.level4.TestWeaponRight;
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
import com.doodlegames.air.force.enemy.level5.BossLv5AlienCanon;
import com.doodlegames.air.force.enemy.level5.BossLv5Canon;
import com.doodlegames.air.force.enemy.level5.BossLv5Shield;
import com.doodlegames.air.force.enemy.level5.BossLv5Stone1;
import com.doodlegames.air.force.enemy.level5.BossLv5Stone2;
import com.doodlegames.air.force.enemy.level5.BossLv5Stone3;
import com.doodlegames.air.force.enemy.level6.AlienMech;
import com.doodlegames.air.force.enemy.level6.BossLv6;
import com.doodlegames.air.force.enemy.level6.BossLv6Eye;
import com.doodlegames.air.force.enemy.level7.BossLv7Egg;
import com.doodlegames.air.force.enemy.level7.BossLv7L;
import com.doodlegames.air.force.enemy.level7.BossLv7LSilk;
import com.doodlegames.air.force.enemy.level7.BossLv7S;
import com.doodlegames.air.force.game.prop.Prop;
import com.doodlegames.air.force.game.prop.PropBoom;
import com.doodlegames.air.force.game.prop.PropBullet;
import com.doodlegames.air.force.game.prop.PropLive;
import com.doodlegames.air.force.game.prop.PropMegnet;
import com.doodlegames.air.force.game.prop.PropPursueBullet;
import com.doodlegames.air.force.game.prop.PropWingFighter;
import com.doodlegames.air.force.utils.Settings;

import java.util.Hashtable;

public class ScoreData {

   private static Hashtable<Class<? extends Enemy>, Integer> scoreEnemyTable = new Hashtable();
   private static Hashtable<Class<? extends Prop>, Integer> scorePropTable = new Hashtable();


   public static int getEnemyScore(Enemy var0) {
      Integer var1 = (Integer)scoreEnemyTable.get(var0.getClass());
      if(var1 == null) {
         Settings.w("unkown enemy class " + var0.getClass().toString());
         return 0;
      } else {
         return var1.intValue();
      }
   }

   public static int getPropScore(Prop var0) {
      Integer var1 = (Integer)scorePropTable.get(var0.getClass());
      if(var1 == null) {
         Settings.w("unkown prop class " + var0.getClass().toString());
         return 0;
      } else {
         return var1.intValue();
      }
   }

   public static void loadScoreTable() {
      scoreEnemyTable.clear();
      scoreEnemyTable.put(Ufo_a.class, Integer.valueOf(50));
      scoreEnemyTable.put(Ufo_b.class, Integer.valueOf(50));
      scoreEnemyTable.put(Sea_canon.class, Integer.valueOf(30));
      scoreEnemyTable.put(Destroyer.class, Integer.valueOf(100));
      scoreEnemyTable.put(BattleShip.class, Integer.valueOf(200));
      scoreEnemyTable.put(Cannon1.class, Integer.valueOf(20));
      scoreEnemyTable.put(CommonCannon.CommonCannon1.class, Integer.valueOf(20));
      scoreEnemyTable.put(Cannon2.class, Integer.valueOf(20));
      scoreEnemyTable.put(CommonCannon.CommonCannon2.class, Integer.valueOf(20));
      scoreEnemyTable.put(Cannon3.class, Integer.valueOf(50));
      scoreEnemyTable.put(CommonCannon.CommonCannonCrossCannon.class, Integer.valueOf(50));
      scoreEnemyTable.put(CommonCannon.CommonCannon4.class, Integer.valueOf(50));
      scoreEnemyTable.put(Cannon5.class, Integer.valueOf(350));
      scoreEnemyTable.put(AirCraft.class, Integer.valueOf(200));
      scoreEnemyTable.put(RailGun.class, Integer.valueOf(100));
      scoreEnemyTable.put(AirCraftOnBoss.class, Integer.valueOf(150));
      scoreEnemyTable.put(RailGunOnBoss.class, Integer.valueOf(250));
      scoreEnemyTable.put(BossAircraftCarrier.class, Integer.valueOf(0));
      scoreEnemyTable.put(LandCannon.class, Integer.valueOf(30));
      scoreEnemyTable.put(Tank.class, Integer.valueOf(100));
      scoreEnemyTable.put(RocketVehicle.class, Integer.valueOf(150));
      scoreEnemyTable.put(Robot.class, Integer.valueOf(200));
      scoreEnemyTable.put(BossS.class, Integer.valueOf(500));
      scoreEnemyTable.put(BossSLeftArm.class, Integer.valueOf(500));
      scoreEnemyTable.put(BossSRightArm.class, Integer.valueOf(500));
      scoreEnemyTable.put(BossLCanon.class, Integer.valueOf(400));
      scoreEnemyTable.put(BossLRocket.class, Integer.valueOf(500));
      scoreEnemyTable.put(BossLHead.class, Integer.valueOf(500));
      scoreEnemyTable.put(BossLBody.class, Integer.valueOf(1000));
      scoreEnemyTable.put(BossL.class, Integer.valueOf(0));
      scoreEnemyTable.put(AlienCanon.class, Integer.valueOf(800));
      scoreEnemyTable.put(HightRobot.class, Integer.valueOf(350));
      scoreEnemyTable.put(MoonCanon.class, Integer.valueOf(0));
      scoreEnemyTable.put(BossLv3.BossLv3MoonCannon.class, Integer.valueOf(0));
      scoreEnemyTable.put(BossLv3Cannon.class, Integer.valueOf(0));
      scoreEnemyTable.put(SpaceBattleShip.class, Integer.valueOf(0));
      scoreEnemyTable.put(BossLv3Building3.class, Integer.valueOf(600));
      scoreEnemyTable.put(BossLv3Building4.class, Integer.valueOf(600));
      scoreEnemyTable.put(BossLv3Building1.class, Integer.valueOf(800));
      scoreEnemyTable.put(BossLv3Building2.class, Integer.valueOf(800));
      scoreEnemyTable.put(AlienTank.class, Integer.valueOf(150));
      scoreEnemyTable.put(AlienRailGuner.class, Integer.valueOf(100));
      scoreEnemyTable.put(AlianRobot.class, Integer.valueOf(0));
      scoreEnemyTable.put(AlianRobotLeft.class, Integer.valueOf(150));
      scoreEnemyTable.put(AlianRobotRight.class, Integer.valueOf(150));
      scoreEnemyTable.put(TestWeapon.class, Integer.valueOf(0));
      scoreEnemyTable.put(TestWeaponCore.class, Integer.valueOf(800));
      scoreEnemyTable.put(TestWeaponLeft.class, Integer.valueOf(300));
      scoreEnemyTable.put(TestWeaponRight.class, Integer.valueOf(300));
      scoreEnemyTable.put(BossLv4.class, Integer.valueOf(0));
      scoreEnemyTable.put(BossLv4Hand.class, Integer.valueOf(600));
      scoreEnemyTable.put(BossLv4Craft.class, Integer.valueOf(500));
      scoreEnemyTable.put(BossLv4Cannon.class, Integer.valueOf(150));
      scoreEnemyTable.put(BossLv4Head.class, Integer.valueOf(2000));
      scoreEnemyTable.put(AlienUfoA.class, Integer.valueOf(100));
      scoreEnemyTable.put(AlienUfoB.class, Integer.valueOf(100));
      scoreEnemyTable.put(AlienAeroBoatA.class, Integer.valueOf(200));
      scoreEnemyTable.put(AlienAeroBoatB.class, Integer.valueOf(300));
      scoreEnemyTable.put(AlienBattleShipA.class, Integer.valueOf(500));
      scoreEnemyTable.put(AlienBattleShipB.class, Integer.valueOf(1000));
      scoreEnemyTable.put(AlienAirCraft.class, Integer.valueOf(500));
      scoreEnemyTable.put(BigRailGuner.class, Integer.valueOf(300));
      scoreEnemyTable.put(BigRailGunerD.class, Integer.valueOf(0));
      scoreEnemyTable.put(BigRailGunerU.class, Integer.valueOf(0));
      scoreEnemyTable.put(AlienCannon1.class, Integer.valueOf(100));
      scoreEnemyTable.put(AlienCannon2.class, Integer.valueOf(200));
      scoreEnemyTable.put(AlienCannon3L.class, Integer.valueOf(150));
      scoreEnemyTable.put(AlienCannon3R.class, Integer.valueOf(150));
      scoreEnemyTable.put(BossLv5Shield.class, Integer.valueOf(450));
      scoreEnemyTable.put(BossLv5Stone1.class, Integer.valueOf(700));
      scoreEnemyTable.put(BossLv5Stone2.class, Integer.valueOf(700));
      scoreEnemyTable.put(BossLv5Stone3.class, Integer.valueOf(700));
      scoreEnemyTable.put(BossLv5Canon.class, Integer.valueOf(0));
      scoreEnemyTable.put(BossLv5AlienCanon.class, Integer.valueOf(0));
      scoreEnemyTable.put(BossLv5.class, Integer.valueOf(0));
      scoreEnemyTable.put(AlienMech.class, Integer.valueOf(450));
      scoreEnemyTable.put(BossLv6.class, Integer.valueOf(3000));
      scoreEnemyTable.put(BossLv6Eye.class, Integer.valueOf(0));
      scoreEnemyTable.put(BossLv7S.class, Integer.valueOf(3000));
      scoreEnemyTable.put(BossLv7L.class, Integer.valueOf(5000));
      scoreEnemyTable.put(BossLv7Egg.class, Integer.valueOf(0));
      scoreEnemyTable.put(BossLv7LSilk.class, Integer.valueOf(0));
      scorePropTable.clear();
      scorePropTable.put(PropBullet.class, Integer.valueOf(200));
      scorePropTable.put(PropWingFighter.class, Integer.valueOf(300));
      scorePropTable.put(PropMegnet.class, Integer.valueOf(200));
      scorePropTable.put(PropPursueBullet.class, Integer.valueOf(300));
      scorePropTable.put(PropBoom.class, Integer.valueOf(300));
      scorePropTable.put(PropLive.class, Integer.valueOf(450));
   }
}
