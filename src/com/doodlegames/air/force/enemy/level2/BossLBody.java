package com.doodlegames.air.force.enemy.level2;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.game.bullet.EnemyBullet2;
import com.doodlegames.air.force.resource.Assets_level2;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

public class BossLBody extends Enemy implements EnemyBullet2.MissleFixer {

   private static final float BULLETSPEED = 10.0F;
   private static final int DEFAULTHEALTHYDEGREE = 100;
   private static final float TIMEOFPLAYCRASHANIMATION = 1.0F;
   private static TextureAtlas.AtlasRegion atlasBroRegion = Assets_level2.atlas_Enemy.findRegion("lboss_body_broken");
   private static TextureAtlas.AtlasRegion atlasRegion = Assets_level2.atlas_Enemy.findRegion("lboss_body");
   public static final Vector2[] bulletPosition;
   private static final Rectangle hitRectangle;
   public static final int regionHeight;
   public static final int regionWidth;
   public static final Vector2 velBullet;
   private Clock clockPursue;
   private Rectangle hitR;
   private Vector2 thisVelBullet;


   static {
      int var0;
      if(atlasRegion.rotate) {
         var0 = atlasRegion.getRegionHeight();
      } else {
         var0 = atlasRegion.getRegionWidth();
      }

      regionWidth = var0;
      int var1;
      if(atlasRegion.rotate) {
         var1 = atlasRegion.getRegionWidth();
      } else {
         var1 = atlasRegion.getRegionHeight();
      }

      regionHeight = var1;
      velBullet = new Vector2(0.0F, 100.0F);
      Vector2[] var2 = new Vector2[]{new Vector2(119.0F, 137.0F), new Vector2(126.0F, 138.0F), new Vector2(133.0F, 139.0F), new Vector2(140.0F, 140.0F), new Vector2(147.0F, 141.0F), new Vector2(154.0F, 142.0F), new Vector2(200.0F, 142.0F), new Vector2(207.0F, 141.0F), new Vector2(214.0F, 140.0F), new Vector2(221.0F, 139.0F), new Vector2(228.0F, 138.0F), new Vector2(235.0F, 137.0F)};
      bulletPosition = var2;
      hitRectangle = new Rectangle(117.0F, 34.0F, 119.0F, 92.0F);
   }

   public BossLBody(World var1, float var2, float var3) {

      super(var1, Settings.lapFixBossDegree(100), var2, var3, atlasRegion.rotate? (float)atlasRegion.getRegionHeight() / 1.0F:
    	  (float)atlasRegion.getRegionWidth() / 1.0F, atlasRegion.rotate? (float)atlasRegion.getRegionWidth() / 1.0F:
    		  (float)atlasRegion.getRegionHeight() / 1.0F );
      this.clockPursue = new Clock(0.05F);
      this.thisVelBullet = new Vector2(velBullet);
      this.hitR = new Rectangle(hitRectangle);
      Rectangle var7 = this.hitR;
      var7.x += this.bounds.x;
      Rectangle var8 = this.hitR;
      var8.y += this.bounds.y;
      this.enemyRegion = atlasRegion;
      this.enemyBroRegion = atlasBroRegion;
   }

   public static void loadResource() {
      atlasRegion = Assets_level2.atlas_Enemy.findRegion("lboss_body");
      atlasBroRegion = Assets_level2.atlas_Enemy.findRegion("lboss_body_broken");
   }

   public void fixMissle(EnemyBullet2 var1) {
      var1.setTurnCountMax(10);
      var1.addGameAction(EnemyBullet2.getSpeedUp(var1, 4.0F, 1.0F, 400.0F));
   }

   public float getTimeOfCrash() {
      return 1.0F;
   }

   public Rectangle gethitR() {
      this.hitR.setX(this.bounds.x + hitRectangle.x);
      this.hitR.setY(this.bounds.y + hitRectangle.y);
      return this.hitR;
   }

   public void shooting(int var1) {
      if(!this.isCrashed()) {
         switch(var1) {
         case 0:
            float var6 = 8.0F * (float)(-1 + bulletPosition.length / 2);
            int var7 = 0;

            while(true) {
               int var8 = bulletPosition.length;
               if(var7 >= var8) {
                  Vector2[] var9 = bulletPosition;
                  int var10 = var9.length;

                  for(int var11 = 0; var11 < var10; ++var11) {
                     Vector2 var12 = var9[var11];
                     Enemy.bullets.add(EnemyBullet1.naziBulletGen.getABullet(this, this.world, this.bounds.x + var12.x, this.bounds.y + var12.y, this.thisVelBullet));
                  }

                  return;
               }

               float var14 = bulletPosition[var7].x;
               float var15 = 800.0F - this.bounds.y - 1.0F;
               int var16 = bulletPosition.length / 2;
               float var17;
               if(var7 < var16) {
                  var17 = -var6 + 8.0F * (float)var7;
               } else {
                  var17 = 8.0F * (float)(var7 - bulletPosition.length / 2);
               }

               this.addEnemyAction(EnemyTools.naziShootBullet(this, 2.0F, 300.0F, var14, var15, var17));
               ++var7;
            }
         case 1:
            int var2 = 0;

            while(true) {
               int var3 = bulletPosition.length;
               if(var2 >= var3) {
                  return;
               }

               if(Clock.rand.nextInt(3) == 0) {
                  float var10000;
                  if(var2 < 6) {
                     var10000 = 0.05F * (float)var2;
                  } else {
                     var10000 = 0.05F * (float)(10 - var2);
                  }

                  this.addEnemyAction(EnemyTools.naziShootMissle(this, 0.2F * (float)var2, 80.0F, bulletPosition[var2].x, bulletPosition[var2].y, 180.0F));
               }

               var2 += 2;
            }
         default:
            Settings.appLog("invalid shooting mode!");
         }
      }
   }

   public void update(float var1) {
      super.update(var1);
   }

   public void updateShooting(float var1) {}
}
