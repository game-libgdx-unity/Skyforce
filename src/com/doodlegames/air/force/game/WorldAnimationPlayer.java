package com.doodlegames.air.force.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.doodlegames.air.force.game.WorldAudio;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;
import com.doodlegames.air.force.utils.Settings;

import java.util.Iterator;

public class WorldAnimationPlayer {

   public static Array<WorldAnimationPlayer.WorldAnimation> StageAnimation = new Array(5);
   public static Array<WorldAnimationPlayer.WorldAnimation> worldAnimationsLayer0 = new Array(10);
   public static Array<WorldAnimationPlayer.WorldAnimation> worldAnimationsLayer1 = new Array(30);
   public static Array<WorldAnimationPlayer.WorldAnimation> worldAnimationsLayer2 = new Array(10);


   public static WorldAnimationPlayer.WorldAnimation addAWorldAnimation(TextureRegion var0, float var1, float var2, float var3, int var4, float var5, Color var6) {
      if(var0 == null) {
         return null;
      } else {
         WorldAnimationPlayer.WorldAnimation var7 = getWorldAnimation(var0, var1, var2, var3, var5, var6);
         addWorldAnimationToLayer(var4, var7);
         return var7;
      }
   }

   public static WorldAnimationPlayer.WorldAnimation addAWorldAnimation(Animation var0, float var1, float var2, int var3) {
      return addAWorldAnimation(var0, var1, var2, var3, (float)Clock.getTimeCounter(), (WorldAudio.PlaySound)null);
   }

   public static WorldAnimationPlayer.WorldAnimation addAWorldAnimation(Animation var0, float var1, float var2, int var3, float var4, Color var5, WorldAudio.PlaySound var6) {
      if(var0 == null) {
         return null;
      } else {
         WorldAnimationPlayer.WorldAnimation var7 = getWorldAnimation(var0, var1, var2, var4, var5, var6);
         addWorldAnimationToLayer(var3, var7);
         return var7;
      }
   }

   public static WorldAnimationPlayer.WorldAnimation addAWorldAnimation(Animation var0, float var1, float var2, int var3, float var4, WorldAudio.PlaySound var5) {
      return addAWorldAnimation(var0, var1, var2, var3, var4, (Color)null, var5);
   }

   public static void addAWorldAnimation(Animation var0, float var1, float var2, int var3, float var4) {
      addAWorldAnimation(var0, var1, var2, var3, var4, (Color)null, (WorldAudio.PlaySound)null);
   }

   public static void addAWorldAnimation(Animation var0, float var1, float var2, int var3, WorldAudio.PlaySound var4) {
      addAWorldAnimation(var0, var1, var2, var3, (float)Clock.getTimeCounter(), var4);
   }

   public static void addAWorldAnimation(Animation var0, float var1, float var2, WorldAudio.PlaySound var3) {
      addAWorldAnimation(var0, var1, var2, 2, var3);
   }

   public static void addStageAnimation(Animation var0, float var1, float var2) {
      addAWorldAnimation(var0, var1, var2, 3, (WorldAudio.PlaySound)null);
   }

   public static void addWorldAnimationToLayer(int var0, WorldAnimationPlayer.WorldAnimation var1) {
      switch(var0) {
      case 0:
         worldAnimationsLayer0.add(var1);
         return;
      case 1:
         worldAnimationsLayer1.add(var1);
         return;
      case 2:
         worldAnimationsLayer2.add(var1);
         return;
      case 3:
         StageAnimation.add(var1);
         return;
      default:
         Settings.i("invalid layer id");
      }
   }

   private static WorldAnimationPlayer.WorldAnimation getAEnemyWorldAnimation(float var0, float var1, Color var2, float var3, float var4, int var5, int var6) {
      WorldAnimationPlayer.WorldAnimation var7 = (WorldAnimationPlayer.WorldAnimation)Pools.obtain(WorldAnimationPlayer.WorldAnimation.class);
      var7.clearAction();
      var7.worldAnimationInfo = null;
      var7.color = var2;
      var7.animation = null;
      var7.region = null;
      var7.startTime = var0;
      var7.durationTime = var1;
      var7.boundX = var3 - (float)var5 / 2.0F;
      var7.boundY = var4 - (float)var6 / 2.0F;
      var7.originX = (float)var5 / 2.0F;
      var7.originY = (float)var6 / 2.0F;
      var7.animationSound = null;
      return var7;
   }

   public static WorldAnimationPlayer.WorldAnimation getWorldAnimation(TextureRegion var0, float var1, float var2, float var3, float var4, Color var5) {
      if(var0 == null) {
         return null;
      } else {
         WorldAnimationPlayer.WorldAnimation var6 = getAEnemyWorldAnimation(var4, var1, var5, var2, var3, RegionUtilFunctions.getRegionWidth(var0), RegionUtilFunctions.getRegionHeight(var0));
         var6.region = var0;
         return var6;
      }
   }

