package com.doodlegames.air.force.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {

   protected float angle = 0.0F;
   public final Rectangle bounds;
   public final Vector2 origin;
   public final Vector2 position;


   public GameObject(float var1, float var2, float var3, float var4) {
      this.position = new Vector2(var1, var2);
      this.bounds = new Rectangle(var1 - var3 / 2.0F, var2 - var4 / 2.0F, var3, var4);
      this.origin = new Vector2(this.bounds.width / 2.0F, this.bounds.height / 2.0F);
   }

   public void PositionAdd(float var1, float var2) {
      float var3 = this.position.x;
      float var4 = this.position.y;
      this.setPosition(var3 + var1, var4 + var2);
   }

   public float getAngle() {
      return this.angle;
   }

   public float getOriginX() {
      return this.origin.x;
   }

   public float getOriginY() {
      return this.origin.y;
   }

   public float getPositionX() {
      return this.position.x;
   }

   public float getPositionY() {
      return this.position.y;
   }

   public abstract void render(SpriteBatch var1);

   public void renderShadow(SpriteBatch var1) {}

   public void setAngle(float var1) {
      this.angle = var1;
   }

   public void setBounds(float var1, float var2, float var3, float var4) {
      this.bounds.x = var1;
      this.bounds.y = var2;
      this.bounds.width = var3;
      this.bounds.height = var4;
      this.position.x = this.bounds.x + this.bounds.width / 2.0F;
      this.position.y = this.bounds.y + this.bounds.height / 2.0F;
   }

   public void setBoundsHeight(float var1) {
      this.bounds.height = var1;
      this.bounds.y = this.position.y - this.bounds.height / 2.0F;
   }

   public void setBoundsWidth(float var1) {
      this.bounds.width = var1;
      this.bounds.x = this.position.x - this.bounds.width / 2.0F;
   }

   public void setPosition(float var1, float var2) {
      this.position.x = var1;
      this.position.y = var2;
      this.bounds.x = this.position.x - this.bounds.width / 2.0F;
      this.bounds.y = this.position.y - this.bounds.height / 2.0F;
   }

   public abstract void update(float var1);
}
