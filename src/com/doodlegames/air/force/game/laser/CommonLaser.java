package com.doodlegames.air.force.game.laser;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.laser.Laser;
import com.doodlegames.air.force.game.laser.LaserGen;
import com.doodlegames.air.force.game.laser.LaserShooter;
import com.doodlegames.air.force.resource.Assets_ShareInAlien;
import com.doodlegames.air.force.resource.Assets_ShareInNazi;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;

public class CommonLaser extends Laser {

   public static LaserGen alienLaserGenAll = new LaserGen() {
      public Laser getALaser(GameObject var1, LaserShooter var2, float var3) {
         return new CommonLaser(var1, var2, var3, Assets_ShareInAlien.laseAnimationAll);
      }
   };
   public static LaserGen alienLaserGenAllHead = new LaserGen() {
      public Laser getALaser(GameObject var1, LaserShooter var2, float var3) {
         return new CommonLaser(var1, var2, var3, Assets_ShareInAlien.laseHeadAnimationAll);
      }
   };
   public static LaserGen alienLaserGenHalf = new LaserGen() {
      public Laser getALaser(GameObject var1, LaserShooter var2, float var3) {
         return new CommonLaser(var1, var2, var3, Assets_ShareInAlien.laseAnimationHalf);
      }
   };
   public static LaserGen alienLaserGenHalfHead = new LaserGen() {
      public Laser getALaser(GameObject var1, LaserShooter var2, float var3) {
         return new CommonLaser(var1, var2, var3, Assets_ShareInAlien.laseHeadAnimationHalf);
      }
   };
   public static LaserGen alienLaserGenQuart = new LaserGen() {
      public Laser getALaser(GameObject var1, LaserShooter var2, float var3) {
         return new CommonLaser(var1, var2, var3, Assets_ShareInAlien.laseAnimationQuart);
      }
   };
   public static LaserGen alienLaserGenQuartHead = new LaserGen() {
      public Laser getALaser(GameObject var1, LaserShooter var2, float var3) {
         return new CommonLaser(var1, var2, var3, Assets_ShareInAlien.laseHeadAnimationQuart);
      }
   };
   private static Animation animationCached = null;
   private static float animationHeight = -1.0F;
   private static float animationWidth = -1.0F;
   public static LaserGen naziLaserGenAll = new LaserGen() {
      public Laser getALaser(GameObject var1, LaserShooter var2, float var3) {
         return new CommonLaser(var1, var2, var3, Assets_ShareInNazi.laseAnimationAll);
      }
   };
   public static LaserGen naziLaserGenAllHead = new LaserGen() {
      public Laser getALaser(GameObject var1, LaserShooter var2, float var3) {
         return new CommonLaser(var1, var2, var3, Assets_ShareInNazi.laseHeadAnimationAll);
      }
   };
   public static LaserGen naziLaserGenHalf = new LaserGen() {
      public Laser getALaser(GameObject var1, LaserShooter var2, float var3) {
         return new CommonLaser(var1, var2, var3, Assets_ShareInNazi.laseAnimationHalf);
      }
   };
   public static LaserGen naziLaserGenHalfHead = new LaserGen() {
      public Laser getALaser(GameObject var1, LaserShooter var2, float var3) {
         return new CommonLaser(var1, var2, var3, Assets_ShareInNazi.laseHeadAnimationHalf);
      }
   };
   public static LaserGen naziLaserGenQuart = new LaserGen() {
      public Laser getALaser(GameObject var1, LaserShooter var2, float var3) {
         return new CommonLaser(var1, var2, var3, Assets_ShareInNazi.laseAnimationQuart);
      }
   };
   public static LaserGen naziLaserGenQuartHead = new LaserGen() {
      public Laser getALaser(GameObject var1, LaserShooter var2, float var3) {
         return new CommonLaser(var1, var2, var3, Assets_ShareInNazi.laseHeadAnimationQuart);
      }
   };
   Animation laserAnimation;


   public CommonLaser(GameObject var1, LaserShooter var2, float var3, Animation var4) {
      super(var1, var2, var3, getAnimationWidth(var4), getAnimationHeight(var4));
      this.laserAnimation = var4;
   }

   private static float getAnimationHeight(Animation var0) {
      updateCachAnimation(var0);
      return animationHeight;
   }

   private static float getAnimationWidth(Animation var0) {
      updateCachAnimation(var0);
      return animationWidth;
   }

   public static float getDeltaY(int var0, Animation var1) {
      updateCachAnimation(var1);
      return -(0.5F + (float)var0) * animationHeight;
   }

   public static float getDeltaYOfAlienAll(int var0) {
      return getDeltaY(var0, Assets_ShareInAlien.laseAnimationAll);
   }

