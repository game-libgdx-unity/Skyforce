package com.doodlegames.air.force.screen;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.doodlegames.air.force.InputMultiplexer;
import com.doodlegames.air.force.NativeService;
import com.doodlegames.air.force.resource.Assets_MainMenu;
import com.doodlegames.air.force.resource.Assets_fighter;
import com.doodlegames.air.force.screen.screenActor.MainScreenBackGround;
import com.doodlegames.air.force.utils.DragListener;

public class MainMenuScreen extends XScreen {

	private static final int ROLESNUM = 6;
	private static Skin gameUISkin;
	public static LoadingScreen.LoadingInfo info = new LoadingScreen.LoadingInfo() {
		public void fillAssetManager(AssetManager var1) {
			Assets_MainMenu.info.fillAssetManager(var1);
		}

		public Screen loadingFinished(Game var1, AssetManager var2) {
			Assets_MainMenu.info.loadingFinished(var1, var2);
			return new MainMenuScreen(var1);
		}
	};
	public static Skin mainMenuScreenSkin;
	MainScreenBackGround backGround;
	Image[] backGroundImages;
	ImageButton buttonAchievement;
	ImageButton buttonMoreGame;
	ImageButton buttonPlay;
	ImageButton buttonSetting;
	ImageButton buttonShiftLeft;
	ImageButton buttonShiftRight;
	ImageButton buttonStore;
	int currentFighterId;
	Image[] fightersChoose;
	boolean screenIsMoving;

