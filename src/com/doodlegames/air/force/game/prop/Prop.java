package com.doodlegames.air.force.game.prop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldParticleEffect;
import com.doodlegames.air.force.game.prop.PropGenerator;
import com.doodlegames.air.force.game.prop.propFloatingPath.CircleFloating;
import com.doodlegames.air.force.game.prop.propFloatingPath.PropFloating;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public abstract class Prop extends DynamicGameObject {

   public static final float ATTRACTINGTIMEMAX = 4.0F;
   public static final float FADEOUTTIME = 3.0F;
   public static final float FLYINGTIME = 8.0F;
   public static final float FLYINGVEL = 80.0F;
   public static List<Prop> propSet = new ArrayList();
   protected final boolean canBeAttracked;
   public float fadeOutTime = 3.0F;
   public float flyingTime = 8.0F;
   protected List<Rectangle> myHitRectangles = new ArrayList(1);
   protected GameObject owner;
   protected Animation propAnimation;
   protected PropFloating propFloating;
   protected Prop.PropState propstate;
   protected float stateTime;
   protected World world;


   public Prop(GameObject var1, World var2, float var3, float var4, float var5, float var6, boolean var7) {
      super(var3, var4, var5, var6);
      this.canBeAttracked = var7;
      this.initProp(var1, var2);
   }

   public static void dropProp(PropGenerator var0, float var1, Enemy var2) {
      if(var1 < 0.0F || var1 > 1.0F) {
         Settings.w("Invalid prop dropping probability:" + var1);
         var1 = 0.0F;
      }

      if(var2.world.rand.nextFloat() <= var1) {
         propSet.add(var0.getAProp(var2, var2.world, var2.getPositionX(), var2.getPositionY()));
      }

   }

   public static void dropProp(PropGenerator var0, float var1, Enemy var2, float var3, float var4) {
      if(var1 < 0.0F || var1 > 1.0F) {
         Settings.w("Invalid prop dropping probability:" + var1);
         var1 = 0.0F;
      }

      if(var2.world.rand.nextFloat() <= var1) {
         propSet.add(var0.getAProp(var2, var2.world, var3, var4));
      }

   }

   private void enterPickUpState() {
      this.propstate = Prop.PropState.Picking;
      this.stateTime = 0.0F;
   }

   private void gotoRemoveState() {
      this.stateTime = 0.0F;
      this.propstate = Prop.PropState.Removed;
      this.propFloating.stopFloating();
   }

   private void initProp(GameObject var1, World var2) {
      this.owner = var1;
      this.world = var2;
      this.propAnimation = null;
      this.stateTime = 0.0F;
      this.propstate = Prop.PropState.Flying;
      this.myHitRectangles.clear();
      this.myHitRectangles.add(this.bounds);
      this.flyingTime = 8.0F;
      this.fadeOutTime = 3.0F;
   }

   private boolean judgeAttracked() {
      if(!this.world.checkEnemyClear()) {
         float var1 = this.getPositionX() - this.world.fighter.getPositionX();
         float var2 = this.getPositionY() - this.world.fighter.getPositionY();
         float var3 = var1 * var1 + var2 * var2;
         float var4 = this.world.fighter.getAttrackedRadis();
         if(var3 >= var4 * var4) {
            return false;
         }
      }

      return true;
   }

   public void beHitByFighter(Fighter var1) {
      if(this.propstate != Prop.PropState.Picking && this.propstate != Prop.PropState.Removed) {
         if(!this.exeutePropEffect(var1)) {
            this.world.firePropUnuseEvent(this);
         }

         this.enterPickUpState();
      } else {
         Settings.appLog("invalid propState, " + this.propstate + " should appear in function beHitByFighter");
      }
   }

   protected void drawRegion(SpriteBatch var1, TextureRegion var2) {
      if(var2 != null) {
         if(!(var2 instanceof TextureAtlas.AtlasRegion)) {
            var1.draw(var2, this.bounds.x, this.bounds.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, 1.0F, 1.0F, this.angle);
            return;
         }

         var1.draw((TextureAtlas.AtlasRegion)TextureAtlas.AtlasRegion.class.cast(var2), this.bounds.x, this.bounds.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, 1.0F, 1.0F, this.angle);
      }

   }

   protected abstract boolean exeutePropEffect(Fighter var1);

   public abstract void freeToPool();

   public List<Rectangle> getHitRectangle() {
      if(this.propstate != Prop.PropState.Removed && this.propstate != Prop.PropState.Picking) {
         return this.myHitRectangles;
      } else {
         Enemy.EMPTYRECTANGELS.clear();
         return Enemy.EMPTYRECTANGELS;
      }
   }

   public Prop.PropState getState() {
      return this.propstate;
   }

   public void render(final SpriteBatch spriteBatch) {

       if (this.propstate == PropState.Removed) {

           return;

       }

       if (this.propAnimation == null) {

           Settings.appLog("PropAnimation is null.");

           return;

       }

       final Color color = spriteBatch.getColor();

       float a = color.a;

       TextureRegion keyFrame = this.propAnimation.getKeyFrame(this.stateTime);

       Label_0091:

       {

           switch (this.propstate) {

               case Flying:

               case Picking: {

                   spriteBatch.setColor(color.r, color.g, color.b, a);

                   this.drawRegion(spriteBatch, keyFrame);

                   spriteBatch.setColor(color);

                   return;

               }

               case Disappearing:

               case Attracking: {

                   a = a * ((this.fadeOutTime - this.stateTime) / this.fadeOutTime);

                   break Label_0091;

               }

               default: {

                   keyFrame = null;

                   break Label_0091;

               }

           }

       }

   }

   

   public void renderShadow(final SpriteBatch spriteBatch) {

       if (this.propstate == PropState.Removed) {

           return;

       }

       if (this.propAnimation == null) {

           Settings.appLog("PropAnimation is null.");

           return;

       }

       final Color color = spriteBatch.getColor();

       float n = 0.5f * color.a;

       TextureRegion keyFrame = this.propAnimation.getKeyFrame(this.stateTime);

       Label_0095:

       {

           switch (this.propstate) {

               case Flying:

               case Picking: {

                   spriteBatch.setColor(0.0f, 0.0f, 0.0f, n);

                   if (keyFrame != null) {

                       if (keyFrame instanceof TextureAtlas.AtlasRegion) {

                           spriteBatch.draw((TextureAtlas.AtlasRegion)TextureAtlas.AtlasRegion.class.cast(keyFrame), this.bounds.x + DynamicGameObject.shadowOffset.x, this.bounds.y + DynamicGameObject.shadowOffset.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, DynamicGameObject.shadowScale.x, DynamicGameObject.shadowScale.y, this.angle);

                       }

                       else {

                           spriteBatch.draw(keyFrame, this.bounds.x + DynamicGameObject.shadowOffset.x, this.bounds.y + DynamicGameObject.shadowOffset.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, DynamicGameObject.shadowScale.x, DynamicGameObject.shadowScale.y, this.angle);

                       }

                   }

                   spriteBatch.setColor(color);

                   return;

               }

               case Disappearing:

               case Attracking: {

                   n = n * ((this.fadeOutTime - this.stateTime) / this.fadeOutTime);

                   break Label_0095;

               }

               default: {

                   keyFrame = null;

                   break Label_0095;

               }

           }

       }

   }

   

   public void reset(final GameObject gameObject, final World world, final float n, final float n2, final float boundsWidth, final float boundsHeight) {

       this.setPosition(n, n2);

       this.setBoundsWidth(boundsWidth);

       this.setBoundsHeight(boundsHeight);

       this.initProp(gameObject, world);

       if (this.propFloating == null) {

           this.propFloating = new CircleFloating(world.rand, this);

           Settings.appLog("subClass of Prop must assign a value to this.propFloating!");

       }

       this.propFloating.beginFloating();

   }

   

   public void update(final float n) {

       super.update(n);

       this.stateTime = n + this.stateTime;

       this.propFloating.updatePropFloating(n);

       switch (this.propstate) {

           case Flying: {

               if (this.canBeAttracked && this.propFloating.canBeAttracked() && this.judgeAttracked()) {

                   this.propFloating.beAttrackedByFighter(this.world.fighter);

                   this.propstate = PropState.Attracking;

                   this.stateTime = 0.0f;

                   return;

               }

               if (this.stateTime > this.flyingTime) {

                   this.propstate = PropState.Disappearing;

                   this.stateTime = 0.0f;

                   return;

               }

               break;

           }

           case Disappearing: {

               if (this.canBeAttracked && this.propFloating.canBeAttracked() && this.judgeAttracked()) {

                   this.propFloating.beAttrackedByFighter(this.world.fighter);

                   this.propstate = PropState.Attracking;

                   this.stateTime = 0.0f;

                   return;

               }

               if (this.stateTime > this.fadeOutTime) {

                   this.gotoRemoveState();

                   return;

               }

               break;

           }

           case Attracking: {

               if (this.stateTime > 4.0f) {

                   this.gotoRemoveState();

                   return;

               }

               break;

           }

           case Picking: {

               WorldParticleEffect.addPropParticle(this.getPositionX(), this.getPositionY());

               this.gotoRemoveState();

               return;

           }

       }

   }

   public static enum PropState {

      Attracking,
      Disappearing,
      Flying,
      Picking,
      Removed
   }
}
