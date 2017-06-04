package com.doodlegames.air.force.enemy.level3;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.level3.BossLv3;
import com.doodlegames.air.force.enemy.level3.BossLv3Cannon;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldAnimationPlayer;
import com.doodlegames.air.force.game.WorldAudio;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.resource.Assets_level3;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;

import java.util.ArrayList;
import java.util.List;

public class BossLv3Building1 extends Enemy {

	public static final Vector2 CANNON1POSITION;
	public static final Vector2 CANNON2POSITION;
	public static final Vector2 CANNON3POSITION;
	private static final int[][] CANNONPOSITION;
	public static final int DEFAULTHEALTHYDEGREE = 180;
	public static int HEIGHT = 0;
	private static int RECNUM = 0;
	private static final float SHADOWDELTA = 25.0F;
	private static final float TIMEOFPLAYCRASHANIMATION = 4.0F;
	public static final int WIDTH;
	private static TextureAtlas.AtlasRegion enemyBroTextureRegion = Assets_level3.atlas_Enemy
			.findRegion("lv3_boss_building_1_broken");
	private static TextureAtlas.AtlasRegion enemyTextureRegion = Assets_level3.atlas_Enemy
			.findRegion("lv3_boss_building", 1);
	private static Rectangle[] relativeHitRectangles;
	private BossLv3.BuildingState buildingState;
	public BossLv3Cannon cannon1;
	public BossLv3Cannon cannon2;
	public BossLv3Cannon cannon3;
	private Vector2 canon1Position;
	private Vector2 canon2Position;
	private Vector2 canon3Position;
	private Clock clockCannonTurn;
	private Clock clockSleep;
	private int downThreshold;
	private Rectangle[] hitHitRec;
	private List<Rectangle> hitRectangles;
	private final BossLv3 parent;
	private final float[] shadowVertices;

	static {
		int var0;
		if (enemyTextureRegion.rotate) {
			var0 = enemyTextureRegion.getRegionHeight();
		} else {
			var0 = enemyTextureRegion.getRegionWidth();
		}

		WIDTH = var0;
		int var1;
		if (enemyTextureRegion.rotate) {
			var1 = enemyTextureRegion.getRegionWidth();
		} else {
			var1 = enemyTextureRegion.getRegionHeight();
		}

		HEIGHT = var1;
		CANNONPOSITION = new int[][] { { 44, 44 }, { 35, 53 }, { 26, 62 } };
		Rectangle[] var2 = new Rectangle[] {
				new Rectangle(0.0F, 105.0F, 10.0F, 10.0F),
				new Rectangle(10.0F, 95.0F, 10.0F, 10.0F),
				new Rectangle(20.0F, 85.0F, 10.0F, 10.0F),
				new Rectangle(30.0F, 75.0F, 10.0F, 10.0F),
				new Rectangle(40.0F, 65.0F, 10.0F, 10.0F),
				new Rectangle(50.0F, 55.0F, 10.0F, 10.0F),
				new Rectangle(60.0F, 45.0F, 10.0F, 10.0F),
				new Rectangle(70.0F, 35.0F, 10.0F, 10.0F),
				new Rectangle(80.0F, 25.0F, 10.0F, 10.0F),
				new Rectangle(90.0F, 25.0F, 10.0F, 10.0F),
				new Rectangle(100.0F, 35.0F, 10.0F, 10.0F),
				new Rectangle(110.0F, 45.0F, 5.0F, 10.0F) };
		relativeHitRectangles = var2;
		CANNON1POSITION = new Vector2(
				((float) CANNONPOSITION[0][0] + (float) BossLv3Cannon.WIDTH1 / 2.0F) / 1.0F,
				((float) CANNONPOSITION[0][1] + (float) BossLv3Cannon.HEIGHT1 / 2.0F) / 1.0F);
		CANNON2POSITION = new Vector2(
				((float) CANNONPOSITION[1][0] + (float) BossLv3Cannon.WIDTH1 / 2.0F) / 1.0F,
				((float) CANNONPOSITION[1][1] + (float) BossLv3Cannon.HEIGHT1 / 2.0F) / 1.0F);
		CANNON3POSITION = new Vector2(
				((float) CANNONPOSITION[2][0] + (float) BossLv3Cannon.WIDTH1 / 2.0F) / 1.0F,
				((float) CANNONPOSITION[2][1] + (float) BossLv3Cannon.HEIGHT1 / 2.0F) / 1.0F);
		RECNUM = relativeHitRectangles.length;
	}

