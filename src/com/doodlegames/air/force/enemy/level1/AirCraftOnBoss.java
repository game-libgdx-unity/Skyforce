package com.doodlegames.air.force.enemy.level1;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.level1.Ufo_b;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldAnimationPlayer;
import com.doodlegames.air.force.resource.Assets_level1;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class AirCraftOnBoss extends Enemy {

    private static final int DEFAULTHEALTHYDEGREE = 20;
   public static final float EXITPORTX = 62.0F;
   public static final float EXITPORTY = 29.0F;
   public static  int HEIGHT = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 0.25F;
   public static final int WIDTH;
   private static TextureAtlas.AtlasRegion enemyBroFlipRegion;
   private static TextureAtlas.AtlasRegion enemyBroTextRegion = Assets_level1.findRegion("boss_aircraft_broken");
   private static TextureAtlas.AtlasRegion enemyFlipRegion;
   private static TextureAtlas.AtlasRegion enemyTextureRegion = Assets_level1.findRegion("boss_aircraft");
   private Vector2 angleOffset = new Vector2();
   private Clock clockShooting;
   private ArrayList<Rectangle> hitRecs;
   private final float initAngle;
   private Enemy parent;
   private final Vector2 relative;
   public static final int ATLASHEIGHT = enemyTextureRegion.getRegionHeight();
   public static final int ATLASWIDTH = enemyTextureRegion.getRegionWidth();
 

   static {
      int var0;
      if(enemyTextureRegion.rotate) {
         var0 = ATLASHEIGHT;
      } else {
         var0 = ATLASWIDTH;
      }

      WIDTH = var0;
      int var1;
      if(enemyTextureRegion.rotate) {
         var1 = ATLASWIDTH;
      } else {
         var1 = ATLASHEIGHT;
      }

      HEIGHT = var1;
      enemyFlipRegion = new TextureAtlas.AtlasRegion(enemyTextureRegion);
      enemyBroFlipRegion = new TextureAtlas.AtlasRegion(enemyBroTextRegion);
   }

   public AirCraftOnBoss(World var1, Enemy var2, float var3, float var4, float var5, boolean var6) {
      super(var1, Settings.lapFixBossDegree(20), var3 + var2.bounds.x + (float)WIDTH / 1.0F / 2.0F, var4 + var2.bounds.y + (float)HEIGHT / 1.0F / 2.0F, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      float var7;
      if(var6) {
         var7 = 0.0F;
      } else {
         var7 = 0.75F;
      }

      this.clockShooting = new Clock(var7, 1.5F);
      this.parent = var2;
      this.relative = new Vector2(var3, var4);
      this.initAngle = var5;
      this.origin.set(this.bounds.width / 2.0F, this.bounds.height / 2.0F);
      this.angle = var5 + var2.getAngle();
      this.angularSpeed = 0.0F;
      this.velocity.x = 0.0F;
      this.velocity.y = 0.0F;
      if(var6) {
         this.enemyRegion = enemyFlipRegion;
         this.enemyBroRegion = enemyBroFlipRegion;
      } else {
         this.enemyRegion = enemyTextureRegion;
         this.enemyBroRegion = enemyBroTextRegion;
      }

      this.hitRecs = new ArrayList();
      Rectangle var9 = new Rectangle();
      var9.x = this.bounds.x + this.bounds.width / 4.0F;
      var9.y = this.bounds.y + this.bounds.height / 4.0F;
      var9.width = this.bounds.width / 2.0F;
      var9.height = this.bounds.height / 2.0F;
      this.hitRecs.add(var9);
   }

   private Vector2 getRotateOffset() {
      this.angleOffset.x = this.origin.x + this.relative.x;
      this.angleOffset.y = this.origin.y + this.relative.y;
      Enemy.getRotateOffset(this.angleOffset.sub(this.parent.origin), this.parent.getAngle());
      return this.angleOffset;
   }

   public static void loadResource() {
      enemyTextureRegion = Assets_level1.findRegion("boss_aircraft");
      enemyFlipRegion.setRegion(enemyTextureRegion);
      boolean var0 = enemyTextureRegion.rotate;
      boolean var1;
      if(!var0) {
         var1 = true;
      } else {
         var1 = false;
      }

      if(enemyFlipRegion.isFlipX() != var1) {
         enemyFlipRegion.flip(true, false);
      }

      if(enemyFlipRegion.isFlipY() != var0) {
         enemyFlipRegion.flip(false, true);
      }

      enemyFlipRegion.offsetX = 0.0F;
      enemyFlipRegion.offsetY = 0.0F;
      enemyBroTextRegion = Assets_level1.findRegion("boss_aircraft_broken");
      enemyBroFlipRegion.setRegion(enemyBroTextRegion);
      boolean var2 = enemyBroTextRegion.rotate;
      if(enemyFlipRegion.isFlipX() != var2) {
         enemyBroFlipRegion.flip(true, false);
      }

      if(enemyFlipRegion.isFlipY() != var2) {
         enemyBroFlipRegion.flip(false, true);
      }

      enemyBroFlipRegion.offsetX = 0.0F;
      enemyBroFlipRegion.offsetY = 0.0F;
   }

   public boolean canBeHit() {
      return this.parent.angularSpeed == 0.0F && this.angle < 45.0F && super.canBeHit();
   }

   public List<Rectangle> getHitRectangle() {
      ArrayList var1 = this.hitRecs;
      Rectangle var2 = (Rectangle)var1.get(0);
      var2.x = this.bounds.x + this.bounds.width / 4.0F;
      var2.y = this.bounds.y + this.bounds.height / 4.0F;
      var2.width = this.bounds.width / 2.0F;
      var2.height = this.bounds.height / 2.0F;
      return var1;
   }

   public float getTimeOfCrash() {
      return 0.25F;
   }

   public void keepRelativePosition() {
      Vector2 var1 = this.getRotateOffset();
      float var2 = this.parent.bounds.x + this.relative.x + this.bounds.width / 2.0F;
      float var3 = this.parent.bounds.y + this.relative.y + this.bounds.height / 2.0F;
      this.setPosition(var2 + var1.x, var3 + var1.y);
   }

   protected void putCrashAniamtion() {
      Animation var1 = this.getShatterAniamtion();
      float var2 = this.getPositionX();
      float var3 = this.getPositionY();
      WorldAnimationPlayer.addAWorldAnimation(var1, var2, var3, 1);
      WorldAnimationPlayer.addAWorldAnimation(this.getCrashBoom(true), var2, var3, 1, this.getCrashSound(true));
   }

   public void update(float var1) {
      super.update(var1);
      if(this.parent.angularSpeed != 0.0F) {
         this.setAngle(this.parent.getAngle() + this.initAngle);
      }

   }

   protected void updateDynamicParameter(float var1) {
      super.updateDynamicParameter(var1);
      this.keepRelativePosition();
   }

   public void updateShooting(float var1) {
      if(this.canBeHit()) {
         float var2 = this.getPositionX();
         float var3 = this.getPositionY();
         float var4 = this.world.fighter.getPositionY();
         if(this.clockShooting.isFired() && var3 > 100.0F + var4) {
            Ufo_b var5 = (Ufo_b)this.world.Ufo_bPool.obtain();
            var5.reset(this.world, var2, var3);
            var5.setBulletCount(1);
            this.world.enemysOnAir.add(var5);
            return;
         }
      }

   }
}
