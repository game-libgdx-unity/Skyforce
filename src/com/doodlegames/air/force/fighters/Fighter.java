package com.doodlegames.air.force.fighters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.fighters.FighterTurn;
import com.doodlegames.air.force.fighters.FighterWorldBoomer;
import com.doodlegames.air.force.fighters.gun.FighterBlueGun;
import com.doodlegames.air.force.fighters.gun.FighterGun;
import com.doodlegames.air.force.fighters.gun.FighterPursueGun;
import com.doodlegames.air.force.fighters.gun.FighterRedGun;
import com.doodlegames.air.force.fighters.gun.FighterYellowGun;
import com.doodlegames.air.force.fighters.gun.WingFighter;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldAnimationPlayer;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.laser.Laser;
import com.doodlegames.air.force.game.prop.Prop;
import com.doodlegames.air.force.game.prop.PropBullet;
import com.doodlegames.air.force.game.prop.PropMegnet;
import com.doodlegames.air.force.game.prop.PropPursueBullet;
import com.doodlegames.air.force.game.prop.PropWingFighter;
import com.doodlegames.air.force.resource.Assets_ShareInNazi;
import com.doodlegames.air.force.resource.Assets_fighter;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Fighter extends DynamicGameObject implements World.BulletHitEnemyListener, FighterWorldBoomer {

   protected static final float DEFAULTATTRACTRADIS = 50.0F;
   public static final float DELAY = 0.08F;
   public static final int FIGHTER_STATE_CRASH = 3;
   public static final int FIGHTER_STATE_FLYINGTOLEFT = 1;
   public static final int FIGHTER_STATE_FLYINGTORIGHT = 2;
   public static final int FIGHTER_STATE_STAND = 0;
   public static final float INIAHEAD = 0.0F;
   private static final int INPUTDELTALIMIT = 1000;
   public static final float MOVEOUTTIME = 1.0F;
   public static final float SPAN = 0.2F;
   public static final int TRANSPARENTTIME = 3;
   public static List<Bullet> bullets = new ArrayList();
   private static Fighter.FighterState fighterState = null;
   public float appearTime;
   protected float attrackedRadis;
   protected FighterGun[] avalibleGuns;
   public int boomLimit = 1;
   protected int boomNum;
   protected FighterGun currentGun;
   protected Animation fightLeft = null;
   protected Animation fightRight = null;
   protected Animation fightStand = null;
   private final FighterTurn fighterTurn = new FighterTurn(0.2F);
   private float findTimeStamp = -10.0F;
   private boolean hasMegnet;
   private boolean hasPursueGun;
   private boolean hasWingFighter;
   protected int healthyDegree;
   private Rectangle hitRectangle = new Rectangle();
   public int iniBoom = 0;
   public int iniLives = 0;
   protected FighterGun initGun;
   public int livesLimit = 1;
   private float[] nearestDis2 = new float[]{Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE};
   private Rectangle[] nearestRec = new Rectangle[]{null, null, null, null};
   final float offsetRatio = 1.3333334F;
   private FighterPursueGun pursueGun;
   public int state;
   public float stateTime;
   private float updateTimeSpan = 0.3F;
   private WingFighter wingFighter = new WingFighter(this);
   public World world;


   public Fighter(World var1, float var2, float var3, float var4, float var5) {
      super(var2, var3, var4, var5);
      this.pursueGun = new FighterPursueGun(this, var1);
      FighterGun[] var6 = new FighterGun[]{new FighterRedGun(this), new FighterYellowGun(this), new FighterBlueGun(this)};
      this.avalibleGuns = var6;
      this.initGun = new FighterRedGun(this);
      bullets.clear();
      this.setPosition(var2, -var5 / 2.0F);
      this.velocity.set(0.0F, (var3 + 3.0F * var5 / 2.0F) / 1.0F);
      this.initFighter(var1);
      Settings.fixFighter(this);
      this.setBoomNumFull();
   }

   public static TextureRegion getKeyFrame(Fighter var0, Animation var1, Animation var2, Animation var3) {
      float var4 = var0.fighterTurn.getCurrentValue();
      return var4 == 0.0F?var3.getKeyFrame(Clock.getCurrentTime()):(var4 < 0.0F?var1.getKeyFrame(Math.abs(var4)):(var4 > 0.0F?var2.getKeyFrame(var4):null));
   }

   private float getScale() {
      float var1;
      if(this.state == 3) {
         var1 = 1.0F - this.stateTime / this.getTimeOfCrash();
      } else {
         var1 = 1.0F;
      }

      if(var1 < 0.0F || var1 > 1.0F) {
         var1 = 1.0F;
      }

      return var1;
   }

   private void playCrashAnimation() {
      WorldAnimationPlayer.addAWorldAnimation(Assets_ShareInNazi.bigBoom2, this.getPositionX(), this.getPositionY(), this.world.worldAudio.playNaziBigBoom);
   }

   public void adjustFlyingAttitude(float var1, Vector2 var2) {
      if(this.state != 3) {
         float var3 = 1.3333334F * var2.x;
         float var4 = 1.3333334F * var2.y;
         float var5 = Math.max(Math.abs(var3), Math.abs(var4));
         if(var5 > 1000.0F) {
            float var6 = 1000.0F / var5;
            var3 *= var6;
            var4 *= var6;
            Settings.appLog("inputLimit is ocurred.");
         }

         this.setPosition(var3 + this.getPositionX(), var4 + this.getPositionY());
         if(0.0F > var3 && this.state != 1) {
            this.state = 1;
            this.stateTime = 0.0F;
            this.fighterTurn.setWhichTo(-1);
         }

         if(0.0F < var3 && this.state != 2) {
            this.state = 2;
            this.stateTime = 0.0F;
            this.fighterTurn.setWhichTo(1);
         }

         if(0.0F == var3 && 0.0F == var4 && this.state != 0 && this.stateTime > 3.0F * var1) {
            this.state = 0;
            this.stateTime = 0.0F;
            this.fighterTurn.setWhichTo(0);
            return;
         }
      }

   }

   public void beHitByProp(Prop var1) {}

   public void beginCheck() {
      if(Clock.getCurrentTime(this.findTimeStamp) > this.updateTimeSpan) {
         for(int var1 = 0; var1 < this.nearestRec.length; ++var1) {
            this.nearestRec[var1] = null;
            this.nearestDis2[var1] = Float.MAX_VALUE;
         }
      }

   }

   public boolean changeGun(int var1) {
      byte var2 = 1;
      int var3 = Math.min(this.currentGun.getLevel(), 3);
      if(var1 >= 0 && var1 < this.avalibleGuns.length) {
         if(this.currentGun == this.avalibleGuns[var1]) {
            if(var3 != 3) {
               this.currentGun.increaseLevel();
               return var2==0?false:true;
            }

            var2 = 0;
         } else {
            this.currentGun = this.avalibleGuns[var1];
            this.currentGun.reset();

            for(int var4 = 0; var4 < var3 - 1; ++var4) {
               this.currentGun.increaseLevel();
            }

            if(var3 == var2) {
               this.currentGun.increaseLevel();
               return var2==0?false:true;
            }
         }
      }

      return var2==0?false:true;
   }

   public void checking(Enemy var1, List<Rectangle> var2) {
      if(Clock.getCurrentTime(this.findTimeStamp) > this.updateTimeSpan) {
         Iterator var3 = var2.iterator();

         while(var3.hasNext()) {
            Rectangle var4 = (Rectangle)var3.next();
            if(var4.y + var4.height >= 10.0F) {
               float var5 = var4.x + var4.width / 2.0F - this.getPositionX();
               float var6 = var4.y + var4.height / 2.0F - this.getPositionY();
               float var7 = var5 * var5 + var6 * var6;

               for(int var8 = 0; var8 < this.nearestRec.length; ++var8) {
                  if(var7 < this.nearestDis2[var8]) {
                     this.nearestDis2[var8] = var7;
                     this.nearestRec[var8] = var4;
                     break;
                  }
               }
            }
         }
      }

   }

   public void dropProp() {
      int var1 = this.currentGun.getLevel();
      float var2 = this.getPositionX();
      float var3 = this.getPositionY();

      label34:
      for(int var4 = 0; var4 < this.avalibleGuns.length; ++var4) {
         if(this.currentGun == this.avalibleGuns[var4]) {
            int var11 = var1 - 2;

            while(true) {
               if(var11 <= 0) {
                  break label34;
               }

               Prop.propSet.add(PropBullet.propBulletGen[var4].getAProp(this, this.world, var2, var3));
               --var11;
            }
         }
      }

      if(this.hasMegnet) {
         Prop var9 = PropMegnet.propMegnetGen.getAProp(this, this.world, var2, var3);
         var9.flyingTime = 4.0F;
         Prop.propSet.add(var9);
      }

      if(this.hasWingFighter) {
         Prop var7 = PropWingFighter.propWingFighterGen.getAProp(this, this.world, var2, var3);
         var7.flyingTime = 4.0F;
         Prop.propSet.add(var7);
      }

      if(this.hasPursueGun) {
         Prop var5 = PropPursueBullet.propPursueBulletGen.getAProp(this, this.world, var2, var3);
         var5.flyingTime = 4.0F;
         Prop.propSet.add(var5);
      }

   }

   public void endCheck() {
      if(Clock.getCurrentTime(this.findTimeStamp) > this.updateTimeSpan) {
         this.findTimeStamp = Clock.getCurrentTime();
      }

   }

   public float getAttrackedRadis() {
      return !this.isCrashed() && this.hasMegnet?this.attrackedRadis:0.0F;
   }

   public int getBoomLimit() {
      return this.boomLimit;
   }

   public int getBoomNum() {
      return this.boomNum;
   }

   public Animation getFighterAnimation() {
      return this.fightStand;
   }

   public List<Rectangle> getHitRectangle() {
      Enemy.EMPTYRECTANGELS.clear();
      if(this.appearTime >= 0.0F && this.state != 3) {
         List var1 = super.getHitRectangle();
         var1.clear();
         this.hitRectangle.set(this.bounds);
         this.hitRectangle.x = this.bounds.x + this.bounds.width / 4.0F;
         this.hitRectangle.width = this.bounds.width / 2.0F;
         this.hitRectangle.y = this.bounds.y + this.bounds.height / 4.0F;
         this.hitRectangle.height = this.bounds.height / 2.0F;
         var1.add(this.hitRectangle);
         return var1;
      } else {
         return Enemy.EMPTYRECTANGELS;
      }
   }

   public Rectangle getNearestRec() {
      return this.nearestRec[0];
   }

   public Rectangle getNearestRec(int var1) {
      if(var1 < 0) {
         var1 = -var1;
      }

      Rectangle var2 = this.nearestRec[var1 % this.nearestRec.length];
      if(var2 == null) {
         var2 = this.nearestRec[0];
      }

      return var2;
   }

   public List<Rectangle> getPropHitRectangle() {
      Enemy.EMPTYRECTANGELS.clear();
      if(this.state == 3) {
         return Enemy.EMPTYRECTANGELS;
      } else {
         List var1 = super.getHitRectangle();
         var1.clear();
         this.hitRectangle.set(this.bounds);
         var1.add(this.hitRectangle);
         return var1;
      }
   }

   public abstract float getTimeOfCrash();

   public boolean getWingFighter() {
      if(!this.hasWingFighter) {
         this.hasWingFighter = true;
         this.wingFighter.reset(this.getPositionX(), 0.0F);
         return true;
      } else {
         return false;
      }
   }

   public void hitByBullet(Bullet var1) {
      this.healthyDegree -= var1.damageValue;
      if(this.healthyDegree <= 0 && this.state != 3) {
         this.stateTime = 0.0F;
         this.state = 3;
         this.world.fireFighterCrashedEvent(this);
         this.playCrashAnimation();
      }

   }

   public void hitByEnemy() {
      if(this.state != 3) {
         this.healthyDegree = 0;
         this.stateTime = 0.0F;
         this.state = 3;
         this.world.fireFighterCrashedEvent(this);
         this.playCrashAnimation();
      }

   }

   public void hitByLaser(Laser var1) {
      this.healthyDegree -= var1.getDamageValue();
      if(this.healthyDegree <= 0 && this.state != 3) {
         this.stateTime = 0.0F;
         this.state = 3;
         this.world.fireFighterCrashedEvent(this);
         this.playCrashAnimation();
      }

   }

   public boolean increaseABoom() {
      if(this.boomNum < this.boomLimit) {
         ++this.boomNum;
         return true;
      } else {
         return false;
      }
   }

   public boolean increaseALive() {
      return this.world.increaseALive();
   }

   protected void initFighter(World var1) {
      this.fightStand = Assets_fighter.f22Stand;
      this.fightLeft = Assets_fighter.f22FlyingToLeft;
      this.fightRight = Assets_fighter.f22FlyingToRight;
      this.state = 0;
      this.stateTime = 0.0F;
      this.appearTime = -3.0F;
      this.healthyDegree = 1;
      this.world = var1;
      this.currentGun = this.avalibleGuns[0];
      this.currentGun.reset();
      this.attrackedRadis = Settings.megaRadis[Settings.megaEffectLevel];
      this.hasPursueGun = false;
      this.hasWingFighter = false;
      this.hasMegnet = false;
      this.fighterTurn.reset();
      this.initNearestGoalFinder();
   }

   protected void initNearestGoalFinder() {
      this.setUpdateTimeSpan(0.3F);
   }

   public boolean isCrashed() {
      return this.state == 3;
   }

   public boolean loadFighter() {
      if(fighterState == null) {
         return false;
      } else {
         fighterState.load(this);
         fighterState = null;
         Settings.i("load fighter successfully.Game should come from next button.");
         return true;
      }
   }

   public void oncePropHandle() {
      if(Settings.allOnceBuy) {
         this.getWingFighter();
         this.turnOnPursueGun();
         this.turnOnMegnetRadis();
         int var16 = this.world.rand.nextInt(3);
         this.changeGun(var16);
         this.changeGun(var16);
         this.changeGun(var16);
         Settings.allOnceBuy = false;
      } else {
         if(Settings.wingManOnceBuy) {
            this.getWingFighter();
            Settings.wingManOnceBuy = false;
         }

         if(Settings.missleOnceBuy) {
            this.turnOnPursueGun();
            Settings.missleOnceBuy = false;
         }

         if(Settings.magnetOnceBuy) {
            this.turnOnMegnetRadis();
            Settings.magnetOnceBuy = false;
         }

         if(Settings.redBulletOnceBuy) {
            this.changeGun(0);
            this.changeGun(0);
            this.changeGun(0);
            Settings.redBulletOnceBuy = false;
            return;
         }

         if(Settings.yellowBulletOnceBuy) {
            this.changeGun(1);
            this.changeGun(1);
            this.changeGun(1);
            Settings.yellowBulletOnceBuy = false;
            return;
         }

         if(Settings.blueBulletOnceBuy) {
            this.changeGun(2);
            this.changeGun(2);
            this.changeGun(2);
            Settings.blueBulletOnceBuy = false;
            return;
         }
      }

   }

   public void playSound(int var1) {
      World var2 = this.world;
      switch(var1) {
      case 1:
         var2.worldAudio.playFighterBullet1();
         return;
      case 2:
         var2.worldAudio.playFighterBullet2();
         return;
      case 3:
         var2.worldAudio.playFighterBullet3();
         return;
      default:
      }
   }

   public abstract void recovery();

   public void render(SpriteBatch var1) {
      if(!this.isCrashed() || this.stateTime <= this.getTimeOfCrash()) {
         if(this.hasWingFighter) {
            this.wingFighter.render(var1);
         }

         float var2 = this.getScale();
         TextureRegion var3 = getKeyFrame(this, this.fightLeft, this.fightRight, this.fightStand);
         if(this.appearTime < 0.0F) {
            float var4;
            if(-((int)(this.appearTime / 0.05F)) % 2 == 1) {
               var4 = 0.3F;
            } else {
               var4 = 0.6F;
            }

            var1.setColor(1.0F, 1.0F, 1.0F, var4);
         }

         if(var3 instanceof TextureAtlas.AtlasRegion) {
            var1.draw((TextureAtlas.AtlasRegion)TextureAtlas.AtlasRegion.class.cast(var3), this.bounds.x, this.bounds.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, var2, var2, this.angle);
         } else {
            var1.draw(var3, this.bounds.x, this.bounds.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, var2, var2, this.angle);
         }

         var1.setColor(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   public void renderShadow(SpriteBatch var1) {
      if(!this.isCrashed() || this.stateTime <= this.getTimeOfCrash()) {
         if(this.hasWingFighter) {
            this.wingFighter.renderShadow(var1);
         }

         float var2 = this.getScale();
         int var3 = this.state;
         TextureRegion var4 = null;
         switch(var3) {
         case 0:
            var4 = this.fightStand.getKeyFrame(this.stateTime);
            break;
         case 1:
            var4 = this.fightLeft.getKeyFrame(this.stateTime);
            break;
         case 2:
            var4 = this.fightRight.getKeyFrame(this.stateTime);
            break;
         case 3:
            var4 = this.fightStand.getKeyFrame(this.stateTime);
         }

         Color var5 = var1.getColor();
         var1.setColor(0.0F, 0.0F, 0.0F, 0.5F);
         if(var4 instanceof TextureAtlas.AtlasRegion) {
            var1.draw((TextureAtlas.AtlasRegion)TextureAtlas.AtlasRegion.class.cast(var4), this.bounds.x + shadowOffset.x, this.bounds.y + shadowOffset.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, var2 * DynamicGameObject.shadowScale.x, var2 * DynamicGameObject.shadowScale.y, this.angle);
         } else {
            var1.draw(var4, this.bounds.x + shadowOffset.x, this.bounds.y + shadowOffset.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, var2 * DynamicGameObject.shadowScale.x, var2 * DynamicGameObject.shadowScale.y, this.angle);
         }

         var1.setColor(var5.r, var5.g, var5.b, 1.0F);
      }
   }

   protected void resetFighter(World var1, float var2, float var3, float var4, float var5) {
      this.setPosition(var2, -var5 / 2.0F);
      this.setBoundsWidth(var4);
      this.setBoundsHeight(var5);
      this.velocity.set(0.0F, (var3 + 3.0F * var5 / 2.0F) / 1.0F);
      this.angle = 0.0F;
      this.wingFighter.reset(this.getPositionX(), this.getPositionY());
      this.initFighter(var1);
   }

   public void saveFighter() {
      fighterState = new Fighter.FighterState();
      fighterState.save(this);
   }

   public void setBoomNumFull() {
      this.boomNum = this.iniBoom;
   }

   protected void setUpdateTimeSpan(float var1) {
      if(var1 < 0.3F) {
         var1 = 0.3F;
      }

      this.updateTimeSpan = var1;
   }

   public boolean turnOnMegnetRadis() {
      if(!this.hasMegnet) {
         this.attrackedRadis = Settings.megaRadis[Settings.megaEffectLevel];
         this.hasMegnet = true;
         return true;
      } else {
         return false;
      }
   }

   public boolean turnOnPursueGun() {
      if(!this.hasPursueGun) {
         this.pursueGun.reset();
         this.hasPursueGun = true;
         return true;
      } else {
         return false;
      }
   }

   public void update(float var1) {
      super.update(var1);
      if(this.appearTime > -2.0F) {
         this.velocity.set(0.0F, 0.0F);
      }

      this.stateTime += var1;
      this.appearTime += var1;
      if(this.hasWingFighter) {
         this.wingFighter.update(var1);
      }

      this.updateShooting(var1);
      this.fighterTurn.update(var1);
   }

   protected void updateShooting(float var1) {
      if(this.state != 3 && !this.world.getStopFighting()) {
         if(this.currentGun != null) {
            this.currentGun.updateShooting(var1);
         } else {
            Settings.appLog("fighter\'s gun is null, fighter can\'t shooting!");
         }

         if(this.hasPursueGun) {
            this.pursueGun.updateShooting(var1);
            return;
         }
      }

   }

   public boolean useABoom() {
      if(this.boomNum <= 0) {
         return false;
      } else {
         this.boomNum += -1;
         return true;
      }
   }

   public static class FighterState {

      int bulletIndex = 0;
      int bulletLevel = 1;
      boolean hasMegnet = false;
      boolean hasMissle = false;
      boolean hasWingFighter = false;


      private void clearData() {
         this.hasWingFighter = false;
         this.hasMissle = false;
         this.hasMegnet = false;
         this.bulletIndex = 0;
         this.bulletLevel = 1;
      }

      private void showInfo(boolean var1) {}

      public void load(Fighter var1) {
         this.showInfo(true);
         if(this.hasWingFighter) {
            var1.getWingFighter();
         }

         if(this.hasMissle) {
            var1.turnOnPursueGun();
         }

         if(this.hasMegnet) {
            var1.turnOnMegnetRadis();
         }

         if(this.bulletIndex != 0) {
            var1.changeGun(this.bulletIndex);
         }

         int var2 = this.bulletLevel - var1.currentGun.getLevel();
         if(var2 > 0) {
            for(int var3 = 0; var3 < var2; ++var3) {
               var1.changeGun(this.bulletIndex);
            }
         }

         this.clearData();
      }

      public void save(Fighter var1) {
         this.hasWingFighter = var1.hasWingFighter;
         this.hasMissle = var1.hasPursueGun;
         this.hasMegnet = var1.hasMegnet;

         int var2;
         for(var2 = 0; var2 < var1.avalibleGuns.length; ++var2) {
            if(var1.currentGun == var1.avalibleGuns[var2]) {
               this.bulletIndex = var2;
               this.bulletLevel = var1.currentGun.getLevel();
               break;
            }
         }

         if(var2 == var1.avalibleGuns.length) {
            this.bulletIndex = 0;
            this.bulletLevel = 1;
            Settings.i("can not find fighter.currentGun in fighter.avaliableGuns");
         }

         this.showInfo(false);
      }
   }
}
