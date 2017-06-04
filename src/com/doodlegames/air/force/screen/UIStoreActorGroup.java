package com.doodlegames.air.force.screen;

import java.text.DecimalFormat;
import java.util.Iterator;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.doodlegames.air.force.NativeService;
import com.doodlegames.air.force.XGame;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.screen.action.FontScaleAction;
import com.doodlegames.air.force.screen.action.SetTextAction;
import com.doodlegames.air.force.screen.screenActor.XTable;
import com.doodlegames.air.force.utils.Settings;

public class UIStoreActorGroup {

	public static final String ITEM1 = "com.sky.force.coin1";
	public static final String ITEM2 = "com.sky.force.coin2";
	public static final String ITEM3 = "com.sky.force.coin3";
	public static final String ITEM4 = "com.sky.force.coin4";
	public static final String ITEM5 = "com.sky.force.coin5";
	public static final String ITEM6 = "com.sky.force.coin6";

	public static final String EQUIPPEDSTR = "equipped";
	private static UIStoreActorGroup.UpdateUI updateUi = new UIStoreActorGroup.UpdateUI();
	private String MoveButtonSelectedText;
	protected UIStoreActorGroup.StoreItem[] coinItems;
	public ScrollPane coinPane;
	private int coinPaneSelectedIndex;
	public ScrollPane dollarPane;
	private int dollarPaneSelectedIndex;
	public Label explation_words;
	private UIStoreActorGroup.ItemSelected itemSelectedFuns;
	public Table levelItemTable;
	private UIStoreActorGroup.MovingButtonInterface moveButtonInterface;
	private final int moveButtonX;
	public TextButton movingButton;
	public Table onceItemTable;
	public Table paneFooter;
	public int paneInUse;
	public Table paneTile;
	Skin skin;
	protected UIStoreActorGroup.StoreItem[] subLevelItems;
	public Label titleCashLabel;
	public Label titleMoneyLabel;
	public Label titleWeaponLabel;

	public UIStoreActorGroup(Skin var1) {
		this(var1, 0);
	}

	public UIStoreActorGroup(Skin var1, int var2) {
		this.paneInUse = 0;
		this.coinPaneSelectedIndex = -1;
		this.dollarPaneSelectedIndex = -1;
		this.MoveButtonSelectedText = "equip";
		this.coinItems = UIStoreActorGroup.Const.onceItems;
		this.subLevelItems = null;
		this.moveButtonInterface = null;
		this.itemSelectedFuns = null;
		this.skin = var1;
		byte var3;
		if (var2 == 0) {
			var3 = 123;
		} else {
			var3 = 38;
		}

		this.moveButtonX = var3;
		this.constructTile(var2);
		this.constructCoinPane(var2);
		this.constructDollarPane();
		this.constructFooter();
		this.constructMovingButton();
	}

	public static void addResource(Skin var0, TextureAtlas var1) {
		UIStoreActorGroup.Const.boomDrawable = var0.newDrawable("boom");
		UIStoreActorGroup.Const.megaDrawable = var0.newDrawable("magnet");
		UIStoreActorGroup.Const.wingManDrawable = var0.newDrawable("wingman");
		UIStoreActorGroup.Const.lifeDrawable = var0.newDrawable("player");
		UIStoreActorGroup.Const.gunPowerDrawable = var0
				.getDrawable("gun_power");
		UIStoreActorGroup.Const.missleDrawable = var0.newDrawable("missile");
		UIStoreActorGroup.Const.luckDrawable = var0.newDrawable("luck");
		UIStoreActorGroup.Const.diceDrawable = var0.newDrawable("dice");
		UIStoreActorGroup.Const.redBulletDrawable = new SpriteDrawable(
				new TextureAtlas.AtlasSprite(var1.findRegion("gun", 0)));
		UIStoreActorGroup.Const.yellowBulletDrawable = new SpriteDrawable(
				new TextureAtlas.AtlasSprite(var1.findRegion("gun", 1)));
		UIStoreActorGroup.Const.blueBulletDrawable = new SpriteDrawable(
				new TextureAtlas.AtlasSprite(var1.findRegion("gun", 2)));
		UIStoreActorGroup.Const.getallDrawable = var0
				.newDrawable("mercilessly");
		UIStoreActorGroup.Const.levelOpenNormal = var0
				.newDrawable("level_open_normal");
		UIStoreActorGroup.Const.levelOpenPressed = var0
				.newDrawable("level_open_press");
		UIStoreActorGroup.Const.levelClosed = var0.newDrawable("level_close");
		UIStoreActorGroup.Const.cashLogo[0] = var0.newDrawable("199");
		UIStoreActorGroup.Const.cashLogo[1] = var0.newDrawable("499");
		UIStoreActorGroup.Const.cashLogo[2] = var0.newDrawable("999");
		UIStoreActorGroup.Const.cashLogo[3] = var0.newDrawable("1999");
		UIStoreActorGroup.Const.cashLogo[4] = var0.newDrawable("4999");
		UIStoreActorGroup.Const.cashLogo[5] = var0.newDrawable("9999");
		UIStoreActorGroup.Const.coinDrawableNormal = var0
				.newDrawable("cash_logo_normal");
		UIStoreActorGroup.Const.coinDrawablePress = var0
				.newDrawable("cash_logo_press");
		UIStoreActorGroup.Const.adFreeDrawable = var0.newDrawable("ADfree");
		UIStoreActorGroup.Const.moneyLogoNormal = var0
				.newDrawable("money_logo_normal");
		UIStoreActorGroup.Const.moneyLogoPress = var0
				.newDrawable("money_logo_press");
		UIAchieveState.Const.titleDrawables[0] = new SpriteDrawable(
				new TextureAtlas.AtlasSprite(var1.findRegion("tag", 0)));
		UIAchieveState.Const.titleDrawables[1] = new SpriteDrawable(
				new TextureAtlas.AtlasSprite(var1.findRegion("tag", 1)));
		TextButton.TextButtonStyle var2 = new TextButton.TextButtonStyle();
		var2.font = XScreen.getBitmapFont36();
		var2.down = var0.getDrawable("green_button");
		var2.up = var0.getDrawable("green_button");
		var2.downFontColor = UIMainMenuState.MainConst.WHITEFONT;
		var2.fontColor = UIMainMenuState.MainConst.GREENFONT;
		var0.add("movingButtonStyle", var2);
		Label.LabelStyle var3 = new Label.LabelStyle();
		var3.font = XScreen.getBitmapFont18();
		var3.fontColor = new Color(0.21960784F, 0.6431373F, 1.0F, 1.0F);
		var0.add("LabelNormalStyle", var3);
		Label.LabelStyle var4 = new Label.LabelStyle();
		var4.font = XScreen.getBitmapFont24();
		var4.fontColor = new Color(0.21960784F, 0.6431373F, 1.0F, 1.0F);
		var0.add("font24", var4);
		Label.LabelStyle var5 = new Label.LabelStyle();
		var5.font = XScreen.getBitmapFont18();
		var5.fontColor = new Color(0.68235296F, 0.68235296F, 0.68235296F, 1.0F);
		var0.add("LabelDisableStyle", var5);
		ScrollPane.ScrollPaneStyle var6 = new ScrollPane.ScrollPaneStyle();
		var0.add("white", XScreen.getTexture());
		var6.background = var0.newDrawable("white", new Color(0.050980393F,
				0.09803922F, 0.09019608F, 0.8F));
		var6.vScroll = var0.newDrawable("white", new Color(0.050980393F,
				0.09803922F, 0.09019608F, 1.0F));
		var6.vScrollKnob = var0.newDrawable("white", new Color(0.21960784F,
				0.6431373F, 1.0F, 1.0F));
		((SpriteDrawable) var6.vScrollKnob).setMinWidth(2.0F);
		var0.add("default", var6);
	}

