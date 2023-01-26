package ui.tooltip;

import core.Position;
import ui.Border;

import java.awt.*;

public class Tooltip {

    public static final int ITEM = 1;
    public static final int SPELL = 1;

    public static final int TOOLTIP_ARC_WIDTH = 8;
    public static final int TOOLTIP_ARC_HEIGHT = 8;

    private int type;
    private Border border;
    private TooltipBody tooltipBody;
    private Position position;

    public Tooltip(){
        this.position = new Position(0,0);
        this.tooltipBody = new TooltipBody();
    }

    public void render(Graphics graphics, Position objectFocusedPosition){
        position = new Position(objectFocusedPosition.intX() - tooltipBody.getDimension().width - 20, objectFocusedPosition.intY() - tooltipBody.getDimension().height/2);

        tooltipBody.render(graphics, position);
        border.render(graphics, position, tooltipBody.getDimension());

        /*if(type == ITEM)
        {
            tooltipBody.render(graphics, position);
            border.render(graphics, objectFocusedPosition, tooltipBody.getDimension());

            int x = objectFocusedPosition.intX() - 100;
            int y = objectFocusedPosition.intY() - 150;
            int w = 220;
            int h = 120;
            int margin = 18;

            graphics.setColor(new Color(0,0,0,175));
            graphics.drawRoundRect(x - 1, y - 1, w + 2, h + 2, arcW, arcH);
            graphics.setColor(bodyColor);
            graphics.fillRoundRect(x, y, w,h, arcW, arcH);
            graphics.setColor(Color.WHITE);
            graphics.drawRoundRect(x, y, w, h, arcW, arcH);
            graphics.setFont(new Font("Verdana", Font.BOLD, 14));
            graphics.drawString(item.getName(), x + 10,y + 20);
            graphics.setColor(Color.YELLOW);
            graphics.setFont(new Font("Verdana", Font.BOLD, 12));
            String itemLevel1 = "Item Level ";
            String itemLevel2 = Integer.toString(((EquipableItem)item).getItemLevel());
            itemLevel1 = itemLevel1.concat(itemLevel2);
            graphics.drawString(itemLevel1,x + 10,y + 20 + margin);
            graphics.setColor(Color.WHITE);
            //graphics.drawString("Binds when equipped", x + 10,y + 20 + margin * 2);
            graphics.drawString("One Hand", x + 10,y + 20 + margin * 2);
            String minDamage = Integer.toString((int)item.getItemStat().getMinMeleeWeaponDamage());
            String maxDamage = Integer.toString((int)item.getItemStat().getMaxMeleeWeaponDamage());
            String damageLine = minDamage.concat(" - ").concat(maxDamage).concat(" Damage");
            graphics.drawString(damageLine, x + 10,y + 20 + margin * 3);
            String dps = new String("(").concat(Double.toString(((OneHandWeapon)item).getDps())).concat(" damage per second)");
            graphics.drawString(dps, x + 10,y + 20 + margin * 4);
            String durability = new String("Durability: ").concat(Integer.toString(((OneHandWeapon) item).getDurability())).concat(" / ").concat(Integer.toString(((OneHandWeapon) item).getMaxDurability()));
            graphics.drawString(durability, x + 10,y + 20 + margin * 5);
        }*/
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
