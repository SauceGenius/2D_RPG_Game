package entity.stats;

import entity.GameObjectID;

public class Hp {

    private double maxHp;
    private double currentHp;

    public Hp(GameObjectID gameObjectID, int stamina){
        if(gameObjectID == GameObjectID.player){
            this.maxHp = stamina * 10;
            this.currentHp = this.maxHp;
        }
        if(gameObjectID == GameObjectID.NPC){
            this.maxHp = stamina * 10;
            this.currentHp = this.maxHp;
        }
    }

    public Hp(int level, int stamina){
        System.out.println("Creating HP");
        if(stamina < 20) {
            this.maxHp = stamina + 20;
        } else {
            this.maxHp = (level - 1 ) * 9 + (stamina - 20) * 10 + 40;
        }
        this.currentHp = this.maxHp;
    }

    public void update(int level, int stamina){
        if(currentHp <= 0) {
            currentHp = 0;
        }

        if(stamina < 20) {
            this.maxHp = stamina + 20;
        } else {
            this.maxHp = (level - 1 ) * 9 + (stamina - 20) * 10 + 40;
        }
    }

    //Setters
    public void setMaxHp(double maxHp) {this.maxHp = maxHp;}
    public void setCurrentHp(double currentHp) {this.currentHp = currentHp;}

    //Getters
    public double getMaxHp() {return maxHp;}
    public double getCurrentHp() {return currentHp;}
}