   public static float getDeltaYOfAlienHalf(int var0) {
      return getDeltaY(var0, Assets_ShareInAlien.laseAnimationHalf) + 0.5F + (float)var0;
   }

   public static float getDeltaYOfAlienQuart(int var0) {
      return getDeltaY(var0, Assets_ShareInAlien.laseAnimationQuart) + 0.5F + (float)var0;
   }

   public static float getDeltaYOfNaziAll(int var0) {
      return getDeltaY(var0, Assets_ShareInNazi.laseAnimationAll);
   }

   public static float getDeltaYOfNaziHalf(int var0) {
      return getDeltaY(var0, Assets_ShareInNazi.laseAnimationHalf);
   }

   public static float getDeltaYOfNaziQuart(int var0) {
      return getDeltaY(var0, Assets_ShareInNazi.laseAnimationQuart);
   }

   public static float getWidthOfAlienAll() {
      return getAnimationWidth(Assets_ShareInAlien.laseAnimationAll);
   }

   public static float getWidthOfAlienHalf() {
      return getAnimationWidth(Assets_ShareInAlien.laseAnimationHalf);
   }

   public static float getWidthOfAlienQuart() {
      return getAnimationWidth(Assets_ShareInAlien.laseAnimationQuart);
   }

   public static float getWidthOfNaziAll() {
      return getAnimationWidth(Assets_ShareInNazi.laseAnimationAll);
   }

   public static float getWidthOfNaziHalf() {
      return getAnimationWidth(Assets_ShareInNazi.laseAnimationHalf);
   }

   public static float getWidthOfNaziQuart() {
      return getAnimationWidth(Assets_ShareInNazi.laseAnimationQuart);
   }

   public static void switchToBody(CommonLaser var0) {
      if(var0.laserAnimation == Assets_ShareInAlien.laseHeadAnimationQuart) {
         var0.setLaserAnimation(Assets_ShareInAlien.laseAnimationQuart);
      }

      if(var0.laserAnimation == Assets_ShareInAlien.laseHeadAnimationHalf) {
         var0.setLaserAnimation(Assets_ShareInAlien.laseAnimationHalf);
      }

      if(var0.laserAnimation == Assets_ShareInAlien.laseHeadAnimationAll) {
         var0.setLaserAnimation(Assets_ShareInAlien.laseAnimationAll);
      }

      if(var0.laserAnimation == Assets_ShareInNazi.laseHeadAnimationQuart) {
         var0.setLaserAnimation(Assets_ShareInNazi.laseAnimationQuart);
      }

      if(var0.laserAnimation == Assets_ShareInNazi.laseHeadAnimationHalf) {
         var0.setLaserAnimation(Assets_ShareInNazi.laseAnimationHalf);
      }

      if(var0.laserAnimation == Assets_ShareInNazi.laseHeadAnimationAll) {
         var0.setLaserAnimation(Assets_ShareInNazi.laseAnimationAll);
      }

   }

   public static void switchToHead(CommonLaser var0) {
      if(var0.laserAnimation == Assets_ShareInAlien.laseAnimationQuart) {
         var0.setLaserAnimation(Assets_ShareInAlien.laseHeadAnimationQuart);
      }

      if(var0.laserAnimation == Assets_ShareInAlien.laseAnimationHalf) {
         var0.setLaserAnimation(Assets_ShareInAlien.laseHeadAnimationHalf);
      }

      if(var0.laserAnimation == Assets_ShareInAlien.laseAnimationAll) {
         var0.setLaserAnimation(Assets_ShareInAlien.laseHeadAnimationAll);
      }

      if(var0.laserAnimation == Assets_ShareInNazi.laseAnimationQuart) {
         var0.setLaserAnimation(Assets_ShareInNazi.laseHeadAnimationQuart);
      }

      if(var0.laserAnimation == Assets_ShareInNazi.laseAnimationHalf) {
         var0.setLaserAnimation(Assets_ShareInNazi.laseHeadAnimationHalf);
      }

      if(var0.laserAnimation == Assets_ShareInNazi.laseAnimationAll) {
         var0.setLaserAnimation(Assets_ShareInNazi.laseHeadAnimationAll);
      }

   }

   private static void updateCachAnimation(Animation var0) {
      if(var0 != animationCached) {
         animationWidth = (float)RegionUtilFunctions.getRegionWidth(var0) / 1.0F;
         animationHeight = (float)RegionUtilFunctions.getRegionHeight(var0) / 1.0F;
         animationCached = var0;
      }

   }

   protected TextureRegion getLaserRegion() {
      return this.laserAnimation.getKeyFrame((float)Clock.getTimeCounter());
   }

   public void setLaserAnimation(Animation var1) {
      if(var1 != null) {
         this.laserAnimation = var1;
      }

   }
}
