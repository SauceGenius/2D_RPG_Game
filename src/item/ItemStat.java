package item;

public class ItemStat {

    //Variables
    private double minMeleeWeaponDamage;
    private double maxMeleeWeaponDamage;
    private int stamina;
    private int strength;
    private int intellect;
    private int agility;
    private int defense;
    private int armor;
    private double attackSpeed;

    //Constructor
    public ItemStat(ItemId id, int stamina, int strength, int intellect, int agility, int defense, int armor){
        this.stamina = stamina;
        this.strength = strength;
        this.intellect = intellect;
        this.agility = agility;
        this.defense = defense;
        this.armor = armor;
    }

    public ItemStat(ItemId id, int minMeleeWeaponDamage, int maxMeleeWeaponDamage, double attackSpeed, int stamina, int strength, int intellect, int agility, int defense, int armor) {
        this.minMeleeWeaponDamage = minMeleeWeaponDamage;
        this.maxMeleeWeaponDamage = maxMeleeWeaponDamage;
        this.attackSpeed = attackSpeed;
        this.stamina = stamina;
        this.strength = strength;
        this.intellect = intellect;
        this.agility = agility;
        this.defense = defense;
        this.armor = armor;
    }

    //Setters
    public void setMinMeleeWeaponDamage(int minMeleeWeaponDamage) {this.minMeleeWeaponDamage = minMeleeWeaponDamage;}
    public void setMaxMeleeWeaponDamage(int maxMeleeWeaponDamage){
        this.maxMeleeWeaponDamage = maxMeleeWeaponDamage;
    }
    public void setStamina(int stamina) {this.stamina = stamina;}
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public void setIntellect(int intellect) {
        this.intellect = intellect;
    }
    public void setAgility(int agility) {
        this.agility = agility;
    }
    public void setDefense(int defense) {
        this.defense = defense;
    }
    public void setArmor(int armor) {
        this.armor = armor;
    }

    //Getters
    public double getMinMeleeWeaponDamage() {return minMeleeWeaponDamage;}
    public double getMaxMeleeWeaponDamage() {return maxMeleeWeaponDamage;}
    public int getStamina() {return stamina;}
    public double getAttackSpeed() {return attackSpeed;}
    public int getStrength() {
        return strength;
    }
    public int getIntellect() {
        return intellect;
    }
    public int getAgility() {
        return agility;
    }
    public int getDefense() {
        return defense;
    }
    public int getArmor() {
        return armor;
    }
}
