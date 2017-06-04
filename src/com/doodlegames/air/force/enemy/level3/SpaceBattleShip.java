package com.doodlegames.air.force.enemy.level3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.cannon.Cannon1;
import com.doodlegames.air.force.enemy.cannon.Cannon3;
import com.doodlegames.air.force.enemy.cannon.RailGun;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.resource.Assets_level3;
import com.doodlegames.air.force.utils.Clock;

import java.util.ArrayList;
import java.util.List;

public class SpaceBattleShip extends Enemy {

   public static final Vector2 CANNON11POSITION;
   public static final Vector2 CANNON12POSITION;
   public static final Vector2 CANNON3POSITION;
   public static final Vector2 CANNONRAILGUNPOSITION;
   public static final int DEFAULTHEALTHYDEGREE = 10;
   private static final int PARTSNUM = 4;
   private static final float PROPELLERANGLESPEED = 720.0F;
   private static int PROPELLERHEIGHT = 0;
   private static final Vector2 PROPELLERORGIN;
   private static int PROPELLERWIDTH = 0;
   public static final Vector2 PROPERLLER1POSITION;
   public static final Vector2 PROPERLLER2POSITION;
   private static final float TIMEOFPLAYCRASHANIMATION = 25.0F;
   private static TextureAtlas.AtlasRegion enemyBroTextureRegion = Assets_level3.atlas_Enemy.findRegion("nazi_space_battleship_broken");
   private static TextureAtlas.AtlasRegion enemyTextureRegion = Assets_level3.atlas_Enemy.findRegion("nazi_space_battleship");
   private static int height;
   private static TextureAtlas.AtlasRegion propellerTextureRegion = Assets_level3.atlas_Enemy.findRegion("nazi_space_battleship_propeller");
   private static int width;
   private Cannon1 cannon11;
   private Cannon1 cannon12;
   private Cannon3 cannon3;
   private Clock clockPursue = new Clock(1.5F);
   private Clock clockShootingCanon11 = new Clock(5.0F, 5.0F, 0.2F, (byte)1);
   private Clock clockShootingCanon12 = new Clock(4.5F, 5.0F, 0.2F, (byte)1);
   private Clock clockShootingCanon3 = new Clock(2.25F, 5.0F, 0.2F, (byte)1);
   private Clock clockShootingRailGun = new Clock(0.0F, 16.0F);
   private Clock clockTurning = new Clock(4.0F);
   private Enemy[] hitMap = new Enemy[4];
   private List<Rectangle> hitRectangles = new ArrayList(4);
   private float propellerAngle;
   private RailGun railGun;


   static {
      int var0;
      if(enemyTextureRegion.rotate) {
         var0 = enemyTextureRegion.getRegionHeight();
      } else {
         var0 = enemyTextureRegion.getRegionWidth();
      }

      width = var0;
      int var1;
      if(enemyTextureRegion.rotate) {
         var1 = enemyTextureRegion.getRegionWidth();
      } else {
         var1 = enemyTextureRegion.getRegionHeight();
      }

      height = var1;
      PROPELLERWIDTH = propellerTextureRegion.getRegionWidth();
      PROPELLERHEIGHT = propellerTextureRegion.getRegionHeight();
      PROPELLERORGIN = new Vector2((float)PROPELLERWIDTH / 2.0F / 1.0F, (float)PROPELLERHEIGHT / 2.0F / 1.0F);
      CANNON11POSITION = new Vector2((65.0F + (float)Cannon1.canonTextureRegion.getRegionWidth() / 2.0F) / 1.0F, (220.0F + (float)Cannon1.canonTextureRegion.getRegionHeight() / 2.0F) / 1.0F);
      CANNON12POSITION = new Vector2((65.0F + (float)Cannon1.canonTextureRegion.getRegionWidth() / 2.0F) / 1.0F, (256.0F + (float)Cannon1.canonTextureRegion.getRegionHeight() / 2.0F) / 1.0F);
      CANNON3POSITION = new Vector2((61.0F + (float)Cannon3.canonTextureRegion.getRegionWidth() / 2.0F) / 1.0F, (80.0F + (float)Cannon3.canonTextureRegion.getRegionHeight() / 2.0F) / 1.0F);
      CANNONRAILGUNPOSITION = new Vector2((62.0F + (float)RailGun.WIDTH / 2.0F) / 1.0F, (-11.0F + (float)RailGun.HEIGHT / 2.0F) / 1.0F);
      PROPERLLER1POSITION = new Vector2(15.0F, 334.0F);
      PROPERLLER2POSITION = new Vector2(103.0F, 334.0F);
   }

