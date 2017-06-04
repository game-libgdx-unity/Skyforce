package com.doodlegames.air.force.enemy.level7;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyProXY;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.enemy.HitDistribute;
import com.doodlegames.air.force.game.EnemyGun;
import com.doodlegames.air.force.game.GameObject;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldAnimationPlayer;
import com.doodlegames.air.force.game.WorldAudio;
import com.doodlegames.air.force.game.Gun.AutoTurningBulletGun;
import com.doodlegames.air.force.game.Gun.BulletGunAlien;
import com.doodlegames.air.force.game.Gun.EnemyActionGun;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.game.bullet.BulletScreen;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.game.bullet.EnemyBullet2;
import com.doodlegames.air.force.game.laser.CommonLaser;
import com.doodlegames.air.force.game.laser.LaserGun;
import com.doodlegames.air.force.resource.Assets_ShareInAlien;
import com.doodlegames.air.force.resource.Assets_level7;
import com.doodlegames.air.force.screen.GameScreen;
import com.doodlegames.air.force.screen.XScreen;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

public class BossLv7L extends Enemy implements World.RenderObject, World.DelayTimeToNextProvider {

   private static  float CRAFT1HEIGHT = 0.0F;
   private static final Vector2 CRAFT1POSITION;
   private static  float CRAFT1WIDTH = 0.0F;
   private static  float CRAFT2HEIGHT = 0.0F;
   private static final Vector2 CRAFT2POSITION;
   private static  float CRAFT2WIDTH = 0.0F;
   private static float CRAFTSHADOWHEIGHT = 0.0F;
   private static float CRAFTSHADOWWIDTH = 0.0F;
   public static final int DEFAULTHEALTHYDEGREE = 900;
   public static  float EGGHEIGHT = 0.0F;
   private static final Vector2[] EGGPOSITION;
   public static  float EGGWIDTH = 0.0F;
   private static final Vector2[] FORCEPOSITION;
   protected static final int HEADREFPOSITIONX = 213;
   protected static final int HEADREFPOSITIONY = 6044;
   private static  float HEADSHADOWHEIGHT = 0.0F;
   private static final Vector2 HEADSHADOWPOSITION;
   private static  float HEADSHADOWWIDTH = 0.0F;
   private static final Vector2 HEADSHOOTPOSITION;
   public static int HEIGHT = 0;
   private static final Rectangle HITRECHEAD;
   private static final int SILKNUM = 6;
   private static final Vector2[] SILKPOSITION;
   private static final float[] SILKSHADOWHEIGHT;
   private static final Vector2[] SILKSHADOWPOSITION;
   private static final float[] SILKSHADOWWIDTH;
   public static final float SLAWDELTA = 150.0F;
   private static final Vector2[] SLAWPOSITION;
   private static  float SLIMEHEIGHT = 0.0F;
   private static final Vector2 SLIMEPOSITION;
   private static  float SLIMEWIDTH = 0.0F;
   private static final float TIMEOFPLAYCRASHANIMATION = 50.0F;
   private static final Vector2[] WALL0POSITION;
   private static final Vector2[] WALL1POSITION;
   private static final float[] WALLHEIGHT;
   private static final float[] WALLWIDTH;
   public static int WIDTH = 0;
   private static TextureAtlas.AtlasRegion craftRegion1;
   private static TextureAtlas.AtlasRegion craftRegion2;
   private static TextureAtlas.AtlasRegion craftShadowRegion;
   private static final float craftShadowX = -133.0F;
   private static final float craftShadowY = -132.0F;
   private static TextureAtlas.AtlasRegion eggRegion;
   private static TextureAtlas.AtlasRegion enemyTextureRegion;
   private static TextureAtlas.AtlasRegion headShadowRegion;
   private static final TextureAtlas.AtlasRegion[] silkShadowRegion;
   private static TextureAtlas.AtlasRegion slimeRegion;
   private static final TextureAtlas.AtlasRegion[] wallRegion;
   private EnemyGun[] avalibleGun;
   private BossLv7L.BossLv7LState bossState;
   private BossLv7L.CircleBoomBulletGun circleBoomBulletGun;
   private BossLv7L.CircleBoomBulletGun circleBoomBulletGun2;
   private Clock clockChangeShoot;
   private float crashTimeStamp;
   private AutoTurningBulletGun crossBulletGun;
   private EnemyGun currentGun;
   float dropTime;
   private BossLv7Egg.EggManager eggManager;
   private BossLv7L.BossLv7EnterState2 enterState2Action;
   private BossLv7Force.ForceManager forceManager;
   private HitDistribute hitProcessor;
   private List<Rectangle> hitRSHead;
   private LaserGun laserGunPush;
   private LaserGun laserGunPush2;
   private LaserGun laserGunSwap;
   private LaserGun laserGunSwap2;
   private BulletGunAlien overBulletGun;
   private BulletGunAlien pointBulletGun;
   private BulletGunAlien rocketBulletGun;
   private BossLv7LSilk[] silks;
   private BossLv7LSlaw[] slaws;
   private WorldAnimationPlayer.WorldAnimation worldAnimation;


