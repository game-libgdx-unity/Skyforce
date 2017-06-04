package com.doodlegames.air.force.enemy.level3;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector2Utils;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.enemy.level3.BossLv3;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldAnimationPlayer;
import com.doodlegames.air.force.game.WorldAudio;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.BulletScreen;
import com.doodlegames.air.force.resource.Assets_level3;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;

import java.util.ArrayList;
import java.util.List;

public class BossLv3Building4 extends Enemy implements EnemyTools.EnemyActionCallBack {

   public static final Vector2 BULLET1POSITION;
   public static final Vector2[] BULLET2POSITION;
   public static final int DEFAULTHEALTHYDEGREE = 180;
   public static int HEIGHT = 0;
   private static int RECNUM = 0;
   private static final float SHADOWDELTA = 25.0F;
   private static final float TIMEOFPLAYCRASHANIMATION = 4.0F;
   public static final Vector2[] VELBULLET1;
   public static final Vector2 VELBULLET2;
   public static final int WIDTH;
   private static TextureAtlas.AtlasRegion enemyBroTextureRegion = Assets_level3.atlas_Enemy.findRegion("lv3_boss_building_4_broken");
   private static TextureAtlas.AtlasRegion enemyTextureRegion = Assets_level3.atlas_Enemy.findRegion("lv3_boss_building", 4);
   private static Rectangle[] relativeHitRectangles;
   private BossLv3.BuildingState buildingState;
   private Clock clockShootingCanon1;
   private Clock clockShootingCanon2;
   private Clock clockSleep;
   private float downThreshold;
   private Rectangle[] hitHitRec;
   private List<Rectangle> hitRectangles;
   private final BossLv3 parent;
   private float[] shadowVertices;
   private Vector2 velBullet;


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
      VELBULLET2 = new Vector2(0.0F, -200.0F);
      BULLET1POSITION = new Vector2(15.0F, 31.0F);
      Vector2[] var2 = new Vector2[]{new Vector2(75.0F, 60.0F), new Vector2(60.0F, 44.0F), new Vector2(45.0F, 28.0F)};
      BULLET2POSITION = var2;
      Vector2[] var3 = new Vector2[]{new Vector2(), new Vector2(), new Vector2()};
      VELBULLET1 = var3;
      Vector2 var4 = Vector2Utils.tmp.set(VELBULLET2);
      VELBULLET1[0].set(var4);
      VELBULLET1[2].set(var4.y, var4.x);
      VELBULLET1[1].set(var4.rotate(-45.0F));
      Rectangle[] var8 = new Rectangle[]{new Rectangle(0.0F, 50.0F, 10.0F, 10.0F), new Rectangle(10.0F, 40.0F, 10.0F, 10.0F), new Rectangle(20.0F, 30.0F, 10.0F, 10.0F), new Rectangle(30.0F, 30.0F, 10.0F, 10.0F), new Rectangle(40.0F, 40.0F, 10.0F, 10.0F), new Rectangle(50.0F, 50.0F, 10.0F, 10.0F), new Rectangle(60.0F, 60.0F, 10.0F, 10.0F), new Rectangle(70.0F, 70.0F, 10.0F, 10.0F), new Rectangle(80.0F, 80.0F, 10.0F, 10.0F), new Rectangle(90.0F, 90.0F, 10.0F, 10.0F), new Rectangle(100.0F, 100.0F, 10.0F, 10.0F), new Rectangle(110.0F, 110.0F, 5.0F, 10.0F)};
      relativeHitRectangles = var8;
      RECNUM = relativeHitRectangles.length;
   }

   public BossLv3Building4(World var1, float var2, float var3, BossLv3 var4) {
      super(var1, 180, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.parent = var4;
      this.hitHitRec = new Rectangle[RECNUM];

      for(int var5 = 0; var5 < RECNUM; ++var5) {
         this.hitHitRec[var5] = new Rectangle(1.0F + this.bounds.x + relativeHitRectangles[var5].x, 1.0F + this.bounds.y + relativeHitRectangles[var5].y, relativeHitRectangles[var5].width, relativeHitRectangles[var5].height);
      }

      this.buildingState = BossLv3.BuildingState.SleepWithOutClock;
      this.clockSleep = new Clock(10.0F);
      this.downThreshold = (float)(30 * ((-1 + this.getHealthyDgree()) / 30));
      this.hitRectangles = new ArrayList(RECNUM);
      this.clockShootingCanon1 = new Clock(6.0F);
      this.clockShootingCanon2 = new Clock(6.0F);
      this.velBullet = new Vector2(VELBULLET2);
      this.shadowVertices = new float[20];
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyBroTextureRegion;
   }

   private float getInitPositionY() {
      return this.parent.bounds.y + BossLv3.BUILDING4POSITION.y;
   }

   public static void loadResource() {
      enemyTextureRegion = Assets_level3.atlas_Enemy.findRegion("lv3_boss_building", 4);
      enemyBroTextureRegion = Assets_level3.atlas_Enemy.findRegion("lv3_boss_building_4_broken");
   }

   public boolean act(EnemyTools.EnemyAction var1, float var2) {
      return false;
   }

   public boolean actionCancel(EnemyTools.EnemyAction var1) {
      return var1 instanceof EnemyTools.DelayShootAction && this.buildingState != BossLv3.BuildingState.Shooting;
   }

   public void beHitByBullet(Bullet var1) {
      int var2 = var1.damageValue;
      int var3 = this.getHealthyDgree() - var1.damageValue;
      float var4 = this.downThreshold - 1.0F;
      if((float)var3 < var4) {
         var1.damageValue = this.getHealthyDgree() - (int)var4;
      }

      super.beHitByBullet(var1);
      var1.damageValue = var2;
   }

   public BossLv3.BuildingState getBuildState() {
      return this.buildingState;
   }

   public List<Rectangle> getHitRectangle() {
      this.hitRectangles.clear();
      if(this.buildingState == BossLv3.BuildingState.Shooting) {
         for(int var1 = 0; var1 < RECNUM; ++var1) {
            this.hitHitRec[var1].x = 1.0F + this.bounds.x + relativeHitRectangles[var1].x;
            this.hitHitRec[var1].y = 1.0F + this.bounds.y + relativeHitRectangles[var1].y;
            this.hitRectangles.add(this.hitHitRec[var1]);
         }
      }

      return this.hitRectangles;
   }

   public float getTimeOfCrash() {
      return 4.0F;
   }

   public void putCrashAniamtion() {
      Animation var1 = this.getShatterAniamtion();
      Animation var2 = this.getCrashBoom(false);
      WorldAudio.PlaySound var3 = this.getCrashSound(false);
      float var4 = 85.0F + this.bounds.x;
      float var5 = 150.0F + this.bounds.y;
      WorldAnimationPlayer.addAWorldAnimation(var1, var4, var5, 1);
      WorldAnimationPlayer.addAWorldAnimation(var2, var4, var5, 1, var3);
      float var7 = 45.0F + this.bounds.x;
      float var8 = 110.0F + this.bounds.y;
      float var9 = 0.1F + (float)Clock.getTimeCounter();
      WorldAnimationPlayer.addAWorldAnimation(var1, var7, var8, 1, var9);
      WorldAnimationPlayer.addAWorldAnimation(var2, var7, var8, 1, var9, var3);
   }

   public void renderShadow(SpriteBatch var1) {
      if(this.position.y != this.getInitPositionY()) {
         float var2 = 1.0F * (25.0F * (this.position.y - this.getInitPositionY()) / 36.0F);
         float var3 = -var2;
         this.shadowVertices[0] = 31.0F + this.bounds.x;
         this.shadowVertices[1] = 36.0F + (this.getInitPositionY() - this.bounds.height / 2.0F);
         this.shadowVertices[3] = 1.0F;
         this.shadowVertices[4] = 1.0F;
         this.shadowVertices[2] = Color.WHITE.toFloatBits();
         this.shadowVertices[5] = var2 + this.shadowVertices[0];
         this.shadowVertices[6] = var3 + this.shadowVertices[1];
         this.shadowVertices[8] = 1.0F;
         this.shadowVertices[9] = 1.0F;
         this.shadowVertices[7] = Color.WHITE.toFloatBits();
         this.shadowVertices[10] = 85.0F + this.shadowVertices[5];
         this.shadowVertices[11] = 85.0F + this.shadowVertices[6];
         this.shadowVertices[13] = 1.0F;
         this.shadowVertices[14] = 1.0F;
         this.shadowVertices[12] = Color.WHITE.toFloatBits();
         this.shadowVertices[15] = 85.0F + this.shadowVertices[0];
         this.shadowVertices[16] = 85.0F + this.shadowVertices[1];
         this.shadowVertices[18] = 1.0F;
         this.shadowVertices[19] = 1.0F;
         this.shadowVertices[17] = Color.WHITE.toFloatBits();
         var1.draw(Assets_level3.buildingShadow, this.shadowVertices, 0, 20);
      }

   }

   public void setBuildState(BossLv3.BuildingState var1) {
      this.buildingState = var1;
   }

   public void update(final float n) {

       super.update(n);

       switch (this.buildingState) {

           case SleepWithOutClock: {

               if (this.position.y != this.getInitPositionY()) {

                   this.position.y = this.getInitPositionY();

                   super.updateDynamicParameter(0.0f);

                   return;

               }

               break;

           }

           case SleepWithClock: {

               if (this.position.y != this.getInitPositionY()) {

                   this.position.y = this.getInitPositionY();

                   super.updateDynamicParameter(0.0f);

               }

               if (this.clockSleep.isFired()) {

                   this.buildingState = BossLv3.BuildingState.Rising;

                   this.velocity.set(0.0f, 36.0f);

                   return;

               }

               break;

           }

           case Rising: {

               if (this.velocity.y != 36.0f) {

                   this.velocity.y = 36.0f;

               }

               if (this.position.y - this.getInitPositionY() >= 36.0f) {

                   this.buildingState = BossLv3.BuildingState.Shooting;

                   this.velocity.set(0.0f, 0.0f);

                   this.downThreshold = (float)(30 * ((-1 + this.getHealthyDgree()) / 30));

                   this.clockShootingCanon1.resetClock(6.0f, 6.0f, 0.2f, (byte) 1);

                   this.clockShootingCanon2.resetClock(6.0f, 6.0f, 0.2f, (byte) 1);

                   return;

               }

               break;

           }

           case Shooting: {

               if (this.position.y == 36.0f + this.getInitPositionY()) {

                   this.position.y = 36.0f + this.getInitPositionY();

                   super.updateDynamicParameter(0.0f);

               }

               if ((float)this.getHealthyDgree() < this.downThreshold && this.downThreshold > 0.0f) {

                   this.buildingState = BossLv3.BuildingState.Falling;

                   this.downThreshold = this.downThreshold - 30.0f;

                   return;

               }

               break;

           }

           case Falling: {

               if (this.velocity.y == 0.0f) {

                   this.velocity.set(0.0f, -36.0f);

               }

               if (this.position.y <= this.getInitPositionY()) {

                   if (this.downThreshold > 0.0f) {

                       this.buildingState = BossLv3.BuildingState.SleepWithClock;

                       this.clockSleep.resetClockFireOnce(2.0f + 4.0f * this.world.rand.nextFloat());

                   }

                   else {

                       this.buildingState = BossLv3.BuildingState.SleepWithOutClock;

                   }

                   this.velocity.set(0.0f, 0.0f);

                   return;

               }

               break;

           }

       }

   }

   public void updateShooting(float var1) {
      if(this.buildingState == BossLv3.BuildingState.Shooting) {
         if(this.clockShootingCanon1.isFired()) {
            if(Clock.rand.nextBoolean()) {
               this.addEnemyAction(EnemyTools.naziShootMissle(this, 2.0F, VELBULLET2.y, BULLET1POSITION.x, BULLET1POSITION.y, 0.0F));
               this.addEnemyAction(EnemyTools.naziShootMissle(this, 2.0F, VELBULLET2.y, BULLET1POSITION.x, BULLET1POSITION.y, -45.0F));
               this.addEnemyAction(EnemyTools.naziShootMissle(this, 2.0F, VELBULLET2.y, BULLET1POSITION.x, BULLET1POSITION.y, -90.0F));
            } else {
               EnemyTools.naziShootScreen(this, 2.0F, BULLET1POSITION.x, BULLET1POSITION.y, BulletScreen.getPublicNoMatterScreen());
            }
         }

         if(this.clockShootingCanon2.isFired()) {
            float var2 = 90.0F + 57.295776F * MathUtils.atan2(this.world.fighter.position.y - (this.bounds.y + BULLET2POSITION[1].y), this.world.fighter.position.x - (this.bounds.x + BULLET2POSITION[1].x));
            if(var2 <= -45.0F || var2 >= 45.0F) {
               var2 = 0.0F;
            }

            float var3 = 0.0F;

            for(int var4 = 0; var4 < BULLET2POSITION.length; ++var4) {
               Vector2 var5 = BULLET2POSITION[var4];
               this.addEnemyAction(EnemyTools.naziShootBullet(this, var3, VELBULLET2.y, var5.x, var5.y, var2));
               var3 += 0.5F;
            }
         }
      }

   }
}
