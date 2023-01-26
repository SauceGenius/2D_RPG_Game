package ui.tooltip;

import core.Position;

import java.awt.*;

public class CLabel {

    private int index;
    private String text;
    private Color color;
    private Font font;


    public CLabel(){

    }

    public void render(Graphics graphics, Position tooltipPosition){
        int margin = 18;
        graphics.setColor(color);
        graphics.setFont(font);
        graphics.drawString(text, tooltipPosition.intX() + 10,tooltipPosition.intY() + 20 + margin * index );
    }

    public void setIndex(int index){
        this.index = index;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public String getText(){
        return text;
    }
}
