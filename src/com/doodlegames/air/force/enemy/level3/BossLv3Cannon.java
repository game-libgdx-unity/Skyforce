package com.doodlegames.air.force.enemy.level3;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.enemy.EnemyCrashPlayer;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.bullet.BulletGenerator;
import com.doodlegames.air.force.game.bullet.EnemyBullet1;
import com.doodlegames.air.force.game.bullet.EnemyBullet2;
import com.doodlegames.air.force.resource.Assets_level3;
import com.doodlegames.air.force.utils.Clock;

public class BossLv3Cannon extends Enemy implements EnemyBullet2.MissleFixer {

	private static final float BULLETSPEED = 10.0F;
	public static final int DEFAULTHEALTHYDEGREE = 5;
	public static int HEIGHT1 = 0;
	public static int HEIGHT2 = 0;
	private static final float TIMEOFPLAYCRASHANIMATION = 0.01F;
	public static final int WIDTH1;
	public static final int WIDTH2;
	public static final Vector2 bulletPostion = new Vector2(3.0F, 16.0F);
	public static final Vector2 cannonOrigin = new Vector2(3.0F, 4.0F);
	public static TextureAtlas.AtlasRegion canon1TextureRegion = Assets_level3.atlas_Enemy
			.findRegion("lv3_boss_cannon", 1);
	public static TextureAtlas.AtlasRegion canon2TextureRegion = Assets_level3.atlas_Enemy
			.findRegion("lv3_boss_cannon", 2);
	private static Integer leftSpanControl;
	private static Integer rightSpanControl;
	public static final Vector2 velBullet = new Vector2(0.0F, 80.0F);
	private TextureAtlas.AtlasRegion canonTextureRegion;
	private final boolean choose;
	private Clock clockShooting;
	private BossLv3Cannon.CannonState cs;
	private final float openedAngle;
	private Vector2 rotateV;
	private Vector2 thisBulletPostion;
	private Vector2 thisVelBullet;

	static {
		int var0;
		if (canon1TextureRegion.rotate) {
			var0 = canon1TextureRegion.getRegionHeight();
		} else {
			var0 = canon1TextureRegion.getRegionWidth();
		}

		WIDTH1 = var0;
		int var1;
		if (canon1TextureRegion.rotate) {
			var1 = canon1TextureRegion.getRegionWidth();
		} else {
			var1 = canon1TextureRegion.getRegionHeight();
		}

		HEIGHT1 = var1;
		int var2;
		if (canon2TextureRegion.rotate) {
			var2 = canon2TextureRegion.getRegionHeight();
		} else {
			var2 = canon2TextureRegion.getRegionWidth();
		}

		WIDTH2 = var2;
		int var3;
		if (canon2TextureRegion.rotate) {
			var3 = canon2TextureRegion.getRegionWidth();
		} else {
			var3 = canon2TextureRegion.getRegionHeight();
		}

		HEIGHT2 = var3;
		leftSpanControl = Integer.valueOf(0);
		rightSpanControl = Integer.valueOf(0);
	}

	public BossLv3Cannon(World var1, float var2, float var3, boolean var4) {
//		int var5 = var4?WIDTH1:WIDTH2; 
//
//		float var6 = (float) var5 / 1.0F;
//		int var7;
//		if (var4) {
//			var7 = HEIGHT1;
//		} else {
//			var7 = HEIGHT2;
//		}

		super(var1, 5, var2, var3, var4?WIDTH1:WIDTH2, var4?HEIGHT1:HEIGHT2);
		this.origin.set(cannonOrigin);
		TextureAtlas.AtlasRegion var9;
		if (var4) {
			var9 = canon1TextureRegion;
		} else {
			var9 = canon2TextureRegion;
		}

		this.canonTextureRegion = var9;
		this.enemyRegion = this.canonTextureRegion;
		this.thisBulletPostion = new Vector2(bulletPostion);
		this.thisVelBullet = new Vector2(velBullet);
		this.rotateV = new Vector2();
		if (var4) {
			this.clockShooting = new Clock(
					3.0F - 0.2F * (float) leftSpanControl.intValue(), 3.0F,
					0.2F, (byte) 1);
			leftSpanControl = Integer
					.valueOf((1 + leftSpanControl.intValue()) % 3);
		} else {
			this.clockShooting = new Clock(
					3.0F - 0.2F * (float) rightSpanControl.intValue(), 3.0F,
					0.2F, (byte) 1);
			rightSpanControl = Integer
					.valueOf((1 + rightSpanControl.intValue()) % 3);
		}

		this.cs = BossLv3Cannon.CannonState.Closed;
		float var10;
		if (var4) {
			var10 = 45.0F;
		} else {
			var10 = -45.0F;
		}

		this.openedAngle = var10;
		this.choose = var4;
	}

