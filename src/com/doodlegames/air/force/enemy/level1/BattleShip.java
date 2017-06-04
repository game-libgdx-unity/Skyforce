package com.doodlegames.air.force.enemy.level1;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.HitDistribute;
import com.doodlegames.air.force.enemy.cannon.CommonCannon;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.resource.Assets_level1;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;

import java.util.ArrayList;
import java.util.List;

public class BattleShip extends Enemy {

   public static final int DEFAULTHEALTHYDEGREE = 20;
   public static int HEIGHT = 0;
   private static final Vector2[] RELATIVEPOSITION;
   private static final float TIMEOFPLAYCRASHANIMATION = 15.0F;
   public static final float WAVEOFFSETX = -4.0F;
   public static final float WAVEOFFSETY = 2.0F;
   public static final int WIDTH;
   private static TextureAtlas.AtlasRegion enemyTextbroRegion;
   private static TextureAtlas.AtlasRegion enemyTextureRegion;
   private static Animation waveAnimation;
   private CommonCannon cannon1;
   private CommonCannon cannon2;
   private CommonCannon cannon3;
   private CommonCannon cannon4;
   private CommonCannon crosscannon;
   private HitDistribute hitDistributor;
   private ArrayList<Rectangle> hitRecs;
   private Clock pointClock;


   static {
      Vector2[] var0 = new Vector2[]{new Vector2(18.0F, 37.0F), new Vector2(18.0F, 72.0F), new Vector2(18.0F, 298.0F), new Vector2(18.0F, 334.0F), new Vector2(15.0F, 167.0F)};
      RELATIVEPOSITION = var0;
      enemyTextbroRegion = Assets_level1.findRegion("nazi_battleship_broken");
      enemyTextureRegion = Assets_level1.findRegion("nazi_battleship");
      int var1;
      if(enemyTextureRegion.rotate) {
         var1 = enemyTextureRegion.getRegionHeight();
      } else {
         var1 = enemyTextureRegion.getRegionWidth();
      }

      WIDTH = var1;
      int var2;
      if(enemyTextureRegion.rotate) {
         var2 = enemyTextureRegion.getRegionWidth();
      } else {
         var2 = enemyTextureRegion.getRegionHeight();
      }

      HEIGHT = var2;
      TextureRegion[] var3 = new TextureRegion[]{Assets_level1.findRegion("nazi_battleship_wave", 0), Assets_level1.findRegion("nazi_battleship_wave", 1), Assets_level1.findRegion("nazi_battleship_wave", 2)};
      waveAnimation = new Animation(0, 0.25F, var3);
   }

