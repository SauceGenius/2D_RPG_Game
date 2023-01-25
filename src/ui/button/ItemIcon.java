package ui.button;

import core.CollisionBox;
import item.Item;

import java.awt.*;

public class ItemIcon extends CButton {

    public static final int iconWidth = 36;
    private static final int iconHeight = 36;

    private boolean mouseOver = false;

    private Item item;


    public ItemIcon(Item item){
        super();
        dimension.setSize(iconWidth, iconHeight);
        this.item = item;
    }

    public CollisionBox getCollisionBox() {
        return new CollisionBox(new Rectangle(position.intX(), position.intY(), iconWidth, iconHeight));
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }
}
