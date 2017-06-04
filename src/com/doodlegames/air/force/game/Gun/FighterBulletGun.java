package com.doodlegames.air.force.game.Gun;

import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.Gun.BulletGun;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.utils.Clock;

public class FighterBulletGun extends BulletGun {

   public FighterBulletGun(GameObject var1, World var2, BulletGenerator var3, Clock var4, Vector2 var5, float ... var6) {
      super(var1, var2, var3, var4, var5, var6);
      this.bulletsSet = Fighter.bullets;
   }
}