   static {
      TextureAtlas.AtlasRegion[] var0 = new TextureAtlas.AtlasRegion[]{Assets_level7.atlas_Enemy.findRegion("lv_7_boss_wall", 0), Assets_level7.atlas_Enemy.findRegion("lv_7_boss_wall", 1)};
      wallRegion = var0;
      float[] var1 = new float[2];
      int var2;
      if(wallRegion[0].rotate) {
         var2 = wallRegion[0].getRegionHeight();
      } else {
         var2 = wallRegion[0].getRegionWidth();
      }

      var1[0] = (float)var2 / 1.0F;
      int var3;
      if(wallRegion[1].rotate) {
         var3 = wallRegion[1].getRegionHeight();
      } else {
         var3 = wallRegion[1].getRegionWidth();
      }

      var1[1] = (float)var3 / 1.0F;
      WALLWIDTH = var1;
      float[] var4 = new float[2];
      int var5;
      if(wallRegion[0].rotate) {
         var5 = wallRegion[0].getRegionWidth();
      } else {
         var5 = wallRegion[0].getRegionHeight();
      }

      var4[0] = (float)var5 / 1.0F;
      int var6;
      if(wallRegion[1].rotate) {
         var6 = wallRegion[1].getRegionWidth();
      } else {
         var6 = wallRegion[1].getRegionHeight();
      }

      var4[1] = (float)var6 / 1.0F;
      WALLHEIGHT = var4;
      Vector2[] var7 = new Vector2[]{new Vector2(-213.0F, 184.0F), new Vector2(-213.0F, -296.0F), new Vector2(347.0F, 184.0F), new Vector2(347.0F, -296.0F)};
      WALL0POSITION = var7;
      Vector2[] var8 = new Vector2[]{new Vector2(-213.0F, 104.0F), new Vector2(-213.0F, 24.0F), new Vector2(-213.0F, -56.0F), new Vector2(-213.0F, -136.0F), new Vector2(-213.0F, -216.0F), new Vector2(-133.0F, 184.0F), new Vector2(-53.0F, 184.0F), new Vector2(27.0F, 184.0F), new Vector2(107.0F, 184.0F), new Vector2(187.0F, 184.0F), new Vector2(267.0F, 184.0F), new Vector2(347.0F, 104.0F), new Vector2(347.0F, 24.0F), new Vector2(347.0F, -56.0F), new Vector2(347.0F, -136.0F), new Vector2(347.0F, -216.0F)};
      WALL1POSITION = var8;
      craftShadowRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_craft_shadow");
      int var9;
      if(craftShadowRegion.rotate) {
         var9 = craftShadowRegion.getRegionHeight();
      } else {
         var9 = craftShadowRegion.getRegionWidth();
      }

      CRAFTSHADOWWIDTH = (float)var9 / 1.0F;
      int var10;
      if(craftShadowRegion.rotate) {
         var10 = craftShadowRegion.getRegionWidth();
      } else {
         var10 = craftShadowRegion.getRegionHeight();
      }

      CRAFTSHADOWHEIGHT = (float)var10 / 1.0F;
      slimeRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_slime");
      int var11;
      if(slimeRegion.rotate) {
         var11 = slimeRegion.getRegionHeight();
      } else {
         var11 = slimeRegion.getRegionWidth();
      }

      SLIMEWIDTH = (float)var11 / 1.0F;
      int var12;
      if(slimeRegion.rotate) {
         var12 = slimeRegion.getRegionWidth();
      } else {
         var12 = slimeRegion.getRegionHeight();
      }

      SLIMEHEIGHT = (float)var12 / 1.0F;
      SLIMEPOSITION = new Vector2(1.0F, -18.0F);
      craftRegion1 = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_craft", 1);
      int var13;
      if(craftRegion1.rotate) {
         var13 = craftRegion1.getRegionHeight();
      } else {
         var13 = craftRegion1.getRegionWidth();
      }

      CRAFT1WIDTH = (float)var13 / 1.0F;
      int var14;
      if(craftRegion1.rotate) {
         var14 = craftRegion1.getRegionWidth();
      } else {
         var14 = craftRegion1.getRegionHeight();
      }

      CRAFT1HEIGHT = (float)var14 / 1.0F;
      CRAFT1POSITION = new Vector2(-202.0F, -37.0F);
      craftRegion2 = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_craft", 2);
      int var15;
      if(craftRegion2.rotate) {
         var15 = craftRegion2.getRegionHeight();
      } else {
         var15 = craftRegion2.getRegionWidth();
      }

      CRAFT2WIDTH = (float)var15 / 1.0F;
      int var16;
      if(craftRegion2.rotate) {
         var16 = craftRegion2.getRegionWidth();
      } else {
         var16 = craftRegion2.getRegionHeight();
      }

      CRAFT2HEIGHT = (float)var16 / 1.0F;
      CRAFT2POSITION = new Vector2(-202.0F, -79.0F);
      eggRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_egg", 0);
      int var17;
      if(eggRegion.rotate) {
         var17 = eggRegion.getRegionHeight();
      } else {
         var17 = eggRegion.getRegionWidth();
      }

      EGGWIDTH = (float)var17 / 1.0F;
      int var18;
      if(eggRegion.rotate) {
         var18 = eggRegion.getRegionWidth();
      } else {
         var18 = eggRegion.getRegionHeight();
      }

      EGGHEIGHT = (float)var18 / 1.0F;
      Vector2[] var19 = new Vector2[]{new Vector2(-2.0F, -65.0F), new Vector2(40.0F, -95.0F), new Vector2(62.0F, -43.0F), new Vector2(163.0F, -93.0F), new Vector2(157.0F, -22.0F), new Vector2(99.0F, -71.0F), new Vector2(-6.0F, -9.0F), new Vector2(32.0F, -120.0F), new Vector2(117.0F, -114.0F)};
      EGGPOSITION = var19;
      Vector2[] var20 = new Vector2[]{new Vector2(-2.0F, -65.0F), new Vector2(40.0F, -95.0F), new Vector2(62.0F, -43.0F), new Vector2(163.0F, -93.0F), new Vector2(157.0F, -22.0F)};
      FORCEPOSITION = var20;
      TextureAtlas.AtlasRegion[] var21 = new TextureAtlas.AtlasRegion[]{Assets_level7.atlas_Enemy.findRegion("lv_7_boss_silk_shadow", 0), new TextureAtlas.AtlasRegion(Assets_level7.atlas_Enemy.findRegion("lv_7_boss_silk_shadow", 0)), Assets_level7.atlas_Enemy.findRegion("lv_7_boss_silk_shadow", 1), new TextureAtlas.AtlasRegion(Assets_level7.atlas_Enemy.findRegion("lv_7_boss_silk_shadow", 1)), Assets_level7.atlas_Enemy.findRegion("lv_7_boss_silk_shadow", 2), new TextureAtlas.AtlasRegion(Assets_level7.atlas_Enemy.findRegion("lv_7_boss_silk_shadow", 2))};
      silkShadowRegion = var21;
      boolean var22 = silkShadowRegion[1].isFlipX();
      boolean var23;
      if(!silkShadowRegion[1].rotate) {
         var23 = true;
      } else {
         var23 = false;
      }

      if(var22 != var23) {
         silkShadowRegion[1].flip(true, false);
      }

      if(silkShadowRegion[1].isFlipY() != silkShadowRegion[1].rotate) {
         silkShadowRegion[1].flip(false, true);
      }

      boolean var24 = silkShadowRegion[3].isFlipX();
      boolean var25;
      if(!silkShadowRegion[3].rotate) {
         var25 = true;
      } else {
         var25 = false;
      }

      if(var24 != var25) {
         silkShadowRegion[3].flip(true, false);
      }

      if(silkShadowRegion[3].isFlipY() != silkShadowRegion[3].rotate) {
         silkShadowRegion[3].flip(false, true);
      }

      boolean var26 = silkShadowRegion[5].isFlipX();
      boolean var27;
      if(!silkShadowRegion[5].rotate) {
         var27 = true;
      } else {
         var27 = false;
      }

      if(var26 != var27) {
         silkShadowRegion[5].flip(true, false);
      }

      if(silkShadowRegion[5].isFlipY() != silkShadowRegion[5].rotate) {
         silkShadowRegion[5].flip(false, true);
      }

      silkShadowRegion[1].offsetX = 0.0F;
      silkShadowRegion[1].offsetY = 0.0F;
      silkShadowRegion[3].offsetX = 0.0F;
      silkShadowRegion[3].offsetY = 0.0F;
      silkShadowRegion[5].offsetX = 0.0F;
      silkShadowRegion[5].offsetY = 0.0F;
      float[] var28 = new float[6];
      int var29;
      if(silkShadowRegion[0].rotate) {
         var29 = silkShadowRegion[0].getRegionHeight();
      } else {
         var29 = silkShadowRegion[0].getRegionWidth();
      }

      var28[0] = (float)var29 / 1.0F;
      int var30;
      if(silkShadowRegion[1].rotate) {
         var30 = silkShadowRegion[1].getRegionHeight();
      } else {
         var30 = silkShadowRegion[1].getRegionWidth();
      }

      var28[1] = (float)var30 / 1.0F;
      int var31;
      if(silkShadowRegion[2].rotate) {
         var31 = silkShadowRegion[2].getRegionHeight();
      } else {
         var31 = silkShadowRegion[2].getRegionWidth();
      }

      var28[2] = (float)var31 / 1.0F;
      int var32;
      if(silkShadowRegion[3].rotate) {
         var32 = silkShadowRegion[3].getRegionHeight();
      } else {
         var32 = silkShadowRegion[3].getRegionWidth();
      }

      var28[3] = (float)var32 / 1.0F;
      int var33;
      if(silkShadowRegion[4].rotate) {
         var33 = silkShadowRegion[4].getRegionHeight();
      } else {
         var33 = silkShadowRegion[4].getRegionWidth();
      }

      var28[4] = (float)var33 / 1.0F;
      int var34;
      if(silkShadowRegion[5].rotate) {
         var34 = silkShadowRegion[5].getRegionHeight();
      } else {
         var34 = silkShadowRegion[5].getRegionWidth();
      }

      var28[5] = (float)var34 / 1.0F;
      SILKSHADOWWIDTH = var28;
      float[] var35 = new float[6];
      int var36;
      if(silkShadowRegion[0].rotate) {
         var36 = silkShadowRegion[0].getRegionWidth();
      } else {
         var36 = silkShadowRegion[0].getRegionHeight();
      }

      var35[0] = (float)var36 / 1.0F;
      int var37;
      if(silkShadowRegion[1].rotate) {
         var37 = silkShadowRegion[1].getRegionWidth();
      } else {
         var37 = silkShadowRegion[1].getRegionHeight();
      }

      var35[1] = (float)var37 / 1.0F;
      int var38;
      if(silkShadowRegion[2].rotate) {
         var38 = silkShadowRegion[2].getRegionWidth();
      } else {
         var38 = silkShadowRegion[2].getRegionHeight();
      }

      var35[2] = (float)var38 / 1.0F;
      int var39;
      if(silkShadowRegion[3].rotate) {
         var39 = silkShadowRegion[3].getRegionWidth();
      } else {
         var39 = silkShadowRegion[3].getRegionHeight();
      }

      var35[3] = (float)var39 / 1.0F;
      int var40;
      if(silkShadowRegion[4].rotate) {
         var40 = silkShadowRegion[4].getRegionWidth();
      } else {
         var40 = silkShadowRegion[4].getRegionHeight();
      }

      var35[4] = (float)var40 / 1.0F;
      int var41;
      if(silkShadowRegion[5].rotate) {
         var41 = silkShadowRegion[5].getRegionWidth();
      } else {
         var41 = silkShadowRegion[5].getRegionHeight();
      }

      var35[5] = (float)var41 / 1.0F;
      SILKSHADOWHEIGHT = var35;
      Vector2[] var42 = new Vector2[]{new Vector2(-213.0F, -114.0F), new Vector2(131.0F, -114.0F), new Vector2(-213.0F, 115.0F), new Vector2(159.0F, 115.0F), new Vector2(-213.0F, 149.0F), new Vector2(147.0F, 149.0F)};
      SILKSHADOWPOSITION = var42;
      enemyTextureRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_head");
      int var43;
      if(enemyTextureRegion.rotate) {
         var43 = enemyTextureRegion.getRegionHeight();
      } else {
         var43 = enemyTextureRegion.getRegionWidth();
      }

      WIDTH = var43;
      int var44;
      if(enemyTextureRegion.rotate) {
         var44 = enemyTextureRegion.getRegionWidth();
      } else {
         var44 = enemyTextureRegion.getRegionHeight();
      }

      HEIGHT = var44;
      headShadowRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_head_shadow");
      int var45;
      if(headShadowRegion.rotate) {
         var45 = headShadowRegion.getRegionHeight();
      } else {
         var45 = headShadowRegion.getRegionWidth();
      }

      HEADSHADOWWIDTH = (float)var45 / 1.0F;
      int var46;
      if(headShadowRegion.rotate) {
         var46 = headShadowRegion.getRegionWidth();
      } else {
         var46 = headShadowRegion.getRegionHeight();
      }

      HEADSHADOWHEIGHT = (float)var46 / 1.0F;
      HEADSHADOWPOSITION = new Vector2(38.0F, -72.0F);
      Vector2[] var47 = new Vector2[]{new Vector2((-213.0F + (float)BossLv7LSilk.WIDTH[0] / 2.0F) / 1.0F, (-34.0F + (float)BossLv7LSilk.HEIGHT[0] / 2.0F) / 1.0F), new Vector2((132.0F + (float)BossLv7LSilk.WIDTH[1] / 2.0F) / 1.0F, (-34.0F + (float)BossLv7LSilk.HEIGHT[1] / 2.0F) / 1.0F), new Vector2((-213.0F + (float)BossLv7LSilk.WIDTH[2] / 2.0F) / 1.0F, (173.0F + (float)BossLv7LSilk.HEIGHT[2] / 2.0F) / 1.0F), new Vector2((160.0F + (float)BossLv7LSilk.WIDTH[3] / 2.0F) / 1.0F, (173.0F + (float)BossLv7LSilk.HEIGHT[3] / 2.0F) / 1.0F), new Vector2((-175.0F + (float)BossLv7LSilk.WIDTH[4] / 2.0F) / 1.0F, (235.0F + (float)BossLv7LSilk.HEIGHT[4] / 2.0F) / 1.0F), new Vector2((149.0F + (float)BossLv7LSilk.WIDTH[5] / 2.0F) / 1.0F, (235.0F + (float)BossLv7LSilk.HEIGHT[5] / 2.0F) / 1.0F)};
      SILKPOSITION = var47;
      Vector2[] var48 = new Vector2[]{new Vector2((-114.0F + (float)BossLv7LSlaw.WIDTH / 2.0F) / 1.0F, (51.0F + (float)BossLv7LSlaw.HEIGHT / 2.0F) / 1.0F), new Vector2((239.0F + (float)BossLv7LSlaw.WIDTH / 2.0F) / 1.0F, (51.0F + (float)BossLv7LSlaw.HEIGHT / 2.0F) / 1.0F)};
      SLAWPOSITION = var48;
      HITRECHEAD = new Rectangle(30.0F, 150.0F, 152.0F, 100.0F);
      HEADSHOOTPOSITION = new Vector2(107.0F, 208.0F);
   }

