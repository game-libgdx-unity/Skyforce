package com.doodlegames.air.force.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;

public class Animation extends TextureRegionDrawable {

   public static final int ANIMATION_LOOPING = 0;
   public static final int ANIMATION_NONLOOPING = 1;
   public final float animationTime;
   public final float frameDuration;
   final TextureRegion[] keyFrames;
   public int mode;
   public Float playedTime;


   public Animation(float var1, TextureRegion ... var2) {
      this(1, var1, var2);
   }

   public Animation(int var1, float var2, TextureRegion ... var3) {
      this.playedTime = null;
      this.mode = var1;
      this.frameDuration = var2;
      this.keyFrames = var3;
      this.animationTime = this.frameDuration * (float)var3.length;
      if(var3.length > 0) {
         super.setRegion(var3[0]);
      }

   }

   public static Animation setAnimationFrame(Animation var0, int var1, float var2, TextureRegion ... var3) {
      if(var0 == null) {
         var0 = new Animation(var1, var2, var3);
      }

      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         var0.setKeyFrame(var5, var3[var5]);
      }

      return var0;
   }

   public void draw(SpriteBatch var1, float var2, float var3, float var4) {
      TextureRegion var5 = this.getKeyFrame(var4);
      RegionUtilFunctions.draw(var1, var5, var2, var3, (float)RegionUtilFunctions.getRegionWidth(var5) / 1.0F, (float)RegionUtilFunctions.getRegionHeight(var5) / 1.0F);
   }

   public void draw(SpriteBatch var1, float var2, float var3, float var4, float var5) {
      RegionUtilFunctions.draw(var1, this.getRegion(), var2, var3, var4, var5);
   }

   public void draw(SpriteBatch var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      TextureRegion var10 = this.getKeyFrame(var4);
      RegionUtilFunctions.draw(var1, var10, var2, var3, var5, var6, (float)RegionUtilFunctions.getRegionWidth(var10) / 1.0F, (float)RegionUtilFunctions.getRegionHeight(var10) / 1.0F, var7, var8, var9);
   }

   public int getFrameCount() {
      return this.keyFrames.length;
   }

   public TextureRegion getKeyFrame(float var1) {
      int var2 = (int)(var1 / this.frameDuration);
      int var3;
      if(this.mode == 1) {
         var3 = Math.min(-1 + this.keyFrames.length, var2);
      } else {
         var3 = var2 % this.keyFrames.length;
      }

      return this.keyFrames[var3];
   }

   public TextureRegion getRegion() {
      return this.playedTime == null?this.getKeyFrame((float)Clock.getTimeCounter()):this.getKeyFrame(this.playedTime.floatValue());
   }

   public void setKeyFrame(int var1, TextureRegion var2) {
      if(var1 >= 0 && var1 < this.keyFrames.length) {
         this.keyFrames[var1] = var2;
      }

   }

   public void setPlayMode(int var1) {
      this.mode = var1;
   }

   public void setPlayedTime(Float var1) {
      this.playedTime = var1;
   }
}