	public BossLv3Building1(World var1, float var2, float var3, BossLv3 var4) {
		super(var1, 180, var2, var3, (float) WIDTH / 1.0F,
				(float) HEIGHT / 1.0F);
		this.parent = var4;
		this.hitHitRec = new Rectangle[RECNUM];

		for (int var5 = 0; var5 < RECNUM; ++var5) {
			this.hitHitRec[var5] = new Rectangle(1.0F + this.bounds.x
					+ relativeHitRectangles[var5].x, 1.0F + this.bounds.y
					+ relativeHitRectangles[var5].y,
					relativeHitRectangles[var5].width,
					relativeHitRectangles[var5].height);
		}

		this.buildingState = BossLv3.BuildingState.SleepWithOutClock;
		this.clockSleep = new Clock(10.0F);
		this.clockCannonTurn = new Clock(0.0F, 5.0F,
				Clock.ClockType.FireOnlyOnce);
		this.downThreshold = 30 * ((-1 + this.getHealthyDgree()) / 30);
		this.hitRectangles = new ArrayList(RECNUM);
		this.canon1Position = new Vector2();
		this.canon2Position = new Vector2();
		this.canon3Position = new Vector2();
		this.canon1Position.set(CANNON1POSITION);
		this.canon2Position.set(CANNON2POSITION);
		this.canon3Position.set(CANNON3POSITION);
		this.cannon1 = new BossLv3Cannon(var1, this.bounds.x
				+ this.canon1Position.x, this.bounds.y + this.canon1Position.y,
				true);
		this.cannon2 = new BossLv3Cannon(var1, this.bounds.x
				+ this.canon2Position.x, this.bounds.y + this.canon2Position.y,
				true);
		this.cannon3 = new BossLv3Cannon(var1, this.bounds.x
				+ this.canon3Position.x, this.bounds.y + this.canon3Position.y,
				true);
		this.shadowVertices = new float[20];
		this.enemyRegion = enemyTextureRegion;
		this.enemyBroRegion = enemyBroTextureRegion;
	}

	private float getInitPositionY() {
		return this.parent.bounds.y + BossLv3.BUILDING1POSITION.y;
	}

	public static void loadResource() {
		enemyTextureRegion = Assets_level3.atlas_Enemy.findRegion(
				"lv3_boss_building", 1);
		enemyBroTextureRegion = Assets_level3.atlas_Enemy
				.findRegion("lv3_boss_building_1_broken");
	}

	public void beHitByBullet(Bullet var1) {
		int var2 = var1.damageValue;
		int var3 = this.getHealthyDgree() - var1.damageValue;
		int var4 = -1 + this.downThreshold;
		if (var3 < var4) {
			var1.damageValue = this.getHealthyDgree() - var4;
		}

		super.beHitByBullet(var1);
		var1.damageValue = var2;
	}

	public void beHitByBullet(Bullet var1, int var2) {
		this.beHitByBullet(var1);
		if (this.isCrashed()) {
			this.cannon1.makeCrash();
			this.cannon2.makeCrash();
			this.cannon3.makeCrash();
		}

	}

	public BossLv3.BuildingState getBuildState() {
		return this.buildingState;
	}

	public List<Rectangle> getHitRectangle() {
		this.hitRectangles.clear();
		if (this.buildingState == BossLv3.BuildingState.Shooting) {
			for (int var1 = 0; var1 < RECNUM; ++var1) {
				this.hitHitRec[var1].x = 1.0F + this.bounds.x
						+ relativeHitRectangles[var1].x;
				this.hitHitRec[var1].y = 1.0F + this.bounds.y
						+ relativeHitRectangles[var1].y;
				this.hitRectangles.add(this.hitHitRec[var1]);
			}
		}

		return this.hitRectangles;
	}

