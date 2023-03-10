package item;

import gfx.SpriteLibrary;

import java.awt.*;

public abstract class Weapon extends EquipableItem{

    protected double speed;
    protected double dps;
    protected int minDamage;
    protected int maxDamage;

    public Weapon(ItemId itemId, Image image) {
        super(itemId, image);
    }

    public void setDPS(double dps){
        this.dps = dps;
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }

    //Getters
    public double getSpeed() {return speed;}
    public double getDps() {return dps;}
    public int getMinDamage() {return minDamage;}
    public int getMaxDamage() {return maxDamage;}
}
