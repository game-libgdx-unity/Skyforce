package com.doodlegames.air.force.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.doodlegames.air.force.ActionResolver;
import com.doodlegames.air.force.NativeService;
import com.doodlegames.air.force.XGame;
import com.doodlegames.air.force.resource.Assets_UI;
import com.doodlegames.air.force.resource.Assets_fighter;
import com.doodlegames.air.force.screen.action.FontScaleAction;
import com.doodlegames.air.force.screen.screenActor.SelectImage;
import com.doodlegames.air.force.utils.DragListener;
import com.doodlegames.air.force.utils.Settings;
import com.doodlegames.utils.AndroidService;

public class UIMainMenuState extends UIScreenState implements EventListener {

	public static boolean showNewLap = false;
	public static boolean showNewStoreItem = false;
	private ImageButton achieveButton;
	private Image achieveTip;
	private boolean buttonsIsPullUp = false;
	private ImageButton leftArrowButton;
	private Image lockImage;
	private Image logoAirForce;
	private ImageButton moreButton;
	private Image newLapTip;
	private Image newStoreTip;
	private ImageButton playButton;
	private ImageButton rateButton;
	private ImageButton rightArrowButton;
	private SelectImage selectFighter;
	private ImageButton settingButton;
	private Label slideTipLabel;
	private ImageButton storeButton;

