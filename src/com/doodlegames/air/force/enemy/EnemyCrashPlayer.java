package com.doodlegames.air.force.enemy;

import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.WorldAnimationPlayer;
import com.doodlegames.air.force.game.WorldAudio;
import com.doodlegames.air.force.utils.Animation;
import com.doodlegames.air.force.utils.Clock;

public class EnemyCrashPlayer {

   public static final float LONGTHRESHOLD = 100.0F;


   public static void putCrashAnimation(Enemy var0) {
      float var1 = (float)Clock.getTimeCounter();
      boolean var2;
      if(var0.bounds.height > 100.0F) {
         var2 = true;
      } else {
         var2 = false;
      }

      Boolean var3 = Boolean.valueOf(var2);
      Animation var4 = var0.getShatterAniamtion();
      Animation var5 = var0.getCrashBoom(var3.booleanValue());
      WorldAudio.PlaySound var6 = var0.getCrashSound(var3.booleanValue());
      if(var3.booleanValue()) {
         int var10 = (int)(var0.bounds.height / 100.0F);
         float var11 = var0.bounds.height / (float)var10;

         for(int var12 = 0; var12 < var10; ++var12) {
            float var13;
            if(var0.world.rand.nextBoolean()) {
               var13 = 0.0F;
            } else {
               var13 = var0.bounds.width / 2.0F;
            }

            float var14 = var13 + var0.bounds.width / 4.0F;
            float var15 = 50.0F + var11 * (float)var12;
            float var16 = var14 + var0.bounds.x;
            float var17 = var15 + var0.bounds.y;
            WorldAnimationPlayer.addAWorldAnimation(var4, var16, var17, 1, var1);
            WorldAnimationPlayer.addAWorldAnimation(var5, var16, var17, 1, var1, var6);
            var1 += 0.1F;
         }
      } else {
         float var7 = var0.getPositionX();
         float var8 = var0.getPositionY();
         WorldAnimationPlayer.addAWorldAnimation(var4, var7, var8, 1);
         WorldAnimationPlayer.addAWorldAnimation(var5, var7, var8, 1, var6);
      }

   }
}
