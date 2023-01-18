package entity.stats;

import audio.AudioPlayer;
import core.Log;
import entity.Equipment;
import entity.GameObjectID;
import entity.character.GameClassId;
import entity.character.RaceId;

public class Stats {

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
    public Stats(GameObjectID gameObjectID, int level){
        this.level = new Level(level);
        totalStamina = 4;
        this.hp = new Hp(gameObjectID, totalStamina);
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
        hp.update(level.getLevelValue(), totalStamina);

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
        attackSpeed = equipment.getItem(0).getItemStat().getAttackSpeed();
        critChance = 0.2;
        hitChance = 0.96;
    }

    private void updateMeleeAttackPower(){
        if(gameClassId == GameClassId.druid){meleeAttackPower = (level.getLevelValue() * 3) + (totalStrength * 2 - 20);}
        if(gameClassId == GameClassId.hunter){meleeAttackPower = (level.getLevelValue() * 2) + (totalStrength - 10) + (totalAgility - 10);}
        if(gameClassId == GameClassId.mage){meleeAttackPower = totalStrength * 2 - 20;}
        if(gameClassId == GameClassId.paladin){meleeAttackPower = (level.getLevelValue() * 3) + (totalStrength * 2 - 20);}
        if(gameClassId == GameClassId.priest){meleeAttackPower = totalStrength * 2 - 20;}
        if(gameClassId == GameClassId.rogue){meleeAttackPower = (level.getLevelValue() * 2) + (totalStrength - 10) + (totalAgility * 2 - 20);}
        if(gameClassId == GameClassId.shaman){meleeAttackPower = (level.getLevelValue() * 2) + (totalStrength - 10) + (totalAgility * 2 - 20);}
        if(gameClassId == GameClassId.warlock){meleeAttackPower = totalStrength * 2 - 20;}
        if(gameClassId == GameClassId.warrior){meleeAttackPower = (level.getLevelValue() * 3) + (totalStrength * 2 - 20);}
    }

    private void updateRangedAttackPower(){
        if(gameClassId == GameClassId.hunter){rangedAttackPower = (level.getLevelValue() * 2) + (totalAgility * 2 - 20);}
        if(gameClassId == GameClassId.rogue){rangedAttackPower = level.getLevelValue() + (totalAgility - 10);}
        if(gameClassId == GameClassId.warrior){rangedAttackPower = level.getLevelValue() + (totalAgility - 10);}
    }

    //Setters
    public void setMaxHpValue(double newHp){hp.setMaxHp(newHp);}
    public void setCurrentHpValue(double newHp){hp.setCurrentHp(newHp);}
    public void setBaseStamina(int baseStamina) {this.baseStamina = baseStamina;}
    public void setBaseStrength(int baseStrength) {this.baseStrength = baseStrength;}
    public void setBaseIntellect(int baseIntellect) {this.baseIntellect = baseIntellect;}
    public void setBaseAgility(int baseAgility) {this.baseAgility = baseAgility;}
    public void setBaseDefense(int baseDefense) {this.baseDefense = baseDefense;}

    //Getters
    public Level getLevel() {return level;}
    public int getLevelValue(){return level.getLevelValue();}
    public Hp getHp() {return hp;}
    public double getMaxHpValue(){return getHp().getMaxHp();}
    public double getCurrentHpValue(){return getHp().getCurrentHp();}
    public int getBaseStat(String statName) {
        if(statName == "stamina"){return baseStamina;}
        if(statName == "strength"){return baseStrength;}
        if(statName == "intellect"){return baseIntellect;}
        if(statName == "agility"){return baseAgility;}
        if(statName == "spirit"){return baseSpirit;}
        else return 0;
    }

    public int getStat(String statName) {
        if(statName == "stamina"){return totalStamina;}
        if(statName == "strength"){return totalStrength;}
        if(statName == "intellect"){return totalIntellect;}
        if(statName == "agility"){return totalAgility;}
        if(statName == "spirit"){return totalSpirit;}
        else return 0;
    }

    public int getBaseDefense() {return baseDefense;}
    public double getMinMeleeWeaponDamage() {return minMeleeWeaponDamage;}
    public double getMaxMeleeWeaponDamage() {return maxMeleeWeaponDamage;}
    public int getTotalStamina() {return totalStamina;}
    public int getTotalStrength() {return totalStrength;}
    public int getTotalIntellect() {return totalIntellect;}
    public int getTotalAgility() {return totalAgility;}
    public int getDefense() {return defense;}
    public int getArmor() {return armor;}
    public double getAttackSpeed() {return attackSpeed;}
    public double getMeleeAttackPower() {return meleeAttackPower;}
    public double getCritChance() {return critChance;}
    public double getHitChance() {return  hitChance;}

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
}
