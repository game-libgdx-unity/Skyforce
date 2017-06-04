package com.doodlegames.air.force.resource;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.doodlegames.air.force.game.WorldParticleEffect;
import com.doodlegames.air.force.game.prop.PropBoom;
import com.doodlegames.air.force.game.prop.PropBullet;
import com.doodlegames.air.force.game.prop.PropCoin;
import com.doodlegames.air.force.game.prop.PropLive;
import com.doodlegames.air.force.game.prop.PropMegnet;
import com.doodlegames.air.force.game.prop.PropPursueBullet;
import com.doodlegames.air.force.game.prop.PropWingFighter;
import com.doodlegames.air.force.screen.LoadingScreen;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Settings;

public class Assets_Props {

   public static TextureAtlas atlas_Prop;
   public static Animation fighterMissle;
   public static LoadingScreen.LoadingInfo info = new LoadingScreen.LoadingInfo() {
      public void fillAssetManager(AssetManager var1) {
         var1.load("prop/prop.atlas", TextureAtlas.class);
      }
      public Screen loadingFinished(Game var1, AssetManager var2) {
         try {
            Assets_Props.atlas_Prop = (TextureAtlas)var2.get("prop/prop.atlas", TextureAtlas.class);
            return null;
         } catch (Throwable var7) {
            Assets_Props.atlas_Prop = null;
            Settings.w(var7.toString());
         } finally {
            Assets_Props.load();
         }

         return null;
      }
   };
   private static final String propFileString = "prop/prop.atlas";
   public static TextureAtlas.AtlasRegion wingBullet1;
   public static TextureAtlas.AtlasRegion wingBullet2;
   public static TextureAtlas.AtlasRegion wingBullet3;
   public static TextureAtlas.AtlasRegion wingBullet4;
   public static Animation wingMan1;
   public static Animation wingMan1Left;
   public static Animation wingMan1Right;
   public static Animation wingMan2;
   public static Animation wingMan2Left;
   public static Animation wingMan2Right;


   public static void load() {
      if(atlas_Prop == null) {
         Settings.w("Assets Load failed.");
         atlas_Prop = new TextureAtlas(Gdx.files.internal("prop/prop.atlas"));
      }

      Animation var0 = wingMan1;
      TextureRegion[] var1 = new TextureRegion[]{atlas_Prop.findRegion("lwingman_stand", 0), atlas_Prop.findRegion("lwingman_stand", 1)};
      wingMan1 = Animation.setAnimationFrame(var0, 0, 0.05F, var1);
      Animation var2 = wingMan1Left;
      TextureRegion[] var3 = new TextureRegion[]{atlas_Prop.findRegion("lwingman_left", 0), atlas_Prop.findRegion("lwingman_left", 1), atlas_Prop.findRegion("lwingman_left", 2), atlas_Prop.findRegion("lwingman_left", 3)};
      wingMan1Left = Animation.setAnimationFrame(var2, 0, 0.05F, var3);
      Animation var4 = wingMan1Right;
      TextureRegion[] var5 = new TextureRegion[]{atlas_Prop.findRegion("lwingman_right", 0), atlas_Prop.findRegion("lwingman_right", 1), atlas_Prop.findRegion("lwingman_right", 2), atlas_Prop.findRegion("lwingman_right", 3)};
      wingMan1Right = Animation.setAnimationFrame(var4, 0, 0.05F, var5);
      Animation var6 = wingMan2;
      TextureRegion[] var7 = new TextureRegion[]{atlas_Prop.findRegion("wingman_stand", 0), atlas_Prop.findRegion("wingman_stand", 1)};
      wingMan2 = Animation.setAnimationFrame(var6, 0, 0.05F, var7);
      Animation var8 = wingMan2Left;
      TextureRegion[] var9 = new TextureRegion[]{atlas_Prop.findRegion("wingman_left", 0), atlas_Prop.findRegion("wingman_left", 1), atlas_Prop.findRegion("wingman_left", 2), atlas_Prop.findRegion("wingman_left", 3)};
      wingMan2Left = Animation.setAnimationFrame(var8, 0, 0.05F, var9);
      Animation var10 = wingMan2Right;
      TextureRegion[] var11 = new TextureRegion[]{atlas_Prop.findRegion("wingman_right", 0), atlas_Prop.findRegion("wingman_right", 1), atlas_Prop.findRegion("wingman_right", 2), atlas_Prop.findRegion("wingman_right", 3)};
      wingMan2Right = Animation.setAnimationFrame(var10, 0, 0.05F, var11);
      if(fighterMissle == null) {
         TextureRegion[] var13 = new TextureRegion[]{atlas_Prop.findRegion("player_missile", 0), atlas_Prop.findRegion("player_missile", 1)};
         fighterMissle = new Animation(0, 0.3F, var13);
      } else {
         fighterMissle.setKeyFrame(0, atlas_Prop.findRegion("player_missile", 0));
         fighterMissle.setKeyFrame(1, atlas_Prop.findRegion("player_missile", 1));
      }

      wingBullet1 = atlas_Prop.findRegion("wingman_bullet_force1");
      wingBullet2 = atlas_Prop.findRegion("wingman_bullet_force2");
      wingBullet3 = atlas_Prop.findRegion("wingman_bullet_force4");
      wingBullet4 = atlas_Prop.findRegion("wingman_bullet_force8");
      PropBullet.loadResource();
      PropCoin.loadResource();
      PropLive.loadResource();
      PropMegnet.loadResource();
      PropBoom.loadResource();
      PropPursueBullet.loadResource();
      PropWingFighter.loadResource();
      ParticleEffect var12 = new ParticleEffect();
      var12.load(Gdx.files.internal("prop/particle/eat_item"), Gdx.files.internal("prop/particle"));
      WorldParticleEffect.loadParticleEffect(var12);
   }
}
