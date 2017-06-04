package com.doodlegames.air.force.game.path;

import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.fighters.Fighter;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.path.Path;

public class WingFighterPath extends Path {

   private static final int FIXIGNORE = 3;
   private static final float SPEEDUPRATIO = 5.0F;
   private float cachedDeltaX;
   private float cachedDeltaY;
   private Vector2 currentVel = new Vector2(0.0F, 0.0F);
   private Fighter fighter;


   public WingFighterPath(DynamicGameObject var1, Fighter var2) {
      super(var1);
      this.initWingFighterPath(var2);
   }

   private void initWingFighterPath(Fighter var1) {
      this.fighter = var1;
      this.cachedDeltaX = 0.0F;
      this.cachedDeltaY = 0.0F;
   }

   protected float MovingForwad(float var1, Vector2 var2) {
      var2.set(var1 * this.currentVel.x, var1 * this.currentVel.y);
      return 0.0F;
   }

   protected float computerInitAngle() {
      return 0.0F;
   }

   public float updateTravel(float var1) {
      if(this.getPathState() == Path.PathState.Moving) {
         float var3 = this.object.getPositionX();
         float var4 = this.object.getPositionY();
         float var5 = this.fighter.getPositionX() - var3;
         float var6 = this.fighter.getPositionY() - var4;
         if(1.0F * Math.abs(var5) < 3.0F) {
            this.currentVel.x = 0.0F;
            this.cachedDeltaX = 0.0F;
            var3 = this.fighter.getPositionX();
         } else {
            if(var5 * this.currentVel.x <= 0.0F || Math.abs(this.cachedDeltaX) < Math.abs(var5)) {
               this.currentVel.x = var5 * 5.0F;
            }

            this.cachedDeltaX = var5;
         }

         if(1.0F * Math.abs(var6) < 3.0F) {
            this.currentVel.y = 0.0F;
            this.cachedDeltaY = 0.0F;
            var4 = this.fighter.getPositionY();
         } else {
            if(var6 * this.currentVel.y <= 0.0F || Math.abs(this.cachedDeltaY) < Math.abs(var6)) {
               this.currentVel.y = var6 * 5.0F;
            }

            this.cachedDeltaY = var6;
         }

         this.object.setPosition(var3, var4);
      }

      super.updateTravel(var1);
      return 0.0F;
   }
}
