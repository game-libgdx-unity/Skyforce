package com.doodlegames.air.force.fighters.gun;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector2Utils;
import com.badlogic.gdx.math.Vector3;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldEvents;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.game.bullet.FighterBullet;
import com.doodlegames.air.force.resource.Assets_fighter;
import com.doodlegames.air.force.screen.screenActor.GameEvent;
import com.doodlegames.air.force.utils.Bezier;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.OverlapTester;
import com.doodlegames.air.force.utils.RegionUtilFunctions;
import com.doodlegames.air.force.utils.Settings;

public class Me262LaserGun implements FighterGun, GameEvent.GameEventListener {

   static final Vector2 p1 = new Vector2();
   static final Vector2 p2 = new Vector2();
   static final Vector2 p3 = new Vector2();
   static final Vector2 p4 = new Vector2();
   static final Vector3 temp = new Vector3();
   private final float INOUTTIME;
   private int bodyNum;
   private ArrayList<Me262LaserGun.LaserBody> bodys;
   private final Fighter fighter;
   private boolean gunIsShooting;
   private Me262LaserGun.LaserHead head;
   private int level;
   private ArrayList<Me262LaserGun.laserGunListener> listeners;
   private float startTimeSpan;
   private float stopTimeSpan;
   private Color temcolor;
   private final float transparentTime;


   public Me262LaserGun(Fighter var1) {
	   super();
	   byte var2 = 1;
      this.temcolor = new Color();
      this.INOUTTIME = 0.3F;
      this.transparentTime = 0.2F;
      this.fighter = var1;
      this.bodyNum = 50;
      this.level = var2;
      this.listeners = new ArrayList();
      this.head = new Me262LaserGun.LaserHead(var2, var1, this);
      this.listeners.add(this.head);
      this.bodys = new ArrayList();

      for(int var4 = 0; var4 < 2 + this.bodyNum; ++var4) {
         Me262LaserGun.LaserBody var5 = new Me262LaserGun.LaserBody(var2, var1, this, var4);
         this.bodys.add(var5);
         this.listeners.add(var5);
      }

      var1.world.addWorldEventListener(this);
      if(var1.world.getStopFighting()) {
         var2 = 0;
      }

      this.gunIsShooting = var2==0?false:true;
   }

   public static Vector3 computerBezierLase(float var0, float var1, float var2, float var3, float var4) {
      float var5 = var1 + 100.0F;
      if(var3 > var1) {
         if(var3 > 200.0F + var1) {
            var5 = (var3 + var1) / 2.0F;
         }

         p1.set(var0, var1);
         p2.set(var0, var5);
         p4.set(var2, var3);
         Bezier.computerBezier2(temp, p1, p2, p4, var4);
      } else {
         float var6 = (var0 + var2) / 2.0F;
         float var7 = (var5 + var3) / 2.0F;
         float var8 = var2 - var6;
         float var9 = var3 - var7;
         float var10;
         float var11;
         if(var2 < var0) {
            var10 = var6 + var9;
            var11 = var7 - var8;
         } else {
            var10 = var6 - var9;
            var11 = var7 + var8;
         }

         p1.set(var0, var1);
         p2.set(var0, var5);
         p3.set(var10, var11);
         p4.set(var2, var3);
         Bezier.computerBezier3(temp, p1, p2, p3, p4, var4);
      }

      return temp;
   }

   private int getBodyDamageValue() {
      switch(this.level) {
      case 1:
         return 1;
      case 2:
         return 2;
      case 3:
         return 3;
      default:
         throw new RuntimeException("invalid level in me262 lase gun.: " + this.level);
      }
   }

   private float getDamageTimeSpan() {
      float var1 = 0.2F;
      switch(this.level) {
      case 3:
         var1 = 0.15F;
      case 1:
      case 2:
         return var1;
      default:
         throw new RuntimeException("invalid level in me262 lase gun.: " + this.level);
      }
   }