   public static WorldAnimationPlayer.WorldAnimation getWorldAnimation(Animation var0, float var1, float var2, float var3, Color var4, WorldAudio.PlaySound var5) {
      if(var0 == null) {
         return null;
      } else {
         int var6 = RegionUtilFunctions.getRegionWidth(var0);
         int var7 = RegionUtilFunctions.getRegionHeight(var0);
         WorldAnimationPlayer.WorldAnimation var8 = getAEnemyWorldAnimation(var3, var0.animationTime, var4, var1, var2, var6, var7);
         var8.animation = var0;
         var8.animationSound = var5;
         return var8;
      }
   }

   private static void playAnimationArray(SpriteBatch var0, Array<WorldAnimationPlayer.WorldAnimation> var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         WorldAnimationPlayer.WorldAnimation var8 = (WorldAnimationPlayer.WorldAnimation)var2.next();
         if(var8 != null) {
            float var9 = (float)Clock.getTimeCounter() - var8.startTime;
            if(var9 >= 0.0F) {
               if(var8.animationSound != null) {
                  var8.animationSound.playSound();
                  var8.animationSound = null;
               }

               float var10 = var8.getX(var9);
               float var11 = var8.getY(var9);
               float var12 = var8.getScaleX(var9);
               float var13 = var8.getScaleY(var9);
               float var14 = var8.getAlpha(var9);
               float var15 = var8.getAngle(var9);
               float var16 = var8.getOriginX();
               float var17 = var8.getOriginY();
               Color var18 = var0.getColor();
               float var19 = var0.getColor().toFloatBits();
               if(var8.color != null) {
                  var18.set(var8.color);
               }

               var18.a = var14 * var18.a;
               var0.setColor(var18);
               if(var8.animation != null) {
                  var8.animation.draw(var0, var10, var11, var9, var16, var17, var12, var13, var15);
               }

               if(var8.region != null) {
                  TextureRegion var20 = var8.region;
                  RegionUtilFunctions.draw(var0, var20, var10, var11, var16, var17, (float)RegionUtilFunctions.getRegionWidth(var20), (float)RegionUtilFunctions.getRegionHeight(var20), var12, var13, var15);
               }

               var0.setColor(var19);
            }
         }
      }

