package com.doodlegames.air.force.enemy.level1;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.HitDistribute;
import com.doodlegames.air.force.enemy.cannon.CommonCannon;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.resource.Assets_level1;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;

import java.util.ArrayList;
import java.util.List;

public class Destroyer extends Enemy {

   private static final float BULLETSPEED = 10.0F;
   public static final int DEFAULTHEALTHYDEGREE = 1;
   public static int HEIGHT = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 15.0F;
   public static final int WIDTH;
   private static TextureAtlas.AtlasRegion destroyerBroTextureRegion = Assets_level1.findRegion("nazi_destroyer_broken");
   private static TextureAtlas.AtlasRegion destroyerTextureRegion = Assets_level1.findRegion("nazi_destroyer");
   private static Animation waveAnimation;
   private CommonCannon cannon2;
   private HitDistribute hitDistribute;
   private ArrayList<Rectangle> hitRecs;
   private Clock shootClock;
   private static final int ATLASHEIGHT = destroyerTextureRegion.getRegionHeight();
   private static final int ATLASWIDTH = destroyerTextureRegion.getRegionWidth();
 

   static {
      int var0;
      if(destroyerTextureRegion.rotate) {
         var0 = ATLASHEIGHT;
      } else {
         var0 = ATLASWIDTH;
      }

      WIDTH = var0;
      int var1;
      if(destroyerTextureRegion.rotate) {
         var1 = ATLASWIDTH;
      } else {
         var1 = ATLASHEIGHT;
      }

      HEIGHT = var1;
      TextureRegion[] var2 = new TextureRegion[]{Assets_level1.findRegion("nazi_destroyer_wave", 0), Assets_level1.findRegion("nazi_destroyer_wave", 1), Assets_level1.findRegion("nazi_destroyer_wave", 2)};
      waveAnimation = new Animation(0, 0.25F, var2);
   }

   public Destroyer(World var1, float var2, float var3) {
      super(var1, 1, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.cannon2 = CommonCannon.getCannon2(var1, new Clock(2.0F + 2.0F * var1.rand.nextFloat(), 9.0F, 3.0F, (byte)2), 10, EnemyBullet1.naziBulletGen, this, 6.0F, 22.0F, 300.0F);
      this.cannon2.setTimeOfCrash(15.0F);
      this.hitDistribute = new HitDistribute(this, 2);
      this.hitRecs = new ArrayList();
      Rectangle var4 = new Rectangle();
      var4.x = this.bounds.x + this.bounds.width / 4.0F;
      var4.y = this.bounds.y + this.bounds.height / 4.0F;
      var4.width = this.bounds.width / 2.0F;
      var4.height = this.bounds.height / 2.0F;
      this.hitRecs.add(var4);
      this.shootClock = new Clock(5.0F);
      this.enemyRegion = destroyerTextureRegion;
      this.enemyBroRegion = destroyerBroTextureRegion;
      this.crashUsePath = false;
   }

   public static void loadResource() {
      destroyerTextureRegion = Assets_level1.findRegion("nazi_destroyer");
      destroyerBroTextureRegion = Assets_level1.findRegion("nazi_destroyer_broken");
      waveAnimation.setKeyFrame(0, Assets_level1.findRegion("nazi_destroyer_wave", 0));
      waveAnimation.setKeyFrame(1, Assets_level1.findRegion("nazi_destroyer_wave", 1));
      waveAnimation.setKeyFrame(2, Assets_level1.findRegion("nazi_destroyer_wave", 2));
   }

   public void beHitByBullet(Bullet var1, int var2) {
      this.hitDistribute.beHitByBullet(var1, var2);
   }

   public boolean canBeHit() {
      return this.cannon2.isCrashed() && super.canBeHit();
   }

   public List<Rectangle> getHitRectangle() {
      this.hitDistribute.beginAdd();
      this.hitDistribute.addEnemyHitRec(this.cannon2);
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
      return super.isCrashed() && this.cannon2.isCrashed();
   }

   public void makeCrash() {
      super.makeCrash();
      this.cannon2.makeCrash();
   }

   public void render(SpriteBatch var1) {
      super.render(var1);
      this.cannon2.render(var1);
   }

   public void renderEnemy(SpriteBatch var1) {
      TextureRegion var2 = waveAnimation.getKeyFrame((float)Clock.getTimeCounter());
      RegionUtilFunctions.draw(var1, var2, this.bounds.x - 3.0F, 2.0F + this.bounds.y, 0.0F, 0.0F, (float)RegionUtilFunctions.getRegionWidth(var2) / 1.0F, (float)RegionUtilFunctions.getRegionHeight(var2) / 1.0F, 1.0F, 1.0F, this.angle);
      super.renderEnemy(var1);
   }

   public void update(float var1) {
      super.update(var1);
      this.cannon2.update(var1);
   }

   public void updateShooting(float var1) {
      if(this.shootClock.isFired() && !this.cannon2.isCrashed()) {
         float var2 = this.world.fighter.bounds.width / 2.0F;
         float var3 = this.world.fighter.getPositionY() - this.cannon2.getPositionY();
         float var4 = this.world.fighter.getPositionX() - this.cannon2.getPositionX();
         if(!this.world.rand.nextBoolean()) {
            var2 = -var2;
         }

         float var5 = var4 + var2;
         if(var3 < 0.0F) {
            float var6 = 90.0F + (float)(57.2957763671875D * Math.atan2((double)var3, (double)var5));
            if(var6 > -90.0F && var6 < 90.0F) {
               this.cannon2.rotateTo(var6);
            }
         }
      }

   }

   protected boolean usePathAngle() {
      return false;
   }
}
