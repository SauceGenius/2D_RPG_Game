package ui.slot;

import ability.Spell;
import character.Character;
import core.CollisionBox;
import core.Position;
import core.Timer;
import gameobject.Player;
import input.Input;
import item.Item;
import settings.KeyBinds;

import java.awt.*;

public class ActionSlot extends Slot {

    private Item item;
    private Spell spell;
    private Image backgroundImage;
    private char keybind;

    private double cooldownRemainingPerc;
    private Timer cooldownTimer;

    public ActionSlot(int index, Position uiPosition) {
        super(index, uiPosition);
        this.position = new Position(uiPosition.intX() + index * (SLOT_WIDTH + 6) + 7, uiPosition.intY() + 4);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        this.backgroundImage = toolkit.getImage("resources/sprites/ui/action_background.png");
    }

    public void update(Character character, Input input){
        if (getCollisionBox().collidesWith(new CollisionBox(new Rectangle(input.getMousePositionRelativeToScreen().intX() - 2, input.getMousePositionRelativeToScreen().intY() - 2, 4, 4)))){
            mouseOver = true;
        } else mouseOver = false;

        switch (index){
            case 0 -> keybind = KeyBinds.ACTION_BAR_1;
            case 1 -> keybind = KeyBinds.ACTION_BAR_2;
            case 2 -> keybind = KeyBinds.ACTION_BAR_3;
            case 3 -> keybind = KeyBinds.ACTION_BAR_4;
            case 4 -> keybind = KeyBinds.ACTION_BAR_5;
            case 5 -> keybind = KeyBinds.ACTION_BAR_6;
            case 6 -> keybind = KeyBinds.ACTION_BAR_7;
            case 7 -> keybind = KeyBinds.ACTION_BAR_8;
            case 8 -> keybind = KeyBinds.ACTION_BAR_9;
            case 9 -> keybind = KeyBinds.ACTION_BAR_0;
        }

        cooldownTimer = ((Player)character.getGameObject()).getAutoAttackTimer();
        cooldownRemainingPerc = cooldownTimer.getUpdatesCountDown() / (character.getStats().getAttackSpeed() * 60);

    }

    public void render(Graphics graphics){
        /** Slot Background Image and Border **/
        graphics.drawImage(backgroundImage, position.intX(), position.intY(), null);
        graphics.setColor(Color.gray);
        graphics.drawRoundRect(position.intX() - 1, position.intY() - 1, SLOT_WIDTH + 2, SLOT_HEIGHT + 2, ARC, ARC);

        /** Mouse Over if Item is not null **/
        if(item != null){
            item.render(graphics, position);

            /** Render KeyBind **/
            int x = position.intX() + 25; int y = position.intY() + 13;
            graphics.setFont(new Font("Times Roman", Font.BOLD, 12));
            graphics.setColor(Color.black);
            graphics.drawString(java.lang.Character.toString(keybind), x - 1, y);
            graphics.drawString(java.lang.Character.toString(keybind), x , y - 1);
            graphics.drawString(java.lang.Character.toString(keybind), x - 1, y - 1);
            graphics.drawString(java.lang.Character.toString(keybind), x - 1, y + 1);
            graphics.drawString(java.lang.Character.toString(keybind), x + 1, y - 1);
            graphics.drawString(java.lang.Character.toString(keybind), x + 1, y + 1);
            graphics.drawString(java.lang.Character.toString(keybind), x + 1, y);
            graphics.drawString(java.lang.Character.toString(keybind), x, y + 1);
            graphics.setColor(Color.lightGray);
            graphics.drawString(java.lang.Character.toString(keybind), x, y);

            if (mouseOver && !draggedOver){

                /** lightGray Mask **/
                graphics.setColor(COLOR_MOUSE_OVER);
                graphics.fillRect(position.intX(), position.intY(), SLOT_WIDTH, SLOT_HEIGHT);
            }
        }

        /** Auto Attack Cooldown**/
        if(index == 0){
            graphics.setColor(new Color(0,0,0, 150));
            graphics.fillRect(position.intX(), position.intY(), (int)((SLOT_WIDTH + 2) * cooldownRemainingPerc), SLOT_HEIGHT + 1);
        }

        /** Super Render **/
        super.render(graphics);


        /**if (mouseOver) {
            graphics.setColor(Color.lightGray);
            graphics.drawRoundRect(position.intX() - 1, position.intY() - 1, SLOT_WIDTH + 2, SLOT_HEIGHT + 2, ARC, ARC);
            graphics.setColor(COLOR_MOUSE_OVER);
            graphics.fillRect(position.intX(), position.intY(), SLOT_WIDTH, SLOT_HEIGHT);
        }**/
    }

    public CollisionBox getCollisionBox() {
        return new CollisionBox(new Rectangle(position.intX(), position.intY(), SLOT_WIDTH, SLOT_HEIGHT));
    }

    public Item getItem() {
        return item;
    }

    public boolean mouseIsOver(){
        return mouseOver;
    }

    public Position getPosition(){
        return position;
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



