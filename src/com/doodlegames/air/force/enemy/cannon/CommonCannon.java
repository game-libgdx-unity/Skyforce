package com.doodlegames.air.force.enemy.cannon;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector2Utils;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.Gun.BulletGunNazi;
import com.doodlegames.air.force.game.Gun.BulletShooter;
import com.doodlegames.air.force.game.Gun.EnemyActionGun;
import com.doodlegames.air.force.game.Gun.OwnNaziCannon;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.game.bullet.EnemyBullet2;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.Iterator;

public class CommonCannon extends Enemy implements Clock.FireListener, OwnNaziCannon {

   private static final float ANGLEMARK = 100000.0F;
   public static final float BULLETDELTA = 150.0F;
   private static final float BULLETVEL = -400.0F;
   private boolean autoTurn;
   private float cachedParentAngle;
   private boolean childrenHasClock;
   private Clock clockTurn;
   private float crashTime;
   private ArrayList<BulletShooter> guns;
   protected CommonCannon.ParentListener listener;
   private DynamicGameObject parent;
   private final float relativeOriginX;
   private final float relativeOriginY;
   private Clock shootClock;
   private Vector2 tempVector;


   public CommonCannon(final World world, final TextureAtlas.AtlasRegion enemyRegion, final Clock clock, final int n, final DynamicGameObject parent, final float n2, final float n3, final float n4, final float n5) {
	   super(world, n, n2, n3, n4, (float)n5 / 1.0f);
       final float n6 = n2 + parent.bounds.x;

       final float n7 = n3 + parent.bounds.y;

       float n8;

       if (enemyRegion.rotate) {

           n8 = enemyRegion.getRegionHeight();

       }

       else {

           n8 = enemyRegion.getRegionWidth();

       }

       final float n9 = (float)n8 / 1.0f;

       float n10;

       if (enemyRegion.rotate) {

           n10 = enemyRegion.getRegionWidth();

       }

       else {

           n10 = enemyRegion.getRegionHeight();

       }

      

       this.childrenHasClock = false;

       this.tempVector = new Vector2();

       this.cachedParentAngle = 100000.0f;

       this.autoTurn = false;

       this.clockTurn = new Clock(5.0f);

       this.listener = null;

       this.guns = new ArrayList();

       this.shootClock = new Clock(clock);

       this.shootClock.addListener(this);

       this.parent = parent;

       this.relativeOriginX = n4 + (n2 - this.bounds.width / 2.0f);

       this.relativeOriginY = n5 + (n3 - this.bounds.height / 2.0f);

       this.origin.set(n4, n5);

       this.enemyRegion = enemyRegion;

       this.enemyBroRegion = null;

       this.crashTime = 1.0f;

   }

   private void angleChanger() {
      boolean var1;
      if(true && this.clockTurn.isFired()) {
         var1 = true;
      } else {
         var1 = false;
      }

      boolean var2;
      if(var1 && !this.isCrashed()) {
         var2 = true;
      } else {
         var2 = false;
      }

      boolean var3;
      if(var2 && this.autoTurn) {
         var3 = true;
      } else {
         var3 = false;
      }

      boolean var4;
      if(var3 && this.canBeHit()) {
         var4 = true;
      } else {
         var4 = false;
      }

      if(var4) {
         float var5 = this.world.fighter.getPositionX() - this.getPositionX();
         float var6 = this.world.fighter.getPositionY() - this.getPositionY();
         if(var6 < 0.0F) {
            this.rotateTo(90.0F + (float)(57.2957763671875D * Math.atan2((double)var6, (double)var5)));
         }
      }

   }

   public static float clockDelay(float var0) {
      return 3.0F * innerDelay(var0);
   }

   public static float clockSpan2(float var0) {
      return 9.0F * innerDelay(var0);
   }

   public static CommonCannon getAutoCannon1(World var0, Clock var1, int var2, DynamicGameObject var3, float var4, float var5, float var6) {
      CommonCannon var7 = getCannon1(var0, var1, var2, EnemyBullet1.naziBulletGen, var3, var4, var5, var6);
      var7.setAutoTurn(true);
      return var7;
   }

   public static CommonCannon getCannon1(World var0, Clock var1, int var2, BulletGenerator var3, DynamicGameObject var4, float var5, float var6) {
      return getCannon1(var0, var1, var2, var3, var4, var5, var6, -400.0F);
   }

