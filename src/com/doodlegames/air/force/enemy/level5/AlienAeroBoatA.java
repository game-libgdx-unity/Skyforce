package com.doodlegames.air.force.enemy.level5;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.cannon.AlienCannon1;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.resource.Assets_level5;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class AlienAeroBoatA extends Enemy {

   public static final Vector2 CANNONPOSITION;
   public static final int DEFAULTHEALTHYDEGREE = 10;
   private static int HEIGHT = 0;
   private static final Rectangle HITRECTANGLE;
   private static final int PARTSNUM = 2;
   private static final float TIMEOFPLAYCRASHANIMATION = 0.1F;
   private static final int WIDTH;
   private static TextureAtlas.AtlasRegion enemyTextureRegion = Assets_level5.atlas_Enemy.findRegion("alien_aeroboat");
   private AlienCannon1 alienCannon;
   private Clock clockPursue = new Clock(1.5F);
   private Clock clockShootingCanon = new Clock(3.0F, 0.25F, (byte)2);
   private Enemy[] hitMap;
   private Rectangle hitR;
   private List<Rectangle> hitRectangles;


   static {
      int var0;
      if(enemyTextureRegion.rotate) {
         var0 = enemyTextureRegion.getRegionHeight();
      } else {
         var0 = enemyTextureRegion.getRegionWidth();
      }

      WIDTH = var0;
      int var1;
      if(enemyTextureRegion.rotate) {
         var1 = enemyTextureRegion.getRegionWidth();
      } else {
         var1 = enemyTextureRegion.getRegionHeight();
      }

      HEIGHT = var1;
      HITRECTANGLE = new Rectangle(17.0F, 57.0F, 30.0F, 40.0F);
      CANNONPOSITION = new Vector2((17.0F + (float)AlienCannon1.canonTextureRegion.getRegionWidth() / 2.0F) / 1.0F, (72.0F + (float)AlienCannon1.canonTextureRegion.getRegionHeight() / 2.0F) / 1.0F);
   }

   public AlienAeroBoatA(World var1, float var2, float var3) {
      super(var1, 10, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.velocity.set(Settings.backgroundVelocity);
      this.hitMap = new Enemy[2];
      this.hitRectangles = new ArrayList(2);
      this.hitR = new Rectangle(HITRECTANGLE);
      this.hitR.x = this.bounds.x + HITRECTANGLE.x;
      this.hitR.y = this.bounds.y + HITRECTANGLE.y;
      this.alienCannon = new AlienCannon1(var1, this.bounds.x + CANNONPOSITION.x, this.bounds.y + CANNONPOSITION.y);
      this.alienCannon.setBulletActionVel(320.0F);
      this.alienCannon.setInitHealthyDgree(5);
      this.enemyRegion = enemyTextureRegion;
   }

   private Rectangle getHitR() {
      this.hitR.x = this.bounds.x + HITRECTANGLE.x;
      this.hitR.y = this.bounds.y + HITRECTANGLE.y;
      return this.hitR;
   }

   public static void loadResource() {
      enemyTextureRegion = Assets_level5.atlas_Enemy.findRegion("alien_aeroboat");
   }

   public void beHitByBullet(Bullet var1, int var2) {
      if(1 != this.state) {
         this.hitMap[var2].beHitByBullet(var1);
         int var3 = var1.damageValue;
         var1.damageValue = 0;
         super.beHitByBullet(var1);
         var1.damageValue = var3;
      }
   }

   public List<Rectangle> getHitRectangle() {
      this.hitRectangles.clear();
      boolean var1 = this.alienCannon.isCrashed();
      int var2 = 0;
      if(!var1) {
         this.hitRectangles.add(this.alienCannon.bounds);
         Enemy[] var9 = this.hitMap;
         int var10 = 0 + 1;
         var9[0] = this.alienCannon;
         var2 = var10;
      }

      int var3;
      if(!super.isCrashed() && this.alienCannon.isCrashed()) {
         this.hitRectangles.add(this.getHitR());
         Enemy[] var7 = this.hitMap;
         var3 = var2 + 1;
         var7[var2] = this;
      } else {
         var3 = var2;
      }

      while(var3 < 2) {
         Enemy[] var4 = this.hitMap;
         int var5 = var3 + 1;
         var4[var3] = null;
         var3 = var5;
      }

      return this.hitRectangles;
   }

   public float getTimeOfCrash() {
      return 0.1F;
   }

   public void render(SpriteBatch var1) {
      super.render(var1);
      this.alienCannon.render(var1);
   }

   public void update(float var1) {
      super.update(var1);
      if(this.clockPursue.isFired()) {
         float var2 = this.world.fighter.getPositionY() - this.alienCannon.getPositionY();
         float var3 = this.world.fighter.getPositionX() - this.alienCannon.getPositionX();
         float var4 = 90.0F + (float)(57.2957763671875D * Math.atan2((double)var2, (double)var3));
         if(var4 > -60.0F && var4 < 60.0F && !this.alienCannon.isCrashed()) {
            this.alienCannon.rotateTo(var4);
         }
      }

      this.alienCannon.update(var1);
   }

   public void updateDynamicParameter(float var1) {
      super.updateDynamicParameter(var1);
      this.alienCannon.setPosition(this.bounds.x + CANNONPOSITION.x, this.bounds.y + CANNONPOSITION.y);
   }

   public void updateShooting(float var1) {
      if(this.clockShootingCanon.isFired()) {
         this.alienCannon.shooting();
      }

   }

   protected boolean usePathAngle() {
      return false;
   }
}
