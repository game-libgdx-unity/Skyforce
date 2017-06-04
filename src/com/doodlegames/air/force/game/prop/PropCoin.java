package com.doodlegames.air.force.game.prop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.doodlegames.air.force.utils.RegionUtilFunctions;
import com.doodlegames.air.force.utils.Settings;

import java.util.LinkedList;
import java.util.Queue;

public class PropCoin extends Prop {

   private static int[] COINMONEY;
   private static int[] HEIGHT;
   public static final Animation[] PROPANIMATION;
   private static Queue<PropCoin> PropCoinPool = new LinkedList();
   private static int[] WIDTH;
   public static PropGenerator propCopperCoinGen = new PropGenerator() {
      public Prop getAProp(GameObject var1, World var2, float var3, float var4) {
         return PropCoin.getAPropCoin(var1, var2, var3, var4, 0);
      }
   };
   public static PropGenerator propGoldCoinGen = new PropGenerator() {
      public Prop getAProp(GameObject var1, World var2, float var3, float var4) {
         return PropCoin.getAPropCoin(var1, var2, var3, var4, 2);
      }
   };
   public static PropGenerator propSilverCoinGen = new PropGenerator() {
      public Prop getAProp(GameObject var1, World var2, float var3, float var4) {
         return PropCoin.getAPropCoin(var1, var2, var3, var4, 1);
      }
   };
   private int index;


   static {
      Animation[] var0 = new Animation[3];
      TextureRegion[] var1 = new TextureRegion[]{Assets_Props.atlas_Prop.findRegion("copper")};
      var0[0] = new Animation(0, 1.0F, var1);
      TextureRegion[] var2 = new TextureRegion[]{Assets_Props.atlas_Prop.findRegion("silver")};
      var0[1] = new Animation(0, 1.0F, var2);
      TextureRegion[] var3 = new TextureRegion[]{Assets_Props.atlas_Prop.findRegion("gold")};
      var0[2] = new Animation(0, 1.0F, var3);
      PROPANIMATION = var0;
      WIDTH = new int[]{-1, -1, -1};
      HEIGHT = new int[]{-1, -1, -1};
      COINMONEY = new int[]{1, 10, 50};
   }

   public PropCoin(GameObject var1, World var2, float var3, float var4, int var5) {
      super(var1, var2, var3, var4, (float)getRegionSize(true, var5) / 1.0F, (float)getRegionSize(false, var5) / 1.0F, true);
      this.initPropCoin(var5);
      this.propFloating = new CircleFloating(var2.rand, this);
   }

   private static void cycleAPropCoin(PropCoin var0) {
      if(var0 != null && PropCoinPool.size() < 10) {
         PropCoinPool.offer(var0);
      }
   }

   private static PropCoin getAPropCoin(GameObject var0, World var1, float var2, float var3, int var4) {
      if(PropCoinPool.peek() == null) {
         for(int var6 = 0; var6 < 10; ++var6) {
            PropCoinPool.offer(new PropCoin(var0, var1, var2, var3, var4));
         }
      }

      PropCoin var5 = (PropCoin)PropCoinPool.remove();
      var5.reset(var0, var1, var2, var3, var4);
      return var5;
   }

   public static int getRegionSize(boolean var0, int var1) {
      if(var1 >= 0 && var1 < 3) {
         if(WIDTH[var1] < 0 || HEIGHT[var1] < 0) {
            TextureAtlas.AtlasRegion var2;
            switch(var1) {
            case 0:
               var2 = Assets_Props.atlas_Prop.findRegion("copper");
               break;
            case 1:
               var2 = Assets_Props.atlas_Prop.findRegion("silver");
               break;
            case 2:
               var2 = Assets_Props.atlas_Prop.findRegion("gold");
               break;
            default:
               var2 = null;
            }

            int[] var3 = WIDTH;
            int var4;
            if(var2.rotate) {
               var4 = var2.getRegionHeight();
            } else {
               var4 = var2.getRegionWidth();
            }

            var3[var1] = var4;
            int[] var5 = HEIGHT;
            int var6;
            if(var2.rotate) {
               var6 = var2.getRegionWidth();
            } else {
               var6 = var2.getRegionHeight();
            }

            var5[var1] = var6;
         }

         return var0?WIDTH[var1]:HEIGHT[var1];
      } else {
         Settings.appLog("invalid index in function getRegionSize");
         return -1;
      }
   }

   private void initPropCoin(int var1) {
      this.index = var1;
      this.propAnimation = PROPANIMATION[var1];
   }

   public static void loadResource() {
      PROPANIMATION[0].setKeyFrame(0, Assets_Props.atlas_Prop.findRegion("copper"));
      PROPANIMATION[1].setKeyFrame(0, Assets_Props.atlas_Prop.findRegion("silver"));
      PROPANIMATION[2].setKeyFrame(0, Assets_Props.atlas_Prop.findRegion("gold"));
   }

   public static int poolSize() {
      return PropCoinPool.size();
   }

   protected void drawRegion(SpriteBatch var1, TextureRegion var2) {
      RegionUtilFunctions.draw(var1, var2, this.bounds.x, this.bounds.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, 0.75F, 0.75F, this.angle);
   }

   protected boolean exeutePropEffect(Fighter var1) {
      Settings.money += (long)COINMONEY[this.index];
      return true;
   }

   public void freeToPool() {
      cycleAPropCoin(this);
   }

   public int getCoinValue() {
      return COINMONEY[this.index];
   }

   public void reset(GameObject var1, World var2, float var3, float var4, int var5) {
      super.reset(var1, var2, var3, var4, (float)getRegionSize(true, var5) / 1.0F, (float)getRegionSize(false, var5) / 1.0F);
      this.initPropCoin(var5);
   }
}
