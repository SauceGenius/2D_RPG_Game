package stats;

import audio.AudioPlayer;
import core.Log;
import equipment.Equipment;
import id.GameClassId;
import id.RaceId;

public class Level {
    private int level;
    private int exp;
    private int expToNextLevel;

    private RaceId raceId;
    private GameClassId gameClassId;

    public Level(int level){
        this.level = level;
        this.exp = 0;
        this.expToNextLevel = this.level * 100;
    }

    public Level(int level, RaceId raceId, GameClassId gameClassId){
        this.level = level;
        this.raceId = raceId;
        this.gameClassId = gameClassId;
        this.exp = 0;
        this.expToNextLevel = this.level * 100;
    }

    public void update(Stats stat, Equipment equipment, AudioPlayer audioPlayer, Log log){
        if(exp >= expToNextLevel){
            levelUp(stat, equipment, audioPlayer, log);
        }
    }

    public void gainExp(int exp){
        this.exp += exp;
    }

    private void levelUp(Stats stats, Equipment equipment, AudioPlayer audioPlayer, Log log) {
        while(exp >= expToNextLevel){
            exp = exp - expToNextLevel;
            level += 1;
            audioPlayer.playSound("LevelUp.wav");

            log.addToGeneral("LevelUp","Congratulations, you have reached level " + level + "!");

            if(raceId == RaceId.human && gameClassId == GameClassId.Warrior){
                log.addToGeneral("LevelUp", "You have gained " + (1 * 10 + 9) + " hit points.");
                stats.gain("stamina", 1, log);
                stats.gain("strength",1, log);
                stats.gain("agility",1, log);
            }

            /** Numbers are to be confirmed **/
            else if (raceId == RaceId.human && gameClassId == GameClassId.Paladin){
                log.addToGeneral("LevelUp", "You have gained " + (1 * 10 + 9) + " hit points.");
                stats.gain("stamina", 1, log);
                stats.gain("strength",1, log);
                stats.gain("agility",1, log);
            }

            stats.setMaxHpValue((level - 1) * 9 + (stats.getBaseStat(Stats.STAMINA) - 20) * 10 + 40 + equipment.getTotalStaminaBonus());
            stats.setCurrentHpValue(stats.getMaxHpValue());
        }
    }


    //Setters & Getters
    public void setLevel(int level) {this.level = level;}
    public void setExp(int exp) {this.exp = exp;}
    public void setExpToNextLevel(int expToNextLevel) {this.expToNextLevel = expToNextLevel;}

    public int getLevelValue() {return level;}
    public int getExp() {return exp;}
    public int getExpToNextLevel() {return expToNextLevel;}
}
