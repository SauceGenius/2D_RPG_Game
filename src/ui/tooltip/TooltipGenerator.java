package ui.tooltip;

import item.EquipableItem;
import item.Item;
import ui.Border;

import java.awt.*;

public class TooltipGenerator {

    public static final int ITEM = 0;
    public static final int SPELL = 1;

    public static final Font FONT_ITEM_NAME = new Font("Verdana", Font.BOLD, 14);
    public static final Font FONT_ITEM_BODY = new Font("Verdana", Font.BOLD, 12);

    public static final Color COLOR_POOR = Color.gray;
    public static final Color COLOR_COMMON = Color.white;
    public static final Color COLOR_UNCOMMON = Color.green;
    public static final Color COLOR_RARE = Color.blue;
    public static final Color COLOR_EPIC = new Color(125,0,125);
    public static final Color COLOR_LEGENDARY = Color.orange;


    private int type;

    public TooltipGenerator(){

    }

    public Tooltip generateItemTooltip(Item item){
        Tooltip tooltip = new Tooltip();

        int indexCount = 0;

        //Item Name
        CLabel labelItemName = new CLabel();
        labelItemName.setText(item.getName());
        /// QUALITY if(item.getQuality() == common;
        labelItemName.setColor(COLOR_COMMON);
        labelItemName.setFont(FONT_ITEM_NAME);
        labelItemName.setIndex(indexCount);
        tooltip.getTooltipBody().addLabel(labelItemName);
        indexCount++;

        //Item Level
        CLabel labelItemLevel = new CLabel();
        labelItemLevel.setText(new String("Item Level ").concat(Integer.toString(((EquipableItem)item).getItemLevel())));
        labelItemLevel.setColor(Color.yellow);
        labelItemLevel.setFont(FONT_ITEM_BODY);
        labelItemLevel.setIndex(indexCount);
        tooltip.getTooltipBody().addLabel(labelItemLevel);
        indexCount++;

        //Weapon Type
        CLabel labelItemType = new CLabel();
        //labelItemType.setText();


        //Set Dimension
        tooltip.getTooltipBody().setDimension(new Dimension(220,120));

        //Set Border
        tooltip.setBorder(new Border());

        return tooltip;
    }
}