	private void clearCoinPaneSelectedIndex() {
		if (this.coinPaneSelectedIndex != -1) {
			int var1 = this.coinPaneSelectedIndex;
			this.coinPaneSelectedIndex = -1;
			this.updateStoreItem(var1);
		}

	}

	private void clearDollarPaneSelectedIndex() {
		if (this.dollarPaneSelectedIndex != -1) {
			int var1 = this.dollarPaneSelectedIndex;
			this.dollarPaneSelectedIndex = -1;
			this.updateCashItem(var1);
		}

	}

	private void constructCoinPane(int var1) {
		int[] var2 = new int[Math.max(
				UIStoreActorGroup.Const.levelItems.length,
				UIStoreActorGroup.Const.onceItems.length)];

		for (int var3 = 0; var3 < var2.length; ++var3) {
			var2[var3] = -1;
		}

		int var4 = 0;

		for (int var5 = 0; var5 < UIStoreActorGroup.Const.levelItems.length; ++var5) {
			if (UIStoreActorGroup.Const.levelItems[var5].canSell()) {
				int var9 = var4 + 1;
				var2[var4] = var5;
				var4 = var9;
			}
		}

		this.subLevelItems = new UIStoreActorGroup.StoreItem[var4];

		for (int var6 = 0; var6 < var4; ++var6) {
			this.subLevelItems[var6] = UIStoreActorGroup.Const.levelItems[var2[var6]];
		}

		UIStoreActorGroup.StoreItem[] var7;
		if (var1 == 0) {
			var7 = this.subLevelItems;
		} else {
			var7 = UIStoreActorGroup.Const.onceItems;
		}

		this.coinItems = var7;
		this.levelItemTable = this.consturctLevelItem(this.subLevelItems, var1);
		this.onceItemTable = this.consturctLevelItem(
				UIStoreActorGroup.Const.onceItems, var1);
		Table var8;
		if (var1 == 0) {
			var8 = this.levelItemTable;
		} else {
			var8 = this.onceItemTable;
		}

		this.coinPane = new ScrollPane(var8, this.skin);
		this.coinPane.setPosition(37.0F, 338.0F);
		this.coinPane.setFadeScrollBars(false);
		this.coinPane.setScrollingDisabled(true, false);
		this.coinPane.setHeight(366.0F);
		this.coinPane.setWidth(401.0F);
		this.coinPane.invalidate();
		this.coinPane.validate();
	}

	private void constructDollarPane() {
		Table var1 = new Table();
		int var2 = UIStoreActorGroup.Const.cashLogo.length;

		for (int var3 = 0; var3 < var2; ++var3) {
			XTable var4 = new XTable(var3, this.skin);
			fillWithDollarItem(this.skin, var4,
					UIStoreActorGroup.Const.cashLogo[var3],
					UIStoreActorGroup.Const.cashName[var3],
					UIStoreActorGroup.Const.coinGet[var3],
					UIStoreActorGroup.Const.adFree[var3],
					UIStoreActorGroup.Const.itemPrice[var3]);
			var4.addListener(new UIStoreActorGroup.dollarPaneItemPressLis());
			if (var3 == 0) {
				var1.add((Actor) var4).space(4.0F, 0.0F, 4.0F, 0.0F)
						.padTop(4.0F);
			} else {
				var1.add((Actor) var4).space(4.0F, 0.0F, 4.0F, 0.0F);
			}

			var1.row();
		}

		this.dollarPane = new ScrollPane(var1, this.skin);
		this.dollarPane.setPosition(37.0F, 277.0F);
		this.dollarPane.setFadeScrollBars(false);
		this.dollarPane.setScrollingDisabled(true, false);
		this.dollarPane.setHeight(427.0F);
		this.dollarPane.setWidth(401.0F);
		this.dollarPane.invalidate();
		this.dollarPane.validate();
	}

	private void constructFooter() {
		this.paneFooter = new Table();
		this.paneFooter.setBounds(37.0F, 276.0F, 402.0F, 61.0F);
		this.paneFooter.setBackground(this.skin.getDrawable("explanation_bar"));
		this.paneFooter.setTouchable(Touchable.disabled);
		this.explation_words = new Label(" ", this.skin, "LabelNormalStyle");
		this.explation_words
				.setColor(this.explation_words.getStyle().fontColor);
		this.explation_words.setWrap(true);
		this.explation_words.setAlignment(10);
		this.paneFooter.left().top().add((Actor) this.explation_words)
				.minWidth(402.0F).fill().padLeft(15.0F).padTop(8.0F);
	}

