package ui.tooltip;

import core.Position;

import java.awt.*;
import java.util.ArrayList;

public class TooltipBody {

    public static final Color color = new Color(10,10,30,210);

    private Dimension dimension;
    private ArrayList<CLabel> labels;

    public TooltipBody(){
        this.dimension = new Dimension(0,0);
        this.labels = new ArrayList<>();
    }

    public void render(Graphics graphics, Position tooltipPosition){
        graphics.setColor(color);
        graphics.fillRoundRect(tooltipPosition.intX(), tooltipPosition.intY(), dimension.width, dimension.height, Tooltip.TOOLTIP_ARC_WIDTH, Tooltip.TOOLTIP_ARC_HEIGHT);
        labels.forEach(label -> {label.render(graphics, tooltipPosition);});
    }

    public void addLabel(CLabel label){
        labels.add(label);
    }

    public void setDimension(Dimension dimension){
        this.dimension = dimension;
    }

    public Dimension getDimension(){
        return dimension;
    }
}