   private int getHeadDamageValue() {
      switch(this.level) {
      case 1:
         return 5;
      case 2:
         return 8;
      case 3:
         return 12;
      default:
         throw new RuntimeException("invalid level in me262 lase gun.: " + this.level);
      }
   }

   private void notifyListeners() {
      Iterator var1 = this.listeners.iterator();

      while(var1.hasNext()) {
         ((Me262LaserGun.laserGunListener)var1.next()).handle(this.level);
      }

   }

   public void draw(SpriteBatch var1) {
      Iterator var2 = this.bodys.iterator();

      while(var2.hasNext()) {
         ((Me262LaserGun.LaserBody)var2.next()).render(var1);
      }

      this.head.render(var1);
   }

   public float getAlpha(int var1) {
      float var2 = 0.3F * ((float)var1 / (float)this.bodyNum);
      float var3;
      if(this.gunIsShooting) {
         var3 = (Clock.getCurrentTime(this.startTimeSpan) - var2) / 0.2F;
      } else {
         var3 = 1.0F - (Clock.getCurrentTime(this.stopTimeSpan) - var2) / 0.2F;
      }

      if(var3 < 0.0F) {
         var3 = 0.0F;
      } else if(var3 > 1.0F) {
         return 1.0F;
      }

      return var3;
   }

   public int getLevel() {
      return this.level;
   }

   public void handle(Object var1, Object var2) {
      if(var2 instanceof WorldEvents.FighterCrashedEvent) {
         Settings.i("recieve a fighter Crashed Event.");
         if(this.gunIsShooting) {
            this.stopShooting();
         }
      }

      if(var2 instanceof WorldEvents.FighterRecoveryEvent) {
         Settings.i("recieve a fighter Recovery Event.");
         if(!this.gunIsShooting) {
            this.startShooting();
         }
      }

      if(var2 instanceof WorldEvents.WorldFightSetEvent) {
         WorldEvents.WorldFightSetEvent var3 = (WorldEvents.WorldFightSetEvent)var2;
         boolean var4 = var3.getNewState();
         Settings.i("recieve a fightSet Event.oldState is :" + var3.getOldState() + "  NewState:" + var4 + "gunIsShooting:" + this.gunIsShooting);
         if(!var4 && !this.gunIsShooting) {
            this.startShooting();
         }

         if(var4 && this.gunIsShooting) {
            this.stopShooting();
         }
      }

   }

   public void increaseLevel() {
      ++this.level;
      this.notifyListeners();
   }

   public void reset() {
      this.level = 1;
      this.notifyListeners();
   }

   public void setLevel(int var1) {
      if(var1 >= 1 && var1 <= 3) {
         this.level = var1;
         this.notifyListeners();
      } else {
         throw new RuntimeException("me262 lase gun: level should not be set to " + var1);
      }
   }

   public void startShooting() {
      if(this.gunIsShooting) {
         Settings.e("startShooting but gunIsShooting is already true.");
      } else if(!this.fighter.isCrashed() && !this.fighter.world.getStopFighting()) {
         Settings.i("start Shooting.......");
         this.gunIsShooting = true;
         this.startTimeSpan = Clock.getCurrentTime();
         return;
      }

   }

   public void stopShooting() {
      Settings.i("stop Shooting.......");
      if(!this.gunIsShooting) {
         Settings.e("stopShooting but gunIsShooting is already false.");
      } else {
         this.gunIsShooting = false;
         this.stopTimeSpan = Clock.getCurrentTime();
      }
   }

   public void updateShooting(float var1) {
      this.head.update(var1);
      Iterator var2 = this.bodys.iterator();

      while(var2.hasNext()) {
         ((Me262LaserGun.LaserBody)var2.next()).update(var1);
      }

   }

   public class LaserHead extends DynamicGameObject implements Me262LaserGun.laserGunListener, World.BulletHitEnemyListener {

