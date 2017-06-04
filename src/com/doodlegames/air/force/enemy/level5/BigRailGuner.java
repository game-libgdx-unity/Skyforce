package com.doodlegames.air.force.enemy.level5;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.level5.BigRailGunerD;
import com.doodlegames.air.force.enemy.level5.BigRailGunerU;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class BigRailGuner extends Enemy {

   public static final Vector2 BOTTOMPARTPOSITION = new Vector2((0.0F + (float)BigRailGunerD.WIDTH / 2.0F) / 1.0F, ((float)(-BigRailGunerD.HEIGHT) + (float)BigRailGunerD.HEIGHT / 2.0F) / 1.0F);
   public static final int DEFAULTHEALTHYDEGREE = 10;
   private static final int PARTSNUM = 2;
   private static final float TIMEOFPLAYCRASHANIMATION = 0.3F;
   public static final Vector2 UPPERPARTPOSITION = new Vector2((0.0F + (float)BigRailGunerU.WIDTH / 2.0F) / 1.0F, 800.0F + (float)BigRailGunerU.HEIGHT / 2.0F / 1.0F);
   private BigRailGunerD bigRailGunerD;
   private BigRailGunerU bigRailGunerU;
   private Clock clockModeChangle;
   private BigRailGuner.GunerState gs;
   private Enemy[] hitMap;
   private List<Rectangle> hitRectangles;


   public BigRailGuner(World var1, float var2, float var3) {
      super(var1, 10, var2, var3, (float)(BigRailGunerD.WIDTH + BigRailGunerU.WIDTH) / 2.0F / 1.0F, 800.0F);
      this.gs = BigRailGuner.GunerState.Invisible;
      this.clockModeChangle = new Clock(0.0F, 10.0F, Clock.ClockType.FireOnlyOnce);
      this.bigRailGunerD = new BigRailGunerD(var1, this.bounds.x + BOTTOMPARTPOSITION.x, this.bounds.y + BOTTOMPARTPOSITION.y);
      this.bigRailGunerU = new BigRailGunerU(var1, this.bounds.x + UPPERPARTPOSITION.x, this.bounds.y + UPPERPARTPOSITION.y);
      this.velocity.set(Settings.backgroundVelocity);
      this.hitMap = new Enemy[2];
      this.hitRectangles = new ArrayList(2);
   }

   public void beHitByBullet(Bullet var1, int var2) {
	   try {
      if(1 != this.state) {
         this.hitMap[var2].beHitByBullet(var1);
         int var3 = var1.damageValue;
         var1.damageValue = 0;
         super.beHitByBullet(var1);
         var1.damageValue = var3;
      }
	   }catch(Exception ex){}
   }

   public List<Rectangle> getHitRectangle() {
      this.hitRectangles.clear();
      BigRailGuner.GunerState var1 = this.gs;
      BigRailGuner.GunerState var2 = BigRailGuner.GunerState.Invisible;
      int var3 = 0;
      int var4;
      if(var1 != var2) {
         boolean var7 = this.bigRailGunerD.isCrashed();
         var3 = 0;
         if(!var7) {
            this.hitRectangles.add(this.bigRailGunerD.bounds);
            Enemy[] var11 = this.hitMap;
            int var12 = 0 + 1;
            var11[0] = this.bigRailGunerD;
            var3 = var12;
         }

         if(!this.bigRailGunerU.isCrashed()) {
            this.hitRectangles.add(this.bigRailGunerU.bounds);
            Enemy[] var9 = this.hitMap;
            var4 = var3 + 1;
            var9[var3] = this.bigRailGunerU;
         }
      }

      int var6;
      for(var4 = var3; var4 < 2; var4 = var6) {
         Enemy[] var5 = this.hitMap;
         var6 = var4 + 1;
         var5[var4] = null;
      }

      return this.hitRectangles;
   }

   public float getTimeOfCrash() {
      return 0.3F;
   }

   public boolean isCrashed() {
      boolean var1 = this.bigRailGunerD.isCrashed();
      boolean var2 = false;
      if(var1) {
         boolean var3 = this.bigRailGunerU.isCrashed();
         var2 = false;
         if(var3) {
            this.setInitHealthyDgree(0);
            var2 = true;
         }
      }

      return var2;
   }

   public void render(SpriteBatch var1) {
      if(this.gs != BigRailGuner.GunerState.Invisible) {
         this.bigRailGunerD.render(var1);
         this.bigRailGunerU.render(var1);
         if(this.gs == BigRailGuner.GunerState.PrepareToShoot) {
            TextureAtlas.AtlasRegion var2 = Assets_ShareInAllLevel.test_Bullet_2;
            int var3 = RegionUtilFunctions.getRegionWidth((TextureRegion)var2);
            int var4 = RegionUtilFunctions.getRegionHeight((TextureRegion)var2);
            var1.draw(var2, this.bounds.x + (float)BigRailGunerD.WIDTH / 2.0F / 1.0F - (float)var3 / 2.0F / 1.0F, this.bounds.y + (float)BigRailGunerD.HEIGHT / 1.0F - (float)var4 / 2.0F / 1.0F, 0.0F, 0.0F, (float)var3 / 1.0F, (float)var4 / 1.0F, 1.0F, 1.0F, 0.0F);
            return;
         }
      }

   }

   public void update(final float n) {

       super.update(n);

       switch (this.gs) {

           case Invisible: {

               if (this.position.y <= 400.0f) {

                   this.velocity.y = 0.0f;

                   this.position.y = 400.0f;

                   this.gs = GunerState.Ready;

                   this.clockModeChangle.resetClockFireOnce(1.0f);

                   break;

               }

               break;

           }

           case Ready: {

               if (this.clockModeChangle.isFired()) {

                   this.gs = GunerState.FlyingUp;

                   this.bigRailGunerD.velocity.y = (float)BigRailGunerD.HEIGHT / 1.0f / 1.0f;

                   this.bigRailGunerU.velocity.y = -this.bigRailGunerD.velocity.y;

                   break;

               }

               break;

           }

           case FlyingUp: {

               if (this.bigRailGunerD.bounds.y > 0.0f) {

                   this.bigRailGunerD.velocity.y = 0.0f;

                   this.bigRailGunerU.velocity.y = 0.0f;

                   this.clockModeChangle.resetClockFireOnce(3.0f);

                   this.gs = GunerState.PrepareToShoot;

                   break;

               }

               break;

           }

           case PrepareToShoot: {

               if (this.clockModeChangle.isFired()) {

                   this.clockModeChangle.resetClockFireOnce(6.0f);

                   this.gs = GunerState.Shoot;

                   break;

               }

               break;

           }

           case Shoot: {

               if (this.clockModeChangle.isFired()) {

                   this.bigRailGunerD.velocity.y = (float)((-BigRailGunerD.HEIGHT)) / 1.0f / 1.0f;

                   this.bigRailGunerU.velocity.y = -this.bigRailGunerD.velocity.y;

                   this.gs = GunerState.FlyingDown;

                   break;

               }

               break;

           }

           case FlyingDown: {

               if (this.bigRailGunerD.bounds.y < -this.bigRailGunerD.bounds.height) {

                   this.bigRailGunerD.velocity.y = 0.0f;

                   this.bigRailGunerU.velocity.y = 0.0f;

                   this.gs = GunerState.Ready;

                   this.clockModeChangle.resetClockFireOnce(1.0f);

                   break;

               }

               break;

           }

       }

       this.bigRailGunerD.update(n);

       this.bigRailGunerU.update(n);

   }

   public void updateDynamicParameter(float var1) {
      super.updateDynamicParameter(var1);
      if(this.gs == BigRailGuner.GunerState.Invisible || this.gs == BigRailGuner.GunerState.Ready) {
         this.bigRailGunerD.setPosition(this.bounds.x + BOTTOMPARTPOSITION.x, this.bounds.y + BOTTOMPARTPOSITION.y);
         this.bigRailGunerU.setPosition(this.bounds.x + UPPERPARTPOSITION.x, this.bounds.y + UPPERPARTPOSITION.y);
      }

   }

   public void updateShooting(float var1) {
      if(!this.bigRailGunerD.isCrashed() && !this.bigRailGunerU.isCrashed() && this.gs == BigRailGuner.GunerState.Shoot) {
         Enemy.bullets.add(EnemyBullet1.naziBulletGen.getABullet(this, this.world, this.bounds.x + (float)BigRailGunerD.WIDTH / 2.0F / 1.0F, this.bounds.y + (float)BigRailGunerD.HEIGHT / 1.0F, new Vector2(0.0F, 30.0F)));
      }
   }

   private static enum GunerState {

      FlyingDown,
      FlyingUp,
      Invisible,
      PrepareToShoot,
      Ready,
      Shoot,
   }
}