   public BossLv7L(World var1, float var2, float var3) {
      super(var1, Settings.lapFixBossDegree(900), var2, var3, (float)WIDTH / 1.0F, (float)HEIGHT / 1.0F);
      this.enemyRegion = enemyTextureRegion;
      this.enemyBroRegion = enemyTextureRegion;
      this.outRender = true;
      BossLv7LSilk[] var4 = new BossLv7LSilk[]{new BossLv7LSilk(var1, this, this.bounds.x + SILKPOSITION[0].x, this.bounds.y + SILKPOSITION[0].y, 0), new BossLv7LSilk(var1, this, this.bounds.x + SILKPOSITION[1].x, this.bounds.y + SILKPOSITION[1].y, 1), new BossLv7LSilk(var1, this, this.bounds.x + SILKPOSITION[2].x, this.bounds.y + SILKPOSITION[2].y, 2), new BossLv7LSilk(var1, this, this.bounds.x + SILKPOSITION[3].x, this.bounds.y + SILKPOSITION[3].y, 3), new BossLv7LSilk(var1, this, this.bounds.x + SILKPOSITION[4].x, this.bounds.y + SILKPOSITION[4].y, 4), new BossLv7LSilk(var1, this, this.bounds.x + SILKPOSITION[5].x, this.bounds.y + SILKPOSITION[5].y, 5)};
      this.silks = var4;
      BossLv7LSlaw[] var5 = new BossLv7LSlaw[]{new BossLv7LSlaw(var1, this, SLAWPOSITION[0].x, SLAWPOSITION[0].y), new BossLv7LSlaw(var1, this, SLAWPOSITION[1].x, SLAWPOSITION[1].y)};
      this.slaws = var5;
      this.hitProcessor = new HitDistribute(this, 76);
      this.hitRSHead = new ArrayList(1);
      this.hitRSHead.add(new Rectangle(this.bounds.x + HITRECHEAD.x, this.bounds.y + HITRECHEAD.y, HITRECHEAD.width, HITRECHEAD.height));
      this.bossState = BossLv7L.BossLv7LState.Appearing;
      this.velocity.set(0.0F, Settings.backgroundVelocity.y);
      this.clockChangeShoot = new Clock(0.0F, 5.0F, Clock.ClockType.FireOnlyOnce);
      Clock var8 = new Clock(6.0F, 6.0F, 0.15F, (byte)1);
      this.pointBulletGun = new BulletGunAlien(this, var1, EnemyBullet1.alienBulletGen, var8, HEADSHOOTPOSITION, new float[]{0.0F, -300.0F});
      this.pointBulletGun.setPursueParameter((Clock)null, (Vector2)null, 0.0F, 0.0F);
      this.pointBulletGun.shootPointIsAbsolute = false;
      Clock var10 = new Clock(6.0F, 6.0F, 0.15F, (byte)1);
      this.overBulletGun = new BulletGunAlien(this, var1, EnemyBullet1.alienBulletGen, var10, HEADSHOOTPOSITION, new float[]{0.0F, -300.0F});
      this.overBulletGun.setPursueParameter((Clock)null, (Vector2)null, var1.fighter.bounds.width, 0.0F);
      this.overBulletGun.shootPointIsAbsolute = false;
      this.overBulletGun.autoChangeOffset = true;
      Clock var12 = new Clock(10.0F, 10.0F, 0.48F, (byte)6);
      this.crossBulletGun = new AutoTurningBulletGun(this, var1, EnemyBullet1.alienBulletGen, var12, HEADSHOOTPOSITION, 20.0F, new float[]{0.0F, -250.0F, 0.0F, 250.0F, 250.0F, 0.0F, -250.0F, 0.0F});
      this.crossBulletGun.shootPointIsAbsolute = false;
      this.crossBulletGun.resetShootClock(false, 10.0F, 10.0F, 0.48F, (byte)6);
      this.circleBoomBulletGun = new BossLv7L.CircleBoomBulletGun(this, var1, EnemyBullet1.alienBulletGen, var12, HEADSHOOTPOSITION, new float[]{0.0F, 250.0F, -85.50503F, 234.92316F, -160.69688F, 191.51111F, -216.50633F, 125.000015F, -246.20193F, 43.412075F, -246.20193F, -43.412014F, -216.50636F, -124.99996F, -160.69695F, -191.51108F, -85.5051F, -234.92313F, 0.0F, -250.0F, 85.50503F, -234.92316F, 160.69688F, -191.51111F, 216.50633F, -125.000015F, 246.20193F, -43.412075F, 246.20193F, 43.412014F, 216.50636F, 124.99996F, 160.69695F, 191.51108F, 85.5051F, 234.92313F});
      this.circleBoomBulletGun.shootPointIsAbsolute = false;
      this.circleBoomBulletGun.resetShootClock(false, 5.0F, 5.0F, 0.5F, (byte)1);
      this.circleBoomBulletGun2 = new BossLv7L.CircleBoomBulletGun(this, var1, EnemyBullet1.alienBulletGen, var12, HEADSHOOTPOSITION, new float[]{0.0F, 250.0F, -64.70476F, 241.48146F, -125.0F, 216.50635F, -176.7767F, 176.7767F, -216.50635F, 125.0F, -241.48145F, 64.70476F, -249.99998F, 0.0F, -241.48145F, -64.70476F, -216.50633F, -124.99999F, -176.77667F, -176.77667F, -124.999985F, -216.50633F, -64.70475F, -241.48141F, 0.0F, -249.99997F, 64.70475F, -241.48141F, 124.999985F, -216.50632F, 176.77666F, -176.77666F, 216.50632F, -124.99997F, 241.4814F, -64.70474F, 249.99994F, 0.0F, 241.4814F, 64.70475F, 216.50629F, 124.99997F, 176.77664F, 176.77666F, 124.999954F, 216.50629F, 64.70473F, 241.48138F});
      this.circleBoomBulletGun2.shootPointIsAbsolute = false;
      this.circleBoomBulletGun2.resetShootClock(false, 5.0F, 5.0F, 0.5F, (byte)2);
      this.rocketBulletGun = new BulletGunAlien(this, var1, EnemyBullet2.alienBulletGen, var8, HEADSHOOTPOSITION, new float[]{0.0F, -200.0F});
      this.rocketBulletGun.setPursueParameter((Clock)null, (Vector2)null, 0.0F, 0.0F);
      this.rocketBulletGun.shootPointIsAbsolute = false;
      this.rocketBulletGun.resetShootClock(false, 5.0F, 5.0F, 0.8F, (byte)4);
      this.laserGunSwap = new LaserGun(this, Assets_ShareInAlien.gather, 0.2F, 3.0F, 3.0F, 1.0F, HEADSHOOTPOSITION.x, HEADSHOOTPOSITION.y, CommonLaser.getWidthOfAlienHalf());
      this.laserGunSwap.setOriginX(31.0F);
      this.laserGunSwap.setOriginY(49.0F);
      this.laserGunSwap.shooterPointIsAbsolute = false;
      this.laserGunSwap.setLoopFire(false);
      float var15 = CommonLaser.getDeltaYOfAlienHalf(0);
      Enemy.lasers.add(CommonLaser.alienLaserGenHalfHead.getALaser(this, this.laserGunSwap, var15));

      for(int var17 = 1; var17 < 60; ++var17) {
         float var42 = CommonLaser.getDeltaYOfAlienHalf(var17);
         Enemy.lasers.add(CommonLaser.alienLaserGenHalf.getALaser(this, this.laserGunSwap, var42));
      }

      this.laserGunSwap.angle = -30.0F;
      LaserGun var18 = this.laserGunSwap;
      LaserGun var19 = this.laserGunSwap;
      var18.updater = var19.new UpdateLaserGun1(-30.0F, 30.0F);
      this.laserGunSwap2 = new LaserGun(this, Assets_ShareInAlien.gather, 0.2F, 3.0F, 3.0F, 1.0F, HEADSHOOTPOSITION.x, HEADSHOOTPOSITION.y, CommonLaser.getWidthOfAlienAll());
      this.laserGunSwap2.setOriginX(31.0F);
      this.laserGunSwap2.setOriginY(49.0F);
      this.laserGunSwap2.shooterPointIsAbsolute = false;
      this.laserGunSwap2.setLoopFire(false);
      float var22 = CommonLaser.getDeltaYOfAlienAll(0);
      Enemy.lasers.add(CommonLaser.alienLaserGenAllHead.getALaser(this, this.laserGunSwap2, var22));

      for(int var24 = 1; var24 < 60; ++var24) {
         float var40 = CommonLaser.getDeltaYOfAlienAll(var24);
         Enemy.lasers.add(CommonLaser.alienLaserGenAll.getALaser(this, this.laserGunSwap2, var40));
      }

      this.laserGunSwap2.angle = -30.0F;
      LaserGun var25 = this.laserGunSwap2;
      LaserGun var26 = this.laserGunSwap2;
      var25.updater = var26.new UpdateLaserGun1(-30.0F, 30.0F);
      this.laserGunPush = new LaserGun(this, Assets_ShareInAlien.gather, 0.2F, 3.0F, 3.0F, 1.0F, HEADSHOOTPOSITION.x, HEADSHOOTPOSITION.y, CommonLaser.getWidthOfAlienHalf());
      this.laserGunPush.setOriginX(31.0F);
      this.laserGunPush.setOriginY(49.0F);
      this.laserGunPush.shooterPointIsAbsolute = false;
      this.laserGunPush.setLoopFire(false);
      float var29 = CommonLaser.getDeltaYOfAlienHalf(0);
      Enemy.lasers.add(CommonLaser.alienLaserGenHalfHead.getALaser(this, this.laserGunPush, var29));

      for(int var31 = 1; var31 < 60; ++var31) {
         float var38 = CommonLaser.getDeltaYOfAlienHalf(var31);
         Enemy.lasers.add(CommonLaser.alienLaserGenHalf.getALaser(this, this.laserGunPush, var38));
      }

      this.laserGunPush2 = new LaserGun(this, Assets_ShareInAlien.gather, 0.2F, 3.0F, 3.0F, 1.0F, HEADSHOOTPOSITION.x, HEADSHOOTPOSITION.y, CommonLaser.getWidthOfAlienAll());
      this.laserGunPush2.setOriginX(31.0F);
      this.laserGunPush2.setOriginY(49.0F);
      this.laserGunPush2.shooterPointIsAbsolute = false;
      this.laserGunPush2.setLoopFire(false);
      float var33 = CommonLaser.getDeltaYOfAlienAll(0);
      Enemy.lasers.add(CommonLaser.alienLaserGenAllHead.getALaser(this, this.laserGunPush2, var33));

      for(int var35 = 1; var35 < 25; ++var35) {
         float var36 = CommonLaser.getDeltaYOfAlienAll(var35);
         Enemy.lasers.add(CommonLaser.alienLaserGenAll.getALaser(this, this.laserGunPush2, var36));
      }

      this.avalibleGun = new EnemyGun[9];
      this.avalibleGun[0] = this.pointBulletGun;
      this.avalibleGun[1] = this.overBulletGun;
      this.avalibleGun[2] = this.crossBulletGun;
      this.avalibleGun[3] = this.crossBulletGun;
      this.avalibleGun[4] = this.circleBoomBulletGun;
      this.avalibleGun[5] = this.rocketBulletGun;
      this.avalibleGun[6] = this.laserGunSwap;
      this.avalibleGun[7] = this.laserGunPush;
      this.avalibleGun[8] = this.constructMutiScreenGun();
      this.currentGun = this.laserGunSwap;
      this.currentGun.reset();
      this.eggManager = BossLv7Egg.getEggManager(this.world, this, EGGPOSITION);
      this.forceManager = BossLv7Force.getForceManager(this.world, this, FORCEPOSITION);
      this.constructNew();
   }

