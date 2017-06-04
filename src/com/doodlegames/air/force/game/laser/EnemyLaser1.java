package com.doodlegames.air.force.game.laser;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.laser.Laser;
import com.doodlegames.air.force.game.laser.LaserGen;
import com.doodlegames.air.force.game.laser.LaserShooter;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;

public class EnemyLaser1 extends Laser {

   public static final LaserGen EnemyLaser1Gen = new LaserGen() {
      public EnemyLaser1 getALaser(GameObject var1, LaserShooter var2, float var3) {
         return new EnemyLaser1(var1, var2, var3);
      }
   };
  
   private static TextureRegion LASERREGION = Assets_ShareInAllLevel.test_Bullet_3;
   public static final int WIDTH = LASERREGION.getRegionWidth();
   public static final int HEIGHT = LASERREGION.getRegionHeight();

   public EnemyLaser1(GameObject var1, LaserShooter var2, float var3) {

      super(var1, var2, var3, var2.getLaserWidth() <= 0.0F?(float)WIDTH / 1.0F:var2.getLaserWidth(), (float)HEIGHT / 1.0F);
      this.region = LASERREGION;
   }

   public static void loadResource() {
      LASERREGION = Assets_ShareInAllLevel.test_Bullet_3;
   }
}
