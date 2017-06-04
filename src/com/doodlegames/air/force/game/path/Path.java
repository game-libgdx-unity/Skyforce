package com.doodlegames.air.force.game.path;

import com.badlogic.gdx.math.Vector2;
import com.doodlegames.air.force.game.DynamicGameObject;
import com.doodlegames.air.force.game.path.GameObjectTravelPath;
import com.doodlegames.air.force.utils.Settings;

public abstract class Path implements GameObjectTravelPath {

   private Vector2 MovingOffset;
   private float MovingTime;
   private Vector2 currentRelativePosition;
   protected DynamicGameObject object;
   private Path.PathState pathState;
   protected Vector2 startPoint;


   public Path(DynamicGameObject var1) {
      this.startPoint = new Vector2(var1.position);
      this.MovingOffset = new Vector2();
      this.currentRelativePosition = new Vector2(var1.position);
      this.initPath(var1);
   }

   protected abstract float MovingForwad(float var1, Vector2 var2);

   public float beginTravel() {
      if(this.pathState == Path.PathState.Sleep) {
         this.pathState = Path.PathState.Moving;
      } else {
         Settings.appLog("you should not invoked Path.beginTravel() when its state is Moving.");
      }

      return this.computerInitAngle();
   }

   protected abstract float computerInitAngle();

   protected float getMovingTime() {
      return this.MovingTime;
   }

   protected Path.PathState getPathState() {
      return this.pathState;
   }

   public void initPath(DynamicGameObject var1) {
      this.MovingTime = 0.0F;
      this.pathState = Path.PathState.Sleep;
      this.object = var1;
      this.startPoint.set(this.object.position);
      this.MovingOffset.set(0.0F, 0.0F);
      this.currentRelativePosition.set(this.object.position);
   }

   public void stopTravel() {
      if(this.pathState == Path.PathState.Moving) {
         this.pathState = Path.PathState.Sleep;
      } else {
         Settings.appLog("you should not invoked Path.stopTravel() when its state is Sleep.");
      }
   }

   public float updateTravel(final float n) {

       switch (this.pathState) {

           case Sleep: {

               return 0.0f;

           }

           case Moving: {

               this.MovingOffset.set(0.0f, 0.0f);

               final float movingForwad = this.MovingForwad(n, this.MovingOffset);

               if (this.MovingOffset.x != 0.0f || this.MovingOffset.y != 0.0f) {

                   this.currentRelativePosition.add(this.MovingOffset);

                   this.object.PositionAdd(this.MovingOffset.x, this.MovingOffset.y);

               }

               this.MovingTime = n + this.MovingTime;

               return movingForwad;

           }

           default: {

               Settings.appLog("invalid state in path.updateTravel()");

               return 0.0f;

           }

       }

   }

   protected static enum PathState {

      Moving,
      Sleep
   }
}
