package ui;

import core.Position;

import java.awt.*;

public class Icon {

    public static final int ICON_WIDTH = 36;
    public static final int ICON_HEIGHT = 36;

    private Image iconImage;
    private Border iconBorder;
    private boolean dragged = false;

    public Icon(Image iconImage){
        this.iconImage = iconImage;
        this.iconBorder = new Border();
    }

    public void render(Graphics graphics, Position iconPosition){
        graphics.drawImage(iconImage, iconPosition.intX() + 2, iconPosition.intY() + 2, null);
        iconBorder.render(graphics, iconPosition, new Dimension(ICON_WIDTH, ICON_HEIGHT));
        iconBorder.render(graphics, iconPosition, new Dimension(ICON_WIDTH, ICON_HEIGHT));
    }

    public boolean isDragged(){
        return dragged;
    }

    public void setDragged(boolean bool){
        this.dragged = bool;
    }
}
