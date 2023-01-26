package ui.inventoryui;

import inventory.Inventory;
import core.CollisionBox;
import core.Position;
import item.EquipableItem;
import item.Item;
import item.OneHandWeapon;
import ui.button.ItemIcon;

import java.awt.*;

public class InventorySlot {

    public static final int slotWidth = 36;
    private static final int slotHeight = 36;
    private boolean mouseOver = false;
    private boolean draggedOver = false;
    private int index;
    private int marginX = 8;
    private int marginY = 7;
    private Position position;
    private ItemIcon itemIcon;
    private Item item;

    public InventorySlot(int index, Position inventoryPosition) {
        this.index = index;
        int row = index / 4;
        int column = index % 4;
        position = new Position(inventoryPosition.intX() + 18 + column * (slotWidth + marginX), inventoryPosition.intY() + 54 + row * (slotHeight + marginY));
    }

    public void update(Inventory inventory){
        this.item = inventory.getItems()[index];
    }

    public void render(Graphics graphics) {
        if(item != null){
            graphics.drawImage(item.getIconSprite(), position.intX() + 2, position.intY() + 2, null);
            if (mouseOver){
                int arcW = 8;
                int arcH = 8;
                graphics.setColor(Color.black);
                graphics.drawRoundRect(position.intX() - 1, position.intY() - 1, slotWidth + 2, slotHeight + 2, arcW, arcH);
                graphics.setColor(new Color(150, 150, 150, 25));
                graphics.fillRect(position.intX(), position.intY(), slotWidth, slotHeight);

                // render item tooltips panel
                //if(1sec delay)
                int x = position.intX() - 100;
                int y = position.intY() - 150;
                int w = 220;
                int h = 120;
                int margin = 18;

                graphics.setColor(new Color(0,0,0,175));
                graphics.drawRoundRect(x - 1, y - 1, w + 2, h + 2, arcW, arcH);
                graphics.setColor(new Color(10,10,30,210));
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
            }
        }

        if(draggedOver){
            int arcW = 8;
            int arcH = 8;
            graphics.setColor(Color.blue);
            graphics.drawRoundRect(position.intX() - 1, position.intY() - 1, slotWidth + 2, slotHeight + 2, arcW, arcH);
            graphics.setColor(new Color(150, 150, 150, 25));
            graphics.fillRect(position.intX(), position.intY(), slotWidth, slotHeight);
        }
    }

    public CollisionBox getCollisionBox() {
        return new CollisionBox(new Rectangle(position.intX(), position.intY(), slotWidth, slotHeight));
    }

    public ItemIcon getItemIcon() {
        return itemIcon;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setMouseOver(Boolean bool) {
        mouseOver = bool;
    }

    public void setDraggedOver(Boolean bool){
        draggedOver = bool;
    }
}

