package com.doodlegames.air.force.enemy.level4;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyProXY;
import com.doodlegames.air.force.enemy.level4.BossLv4Craft;
import com.doodlegames.air.force.enemy.level4.BossLv4Hand;
import com.doodlegames.air.force.enemy.level4.BossLv4Head;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.resource.Assets_level4;
import com.doodlegames.air.force.screen.GameScreen;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class BossLv4 extends Enemy implements World.RenderObject {

   public static int BODYHEIGHT = 0;
   public static final Vector2 BODYPOSTION;
   public static int BODYWIDTH = 0;
   public static final Vector2 CRAFTLEFTPOSTION;
   public static final Vector2 CRAFTRIGHTPOSTION;
   public static final int DEFAULTHEALTHYDEGREE = 1000;
   public static final Vector2 HANDPOSTION;
   public static final Vector2 HEADPOSTION;
   private static final int PARTSNUM = 19;
   public static int SHADOWHEIGHT = 0;
   public static int SHADOWWIDTH = 0;
   private static final float TIMEOFPLAYCRASHANIMATION = 18.0F;
   public static TextureAtlas.AtlasRegion bodyTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_body");
   public static TextureAtlas.AtlasRegion shadowTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_shadow");
   private BossLv4Craft craft1;
   private BossLv4Craft craft2;
   float dropTime;
   private BossLv4Hand hand;
   private BossLv4Head head;
   private Enemy.HitMapItem[] hitMap;
   private List<Rectangle> hitRectangles;
   private Vector2 thisVelBullet;


   static {
      int var0;
      if(shadowTextureRegion.rotate) {
         var0 = shadowTextureRegion.getRegionHeight();
      } else {
         var0 = shadowTextureRegion.getRegionWidth();
      }

      SHADOWWIDTH = var0;
      int var1;
      if(shadowTextureRegion.rotate) {
         var1 = shadowTextureRegion.getRegionWidth();
      } else {
         var1 = shadowTextureRegion.getRegionHeight();
      }

      SHADOWHEIGHT = var1;
      int var2;
      if(bodyTextureRegion.rotate) {
         var2 = bodyTextureRegion.getRegionHeight();
      } else {
         var2 = bodyTextureRegion.getRegionWidth();
      }

      BODYWIDTH = var2;
      int var3;
      if(bodyTextureRegion.rotate) {
         var3 = bodyTextureRegion.getRegionWidth();
      } else {
         var3 = bodyTextureRegion.getRegionHeight();
      }

      BODYHEIGHT = var3;
      BODYPOSTION = new Vector2(147.0F, 224.0F);
      HEADPOSTION = new Vector2((210.0F + (float)BossLv4Head.WIDTH / 2.0F) / 1.0F, (352.0F + (float)BossLv4Head.HEIGHT / 2.0F) / 1.0F);
      HANDPOSTION = new Vector2((74.0F + (float)BossLv4Hand.HANDWIDTH / 2.0F) / 1.0F, (270.0F + (float)BossLv4Hand.HANDHEIGHT / 2.0F) / 1.0F);
      CRAFTLEFTPOSTION = new Vector2((0.0F + (float)BossLv4Craft.CRAFT1WIDTH / 2.0F) / 1.0F, (224.0F + (float)BossLv4Craft.CRAFT1HEIGHT / 2.0F) / 1.0F);
      CRAFTRIGHTPOSTION = new Vector2((419.0F + (float)BossLv4Craft.CRAFT1WIDTH / 2.0F) / 1.0F, (224.0F + (float)BossLv4Craft.CRAFT1HEIGHT / 2.0F) / 1.0F);
   }

   public BossLv4(World var1, float var2, float var3) {
      super(var1, Settings.lapFixBossDegree(1000), var2, var3, (float)SHADOWWIDTH / 1.0F, (float)SHADOWHEIGHT / 1.0F);
      this.head = new BossLv4Head(var1, this.bounds.x + HEADPOSTION.x, this.bounds.y + HEADPOSTION.y);
      this.hand = new BossLv4Hand(var1, this.bounds.x + HANDPOSTION.x, this.bounds.y + HANDPOSTION.y, this);
      this.craft1 = new BossLv4Craft(var1, this.bounds.x + CRAFTLEFTPOSTION.x, this.bounds.y + CRAFTLEFTPOSTION.y);
      this.craft2 = new BossLv4Craft(var1, this.bounds.x + CRAFTRIGHTPOSTION.x, this.bounds.y + CRAFTRIGHTPOSTION.y);
      this.hitRectangles = new ArrayList(19);
      this.hitMap = new Enemy.HitMapItem[19];

      for(int var4 = 0; var4 < 19; ++var4) {
         this.hitMap[var4] = new Enemy.HitMapItem();
      }

      this.needShowWarning = true;
      this.world.addObjectToBGM(this);
   }

   public static Enemy addABossLv4(World var0, float var1, float var2, float var3, List<Enemy> var4) {
      return EnemyProXY.addAEnemy(new BossLv4(var0, var1, var2), var3, var4, true);
   }

   public static void loadResource() {
      shadowTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_shadow");
      bodyTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_body");
   }

   public void beHitByBullet(Bullet var1, int var2) {
      if(1 != this.state) {
         if(this.hitMap[var2].e == this) {
            super.beHitByBullet(var1);
         } else {
            this.hitMap[var2].e.beHitByBullet(var1, this.hitMap[var2].index);
         }

         GameScreen.bossHealthyDegree = this.getHealthyDgree();
      }
   }

   public void enemyDropped(EnemyProXY var1) {
      this.world.askBeginFighting(1.5F);
      this.world.playBossMusic();
      this.dropTime = Clock.getCurrentTime();
   }

   public boolean getAllMissleCannonCrash() {
      return this.head.getAllMissleCannonCrash();
   }

   public float getDropX() {
      return this.head.getPositionX();
   }

   public float getDropY() {
      return this.head.getPositionY();
   }

   public int getHeadState() {
      return this.head.getHeadState();
   }

   public int getHealthyDgree() {
      int var1;
      if(this.hand.getHealthyDgree() > 0) {
         var1 = this.hand.getHealthyDgree();
      } else {
         var1 = 0;
      }

      int var2 = 0 + var1;
      int var3;
      if(this.head.getHealthyDgree() > 0) {
         var3 = this.head.getHealthyDgree();
      } else {
         var3 = 0;
      }

      int var4 = var2 + var3;
      int var5;
      if(this.craft1.getHealthyDgree() > 0) {
         var5 = this.craft1.getHealthyDgree();
      } else {
         var5 = 0;
      }

      int var6 = var4 + var5;
      int var7 = this.craft2.getHealthyDgree();
      int var8 = 0;
      if(var7 > 0) {
         var8 = this.craft2.getHealthyDgree();
      }

      return var6 + var8;
   }

   public List<Rectangle> getHitRectangle() {
      this.hitRectangles.clear();
      boolean var1 = this.hand.isCrashed();
      int var2 = 0;
      if(!var1) {
         List var15 = this.hand.getHitRectangle();
         int var16 = var15.size();

         for(int var17 = 0; var17 < var16; ++var17) {
            this.hitRectangles.add((Rectangle) var15.get(var17));
            this.hitMap[var2].e = this.hand;
            this.hitMap[var2].index = var17;
            ++var2;
         }
      }

      if(!this.craft1.isCrashed()) {
         List var11 = this.craft1.getHitRectangle();
         int var12 = var11.size();

         for(int var13 = 0; var13 < var12; ++var13) {
            this.hitRectangles.add((Rectangle)var11.get(var13));
            this.hitMap[var2].e = this.craft1;
            this.hitMap[var2].index = var13;
            ++var2;
         }
      }

      if(!this.craft2.isCrashed()) {
         List var7 = this.craft2.getHitRectangle();
         int var8 = var7.size();

         for(int var9 = 0; var9 < var8; ++var9) {
            this.hitRectangles.add((Rectangle)var7.get(var9));
            this.hitMap[var2].e = this.craft2;
            this.hitMap[var2].index = var9;
            ++var2;
         }
      }

      if(!this.head.isCrashed()) {
         List var3 = this.head.getHitRectangle();
         int var4 = var3.size();

         for(int var5 = 0; var5 < var4; ++var5) {
            this.hitRectangles.add((Rectangle)var3.get(var5));
            this.hitMap[var2].e = this.head;
            this.hitMap[var2].index = var5;
            ++var2;
         }
      }

      return this.hitRectangles;
   }

   public float getTimeOfCrash() {
      return 18.0F;
   }

   public boolean isCrashed() {
      if(this.hand.isCrashed() && this.head.isCrashed() && this.craft1.isCrashed() && this.craft2.isCrashed()) {
         if(!super.isCrashed()) {
            Settings.bossKilled(4, Clock.getCurrentTime(this.dropTime));
            this.makeCrash();
         }

         return true;
      } else {
         return false;
      }
   }

   public void render(SpriteBatch var1) {
      var1.draw(bodyTextureRegion, this.bounds.x + BODYPOSTION.x, this.bounds.y + BODYPOSTION.y, this.origin.x, this.origin.y, (float)BODYWIDTH / 1.0F, (float)BODYHEIGHT / 1.0F, 1.0F, 1.0F, this.angle);
      this.head.render(var1);
      this.hand.render(var1);
      this.craft1.render(var1);
      this.craft2.render(var1);
   }

   public void renderObject(SpriteBatch var1) {
      var1.draw(shadowTextureRegion, this.bounds.x, this.bounds.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, 1.0F, 1.0F, this.angle);
   }

   public void setSleepPosition(float var1, float var2) {
      super.setSleepPosition(var1, var2);
      this.head.setSleepPosition(this.bounds.x + HEADPOSTION.x, this.bounds.y + HEADPOSTION.y);
      this.hand.setSleepPosition(this.bounds.x + HANDPOSTION.x, this.bounds.y + HANDPOSTION.y);
      this.craft1.setSleepPosition(this.bounds.x + CRAFTLEFTPOSTION.x, this.bounds.y + CRAFTLEFTPOSTION.y);
      this.craft2.setSleepPosition(this.bounds.x + CRAFTRIGHTPOSTION.x, this.bounds.y + CRAFTRIGHTPOSTION.y);
   }

   public void update(float var1) {
      super.update(var1);
      this.head.update(var1);
      this.hand.update(var1);
      this.craft1.update(var1);
      this.craft2.update(var1);
   }

   public void updateShooting(float var1) {}
}
