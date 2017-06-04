package com.doodlegames.air.force.game.bullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.doodlegames.air.force.fighters.gun.Me262LaserGun;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.resource.Assets_Props;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.resource.Assets_fighter;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;
import com.doodlegames.air.force.utils.Settings;
import com.doodlegames.air.force.utils.XStack;

public class FighterBullet extends Bullet {

   private static XStack<FighterBullet> BulletPool;
   private static final int POOLSIZELIMIT = 200;
   public static final BulletGenerator bulletGen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         return FighterBullet.getAFighterBullet(var1, var3, var4, var5, (TextureRegion)Assets_ShareInAllLevel.test_Bullet_1);
      }
   };
   private static TextureRegion bulletTextureRegion;
   public static final BulletGenerator f22Bullet1Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, Assets_fighter.f22Bullet1);
         var6.damageValue = 1 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   public static final BulletGenerator f22Bullet2Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, Assets_fighter.f22Bullet2);
         var6.damageValue = 2 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   public static final BulletGenerator f22Bullet4Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, Assets_fighter.f22Bullet3);
         var6.damageValue = 3 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   private static final BulletGenerator[] f22BulletGen;
   public static final BulletGenerator[] f22BulletGenCycleMore;
   public static final BulletGenerator invisibleBullet = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, (TextureRegion)Assets_ShareInAllLevel.test_boom);
         var6.damageValue = 0;
         var6.setBoundsWidth(0.0F);
         var6.setBoundsHeight(0.0F);
         var6.bulletType = 1;
         FighterBullet.BulletDisAction var7 = (FighterBullet.BulletDisAction)Pools.obtain(FighterBullet.BulletDisAction.class);
         var7.reset();
         var6.addGameAction(var7);
         return var6;
      }
   };
   public static final BulletGenerator j20BoomBullet = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, Assets_Props.fighterMissle);
         var6.damageValue = 5;
         var6.bulletType = 1;
         FighterBullet.ReflectAction var7 = (FighterBullet.ReflectAction)Pools.obtain(FighterBullet.ReflectAction.class);
         var7.reset(3);
         var6.addGameAction(var7);
         return var6;
      }
   };
   public static final BulletGenerator j20Bullet2Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, Assets_fighter.j20Bullet2);
         var6.damageValue = 2 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   public static final BulletGenerator j20Bullet3Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, Assets_fighter.j20Bullet3);
         var6.damageValue = 3 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   public static final BulletGenerator j20Bullet5Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, Assets_fighter.j20Bullet6);
         var6.damageValue = 6 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   private static final BulletGenerator[] j20BulletGen;
   public static final BulletGenerator[] j20BulletGenCycleMore;
   public static final BulletGenerator me262Bullet1Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, Assets_fighter.me262Bullet1);
         var6.damageValue = 1 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   public static final BulletGenerator me262Bullet2Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, Assets_fighter.me262Bullet2);
         var6.damageValue = 2 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   public static final BulletGenerator me262Bullet4Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, Assets_fighter.me262Bullet4);
         var6.damageValue = 4 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   private static final BulletGenerator[] me262BulletGen;
   public static final BulletGenerator[] me262BulletGenCycleMore;
   public static final BulletGenerator me262LaseBulletGen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, (TextureRegion)Assets_ShareInAllLevel.test_boom);
         var6.damageValue = 0;
         var6.setBoundsWidth(0.0F);
         var6.setBoundsHeight(0.0F);
         var6.bulletType = 2;
         FighterBullet.BulletDisAction var7 = (FighterBullet.BulletDisAction)Pools.obtain(FighterBullet.BulletDisAction.class);
         var7.reset();
         var6.addGameAction(var7);
         return var6;
      }
   };
   public static final BulletGenerator rafaleBullet1Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, Assets_fighter.rafaleBullet1);
         var6.damageValue = 1 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   public static final BulletGenerator rafaleBullet3Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, Assets_fighter.rafaleBullet3);
         var6.damageValue = 3 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   public static final BulletGenerator rafaleBullet5Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, Assets_fighter.rafaleBullet10);
         var6.damageValue = 10 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   private static final BulletGenerator[] rafaleBulletGen;
   public static final BulletGenerator[] rafaleBulletGenCycleMore;
   public static final BulletGenerator su47Bullet1Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, Assets_fighter.su47Bullet1);
         var6.damageValue = 1 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   public static final BulletGenerator su47Bullet2Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, Assets_fighter.su47Bullet3);
         var6.damageValue = 3 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   public static final BulletGenerator su47Bullet5Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, Assets_fighter.su47Bullet5);
         var6.damageValue = 5 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   private static final BulletGenerator[] su47BulletGen;
   public static final BulletGenerator[] su47BulletGenCycleMore;
   public static final BulletGenerator typhoonBullet1Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, Assets_fighter.typhoonBullet1);
         var6.damageValue = 1 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   public static final BulletGenerator typhoonBullet2Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, Assets_fighter.typhoonBullet2);
         var6.damageValue = 2 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   public static final BulletGenerator typhoonBullet6Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, Assets_fighter.typhoonBullet5);
         var6.damageValue = 5 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   private static final BulletGenerator[] typhoonBulletGen;
   public static final BulletGenerator[] typhoonBulletGenCycleMore;
   public static final BulletGenerator wingBullet1Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, (TextureRegion)Assets_Props.wingBullet1);
         var6.damageValue = 1 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   public static final BulletGenerator wingBullet2Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, (TextureRegion)Assets_Props.wingBullet2);
         var6.damageValue = 2 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   public static final BulletGenerator wingBullet3Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, (TextureRegion)Assets_Props.wingBullet3);
         var6.damageValue = 4 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   public static final BulletGenerator wingBullet4Gen = new BulletGenerator() {
      public Bullet getABullet(GameObject var1, World var2, float var3, float var4, Vector2 var5) {
         FighterBullet var6 = FighterBullet.getAFighterBullet(var1, var3, var4, var5, (TextureRegion)Assets_Props.wingBullet4);
         var6.damageValue = 8 + Settings.getFireEffectIncrease();
         return var6;
      }
   };
   private Animation bulletAnimation;
   private TextureRegion bulletRegion;


   static {
      BulletGenerator[] var0 = new BulletGenerator[]{f22Bullet2Gen, f22Bullet2Gen, f22Bullet2Gen};
      f22BulletGen = var0;
      BulletGenerator[] var1 = new BulletGenerator[]{j20Bullet3Gen, j20Bullet3Gen, j20Bullet3Gen};
      j20BulletGen = var1;
      BulletGenerator[] var2 = new BulletGenerator[]{typhoonBullet2Gen, typhoonBullet2Gen, typhoonBullet2Gen};
      typhoonBulletGen = var2;
      BulletGenerator[] var3 = new BulletGenerator[]{rafaleBullet3Gen, rafaleBullet3Gen, rafaleBullet3Gen};
      rafaleBulletGen = var3;
      BulletGenerator[] var4 = new BulletGenerator[]{su47Bullet2Gen, su47Bullet2Gen, su47Bullet2Gen};
      su47BulletGen = var4;
      BulletGenerator[] var5 = new BulletGenerator[]{me262Bullet2Gen, me262Bullet2Gen, me262Bullet2Gen};
      me262BulletGen = var5;
      BulletGenerator[] var6 = new BulletGenerator[]{f22Bullet1Gen, f22Bullet2Gen, f22Bullet4Gen};
      f22BulletGenCycleMore = var6;
      BulletGenerator[] var7 = new BulletGenerator[]{j20Bullet2Gen, j20Bullet3Gen, j20Bullet5Gen};
      j20BulletGenCycleMore = var7;
      BulletGenerator[] var8 = new BulletGenerator[]{typhoonBullet1Gen, typhoonBullet2Gen, typhoonBullet6Gen};
      typhoonBulletGenCycleMore = var8;
      BulletGenerator[] var9 = new BulletGenerator[]{rafaleBullet1Gen, rafaleBullet3Gen, rafaleBullet5Gen};
      rafaleBulletGenCycleMore = var9;
      BulletGenerator[] var10 = new BulletGenerator[]{su47Bullet1Gen, su47Bullet2Gen, su47Bullet5Gen};
      su47BulletGenCycleMore = var10;
      BulletGenerator[] var11 = new BulletGenerator[]{me262Bullet1Gen, me262Bullet2Gen, me262Bullet4Gen};
      me262BulletGenCycleMore = var11;
      BulletPool = new XStack(200);
      bulletTextureRegion = Assets_ShareInAllLevel.test_Bullet_1;
   }

   private FighterBullet(GameObject var1, float var2, float var3, Vector2 var4, TextureRegion var5) {
      super(var1, var2, var3, (float)RegionUtilFunctions.getRegionWidth(var5) / 1.0F, (float)RegionUtilFunctions.getRegionHeight(var5) / 1.0F, var4);
      this.damageValue = 1;
      this.angle = 57.295776F * MathUtils.atan2(var4.y, var4.x) - 90.0F;
      this.bulletRegion = var5;
   }

   private FighterBullet(GameObject var1, float var2, float var3, Vector2 var4, Animation var5) {
      this(var1, var2, var3, var4, var5.getKeyFrame(0.0F));
      this.bulletAnimation = var5;
   }

   public static void cycleAFighterBullet1(FighterBullet var0) {
      if(var0 != null && BulletPool.size() < 200) {
         var0.set((GameObject)null, 0.0F, 1.0F, new Vector2(0.0F, 10.0F), var0.bulletRegion);
         BulletPool.push(var0);
      } else {
         if(BulletPool.size() >= 200) {
            Settings.appLog("pool free failed in fighterBullet1 POOL" + poolSize());
         }

      }
   }

   public static FighterBullet getAFighterBullet(GameObject var0, float var1, float var2, Vector2 var3, TextureRegion var4) {
      if(BulletPool.size() == 0) {
         for(int var6 = 0; var6 < 100; ++var6) {
            BulletPool.add(new FighterBullet((GameObject)null, 0.0F, 0.0F, new Vector2(0.0F, 10.0F), var4));
         }

         Settings.appLog("pool get failed in fighterBullet1 POOL");
      }

      FighterBullet var5 = (FighterBullet)BulletPool.pop();
      var5.set(var0, var1, var2, var3, var4);
      return var5;
   }

   public static FighterBullet getAFighterBullet(GameObject var0, float var1, float var2, Vector2 var3, Animation var4) {
      if(var4 == null) {
         Settings.e("fighter bullet animation should not be null.");
         return null;
      } else {
         FighterBullet var5 = getAFighterBullet(var0, var1, var2, var3, var4.getKeyFrame(0.0F));
         var5.set(var0, var1, var2, var3, var4);
         return var5;
      }
   }

   public static BulletGenerator[] getF22BulletGen() {
      return Settings.lapCount == 1?f22BulletGen:f22BulletGenCycleMore;
   }

   public static BulletGenerator[] getJ20BulletGen() {
      return Settings.lapCount == 1?j20BulletGen:j20BulletGenCycleMore;
   }

   public static BulletGenerator[] getMe262BulletGen() {
      return Settings.lapCount == 1?me262BulletGen:me262BulletGenCycleMore;
   }

   public static BulletGenerator[] getRafaleBulletGen() {
      return Settings.lapCount == 1?rafaleBulletGen:rafaleBulletGenCycleMore;
   }

   public static BulletGenerator[] getSu47BulletGen() {
      return Settings.lapCount == 1?su47BulletGen:su47BulletGenCycleMore;
   }

   public static BulletGenerator[] getTyphoonBulletGen() {
      return Settings.lapCount == 1?typhoonBulletGen:typhoonBulletGenCycleMore;
   }

   public static void loadResource() {
      bulletTextureRegion = Assets_ShareInAllLevel.test_Bullet_1;
   }

   public static int poolSize() {
      return BulletPool.size();
   }

   private void set(GameObject var1, float var2, float var3, Vector2 var4, TextureRegion var5) {
      super.reset(var1, var2, var3, (float)RegionUtilFunctions.getRegionWidth(var5) / 1.0F, (float)RegionUtilFunctions.getRegionHeight(var5) / 1.0F, var4);
      this.damageValue = 1;
      this.angle = 57.295776F * MathUtils.atan2(var4.y, var4.x) - 90.0F;
      this.angularSpeed = 0.0F;
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
      cycleAFighterBullet1(this);
   }

   public void render(SpriteBatch var1) {
      if(this.bulletAnimation != null) {
         this.bulletRegion = this.bulletAnimation.getKeyFrame((float)Clock.getTimeCounter());
      }

      this.render(var1, this.bulletRegion);
   }

   public void update(float var1) {
      super.update(var1);
   }

   public static class BulletDisAction extends DynamicGameObject.GameAction {

      private int frameCount = 0;


      public boolean act(float var1) {
         if(this.frameCount >= 2) {
            this.gameObject.setPosition(-4800.0F, -8000.0F);
            GameObject var2 = ((Bullet)this.gameObject).owner;
            if(!(var2 instanceof Me262LaserGun.LaserHead) && !(var2 instanceof Me262LaserGun.LaserBody)) {
               Settings.i("bullet missing." + ((Bullet)this.gameObject).bulletType + "  " + ((Bullet)this.gameObject).isAvalible() + "  " + var2.getClass().toString());
            }

            return true;
         } else {
            ++this.frameCount;
            return false;
         }
      }

      public void reset() {
         this.frameCount = 0;
      }
   }

   public static class ReflectAction extends DynamicGameObject.GameAction {

      int turnCount = 3;


      public boolean act(float var1) {
         DynamicGameObject var2 = super.gameObject;
         if(this.turnCount > 0) {
            float var3 = Math.abs(var1 * var2.velocity.x);
            float var4 = Math.abs(var1 * var2.velocity.y);
            float var8;
            int var5 = (var8 = var2.getPositionX() - var3) == 0.0F?0:(var8 < 0.0F?-1:1);
            boolean var6 = false;
            if(var5 <= 0) {
               float var9;
               int var7 = (var9 = var2.velocity.x - 0.0F) == 0.0F?0:(var9 < 0.0F?-1:1);
               var6 = false;
               if(var7 < 0) {
                  var2.velocity.x = -var2.velocity.x;
                  var6 = true;
               }
            }

            if(var2.getPositionX() >= 640.0F - var3 && var2.velocity.x > 0.0F) {
               var2.velocity.x = -var2.velocity.x;
               var6 = true;
            }

            if(var2.getPositionY() <= var4 && var2.velocity.y < 0.0F) {
               var2.velocity.y = -var2.velocity.y;
               var6 = true;
            }

            if(var2.getPositionY() >= 800.0F - var4 && var2.velocity.y > 0.0F) {
               var2.velocity.y = -var2.velocity.y;
               var6 = true;
            }

            if(var6) {
               var2.setAngle(var2.velocity.angle() - 90.0F);
               this.turnCount += -1;
            }

            return false;
         } else {
            return true;
         }
      }

      public void reset(int var1) {
         if(var1 < 0) {
            var1 = 0;
         }

         this.turnCount = var1;
      }
   }
}
