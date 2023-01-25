package item;

import gfx.SpriteLibrary;

public abstract class Weapon extends Item{

    protected double speed;
    protected double dps;
    protected int minDamage;
    protected int maxDamage;

    public Weapon(ItemId itemId, SpriteLibrary spriteLibrary) {
        super(itemId, spriteLibrary);

        this.equipable = true;
    }

    //Getters
    public double getSpeed() {return speed;}
    public double getDps() {return dps;}
    public int getMinDamage() {return minDamage;}
    public int getMaxDamage() {return maxDamage;}
}
