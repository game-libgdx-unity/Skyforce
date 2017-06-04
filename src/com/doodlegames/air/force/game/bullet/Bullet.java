package com.doodlegames.air.force.game.bullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.utils.RegionUtilFunctions;

public abstract class Bullet extends DynamicGameObject {

   public static final int BOOMBULLET = 1;
   public static final int COMMONBULLET = 0;
   private static int DEFALUTDAMAGEVALUE = 1;
   public static final int FIGHTERHIT = 3;
   public static final int LASEBULLET = 2;
   public int bulletType = 0;
   public int damageValue;
   protected boolean isUsed;
   protected GameObject owner;


   public Bullet(GameObject var1, float var2, float var3, float var4, float var5, Vector2 var6) {
      super(var2, var3, var4, var5);
      this.initBullet(var1, var6);
   }

   private void initBullet(GameObject var1, Vector2 var2) {
      this.owner = var1;
      this.damageValue = DEFALUTDAMAGEVALUE;
      this.isUsed = false;
      this.velocity.set(var2);
   }

   public abstract void freeToPool();

   public Rectangle getHitRec() {
      return this.bounds;
   }

   public GameObject getOwner() {
      return this.owner;
   }

   public float getVelX() {
      return this.velocity.x;
   }

   public float getVelY() {
      return this.velocity.y;
   }

   public void hitSomething() {
      this.isUsed = true;
   }

   public boolean isAvalible() {
      return !this.isUsed;
   }

   public void makeUsed() {
      this.isUsed = true;
   }

   protected void render(SpriteBatch var1, TextureRegion var2) {
      if(!this.isUsed) {
         RegionUtilFunctions.draw(var1, var2, this.bounds.x, this.bounds.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, 1.0F, 1.0F, this.angle);
      }
   }

   public void reset(GameObject var1, float var2, float var3, float var4, float var5, Vector2 var6) {
      this.setPosition(var2, var3);
      this.velocity.set(0.0F, 0.0F);
      this.setBoundsWidth(var4);
      this.setBoundsHeight(var5);
      this.angle = 0.0F;
      this.angularSpeed = 0.0F;
      this.accel.x = 0.0F;
      this.accel.y = 0.0F;
      this.initBullet(var1, var6);
      this.bulletType = 0;
      this.clearGameObjectActions();
   }

   public void update(float var1) {
      super.update(var1);
   }
}