      float attrackX = 240.0F;
      float attrackY = 800.0F;
      float bulletGenNextTime = -10.0F;
      Clock clockCheck;
      final Fighter fighter;
      Rectangle goalRec;
      final Me262LaserGun gun;
      int level;
      float nearestDistance;


      public LaserHead(int var2, Fighter var3, Me262LaserGun var4) {
         super(var3.getPositionX(), var3.getPositionY(), (float)RegionUtilFunctions.getRegionWidth(Assets_fighter.getLaseHeadAnimation()) / 1.0F, (float)RegionUtilFunctions.getRegionHeight(Assets_fighter.getLaseHeadAnimation()) / 1.0F);
         this.level = var2;
         this.fighter = var3;
         this.origin.set(this.bounds.width / 2.0F, this.bounds.height / 2.0F);
         this.setPosition(var3.getPositionX(), 800.0F);
         this.clockCheck = new Clock(0.4F);
         this.fighter.world.hitCheckListeners.add(this);
         this.gun = var4;
      }

      private void attractTo(float var1, float var2, float var3) {
         float var4 = var1 - this.getPositionX();
         float var5 = var2 - this.getPositionY();
         float var6 = this.velocity.x;
         float var7 = this.velocity.y;
         Vector2 var8 = this.accel;
         byte var9;
         if(var4 > 0.0F) {
            var9 = 1;
         } else {
            var9 = -1;
         }

         byte var10;
         if(var5 > 0.0F) {
            var10 = 1;
         } else {
            var10 = -1;
         }

         float var11 = Math.abs(var4);
         float var12 = Math.abs(var5);
         float var13 = (1280.0F - var11 - var12) / 30.0F;
         float var14 = var11 * var13 * (float)var9;
         float var15 = var12 * var13 * (float)var10;
         float var16 = 1.0F / var3;
         if(var16 > 50.0F) {
            var16 = 50.0F;
         }

         if(var6 * (float)var9 < 0.0F) {
            var14 += var6 * -var16;
         }

         if(var7 * (float)var10 < 0.0F) {
            var15 += var7 * -var16;
         }

         var8.set(var14, var15);
         float var18 = (float)(-3 + Clock.rand.nextInt(6));
         float var19 = (float)(-3 + Clock.rand.nextInt(6));
         float var20 = this.getPositionX();
         float var21 = this.getPositionY();
         if(var20 < 0.0F) {
            var20 = 0.0F;
         }

         if(var20 > 480.0F) {
            var20 = 480.0F;
         }

         if(var21 < 0.0F) {
            var21 = 0.0F;
         }

         if(var21 > 810.0F) {
            var21 = 810.0F;
         }

         this.setPosition(var20 + var18, var21 + var19);
      }

      private void headPutBullet() {
         if(this.gun.getAlpha(this.gun.bodyNum) == 1.0F && Clock.getCurrentTime(this.bulletGenNextTime) > 0.0F) {
            this.bulletGenNextTime = Clock.getCurrentTime() + this.gun.getDamageTimeSpan();
            BulletGenerator var1 = FighterBullet.me262LaseBulletGen;
            Vector2Utils.tmp.set(Settings.backgroundVelocity);
            float var3 = this.getPositionX();
            float var4 = this.getPositionY();
            Bullet var5 = var1.getABullet(this, this.fighter.world, var3, var4, Vector2Utils.tmp);
            if(Clock.rand.nextInt(3) < 2) {
               var5.damageValue = this.gun.getHeadDamageValue();
            } else {
               var5.damageValue = 0;
            }

            Fighter.bullets.add(var5);
         }

      }

      private void limitVelAccel() {
         if(this.velocity.x < -800.0F) {
            this.velocity.x = -800.0F;
         }

         if(this.velocity.x > 800.0F) {
            this.velocity.x = 800.0F;
         }

         if(this.velocity.y < -800.0F) {
            this.velocity.y = -800.0F;
         }

         if(this.velocity.y > 800.0F) {
            this.velocity.y = 800.0F;
         }

      }

