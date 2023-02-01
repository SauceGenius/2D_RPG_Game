package ui;

import audio.AudioPlayer;
import core.CollisionBox;
import core.Position;
import gameobject.Player;
import character.Character;
import input.Input;
import ui.slot.ActionSlot;
import ui.slot.Slot;

import java.awt.*;

public class ActionBarUI extends UI {

    private Character character;
    private ActionSlot[] actionBar1;

    public ActionBarUI(Character character){
        this.character = character;
        this.position = new Position(450, 1036);
        this.dimension = new Dimension(512,44);
        actionBar1 = new ActionSlot[12];
        for(int i  = 0; i < actionBar1.length; i++){
            actionBar1[i] = new ActionSlot(i, position);
        }
    }

    @Override
    public void update() {
        actionBar1[0].setItem(character.getEquipment().getItem(0));
    }

    public void update(Input input) {
        for (int i = 0; i < actionBar1.length; i++){
            actionBar1[i].update(character, input);
        }
    }

    @Override
    public void render(Graphics graphics) {

        /** Temporary Right Gray Panel **/
        graphics.setColor(new Color(70,70,70));
        graphics.fillRect(position.intX(), position.intY(), dimension.width + 520, dimension.height);
        graphics.setColor(Color.gray);
        graphics.drawRect(position.intX(), position.intY(), dimension.width + 520, dimension.height);
        graphics.setColor(new Color(20,20,40,175));
        graphics.drawRect(position.intX() - 1, position.intY() - 1, dimension.width + 522, dimension.height + 2);

        /** Background **/
        graphics.setColor(new Color(70,70,70));
        graphics.fillRect(position.intX(), position.intY(), dimension.width, dimension.height);
        graphics.setColor(Color.gray);
        graphics.drawRect(position.intX(), position.intY(), dimension.width, dimension.height);
        graphics.setColor(new Color(20,20,40,175));
        graphics.drawRect(position.intX() - 1, position.intY() - 1, dimension.width + 2, dimension.height + 2);

        /** Action Buttons **/
        for (int i = 0; i < actionBar1.length; i++){
            actionBar1[i].render(graphics);
        }

        /** Auto Attack Cooldown**/
        //graphics.setColor(new Color(250,250,250, 150));
        //graphics.fillRect(position.intX() + 3, position.intY() + 3, (int)(36 * ((Player)character.getGameObject()).getAutoAttackTimer().getUpdatesCountDown() / (character.getStats().getAttackSpeed() * 60)), 36);

        /** Draw Left Gryphon **/
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image leftGryphonImage = toolkit.getImage("resources/sprites/ui/gryphon_left.png");
        graphics.drawImage(leftGryphonImage, position.intX() - 110, position.intY() - 53, null);

        /** Draw Right Gryphon **/
        Image rightGryphonImage = toolkit.getImage("resources/sprites/ui/gryphon_right.png");
        graphics.drawImage(rightGryphonImage, position.intX() + 991, position.intY() - 53, null);
    }

    @Override
    public void toggle(AudioPlayer audioPlayer) {

    }

    @Override
    public CollisionBox getCollisionBox() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Slot[] getActionBar1() {
        return actionBar1;
    }
}