	public float getTimeOfCrash() {
		return 4.0F;
	}

	public void putCrashAniamtion() {
		Animation var1 = this.getShatterAniamtion();
		Animation var2 = this.getCrashBoom(false);
		WorldAudio.PlaySound var3 = this.getCrashSound(false);
		float var4 = 30.0F + this.bounds.x;
		float var5 = 150.0F + this.bounds.y;
		WorldAnimationPlayer.addAWorldAnimation(var1, var4, var5, 1);
		WorldAnimationPlayer.addAWorldAnimation(var2, var4, var5, 1, var3);
		float var7 = 70.0F + this.bounds.x;
		float var8 = 110.0F + this.bounds.y;
		float var9 = 0.1F + (float) Clock.getTimeCounter();
		WorldAnimationPlayer.addAWorldAnimation(var1, var7, var8, 1, var9);
		WorldAnimationPlayer
				.addAWorldAnimation(var2, var7, var8, 1, var9, var3);
	}

	public void render(SpriteBatch var1) {
		super.render(var1);
		this.cannon3.render(var1);
		this.cannon2.render(var1);
		this.cannon1.render(var1);
	}

	public void renderShadow(SpriteBatch var1) {
		if (this.position.y != this.getInitPositionY()) {
			float var2 = 1.0F * (25.0F * (this.position.y - this
					.getInitPositionY()) / 36.0F);
			float var3 = -var2;
			this.shadowVertices[0] = 86.0F + this.bounds.x;
			this.shadowVertices[1] = 36.0F + (this.getInitPositionY() - this.bounds.height / 2.0F);
			this.shadowVertices[3] = 1.0F;
			this.shadowVertices[4] = 1.0F;
			this.shadowVertices[2] = Color.WHITE.toFloatBits();
			this.shadowVertices[5] = var2 + this.shadowVertices[0];
			this.shadowVertices[6] = var3 + this.shadowVertices[1];
			this.shadowVertices[8] = 1.0F;
			this.shadowVertices[9] = 1.0F;
			this.shadowVertices[7] = Color.WHITE.toFloatBits();
			this.shadowVertices[10] = 30.0F + this.shadowVertices[5];
			this.shadowVertices[11] = 30.0F + this.shadowVertices[6];
			this.shadowVertices[13] = 1.0F;
			this.shadowVertices[14] = 1.0F;
			this.shadowVertices[12] = Color.WHITE.toFloatBits();
			this.shadowVertices[15] = 30.0F + this.shadowVertices[0];
			this.shadowVertices[16] = 30.0F + this.shadowVertices[1];
			this.shadowVertices[18] = 1.0F;
			this.shadowVertices[19] = 1.0F;
			this.shadowVertices[17] = Color.WHITE.toFloatBits();
			var1.draw(Assets_level3.buildingShadow, this.shadowVertices, 0, 20);
		}

	}

	public void setBuildState(BossLv3.BuildingState var1) {
		this.buildingState = var1;
	}

	public void setSleepPosition(float var1, float var2) {
		super.setSleepPosition(var1, var2);
		this.cannon1.setSleepPosition(this.bounds.x + this.canon1Position.x,
				this.bounds.y + this.canon1Position.y);
		this.cannon2.setSleepPosition(this.bounds.x + this.canon2Position.x,
				this.bounds.y + this.canon2Position.y);
		this.cannon3.setSleepPosition(this.bounds.x + this.canon3Position.x,
				this.bounds.y + this.canon3Position.y);
	}

