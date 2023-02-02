package stats;

import audio.AudioPlayer;
import core.Log;
import equipment.Equipment;
import gameobject.LivingObject;
import id.GameObjectID;
import id.GameClassId;
import id.RaceId;

public class Stats {

    public static final int STAMINA = 0;
    public static final int STRENGTH = 1;
    public static final int INTELLECT = 2;
    public static final int AGILITY = 3;
    public static final int SPIRIT = 4;

    private GameClassId gameClassId;
    private Level level;
    private Hp hp;

    //Primary stat variables
    private int baseStamina;
    private int baseStrength;
    private int baseIntellect;
    private int baseAgility;
    private int baseSpirit;

    private int totalStamina;
    private int totalStrength;
    private int totalIntellect;
    private int totalAgility;
    private int totalSpirit;

    //Secondary stat variables
    private double dps;
    private double attackSpeed;
    private double meleeAttackPower;
    private double rangedAttackPower;
    private double spellPower;
    private double mana;
    private double critChance;
    private double critDamageBonus;
    private double hitChance;
    private int armor;
    private double dodgeChance;
    private double blockChance;
    private double blockDamage;
    private double hp5;
    private double mp5;

    //private double elementResistance;

    private int defense;
    private int baseDefense;
    private int minMeleeWeaponDamage;
    private int maxMeleeWeaponDamage;

    // Constructor for new character
    public Stats(RaceId raceId, GameClassId gameClassId){
        this.gameClassId = gameClassId;
        level = new Level(1, raceId, gameClassId);

        BaseStatsGenerator baseStatsGenerator = new BaseStatsGenerator(raceId, gameClassId);
        int[] baseStats = baseStatsGenerator.getBaseStats();

        baseStamina = baseStats[0];
        baseStrength = baseStats[1];
        baseIntellect = baseStats[2];
        baseAgility = baseStats[3];
        baseSpirit = baseStats[4];

        this.hp = new Hp(level.getLevelValue(), baseStamina);
    }

    //Constructor for NPCs
    public Stats(LivingObject livingObject, int level){
        this.level = new Level(level);
        totalStamina = 4 + 1 * (level - 1);
        this.hp = new Hp(livingObject, totalStamina);
        minMeleeWeaponDamage = 0;
        baseStrength = 0;
        baseIntellect = 0;
        baseAgility = 0;
        baseDefense = 0;
        armor = 0;
    }

    //Update methods
    public void update(Equipment equipment, AudioPlayer audioPlayer, Log log){
        level.update(this, equipment, audioPlayer, log);

        totalStamina = baseStamina + equipment.getTotalStaminaBonus();

        updatePrimaryStats(equipment);
        updateSecondaryStats(equipment);
    }

    private void updatePrimaryStats(Equipment equipment) {
        totalStrength = baseStrength + equipment.getTotalStrengthBonus();
        totalIntellect = baseIntellect + equipment.getTotalIntellectBonus();
        totalAgility = baseAgility + equipment.getTotalAgilityBonus();
        totalSpirit = baseSpirit + equipment.getTotalAgilityBonus();
    }

    private void updateSecondaryStats(Equipment equipment){
        updateMeleeAttackPower();
        updateRangedAttackPower();

        defense = equipment.getTotalDefenseBonus();
        armor = equipment.getTotalArmorBonus();
        minMeleeWeaponDamage = equipment.getTotalMinMeleeWeaponDamage();
        maxMeleeWeaponDamage = equipment.getTotalMaxMeleeWeaponDamage();

        if( equipment.getItem(0) != null){
            attackSpeed = equipment.getItem(0).getItemStat().getAttackSpeed();
        }
        critChance = 0.2;
        hitChance = 0.96;
    }

