package com.doodlegames.air.force.enemy.level4;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.enemy.HitDistribute;
import com.doodlegames.air.force.enemy.level4.BossLv4Cannon;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.Gun.BulletGun;
import com.doodlegames.air.force.game.Gun.BulletGunAlien;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.resource.Assets_level4;
import com.doodlegames.air.force.utils.Clock;

import java.util.ArrayList;
import java.util.List;

public class BossLv4HeadN extends Enemy {

   private static final int CANNONNUM = 4;
   public static final Vector2[] CANNONPOSITION;
   public static final int DEFAULTHEALTHYDEGREE = 220;
   public static int EYE1HEIGHT = 0;
   public static final Vector2 EYE1ORIGIN;
   public static final Vector2 EYE1POSTION;
   public static int EYE1WIDTH = 0;
   public static int EYE2HEIGHT = 0;
   public static final Vector2 EYE2ORIGIN;
   public static final Vector2 EYE2POSTION;
   public static int EYE2WIDTH = 0;
   public static int HEIGHT = 0;
   public static final Vector2 ORIGIN;
   private static final float TIMEOFPLAYCRASHANIMATION = 15.0F;
   public static final int WIDTH;
   public static TextureAtlas.AtlasRegion eye1TextureRegion;
   public static TextureAtlas.AtlasRegion eye2TextureRegion;
   public static final TextureAtlas.AtlasRegion[] headTextureRegion;
   private BossLv4Cannon[] cannons;
   private Clock clockShootModeChangeLeft;
   private Clock clockShootModeChangeRight;
   private float eyeAlpha;
   private HitDistribute hitDis;
   private List<Rectangle> hitRs;
   private BulletGun overGunL;
   private BulletGun pointGunL;
   private BulletGun turningGun;


   static {
      TextureAtlas.AtlasRegion[] var0 = new TextureAtlas.AtlasRegion[]{Assets_level4.atlas_Enemy.findRegion("lv_4_boss_head", 1), Assets_level4.atlas_Enemy.findRegion("lv_4_boss_head", 2)};
      headTextureRegion = var0;
      eye1TextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_eye", 1);
      eye2TextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_eye", 2);
      int var1;
      if(headTextureRegion[0].rotate) {
         var1 = headTextureRegion[0].getRegionHeight();
      } else {
         var1 = headTextureRegion[0].getRegionWidth();
      }

      WIDTH = var1;
      int var2;
      if(headTextureRegion[0].rotate) {
         var2 = headTextureRegion[0].getRegionWidth();
      } else {
         var2 = headTextureRegion[0].getRegionHeight();
      }

      HEIGHT = var2;
      int var3;
      if(eye1TextureRegion.rotate) {
         var3 = eye1TextureRegion.getRegionHeight();
      } else {
         var3 = eye1TextureRegion.getRegionWidth();
      }

      EYE1WIDTH = var3;
      int var4;
      if(eye1TextureRegion.rotate) {
         var4 = eye1TextureRegion.getRegionWidth();
      } else {
         var4 = eye1TextureRegion.getRegionHeight();
      }

      EYE1HEIGHT = var4;
      int var5;
      if(eye2TextureRegion.rotate) {
         var5 = eye2TextureRegion.getRegionHeight();
      } else {
         var5 = eye2TextureRegion.getRegionWidth();
      }

      EYE2WIDTH = var5;
      int var6;
      if(eye2TextureRegion.rotate) {
         var6 = eye2TextureRegion.getRegionWidth();
      } else {
         var6 = eye2TextureRegion.getRegionHeight();
      }

      EYE2HEIGHT = var6;
      EYE1POSTION = new Vector2(37.0F, 79.0F);
      EYE2POSTION = new Vector2(84.0F, 80.0F);
      Vector2[] var7 = new Vector2[]{new Vector2((-46.0F + (float)BossLv4Cannon.WIDTH / 2.0F) / 1.0F, (-121.0F + (float)BossLv4Cannon.HEIGHT / 2.0F) / 1.0F), new Vector2((-16.0F + (float)BossLv4Cannon.WIDTH / 2.0F) / 1.0F, (-121.0F + (float)BossLv4Cannon.HEIGHT / 2.0F) / 1.0F), new Vector2((140.0F + (float)BossLv4Cannon.WIDTH / 2.0F) / 1.0F, (-121.0F + (float)BossLv4Cannon.HEIGHT / 2.0F) / 1.0F), new Vector2((170.0F + (float)BossLv4Cannon.WIDTH / 2.0F) / 1.0F, (-121.0F + (float)BossLv4Cannon.HEIGHT / 2.0F) / 1.0F)};
      CANNONPOSITION = var7;
      ORIGIN = new Vector2(65.0F, 43.0F);
      EYE1ORIGIN = new Vector2(ORIGIN.x - EYE1POSTION.x, ORIGIN.y - EYE1POSTION.y);
      EYE2ORIGIN = new Vector2(ORIGIN.x - EYE2POSTION.x, ORIGIN.y - EYE2POSTION.y);
   }

