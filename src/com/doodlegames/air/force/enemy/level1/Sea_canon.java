package com.doodlegames.air.force.enemy.level1;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.HitDistribute;
import com.doodlegames.air.force.enemy.cannon.CommonCannon;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.resource.Assets_level1;
import com.doodlegames.air.force.utils.Clock;

import java.util.ArrayList;
import java.util.List;

public class Sea_canon extends Enemy {

   public static final int DEFAULTHEALTHYDEGREE = 1;
   public static  int HEIGHT = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 15.0F;
   public static final int WIDTH;
   private static TextureAtlas.AtlasRegion seaCanonTextureRegion = Assets_level1.findRegion("nazi_sea_canon");
   private CommonCannon cannon1;
   private HitDistribute hitDistribute;
   private ArrayList<Rectangle> hitRecs;
   private Clock shootClock;
   private static final int ATLASHEIGHT = seaCanonTextureRegion.getRegionWidth();
   private static final int ATLASWIDTH = seaCanonTextureRegion.getRegionWidth();
 

   static {
      int var0;
      if(seaCanonTextureRegion.rotate) {
         var0 = ATLASHEIGHT;
      } else {
         var0 = ATLASWIDTH;
      }

      WIDTH = var0;
      int var1;
      if(seaCanonTextureRegion.rotate) {
         var1 = ATLASWIDTH;
      } else {
         var1 = ATLASHEIGHT;
      }

      HEIGHT = var1;
   }

   public Sea_canon(World var1, float var2, float var3) {
      super(var1, 1, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.cannon1 = CommonCannon.getCannon1(var1, new Clock(1.0F + 3.0F * var1.rand.nextFloat(), 9.0F, 3.0F, (byte)2), 5, EnemyBullet1.naziBulletGen, this, 13.0F, 12.0F, 150.0F);
      this.cannon1.setTimeOfCrash(15.0F);
      this.hitDistribute = new HitDistribute(this, 2);
      this.hitRecs = new ArrayList();
      Rectangle var4 = new Rectangle();
      var4.x = this.bounds.x + this.bounds.width / 4.0F;
      var4.y = this.bounds.y + this.bounds.height / 4.0F;
      var4.width = this.bounds.width / 2.0F;
      var4.height = this.bounds.height / 2.0F;
      this.hitRecs.add(var4);
      this.shootClock = new Clock(5.0F);
      this.enemyRegion = seaCanonTextureRegion;
   }

   public static void loadResource() {
      seaCanonTextureRegion = Assets_level1.findRegion("nazi_sea_canon");
   }

   public void beHitByBullet(Bullet var1, int var2) {
      this.hitDistribute.beHitByBullet(var1, var2);
   }

   public boolean canBeHit() {
      return this.cannon1.isCrashed() && super.canBeHit();
   }

   public List<Rectangle> getHitRectangle() {
      this.hitDistribute.beginAdd();
      this.hitDistribute.addEnemyHitRec(this.cannon1);
      this.hitDistribute.addEnemyHitRec(this);
      this.hitDistribute.endAdd();
      return this.hitDistribute.getHitRectangle();
   }

   public List<Rectangle> getHitRectangle(int var1) {
      ArrayList var2 = this.hitRecs;
      Rectangle var3 = (Rectangle)var2.get(0);
      var3.x = this.bounds.x + this.bounds.width / 4.0F;
      var3.y = this.bounds.y + this.bounds.height / 4.0F;
      var3.width = this.bounds.width / 2.0F;
      var3.height = this.bounds.height / 2.0F;
      return var2;
   }

   public float getTimeOfCrash() {
      return 15.0F;
   }

   public boolean isCrashed() {
      return super.isCrashed() && this.cannon1.isCrashed();
   }

   public void render(SpriteBatch var1) {
      super.render(var1);
      this.cannon1.render(var1);
   }

   public void update(float var1) {
      super.update(var1);
      this.cannon1.update(var1);
   }

   public void updateShooting(float var1) {
      if(this.shootClock.isFired() && !this.cannon1.isCrashed()) {
         float var2 = this.world.fighter.getPositionY() - this.cannon1.getPositionY();
         float var3 = this.world.fighter.getPositionX() - this.cannon1.getPositionX();
         if(var2 < 0.0F) {
            float var4 = 90.0F + (float)(57.2957763671875D * Math.atan2((double)var2, (double)var3));
            this.cannon1.rotateTo(var4);
         }
      }

   }
}
