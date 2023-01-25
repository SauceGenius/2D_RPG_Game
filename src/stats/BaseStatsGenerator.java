package stats;

import id.GameClassId;
import id.RaceId;

public class BaseStatsGenerator {

    private GameClassId gameClassId;
    private RaceId raceId;

    public  BaseStatsGenerator(RaceId raceId, GameClassId gameClassId){
        this.raceId = raceId;
        this.gameClassId = gameClassId;
    }

    // Get base stats for the class method
    public int[] getBaseStats(){
        int[] baseStats = new int[5];
        // [0] : Stamina
        // [1] : Strength
        // [2] : Intellect
        // [3] : Agility
        // [4] : Spirit

        /*
        if(gameClassId == GameClassId.druid){
            baseStats[0] = 24;
            baseStats[1] = 26;
            baseStats[2] = 17;
            baseStats[3] = 17;
            baseStats[4] = 23;
        }

        if(gameClassId == GameClassId.hunter){
            baseStats[0] = 24;
            baseStats[1] = 26;
            baseStats[2] = 17;
            baseStats[3] = 17;
            baseStats[4] = 23;
        }

        if(gameClassId == GameClassId.mage){
            baseStats[0] = 24;
            baseStats[1] = 26;
            baseStats[2] = 17;
            baseStats[3] = 17;
            baseStats[4] = 23;
        }

        if(gameClassId == GameClassId.paladin){
            baseStats[0] = 24;
            baseStats[1] = 26;
            baseStats[2] = 17;
            baseStats[3] = 17;
            baseStats[4] = 23;
        }

        if(gameClassId == GameClassId.priest){
            baseStats[0] = 24;
            baseStats[1] = 26;
            baseStats[2] = 17;
            baseStats[3] = 17;
            baseStats[4] = 23;
        }

        if(gameClassId == GameClassId.rogue){
            baseStats[0] = 24;
            baseStats[1] = 26;
            baseStats[2] = 17;
            baseStats[3] = 17;
            baseStats[4] = 23;
        }

        if(gameClassId == GameClassId.shaman){
            baseStats[0] = 24;
            baseStats[1] = 26;
            baseStats[2] = 17;
            baseStats[3] = 17;
            baseStats[4] = 23;
        }

        if(gameClassId == GameClassId.warlock){
            baseStats[0] = 24;
            baseStats[1] = 26;
            baseStats[2] = 17;
            baseStats[3] = 17;
            baseStats[4] = 23;
        }
         */

        // Orc //
        if(raceId == RaceId.orc && gameClassId == GameClassId.Warrior){
            System.out.println("Creating base stats for orc warrior");
            baseStats[0] = 24;
            baseStats[1] = 26;
            baseStats[2] = 17;
            baseStats[3] = 17;
            baseStats[4] = 23;
        }

        // Human //
        if(raceId == RaceId.human && gameClassId == GameClassId.Mage){
            System.out.println("Creating base stats for human mage");
            baseStats[0] = 20;
            baseStats[1] = 20;
            baseStats[2] = 20;
            baseStats[3] = 23;
            baseStats[4] = 23;
        }

        if(raceId == RaceId.human && gameClassId == GameClassId.Paladin){
            System.out.println("Creating base stats for human paladin");
            baseStats[0] = 22;
            baseStats[1] = 22;
            baseStats[2] = 20;
            baseStats[3] = 20;
            baseStats[4] = 22;
        }

        if(raceId == RaceId.human && gameClassId == GameClassId.Priest){
            System.out.println("Creating base stats for human priest");
            baseStats[0] = 20;
            baseStats[1] = 20;
            baseStats[2] = 20;
            baseStats[3] = 22;
            baseStats[4] = 24;
        }

        if(raceId == RaceId.human && gameClassId == GameClassId.Rogue){
            System.out.println("Creating base stats for human rogue");
            baseStats[0] = 21;
            baseStats[1] = 21;
            baseStats[2] = 23;
            baseStats[3] = 20;
            baseStats[4] = 21;
        }

        if(raceId == RaceId.human && gameClassId == GameClassId.Warlock){
            System.out.println("Creating base stats for human warlock");
            baseStats[0] = 21;
            baseStats[1] = 20;
            baseStats[2] = 20;
            baseStats[3] = 22;
            baseStats[4] = 23;
        }

        if(raceId == RaceId.human && gameClassId == GameClassId.Warrior){
            System.out.println("Creating base stats for human warrior");
            baseStats[0] = 22;
            baseStats[1] = 23;
            baseStats[2] = 20;
            baseStats[3] = 20;
            baseStats[4] = 21;
        }

        return baseStats;
    }
}