   public static CommonCannon getCannon1(World var0, Clock var1, int var2, BulletGenerator var3, DynamicGameObject var4, float var5, float var6, float var7) {
      TextureAtlas var8 = Assets_ShareInAllLevel.atlasSharedEnemy;
      TextureAtlas.AtlasRegion var9 = var8.findRegion("nazi_canon", 1);
      float var10 = (float)RegionUtilFunctions.getRegionWidth((TextureRegion)var9) / 1.0F;
      float var11 = (float)RegionUtilFunctions.getRegionHeight((TextureRegion)var9) / 1.0F;
      CommonCannon.CommonCannon1 var12 = new CommonCannon.CommonCannon1(var0, var9, var1, var2, var4, var5 + var10 / 2.0F, var6 + var11 / 2.0F, 12.0F, 21.0F);
      if(var7 >= 0.0F) {
         var7 = -var7;
      }

      var12.addNaziActionGun(8.0F, 2.0F, var7, 0.0F);
      float var13 = innerDelay(var7);
      var12.addNaziActionGun(15.0F, 2.0F, var7, var13);
      var12.setBroRegion(var8.findRegion("nazi_canon_1_broken"));
      return var12;
   }

   public static CommonCannon getCannon2(World var0, Clock var1, int var2, BulletGenerator var3, DynamicGameObject var4, float var5, float var6, float var7) {
      TextureAtlas var8 = Assets_ShareInAllLevel.atlasSharedEnemy;
      TextureAtlas.AtlasRegion var9 = var8.findRegion("nazi_canon", 2);
      float var10 = (float)RegionUtilFunctions.getRegionWidth((TextureRegion)var9) / 1.0F;
      float var11 = (float)RegionUtilFunctions.getRegionHeight((TextureRegion)var9) / 1.0F;
      CommonCannon.CommonCannon2 var12 = new CommonCannon.CommonCannon2(var0, var9, var1, var2, var4, var5 + var10 / 2.0F, var6 + var11 / 2.0F, 12.0F, 20.0F);
      var12.addNaziActionGun(8.0F, 2.0F, -Math.abs(var7), 0.0F);
      var12.addNaziActionGun(15.0F, 2.0F, -Math.abs(var7), 1.0F);
      var12.setBroRegion(var8.findRegion("nazi_canon_2_broken"));
      return var12;
   }

   public static CommonCannon getCannon4(World var0, Clock var1, int var2, BulletGenerator var3, DynamicGameObject var4, float var5, float var6) {
      return getCannon4(var0, var1, var2, var3, var4, var5, var6, -400.0F);
   }

   public static CommonCannon getCannon4(World var0, Clock var1, int var2, BulletGenerator var3, DynamicGameObject var4, float var5, float var6, float var7) {
      TextureAtlas var8 = Assets_ShareInAllLevel.atlasSharedEnemy;
      TextureAtlas.AtlasRegion var9 = var8.findRegion("nazi_canon", 4);
      float var10 = (float)RegionUtilFunctions.getRegionWidth((TextureRegion)var9) / 1.0F;
      float var11 = (float)RegionUtilFunctions.getRegionHeight((TextureRegion)var9) / 1.0F;
      CommonCannon.CommonCannon4 var12 = new CommonCannon.CommonCannon4(var0, var9, var1, var2, var4, var5 + var10 / 2.0F, var6 + var11 / 2.0F, var10 / 2.0F, var11 / 2.0F);
      if(var7 >= 0.0F) {
         var7 = -var7;
      }

      var12.addGun(EnemyActionGun.getGun(EnemyBullet2.naziBulletGen, var12, var7, 14.0F, 5.0F, 0.2F));
      var12.addGun(EnemyActionGun.getGun(EnemyBullet2.naziBulletGen, var12, var7, 22.0F, 5.0F, 0.0F));
      var12.addGun(EnemyActionGun.getGun(EnemyBullet2.naziBulletGen, var12, var7, 31.0F, 5.0F, 0.2F));
      var12.setBroRegion(var8.findRegion("nazi_canon_4_broken"));
      return var12;
   }

   public static Clock getClock1(float var0) {
      return new Clock(6.0F * innerDelay(var0), 1.0F, (byte)1);
   }

   public static Clock getClock2(float var0) {
      float var1 = innerDelay(var0);
      return new Clock(9.0F * var1, 3.0F * var1, (byte)2);
   }

