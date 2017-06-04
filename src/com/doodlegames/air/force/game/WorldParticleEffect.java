package com.doodlegames.air.force.game;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.doodlegames.air.force.utils.Settings;

public class WorldParticleEffect {

   public static ParticleEffectPool bombEffectPool;
   public static Array<ParticleEffectPool.PooledEffect> effects = new Array();


   public static void addPropParticle(float var0, float var1) {
      if(bombEffectPool == null) {
         Settings.e("bombEffectPool can not be null.");
      } else {
         ParticleEffectPool.PooledEffect var2 = bombEffectPool.obtain();
         var2.setPosition(var0, var1);
         effects.add(var2);
      }
   }

   public static void drawPropParticle(SpriteBatch var0, float var1) {
      for(int var2 = -1 + effects.size; var2 >= 0; --var2) {
         ParticleEffectPool.PooledEffect var3 = (ParticleEffectPool.PooledEffect)effects.get(var2);
         var3.draw(var0, var1);
         if(var3.isComplete()) {
            var3.free();
            effects.removeIndex(var2);
         }
      }

   }

   public static void loadParticleEffect(ParticleEffect var0) {
      bombEffectPool = new ParticleEffectPool(var0, 5, 8);
   }

   public static void resetParticle() {
      for(int var0 = -1 + effects.size; var0 >= 0; --var0) {
         ((ParticleEffectPool.PooledEffect)effects.get(var0)).free();
      }

      effects.clear();
   }
}
