package item;

import core.CollisionBox;
import core.Position;
import gfx.SpriteLibrary;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Item {

    protected ItemId itemId;
    protected String name;
    protected BufferedImage icon;
    protected Position iconPosition = new Position(0, 0);
    protected Dimension iconDimension = new Dimension(36,36);
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
    public CollisionBox getCollisionBox(){return new CollisionBox(new Rectangle(iconPosition.intX(), iconPosition.intY(), iconDimension.width, iconDimension.height));}
}