   public BossLv4HeadN(World var1, float var2, float var3) {
      super(var1, 220, var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.enemyRegion = headTextureRegion[0];
      this.enemyBroRegion = headTextureRegion[1];
      this.origin.set(ORIGIN);
      this.hitDis = new HitDistribute(this, 5);
      this.cannons = new BossLv4Cannon[4];

      for(int var5 = 0; var5 < 4; ++var5) {
         this.cannons[var5] = new BossLv4Cannon(var1, this.bounds.x + CANNONPOSITION[var5].x, this.bounds.y + CANNONPOSITION[var5].y);
      }

      this.hitRs = new ArrayList(1);
      this.hitRs.add(new Rectangle());
      this.getHitRectangle(0);
      this.clockShootModeChangeLeft = new Clock(0.0F, 6.0F + 2.0F * var1.rand.nextFloat(), Clock.ClockType.FireOnlyOnce);
      this.clockShootModeChangeRight = new Clock(0.0F, 6.0F + 2.0F * var1.rand.nextFloat(), Clock.ClockType.FireOnlyOnce);
      this.eyeAlpha = 0.0F;
      this.addEnemyAction((EnemyTools.EnemyAction)Pools.obtain(BossLv4HeadN.EyeAlphaAction.class));
      this.constructLeftGuns();
   }

   private void constructLeftGuns() {
      Clock var1 = new Clock(10.0F, 10.0F, 0.2F, (byte)4);
      float var2 = EYE2POSTION.x + (float)EYE2WIDTH / 1.0F;
      float var3 = EYE2POSTION.y + (float)EYE2HEIGHT / 1.0F;
      this.pointGunL = BulletGunAlien.getNaziPursueGun(this, var1, var2, var3, 250.0F);
      this.overGunL = BulletGunAlien.getNaziOffsetGun(this, var1, var2, var3, 250.0F);
   }

   public static void loadResource() {
      headTextureRegion[0] = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_head", 1);
      headTextureRegion[1] = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_head", 2);
      eye1TextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_eye", 1);
      eye2TextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_eye", 2);
   }

   public void beHitByBullet(Bullet var1) {
      super.beHitByBullet(var1);
   }

   public void beHitByBullet(Bullet var1, int var2) {
      if(1 != this.state) {
         this.hitDis.beHitByBullet(var1, var2);
      }
   }

   public List<Rectangle> getHitRectangle() {
      this.hitDis.beginAdd();

      for(int var1 = 0; var1 < this.cannons.length; ++var1) {
         this.hitDis.addEnemyHitRec(this.cannons[var1]);
      }

      this.hitDis.addEnemyHitRec(this);
      this.hitDis.endAdd();
      return this.hitDis.getHitRectangle();
   }

   public List<Rectangle> getHitRectangle(int var1) {
      Rectangle var2 = (Rectangle)this.hitRs.get(0);
      var2.x = this.bounds.x + this.bounds.width / 4.0F;
      var2.y = this.bounds.y + this.bounds.height / 4.0F;
      var2.width = this.bounds.width / 2.0F;
      var2.height = this.bounds.height / 2.0F;
      return this.hitRs;
   }

   public float getTimeOfCrash() {
      return 15.0F;
   }

   public boolean isCrashed() {
      if(super.isCrashed() && this.cannons[0].isCrashed() && this.cannons[1].isCrashed() && this.cannons[2].isCrashed() && this.cannons[3].isCrashed()) {
         this.makeCrash();
         return true;
      } else {
         return false;
      }
   }

   public void render(SpriteBatch var1) {
      super.render(var1);

      for(int var2 = 0; var2 < 4; ++var2) {
         this.cannons[var2].render(var1);
      }

      Color var3 = var1.getColor();
      var1.setColor(1.0F, 1.0F, 1.0F, this.eyeAlpha);
      var1.draw(eye1TextureRegion, this.bounds.x + EYE1POSTION.x, this.bounds.y + EYE1POSTION.y, EYE1ORIGIN.x, EYE1ORIGIN.y, (float)EYE1WIDTH / 1.0F, (float)EYE1HEIGHT / 1.0F, 1.0F, 1.0F, this.angle);
      var1.draw(eye2TextureRegion, this.bounds.x + EYE2POSTION.x, this.bounds.y + EYE2POSTION.y, EYE2ORIGIN.x, EYE2ORIGIN.y, (float)EYE2WIDTH / 1.0F, (float)EYE2HEIGHT / 1.0F, 1.0F, 1.0F, this.angle);
      var1.setColor(var3);
   }

   public void setSleepPosition(float var1, float var2) {
      super.setSleepPosition(var1, var2);
      int var3 = this.cannons.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         float var5 = this.bounds.x + CANNONPOSITION[var4].x;
         float var6 = this.bounds.y + CANNONPOSITION[var4].y;
         this.cannons[var4].setSleepPosition(var5, var6);
      }

   }

   public void update(float var1) {
      super.update(var1);

      for(int var2 = 0; var2 < 4; ++var2) {
         this.cannons[var2].update(var1);
      }

   }

   public void updateShooting(float var1) {}

   public static class EyeAlphaAction extends EnemyTools.EnemyAction {

      public boolean act(float var1) {
         BossLv4HeadN var2 = (BossLv4HeadN)((BossLv4HeadN)this.getEnemy());
         if(var2.eyeAlpha < 1.0F) {
//            BossLv4HeadN.access$016(var2, 0.1F * var1);
         }

         if(var2.eyeAlpha >= 1.0F) {
            var2.eyeAlpha = 1.0F;
            return true;
         } else {
            return false;
         }
      }
   }
}