	private void constructMovingButton() {
		// this.movingButton = new Image(XGame.btnMis7);
		this.movingButton = new TextButton(this.MoveButtonSelectedText,
				this.skin, "movingButtonStyle")
		// {
		// public void draw(com.badlogic.gdx.graphics.g2d.SpriteBatch batch,
		// float parentAlpha) {
		// batch.draw(XGame.btnMis7, getX(), getY());
		// };
		;
		movingButton.setBackground(XGame.btnMis7);
		this.movingButton.setPosition((float) this.moveButtonX, 180.0F);
		this.movingButton.addListener(new ChangeListener() {
			public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
				if (UIStoreActorGroup.this.movingButton.getTouchable() == Touchable.enabled) {
					UIStoreActorGroup.this.moveButtonInterface
							.playButtonSound();
					if (UIStoreActorGroup.this.paneInUse == 0) {
						UIStoreActorGroup.this
								.payForCoinItem(UIStoreActorGroup.this.coinPaneSelectedIndex);
					}

					if (UIStoreActorGroup.this.paneInUse == 1) {
						UIStoreActorGroup.this
								.payForCashItem(UIStoreActorGroup.this.dollarPaneSelectedIndex);
					}

					int var3;
					if (UIStoreActorGroup.this.paneInUse == 0) {
						var3 = UIStoreActorGroup.this.coinPaneSelectedIndex;
					} else {
						var3 = UIStoreActorGroup.this.dollarPaneSelectedIndex;
					}

					UIStoreActorGroup.this.moveButtonInterface.buttonClicked(
							UIStoreActorGroup.this.paneInUse, var3);
				}
			}
		});
	}

	private void constructTile(int var1) {
		String var2;
		if (var1 == 0) {
			var2 = "upgrade";
		} else {
			var2 = "item";
		}

		this.titleWeaponLabel = new Label(var2, this.skin, "LabelNormalStyle");
		this.titleWeaponLabel
				.setColor(this.titleWeaponLabel.getStyle().fontColor);
		this.titleCashLabel = new Label("CASHBOX", this.skin,
				"LabelDisableStyle");
		this.titleCashLabel.setColor(this.titleCashLabel.getStyle().fontColor);
		this.titleMoneyLabel = new Label("" + Settings.money,
				new Label.LabelStyle(XScreen.getBitmapFont30(),
						UIAchieveState.Const.itemStateColor));
		this.titleMoneyLabel.setColor(UIAchieveState.Const.itemStateColor);
		this.paneTile = new Table(this.skin);
		this.paneTile.left();
		this.paneTile
				.setBackground(UIAchieveState.Const.titleDrawables[this.paneInUse]);
		this.paneTile.setPosition(35.0F, 704.0F);
		this.paneTile.add((Actor) this.titleWeaponLabel)
				.width(this.titleWeaponLabel.getWidth()).minWidth(112.0F);
		this.paneTile.add((Actor) this.titleCashLabel)
				.width(this.titleCashLabel.getWidth()).minWidth(112.0F);
		this.paneTile.add((Actor) this.titleMoneyLabel).expand().bottom()
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

					if (UIStoreActorGroup.this.switchPane(var4)
							&& UIStoreActorGroup.this.itemSelectedFuns != null) {
						int var5;
						if (UIStoreActorGroup.this.paneInUse == 1) {
							var5 = UIStoreActorGroup.this.dollarPaneSelectedIndex;
						} else {
							var5 = UIStoreActorGroup.this.coinPaneSelectedIndex;
						}

						UIStoreActorGroup.this.itemSelectedFuns.switchPane(
								UIStoreActorGroup.this.paneInUse, var5);
						return;
					}
				}

			}
		});
		this.paneTile.addAction(new Action() {
			public boolean act(float var1) {
				BitmapFont var2 = UIStoreActorGroup.this.titleMoneyLabel
						.getStyle().font;
				if (var2.getScaleY() != 1.0F) {
					var2.setScale(1.0F);
					UIStoreActorGroup.this.titleMoneyLabel.layout();
					UIStoreActorGroup.this.paneTile.layout();
				}

				return false;
			}
		});
	}

	private Table consturctLevelItem(UIStoreActorGroup.StoreItem[] var1,
			int var2) {
		Table var3 = new Table();
		int var4 = var1.length;

		for (int var5 = 0; var5 < var4; ++var5) {
			UIStoreActorGroup.StoreItem var6 = var1[var5];
			XTable var7 = new XTable(var5, this.skin);
			var6.refreshData();
			if (var2 == 0) {
				fillCoinStoreItem(this.skin, var7, var6);
			} else {
				fillOnceCoinStoreItem(this.skin, var7, var6);
			}

			var7.addListener(new UIStoreActorGroup.storePaneItemPressLis());
			if (var5 == 0) {
				var3.add((Actor) var7).space(6.0F, 0.0F, 6.0F, 0.0F)
						.padTop(6.0F);
			} else {
				var3.add((Actor) var7).space(6.0F, 0.0F, 6.0F, 0.0F);
			}

			var3.row();
		}

		return var3;
	}

	public static void fillCoinStoreItem(Skin var0, XTable var1,
			UIStoreActorGroup.StoreItem var2) {
		var1.setBackground(var0.getDrawable("store_button_normal"));
		var1.setTouchable(Touchable.enabled);
		var1.left();
		Table var4 = new Table();
		var4.add((Actor) (new Image(var2.getLogoDrawable()))).expand();
		var1.add(var4).width(61.0F).expandY().fillY();
		Group var7 = new Group();
		Label var8 = new Label(var2.itemName, var0, "font24");
		var8.setColor(UIAchieveState.Const.itemNormalColor);
		var8.setPosition(18.0F, 36.0F);
		var7.addActor(var8);
		Label var9 = new Label("LV", var0, "font24");
		var9.setColor(UIAchieveState.Const.itemNormalColor);
		var9.setPosition(19.0F, 5.0F);
		var7.addActor(var9);
		int var10 = var2.levelMax;

		for (int var11 = 1; var11 <= var10; ++var11) {
			Image var12 = new Image(UIStoreActorGroup.Const.levelOpenNormal);
			if (var11 > var2.currentLevel) {
				var12.setDrawable(UIStoreActorGroup.Const.levelClosed);
			}

			var12.setPosition((float) (70 + 21 * (var11 - 1)), 10.0F);
			var7.addActor(var12);
		}

		var1.add(var7).width(185.0F).fillY();
		Table var14 = new Table();
		String var15 = (int) var2.price + "";
		if (Math.abs(var2.price) < 0.001D) {
			var15 = "max";
		}

		Label var16 = new Label(var15,
				new Label.LabelStyle(XScreen.getBitmapFont48(),
						UIAchieveState.Const.itemNormalColor));
		var16.setColor(UIAchieveState.Const.itemNormalColor);
		var14.add((Actor) var16).padRight(15.0F);
		var1.add(var14).expandX().right();
	}

	public static void fillOnceCoinStoreItem(Skin var0, XTable var1,
			UIStoreActorGroup.StoreItem var2) {
		var1.setBackground(var0.getDrawable("store_button_normal"));
		var1.setTouchable(Touchable.enabled);
		var1.left();
		Table var4 = new Table();
		var4.add((Actor) (new Image(var2.getLogoDrawable()))).expand();
		var1.add(var4).width(61.0F).expandY().fillY();
		Group var7 = new Group();
		Label var8 = new Label(var2.itemName, var0, "font24");
		var8.setColor(UIAchieveState.Const.itemNormalColor);
		var8.setPosition(18.0F, 36.0F);
		var7.addActor(var8);
		int var9 = var2.levelMax;

		for (int var10 = 1; var10 <= var9; ++var10) {
			Image var11 = new Image(UIStoreActorGroup.Const.levelOpenNormal);
			if (var10 > var2.currentLevel) {
				var11.setDrawable(UIStoreActorGroup.Const.levelClosed);
			}

			var11.setPosition((float) (19 + 21 * (var10 - 1)), 10.0F);
			var7.addActor(var11);
		}

		String var12;
		if (var2.currentLevel == 0) {
			var12 = "  ";
		} else {
			var12 = "equipped";
		}

		Label var13 = new Label(var12, var0, "font24");
		var13.setColor(UIAchieveState.Const.itemNormalColor);
		var13.setPosition(70.0F, 7.0F);
		var7.addActor(var13);
		var1.add(var7).width(185.0F).fillY();
		Table var15 = new Table();
		String var16 = (int) var2.price + "";
		if (Math.abs(var2.price) < 0.001D) {
			var16 = "max";
		}

		Label var17 = new Label(var16,
				new Label.LabelStyle(XScreen.getBitmapFont48(),
						UIAchieveState.Const.itemNormalColor));
		var17.setColor(UIAchieveState.Const.itemNormalColor);
		var15.add((Actor) var17).padRight(15.0F);
		var1.add(var15).expandX().right();
	}

	public static void fillWithDollarItem(Skin var0, XTable var1,
			Drawable var2, String var3, int var4, boolean var5, double var6) {
		var1.setBackground(var0.getDrawable("store_button_cash"));
		var1.setTouchable(Touchable.enabled);
		var1.left();
		Table var9 = new Table();
		var9.add((Actor) (new Image(var2))).expand();
		var1.add(var9).width(61.0F).expandY().fillY();
		Group var12 = new Group();
		Label var13 = new Label(var3, var0, "font24");
		var13.setColor(UIAchieveState.Const.itemStateColor);
		var13.setPosition(18.0F, 36.0F);
		var12.addActor(var13);
		Image var14 = new Image(UIStoreActorGroup.Const.coinDrawableNormal);
		var14.setPosition(19.0F, 9.0F);
		var12.addActor(var14);
		Label var15 = new Label("X " + var4, var0, "font24");
		var15.setColor(UIAchieveState.Const.itemStateColor);
		var15.setPosition(73.0F, 6.0F);
		var12.addActor(var15);
		if (var5) {
			Image var16 = new Image(UIStoreActorGroup.Const.adFreeDrawable);
			var16.setPosition(133.0F, 37.0F);
			var12.addActor(var16);
		}

		var1.add(var12).width(185.0F).fillY();
		Table var18 = new Table();
		Image var19 = new Image(UIStoreActorGroup.Const.moneyLogoNormal);
		var18.left().add((Actor) var19).padLeft(15.0F);
		DecimalFormat df = new DecimalFormat("0.00");
		Label var21 = new Label(String.valueOf(var6 / 100),
				new Label.LabelStyle(XScreen.getBitmapFont36(),
						UIAchieveState.Const.itemStateColor));
		var21.setColor(UIAchieveState.Const.itemStateColor);
		var18.add((Actor) var21).expandX().right().padRight(20.0F);
		var1.add(var18).expandX().fillX();
	}

	private void payForCashItem(int var1) {
		if (var1 >= 0 && var1 < UIStoreActorGroup.Const.cashName.length) {
			if (Gdx.app.getType() == Application.ApplicationType.Android) {
				updateUi.moneyLabel = this.titleMoneyLabel;
				// DoodleGame.updateUI = updateUi;
				Gdx.app.log("DollarIndex is ", String.valueOf(var1));
				if (Gdx.app.getType() == ApplicationType.Android)
					switch (var1) {
					case 0:
						((NativeService) Gdx.app).request(ITEM1);
						break;
					case 1:
						((NativeService) Gdx.app).request(ITEM2);
						break;
					case 2:
						((NativeService) Gdx.app).request(ITEM3);
						break;
					case 3:
						((NativeService) Gdx.app).request(ITEM4);
						break;
					case 4:
						((NativeService) Gdx.app).request(ITEM5);
						break;
					case 5:
						((NativeService) Gdx.app).request(ITEM6);
						break;

					}
			}

			if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
				Settings.money += (long) UIStoreActorGroup.Const.coinGet[var1];
			}
		}

	}

	private void payForCoinItem(int var1) {
		if (var1 >= 0 && var1 < this.coinItems.length) {
			UIStoreActorGroup.StoreItem var2 = this.coinItems[var1];
			if (var2.currentLevel >= 5) {
				return;
			}

			if ((double) Settings.money > var2.price) {
				if (var2.currentLevel < var2.levelMax) {
					if (var2.price > 3000.0D) {
						Settings.flurryLog("itemPayment", "ITEMNAME",
								var2.itemName, "ITEMLEVEL", var2.currentLevel
										+ "");
					}

					var2.payForStoreItem();
					this.updateStoreItem(var1);
					Settings.save();
					if (var2.currentLevel == var2.levelMax) {
						this.clearCoinPaneSelectedIndex();
						this.moveMovingButtonOut(0.0F);
					}
				}
			} else if (var2.currentLevel < var2.levelMax) {
				this.switchPane(1);
			}
		}

		this.updateTitleMoneyLabel();
	}

	private void updateCashItem(int var1) {
		boolean var2;
		if (var1 == this.dollarPaneSelectedIndex) {
			var2 = true;
		} else {
			var2 = false;
		}

		Table var3 = (Table) this.dollarPane.getChildren().get(0);
		XTable var4 = (XTable) var3.getChildren().get(var1);
		if (var4.index == var1) {
			Color var5;
			if (var2) {
				var5 = UIAchieveState.Const.itemPressedColor;
			} else {
				var5 = UIAchieveState.Const.itemStateColor;
			}

			Drawable var6;
			if (var2) {
				var6 = this.skin.getDrawable("store_button_press");
			} else {
				var6 = this.skin.getDrawable("store_button_cash");
			}

			var4.setBackground(var6);
			Iterator var7 = ((Group) var4.getChildren().get(1)).getChildren()
					.iterator();

			while (var7.hasNext()) {
				Actor var11 = (Actor) var7.next();
				if (var11 instanceof Label) {
					var11.setColor(var5);
				}

				if (var11 instanceof Image) {
					Image var12 = (Image) var11;
					Drawable var13;
					if (var2) {
						var13 = UIStoreActorGroup.Const.coinDrawablePress;
					} else {
						var13 = UIStoreActorGroup.Const.coinDrawableNormal;
					}

					Drawable var14 = var12.getDrawable();
					if (var14 == UIStoreActorGroup.Const.coinDrawablePress
							|| var14 == UIStoreActorGroup.Const.coinDrawableNormal) {
						var12.setDrawable(var13);
					}
				}
			}

			Table var8 = (Table) var4.getChildren().get(2);
			Drawable var9;
			if (var2) {
				var9 = UIStoreActorGroup.Const.moneyLogoPress;
			} else {
				var9 = UIStoreActorGroup.Const.moneyLogoNormal;
			}

			((Image) var8.getChildren().get(0)).setDrawable(var9);
			Label var10 = (Label) ((Label) var8.getChildren().get(1));
			var10.setColor(var5);
			var10.getStyle().font.setScale(1.0F);
		} else {
			Settings.w("error ocucured");
		}

		var3.layout();
	}

	private void updateStoreItem(int var1) {
		boolean var2;
		if (var1 == this.coinPaneSelectedIndex) {
			var2 = true;
		} else {
			var2 = false;
		}

		Table var3 = (Table) this.coinPane.getChildren().get(0);
		XTable var4 = (XTable) var3.getChildren().get(var1);
		if (var4.index == var1) {
			Color var5;
			if (var2) {
				var5 = UIAchieveState.Const.itemPressedColor;
			} else {
				var5 = UIAchieveState.Const.itemNormalColor;
			}

			Drawable var6;
			if (var2) {
				var6 = this.skin.getDrawable("store_button_press");
			} else {
				var6 = this.skin.getDrawable("store_button_normal");
			}

			var4.setBackground(var6);
			Group var7 = (Group) var4.getChildren().get(1);
			int var8 = 0;
			Iterator var9 = var7.getChildren().iterator();

			while (var9.hasNext()) {
				Actor var11 = (Actor) var9.next();
				if (var11 instanceof Label) {
					var11.setColor(var5);
					Label var15 = (Label) var11;
					String var16 = var15.getText().toString().trim();
					if (var16.equalsIgnoreCase("")
							|| var16.equalsIgnoreCase("equipped")) {
						String var17;
						if (this.coinItems[var1].currentLevel == 0) {
							var17 = "  ";
						} else {
							var17 = "equipped";
						}

						var15.setText(var17);
					}
				}

				if (var11 instanceof Image) {
					Image var12 = (Image) var11;
					Drawable var13;
					if (var2) {
						var13 = UIStoreActorGroup.Const.levelOpenPressed;
					} else {
						var13 = UIStoreActorGroup.Const.levelOpenNormal;
					}

					Drawable var14 = var12.getDrawable();
					if (var14 == UIStoreActorGroup.Const.levelOpenPressed
							|| var14 == UIStoreActorGroup.Const.levelOpenNormal) {
						++var8;
					}

					if (var14 == UIStoreActorGroup.Const.levelOpenPressed
							|| var14 == UIStoreActorGroup.Const.levelOpenNormal) {
						if (var8 > this.coinItems[var1].currentLevel) {
							var12.setDrawable(UIStoreActorGroup.Const.levelClosed);
							--var8;
						} else {
							var12.setDrawable(var13);
						}
					}

					if (var14 == UIStoreActorGroup.Const.levelClosed
							&& var8 < this.coinItems[var1].currentLevel) {
						var12.setDrawable(var13);
						++var8;
					}
				}
			}

			XScreen.getBitmapFont48().setScale(1.0F);
			Label var10 = (Label) ((Label) ((Table) var4.getChildren().get(2))
					.getChildren().get(0));
			var10.setColor(var5);
			if (Math.abs(this.coinItems[var1].price) < 0.001D) {
				var10.setText("MAX");
			} else {
				var10.setText((int) this.coinItems[var1].price + "");
			}
		} else {
			Settings.w("error ocucured");
		}

		var3.layout();
	}

	public void addActionToMovingButton(Action var1) {
		this.movingButton.addAction(var1);
	}

	public void addPaneToGroup(Group var1) {
		byte var2 = 1;
		var1.addActor(this.paneTile);
		ScrollPane var3 = this.coinPane;
		byte var4;
		if (this.paneInUse == 0) {
			var4 = var2;
		} else {
			var4 = 0;
		}

		var3.setVisible(var4 == 0 ? false : true);
		var1.addActor(this.coinPane);
		ScrollPane var5 = this.dollarPane;
		if (this.paneInUse != var2) {
			var2 = 0;
		}

		var5.setVisible((var2 == 0 ? false : true));
		var1.addActor(this.dollarPane);
		var1.addActor(this.paneFooter);
		XScreen.getBitmapFont36().setScale(1.0F);
		var1.addActor(this.movingButton);
	}

	public void clearSelectedIndex() {
		this.clearCoinPaneSelectedIndex();
		this.clearDollarPaneSelectedIndex();
	}

	public Action getMoveInAction(float var1) {
		if (this.paneInUse == 0
				&& this.coinPaneSelectedIndex != -1
				&& this.coinItems[this.coinPaneSelectedIndex].currentLevel == this.coinItems[this.coinPaneSelectedIndex].levelMax) {
			return Actions.delay(0.0F);
		} else {
			float var2 = (float) this.moveButtonX;
			FontScaleAction var3 = FontScaleAction.fontScaleIn(0.16666667F);
			MoveToAction var4 = Actions.moveTo(var2, 180.0F, 0.2F,
					Interpolation.pow2Out);
			Action[] var5 = new Action[] {
					Actions.touchable(Touchable.disabled), Actions.delay(var1),
					SetTextAction.setText(this.getUpdateMovingButtonText()),
					FontScaleAction.setFontScale0(), var4, var3,
					Actions.touchable(Touchable.enabled) };
			return Actions.sequence(var5);
		}
	}

	public Action getMoveOutAction(float var1) {
		float var2 = (float) this.moveButtonX;
		FontScaleAction var3 = FontScaleAction.fontScaleOut(0.16666667F);
		MoveToAction var4 = Actions.moveTo(var2, -66.0F, 0.2F,
				Interpolation.pow2Out);
		return Actions.sequence(Actions.touchable(Touchable.disabled),
				Actions.delay(var1), var3, var4);
	}

	public String getUpdateMovingButtonText() {
		int var1 = this.paneInUse;
		String var2;
		if (var1 == 1) {
			int var4 = this.dollarPaneSelectedIndex;
			var2 = null;
			if (var4 != -1) {
				var2 = "BUY";
			}
		} else {
			var2 = null;
			if (var1 == 0) {
				int var3 = this.coinPaneSelectedIndex;
				var2 = null;
				if (var3 != -1) {
					return this.MoveButtonSelectedText;
				}
			}
		}

		return var2;
	}

	public boolean moveButtonIsOut() {
		return this.movingButton.getY() == -66.0F
				&& this.movingButton.getX() == (float) this.moveButtonX;
	}

	public void moveMovingButton(float var1, float var2) {
		this.movingButton.addAction(Actions.moveTo((float) this.moveButtonX,
				var1, var2, Interpolation.pow2Out));
	}

	public void moveMovingButtonIn(float var1) {
		if (this.movingButton.getY() != 180.0F
				|| this.movingButton.getX() != (float) this.moveButtonX) {
			Action var2 = this.getMoveInAction(var1);
			this.movingButton.addAction(var2);
		}
	}

	public void moveMovingButtonOut(float var1) {
		if (!this.moveButtonIsOut()) {
			Action var2 = this.getMoveOutAction(var1);
			this.movingButton.addAction(var2);
		}
	}

	public void setItemSelectedFuns(UIStoreActorGroup.ItemSelected var1) {
		this.itemSelectedFuns = var1;
	}

	public void setMoveButtonInterface(
			UIStoreActorGroup.MovingButtonInterface var1) {
		this.moveButtonInterface = var1;
		this.updateMoveButtonText();
	}

	public void setMoveButtonSelectedText(String var1) {
		this.MoveButtonSelectedText = var1;
	}

	public boolean switchPane(int var1) {
		if (var1 != this.paneInUse && (var1 == 0 || var1 == 1)) {
			this.explation_words.setText("   ");
			this.paneInUse = var1;
			ScrollPane var2 = this.coinPane;
			boolean var3;
			if (var1 == 0) {
				var3 = true;
			} else {
				var3 = false;
			}

			var2.setVisible(var3);
			ScrollPane var4 = this.dollarPane;
			boolean var5;
			if (var1 == 1) {
				var5 = true;
			} else {
				var5 = false;
			}

			var4.setVisible(var5);
			Label.LabelStyle var6 = (Label.LabelStyle) this.skin.get(
					"LabelNormalStyle", Label.LabelStyle.class);
			Label.LabelStyle var7 = (Label.LabelStyle) this.skin.get(
					"LabelDisableStyle", Label.LabelStyle.class);
			Label var8 = this.titleWeaponLabel;
			Label.LabelStyle var9;
			if (var1 == 0) {
				var9 = var6;
			} else {
				var9 = var7;
			}

			var8.setStyle(var9);
			this.titleWeaponLabel
					.setColor(this.titleWeaponLabel.getStyle().fontColor);
			Label var10 = this.titleCashLabel;
			if (var1 != 1) {
				var6 = var7;
			}

			var10.setStyle(var6);
			this.titleCashLabel
					.setColor(this.titleCashLabel.getStyle().fontColor);
			this.paneTile
					.setBackground(UIAchieveState.Const.titleDrawables[var1]);
			Table var11 = this.paneFooter;
			boolean var12 = false;
			if (var1 == 0) {
				var12 = true;
			}

			var11.setVisible(var12);
			if (var1 != 0) {
				this.clearCoinPaneSelectedIndex();
			}

			if (var1 != 1) {
				this.clearDollarPaneSelectedIndex();
			}

			this.moveMovingButtonOut(0.0F);
			return true;
		} else {
			return false;
		}
	}

	public void switchPaneToCoinPane() {
		this.switchPane(0);
	}

	public void switchToLevel() {
		this.coinPane.setWidget(this.levelItemTable);
		this.coinItems = this.subLevelItems;
	}

	public void switchToOnce() {
		this.coinPane.setWidget(this.onceItemTable);
		this.coinItems = UIStoreActorGroup.Const.onceItems;
	}

	public void upateAllCoinItem() {
		for (int var1 = 0; var1 < this.coinItems.length; ++var1) {
			this.coinItems[var1].refreshData();
			this.updateStoreItem(var1);
		}

	}

	public void updateMoveButtonText() {
		// this.movingButton.getLabel().setText(this.getUpdateMovingButtonText());
	}

	public void updateTitleMoneyLabel() {
		this.titleMoneyLabel.setText(Settings.money + "");
	}

	public static class CoinDropEffectItem extends UIStoreActorGroup.StoreItem {

		public CoinDropEffectItem() {
			this.itemName = "Bless";
			this.explationWords = "Increase the chance of dropping coins.\n<Bonus increase in max level>";
			this.refreshData();
		}

		public Drawable getLogoDrawable() {
			return UIStoreActorGroup.Const.luckDrawable;
		}

		public void payForStoreItem() {
			Settings.money = (long) ((double) Settings.money - this.price);
			++Settings.coinDropLevel;
			this.refreshData();
		}

		public void refreshData() {
			this.currentLevel = Settings.coinDropLevel;
			double var1;
			if (this.currentLevel == this.levelMax) {
				var1 = 0.0D;
			} else {
				var1 = (double) Settings.priceCoinDropLevelUp[this.currentLevel];
			}

			this.price = var1;
		}
	}

	public static class FireEffectItem extends UIStoreActorGroup.StoreItem {

		public FireEffectItem() {
			this.itemName = "Power";
			this.explationWords = "Upgrade the firepower of your fighters.";
			this.refreshData();
		}

		public boolean canSell() {
			return Settings.lapCount > 1;
		}

		public Drawable getLogoDrawable() {
			return UIStoreActorGroup.Const.gunPowerDrawable;
		}

		public void payForStoreItem() {
			Settings.money = (long) ((double) Settings.money - this.price);
			++Settings.fireEffectLevel;
			this.refreshData();
		}

		public void refreshData() {
			this.currentLevel = Settings.fireEffectLevel;
			double var1;
			if (this.currentLevel == this.levelMax) {
				var1 = 0.0D;
			} else {
				var1 = (double) Settings.priceFireEffectLevelUp[this.currentLevel];
			}

			this.price = var1;
		}
	}

	public static class Const {

		public static final int ADFREEX = 133;
		public static final int ADFREEY = 37;
		public static final int CASHPANEINUSE = 1;
		public static boolean[] adFree = new boolean[] { false, true, true,
				true, true, true };
		public static Drawable adFreeDrawable;
		public static Drawable blueBulletDrawable;
		public static Drawable boomDrawable;
		public static Drawable[] cashLogo = new Drawable[] { null, null, null,
				null, null, null };
		public static String[] cashName = new String[] { "coin", "cheque",
				"gem", "jewel", "treasure", "gold mine" };
		public static Drawable coinDrawableNormal;
		public static Drawable coinDrawablePress;
		public static final int[] coinGet = new int[] { 5000, 12800, 26000,
				55000, 150000, 320000 };
		public static Drawable diceDrawable;
		public static String[] explationWordString = new String[] {
				"pay 0.99 dollars to get 5000 coins",
				"pay 1.99 dollars to get 12800 coins",
				"pay 2.99 dollars to get 26000 coins",
				"pay 4.99 dollars to get 55000 coins",
				"pay 9.99 dollars to get 150000 coins",
				"pay 19.99 dollars to get 320000 coins" };
		public static DecimalFormat format1;
		public static DecimalFormat format2;
		public static Drawable getallDrawable;
		public static Drawable gunPowerDrawable;
		public static double[] itemPrice = new double[] { 99f, 199f, 299f,
				499f, 999f, 1999f };
		public static Drawable levelClosed;
		protected static final UIStoreActorGroup.StoreItem[] levelItems;
		public static Drawable levelOpenNormal;
		public static Drawable levelOpenPressed;
		public static Drawable lifeDrawable;
		public static Drawable luckDrawable;
		public static Drawable megaDrawable;
		public static Drawable missleDrawable;
		public static Drawable moneyLogoNormal;
		public static Drawable moneyLogoPress;
		protected static final UIStoreActorGroup.StoreItem[] onceItems;
		public static Drawable redBulletDrawable;
		public static Drawable wingManDrawable;
		public static Drawable yellowBulletDrawable;

		static {
			UIStoreActorGroup.StoreItem[] var0 = new UIStoreActorGroup.StoreItem[] {
					new UIStoreActorGroup.FireEffectItem(),
					new UIStoreActorGroup.BoomItem(),
					new UIStoreActorGroup.MissileEffectItem(),
					new UIStoreActorGroup.MegeEffectItem(),
					new UIStoreActorGroup.WingEffectItem(),
					new UIStoreActorGroup.LifeEffectItem(),
					new UIStoreActorGroup.ItemDropEffectItem(),
					new UIStoreActorGroup.CoinDropEffectItem() };
			levelItems = var0;
			UIStoreActorGroup.StoreItem[] var1 = new UIStoreActorGroup.StoreItem[] {
					new UIStoreActorGroup.OnceAllItem(),
					new UIStoreActorGroup.OnceRedBulletItem(),
					new UIStoreActorGroup.OnceYellowBulletItem(),
					new UIStoreActorGroup.OnceBlueBulletItem(),
					new UIStoreActorGroup.OnceMissleItem(),
					new UIStoreActorGroup.OnceWingManItem(),
					new UIStoreActorGroup.OnceMagnetItem() };
			onceItems = var1;
			format1 = new DecimalFormat(".0");
			format2 = new DecimalFormat(".00");
		}

	}

	public static class OnceAllItem extends UIStoreActorGroup.OnceItem {

		public OnceAllItem() {
			this.itemName = "Billionaire";
			this.explationWords = "Wrap up all below for you in a good price. <Random Bullet Type>.";
			this.refreshData();
		}

		public Drawable getLogoDrawable() {
			return UIStoreActorGroup.Const.getallDrawable;
		}

		public void payForStoreItem() {
			Settings.money = (long) ((double) Settings.money - this.price);
			Settings.allOnceBuy = true;
			this.refreshData();
		}

		public void refreshData() {
			byte var1;
			if (Settings.allOnceBuy) {
				var1 = 1;
			} else {
				var1 = 0;
			}

			this.currentLevel = var1;
			this.price = 1500.0D;
		}
	}

	public static class MissileEffectItem extends UIStoreActorGroup.StoreItem {

		public MissileEffectItem() {
			this.itemName = "Surge";
			this.explationWords = "Upgrade the damage of your missiles.";
			this.refreshData();
		}

		public Drawable getLogoDrawable() {
			return UIStoreActorGroup.Const.missleDrawable;
		}

		public void payForStoreItem() {
			Settings.money = (long) ((double) Settings.money - this.price);
			++Settings.missleEffectLevel;
			this.refreshData();
		}

		public void refreshData() {
			this.currentLevel = Settings.missleEffectLevel;
			double var1;
			if (this.currentLevel == this.levelMax) {
				var1 = 0.0D;
			} else {
				var1 = (double) Settings.priceMissleEffectLevelUp[this.currentLevel];
			}

			this.price = var1;
		}
	}

	public static class BoomItem extends UIStoreActorGroup.StoreItem {

		public BoomItem() {
			this.itemName = "Valor";
			this.explationWords = "Allow you to take more bombs.";
			this.refreshData();
		}

		public Drawable getLogoDrawable() {
			return UIStoreActorGroup.Const.boomDrawable;
		}

		public void payForStoreItem() {
			Settings.money = (long) ((double) Settings.money - this.price);
			++Settings.boomEffectLevel;
			this.refreshData();
		}

		public void refreshData() {
			this.currentLevel = Settings.boomEffectLevel;
			double var1;
			if (this.currentLevel == this.levelMax) {
				var1 = 0.0D;
			} else {
				var1 = (double) Settings.priceBoomEffectLevelUp[this.currentLevel];
			}

			this.price = var1;
		}
	}

	public static class WingEffectItem extends UIStoreActorGroup.StoreItem {

		public WingEffectItem() {
			this.itemName = "Passion";
			this.explationWords = "Upgrade the damage of your wingman.";
			this.refreshData();
		}

		public Drawable getLogoDrawable() {
			return UIStoreActorGroup.Const.wingManDrawable;
		}

		public void payForStoreItem() {
			Settings.money = (long) ((double) Settings.money - this.price);
			++Settings.wingEffectLevel;
			this.refreshData();
		}

		public void refreshData() {
			this.currentLevel = Settings.wingEffectLevel;
			double var1;
			if (this.currentLevel == this.levelMax) {
				var1 = 0.0D;
			} else {
				var1 = (double) Settings.priceWingEffectLevelUp[this.currentLevel];
			}

			this.price = var1;
		}
	}

	public abstract static class OnceItem extends UIStoreActorGroup.StoreItem {

		public OnceItem() {
			this.levelMax = 1;
		}
	}

	public static class OnceWingManItem extends UIStoreActorGroup.OnceItem {

		public OnceWingManItem() {
			this.itemName = "Wingman";
			this.explationWords = "Start mission with wingman.";
			this.refreshData();
		}

		public Drawable getLogoDrawable() {
			return UIStoreActorGroup.Const.wingManDrawable;
		}

		public void payForStoreItem() {
			Settings.money = (long) ((double) Settings.money - this.price);
			Settings.wingManOnceBuy = true;
			this.refreshData();
		}

		public void refreshData() {
			byte var1;
			if (Settings.wingManOnceBuy) {
				var1 = 1;
			} else {
				var1 = 0;
			}

			this.currentLevel = var1;
			this.price = 500.0D;
		}
	}

	public class storePaneItemPressLis extends PressedListener {

		public boolean touchDown(InputEvent var1, float var2, float var3,
				int var4, int var5) {
			if (super.touchDown(var1, var2, var3, var4, var5)) {
				XTable var6 = (XTable) var1.getListenerActor();
				int var7 = UIStoreActorGroup.this.coinPaneSelectedIndex;
				if (var6.index != UIStoreActorGroup.this.coinPaneSelectedIndex) {
					UIStoreActorGroup.this.coinPaneSelectedIndex = var6.index;
					UIStoreActorGroup.this
							.updateStoreItem(UIStoreActorGroup.this.coinPaneSelectedIndex);
					if (var7 >= 0
							&& var7 < UIStoreActorGroup.this.coinItems.length) {
						UIStoreActorGroup.this.updateStoreItem(var7);
					}

					String var10 = UIStoreActorGroup.this.coinItems[UIStoreActorGroup.this.coinPaneSelectedIndex].explationWords;
					UIStoreActorGroup.this.explation_words.setText(var10);
				} else {
					UIStoreActorGroup.this.coinPaneSelectedIndex = -1;
					UIStoreActorGroup.this.updateStoreItem(var6.index);
					UIStoreActorGroup.this.explation_words.setText("   ");
				}

				if (UIStoreActorGroup.this.itemSelectedFuns != null) {
					UIStoreActorGroup.this.itemSelectedFuns.itemSelected(
							UIStoreActorGroup.this.paneInUse,
							UIStoreActorGroup.this.coinPaneSelectedIndex, var7);
				}

				return true;
			} else {
				return false;
			}
		}
	}

	public static class OnceMissleItem extends UIStoreActorGroup.OnceItem {

		public OnceMissleItem() {
			this.itemName = "Missile";
			this.explationWords = "Start mission with missile.";
			this.refreshData();
		}

		public Drawable getLogoDrawable() {
			return UIStoreActorGroup.Const.missleDrawable;
		}

		public void payForStoreItem() {
			Settings.money = (long) ((double) Settings.money - this.price);
			Settings.missleOnceBuy = true;
			this.refreshData();
		}

		public void refreshData() {
			byte var1;
			if (Settings.missleOnceBuy) {
				var1 = 1;
			} else {
				var1 = 0;
			}

			this.currentLevel = var1;
			this.price = 500.0D;
		}
	}

	public static class ItemDropEffectItem extends UIStoreActorGroup.StoreItem {

		public ItemDropEffectItem() {
			this.itemName = "Luck";
			this.explationWords = "Increase the chance of dropping items.\n<Bonus increase in max level>";
			this.refreshData();
		}

		public Drawable getLogoDrawable() {
			return UIStoreActorGroup.Const.diceDrawable;
		}

		public void payForStoreItem() {
			Settings.money = (long) ((double) Settings.money - this.price);
			++Settings.itemDropLevel;
			this.refreshData();
		}

		public void refreshData() {
			this.currentLevel = Settings.itemDropLevel;
			double var1;
			if (this.currentLevel == this.levelMax) {
				var1 = 0.0D;
			} else {
				var1 = (double) Settings.priceDropLevelUp[this.currentLevel];
			}

			this.price = var1;
		}
	}

	public interface MovingButtonInterface {

		void buttonClicked(int var1, int var2);

		void playButtonSound();
	}

	public class dollarPaneItemPressLis extends PressedListener {

		public boolean touchDown(InputEvent var1, float var2, float var3,
				int var4, int var5) {
			if (super.touchDown(var1, var2, var3, var4, var5)) {
				XTable var6 = (XTable) var1.getListenerActor();
				int var7 = UIStoreActorGroup.this.dollarPaneSelectedIndex;
				if (var6.index != UIStoreActorGroup.this.dollarPaneSelectedIndex) {
					UIStoreActorGroup.this.dollarPaneSelectedIndex = var6.index;
					UIStoreActorGroup.this
							.updateCashItem(UIStoreActorGroup.this.dollarPaneSelectedIndex);
					if (var7 >= 0
							&& var7 < UIStoreActorGroup.Const.cashName.length) {
						UIStoreActorGroup.this.updateCashItem(var7);
					}

					UIStoreActorGroup.this.explation_words
							.setText(UIStoreActorGroup.Const.explationWordString[UIStoreActorGroup.this.dollarPaneSelectedIndex]);
				} else {
					UIStoreActorGroup.this.dollarPaneSelectedIndex = -1;
					UIStoreActorGroup.this.updateCashItem(var6.index);
					UIStoreActorGroup.this.explation_words.setText("    ");
				}

				if (UIStoreActorGroup.this.itemSelectedFuns != null) {
					UIStoreActorGroup.this.itemSelectedFuns.itemSelected(
							UIStoreActorGroup.this.paneInUse,
							UIStoreActorGroup.this.dollarPaneSelectedIndex,
							var7);
				}

				return true;
			} else {
				return false;
			}
		}
	}

	public static class LifeEffectItem extends UIStoreActorGroup.StoreItem {

		public LifeEffectItem() {
			this.itemName = "Faith";
			this.explationWords = "Allow you to have more lives.";
			this.refreshData();
		}

		public Drawable getLogoDrawable() {
			return UIStoreActorGroup.Const.lifeDrawable;
		}

		public void payForStoreItem() {
			Settings.money = (long) ((double) Settings.money - this.price);
			++Settings.lifeEffectLevel;
			this.refreshData();
		}

		public void refreshData() {
			this.currentLevel = Settings.lifeEffectLevel;
			double var1;
			if (this.currentLevel == this.levelMax) {
				var1 = 0.0D;
			} else {
				var1 = (double) Settings.priceLifeEffectLevelUp[this.currentLevel];
			}

			this.price = var1;
		}
	}

	public static class OnceMagnetItem extends UIStoreActorGroup.OnceItem {

		public OnceMagnetItem() {
			this.itemName = "Magnet";
			this.explationWords = "Start mission with magnet.";
			this.refreshData();
		}

		public Drawable getLogoDrawable() {
			return UIStoreActorGroup.Const.megaDrawable;
		}

		public void payForStoreItem() {
			Settings.money = (long) ((double) Settings.money - this.price);
			Settings.magnetOnceBuy = true;
			this.refreshData();
		}

		public void refreshData() {
			byte var1;
			if (Settings.magnetOnceBuy) {
				var1 = 1;
			} else {
				var1 = 0;
			}

			this.currentLevel = var1;
			this.price = 300.0D;
		}
	}

	public static class UpdateUI implements EnemyTools.DeleFunction {

		Label moneyLabel = null;

		public void function(Object var1) {
			if (this.moneyLabel != null) {
				this.moneyLabel.setText(Settings.money + "");
			}

		}
	}

	public static class OnceRedBulletItem extends UIStoreActorGroup.OnceItem {

		public OnceRedBulletItem() {
			this.itemName = "Focus";
			this.explationWords = "Start mission with max level bullet.";
			this.refreshData();
		}

		public Drawable getLogoDrawable() {
			return UIStoreActorGroup.Const.redBulletDrawable;
		}

		public void payForStoreItem() {
			Settings.money = (long) ((double) Settings.money - this.price);
			Settings.redBulletOnceBuy = true;
			this.refreshData();
		}

		public void refreshData() {
			byte var1;
			if (Settings.redBulletOnceBuy) {
				var1 = 1;
			} else {
				var1 = 0;
			}

			this.currentLevel = var1;
			this.price = 800.0D;
		}
	}

	public interface ItemSelected {

		void itemSelected(int var1, int var2, int var3);

		void switchPane(int var1, int var2);
	}

	public static class MegeEffectItem extends UIStoreActorGroup.StoreItem {

		public MegeEffectItem() {
			this.itemName = "Attraction";
			this.explationWords = "Upgrade the range of your magnet.";
			this.refreshData();
		}

		public Drawable getLogoDrawable() {
			return UIStoreActorGroup.Const.megaDrawable;
		}

		public void payForStoreItem() {
			Settings.money = (long) ((double) Settings.money - this.price);
			++Settings.megaEffectLevel;
			this.refreshData();
		}

		public void refreshData() {
			this.currentLevel = Settings.megaEffectLevel;
			double var1;
			if (this.currentLevel == this.levelMax) {
				var1 = 0.0D;
			} else {
				var1 = (double) Settings.priceMegaEffectLevelUp[this.currentLevel];
			}

			this.price = var1;
		}
	}

	public static class OnceBlueBulletItem extends UIStoreActorGroup.OnceItem {

		public OnceBlueBulletItem() {
			this.itemName = "Fractal";
			this.explationWords = "Start mission with max level bullet.";
			this.refreshData();
		}

		public Drawable getLogoDrawable() {
			return UIStoreActorGroup.Const.blueBulletDrawable;
		}

		public void payForStoreItem() {
			Settings.money = (long) ((double) Settings.money - this.price);
			Settings.blueBulletOnceBuy = true;
			this.refreshData();
		}

		public void refreshData() {
			byte var1;
			if (Settings.blueBulletOnceBuy) {
				var1 = 1;
			} else {
				var1 = 0;
			}

			this.currentLevel = var1;
			this.price = 800.0D;
		}
	}

	public static class OnceYellowBulletItem extends UIStoreActorGroup.OnceItem {

		public OnceYellowBulletItem() {
			this.itemName = "Balance";
			this.explationWords = "Start mission with max level bullet.";
			this.refreshData();
		}

		public Drawable getLogoDrawable() {
			return UIStoreActorGroup.Const.yellowBulletDrawable;
		}

		public void payForStoreItem() {
			Settings.money = (long) ((double) Settings.money - this.price);
			Settings.yellowBulletOnceBuy = true;
			this.refreshData();
		}

		public void refreshData() {
			byte var1;
			if (Settings.yellowBulletOnceBuy) {
				var1 = 1;
			} else {
				var1 = 0;
			}

			this.currentLevel = var1;
			this.price = 800.0D;
		}
	}

	public abstract static class StoreItem {

		public int currentLevel = 0;
		public String explationWords = "test explationWords";
		public String itemName = "test ItemName";
		public int levelMax = 5;
		public double price = 10.0D;

		public boolean canSell() {
			return true;
		}

		public abstract Drawable getLogoDrawable();

		public abstract void payForStoreItem();

		public abstract void refreshData();
	}
}
