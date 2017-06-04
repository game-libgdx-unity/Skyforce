package com.doodlegames.air.force.screen.screenActor;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class XTable extends Table {

   public final int index;


   public XTable(int var1) {
      this.index = var1;
   }

   public XTable(int var1, Skin var2) {
      super(var2);
      this.index = var1;
   }
}
