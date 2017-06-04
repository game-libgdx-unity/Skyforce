package com.doodlegames.air.force.utils;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Pools;
import com.doodlegames.air.force.screen.action.TemDelayAction;
import com.doodlegames.air.force.screen.action.XMoveToAction;

public class ActionGen {

   public static TemDelayAction getDelay(float var0) {
      TemDelayAction var1 = (TemDelayAction)Pools.obtain(TemDelayAction.class);
      var1.setDuration(var0);
      return var1;
   }

   public static XMoveToAction getMoveTo(float var0, float var1, float var2) {
      XMoveToAction var3 = (XMoveToAction)Pools.obtain(XMoveToAction.class);
      var3.setDuration(var0);
      var3.setPosition(var1, var2);
      var3.setInterpolation(Interpolation.pow2Out);
      return var3;
   }
}
