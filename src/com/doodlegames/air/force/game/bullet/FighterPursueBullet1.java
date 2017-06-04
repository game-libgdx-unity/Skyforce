package com.doodlegames.air.force.game.bullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.game.path.GameObjectTravelPath;
import com.doodlegames.air.force.game.path.PursuePath;
import com.doodlegames.air.force.resource.Assets_Props;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;
import com.doodlegames.air.force.utils.Settings;
import com.doodlegames.air.force.utils.XStack;

public class FighterPursueBullet1 extends Bullet {

   private static XStack<FighterPursueBullet1> BulletPool = new XStack(200);
   private static final int POOLLIMIT = 200;
   public static final BulletGenerator bulletGen2 = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterPursueBullet1 var6 = FighterPursueBullet1.getAFighterPursueBullet1(var2, var1, var3, var4, var5, Assets_Props.fighterMissle, 1);
         var6.damageValue = 2;
         return var6;
      }
   };
   public static final BulletGenerator bulletGen3 = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterPursueBullet1 var6 = (FighterPursueBullet1)FighterPursueBullet1.bulletGen2.getABullet(var1, var2, var3, var4, var5);
         var6.damageValue = 3;
         return var6;
      }
   };
   public static final BulletGenerator bulletGen4 = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterPursueBullet1 var6 = (FighterPursueBullet1)FighterPursueBullet1.bulletGen2.getABullet(var1, var2, var3, var4, var5);
         var6.damageValue = 4;
         return var6;
      }
   };
   public static final BulletGenerator bulletGen5 = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterPursueBullet1 var6 = (FighterPursueBullet1)FighterPursueBullet1.bulletGen2.getABullet(var1, var2, var3, var4, var5);
         var6.damageValue = 5;
         return var6;
      }
   };
   public static final BulletGenerator bulletGen6 = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterPursueBullet1 var6 = (FighterPursueBullet1)FighterPursueBullet1.bulletGen2.getABullet(var1, var2, var3, var4, var5);
         var6.damageValue = 6;
         return var6;
      }
   };
   public static final BulletGenerator j20BoomBulletGen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterPursueBullet1 var6 = FighterPursueBullet1.getAFighterPursueBullet1(var2, var1, var3, var4, var5, Assets_Props.fighterMissle, 4);
         byte var7;
         if(Settings.backgroundVelocity.y == 0.0F) {
            var7 = 2;
         } else {
            var7 = 6;
         }

         var6.damageValue = var7;
         var6.bulletType = 1;
         return var6;
      }
   };
   private Animation bulletAnimation;
   private PursuePath pursuePath;
   private GameObjectTravelPath travelPath;


   private FighterPursueBullet1(World var1, GameObject var2, float var3, float var4, Vector2 var5, Animation var6) {
      super(var2, var3, var4, (float)RegionUtilFunctions.getRegionWidth(var6) / 1.0F, (float)RegionUtilFunctions.getRegionHeight(var6) / 1.0F, var5);
      this.velocity.set(0.0F, 0.0F);
      this.pursuePath = new PursuePath(this, var5.len(), var1);
      this.travelPath = this.pursuePath;
      this.bulletAnimation = var6;
   }

   public static void cycleAFighterPursueBullet1(FighterPursueBullet1 var0) {
      if(var0 != null && BulletPool.size() < 200) {
         BulletPool.push(var0);
      } else {
         Settings.appLog("pool free failed.");
      }
   }

   public static FighterPursueBullet1 getAFighterPursueBullet1(World var0, GameObject var1, float var2, float var3, Vector2 var4, Animation var5, int var6) {
      if(BulletPool.size() == 0) {
         for(int var8 = 0; var8 < 100; ++var8) {
            BulletPool.push(new FighterPursueBullet1(var0, (GameObject)null, 0.0F, 0.0F, var4, var5));
         }

         Settings.appLog("pool get failed");
      }

      FighterPursueBullet1 var7 = (FighterPursueBullet1)BulletPool.pop();
      var7.set(var0, var1, var2, var3, var4, var5, var6);
      return var7;
   }

   public static int poolSize() {
      return BulletPool.size();
   }

   public void freeToPool() {
      cycleAFighterPursueBullet1(this);
      this.travelPath.stopTravel();
   }

   public float getVelX() {
      return this.pursuePath.getVelX();
   }

   public float getVelY() {
      return this.pursuePath.getVelY();
   }

   public void render(SpriteBatch var1) {
      super.render(var1, this.bulletAnimation.getKeyFrame((float)Clock.getTimeCounter()));
   }

   public void set(World var1, GameObject var2, float var3, float var4, Vector2 var5, Animation var6, int var7) {
      super.reset(var2, var3, var4, (float)RegionUtilFunctions.getRegionWidth(var6) / 1.0F, (float)RegionUtilFunctions.getRegionHeight(var6) / 1.0F, var5);
      this.pursuePath.initPursuePath(this, var1, var7);
      this.angle = this.travelPath.beginTravel();
      this.bulletAnimation = var6;
   }

   public void setOffsetVel(float var1, float var2, float var3, int var4) {
      this.pursuePath.setOffsetVel(var1, var2, var3, var4);
   }

   public void update(float var1) {
      super.update(var1);
      this.angle += this.travelPath.updateTravel(var1);
   }
}
