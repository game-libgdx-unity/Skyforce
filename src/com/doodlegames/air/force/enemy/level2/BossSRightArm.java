package com.doodlegames.air.force.enemy.level2;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.EnemyBullet2;
import com.doodlegames.air.force.resource.Assets_level2;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

public class BossSRightArm extends Enemy implements EnemyBullet2.MissleFixer {

   private static final float BULLETSPEED = 400.0F;
   public static final int DEFAULTHEALTHYDEGREE = 40;
   private static final float TIMEOFPLAYCRASHANIMATION = 1.0F;
   private static TextureAtlas.AtlasRegion atlasBroRegion = Assets_level2.atlas_Enemy.findRegion("boss_rocket_r_broken");
   private static TextureAtlas.AtlasRegion atlasRegion = Assets_level2.atlas_Enemy.findRegion("boss_rocket_r");
   public static final Vector2[] bulletPostion;
   private static final Rectangle hitRightArm;
   public static final int regionHeight;
   public static final int regionWidth;
   public static final Vector2 velBullet;
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
      velBullet = new Vector2(0.0F, -400.0F);
      Vector2[] var2 = new Vector2[]{new Vector2(10.0F, 8.0F), new Vector2(10.0F, 15.0F), new Vector2(10.0F, 22.0F), new Vector2(10.0F, 29.0F), new Vector2(10.0F, 36.0F), new Vector2(10.0F, 43.0F)};
      bulletPostion = var2;
      hitRightArm = new Rectangle(2.0F, 0.0F, 14.0F, (float)atlasRegion.getRegionHeight() / 1.0F);
   }

   public BossSRightArm(World var1, float var2, float var3) {
      super(var1, Settings.lapFixBossDegree(40), var2, var3, (float)regionWidth / 1.0F, (float)regionHeight / 1.0F);
      this.thisVelBullet = new Vector2(velBullet);
      this.hitR = new Rectangle(hitRightArm);
      Rectangle var4 = this.hitR;
      var4.x += this.bounds.x;
      Rectangle var5 = this.hitR;
      var5.y += this.bounds.y;
      this.enemyRegion = atlasRegion;
      this.enemyBroRegion = atlasBroRegion;
   }

   public static void loadResource() {
      atlasRegion = Assets_level2.atlas_Enemy.findRegion("boss_rocket_r");
      atlasBroRegion = Assets_level2.atlas_Enemy.findRegion("boss_rocket_r_broken");
   }

   public void fixMissle(EnemyBullet2 var1) {
      var1.setTurnCountMax(10);
      var1.addGameAction(EnemyBullet2.getSpeedUp(var1, 1.0F, 1.0F, 500.0F));
   }

   public float getTimeOfCrash() {
      return 1.0F;
   }

   public Rectangle gethitR() {
      this.hitR.setX(this.bounds.x + hitRightArm.x);
      this.hitR.setY(this.bounds.y + hitRightArm.y);
      return this.hitR;
   }

   public void shooting() {
      if(!this.isCrashed()) {
         float var1 = 0.0F;

         for(int var2 = Clock.rand.nextInt(2); var2 < bulletPostion.length; var2 += 2) {
            Vector2 var3 = bulletPostion[var2];
            this.addEnemyAction(EnemyTools.naziShootMissle(this, var1, 100.0F, var3.x, var3.y, 0.0F));
            var1 += 0.4F;
         }
      }

   }

   public void update(float var1) {
      super.update(var1);
   }

   public void updateShooting(float var1) {}
}
