package com.doodlegames.air.force.game.path;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.path.Path;

public class RayPath extends Path {

   private Vector2 vel;


   public RayPath(DynamicGameObject var1, Vector2 var2) {
      super(var1);
      this.vel = new Vector2(var2);
   }

   public float MovingForwad(float var1, Vector2 var2) {
      var2.x = var1 * this.vel.x;
      var2.y = var1 * this.vel.y;
      return 0.0F;
   }

   protected float computerInitAngle() {
      return 90.0F + 57.295776F * MathUtils.atan2(this.vel.y, this.vel.x);
   }
}
