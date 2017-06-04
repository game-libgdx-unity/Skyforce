package com.doodlegames.air.force.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.doodlegames.air.force.screen.PressedListener;
import com.doodlegames.air.force.screen.screenActor.XTable;

public class VerticalScrollPane extends ScrollPane {

   private static final int NOITEMSELECTED = -1;
   public static VerticalScrollPane.UpdateItem defaultUpdater = new VerticalScrollPane.UpdateItem() {
      public void updateItem(XTable var1, boolean var2) {}
   };
   private VerticalScrollPane.UpdateItem itemChanged;
   private Table paneTable;
   private int selectedIndex;


   public VerticalScrollPane(Actor var1, Skin var2, String var3) {
      super(var1, var2, var3);
      this.itemChanged = defaultUpdater;
      this.paneTable = new Table();
      this.setFadeScrollBars(false);
      this.setScrollingDisabled(true, false);
      this.selectedIndex = -1;
   }

   public XTable addRow(Skin var1) {
      if(this.paneTable.getChildren().size != 0) {
         this.paneTable.row();
      }

      XTable var2 = new XTable(this.paneTable.getChildren().size, var1);
      var2.setTouchable(Touchable.enabled);
      var2.addListener(new VerticalScrollPane.ItemPressLis());
      this.paneTable.add((Actor)var2);
      return var2;
   }

   public XTable addRow(Skin var1, Drawable var2) {
      XTable var3 = this.addRow(var1);
      var3.setBackground(var2);
      return var3;
   }

   public XTable getRowItem(int var1) {
      return var1 >= 0 && var1 < this.paneTable.getChildren().size?(XTable)this.paneTable.getChildren().get(var1):null;
   }

   public void registerItemEventHandler(VerticalScrollPane.UpdateItem var1) {
      if(var1 != null) {
         this.itemChanged = var1;
      }

   }

   public void resetSelected() {
      if(this.selectedIndex != -1) {
         XTable var1 = (XTable)((XTable)this.paneTable.getChildren().get(this.selectedIndex));
         this.itemChanged.updateItem(var1, false);
      }
   }

   public interface UpdateItem {

      void updateItem(XTable var1, boolean var2);
   }

   public class ItemPressLis extends PressedListener {

      public boolean touchDown(InputEvent var1, float var2, float var3, int var4, int var5) {
         if(super.touchDown(var1, var2, var3, var4, var5)) {
            XTable var6 = (XTable)var1.getListenerActor();
            if(var6.index != VerticalScrollPane.this.selectedIndex) {
               if(VerticalScrollPane.this.selectedIndex != -1) {
                  XTable var9 = VerticalScrollPane.this.getRowItem(VerticalScrollPane.this.selectedIndex);
                  VerticalScrollPane.this.itemChanged.updateItem(var9, false);
               }

               VerticalScrollPane.this.selectedIndex = var6.index;
               VerticalScrollPane.this.itemChanged.updateItem(var6, true);
               return true;
            } else {
               VerticalScrollPane.this.itemChanged.updateItem(var6, false);
               VerticalScrollPane.this.selectedIndex = -1;
               return true;
            }
         } else {
            return false;
         }
      }
   }
}