   public static Clock getClock3(float var0) {
      float var1 = innerDelay(var0);
      return new Clock(12.0F * var1, 3.0F * var1, (byte)3);
   }

   public static CommonCannon getCrossCannon(World var0, Clock var1, int var2, BulletGenerator var3, DynamicGameObject var4, float var5, float var6) {
      return getCrossCannon(var0, var1, var2, var3, var4, var5, var6, -400.0F);
   }

   public static CommonCannon getCrossCannon(World var0, Clock var1, int var2, BulletGenerator var3, DynamicGameObject var4, float var5, float var6, float var7) {
      TextureAtlas var8 = Assets_ShareInAllLevel.atlasSharedEnemy;
      TextureAtlas.AtlasRegion var9 = var8.findRegion("nazi_canon", 3);
      float var10 = (float)RegionUtilFunctions.getRegionWidth((TextureRegion)var9) / 1.0F;
      float var11 = (float)RegionUtilFunctions.getRegionHeight((TextureRegion)var9) / 1.0F;
      CommonCannon.CommonCannonCrossCannon var12 = new CommonCannon.CommonCannonCrossCannon(var0, var9, var1, var2, var4, var5 + var10 / 2.0F, var6 + var11 / 2.0F, var10 / 2.0F, var11 / 2.0F);
      if(var7 >= 0.0F) {
         var7 = -var7;
      }

      var12.addGun(15.5F, 0.0F, var3, 0.0F, var7);
      var12.addGun(31.0F, 15.5F, var3, -var7, 0.0F);
      var12.addGun(15.5F, 31.0F, var3, 0.0F, -var7);
      var12.addGun(0.0F, 15.5F, var3, var7, 0.0F);
      var12.setBroRegion(var8.findRegion("nazi_canon_3_broken"));
      return var12;
   }

   public static float getHeightOfCannon1() {
      return (float)RegionUtilFunctions.getRegionHeight((TextureRegion)Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("nazi_canon", 1)) / 1.0F;
   }

   public static float getWidthOfCannon1() {
      return (float)RegionUtilFunctions.getRegionWidth((TextureRegion)Assets_ShareInAllLevel.atlasSharedEnemy.findRegion("nazi_canon", 1)) / 1.0F;
   }

   public static float innerDelay(float var0) {
      float var1 = Math.abs(var0);
      if((double)var1 < 1.0E-4D) {
         Settings.e("velBullet is too small " + var0);
         return 1.0F;
      } else {
         return 150.0F / var1;
      }
   }

   public void addGun(float var1, float var2, BulletGenerator var3, float var4, float var5) {
      if(this.guns.size() != 0 && this.childrenHasClock) {
         Settings.e("childRen has clock, cannot add gun without clock");
      } else {
         this.childrenHasClock = false;
         BulletGunNazi var6 = new BulletGunNazi(this, this, this.world, var3, (Clock)null, Vector2Utils.tmp.set(var1, var2), new float[]{var4, var5});
         var6.shootPointIsAbsolute = false;
         this.guns.add(var6);
      }
   }

   public void addGun(BulletShooter var1) {
      this.guns.add(var1);
   }

   public void addGun(Clock var1, float var2, float var3, BulletGenerator var4, float var5, float var6) {
      if(this.guns.size() != 0 && !this.childrenHasClock) {
         Settings.e("childRen has not clock, cannot add gun with clock");
      } else {
         this.childrenHasClock = true;
         BulletGunNazi var7 = new BulletGunNazi(this, this, this.world, var4, var1, Vector2Utils.tmp.set(var2, var3), new float[]{var5, var6});
         var7.shootPointIsAbsolute = false;
         this.guns.add(var7);
      }
   }

   public void addNaziActionGun(float var1, float var2, float var3, float var4) {
      EnemyActionGun var5 = EnemyActionGun.getNaziCannonGun(this, var3, var1, var2, var4);
      this.guns.add(var5);
   }

