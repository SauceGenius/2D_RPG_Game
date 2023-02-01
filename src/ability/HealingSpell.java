package ability;

public class HealingSpell extends Spell{

    private int manaCost;
    private double castTime;
    private int range;

    public HealingSpell(){

    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public double getCastTime() {
        return castTime;
    }

    public void setCastTime(double castTime) {
        this.castTime = castTime;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
