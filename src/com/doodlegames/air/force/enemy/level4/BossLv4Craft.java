package com.doodlegames.air.force.enemy.level4;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.level1.Ufo_a;
import com.doodlegames.air.force.enemy.level1.Ufo_b;
import com.doodlegames.air.force.enemy.level2.LandCannon;
import com.doodlegames.air.force.enemy.level2.Tank;
import com.doodlegames.air.force.enemy.level4.AlienTank;
import com.doodlegames.air.force.enemy.level4.ChildEnemyMoveActions;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.resource.Assets_level4;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class BossLv4Craft extends Enemy {

   private static final Vector2 CANNONPOSITION;
   public static  int CRAFT1HEIGHT = 0;
   public static int CRAFT1WIDTH = 0;
   public static  int CRAFT2HEIGHT = 0;
   public static final Vector2 CRAFT2POSITION;
   public static  int CRAFT2WIDTH = 0;
   private static final Vector2 CRAFTPOSITION1;
   private static final Vector2 CRAFTPOSITION2;
   public static final int DEFAULTHEALTHYDEGREE = 150;
   private static final int PARTSNUM = 1;
   private static final float TIMEOFPLAYCRASHANIMATION = 1.0F;
   public static TextureAtlas.AtlasRegion craft1BroTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_craft_1_broken");
   public static TextureAtlas.AtlasRegion craft1TextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_craft", 1);
   public static TextureAtlas.AtlasRegion craft2BroTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_craft_2_broken");
   public static TextureAtlas.AtlasRegion craft2TextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_craft", 2);
   private LandCannon cannon;
   private Clock clockCraft1;
   private Clock clockCraft2;
   private Enemy.HitMapItem[] hitMap;
   private List<Rectangle> hitRectangles;


   static {
      int var0;
      if(craft1TextureRegion.rotate) {
         var0 = craft1TextureRegion.getRegionHeight();
      } else {
         var0 = craft1TextureRegion.getRegionWidth();
      }

      CRAFT1WIDTH = var0;
      int var1;
      if(craft1TextureRegion.rotate) {
         var1 = craft1TextureRegion.getRegionWidth();
      } else {
         var1 = craft1TextureRegion.getRegionHeight();
      }

      CRAFT1HEIGHT = var1;
      int var2;
      if(craft2TextureRegion.rotate) {
         var2 = craft2TextureRegion.getRegionHeight();
      } else {
         var2 = craft2TextureRegion.getRegionWidth();
      }

      CRAFT2WIDTH = var2;
      int var3;
      if(craft2TextureRegion.rotate) {
         var3 = craft2TextureRegion.getRegionWidth();
      } else {
         var3 = craft2TextureRegion.getRegionHeight();
      }

      CRAFT2HEIGHT = var3;
      CRAFT2POSITION = new Vector2(0.0F, 238.0F);
      CANNONPOSITION = new Vector2(30.0F, 75.5F);
      CRAFTPOSITION1 = new Vector2(30.0F, 254.0F);
      CRAFTPOSITION2 = new Vector2(30.0F, 17.0F);
   }

   public BossLv4Craft(World var1, float var2, float var3) {
      super(var1, Settings.lapFixBossDegree(150), var2, var3, (float)CRAFT1WIDTH / 1.0F, (float)CRAFT1HEIGHT / 1.0F);
      this.enemyRegion = craft1TextureRegion;
      this.enemyBroRegion = craft1BroTextureRegion;
      this.cannon = new LandCannon(var1, this.bounds.x + CANNONPOSITION.x, this.bounds.y + CANNONPOSITION.y);
      this.cannon.setInitHealthyDgree(30);
      this.clockCraft1 = new Clock((float)(10 + Clock.rand.nextInt(5)), 20.0F);
      this.clockCraft2 = new Clock((float)Clock.rand.nextInt(5), 20.0F);
      this.hitRectangles = new ArrayList(1);
      this.hitMap = new Enemy.HitMapItem[1];

      for(int var4 = 0; var4 < 1; ++var4) {
         this.hitMap[var4] = new Enemy.HitMapItem();
      }

   }

   public static void loadResource() {
      craft1TextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_craft", 1);
      craft2TextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_craft", 2);
      craft1BroTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_craft_1_broken");
      craft2BroTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_craft_2_broken");
   }

   public void beHitByBullet(Bullet var1, int var2) {
      if(this.state != 1) {
         if(this.hitMap[var2].e == this) {
            super.beHitByBullet(var1);
         } else {
            this.hitMap[var2].e.beHitByBullet(var1, var2);
         }
      }
   }

   public List<Rectangle> getHitRectangle() {
      this.hitRectangles.clear();
      boolean var1 = this.cannon.isCrashed();
      int var2 = 0;
      if(!var1) {
         List var5 = this.cannon.getHitRectangle();
         int var6 = var5.size();

         for(int var7 = 0; var7 < var6; ++var7) {
            this.hitRectangles.add((Rectangle)var5.get(var7));
            this.hitMap[var2].e = this.cannon;
            this.hitMap[var2].index = var7;
            ++var2;
         }
      }

      if(this.cannon.isCrashed() && !super.isCrashed()) {
         this.hitRectangles.add(this.bounds);
         this.hitMap[var2].e = this;
         this.hitMap[var2].index = 0;
         int var10000 = var2 + 1;
      }

      return this.hitRectangles;
   }

   public float getTimeOfCrash() {
      return 1.0F;
   }

   public boolean isCrashed() {
      return this.cannon.isCrashed() && super.isCrashed();
   }

   public void render(SpriteBatch var1) {
      TextureAtlas.AtlasRegion var2;
      if(this.isCrashed()) {
         var2 = craft2BroTextureRegion;
      } else {
         var2 = craft2TextureRegion;
      }

      super.render(var1);
      var1.draw(var2, this.bounds.x + CRAFT2POSITION.x, this.bounds.y + CRAFT2POSITION.y, this.origin.x, this.origin.y, (float)CRAFT2WIDTH / 1.0F, (float)CRAFT2HEIGHT / 1.0F, 1.0F, 1.0F, this.angle);
      this.cannon.render(var1);
   }

   public void setSleepPosition(float var1, float var2) {
      super.setSleepPosition(var1, var2);
      this.cannon.setSleepPosition(this.bounds.x + CANNONPOSITION.x, this.bounds.y + CANNONPOSITION.y);
   }

   public void update(float var1) {
      super.update(var1);
      this.cannon.setPosition(this.bounds.x + CANNONPOSITION.x, this.bounds.y + CANNONPOSITION.y);
      this.cannon.update(var1);
   }

   public void updateShooting(float var1) {
      if(this.clockCraft1.isFired()) {
         int var6 = this.world.rand.nextInt(2);
         if(var6 == 0) {
            Ufo_a var9 = (Ufo_a)this.world.Ufo_aPool.obtain();
            var9.reset(this.world, this.bounds.x + CRAFTPOSITION1.x, this.bounds.y + CRAFTPOSITION1.y);
            ChildEnemyMoveActions.addBezierPursue(var9, this.world.fighter);
            var9.setBulletCount(1);
            this.world.enemysOnAir.add(var9);
         } else if(var6 == 1) {
            Ufo_b var7 = (Ufo_b)this.world.Ufo_bPool.obtain();
            var7.reset(this.world, this.bounds.x + CRAFTPOSITION1.x, this.bounds.y + CRAFTPOSITION1.y);
            var7.setBulletCount(1);
            ChildEnemyMoveActions.addBezierPursue(var7, this.world.fighter);
            this.world.enemysOnAir.add(var7);
         }
      }

      if(this.clockCraft2.isFired()) {
         switch(this.world.rand.nextInt(2)) {
         case 0:
            Tank var4 = (Tank)this.world.TankPool.obtain();
            var4.reset(this.world, this.bounds.x + CRAFTPOSITION2.x, this.bounds.y + CRAFTPOSITION2.y);
            ChildEnemyMoveActions.addDotMoveAction(var4);
            this.world.enemysOnSeaOrLand.add(var4);
            return;
         case 1:
            AlienTank var2 = (AlienTank)this.world.AlienTankPool.obtain();
            var2.reset(this.world, this.bounds.x + CRAFTPOSITION2.x, this.bounds.y + CRAFTPOSITION2.y);
            ChildEnemyMoveActions.addDotMoveAction(var2);
            this.world.enemysOnSeaOrLand.add(var2);
            return;
         }
      }

   }
}