      public void beginCheck() {
         this.goalRec = null;
         this.nearestDistance = Float.MAX_VALUE;
      }

      public void checking(Enemy var1, List<Rectangle> var2) {
         if(var2 != null && this.gun.getLevel() != 1) {
            Iterator var3 = var2.iterator();

            while(var3.hasNext()) {
               Rectangle var4 = (Rectangle)var3.next();
               float var5 = var4.x + var4.width / 2.0F;
               float var6 = var4.y + var4.height / 2.0F;
               if(OverlapTester.pointInRectangle(Settings.screenRec, var5, var6)) {
                  float var7 = var5 - this.fighter.getPositionX();
                  float var8 = var6 - this.fighter.getPositionY();
                  if(var8 <= 0.0F || var8 >= 150.0F) {
                     if(var7 <= 0.0F) {
                        var7 = -var7;
                     }

                     if(var8 <= 0.0F) {
                        var8 = -var8;
                     }

                     float var9 = var7 + var8;
                     if(var9 < this.nearestDistance) {
                        this.nearestDistance = var9;
                        this.goalRec = var4;
                     }
                  }
               }
            }
         }

      }

      public void endCheck() {
         if(this.gun.getLevel() != 1) {
            float var1;
            float var2;
            if(this.goalRec == null) {
               var1 = this.fighter.getPositionX();
               var2 = 800.0F;
               if(this.gun.gunIsShooting) {
                  this.gun.stopShooting();
               }
            } else {
               var1 = this.goalRec.x + this.goalRec.width / 2.0F;
               var2 = 4.0F + this.goalRec.y;
               if(!this.gun.gunIsShooting) {
                  this.gun.startShooting();
               }
            }

            if(var2 < this.fighter.getPositionY()) {
               var1 = this.fighter.getPositionX();
               var2 = 800.0F;
            }

            this.attrackX = var1;
            this.attrackY = var2;
         }
      }

      public void handle(int var1) {
         if(this.level != var1) {
            this.level = var1;
         }
      }

      public void render(SpriteBatch var1) {
         TextureRegion var2 = Assets_fighter.getLaseHeadAnimation().getKeyFrame(Clock.getCurrentTime());
         float var3 = this.gun.getAlpha(this.gun.bodyNum);
         float var4;
         if(this.level == 3) {
            var4 = 1.0F;
         } else {
            var4 = 0.5F;
         }

         Color var5 = var1.getColor();
         Me262LaserGun.this.temcolor.set(var5);
         Me262LaserGun.this.temcolor.a = var3;
         var1.setColor(Me262LaserGun.this.temcolor);
         float var7 = this.bounds.x;
         float var8 = this.bounds.y;
         float var9 = this.origin.x;
         float var10 = this.origin.y;
         float var11 = this.bounds.width;
         float var12 = this.bounds.height;
         float var13 = this.angle;
         RegionUtilFunctions.draw(var1, var2, var7, var8, var9, var10, var11, var12, var4, var4, var13);
         var1.setColor(var5);
      }

      public void update(float var1) {
         this.limitVelAccel();
         super.update(var1);
         ArrayList var2 = this.fighter.world.hitCheckListeners;
         if(this.clockCheck.isFired()) {
            if(!var2.contains(this)) {
               var2.add(this);
            }
         } else {
            var2.remove(this);
         }

         this.angle = Me262LaserGun.computerBezierLase(this.fighter.getPositionX(), this.fighter.getPositionY() + this.fighter.bounds.height / 2.0F - 10.0F, this.gun.head.getPositionX(), this.gun.head.getPositionY(), 1.0F).z;
         if(this.gun.getLevel() == 1) {
            this.attrackX = this.fighter.getPositionX();
            this.attrackY = 800.0F;
         }

         this.attractTo(this.attrackX, this.attrackY, var1);
         this.headPutBullet();
      }
   }

   private interface laserGunListener {

      void handle(int var1);
   }

