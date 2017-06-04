package com.doodlegames.air.force.enemy.level7;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldAnimationPlayer;
import com.doodlegames.air.force.game.WorldAudio;
import com.doodlegames.air.force.resource.Assets_level7;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class BossLv7LSilk extends Enemy {

   public static final int DEFAULTHEALTHYDEGREE = 75;
   protected static final int[] HEIGHT;
   private static final float TIMEOFPLAYCRASHANIMATION = 0.2F;
   protected static final int[] WIDTH;
   private static final TextureAtlas.AtlasRegion[] enemyTextureRegion;
   private static final Rectangle[] hitRecs0;
   private static final Rectangle[] hitRecs1;
   private static final Rectangle[] hitRecs2;
   private final Rectangle[] finalHitRecs;
   private List<Rectangle> hitRects;
   private GameObject owner;
   private final float relativeX;
   private final float relativeY;
   private int silkIndex;


   static {
      TextureAtlas.AtlasRegion[] var0 = new TextureAtlas.AtlasRegion[]{Assets_level7.atlas_Enemy.findRegion("lv_7_boss_silk", 0), new TextureAtlas.AtlasRegion(Assets_level7.atlas_Enemy.findRegion("lv_7_boss_silk", 0)), Assets_level7.atlas_Enemy.findRegion("lv_7_boss_silk", 1), new TextureAtlas.AtlasRegion(Assets_level7.atlas_Enemy.findRegion("lv_7_boss_silk", 1)), Assets_level7.atlas_Enemy.findRegion("lv_7_boss_silk", 2), new TextureAtlas.AtlasRegion(Assets_level7.atlas_Enemy.findRegion("lv_7_boss_silk", 2))};
      enemyTextureRegion = var0;
      boolean var1 = enemyTextureRegion[1].isFlipX();
      boolean var2;
      if(!enemyTextureRegion[1].rotate) {
         var2 = true;
      } else {
         var2 = false;
      }

      if(var1 != var2) {
         enemyTextureRegion[1].flip(true, false);
      }

      if(enemyTextureRegion[1].isFlipY() != enemyTextureRegion[1].rotate) {
         enemyTextureRegion[1].flip(false, true);
      }

      boolean var3 = enemyTextureRegion[3].isFlipX();
      boolean var4;
      if(!enemyTextureRegion[3].rotate) {
         var4 = true;
      } else {
         var4 = false;
      }

      if(var3 != var4) {
         enemyTextureRegion[3].flip(true, false);
      }

      if(enemyTextureRegion[3].isFlipY() != enemyTextureRegion[3].rotate) {
         enemyTextureRegion[3].flip(false, true);
      }

      boolean var5 = enemyTextureRegion[5].isFlipX();
      boolean var6;
      if(!enemyTextureRegion[5].rotate) {
         var6 = true;
      } else {
         var6 = false;
      }

      if(var5 != var6) {
         enemyTextureRegion[5].flip(true, false);
      }

      if(enemyTextureRegion[5].isFlipY() != enemyTextureRegion[5].rotate) {
         enemyTextureRegion[5].flip(false, true);
      }

      enemyTextureRegion[1].offsetX = 0.0F;
      enemyTextureRegion[1].offsetY = 0.0F;
      enemyTextureRegion[3].offsetX = 0.0F;
      enemyTextureRegion[3].offsetY = 0.0F;
      enemyTextureRegion[5].offsetX = 0.0F;
      enemyTextureRegion[5].offsetY = 0.0F;
      int[] var7 = new int[6];
      int var8;
      if(enemyTextureRegion[0].rotate) {
         var8 = enemyTextureRegion[0].getRegionHeight();
      } else {
         var8 = enemyTextureRegion[0].getRegionWidth();
      }

      var7[0] = var8;
      int var9;
      if(enemyTextureRegion[0].rotate) {
         var9 = enemyTextureRegion[0].getRegionHeight();
      } else {
         var9 = enemyTextureRegion[0].getRegionWidth();
      }

      var7[1] = var9;
      int var10;
      if(enemyTextureRegion[2].rotate) {
         var10 = enemyTextureRegion[2].getRegionHeight();
      } else {
         var10 = enemyTextureRegion[2].getRegionWidth();
      }

      var7[2] = var10;
      int var11;
      if(enemyTextureRegion[2].rotate) {
         var11 = enemyTextureRegion[2].getRegionHeight();
      } else {
         var11 = enemyTextureRegion[2].getRegionWidth();
      }

      var7[3] = var11;
      int var12;
      if(enemyTextureRegion[4].rotate) {
         var12 = enemyTextureRegion[4].getRegionHeight();
      } else {
         var12 = enemyTextureRegion[4].getRegionWidth();
      }

      var7[4] = var12;
      int var13;
      if(enemyTextureRegion[4].rotate) {
         var13 = enemyTextureRegion[4].getRegionHeight();
      } else {
         var13 = enemyTextureRegion[4].getRegionWidth();
      }

      var7[5] = var13;
      WIDTH = var7;
      int[] var14 = new int[6];
      int var15;
      if(enemyTextureRegion[0].rotate) {
         var15 = enemyTextureRegion[0].getRegionWidth();
      } else {
         var15 = enemyTextureRegion[0].getRegionHeight();
      }

      var14[0] = var15;
      int var16;
      if(enemyTextureRegion[0].rotate) {
         var16 = enemyTextureRegion[0].getRegionWidth();
      } else {
         var16 = enemyTextureRegion[0].getRegionHeight();
      }

      var14[1] = var16;
      int var17;
      if(enemyTextureRegion[2].rotate) {
         var17 = enemyTextureRegion[2].getRegionWidth();
      } else {
         var17 = enemyTextureRegion[2].getRegionHeight();
      }

      var14[2] = var17;
      int var18;
      if(enemyTextureRegion[2].rotate) {
         var18 = enemyTextureRegion[2].getRegionWidth();
      } else {
         var18 = enemyTextureRegion[2].getRegionHeight();
      }

      var14[3] = var18;
      int var19;
      if(enemyTextureRegion[4].rotate) {
         var19 = enemyTextureRegion[4].getRegionWidth();
      } else {
         var19 = enemyTextureRegion[4].getRegionHeight();
      }

      var14[4] = var19;
      int var20;
      if(enemyTextureRegion[4].rotate) {
         var20 = enemyTextureRegion[4].getRegionWidth();
      } else {
         var20 = enemyTextureRegion[4].getRegionHeight();
      }

      var14[5] = var20;
      HEIGHT = var14;
      Rectangle[] var21 = new Rectangle[]{new Rectangle(0.0F, 25.0F, 30.0F, 60.0F), new Rectangle(30.0F, 49.0F, 30.0F, 45.0F), new Rectangle(60.0F, 73.0F, 30.0F, 30.0F), new Rectangle(90.0F, 91.0F, 30.0F, 30.0F), new Rectangle(120.0F, 111.0F, 30.0F, 20.0F), new Rectangle(143.0F, 132.0F, 30.0F, 10.0F), new Rectangle(165.0F, 145.0F, 30.0F, 10.0F), new Rectangle(192.0F, 158.0F, 20.0F, 10.0F), new Rectangle(212.0F, 170.0F, 20.0F, 10.0F), new Rectangle(233.0F, 180.0F, 20.0F, 10.0F)};
      hitRecs0 = var21;
      Rectangle[] var22 = new Rectangle[]{new Rectangle(0.0F, 11.0F, 100.0F, 33.0F), new Rectangle(100.0F, 14.0F, 100.0F, 20.0F), new Rectangle(179.0F, 18.0F, 67.0F, 10.0F)};
      hitRecs1 = var22;
      Rectangle[] var23 = new Rectangle[]{new Rectangle(37.0F, 101.0F, 75.0F, 20.0F), new Rectangle(81.0F, 81.0F, 50.0F, 20.0F), new Rectangle(117.0F, 67.0F, 30.0F, 20.0F), new Rectangle(139.0F, 55.0F, 30.0F, 15.0F), new Rectangle(165.0F, 45.0F, 20.0F, 10.0F), new Rectangle(180.0F, 35.0F, 20.0F, 10.0F), new Rectangle(199.0F, 25.0F, 10.0F, 10.0F), new Rectangle(209.0F, 17.0F, 10.0F, 10.0F)};
      hitRecs2 = var23;
   }

   public BossLv7LSilk(World var1, GameObject var2, float var3, float var4, int var5) {
	   super(var1, Settings.lapFixBossDegree(75), var3, var4, 1f, 1f);
//      int[] var7 = WIDTH;
//      int var8;
//      if(var5 >= 0 && var5 < 6) {
//         var8 = var5;
//      } else {
//         var8 = 0;
//      }
//
//      float var9 = (float)var7[var8] / 1.0F;
//      int[] var10 = HEIGHT;
//      int var11;
//      if(var5 >= 0 && var5 < 6) {
//         var11 = var5;
//      } else {
//         var11 = 0;
//      }

      
      this.owner = var2;
      this.relativeX = var3 - var2.bounds.x;
      this.relativeY = var4 - var2.bounds.y;
      if(var5 >= 6 || var5 < 0) {
         Settings.appLog("invalid index detected. change to 0 already.");
         var5 = 0;
      }

      this.silkIndex = var5;
      this.hitRects = new ArrayList(10);
      this.hitRects.clear();
      if(this.silkIndex != 0 && this.silkIndex != 1) {
         if(this.silkIndex != 2 && this.silkIndex != 3) {
            if(this.silkIndex != 4 && this.silkIndex != 5) {
               Settings.appLog("this.silkIndex is not in [0, 6)");
               this.finalHitRecs = null;
            } else {
               this.finalHitRecs = hitRecs2;
            }
         } else {
            this.finalHitRecs = hitRecs1;
         }
      } else {
         this.finalHitRecs = hitRecs0;
      }

      Rectangle[] var12 = this.finalHitRecs;
      int var13 = var12.length;

      for(int var14 = 0; var14 < var13; ++var14) {
         Rectangle var15 = var12[var14];
         Rectangle var16 = new Rectangle(var15);
         if(this.silkIndex % 2 == 1) {
            var16.x = this.bounds.width - var15.width - var15.x + this.bounds.x;
         } else {
            var16.x = var15.x + this.bounds.x;
         }

         var16.y = var15.y + this.bounds.y;
         this.hitRects.add(var16);
      }

      this.enemyRegion = enemyTextureRegion[var5];
      this.enemyBroRegion = null;
   }

   public static void loadResource() {
      enemyTextureRegion[0] = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_silk", 0);
      enemyTextureRegion[2] = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_silk", 1);
      enemyTextureRegion[4] = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_silk", 2);
      enemyTextureRegion[1].setRegion(enemyTextureRegion[0]);
      enemyTextureRegion[3].setRegion(enemyTextureRegion[2]);
      enemyTextureRegion[5].setRegion(enemyTextureRegion[4]);
      boolean var0 = enemyTextureRegion[1].isFlipX();
      boolean var1;
      if(!enemyTextureRegion[1].rotate) {
         var1 = true;
      } else {
         var1 = false;
      }

      if(var0 != var1) {
         enemyTextureRegion[1].flip(true, false);
      }

      if(enemyTextureRegion[1].isFlipY() != enemyTextureRegion[1].rotate) {
         enemyTextureRegion[1].flip(false, true);
      }

      boolean var2 = enemyTextureRegion[3].isFlipX();
      boolean var3;
      if(!enemyTextureRegion[3].rotate) {
         var3 = true;
      } else {
         var3 = false;
      }

      if(var2 != var3) {
         enemyTextureRegion[3].flip(true, false);
      }

      if(enemyTextureRegion[3].isFlipY() != enemyTextureRegion[3].rotate) {
         enemyTextureRegion[3].flip(false, true);
      }

      boolean var4 = enemyTextureRegion[5].isFlipX();
      boolean var5;
      if(!enemyTextureRegion[5].rotate) {
         var5 = true;
      } else {
         var5 = false;
      }

      if(var4 != var5) {
         enemyTextureRegion[5].flip(true, false);
      }

      if(enemyTextureRegion[5].isFlipY() != enemyTextureRegion[5].rotate) {
         enemyTextureRegion[5].flip(false, true);
      }

      enemyTextureRegion[1].offsetX = 0.0F;
      enemyTextureRegion[1].offsetY = 0.0F;
      enemyTextureRegion[3].offsetX = 0.0F;
      enemyTextureRegion[3].offsetY = 0.0F;
      enemyTextureRegion[5].offsetX = 0.0F;
      enemyTextureRegion[5].offsetY = 0.0F;
   }

   public List<Rectangle> getHitRectangle() {
      int var1 = this.hitRects.size();

      for(int var2 = 0; var2 < var1; ++var2) {
         Rectangle var3 = (Rectangle)this.hitRects.get(var2);
         if(this.silkIndex % 2 == 1) {
            var3.x = this.bounds.width - this.finalHitRecs[var2].width - this.finalHitRecs[var2].x + this.bounds.x;
         } else {
            var3.x = this.bounds.x + this.finalHitRecs[var2].x;
         }

         var3.y = this.bounds.y + this.finalHitRecs[var2].y;
      }

      return this.hitRects;
   }

   public float getTimeOfCrash() {
      return 0.2F;
   }

   public void putCrashAniamtion() {
      byte var1 = 3;
      float var2 = (float)Clock.getTimeCounter();
      Animation var3 = this.getShatterAniamtion();
      Animation var4 = this.getCrashBoom(true);
      WorldAudio.PlaySound var5 = this.getCrashSound(true);
      int var6 = this.finalHitRecs.length;
      if(var6 <= var1) {
         var1 = 1;
      }

      for(int var7 = 0; var7 < var6; var7 += var1) {
         Rectangle var8 = this.finalHitRecs[var7];
         float var9 = var8.x + var8.width / 2.0F;
         float var10 = var8.y + var8.height / 2.0F;
         if(this.silkIndex % 2 == 1) {
            var9 = this.bounds.width - var9;
         }

         float var11 = var9 + this.bounds.x;
         float var12 = var10 + this.bounds.y;
         float var13 = var2 + 0.3F * this.world.rand.nextFloat();
         WorldAnimationPlayer.addAWorldAnimation(var3, var11, var12, 1, var13);
         WorldAnimationPlayer.addAWorldAnimation(var4, var11, var12, 1, var13, var5);
      }

   }

   public void update(float var1) {
      super.update(var1);
      this.setPosition(this.owner.bounds.x + this.relativeX, this.owner.bounds.y + this.relativeY);
   }

   public void updateShooting(float var1) {}
}
