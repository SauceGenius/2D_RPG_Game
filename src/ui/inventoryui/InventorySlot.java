package ui.inventoryui;

import inventory.Inventory;
import core.CollisionBox;
import core.Position;
import item.Item;
import ui.button.ItemIcon;

import java.awt.*;

public class InventorySlot {

    public static final int slotWidth = 36;
    private static final int slotHeight = 36;
    private boolean mouseOver = false;
    private boolean draggedOver = false;
    private int index;
    private int marginX = 8;
    private int marginY = 7;
    private Position position;
    private ItemIcon itemIcon;
    private Item item;

    public InventorySlot(int index, Position inventoryPosition) {
        this.index = index;
        int row = index / 4;
        int column = index % 4;
        position = new Position(inventoryPosition.intX() + 18 + column * (slotWidth + marginX), inventoryPosition.intY() + 54 + row * (slotHeight + marginY));
    }

    public void update(Inventory inventory){
        this.item = inventory.getItems()[index];
    }

    public void render(Graphics graphics) {
        if(item != null){
            if (mouseOver){
                item.render(graphics, position, true);

                int arcW = 8;
                int arcH = 8;
                graphics.setColor(Color.black);
                graphics.drawRoundRect(position.intX() - 1, position.intY() - 1, slotWidth + 2, slotHeight + 2, arcW, arcH);
                graphics.setColor(new Color(150, 150, 150, 25));
                graphics.fillRect(position.intX(), position.intY(), slotWidth, slotHeight);

            } else {
                item.render(graphics, position, false);
            }
        }

        if(draggedOver){
            int arcW = 8;
            int arcH = 8;
            graphics.setColor(Color.gray);
            graphics.drawRoundRect(position.intX() - 1, position.intY() - 1, slotWidth + 2, slotHeight + 2, arcW, arcH);
            graphics.setColor(new Color(150, 150, 150, 25));
            graphics.fillRect(position.intX(), position.intY(), slotWidth, slotHeight);
        }
    }

    public CollisionBox getCollisionBox() {
        return new CollisionBox(new Rectangle(position.intX(), position.intY(), slotWidth, slotHeight));
    }

    public ItemIcon getItemIcon() {
        return itemIcon;
    }

    public Item getItem() {
        return item;
    }
    public boolean mouseIsOver(){
        return mouseOver;
    }

    public Position getPosition(){
        return position;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setMouseOver(Boolean bool) {
        mouseOver = bool;
    }

    public void setDraggedOver(Boolean bool){
        draggedOver = bool;
    }
}

