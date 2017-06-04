package com.doodlegames.air.force.game.prop;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.prop.Prop;
import com.doodlegames.air.force.game.prop.PropGenerator;
import com.doodlegames.air.force.game.prop.propFloatingPath.CircleFloating;
import com.doodlegames.air.force.resource.Assets_Props;
import com.doodlegames.air.force.utils.Animation;

import java.util.LinkedList;
import java.util.Queue;

public class PropBoom extends Prop {

   private static int HEIGHT;
   public static final Animation PROPANIMATION;
   private static int WIDTH;
   public static PropGenerator propBoomGen = new PropGenerator() {
      public Prop getAProp(GameObject var1, World var2, float var3, float var4) {
         return PropBoom.getAPropBoom(var1, var2, var3, var4);
      }
   };
   private static Queue<PropBoom> propBoomPool = new LinkedList();


   static {
      TextureRegion[] var0 = new TextureRegion[]{Assets_Props.atlas_Prop.findRegion("boom")};
      PROPANIMATION = new Animation(0, 1.0F, var0);
      WIDTH = -1;
      HEIGHT = -1;
   }

   public PropBoom(GameObject var1, World var2, float var3, float var4) {
      super(var1, var2, var3, var4, (float)getRegionSize(true) / 1.0F, (float)getRegionSize(false) / 1.0F, true);
      this.initPropBoom();
      this.propFloating = new CircleFloating(var2.rand, this);
   }

   private static void cycleAPropBoom(PropBoom var0) {
      if(var0 != null && propBoomPool.size() < 5) {
         propBoomPool.offer(var0);
      }
   }

   private static PropBoom getAPropBoom(GameObject var0, World var1, float var2, float var3) {
      if(propBoomPool.peek() == null) {
         for(int var5 = 0; var5 < 10; ++var5) {
            propBoomPool.offer(new PropBoom(var0, var1, var2, var3));
         }
      }

      PropBoom var4 = (PropBoom)propBoomPool.remove();
      var4.reset(var0, var1, var2, var3);
      return var4;
   }

   public static int getRegionSize(boolean var0) {
      if(WIDTH < 0 || HEIGHT < 0) {
         TextureAtlas.AtlasRegion var1 = Assets_Props.atlas_Prop.findRegion("boom");
         int var2;
         if(var1.rotate) {
            var2 = var1.getRegionHeight();
         } else {
            var2 = var1.getRegionWidth();
         }

         WIDTH = var2;
         int var3;
         if(var1.rotate) {
            var3 = var1.getRegionWidth();
         } else {
            var3 = var1.getRegionHeight();
         }

         HEIGHT = var3;
      }

      return var0?WIDTH:HEIGHT;
   }

   public static void loadResource() {
      PROPANIMATION.setKeyFrame(0, Assets_Props.atlas_Prop.findRegion("boom"));
   }

   public static int poolSize() {
      return propBoomPool.size();
   }

   protected boolean exeutePropEffect(Fighter var1) {
      return var1.increaseABoom();
   }

   public void freeToPool() {
      cycleAPropBoom(this);
   }

   public void initPropBoom() {
      this.propAnimation = PROPANIMATION;
   }

   public void reset(GameObject var1, World var2, float var3, float var4) {
      super.reset(var1, var2, var3, var4, (float)getRegionSize(true) / 1.0F, (float)getRegionSize(false) / 1.0F);
      this.initPropBoom();
   }
}
