package ui.tooltip;

import item.*;
import ui.Border;

import java.awt.*;
import java.text.DecimalFormat;

public class TooltipGenerator {

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
        labelItemLevel.setColor(TooltipSettings.COLOR_TEXT_ITEM_LEVEL);
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

        if (item instanceof EquipableItem){
            /**Equippable Sub Type**/
            TooltipLabel labelItemSubType = new TooltipLabel();
            labelItemSubType.setText("Main-Hand"); /// TO IMPROVE
            labelItemSubType.setIndex(indexCount);
            tooltip.getTooltipBody().addLabel(labelItemSubType);

            /**Equippable Type**/
            TooltipLabelRight labelItemType = new TooltipLabelRight();
            labelItemType.setText("Sword");
            labelItemType.setIndex(indexCount);
            tooltip.getTooltipBody().addLabel(labelItemType);
            indexCount++;



            if(item instanceof Weapon){
                //Min and Max Damage
                TooltipLabel labelDamage = new TooltipLabel();
                labelDamage.setText(new String(String.format("%.0f",item.getItemStat().getMinMeleeWeaponDamage()).concat(" - ").concat(String.format("%.0f",item.getItemStat().getMaxMeleeWeaponDamage()).concat(" Damage"))));
                labelDamage.setIndex(indexCount);
                tooltip.getTooltipBody().addLabel(labelDamage);

                //Speed
                TooltipLabelRight labelSpeed = new TooltipLabelRight();
                DecimalFormat formatter = new DecimalFormat("#0.00");
                labelSpeed.setText(new String("Speed ").concat(formatter.format(((Weapon)item).getItemStat().getAttackSpeed())));
                labelSpeed.setIndex(indexCount);
                tooltip.getTooltipBody().addLabel(labelSpeed);
                indexCount++;

                //Damage per second
                TooltipLabel labelDps = new TooltipLabel();
                labelDps.setText(new String("(").concat(formatter.format(((Weapon) item).getDps())).concat(" damage per second)"));
                labelDps.setIndex(indexCount);
                tooltip.getTooltipBody().addLabel(labelDps);
                indexCount++;
            }
            //Stamina
            if(item.getItemStat().getStamina() > 0){
                TooltipLabel labelStamina = new TooltipLabel();
                labelStamina.setText(new String("+").concat(Integer.toString(item.getItemStat().getStamina())).concat(" Stamina"));
                labelStamina.setIndex(indexCount);
                tooltip.getTooltipBody().addLabel(labelStamina);
                indexCount++;
            }

            //Strength
            if(item.getItemStat().getStrength() > 0){
                TooltipLabel labelStrength = new TooltipLabel();
                labelStrength.setText(new String("+").concat(Integer.toString(item.getItemStat().getStrength())).concat(" Strength"));
                labelStrength.setIndex(indexCount);
                tooltip.getTooltipBody().addLabel(labelStrength);
                indexCount++;
            }

            //Durability
            TooltipLabel labelDurability = new TooltipLabel();
            labelDurability.setText(new String("Durability ").concat(Integer.toString(((OneHandWeapon) item).getDurability())).concat(" / ").concat(Integer.toString(((OneHandWeapon) item).getMaxDurability())));
            labelDurability.setIndex(indexCount);
            tooltip.getTooltipBody().addLabel(labelDurability);
            indexCount++;

            //Level Requirement
            if(((EquipableItem) item).getLevelRequired() > 0){
                TooltipLabel labelLevelRequired = new TooltipLabel();
                labelLevelRequired.setText(new String("Requires Level ").concat(Integer.toString(((EquipableItem) item).getLevelRequired())));
                labelLevelRequired.setIndex(indexCount);
                tooltip.getTooltipBody().addLabel(labelLevelRequired);
                indexCount++;
            }
        } else {

        }

        //Set Dimension
        int height = TooltipSettings.TOOLTIP_ARC_HEIGHT * 2 + indexCount * TooltipSettings.LABEL_TEXT_SPACING + 2;
        tooltip.getTooltipBody().setDimension(new Dimension(TooltipSettings.ITEM_TOOLTIP_WIDTH,height));

        //Set Border
        tooltip.setBorder(new Border());

        return tooltip;
    }
}
