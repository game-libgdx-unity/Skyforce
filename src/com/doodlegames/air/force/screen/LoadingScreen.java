package com.doodlegames.air.force.screen;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.doodlegames.air.force.NativeService;
import com.doodlegames.air.force.game.path.EnemyMutiMove;
import com.doodlegames.air.force.game.path.PathInfoLoader;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;
import com.doodlegames.air.force.utils.SoundPlayer;
import com.doodlegames.air.force.utils.TextureAtlasLoader;

public class LoadingScreen extends XScreen {

	public static AssetManager assetManager;
	private static Skin loadingSkin;
	public static final String pathWithHelp = "loading/loadingWithHelp.atlas";
	public static final String pathWithOutHelp = "loading/loadingWithOutHelp.atlas";
	private float beginTimeStamp;
	private boolean hasInit = false;
	private Image helpImage;
	private final LoadingScreen.LoadingInfo info;
	private InputProcessor inputProcessor;
	private Image loadingCube;
	private float loadingWait = 5.0F;
	private float percent;
	private final boolean showHelp;
	private Image word;

	public LoadingScreen(Game var1, LoadingScreen.LoadingInfo var2, float var3,
			boolean var4) {
		super(var1, getLoadingSkin());
		this.info = var2;
		// this.loadingWait = var3;

		this.showHelp = var4;

//		if (!showHelp)
//			this.loadingWait = 8f;
	}

	private static Skin getLoadingSkin() {
		if (loadingSkin == null) {
			loadingSkin = new Skin();
		}

		return loadingSkin;
	}

	Texture loadGB;

	private void initLoadingScreen(LoadingScreen.LoadingInfo var1, boolean var2) {
		initSkin(super.skin, var2);
		if (var2) {
			this.helpImage = new Image(this.skin.getDrawable("help"));
			this.helpImage.setPosition(0.0F, 0.0F);
			this.loadingCube = new Image(this.skin.getDrawable("cube"));
			this.loadingCube.setPosition(315.0F, 15.0F);
			this.screenStage.addActor(this.helpImage);
			this.screenStage.addActor(this.loadingCube);
		} else {
			// this.word = new Image(this.skin.getDrawable("word"));
			// this.word.setPosition(113.0F, 339.0F);
			// this.loadingCube = new Image(this.skin.getDrawable("cube"));
			// this.loadingCube.setPosition(235.0F, 339.0F);
			// this.screenStage.addActor(this.word);
			// this.screenStage.addActor(this.loadingCube);
			if (loadGB == null)
				loadGB = new Texture(
						Gdx.files.internal("loading/loadingWithOutHelp.png"));
			this.word = new Image(loadGB);
			this.screenStage.addActor(word);
			this.loadingCube = new Image(loadGB);
			this.screenStage.addActor(loadingCube);

		}

		RepeatAction var3 = new RepeatAction();
		LoadingScreen.GlitterAction var4 = new LoadingScreen.GlitterAction();
		var4.setDuration(1.0F);
		var3.setAction(var4);
		var3.setCount(-1);
		this.loadingCube.addAction(var3);
		InternalFileHandleResolver var5 = new InternalFileHandleResolver();
		if (assetManager == null) {
			assetManager = new AssetManager();
			assetManager.setLoader(EnemyMutiMove.IPathInfoSet.class,
					new PathInfoLoader(var5));
		}

		// if(Settings.isBadPermancePhone()) {
		// assetManager.setLoader(TextureAtlas.class, new
		// TextureAtlasLoader(var5){
		//
		// });
		// } else {
		assetManager.setLoader(TextureAtlas.class,
				new com.badlogic.gdx.assets.loaders.TextureAtlasLoader(var5));
		// }

		SoundPlayer.clearSoundQueue();
		assetManager.clear();
		this.info.fillAssetManager(assetManager);
		this.percent = 0.0F;
		this.beginTimeStamp = Clock.getCurrentTime();
	}

	private static void initSkin(Skin var0, boolean var1) {
		String var2;
		if (var1) {
			var2 = "loading/loadingWithHelp.atlas";
		} else {
			var2 = "loading/loadingWithOutHelp.atlas";
		}

		var0.addRegions(new TextureAtlas(Gdx.files.internal(var2)));
	}

	protected void act(float var1) {
		if (!this.hasInit) {
			this.initLoadingScreen(this.info, this.showHelp);
			this.hasInit = true;
		}

		super.act(var1);

		

		this.percent = assetManager.getProgress();
		if (this.percent < 1.0F) {
			assetManager.update();
		}

		float var2 = Clock.getCurrentTime(this.beginTimeStamp);
		if (this.percent >= 1.0F && var2 > this.loadingWait) {
			Screen var3 = this.info.loadingFinished(this.game, assetManager);
			this.game.setScreen(var3);

			// if(!////((Lightning)this.game).getPlatFormFunction().isFullScreenShowing())
			// {
			// ////((Lightning)this.game).getPlatFormFunction().hideFullScreenAd(0L);
			// }

			Settings.i("Loading Time:" + var2);
		}
		
		if (this.showHelp) {
			return;
		}

//		if (Gdx.input.justTouched()) {
//			float y = 800 - Gdx.input.getY();
//
//			if (y < 200) {
//				if ((Gdx.app.getType() == ApplicationType.Android))
//					((NativeService) Gdx.app).showPrincessGame();
//			} else if (y < 400) {
//				if ((Gdx.app.getType() == ApplicationType.Android))
//					((NativeService) Gdx.app).showFrozenGame();
//			} else {
//				if ((Gdx.app.getType() == ApplicationType.Android))
//					((NativeService) Gdx.app).showTrollGame();
//			}
//
//		}

	}

	public void dispose() {
		super.dispose();
		if (loadingSkin != null) {
			loadingSkin.dispose();
		}

		loadingSkin = null;
		Settings.i("loadingScreen is disposing.");
	}

	public void hide() {
		super.hide();
		Gdx.input.setInputProcessor(this.inputProcessor);
	}

	public void show() {
		super.show();
		this.inputProcessor = Gdx.input.getInputProcessor();
		Gdx.input.setInputProcessor((InputProcessor) null);
	}

	public interface LoadingInfo {

		void fillAssetManager(AssetManager var1);

		Screen loadingFinished(Game var1, AssetManager var2);
	}

	private class GlitterAction extends AlphaAction {

		private GlitterAction() {
		}

		protected void update(float var1) {
			Color var2 = super.getColor();
			if ((double) var1 > 0.5D) {
				var2.a = 0.0F;
			} else {
				var2.a = 1.0F;
			}
		}
	}
}
