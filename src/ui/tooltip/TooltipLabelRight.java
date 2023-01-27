package ui.tooltip;

import core.Position;

import java.awt.*;

public class TooltipLabelRight extends TooltipLabel{

    public TooltipLabelRight(){
        super();
    }

    @Override
    public void render(Graphics graphics, Position tooltipPosition){
        graphics.setColor(color);
        graphics.setFont(font);

        Position labelPosition = calculatePosition(graphics, tooltipPosition);
        graphics.drawString(text, labelPosition.intX(),labelPosition.intY());
    }

    private Position calculatePosition(Graphics graphics, Position tooltipPosition){
        FontMetrics metrics = graphics.getFontMetrics(TooltipSettings.FONT_ITEM_TEXT_DEFAULT);
        int textWidth = metrics.stringWidth(text);

        return new Position(tooltipPosition.intX() + TooltipSettings.ITEM_TOOLTIP_WIDTH - textWidth - TooltipSettings.LABEL_TEXT_MARGIN_HORIZONTAL, tooltipPosition.intY() + TooltipSettings.LABEL_TEXT_MARGIN_VERTICAL + TooltipSettings.FONT_ITEM_NAME.getSize() + TooltipSettings.LABEL_TEXT_SPACING * index);
    }

}