	public static void loadResource() {
		canon1TextureRegion = Assets_level3.atlas_Enemy.findRegion(
				"lv3_boss_cannon", 1);
		canon2TextureRegion = Assets_level3.atlas_Enemy.findRegion(
				"lv3_boss_cannon", 2);
	}

	public void changeAngle(float var1) {
		if (this.angle != var1) {
			this.angle = var1;
			this.rotateV.set(bulletPostion).sub(this.origin);
			Enemy.getRotateOffset(this.rotateV, this.angle).add(bulletPostion);
			this.thisBulletPostion.set(this.rotateV);
			this.thisVelBullet.set(velBullet).rotate(this.angle);
		}
	}

	public void closeCannon() {
		if (this.cs == BossLv3Cannon.CannonState.Opened
				|| this.cs == BossLv3Cannon.CannonState.Opening) {
			this.cs = BossLv3Cannon.CannonState.Closing;
			this.angularSpeed = -this.openedAngle / 0.25F;
		}

	}

	public void fixMissle(EnemyBullet2 var1) {
		var1.setTurnCountMax(10);
		var1.addGameAction(EnemyBullet2.getSpeedUp(var1,
				2.0F + 2.0F * Clock.rand.nextFloat(), 1.0F, 450.0F));
	}

	public BossLv3Cannon.CannonState getCannonState() {
		return this.cs;
	}

	public EnemyCrashPlayer getMyCrashPlayer() {
		return null;
	}

	public float getTimeOfCrash() {
		return 0.01F;
	}

	public void openCannon() {
		if (this.cs == BossLv3Cannon.CannonState.Closed
				|| this.cs == BossLv3Cannon.CannonState.Closing) {
			this.cs = BossLv3Cannon.CannonState.Opening;
			this.angularSpeed = this.openedAngle / 0.25F;
			this.clockShooting.resetClock(5.5F, 6.0F, 0.2F, (byte) 1);
		}

	}

	public void setAngle(float var1) {
		this.changeAngle(var1);
		super.setAngle(var1);
	}

	public void shooting() {
		if (!this.isCrashed()) {
			Enemy.bullets.add(EnemyBullet1.naziBulletGen.getABullet(this,
					this.world, this.bounds.x + this.thisBulletPostion.x,
					this.bounds.y + this.thisBulletPostion.y,
					this.thisVelBullet));
		}
	}

	public void shooting(BulletGenerator var1) {
		if (!this.isCrashed()) {
			Enemy.bullets.add(var1.getABullet(this, this.world, this.bounds.x
					+ this.thisBulletPostion.x, this.bounds.y
					+ this.thisBulletPostion.y, this.thisVelBullet));
		}
	}

	public void update(final float n) {

        boolean n2 = true;

        super.update(n);

        switch (this.cs) {

            case Opening: {

                if (this.angle < this.openedAngle) {

                    n2 = false;

                }

                if (n2 == this.choose) {

                    this.angle = this.openedAngle;

                    this.angularSpeed = 0.0f;

                    this.cs = CannonState.Opened;

                    return;

                }

                return;

            }

            case Opened:

            case Closed: {

                return;

            }

            case Closing: {

                if (this.angle > 0.0f) {

                    n2 = false;

                }

                if (n2 == this.choose) {

                    this.angle = 0.0f;

                    this.angularSpeed = 0.0f;

                    this.cs = CannonState.Closed;

                    return;

                }

                return;

            }

            default: {

                this.angularSpeed = 0.0f;

                return;

            }

        }

    }

	public void updateShooting(float var1) {
		if (this.clockShooting.isFired()
				&& this.cs == BossLv3Cannon.CannonState.Opened) {
			this.shooting(EnemyBullet2.naziBulletGen);
		}

	}

	public static enum CannonState {
		Opening, Opened, Closing, Closed,
	}
}
