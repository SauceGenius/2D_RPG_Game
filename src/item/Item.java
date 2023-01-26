package item;

import core.Position;
import ui.Icon;
import ui.tooltip.Tooltip;

import java.awt.*;


public abstract class Item {

    protected ItemId itemId;
    protected String name;
    protected Icon itemIcon;
    protected ItemStat itemStat;
    protected Tooltip tooltip;
    protected boolean equipable;

    //protected Position iconPosition = new Position(0, 0);
    //protected Dimension iconDimension = new Dimension(36,36);

    public Item(ItemId itemId, Image image){
        this.itemId = itemId;
        this.itemIcon = new Icon(image);
    }

    public void render(Graphics graphics, Position itemIconPosition, boolean mouseOver){
        itemIcon.render(graphics, itemIconPosition);
        /*if(mouseOver){
            tooltip.render(graphics, itemIconPosition);
        }*/
    }

    //Setter & Getters
    public String getName() {return name;}
    public ItemId getId() {return itemId;}
    public ItemStat getItemStat() {return itemStat;}
    //public CollisionBox getCollisionBox(){return new CollisionBox(new Rectangle(iconPosition.intX(), iconPosition.intY(), iconDimension.width, iconDimension.height));}
    public Icon getItemIcon(){
        return itemIcon;
    }
    public Tooltip getTooltip(){
        return tooltip;
    }
}
