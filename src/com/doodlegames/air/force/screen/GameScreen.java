package com.doodlegames.air.force.screen;

import java.text.DecimalFormat;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.doodlegames.air.force.ActionResolver;
import com.doodlegames.air.force.InputMultiplexer;
import com.doodlegames.air.force.NativeService;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.NewStage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.TouchableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.doodlegames.air.force.enemy.ScoreData;
import com.doodlegames.air.force.game.BGfixer;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldAnimationPlayer;
import com.doodlegames.air.force.game.WorldAudio;
import com.doodlegames.air.force.game.WorldEvents;
import com.doodlegames.air.force.game.WorldRender;
import com.doodlegames.air.force.game.prop.PropDropHandler;
import com.doodlegames.air.force.resource.Assets_Audio;
import com.doodlegames.air.force.resource.Assets_Props;
import com.doodlegames.air.force.resource.Assets_ShareInAlien;
import com.doodlegames.air.force.resource.Assets_ShareInAllLevel;
import com.doodlegames.air.force.resource.Assets_ShareInNazi;
import com.doodlegames.air.force.resource.Assets_fighter;
import com.doodlegames.air.force.resource.Assets_level1;
import com.doodlegames.air.force.resource.Assets_level2;
import com.doodlegames.air.force.resource.Assets_level3;
import com.doodlegames.air.force.resource.Assets_level4;
import com.doodlegames.air.force.resource.Assets_level5;
import com.doodlegames.air.force.resource.Assets_level6;
import com.doodlegames.air.force.resource.Assets_level7;
import com.doodlegames.air.force.screen.action.FontScaleAction;
import com.doodlegames.air.force.screen.action.SetTextAction;
import com.doodlegames.air.force.screen.screenActor.XImageButton;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.MusicPlayer;
import com.doodlegames.air.force.utils.RegionUtilFunctions;
import com.doodlegames.air.force.utils.Settings;

public class GameScreen implements Screen, UIStoreActorGroup.MovingButtonInterface {

   public static final int FROMNEXT = 1;
   public static final int FROMUIMENU = 2;
   static final int GAME_LEVEL_END = 3;
   static final int GAME_OVER = 4;
   static final int GAME_PAUSED = 2;
   static final int GAME_READY = 0;
   static final int GAME_RUNNING = 1;
   private static final int LOGOIDGAMEOVER = 2;
   private static final int LOGOIDPAUSE = 0;
   private static final int LOGOIDWIN = 1;
   public static int bossHealthyDegree = 0;
   public static int comFromWhere = 0;
   public static GameScreen.GameScreenInfo gameScreenInfo;
   private static LoadingScreen.LoadingInfo[] infos;
   public static int level = -1;
   private static TextureAtlas loadingAtlas;
   private static Skin skin;
   long backPressTimeStamp;
   private SpriteBatch batcher;
   private Image boomImage;
   private Label boomNumLabel;
   private ImageButton bottomBackButton;
   private Group bottomGroup;
   private ImageButton bottomRateButton;
   private GameScreen.StateChangeAction changeStateTo;
   public Color[] clsColors;
   private Label coinNumLabel;
   private Vector2 deltaVector2;
   private Game game;
   private Group gameOverGroup;
   private NewStage gameUIStage;
   private OrthographicCamera guiCam;
   private Label levelContentLabel;
   private Table levelTable;
   private Image lifeImage;
   private Label lifeNumLabel;
   private Group loadingGroup;
   private Drawable[] logoDrawables;
   private Image logoImage;
   private ImageButton musicButton;
   private InputMultiplexer mutiPro;
   private UIStoreActorGroup onceStore;
   private Group onceStoreGroup;
   private ImageButton pauseButton;
   private Group pauseGroup;
   private TextButton playButton;
   GameScreen.PlayWarningSound[] playSounds;
   private Group readyGroup;
   private TextButton resumeButton;
   final Action resumeMusicAction;
   private Group runningGroup;
   private GameScreen.GameScreenInputProcessor runningPro;
   private Label scoreNumLabel;
   private Rectangle screenArea;
   Action showBannerAction;
   private Table showDataTable;
   private ImageButton soundButton;
   private Image[] starImages;
   private int state;
   final Action stopMusicAction;
   private UIStoreActorGroup storeActors;
   private Group storeGroup;
   private Group upperGroup;
   private Group vectoryGroup;
   private Image warningImage;
   private Action warningOverAction;
   private World world;
   private boolean worldIsWarning;
   private WorldRender worldRender;


   static {
      LoadingScreen.LoadingInfo[] var0 = new LoadingScreen.LoadingInfo[]{Assets_level1.info, Assets_level2.info, Assets_level3.info, Assets_level4.info, Assets_level5.info, Assets_level6.info, Assets_level7.info};
      infos = var0;
      gameScreenInfo = new GameScreen.GameScreenInfo();
      loadingAtlas = null;
   }

   public GameScreen(Game var1, int var2) {
      Color[] var3 = new Color[]{new Color(0.19215687F, 0.3529412F, 0.58431375F, 1.0F), new Color(0.5137255F, 0.45490196F, 0.31764707F, 1.0F), new Color(0.1254902F, 0.17254902F, 0.20784314F, 1.0F), new Color(0.14901961F, 0.22352941F, 0.22745098F, 1.0F), new Color(0.0F, 0.0F, 0.0F, 1.0F), new Color(0.4392157F, 0.23529412F, 0.19607843F, 1.0F), new Color(0.27058825F, 0.1764706F, 0.28235295F, 1.0F)};
      this.clsColors = var3;
      this.showBannerAction = new Action() {
         public boolean act(float var1) {
            if(GameScreen.this.state == 2 || GameScreen.this.state == 4 || GameScreen.this.state == 3) {
//               //////((Lightning)GameScreen.this.game).getPlatFormFunction().showBanner();
            }

            return true;
         }
      };
      this.screenArea = new Rectangle(0.0F, 0.0F, 480.0F, 800.0F);
      this.logoDrawables = new Drawable[]{null, null, null};
      this.stopMusicAction = new Action() {
         public boolean act(float var1) {
            MusicPlayer.musicPlayer.stop();
            return true;
         }
      };
      this.resumeMusicAction = new Action() {
         public boolean act(float var1) {
            if(Settings.isFocus()) {
               MusicPlayer.musicPlayer.play();
            }

            return true;
         }
      };
      this.backPressTimeStamp = -1L;
      this.changeStateTo = new GameScreen.StateChangeAction();
      this.worldIsWarning = false;
      this.warningOverAction = new Action() {
         public boolean act(float var1) {
            GameScreen.this.worldIsWarning = false;
            Settings.i("warning action execute over." + GameScreen.this.worldIsWarning);
            return true;
         }
      };
      this.runningPro = new GameScreen.GameScreenInputProcessor();
      this.mutiPro = null;
      this.guiCam = new OrthographicCamera(480.0F, 800.0F);
      this.guiCam.position.set(240.0F, 400.0F, 0.0F);
      this.batcher = XScreen.getSpriteBatch();
      this.deltaVector2 = new Vector2();
      this.game = var1;
      if(var2 <= 0 || var2 >= 8) {
         Settings.w("invalid level number, fix it to 1");
         var2 = 1;
      }

      this.world = new World(this, var2);
      this.state = 0;
      this.worldRender = new WorldRender(XScreen.getSpriteBatch(), this.world);
      this.constructGameUI(var1);
   }

   private void addActionToButton(boolean var1, TextButton var2, float var3, float var4, float var5) {
      FontScaleAction var6;
      if(var1) {
         var6 = FontScaleAction.setFontScale0(var3 + 0.5F);
      } else {
         var6 = FontScaleAction.setFontScale1(var3);
      }

      FontScaleAction var7;
      if(var1) {
         var7 = FontScaleAction.setFontScale0();
      } else {
         var7 = FontScaleAction.fontScaleOut(0.16666667F);
      }

      FontScaleAction var8;
      if(var1) {
         var8 = FontScaleAction.fontScaleIn(0.16666667F);
      } else {
         var8 = FontScaleAction.setFontScale0();
      }

      TouchableAction var9;
      if(var1) {
         var9 = Actions.touchable(Touchable.enabled);
      } else {
         var9 = Actions.touchable(Touchable.disabled);
      }

      var2.addAction(var6);
      Action[] var10 = new Action[]{Actions.touchable(Touchable.disabled), Actions.delay(var3), var7, Actions.moveTo(var4, var5, 0.5F, Interpolation.pow2Out), var8, var9};
      var2.addAction(Actions.sequence(var10));
   }

