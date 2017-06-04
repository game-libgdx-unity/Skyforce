package com.doodlegames.air.force.game;

import com.doodlegames.air.force.game.World;
import com.doodlegames.air.force.resource.Assets_Audio;
import com.doodlegames.air.force.utils.SoundPlayer;

public class WorldAudio {

   private SoundPlayer.SoundProXY alienBigBoomProXY;
   private SoundPlayer.SoundProXY alienLaserGatherProXY;
   private SoundPlayer.SoundProXY alienLaserShootProXY;
   private SoundPlayer.SoundProXY alienSmallBoomProXY;
   private SoundPlayer.SoundProXY bulletHitEnemyProXY;
   private SoundPlayer.SoundProXY fighterBullet1ProXY;
   private SoundPlayer.SoundProXY fighterBullet2ProXY;
   private SoundPlayer.SoundProXY fighterBullet3ProXY;
   private SoundPlayer.SoundProXY getCoinProXY;
   private SoundPlayer.SoundProXY getLifeProXY;
   private SoundPlayer.SoundProXY getPropProXY;
   private SoundPlayer.SoundProXY naziBigBoomProXY;
   private SoundPlayer.SoundProXY naziLaserGatherProXY;
   private SoundPlayer.SoundProXY naziLaserShootProXY;
   private SoundPlayer.SoundProXY naziSmallBoomProXY;
   public WorldAudio.PlaySound playAlienBigBoom = new WorldAudio.PlaySound() {
      public void playSound() {
         WorldAudio.this.playAlienBigBoom();
      }
   };
   public WorldAudio.PlaySound playAlienLaseGather = new WorldAudio.PlaySound() {
      public void playSound() {
         WorldAudio.this.playAlienLaseGather();
      }
   };
   public WorldAudio.PlaySound playAlienLaseShooting = new WorldAudio.PlaySound() {
      public void playSound() {
         WorldAudio.this.playAlienLaseShooting();
      }
   };
   public WorldAudio.PlaySound playAlienSmallBoom = new WorldAudio.PlaySound() {
      public void playSound() {
         WorldAudio.this.playAlienSmallBoom();
      }
   };
   public WorldAudio.PlaySound playBulletHitEnemy = new WorldAudio.PlaySound() {
      public void playSound() {
         WorldAudio.this.playBulletHitEnemy();
      }
   };
   public WorldAudio.PlaySound playFighterBullet1 = new WorldAudio.PlaySound() {
      public void playSound() {
         WorldAudio.this.playFighterBullet1();
      }
   };
   public WorldAudio.PlaySound playFighterBullet2 = new WorldAudio.PlaySound() {
      public void playSound() {
         WorldAudio.this.playFighterBullet2();
      }
   };
   public WorldAudio.PlaySound playFighterBullet3 = new WorldAudio.PlaySound() {
      public void playSound() {
         WorldAudio.this.playFighterBullet3();
      }
   };
   public WorldAudio.PlaySound playGameOver = new WorldAudio.PlaySound() {
      public void playSound() {
         WorldAudio.this.playGameOver();
      }
   };
   public WorldAudio.PlaySound playGetCoin = new WorldAudio.PlaySound() {
      public void playSound() {
         WorldAudio.this.playGetCoin();
      }
   };
   public WorldAudio.PlaySound playGetLife = new WorldAudio.PlaySound() {
      public void playSound() {
         WorldAudio.this.playGetLife();
      }
   };
   public WorldAudio.PlaySound playGetProp = new WorldAudio.PlaySound() {
      public void playSound() {
         WorldAudio.this.playGetProp();
      }
   };
   public WorldAudio.PlaySound playNaziBigBoom = new WorldAudio.PlaySound() {
      public void playSound() {
         WorldAudio.this.playNaziBigBoom();
      }
   };
   public WorldAudio.PlaySound playNaziLaseGather = new WorldAudio.PlaySound() {
      public void playSound() {
         WorldAudio.this.playNaziLaseGather();
      }
   };
   public WorldAudio.PlaySound playNaziLaseShooting = new WorldAudio.PlaySound() {
      public void playSound() {
         WorldAudio.this.playNaziLaseShooting();
      }
   };
   public WorldAudio.PlaySound playNaziSmallBoom = new WorldAudio.PlaySound() {
      public void playSound() {
         WorldAudio.this.playNaziSmallBoom();
      }
   };
   public WorldAudio.PlaySound playWarning = new WorldAudio.PlaySound() {
      public void playSound() {
         WorldAudio.this.playWarning();
      }
   };
   public WorldAudio.PlaySound playWin = new WorldAudio.PlaySound() {
      public void playSound() {
         WorldAudio.this.playWin();
      }
   };
   private SoundPlayer.SoundProXY warningProXY;
   public final World world;