	public UIMainMenuState(UIScreen var1) {
		super(var1);
		// this.playButton = new TextButton("PLAY", this.skin,
		// "playButtonStyle");
		Drawable var5 = (Drawable) this.skin.get("F22",
				TextureRegionDrawable.class);
		this.playButton = new ImageButton(XGame.btnPlay);
		this.playButton.setPosition(127.0F, -73.0F);
		this.playButton.addListener(new UIMainMenuState.PlayButtonChange());
		this.newLapTip = new Image(
				this.skin.getDrawable("achievement_button_new"));
		this.newLapTip.setPosition(205.0F, 45.0F);
		this.playButton.addActor(this.newLapTip);
		this.newLapTip.setVisible(showNewLap);
		this.storeButton = new ImageButton(XGame.btnStore);
		this.storeButton.setPosition(127.0F, -160.0F);
		this.storeButton.addListener(new UIMainMenuState.StoreButtonChange());
		this.newStoreTip = new Image(
				this.skin.getDrawable("achievement_button_new"));
		this.newStoreTip.setPosition(205.0F, 45.0F);
		this.storeButton.addActor(this.newStoreTip);
		this.newStoreTip.setVisible(showNewStoreItem);
		this.moreButton = new ImageButton(XGame.btnMore);
		this.moreButton.setPosition(127.0F, -246.0F);
		this.moreButton.addListener(new UIMainMenuState.MoreButtonChange());
		this.slideTipLabel = new Label("<< FLICK TO CHANGE FIGHTER >>",
				this.skin, "slidTipStyle");
		this.slideTipLabel.setColor(UIMainMenuState.MainConst.TIPCOLOR);
		this.slideTipLabel.setPosition(80.0F, 280.0F);
		this.logoAirForce = new Image(this.skin.getDrawable("logo"));
		this.logoAirForce.setPosition(140.0F, 143.0F);
		this.logoAirForce.addAction(UIScreen.getLogoGlitterAction());
		this.logoAirForce.setTouchable(Touchable.disabled);

		Drawable[] var6 = new Drawable[] {
				(Drawable) this.skin.get("J20", TextureRegionDrawable.class),
				(Drawable) this.skin.get("Rafale", TextureRegionDrawable.class),
				(Drawable) this.skin.get("Su47", TextureRegionDrawable.class),
				(Drawable) this.skin
						.get("Typhoon", TextureRegionDrawable.class),
				(Drawable) this.skin.get("Me262", TextureRegionDrawable.class) };
		this.selectFighter = new SelectImage(var5, var6);
		Assets_fighter.fighterIndex = this.selectFighter.getIndex();
		this.selectFighter.setPosition(219.0F, 56.0F);
		float var7 = this.selectFighter.getWidth() / 2.0F;
		float var8 = this.selectFighter.getHeight() / 2.0F;
		this.selectFighter.setOrigin(var7, var8);
		this.selectFighter.setScale(0.7F);
		this.selectFighter.getColor().a = 0.8F;
		this.selectFighter.addListener(this);
		this.lockImage = new Image(this.skin.getDrawable("lock"));
		this.lockImage.setTouchable(Touchable.disabled);
		this.lockImage.getColor().a = 0.0F;
		this.lockImage.setPosition(227.0F, 71.0F);
		this.leftArrowButton = new ImageButton(this.skin, "leftArrow");
		this.leftArrowButton.setPosition(-2.0F, 59.0F);
		this.leftArrowButton.addAction(Actions.repeat(-1,
				Actions.sequence(Actions.moveTo(-7.0F, 59.0F, 0.33333334F,
						Interpolation.pow2Out), Actions.moveTo(-2.0F, 59.0F,
						0.33333334F, Interpolation.pow2Out))));
		this.leftArrowButton.addListener(new ChangeListener() {
			public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
				UIMainMenuState.this.screen.playButtonSound();
				UIMainMenuState.this.chooseLastFighter();
			}
		});
		this.rightArrowButton = new ImageButton(this.skin, "rightArrow");
		this.rightArrowButton.setPosition(447.0F, 59.0F);
		this.rightArrowButton.addAction(Actions.repeat(-1, Actions.sequence(
				Actions.moveTo(452.0F, 59.0F, 0.33333334F,
						Interpolation.pow2Out), Actions.moveTo(447.0F, 59.0F,
						0.33333334F, Interpolation.pow2Out))));
		this.rightArrowButton.addListener(new ChangeListener() {
			public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
				UIMainMenuState.this.screen.playButtonSound();
				UIMainMenuState.this.chooseNextFighter();
			}
		});
		this.rateButton = new ImageButton(this.skin, "rateButtonStyle");
		this.rateButton.setPosition(25.0F, 97.0F);
		this.rateButton.addListener(new UIMainMenuState.RateButtonChange());
		this.achieveButton = new ImageButton(this.skin, "achieveButtonStyle") {
			public Actor hit(float var1, float var2) {
				if (Settings.isSmallScreen()) {
					float var3 = UIMainMenuState.this.achieveButton.getHeight();
					if (var2 < var3 * 2.0F) {
						var2 = var3 / 2.0F;
					}
				}

				return super.hit(var1, var2, true);
			}
		};
		this.achieveButton.setPosition(323.0F, 98.0F);
		this.achieveButton
				.addListener(new UIMainMenuState.AchievementButtonChange());
		this.achieveTip = new Image(
				this.skin.getDrawable("achievement_button_new"));
		this.achieveTip.setPosition(31.0F, 35.0F);
		this.achieveTip.setVisible(UIAchieveState.checkNewAchGet());
		this.achieveTip.setTouchable(Touchable.disabled);
		this.achieveButton.addActor(this.achieveTip);
		this.settingButton = new ImageButton(this.skin, "settingButtonStyle") {
			public Actor hit(float var1, float var2) {
				if (Settings.isSmallScreen()) {
					float var3 = UIMainMenuState.this.settingButton.getHeight();
					if (var2 < var3 * 2.0F) {
						var2 = var3 / 2.0F;
					}
				}

				return super.hit(var1, var2, true);
			}
		};
		this.settingButton.setPosition(391.0F, 98.0F);
		this.settingButton
				.addListener(new UIMainMenuState.SettingButtonChange());
	}

	public static void addResource(Skin var0) {
		TextButton.TextButtonStyle var1 = new TextButton.TextButtonStyle();
		var1.font = XScreen.getBitmapFont48();
		var1.down = var0.getDrawable("green_button");
		var1.up = var0.getDrawable("green_button");
		var1.downFontColor = UIMainMenuState.MainConst.WHITEFONT;
		var1.fontColor = UIMainMenuState.MainConst.GREENFONT;
		var0.add("playButtonStyle", var1);
		TextButton.TextButtonStyle var2 = new TextButton.TextButtonStyle();
		var2.font = XScreen.getBitmapFont36();
		var2.down = var0.getDrawable("green_button");
		var2.up = var0.getDrawable("green_button");
		var2.downFontColor = UIMainMenuState.MainConst.WHITEFONT;
		var2.fontColor = UIMainMenuState.MainConst.GREENFONT;
		var0.add("storeButtonStyle", var2);
		TextButton.TextButtonStyle var3 = new TextButton.TextButtonStyle();
		var3.font = XScreen.getBitmapFont48();
		var3.down = var0.getDrawable("yellow_button");
		var3.up = var0.getDrawable("yellow_button");
		var3.downFontColor = UIMainMenuState.MainConst.WHITEFONT;
		var3.fontColor = UIMainMenuState.MainConst.YELLOWFONT;
		var0.add("moreButtonStyle", var3);
		Label.LabelStyle var4 = new Label.LabelStyle();
		var4.font = XScreen.getBitmapFont24();
		var4.fontColor = UIMainMenuState.MainConst.TIPCOLOR;
		var0.add("slidTipStyle", var4);
		var0.add("F22", Assets_UI.uiF22, TextureRegionDrawable.class);
		var0.add("J20", Assets_UI.uiJ20, TextureRegionDrawable.class);
		var0.add("Su47", Assets_UI.uiSu47, TextureRegionDrawable.class);
		var0.add("Rafale", Assets_UI.uiRafale, TextureRegionDrawable.class);
		var0.add("Typhoon", Assets_UI.uiTyphoon, TextureRegionDrawable.class);
		var0.add("Me262", Assets_UI.uiMe262, TextureRegionDrawable.class);
		var0.add("arrowButtonNormalLeft", new TextureAtlas.AtlasSprite(
				Assets_UI.arrowButtonNormalLeft), Sprite.class);
		var0.add("arrowButtonPressLeft", new TextureAtlas.AtlasSprite(
				Assets_UI.arrowButtonPressLeft), Sprite.class);
		ImageButton.ImageButtonStyle var5 = new ImageButton.ImageButtonStyle();
		var5.imageDown = var0.getDrawable("arrowButtonPressLeft");
		var5.imageUp = var0.getDrawable("arrowButtonNormalLeft");
		var0.add("leftArrow", var5);
		ImageButton.ImageButtonStyle var6 = new ImageButton.ImageButtonStyle();
		var6.imageDown = var0.getDrawable("arrow_button_press");
		var6.imageUp = var0.getDrawable("arrow_button_normal");
		var0.add("rightArrow", var6);
		ImageButton.ImageButtonStyle var7 = new ImageButton.ImageButtonStyle();
		var7.up = var0.getDrawable("rate_button_normal");
		var7.down = var0.getDrawable("rate_button_press");
		var0.add("rateButtonStyle", var7);
		ImageButton.ImageButtonStyle var8 = new ImageButton.ImageButtonStyle();
		var8.up = var0.getDrawable("achievement_button_normal");
		var8.down = var0.getDrawable("achievement_button_press");
		var0.add("achieveButtonStyle", var8);
		ImageButton.ImageButtonStyle var9 = new ImageButton.ImageButtonStyle();
		var9.up = var0.getDrawable("setting_button_normal");
		var9.down = var0.getDrawable("setting_button_press");
		var0.add("settingButtonStyle", var9);
	}

	private void changeTostate(Actor var1, int var2) {
		this.pullControls();
		this.screen.getUIUpperGroup()
				.addAction(
						Actions.moveTo(0.0F, 761.0F, 0.33333334F,
								Interpolation.pow2Out));
		this.screen.getUIBottomGroup().addAction(
				Actions.moveTo(0.0F, -100.0F, 0.33333334F,
						Interpolation.pow2Out));
		var1.addAction(Actions.delay(
				0.2F + Math.max(Math.max(0.6666667F, 0.6666667F), 0.6666667F),
				UIScreen.uiState(this.screen, var2)));
	}

	private void chooseLastFighter() {
		this.selectFighter.chooseLastDrawable();
		this.screen.rotateWireFrame();
	}

	private void chooseNextFighter() {
		this.selectFighter.chooseNextDrawable();
		this.screen.rotateWireFrame();
	}

	private void initBottomPart() {
		Group var1 = this.screen.getUIBottomGroup();
		if (Settings.isSmallScreen()) {
			this.settingButton.setY(103.0F);
			this.achieveButton.setY(103.0F);
			this.rateButton.setY(102.0F);
		}

		MoveToAction var2 = Actions.moveTo(0.0F, 0.0F, 0.33333334F,
				Interpolation.pow2Out);
		this.screen.getUIBottomGroup().addAction(var2);
		var1.addActor(this.rateButton);
		var1.addActor(this.settingButton);
		var1.addActor(this.achieveButton);
		this.achieveTip.setVisible(UIAchieveState.checkNewAchGet());
		this.rateButton.setTouchable(Touchable.enabled);
		this.settingButton.setTouchable(Touchable.enabled);
		this.achieveButton.setTouchable(Touchable.enabled);
	}

	private void initUpperPart() {
		Group var1 = this.screen.getUIUpperGroup();
		MoveToAction var2 = Actions.moveTo(0.0F, 541.0F, 0.33333334F,
				Interpolation.pow2Out);
		this.screen.getUIUpperGroup().addAction(var2);
		var1.addActor(this.logoAirForce);
		var1.addActor(this.selectFighter);
		var1.addActor(this.lockImage);
		var1.addActor(this.leftArrowButton);
		var1.addActor(this.rightArrowButton);
	}

	private void pullControls() {
		boolean var1 = true;
		this.playButton.getActions().clear();
		this.storeButton.getActions().clear();
		this.moreButton.getActions().clear();
		this.slideTipLabel.getActions().clear();
		Interpolation.PowOut var2 = Interpolation.pow2Out;
		if (this.buttonsIsPullUp) {
			this.playButton.addAction(Actions.sequence(
					Actions.delay(0.16666667F),
					FontScaleAction.fontScale(0.16666667F, var1),
					Actions.moveTo(127.0F, -73.0F, 0.5F, var2)));
			this.storeButton.addAction(Actions.sequence(
					Actions.delay(0.083333336F),
					FontScaleAction.fontScale(0.16666667F, var1),
					Actions.moveTo(127.0F, -160.0F, 0.5F, var2)));
			this.moreButton.addAction(Actions.sequence(
					FontScaleAction.fontScale(0.16666667F, var1),
					Actions.moveTo(127.0F, -246.0F, 0.5F, var2)));
			this.slideTipLabel.setVisible(false);
			this.rateButton.addAction(Actions.fadeOut(0.5F));
			this.achieveButton.addAction(Actions.fadeOut(0.5F));
			this.settingButton.addAction(Actions.fadeOut(0.5F));
		} else {
			this.playButton.addAction(Actions.sequence(
					Actions.moveTo(127.0F, 505.0F, 0.5F, var2),
					FontScaleAction.fontScale(0.16666667F, false)));
			this.storeButton.addAction(Actions.sequence(
					Actions.delay(0.083333336F),
					Actions.moveTo(127.0F, 418.0F, 0.5F, var2),
					FontScaleAction.fontScale(0.16666667F, false)));
			this.moreButton.addAction(Actions.sequence(
					Actions.delay(0.16666667F),
					Actions.moveTo(127.0F, 331.0F, 0.5F, var2),
					FontScaleAction.fontScale(0.16666667F, false)));
			SequenceAction var3 = Actions.sequence(
					Actions.alpha(0.0F),
					Actions.delay(1.2F),
					Actions.repeat(-1, Actions.sequence(
							Actions.alpha(1.0F, 0.33333334F),
							Actions.alpha(1.0F, 0.33333334F),
							Actions.alpha(0.0F, 0.6666667F))));
			this.slideTipLabel.addAction(var3);
			this.slideTipLabel.setVisible(var1);
			this.rateButton.addAction(Actions.fadeIn(0.5F));
			this.achieveButton.addAction(Actions.fadeIn(0.5F));
			this.settingButton.addAction(Actions.fadeIn(0.5F));
		}

		if (this.buttonsIsPullUp) {
			var1 = false;
		}

		this.buttonsIsPullUp = var1;
	}

	public void backPressed() {
		if (Gdx.app.getType() == Application.ApplicationType.Android) {
			((AndroidService)Gdx.app).showAppWall();
		}

	}

	public boolean currentFighterIsLock() {
		return this.selectFighter.getIndex() > -1 + Settings.unlockFighter;
	}

	public boolean handle(Event var1) {
		if (var1 instanceof SelectImage.IndexChangeEvent) {
			if (this.currentFighterIsLock()) {
				this.lockImage.addAction(Actions.alpha(0.7F, 0.16666667F));
				this.selectFighter.setVisible(false);
			} else {
				this.lockImage.addAction(Actions.alpha(0.0F, 0.16666667F));
				this.selectFighter.setVisible(true);
			}

			Assets_fighter.fighterIndex = this.selectFighter.getIndex();
			return true;
		} else {
			return false;
		}
	}

	public void switchToThisState(UIScreen var1) {
		super.switchToThisState(var1);
		this.initUpperPart();
		this.initBottomPart();
		Group var2 = var1.getStateGroup();
		this.screen.getStage().addListener(
				new UIMainMenuState.DrageChangeFighter());
		var2.addActor(this.playButton);
		var2.addActor(this.storeButton);
		var2.addActor(this.moreButton);
		var2.addActor(this.slideTipLabel);
		XScreen.getBitmapFont48().setScale(1.0F, 0.01F);
		XScreen.getBitmapFont36().setScale(1.0F, 0.01F);
		this.pullControls();
		this.playButton.setTouchable(Touchable.enabled);
		this.storeButton.setTouchable(Touchable.enabled);
		this.moreButton.setTouchable(Touchable.enabled);
		this.slideTipLabel.getColor().a = 0.0F;
		this.newLapTip.setVisible(showNewLap);
		this.newStoreTip.setVisible(showNewStoreItem);
		// ////((Lightning)////((Lightning)this.screen.game)).getPlatFormFunction().showBanner();
	}

	private class SettingButtonChange extends ChangeListener {

		UIMainMenuState wThis;

		private SettingButtonChange() {
			this.wThis = UIMainMenuState.this;
		}

		public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
			UIMainMenuState.this.screen.playButtonSound();
			if (this.wThis.buttonsIsPullUp) {
				var2.setTouchable(Touchable.disabled);
				UIMainMenuState.this.changeTostate(var2, 3);
			}
		}
	}

	private class RateButtonChange extends ChangeListener {

		private RateButtonChange() {
		}

		public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
			UIMainMenuState.this.screen.playButtonSound();
			if (Gdx.app.getType() == ApplicationType.Android)
				((ActionResolver) Gdx.app).getLeaderboardGPGS();
			// ((NativeService)Gdx.app).rate();
			// ////((Lightning)////((Lightning)UIMainMenuState.this.screen.game)).getPlatFormFunction().rateGame();
		}
	}

	private class DrageChangeFighter extends DragListener {

		public int threshold;
		UIMainMenuState wThis;

		private DrageChangeFighter() {
			this.threshold = 30;
			this.wThis = UIMainMenuState.this;
		}

		public void dragStop(InputEvent var1, float var2, float var3, int var4) {
			if (var2 - this.getTouchDownX() > (float) this.threshold) {
				this.wThis.chooseLastFighter();
			}

			if (var2 - this.getTouchDownX() < (float) (-this.threshold)) {
				this.wThis.chooseNextFighter();
			}

		}
	}

	private class AchievementButtonChange extends ChangeListener {

		UIMainMenuState wThis;

		private AchievementButtonChange() {
			this.wThis = UIMainMenuState.this;
		}

		public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
			UIMainMenuState.this.screen.playButtonSound();
			if (this.wThis.buttonsIsPullUp) {
				var2.setTouchable(Touchable.disabled);
				UIMainMenuState.this.changeTostate(var2, 4);
			}
		}
	}

	private class StoreButtonChange extends ChangeListener {

		UIMainMenuState wThis;

		private StoreButtonChange() {
			this.wThis = UIMainMenuState.this;
		}

		public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
			UIMainMenuState.this.screen.playButtonSound();
			if (this.wThis.buttonsIsPullUp) {
				var2.setTouchable(Touchable.disabled);
				UIMainMenuState.this.changeTostate(var2, 2);
				UIMainMenuState.showNewStoreItem = false;
				// if(Gdx.app.getType()==ApplicationType.Android)
				// ((NativeService)Gdx.app).showOffer();
				System.out.println("Call tapjoy");
			}
		}
	}

	private class MoreButtonChange extends ChangeListener {

		private MoreButtonChange() {
		}

		public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
			UIMainMenuState.this.screen.playButtonSound();
			if (Gdx.app.getType() == ApplicationType.Android)
				((NativeService) Gdx.app).moreGames();
		}
	}

	private class PlayButtonChange extends ChangeListener {

		UIMainMenuState wThis;

		private PlayButtonChange() {
			this.wThis = UIMainMenuState.this;
		}

		public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
			UIMainMenuState.this.screen.playButtonSound();
			if (!UIMainMenuState.this.currentFighterIsLock()
					&& this.wThis.buttonsIsPullUp) {
				var2.setTouchable(Touchable.disabled);
				UIMainMenuState.this.changeTostate(var2, 1);
				UIMainMenuState.showNewLap = false;
			}
		}
	}

	public static class MainConst {

		public static final int ACHIEVEX = 323;
		public static final int ACHIEVEY = 98;
		public static final int ARROWDELTA = 5;
		public static final int ARROWY = 59;
		public static final float BUTTONDELAY1 = 0.083333336F;
		public static final float BUTTONDELAY2 = 0.16666667F;
		public static final float FIGHTERALPHA = 0.8F;
		public static final float FIGHTERSCALE = 0.7F;
		public static final int FIGHTERX = 219;
		public static final int FIGHTERY = 56;
		public static final float FLOATCYCLE = 0.33333334F;
		public static final Color GREENFONT = new Color(0.4745098F,
				0.91764706F, 0.0F, 1.0F);
		public static final int LEFTARROWX = -2;
		public static final int LOGOX = 140;
		public static final int LOGOY = 143;
		public static final float MOREAPPEAR = 0.5F;
		public static final int MOREBUTTONENDY = 331;
		public static final int MOREBUTTONSTARTY = -246;
		public static final int MOREBUTTONX = 127;
		public static final float MOREFONTAPPEAR = 0.16666667F;
		public static final float PLAYAPPEAR = 0.5F;
		public static final int PLAYBUTTONENDY = 505;
		public static final int PLAYBUTTONSTARTY = -73;
		public static final int PLAYBUTTONX = 127;
		public static final float PLAYFONTAPPEAR = 0.16666667F;
		public static final int RATEX = 25;
		public static final int RATEY = 97;
		public static final int RIGHTARROWX = 447;
		public static final int SETINTX = 391;
		public static final int SETINTY = 98;
		public static final float STOREAPPEAR = 0.5F;
		public static final int STOREBUTTONENDY = 418;
		public static final int STOREBUTTONSTARTY = -160;
		public static final int STOREBUTTONX = 127;
		public static final float STOREFONTAPPEAR = 0.16666667F;
		public static final Color TIPCOLOR = GREENFONT;
		public static final float TIPDELAY = 1.2F;
		public static final int TIPLABELX = 80;
		public static final int TIPLABELY = 280;
		public static final Color WHITEFONT = Color.WHITE;
		public static final Color YELLOWFONT = new Color(0.91764706F,
				0.627451F, 0.0F, 1.0F);

	}
}
