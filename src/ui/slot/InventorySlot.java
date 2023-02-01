package ui.slot;

import input.Input;
import inventory.Inventory;
import core.CollisionBox;
import core.Position;
import item.Item;

import java.awt.*;

public class InventorySlot extends Slot {

    private int marginX = 8;
    private int marginY = 7;

    private Item item;

    public InventorySlot(int index, Position uiPosition) {
        super(index, uiPosition);
        int row = index / 4;
        int column = index % 4;
        position = new Position(uiPosition.intX() + 18 + column * (SLOT_WIDTH + marginX), uiPosition.intY() + 54 + row * (SLOT_HEIGHT + marginY));
    }

    public void update(Input input, Inventory inventory){
        if(getCollisionBox().collidesWith(new CollisionBox(new Rectangle(input.getMousePositionRelativeToScreen().intX() - 2, input.getMousePositionRelativeToScreen().intY() - 2, 4, 4)))){
            mouseOver = true;
        } else mouseOver = false;

        this.item = inventory.getItems()[index];
    }

    public void render(Graphics graphics) {

        /** Mouse Over if Item is not null **/
        if(item != null){
            item.render(graphics, position);
            if (mouseOver && !draggedOver){

                /** lightGray Mask **/
                graphics.setColor(COLOR_MOUSE_OVER);
                graphics.fillRect(position.intX(), position.intY(), SLOT_WIDTH, SLOT_HEIGHT);
            }
        }

        /** Super Render **/
        super.render(graphics);
    }

    public CollisionBox getCollisionBox() {
        return new CollisionBox(new Rectangle(position.intX(), position.intY(), SLOT_WIDTH, SLOT_HEIGHT));
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

