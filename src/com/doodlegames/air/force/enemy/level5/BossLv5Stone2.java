package com.doodlegames.air.force.enemy.level5;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.cannon.AlienCannon3R;
import com.doodlegames.air.force.enemy.level5.BossLv5;
import com.doodlegames.air.force.enemy.level5.BossLv5AlienCanon;
import com.doodlegames.air.force.enemy.level5.BossLv5Canon;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.resource.Assets_level5;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class BossLv5Stone2 extends Enemy {

   private static final Clock CLOCKSHOOT;
   public static final int DEFAULTHEALTHYDEGREE = 150;
   public static int HEIGHT = 0;
   private static final Rectangle[] HITREC;
   public static final int RECTANGLENUM = 4;
   public static final int WIDTH;
   private static final Vector2 bossCanonPosition;
   private static final Vector2 cannon1Position;
   private static final Vector2 cannon2Position;
   public static TextureAtlas.AtlasRegion stoneRegion = Assets_level5.atlas_Enemy.findRegion("lv_5_boss_stone_r");
   private BossLv5AlienCanon alienCannon1;
   private BossLv5AlienCanon alienCannon2;
   private BossLv5Canon bossCannon;
   private Clock clockPursueChange;
   private boolean enterState2;
   private List<Rectangle> hitRects;
   private final Vector2[] pursueOffset;
   private Rectangle[] thisHitRecs;


   static {
      int var0;
      if(stoneRegion.rotate) {
         var0 = stoneRegion.getRegionHeight();
      } else {
         var0 = stoneRegion.getRegionWidth();
      }

      WIDTH = var0;
      int var1;
      if(stoneRegion.rotate) {
         var1 = stoneRegion.getRegionWidth();
      } else {
         var1 = stoneRegion.getRegionHeight();
      }

      HEIGHT = var1;
      Rectangle[] var2 = new Rectangle[]{new Rectangle(140.0F, 27.0F, 65.0F, 170.0F), new Rectangle(206.0F, 43.0F, 26.0F, 70.0F), new Rectangle(122.0F, 50.0F, 25.0F, 30.0F), new Rectangle(26.0F, 147.0F, 132.0F, 80.0F)};
      HITREC = var2;
      cannon1Position = new Vector2((158.0F + (float)AlienCannon3R.WIDTH / 2.0F) / 1.0F, (220.0F + (float)AlienCannon3R.HEIGHT / 2.0F) / 1.0F);
      cannon2Position = new Vector2((189.0F + (float)AlienCannon3R.WIDTH / 2.0F) / 1.0F, (184.0F + (float)AlienCannon3R.HEIGHT / 2.0F) / 1.0F);
      bossCanonPosition = new Vector2((15.0F + (float)BossLv5Canon.WIDTH / 2.0F) / 1.0F, (218.0F + (float)BossLv5Canon.HEIGHT / 2.0F) / 1.0F);
      CLOCKSHOOT = new Clock(5.0F, 0.2F, (byte)6);
   }

   public BossLv5Stone2(World var1, float var2, float var3) {
      super(var1, Settings.lapFixBossDegree(150), var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.enemyRegion = stoneRegion;
      this.enemyBroRegion = null;
      this.hitRects = new ArrayList(4);
      this.hitRects.clear();
      this.thisHitRecs = new Rectangle[4];

      for(int var4 = 0; var4 < 4; ++var4) {
         this.thisHitRecs[var4] = new Rectangle(HITREC[var4]);
      }

      this.alienCannon1 = new BossLv5AlienCanon(var1, this.bounds.x + cannon1Position.x, this.bounds.y + cannon1Position.y, false);
      this.alienCannon2 = new BossLv5AlienCanon(var1, this.bounds.x + cannon2Position.x, this.bounds.y + cannon2Position.y, false);
      this.alienCannon1.bulletGun.resetShootClock(false, 1.0F, 7.0F, 0.2F, (byte)1);
      this.alienCannon2.bulletGun.resetShootClock(false, 2.7F, 7.0F, 0.2F, (byte)1);
      this.bossCannon = new BossLv5Canon(var1, this.bounds.x + bossCanonPosition.x, this.bounds.y + bossCanonPosition.y, 0.0F, 2);
      Vector2[] var5 = new Vector2[]{new Vector2(-var1.fighter.bounds.width, 0.0F), new Vector2(var1.fighter.bounds.width, 0.0F), new Vector2()};
      this.pursueOffset = var5;
      this.enterState2 = false;
      this.clockPursueChange = new Clock(0.0F, 3.0F, 0.0F, (byte)1);
   }

   public static void loadResource() {
      stoneRegion = Assets_level5.atlas_Enemy.findRegion("lv_5_boss_stone_r");
   }

   public void beHitByBullet(Bullet var1) {
      super.beHitByBullet(var1);
      if(super.isCrashed()) {
         this.alienCannon1.makeCrash();
         this.alienCannon2.makeCrash();
         this.bossCannon.makeCrash();
      }

   }

   public void enterNewState(final BossLv5.BossState bossState) {

       switch (bossState) {

           case BossLv5State2: {

               this.alienCannon1.bulletGun.resetShootClock(false, 1.0f, 5.0f, 0.2f, (byte) 1);

               this.alienCannon2.bulletGun.resetShootClock(false, 2.5f, 5.0f, 0.2f, (byte) 1);

               this.enterState2 = true;

               return;

           }

           default: {

               return;

           }

       }

   }

   public List<Rectangle> getHitRectangle() {
      this.hitRects.clear();

      for(int var1 = 0; var1 < 4; ++var1) {
         this.thisHitRecs[var1].x = this.bounds.x + HITREC[var1].x;
         this.thisHitRecs[var1].y = this.bounds.y + HITREC[var1].y;
         this.thisHitRecs[var1].width = HITREC[var1].width;
         this.thisHitRecs[var1].height = HITREC[var1].height;
         this.hitRects.add(this.thisHitRecs[var1]);
      }

      return this.hitRects;
   }

   public float getTimeOfCrash() {
      return 0.1F;
   }

   public void render(SpriteBatch var1) {
      super.render(var1);
      this.alienCannon1.render(var1);
      this.alienCannon2.render(var1);
      this.bossCannon.render(var1);
   }

   public void setSleepPosition(float var1, float var2) {
      super.setSleepPosition(var1, var2);
      this.alienCannon1.setSleepPosition(this.bounds.x + cannon1Position.x, this.bounds.y + cannon1Position.y);
      this.alienCannon2.setSleepPosition(this.bounds.x + cannon2Position.x, this.bounds.y + cannon2Position.y);
      this.bossCannon.setSleepPosition(this.bounds.x + bossCanonPosition.x, this.bounds.y + bossCanonPosition.y);
   }

   public void update(float var1) {
      super.update(var1);
      this.alienCannon1.update(var1);
      this.alienCannon2.update(var1);
      this.bossCannon.update(var1);
   }

   public void updateShooting(float var1) {}
}