   public static Enemy addABossLv7(World var0, float var1, float var2, float var3, List<Enemy> var4) {
      BossLv7L var5 = new BossLv7L(var0, var1, var2);
      EnemyProXY var6 = (EnemyProXY)EnemyProXY.addAEnemy(var5, var3, var4, true);
      var6.outRender = var5.outRender;
      int var7 = var4.indexOf(var6);
      if(var7 > 0) {
         var4.set(var7, (Enemy)var4.get(0));
         var4.set(0, var6);
      }

      return var6;
   }

   private EnemyGun constructMutiScreenGun() {
      float var1 = HEADSHOOTPOSITION.x;
      float var2 = HEADSHOOTPOSITION.y;
      Clock var3 = new Clock(100.0F, 100.0F, 0.1F, (byte)1);
      BulletGenerator var4 = EnemyBullet1.alienBulletGen;
      float[][][] var5 = new float[][][]{BulletScreen.bulletVelAngles[8], BulletScreen.bulletVelAngles[9], BulletScreen.bulletVelAngles[10], BulletScreen.bulletVelAngles[14], BulletScreen.bulletVelAngles[15], BulletScreen.bulletVelAngles[16], BulletScreen.bulletVelAngles[17], BulletScreen.bulletVelAngles[22], BulletScreen.bulletVelAngles[23], BulletScreen.bulletVelAngles[24], BulletScreen.bulletVelAngles[25], BulletScreen.bulletVelAngles[26]};
      return EnemyActionGun.getMutiScreenGun(this, var4, var3, var1, var2, var5);
   }

