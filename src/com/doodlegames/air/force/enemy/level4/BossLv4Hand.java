package com.doodlegames.air.force.enemy.level4;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.enemy.level4.BossLv4;
import com.doodlegames.air.force.enemy.level4.BossLv4Cannon;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.BulletScreen;
import com.doodlegames.air.force.game.bullet.EnemyBullet2;
import com.doodlegames.air.force.resource.Assets_level4;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class BossLv4Hand extends Enemy implements EnemyBullet2.MissleFixer {

   public static  int BASEHEIGHT = 0;
   public static final Vector2 BASEPOSITION;
   public static  int BASEWIDTH = 0;
   public static final Vector2 BULLETPOSITION;
   public static final Vector2 CANNONPOSITION;
   public static final int DEFAULTHEALTHYDEGREE = 150;
   private static final float DELTAY = 45.0F;
   public static  int HANDHEIGHT = 0;
   public static  int HANDWIDTH = 0;
   public static  int LOGOHEIGHT = 0;
   public static final Vector2 LOGOPOSITION;
   public static  int LOGOWIDTH = 0;
   private static final int PARTSNUM = 2;
   private static final float TIMEOFPLAYCRASHANIMATION = 1.0F;
   public static final Vector2 VELBULLET = new Vector2(0.0F, -400.0F);
   public static TextureAtlas.AtlasRegion baseTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_base");
   public static TextureAtlas.AtlasRegion handBroTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_hand_broken");
   public static TextureAtlas.AtlasRegion handTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_hand");
   private static float hitRecRelativeX;
   private static float hitRecRelativeY;
   public static TextureAtlas.AtlasRegion logoTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_nazi_logo");
   private float[][][] bulletScreens;
   private BossLv4Cannon cannon;
   private int currentStateIndex = 0;
   private Enemy.HitMapItem[] hitMap;
   private Rectangle hitR;
   private List<Rectangle> hitRectangles;
   private Clock moveClock;
   private BossLv4 parent;
   private BossLv4Hand.ShootState[] shootStates;


   static {
      int var0;
      if(logoTextureRegion.rotate) {
         var0 = logoTextureRegion.getRegionHeight();
      } else {
         var0 = logoTextureRegion.getRegionWidth();
      }

      LOGOWIDTH = var0;
      int var1;
      if(logoTextureRegion.rotate) {
         var1 = logoTextureRegion.getRegionWidth();
      } else {
         var1 = logoTextureRegion.getRegionHeight();
      }

      LOGOHEIGHT = var1;
      LOGOPOSITION = new Vector2(-14.0F, 3.0F);
      int var2;
      if(handTextureRegion.rotate) {
         var2 = handTextureRegion.getRegionHeight();
      } else {
         var2 = handTextureRegion.getRegionWidth();
      }

      HANDWIDTH = var2;
      int var3;
      if(handTextureRegion.rotate) {
         var3 = handTextureRegion.getRegionWidth();
      } else {
         var3 = handTextureRegion.getRegionHeight();
      }

      HANDHEIGHT = var3;
      int var4;
      if(baseTextureRegion.rotate) {
         var4 = baseTextureRegion.getRegionHeight();
      } else {
         var4 = baseTextureRegion.getRegionWidth();
      }

      BASEWIDTH = var4;
      int var5;
      if(baseTextureRegion.rotate) {
         var5 = baseTextureRegion.getRegionWidth();
      } else {
         var5 = baseTextureRegion.getRegionHeight();
      }

      BASEHEIGHT = var5;
      BASEPOSITION = new Vector2(-14.0F, -46.0F);
      CANNONPOSITION = new Vector2((17.0F + (float)BossLv4Cannon.WIDTH / 2.0F) / 1.0F, (-39.0F + (float)BossLv4Cannon.HEIGHT / 2.0F) / 1.0F);
      BULLETPOSITION = new Vector2(27.0F, 161.0F);
      hitRecRelativeX = 7.0F;
      hitRecRelativeY = 133.0F;
   }

   public BossLv4Hand(World var1, float var2, float var3, BossLv4 var4) {
      super(var1, Settings.lapFixBossDegree(150), var2, var3, (float)HANDWIDTH / 1.0F, (float)HANDHEIGHT / 1.0F);
      float[][][] var5 = new float[][][]{BulletScreen.bulletVelAngles[45], BulletScreen.bulletVelAngles[46], BulletScreen.bulletVelAngles[47]};
      this.bulletScreens = var5;
      this.parent = var4;
      this.enemyRegion = handTextureRegion;
      this.enemyBroRegion = handBroTextureRegion;
      this.hitR = new Rectangle(this.bounds.x + hitRecRelativeX, this.bounds.y + hitRecRelativeY, 40.0F, 90.0F);
      this.hitRectangles = new ArrayList(2);
      this.hitMap = new Enemy.HitMapItem[2];

      for(int var6 = 0; var6 < 2; ++var6) {
         this.hitMap[var6] = new Enemy.HitMapItem();
      }

      this.cannon = new BossLv4Cannon(var1, this.bounds.x + CANNONPOSITION.x, this.bounds.y + CANNONPOSITION.y);
      BossLv4Hand.ShootState[] var7 = new BossLv4Hand.ShootState[]{new BossLv4Hand.ShootStateShootScreen1(), new BossLv4Hand.ShootStateShoot2Missle(), new BossLv4Hand.ShootStateShoot6Missle()};
      this.shootStates = var7;
      System.out.println("length:" + this.shootStates.length);
      this.currentStateIndex = 0;
      this.moveClock = Clock.getFireOnceClock(1.0F);
   }

   private float getIniBoundX() {
      return this.parent.bounds.x + BossLv4.HANDPOSTION.x - this.bounds.width / 2.0F;
   }

   private float getIniBoundY() {
      return this.parent.bounds.y + BossLv4.HANDPOSTION.y - this.bounds.height / 2.0F;
   }

   public static void loadResource() {
      logoTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_nazi_logo");
      handTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_hand");
      handBroTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_hand_broken");
      baseTextureRegion = Assets_level4.atlas_Enemy.findRegion("lv_4_boss_base");
   }

   public void beHitByBullet(Bullet var1, int var2) {
      if(1 != this.state) {
         if(this.hitMap[var2].e == this) {
            super.beHitByBullet(var1);
         } else {
            this.hitMap[var2].e.beHitByBullet(var1, this.hitMap[var2].index);
         }

         if(this.isCrashed() && this.state != 1) {
            this.makeCrash();
            return;
         }
      }

   }

   public void fixMissle(EnemyBullet2 var1) {
      BossLv4Hand.ShootState var2 = this.shootStates[this.currentStateIndex];
      if(var2 instanceof BossLv4Hand.ShootStateShoot2Missle) {
         var1.setTurnCountMax(10);
         var1.addGameAction(EnemyBullet2.getSpeedUp(var1, 0.5F, 1.0F, 300.0F));
      }

      if(var2 instanceof BossLv4Hand.ShootStateShoot6Missle) {
         var1.setTurnCountMax(10);
         var1.addGameAction(EnemyBullet2.getSpeedUp(var1, 2.0F, 1.0F, 300.0F));
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

      if(!super.isCrashed()) {
         this.hitR.x = this.bounds.x + hitRecRelativeX;
         this.hitR.y = this.bounds.y + hitRecRelativeY;
         this.hitRectangles.add(this.hitR);
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
      return super.isCrashed() && this.cannon.isCrashed();
   }

   public void moveHandUpDown(float var1) {
      if(!this.isCrashed() && this.moveClock.isFired()) {
         float var2 = this.bounds.y;
         float var3 = this.getPositionX();
         float var4 = this.getIniBoundY();
         float var5 = var4 - 45.0F;
         if(var2 > var4 - 2.0F) {
            this.addEnemyAction(EnemyTools.moveTo(this, var3, var5 + this.bounds.height / 2.0F, 1.0F));
         }

         if(var2 < var5 + 2.0F) {
            this.addEnemyAction(EnemyTools.moveTo(this, var3, var4 + this.bounds.height / 2.0F, 1.0F));
         }

         this.moveClock.resetClockFireOnce(5.0F + 3.0F * this.world.rand.nextFloat());
      }
   }

   public void render(SpriteBatch var1) {
      var1.draw(logoTextureRegion, this.getIniBoundX() + LOGOPOSITION.x, this.getIniBoundY() + LOGOPOSITION.y, this.origin.x, this.origin.y, (float)LOGOWIDTH / 1.0F, (float)LOGOHEIGHT / 1.0F, 1.0F, 1.0F, this.angle);
      super.render(var1);
      var1.draw(baseTextureRegion, this.getIniBoundX() + BASEPOSITION.x, this.getIniBoundY() + BASEPOSITION.y, this.origin.x, this.origin.y, (float)BASEWIDTH / 1.0F, (float)BASEHEIGHT / 1.0F, 1.0F, 1.0F, this.angle);
      this.cannon.render(var1);
   }

   public void setSleepPosition(float var1, float var2) {
      super.setSleepPosition(var1, var2);
      this.cannon.setSleepPosition(this.bounds.x + CANNONPOSITION.x, this.bounds.y + CANNONPOSITION.y);
   }

   public void update(float var1) {
      super.update(var1);
      this.cannon.update(var1);
      this.moveHandUpDown(var1);
   }

   public void updateShooting(float var1) {
      if(this.shootStates[this.currentStateIndex].updateShooting(var1)) {
         int var2 = this.shootStates.length;
         if(!this.parent.getAllMissleCannonCrash()) {
            var2 = -2 + this.shootStates.length;
         } else if(this.parent.getHeadState() == 1) {
            var2 = -1 + this.shootStates.length;
         }

         if(var2 < 0) {
            throw new RuntimeException("shootStates\'s range is invalid:" + var2 + ".");
         }

         this.currentStateIndex = this.world.rand.nextInt(var2);
         this.shootStates[this.currentStateIndex].stateInit();
      }

   }

   public class ShootStateShoot2Missle implements BossLv4Hand.ShootState {

      float currentTime = 0.0F;
      float delayTime = 1.0F;
      BossLv4Hand wThis = BossLv4Hand.this;


      public void stateInit() {
         this.currentTime = 0.0F;
         this.delayTime = 3.0F + 3.0F * Clock.rand.nextFloat();
      }

      public boolean updateShooting(float var1) {
         if(this.currentTime > this.delayTime) {
            return true;
         } else {
            if(this.currentTime == 0.0F) {
               float var2 = BossLv4Hand.BULLETPOSITION.x;
               float var3 = BossLv4Hand.BULLETPOSITION.y;

               for(int var4 = 0; var4 < 2; ++var4) {
                  float var5 = 1.0F * (float)var4;
                  this.wThis.addEnemyAction(EnemyTools.naziShootMissle(this.wThis, var5, 100.0F, var2, var3));
               }
            }

            this.currentTime += var1;
            return false;
         }
      }
   }

   public interface ShootState {

      void stateInit();

      boolean updateShooting(float var1);
   }

   public class ShootStateShootScreen2 implements BossLv4Hand.ShootState {

      float currentTime = 0.0F;
      float delayTime = 1.0F;
      BossLv4Hand wThis = BossLv4Hand.this;


      public void stateInit() {
         this.currentTime = 0.0F;
         this.delayTime = 3.0F + 3.0F * Clock.rand.nextFloat();
      }

      public boolean updateShooting(float var1) {
         if(this.currentTime > this.delayTime) {
            return true;
         } else {
            if(this.currentTime == 0.0F) {
               EnemyTools.naziShootScreen(this.wThis, BossLv4Hand.BULLETPOSITION.x, BossLv4Hand.BULLETPOSITION.y, BulletScreen.chooseScreen(BossLv4Hand.this.bulletScreens));
            }

            this.currentTime += var1;
            return false;
         }
      }
   }

   public class ShootStateShoot6Missle implements BossLv4Hand.ShootState {

      float currentTime = 0.0F;
      float delayTime = 1.0F;
      BossLv4Hand wThis = BossLv4Hand.this;


      public void stateInit() {
         this.currentTime = 0.0F;
         this.delayTime = 3.0F + 2.0F * Clock.rand.nextFloat();
      }

      public boolean updateShooting(float var1) {
         if(this.currentTime > this.delayTime) {
            return true;
         } else {
            if(this.currentTime == 0.0F) {
               float var2 = BossLv4Hand.BULLETPOSITION.x;
               float var3 = BossLv4Hand.BULLETPOSITION.y;

               for(int var4 = 0; var4 < 6; ++var4) {
                  float var5 = (float)(-150 + var4 * 60);
                  this.wThis.addEnemyAction(EnemyTools.naziShootMissle(this.wThis, 0.0F, 80.0F, var2, var3, var5));
               }
            }

            this.currentTime += var1;
            return false;
         }
      }
   }

   public class ShootStateShootScreen1 implements BossLv4Hand.ShootState {

      float currentTime = 0.0F;
      float delayTime = 1.0F;
      BossLv4Hand wThis = BossLv4Hand.this;


      public void stateInit() {
         this.currentTime = 0.0F;
         this.delayTime = 3.0F + 3.0F * Clock.rand.nextFloat();
      }

      public boolean updateShooting(float var1) {
         if(this.currentTime > this.delayTime) {
            return true;
         } else {
            if(this.currentTime == 0.0F) {
               EnemyTools.naziShootScreen(this.wThis, BossLv4Hand.BULLETPOSITION.x, BossLv4Hand.BULLETPOSITION.y, BulletScreen.getPublicScreen(0, BossLv4Hand.this.bulletScreens));
            }

            this.currentTime += var1;
            return false;
         }
      }
   }
}
