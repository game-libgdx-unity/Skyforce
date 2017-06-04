package com.doodlegames.air.force.game.prop;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.prop.Prop;
import com.doodlegames.air.force.game.prop.PropGenerator;
import com.doodlegames.air.force.game.prop.propFloatingPath.PingPongFloating;
import com.doodlegames.air.force.resource.Assets_Props;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.LinkedList;
import java.util.Queue;

public class PropBullet extends Prop {

   private static int[] HEIGHT;
   public static final Animation[] PROPANIMATION;
   private static Queue<PropBullet> PropBulletPool;
   private static int[] WIDTH;
   public static PropGenerator propBlueGen = new PropGenerator() {
      public Prop getAProp(GameObject var1, World var2, float var3, float var4) {
         return PropBullet.getAPropBullet(var1, var2, var3, var4, 2);
      }
   };
   public static PropGenerator[] propBulletGen;
   public static PropGenerator propRedGen = new PropGenerator() {
      public Prop getAProp(GameObject var1, World var2, float var3, float var4) {
         return PropBullet.getAPropBullet(var1, var2, var3, var4, 0);
      }
   };
   public static PropGenerator propYellowGen = new PropGenerator() {
      public Prop getAProp(GameObject var1, World var2, float var3, float var4) {
         return PropBullet.getAPropBullet(var1, var2, var3, var4, 1);
      }
   };
   private Clock ClockBulletChange;
   private int index;


   static {
      PropGenerator[] var0 = new PropGenerator[]{propRedGen, propYellowGen, propBlueGen};
      propBulletGen = var0;
      PropBulletPool = new LinkedList();
      Animation[] var1 = new Animation[3];
      TextureRegion[] var2 = new TextureRegion[]{Assets_Props.atlas_Prop.findRegion("gun", 0)};
      var1[0] = new Animation(0, 1.0F, var2);
      TextureRegion[] var3 = new TextureRegion[]{Assets_Props.atlas_Prop.findRegion("gun", 1)};
      var1[1] = new Animation(0, 1.0F, var3);
      TextureRegion[] var4 = new TextureRegion[]{Assets_Props.atlas_Prop.findRegion("gun", 2)};
      var1[2] = new Animation(0, 1.0F, var4);
      PROPANIMATION = var1;
      WIDTH = new int[]{-1, -1, -1};
      HEIGHT = new int[]{-1, -1, -1};
   }

   public PropBullet(GameObject var1, World var2, float var3, float var4, int var5) {
      super(var1, var2, var3, var4, (float)getRegionSize(true, var5) / 1.0F, (float)getRegionSize(false, var5) / 1.0F, false);
      this.initPropBullet(var5);
      this.ClockBulletChange = new Clock(3.0F);
      this.propFloating = new PingPongFloating(var2.rand, this);
   }

   public static void addABulletProp(GameObject var0, World var1) {
      float var2 = var0.getPositionX();
      float var3 = var0.getPositionY();
      propSet.add(getAPropBullet(var0, var1, var2, var3, Clock.rand.nextInt(3)));
   }

   private static void cycleAPropBullet(PropBullet var0) {
      if(var0 != null && PropBulletPool.size() < 10) {
         PropBulletPool.offer(var0);
      }
   }

   private static PropBullet getAPropBullet(GameObject var0, World var1, float var2, float var3, int var4) {
      if(PropBulletPool.peek() == null) {
         for(int var6 = 0; var6 < 10; ++var6) {
            PropBulletPool.offer(new PropBullet(var0, var1, var2, var3, var4));
         }
      }

      PropBullet var5 = (PropBullet)PropBulletPool.remove();
      var5.reset(var0, var1, var2, var3, var4);
      return var5;
   }

   public static int getRegionSize(boolean var0, int var1) {
      if(var1 >= 0 && var1 < 3) {
         if(WIDTH[var1] < 0 || HEIGHT[var1] < 0) {
            TextureAtlas.AtlasRegion var2 = Assets_Props.atlas_Prop.findRegion("gun", var1);
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

   private void initPropBullet(int var1) {
      super.propAnimation = PROPANIMATION[var1];
      this.index = var1;
   }

   public static void loadResource() {
      int var0 = PROPANIMATION.length;

      for(int var1 = 0; var1 < var0; ++var1) {
         PROPANIMATION[var1].setKeyFrame(0, Assets_Props.atlas_Prop.findRegion("gun", var1));
      }

   }

   public static int poolSize() {
      return PropBulletPool.size();
   }

   protected boolean exeutePropEffect(Fighter var1) {
      return var1.changeGun(this.index);
   }

   public void freeToPool() {
      cycleAPropBullet(this);
   }

   public void reset(GameObject var1, World var2, float var3, float var4, int var5) {
      super.reset(var1, var2, var3, var4, (float)getRegionSize(true, var5) / 1.0F, (float)getRegionSize(false, var5) / 1.0F);
      this.initPropBullet(var5);
   }

   public void update(float var1) {
      super.update(var1);
      if(this.ClockBulletChange.isFired()) {
         this.initPropBullet((1 + this.index) % 3);
         this.setBoundsWidth((float)getRegionSize(true, this.index) / 1.0F);
         this.setBoundsHeight((float)getRegionSize(false, this.index) / 1.0F);
      }

   }
}