	 public void update(final float n) {

	        super.update(n);

	        switch (this.buildingState) {

	            case SleepWithOutClock: {

	                if (this.position.y != this.getInitPositionY()) {

	                    this.position.y = this.getInitPositionY();

	                    super.updateDynamicParameter(0.0f);

	                    break;

	                }

	                break;

	            }

	            case SleepWithClock: {

	                if (this.position.y != this.getInitPositionY()) {

	                    this.position.y = this.getInitPositionY();

	                    super.updateDynamicParameter(0.0f);

	                }

	                if (this.clockSleep.isFired()) {

	                    this.buildingState = BossLv3.BuildingState.Rising;

	                    this.velocity.set(0.0f, 36.0f);

	                    break;

	                }

	                break;

	            }

	            case Rising: {

	                if (this.velocity.y != 36.0f) {

	                    this.velocity.y = 36.0f;

	                }

	                if (this.position.y - this.getInitPositionY() >= 36.0f) {

	                    this.velocity.set(0.0f, 0.0f);

	                    this.buildingState = BossLv3.BuildingState.Shooting;

	                    this.downThreshold = 30 * ((-1 + this.getHealthyDgree()) / 30);

	                    this.clockCannonTurn.resetClock(-0.125f, 0.125f, 0.0f, (byte) 1);

	                    break;

	                }

	                break;

	            }

	            case Shooting: {

	                if (this.position.y != 36.0f + this.getInitPositionY()) {

	                    this.position.y = 36.0f + this.getInitPositionY();

	                    super.updateDynamicParameter(0.0f);

	                }

	                if (this.clockCannonTurn.isFired()) {

	                    if (this.cannon3.getAngle() == 0.0f) {

	                        this.cannon3.openCannon();

	                    }

	                    else if (this.cannon2.getAngle() == 0.0f) {

	                        this.cannon2.openCannon();

	                    }

	                    else if (this.cannon1.getAngle() == 0.0f) {

	                        this.cannon1.openCannon();

	                    }

	                }

	                if (this.getHealthyDgree() < this.downThreshold && this.downThreshold > 0) {

	                    this.buildingState = BossLv3.BuildingState.Falling;

	                    this.downThreshold = -30 + this.downThreshold;

	                    this.clockCannonTurn.resetClock(-0.125f, 0.125f, 0.0f, (byte) 1);

	                    break;

	                }

	                break;

	            }

	            case Falling: {

	                if (this.clockCannonTurn.isFired()) {

	                    if (this.cannon1.getAngle() != 0.0f) {

	                        this.cannon1.closeCannon();

	                    }

	                    else if (this.cannon2.getAngle() != 0.0f) {

	                        this.cannon2.closeCannon();

	                    }

	                    else if (this.cannon3.getAngle() != 0.0f) {

	                        this.cannon3.closeCannon();

	                    }

	                }

	                if (this.cannon1.getAngle() == 0.0f && this.cannon2.getAngle() == 0.0f && this.cannon3.getAngle() == 0.0f && this.velocity.y == 0.0f) {

	                    this.velocity.set(0.0f, -36.0f);

	                }

	                if (this.position.y <= this.getInitPositionY()) {

	                    if (this.downThreshold > 0) {

	                        this.buildingState = BossLv3.BuildingState.SleepWithClock;

	                        this.clockSleep.resetClockFireOnce(2.0f + 4.0f * this.world.rand.nextFloat());

	                    }

	                    else {

	                        this.buildingState = BossLv3.BuildingState.SleepWithOutClock;

	                    }

	                    this.velocity.set(0.0f, 0.0f);

	                    break;

	                }

	                break;

	            }

	        }

	        this.cannon1.update(n);

	        this.cannon2.update(n);

	        this.cannon3.update(n);

	    }

	public void updateDynamicParameter(float var1) {
		super.updateDynamicParameter(var1);
		this.cannon1.setPosition(this.bounds.x + this.canon1Position.x,
				this.bounds.y + this.canon1Position.y);
		this.cannon2.setPosition(this.bounds.x + this.canon2Position.x,
				this.bounds.y + this.canon2Position.y);
		this.cannon3.setPosition(this.bounds.x + this.canon3Position.x,
				this.bounds.y + this.canon3Position.y);
	}

	public void updateShooting(float var1) {
	}
}
