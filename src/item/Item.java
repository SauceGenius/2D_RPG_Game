package item;

import core.Position;
import ui.Icon;

import java.awt.*;


public abstract class Item {

    protected ItemId itemId;
    protected String name;
    protected int quality;
    protected Icon itemIcon;
    protected ItemStat itemStat;

    public Item(ItemId itemId, Image image){
        this.itemId = itemId;
        this.itemIcon = new Icon(image);
    }

    public void render(Graphics graphics, Position itemIconPosition){
        itemIcon.render(graphics, itemIconPosition);
    }

    //Setter & Getters
    public void setName(String name){
        this.name = name;
    }
    public void setQuality(int qualityIndex){
        this.quality = qualityIndex;
    }

    public ItemId getId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public int getQuality(){
        return quality;
    }

    public ItemStat getItemStat() {
        return itemStat;
    }

    public Icon getItemIcon(){
        return itemIcon;
    }
}
