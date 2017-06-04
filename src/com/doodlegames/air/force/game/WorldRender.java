package com.doodlegames.air.force.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyProXY;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldAnimationPlayer;
import com.doodlegames.air.force.game.WorldParticleEffect;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.laser.Laser;
import com.doodlegames.air.force.game.prop.Prop;
import com.doodlegames.air.force.utils.Settings;

import java.util.Iterator;

public class WorldRender {

   static final float FRUSTUM_HEIGHT = 800.0F;
   static final float FRUSTUM_WIDTH = 480.0F;
   TextureRegion background;
   SpriteBatch batch;
   OrthographicCamera cam;
   World world;


   public WorldRender(SpriteBatch var1, World var2) {
      this.batch = var1;
      this.cam = new OrthographicCamera(480.0F, 800.0F);
      this.cam.position.set(var2.CamPosition.x, var2.CamPosition.y, 0.0F);
      this.world = var2;
   }

   private boolean outRenderJudge(Enemy var1) {
      return var1.outRender && var1.bounds.y < 1600.0F;
   }

   private void renderBackground() {
      if(this.world.bigBG != null) {
         this.world.bigBG.render(this.batch);
      }

      this.renderObjects(this.batch);
      if(this.world.smallBG != null) {
         this.world.smallBG.render(this.batch);
      }

   }

   private void renderBullet() {
      int var1 = Enemy.bullets.size();

      for(int var2 = 0; var2 < var1; ++var2) {
         Bullet var6 = (Bullet)Enemy.bullets.get(var2);
         if(Settings.gameObjectInScreen(var6) == Settings.PositionType.InScreenMiddle) {
            var6.render(this.batch);
         }
      }

      int var3 = Fighter.bullets.size();

      for(int var4 = 0; var4 < var3; ++var4) {
         Bullet var5 = (Bullet)Fighter.bullets.get(var4);
         if(Settings.gameObjectInScreen(var5) == Settings.PositionType.InScreenMiddle) {
            var5.render(this.batch);
         }
      }

   }

   private void renderEnemyShadow(Enemy var1) {
      if(var1 instanceof EnemyProXY) {
         var1.renderShadow(this.batch);
      } else if(this.outRenderJudge(var1) || Settings.gameObjectInScreen(var1) == Settings.PositionType.InScreenMiddle) {
         var1.renderShadow(this.batch);
         return;
      }

   }

   private void renderEnemysOnAir() {
      int var1 = this.world.enemysOnAir.size();

      for(int var2 = 0; var2 < var1; ++var2) {
         Enemy var3 = (Enemy)this.world.enemysOnAir.get(var2);
         if(this.outRenderJudge(var3) || Settings.gameObjectInScreen(var3) == Settings.PositionType.InScreenMiddle) {
            var3.render(this.batch);
         }
      }

   }

   private void renderEnemysOnSeaOrLand() {
      int var1 = this.world.enemysOnSeaOrLand.size();

      for(int var2 = 0; var2 < var1; ++var2) {
         Enemy var5 = (Enemy)this.world.enemysOnSeaOrLand.get(var2);
         if(var5.isCrashed() && (this.outRenderJudge(var5) || Settings.gameObjectInScreen(var5) == Settings.PositionType.InScreenMiddle)) {
            var5.render(this.batch);
         }
      }

      for(int var3 = 0; var3 < var1; ++var3) {
         Enemy var4 = (Enemy)this.world.enemysOnSeaOrLand.get(var3);
         if(!var4.isCrashed() && (this.outRenderJudge(var4) || Settings.gameObjectInScreen(var4) == Settings.PositionType.InScreenMiddle)) {
            var4.render(this.batch);
         }
      }

   }

   private void renderFigher() {
      this.world.fighter.render(this.batch);
   }

   private void renderLaser() {
      int var1 = Enemy.lasers.size();

      for(int var2 = 0; var2 < var1; ++var2) {
         ((Laser)Enemy.lasers.get(var2)).render(this.batch);
      }

   }

   private void renderObjects(SpriteBatch var1) {
      Iterator var2 = this.world.getObjectsBGM().iterator();

      while(var2.hasNext()) {
         ((World.RenderObject)var2.next()).renderObject(var1);
      }

   }

   private void renderProp(float var1) {
      int var2 = Prop.propSet.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         Prop var4 = (Prop)Prop.propSet.get(var3);
         if(Settings.gameObjectInScreen(var4) == Settings.PositionType.InScreenMiddle) {
            var4.render(this.batch);
         }
      }

      WorldParticleEffect.drawPropParticle(this.batch, var1);
   }

   private void renderShadow() {
      if(this.world.level != 5) {
         this.world.fighter.renderShadow(this.batch);
         int var1 = this.world.enemysOnSeaOrLand.size();

         for(int var2 = 0; var2 < var1; ++var2) {
            this.renderEnemyShadow((Enemy)this.world.enemysOnSeaOrLand.get(var2));
         }

         int var3 = this.world.enemysOnAir.size();

         for(int var4 = 0; var4 < var3; ++var4) {
            this.renderEnemyShadow((Enemy)this.world.enemysOnAir.get(var4));
         }

         int var5 = Prop.propSet.size();

         for(int var6 = 0; var6 < var5; ++var6) {
            Prop var7 = (Prop)Prop.propSet.get(var6);
            if(Settings.gameObjectInScreen(var7) == Settings.PositionType.InScreenMiddle) {
               var7.renderShadow(this.batch);
            }
         }
      }

   }

   private void renderWorldAnimationLay0() {
      WorldAnimationPlayer.renderWorldAnimationLayer0(this.batch);
   }

   private void renderWorldAnimationLay1() {
      WorldAnimationPlayer.renderWorldAnimationLayer1(this.batch);
   }

   private void renderWorldAnimationLay2() {
      WorldAnimationPlayer.renderWorldAnimationLayer2(this.batch);
   }

   public Camera getCamera() {
      return this.cam;
   }

   public void render(float var1) {
      this.cam.position.set(this.world.CamPosition.x, this.world.CamPosition.y, 0.0F);
      this.cam.update();
      this.batch.setProjectionMatrix(this.cam.combined);
      this.batch.enableBlending();
      this.batch.begin();
      this.renderBackground();
      this.renderEnemysOnSeaOrLand();
      this.renderShadow();
      this.renderEnemysOnAir();
      this.renderProp(var1);
      this.renderLaser();
      if(this.world.getSnakeGun() != null) {
         this.world.getSnakeGun().renderObject(this.batch);
      }

      this.renderWorldAnimationLay0();
      this.renderWorldAnimationLay1();
      this.renderBullet();
      this.renderFigher();
      this.renderWorldAnimationLay2();
      this.batch.end();
   }
}
