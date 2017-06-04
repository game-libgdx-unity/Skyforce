package com.doodlegames.air.force.enemy.level1;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.HitDistribute;
import com.doodlegames.air.force.enemy.cannon.CommonCannon;
import com.doodlegames.air.force.enemy.level1.Ufo_b;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.resource.Assets_level1;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class AirCraft extends Enemy {

   private static final float BULLETSPEED = 10.0F;
   public static final int DEFAULTHEALTHYDEGREE = 20;
   private static  int HEIGHT = 0;
   private static final Rectangle HITR;
   private static final Vector2[] RELATIVEPOSITION;
   private static final float TIMEOFPLAYCRASHANIMATION = 15.0F;
   private static final int TakeOffUfoCount = 6;
   private static  int WIDTH = 0;
   private static TextureAtlas.AtlasRegion enemyTextBroRegion = Assets_level1.findRegion("nazi_aircraft_broken");
   private static TextureAtlas.AtlasRegion enemyTextureRegion = Assets_level1.findRegion("nazi_aircraft");
   private static final int ATLASHEIGHT = enemyTextureRegion.getRegionHeight();
   private static final int ATLASWIDTH = enemyTextureRegion.getRegionWidth();
   private static final int relativeX1 = 55;
   private static final int relativeX2 = 86;
   private static final int relativeY1 = 469;
   private static final int relativeY2 = 469;
   private static final int takeOffLimit = 1;
   private static Animation waveAnimation;
   public Vector2 angleVector1 = new Vector2(0.0F, 0.0F);
   private boolean choose = false;
   private CommonCannon crossCannon;
   private HitDistribute hitDistribute;
   private ArrayList<Rectangle> hitRecs;
   private AirCraft.Ufo_bTakeOff[] ub = new AirCraft.Ufo_bTakeOff[6];


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
      TextureRegion[] var2 = new TextureRegion[]{Assets_level1.findRegion("nazi_battleship_wave", 0), Assets_level1.findRegion("nazi_battleship_wave", 1), Assets_level1.findRegion("nazi_battleship_wave", 2)};
      waveAnimation = new Animation(0, 0.25F, var2);
      Vector2[] var3 = new Vector2[]{new Vector2(0.0F, 222.0F)};
      RELATIVEPOSITION = var3;
      HITR = new Rectangle((float)WIDTH / 1.0F / 4.0F, (float)HEIGHT / 1.0F / 4.0F, (float)WIDTH / 1.0F / 2.0F, (float)HEIGHT / 1.0F / 2.0F);
   }

   public AirCraft(World var1, float var2, float var3) {
      super(var1, 20, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);

      for(int var4 = 0; var4 < 6; ++var4) {
         this.ub[var4] = new AirCraft.Ufo_bTakeOff(this);
         this.ub[var4].stateTime = 0.5F * (float)(-var4);
      }

      this.velocity.y = Settings.backgroundVelocity.y;
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyTextBroRegion;
      this.crossCannon = CommonCannon.getCrossCannon(var1, new Clock(5.0F, 0.3F, (byte)1), 20, EnemyBullet1.naziBulletGen, this, 0.0F, 222.0F);
      this.crossCannon.angularSpeed = 25.0F;
      this.hitDistribute = new HitDistribute(this, 4);
      this.hitRecs = new ArrayList();
      Rectangle var5 = new Rectangle(HITR);
      var5.x += this.bounds.x;
      var5.y += this.bounds.y;
      this.hitRecs.add(var5);
   }

   public static void loadResource() {
      enemyTextBroRegion = Assets_level1.findRegion("nazi_aircraft_broken");
      enemyTextureRegion = Assets_level1.findRegion("nazi_aircraft");
      waveAnimation.setKeyFrame(0, Assets_level1.findRegion("nazi_battleship_wave", 0));
      waveAnimation.setKeyFrame(1, Assets_level1.findRegion("nazi_battleship_wave", 1));
      waveAnimation.setKeyFrame(2, Assets_level1.findRegion("nazi_battleship_wave", 2));
   }

   public void beHitByBullet(Bullet var1, int var2) {
      this.hitDistribute.beHitByBullet(var1, var2);
   }

   public boolean canBeHit() {
      return this.crossCannon.isCrashed() && super.canBeHit();
   }

   public List<Rectangle> getHitRectangle() {
      this.hitDistribute.beginAdd();
      this.hitDistribute.addEnemyHitRec(this.crossCannon);
      this.hitDistribute.addEnemyHitRec(this);
      this.hitDistribute.endAdd();
      return this.hitDistribute.getHitRectangle();
   }

   public List<Rectangle> getHitRectangle(int var1) {
      Rectangle var2 = (Rectangle)this.hitRecs.get(0);
      var2.x = this.bounds.x + HITR.x;
      var2.y = this.bounds.y + HITR.y;
      return this.hitRecs;
   }

   public float getTimeOfCrash() {
      return 15.0F;
   }

   public boolean isCrashed() {
      return super.isCrashed() && this.crossCannon.isCrashed();
   }

   public void render(SpriteBatch var1) {
      if(this.state != 1 || this.stateTime < this.getTimeOfCrash()) {
         TextureRegion var2 = waveAnimation.getKeyFrame((float)Clock.getTimeCounter());
         float var3 = (float)RegionUtilFunctions.getRegionWidth(waveAnimation);
         float var4 = (float)RegionUtilFunctions.getRegionHeight(waveAnimation);
         RegionUtilFunctions.draw(var1, var2, 19.0F + this.bounds.x, this.bounds.y, this.origin.x - 19.0F, this.origin.y, var3, var4, 1.0F, 1.0F, this.angle);
      }

      super.render(var1);
      if(!this.isCrashed()) {
         for(int var5 = 0; var5 < 6; ++var5) {
            this.ub[var5].render(var1);
         }
      }

      this.crossCannon.render(var1);
   }

   public void update(float var1) {
      super.update(var1);
      if(this.state != 1) {
         for(int var2 = 0; var2 < 6; ++var2) {
            this.ub[var2].update(var1);
         }
      }

      this.crossCannon.update(var1);
   }

   public void updateShooting(float var1) {}

   public class Ufo_bTakeOff extends Ufo_b {

      private Enemy parent;
      private float scaleShadow;
      private float scaleUfo;
      private Vector2 shadowOffset;
      private int takeOff = 0;
      private boolean visible;


      public Ufo_bTakeOff(Enemy var2) {
         super((World)null, 55.0F + var2.bounds.x, 469.0F + var2.bounds.y, new Vector2(0.0F, -126.0F));
         this.parent = var2;
         this.world = this.parent.world;
         this.shadowOffset = new Vector2();
         this.visible = false;
         this.initUfo_b();
      }

      public void endUfo_b() {
         this.visible = false;
         if(this.takeOff < 1) {
            ++this.takeOff;
            Ufo_b var1 = (Ufo_b)this.parent.world.Ufo_bPool.obtain();
            var1.reset(this.world, this.position.x, this.position.y);
            var1.setBulletCount(1);
            this.parent.world.enemysOnAir.add(var1);
         }

      }

      public void initUfo_b() {
         byte var1;
         short var2;
         if(!AirCraft.this.choose) {
            var1 = 55;
            var2 = 469;
            AirCraft.this.choose = true;
         } else {
            var1 = 86;
            var2 = 469;
            AirCraft.this.choose = false;
         }

         this.stateTime = 0.0F;
         this.position.x = (float)var1 / 1.0F + this.parent.bounds.x;
         this.position.y = (float)var2 / 1.0F + this.parent.bounds.y;
         this.scaleUfo = 0.5F;
         this.scaleShadow = 0.5F;
         this.shadowOffset.x = 2.0F;
         this.shadowOffset.y = -2.0F;
      }

      public void render(SpriteBatch var1) {
         if(this.stateTime >= 0.0F && this.stateTime <= 3.0F) {
            this.renderShadow(var1);
            if(this.stateTime < 1.5F) {
               this.scaleUfo = 0.5F;
            } else if(this.stateTime <= 3.0F) {
               this.scaleUfo = 0.5F + 0.33333334F * (this.stateTime - 1.5F);
            }

            RegionUtilFunctions.draw(var1, ufo_bTextureRegion, this.bounds.x, this.bounds.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, this.scaleUfo, this.scaleUfo, this.angle);
         }
      }

      public void renderShadow(SpriteBatch var1) {
         if(this.stateTime >= 0.0F && this.stateTime <= 3.0F) {
            if(this.stateTime < 1.5F) {
               this.scaleShadow = 0.5F;
            } else {
               this.scaleShadow = 0.5F + 0.2F * (this.stateTime - 1.5F);
            }

            if(this.stateTime < 1.5F) {
               this.shadowOffset.x = 2.0F;
               this.shadowOffset.y = -2.0F;
            } else {
               this.shadowOffset.x = 2.0F + 45.333332F * (this.stateTime - 1.5F) / 1.0F;
               this.shadowOffset.y = -this.shadowOffset.x;
            }

            Color var2 = var1.getColor();
            var1.setColor(0.0F, 0.0F, 0.0F, 0.5F);
            var1.draw(ufo_bTextureRegion, this.position.x - this.bounds.width / 2.0F + this.shadowOffset.x, this.position.y - this.bounds.height / 2.0F + this.shadowOffset.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, this.scaleShadow, this.scaleShadow, this.angle);
            var1.setColor(var2.r, var2.g, var2.b, 1.0F);
         }
      }

      public void update(float var1) {
         this.stateTime += var1;
         if(!this.visible && this.stateTime >= 0.0F && this.takeOff < 1) {
            this.initUfo_b();
            this.visible = true;
         }

         float var2 = this.velocity.y;
         this.velocity.y = var2 + this.parent.velocity.y;
         this.updateDynamicParameter(var1);
         this.velocity.y = var2;
         if(this.stateTime > 3.0F) {
            this.endUfo_b();
         }

      }
   }
}
