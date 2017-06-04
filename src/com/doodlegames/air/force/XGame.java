package com.doodlegames.air.force;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.doodlegames.air.force.screen.LoadingScreen;
import com.doodlegames.air.force.screen.MainMenuScreen;
import com.doodlegames.air.force.screen.UIScreen;
import com.doodlegames.air.force.utils.Settings;

public class XGame extends Game {

	public static TextureRegionDrawable btnPlay;
	public static TextureRegionDrawable btnStore;
	public static TextureRegionDrawable btnMore;
	public static TextureRegionDrawable btnMis1;
	public static TextureRegionDrawable btnMis2;
	public static TextureRegionDrawable btnMis3;
	public static TextureRegionDrawable btnMis4;
	public static TextureRegionDrawable btnMis5;
	public static TextureRegionDrawable btnMis6;
	public static TextureRegionDrawable btnMis7;

	public void setScreen(Screen var1) {
		
		super.setScreen(var1); 
		System.gc();
	}

	TextureAtlas atlas;
	public void create() {

		// this.constructPlatFormFunction();

		Gdx.input.setCatchBackKey(true);

		Settings.load();

		this.setScreen(new LoadingScreen(this, UIScreen.info, 5.0f, false));
		atlas = new TextureAtlas(Gdx.files.internal("button/button.pack"));
		btnPlay = getDrawable("play");
		btnStore = getDrawable("store");
		btnMore = getDrawable("more");
		btnMis1 = getDrawable("mis1");
		btnMis2 = getDrawable("mis2");
		btnMis3 = getDrawable("mis3");
		btnMis4 = getDrawable("mis4");
		btnMis5 = getDrawable("mis5");
		btnMis6 = getDrawable("mis6");
		btnMis7 = getDrawable("mis7");

		// this.getPlatFormFunction().showFullScreenAd(1000L);

	}

	TextureRegionDrawable getDrawable(String filename) {
		TextureRegion tr = atlas.findRegion(filename);
		return new TextureRegionDrawable(tr);
	}
	
	@Override
	public void render() {
//		try {
		super.render();
//		}catch(Exception ex){}
	}

	public void dispose() {

		super.dispose();

		this.getScreen().dispose();

		Settings.i("Lightning dispose is happening");

		if (LoadingScreen.assetManager != null) {

			LoadingScreen.assetManager.dispose();

			LoadingScreen.assetManager = null;

		}
		
		atlas.dispose();

	}
}