      for(int var3 = -1 + var1.size; var3 >= 0; --var3) {
         WorldAnimationPlayer.WorldAnimation var4 = (WorldAnimationPlayer.WorldAnimation)var1.get(var3);
         if(var4 == null) {
            var1.removeIndex(var3);
         }

         float var5 = (float)(Clock.getTimeCounter() - (double)var4.startTime);
         if(var4 != null && var5 > var4.durationTime) {
            var1.removeIndex(var3);
            var4.animationRemoved();
         }
      }

   }

   public static void renderStageAnimation(SpriteBatch var0) {
      playAnimationArray(var0, StageAnimation);
   }

   public static void renderWorldAnimationLayer0(SpriteBatch var0) {
      playAnimationArray(var0, worldAnimationsLayer0);
   }

   public static void renderWorldAnimationLayer1(SpriteBatch var0) {
      playAnimationArray(var0, worldAnimationsLayer1);
   }

   public static void renderWorldAnimationLayer2(SpriteBatch var0) {
      playAnimationArray(var0, worldAnimationsLayer2);
   }

   public void animationActionTest() {}

   public interface WorldAnimationInfo {

      float getAlpha();

      float getAngle();

      float getScaleX();

      float getScaleY();

      float getX();

      float getY();
   }

   public static class WorldAnimation {

      WorldAnimationPlayer.AnimationAction alphaAction = null;
      Animation animation;
      WorldAudio.PlaySound animationSound;
      float boundX;
      float boundY;
      Color color = null;
      float durationTime;
      float originX = 0.0F;
      float originY = 0.0F;
      TextureRegion region;
      WorldAnimationPlayer.AnimationAction rotateAction = null;
      WorldAnimationPlayer.AnimationAction scaleXAction = null;
      WorldAnimationPlayer.AnimationAction scaleYAction = null;
      float startTime;
      private WorldAnimationPlayer.WorldAnimationInfo worldAnimationInfo = null;
      WorldAnimationPlayer.AnimationAction xMoveAction = null;
      WorldAnimationPlayer.AnimationAction yMoveAction = null;


      public void animationRemoved() {
         this.clearAction();
         this.worldAnimationInfo = null;
         Pools.free((Object)this);
      }

      public void clearAction() {
         if(this.alphaAction != null) {
            Pools.free((Object)this.alphaAction);
         }

         if(this.scaleXAction != null) {
            Pools.free((Object)this.scaleXAction);
         }

         if(this.scaleYAction != null) {
            Pools.free((Object)this.scaleYAction);
         }

         if(this.rotateAction != null) {
            Pools.free((Object)this.rotateAction);
         }

         if(this.xMoveAction != null) {
            Pools.free((Object)this.xMoveAction);
         }

         if(this.yMoveAction != null) {
            Pools.free((Object)this.yMoveAction);
         }

         this.alphaAction = null;
         this.scaleXAction = null;
         this.scaleYAction = null;
         this.rotateAction = null;
         this.xMoveAction = null;
         this.yMoveAction = null;
      }

      public float getAlpha(float var1) {
         return this.worldAnimationInfo != null?this.worldAnimationInfo.getAlpha():this.getValue(this.alphaAction, var1, 1.0F);
      }

      public float getAngle(float var1) {
         return this.worldAnimationInfo != null?this.worldAnimationInfo.getAngle():this.getValue(this.rotateAction, var1, 0.0F);
      }

      public float getOriginX() {
         return this.originX;
      }

      public float getOriginY() {
         return this.originY;
      }

      public float getScaleX(float var1) {
         return this.worldAnimationInfo != null?this.worldAnimationInfo.getScaleX():this.getValue(this.scaleXAction, var1, 1.0F);
      }

      public float getScaleY(float var1) {
         return this.worldAnimationInfo != null?this.worldAnimationInfo.getScaleY():this.getValue(this.scaleYAction, var1, 1.0F);
      }

      public float getValue(WorldAnimationPlayer.AnimationAction var1, float var2, float var3) {
         if(var1 == null) {
            return var3;
         } else {
            float var4 = var2 / this.durationTime;
            if(var4 < 0.0F) {
               var4 = 0.0F;
            }

            if(var4 > 1.0F) {
               var4 = 1.0F;
            }

            return var1.getValue(var4);
         }
      }

      public float getX(float var1) {
         if(this.worldAnimationInfo != null) {
            float var2 = this.worldAnimationInfo.getX();
            TextureRegion var3 = this.region;
            float var4 = 0.0F;
            if(var3 != null) {
               var4 = (float)RegionUtilFunctions.getRegionWidth(this.region);
            }

            if(this.animation != null) {
               var4 = (float)RegionUtilFunctions.getRegionWidth(this.animation);
            }

            return var2 - var4 / 2.0F;
         } else {
            return this.getValue(this.xMoveAction, var1, 0.0F) + this.boundX;
         }
      }

      public float getY(float var1) {
         if(this.worldAnimationInfo != null) {
            float var2 = this.worldAnimationInfo.getY();
            TextureRegion var3 = this.region;
            float var4 = 0.0F;
            if(var3 != null) {
               var4 = (float)RegionUtilFunctions.getRegionHeight(this.region);
            }

            if(this.animation != null) {
               var4 = (float)RegionUtilFunctions.getRegionHeight(this.animation);
            }

            return var2 - var4 / 2.0F;
         } else {
            return this.getValue(this.yMoveAction, var1, 0.0F) + this.boundY;
         }
      }

      public void reset(float var1) {
         this.startTime = var1 + (float)Clock.getTimeCounter();
      }

      public void setAlphaAction(WorldAnimationPlayer.AnimationAction var1) {
         this.alphaAction = var1;
      }

      public void setMoveBy(float var1, float var2) {
         WorldAnimationPlayer.AnimationAction var3 = (WorldAnimationPlayer.AnimationAction)Pools.obtain(WorldAnimationPlayer.AnimationAction.class);
         var3.startValue = 0.0F;
         var3.endValue = var1;
         var3.inter = null;
         this.setXMoveAction(var3);
         WorldAnimationPlayer.AnimationAction var4 = (WorldAnimationPlayer.AnimationAction)Pools.obtain(WorldAnimationPlayer.AnimationAction.class);
         var4.startValue = 0.0F;
         var4.endValue = var2;
         var4.inter = null;
         this.setYMoveAction(var4);
      }

      public void setRotateAction(WorldAnimationPlayer.AnimationAction var1) {
         this.rotateAction = var1;
      }

      public void setScaleXAction(WorldAnimationPlayer.AnimationAction var1) {
         this.scaleXAction = var1;
      }

      public void setScaleYAction(WorldAnimationPlayer.AnimationAction var1) {
         this.scaleYAction = var1;
      }

      public void setWorldAnimationInfo(WorldAnimationPlayer.WorldAnimationInfo var1) {
         this.worldAnimationInfo = var1;
         this.clearAction();
      }

      public void setXMoveAction(WorldAnimationPlayer.AnimationAction var1) {
         this.xMoveAction = var1;
      }

      public void setYMoveAction(WorldAnimationPlayer.AnimationAction var1) {
         this.yMoveAction = var1;
      }
   }

   public abstract static class EnemyWorldAnimaionInfo implements WorldAnimationPlayer.WorldAnimationInfo {

      public float getAlpha() {
         return 1.0F;
      }

      public float getAngle() {
         return 0.0F;
      }

      public float getScaleX() {
         return 1.0F;
      }

      public float getScaleY() {
         return 1.0F;
      }
   }

   public static class AnimationAction {

      public float endValue;
      public Interpolation inter;
      public float startValue;


      public float getValue(float var1) {
         float var2;
         if(this.inter == null) {
            var2 = var1;
         } else {
            var2 = this.inter.apply(var1);
         }

         return var2 * (this.endValue - this.startValue) + this.startValue;
      }
   }
}
