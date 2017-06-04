package com.doodlegames.air.force.screen;

import java.util.Iterator;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.doodlegames.air.force.XGame;
import com.doodlegames.air.force.resource.Assets_UI;
import com.doodlegames.air.force.screen.action.FontScaleAction;
import com.doodlegames.air.force.screen.screenActor.XTable;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Settings;

public class UIAchieveState extends UIScreenState {

	private static final int ACHCOMPLETED = 2;
	private static final int ACHINPROGRESS = 0;
	private static final int ACHREWARD = 1;
	private static final String[] STATETEXT = new String[] { "IN PROGRESS",
			"REWARD", "COMPLETED" };
	public static boolean NO_ADD = false;
	private ScrollPane achPane;
	private int achSelectedIndex = -1;
	private ImageButton backButton;
	private Label explation_words;
	private TextButton getMoneyButton;
	private ScrollPane levelStatePane;
	private int levelStateSelectedIndex = -1;
	private Table paneFooter;
	private int paneInUse = 0;
	private Table paneTile;
	private Label titleAchLabel;
	private Label titleMonyeLabel;
	private Label titleStaLabel;

	public UIAchieveState(UIScreen var1) {
		super(var1);
		this.constructTile();
		this.constructAchPane();
		this.constructStatePane();
		this.constructFooter();
		this.constructMoneyButton();
		this.constructBackButton();
	}

	public static void addResource(Skin var0) {
		ScrollPane.ScrollPaneStyle var1 = new ScrollPane.ScrollPaneStyle();
		var0.add("white", XScreen.getTexture());
		var1.background = var0.newDrawable("white", new Color(0.050980393F,
				0.09803922F, 0.09019608F, 0.8F));
		var1.vScroll = var0.newDrawable("white", new Color(0.050980393F,
				0.09803922F, 0.09019608F, 1.0F));
		var1.vScrollKnob = var0.newDrawable("white", new Color(0.21960784F,
				0.6431373F, 1.0F, 1.0F));
		((SpriteDrawable) var1.vScrollKnob).setMinWidth(2.0F);
		var0.add("default", var1);
		Label.LabelStyle var2 = new Label.LabelStyle();
		var2.font = XScreen.getBitmapFont18();
		var2.fontColor = new Color(0.21960784F, 0.6431373F, 1.0F, 1.0F);
		var0.add("achLabelNormalStyle", var2);
		Label.LabelStyle var3 = new Label.LabelStyle();
		var3.font = XScreen.getBitmapFont18();
		var3.fontColor = new Color(0.68235296F, 0.68235296F, 0.68235296F, 1.0F);
		var0.add("achLabelDisableStyle", var3);
		UIAchieveState.Const.titleDrawables[0] = new SpriteDrawable(
				new TextureAtlas.AtlasSprite(Assets_UI.atlas.findRegion("tag",
						0)));
		UIAchieveState.Const.titleDrawables[1] = new SpriteDrawable(
				new TextureAtlas.AtlasSprite(Assets_UI.atlas.findRegion("tag",
						1)));
		UIAchieveState.Const.achieveLogoNormal[0] = var0
				.getDrawable("in_progress_normal");
		UIAchieveState.Const.achieveLogoNormal[1] = var0
				.getDrawable("reward_normal");
		UIAchieveState.Const.achieveLogoNormal[2] = var0
				.getDrawable("completed_normal");
		UIAchieveState.Const.achieveLogoPress[0] = var0
				.getDrawable("in_progress_press");
		UIAchieveState.Const.achieveLogoPress[1] = var0
				.getDrawable("reward_press");
		UIAchieveState.Const.achieveLogoPress[2] = var0
				.getDrawable("completed_press");
		UIAchieveState.Const.diffStarNormal = var0
				.getDrawable("chievement_level_normal");
		UIAchieveState.Const.diffStarPress = var0.getDrawable("level_press");
		UIAchieveState.Const.diffStarYellow = var0
				.getDrawable("stats_level_normal");
		UIAchieveState.Const.titleLabelUpStyles = var2;
		UIAchieveState.Const.titleLabelDownStyles = var3;
	}

	private void backToMain() {
		this.screen.getStateGroup().addAction(
				Actions.sequence(Actions.moveTo(0.0F, -800.0F, 0.5F,
						Interpolation.pow2Out), new Action() {
					public boolean act(float var1) {
						UIAchieveState.this.screen.askForStateChange(0);
						return true;
					}
				}));
		this.backButton.setTouchable(Touchable.disabled);
		this.backButton.addAction(Actions.fadeOut(0.5F));
	}

	public static boolean checkLapAchUnlock() {
		return -1 + Settings.lapCount > Settings.rewardLapCount;
	}

	public static boolean checkNewAchGet() {
		UIAchieveState.Const.checkAchUnlock();
		UIAchieveState.AchElement[] var0 = UIAchieveState.Const.achievements;
		int var1 = var0.length;

		for (int var2 = 0; var2 < var1; ++var2) {
			if (var0[var2].achState == 1) {
				return true;
			}
		}

		return false;
	}

