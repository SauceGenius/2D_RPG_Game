package ui.tooltip;

import core.Position;
import ui.Border;

import java.awt.*;

public class Tooltip {

    public static final int ITEM = 1;
    public static final int SPELL = 1;

    private int type;
    private Border border;
    private TooltipBody tooltipBody;
    private Position position;

    public Tooltip(){
        this.position = new Position(0,0);
        this.tooltipBody = new TooltipBody();
    }

    public void render(Graphics graphics, Position objectFocusedPosition){
        if(objectFocusedPosition.getX() > 540){
            position = new Position(objectFocusedPosition.intX() - tooltipBody.getDimension().width - 20, objectFocusedPosition.intY() - tooltipBody.getDimension().height/2);
        } else position = new Position(objectFocusedPosition.intX() + 60, objectFocusedPosition.intY() - tooltipBody.getDimension().height/2);

        tooltipBody.render(graphics, position);
        border.render(graphics, position, tooltipBody.getDimension());
    }

    public void setBorder(Border border){
        this.border = border;
    }

    public void setTooltipBody(TooltipBody tooltipBody){
        this.tooltipBody = tooltipBody;
    }

    public TooltipBody getTooltipBody() {
        return tooltipBody;
    }


}
