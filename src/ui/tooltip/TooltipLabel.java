package ui.tooltip;

import core.Position;

import java.awt.*;

public class TooltipLabel {

    protected int index;
    protected String text;
    protected Color color;
    protected Font font;


    public TooltipLabel(){
        this.color = TooltipSettings.COLOR_TEXT_DEFAULT;
        this.font = TooltipSettings.FONT_ITEM_TEXT_DEFAULT;
    }

    public void render(Graphics graphics, Position tooltipPosition){
        graphics.setColor(color);
        graphics.setFont(font);
        graphics.drawString(text, tooltipPosition.intX() + TooltipSettings.LABEL_TEXT_MARGIN_HORIZONTAL,tooltipPosition.intY() + TooltipSettings.LABEL_TEXT_MARGIN_VERTICAL + TooltipSettings.FONT_ITEM_NAME.getSize() + TooltipSettings.LABEL_TEXT_SPACING * index);
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