   public SpaceBattleShip(World var1, float var2, float var3) {
      super(var1, 10, var2 + 0.5F, var3, (float)width / 1.0F, (float)height / 1.0F);
      this.cannon11 = new Cannon1(var1, this.bounds.x + CANNON11POSITION.x, this.bounds.y + CANNON11POSITION.y);
      this.cannon12 = new Cannon1(var1, this.bounds.x + CANNON12POSITION.x, this.bounds.y + CANNON12POSITION.y);
      this.cannon3 = new Cannon3(var1, this.bounds.x + CANNON3POSITION.x, this.bounds.y + CANNON3POSITION.y);
      this.cannon3.angularSpeed = 90.0F;
      this.railGun = new RailGun(var1, this.bounds.x + CANNONRAILGUNPOSITION.x, this.bounds.y + CANNONRAILGUNPOSITION.y);
      this.cannon11.setInitHealthyDgree(10);
      this.cannon12.setInitHealthyDgree(10);
      this.cannon3.setInitHealthyDgree(10);
      this.railGun.setInitHealthyDgree(20);
      this.cannon11.setThisVelBullet(0.0F, -300.0F);
      this.cannon12.setThisVelBullet(0.0F, -300.0F);
      this.cannon3.setBulletSpeed(200.0F);
      this.propellerAngle = 0.0F;
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyBroTextureRegion;
   }