   private void enterState2() {
      this.bossState = BossLv7L.BossLv7LState.AttackingState2;
      this.slaws[0].enableSlaw();
      this.slaws[1].enableSlaw();
      this.pointBulletGun.resetShootClock(false, 6.0F, 6.0F, 0.15F, (byte)1);
      this.overBulletGun.resetShootClock(false, 6.0F, 6.0F, 0.15F, (byte)1);
      this.crossBulletGun.resetShootClock(false, 15.0F, 15.0F, 0.48F, (byte)12);
      this.avalibleGun[4] = this.circleBoomBulletGun2;
      this.rocketBulletGun.resetShootClock(false, 5.0F, 5.0F, 0.64F, (byte)6);
      this.avalibleGun[6] = this.laserGunSwap2;
      this.avalibleGun[7] = this.laserGunPush2;
      this.pointBulletGun.getDirectionsVel()[0].set(0.0F, -350.0F);
      this.overBulletGun.getDirectionsVel()[0].set(0.0F, -350.0F);
      this.rocketBulletGun.getDirectionsVel()[0].set(0.0F, -250.0F);
   }

   public static void loadResource() {
      headShadowRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_head_shadow");
      enemyTextureRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_head");
      eggRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_egg", 0);
      craftRegion1 = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_craft", 1);
      craftRegion2 = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_craft", 2);
      slimeRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_slime");
      wallRegion[0] = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_wall", 0);
      wallRegion[1] = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_wall", 1);
      silkShadowRegion[0] = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_silk_shadow", 0);
      silkShadowRegion[2] = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_silk_shadow", 1);
      silkShadowRegion[4] = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_silk_shadow", 2);
      silkShadowRegion[1].setRegion(silkShadowRegion[0]);
      silkShadowRegion[3].setRegion(silkShadowRegion[2]);
      silkShadowRegion[5].setRegion(silkShadowRegion[4]);
      boolean var0 = silkShadowRegion[1].isFlipX();
      boolean var1;
      if(!silkShadowRegion[1].rotate) {
         var1 = true;
      } else {
         var1 = false;
      }

      if(var0 != var1) {
         silkShadowRegion[1].flip(true, false);
      }

      if(silkShadowRegion[1].isFlipY() != silkShadowRegion[1].rotate) {
         silkShadowRegion[1].flip(false, true);
      }

      boolean var2 = silkShadowRegion[3].isFlipX();
      boolean var3;
      if(!silkShadowRegion[3].rotate) {
         var3 = true;
      } else {
         var3 = false;
      }

      if(var2 != var3) {
         silkShadowRegion[3].flip(true, false);
      }

      if(silkShadowRegion[3].isFlipY() != silkShadowRegion[3].rotate) {
         silkShadowRegion[3].flip(false, true);
      }

      boolean var4 = silkShadowRegion[5].isFlipX();
      boolean var5;
      if(!silkShadowRegion[5].rotate) {
         var5 = true;
      } else {
         var5 = false;
      }

      if(var4 != var5) {
         silkShadowRegion[5].flip(true, false);
      }

      if(silkShadowRegion[5].isFlipY() != silkShadowRegion[5].rotate) {
         silkShadowRegion[5].flip(false, true);
      }

      silkShadowRegion[1].offsetX = 0.0F;
      silkShadowRegion[1].offsetY = 0.0F;
      silkShadowRegion[3].offsetX = 0.0F;
      silkShadowRegion[3].offsetY = 0.0F;
      silkShadowRegion[5].offsetX = 0.0F;
      silkShadowRegion[5].offsetY = 0.0F;
      craftShadowRegion = Assets_level7.atlas_Enemy.findRegion("lv_7_boss_craft_shadow");
      BossLv7Egg.loadResource();
      BossLv7Force.loadResource();
   }

