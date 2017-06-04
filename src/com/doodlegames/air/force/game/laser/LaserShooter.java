package com.doodlegames.air.force.game.laser;

import com.doodlegames.air.force.game.EnemyGun;

public interface LaserShooter extends EnemyGun {

   float getAlpha();

   float getLaserAngle();

   float getLaserWidth();

   boolean laseSleep();

   boolean shouldRemove();
}
