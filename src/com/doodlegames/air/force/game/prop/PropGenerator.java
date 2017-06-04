package com.doodlegames.air.force.game.prop;

import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.prop.Prop;

public interface PropGenerator {

   Prop getAProp(GameObject var1, World var2, float var3, float var4);
}
