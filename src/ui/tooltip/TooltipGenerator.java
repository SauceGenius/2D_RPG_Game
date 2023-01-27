package ui.tooltip;

import item.*;
import ui.Border;

import java.awt.*;

public class TooltipGenerator {

    public static final int ITEM = 0;
    public static final int SPELL = 1;

    private int type;

    public TooltipGenerator(){

    }

    public Tooltip generateItemTooltip(Item item){
        Tooltip tooltip = new Tooltip();

        int indexCount = 0;

        //Item Name
        TooltipLabel labelItemName = new TooltipLabel();
        labelItemName.setText(item.getName());
        switch (item.getQuality()){
            case ItemSettings.POOR_QUALITY -> labelItemName.setColor(TooltipSettings.COLOR_POOR_QUALITY);
            case ItemSettings.COMMON_QUALITY -> labelItemName.setColor(TooltipSettings.COLOR_COMMON_QUALITY);
            case ItemSettings.UNCOMMON_QUALITY -> labelItemName.setColor(TooltipSettings.COLOR_UNCOMMON_QUALITY);
            case ItemSettings.RARE_QUALITY -> labelItemName.setColor(TooltipSettings.COLOR_RARE_QUALITY);
            case ItemSettings.EPIC_QUALITY -> labelItemName.setColor(TooltipSettings.COLOR_EPIC_QUALITY);
            case ItemSettings.LEGENDARY_QUALITY -> labelItemName.setColor(TooltipSettings.COLOR_LEGENDARY_QUALITY);
        }

        labelItemName.setFont(TooltipSettings.FONT_ITEM_NAME);
        labelItemName.setIndex(indexCount);
        tooltip.getTooltipBody().addLabel(labelItemName);
        indexCount++;

        //Item Level
        TooltipLabel labelItemLevel = new TooltipLabel();
        labelItemLevel.setText(new String("Item Level ").concat(Integer.toString(((EquipableItem)item).getItemLevel())));
        labelItemLevel.setColor(Color.yellow);
        labelItemLevel.setIndex(indexCount);
        tooltip.getTooltipBody().addLabel(labelItemLevel);
        indexCount++;

        //If binding
        if(((EquipableItem)item).getBinding() > 0){
            TooltipLabel labelBinding = new TooltipLabel();
            switch (((EquipableItem)item).getBinding()){
                case 0 -> labelBinding.setText("Binds when equipped");
                case 1 -> labelBinding.setText("Binds when picked up");
            }
            labelBinding.setIndex(indexCount);
            tooltip.getTooltipBody().addLabel(labelBinding);
            indexCount++;
        }

        //Weapon Type
        TooltipLabel labelItemType = new TooltipLabel();
        if(item instanceof Weapon) {
            if (item instanceof OneHandWeapon) {
                labelItemType.setText("One-Hand");
            }
        }
        labelItemType.setIndex(indexCount);
        tooltip.getTooltipBody().addLabel(labelItemType);
        indexCount++;

        //Min and Max Damage
        TooltipLabel labelDamage = new TooltipLabel();
        labelDamage.setText(new String(Integer.toString(((Weapon)item).getMinDamage())).concat(" - ").concat(Integer.toString(((Weapon)item).getMaxDamage())).concat(" Damage"));
        labelDamage.setIndex(indexCount);
        tooltip.getTooltipBody().addLabel(labelDamage);

        //Speed
        TooltipLabelRight labelSpeed = new TooltipLabelRight();
        labelSpeed.setText(new String("Speed ").concat(Double.toString(((Weapon)item).getItemStat().getAttackSpeed())));
        labelSpeed.setIndex(indexCount);
        tooltip.getTooltipBody().addLabel(labelSpeed);
        indexCount++;

        //Damage per second
        TooltipLabel labelDps = new TooltipLabel();
        labelDps.setText(new String("(").concat(Double.toString(((Weapon) item).getDps())).concat(" damage per second)"));
        labelDps.setIndex(indexCount);
        tooltip.getTooltipBody().addLabel(labelDps);
        indexCount++;

        //Durability
        TooltipLabel labelDurability = new TooltipLabel();
        labelDurability.setText(new String("Durability ").concat(Integer.toString(((OneHandWeapon) item).getDurability())).concat(" / ").concat(Integer.toString(((OneHandWeapon) item).getMaxDurability())));
        labelDurability.setIndex(indexCount);
        tooltip.getTooltipBody().addLabel(labelDurability);
        indexCount++;

        ///TEST
        TooltipLabel labelTest = new TooltipLabel();
        labelTest.setText("Sell Price: 7");
        labelTest.setIndex(indexCount);
        tooltip.getTooltipBody().addLabel(labelTest);
        indexCount++;

        //Set Dimension
        //int height = TooltipSettings.LABEL_TEXT_MARGIN_VERTICAL
        int height = TooltipSettings.TOOLTIP_ARC_HEIGHT * 2 + indexCount * TooltipSettings.LABEL_TEXT_SPACING + 1;

        tooltip.getTooltipBody().setDimension(new Dimension(TooltipSettings.ITEM_TOOLTIP_WIDTH,height));

        //Set Border
        tooltip.setBorder(new Border());

        return tooltip;
    }
}