	public MainMenuScreen(Game var1) {
		super(var1, getMainMenuScreenSkin());
		this.initMainMenueScreen();
		this.backGroundImages = new Image[6];

		for (int var2 = 0; var2 < 6; ++var2) {
			if (var2 % 2 == 0) {
				this.backGroundImages[var2] = new Image(
						(Drawable) this.skin.get("green", SpriteDrawable.class));
			} else {
				this.backGroundImages[var2] = new Image(
						(Drawable) this.skin.get("blue", SpriteDrawable.class));
			}

			this.backGroundImages[var2].setX((float) (var2 * 480));
			this.backGroundImages[var2].setWidth(480.0F);
			this.backGroundImages[var2].setHeight(800.0F);
		}

		Image[] var3 = new Image[] { this.backGroundImages[0],
				this.backGroundImages[1], this.backGroundImages[2],
				this.backGroundImages[3], this.backGroundImages[4],
				this.backGroundImages[5] };
		this.backGround = new MainScreenBackGround(var3);
		this.backGround.addListener(new DragListener() {
			public void dragStop(InputEvent var1, float var2, float var3,
					int var4) {
				MainScreenBackGround var5 = (MainScreenBackGround) var1
						.getListenerActor();
				MainMenuScreen.this.updateShiftButton(var5.getCurrentPage());
			}
		});
		this.backGround.addListener(new ChangeListener() {
			public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
				MainMenuScreen.this.chooseFighter();
			}
		});
		this.buttonPlay = new ImageButton(this.skin);
		this.buttonPlay.setBounds(240.0F, 500.0F, 100.0F, 40.0F);
		this.buttonPlay.addListener(new ChangeListener() {
			public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
				MainMenuScreen var3 = MainMenuScreen.this;
				if (!var3.screenIsMoving) {
					var3.game.setScreen(new ChooseLevelScreen(var3.game));
				}

				Assets_fighter.fighterIndex = var3.backGround.getCurrentPage();
			}
		});
		this.buttonPlay = new ImageButton(this.skin);
		this.buttonPlay.setBounds(240.0F, 400.0F, 100.0F, 40.0F);
		this.buttonPlay.addListener(new ChangeListener() {
			public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
				MainMenuScreen var3 = MainMenuScreen.this;
				if (!var3.screenIsMoving) {
					var3.game.setScreen(new ChooseLevelScreen(var3.game));
				}

				Assets_fighter.fighterIndex = var3.backGround.getCurrentPage();
			}
		});
		this.buttonStore = new ImageButton(this.skin);
		this.buttonStore.setBounds(240.0F, 350.0F, 100.0F, 40.0F);
		this.buttonStore.addListener(new ChangeListener() {
			public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
				MainMenuScreen var3 = MainMenuScreen.this;
				var3.game.setScreen(new PropPaymentScreen(var3.game));
				// if(Gdx.app.getType()==ApplicationType.Android)
				// ((NativeService)Gdx.app).showOffer();
			}
		});
		this.buttonMoreGame = new ImageButton(this.skin);
		this.buttonMoreGame.setBounds(240.0F, 300.0F, 100.0F, 40.0F);
		this.buttonMoreGame.addListener(new ChangeListener() {

			MainMenuScreen screenThis = MainMenuScreen.this;

			public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
			}
		});
		this.buttonShiftLeft = new ImageButton(this.skin);
		this.buttonShiftLeft.setBounds(5.0F, 380.0F, 50.0F, 40.0F);
		this.buttonShiftLeft.addListener(new ChangeListener() {
			public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
				int var3 = MainMenuScreen.this.backGround.shiftLeft();
				MainMenuScreen.this.updateShiftButton(var3);
			}
		});
		this.buttonShiftRight = new ImageButton(this.skin);
		this.buttonShiftRight.setBounds(430.0F, 380.0F, 50.0F, 40.0F);
		this.buttonShiftRight.addListener(new ChangeListener() {
			public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
				MainMenuScreen.this
						.updateShiftButton(MainMenuScreen.this.backGround
								.shiftRight());
			}
		});
		this.buttonAchievement = new ImageButton(this.skin);
		this.buttonAchievement.setBounds(10.0F, 60.0F, 40.0F, 40.0F);
		this.buttonAchievement.addListener(new ChangeListener() {
			public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
				MainMenuScreen.this.game.setScreen(new AchievementScreen(
						MainMenuScreen.this.game));
			}
		});
		this.buttonSetting = new ImageButton(this.skin);
		this.buttonSetting.setBounds(70.0F, 60.0F, 40.0F, 40.0F);
		this.buttonSetting.addListener(new ChangeListener() {
			public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
				MainMenuScreen.this.game.setScreen(new SettingScreen(
						MainMenuScreen.this.game));
			}
		});
		this.fightersChoose = new Image[6];
		String[] var13 = new String[] { "F22", "J20", "Rafale", "Su47",
				"Typhoon", "Me262" };

		for (int var14 = 0; var14 < 6; ++var14) {
			this.fightersChoose[var14] = new Image((Drawable) this.skin.get(
					var13[var14], TextureRegionDrawable.class));
			this.fightersChoose[var14].setVisible(false);
			this.fightersChoose[var14].setX(240.0F);
			this.fightersChoose[var14].setY(650.0F);
		}

		this.currentFighterId = 0;
		this.chooseFighter();
		this.screenStage.addActor(this.backGround);

		for (int var15 = 0; var15 < 6; ++var15) {
			this.screenStage.addActor(this.fightersChoose[var15]);
		}

		this.screenStage.addActor(this.buttonPlay);
		this.screenStage.addActor(this.buttonStore);
		this.screenStage.addActor(this.buttonMoreGame);
		this.screenStage.addActor(this.buttonShiftLeft);
		this.screenStage.addActor(this.buttonShiftRight);
		this.screenStage.addActor(this.buttonAchievement);
		this.screenStage.addActor(this.buttonSetting); 
		this.updateShiftButton(this.backGround.getCurrentPage());
		Gdx.input.setCatchBackKey(true);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
