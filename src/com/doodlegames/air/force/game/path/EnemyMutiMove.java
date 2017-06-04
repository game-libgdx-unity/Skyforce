package com.doodlegames.air.force.game.path;

import com.badlogic.gdx.utils.Array;
import com.doodlegames.air.force.enemy.Enemy;
import com.doodlegames.air.force.game.path.AbstractEnemyPath;
import com.doodlegames.air.force.utils.Settings;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class EnemyMutiMove extends AbstractEnemyPath {

   public static Pattern pattern = Pattern.compile("(.*(point).*)");
   private AbstractEnemyPath currentMove;
   protected ArrayList<AbstractEnemyPath> paths = new ArrayList();


   public EnemyMutiMove(Enemy var1, float var2, float var3, String var4, AbstractEnemyPath ... var5) {
      super(var1, var2, var3, var4);
      float var6 = 0.0F;
      int var7 = var5.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         AbstractEnemyPath var9 = var5[var8];
         if(var9.getLimitTime() < 0.0F) {
            Settings.w("can not add a path with a negative limitTime. ignore it already!");
         } else if(var9 instanceof EnemyMutiMove) {
            Settings.w("can not add a MutiMovePath, ignore it already.");
         } else {
            var6 += var9.getLimitTime();
            this.paths.add(var9);
         }
      }

      this.setLimitTime(var6);
      if(this.paths.size() == 0) {
         this.currentMove = null;
      } else {
         this.currentMove = (AbstractEnemyPath)this.paths.get(0);
      }
   }

   protected float computInitAngle() {
      return this.paths.size() != 0?((AbstractEnemyPath)this.paths.get(0)).restartFrom(this.getX(), this.getY()):0.0F;
   }

   public AbstractEnemyPath getCurrentMove() {
      return this.currentMove;
   }

   public boolean isOver() {
      int var1 = this.paths.size();
      if(this.currentMove != null && var1 != 0) {
         boolean var2;
         if(this.paths.indexOf(this.currentMove) == var1 - 1) {
            var2 = true;
         } else {
            var2 = false;
         }

         if(!this.currentMove.isOver() || !var2) {
            return false;
         }
      }

      return true;
   }

   public float moveForward(float var1) {
      super.moveForward(var1);
      if(!this.isOver()) {
         if(this.currentMove.isOver()) {
            int var4 = 1 + this.paths.indexOf(this.currentMove);
            if(var4 < this.paths.size()) {
               AbstractEnemyPath var5 = this.currentMove;
               this.currentMove = (AbstractEnemyPath)this.paths.get(var4);
               this.currentMove.restartFrom(var5.getX(), var5.getY());
            }
         }

         float var3 = this.currentMove.moveForward(var1);
         this.x = this.currentMove.getX();
         this.y = this.currentMove.getY();
         return var3;
      } else {
         return this.enemy.getAngle();
      }
   }

   public float restartFrom(float var1, float var2) {
      if(this.paths.size() != 0) {
         this.currentMove = (AbstractEnemyPath)this.paths.get(0);
         return this.currentMove.restartFrom(var1, var2);
      } else {
         return 0.0F;
      }
   }

   public void setNextPath() {
      int var1;
      for(var1 = this.paths.indexOf(this.currentMove); var1 >= 0 && var1 < this.paths.size(); ++var1) {
         String var5 = ((AbstractEnemyPath)this.paths.get(var1)).pathName;
         if(pattern.matcher(var5).find()) {
            break;
         }
      }

      int var2 = var1 + 1;
      if(var2 >= 0 && var2 < this.paths.size()) {
         AbstractEnemyPath var3 = this.currentMove;
         this.currentMove = (AbstractEnemyPath)this.paths.get(var2);
         this.currentMove.restartFrom(var3.getX(), var3.getY());
      }

   }

   public interface IPathInfoSet {

      void addPathInfo(AbstractEnemyPath.PathInfo var1);

      AbstractEnemyPath.PathInfo getPathInfo(int var1);

      AbstractEnemyPath.PathInfo getPathInfo(String var1);

      int getPathInfoLength();
   }

   public static class MutiPathInfo extends AbstractEnemyPath.PathInfo {

      EnemyMutiMove.IPathInfoSet infoProvider;
      Array<AbstractEnemyPath.PathInfo> pathInfos = new Array(4);
      String strPathName = " ";


      public MutiPathInfo() {
         this.pathInfos.clear();
      }

      public void deSerialize(BufferedReader var1) {
         super.deSerialize(var1);

         try {
            this.strPathName = var1.readLine();
         } catch (Exception var3) {
            Settings.w(var3.getMessage());
         }
      }

      public int fillPathInfos(EnemyMutiMove.IPathInfoSet var1) {
         if(this.pathInfos.size != 0) {
            return this.pathInfos.size;
         } else if(var1 == null) {
            return 0;
         } else {
            this.infoProvider = var1;
            String[] var2 = this.strPathName.split("\\s+");
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               String var5 = var2[var4];
               if(var5.trim().length() != 0) {
                  AbstractEnemyPath.PathInfo var6 = var1.getPathInfo(var5);
                  if(var6 != null) {
                     this.pathInfos.add(var6);
                  }
               }
            }

            return this.pathInfos.size;
         }
      }

      public AbstractEnemyPath getMoveProcessor(Enemy var1) {
         if(this.fillPathInfos(this.infoProvider) == 0) {
            return null;
         } else {
            AbstractEnemyPath[] var2 = new AbstractEnemyPath[this.pathInfos.size];

            for(int var3 = 0; var3 < this.pathInfos.size; ++var3) {
               var2[var3] = ((AbstractEnemyPath.PathInfo)this.pathInfos.get(var3)).getMoveProcessor(var1);
            }

            return new EnemyMutiMove(var1, super.startPoint.x, super.startPoint.y, super.pathName, var2);
         }
      }
   }
}
