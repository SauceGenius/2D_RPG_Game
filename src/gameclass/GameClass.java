package gameclass;

import id.GameClassId;

public class GameClass {

    private GameClassId gameClassId;

    // Constructor
    public GameClass(GameClassId gameClassId){
        this.gameClassId = gameClassId;
    }

    // Get base stats for the class method
    public int[] getClassBaseStats(int level){
        int classBaseStats[] = new int[5];
        // [0] : Stamina
        // [1] : Strength
        // [2] : Intellect
        // [3] : Agility
        // [4] : Spirit

        /*
        if(gameClassId == GameClassId.druid){
            classBaseStats[0] = 24;
            classBaseStats[1] = 26;
            classBaseStats[2] = 17;
            classBaseStats[3] = 17;
            classBaseStats[4] = 23;
        }

        if(gameClassId == GameClassId.hunter){
            classBaseStats[0] = 24;
            classBaseStats[1] = 26;
            classBaseStats[2] = 17;
            classBaseStats[3] = 17;
            classBaseStats[4] = 23;
        }

        if(gameClassId == GameClassId.mage){
            classBaseStats[0] = 24;
            classBaseStats[1] = 26;
            classBaseStats[2] = 17;
            classBaseStats[3] = 17;
            classBaseStats[4] = 23;
        }

        if(gameClassId == GameClassId.paladin){
            classBaseStats[0] = 24;
            classBaseStats[1] = 26;
            classBaseStats[2] = 17;
            classBaseStats[3] = 17;
            classBaseStats[4] = 23;
        }

        if(gameClassId == GameClassId.priest){
            classBaseStats[0] = 24;
            classBaseStats[1] = 26;
            classBaseStats[2] = 17;
            classBaseStats[3] = 17;
            classBaseStats[4] = 23;
        }

        if(gameClassId == GameClassId.rogue){
            classBaseStats[0] = 24;
            classBaseStats[1] = 26;
            classBaseStats[2] = 17;
            classBaseStats[3] = 17;
            classBaseStats[4] = 23;
        }

        if(gameClassId == GameClassId.shaman){
            classBaseStats[0] = 24;
            classBaseStats[1] = 26;
            classBaseStats[2] = 17;
            classBaseStats[3] = 17;
            classBaseStats[4] = 23;
        }

        if(gameClassId == GameClassId.warlock){
            classBaseStats[0] = 24;
            classBaseStats[1] = 26;
            classBaseStats[2] = 17;
            classBaseStats[3] = 17;
            classBaseStats[4] = 23;
        }
         */

        if(gameClassId == GameClassId.Warrior){
            classBaseStats[0] = 24;
            classBaseStats[1] = 26;
            classBaseStats[2] = 17;
            classBaseStats[3] = 17;
            classBaseStats[4] = 23;
        }

        return classBaseStats;
    }
}
