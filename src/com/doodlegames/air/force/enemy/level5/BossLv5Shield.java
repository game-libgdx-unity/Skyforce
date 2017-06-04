package com.doodlegames.air.force.enemy.level5;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldAnimationPlayer;
import com.doodlegames.air.force.game.WorldAudio;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.resource.Assets_level5;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class BossLv5Shield extends Enemy {

   public static final int DEFAULTHEALTHYDEGREE = 100;
   public static  int DHEIGHT = 0;
   public static TextureAtlas.AtlasRegion DShieldRegion = Assets_level5.atlas_Enemy.findRegion("lv_5_boss_shield_d");
   public static  int DWIDTH = 0;
   private static final Rectangle[] HITREC1;
   private static final Rectangle[] HITREC2;
   private static final Rectangle[] HITREC3;
   public static  int LHEIGHT = 0;
   public static TextureAtlas.AtlasRegion LShieldRegion = Assets_level5.atlas_Enemy.findRegion("lv_5_boss_shield_l");
   public static int LWIDTH = 0;
   public static final int RECTANGLENUM = 10;
   public static final int RHEIGHT;
   public static TextureAtlas.AtlasRegion RShieldRegion = Assets_level5.atlas_Enemy.findRegion("lv_5_boss_shield_r");
   public static final int RWIDTH;
   private Rectangle[] dynamicHitRecs;
   private Rectangle[] fixedHitRecs;
   private List<Rectangle> hitRects;
   private BossLv5Shield.SheildPosition sp;


   static {
      int var0;
      if(LShieldRegion.rotate) {
         var0 = LShieldRegion.getRegionHeight();
      } else {
         var0 = LShieldRegion.getRegionWidth();
      }

      LWIDTH = var0;
      int var1;
      if(LShieldRegion.rotate) {
         var1 = LShieldRegion.getRegionWidth();
      } else {
         var1 = LShieldRegion.getRegionHeight();
      }

      LHEIGHT = var1;
      Rectangle[] var2 = new Rectangle[]{new Rectangle(0.0F, 25.0F, 23.75F, 20.0F), new Rectangle(23.75F, 36.0F, 23.75F, 20.0F), new Rectangle(47.5F, 47.0F, 23.75F, 20.0F), new Rectangle(71.25F, 58.0F, 23.75F, 20.0F), new Rectangle(95.0F, 69.0F, 7.0F, 30.0F), new Rectangle(95.0F, 95.0F, 20.0F, 85.0F), new Rectangle(0.0F, 45.0F, 10.0F, 20.0F), new Rectangle(4.0F, 65.0F, 10.0F, 20.0F), new Rectangle(8.0F, 85.0F, 10.0F, 20.0F), new Rectangle(12.0F, 105.0F, 10.0F, 20.0F)};
      HITREC1 = var2;
      int var3;
      if(RShieldRegion.rotate) {
         var3 = RShieldRegion.getRegionHeight();
      } else {
         var3 = RShieldRegion.getRegionWidth();
      }

      RWIDTH = var3;
      int var4;
      if(RShieldRegion.rotate) {
         var4 = RShieldRegion.getRegionWidth();
      } else {
         var4 = RShieldRegion.getRegionHeight();
      }

      RHEIGHT = var4;
      Rectangle[] var5 = new Rectangle[]{new Rectangle(20.0F, 180.0F, 30.0F, 14.0F), new Rectangle(36.25F, 152.0F, 30.0F, 30.0F), new Rectangle(52.5F, 124.0F, 30.0F, 30.0F), new Rectangle(68.75F, 96.0F, 30.0F, 30.0F), new Rectangle(85.0F, 68.0F, 20.0F, 30.0F), new Rectangle(138.0F, 37.0F, 24.0F, 36.0F), new Rectangle(106.0F, 68.0F, 33.0F, 30.0F), new Rectangle(80.0F, 148.0F, 16.0F, 20.0F), new Rectangle(97.0F, 123.0F, 20.0F, 30.0F), new Rectangle(108.0F, 99.0F, 20.0F, 20.0F)};
      HITREC2 = var5;
      int var6;
      if(DShieldRegion.rotate) {
         var6 = DShieldRegion.getRegionHeight();
      } else {
         var6 = DShieldRegion.getRegionWidth();
      }

      DWIDTH = var6;
      int var7;
      if(DShieldRegion.rotate) {
         var7 = DShieldRegion.getRegionWidth();
      } else {
         var7 = DShieldRegion.getRegionHeight();
      }

      DHEIGHT = var7;
      Rectangle[] var8 = new Rectangle[]{new Rectangle(0.0F, 12.0F, 12.0F, 36.0F), new Rectangle(12.0F, 12.0F, 126.0F, 68.0F), new Rectangle(140.0F, 14.0F, 12.0F, 36.0F), new Rectangle(35.0F, 0.0F, 85.0F, 20.0F)};
      HITREC3 = var8;
   }

   public BossLv5Shield(final World world, final float n, final float n2, final SheildPosition sp) {

       super(world, Settings.lapFixBossDegree(100), n, n2, getSheildWidth(sp), getSheildHeight(sp));

       this.sp = sp;

       switch (this.sp) {

           case LeftSheild: {

               this.enemyRegion = BossLv5Shield.LShieldRegion;

               this.fixedHitRecs = BossLv5Shield.HITREC1;

               break;

           }

           case RightSheild: {

               this.enemyRegion = BossLv5Shield.RShieldRegion;

               this.fixedHitRecs = BossLv5Shield.HITREC2;

               break;

           }

           case BottomSheild: {

               this.enemyRegion = BossLv5Shield.DShieldRegion;

               this.fixedHitRecs = BossLv5Shield.HITREC3;

               break;

           }

       }

       this.dynamicHitRecs = new Rectangle[10];

       this.hitRects = new ArrayList(10);

       this.hitRects.clear();

       for (int i = 0; i < 10; ++i) {

           if (i < this.fixedHitRecs.length) {

               this.dynamicHitRecs[i] = new Rectangle(this.fixedHitRecs[i]);

           }

           else {

               this.dynamicHitRecs[i] = null;

           }

       }

   }

   
   public static final float getSheildHeight(final SheildPosition sheildPosition) {

       switch (sheildPosition) {

           case LeftSheild: {

               return (float)BossLv5Shield.LHEIGHT / 1.0f;

           }

           case RightSheild: {

               return (float)BossLv5Shield.RHEIGHT / 1.0f;

           }

           case BottomSheild: {

               return (float)BossLv5Shield.DHEIGHT / 1.0f;

           }

           default: {

               return 0.0f;

           }

       }

   }

   

   public static final float getSheildWidth(final SheildPosition sheildPosition) {

       switch (sheildPosition) {

           case LeftSheild: {

               return (float)BossLv5Shield.LWIDTH / 1.0f;

           }

           case RightSheild: {

               return (float)BossLv5Shield.RWIDTH / 1.0f;

           }

           case BottomSheild: {

               return (float)BossLv5Shield.DWIDTH / 1.0f;

           }

           default: {

               return 0.0f;

           }

       }

   }
   public static void loadResource() {
      LShieldRegion = Assets_level5.atlas_Enemy.findRegion("lv_5_boss_shield_l");
      RShieldRegion = Assets_level5.atlas_Enemy.findRegion("lv_5_boss_shield_r");
      DShieldRegion = Assets_level5.atlas_Enemy.findRegion("lv_5_boss_shield_d");
   }

   public void beHitByBullet(Bullet var1) {
      super.beHitByBullet(var1);
   }

   public List<Rectangle> getHitRectangle() {
      this.hitRects.clear();

      for(int var1 = 0; var1 < this.fixedHitRecs.length; ++var1) {
         this.dynamicHitRecs[var1].x = this.bounds.x + this.fixedHitRecs[var1].x;
         this.dynamicHitRecs[var1].y = this.bounds.y + this.fixedHitRecs[var1].y;
         this.dynamicHitRecs[var1].width = this.fixedHitRecs[var1].width;
         this.dynamicHitRecs[var1].height = this.fixedHitRecs[var1].height;
         this.hitRects.add(this.dynamicHitRecs[var1]);
      }

      return this.hitRects;
   }

   public float getTimeOfCrash() {
      return 1.0F;
   }

   public void putCrashAniamtion() {
      Animation var1 = this.getShatterAniamtion();
      Animation var2 = this.getCrashBoom(true);
      WorldAudio.PlaySound var3 = this.getCrashSound(true);
      float var4 = (float)Clock.getTimeCounter();
      float var5 = this.bounds.x + this.bounds.width * Clock.rand.nextFloat();
      float var6 = this.bounds.y + this.bounds.height * Clock.rand.nextFloat();
      WorldAnimationPlayer.addAWorldAnimation(var1, var5, var6, 1, var4);
      WorldAnimationPlayer.addAWorldAnimation(var2, var5, var6, 1, var4, var3);
      float var8 = this.bounds.x + this.bounds.width * Clock.rand.nextFloat();
      float var9 = this.bounds.y + this.bounds.height * Clock.rand.nextFloat();
      float var10 = var4 + 0.4F;
      WorldAnimationPlayer.addAWorldAnimation(var1, var8, var9, 1, var10);
      WorldAnimationPlayer.addAWorldAnimation(var2, var8, var9, 1, var10, var3);
      float var12 = this.bounds.x + this.bounds.width * Clock.rand.nextFloat();
      float var13 = this.bounds.y + this.bounds.height * Clock.rand.nextFloat();
      float var14 = var10 + 0.4F;
      WorldAnimationPlayer.addAWorldAnimation(var1, var12, var13, 1, var14);
      WorldAnimationPlayer.addAWorldAnimation(var2, var12, var13, 1, var14, var3);
   }

   public void render(SpriteBatch var1) {
      super.render(var1);
   }

   public void update(float var1) {
      super.update(var1);
   }

   public void updateShooting(float var1) {}

   public static enum SheildPosition {

      BottomSheild,
      LeftSheild,
      RightSheild,
   }
}
