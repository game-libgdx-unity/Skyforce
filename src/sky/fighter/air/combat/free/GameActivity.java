package sky.fighter.air.combat.free;

import vn.gamework.alien.combat.ActionResolver;
import vn.gamework.alien.combat.Maybay;
import vn.gamework.alien.combat.NativeService;
import vn.gamework.alien.combat.util.AndroidService;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class GameActivity extends AndroidApplication implements NativeService,
		ActionResolver, AndroidService {

	public Handler gamehandler;
	private boolean needExit;

	public boolean getNoAds() {
		SharedPreferences prefs = getSharedPreferences("splash", MODE_PRIVATE);
		return prefs.getBoolean("noAds", false);
	}

	public void setNoAds() {
		SharedPreferences.Editor editor = getSharedPreferences("splash",
				MODE_PRIVATE).edit();
		editor.putBoolean("noAds", true);
		editor.commit();
		Toast.makeText(GameActivity.this, "All Ads are removed, thank you!",
				Toast.LENGTH_LONG).show();
	}

	public boolean getFirstTime() {
		SharedPreferences prefs = getSharedPreferences("splash", MODE_PRIVATE);
		return prefs.getBoolean("first", false);
	}

	public void setFirstTime() {
		SharedPreferences.Editor editor = getSharedPreferences("splash",
				MODE_PRIVATE).edit();
		editor.putBoolean("first", true);
		editor.commit();
	}

	public boolean getSecondTime() {
		SharedPreferences prefs = getSharedPreferences("splash", MODE_PRIVATE);
		return prefs.getBoolean("second", false);
	}

	public void setSecondTime() {
		SharedPreferences.Editor editor = getSharedPreferences("splash",
				MODE_PRIVATE).edit();
		editor.putBoolean("second", true);
		editor.commit();
	}

	public static String AppFloodSecrectKey = "0aTSyZXJ4878L53c16c1b";
	public static String AppFloodAppKey = "HXRCt76qTbSbA2kZ";

	public static String AppId = "53c169b789b0bb3697c24f08";
	public static String AppSignature = "e256e9a8dabbaec5abdeb10baf0ca514aad2a604";

	public boolean isOnline() {
		// ConnectivityManager cm = (ConnectivityManager)
		// getSystemService(Context.CONNECTIVITY_SERVICE);
		// NetworkInfo netInfo = cm.getActiveNetworkInfo();
		// if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		// return true;
		// }
		return false;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void rate() {
		Uri uri = Uri.parse("market://details?id=" + getPackageName());
		Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
		try {
			startActivity(myAppLinkToMarket);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, " unable to find market app",
					Toast.LENGTH_LONG).show();
		}
	}

	public void showVideoAds() {

	}

	public void startFullScreenAd() {
		showChartboostAds();
	}

	public void onVideoStart() {
		showToast("Watch Sponsored Video give you free Game Items :)");
	}

	public void onVideoReward() {
		showToast("You got free Game Items for watching video, thank you! :)");
	}

	@Override
	public boolean isVideoAvai() {

		return false;
	}

	private boolean shownVideo;

	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		if (showMoreAppEnd)
			finish();
	}

	private boolean showMoreAppEnd;

	public void share() {
		String urlToShare = "https://play.google.com/store/apps/details?id="
				+ GameActivity.this.getPackageName();
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		// intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no
		// effect!
		intent.putExtra(Intent.EXTRA_TEXT, urlToShare);

		startActivity(intent);
	}

	private AdRequest adRequest;
	private InterstitialAd interstitial;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useAccelerometer = false;
		cfg.useCompass = false;
		gamehandler = new Handler();
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId("ca-app-pub-2498626873188323/4408283691");
		// Create ad request.
		adRequest = new AdRequest.Builder().build();
		interstitial.loadAd(adRequest);
		interstitial.setAdListener(new AdListener() {
			@Override
			public void onAdClosed() {
				// TODO Auto-generated method stub
				super.onAdClosed();
				interstitial.loadAd(adRequest);
			}
		});

		initialize(new Maybay(), cfg);
	}

	public boolean useCompass() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean useAccelerometer() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setShowSplash() {
		SharedPreferences.Editor editor = getSharedPreferences("splash",
				MODE_PRIVATE).edit();
		editor.putBoolean("show", true);
		editor.commit();
		Toast.makeText(GameActivity.this,
				"Sponsored Splash shows only once at first time App start!",
				Toast.LENGTH_LONG).show();
	}

	public boolean getSplash() {

		if (getNoAds())
			return true;

		SharedPreferences prefs = getSharedPreferences("splash", MODE_PRIVATE);
		boolean rs = prefs.getBoolean("show", false);
		return rs;
	}

	private void initAppflood() {
	}

	public String toastText = "";
	private boolean shownCB;
	private boolean shownAP;
	protected boolean showMoreAppStart;

	public void showToast(final String str) {
		toastText = str;
		gamehandler.sendEmptyMessage(2);
	}

	public void onSubscriptionChecked(boolean supported) {
		// TODO Auto-generated method stub

	}

	public void onBillingChecked(boolean supported) {
		// TODO Auto-generated method stub

	}

	public void showChartboostAds() {
		gamehandler.sendEmptyMessage(1);
	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	public void onStop() {
		super.onStop();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onBackPressed() {

		// If an interstitial is on screen, close it. Otherwise continue as
		// normal.
		// if (this.cb.onBackPressed())
		// return;
		// else {
		// super.onBackPressed();
		// }
	}

	public void showRating() {
		final Intent intent = new Intent("android.intent.action.VIEW");

		intent.setData(Uri.parse("market://details?id="
				+ GameActivity.this.getPackageName()));

		try {

			GameActivity.this.startActivity(intent);

			return;

		}

		catch (Exception ex) {

			Toast.makeText(GameActivity.this, "Market Not Work", 1).show();

			return;

		}
	}

	@Override
	public void moreGames() {
		Intent browserIntent = new Intent(
				Intent.ACTION_VIEW,
				Uri.parse("https://play.google.com/store/apps/developer?id=Gamedroid"));
		startActivity(browserIntent);
	}

	public void buy() {
	}

	@Override
	public void showAds() {
		// Begin loading your interstitial.
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				try {

					if (interstitial.isLoaded()) {
						interstitial.show();
					}
				} catch (Exception ex) {
					return;
				}
			}
		}, 300);
	}

	
	boolean showStart = false;
	@Override
	public void showAds2() {
		// Begin loading your interstitial.
		if (!showStart)
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					try {

						if (interstitial.isLoaded()) {
							interstitial.show();
						}
					} catch (Exception ex) {
						return;
					}
				}
			}, 300);
		showStart = true;
	}

	@Override
	public void showAds(int show) {
		if (show % 3 == 1)
			startFullScreenAd();
		else if (show % 5 == 0) {
			showVideoAds();
		}
	}

	protected void showMoreAppCB() {
		gamehandler.sendEmptyMessage(303);
	}

	@Override
	public void showAppWall() {
		showQuitMoreAppCB();
	}

	private void showQuitMoreAppCB() {
		gamehandler.sendEmptyMessage(404);
	}

	@Override
	public ApplicationListener getApplicationListener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void log(String tag, String message, Throwable exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLogLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addLifecycleListener(LifecycleListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeLifecycleListener(LifecycleListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestPurchase(String coin1000Sku) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buyItem() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getSignedInGPGS() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void loginGPGS() {
		// TODO Auto-generated method stub

	}

	@Override
	public void submitScoreGPGS(int score) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getLeaderboardGPGS() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getAchievementsGPGS() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showOffer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showPrincessGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showFrozenGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showTrollGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void request(String item1) {
		// TODO Auto-generated method stub

	}
}