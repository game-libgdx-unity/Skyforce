package com.doodlegames.air.force.fighters.gun;


public interface FighterGun {

   int getLevel();

   void increaseLevel();

   void reset();

   void updateShooting(float var1);
}
