package com.doodlegames.utils;


public interface AndroidService { 

	void showAds(int show);
	
	void showAds();

	void share();  

	void rate();

	void showAppWall();

	void buy();

	void requestPurchase(String coin1000Sku);

	void buyItem();

	public boolean getSignedInGPGS();

	public void loginGPGS();

	public void submitScoreGPGS(int score);

	public void unlockAchievementGPGS(String achievementId);

	public void getLeaderboardGPGS();

	public void getAchievementsGPGS();

	void showToast(String string);

	void showVideoAds();

	boolean isVideoAvai();

	void moreGames(); 
}
