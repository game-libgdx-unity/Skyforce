package com.doodlegames.air.force.game.laser;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.laser.LaserShooter;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;

public abstract class Laser extends DynamicGameObject {

   private static final int DEFAULTDAMAGEVALUE = 1;
   protected Vector2 currentShootPosition;
   private Clock damageClock = new Clock(0.1F);
   private int damageValue = 1;
   private float deltaY;
   private final float initWidth;
   protected GameObject owner;
   protected TextureRegion region = null;
   protected LaserShooter shooter;


   public Laser(GameObject var1, LaserShooter var2, float var3, float var4, float var5) {
      super(var2.getShootingPoint().x, var3 + var2.getShootingPoint().y, var4, var5);
      this.deltaY = var3;
      this.initWidth = var4;
      this.owner = var1;
      this.shooter = var2;
      this.currentShootPosition = new Vector2(var2.getShootingPoint());
   }

   public int getDamageValue() {
      return this.shooter.getAlpha() >= 0.5F && this.damageClock.isFired(0)?this.damageValue:0;
   }

   public float getDeltaY() {
      return this.deltaY;
   }

   protected TextureRegion getLaserRegion() {
      return this.region;
   }

   public void hitSomething() {}

   public boolean isAvalible() {
      return this.shooter.getAlpha() > 0.5F && !this.shooter.shouldRemove();
   }

   public void render(SpriteBatch var1) {
      if(!this.shooter.laseSleep()) {
         TextureRegion var2 = this.getLaserRegion();
         if(var2 != null) {
            Color var3 = var1.getColor();
            float var4 = this.shooter.getAlpha();
            if(var4 >= 1.0E-4F) {
               var1.setColor(1.0F, 1.0F, 1.0F, var4);
               this.origin.set(this.bounds.width / 2.0F, this.bounds.height / 2.0F);
               RegionUtilFunctions.draw(var1, var2, this.bounds.x, this.bounds.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, 1.0F, 1.0F, this.angle);
               var1.setColor(var3);
               return;
            }
         }
      }

   }

   public void setDeltaY(float var1) {
      this.deltaY = var1;
      Vector2.tmp.set(0.0F, var1).rotate(this.shooter.getLaserAngle()).add(this.shooter.getShootingPoint());
      this.setPosition(Vector2.tmp.x, Vector2.tmp.y);
      this.currentShootPosition.set(this.shooter.getShootingPoint());
      this.angle = this.shooter.getLaserAngle();
   }

   public void setLaserShoot(LaserShooter var1) {
      this.shooter = var1;
   }

   public boolean shouldRemove() {
      return this.shooter.shouldRemove();
   }

   public void update(float var1) {
      if(!this.shooter.laseSleep()) {
         float var2 = this.shooter.getLaserWidth();
         if(var2 > 0.0F) {
            this.setBoundsWidth(var2);
         } else {
            this.setBoundsWidth(this.initWidth);
         }

         float var3 = this.shooter.getLaserAngle();
         Vector2 var4 = this.shooter.getShootingPoint();
         if(this.currentShootPosition.x != var4.x || this.currentShootPosition.y != var4.y) {
            this.position.add(var4.x - this.currentShootPosition.x, var4.y - this.currentShootPosition.y);
            this.currentShootPosition.set(var4);
         }

         if(this.angle != var3) {
            Vector2.tmp.set(0.0F, this.deltaY).rotate(var3).add(var4);
            this.setPosition(Vector2.tmp.x, Vector2.tmp.y);
            this.angle = var3;
         }

         super.update(var1);
      }
   }
}
