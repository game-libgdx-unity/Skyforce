package com.doodlegames.air.force.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.doodlegames.air.force.utils.Settings;

public class AtlasRegionDrawable extends EmptyDrawable {

   private TextureAtlas.AtlasRegion region;


   public AtlasRegionDrawable() {}

   public AtlasRegionDrawable(TextureAtlas.AtlasRegion var1) {
      this.setRegion(var1);
   }

   public AtlasRegionDrawable(AtlasRegionDrawable var1) {
      super(var1);
      this.setRegion(var1.region);
   }

   public void draw(SpriteBatch var1, float var2, float var3) {
      float var4 = this.getMinWidth();
      float var5 = this.getMinHeight();
      if(this.region.rotate) {
         var1.draw(this.region, var2, var3, 0.0F, 0.0F, var4, var5, 1.0F, 1.0F, 0.0F, true);
      } else {
         var1.draw((TextureRegion)this.region, var2, var3, var4, var5);
      }
   }

   public void draw(SpriteBatch var1, float var2, float var3, float var4, float var5) {
      if(this.region == null) {
         Settings.w("AtalsRegionDraw region is null, can not draw.");
      } else if(this.region.rotate) {
         var1.draw(this.region, var2, var3, 0.0F, 0.0F, var4, var5, 1.0F, 1.0F, 0.0F, true);
      } else {
         var1.draw((TextureRegion)this.region, var2, var3, var4, var5);
      }
   }

   public TextureAtlas.AtlasRegion getRegion() {
      return this.region;
   }

   public void setRegion(TextureAtlas.AtlasRegion var1) {
      this.region = var1;
      float var2 = (float)var1.getRegionWidth();
      float var3 = (float)var1.getRegionHeight();
      float var4;
      if(var1.rotate) {
         var4 = var3;
      } else {
         var4 = var2;
      }

      this.setMinWidth(var4);
      if(!var1.rotate) {
         var2 = var3;
      }

      this.setMinHeight(var2);
   }
}
