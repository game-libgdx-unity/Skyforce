package com.doodlegames.air.force.game.prop.propFloatingPath;

import com.doodlegames.air.force.fighters.Fighter;

public interface PropFloating {

   void beAttrackedByFighter(Fighter var1);

   void beginFloating();

   boolean canBeAttracked();

   void stopFloating();

   void updatePropFloating(float var1);
}