   public BattleShip(World var1, float var2, float var3) {
      super(var1, 20, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyTextbroRegion;
      float var4 = (float)(200 + Clock.rand.nextInt(100));
      this.cannon1 = CommonCannon.getCannon1(var1, new Clock(4.0F, 9.0F, 3.0F, (byte)1), 20, EnemyBullet1.naziBulletGen, this, RELATIVEPOSITION[0].x, RELATIVEPOSITION[0].y, var4);
      float var5 = (float)(200 + Clock.rand.nextInt(100));
      this.cannon2 = CommonCannon.getCannon1(var1, new Clock(4.7F, 9.0F, 3.0F, (byte)1), 20, EnemyBullet1.naziBulletGen, this, RELATIVEPOSITION[1].x, RELATIVEPOSITION[1].y, var5);
      float var6 = (float)(200 + Clock.rand.nextInt(100));
      this.cannon3 = CommonCannon.getCannon1(var1, new Clock(4.4F, 9.0F, 3.0F, (byte)1), 20, EnemyBullet1.naziBulletGen, this, RELATIVEPOSITION[2].x, RELATIVEPOSITION[2].y, var6);
      float var7 = (float)(200 + Clock.rand.nextInt(100));
      this.cannon4 = CommonCannon.getCannon1(var1, new Clock(4.6F, 9.0F, 3.0F, (byte)1), 20, EnemyBullet1.naziBulletGen, this, RELATIVEPOSITION[3].x, RELATIVEPOSITION[3].y, var7);
      this.crosscannon = CommonCannon.getCrossCannon(var1, new Clock(4.0F, 5.0F, 0.4F, (byte)2), 20, EnemyBullet1.naziBulletGen, this, RELATIVEPOSITION[4].x, RELATIVEPOSITION[4].y, 200.0F);
      this.crosscannon.angularSpeed = 50.0F;
      this.pointClock = new Clock(5.0F);
      this.hitDistributor = new HitDistribute(this, 6);
      this.hitRecs = new ArrayList();
      Rectangle var8 = new Rectangle(this.bounds.width / 4.0F, this.bounds.height / 4.0F, this.bounds.width / 2.0F, this.bounds.height / 2.0F);
      this.hitRecs.add(var8);
   }

   public static void loadResource() {
      enemyTextbroRegion = Assets_level1.findRegion("nazi_battleship_broken");
      enemyTextureRegion = Assets_level1.findRegion("nazi_battleship");
      waveAnimation.setKeyFrame(0, Assets_level1.findRegion("nazi_battleship_wave", 0));
      waveAnimation.setKeyFrame(1, Assets_level1.findRegion("nazi_battleship_wave", 1));
      waveAnimation.setKeyFrame(2, Assets_level1.findRegion("nazi_battleship_wave", 2));
   }

   public void beHitByBullet(Bullet var1, int var2) {
      this.hitDistributor.beHitByBullet(var1, var2);
   }

   public boolean canBeHit() {
      return this.cannon1.isCrashed() && this.cannon2.isCrashed() && this.cannon3.isCrashed() && this.cannon4.isCrashed() && this.crosscannon.isCrashed() && super.canBeHit();
   }

   public List<Rectangle> getHitRectangle() {
      this.hitDistributor.beginAdd();
      this.hitDistributor.addEnemyHitRec(this.cannon1);
      this.hitDistributor.addEnemyHitRec(this.cannon2);
      this.hitDistributor.addEnemyHitRec(this.cannon3);
      this.hitDistributor.addEnemyHitRec(this.cannon4);
      this.hitDistributor.addEnemyHitRec(this.crosscannon);
      this.hitDistributor.addEnemyHitRec(this);
      this.hitDistributor.endAdd();
      return this.hitDistributor.getHitRectangle();
   }

   public List<Rectangle> getHitRectangle(int var1) {
      Rectangle var2 = (Rectangle)this.hitRecs.get(0);
      var2.x = this.bounds.x + this.bounds.width / 4.0F;
      var2.y = this.bounds.y + this.bounds.height / 4.0F;
      var2.width = this.bounds.width / 2.0F;
      var2.height = this.bounds.height / 2.0F;
      return this.hitRecs;
   }

   public float getTimeOfCrash() {
      return 15.0F;
   }

   public boolean isCrashed() {
      return super.isCrashed() && this.cannon1.isCrashed() && this.cannon2.isCrashed() && this.cannon3.isCrashed() && this.cannon4.isCrashed() && this.crosscannon.isCrashed();
   }

   public void render(SpriteBatch var1) {
      float var2 = -4.0F + this.bounds.x;
      float var3 = 2.0F + this.bounds.y;
      waveAnimation.draw(var1, var2, var3, (float)Clock.getTimeCounter());
      super.render(var1);
      this.cannon1.render(var1);
      this.cannon2.render(var1);
      this.cannon3.render(var1);
      this.cannon4.render(var1);
      this.crosscannon.render(var1);
   }

   public void update(float var1) {
      super.update(var1);
      this.cannon1.update(var1);
      this.cannon2.update(var1);
      this.cannon3.update(var1);
      this.cannon4.update(var1);
      this.crosscannon.update(var1);
   }

   public void updateShooting(float var1) {
      if(this.pointClock.isFired()) {
         CommonCannon[] var2 = new CommonCannon[]{this.cannon1, this.cannon2, this.cannon3, this.cannon4};
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            CommonCannon var5 = var2[var4];
            if(!var5.isCrashed()) {
               float var6 = this.world.fighter.getPositionY() - var5.getPositionY();
               float var7 = this.world.fighter.getPositionX() - var5.getPositionX();
               if(var6 < 0.0F) {
                  var5.rotateTo(90.0F + (float)(57.2957763671875D * Math.atan2((double)var6, (double)var7)));
               }
            }
         }
      }

   }
}
