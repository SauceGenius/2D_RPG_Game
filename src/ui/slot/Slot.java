package ui.slot;

import core.CollisionBox;
import core.Position;

import java.awt.*;

public abstract class Slot {

    public static final int SLOT_WIDTH = 36;
    public static final int SLOT_HEIGHT = 36;
    public static final int ARC = 8;
    public static final Color COLOR_MOUSE_OVER = new Color(200, 200, 200, 40);

    protected boolean mouseOver = false;
    protected boolean draggedOver = false;
    protected boolean dragged = false;
    protected int index;
    protected Position position;

    public Slot(int index, Position uiPosition) {
        this.index = index;
    }

    public void render(Graphics graphics){

        /** LightGray Mask when dragged over **/
        if(draggedOver){
            graphics.setColor(Color.gray);
            graphics.drawRoundRect(position.intX() - 1, position.intY() - 1, SLOT_WIDTH + 2, SLOT_HEIGHT + 2, ARC, ARC);
            graphics.setColor(COLOR_MOUSE_OVER);
            graphics.fillRect(position.intX(), position.intY(), SLOT_WIDTH, SLOT_HEIGHT);
        }
    }

    public CollisionBox getCollisionBox() {
        return new CollisionBox(new Rectangle(position.intX(), position.intY(), SLOT_WIDTH, SLOT_HEIGHT));
    }

    public boolean mouseIsOver(){
        return mouseOver;
    }

    public Position getPosition(){
        return position;
    }

    public void setMouseOver(Boolean bool) {
        mouseOver = bool;
    }

    public void setDraggedOver(Boolean bool){
        draggedOver = bool;
    }

    public void setDragged(boolean dragged) {
        this.dragged = dragged;
    }
}
