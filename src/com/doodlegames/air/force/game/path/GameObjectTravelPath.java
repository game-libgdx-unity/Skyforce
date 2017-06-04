package com.doodlegames.air.force.game.path;


public interface GameObjectTravelPath {

   float beginTravel();

   void stopTravel();

   float updateTravel(float var1);
}
