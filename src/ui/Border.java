package ui;

import core.Position;

import java.awt.*;

public class Border {

    public static final int DEFAULT = 0;

    private int borderType;
    private int arcW = 8;
    private int arcH = 8;

    public Border(){
        this.borderType = DEFAULT;
    }

    public void render(Graphics graphics, Position bodyPosition, Dimension bodyDimension){
        if(borderType == DEFAULT){
            graphics.setColor(new Color(0,0,0,175));
            graphics.drawRoundRect(bodyPosition.intX() - 1, bodyPosition.intY() - 1, bodyDimension.width + 2, bodyDimension.height + 2, arcW, arcH);
            graphics.setColor(Color.WHITE);
            graphics.drawRoundRect(bodyPosition.intX() + 0, bodyPosition.intY() + 0, bodyDimension.width - 0, bodyDimension.height - 0, arcW, arcH);
        }
    }
}
