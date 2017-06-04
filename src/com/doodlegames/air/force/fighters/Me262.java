package com.doodlegames.air.force.fighters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector2Utils;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.fighters.gun.Me262BlueGun;
import com.doodlegames.air.force.fighters.gun.Me262LaserGun;
import com.doodlegames.air.force.fighters.gun.Me262RedGun;
import com.doodlegames.air.force.fighters.gun.Me262YellowGun;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldBoomer;
import com.doodlegames.air.force.game.bullet.BoomBullet;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.resource.Assets_fighter;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;

import java.util.List;

public class Me262 extends Fighter implements World.RenderObject {

   public static final int DEFAULTLIVES = 0;
   private static final int InitHealthy = 1;
   public static final Vector2[] SHOOTPOINT;
   private static final float timeOfPlayCrashedAnimation = 0.3F;
   WorldBoomer.BoomPoint boomPoint;
   Me262LaserGun snakeLaserGun = new Me262LaserGun(this);


   static {
      Vector2[] var0 = new Vector2[]{new Vector2(2.0F, 63.0F), new Vector2(7.0F, 68.0F), new Vector2(12.0F, 74.0F), new Vector2(48.0F, 74.0F), new Vector2(53.0F, 68.0F), new Vector2(58.0F, 63.0F)};
      SHOOTPOINT = var0;
   }

   public Me262(World var1, float var2, float var3) {
      super(var1, var2, var3, (float)RegionUtilFunctions.getRegionWidth(Assets_fighter.me262Stand) / 1.0F, (float)RegionUtilFunctions.getRegionHeight(Assets_fighter.me262Stand) / 1.0F);
      this.initMe262();
      this.avalibleGuns[0] = new Me262RedGun(this);
      this.avalibleGuns[1] = new Me262YellowGun(this);
      this.avalibleGuns[2] = new Me262BlueGun(this);
      this.currentGun = this.avalibleGuns[0];
      this.currentGun.reset();
      if(!this.loadFighter()) {
         ;
      }

      this.constructBoomer();
   }

   private void constructBoomer() {
      this.boomPoint = new WorldBoomer.BoomPoint(this.world);
      this.boomPoint.setScale(1.2F);
   }

   public void beginCheck() {
      super.beginCheck();
      this.boomPoint.setBoomPosition(this.getPositionX(), this.getPositionY());
      this.boomPoint.beginCheck();
   }

   public void boomStart() {
      this.world.getParentScreen().playBoomLaunchAnimation();
      if(!this.isBooming()) {
         Vector2 var1 = Vector2Utils.tmp.set(0.0F, 600.0F);
         float var2 = this.getPositionX();
         float var3 = this.getPositionY();

         for(int var4 = 0; var4 < 3; ++var4) {
            var1.rotate((float)(Clock.rand.nextInt(120) + var4 * 120));
            Bullet var6 = BoomBullet.me262BoomBulletGen.getABullet(this, this.world, var2, var3, var1);
            Fighter.bullets.add(var6);
         }

         this.boomPoint.boomeStartAt(var2, var3, 0.0F);
         this.world.worldAudio.playNaziBigBoom();
      }
   }

   public boolean changeGun(int var1) {
      boolean var2 = super.changeGun(var1);
      if(this.currentGun != null) {
         int var3 = this.currentGun.getLevel();
         this.snakeLaserGun.setLevel(var3);
      }

      return var2;
   }

   public void checking(Enemy var1, List<Rectangle> var2) {
      super.checking(var1, var2);
      this.boomPoint.checking(var1, var2);
   }

   public void endCheck() {
      super.endCheck();
      this.boomPoint.endCheck();
   }

   public float getTimeOfCrash() {
      return 0.3F;
   }

   public void initMe262() {
      this.fightStand = Assets_fighter.me262Stand;
      this.fightLeft = Assets_fighter.me262FlyingToLeft;
      this.fightRight = Assets_fighter.me262FlyingToRight;
      this.healthyDegree = 1;
      this.world.setSnakeGunRenderInterface(this);
      this.snakeLaserGun.reset();
   }

   public boolean isBooming() {
      return !this.boomPoint.boomingCompleted();
   }

   public void recovery() {
      float var1 = (float)RegionUtilFunctions.getRegionWidth(Assets_fighter.me262Stand) / 1.0F;
      float var2 = (float)RegionUtilFunctions.getRegionHeight(Assets_fighter.me262Stand) / 1.0F;
      super.resetFighter(this.world, 320.0F, var2 / 2.0F, var1, var2);
      this.initMe262();
   }

   public void render(SpriteBatch var1) {
      super.render(var1);
   }

   public void renderObject(SpriteBatch var1) {
      if(!this.isCrashed() && this.bounds.y > -this.bounds.height) {
         this.snakeLaserGun.draw(var1);
      }

   }

   public void update(float var1) {
      super.update(var1);
      this.snakeLaserGun.updateShooting(var1);
   }

   public void updateBoomer(float var1) {
      this.boomPoint.updateBoomer(var1);
   }
}