//		if (Gdx.app.getType() == ApplicationType.Android) {
//			((NativeService) Gdx.app).showVideoAds();
//		}
		InputMultiplexer multiplexer = BackProcessor
				.getProcessor(this.screenStage); 
		Gdx.input.setInputProcessor(multiplexer);
	}

	private void chooseFighter() {
		int var1 = this.currentFighterId;
		int var2 = this.backGround.getCurrentPage();
		this.fightersChoose[var1].setVisible(false);
		this.fightersChoose[var2].setVisible(true);
		this.currentFighterId = var2;
	}

	public static Skin getGameUIScreenSkin() {
		if (gameUISkin == null) {
			gameUISkin = new Skin();
		}

		Skin var0 = gameUISkin;
		var0.add("white", XScreen.getTexture());
		var0.add("default", XScreen.getBitmapFont48());
		Drawable var1 = var0.newDrawable("white", Color.DARK_GRAY);
		Drawable var2 = var0.newDrawable("white", Color.RED);
		Drawable var3 = var0.newDrawable("white", Color.GREEN);
		Drawable var4 = var0.newDrawable("white", Color.YELLOW);
		Drawable var5 = var0.newDrawable("white", Color.BLUE);
		Drawable var6 = var0.newDrawable("white", Color.BLACK);
		Drawable var7 = var0.newDrawable("white", Color.WHITE);
		Drawable var8 = var0.newDrawable("white", Color.CYAN);
		var0.add("gray", var1);
		var0.add("red", var2);
		var0.add("green", var3);
		var0.add("yellow", var4);
		var0.add("blue", var5);
		var0.add("black", var6);
		var0.add("white", var7);
		var0.add("cyan", var8);
		TextButton.TextButtonStyle var9 = new TextButton.TextButtonStyle();
		ImageButton.ImageButtonStyle var10 = new ImageButton.ImageButtonStyle();
		var9.up = var1;
		var9.down = var2;
		var9.font = var0.getFont("default");
		var10.up = var1;
		var10.down = var6;
		var10.disabled = var7;
		var10.imageDown = var3;
		var10.imageUp = var8;
		var0.add("default", var9);
		var0.add("default", var10);
		return var0;
	}

	public static Skin getMainMenuScreenSkin() {
		if (mainMenuScreenSkin != null) {
			return mainMenuScreenSkin;
		} else {
			Skin var0 = new Skin();
			var0.add("F22", Assets_MainMenu.uiF22, TextureRegionDrawable.class);
			var0.add("J20", Assets_MainMenu.uiJ20, TextureRegionDrawable.class);
			var0.add("Rafale", Assets_MainMenu.uiRafale,
					TextureRegionDrawable.class);
			var0.add("Su47", Assets_MainMenu.uiSu47,
					TextureRegionDrawable.class);
			var0.add("Typhoon", Assets_MainMenu.uiTyphoon,
					TextureRegionDrawable.class);
			var0.add("Me262", Assets_MainMenu.uiMe262,
					TextureRegionDrawable.class);
			var0.add("white", XScreen.getTexture());
			var0.add("default", XScreen.getBitmapFont48());
			Drawable var1 = var0.newDrawable("white", Color.DARK_GRAY);
			Drawable var2 = var0.newDrawable("white", Color.RED);
			Drawable var3 = var0.newDrawable("white", Color.GREEN);
			Drawable var4 = var0.newDrawable("white", Color.YELLOW);
			Drawable var5 = var0.newDrawable("white", Color.BLUE);
			Drawable var6 = var0.newDrawable("white", Color.BLACK);
			Drawable var7 = var0.newDrawable("white", Color.WHITE);
			Drawable var8 = var0.newDrawable("white", Color.CYAN);
			var0.add("gray", var1);
			var0.add("red", var2);
			var0.add("green", var3);
			var0.add("yellow", var4);
			var0.add("blue", var5);
			var0.add("black", var6);
			var0.add("white", var7);
			var0.add("cyan", var8);
			TextButton.TextButtonStyle var9 = new TextButton.TextButtonStyle();
			ImageButton.ImageButtonStyle var10 = new ImageButton.ImageButtonStyle();
			var9.up = var1;
			var9.down = var2;
			var9.font = var0.getFont("default");
			var10.up = var1;
			var10.down = var6;
			var10.disabled = var7;
			var10.imageDown = var3;
			var10.imageUp = var8;
			var0.add("default", var9);
			var0.add("default", var10);
			mainMenuScreenSkin = var0;
			return mainMenuScreenSkin;
		}
	}

	private void initMainMenueScreen() {
		this.screenIsMoving = false;
	}

	private void updateShiftButton(int var1) {
		switch (var1) {
		case 0:
			this.buttonShiftLeft.setDisabled(true);
			return;
		case 1:
		case 2:
		case 3:
		case 4:
			this.buttonShiftLeft.setDisabled(false);
			this.buttonShiftRight.setDisabled(false);
			return;
		case 5:
			this.buttonShiftRight.setDisabled(true);
			return;
		default:
		}
	}
}
