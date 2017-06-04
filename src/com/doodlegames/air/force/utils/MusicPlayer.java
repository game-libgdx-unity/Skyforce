package com.doodlegames.air.force.utils;

import com.badlogic.gdx.audio.Music;
import com.doodlegames.air.force.utils.Settings;

public class MusicPlayer {

   public static MusicPlayer musicPlayer = new MusicPlayer();
   public Music currentMusic = null;
   public float volume = 1F;


   public void clearCurrentMusic() {
      this.currentMusic = null;
   }

   public void dispose() {
      if(this.currentMusic != null) {
         this.currentMusic.dispose();
         this.currentMusic = null;
      }

   }

   public void pause() {
      if(this.currentMusic != null) {
         this.currentMusic.pause();
      }

   }

   public void play() {
      if(this.currentMusic != null && Settings.getMusicEnable()) {
         this.currentMusic.play();
         this.currentMusic.setLooping(true);
         this.currentMusic.setVolume(this.volume);
      }

   }

   public boolean playMusic(Music var1) {
      if(this.currentMusic == null) {
         this.currentMusic = var1;
      }

      if(Settings.getMusicEnable()) {
         if(this.currentMusic != var1) {
            this.currentMusic.stop();
         }

         this.currentMusic = var1;
         this.currentMusic.play();
         this.currentMusic.setLooping(true);
         this.currentMusic.setVolume(this.volume);
      }

      return Settings.getMusicEnable();
   }

   public void resume() {
      this.play();
   }

   public void setVolume(float var1) {
      this.volume = var1;
      this.currentMusic.setVolume(this.volume);
   }

   public void stop() {
      if(this.currentMusic != null) {
         this.currentMusic.stop();
      }

   }
}