   private void renderCraft1(SpriteBatch var1) {
      var1.draw(craftRegion1, this.bounds.x + CRAFT1POSITION.x, this.bounds.y + CRAFT1POSITION.y, 0.0F, 0.0F, CRAFT1WIDTH, CRAFT1HEIGHT, 1.0F, 1.0F, 0.0F);
   }

   private void renderCraft2(SpriteBatch var1) {
      var1.draw(craftRegion2, this.bounds.x + CRAFT2POSITION.x, this.bounds.y + CRAFT2POSITION.y, 0.0F, 0.0F, CRAFT2WIDTH, CRAFT2HEIGHT, 1.0F, 1.0F, 0.0F);
   }

   private void renderHeadShadow(SpriteBatch var1) {
      var1.draw(headShadowRegion, this.bounds.x + HEADSHADOWPOSITION.x, this.bounds.y + HEADSHADOWPOSITION.y, 0.0F, 0.0F, HEADSHADOWWIDTH, HEADSHADOWHEIGHT, 1.0F, 1.0F, 0.0F);
   }

   private void renderLaser(SpriteBatch var1) {
      this.laserGunPush.renderLaserAggregation(var1);
      this.laserGunSwap.renderLaserAggregation(var1);
      this.laserGunPush2.renderLaserAggregation(var1);
      this.laserGunSwap2.renderLaserAggregation(var1);
   }

   private void renderSilk(SpriteBatch var1) {
      for(int var2 = 0; var2 < 6; ++var2) {
         Color var4 = var1.getColor();
         Color var5 = new Color(var4);
         if(this.silks[var2].isCrashed()) {
            if(this.silks[var2].stateTime > this.silks[var2].getTimeOfCrash()) {
               continue;
            }

            var5.a = (1.0F - this.silks[var2].stateTime / this.silks[var2].getTimeOfCrash()) * var4.a;
            var1.setColor(var5);
         }

         var1.draw(silkShadowRegion[var2], this.bounds.x + SILKSHADOWPOSITION[var2].x, this.bounds.y + SILKSHADOWPOSITION[var2].y, 0.0F, 0.0F, SILKSHADOWWIDTH[var2], SILKSHADOWHEIGHT[var2], 1.0F, 1.0F, 0.0F);
         var1.setColor(var4);
      }

      for(int var3 = 0; var3 < 6; ++var3) {
         this.silks[var3].render(var1);
      }

   }

   private void renderSlaw(SpriteBatch var1) {
      this.slaws[0].render(var1);
      this.slaws[1].render(var1);
   }

   private void renderSlawShadow(SpriteBatch var1) {
      this.slaws[0].renderSlawShadow(var1);
      this.slaws[1].renderSlawShadow(var1);
   }

   private void renderSlime(SpriteBatch var1) {
      var1.draw(slimeRegion, this.bounds.x + SLIMEPOSITION.x, this.bounds.y + SLIMEPOSITION.y, 0.0F, 0.0F, SLIMEWIDTH, SLIMEHEIGHT, 1.0F, 1.0F, 0.0F);
   }