   public class LaserBody extends DynamicGameObject implements Me262LaserGun.laserGunListener {

      final Fighter fighter;
      final Me262LaserGun gun;
      final int id;
      int level;
      float shootTimeStamp = -10.0F;


      public LaserBody(int var2, Fighter var3, Me262LaserGun var4, int var5) {
         super(var3.getPositionX(), var3.getPositionY(), (float)RegionUtilFunctions.getRegionWidth(Assets_fighter.getMe262RailGun3()) / 1.0F, (float)RegionUtilFunctions.getRegionHeight(Assets_fighter.getMe262RailGun3()) / 1.0F);
         this.id = var5;
         this.level = var2;
         this.fighter = var3;
         this.gun = var4;
         this.origin.set(this.bounds.width / 2.0F, this.bounds.height / 2.0F);
      }

      public void handle(int var1) {
         if(this.level != var1) {
            this.level = var1;
            float var2;
            float var3;
            if(this.level == 3) {
               var2 = (float)RegionUtilFunctions.getRegionWidth(Assets_fighter.getMe262RailGun6());
               var3 = (float)RegionUtilFunctions.getRegionHeight(Assets_fighter.getMe262RailGun6());
            } else {
               var2 = (float)RegionUtilFunctions.getRegionWidth(Assets_fighter.getMe262RailGun3());
               var3 = (float)RegionUtilFunctions.getRegionHeight(Assets_fighter.getMe262RailGun3());
            }

            float var4 = var2 / 1.0F;
            float var5 = var3 / 1.0F;
            this.setBoundsWidth(var4);
            this.setBoundsHeight(var5);
            this.origin.set(var4 / 2.0F, var5 / 2.0F);
         }
      }

      public void render(SpriteBatch var1) {
         if(this.id <= this.gun.bodyNum) {
            TextureRegion var2;
            if(this.level == 3) {
               var2 = Assets_fighter.getMe262RailGun6();
            } else {
               var2 = Assets_fighter.getMe262RailGun3();
            }

            Color var3 = var1.getColor();
            Me262LaserGun.this.temcolor.set(var3);
            Me262LaserGun.this.temcolor.a = this.gun.getAlpha(this.id);
            var1.setColor(Me262LaserGun.this.temcolor);
            RegionUtilFunctions.draw(var1, var2, this.bounds.x, this.bounds.y, this.origin.x, this.origin.y, this.bounds.width, this.bounds.height, 1.0F, 1.2F, this.angle);
            var1.setColor(var3);
         }
      }

      public void update(float var1) {
         if(this.id <= this.gun.bodyNum) {
            float var2 = (float)this.id / (float)this.gun.bodyNum;
            Vector3 var3 = Me262LaserGun.computerBezierLase(this.fighter.getPositionX(), this.fighter.getPositionY() + this.fighter.bounds.height / 2.0F - 10.0F, this.gun.head.getPositionX(), this.gun.head.getPositionY(), var2);
            this.angle = var3.z;
            this.setPosition(var3.x, var3.y);
            if(Clock.getCurrentTime(this.shootTimeStamp) > 0.0F && this.gun.getAlpha(this.id) == 1.0F) {
               float var4 = this.getPositionX() + (float)Clock.rand.nextInt(11) - 5.0F;
               float var5 = this.getPositionY() + (float)Clock.rand.nextInt(11) - 5.0F;
               Vector2Utils.tmp.set(Settings.backgroundVelocity);
               Bullet var7 = FighterBullet.me262LaseBulletGen.getABullet(this, this.fighter.world, var4, var5, Vector2Utils.tmp);
               if(Clock.rand.nextBoolean()) {
                  var7.damageValue = this.gun.getBodyDamageValue();
               } else {
                  var7.damageValue = 0;
               }

               Fighter.bullets.add(var7);
               this.shootTimeStamp = Clock.getCurrentTime() + this.gun.getDamageTimeSpan();
               return;
            }
         }

      }
   }
}
