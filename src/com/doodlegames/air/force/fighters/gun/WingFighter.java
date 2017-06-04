package com.doodlegames.air.force.fighters.gun;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.fighters.gun.WingFighterGun;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.path.WingFighterPath;
import com.doodlegames.air.force.resource.Assets_Props;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;
import com.doodlegames.air.force.utils.Settings;

public class WingFighter extends DynamicGameObject {

   private final Fighter fighter;
   private WingFighterGun gun;
   private float height;
   private Animation leftAnimation;
   private Animation rightAnimation;
   private float width;
   private Animation wingAnimation;
   private WingFighterPath wingPath;
   private Vector2 wingRelativeXY;


   public WingFighter(Fighter var1) {
      super(var1.getPositionX(), var1.getPositionY(), var1.bounds.width, var1.bounds.height);
      this.fighter = var1;
      this.wingRelativeXY = new Vector2();
      this.wingPath = new WingFighterPath(this, this.fighter);
      this.initWingFighter(var1);
      this.gun = new WingFighterGun(this, var1.world);
   }

   private void initWingFighter(Fighter var1) {
      this.setWingAnimation(Assets_Props.wingMan1, Assets_Props.wingMan1Left, Assets_Props.wingMan1Right);
      this.wingPath.beginTravel();
      if(this.gun != null) {
         this.gun.reset();

         for(int var3 = this.getLevel(); var3 < Settings.wingEffectLevel; ++var3) {
            this.increaseLevel();
         }
      }

   }

   private void setWingAnimation(Animation var1, Animation var2, Animation var3) {
      if(var1 != null) {
         this.wingAnimation = var1;
         this.leftAnimation = var2;
         this.rightAnimation = var3;
         this.width = (float)RegionUtilFunctions.getRegionWidth(this.wingAnimation.getKeyFrame(0.0F)) / 1.0F;
         this.height = (float)RegionUtilFunctions.getRegionHeight(this.wingAnimation.getKeyFrame(0.0F)) / 1.0F;
         this.wingRelativeXY.x = -(this.width - this.fighter.bounds.width) / 2.0F;
         this.wingRelativeXY.y = 0.0F;
      }
   }

   public int getLevel() {
      return this.gun.getLevel();
   }

   public float getWingHeight() {
      return this.height;
   }

   public Vector2 getWingRelativeXY() {
      return this.wingRelativeXY;
   }

   public float getWingWidth() {
      return this.width;
   }

   public void increaseLevel() {
      this.gun.increaseLevel();
      switch(this.gun.getLevel()) {
      case 0:
      case 1:
      case 2:
      case 3:
         this.setWingAnimation(Assets_Props.wingMan1, Assets_Props.wingMan1Left, Assets_Props.wingMan1Right);
         return;
      case 4:
      case 5:
         this.setWingAnimation(Assets_Props.wingMan2, Assets_Props.wingMan2Left, Assets_Props.wingMan2Right);
         return;
      default:
      }
   }

   public void render(SpriteBatch var1) {
      TextureRegion var2 = Fighter.getKeyFrame(this.fighter, this.leftAnimation, this.rightAnimation, this.wingAnimation);
      float var3 = this.width / 2.0F;
      float var4 = this.height / 2.0F;
      float var5 = 1.0F;
      if(this.fighter.isCrashed()) {
         var5 *= 1.0F - this.fighter.stateTime / this.fighter.getTimeOfCrash();
      }

      RegionUtilFunctions.draw(var1, var2, this.bounds.x + this.wingRelativeXY.x, this.bounds.y + this.wingRelativeXY.y, var3, var4, this.width, this.height, var5, var5, 0.0F);
   }

   public void renderShadow(SpriteBatch var1) {
      TextureRegion var2 = this.wingAnimation.getKeyFrame((float)Clock.getTimeCounter());
      float var3 = this.width / 2.0F;
      float var4 = this.height / 2.0F;
      float var5 = 1.0F;
      if(this.fighter.isCrashed()) {
         var5 *= 1.0F - this.fighter.stateTime / this.fighter.getTimeOfCrash();
      }

      float var6 = this.bounds.x + this.wingRelativeXY.x;
      float var7 = this.bounds.y + this.wingRelativeXY.y;
      RegionUtilFunctions.drawShadow(var1, var2, 0.5F, var6 + DynamicGameObject.shadowOffset.x, var7 + DynamicGameObject.shadowOffset.y, var3, var4, this.width, this.height, var5 * DynamicGameObject.shadowScale.x, var5 * DynamicGameObject.shadowScale.y, 0.0F);
   }

   public void reset(float var1, float var2) {
      this.setPosition(var1, var2);
      this.wingPath.stopTravel();
      this.initWingFighter(this.fighter);
   }

   public void update(float var1) {
      super.update(var1);
      this.wingPath.updateTravel(var1);
      if(!this.fighter.isCrashed() && !this.fighter.world.getStopFighting()) {
         this.gun.updateShooting(var1);
      }

   }
}
