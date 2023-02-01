package ui.slot;

import core.Position;
import equipment.Equipment;
import item.Item;

import java.awt.*;

public class EquipmentSlot extends Slot {

    private int marginX = 8;
    private int marginY = 7;
    private Item item;
    private Image backgroundImage;

    public EquipmentSlot(int index, Position uiPosition){
        super(index, uiPosition);
        if(index > 3){
            int row = (index - 4) % 8;
            int column = (index - 4) / 8;
            position = new Position(uiPosition.intX() + 6 + column * (SLOT_WIDTH + marginX + 258), uiPosition.intY() + 54 + row * (SLOT_HEIGHT + marginY));
        } else {
            int column = index % 5;
            position = new Position(uiPosition.intX() + 85 + column * (SLOT_WIDTH + marginX), uiPosition.intY() + 54 + 8 * (SLOT_HEIGHT + marginY));
        }
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        this.backgroundImage = toolkit.getImage("resources/sprites/ui/action_background.png");
    }

    public void update(Equipment equipment){
        this.item = equipment.getItem(index);
    }

    public void render(Graphics graphics) {

        /** Slot Background Image and Border **/
        graphics.drawImage(backgroundImage, position.intX(), position.intY(), null);
        graphics.setColor(Color.gray);
        graphics.drawRoundRect(position.intX() - 1, position.intY() - 1, SLOT_WIDTH + 2, SLOT_HEIGHT + 2, ARC, ARC);

        /** Mouse Over if Item is not null **/
        if(item != null){
            item.render(graphics, position);
            if (mouseOver && !draggedOver){

                /** lightGray Mask **/
                graphics.setColor(COLOR_MOUSE_OVER);
                graphics.fillRect(position.intX(), position.intY(), SLOT_WIDTH, SLOT_HEIGHT);
            }
        }

        /** Super Render **/
        super.render(graphics);
    }

    public Item getItem() {
        return item;
    }

}