   private void renderWalls(SpriteBatch var1) {
      int var2 = 0;
      var1.draw(wallRegion[0], this.bounds.x + WALL0POSITION[0].x, this.bounds.y + WALL0POSITION[0].y, 0.0F, 0.0F, WALLWIDTH[0], WALLHEIGHT[0], 1.0F, 1.0F, 0.0F);
      int var3 = 0 + 1;

      for(int var4 = 0; var4 < 5; ++var4) {
         var1.draw(wallRegion[1], this.bounds.x + WALL1POSITION[var2].x, this.bounds.y + WALL1POSITION[var2].y, 0.0F, 0.0F, WALLWIDTH[1], WALLHEIGHT[1], 1.0F, 1.0F, 0.0F);
         ++var2;
      }

      var1.draw(wallRegion[0], this.bounds.x + WALL0POSITION[var3].x, this.bounds.y + WALL0POSITION[var3].y, 0.0F, 0.0F, WALLWIDTH[0], WALLHEIGHT[0], 1.0F, 1.0F, 0.0F);
      int var5 = var3 + 1;

      for(int var6 = 0; var6 < 6; ++var6) {
         var1.draw(wallRegion[1], this.bounds.x + WALL1POSITION[var2].x, this.bounds.y + WALL1POSITION[var2].y, 0.0F, 0.0F, WALLWIDTH[1], WALLHEIGHT[1], 1.0F, 1.0F, 0.0F);
         ++var2;
      }

      var1.draw(wallRegion[0], this.bounds.x + WALL0POSITION[var5].x, this.bounds.y + WALL0POSITION[var5].y, 0.0F, 0.0F, WALLWIDTH[0], WALLHEIGHT[0], 1.0F, 1.0F, 0.0F);
      int var7 = var5 + 1;

      for(int var8 = 0; var8 < 5; ++var8) {
         var1.draw(wallRegion[1], this.bounds.x + WALL1POSITION[var2].x, this.bounds.y + WALL1POSITION[var2].y, 0.0F, 0.0F, WALLWIDTH[1], WALLHEIGHT[1], 1.0F, 1.0F, 0.0F);
         ++var2;
      }

      var1.draw(wallRegion[0], this.bounds.x + WALL0POSITION[var7].x, this.bounds.y + WALL0POSITION[var7].y, 0.0F, 0.0F, WALLWIDTH[0], WALLHEIGHT[0], 1.0F, 1.0F, 0.0F);
      int var10000 = var7 + 1;
   }

   public void beHitByBullet(Bullet var1, int var2) {
      this.hitProcessor.beHitByBullet(var1, var2);
      if(super.isCrashed()) {
         this.currentGun.forceStop();
         this.laserGunPush.setRemoveAllLasers();
         this.laserGunSwap.setRemoveAllLasers();
         this.eggManager.makeCrashAndStopGen();
         this.forceManager.makeCrash();
         if(!this.slaws[0].isCrashed()) {
            this.slaws[0].makeCrash();
         }

         if(!this.slaws[1].isCrashed()) {
            this.slaws[1].makeCrash();
         }

         Settings.bossKilled(7, Clock.getCurrentTime(this.dropTime));
         this.crashTimeStamp = Clock.getCurrentTime();
      }

      GameScreen.bossHealthyDegree = this.getHealthyDgree();
   }

   public void constructNew() {
      this.enterState2Action = new BossLv7L.BossLv7EnterState2();
      this.constructWhiteAnimation();
      this.world.setDelayTimeToNextLevel(this);
      this.needShowWarning = true;
      this.outRender = true;
      this.world.addObjectToBGM(this);
   }

   protected void constructWhiteAnimation() {
      TextureRegion var1 = new TextureRegion(XScreen.getTexture());
      WorldAnimationPlayer.WorldAnimation var2 = WorldAnimationPlayer.getWorldAnimation(var1, 9.0F, 320.0F, 400.0F, (float)Clock.getTimeCounter(), (Color)null);
      WorldAnimationPlayer.AnimationAction var3 = (WorldAnimationPlayer.AnimationAction)Pools.obtain(WorldAnimationPlayer.AnimationAction.class);
      var3.startValue = 0.0F;
      var3.endValue = 1.0F;
      var3.inter = new Interpolation() {
         public float apply(float var1) {
            float var2 = 1.0F;
            float var3 = var1 * 9.0F;
            if(var3 < 3.0F) {
               var2 = var3 / 3.0F;
            } else if(var3 >= 6.0F && var3 <= 9.0F) {
               return (9.0F - var3) / 3.0F;
            }

            return var2;
         }
      };
      var2.setAlphaAction(var3);
      WorldAnimationPlayer.AnimationAction var4 = (WorldAnimationPlayer.AnimationAction)Pools.obtain(WorldAnimationPlayer.AnimationAction.class);
      var4.startValue = 640.0F / (float)var1.getRegionWidth();
      var4.endValue = var4.startValue;
      var2.setScaleXAction(var4);
      WorldAnimationPlayer.AnimationAction var5 = (WorldAnimationPlayer.AnimationAction)Pools.obtain(WorldAnimationPlayer.AnimationAction.class);
      var5.startValue = 800.0F / (float)var1.getRegionHeight();
      var5.endValue = var5.startValue;
      var2.setScaleYAction(var5);
      this.worldAnimation = var2;
   }

   public void enemyDropped(EnemyProXY var1) {
      for(int var2 = 0; var2 < this.slaws.length; ++var2) {
         this.slaws[var2].slawDropInit();
      }

      this.world.askBeginFighting(0.5F);
      this.world.playBossMusic();
      this.dropTime = Clock.getCurrentTime();
   }

   public float getDelayTimeToNextLevel() {
      float var1 = 10.0F - Clock.getCurrentTime(this.crashTimeStamp);
      if(var1 < 1.0F) {
         var1 = 1.0F;
      }

      if(var1 > 10.0F) {
         var1 = 10.0F;
      }

      return var1;
   }

   public float getDropDelayTime() {
      return 7.0F;
   }

   public List<Rectangle> getFighterHitRecs() {
      this.hitProcessor.beginAdd();
      if(this.bossState == BossLv7L.BossLv7LState.AttackingState2) {
         this.hitProcessor.addEnemyHitRec(this);
         this.hitProcessor.addEnemyHitRec(this.slaws[0]);
         this.hitProcessor.addEnemyHitRec(this.slaws[1]);
      }

      for(int var1 = 0; var1 < 6; ++var1) {
         this.hitProcessor.addEnemyHitRec(this.silks[var1]);
      }

      this.hitProcessor.endAdd();
      return this.hitProcessor.getHitRectangle();
   }

   public List<Rectangle> getHitRectangle() {
      this.hitProcessor.beginAdd();
      if(this.bossState == BossLv7L.BossLv7LState.AttackingState2) {
         this.hitProcessor.addEnemyHitRec(this);
         this.hitProcessor.addEnemyHitRec(this.slaws[0]);
         this.hitProcessor.addEnemyHitRec(this.slaws[1]);
         Iterator var4 = this.forceManager.getForces().iterator();

         while(var4.hasNext()) {
            BossLv7Force var5 = (BossLv7Force)var4.next();
            this.hitProcessor.addEnemyHitRec(var5);
         }
      }

      for(int var1 = 0; var1 < 6; ++var1) {
         this.hitProcessor.addEnemyHitRec(this.silks[var1]);
      }

      Iterator var2 = this.eggManager.getEggs().iterator();

      while(var2.hasNext()) {
         BossLv7Egg var3 = (BossLv7Egg)var2.next();
         this.hitProcessor.addEnemyHitRec(var3);
      }

      this.hitProcessor.endAdd();
      return this.hitProcessor.getHitRectangle();
   }