    private void updateMeleeAttackPower(){
        if(gameClassId == GameClassId.Druid){meleeAttackPower = (level.getLevelValue() * 3) + (totalStrength * 2 - 20);}
        if(gameClassId == GameClassId.Hunter){meleeAttackPower = (level.getLevelValue() * 2) + (totalStrength - 10) + (totalAgility - 10);}
        if(gameClassId == GameClassId.Mage){meleeAttackPower = totalStrength * 2 - 20;}
        if(gameClassId == GameClassId.Paladin){meleeAttackPower = (level.getLevelValue() * 3) + (totalStrength * 2 - 20);}
        if(gameClassId == GameClassId.Priest){meleeAttackPower = totalStrength * 2 - 20;}
        if(gameClassId == GameClassId.Rogue){meleeAttackPower = (level.getLevelValue() * 2) + (totalStrength - 10) + (totalAgility * 2 - 20);}
        if(gameClassId == GameClassId.Shaman){meleeAttackPower = (level.getLevelValue() * 2) + (totalStrength - 10) + (totalAgility * 2 - 20);}
        if(gameClassId == GameClassId.Warlock){meleeAttackPower = totalStrength * 2 - 20;}
        if(gameClassId == GameClassId.Warrior){meleeAttackPower = (level.getLevelValue() * 3) + (totalStrength * 2 - 20);}
    }

    //Gain stat method
    public void gain(String statName, int statGained, Log log) {
        if(statName == "stamina"){
            baseStamina += statGained;
            log.addToGeneral("LevelUp", "Your Stamina increases by " + statGained + ".");
        }
        if(statName == "strength"){
            baseStrength += statGained;
            log.addToGeneral("LevelUp", "Your Strength increases by " + statGained + ".");
        }
        if(statName == "intellect"){
            baseIntellect += statGained;
            log.addToGeneral("LevelUp", "Your Intellect increases by " + statGained + ".");
        }
        if(statName == "agility"){
            baseAgility += statGained;
            log.addToGeneral("LevelUp", "Your Agility increases by " + statGained + ".");
        }
        if(statName == "spirit"){
            baseSpirit += statGained;
            log.addToGeneral("LevelUp", "Your Spirit increases by " + statGained + ".");
        }
    }

    private void updateRangedAttackPower(){
        if(gameClassId == GameClassId.Hunter){rangedAttackPower = (level.getLevelValue() * 2) + (totalAgility * 2 - 20);}
        if(gameClassId == GameClassId.Rogue){rangedAttackPower = level.getLevelValue() + (totalAgility - 10);}
        if(gameClassId == GameClassId.Warrior){rangedAttackPower = level.getLevelValue() + (totalAgility - 10);}
    }

    /** Setters **/
    public void setMaxHpValue(double newHp){hp.setMaxHp(newHp);}
    public void setCurrentHpValue(double newHp){hp.setCurrentHp(newHp);}


    /** Getters **/
    public int getBaseStat(int statIndex) {
        switch (statIndex){
            case STAMINA -> {return baseStamina;}
            case STRENGTH -> {return baseStrength;}
            case INTELLECT -> {return baseIntellect;}
            case AGILITY -> {return baseAgility;}
            case SPIRIT -> {return baseSpirit;}
        }
        return 0;
    }

    public int getStat(int statIndex) {
        switch (statIndex){
            case STAMINA -> {return totalStamina;}
            case STRENGTH -> {return totalStrength;}
            case INTELLECT -> {return totalIntellect;}
            case AGILITY -> {return totalAgility;}
            case SPIRIT -> {return totalSpirit;}
        }
        return 0;
    }

    public Level getLevel() {
        return level;
    }

    public int getLevelValue(){
        return level.getLevelValue();
    }

    public Hp getHp() {
        return hp;
    }

    public double getMaxHpValue(){
        return getHp().getMaxHp();
    }

    public double getCurrentHpValue(){
        return getHp().getCurrentHp();
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public double getMinMeleeWeaponDamage() {
        return minMeleeWeaponDamage;
    }

    public double getMaxMeleeWeaponDamage() {
        return maxMeleeWeaponDamage;
    }

    public int getDefense() {
        return defense;
    }

    public int getArmor() {
        return armor;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public double getMeleeAttackPower() {
        return meleeAttackPower;
    }

    public double getCritChance() {
        return critChance;
    }

    public double getHitChance() {
        return  hitChance;
    }
}