   public boolean canBeHit() {
      boolean var1;
      if(this.listener != null && !this.listener.parentAllowHit()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return super.canBeHit() && var1;
   }

   public float getCannonAngle() {
      return this.getAngle();
   }

   public float getCannonOriginX() {
      return this.origin.x;
   }

   public float getCannonOriginY() {
      return this.origin.y;
   }

   public Clock getClockTurn() {
      return this.clockTurn;
   }

   public float getTimeOfCrash() {
      return this.crashTime;
   }

   public void handle(Clock.ClockFireEvent var1) {
      if(this.guns != null && (this.listener == null || this.listener.parentAllowAttack()) && !this.childrenHasClock) {
         Iterator var2 = this.guns.iterator();

         while(var2.hasNext()) {
            ((BulletShooter)var2.next()).shooting();
         }
      }

   }

   public void reset(int var1, TextureAtlas.AtlasRegion var2, TextureAtlas.AtlasRegion var3) {
      this.cachedParentAngle = 100000.0F;
      float var4 = this.relativeOriginX - this.origin.x + this.bounds.width / 2.0F;
      float var5 = this.relativeOriginY - this.origin.y + this.bounds.height / 2.0F;
      float var6 = var4 + this.parent.bounds.x;
      float var7 = var5 + this.parent.bounds.y;
      super.reset(this.world, var1, var6, var7);
      this.enemyRegion = var2;
      this.enemyBroRegion = var3;
   }

   public boolean setAutoTurn(boolean var1) {
      this.autoTurn = var1;
      return this.autoTurn;
   }

   public void setParentListener(CommonCannon.ParentListener var1) {
      this.listener = var1;
   }

   public void setTimeOfCrash(float var1) {
      this.crashTime = var1;
   }

   public void update(float var1) {
      super.update(var1);
      if(this.isCrashed() && this.angularSpeed != 0.0F) {
         this.angularSpeed = 0.0F;
      }

      this.updatePosition();
      this.angleChanger();
   }

   public void updatePosition() {
      Vector2 var1 = this.tempVector.set(this.relativeOriginX, this.relativeOriginY);
      float var2 = this.parent.getAngle();
      DynamicGameObject.RotatePoint(var1, this.parent.origin, var2);
      float var4 = var1.x - this.origin.x;
      float var5 = var1.y - this.origin.y;
      float var6 = var4 + this.bounds.width / 2.0F;
      float var7 = var5 + this.bounds.height / 2.0F;
      this.setPosition(var6 + this.parent.bounds.x, var7 + this.parent.bounds.y);
      float var8 = this.getAngle();
      if(this.cachedParentAngle != 100000.0F) {
         this.setAngle(var8 + (var2 - this.cachedParentAngle));
      }

      this.cachedParentAngle = var2;
   }

   public void updateShooting(float var1) {
      if(!this.childrenHasClock) {
         this.shootClock.isFired();
      }

      Iterator var2 = this.guns.iterator();

      while(var2.hasNext()) {
         ((BulletShooter)var2.next()).updateShooting(var1);
      }

   }

   public static class CommonCannon1 extends CommonCannon {

      public CommonCannon1(World var1, TextureAtlas.AtlasRegion var2, Clock var3, int var4, DynamicGameObject var5, float var6, float var7, float var8, float var9) {
         super(var1, var2, var3, var4, var5, var6, var7, var8, var9);
      }
   }

   public static class CommonCannon2 extends CommonCannon {

      public CommonCannon2(World var1, TextureAtlas.AtlasRegion var2, Clock var3, int var4, DynamicGameObject var5, float var6, float var7, float var8, float var9) {
         super(var1, var2, var3, var4, var5, var6, var7, var8, var9);
      }
   }

   public static class CommonCannonCrossCannon extends CommonCannon {

      public CommonCannonCrossCannon(World var1, TextureAtlas.AtlasRegion var2, Clock var3, int var4, DynamicGameObject var5, float var6, float var7, float var8, float var9) {
         super(var1, var2, var3, var4, var5, var6, var7, var8, var9);
      }
   }

   public static class CommonCannon4 extends CommonCannon {

      public CommonCannon4(World var1, TextureAtlas.AtlasRegion var2, Clock var3, int var4, DynamicGameObject var5, float var6, float var7, float var8, float var9) {
         super(var1, var2, var3, var4, var5, var6, var7, var8, var9);
      }
   }

   public static class Const {

      public static final float arraySpan = 5.0F;
      public static final float clockDelay2 = 3.0F;
      public static final float clockDelay3 = 3.0F;
      public static final float clockSpan2 = 9.0F;
      public static final float clockSpan3 = 12.0F;
      public static final float innerDelay = 1.0F;
      public static final float innerSpan = 2.0F;


   }

   public interface ParentListener {

      boolean parentAllowAttack();

      boolean parentAllowHit();
   }
}
