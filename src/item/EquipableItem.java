package item;

import gfx.SpriteLibrary;

import java.awt.*;

public abstract class EquipableItem extends Item {

    protected int itemLevel;
    protected int levelRequired;
    protected int maxDurability;
    protected int durability;

    public EquipableItem(ItemId itemId, Image image) {
        super(itemId, image);
    }

    public int getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(int itemLevel) {
        this.itemLevel = itemLevel;
    }

    public int getLevelRequired() {
        return levelRequired;
    }

    public void setLevelRequired(int levelRequired) {
        this.levelRequired = levelRequired;
    }

    public int getMaxDurability() {
        return maxDurability;
    }

    public void setMaxDurability(int maxDurability) {
        this.maxDurability = maxDurability;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
}
