package equipment;

import item.*;

public class Equipment {

    public static final int MAIN_HAND = 0;
    public static final int OFF_HAND = 1;
    public static final int RANGED = 2;
    public static final int AMMUNITION = 3;
    public static final int HEAD = 4;
    public static final int NECK = 5;
    public static final int SHOULDER = 6;
    public static final int BACK = 7;
    public static final int CHEST = 8;
    public static final int SHIRT = 9;
    public static final int TABARD = 10;
    public static final int WRIST = 11;
    public static final int HANDS = 12;
    public static final int WAIST = 13;
    public static final int LEGS = 14;
    public static final int FOOT = 15;
    public static final int FINGER_1 = 16;
    public static final int FINGER_2 = 17;
    public static final int OTHER_1 = 18;
    public static final int OTHER_2 = 19;

    private int size = 20;
    private Item equippedItems[];


    public Equipment(){
        this.equippedItems = new Item[size];
    }

    public void equip(EquipableItem item){
        if(item instanceof Weapon){
            equippedItems[0] = item;
        }
    }

    public void unequip(int index) {
        equippedItems[index] = null;
    }

    public Item getItem(int i) {
        return equippedItems[i];
    }

    public int getTotalStrengthBonus(){
        int strength = 0;
        for(int i = 0; i < size; i++){
            if(equippedItems[i] != null){
                strength += equippedItems[i].getItemStat().getStrength();
            }
        }
        return strength;
    }

    public int getTotalIntellectBonus(){
        int intellect = 0;
        for(int i = 0; i < size; i++){
            if(equippedItems[i] != null){
                intellect += equippedItems[i].getItemStat().getIntellect();
            }
        }
        return intellect;
    }

    public int getTotalAgilityBonus(){
        int agility = 0;
        for(int i = 0; i < size; i++){
            if(equippedItems[i] != null){
                agility += equippedItems[i].getItemStat().getAgility();
            }
        }
        return agility;
    }

    public int getTotalDefenseBonus(){
        int Defense = 0;
        for(int i = 0; i < size; i++){
            if(equippedItems[i] != null){
                Defense += equippedItems[i].getItemStat().getDefense();
            }
        }
        return Defense;
    }

    public int getTotalArmorBonus(){
        int Armor = 0;
        for(int i = 0; i < size; i++){
            if(equippedItems[i] != null){
                Armor += equippedItems[i].getItemStat().getArmor();
            }
        }
        return Armor;
    }

    public int getTotalMinMeleeWeaponDamage(){
        int minMeleeWeaponDamage = 0;
        for(int i = 0; i < size; i++){
            if(equippedItems[i] != null && equippedItems[i].getId() == ItemId.wornShortSword){
                minMeleeWeaponDamage += equippedItems[i].getItemStat().getMinMeleeWeaponDamage();
            }
        }
        return minMeleeWeaponDamage;
    }

    public int getTotalMaxMeleeWeaponDamage(){
        int maxMeleeWeaponDamage = 0;
        for(int i = 0; i < size; i++){
            if(equippedItems[i] != null && equippedItems[i].getId() == ItemId.wornShortSword){
                maxMeleeWeaponDamage += equippedItems[i].getItemStat().getMaxMeleeWeaponDamage();
            }
        }
        return maxMeleeWeaponDamage;
    }

    public double getMeleeAttackSpeed(){
        int meleeAttackSpeed = 0;
        if(equippedItems[0] != null) {
            meleeAttackSpeed += equippedItems[0].getItemStat().getAttackSpeed();
        }

        return meleeAttackSpeed;
    }

    public Item[] getEquippedItems() {
        return equippedItems;
    }

    public int getTotalStaminaBonus() {
        int stamina = 0;
        for(int i = 0; i < size; i++){
            if(equippedItems[i] != null){
                stamina += equippedItems[i].getItemStat().getStamina();
            }
        }
        return stamina;
    }
}
