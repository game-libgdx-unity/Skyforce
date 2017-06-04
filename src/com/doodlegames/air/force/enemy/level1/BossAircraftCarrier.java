package com.doodlegames.air.force.enemy.level1;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyProXY;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.enemy.HitDistribute;
import com.doodlegames.air.force.enemy.cannon.CommonCannon;
import com.doodlegames.air.force.enemy.level1.AirCraftOnBoss;
import com.doodlegames.air.force.enemy.level1.RailGunOnBoss;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldAnimationPlayer;
import com.doodlegames.air.force.game.WorldAudio;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.resource.Assets_level1;
import com.doodlegames.air.force.screen.GameScreen;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BossAircraftCarrier extends Enemy {

   public static final int DEFAULTHEALTHYDEGREE = 1000;
   private static final Vector2[] ORIGINS;
   private static final Vector2[] RELATIVEPOSITION;
   public static final float ROTATEPAUSE = 2.0F;
   private static final float TIMEOFPLAYCRASHANIMATION = 15.0F;
   public static final int WEAPONCOUNT;
   public static final int[] WEAPONTYPE;
   protected static TextureRegion enemyTextBroRegion = Assets_level1.findRegion("boss_broken");
   protected static TextureRegion enemyTextureRegion = Assets_level1.findRegion("boss");
   private ArrayList<AirCraftOnBoss> acArray;
   private ArrayList<CommonCannon> cannonArray;
   private int currentPhase = 0;
   float dropTime;
   private HitDistribute hitDistribute;
   CommonCannon.ParentListener parentListener1;
   CommonCannon.ParentListener parentListener2;
   CommonCannon.ParentListener parentListener3;
   CommonCannon.ParentListener parentListener4;
   private TextureRegion[] regions;
   private ArrayList<RailGunOnBoss> rgArray;
   private BossAircraftCarrier.AngleChanger rotateBossBy90;


   static {
      Vector2[] var0 = new Vector2[]{new Vector2(12.0F, 21.0F), new Vector2(15.0F, 15.0F), new Vector2(51.0F, 31.0F), new Vector2(35.0F, 52.0F)};
      ORIGINS = var0;
      WEAPONTYPE = new int[]{0, 0, 1, 2, 2, 0, 0, 1, 3, 0, 0, 2, 2, 3, 0, 0, 1, 2, 2, 3};
      WEAPONCOUNT = WEAPONTYPE.length;
      Vector2[] var1 = new Vector2[]{new Vector2(86.0F, 69.0F), new Vector2(249.0F, 69.0F), new Vector2(164.0F, 17.0F), new Vector2(47.0F, -20.0F), new Vector2(209.0F, -20.0F), new Vector2(375.0F, 198.0F), new Vector2(375.0F, 361.0F), new Vector2(341.0F, 285.0F), new Vector2(419.0F, 248.0F), new Vector2(246.0F, 487.0F), new Vector2(83.0F, 487.0F), new Vector2(207.0F, 556.0F), new Vector2(45.0F, 556.0F), new Vector2(142.0F, 523.0F), new Vector2(-43.0F, 358.0F), new Vector2(-43.0F, 195.0F), new Vector2(-15.0F, 283.0F), new Vector2(-161.0F, 348.0F), new Vector2(-161.0F, 186.0F), new Vector2(-133.0F, 246.0F)};
      RELATIVEPOSITION = var1;
   }

   public BossAircraftCarrier(World var1, float var2, float var3) {
      super(var1, 1000, var2, var3, (float)enemyTextureRegion.getRegionWidth() / 1.0F, (float)enemyTextureRegion.getRegionHeight() / 1.0F);
      TextureRegion[] var4 = new TextureRegion[]{enemyTextureRegion, enemyTextureRegion, enemyTextureRegion, enemyTextureRegion};
      this.regions = var4;
      this.dropTime = 0.0F;
      this.parentListener1 = new CommonCannon.ParentListener() {
         public boolean parentAllowAttack() {
            return BossAircraftCarrier.this.checkPhaseChange() == 1 && BossAircraftCarrier.this.isNotTurning();
         }
         public boolean parentAllowHit() {
            return BossAircraftCarrier.this.checkPhaseChange() == 1 && BossAircraftCarrier.this.isNotTurning();
         }
      };
      this.parentListener2 = new CommonCannon.ParentListener() {
         public boolean parentAllowAttack() {
            return BossAircraftCarrier.this.checkPhaseChange() == 2 && BossAircraftCarrier.this.isNotTurning();
         }
         public boolean parentAllowHit() {
            return BossAircraftCarrier.this.checkPhaseChange() == 2 && BossAircraftCarrier.this.isNotTurning();
         }
      };
      this.parentListener3 = new CommonCannon.ParentListener() {
         public boolean parentAllowAttack() {
            return BossAircraftCarrier.this.checkPhaseChange() == 3 && BossAircraftCarrier.this.isNotTurning();
         }
         public boolean parentAllowHit() {
            return BossAircraftCarrier.this.checkPhaseChange() == 3 && BossAircraftCarrier.this.isNotTurning();
         }
      };
      this.parentListener4 = new CommonCannon.ParentListener() {
         public boolean parentAllowAttack() {
            return BossAircraftCarrier.this.checkPhaseChange() == 4 && BossAircraftCarrier.this.isNotTurning();
         }
         public boolean parentAllowHit() {
            return BossAircraftCarrier.this.checkPhaseChange() == 4 && BossAircraftCarrier.this.isNotTurning();
         }
      };
      this.rotateBossBy90 = new BossAircraftCarrier.AngleChanger() {

         float durationTime = 0.0F;
         float initAngle = 0.0F;
         boolean rotateIsOver = true;

         public void reset() {
            this.rotateIsOver = false;
            this.durationTime = 0.0F;
            this.initAngle = BossAircraftCarrier.this.getAngle();
            BossAircraftCarrier.this.world.askStopFighting();
         }
         public void updateAngleChanger(float var1) {
            if(!this.rotateIsOver) {
               this.durationTime += var1;
               if(this.durationTime > 2.0F) {
                  if(BossAircraftCarrier.this.angularSpeed == 0.0F) {
                     BossAircraftCarrier.this.angularSpeed = -20.0F;
                  } else if(Math.abs(BossAircraftCarrier.this.getAngle() - this.initAngle) > 90.0F) {
                     BossAircraftCarrier.this.angularSpeed = 0.0F;
                     BossAircraftCarrier.this.setAngle(this.initAngle - 90.0F);
                     this.rotateIsOver = true;
                     BossAircraftCarrier.this.world.askBeginFighting();
                  }
               } else {
                  this.initAngle = BossAircraftCarrier.this.getAngle();
               }

               if(this.durationTime > 0.7F) {
                  BossAircraftCarrier.this.setLastRegionCrash();
                  return;
               }
            }

         }
      };
      this.needShowWarning = true;
      this.origin.x = 178.0F;
      this.origin.y = 299.0F;
      this.acArray = new ArrayList();
      this.rgArray = new ArrayList();
      this.cannonArray = new ArrayList();
      int var5 = 0;

      while(true) {
         int var6 = WEAPONCOUNT;
         if(var5 >= var6) {
            this.hitDistribute = new HitDistribute(this, 100);
            this.regions[0] = enemyTextureRegion;
            this.regions[1] = enemyTextureRegion;
            this.regions[2] = enemyTextureRegion;
            this.regions[3] = enemyTextureRegion;
            return;
         }

         float var7 = RELATIVEPOSITION[var5].x;
         float var8 = RELATIVEPOSITION[var5].y;
         switch(WEAPONTYPE[var5]) {
         case 0:
            CommonCannon var20 = CommonCannon.getCannon1(var1, new Clock(2.0F * var1.rand.nextFloat(), 12.0F, 3.0F, (byte)3), Settings.lapFixBossDegree(20), EnemyBullet1.naziBulletGen, this, var7, var8, 300.0F);
            var20.setAutoTurn(true);
            switch(var5) {
            case 0:
            case 1:
               var20.setAngle(0.0F);
               var20.setParentListener(this.parentListener1);
            case 2:
            case 3:
            case 4:
            case 7:
            case 8:
            case 11:
            case 12:
            case 13:
            default:
               break;
            case 5:
            case 6:
               var20.setAngle(90.0F);
               var20.setParentListener(this.parentListener2);
               break;
            case 9:
            case 10:
               var20.setAngle(180.0F);
               var20.setParentListener(this.parentListener3);
               break;
            case 14:
            case 15:
               var20.setAngle(270.0F);
               var20.setParentListener(this.parentListener4);
            }

            this.cannonArray.add(var20);
            break;
         case 1:
            CommonCannon var18 = CommonCannon.getCrossCannon(var1, new Clock(3.0F + var1.rand.nextFloat(), 5.0F, 0.2F, (byte)5), Settings.lapFixBossDegree(20), EnemyBullet1.naziBulletGen, this, var7, var8, 300.0F);
            switch(var5) {
            case 2:
               var18.setParentListener(this.parentListener1);
               break;
            case 7:
               var18.setParentListener(this.parentListener2);
               break;
            case 16:
               var18.setParentListener(this.parentListener4);
            }

            var18.angularSpeed = 80.0F;
            this.cannonArray.add(var18);
            break;
         case 2:
            if(var5 != 3 && var5 == 4) {
               ;
            }

            float var13;
            label73: {
               if(var5 != 11) {
                  var13 = 0.0F;
                  if(var5 != 12) {
                     break label73;
                  }
               }

               var13 = 180.0F;
            }

            if(var5 == 17 || var5 == 18) {
               var13 = 270.0F;
            }

            boolean var14;
            if(var5 != 4 && var5 != 12 && var5 != 18) {
               var14 = false;
            } else {
               var14 = true;
            }

            AirCraftOnBoss var15 = new AirCraftOnBoss(this.world, this, var7, var8, var13, var14);
            this.acArray.add(var15);
            break;
         case 3:
            float var10 = 0.0F;
            if(var5 == 8) {
               var10 = 90.0F;
            }

            if(var5 == 13) {
               var10 = 180.0F;
            }

            if(var5 == 19) {
               var10 = 270.0F;
            }

            RailGunOnBoss var11 = new RailGunOnBoss(this.world, this, var7, var8, var10);
            this.rgArray.add(var11);
         }

         ++var5;
      }
   }

   public static Enemy addABossLv1(World var0, float var1, float var2, float var3, List<Enemy> var4) {
      EnemyProXY var5 = new EnemyProXY(var0, var3, new BossAircraftCarrier(var0, var1, var2), var4);
      var4.add(var5);
      var5.sleepRending = true;
      return var5;
   }

   private int checkPhaseChange() {
      byte var1;
      if(((CommonCannon)this.cannonArray.get(0)).isCrashed() && ((CommonCannon)this.cannonArray.get(1)).isCrashed() && ((CommonCannon)this.cannonArray.get(2)).isCrashed() && ((AirCraftOnBoss)this.acArray.get(0)).isCrashed() && ((AirCraftOnBoss)this.acArray.get(1)).isCrashed()) {
         if(((CommonCannon)this.cannonArray.get(3)).isCrashed() && ((CommonCannon)this.cannonArray.get(4)).isCrashed() && ((CommonCannon)this.cannonArray.get(5)).isCrashed() && ((RailGunOnBoss)this.rgArray.get(0)).isCrashed()) {
            if(((CommonCannon)this.cannonArray.get(6)).isCrashed() && ((CommonCannon)this.cannonArray.get(7)).isCrashed() && ((AirCraftOnBoss)this.acArray.get(2)).isCrashed() && ((AirCraftOnBoss)this.acArray.get(3)).isCrashed() && ((RailGunOnBoss)this.rgArray.get(1)).isCrashed()) {
               if(((CommonCannon)this.cannonArray.get(8)).isCrashed() && ((CommonCannon)this.cannonArray.get(9)).isCrashed() && ((CommonCannon)this.cannonArray.get(10)).isCrashed() && ((AirCraftOnBoss)this.acArray.get(4)).isCrashed() && ((AirCraftOnBoss)this.acArray.get(5)).isCrashed() && ((RailGunOnBoss)this.rgArray.get(2)).isCrashed()) {
                  var1 = 5;
               } else {
                  var1 = 4;
               }
            } else {
               var1 = 3;
            }
         } else {
            var1 = 2;
         }
      } else {
         var1 = 1;
      }

      if(var1 != this.currentPhase) {
         this.phaseChanged(this.currentPhase, var1);
         this.currentPhase = var1;
      }

      return var1;
   }

   public static void loadResource() {
      enemyTextureRegion = Assets_level1.findRegion("boss");
      enemyTextBroRegion = Assets_level1.findRegion("boss_broken");
   }

   private void phaseChanged(int var1, int var2) {
      switch(var2) {
      case 2:
         this.rotateBossBy90.reset();
         this.putCrashAniamtion();
         return;
      case 3:
         this.rotateBossBy90.reset();
         this.putCrashAniamtion();
         return;
      case 4:
         this.rotateBossBy90.reset();
         this.putCrashAniamtion();
         return;
      case 5:
         this.putCrashAniamtion();
         return;
      default:
      }
   }

   public void beHitByBullet(Bullet var1, int var2) {
      this.hitDistribute.beHitByBullet(var1, var2);
      GameScreen.bossHealthyDegree = this.getHealthyDgree();
   }

   public boolean canBeHit() {
      return false;
   }

   public void enemyDropped(EnemyProXY var1) {
      this.addEnemyAction(EnemyTools.getWarningOverFighter(this));
      this.world.playBossMusic();
      this.dropTime = Clock.getCurrentTime();
   }

   public int getHealthyDgree() {
      int var1 = 0;
      Iterator var2 = this.acArray.iterator();

      while(var2.hasNext()) {
         AirCraftOnBoss var7 = (AirCraftOnBoss)var2.next();
         if(var7.getHealthyDgree() > 0) {
            var1 += var7.getHealthyDgree();
         }
      }

      Iterator var3 = this.rgArray.iterator();

      while(var3.hasNext()) {
         RailGunOnBoss var6 = (RailGunOnBoss)var3.next();
         if(var6.getHealthyDgree() > 0) {
            var1 += var6.getHealthyDgree();
         }
      }

      Iterator var4 = this.cannonArray.iterator();

      while(var4.hasNext()) {
         CommonCannon var5 = (CommonCannon)var4.next();
         if(var5.getHealthyDgree() > 0) {
            var1 += var5.getHealthyDgree();
         }
      }

      return var1;
   }

   public List<Rectangle> getHitRectangle() {
      this.hitDistribute.beginAdd();
      Iterator var1 = this.acArray.iterator();

      while(var1.hasNext()) {
         AirCraftOnBoss var6 = (AirCraftOnBoss)var1.next();
         this.hitDistribute.addEnemyHitRec(var6);
      }

      Iterator var2 = this.rgArray.iterator();

      while(var2.hasNext()) {
         RailGunOnBoss var5 = (RailGunOnBoss)var2.next();
         this.hitDistribute.addEnemyHitRec(var5);
      }

      Iterator var3 = this.cannonArray.iterator();

      while(var3.hasNext()) {
         CommonCannon var4 = (CommonCannon)var3.next();
         this.hitDistribute.addEnemyHitRec(var4);
      }

      this.hitDistribute.endAdd();
      return this.hitDistribute.getHitRectangle();
   }

   public float getTimeOfCrash() {
      return 15.0F;
   }

   public boolean isCrashed() {
      Iterator var1 = this.cannonArray.iterator();

      do {
         if(!var1.hasNext()) {
            Iterator var2 = this.acArray.iterator();

            do {
               if(!var2.hasNext()) {
                  Iterator var3 = this.rgArray.iterator();

                  do {
                     if(!var3.hasNext()) {
                        if(!super.isCrashed()) {
                           Settings.bossKilled(1, Clock.getCurrentTime(this.dropTime));
                           this.makeCrash();
                        }

                        return true;
                     }
                  } while(((RailGunOnBoss)var3.next()).isCrashed());

                  return false;
               }
            } while(((AirCraftOnBoss)var2.next()).isCrashed());

            return false;
         }
      } while(((CommonCannon)var1.next()).isCrashed());

      return false;
   }

   public boolean isNotTurning() {
      return this.angularSpeed == 0.0F;
   }

   protected void putCrashAniamtion() {
      float var1 = (float)Clock.getTimeCounter();
      WorldAudio.PlaySound var2 = this.getCrashSound(true);
      Animation var3 = this.getShatterAniamtion();
      Animation var4 = this.getCrashBoom(true);
      WorldAnimationPlayer.addAWorldAnimation(var3, 320.0F, 700.0F, 1);
      WorldAnimationPlayer.addAWorldAnimation(var4, 320.0F, 700.0F, 1, var2);
      float var6 = 0.3F * this.world.rand.nextFloat();
      WorldAnimationPlayer.addAWorldAnimation(var3, 220.0F, 750.0F, 1, var1 + var6);
      WorldAnimationPlayer.addAWorldAnimation(var4, 220.0F, 750.0F, 1, var1 + var6, var2);
      WorldAnimationPlayer.addAWorldAnimation(var3, 420.0F, 750.0F, 1, var1 + 0.7F);
      WorldAnimationPlayer.addAWorldAnimation(var4, 420.0F, 750.0F, 1, var1 + 0.7F, var2);
   }

   public void render(SpriteBatch var1) {
      super.render(var1);
      Iterator var2 = this.acArray.iterator();

      while(var2.hasNext()) {
         ((AirCraftOnBoss)var2.next()).render(var1);
      }

      Iterator var3 = this.rgArray.iterator();

      while(var3.hasNext()) {
         ((RailGunOnBoss)var3.next()).render(var1);
      }

      Iterator var4 = this.cannonArray.iterator();

      while(var4.hasNext()) {
         ((CommonCannon)var4.next()).render(var1);
      }

   }

   public void renderCrash(SpriteBatch var1) {
      for(int var2 = 0; var2 < 4; ++var2) {
         var1.draw(enemyTextBroRegion, this.bounds.x, this.bounds.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, 1.0F, 1.0F, this.angle + (float)(var2 * 90));
      }

   }

   public void renderEnemy(SpriteBatch var1) {
      int var2 = this.currentPhase;

      for(int var3 = 0; var3 < 4; ++var3) {
         TextureRegion var10000;
         if(var3 + 1 < var2) {
            var10000 = enemyTextBroRegion;
         } else {
            var10000 = enemyTextureRegion;
         }

         if(this.angularSpeed == 0.0F) {
            var10000 = enemyTextureRegion;
         }

         var1.draw(this.regions[var3], this.bounds.x, this.bounds.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, 1.0F, 1.0F, this.angle + (float)(var3 * 90));
      }

   }

   public void renderShadow(SpriteBatch var1) {
      float var2 = var1.getColor().toFloatBits();
      var1.setColor(0.0F, 0.0F, 0.0F, 0.5F);
      var1.draw(enemyTextureRegion, this.position.x - this.bounds.width / 2.0F + shadowOffset.x, this.position.y - this.bounds.height / 2.0F + shadowOffset.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, 0.8F, 0.8F, this.angle);
      var1.draw(enemyTextureRegion, this.position.x - this.bounds.width / 2.0F + shadowOffset.x, this.position.y - this.bounds.height / 2.0F + shadowOffset.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, 0.8F, 0.8F, 90.0F + this.angle);
      var1.draw(enemyTextureRegion, this.position.x - this.bounds.width / 2.0F + shadowOffset.x, this.position.y - this.bounds.height / 2.0F + shadowOffset.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, 0.8F, 0.8F, 180.0F + this.angle);
      var1.draw(enemyTextureRegion, this.position.x - this.bounds.width / 2.0F + shadowOffset.x, this.position.y - this.bounds.height / 2.0F + shadowOffset.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, 0.8F, 0.8F, 270.0F + this.angle);
      var1.setColor(var2);
   }

   protected void setLastRegionCrash() {
      int var1 = -2 + this.currentPhase;
      if(var1 >= 0 && var1 < 4) {
         this.regions[var1] = enemyTextBroRegion;
      }

   }

   public void setSleepPosition(float var1, float var2) {
      super.setSleepPosition(var1, var2);
      Iterator var3 = this.cannonArray.iterator();

      while(var3.hasNext()) {
         ((CommonCannon)var3.next()).updatePosition();
      }

      Iterator var4 = this.acArray.iterator();

      while(var4.hasNext()) {
         ((AirCraftOnBoss)var4.next()).keepRelativePosition();
      }

      Iterator var5 = this.rgArray.iterator();

      while(var5.hasNext()) {
         ((RailGunOnBoss)var5.next()).keepRelativePosition();
      }

   }

   public void update(float var1) {
      this.velocity.y = Settings.backgroundVelocity.y;
      super.update(var1);
      this.checkPhaseChange();
      this.rotateBossBy90.updateAngleChanger(var1);
      Iterator var3 = this.acArray.iterator();

      while(var3.hasNext()) {
         ((AirCraftOnBoss)var3.next()).update(var1);
      }

      Iterator var4 = this.rgArray.iterator();

      while(var4.hasNext()) {
         ((RailGunOnBoss)var4.next()).update(var1);
      }

      Iterator var5 = this.cannonArray.iterator();

      while(var5.hasNext()) {
         ((CommonCannon)var5.next()).update(var1);
      }

   }

   public void updateShooting(float var1) {}

   public interface AngleChanger {

      void reset();

      void updateAngleChanger(float var1);
   }
}
