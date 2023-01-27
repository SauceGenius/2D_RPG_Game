package ui.tooltip;

import core.Position;

import java.awt.*;
import java.util.ArrayList;

public class TooltipBody {

    private Dimension dimension;
    private ArrayList<TooltipLabel> labels;

    public TooltipBody(){
        this.dimension = new Dimension(0,0);
        this.labels = new ArrayList<>();
    }

    public void render(Graphics graphics, Position tooltipPosition){
        graphics.setColor(TooltipSettings.COLOR_BACKGROUND_DEFAULT);
        graphics.fillRoundRect(tooltipPosition.intX(), tooltipPosition.intY(), dimension.width, dimension.height, TooltipSettings.TOOLTIP_ARC_WIDTH, TooltipSettings.TOOLTIP_ARC_HEIGHT);
        labels.forEach(label -> {label.render(graphics, tooltipPosition);});
    }

    public void addLabel(TooltipLabel label){
        labels.add(label);
    }

    public void setDimension(Dimension dimension){
        this.dimension = dimension;
    }

    public Dimension getDimension(){
        return dimension;
    }
}
