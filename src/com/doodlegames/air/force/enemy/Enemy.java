package com.doodlegames.air.force.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.doodlegames.air.force.enemy.EnemyCrashPlayer;
import com.doodlegames.air.force.enemy.EnemyProXY;
import com.doodlegames.air.force.enemy.EnemyTools;
import com.doodlegames.air.force.enemy.cannon.Cannon1;
import com.doodlegames.air.force.enemy.cannon.Cannon2;
import com.doodlegames.air.force.enemy.cannon.Cannon3;
import com.doodlegames.air.force.enemy.cannon.Cannon5;
import com.doodlegames.air.force.enemy.cannon.CommonCannon;
import com.doodlegames.air.force.enemy.cannon.RailGun;
import com.doodlegames.air.force.enemy.level1.AirCraft;
import com.doodlegames.air.force.enemy.level1.AirCraftOnBoss;
import com.doodlegames.air.force.enemy.level1.BattleShip;
import com.doodlegames.air.force.enemy.level1.BossAircraftCarrier;
import com.doodlegames.air.force.enemy.level1.Destroyer;
import com.doodlegames.air.force.enemy.level1.RailGunOnBoss;
import com.doodlegames.air.force.enemy.level1.Sea_canon;
import com.doodlegames.air.force.enemy.level1.Ufo_a;
import com.doodlegames.air.force.enemy.level1.Ufo_b;
import com.doodlegames.air.force.enemy.level2.BigUfo;
import com.doodlegames.air.force.enemy.level2.BossL;
import com.doodlegames.air.force.enemy.level2.BossLBody;
import com.doodlegames.air.force.enemy.level2.BossLCanon;
import com.doodlegames.air.force.enemy.level2.BossLHead;
import com.doodlegames.air.force.enemy.level2.BossLRocket;
import com.doodlegames.air.force.enemy.level2.BossS;
import com.doodlegames.air.force.enemy.level2.BossSLeftArm;
import com.doodlegames.air.force.enemy.level2.BossSRightArm;
import com.doodlegames.air.force.enemy.level2.LandCannon;
import com.doodlegames.air.force.enemy.level2.Robot;
import com.doodlegames.air.force.enemy.level2.RocketVehicle;
import com.doodlegames.air.force.enemy.level2.Tank;
import com.doodlegames.air.force.enemy.level3.BossLv3;
import com.doodlegames.air.force.enemy.level3.BossLv3Building1;
import com.doodlegames.air.force.enemy.level3.BossLv3Building2;
import com.doodlegames.air.force.enemy.level3.BossLv3Building3;
import com.doodlegames.air.force.enemy.level3.BossLv3Building4;
import com.doodlegames.air.force.enemy.level3.BossLv3Cannon;
import com.doodlegames.air.force.enemy.level3.HightRobot;
import com.doodlegames.air.force.enemy.level3.MoonCanon;
import com.doodlegames.air.force.enemy.level3.SpaceBattleShip;
import com.doodlegames.air.force.enemy.level4.BossLv4;
import com.doodlegames.air.force.enemy.level4.BossLv4Cannon;
import com.doodlegames.air.force.enemy.level4.BossLv4Craft;
import com.doodlegames.air.force.enemy.level4.BossLv4Hand;
import com.doodlegames.air.force.enemy.level4.BossLv4Head;
import com.doodlegames.air.force.enemy.level4.TestWeapon;
import com.doodlegames.air.force.enemy.level4.TestWeaponCore;
import com.doodlegames.air.force.enemy.level4.TestWeaponLeft;
import com.doodlegames.air.force.enemy.level4.TestWeaponRight;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.EnemyLoader;
import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.game.WorldAudio;
import com.doodlegames.air.force.game.bullet.Bullet;
import com.doodlegames.air.force.game.laser.Laser;
import com.doodlegames.air.force.game.path.AbstractEnemyPath;
import com.doodlegames.air.force.resource.Assets_ShareInAlien;
import com.doodlegames.air.force.resource.Assets_ShareInNazi;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.RegionUtilFunctions;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class Enemy extends DynamicGameObject {

	public static final float DefaultHitingRestore = 0.22222222F;
	public static final ArrayList<Rectangle> EMPTYRECTANGELS = new ArrayList(0);
	public static final int ENEMY_STATE_ALIVE = 0;
	public static final int ENEMY_STATE_CRASH = 1;
	public static final int KILLBYBOOMBULLET = 1;
	public static final int KILLBYCOMMONBULLET = 0;
	public static final int KILLBYFIGHTERHIT = 3;
	public static final int KILLBYLASEBULLET = 2;
	public static final int KILLBYMAKECRASH = -1;
	private static final HashSet<Class<? extends Enemy>> NaziEnemy;
	private static Animation[] boomBigAlien;
	private static Animation[] boomBigNazi;
	private static Animation[] boomSmallAlien;
	private static Animation[] boomSmallNazi;
	public static List<Bullet> bullets = new ArrayList();
	public static List<Laser> lasers = new ArrayList();
	private static Animation[] shatterAlien;
	private static Animation[] shatterNazi;
	private int bulletCount;
	public boolean crashAnimationPutted = false;
	public boolean crashUsePath = true;
	protected Array<EnemyTools.EnemyAction> enemyActions = new Array(4);
	protected TextureRegion enemyBroRegion;
	protected Enemy.EnemyFreeToPoolFunction enemyFreeToPool = null;
	protected TextureRegion enemyRegion;
	private int healthyDegree;
	protected boolean isBeHiting;
	public int killBy = -1;
	protected float lastHitTime;
	protected AbstractEnemyPath moveProcessor;
	protected boolean needShowWarning = false;
	public boolean outRemove = true;
	public boolean outRender = false;
	public int state;
	public float stateTime;
	public float warningPosition = 6000.0F;
	public World world;

	static {
		EMPTYRECTANGELS.clear();
		NaziEnemy = new HashSet();
		NaziEnemy.clear();
		NaziEnemy.add(Cannon1.class);
		NaziEnemy.add(Cannon2.class);
		NaziEnemy.add(Cannon3.class);
		NaziEnemy.add(Cannon5.class);
		NaziEnemy.add(CommonCannon.class);
		NaziEnemy.add(CommonCannon.CommonCannon1.class);
		NaziEnemy.add(CommonCannon.CommonCannon2.class);
		NaziEnemy.add(CommonCannon.CommonCannon4.class);
		NaziEnemy.add(CommonCannon.CommonCannonCrossCannon.class);
		NaziEnemy.add(RailGun.class);
		NaziEnemy.add(AirCraft.class);
		NaziEnemy.add(AirCraftOnBoss.class);
		NaziEnemy.add(BattleShip.class);
		NaziEnemy.add(BossAircraftCarrier.class);
		NaziEnemy.add(Destroyer.class);
		NaziEnemy.add(RailGunOnBoss.class);
		NaziEnemy.add(Sea_canon.class);
		NaziEnemy.add(Ufo_a.class);
		NaziEnemy.add(Ufo_b.class);
		NaziEnemy.add(AirCraft.Ufo_bTakeOff.class);
		NaziEnemy.add(BigUfo.class);
		NaziEnemy.add(BossL.class);
		NaziEnemy.add(BossLBody.class);
		NaziEnemy.add(BossLCanon.class);
		NaziEnemy.add(BossLHead.class);
		NaziEnemy.add(BossLRocket.class);
		NaziEnemy.add(BossS.class);
		NaziEnemy.add(BossSLeftArm.class);
		NaziEnemy.add(BossSRightArm.class);
		NaziEnemy.add(LandCannon.class);
		NaziEnemy.add(Robot.class);
		NaziEnemy.add(RocketVehicle.class);
		NaziEnemy.add(Tank.class);
		NaziEnemy.add(BossLv3.class);
		NaziEnemy.add(BossLv3Building1.class);
		NaziEnemy.add(BossLv3Building2.class);
		NaziEnemy.add(BossLv3Building3.class);
		NaziEnemy.add(BossLv3Building4.class);
		NaziEnemy.add(BossLv3Cannon.class);
		NaziEnemy.add(HightRobot.class);
		NaziEnemy.add(MoonCanon.class);
		NaziEnemy.add(BossLv3.BossLv3MoonCannon.class);
		NaziEnemy.add(SpaceBattleShip.class);
		NaziEnemy.add(BossLv4.class);
		NaziEnemy.add(BossLv4Cannon.class);
		NaziEnemy.add(BossLv4Hand.class);
		NaziEnemy.add(BossLv4Head.class);
		NaziEnemy.add(BossLv4Craft.class);
		NaziEnemy.add(TestWeapon.class);
		NaziEnemy.add(TestWeaponCore.class);
		NaziEnemy.add(TestWeaponLeft.class);
		NaziEnemy.add(TestWeaponRight.class);
		Animation[] var52 = new Animation[] { Assets_ShareInNazi.shatter1,
				Assets_ShareInNazi.shatter2 };
		shatterNazi = var52;
		Animation[] var53 = new Animation[] { Assets_ShareInAlien.shatter1,
				Assets_ShareInAlien.shatter2 };
		shatterAlien = var53;
		Animation[] var54 = new Animation[] { Assets_ShareInNazi.smallBoom1,
				Assets_ShareInNazi.smallBoom2 };
		boomSmallNazi = var54;
		Animation[] var55 = new Animation[] { Assets_ShareInNazi.bigBoom2 };
		boomBigNazi = var55;
		Animation[] var56 = new Animation[] { Assets_ShareInAlien.smallBoom2,
				Assets_ShareInAlien.smallBoom3 };
		boomSmallAlien = var56;
		Animation[] var57 = new Animation[] { Assets_ShareInAlien.bigBoom1 };
		boomBigAlien = var57;
	}

	public Enemy(World var1, int var2, float var3, float var4, float var5,
			float var6) {
		super(var3, var4, var5, var6);
		this.setInitHealthyDgree(var2);
		this.stateTime = 0.0F;
		this.state = 0;
		this.setBulletCount(0);
		this.world = var1;
		this.enemyRegion = null;
		this.enemyBroRegion = null;
	}

	public static float getHitColor() {
		return Color.toFloatBits(1.0F, 0.5F, 0.0F, 1.0F);
	}

	public void addEnemyAction(EnemyTools.EnemyAction var1) {
		var1.setEnemy(this);
		this.enemyActions.add(var1);
	}

	public void beHitByBullet(Bullet var1) {
		if (1 != this.state) {
			this.healthyDegree -= var1.damageValue;
			if (this.isCrashed()) {
				this.stateTime = 0.0F;
				this.state = 1;
				this.killBy = var1.bulletType;
				try {
				this.world.fireEnemyCrashedEvent(this);
				} catch(Exception ex) {
					
				}
			}

			this.isBeHiting = true;
			this.lastHitTime = (float) Clock.getTimeCounter();
			if (var1.damageValue < 0) {
				Settings.e("invalid damageValue : " + var1.damageValue);
				if (var1.getOwner() != null) {
					Settings.e("owner is :" + var1.getOwner().toString());
				}

				Settings.e("bulletType is :" + var1.bulletType);
				return;
			}
		}

	}

	public void beHitByBullet(Bullet var1, int var2) {
		this.beHitByBullet(var1);
	}

	public boolean canBeHit() {
		Settings.PositionType var1 = Settings.gameObjectInScreen(this);
		boolean var2;
		if (this.healthyDegree > 0) {
			var2 = true;
		} else {
			var2 = false;
		}

		boolean var3;
		if (var1 == Settings.PositionType.InScreenMiddle) {
			var3 = true;
		} else {
			var3 = false;
		}

		return var2 && var3;
	}

	protected void checkShowWarnning() {
		if (this.needShowWarning
				&& 800.0F + this.world.getViewPosition() > this.warningPosition) {
			this.world.askStopFighting();
			this.showWarning();
			this.needShowWarning = false;
		}

	}

	public void clearEnemyActions() {
		for (int var1 = 0; var1 < this.enemyActions.size; ++var1) {
			this.freeEnemyAction((EnemyTools.EnemyAction) this.enemyActions
					.get(var1));
		}

		this.enemyActions.clear();
	}

	public void enemyDropped(EnemyProXY var1) {
	}

	public void enemyRemoved() {
		this.clearGameObjectActions();
		this.clearEnemyActions();
		if (this.enemyFreeToPool != null) {
			this.enemyFreeToPool.freeToPool(this);
		}

		this.freeToPool();
	}

	protected void excuteEnemyActions(float var1) {
		for (int var2 = -1 + this.enemyActions.size; var2 >= 0; --var2) {
			if (((EnemyTools.EnemyAction) this.enemyActions.get(var2))
					.act(var1)) {
				EnemyTools.EnemyAction var3 = (EnemyTools.EnemyAction) this.enemyActions
						.get(var2);
				this.enemyActions.removeIndex(var2);
				this.freeEnemyAction(var3);
			}
		}

	}

	public void freeEnemyAction(EnemyTools.EnemyAction var1) {
		if (var1.getEnemy() == this) {
			try {
				Pools.free((Object) var1);
			} catch (IllegalArgumentException var3) {
				;
			}
		}
	}

	protected void freeToPool() {
	}

	public int getBulletCount() {
		return this.bulletCount;
	}

	public Animation getCrashBoom(boolean var1) {
		boomSmallNazi[0] = Assets_ShareInNazi.smallBoom1;
		boomSmallNazi[1] = Assets_ShareInNazi.smallBoom2;
		boomBigNazi[0] = Assets_ShareInNazi.bigBoom2;
		boomSmallAlien[0] = Assets_ShareInAlien.smallBoom2;
		boomSmallAlien[1] = Assets_ShareInAlien.smallBoom3;
		boomBigAlien[0] = Assets_ShareInAlien.bigBoom1;
		Animation[] var2;
		if (NaziEnemy.contains(this.getClass())) {
			if (var1) {
				var2 = boomBigNazi;
			} else {
				var2 = boomSmallNazi;
			}
		} else if (var1) {
			var2 = boomBigAlien;
		} else {
			var2 = boomSmallAlien;
		}

		return var2[this.world.rand.nextInt(var2.length)];
	}

	public WorldAudio.PlaySound getCrashSound(boolean var1) {
		if (NaziEnemy.contains(this.getClass())) {
			WorldAudio.PlaySound var4 = this.world.worldAudio.playNaziSmallBoom;
			WorldAudio.PlaySound var5 = this.world.worldAudio.playNaziBigBoom;
			return var1 ? var5 : var4;
		} else {
			WorldAudio.PlaySound var2 = this.world.worldAudio.playAlienBigBoom;
			WorldAudio.PlaySound var3 = this.world.worldAudio.playAlienSmallBoom;
			return var1 ? var2 : var3;
		}
	}

	public float getDropDelayTime() {
		return -1.0F;
	}

	public float getDropX() {
		return this.getPositionX();
	}

	public float getDropY() {
		return this.getPositionY();
	}

	public List<Rectangle> getFighterHitRecs() {
		return this.getHitRectangle();
	}

	public int getHealthyDgree() {
		return this.healthyDegree;
	}

	public Animation getHitAnimation(Bullet var1) {
		return NaziEnemy.contains(this.getClass()) ? Assets_ShareInNazi.hitBoom
				: Assets_ShareInAlien.hitBoom;
	}

	public AbstractEnemyPath getMoveProcessor() {
		return this.moveProcessor;
	}

	public Animation getShatterAniamtion() {
		try {
		shatterNazi[0] = Assets_ShareInNazi.shatter1;
		shatterNazi[1] = Assets_ShareInNazi.shatter2;
		shatterAlien[0] = Assets_ShareInAlien.shatter1;
		shatterAlien[1] = Assets_ShareInAlien.shatter2;
		Animation[] var1;
		if (NaziEnemy.contains(this.getClass())) {
			var1 = shatterNazi;
		} else {
			var1 = shatterAlien;
		}

		return var1[this.world.rand.nextInt(var1.length)];
		} catch(Exception ex) {
			return new Animation(.1f, shatterNazi[0].getRegion());
		}
	}

	public abstract float getTimeOfCrash();

	public boolean isCrashed() {
		return this.healthyDegree <= 0;
	}

	public void makeCrash() {
		if (1 != this.state) {
			this.healthyDegree = -1;
			this.stateTime = 0.0F;
			this.state = 1;
			this.world.fireEnemyCrashedEvent(this);
		}
	}

	protected void putCrashAniamtion() {
		EnemyCrashPlayer.putCrashAnimation(this);
	}

	public void render(SpriteBatch var1) {
		switch (this.state) {
		case 0:
			this.renderEnemy(var1);
			return;
		case 1:
			this.renderCrash(var1);
			return;
		default:
		}
	}

	protected void renderCrash(SpriteBatch var1) {
		TextureRegion var2 = this.enemyBroRegion;
		if (var2 == null && this.stateTime < this.getTimeOfCrash()) {
			var2 = this.enemyRegion;
		}

		if (var2 != null) {
			RegionUtilFunctions.draw(var1, var2, this.bounds.x, this.bounds.y,
					this.origin.x, this.origin.y, this.bounds.width,
					this.bounds.height, 1.0F, 1.0F, this.angle);
		}

		if (this.stateTime <= this.getTimeOfCrash()
				&& !this.crashAnimationPutted) {
			this.crashAnimationPutted = true;
			this.putCrashAniamtion();
		}
	}

	public void renderEnemy(SpriteBatch var1) {
		if (this.enemyRegion != null) {
			Color var2 = var1.getColor();
			if (this.isBeHiting) {
				var1.setColor(getHitColor());
			}

			RegionUtilFunctions.draw(var1, this.enemyRegion, this.bounds.x,
					this.bounds.y, this.origin.x, this.origin.y,
					this.bounds.width, this.bounds.height, 1.0F, 1.0F,
					this.angle);
			var1.setColor(var2);
		}
	}

	public void renderShadow(SpriteBatch var1) {
		if (EnemyLoader.enemyIsOnSea(this.getClass(), this.world.level) == 1) {
			this.renderShadow(var1, shadowOffset.x, shadowOffset.y,
					shadowScale.x, shadowScale.y);
		}
	}

	public void renderShadow(SpriteBatch var1, float var2, float var3,
			float var4, float var5) {
		TextureRegion var6 = this.enemyRegion;
		if (this.isCrashed()) {
			var6 = this.enemyBroRegion;
		}

		if (var6 != null) {
			float var7 = var2 + this.bounds.x;
			float var8 = var3 + this.bounds.y;
			float var9 = this.origin.x;
			float var10 = this.origin.y;
			float var11 = this.bounds.width;
			float var12 = this.bounds.height;
			float var13 = this.angle;
			float var14 = var1.getColor().toFloatBits();
			var1.setColor(0.0F, 0.0F, 0.0F, 0.5F);
			RegionUtilFunctions.draw(var1, var6, var7, var8, var9, var10,
					var11, var12, var4, var5, var13);
			var1.setColor(var14);
		}
	}

	public void reset(World var1, int var2, float var3, float var4) {
		this.world = var1;
		this.setInitHealthyDgree(var2);
		this.setPosition(var3, var4);
		this.stateTime = 0.0F;
		this.state = 0;
		this.isBeHiting = false;
		this.lastHitTime = Float.NEGATIVE_INFINITY;
		this.setBulletCount(0);
		this.world = var1;
		this.enemyRegion = null;
		this.enemyBroRegion = null;
		this.moveProcessor = null;
		this.killBy = -1;
		this.outRender = false;
		this.outRemove = true;
		this.crashAnimationPutted = false;
		this.clearEnemyActions();
	}

	public void rotateTo(float var1) {
		this.enemyActions.add(EnemyTools.enemyRotateTo(this, var1));
	}

	public void rotateToOnly(float var1) {
		if (this.enemyActions.size == 0) {
			this.enemyActions.add(EnemyTools.enemyRotateTo(this, var1));
		}

	}

	public void setBroRegion(TextureAtlas.AtlasRegion var1) {
		if (this.enemyBroRegion == null && var1 != null) {
			this.enemyBroRegion = var1;
		} else {
			Settings.w("warning:setBroRegion error!");
		}
	}

	public void setBulletCount(int var1) {
		this.bulletCount = var1;
	}

	public void setEnemyFreeToPoolFunction(Enemy.EnemyFreeToPoolFunction var1) {
		this.enemyFreeToPool = var1;
	}

	public void setHealthyDgree(int var1) {
		this.healthyDegree = var1;
	}

	public void setInitHealthyDgree(int var1) {
		this.healthyDegree = (int) ((float) var1 * Settings.lapRatio());
	}

	public void setMoveProcessor(AbstractEnemyPath var1) {
		if (var1 != null) {
			this.moveProcessor = var1;
			float var2 = this.moveProcessor.restartFrom(this.getPositionX(),
					this.getPositionY());
			if (this.usePathAngle()) {
				this.setAngle(var2);
			} else {
				this.setAngle(0.0F);
			}
		}
	}

	public void setSleepPosition(float var1, float var2) {
		this.setPosition(var1, var2);
		this.checkShowWarnning();
	}

	protected void showWarning() {
		this.world.showWarining(1);
	}

	public void update(float var1) {
		this.stateTime += var1;
		super.update(var1);
		this.excuteEnemyActions(var1);
		if (this.isBeHiting
				&& Clock.getTimeCounter() - (double) this.lastHitTime > 0.2222222238779068D) {
			this.isBeHiting = false;
		}

		if (this.world != null)
			if (this.state != 1 && !this.world.getStopFighting()) {
				this.updateShooting(var1);
			}

	}

	protected void updateDynamicParameter(float var1) {
		if (this.moveProcessor == null) {
			super.updateDynamicParameter(var1);
		} else {
			if (this.moveProcessor == EnemyProXY.BGMARK || this.isCrashed()
					&& !this.crashUsePath) {
				this.setPosition(this.getPositionX(), this.getPositionY()
						+ var1 * Settings.backgroundVelocity.y);
				return;
			}

			float var2 = this.moveProcessor.moveForward(var1);
			this.setPosition(this.moveProcessor.getX(),
					this.moveProcessor.getY());
			if (this.usePathAngle()) {
				this.setAngle(var2);
				return;
			}
		}

	}

	public abstract void updateShooting(float var1);

	protected boolean usePathAngle() {
		return true;
	}

	public interface EnemyFreeToPoolFunction {

		void freeToPool(Enemy var1);
	}

	public static class HitMapItem {

		public Enemy e;
		public int index;

	}
}
