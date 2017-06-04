package com.doodlegames.air.force.enemy.level7;

import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.resource.Assets_level7;
import com.doodlegames.air.force.utils.RegionUtilFunctions;

public class BossLv7Force extends Enemy {

  private static float fadeOut = 2.0F;
   private static TextureAtlas.AtlasRegion forceRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_force");
   private static int healthyDegree = 1;
   float alpha = 0.0F;
   Vector2 tmp = new Vector2();
 private static final float FORCEHEIGHT = (float)RegionUtilFunctions.getRegionHeight((TextureRegion)forceRegion);
   private static final float FORCEWIDTH = (float)RegionUtilFunctions.getRegionWidth((TextureRegion)forceRegion);
   

   public BossLv7Force(World var1, float var2, float var3) {
      super(var1, healthyDegree, var2, var3, FORCEWIDTH, FORCEHEIGHT);
      this.enemyRegion = forceRegion;
   }

   public static BossLv7Force.ForceManager getForceManager(World var0, BossLv7L var1, Vector2 ... var2) {
      int var3 = var2.length;
      BossLv7Force.ForceManager var4 = new BossLv7Force.ForceManager(var0);

      for(int var5 = 0; var5 < var3; ++var5) {
         var4.addAForce(var1.bounds.x + var2[var5].x, var1.bounds.y + var2[var5].y);
      }

      return var4;
   }

   public static void loadResource() {
      forceRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_force");
   }

   public void beHitByBullet(Bullet var1) {
      float var2 = 150.0F;
      if(var1.bulletType == 0) {
         float var3 = var1.getPositionX();
         float var4 = var1.getPositionY();
         this.tmp.set(var1.getVelX(), var1.getVelY());
         Vector2 var6 = this.tmp;
         if(this.tmp.y <= var2) {
            var2 = this.tmp.y;
         }

         var6.y = var2;
         this.tmp.y = -this.tmp.y;
         Bullet var7 = EnemyBullet1.alienBulletGen.getABullet(this, this.world, var3, var4, this.tmp);
         Enemy.bullets.add(var7);
         this.alpha = 1.0F;
      }
   }

   public List<Rectangle> getHitRectangle() {
      return (List)(this.world.rand.nextInt(250) == 0 && !this.isCrashed()?super.getHitRectangle():Enemy.EMPTYRECTANGELS);
   }

   public float getTimeOfCrash() {
      return 2.0F;
   }

   public void render(SpriteBatch var1) {
      Color var2 = var1.getColor();
      float var3 = var2.a;
      var2.a = this.alpha;
      var1.setColor(var2);
      super.render(var1);
      var2.a = var3;
      var1.setColor(var2);
   }

   public void update(float var1) {
      super.update(var1);
      if(this.alpha > 0.0F) {
         this.alpha -= var1 / fadeOut;
      }

      if(this.alpha < 0.0F) {
         this.alpha = 0.0F;
      }

   }

   public void updateShooting(float var1) {}

   public static class ForceManager {

      Array<BossLv7Force> forces;
      World world;


      public ForceManager(World var1) {
         this.world = var1;
         this.forces = new Array(3);
      }

      public void addAForce(float var1, float var2) {
         this.forces.add(new BossLv7Force(this.world, var1, var2));
      }

      public Array<BossLv7Force> getForces() {
         return this.forces;
      }

      public void makeCrash() {
         Iterator var1 = this.forces.iterator();

         while(var1.hasNext()) {
            ((BossLv7Force)var1.next()).makeCrash();
         }

      }

      public void render(SpriteBatch var1) {
         Iterator var2 = this.forces.iterator();

         while(var2.hasNext()) {
            ((BossLv7Force)var2.next()).render(var1);
         }

      }

      public void setSleepPostion(BossLv7L var1, Vector2 ... var2) {
         int var3 = var2.length;
         int var4 = Math.min(this.forces.size, var3);

         for(int var5 = 0; var5 < var4; ++var5) {
            float var6 = var1.bounds.x + var2[var5].x;
            float var7 = var1.bounds.y + var2[var5].y;
            ((BossLv7Force)this.forces.get(var5)).setSleepPosition(var6, var7);
         }

      }

      public void update(float var1) {
         Iterator var2 = this.forces.iterator();

         while(var2.hasNext()) {
            ((BossLv7Force)var2.next()).update(var1);
         }

      }
   }
}
