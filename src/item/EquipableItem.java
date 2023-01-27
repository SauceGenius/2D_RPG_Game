package item;

import game.state.State;
import gfx.SpriteLibrary;

import java.awt.*;

public abstract class EquipableItem extends Item {

    public static final int BIND_ON_EQUIP = 1;
    public static final int BIND_ON_PICKUP = 2;

    protected int itemLevel;

    protected int binding;
    protected int levelRequired;
    protected int maxDurability;
    protected int durability;

    public EquipableItem(ItemId itemId, Image image) {
        super(itemId, image);
    }

    public int getItemLevel() {
        return itemLevel;
    }

    public int getBinding() {
        return binding;
    }

    public int getLevelRequired() {
        return levelRequired;
    }

    public int getMaxDurability() {
        return maxDurability;
    }

    public int getDurability() {
        return durability;
    }

    public void setItemLevel(int itemLevel) {
        this.itemLevel = itemLevel;
    }

    public void setBinding(int binding) {
        this.binding = binding;
    }

    public void setLevelRequired(int levelRequired) {
        this.levelRequired = levelRequired;
    }

    public void setMaxDurability(int maxDurability) {
        this.maxDurability = maxDurability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
}