   public static void loadResource() {
      enemyTextureRegion = Assets_level3.atlas_Enemy.findRegion("nazi_space_battleship");
      enemyBroTextureRegion = Assets_level3.atlas_Enemy.findRegion("nazi_space_battleship_broken");
      propellerTextureRegion = Assets_level3.atlas_Enemy.findRegion("nazi_space_battleship_propeller");
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

   public void enemyRemoved() {
      this.railGun.enemyRemoved();
   }

   public List<Rectangle> getHitRectangle() {
      this.hitRectangles.clear();
      boolean var1 = this.cannon11.isCrashed();
      int var2 = 0;
      if(!var1) {
         this.hitRectangles.add(this.cannon11.bounds);
         Enemy[] var15 = this.hitMap;
         int var16 = 0 + 1;
         var15[0] = this.cannon11;
         var2 = var16;
      }

      if(!this.cannon12.isCrashed()) {
         this.hitRectangles.add(this.cannon12.bounds);
         Enemy[] var12 = this.hitMap;
         int var13 = var2 + 1;
         var12[var2] = this.cannon12;
         var2 = var13;
      }

      if(!this.cannon3.isCrashed()) {
         this.hitRectangles.add(this.cannon3.bounds);
         Enemy[] var9 = this.hitMap;
         int var10 = var2 + 1;
         var9[var2] = this.cannon3;
         var2 = var10;
      }

      int var3;
      if(!this.railGun.isCrashed()) {
         this.hitRectangles.add(this.railGun.getHitR());
         Enemy[] var7 = this.hitMap;
         var3 = var2 + 1;
         var7[var2] = this.railGun;
      } else {
         var3 = var2;
      }

      while(var3 < 4) {
         Enemy[] var4 = this.hitMap;
         int var5 = var3 + 1;
         var4[var3] = null;
         var3 = var5;
      }

      return this.hitRectangles;
   }

   public float getTimeOfCrash() {
      return 25.0F;
   }

   public boolean isCrashed() {
      boolean var1 = this.cannon11.isCrashed();
      boolean var2 = false;
      if(var1) {
         boolean var3 = this.cannon12.isCrashed();
         var2 = false;
         if(var3) {
            boolean var4 = this.cannon3.isCrashed();
            var2 = false;
            if(var4) {
               boolean var5 = this.railGun.isCrashed();
               var2 = false;
               if(var5) {
                  this.setInitHealthyDgree(0);
                  var2 = true;
               }
            }
         }
      }

      return var2;
   }

   public void render(SpriteBatch var1) {
      var1.draw(propellerTextureRegion, this.bounds.x + PROPERLLER1POSITION.x, this.bounds.y + PROPERLLER1POSITION.y, PROPELLERORGIN.x, PROPELLERORGIN.y, (float)PROPELLERWIDTH / 1.0F, (float)PROPELLERHEIGHT / 1.0F, 1.0F, 1.0F, this.propellerAngle);
      var1.draw(propellerTextureRegion, this.bounds.x + PROPERLLER2POSITION.x, this.bounds.y + PROPERLLER2POSITION.y, PROPELLERORGIN.x, PROPELLERORGIN.y, (float)PROPELLERWIDTH / 1.0F, (float)PROPELLERHEIGHT / 1.0F, 1.0F, 1.0F, this.propellerAngle);
      super.render(var1);
      this.cannon11.render(var1);
      this.cannon12.render(var1);
      this.cannon3.render(var1);
      this.railGun.render(var1);
   }

   public void update(float var1) {
      super.update(var1);
      if(this.clockPursue.isFired()) {
         float var2 = this.world.fighter.getPositionY() - (this.cannon11.getPositionY() + this.cannon12.getPositionY()) / 2.0F;
         float var3 = this.world.fighter.getPositionX() - (this.cannon11.getPositionX() + this.cannon12.getPositionX()) / 2.0F;
         if(var2 < 0.0F) {
            float var4 = 90.0F + (float)(57.2957763671875D * Math.atan2((double)var2, (double)var3));
            if(!this.cannon11.isCrashed()) {
               this.cannon11.rotateTo(var4);
            }

            if(!this.cannon12.isCrashed()) {
               this.cannon12.rotateTo(var4);
            }
         }
      }

      this.cannon11.update(var1);
      this.cannon12.update(var1);
      this.cannon3.update(var1);
      this.railGun.update(var1);
      this.propellerAngle += 720.0F * var1;
      if(this.clockTurning.isFired()) {
         this.cannon3.angularSpeed = -this.cannon3.angularSpeed;
      }

      if(this.cannon3.isCrashed()) {
         this.cannon3.angularSpeed = 0.0F;
      }

   }

   public void updateDynamicParameter(float var1) {
      super.updateDynamicParameter(var1);
      this.cannon11.setPosition(this.bounds.x + CANNON11POSITION.x, this.bounds.y + CANNON11POSITION.y);
      this.cannon12.setPosition(this.bounds.x + CANNON12POSITION.x, this.bounds.y + CANNON12POSITION.y);
      this.cannon3.setPosition(this.bounds.x + CANNON3POSITION.x, this.bounds.y + CANNON3POSITION.y);
      this.railGun.setPosition(this.bounds.x + CANNONRAILGUNPOSITION.x, this.bounds.y + CANNONRAILGUNPOSITION.y);
   }

   public void updateShooting(float var1) {
      if(this.clockShootingCanon11.isFired()) {
         this.cannon11.shooting();
      }

      if(this.clockShootingCanon12.isFired()) {
         this.cannon12.shooting();
      }

      if(this.clockShootingCanon3.isFired()) {
         this.cannon3.shooting();
      }

      if(this.clockShootingRailGun.isFired()) {
         this.railGun.shooting();
      }

   }

   protected boolean usePathAngle() {
      return false;
   }
}
