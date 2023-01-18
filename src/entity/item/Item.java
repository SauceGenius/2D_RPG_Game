package entity.item;

import gfx.SpriteLibrary;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Item {

    protected ItemId itemId;
    protected String name;
    protected BufferedImage icon;
    protected ItemStat itemStat;
    protected boolean equipable;

    public Item(ItemId itemId, SpriteLibrary spriteLibrary){
        this.itemId = itemId;
        icon = (BufferedImage) spriteLibrary.getIcon("Icon1");
    }

    //Setter & Getters
    public String getName() {return name;}
    public Image getIconSprite() {return icon;}
    public ItemId getId() {return itemId;}
    public ItemStat getItemStat() {return itemStat;}
}