	private void constructAchPane() {
		Table var1 = new Table();
		int var2 = UIAchieveState.Const.achievements.length;

		for (int var3 = 0; var3 < var2; ++var3) {
			UIAchieveState.AchElement var5 = UIAchieveState.Const.achievements[var3];
			XTable var6 = new XTable(var3, this.skin);
			var6.setBackground(this.skin.getDrawable("store_button_normal"));
			UIAchieveState.achPaneElePressLis var7 = new UIAchieveState.achPaneElePressLis();
			var6.addListener(var7);
			var6.setTouchable(Touchable.enabled);
			var6.left();
			Table var10 = new Table();
			var10.add(
					(Actor) (new Image(
							UIAchieveState.Const.achieveLogoNormal[var5
									.getAchState()]))).expand();
			var6.add(var10).width(61.0F).expandY().fillY();
			Group var13 = new Group();
			Label var14 = new Label(
					UIAchieveState.Const.achievements[var3].achName, this.skin,
					"font24");
			var14.setColor(UIAchieveState.Const.itemNormalColor);
			var14.setPosition(18.0F, 36.0F);
			var13.addActor(var14);
			Label var15 = new Label("LV", this.skin, "font24");
			var15.setColor(UIAchieveState.Const.itemNormalColor);
			var15.setPosition(19.0F, 5.0F);
			var13.addActor(var15);
			int var16 = UIAchieveState.Const.achievements[var3].difficulty;

			for (int var17 = 0; var17 < var16; ++var17) {
				Image var18 = new Image(UIAchieveState.Const.diffStarNormal);
				var18.setPosition((float) (70 + var17 * 21), 10.0F);
				var13.addActor(var18);
			}

			var6.add(var13).width(185.0F).fillY();
			Table var20 = new Table();
			int var21 = UIAchieveState.Const.achievements[var3].getAchState();
			Label.LabelStyle var22 = new Label.LabelStyle(
					XScreen.getBitmapFont24(),
					UIAchieveState.Const.itemStateColor);
			Label var23 = new Label(STATETEXT[var21], var22);
			if (var21 == 1) {
				var23.addAction(Actions.repeat(-1,
						Actions.sequence(Actions.alpha(1.0F, 0.33333334F,
								Interpolation.pow2Out), Actions.alpha(1.0F,
								0.33333334F), Actions.alpha(0.0F, 0.33333334F,
								Interpolation.pow2In))));
			}

			var23.setColor(UIAchieveState.Const.itemNormalColor);
			var20.add((Actor) var23);
			var6.add(var20).expandX();
			var1.add((Actor) var6);
			var1.row();
		}

		ScrollPane var4 = new ScrollPane(var1, this.skin);
		this.achPane = var4;
		this.achPane.setPosition(37.0F, 338.0F);
		this.achPane.setFadeScrollBars(false);
		this.achPane.setScrollingDisabled(true, false);
		this.achPane.setHeight(366.0F);
		this.achPane.setWidth(401.0F);
		this.achPane.invalidate();
		this.achPane.validate();
	}

