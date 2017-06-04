package com.doodlegames.air.force.utils;

import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Clock {

   private static long frameNo = 1L;
   public static final Random rand = new Random();
   private static boolean stopped = false;
   private static double timeCounter = 0.0D;
   public boolean checkLondDelay = true;
   private Clock.ClockType clockType;
   private int cycleCount = 0;
   private float delay;
   private Clock.ClockFireEvent event = new Clock.ClockFireEvent();
   private byte fireCounter;
   private float iniAhead = 0.0F;
   private ArrayList<Clock.FireListener> listeners = new ArrayList();
   private byte num;
   private float span;
   private double state;


   public Clock(float var1) {
      this.iniAhead = var1 * rand.nextFloat();
      this.state = timeCounter - (double)this.iniAhead;
      this.span = var1;
      this.delay = 0.0F;
      this.num = 1;
      this.fireCounter = 0;
      this.clockType = Clock.ClockType.LoopFire;
   }

   public Clock(float var1, float var2) {
      if(var1 > var2) {
         var1 = 0.0F;
      }

      this.iniAhead = var1;
      this.state = timeCounter - (double)var1;
      this.span = var2;
      this.delay = 0.0F;
      this.num = 1;
      this.fireCounter = 0;
      this.clockType = Clock.ClockType.LoopFire;
   }

   public Clock(float var1, float var2, byte var3) {
      this.iniAhead = var1 * rand.nextFloat();
      this.state = timeCounter - (double)this.iniAhead;
      this.span = var1;
      this.delay = var2;
      this.fireCounter = 0;
      this.num = var3;
      this.clockType = Clock.ClockType.LoopFire;
   }

   public Clock(float var1, float var2, float var3, byte var4) {
      if(var1 > var2) {
         var1 = 0.0F;
      }

      this.iniAhead = var1;
      this.state = timeCounter - (double)var1;
      this.span = var2;
      this.delay = var3;
      this.fireCounter = 0;
      this.num = var4;
      this.clockType = Clock.ClockType.LoopFire;
   }

   public Clock(float var1, float var2, Clock.ClockType var3) {
      if(var3 == Clock.ClockType.FireOnlyOnce || var1 > var2) {
         var1 = 0.0F;
      }

      this.iniAhead = var1;
      this.state = timeCounter - (double)var1;
      this.span = var2;
      this.delay = 0.0F;
      this.num = 1;
      this.fireCounter = 0;
      this.clockType = var3;
   }

   public Clock(Clock var1) {
      this.span = var1.span;
      this.delay = var1.delay;
      this.num = var1.num;
      this.clockType = var1.clockType;
      this.iniAhead = var1.iniAhead;
      this.state = timeCounter - (double)this.iniAhead;
      this.fireCounter = 0;
   }

   private boolean checkFired() {

       if (!this.checkLongDelay()) {

           switch (this.clockType) {

               case FireOnlyOnce: {

                   if (this.state + (double)this.span <= Clock.timeCounter && this.fireCounter == 0) {

                       this.fireCounter = (byte)(1 + this.fireCounter);

                       return true;

                   }

                   break;

               }

               case LoopFire: {

                   if (this.state + (double)this.span <= Clock.timeCounter) {

                       if (this.fireCounter < -1 + this.num) {

                           this.fireCounter = (byte)(1 + this.fireCounter);

                           this.state = Clock.timeCounter - (double)this.span + (double)this.delay;

                       }

                       else {

                           this.state = Clock.timeCounter - (double)(this.delay * (float)(-1 + this.num));

                           this.fireCounter = 0;

                       }

                       return true;

                   }

                   break;

               }

               default: {

                   Settings.appLog("unreached code!");

                   return false;

               }

           }

       }

       return false;

   }

   private boolean checkLongDelay() {
      if(this.checkLondDelay && this.state + (double)this.span < timeCounter - 0.10000000149011612D) {
         this.state = timeCounter - (double)this.iniAhead;
         return true;
      } else {
         return false;
      }
   }

   public static double getCurrentTime(double var0) {
      return getTimeCounter() - var0;
   }

   public static float getCurrentTime() {
      return (float)timeCounter;
   }

   public static float getCurrentTime(float var0) {
      return getCurrentTime() - var0;
   }

   public static Clock getFireOnceClock(float var0) {
      return new Clock(0.0F, var0, Clock.ClockType.FireOnlyOnce);
   }

   public static long getFrameNo() {
      return frameNo;
   }

   public static double getTimeCounter() {
      return timeCounter;
   }

   private void notifyListeners() {
      Iterator var1 = this.listeners.iterator();

      while(var1.hasNext()) {
         Clock.FireListener var2 = (Clock.FireListener)var1.next();
         this.event.reset();
         var2.handle(this.event);
      }

   }

   public static void update(float var0) {
      if(!stopped) {
         if(timeCounter == 0.0D) {
            frameNo = 1L;
         } else {
            ++frameNo;
         }

         timeCounter += (double)var0;
      }
   }

   public void addListener(Clock.FireListener var1) {
      if(!this.listeners.contains(var1)) {
         this.listeners.add(var1);
      }

   }

   public void clearListeners() {
      this.listeners.clear();
   }

   public Clock.ClockType getClockType() {
      return this.clockType;
   }

   public int getCycleCount() {
      return this.cycleCount;
   }

   public float getDelay() {
      return this.delay;
   }

   public byte getFireCounter() {
      return this.fireCounter;
   }

   public float getIniAhead() {
      return this.iniAhead;
   }

   public byte getNum() {
      return this.num;
   }

   public float getSpan() {
      return this.span;
   }

   public double getState() {
      return this.state;
   }

   public boolean isFired() {
      if(!this.checkFired()) {
         return false;
      } else {
         if(this.fireCounter == 0 || this.fireCounter == 1) {
            ++this.cycleCount;
         }

         this.notifyListeners();
         return true;
      }
   }

   public boolean isFired(int var1) {
      return !this.checkLongDelay() && this.state + (double)this.span <= timeCounter;
   }

   public boolean removeListener(Clock.FireListener var1) {
      return this.listeners.remove(var1);
   }

   public void resetClock(float var1, float var2, float var3, byte var4) {
      if(var1 > var2) {
         var1 = 0.0F;
      }

      this.iniAhead = var1;
      this.state = timeCounter - (double)var1;
      this.span = var2;
      this.delay = var3;
      this.fireCounter = 0;
      this.num = var4;
      this.clockType = Clock.ClockType.LoopFire;
      this.cycleCount = 0;
   }

   public void resetClock(boolean var1, float var2, float var3, float var4, byte var5) {
      if(var1) {
         this.resetClockFireOnce(var3);
      } else {
         this.resetClock(var2, var3, var4, var5);
      }
   }

   public void resetClockFireOnce(float var1) {
      this.resetClock(0.0F, var1, 0.0F, (byte)1);
      this.clockType = Clock.ClockType.FireOnlyOnce;
   }

   public void restartClock() {
      this.restartClock(this.iniAhead);
   }

   public void restartClock(float var1) {
      if(var1 > this.span) {
         var1 = this.span;
      }

      this.iniAhead = var1;
      this.fireCounter = 0;
      this.state = timeCounter - (double)var1;
   }

   public void setIniAhead(float var1) {
      if(var1 < this.span) {
         this.iniAhead = var1;
      }

   }

   public static enum ClockType {

      FireOnlyOnce,
      LoopFire
   }

   public interface FireListener {

      void handle(Clock.ClockFireEvent var1);
   }

   public class ClockFireEvent {

      public final Clock clock = Clock.this;


      private void reset() {}
   }
}