   public List<Rectangle> getHitRectangle(int var1) {
      Rectangle var2 = (Rectangle)this.hitRSHead.get(0);
      var2.x = this.bounds.x + HITRECHEAD.x;
      var2.y = this.bounds.y + HITRECHEAD.y;
      return this.hitRSHead;
   }

   public float getTimeOfCrash() {
      return 50.0F;
   }

   protected void playWhiteAnimation() {
      this.worldAnimation.reset(0.0F);
      WorldAnimationPlayer.addWorldAnimationToLayer(1, this.worldAnimation);
   }

   protected void putCrashAniamtion() {
      float var1 = this.bounds.x;
      float var2 = this.bounds.y;
      float var3 = this.bounds.width;
      float var4 = this.bounds.height;
      float var10000 = var1 + var3 / 2.0F;
      var10000 = var2 + var4 / 2.0F;
      float var7 = (float)Clock.getTimeCounter();
      Animation var8 = this.getShatterAniamtion();
      Animation var9 = this.getCrashBoom(true);
      WorldAudio.PlaySound var10 = this.getCrashSound(true);

      for(int var11 = 0; var11 < 20; ++var11) {
         float var12 = var1 + var3 * this.world.rand.nextFloat();
         float var13 = var2 + 0.7F * var4 * this.world.rand.nextFloat();
         float var14 = var7 + 0.3333F * (float)var11 + 0.3F * var9.animationTime * (this.world.rand.nextFloat() - 0.5F);
         WorldAnimationPlayer.addAWorldAnimation(var8, var12, var13, 1, var14);
         WorldAnimationPlayer.addAWorldAnimation(var9, var12, var13, 1, var14, var10);
      }

   }

   public void renderCrash(SpriteBatch var1) {
      this.renderWalls(var1);
      this.renderSlime(var1);
      this.renderCraft2(var1);
      this.renderCraft1(var1);
      if(this.stateTime < 4.0F) {
         this.renderHeadShadow(var1);
         super.renderEnemy(var1);
      }

      if(!this.crashAnimationPutted) {
         this.putCrashAniamtion();
         this.playWhiteAnimation();
         this.crashAnimationPutted = true;
      }

   }

   public void renderEnemy(SpriteBatch var1) {
      this.renderWalls(var1);
      this.renderSlime(var1);
      this.renderSlawShadow(var1);
      this.renderCraft2(var1);
      this.renderSlaw(var1);
      this.renderCraft1(var1);
      this.renderHeadShadow(var1);
      this.eggManager.render(var1);
      this.forceManager.render(var1);
      super.renderEnemy(var1);
      this.renderLaser(var1);
      this.renderSilk(var1);
   }

   public void renderObject(SpriteBatch var1) {
      float var2 = var1.getColor().toFloatBits();
      var1.setColor(0.0F, 0.0F, 0.0F, 0.5F);
      var1.draw(craftShadowRegion, -133.0F + this.bounds.x, -132.0F + this.bounds.y, 0.0F, 0.0F, CRAFTSHADOWWIDTH, CRAFTSHADOWHEIGHT, 1.0F, 1.0F, 0.0F);
      var1.setColor(var2);
   }

   public void renderShadow(SpriteBatch var1) {}

   public void setSleepPosition(float var1, float var2) {
      super.setSleepPosition(var1, var2);

      for(int var3 = 0; var3 < this.silks.length; ++var3) {
         this.silks[var3].setSleepPosition(this.bounds.x + SILKPOSITION[var3].x, this.bounds.y + SILKPOSITION[var3].y);
      }

      this.slaws[0].setSleepPosition(this.bounds.x + SLAWPOSITION[0].x, this.bounds.y + SLAWPOSITION[0].y);
      this.slaws[1].setSleepPosition(this.bounds.x + SLAWPOSITION[1].x, this.bounds.y + SLAWPOSITION[1].y);
      this.eggManager.setSleepPostion(this, EGGPOSITION);
      this.forceManager.setSleepPostion(this, FORCEPOSITION);
   }

   public void update(final float n) {

       super.update(n);

       this.eggManager.update(n);

       this.forceManager.update(n);

       switch (this.bossState) {

           case Appearing: {

               if (this.getPositionY() < 623.0f) {

                   this.setPosition(this.getPositionX(), 623.0f);

                   this.velocity.set(0.0f, 0.0f);

                   this.bossState = BossLv7LState.AttackingState1;

               }

               this.eggManager.setSleepPostion(this, BossLv7L.EGGPOSITION);

               this.forceManager.setSleepPostion(this, BossLv7L.FORCEPOSITION);

               break;

           }

           case AttackingState1: {

               int n2 = 1;

               for (int i = 0; i < 6; ++i) {

                   if (!this.silks[i].isCrashed()) {

                       n2 = 0;

                   }

               }

               if (n2 != 0 && !this.enemyActions.contains(this.enterState2Action, true)) {

                   this.addEnemyAction(this.enterState2Action);

                   break;

               }

               break;

           }

       }

       for (int j = 0; j < 6; ++j) {

           this.silks[j].update(n);

       }

       this.slaws[0].update(n);

       this.slaws[1].update(n);

   }

   public void updateShooting(float var1) {
      if(this.currentGun != null) {
         this.currentGun.updateShooting(var1);
      }

      if(this.clockChangeShoot.isFired() && !super.isCrashed()) {
         if(!this.currentGun.canStop()) {
            this.clockChangeShoot.resetClockFireOnce(0.2F);
            return;
         }

         int var2 = this.world.rand.nextInt(this.avalibleGun.length);
         if(var2 == 2) {
            this.crossBulletGun.angleSpeed = Math.abs(this.crossBulletGun.angleSpeed);
         }

         if(var2 == 3) {
            this.crossBulletGun.angleSpeed = -Math.abs(this.crossBulletGun.angleSpeed);
         }

         this.currentGun = this.avalibleGun[var2];
         this.currentGun.reset();
         this.clockChangeShoot.resetClockFireOnce(5.0F);
      }

   }

   private class CircleBoomBulletGun extends AutoTurningBulletGun {

      public CircleBoomBulletGun(GameObject var2, World var3, BulletGenerator var4, Clock var5, Vector2 var6, float ... var7) {
         super(var2, var3, var4, var5, var6, 0.0F, var7);
      }

      protected void shootBullets() {
         this.currentAngle = 5.0F;
         super.shootBullets();
      }
   }

   public class BossLv7EnterState2 extends EnemyTools.EnemyAction {

      float currentTime = 0.0F;


      public boolean act(float var1) {
         if(this.currentTime == 0.0F) {
            BossLv7L.this.world.askStopFighting();
            if(BossLv7L.this.currentGun != null) {
               BossLv7L.this.currentGun.forceStop();
            }
         }

         if(this.currentTime < 2.0F) {
            this.currentTime += var1;
            return false;
         } else {
            this.currentTime = 0.0F;
            BossLv7L.this.enterState2();
            BossLv7L.this.world.askBeginFighting();
            return true;
         }
      }
   }

   private static enum BossLv7LState {

      Appearing,
      AttackingState1,
      AttackingState2,
   }
}