   public WorldAudio(World var1) {
      this.world = var1;
      this.bulletHitEnemyProXY = new SoundPlayer.SoundInnerIgnore("bulletHitEnemy", Assets_Audio.bulletHitEnemy, 0.5F);
      this.getCoinProXY = new SoundPlayer.SoundInnerStop("getCoin", Assets_Audio.getCoin, 0.5F);
      this.getPropProXY = new SoundPlayer.SoundInnerIgnore("getProp", Assets_Audio.getProp, 0.5F);
      this.getLifeProXY = new SoundPlayer.SoundInnerStop("getLife", Assets_Audio.getLife, 0.5F);
      this.naziBigBoomProXY = new SoundPlayer.SoundInnerStop("naziBigBoom", Assets_Audio.naziBigBoom, 2.0F);
      this.alienBigBoomProXY = new SoundPlayer.SoundInnerStop("alienBigBoom", Assets_Audio.alienBigBoom, 2.0F);
      this.naziSmallBoomProXY = new SoundPlayer.SoundInnerStop("naziSmallBoom", Assets_Audio.naziSmallBoom, 2.0F);
      this.alienSmallBoomProXY = new SoundPlayer.SoundInnerStop("alienSmallBoom", Assets_Audio.alienSmallBoom, 2.0F);
      this.naziLaserGatherProXY = new SoundPlayer.SoundInnerStop("naziLaserGather", Assets_Audio.naziLaseGather, 2.0F);
      this.alienLaserGatherProXY = new SoundPlayer.SoundInnerStop("alienLaserGather", Assets_Audio.alienLaseGather, 2.0F);
      this.naziLaserShootProXY = new SoundPlayer.SoundInnerStop("naziLaserShoot", Assets_Audio.naziLaseShooting, 2.0F);
      this.alienLaserShootProXY = new SoundPlayer.SoundInnerStop("alienLaserShoot", Assets_Audio.alienLaseShooting, 2.0F);
      this.fighterBullet1ProXY = new SoundPlayer.SoundInnerStop("fighterBullet1", Assets_Audio.fighterBullet1, 1.0F);
      this.fighterBullet2ProXY = new SoundPlayer.SoundInnerStop("fighterBullet2", Assets_Audio.fighterBullet2, 1.0F);
      this.fighterBullet3ProXY = new SoundPlayer.SoundInnerStop("fighterBullet3", Assets_Audio.fighterBullet3, 1.0F);
      this.warningProXY = new SoundPlayer.SoundInnerStop("warning", Assets_Audio.warning, 1.0F);
   }

   public void playAlienBigBoom() {
      SoundPlayer.soundPlayer.playSound(this.alienBigBoomProXY);
   }

   public void playAlienLaseGather() {
      SoundPlayer.soundPlayer.playSound(this.alienLaserGatherProXY);
   }

   public void playAlienLaseShooting() {
      SoundPlayer.soundPlayer.playSound(this.alienLaserShootProXY);
   }

   public void playAlienSmallBoom() {
      SoundPlayer.soundPlayer.playSound(this.alienSmallBoomProXY);
   }

   public void playBackButton() {
      SoundPlayer.soundPlayer.playSound(Assets_Audio.backButton);
   }

   public void playBulletHitEnemy() {
      SoundPlayer.soundPlayer.playSound(this.bulletHitEnemyProXY);
   }

   public void playFighterBullet1() {}

   public void playFighterBullet2() {}

   public void playFighterBullet3() {}

   public void playGameOver() {
      SoundPlayer.soundPlayer.playSoundWithNoControl(Assets_Audio.gameOver);
   }

   public void playGetCoin() {
      SoundPlayer.soundPlayer.playSound(this.getCoinProXY);
   }

   public void playGetLife() {
      SoundPlayer.soundPlayer.playSound(this.getLifeProXY);
   }

   public void playGetProp() {
      SoundPlayer.soundPlayer.playSound(this.getPropProXY);
   }

   public void playNaziBigBoom() {
      SoundPlayer.soundPlayer.playSound(this.naziBigBoomProXY);
   }

   public void playNaziLaseGather() {
      SoundPlayer.soundPlayer.playSound(this.naziLaserGatherProXY);
   }

   public void playNaziLaseShooting() {
      SoundPlayer.soundPlayer.playSound(this.naziLaserShootProXY);
   }

   public void playNaziSmallBoom() {
      SoundPlayer.soundPlayer.playSound(this.naziSmallBoomProXY);
   }

   public void playNormalButton() {
      SoundPlayer.soundPlayer.playSound(Assets_Audio.normalButton);
   }

   public void playWarning() {
      SoundPlayer.soundPlayer.playSound(this.warningProXY);
   }

   public void playWin() {
      SoundPlayer.soundPlayer.playSoundWithNoControl(Assets_Audio.win);
   }

   public interface PlaySound {

      void playSound();
   }
}
