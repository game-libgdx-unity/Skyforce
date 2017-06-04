package com.doodlegames.air.force.game.path;

import com.badlogic.gdx.math.Interpolation;

public interface EnemyPathInterface {

   Interpolation getInterpolation();

   float getLimitTime();

   float getX();

   float getY();

   boolean isOver();

   float moveForward(float var1);

   float restartFrom(float var1, float var2);

   void setInterpolation(Interpolation var1);

   void setLimitTime(float var1);
}
