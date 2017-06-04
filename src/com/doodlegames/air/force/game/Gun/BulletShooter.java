package com.doodlegames.air.force.game.Gun;

import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.game.EnemyGun;

public interface BulletShooter extends EnemyGun {

   Vector2[] getDirectionsVel();

   int getShootBulletCount();

   void resetShootClock(boolean var1, float var2, float var3, float var4, byte var5);

   void shooting();
}
