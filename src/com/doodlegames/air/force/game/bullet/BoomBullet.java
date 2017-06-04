package com.doodlegames.air.force.game.bullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.game.bullet.FighterBullet;
import com.doodlegames.air.force.resource.Assets_fighter;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;
import com.doodlegames.air.force.utils.Settings;
import com.doodlegames.air.force.utils.XStack;

public class BoomBullet extends Bullet {

   private static XStack<BoomBullet> BulletPool = new XStack(40);
   private static final int POOLSIZELIMIT = 40;
   private static final Rectangle emptyRec = new Rectangle(0.0F, 0.0F, 0.0F, 0.0F);
   public static final BulletGenerator me262BoomBulletGen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         BoomBullet var6 = BoomBullet.getARafalBoomBullet(var1, var3, var4, var5, Assets_fighter.getLaseHeadAnimation());
         FighterBullet.ReflectAction var7 = (FighterBullet.ReflectAction)Pools.obtain(FighterBullet.ReflectAction.class);
         var7.reset(6);
         var6.addGameAction(var7);
         byte var8;
         if(Settings.backgroundVelocity.y == 0.0F) {
            var8 = 2;
         } else {
            var8 = 20;
         }

         var6.damageValue = var8;
         return var6;
      }
   };
   public static final BulletGenerator rafaleBulletGen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         BoomBullet var6 = BoomBullet.getARafalBoomBullet(var1, var3, var4, var5, Assets_fighter.getFighterBoomAnimation());
         byte var7;
         if(Settings.backgroundVelocity.y == 0.0F) {
            var7 = 2;
         } else {
            var7 = 20;
         }

         var6.damageValue = var7;
         return var6;
      }
   };
   private Animation bulletAnimation;
   private TextureRegion bulletRegion;
   private float hitTimeStamp;


   private BoomBullet(GameObject var1, float var2, float var3, Vector2 var4, TextureRegion var5) {
      super(var1, var2, var3, (float)RegionUtilFunctions.getRegionWidth(var5) / 1.0F, (float)RegionUtilFunctions.getRegionHeight(var5) / 1.0F, var4);
      this.hitTimeStamp = 0.0F;
      this.damageValue = 1;
      this.angle = 57.295776F * MathUtils.atan2(var4.y, var4.x) - 90.0F;
      this.bulletRegion = var5;
   }

   private BoomBullet(GameObject var1, float var2, float var3, Vector2 var4, Animation var5) {
      this(var1, var2, var3, var4, var5.getKeyFrame(0.0F));
      this.bulletAnimation = var5;
   }

   public static void cycleARafaleBoomBullet1(BoomBullet var0) {
      if(var0 != null && BulletPool.size() < 40) {
         var0.set((GameObject)null, 0.0F, 1.0F, new Vector2(0.0F, 10.0F), var0.bulletRegion);
         BulletPool.push(var0);
      } else {
         if(BulletPool.size() >= 40) {
            Settings.i("pool free failed in rafaleBoomBullet1 POOL" + poolSize());
         }

      }
   }

   private static BoomBullet getARafalBoomBullet(GameObject var0, float var1, float var2, Vector2 var3, TextureRegion var4) {
      if(BulletPool.size() == 0) {
         for(int var6 = 0; var6 < 20; ++var6) {
            BulletPool.add(new BoomBullet((GameObject)null, 0.0F, 0.0F, new Vector2(0.0F, 10.0F), var4));
         }
      }

      BoomBullet var5 = (BoomBullet)BulletPool.pop();
      var5.set(var0, var1, var2, var3, var4);
      return var5;
   }

   private static BoomBullet getARafalBoomBullet(GameObject var0, float var1, float var2, Vector2 var3, Animation var4) {
      if(var4 == null) {
         Settings.e("fighter bullet animation should not be null.");
         return null;
      } else {
         BoomBullet var5 = getARafalBoomBullet(var0, var1, var2, var3, var4.getKeyFrame(0.0F));
         var5.set(var0, var1, var2, var3, var4);
         return var5;
      }
   }

   public static int poolSize() {
      return BulletPool.size();
   }

   private void set(GameObject var1, float var2, float var3, Vector2 var4, TextureRegion var5) {
      super.reset(var1, var2, var3, (float)RegionUtilFunctions.getRegionWidth(var5) / 1.0F, (float)RegionUtilFunctions.getRegionHeight(var5) / 1.0F, var4);
      this.damageValue = 1;
      this.angle = 57.295776F * MathUtils.atan2(var4.y, var4.x) - 90.0F;
      this.angularSpeed = 0.0F;
      this.bulletType = 1;
      this.accel.x = 0.0F;
      this.accel.y = 0.0F;
      this.bulletAnimation = null;
      this.bulletRegion = var5;
   }

   private void set(GameObject var1, float var2, float var3, Vector2 var4, Animation var5) {
      this.set(var1, var2, var3, var4, var5.getKeyFrame(0.0F));
      this.bulletAnimation = var5;
   }

   public void freeToPool() {
      super.clearGameObjectActions();
      cycleARafaleBoomBullet1(this);
   }

   public Rectangle getHitRec() {
      return Clock.getCurrentTime(this.hitTimeStamp) < 0.1F?emptyRec:this.bounds;
   }

   public void hitSomething() {
      this.hitTimeStamp = Clock.getCurrentTime();
   }

   public void render(SpriteBatch var1) {
      if(this.bulletAnimation != null) {
         this.bulletRegion = this.bulletAnimation.getKeyFrame((float)Clock.getTimeCounter());
      }

      this.render(var1, this.bulletRegion);
   }
}
