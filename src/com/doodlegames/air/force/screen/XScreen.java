package com.doodlegames.air.force.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.scenes.scene2d.NewStage;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.doodlegames.air.force.screen.BackProcessor;
import com.doodlegames.air.force.utils.Settings;

public abstract class XScreen implements Screen {

   private static SpriteBatch batch;
   private static BitmapFont bitmapFont18;
   private static BitmapFont bitmapFont24;
   private static BitmapFont bitmapFont30;
   private static BitmapFont bitmapFont36;
   private static BitmapFont bitmapFont48;
   private static TextureRegion fontRegion;
   public static final String[] pureColorDrawable = new String[]{"gray", "red", "green", "yellow", "blue", "black", "white", "cyan"};
   private static Texture texture;
   private static PixmapTextureData textureData;
   protected Color clsColor = new Color(0.0F, 0.0F, 0.0F, 1.0F);
   protected final Game game;
   private boolean inputProcessorSetted;
   protected final NewStage screenStage;
   protected final Skin skin;


   public XScreen(Game var1, Skin var2) {
      this.game = var1;
      this.skin = var2; 
      this.screenStage = new NewStage(480.0F, 800.0F, false, getSpriteBatch());
      this.inputProcessorSetted = false;
   }

   public static BitmapFont getBitmapFont18() {
      if(bitmapFont18 == null) {
         bitmapFont18 = new BitmapFont(Gdx.files.internal("fonts/amarurgt-18.fnt"), getFontTextureRegion(), false);
      }

      return bitmapFont18;
   }

   public static BitmapFont getBitmapFont24() {
      if(bitmapFont24 == null) {
         bitmapFont24 = new BitmapFont(Gdx.files.internal("fonts/amarurgt-24.fnt"), getFontTextureRegion(), false);
      }

      return bitmapFont24;
   }

   public static BitmapFont getBitmapFont30() {
      if(bitmapFont30 == null) {
         bitmapFont30 = new BitmapFont(Gdx.files.internal("fonts/amarurgt-30.fnt"), getFontTextureRegion(), false);
      }

      return bitmapFont30;
   }

   public static BitmapFont getBitmapFont36() {
      if(bitmapFont36 == null) {
         bitmapFont36 = new BitmapFont(Gdx.files.internal("fonts/amarurgt-36.fnt"), getFontTextureRegion(), false);
      }

      return bitmapFont36;
   }

   public static BitmapFont getBitmapFont48() {
      if(bitmapFont48 == null) {
         bitmapFont48 = new BitmapFont(Gdx.files.internal("fonts/amarurgt-48.fnt"), getFontTextureRegion(), false);
      }

      return bitmapFont48;
   }

   private static TextureRegion getFontTextureRegion() {
      if(fontRegion == null) {
         fontRegion = new TextureRegion(new Texture(Gdx.files.internal("fonts/fonts.png")));
      }

      return fontRegion;
   }

   public static SpriteBatch getSpriteBatch() {
      if(batch == null) {
         batch = new SpriteBatch();
      }

      return batch;
   }

   public static Texture getTexture() {
      if(texture == null) {
         Pixmap var0 = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
         var0.setColor(Color.WHITE);
         var0.fill();
         XScreen.ManagedPixmapTextureData var1 = new XScreen.ManagedPixmapTextureData(var0, (Pixmap.Format)null, false, false);
         textureData = var1;
         texture = new Texture(var1);
      }

      return texture;
   }

   public static void reloadTexture() {
      if(textureData != null) {
         texture.load(textureData);
      }

   }

   protected void act(float var1) {
      if(!this.inputProcessorSetted) {
         Gdx.input.setInputProcessor(BackProcessor.getProcessor(this.screenStage));
         this.inputProcessorSetted = true;
      }

      this.screenStage.act(var1);
   }

   public void dispose() {
      this.screenStage.dispose();
      this.skin.dispose();
   }

   protected void draw(float var1) {
      this.screenStage.draw();
   }

   public Game getGame() {
      return this.game;
   }

   public Skin getSkin() {
      return this.skin;
   }

   public NewStage getStage() {
      return this.screenStage;
   }

   public void hide() {
      Gdx.input.setInputProcessor((InputProcessor)null);
      this.dispose();
   }

   public void pause() {}

   public void render(float var1) {
	   float var2 = Settings.fixDelta(var1);
	   if(game.getScreen() instanceof GameScreen){
		   this.act(var2);
	   } else {
		   try {
			   this.act(var2);
		   } catch (IndexOutOfBoundsException ex){
			   
		   }
	   }
      
      
      GLCommon var3 = Gdx.gl;
      var3.glClearColor(this.clsColor.r, this.clsColor.g, this.clsColor.b, this.clsColor.a);
      var3.glClear(16384);
      this.draw(var2);
      Table.drawDebug(this.screenStage);
   }

   public void resize(int var1, int var2) {}

   public void resume() {}

   public void show() {
      if(Gdx.input.getInputProcessor() != this.screenStage) {
         Gdx.input.setInputProcessor(this.screenStage);
      }

   }

   public static class ManagedPixmapTextureData extends PixmapTextureData {

      public ManagedPixmapTextureData(Pixmap var1, Pixmap.Format var2, boolean var3, boolean var4) {
         super(var1, var2, var3, var4);
      }

      public boolean isManaged() {
         return true;
      }
   }
}
