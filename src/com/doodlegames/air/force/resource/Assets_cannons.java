package com.doodlegames.air.force.resource;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.doodlegames.air.force.enemy.cannon.AlienCannon1;
import com.doodlegames.air.force.enemy.cannon.AlienCannon2;
import com.doodlegames.air.force.enemy.cannon.AlienCannon3L;
import com.doodlegames.air.force.enemy.cannon.AlienCannon3R;
import com.doodlegames.air.force.enemy.cannon.Cannon1;
import com.doodlegames.air.force.enemy.cannon.Cannon2;
import com.doodlegames.air.force.enemy.cannon.Cannon3;
import com.doodlegames.air.force.enemy.cannon.Cannon5;
import com.doodlegames.air.force.enemy.cannon.RailGun;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;

public class Assets_cannons {

   public static TextureAtlas atlas_canons;


   public static void load() {
      atlas_canons = Assets_ShareInAllLevel.atlasSharedEnemy;
      AlienCannon1.loadResource();
      AlienCannon2.loadResource();
      AlienCannon3L.loadResource();
      AlienCannon3R.loadResource();
      Cannon1.loadResource();
      Cannon2.loadResource();
      Cannon3.loadResource();
      Cannon5.loadResource();
      RailGun.loadResource();
   }
}
