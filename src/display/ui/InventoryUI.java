package display.ui;

import audio.AudioPlayer;
import core.Position;
import entity.Inventory;

import java.awt.*;;

public class InventoryUI extends UI {

    private Inventory inventory;

    public InventoryUI(Inventory inventory) {
        enabled = false;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image bagImage = toolkit.getImage("resources/sprites/ui/Bag.png");
        images.add(bagImage);
        position = new Position(1600,700);
        this.inventory = inventory;
    }

    public void update() {}

    @Override
    public void render(Graphics graphics){
        if(enabled){
            graphics.drawImage(images.get(0), position.intX(), position.intY(), null);

            // Each space (items)
            int iconWidth = 36;
            int iconHeight = 36;
            int itemPosX = position.intX() + 20;
            int itemPosY = position.intY() + 56;
            int marginX = 8;
            int marginY = 8;

            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {

                    // Render item icons
                    if (inventory.getItems()[(x + y * 4)] != null) {
                        graphics.drawImage(inventory.getItems()[(x + y * 4)].getIconSprite(), x * (iconWidth + marginX) + itemPosX, y * (iconHeight + marginY) + itemPosY, null);
                    }

                    /*
                    //item grey box
                    graphics.setColor(new Color(70, 70, 70));
                    graphics.fillRect(x * (iconWidth + marginX) + itemPosX, y * (iconHeight + marginY) + itemPosY, iconWidth, iconHeight);
                    graphics.setColor(new Color(0, 0, 0));
                    graphics.drawRect(x * (iconWidth + marginX) + itemPosX, y * (iconHeight + marginY) + itemPosY, iconWidth, iconHeight);
                    */
                }
            }
        }
    }

    @Override
    public void open(AudioPlayer audioPlayer){
        if (!enabled) {
            enabled = true;
            audioPlayer.playSound("OpenBag.wav");
        } else enabled = false;
    }
}