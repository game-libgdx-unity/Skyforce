package com.doodlegames.air.force.enemy.level2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.cannon.Cannon1;
import com.doodlegames.air.force.enemy.cannon.Cannon5;
import com.doodlegames.air.force.enemy.cannon.CommonCannon;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.path.EnemyMutiMove;
import com.doodlegames.air.force.resource.Assets_level2;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class BigUfo extends Enemy {

   private static final Vector2[] ANGLECYCLE;
   public static final Vector2 CANNONLEFTPOSITION;
   public static final Vector2 CANNONRIGHTPOSITION;
   public static final Vector2 CANNONTOPPOSITION;
   public static final int DEFAULTHEALTHYDEGREE = 10;
   private static final float SPAN = 0.6F;
   private static final float TIMEOFPLAYCRASHANIMATION = 4.0F;
   private static TextureAtlas.AtlasRegion enemyTextureRegion;
   private CommonCannon cannonLeft;
   private CommonCannon cannonRight;
   private Cannon5 cannonTop;
   private Clock clockPursue;
   private Clock clockShooting;
   private boolean crashJump;
   private Enemy[] hitMap;
   final float shootModeChangeTime;
   private BigUfo.ShootingMode shootingMode;
   private float startShooting;


   static {
      Vector2[] var0 = new Vector2[]{new Vector2(0.0F, 0.0F), new Vector2(0.0F, 30.0F), new Vector2(30.0F, 30.0F), new Vector2(30.0F, 60.0F), new Vector2(60.0F, 60.0F), new Vector2(60.0F, 30.0F), new Vector2(30.0F, 30.0F), new Vector2(30.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, -30.0F), new Vector2(-30.0F, -30.0F), new Vector2(-30.0F, -60.0F), new Vector2(-60.0F, -60.0F), new Vector2(-60.0F, -30.0F), new Vector2(-30.0F, -30.0F), new Vector2(-30.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F), new Vector2(0.0F, 0.0F)};
      ANGLECYCLE = var0;
      CANNONLEFTPOSITION = new Vector2((76.0F + (float)Cannon1.canonTextureRegion.getRegionWidth() / 2.0F) / 1.0F, (3.0F + (float)Cannon1.canonTextureRegion.getRegionHeight() / 2.0F) / 1.0F);
      CANNONRIGHTPOSITION = new Vector2((141.0F + (float)Cannon1.canonTextureRegion.getRegionWidth() / 2.0F) / 1.0F, (3.0F + (float)Cannon1.canonTextureRegion.getRegionHeight() / 2.0F) / 1.0F);
      CANNONTOPPOSITION = new Vector2((85.0F + (float)Cannon5.REGIONWIDTH / 2.0F) / 1.0F, (49.0F + (float)Cannon5.REGIONHEIGHT / 2.0F) / 1.0F);
      enemyTextureRegion = Assets_level2.atlas_Enemy.findRegion("nazi_big_ufo");
   }

   public BigUfo(final World world, final float n, final float n2) {

	   super(world, 10, n + 0.5f,  n2 + 0.5f, BigUfo.enemyTextureRegion.rotate?
			   (float)BigUfo.enemyTextureRegion.getRegionHeight() / 1.0f: (float)BigUfo.enemyTextureRegion.getRegionWidth() / 1.0f  , 
			   BigUfo.enemyTextureRegion.rotate? (float)BigUfo.enemyTextureRegion.getRegionWidth() / 1.0f:
				   (float)BigUfo.enemyTextureRegion.getRegionHeight() / 1.0f );
      

       this.crashJump = false;

       this.clockShooting = new Clock(4.9f, 5.0f);

       this.clockPursue = new Clock(1.5f);

       this.velocity.set(Settings.backgroundVelocity);

       this.cannonLeft = CommonCannon.getAutoCannon1(world, new Clock(4.5f, 5.0f), 10, this, 76.0f, 3.0f, 300.0f);

       this.cannonRight = CommonCannon.getAutoCannon1(world, new Clock(4.1f, 5.0f), 10, this, 141.0f, 3.0f, 300.0f);

       this.cannonTop = new Cannon5(this.world, this.bounds.x + BigUfo.CANNONTOPPOSITION.x, this.bounds.y + BigUfo.CANNONTOPPOSITION.y);

       this.hitMap = new Enemy[3];

       this.shootingMode = ShootingMode.Pursue;

       this.shootModeChangeTime = 5.0f + 10.0f * world.rand.nextFloat();

       this.enemyRegion = BigUfo.enemyTextureRegion;

   }

   

   public static void loadResource() {
      enemyTextureRegion = Assets_level2.atlas_Enemy.findRegion("nazi_big_ufo");
   }

   public void beHitByBullet(Bullet var1, int var2) {
      this.hitMap[var2].beHitByBullet(var1);
      if(1 == this.state) {
         ;
      }

   }

   public List<Rectangle> getHitRectangle() {
      ArrayList var1 = new ArrayList();
      boolean var2 = this.cannonLeft.isCrashed();
      int var3 = 0;
      if(!var2) {
         var1.add(this.cannonLeft.bounds);
         Enemy[] var12 = this.hitMap;
         int var13 = 0 + 1;
         var12[0] = this.cannonLeft;
         var3 = var13;
      }

      if(!this.cannonRight.isCrashed()) {
         var1.add(this.cannonRight.bounds);
         Enemy[] var9 = this.hitMap;
         int var10 = var3 + 1;
         var9[var3] = this.cannonRight;
         var3 = var10;
      }

      if(!this.cannonTop.isCrashed()) {
         var1.add(this.cannonTop.bounds);
         Enemy[] var6 = this.hitMap;
         int var7 = var3 + 1;
         var6[var3] = this.cannonTop;
         var3 = var7;
      }

      for(int var4 = var3; var4 < 3; ++var4) {
         this.hitMap[var4] = null;
      }

      return var1;
   }

   public float getTimeOfCrash() {
      return 4.0F;
   }

   public boolean isCrashed() {
      return false;
   }

   public void render(SpriteBatch var1) {
      super.render(var1);
      this.cannonLeft.render(var1);
      this.cannonRight.render(var1);
      this.cannonTop.render(var1);
   }

   public BigUfo.ShootingMode switchShootingMode() {
      if(this.shootingMode == BigUfo.ShootingMode.Sweep) {
         this.shootingMode = BigUfo.ShootingMode.Pursue;
         this.clockShooting.resetClock(5.0F * this.world.rand.nextFloat(), 5.0F, 0.0F, (byte)1);
         this.clockShooting.checkLondDelay = true;
      } else if(this.shootingMode == BigUfo.ShootingMode.Pursue) {
         this.shootingMode = BigUfo.ShootingMode.Sweep;
         this.startShooting = (float)Clock.getTimeCounter();
         this.clockShooting.resetClock(0.0F, 0.6F, 0.0F, (byte)1);
         this.clockShooting.checkLondDelay = false;
      } else {
         this.shootingMode = BigUfo.ShootingMode.Pursue;
      }

      return this.shootingMode;
   }

   public void update(float var1) {
      super.update(var1);
      this.cannonTop.setPosition(this.bounds.x + CANNONTOPPOSITION.x, this.bounds.y + CANNONTOPPOSITION.y);
      this.cannonLeft.update(var1);
      this.cannonRight.update(var1);
      this.cannonTop.update(var1);
      if(this.stateTime > this.shootModeChangeTime && this.shootingMode != BigUfo.ShootingMode.Sweep) {
         this.switchShootingMode();
      }

   }

   public void updateDynamicParameter(float var1) {
      super.updateDynamicParameter(var1);
      if(this.moveProcessor != null && !this.crashJump && this.cannonLeft.isCrashed() && this.cannonRight.isCrashed() && this.cannonTop.isCrashed()) {
         try {
    	  ((EnemyMutiMove)((EnemyMutiMove)this.moveProcessor)).setNextPath();
         }catch(Exception ex){}
         this.crashJump = true;
      }

   }

   public void updateShooting(float var1) {
      boolean var2 = true;
      if(this.shootingMode == BigUfo.ShootingMode.Sweep) {
         float var8 = (float)Clock.getTimeCounter() - this.startShooting;
         int var9 = (int)(var8 / 0.6F);
         int var10 = var9 % ANGLECYCLE.length;
         float var11 = (ANGLECYCLE[var10].y - ANGLECYCLE[var10].x) / 0.6F * (var8 - 0.6F * (float)var9) + ANGLECYCLE[var10].x;
         if(ANGLECYCLE[var10].x != ANGLECYCLE[var10].y) {
            if(!this.cannonTop.isCrashed()) {
               this.cannonTop.rotateToOnly(var11);
            }

            return;
         }

         if(var10 > 15) {
            var2 = false;
         }
      }

      if(this.shootingMode == BigUfo.ShootingMode.Pursue && this.clockPursue.isFired()) {
         float var3 = this.cannonTop.getPositionX();
         float var4 = this.cannonTop.getPositionY();
         float var5 = this.world.fighter.getPositionX() - var3;
         float var6 = this.world.fighter.getPositionY() - var4;
         if(var6 < 0.0F) {
            float var7 = 90.0F + (float)(57.2957763671875D * Math.atan2((double)var6, (double)var5));
            if(!this.cannonTop.isCrashed()) {
               this.cannonTop.rotateTo(var7);
            }
         }
      }

      if(this.clockShooting.isFired() && var2) {
         this.cannonTop.shooting();
      }
   }

   protected boolean usePathAngle() {
      return false;
   }

   private static enum ShootingMode {

      Pursue,
      Sweep
   }
}
