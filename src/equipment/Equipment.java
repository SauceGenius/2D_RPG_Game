package equipment;

import controller.PlayerController;
import item.Item;
import item.ItemId;
import item.OneHandWeapon;
import gfx.SpriteLibrary;

public class Equipment {
    private boolean isOpen;
    private int size = 20;
    private PlayerController playerController;
    private Item equipment[];


    public Equipment(PlayerController playerController, SpriteLibrary spriteLibrary){
        this.isOpen = false;
        this.playerController = playerController;
        this.equipment = new Item[size];
        equipment[0] = (Item) new OneHandWeapon(ItemId.wornShortSword, spriteLibrary.getIcon("inv_sword_34"));
    }

    public void update() {

    }

    public Item getItem(int i) {
        return equipment[i];
    }

    public void equips(Item item){
        if(item.getId() == ItemId.wornShortSword){
            equipment[0] = item;
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public int getTotalStrengthBonus(){
        int strength = 0;
        for(int i = 0; i < size; i++){
            if(equipment[i] != null){
                strength += equipment[i].getItemStat().getStrength();
            }
        }
        return strength;
    }

    public int getTotalIntellectBonus(){
        int intellect = 0;
        for(int i = 0; i < size; i++){
            if(equipment[i] != null){
                intellect += equipment[i].getItemStat().getStrength();
            }
        }
        return intellect;
    }

    public int getTotalAgilityBonus(){
        int agility = 0;
        for(int i = 0; i < size; i++){
            if(equipment[i] != null){
                agility += equipment[i].getItemStat().getStrength();
            }
        }
        return agility;
    }

    public int getTotalDefenseBonus(){
        int Defense = 0;
        for(int i = 0; i < size; i++){
            if(equipment[i] != null){
                Defense += equipment[i].getItemStat().getStrength();
            }
        }
        return Defense;
    }

    public int getTotalArmorBonus(){
        int Armor = 0;
        for(int i = 0; i < size; i++){
            if(equipment[i] != null){
                Armor += equipment[i].getItemStat().getStrength();
            }
        }
        return Armor;
    }

    public int getTotalMinMeleeWeaponDamage(){
        int minMeleeWeaponDamage = 0;
        for(int i = 0; i < size; i++){
            if(equipment[i] != null && equipment[i].getId() == ItemId.wornShortSword){
                minMeleeWeaponDamage += equipment[i].getItemStat().getMinMeleeWeaponDamage();
            }
        }
        return minMeleeWeaponDamage;
    }

    public int getTotalMaxMeleeWeaponDamage(){
        int maxMeleeWeaponDamage = 0;
        for(int i = 0; i < size; i++){
            if(equipment[i] != null && equipment[i].getId() == ItemId.wornShortSword){
                maxMeleeWeaponDamage += equipment[i].getItemStat().getMaxMeleeWeaponDamage();
            }
        }
        return maxMeleeWeaponDamage;
    }

    public double getMeleeAttackSpeed(){
        int meleeAttackSpeed = 0;
        if(equipment[0] != null) {
            meleeAttackSpeed += equipment[0].getItemStat().getAttackSpeed();
        }

        return meleeAttackSpeed;
    }

    public Item[] getEquipment() {return equipment;}

    public int getTotalStaminaBonus() {
        return 0;
    }
}
