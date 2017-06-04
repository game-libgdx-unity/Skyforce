package com.doodlegames.air.force.game.bullet;

import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;

public interface BulletGenerator {

   Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5);
}
