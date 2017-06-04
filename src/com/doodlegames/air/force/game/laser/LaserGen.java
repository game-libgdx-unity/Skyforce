package com.doodlegames.air.force.game.laser;

import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.laser.Laser;
import com.doodlegames.air.force.game.laser.LaserShooter;

public interface LaserGen {

   Laser getALaser(GameObject var1, LaserShooter var2, float var3);
}
