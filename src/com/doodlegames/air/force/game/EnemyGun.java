package com.doodlegames.air.force.game;

import com.badlogic.gdx.math.Vector2;

public interface EnemyGun {

   boolean canStop();

   void forceStop();

   Vector2 getShootingPoint();

   void reset();

   void updateShooting(float var1);
}
