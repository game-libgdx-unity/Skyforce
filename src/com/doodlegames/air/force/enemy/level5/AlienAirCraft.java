package com.doodlegames.air.force.enemy.level5;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.cannon.AlienCannon3L;
import com.doodlegames.air.force.enemy.cannon.AlienCannon3R;
import com.doodlegames.air.force.enemy.level4.ChildEnemyMoveActions;
import com.doodlegames.air.force.enemy.level5.AlienUfoA;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.utils.Clock;

import java.util.ArrayList;
import java.util.List;

public class AlienAirCraft extends Enemy {

   public static final Vector2 CANNONL1POSITION;
   public static final Vector2 CANNONL2POSITION;
   public static final Vector2 CANNONR1POSITION;
   public static final Vector2 CANNONR2POSITION;
   public static final int DEFAULTHEALTHYDEGREE = 20;
   private static int HEIGHT = 0;
   private static final Rectangle HITRECTANGLE;
   private static final int PARTSNUM = 5;
   private static final float TIMEOFPLAYCRASHANIMATION = 15.0F;
   public static final Vector2 UFOAPPEARPOSITION;
   private static final int WIDTH;
   private static TextureAtlas.AtlasRegion enemyBroTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_aircraft_broken");
   private static TextureAtlas.AtlasRegion enemyTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_aircraft");
   private AlienCannon3L alienCannonL1;
   private AlienCannon3L alienCannonL2;
   private AlienCannon3R alienCannonR1;
   private AlienCannon3R alienCannonR2;
   private Clock clockGenUFO = new Clock(1.5F);
   private Clock clockShootingCanon1 = new Clock(2.5F, 5.0F, 0.2F, (byte)1);
   private Clock clockShootingCanon2 = new Clock(0.0F, 5.0F, 0.2F, (byte)1);
   private Enemy[] hitMap = new Enemy[5];
   private Rectangle hitR;
   private List<Rectangle> hitRectangles = new ArrayList(5);


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
      HITRECTANGLE = new Rectangle(75.0F, 45.0F, 90.0F, 85.0F);
      CANNONL1POSITION = new Vector2((17.0F + (float)AlienCannon3L.canonTextureRegion.getRegionWidth() / 2.0F) / 1.0F, (104.0F + (float)AlienCannon3L.canonTextureRegion.getRegionHeight() / 2.0F) / 1.0F);
      CANNONL2POSITION = new Vector2((25.0F + (float)AlienCannon3L.canonTextureRegion.getRegionWidth() / 2.0F) / 1.0F, (62.0F + (float)AlienCannon3L.canonTextureRegion.getRegionHeight() / 2.0F) / 1.0F);
      CANNONR1POSITION = new Vector2((170.0F + (float)AlienCannon3R.canonTextureRegion.getRegionWidth() / 2.0F) / 1.0F, (104.0F + (float)AlienCannon3R.canonTextureRegion.getRegionHeight() / 2.0F) / 1.0F);
      CANNONR2POSITION = new Vector2((162.0F + (float)AlienCannon3R.canonTextureRegion.getRegionWidth() / 2.0F) / 1.0F, (62.0F + (float)AlienCannon3R.canonTextureRegion.getRegionHeight() / 2.0F) / 1.0F);
      UFOAPPEARPOSITION = new Vector2(119.0F, 11.0F);
   }

   public AlienAirCraft(World var1, float var2, float var3) {
      super(var1, 20, var2 + 0.5F, var3 + 0.5F, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.hitR = new Rectangle(HITRECTANGLE);
      this.hitR.x = this.bounds.x + HITRECTANGLE.x;
      this.hitR.y = this.bounds.y + HITRECTANGLE.y;
      this.alienCannonL1 = new AlienCannon3L(var1, this.bounds.x + CANNONL1POSITION.x, this.bounds.y + CANNONL1POSITION.y);
      this.alienCannonL2 = new AlienCannon3L(var1, this.bounds.x + CANNONL2POSITION.x, this.bounds.y + CANNONL2POSITION.y);
      this.alienCannonR1 = new AlienCannon3R(var1, this.bounds.x + CANNONR1POSITION.x, this.bounds.y + CANNONR1POSITION.y);
      this.alienCannonR2 = new AlienCannon3R(var1, this.bounds.x + CANNONR2POSITION.x, this.bounds.y + CANNONR2POSITION.y);
      this.alienCannonL1.setInitHealthyDgree(10);
      this.alienCannonL2.setInitHealthyDgree(10);
      this.alienCannonR1.setInitHealthyDgree(10);
      this.alienCannonR2.setInitHealthyDgree(10);
      this.alienCannonL1.velocity.set(0.0F, 0.0F);
      this.alienCannonL2.velocity.set(0.0F, 0.0F);
      this.alienCannonR1.velocity.set(0.0F, 0.0F);
      this.alienCannonR2.velocity.set(0.0F, 0.0F);
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyBroTextureRegion;
   }

   private Rectangle getHitR() {
      this.hitR.x = this.bounds.x + HITRECTANGLE.x;
      this.hitR.y = this.bounds.y + HITRECTANGLE.y;
      return this.hitR;
   }

   public static void loadResource() {
      enemyTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_aircraft");
      enemyBroTextureRegion = Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("alien_aircraft_broken");
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
      boolean var1 = this.alienCannonL1.isCrashed();
      int var2 = 0;
      if(!var1) {
         this.hitRectangles.add(this.alienCannonL1.bounds);
         Enemy[] var18 = this.hitMap;
         int var19 = 0 + 1;
         var18[0] = this.alienCannonL1;
         var2 = var19;
      }

      if(!this.alienCannonL2.isCrashed()) {
         this.hitRectangles.add(this.alienCannonL2.bounds);
         Enemy[] var15 = this.hitMap;
         int var16 = var2 + 1;
         var15[var2] = this.alienCannonL2;
         var2 = var16;
      }

      if(!this.alienCannonR1.isCrashed()) {
         this.hitRectangles.add(this.alienCannonR1.bounds);
         Enemy[] var12 = this.hitMap;
         int var13 = var2 + 1;
         var12[var2] = this.alienCannonR1;
         var2 = var13;
      }

      if(!this.alienCannonR2.isCrashed()) {
         this.hitRectangles.add(this.alienCannonR2.bounds);
         Enemy[] var9 = this.hitMap;
         int var10 = var2 + 1;
         var9[var2] = this.alienCannonR2;
         var2 = var10;
      }

      int var3;
      if(this.alienCannonL1.isCrashed() && this.alienCannonL2.isCrashed() && this.alienCannonR1.isCrashed() && this.alienCannonR2.isCrashed() && !super.isCrashed()) {
         this.hitRectangles.add(this.getHitR());
         Enemy[] var7 = this.hitMap;
         var3 = var2 + 1;
         var7[var2] = this;
      } else {
         var3 = var2;
      }

      while(var3 < 5) {
         Enemy[] var4 = this.hitMap;
         int var5 = var3 + 1;
         var4[var3] = null;
         var3 = var5;
      }

      return this.hitRectangles;
   }

   public float getTimeOfCrash() {
      return 15.0F;
   }

   public void renderEnemy(SpriteBatch var1) {
      super.renderEnemy(var1);
      this.alienCannonL1.render(var1);
      this.alienCannonL2.render(var1);
      this.alienCannonR1.render(var1);
      this.alienCannonR2.render(var1);
   }

   public void update(float var1) {
      super.update(var1);
      float var2 = this.world.fighter.getPositionY();
      boolean var3;
      if(this.bounds.y + UFOAPPEARPOSITION.y > 20.0F + var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      if(this.clockGenUFO.isFired() && var3 && !this.isCrashed()) {
         AlienUfoA var4 = AlienUfoA.getAlienUfoA(this.world, this.bounds.x + UFOAPPEARPOSITION.x, this.bounds.y + UFOAPPEARPOSITION.y);
         ChildEnemyMoveActions.addBezierPursue(var4, this.world.fighter);
         this.world.enemysOnAir.add(var4);
      }

      this.alienCannonL1.update(var1);
      this.alienCannonL2.update(var1);
      this.alienCannonR1.update(var1);
      this.alienCannonR2.update(var1);
   }

   public void updateDynamicParameter(float var1) {
      super.updateDynamicParameter(var1);
      this.alienCannonL1.setPosition(this.bounds.x + CANNONL1POSITION.x, this.bounds.y + CANNONL1POSITION.y);
      this.alienCannonL2.setPosition(this.bounds.x + CANNONL2POSITION.x, this.bounds.y + CANNONL2POSITION.y);
      this.alienCannonR1.setPosition(this.bounds.x + CANNONR1POSITION.x, this.bounds.y + CANNONR1POSITION.y);
      this.alienCannonR2.setPosition(this.bounds.x + CANNONR2POSITION.x, this.bounds.y + CANNONR2POSITION.y);
   }

   public void updateShooting(float var1) {
      if(this.clockShootingCanon1.isFired()) {
         this.alienCannonL1.shooting();
         this.alienCannonR1.shooting();
      }

      if(this.clockShootingCanon2.isFired()) {
         this.alienCannonL2.shooting();
         this.alienCannonR2.shooting();
      }

   }

   protected boolean usePathAngle() {
      return false;
   }
}
