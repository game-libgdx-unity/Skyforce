package com.doodlegames.air.force.utils;

import com.badlogic.gdx.audio.Sound;
import com.doodlegames.air.force.utils.Clock;
import com.doodlegames.air.force.utils.Settings;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class SoundPlayer {

   public static SoundPlayer soundPlayer = new SoundPlayer();
   float playTimeStamp = -10.0F;
   Queue<Sound> soundQueue = new ArrayBlockingQueue(4);
   float volume = 0.3F;


   public static void clearSoundQueue() {
      soundPlayer.soundQueue.clear();
   }

   public void playSound(Sound var1) {
      if(Settings.getSoundEnable() && !this.soundQueue.offer(var1)) {
         Settings.i("too many sounds are playing.");
      }
   }

   public void playSound(SoundPlayer.SoundProXY var1) {
      if(var1.playIsAllow()) {
         this.playSound(var1.sound);
      }

   }

   public void playSoundWithNoControl(Sound var1) {
      if(Settings.getSoundEnable()) {
         var1.play(this.volume);
      }
   }

   public void update(float var1) {
      if(this.soundQueue.size() != 0 && Clock.getCurrentTime(this.playTimeStamp) > 0.1F) {
         ((Sound)this.soundQueue.poll()).play(this.volume);
         this.playTimeStamp = Clock.getCurrentTime();
      }
   }

   public abstract static class SoundControlCount extends SoundPlayer.SoundProXY {

      float lastPlayTime = 0.0F;
      final float miniSpan;


      public SoundControlCount(String var1, Sound var2, float var3) {
         super(var1, var2);
         this.miniSpan = var3;
      }

      protected abstract boolean playInnerSpan();

      public boolean playIsAllow() {
         if(Clock.getCurrentTime(this.lastPlayTime) < this.miniSpan) {
            if(this.playInnerSpan()) {
               this.lastPlayTime = Clock.getCurrentTime();
               return true;
            } else {
               return false;
            }
         } else {
            this.lastPlayTime = Clock.getCurrentTime();
            return true;
         }
      }
   }

   public static class SoundProXY {

      Sound sound;
      final String soundName;


      public SoundProXY(String var1, Sound var2) {
         this.soundName = var1;
         this.sound = var2;
      }

      public void dispose() {
         this.sound.dispose();
      }

      public boolean playIsAllow() {
         return true;
      }
   }

   public static class SoundInnerStop extends SoundPlayer.SoundControlCount {

      public SoundInnerStop(String var1, Sound var2, float var3) {
         super(var1, var2, var3);
      }

      protected boolean playInnerSpan() {
         super.sound.stop();
         return true;
      }
   }

   public static class SoundInnerIgnore extends SoundPlayer.SoundControlCount {

      public SoundInnerIgnore(String var1, Sound var2, float var3) {
         super(var1, var2, var3);
      }

      protected boolean playInnerSpan() {
         return false;
      }
   }

   public static class AutoDecreaseInt {

      final float decreaseSpan;
      int num = 0;
      final float numLimit;
      float timeCounter;


      public AutoDecreaseInt(float var1, int var2) {
         this.numLimit = (float)var2;
         this.decreaseSpan = var1;
         this.timeCounter = 0.0F;
      }

      public boolean increase() {
         if((float)(1 + this.num) <= this.numLimit) {
            ++this.num;
            return true;
         } else {
            return false;
         }
      }

      public void update(float var1) {
         if(this.timeCounter < 0.0F) {
            this.timeCounter = this.decreaseSpan;
            if(this.num > 1) {
               this.num += -2;
            } else {
               this.num = 0;
            }
         }

         this.timeCounter -= var1;
      }
   }
}
