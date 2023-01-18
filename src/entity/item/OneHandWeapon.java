package entity.item;

import gfx.SpriteLibrary;

public class OneHandWeapon extends Weapon {

    public OneHandWeapon(ItemId itemId, SpriteLibrary spriteLibrary) {
        super(itemId, spriteLibrary);

        if(itemId == ItemId.wornShortSword){
            createWornShortSword();
        }
    }

    private void createWornShortSword(){
        this.name = "Worn Shortsword";
        this.itemStat = new ItemStat(itemId, 1,3,1.9,0,0,0,0,0,0);
        this.speed = 1.9;
        this.minDamage = 1;
        this.maxDamage = 3;
        this.dps = 1.1;
    }
}