   private void addInitAction() {
      if(comFromWhere != 1 && comFromWhere != 2) {
         this.moveBottomToScreenBottom(0.0F);
         this.moveOnceStoreUp(0.0F);
         this.onceStore.addActionToMovingButton(this.onceStore.getMoveInAction(0.5F));
      } else {
         this.changeStateTo.goalState = 1;
         this.bottomGroup.addAction(Actions.after(this.changeStateTo));
         this.readyGroup.setVisible(false);
         this.runningGroup.setTouchable(Touchable.childrenOnly);
         this.runningActorsFadeIn(2.0F);
         if(comFromWhere == 2) {
            this.world.fighter.oncePropHandle();
         }

         comFromWhere = 0;
      }
   }

   private boolean backPressed(int var1) {
      if(this.backPressTimeStamp <= System.currentTimeMillis()) {
         this.backPressTimeStamp = 2000L + System.currentTimeMillis();
         if(var1 == 4) {
            switch(this.state) {
            case 0:
            default:
               break;
            case 1:
               if(this.pauseButton.getTouchable() == Touchable.enabled) {
                  this.ct_runningStateToPause();
               }
               break;
            case 2:
               if(this.resumeButton.getTouchable() == Touchable.enabled) {
                  this.ct_pauseStateToRunning();
//                  //////((Lightning)//////((Lightning)this.game)).getPlatFormFunction().closeBanner();
               }
               break;
            case 3:
            case 4:
               if(this.bottomBackButton.getTouchable() == Touchable.enabled) {
                  this.storeBack();
               }
            }

            return true;
         }
      }

      return false;
   }

   private void bottomShowBackButton() {
      this.bottomRateButton.addAction(Actions.sequence(Actions.touchable(Touchable.disabled), Actions.fadeOut(0.5F), Actions.hide()));
      this.bottomBackButton.addAction(Actions.sequence(Actions.delay(0.5F), Actions.alpha(0.0F), Actions.show(), Actions.fadeIn(0.5F), Actions.touchable(Touchable.enabled)));
   }

   private void bottomShowRateButton() {
      this.bottomBackButton.addAction(Actions.sequence(Actions.touchable(Touchable.disabled), Actions.fadeOut(0.5F), Actions.hide()));
      this.bottomRateButton.addAction(Actions.sequence(Actions.delay(0.5F), Actions.alpha(0.0F), Actions.show(), Actions.fadeIn(0.5F), Actions.touchable(Touchable.enabled)));
   }

   private void constructActorGroups() {
      this.readyGroup = new Group();
      this.readyGroup.setVisible(true);
      this.runningGroup = new Group();
      this.runningGroup.setTouchable(Touchable.disabled);
      this.pauseGroup = new Group();
      this.pauseGroup.setVisible(true);
      this.gameOverGroup = new Group();
      this.gameOverGroup.setVisible(false);
      this.vectoryGroup = new Group();
      this.vectoryGroup.setVisible(false);
      this.upperGroup = new Group();
      this.upperGroup.setTouchable(Touchable.disabled);
      this.bottomGroup = new Group();
      this.bottomGroup.setTouchable(Touchable.childrenOnly);
      this.storeGroup = new Group();
      this.loadingGroup = new Group() {
         public Actor hit(float var1, float var2) {
            Actor var3;
            if(this.getTouchable() == Touchable.disabled) {
               var3 = null;
            } else {
               var3 = super.hit(var1, var2, true);
               if(var3 == null) {
                  return this;
               }
            }

            return var3;
         }
      };
      this.gameUIStage.addActor(this.readyGroup);
      this.gameUIStage.addActor(this.runningGroup);
      this.gameUIStage.addActor(this.upperGroup);
      this.gameUIStage.addActor(this.pauseGroup);
      this.gameUIStage.addActor(this.gameOverGroup);
      this.gameUIStage.addActor(this.vectoryGroup);
      this.gameUIStage.addActor(this.storeGroup);
      this.gameUIStage.addActor(this.bottomGroup);
      this.gameUIStage.addActor(this.loadingGroup);
      this.gameUIStage.getRoot().setCullingArea(this.screenArea);
   }

   private void constructBottomGroup() {
      this.bottomGroup.setPosition(0.0F, -300.0F);
      Image var1 = new Image(skin.getDrawable("cockpit_end"));
      XImageButton var2 = new XImageButton(new XImageButton.XImageButtonStyle(skin.getDrawable("music_off_normal"), skin.getDrawable("music_off_press"), skin.getDrawable("music_on_press"), skin.getDrawable("music_on_normal"))) {
         public Actor hit(float var1, float var2) {
            if(Settings.isSmallScreen()) {
               float var3 = GameScreen.this.musicButton.getHeight();
               if(var2 < var3 * 2.0F) {
                  var2 = var3 / 2.0F;
               }
            }

            return super.hit(var1, var2,true);
         }
      };
      var2.setChecked(Settings.getMusicEnable());
      var2.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            GameScreen.this.playNormalButtonSound();
            Settings.changeMusicEnable();
         }
      });
      var2.setPosition(323.0F, 98.0F);
      this.musicButton = var2;
      XImageButton var4 = new XImageButton(new XImageButton.XImageButtonStyle(skin.getDrawable("sound_off_normal"), skin.getDrawable("sound_off_press"), skin.getDrawable("sound_on_press"), skin.getDrawable("sound_on_normal"))) {
         public Actor hit(float var1, float var2) {
            if(Settings.isSmallScreen()) {
               float var3 = GameScreen.this.soundButton.getHeight();
               if(var2 < var3 * 2.0F) {
                  var2 = var3 / 2.0F;
               }
            }

            return super.hit(var1, var2,true);
         }
      };
      var4.setChecked(Settings.getSoundEnable());
      var4.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            Settings.changeSoundEnable();
         }
      });
      var4.setPosition(391.0F, 98.0F);
      this.soundButton = var4;
      ImageButton.ImageButtonStyle var6 = new ImageButton.ImageButtonStyle();
      var6.up = skin.getDrawable("rate_button_normal");
      var6.down = skin.getDrawable("rate_button_press");
      ImageButton var7 = new ImageButton(var6);
      var7.setPosition(25.0F, 97.0F);
      var7.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            GameScreen.this.playNormalButtonSound();
            ((ActionResolver)Gdx.app).getLeaderboardGPGS();
//            ////////((Lightning)//////((Lightning)GameScreen.this.game)).getPlatFormFunction().rateGame();
         }
      });
      this.bottomRateButton = var7;
      ImageButton.ImageButtonStyle var9 = new ImageButton.ImageButtonStyle();
      var9.up = skin.getDrawable("back_normal");
      var9.down = skin.getDrawable("back_press");
      ImageButton var10 = new ImageButton(var9);
      var10.setPosition(25.0F, 97.0F);
      var10.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            GameScreen.this.playBackButtonSound();
            GameScreen.this.storeBack();
         }
      });
      var10.setVisible(false);
      var10.setTouchable(Touchable.disabled);
      this.bottomBackButton = var10;
      this.bottomGroup.addActor(var1);
      this.bottomGroup.addActor(var2);
      this.bottomGroup.addActor(var4);
      this.bottomGroup.addActor(var7);
      this.bottomGroup.addActor(var10);
      this.bottomGroup.setWidth(var1.getWidth());
      this.bottomGroup.setHeight(var1.getHeight());
   }

   private void constructGameOverGroup(Skin var1) {
      this.gameOverGroup.setCullingArea(this.screenArea);
      TextButton var2 = new TextButton("RETRY", var1, "greenButtonStyle");
      var2.setPosition(127.0F, -159.0F);
      var2.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            GameScreen.this.playNormalButtonSound();
            GameScreen.this.ct_gameoverToReady();
            GameScreen.this.warningImage.getActions().clear();
            GameScreen.this.warningImage.getColor().a = 0.0F;
//            //////((Lightning)GameScreen.this.game).getPlatFormFunction().closeBanner();
         }
      });
      this.gameOverGroup.addActor(var2);
      TextButton var4 = new TextButton("store", var1, "greenButtonStyle");
      var4.setPosition(127.0F, -246.0F);
      var4.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            GameScreen.this.playNormalButtonSound();
            GameScreen.this.showStore(0.75F);
            GameScreen.this.moveGOGroupAndTable(false, 0.0F);
//            //////((Lightning)GameScreen.this.game).getPlatFormFunction().closeBanner();
         }
      });
      this.gameOverGroup.addActor(var4);
      TextButton var6 = new TextButton("main", var1, "yellowButtonStyle");
      var6.setPosition(127.0F, -333.0F);
      var6.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            GameScreen.this.playNormalButtonSound();