	private void constructBackButton() {
		this.backButton = new ImageButton(this.skin, "LevelBackTo");
		this.backButton.setPosition(25.0F, 97.0F);
		this.backButton.addListener(new ChangeListener() {
			public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
				UIAchieveState.this.screen.playBackSound();
				UIAchieveState.this.backToMain();
			}
		});
		this.backButton.getColor().a = 0.0F;
		this.backButton.setTouchable(Touchable.disabled);
	}

	private void constructFooter() {
		this.paneFooter = new Table();
		this.paneFooter.setBounds(37.0F, 276.0F, 402.0F, 61.0F);
		this.paneFooter.setBackground(this.skin.getDrawable("explanation_bar"));
		this.paneFooter.setTouchable(Touchable.disabled);
		this.explation_words = new Label("  ", this.skin, "LabelNormalStyle");
		this.explation_words
				.setColor(this.explation_words.getStyle().fontColor);
		this.explation_words.setWrap(true);
		this.explation_words.setAlignment(10);
		this.paneFooter.left().top().add((Actor) this.explation_words)
				.minWidth(402.0F).fill().padLeft(15.0F).padTop(8.0F);
	}

	private void constructMoneyButton() {
		this.getMoneyButton = new TextButton("GET MONEY", this.skin,
				"storeButtonStyle");
		this.getMoneyButton.setPosition(123.0F, -66.0F);
		this.getMoneyButton.addListener(new ChangeListener() {
			public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
				UIAchieveState.this.screen.playButtonSound();
				if (UIAchieveState.this.achSelectedIndex >= 0
						&& UIAchieveState.this.achSelectedIndex < UIAchieveState.Const.achievements.length) {
					int var3 = UIAchieveState.Const.achievements[UIAchieveState.this.achSelectedIndex]
							.getRewardMoney();
					Settings.money += (long) var3;
					UIAchieveState.this.titleMonyeLabel.setText(""
							+ Settings.money);
					UIAchieveState.this
							.updateAchItem(UIAchieveState.this.achSelectedIndex);
					UIAchieveState.this.moveMovingButton(false);
					Settings.save();
				}

			}
		});
	}

	private void constructStatePane() {
		Table var1 = new Table();
		Label.LabelStyle var2 = new Label.LabelStyle(XScreen.getBitmapFont48(),
				UIAchieveState.Const.itemStateColor);
		int var3 = UIAchieveState.Const.levelAchievements.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			UIAchieveState.AchElement var6 = UIAchieveState.Const.levelAchievements[var4];
			XTable var7 = new XTable(var4, this.skin);
			var7.setBackground(this.skin.getDrawable("store_button_cash"));
//			var7.setBackground(XGame.btnMis1);
			UIAchieveState.staPaneElePressLis var8 = new UIAchieveState.staPaneElePressLis();
			var7.addListener(var8);
			var7.setTouchable(Touchable.enabled);
			var7.left();
			Table var11 = new Table();
			Animation var12 = null;
			switch (var4) {
			case 0:
			case 1:
				var12 = Assets_UI.earthAnimation;
				break;
			case 2:
			case 3:
				var12 = Assets_UI.moonAnimation;
				break;
			case 4:
				var12 = Assets_UI.asteroidAnimation;
				break;
			case 5:
			case 6:
				var12 = Assets_UI.marAnimation;
			}

			Image var13 = new Image(var12);
			var11.add((Actor) var13).maxHeight(0.2F * var13.getHeight())
					.maxWidth(0.2F * var13.getWidth());
			var7.add(var11).width(61.0F).expandY().fillY();
			Group var16 = new Group();
			Label var17 = new Label(var6.achName, this.skin, "font24");
			var17.setColor(UIAchieveState.Const.itemStateColor);
			var17.setPosition(18.0F, 36.0F);
			var16.addActor(var17);
			Label var18 = new Label("LV", this.skin, "font24");
			var18.setColor(UIAchieveState.Const.itemStateColor);
			var18.setPosition(19.0F, 5.0F);
			var16.addActor(var18);
			int var19 = this.getLevelEvaluation(var4 + 1);

			for (int var20 = 0; var20 < var19; ++var20) {
				Image var21 = new Image(UIAchieveState.Const.diffStarYellow);
				var21.setPosition((float) (70 + var20 * 21), 10.0F);
				var16.addActor(var21);
			}

			var7.add(var16).width(185.0F).fillY();
			Table var23 = new Table();
			long var24 = (long) Settings.levelScore[var4];
			Label var26 = new Label(var24 + "", var2);
			var26.setColor(UIAchieveState.Const.itemStateColor);
			var23.add((Actor) var26);
			var7.add(var23).expandX().right().padRight(15.0F);
			var1.add((Actor) var7);
			var1.row();
		}

		ScrollPane var5 = new ScrollPane(var1, this.skin);
		this.levelStatePane = var5;
		this.levelStatePane.setPosition(37.0F, 338.0F);
		this.levelStatePane.setFadeScrollBars(false);
		this.levelStatePane.setScrollingDisabled(true, false);
		this.levelStatePane.setHeight(366.0F);
		this.levelStatePane.setWidth(401.0F);
		this.levelStatePane.invalidate();
		this.levelStatePane.validate();
		this.levelStatePane.setVisible(false);
	}

	private void constructTile() {
		this.titleAchLabel = new Label("ACHIEVEMENT", this.skin,
				"achLabelNormalStyle");
		this.titleAchLabel.setColor(this.titleAchLabel.getStyle().fontColor);
		this.titleStaLabel = new Label("HIGH SCORE", this.skin,
				"achLabelDisableStyle");
		this.titleStaLabel.setColor(this.titleStaLabel.getStyle().fontColor);
		Label.LabelStyle var1 = new Label.LabelStyle(XScreen.getBitmapFont30(),
				UIAchieveState.Const.itemStateColor);
		this.titleMonyeLabel = new Label("" + Settings.money, var1);
		this.titleMonyeLabel.setColor(UIAchieveState.Const.itemStateColor);
		this.paneTile = new Table(this.skin);
		this.paneTile.left();
		this.paneTile
				.setBackground(UIAchieveState.Const.titleDrawables[this.paneInUse]);
		this.paneTile.setPosition(35.0F, 704.0F);
		this.paneTile.add((Actor) this.titleAchLabel)
				.width(this.titleAchLabel.getWidth()).minWidth(112.0F);
		this.paneTile.add((Actor) this.titleStaLabel)
				.width(this.titleStaLabel.getWidth()).minWidth(112.0F);
		this.paneTile.add((Actor) this.titleMonyeLabel).expand().bottom()
				.right().padBottom(-1.0F);
		this.paneTile.setWidth(403.0F);
		this.paneTile.setHeight(45.0F);
		this.paneTile.setTouchable(Touchable.enabled);
		this.paneTile.addListener(new ClickListener() {
			public void clicked(InputEvent var1, float var2, float var3) {
				if (var2 >= 0.0F && var2 <= 224.0F) {
					byte var4;
					if (var2 < 112.0F) {
						var4 = 0;
					} else {
						var4 = 1;
					}

					if (UIAchieveState.this.switchPane(var4)) {
						UIAchieveState.this.screen.playButtonSound();
						return;
					}
				}

			}
		});
	}

	private int getLevelEvaluation(int var1) {
		return Settings.levelStar[var1 - 1];
	}

	private Action getMoveInAction() {
		return Actions.sequence(
				Actions.moveTo(123.0F, 180.0F, 0.2F, Interpolation.pow2Out),
				FontScaleAction.fontScaleIn(0.16666667F));
	}

	private Action getMoveOutAction() {
		return Actions.sequence(FontScaleAction.fontScaleOut(0.16666667F),
				Actions.moveTo(123.0F, -66.0F, 0.2F, Interpolation.pow2Out));
	}

	private void moveMovingButton(boolean var1) {
		if (var1) {
			this.getMoneyButton.addAction(this.getMoveInAction());
		} else {
			this.getMoneyButton.addAction(this.getMoveOutAction());
		}
	}

	private void moveOutAndIn() {
		this.getMoneyButton.addAction(Actions.sequence(this.getMoveOutAction(),
				this.getMoveInAction()));
	}

	private boolean switchPane(int var1) {
		if (var1 != this.paneInUse && (var1 == 0 || var1 == 1)) {
			this.clearSelectedIndex();
			this.explation_words.setText(" ");
			this.paneInUse = var1;
			ScrollPane var2 = this.achPane;
			boolean var3;
			if (var1 == 0) {
				var3 = true;
			} else {
				var3 = false;
			}

			var2.setVisible(var3);
			ScrollPane var4 = this.levelStatePane;
			boolean var5;
			if (var1 == 1) {
				var5 = true;
			} else {
				var5 = false;
			}

			var4.setVisible(var5);
			Label var6 = this.titleAchLabel;
			Label.LabelStyle var7;
			if (var1 == 0) {
				var7 = UIAchieveState.Const.titleLabelUpStyles;
			} else {
				var7 = UIAchieveState.Const.titleLabelDownStyles;
			}

			var6.setStyle(var7);
			this.titleAchLabel
					.setColor(this.titleAchLabel.getStyle().fontColor);
			Label var8 = this.titleStaLabel;
			Label.LabelStyle var9;
			if (var1 == 1) {
				var9 = UIAchieveState.Const.titleLabelUpStyles;
			} else {
				var9 = UIAchieveState.Const.titleLabelDownStyles;
			}

			var8.setStyle(var9);
			this.titleStaLabel
					.setColor(this.titleStaLabel.getStyle().fontColor);
			this.paneTile
					.setBackground(UIAchieveState.Const.titleDrawables[var1]);
			this.moveMovingButton(false);
			return true;
		} else {
			return false;
		}
	}

	private void updateAchAllItem() {
		UIAchieveState.Const.checkAchUnlock();

		for (int var1 = 0; var1 < UIAchieveState.Const.achievements.length; ++var1) {
			this.updateAchItem(var1);
		}

	}

	private void updateAchItem(int var1) {
		boolean var2;
		if (var1 == this.achSelectedIndex) {
			var2 = true;
		} else {
			var2 = false;
		}

		XTable var3 = (XTable) ((Table) this.achPane.getChildren().get(0))
				.getChildren().get(var1);
		if (var3.index == var1) {
			Color var4;
			if (var2) {
				var4 = UIAchieveState.Const.itemPressedColor;
			} else {
				var4 = UIAchieveState.Const.itemNormalColor;
			}

			Drawable var5;
			if (var2) {
				var5 = this.skin.getDrawable("store_button_press");
			} else {
				var5 = this.skin.getDrawable("store_button_normal");
			}

			var3.setBackground(var5);
			Image var6 = (Image) ((Table) var3.getChildren().get(0))
					.getChildren().get(0);
			int var7 = UIAchieveState.Const.achievements[var1].getAchState();
			Drawable var8;
			if (var2) {
				var8 = UIAchieveState.Const.achieveLogoPress[var7];
			} else {
				var8 = UIAchieveState.Const.achieveLogoNormal[var7];
			}

			var6.setDrawable(var8);
			Iterator var9 = ((Group) var3.getChildren().get(1)).getChildren()
					.iterator();

			while (var9.hasNext()) {
				Actor var11 = (Actor) var9.next();
				if (var11 instanceof Label) {
					var11.setColor(var4);
				}

				if (var11 instanceof Image) {
					Drawable var12;
					if (var2) {
						var12 = UIAchieveState.Const.diffStarPress;
					} else {
						var12 = UIAchieveState.Const.diffStarNormal;
					}

					((Image) var11).setDrawable(var12);
				}
			}

			Label var10 = (Label) ((Table) var3.getChildren().get(2))
					.getChildren().get(0);
			var10.setColor(var4);
			var10.setText(STATETEXT[UIAchieveState.Const.achievements[var1].achState]);
			var10.getActions().clear();
			if (UIAchieveState.Const.achievements[var1].achState == 1) {
				var10.addAction(Actions.repeat(-1,
						Actions.sequence(Actions.alpha(1.0F, 0.33333334F,
								Interpolation.pow2Out), Actions.alpha(1.0F,
								0.33333334F), Actions.alpha(0.0F, 0.33333334F,
								Interpolation.pow2In))));
			}

		} else {
			Settings.w("error ocucured");
		}
	}

	private void updateLevelStateItem(int var1) {
		boolean var2;
		if (var1 == this.levelStateSelectedIndex) {
			var2 = true;
		} else {
			var2 = false;
		}

		XTable var3 = (XTable) ((Table) this.levelStatePane.getChildren()
				.get(0)).getChildren().get(var1);
		if (var3.index == var1) {
			Color var4;
			if (var2) {
				var4 = UIAchieveState.Const.itemPressedColor;
			} else {
				var4 = UIAchieveState.Const.itemStateColor;
			}

			Drawable var5;
			if (var2) {
				var5 = this.skin.getDrawable("store_button_press");
			} else {
				var5 = this.skin.getDrawable("store_button_cash");
			}

			var3.setBackground(var5);
			Iterator var6 = ((Group) var3.getChildren().get(1)).getChildren()
					.iterator();

			while (var6.hasNext()) {
				Actor var7 = (Actor) var6.next();
				if (var7 instanceof Label) {
					var7.setColor(var4);
				}

				if (var7 instanceof Image) {
					Drawable var8;
					if (var2) {
						var8 = UIAchieveState.Const.diffStarPress;
					} else {
						var8 = UIAchieveState.Const.diffStarYellow;
					}

					((Image) var7).setDrawable(var8);
				}
			}

			((Actor) ((Table) var3.getChildren().get(2)).getChildren().get(0))
					.setColor(var4);
		} else {
			Settings.w("error ocucured");
		}
	}

	public void backPressed() {
		if (this.backButton.getTouchable() == Touchable.enabled) {
			this.backToMain();
		}

	}

	public void clearSelectedIndex() {
		if (this.achSelectedIndex != -1) {
			int var2 = this.achSelectedIndex;
			this.achSelectedIndex = -1;
			this.updateAchItem(var2);
		}

		if (this.levelStateSelectedIndex != -1) {
			int var1 = this.levelStateSelectedIndex;
			this.levelStateSelectedIndex = -1;
			this.updateLevelStateItem(var1);
		}

	}

	public void switchToThisState(UIScreen var1) {
		super.switchToThisState(var1);
		Group var2 = var1.getStateGroup();
		var1.getUIBottomGroup().addActor(this.backButton);
		var2.addActor(this.levelStatePane);
		var2.addActor(this.achPane);
		var2.addActor(this.paneTile);
		var2.addActor(this.paneFooter);
		var2.addActor(this.getMoneyButton);
		XScreen.getBitmapFont36().setScale(1.0F);
		var2.setPosition(0.0F, -800.0F);
		var2.addAction(Actions.moveTo(0.0F, 0.0F, 0.5F, Interpolation.pow2Out));
		this.switchPane(0);
		this.clearSelectedIndex();
		this.getMoneyButton.setPosition(123.0F, -66.0F);
		this.getMoneyButton.addAction(FontScaleAction.setFontScale0());
		this.backButton.addAction(Actions.sequence(Actions.fadeIn(0.5F),
				Actions.touchable(Touchable.enabled)));
		this.upateTitleMoneyLabel();
		this.updateAchAllItem();
	}

	public void upateTitleMoneyLabel() {
		this.titleMonyeLabel.setText(Settings.money + "");
	}

	public static class LapAchElement extends UIAchieveState.AchElement {

		public LapAchElement(int var1, String var2, String var3, int var4,
				int var5) {
			super(var1, var2, var3, var4, var5);
		}

		public int getRewardMoney() {
			super.getRewardMoney();
			if (this.achState == 2) {
				int var2 = 5000 * (-1 + Settings.lapCount - Settings.rewardLapCount);
				this.achState = 0;
				Settings.rewardLapCount = -1 + Settings.lapCount;
				return var2;
			} else {
				return 0;
			}
		}
	}

	public class achPaneElePressLis extends PressedListener {

		private void moveGetMoneyButton(int var1, int var2) {
			boolean var3;
			if (var1 != -1
					&& UIAchieveState.Const.achievements[var1].achState == 1) {
				var3 = true;
			} else {
				var3 = false;
			}

			boolean var4;
			if (var2 != -1
					&& UIAchieveState.Const.achievements[var2].achState == 1) {
				var4 = true;
			} else {
				var4 = false;
			}

			if (var3 && var4) {
				UIAchieveState.this.moveOutAndIn();
			}

			if (!var3 && var4) {
				UIAchieveState.this.moveMovingButton(true);
			}

			if (var3 && !var4) {
				UIAchieveState.this.moveMovingButton(false);
			}

		}

		public boolean touchDown(InputEvent var1, float var2, float var3,
				int var4, int var5) {
			UIAchieveState.this.screen.playButtonSound();
			if (super.touchDown(var1, var2, var3, var4, var5)) {
				XTable var6 = (XTable) var1.getListenerActor();
				int var7 = UIAchieveState.this.achSelectedIndex;
				if (var6.index != UIAchieveState.this.achSelectedIndex) {
					UIAchieveState.this.achSelectedIndex = var6.index;
					if (var7 >= 0
							&& var7 < UIAchieveState.Const.achievements.length) {
						UIAchieveState.this.updateAchItem(var7);
					}

					UIAchieveState.this
							.updateAchItem(UIAchieveState.this.achSelectedIndex);
					String var10 = UIAchieveState.Const.achievements[UIAchieveState.this.achSelectedIndex]
							.getExplationWord();
					UIAchieveState.this.explation_words.setText(var10);
				} else {
					UIAchieveState.this.achSelectedIndex = -1;
					UIAchieveState.this.updateAchItem(var6.index);
					UIAchieveState.this.explation_words.setText("   ");
				}

				this.moveGetMoneyButton(var7,
						UIAchieveState.this.achSelectedIndex);
				return true;
			} else {
				return false;
			}
		}
	}

	public static class ScoreAchEl extends UIAchieveState.AchElement {

		public ScoreAchEl(int var1, String var2, String var3, int var4, int var5) {
			super(var1, var2, var3, var4, var5);
		}

		public String getExplationWord() {
			return super.getExplationWord() + ".\n<current total score "
					+ (long) Settings.score + ">";
		}
	}

	public static class Const {

		public static final int ACHNAMEX = 18;
		public static final int ACHNAMEY = 40;
		public static final int ACHPANEINUSE = 0;
		public static final int FOOTERHEIGHT = 61;
		public static final int FOOTERWIDTH = 402;
		public static final int FOOTERX = 37;
		public static final int FOOTERY = 276;
		public static final int ITEMLEFTWIDTH = 61;
		public static final int ITEMMIDDLELEFTWIDTH = 56;
		public static final int ITEMMIDDLEWIDTH = 185;
		public static final int LVX = 19;
		public static final int LVY = 5;
		public static final int MOVINGBUTTONENDY = 180;
		public static final float MOVINGBUTTONMOVETIME = 0.2F;
		public static final int MOVINGBUTTONSTARTY = -66;
		public static final int MOVINGBUTTONX = 123;
		public static final int PANEHEIGHT = 366;
		public static final int PANEWIDTH = 401;
		public static final int PANEX = 37;
		public static final int PANEY = 338;
		public static final int STARDELTAX = 21;
		public static final int STARINITX = 70;
		public static final int STARY = 10;
		public static final int STATPANEINUSE = 1;
		public static final int TITLEHEIGHT = 45;
		public static final int TITLEPOSITIONX = 35;
		public static final int TITLEPOSITIONY = 704;
		public static final int TITLEWIDTH = 403;
		public static final Drawable[] achieveLogoNormal = new Drawable[] {
				null, null, null };
		public static final Drawable[] achieveLogoPress = new Drawable[] {
				null, null, null };
		protected static UIAchieveState.AchElement[] achievements;
		public static Drawable diffStarNormal = null;
		public static Drawable diffStarPress = null;
		public static Drawable diffStarYellow = null;
		public static final Color itemNormalColor = new Color(0.18039216F,
				0.6431373F, 1.0F, 1.0F);
		public static final Color itemPressedColor = new Color(0.4745098F,
				0.91764706F, 0.0F, 1.0F);
		public static final Color itemStateColor = new Color(0.91764706F,
				0.62352943F, 0.0F, 1.0F);
		public static UIAchieveState.AchElement[] levelAchievements;
		public static final Drawable[] titleDrawables = new Drawable[] { null,
				null };
		public static Label.LabelStyle titleLabelDownStyles;
		public static Label.LabelStyle titleLabelUpStyles;

		static {
			UIAchieveState.AchElement[] var0 = new UIAchieveState.AchElement[] {
					new UIAchieveState.AchElement(0, "Nice Show",
							"destroy 100 enemies", 1, 100),
					new UIAchieveState.AchElement(0, "Great Power",
							"destroy 1000 enemies", 2, 200),
					new UIAchieveState.AchElement(0, "Lord of space",
							"destroy 10000 enemies", 3, 500),
					new UIAchieveState.AchElement(0, "God of War",
							"destroy 999999 enemies", 4, 1000),
					new UIAchieveState.AchElement(0, "First Show",
							"Complete mission 1", 1, 100),
					new UIAchieveState.AchElement(0, "Earth guardian",
							"Complete mission 2", 1, 100),
					new UIAchieveState.AchElement(0, "No mercy",
							"Complete mission 3", 2, 200),
					new UIAchieveState.AchElement(0, "Leave The Moon",
							"Complete mission 4", 2, 200),
					new UIAchieveState.AchElement(0, "SPACE Battle",
							"Complete mission 5", 3, 500),
					new UIAchieveState.AchElement(0, "Dawn time",
							"Complete mission 6", 3, 500),
					new UIAchieveState.AchElement(0, "The very day",
							"Complete mission 7", 3, 500),
					new UIAchieveState.AchElement(0, "Curiosity",
							"upgrade all kinds of items", 3, 500),
					new UIAchieveState.AchElement(0, "Explorer",
							"upgrade one item to max level", 3, 500),
					new UIAchieveState.AchElement(0, "Money Speaks",
							"upgrade every item to max level", 5, 2000),
					new UIAchieveState.AchElement(0, "Collector",
							"Unlock all planes", 2, 200),
					new UIAchieveState.AchElement(0, "The Croesus",
							"Collect 1000000 coins", 4, 1000),
					new UIAchieveState.AchElement(0, "I am Strong",
							"complete a mission in one life", 4, 1000),
					new UIAchieveState.AchElement(0, "No Items",
							"complete a mission and avoid any item in it", 4,
							1000),
					new UIAchieveState.AchElement(0, "sweep all",
							"destroy every enemy in a mission", 4, 1000),
					new UIAchieveState.AchElement(0, "Unbelievable",
							"complete all missions in one life", 5, 2000),
					new UIAchieveState.AchElement(0, "Quick Finish",
							"destroy a boss within 20 seconds", 4, 1000),
					new UIAchieveState.AchElement(0, "BOSS Killer",
							"destroy all seven bosses", 3, 500),
					new UIAchieveState.AchElement(0, "Lucky Person",
							"collect more than 1000 coins in one mission", 2,
							200),
					new UIAchieveState.AchElement(0, "Lots of bombs",
							"Use 1000 bombs", 3, 500),
					new UIAchieveState.AchElement(0, "Toughest",
							"Crash 300 times", 3, 500),
					new UIAchieveState.ScoreAchEl(0, "Corporal",
							"total score reaches 50000", 1, 100),
					new UIAchieveState.ScoreAchEl(0, "Captain",
							"total score reaches 900000", 3, 500),
					new UIAchieveState.ScoreAchEl(0, "Colonel",
							"total score reaches 2000000", 4, 1000),
					new UIAchieveState.ScoreAchEl(0, "Ace Genneral",
							"total score reaches 5000000", 5, 2000),
					new UIAchieveState.LapAchElement(0, "Cycle hero",
							"finish current cycle", 5, 0) };
			achievements = var0;
			UIAchieveState.AchElement[] var1 = new UIAchieveState.AchElement[] {
					new UIAchieveState.AchElement(0, "MISSION 1", "mission 1",
							1, 0),
					new UIAchieveState.AchElement(0, "MISSION 2", "mission 2",
							1, 0),
					new UIAchieveState.AchElement(0, "MISSION 3", "mission 3",
							2, 0),
					new UIAchieveState.AchElement(0, "MISSION 4", "mission 4",
							3, 0),
					new UIAchieveState.AchElement(0, "MISSION 5", "mission 5",
							4, 0),
					new UIAchieveState.AchElement(0, "MISSION 6", "mission 6",
							5, 0),
					new UIAchieveState.AchElement(0, "MISSION 7", "mission 7",
							5, 0) };
			levelAchievements = var1;
		}

		public static void checkAchUnlock() {
			if (Settings.enemyKilledCount >= 100L) {
				unLockAch(0);
			}

			if (Settings.enemyKilledCount >= 1000L) {
				unLockAch(1);
			}

			if (Settings.enemyKilledCount >= 10000L) {
				unLockAch(2);
			}

			if (Settings.enemyKilledCount >= 999999L) {
				unLockAch(3);
			}

			if (Settings.levelPassed >= 1) {
				unLockAch(4);
			}

			if (Settings.levelPassed >= 2) {
				unLockAch(5);
			}

			if (Settings.levelPassed >= 3) {
				unLockAch(6);
			}

			if (Settings.levelPassed >= 4) {
				unLockAch(7);
			}

			if (Settings.levelPassed >= 5) {
				unLockAch(8);
			}

			if (Settings.levelPassed >= 6) {
				unLockAch(9);
			}

			if (Settings.levelPassed >= 7) {
				unLockAch(10);
			}

			if (checkAllBuyLevel(1)) {
				unLockAch(11);
			}

			if (checkExistMaxBuy()) {
				unLockAch(12);
			}

			if (checkAllBuyLevel(5)) {
				unLockAch(13);
			}

			if (Settings.unlockFighter == 6) {
				unLockAch(14);
			}

			if (Settings.money > 1000000L) {
				unLockAch(15);
			}

			if (checkPassLevelOneLife()) {
				unLockAch(16);
			}

			if (Settings.passLevelWithOutProp) {
				unLockAch(17);
			}

			if (Settings.killAllEenmyInOneLevel) {
				unLockAch(18);
			}

			if (checkPassAllLevelOneLife()) {
				unLockAch(19);
			}

			if (checkQuickKill(20.0F)) {
				unLockAch(20);
			}

			if (checkKillAllBoss()) {
				unLockAch(21);
			}

			if (Settings.levelMoney > 1000L) {
				unLockAch(22);
			}

			if (Settings.boomCount > 1000L) {
				unLockAch(23);
			}

			if (Settings.fighterCrashCount > 300L) {
				unLockAch(24);
			}

			if (Settings.score > 50000.0D) {
				unLockAch(25);
			}

			if (Settings.score > 900000.0D) {
				unLockAch(26);
			}

			if (Settings.score > 2000000.0D) {
				unLockAch(27);
			}

			if (Settings.score > 5000000.0D) {
				unLockAch(28);
			}

			if (UIAchieveState.checkLapAchUnlock()) {
				unLockAch(29);
			}

		}

		private static boolean checkAllBuyLevel(int var0) {
			return Settings.boomEffectLevel >= var0
					&& Settings.missleEffectLevel >= var0
					&& Settings.megaEffectLevel >= var0
					&& Settings.wingEffectLevel >= var0
					&& Settings.lifeEffectLevel >= var0
					&& Settings.fireEffectLevel >= var0
					&& Settings.itemDropLevel >= var0
					&& Settings.coinDropLevel >= var0;
		}

		private static boolean checkExistMaxBuy() {
			return Settings.boomEffectLevel == 5
					|| Settings.missleEffectLevel == 5
					|| Settings.megaEffectLevel == 5
					|| Settings.wingEffectLevel == 5
					|| Settings.lifeEffectLevel == 5
					|| Settings.fireEffectLevel == 5
					|| Settings.itemDropLevel == 5
					|| Settings.coinDropLevel == 5;
		}

		private static boolean checkKillAllBoss() {
			return Settings.levelPassed >= 7;
		}

		private static boolean checkPassAllLevelOneLife() {
			for (int var0 = 0; var0 < Settings.passLevelOneLife.length; ++var0) {
				if (!Settings.passLevelOneLife[var0]) {
					return false;
				}
			}

			return true;
		}

		private static boolean checkPassLevelOneLife() {
			for (int var0 = 0; var0 < Settings.passLevelOneLife.length; ++var0) {
				if (Settings.passLevelOneLife[var0]) {
					return true;
				}
			}

			return false;
		}

		private static boolean checkQuickKill(float var0) {
			for (int var1 = 0; var1 < Settings.minTimeKillBoss.length; ++var1) {
				if (Settings.minTimeKillBoss[var1] < var0) {
					return true;
				}
			}

			return false;
		}

		public static void loadAch(Preferences var0) {
			for (int var1 = 0; var1 < achievements.length; ++var1) {
				achievements[var1].achState = var0.getInteger(
						achievements[var1].achName, 0);
			}

		}

		public static void saveAch(Preferences var0, boolean var1) {
			for (int var2 = 0; var2 < achievements.length; ++var2) {
				var0.putInteger(achievements[var2].achName,
						achievements[var2].achState);
			}

			if (var1) {
				var0.flush();
			}

		}

		private static void unLockAch(int var0) {
			if (achievements[var0].achState == 0) {
				if (achievements[var0].achName.equalsIgnoreCase("Cycle hero")) {
					Settings.flurryLog(achievements[var0].achName);
				}

				achievements[var0].achState = 1;
			}

		}
	}

	private static class AchElement {

		public String achName = "achName";
		protected int achState = 0;
		public int difficulty = 4;
		private String explationWords = "this is a achName achievement.";
		int rewardMoney = 10;

		public AchElement() {
		}

		public AchElement(int var1, String var2, String var3, int var4, int var5) {
			this.achState = var1;
			this.achName = var2;
			this.explationWords = var3;
			this.difficulty = var4;
			this.rewardMoney = var5;
		}

		public int getAchState() {
			if (this.achState == 0) {
				;
			}

			return this.achState;
		}

		public String getExplationWord() {
			return this.explationWords;
		}

		public int getRewardMoney() {
			if (this.getAchState() != 1) {
				return 0;
			} else {
				this.achState = 2;
				return this.rewardMoney;
			}
		}
	}

	public class staPaneElePressLis extends PressedListener {

		public boolean touchDown(InputEvent var1, float var2, float var3,
				int var4, int var5) {
			UIAchieveState.this.screen.playButtonSound();
			if (super.touchDown(var1, var2, var3, var4, var5)) {
				XTable var6 = (XTable) var1.getListenerActor();
				int var7 = UIAchieveState.this.levelStateSelectedIndex;
				if (var6.index != UIAchieveState.this.levelStateSelectedIndex) {
					UIAchieveState.this.levelStateSelectedIndex = var6.index;
					if (var7 >= 0
							&& var7 < UIAchieveState.Const.levelAchievements.length) {
						UIAchieveState.this.updateLevelStateItem(var7);
					}

					XScreen.getBitmapFont48().setScale(1.0F);
					UIAchieveState.this
							.updateLevelStateItem(UIAchieveState.this.levelStateSelectedIndex);
					UIAchieveState.this.explation_words
							.setText(UIAchieveState.Const.levelAchievements[UIAchieveState.this.levelStateSelectedIndex]
									.getExplationWord());
				} else {
					UIAchieveState.this.levelStateSelectedIndex = -1;
					UIAchieveState.this.updateLevelStateItem(var6.index);
					UIAchieveState.this.explation_words.setText("   ");
				}

				return true;
			} else {
				return false;
			}
		}
	}
}
