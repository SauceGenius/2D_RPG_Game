package ui.inventory;

import core.CollisionBox;
import core.Position;
import item.Item;
import ui.button.ItemIcon;

import java.awt.*;

public class InventorySlot {

    public static final int slotWidth = 36;
    private static final int slotHeight = 36;
    private boolean mouseOver = false;
    int marginX = 8;
    int marginY = 7;
    private Position position;

    private ItemIcon itemIcon;
    private Item item;

    public InventorySlot(int index, Position inventoryPosition){
        int row = index/4;
        int column = index%4;
        position = new Position(inventoryPosition.intX() + 18 + row * (slotWidth + marginX), inventoryPosition.intY() + 54 + column * (slotHeight + marginY));
    }

    public void update(){

    }

    public void render(Graphics graphics){
        if(mouseOver){
            graphics.setColor(Color.BLUE);
            graphics.drawRect(position.intX() - 1,position.intY() - 1, slotWidth + 2, slotHeight + 2);
        }
        graphics.setColor(new Color(245,245,245,150));
        graphics.fillRect(position.intX(),position.intY(), slotWidth, slotHeight);
    }

    public CollisionBox getCollisionBox() {
        return new CollisionBox(new Rectangle(position.intX(), position.intY(), slotWidth, slotHeight));
    }

    public void setItemIcon(ItemIcon itemIcon){
        this.itemIcon = itemIcon;
    }

    public void setMouseOver(Boolean bool){
        mouseOver = bool;
    }

    public ItemIcon getItemIcon() {
        return itemIcon;
    }
}