//            //////((Lightning)GameScreen.this.game).getPlatFormFunction().closeBanner();
            GameScreen.this.gameScreenGoToMain(GameScreen.this.gameOverGroup);
         }
      });
      this.gameOverGroup.addActor(var6);
   }

   private void constructGameUI(Game var1) {
      this.gameUIStage = new NewStage(480.0F, 800.0F, false, this.batcher);
      this.gameUIStage.clear();
      this.gameUIStage.getRoot().setTransform(false);
      Actor var2 = new Actor();
      var2.addListener(new InputListener() {
         public boolean keyDown(InputEvent var1, int var2) {
            return GameScreen.this.backPressed(var2);
         }
      });
      this.gameUIStage.setKeyboardFocus(var2);
      Skin var4 = getGameUISkin();
      this.constructSenceTabel(var4);
      this.constructScreenTips(var4);
      this.constructActorGroups();
      this.constructReadyGroup(var4);
      this.constructRunningGroup(var4);
      this.constructPauseGroup(var4);
      this.constructGameOverGroup(var4);
      this.constructVectoryGroup(var4);
      this.constructMutiProcessor();
      this.constructStoreActors(var4);
      this.contructTopGroup();
      this.constructBottomGroup();
      this.constructWarningSound();
      this.constructLoadingGroup(var4);
      this.addInitAction();
   }

   private void constructLoadingGroup(Skin var1) {
      Image var2 = new Image(var1.getDrawable("help"));
      Image var3 = new Image(var1.getDrawable("cube")) {
         public void act(float var1) {
            if(GameScreen.this.loadingGroup.getTouchable() != Touchable.disabled) {
               super.act(var1);
            }
         }
      };
      var3.setPosition(315.0F, 15.0F);
      var3.addAction(Actions.forever(Actions.sequence(Actions.alpha(0.0F), Actions.delay(0.5F), Actions.alpha(1.0F), Actions.delay(0.5F))));
      this.loadingGroup.addActor(var2);
      this.loadingGroup.addActor(var3);
      this.loadingGroup.setVisible(false);
      this.loadingGroup.setTouchable(Touchable.disabled);
   }

   private InputMultiplexer constructMutiProcessor() {
      this.mutiPro = new InputMultiplexer();
      this.mutiPro.addProcessor(this.gameUIStage);
      this.mutiPro.addProcessor(this.runningPro);
      return this.mutiPro;
   }

   private void constructOnceStoreActors(Skin var1) {
      this.onceStoreGroup = new Group();
      this.readyGroup.addActor(this.onceStoreGroup);
      this.onceStore = new UIStoreActorGroup(var1, 1);
      this.onceStore.addPaneToGroup(this.onceStoreGroup);
      this.onceStoreGroup.setWidth(480.0F);
      this.onceStoreGroup.setHeight(800.0F);
      this.onceStoreGroup.setCullingArea(this.screenArea);
      this.onceStoreGroup.setVisible(false);
      this.onceStoreGroup.setPosition(0.0F, -800.0F);
      this.onceStore.setMoveButtonInterface(new UIStoreActorGroup.MovingButtonInterface() {
         public void buttonClicked(int var1, int var2) {
            if(var2 != -1) {
               GameScreen.this.onceStore.clearSelectedIndex();
               GameScreen.this.onceStore.addActionToMovingButton(Actions.sequence(GameScreen.this.onceStore.getMoveOutAction(0.0F)));
            }

         }
         public void playButtonSound() {
            GameScreen.this.world.worldAudio.playNormalButton();
         }
      });
      this.onceStore.setItemSelectedFuns(new UIStoreActorGroup.ItemSelected() {
         public void itemSelected(int var1, int var2, int var3) {
            GameScreen.this.world.worldAudio.playNormalButton();
            if(var2 != -1) {
               Object var4;
               if(GameScreen.this.onceStore.moveButtonIsOut()) {
                  var4 = Actions.delay(0.0F);
               } else {
                  var4 = GameScreen.this.onceStore.getMoveOutAction(0.0F);
               }

               Action var5 = GameScreen.this.onceStore.getMoveInAction(0.0F);
               GameScreen.this.onceStore.addActionToMovingButton(Actions.sequence((Action)var4, var5));
            } else {
               GameScreen.this.onceStore.addActionToMovingButton(Actions.sequence(GameScreen.this.onceStore.getMoveOutAction(0.0F)));
            }
         }
         public void switchPane(int var1, int var2) {
            this.itemSelected(var1, -1, -1);
         }
      });
      this.onceStore.setMoveButtonSelectedText("EQUIP");
      this.onceStore.addActionToMovingButton(SetTextAction.setText("play"));
      this.onceStore.moveMovingButton(-66.0F, 0.0F);
      this.playButton = new TextButton("PLAY", var1, "smallGreenButton");
      this.playButton.setPosition(282.0F, -66.0F);
      this.playButton.setTouchable(Touchable.disabled);
      this.playButton.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            if(GameScreen.this.playButton.getTouchable() == Touchable.enabled) {
               GameScreen.this.world.worldAudio.playNormalButton();
               GameScreen.this.ct_readyStateToRunning();
            }
         }
      });
      this.onceStoreGroup.addActor(this.playButton);
   }

   private void constructPauseGroup(Skin var1) {
      this.pauseGroup.setCullingArea(this.screenArea);
      TextButton var2 = new TextButton("resume", var1, "greenButtonStyle");
      var2.setPosition(127.0F, -159.0F);
      var2.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            GameScreen.this.playNormalButtonSound();
            GameScreen.this.ct_pauseStateToRunning();
            ////////((Lightning)//////((Lightning)GameScreen.this.game)).getPlatFormFunction().closeBanner();
         }
      });
      this.pauseGroup.addActor(var2);
      var2.setTouchable(Touchable.disabled);
      this.resumeButton = var2;
      TextButton var4 = new TextButton("retry", var1, "greenButtonStyle");
      var4.setPosition(127.0F, -246.0F);
      var4.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            GameScreen.this.playNormalButtonSound();
            GameScreen.this.ct_pauseStateToReady();
            GameScreen.this.warningImage.getActions().clear();
            GameScreen.this.warningImage.getColor().a = 0.0F;
            ////////((Lightning)//////((Lightning)GameScreen.this.game)).getPlatFormFunction().closeBanner();
         }
      });
      this.pauseGroup.addActor(var4);
      TextButton var6 = new TextButton("main", var1, "yellowButtonStyle");
      var6.setPosition(127.0F, -333.0F);
      var6.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            GameScreen.this.playNormalButtonSound();
            GameScreen.this.world.fireLevelEndEvent(false);
            ////////((Lightning)//////((Lightning)GameScreen.this.game)).getPlatFormFunction().closeBanner();
            GameScreen.this.gameScreenGoToMain(GameScreen.this.pauseGroup);
         }
      });
      this.pauseGroup.addActor(var6);
   }

   private void constructReadyGroup(Skin var1) {
      this.constructOnceStoreActors(var1);
   }

   private void constructRunningGroup(Skin var1) {
      ImageButton var2 = new ImageButton(var1, "pauseButtonStyle");
      var2.setPosition((float)GameScreen.Const.PAUSEBUTTONX, (float)GameScreen.Const.PAUSEBUTTONY);
      var2.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            GameScreen.this.playButtonSound();
            GameScreen.this.ct_runningStateToPause();
         }
      });
      this.runningGroup.addActor(var2);
      var2.setTouchable(Touchable.disabled);
      this.pauseButton = var2;
      ImageButton var4 = new ImageButton(var1, "launchButtonStyle");
      var4.setPosition((float)GameScreen.Const.LAUNCHBUTTONX, (float)GameScreen.Const.LAUNCHBUTTONY);
      var4.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            GameScreen.this.playButtonSound();
            GameScreen.this.world.boomTheWorld(0);
         }
      });
      this.runningGroup.addActor(var4);
      Image var6 = new Image(var1.getDrawable("launch_logo"));
      var6.setPosition(410.0F, 48.0F);
      var6.setTouchable(Touchable.disabled);
      this.runningGroup.addActor(var6);
      Image var7 = new Image(var1, "warning");
      var7.setPosition((float)GameScreen.Const.WARNINGX, (float)GameScreen.Const.WARNINGY);
      var7.setTouchable(Touchable.disabled);
      var7.getColor().a = 0.0F;
      this.runningGroup.addActor(var7);
      this.warningImage = var7;
      this.runningGroup.getColor().a = 0.0F;
   }

   private void constructScreenTips(Skin var1) {
      this.lifeImage = new Image(var1.getDrawable("life"));
      this.lifeImage.setPosition(8.0F, 751.0F);
      this.lifeImage.getColor().a = 0.0F;
      this.gameUIStage.addActor(this.lifeImage);
      this.lifeNumLabel = new Label("x" + this.world.getLives(), var1, "numLabel");
      this.lifeNumLabel.setPosition(56.0F, 745.0F);
      this.lifeNumLabel.setColor(this.lifeNumLabel.getStyle().fontColor);
      this.lifeNumLabel.getColor().a = 0.0F;
      this.gameUIStage.addActor(this.lifeNumLabel);
      this.boomImage = new Image(var1.getDrawable("boom_logo"));
      this.boomImage.setPosition((float)GameScreen.Const.BOOMIMAGEX, (float)GameScreen.Const.BOOMIMAGEY);
      this.boomImage.getColor().a = 0.0F;
      this.gameUIStage.addActor(this.boomImage);
      this.boomNumLabel = new Label("x" + this.world.fighter.getBoomNum(), var1, "numLabel");
      this.boomNumLabel.setPosition(56.0F, 700.0F);
      this.boomNumLabel.setColor(this.boomNumLabel.getStyle().fontColor);
      this.boomNumLabel.getColor().a = 0.0F;
      this.gameUIStage.addActor(this.boomNumLabel);
   }

   private void constructSenceTabel(Skin var1) {
      this.showDataTable = new Table();
      this.showDataTable.setBackground(var1.getDrawable("scene"));
      this.showDataTable.setTouchable(Touchable.disabled);
      this.showDataTable.setVisible(false);
      Label.LabelStyle var2 = new Label.LabelStyle(XScreen.getBitmapFont18(), GameScreen.Const.SENCETEXTCOLOR);
      Label var3 = new Label("SCORE", var2);
      var3.setColor(GameScreen.Const.SENCETEXTCOLOR);
      Label var4 = new Label("LEVEL", var2);
      var4.setColor(GameScreen.Const.SENCETEXTCOLOR);
      Label var5 = new Label("coin", var2);
      var5.setColor(GameScreen.Const.SENCETEXTCOLOR);
      this.showDataTable.add((Actor)var3).width(var3.getWidth()).minWidth(90.0F).bottom().padLeft(8.0F);
      this.showDataTable.add((Actor)var4).expand().bottom();
      this.showDataTable.add((Actor)var5).width(var5.getWidth()).minWidth(90.0F).bottom().padRight(8.0F);
      this.showDataTable.row();
      this.scoreNumLabel = new Label("" + WorldEvents.levelScore, var2);
      this.scoreNumLabel.setColor(GameScreen.Const.SENCETEXTCOLOR);
      this.levelTable = new Table();
      this.levelContentLabel = new Label("--", var2);
      this.levelContentLabel.setColor(GameScreen.Const.SENCETEXTCOLOR);
      Image[] var10 = new Image[]{new Image(var1.getDrawable("stats_level_normal")), new Image(var1.getDrawable("stats_level_normal")), new Image(var1.getDrawable("stats_level_normal")), new Image(var1.getDrawable("stats_level_normal")), new Image(var1.getDrawable("stats_level_normal"))};
      this.starImages = var10;
      this.coinNumLabel = new Label("" + WorldEvents.levelMoney, var2);
      this.coinNumLabel.setColor(GameScreen.Const.SENCETEXTCOLOR);
      this.showDataTable.add((Actor)this.scoreNumLabel).padLeft(8.0F).padBottom(7.0F);
      this.showDataTable.add((Actor)this.levelTable).expand().fill().padBottom(7.0F);
      this.showDataTable.add((Actor)this.coinNumLabel).padRight(8.0F).padBottom(7.0F);
      this.showDataTable.setPosition(98.0F, -8.0F);
      this.showDataTable.pack();
      this.gameUIStage.addActor(this.showDataTable);
   }

   private void constructStoreActors(Skin var1) {
      this.storeActors = new UIStoreActorGroup(var1, 0);
      this.storeActors.addPaneToGroup(this.storeGroup);
      this.storeGroup.setWidth(480.0F);
      this.storeGroup.setHeight(800.0F);
      this.storeGroup.setVisible(false);
      this.storeGroup.translate(0.0F, -800.0F);
      this.storeActors.setMoveButtonInterface(this);
      this.storeActors.setItemSelectedFuns(new UIStoreActorGroup.ItemSelected() {
         public void itemSelected(int var1, int var2, int var3) {
            GameScreen.this.world.worldAudio.playNormalButton();
            if(var2 == -1) {
               GameScreen.this.storeActors.moveMovingButtonOut(0.0F);
            } else {
               Object var4;
               if(GameScreen.this.storeActors.moveButtonIsOut()) {
                  var4 = Actions.delay(0.0F);
               } else {
                  var4 = GameScreen.this.storeActors.getMoveOutAction(0.0F);
               }

               Action var5 = GameScreen.this.storeActors.getMoveInAction(0.0F);
               GameScreen.this.storeActors.addActionToMovingButton(Actions.sequence((Action)var4, var5));
            }
         }
         public void switchPane(int var1, int var2) {
            this.itemSelected(var1, -1, -1);
         }
      });
      this.storeActors.setMoveButtonSelectedText("upgrade");
   }

   private void constructVectoryGroup(Skin var1) {
      this.vectoryGroup.setCullingArea(this.screenArea);
      TextButton var2 = new TextButton("next", var1, "greenButtonStyle");
      var2.setPosition(127.0F, -159.0F);
      var2.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            GameScreen.this.playNormalButtonSound();
            GameScreen.comFromWhere = 1;
            GameScreen.this.world.levelEndDoSomeThing();
            //////((Lightning)GameScreen.this.game).getPlatFormFunction().closeBanner();
            Action var3 = new Action() {
               public boolean act(float var1) {
                  GameScreen.gameScreenInfo.currentScreen = null;
                  GameScreen.level = 1 + GameScreen.this.world.level;
                  GameScreen.this.game.setScreen(new LoadingScreen(GameScreen.this.game, GameScreen.gameScreenInfo, 0.0F, true));
                  return true;
               }
            };
            GameScreen.this.moveBottomDown(0.9166667F);
            GameScreen.this.moveUpperUp();
            GameScreen.this.moveVGroupAndTable(false, 0.0F);
            GameScreen.this.bottomGroup.addAction(Actions.after(var3));
         }
      });
      this.vectoryGroup.addActor(var2);
      TextButton var4 = new TextButton("store", var1, "greenButtonStyle");
      var4.setPosition(127.0F, -246.0F);
      var4.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            GameScreen.this.playNormalButtonSound();
            GameScreen.this.showStore(0.75F);
            GameScreen.this.moveVGroupAndTable(false, 0.0F);
            //////((Lightning)GameScreen.this.game).getPlatFormFunction().closeBanner();
         }
      });
      this.vectoryGroup.addActor(var4);
      TextButton var6 = new TextButton("main", var1, "yellowButtonStyle");
      var6.setPosition(127.0F, -333.0F);
      var6.addListener(new ChangeListener() {
         public void changed(ChangeListener.ChangeEvent var1, Actor var2) {
            GameScreen.this.playNormalButtonSound();
            //////((Lightning)GameScreen.this.game).getPlatFormFunction().closeBanner();
            GameScreen.this.gameScreenGoToMain(GameScreen.this.vectoryGroup);
         }
      });
      this.vectoryGroup.addActor(var6);
   }

   private void constructWarningSound() {
      this.playSounds = new GameScreen.PlayWarningSound[5];

      for(int var1 = 0; var1 < this.playSounds.length; ++var1) {
         this.playSounds[var1] = new GameScreen.PlayWarningSound(this.world.worldAudio);
      }

   }

   private void contructTopGroup() {
      this.upperGroup.setPosition(0.0F, 821.0F);
      Image var1 = new Image(skin.getDrawable("cockpit_top"));
      var1.setPosition(0.0F, 0.0F);
      this.upperGroup.addActor(var1);
      Image var2 = new Image(skin.getDrawable("wireframe"));
      var2.setPosition(202.0F, 49.0F);
      var2.addAction(Actions.forever(Actions.rotateBy(-360.0F, 5.0F)));
      var2.setOrigin(37.5F, 37.5F);
      this.upperGroup.addActor(var2);
      this.logoDrawables[0] = skin.getDrawable("pasue");
      this.logoDrawables[1] = skin.getDrawable("wing_logo");
      this.logoDrawables[2] = skin.getDrawable("gameover_logo");
      this.logoImage = new Image(this.logoDrawables[0]);
      this.logoImage.setWidth(199.0F);
      this.logoImage.setScaling(Scaling.none);
      this.logoImage.setPosition(140.0F, 143.0F);
      this.upperGroup.addActor(this.logoImage);
      Image var3 = new Image(this.world.fighter.getFighterAnimation());
      var3.setPosition(219.0F, 56.0F);
      var3.setOrigin(var3.getWidth() / 2.0F, var3.getHeight() / 2.0F);
      var3.setScale(0.7F);
      var3.getColor().a = 0.8F;
      this.upperGroup.addActor(var3);
      this.upperGroup.setWidth(var1.getWidth());
      this.upperGroup.setHeight(var1.getHeight());
   }

   private void ct_gameoverToReady() {
      if(this.state == 4) {
         this.moveGOGroupAndTable(false, 0.0F);
         this.changeStateTo.goalState = 0;
         this.bottomGroup.addAction(Actions.after(this.changeStateTo));
         this.world.reStartLevel();
         this.showReadyGroup();
      }

   }

   private void ct_pauseStateToReady() {
      if(this.state == 2) {
         this.movePGroupAndTable(false);
         this.changeStateTo.goalState = 0;
         this.bottomGroup.addAction(Actions.after(this.changeStateTo));
         this.world.fireLevelEndEvent(false);
         this.world.reStartLevel();
         this.showReadyGroup();
      }

   }

   private void ct_pauseStateToRunning() {
      if(this.state == 2) {
         this.world.resumeMusic();
         this.runningGroup.setTouchable(Touchable.childrenOnly);
         this.movePGroupAndTable(false);
         this.moveUpperUp();
         this.moveBottomDown(0.9166667F);
         this.changeStateTo.goalState = 1;
         this.showDataTable.addAction(Actions.after(this.changeStateTo));
         this.runningActorsFadeIn(1.0F);
      }

   }

   private void ct_readyStateToRunning() {
      this.world.fighter.oncePropHandle();
      this.moveBottomDown(0.9166667F);
      this.moveUpperUp();
      this.moveStoreDown();
      this.moveOnceStoreDown(0.0F);
      this.showLoading(1.25F, 0.7F);
      this.loadingGroup.addAction(Actions.after(new Action() {
         public boolean act(float var1) {
            if(GameScreen.this.state == 0) {
               GameScreen.this.state = 1;
               GameScreen.this.readyGroup.setVisible(false);
               GameScreen.this.runningGroup.setTouchable(Touchable.childrenOnly);
               GameScreen.this.runningActorsFadeIn(1.0F);
            }

            return true;
         }
      }));
   }

   private void ct_runningStateToGameOver() {
      if(this.state == 1) {
         this.state = 4;
         this.logoImage.setDrawable(this.logoDrawables[2]);
         this.runningGroup.setTouchable(Touchable.disabled);
         this.fillShowDataTable(false);
         this.moveGOGroupAndTable(true, 0.0F);
         this.moveBottomUp();
         this.moveUpperDown();
         this.runningActorsFadeOut();
         //////((Lightning)//////((Lightning)this.game)).getPlatFormFunction().showBanner();
         this.warningImage.clearActions();
      }

   }

   private void ct_runningStateToPause() {
      if(this.state == 1) {
         this.state = 2;
         this.world.stopMusic();
         this.logoImage.setDrawable(this.logoDrawables[0]);
         this.runningGroup.setTouchable(Touchable.disabled);
         this.movePGroupAndTable(true);
         this.fillShowDataTable(false);
         this.moveUpperDown();
         this.moveBottomUp();
         this.runningActorsFadeOut();
         //////((Lightning)//////((Lightning)this.game)).getPlatFormFunction().showBanner();
      }

   }

   private void ct_runningStateToVectory() {
      if(this.state == 1) {
         this.state = 3;
         this.logoImage.setDrawable(this.logoDrawables[1]);
         this.runningGroup.setTouchable(Touchable.disabled);
         this.fillShowDataTable(true);
         this.moveVGroupAndTable(true, 0.0F);
         this.moveBottomUp();
         this.moveUpperDown();
         this.runningActorsFadeOut();
         if(Gdx.app.getType()==ApplicationType.Android){
        	 ((NativeService)Gdx.app).showAds();
        	 ((ActionResolver)Gdx.app).submitScoreGPGS((int) Settings.score);
         }
         //////((Lightning)//////((Lightning)this.game)).getPlatFormFunction().showBanner();
      }

   }

   private void ct_vectoryGroupGotoStage() {
      ((TextButton)this.vectoryGroup.getChildren().get(0)).addAction(Actions.moveTo(127.0F, 424.0F, 0.5F, Interpolation.pow2Out));
      ((TextButton)this.vectoryGroup.getChildren().get(1)).addAction(Actions.delay(0.083333336F, Actions.moveTo(127.0F, 337.0F, 0.5F, Interpolation.pow2Out)));
      ((TextButton)this.vectoryGroup.getChildren().get(2)).addAction(Actions.delay(0.16666667F, Actions.moveTo(127.0F, 250.0F, 0.5F, Interpolation.pow2Out)));
   }

   private void draw(float var1) {
      GLCommon var2 = Gdx.gl;
      Color var3 = this.clsColors[-1 + this.world.level];
      var2.glClearColor(var3.r, var3.g, var3.b, var3.a);
      var2.glClear(16384);
      this.batcher.setColor(1.0F, 1.0F, 1.0F, 1.0F);
      this.worldRender.render(var1);
      this.guiCam.update();
      this.batcher.setProjectionMatrix(this.guiCam.combined);
      this.batcher.enableBlending();
      this.batcher.begin();
      switch(this.state) {
      case 0:
         this.presentReady();
         break;
      case 1:
         this.presentRunning();
         break;
      case 2:
         this.presentPaused();
         break;
      case 3:
         this.presentLevelEnd();
         break;
      case 4:
         this.presentGameOver();
      }

      this.batcher.end();
      this.gameUIStage.draw();
      this.gameUIStage.getCamera().update();
      this.batcher.setProjectionMatrix(this.gameUIStage.getCamera().combined);
      this.batcher.begin();
      WorldAnimationPlayer.renderStageAnimation(this.batcher);
      this.batcher.end();
   }

   private void drawTips() {
      XScreen.getBitmapFont18().draw(this.batcher, "totalMoney  " + Settings.money, 0.0F, (float)40);
      int var2 = 40 + 20;
      XScreen.getBitmapFont18().draw(this.batcher, "totalScore  " + (new DecimalFormat(".0")).format(Settings.score), 0.0F, (float)var2);
      int var4 = var2 + 20;
      XScreen.getBitmapFont18().draw(this.batcher, "levelScore  " + WorldEvents.levelScore, 0.0F, (float)var4);
      int var6 = var4 + 20;
      XScreen.getBitmapFont18().draw(this.batcher, "levelMoney  " + WorldEvents.levelMoney, 0.0F, (float)var6);
      int var8 = var6 + 20;
      XScreen.getBitmapFont18().draw(this.batcher, "enemyKilledCount  " + WorldEvents.levelEnemyKilled, 0.0F, (float)var8);
      int var10 = var8 + 20;
      XScreen.getBitmapFont18().draw(this.batcher, "levelEnemyCount  " + WorldEvents.levelEnemyCount, 0.0F, (float)var10);
      int var12 = var10 + 20;
      XScreen.getBitmapFont18().draw(this.batcher, "boomUseCount  " + WorldEvents.levelBoomCount, 0.0F, (float)var12);
      int var14 = var12 + 20;
      XScreen.getBitmapFont18().draw(this.batcher, "FPS" + Gdx.graphics.getFramesPerSecond(), 0.0F, (float)var14);
      int var16 = var14 + 20;
      XScreen.getBitmapFont18().draw(this.batcher, "roundCount " + Settings.lapCount, 0.0F, (float)var16);
      int var10000 = var16 + 20;
      float var19 = XScreen.getBitmapFont48().getColor().toFloatBits();
      XScreen.getBitmapFont48().setColor(1.0F, 0.0F, 0.0F, 0.5F);
      XScreen.getBitmapFont48().draw(this.batcher, bossHealthyDegree + "", 0.0F, 400.0F);
      XScreen.getBitmapFont48().setColor(var19);
   }

   private void fillShowDataTable(boolean var1) {
      this.coinNumLabel.setText("" + WorldEvents.levelMoney);
      this.scoreNumLabel.setText("" + WorldEvents.levelScore);
      this.levelTable.clear();
      if(var1) {
         int var3 = this.world.evaluateStar();

         for(int var4 = 0; var4 < var3; ++var4) {
            this.levelTable.add((Actor)this.starImages[var4]);
         }
      } else {
         this.levelTable.add((Actor)this.levelContentLabel);
      }

   }

   private void gameScreenGoToMain(Group var1) {
      Action var2 = new Action() {
         public boolean act(float var1) {
            UIScreen.initState = 0;
            GameScreen.this.game.setScreen(new LoadingScreen(GameScreen.this.game, UIScreen.info, 0.0F, false));
            return true;
         }
      };
      this.moveBottomDown(0.9166667F);
      this.moveUpperUp();
      this.moveGroupAndSenceTable(false, var1, 0.0F);
      this.bottomGroup.addAction(Actions.after(var2));
   }

   private static Skin getGameUISkin() {
      skin = new Skin();
      skin.addRegions(Assets_ShareInAllLevel.atlasGameUI);
      skin.addRegions(loadingAtlas);
      loadingAtlas = null;
      TextButton.TextButtonStyle var0 = new TextButton.TextButtonStyle();
      var0.font = XScreen.getBitmapFont36();
      var0.down = skin.getDrawable("green_button_small");
      var0.up = skin.getDrawable("green_button_small");
      var0.downFontColor = Color.WHITE;
      var0.fontColor = Color.GREEN;
      skin.add("smallGreenButton", var0);
      TextButton.TextButtonStyle var1 = new TextButton.TextButtonStyle();
      var1.up = skin.getDrawable("green_button");
      var1.font = XScreen.getBitmapFont36();
      var1.font.setScale(1.0F);
      var1.fontColor = GameScreen.Const.FONTCOLOR;
      var1.downFontColor = Color.WHITE;
      skin.add("greenButtonStyle", var1);
      ImageButton.ImageButtonStyle var2 = new ImageButton.ImageButtonStyle();
      var2.up = skin.newDrawable("pause_normal");
      var2.down = skin.newDrawable("pause_press");
      skin.add("pauseButtonStyle", var2);
      ImageButton.ImageButtonStyle var3 = new ImageButton.ImageButtonStyle();
      var3.up = skin.newDrawable("launch_normal");
      var3.down = new SpriteDrawable(new TextureAtlas.AtlasSprite(Assets_ShareInAllLevel.atlasGameUI.findRegion("launch_press", 0)));
      skin.add("launchButtonStyle", var3);
      TextureRegion[] var4 = new TextureRegion[]{Assets_ShareInAllLevel.atlasGameUI.findRegion("launch_press", 0), Assets_ShareInAllLevel.atlasGameUI.findRegion("launch_press", 1), Assets_ShareInAllLevel.atlasGameUI.findRegion("launch_press", 2)};
      GameScreen.Const.launchAnimation = new Animation(1, 0.083333336F, var4);
      TextButton.TextButtonStyle var5 = new TextButton.TextButtonStyle();
      var5.up = skin.getDrawable("yellow_button");
      var5.font = XScreen.getBitmapFont48();
      var5.font.setScale(1.0F);
      var5.fontColor = GameScreen.Const.YELLOWFONTCOLOR;
      var5.downFontColor = Color.WHITE;
      skin.add("yellowButtonStyle", var5);
      Label.LabelStyle var6 = new Label.LabelStyle();
      var6.font = XScreen.getBitmapFont24();
      var6.fontColor = GameScreen.Const.FONTCOLOR;
      skin.add("numLabel", var6);
      UIStoreActorGroup.addResource(skin, Assets_ShareInAllLevel.atlasGameUI);
      return skin;
   }

   private void hideStore() {
      this.moveStoreDown();
      this.moveBottomUp();
      this.moveUpperDown();
      this.bottomShowRateButton();
   }

   private void moveBottomDown(float var1) {
      this.bottomGroup.addAction(Actions.sequence(Actions.touchable(Touchable.disabled), Actions.delay(var1), Actions.moveTo(0.0F, -300.0F, 0.33333334F, Interpolation.pow2Out), Actions.hide()));
   }

   private void moveBottomToScreenBottom(float var1) {
      this.bottomGroup.addAction(Actions.delay(var1, Actions.moveTo(0.0F, -100.0F, 0.33333334F, Interpolation.pow2Out)));
   }

   private void moveBottomUp() {
      if(Settings.isSmallScreen()) {
         this.musicButton.setY(103.0F);
         this.soundButton.setY(103.0F);
      }

      this.bottomGroup.addAction(Actions.sequence(Actions.touchable(Touchable.childrenOnly), Actions.show(), Actions.moveTo(0.0F, 0.0F, 0.33333334F, Interpolation.pow2Out)));
   }

   private void moveGOGroupAndTable(boolean var1, float var2) {
      this.moveGroupAndSenceTable(var1, this.gameOverGroup, var2);
   }

   private void moveGroupAndSenceTable(boolean var1, Group var2, float var3) {
      var2.setVisible(true);
      Touchable var4;
      if(var1) {
         var4 = Touchable.childrenOnly;
      } else {
         var4 = Touchable.disabled;
      }

      var2.setTouchable(var4);
      TextButton var5 = (TextButton)var2.getChildren().get(0);
      TextButton var6 = (TextButton)var2.getChildren().get(1);
      TextButton var7 = (TextButton)var2.getChildren().get(2);
      byte var8;
      if(var1) {
         var8 = 1;
      } else {
         var8 = 3;
      }

      byte var9;
      if(var1) {
         var9 = 1;
      } else {
         var9 = -1;
      }

      this.moveSenceTable(var1, var3);
      float var10;
      if(var1) {
         var10 = 424.0F;
      } else {
         var10 = -159.0F;
      }

      this.addActionToButton(var1, var5, var3 + 0.083333336F * (float)var8, 127.0F, var10);
      int var11 = var8 + var9;
      float var12;
      if(var1) {
         var12 = 337.0F;
      } else {
         var12 = -246.0F;
      }

      this.addActionToButton(var1, var6, var3 + 0.083333336F * (float)var11, 127.0F, var12);
      int var13 = var11 + var9;
      float var14;
      if(var1) {
         var14 = 250.0F;
      } else {
         var14 = -333.0F;
      }

      this.addActionToButton(var1, var7, var3 + 0.083333336F * (float)var13, 127.0F, var14);
   }

   private void moveOnceStoreDown(float var1) {
      if(this.onceStoreGroup.isVisible()) {
         this.onceStoreGroup.addAction(Actions.sequence(Actions.delay(0.4F + var1), Actions.moveTo(0.0F, -800.0F, 0.5F, Interpolation.pow2Out), Actions.hide()));
         this.onceStore.moveMovingButtonOut(var1);
         this.playButton.addAction(Actions.sequence(Actions.touchable(Touchable.disabled), FontScaleAction.fontScaleOut(0.16666667F), Actions.moveTo(282.0F, -66.0F, 0.2F, Interpolation.pow2Out)));
      }
   }

   private void moveOnceStoreUp(float var1) {
      if(!this.onceStoreGroup.isVisible()) {
         this.onceStore.updateTitleMoneyLabel();
         this.onceStoreGroup.addAction(Actions.sequence(Actions.show(), Actions.delay(var1), Actions.moveTo(0.0F, 0.0F, 0.5F, Interpolation.pow2Out)));
         float var2 = var1 + 0.4F;
         this.onceStore.upateAllCoinItem();
         this.playButton.addAction(Actions.parallel(Actions.sequence(Actions.delay(var2), Actions.moveTo(282.0F, 180.0F, 0.2F, Interpolation.pow2Out)), Actions.sequence(FontScaleAction.setFontScale0(var2 + 0.2F), FontScaleAction.fontScaleIn(0.16666667F), Actions.touchable(Touchable.enabled))));
      }
   }

   private void movePGroupAndTable(boolean var1) {
      this.moveGroupAndSenceTable(var1, this.pauseGroup, 0.0F);
   }

   private void moveSenceTable(boolean var1, float var2) {
      if(this.showDataTable.getActions().size == 0) {
         if(var1) {
            this.showDataTable.addAction(Actions.sequence(Actions.delay(var2), Actions.show(), Actions.moveTo(98.0F, 500.0F, 0.5F, Interpolation.pow2Out)));
         } else {
            this.showDataTable.addAction(Actions.sequence(Actions.delay(0.4166667F + var2, Actions.moveTo(98.0F, -8.0F, 0.5F, Interpolation.pow2Out)), Actions.hide()));
         }
      }
   }

   private void moveStoreDown() {
      this.storeGroup.addAction(Actions.sequence(Actions.moveTo(0.0F, -800.0F, 0.4F, Interpolation.pow2Out), Actions.hide()));
   }

   private void moveStoreUp(float var1) {
      this.storeActors.updateTitleMoneyLabel();
      this.storeGroup.addAction(Actions.sequence(Actions.show(), Actions.delay(var1), Actions.moveTo(0.0F, 0.0F, 0.2F, Interpolation.pow2Out)));
   }

   private void moveUpperDown() {
      this.upperGroup.addAction(Actions.sequence(Actions.show(), Actions.moveTo(0.0F, 541.0F, 0.33333334F, Interpolation.pow2Out)));
   }

   private void moveUpperUp() {
      this.upperGroup.addAction(Actions.sequence(Actions.delay(0.9166667F), Actions.moveTo(0.0F, 821.0F, 0.33333334F, Interpolation.pow2Out), Actions.hide()));
   }

   private void moveVGroupAndTable(boolean var1, float var2) {
      byte var3 = 2;
      byte var4 = 1;
      Group var5 = this.vectoryGroup;
      if(this.world.level == Settings.getMaxLevelId()) {
         var5.setVisible(true);
         Touchable var6;
         if(var1) {
            var6 = Touchable.childrenOnly;
         } else {
            var6 = Touchable.disabled;
         }

         var5.setTouchable(var6);
         TextButton var7 = (TextButton)var5.getChildren().get(var4);
         TextButton var8 = (TextButton)var5.getChildren().get(var3);
         if(var1) {
            var3 = var4;
         }

         if(!var1) {
            var4 = -1;
         }

         this.moveSenceTable(var1, var2);
         float var9;
         if(var1) {
            var9 = 424.0F;
         } else {
            var9 = -159.0F;
         }

         var7.addAction(Actions.delay(var2 + 0.083333336F * (float)var3, Actions.moveTo(127.0F, var9, 0.5F, Interpolation.pow2Out)));
         int var10 = var3 + var4;
         float var11;
         if(var1) {
            var11 = 337.0F;
         } else {
            var11 = -246.0F;
         }

         var8.addAction(Actions.delay(var2 + 0.083333336F * (float)var10, Actions.moveTo(127.0F, var11, 0.5F, Interpolation.pow2Out)));
      } else {
         this.moveGroupAndSenceTable(var1, this.vectoryGroup, var2);
      }
   }

   private void presentGameOver() {}

   private void presentLevelEnd() {}

   private void presentPaused() {}

   private void presentReady() {}

   private void presentRunning() {}

   private void runningActorsFadeIn(float var1) {
      this.lifeNumLabel.addAction(Actions.fadeIn(var1));
      this.lifeNumLabel.addAction(FontScaleAction.setFontScale1(2.0F));
      this.lifeImage.addAction(Actions.fadeIn(var1));
      this.boomNumLabel.addAction(Actions.fadeIn(var1));
      this.boomNumLabel.addAction(FontScaleAction.setFontScale1(2.0F));
      this.boomImage.addAction(Actions.fadeIn(var1));
      this.runningGroup.addAction(Actions.sequence(Actions.show(), Actions.fadeIn(var1)));
      this.pauseButton.addAction(Actions.sequence(Actions.delay(var1), Actions.touchable(Touchable.enabled)));
   }

   private void runningActorsFadeOut() {
      this.lifeNumLabel.addAction(Actions.fadeOut(1.0F));
      this.lifeNumLabel.addAction(FontScaleAction.setFontScale1(2.0F));
      this.lifeImage.addAction(Actions.fadeOut(1.0F));
      this.boomNumLabel.addAction(Actions.fadeOut(1.0F));
      this.boomNumLabel.addAction(FontScaleAction.setFontScale1(2.0F));
      this.boomImage.addAction(Actions.fadeOut(1.0F));
      this.runningGroup.addAction(Actions.sequence(Actions.fadeOut(1.0F), Actions.hide()));
      this.pauseButton.setTouchable(Touchable.disabled);
   }

   private void showLoading(float var1, float var2) {
      Group var3 = this.loadingGroup;
      Action[] var4 = new Action[]{Actions.delay(var1), this.stopMusicAction, Actions.touchable(Touchable.enabled), Actions.show(), Actions.delay(var2), Actions.hide(), Actions.touchable(Touchable.disabled), this.resumeMusicAction};
      var3.addAction(Actions.sequence(var4));
   }

   private void showReadyGroup() {
      this.readyGroup.setVisible(true);
      this.moveBottomToScreenBottom(0.9166667F);
      this.moveUpperUp();
      this.moveOnceStoreUp(0.9166667F);
   }

   private void showStore(float var1) {
      this.storeActors.clearSelectedIndex();
      this.storeActors.moveMovingButtonOut(0.0F);
      this.storeActors.switchPaneToCoinPane();
      this.moveStoreUp(var1);
      this.moveUpperUp();
      this.moveBottomToScreenBottom(0.9166667F);
      if(this.state != 0) {
         this.bottomShowBackButton();
      }

   }

   private void storeBack() {
      this.hideStore();
      if(this.state == 4) {
         this.moveGOGroupAndTable(true, 0.4F);
      }

      if(this.state == 3) {
         this.moveVGroupAndTable(true, 0.4F);
      }

      //////((Lightning)this.game).getPlatFormFunction().showBanner();
   }

   private void update(float var1) {
      switch(this.state) {
      case 0:
         this.updateReady();
         break;
      case 1:
         this.updateRunning(var1);
         break;
      case 2:
         this.updatePaused();
         break;
      case 3:
         this.updateLevelEnd();
         break;
      case 4:
         this.updateGameOver();
      }
      try {
      this.gameUIStage.act(var1);
      } catch(Exception e){}
   }

   private void updateGameOver() {}

   private void updateLevelEnd() {}

   private void updatePaused() {
      if(Gdx.input.getInputProcessor() != this.gameUIStage) {
         Gdx.input.setInputProcessor(this.gameUIStage);
         Gdx.input.setCatchBackKey(true);
      }

   }

   private void updateReady() {
      if(Gdx.input.getInputProcessor() != this.gameUIStage) {
         Gdx.input.setInputProcessor(this.gameUIStage);
      }

   }

   private void updateRunning(float var1) {
      if(Gdx.input.getInputProcessor() != this.mutiPro) {
         Gdx.input.setCatchBackKey(true);
         Gdx.input.setInputProcessor(this.mutiPro);
         this.runningPro.reset();
      }

      this.world.update(var1, this.deltaVector2.set(this.runningPro.getDeltaX(), this.runningPro.getDeltaY()));
      if(this.world.state == 1) {
         this.ct_runningStateToVectory();
      }

      if(this.world.state == 2) {
         this.ct_runningStateToGameOver();
      }

      this.lifeNumLabel.setText("x" + this.world.getLives());
      this.boomNumLabel.setText("x" + this.world.fighter.getBoomNum());
   }

   public void buttonClicked(int var1, int var2) {}

   public void dispose() {}

   public void hide() {}

   public void pause() {
      this.loadingGroup.setVisible(true);
      this.loadingGroup.setTouchable(Touchable.enabled);
      Settings.i("pause is executing.");
   }

   public void playBackButtonSound() {
      this.world.worldAudio.playBackButton();
   }

   public void playBoomLaunchAnimation() {
      Animation var1 = Assets_ShareInAllLevel.launchAnimation;
      WorldAnimationPlayer.addStageAnimation(var1, (float)GameScreen.Const.LAUNCHBUTTONX + (float)RegionUtilFunctions.getRegionWidth(var1) / 2.0F, (float)GameScreen.Const.LAUNCHBUTTONY + (float)RegionUtilFunctions.getRegionHeight(var1) / 2.0F);
   }

   public void playButtonSound() {
      this.playNormalButtonSound();
   }

   public void playNormalButtonSound() {
      this.world.worldAudio.playNormalButton();
   }

   public void render(float var1) {
      float var2 = Settings.fixDelta(var1);
      this.update(var2);
      this.draw(var2);
   }

   public void resize(int var1, int var2) {}

   public void resume() {
      this.loadingGroup.setVisible(true);
      this.loadingGroup.setTouchable(Touchable.enabled);
      float var1;
      if(Settings.isBadPermancePhone()) {
         var1 = 5.0F;
      } else {
         var1 = 3.0F;
      }

      this.showLoading(0.0F, var1);
      this.loadingGroup.addAction(Actions.after(this.showBannerAction));
      this.ct_runningStateToPause();
      //////((Lightning)//////((Lightning)this.game)).getPlatFormFunction().closeBanner();
   }

   public void show() {
      if(this.state == 3) {
         this.world.generateNextLevel();
         this.state = 1;
      }

   }

   public void showWarning(int var1) {
      if(!this.worldIsWarning) {
         if(var1 == 0) {
            this.warningImage.setDrawable(skin.getDrawable("warning_s"));
         }

         if(var1 == 1) {
            this.warningImage.setDrawable(skin.getDrawable("warning"));
         }

         this.worldIsWarning = true;
         Image var2 = this.warningImage;
         Action[] var3 = new Action[]{this.playSounds[0], Actions.fadeIn(0.33333334F), Actions.delay(0.33333334F), Actions.fadeOut(0.33333334F), Actions.delay(0.33333334F), this.playSounds[1], Actions.fadeIn(0.33333334F), Actions.delay(0.33333334F), Actions.fadeOut(0.33333334F), Actions.delay(0.33333334F), this.playSounds[2], Actions.fadeIn(0.33333334F), Actions.delay(0.33333334F), Actions.fadeOut(0.33333334F), Actions.delay(0.33333334F), this.playSounds[3], Actions.fadeIn(0.33333334F), Actions.delay(0.33333334F), Actions.fadeOut(0.33333334F), Actions.delay(0.33333334F), this.playSounds[4], Actions.fadeIn(0.33333334F), Actions.delay(0.33333334F), Actions.fadeOut(0.33333334F), Actions.delay(0.33333334F, this.warningOverAction)};
         var2.addAction(Actions.sequence(var3));
      }
   }

   public boolean warningIsOver() {
      return !this.worldIsWarning;
   }

   private static class PlayWarningSound extends Action {

      WorldAudio worldAudio;


      public PlayWarningSound(WorldAudio var1) {
         this.worldAudio = var1;
      }

      public boolean act(float var1) {
         if(this.worldAudio.world.getParentScreen().state == 2) {
            return false;
         } else {
            this.worldAudio.playWarning();
            return true;
         }
      }
   }

   private class StateChangeAction extends Action {

      int goalState;


      private StateChangeAction() {
         this.goalState = 0;
      }

      public boolean act(float var1) {
         GameScreen.this.state = this.goalState;
         return true;
      }
   }

   static class GameScreenInfo implements LoadingScreen.LoadingInfo {

      public Assets_Audio.SoundLoadingInfo audioLoadingInfo = null;
      public GameScreen currentScreen = null;


      public void fillAssetManager(AssetManager var1) {
         Assets_fighter.info.fillAssetManager(var1);
         Assets_ShareInAllLevel.info.fillAssetManager(var1);
         Assets_ShareInAlien.info.fillAssetManager(var1);
         Assets_ShareInNazi.info.fillAssetManager(var1);
         if(GameScreen.level < 1 || GameScreen.level > 7) {
            GameScreen.level = 1;
         }

         GameScreen.infos[-1 + GameScreen.level].fillAssetManager(var1);
         Assets_Props.info.fillAssetManager(var1);
         this.audioLoadingInfo = new Assets_Audio.SoundLoadingInfo(GameScreen.level);
         this.audioLoadingInfo.fillAssetManager(var1);
         var1.load("loading/loadingWithHelp.atlas", TextureAtlas.class);
      }

      public Screen loadingFinished(Game var1, AssetManager var2) {
         BGfixer.LevelActionAdder.levelActionAdder(GameScreen.level);
         Assets_fighter.info.loadingFinished(var1, var2);
         Assets_ShareInAllLevel.info.loadingFinished(var1, var2);
         Assets_ShareInAlien.info.loadingFinished(var1, var2);
         Assets_ShareInNazi.info.loadingFinished(var1, var2);
         if(GameScreen.level < 1 || GameScreen.level > 7) {
            GameScreen.level = 1;
         }

         GameScreen.infos[-1 + GameScreen.level].loadingFinished(var1, var2);
         Assets_Props.info.loadingFinished(var1, var2);
         this.audioLoadingInfo.loadingFinished(var1, var2);
         GameScreen.loadingAtlas = (TextureAtlas)var2.get("loading/loadingWithHelp.atlas", TextureAtlas.class);
         PropDropHandler.loadSMBData(GameScreen.level);
         ScoreData.loadScoreTable();
         return new GameScreen(var1, GameScreen.level);
      }
   }

   private class GameScreenInputProcessor extends InputAdapter {

      float deltaX;
      float deltaY;
      boolean isDragging;
      Vector2 lastTouchPoint;
      int pointer;
      Vector3 temp;
      GameScreen wThis;


      private GameScreenInputProcessor() {
         this.pointer = -1;
         this.isDragging = false;
         this.temp = new Vector3();
         this.lastTouchPoint = new Vector2();
         this.wThis = GameScreen.this;
      }

      public float getDeltaX() {
         float var1 = this.deltaX;
         this.deltaX = 0.0F;
         return var1;
      }

      public float getDeltaY() {
         float var1 = this.deltaY;
         this.deltaY = 0.0F;
         return var1;
      }

      public void reset() {
         this.deltaX = 0.0F;
         this.deltaY = 0.0F;
         this.pointer = -1;
         this.isDragging = false;
      }

      public boolean touchDown(int var1, int var2, int var3, int var4) {
         if(this.pointer == -1 && var4 == 0) {
            this.pointer = var3;
            this.isDragging = true;
            this.wThis.guiCam.unproject(this.temp.set((float)var1, (float)var2, 0.0F));
            this.lastTouchPoint.set(this.temp.x, this.temp.y);
            return true;
         } else {
            return false;
         }
      }

      public boolean touchDragged(int var1, int var2, int var3) {
         if(this.pointer == var3 && this.isDragging) {
            this.wThis.guiCam.unproject(this.temp.set((float)var1, (float)var2, 0.0F));
            float var4 = (this.temp.x - this.lastTouchPoint.x) / 1.0F;
            float var5 = (this.temp.y - this.lastTouchPoint.y) / 1.0F;
            this.lastTouchPoint.set(this.temp.x, this.temp.y);
            this.deltaX += var4;
            this.deltaY += var5;
            return false;
         } else {
            return false;
         }
      }

      public boolean touchUp(int var1, int var2, int var3, int var4) {
         if(this.pointer != var3 && this.pointer != -1) {
            return false;
         } else {
            this.pointer = -1;
            this.isDragging = false;
            return false;
         }
      }
   }

   public static class Const {

      public static int BOOMIMAGEX = 8;
      public static int BOOMIMAGEY = 706;
      public static final int BOOMLABELX = 56;
      public static final int BOOMLABELY = 700;
      public static final int BOTTOMENDY = 0;
      public static final float BOTTOMMOVETIME = 0.33333334F;
      public static final float BOTTOMOUTDELAY = 0.9166667F;
      public static final int BOTTOMSTARTY = -300;
      public static final int BOTTOMX = 0;
      public static final int BUTTON1ENDY = -159;
      public static final int BUTTON1X = 127;
      public static final int BUTTON1Y = 424;
      public static final int BUTTON2ENDY = -246;
      public static final int BUTTON2X = 127;
      public static final int BUTTON2Y = 337;
      public static final int BUTTON3ENDY = -333;
      public static final int BUTTON3X = 127;
      public static final int BUTTON3Y = 250;
      public static final float DELAYTIME = 0.083333336F;
      public static final Color FONTCOLOR = new Color(0.4745098F, 0.91764706F, 0.0F, 1.0F);
      public static final float FONTSCALETIME = 0.16666667F;
      public static int LAUNCHBUTTONX = 363;
      public static int LAUNCHBUTTONY = 8;
      public static final int LIFEIMAGEX = 8;
      public static final int LIFEIMAGEY = 751;
      public static final int LIFTNUMLABELX = 56;
      public static final int LIFTNUMLABELY = 745;
      public static final float MOVETIME = 0.5F;
      public static final int MUSICX = 323;
      public static final int MUSICY = 98;
      public static int PAUSEBUTTONX = 384;
      public static int PAUSEBUTTONY = 704;
      public static final int SENCEENDY = -8;
      public static final Color SENCETEXTCOLOR = new Color(0.0F, 0.6392157F, 0.6392157F, 1.0F);
      public static final int SENCEX = 98;
      public static final int SENCEY = 500;
      public static final int SOUNDX = 391;
      public static final int SOUNDY = 98;
      public static final float STOREINOUTTIME = 0.5F;
      public static final int TIPLABELX = 80;
      public static final int TIPLABELY = 400;
      public static final int UPPERENDY = 541;
      public static final float UPPERMOVETIME = 0.33333334F;
      public static final float UPPEROUTDELAY = 0.9166667F;
      public static final int UPPERSTARTY = 821; 
      public static int WARNINGX = 0;
      public static int WARNINGY = 440;
      public static final Color YELLOWFONTCOLOR = new Color(0.91764706F, 0.627451F, 0.0F, 1.0F);
      public static Animation launchAnimation;


   }
}
