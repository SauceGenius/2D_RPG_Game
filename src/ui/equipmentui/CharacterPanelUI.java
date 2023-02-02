package ui.equipmentui;

import audio.AudioLibrary;
import audio.AudioPlayer;
import core.CollisionBox;
import core.Position;
import gameobject.Player;
import character.Character;
import input.Input;
import stats.Stats;
import ui.UI;
import ui.slot.EquipmentSlot;
import ui.tooltip.TooltipGenerator;

import java.awt.*;

public class CharacterPanelUI extends UI {

    private static final int CHARACTER_PANEL_SIZE = 20;
    private EquipmentSlot[] equipmentSlots;
    private Character character;
    private Player player;
    private TooltipGenerator tooltipGenerator;

    public CharacterPanelUI(Character character){
        this.opened = false;
        this.position = new Position(20,200);
        this.dimension = new Dimension(350, 460);
        this.character = character;
        this.player = (Player)character.getGameObject();
        this.tooltipGenerator = new TooltipGenerator();

        equipmentSlots = new EquipmentSlot[CHARACTER_PANEL_SIZE];
        for(int i = 0; i < CHARACTER_PANEL_SIZE; i++) equipmentSlots[i] = new EquipmentSlot(i, position);
    }

    @Override
    public void update() {
        for(int i = 0; i < CHARACTER_PANEL_SIZE; i++){
            equipmentSlots[i].update(character.getEquipment());
        }
    }

    public void update(Input input){
        super.update(input);

        for(int i = 0; i < CHARACTER_PANEL_SIZE; i++){
            equipmentSlots[i].update(character.getEquipment());
        }

        if(opened){
            if(getCollisionBox().collidesWith(new CollisionBox(new Rectangle(
                    input.getMousePositionRelativeToScreen().intX() - 2,
                    input.getMousePositionRelativeToScreen().intY() - 2, 4, 4)))){

                mouseOverUI = true;

                for(int i = 0; i < equipmentSlots.length; i++){
                    if(equipmentSlots[i].getCollisionBox().collidesWith(new CollisionBox(new Rectangle(
                            input.getMousePositionRelativeToScreen().intX() - 2,
                            input.getMousePositionRelativeToScreen().intY() - 2, 4, 4)))) {

                        equipmentSlots[i].setMouseOver(true);
                    } else equipmentSlots[i].setMouseOver(false);
                }

            } else mouseOverUI = false;
        }

    }

    @Override
    public void render(Graphics graphics) {
        if (opened) {
            /** Background **/
            graphics.setColor(new Color(70, 70, 70));
            graphics.fillRect(position.intX(), position.intY(), dimension.width, dimension.height);
            graphics.setColor(new Color(0, 0, 0));
            graphics.drawRect(position.intX(), position.intY(), dimension.width, dimension.height);

            /** Equipment top **/
            graphics.setColor(new Color(70, 70, 70));
            graphics.fillRect( position.intX() + 37, position.intY() + 5, dimension.width - 72, 19);
            graphics.setColor(Color.black);
            graphics.drawRect(position.intX() + 37, position.intY() + 5, dimension.width - 72, 19);

            graphics.setColor(Color.white);
            graphics.setFont(new Font("TimesRoman", Font.BOLD, 12));
            graphics.drawString(player.getName(),  position.intX() + 120, position.intY() + 18);
            graphics.setColor(Color.yellow);
            graphics.setFont(new Font("TimesRoman", Font.BOLD, 11));
            graphics.drawString("Level " + player.getLevel() + " Human " + character.getGameClassId().toString(),  position.intX() + 100, position.intY() + 36);

            /** Stats name **/
            graphics.setColor(Color.yellow);
            graphics.setFont(new Font("TimesRoman", Font.BOLD, 11));
            graphics.drawString("Strength: ",position.intX() + 50, position.intY() + 270);
            graphics.drawString("Agility: ",position.intX() + 50, position.intY() + 284);
            graphics.drawString("Stamina: ",position.intX() + 50, position.intY() + 298);
            graphics.drawString("Intellect:  ",position.intX() + 50, position.intY() + 312);
            graphics.drawString("Spirit: ",position.intX() + 50, position.intY() + 326);
            graphics.drawString("Armor: ",position.intX() + 50, position.intY() + 340);

            graphics.drawString("Melee Attack: ",position.intX() + 160, position.intY() + 270);
            graphics.drawString("Power: ",position.intX() + 164, position.intY() + 284);
            graphics.drawString("Damage: ",position.intX() + 164, position.intY() + 298);

            /** Stats value **/
            graphics.setColor(Color.white);
            graphics.setFont(new Font("TimesRoman", Font.BOLD, 11));
            graphics.drawString(Integer.toString(player.getStats().getStat(Stats.STRENGTH)),position.intX() + 135, position.intY() + 270);
            graphics.drawString(Integer.toString(player.getStats().getStat(Stats.AGILITY)),position.intX() + 135, position.intY() + 284);
            graphics.drawString(Integer.toString(player.getStats().getStat(Stats.STAMINA)),position.intX() + 135, position.intY() + 298);
            graphics.drawString(Integer.toString(player.getStats().getStat(Stats.INTELLECT)),position.intX() + 135, position.intY() + 312);
            graphics.drawString(Integer.toString(player.getStats().getStat(Stats.SPIRIT)),position.intX() + 135, position.intY() + 326);
            graphics.drawString(Integer.toString(player.getStats().getArmor()),position.intX() + 135, position.intY() + 340);

            graphics.drawString("Skill",position.intX() + 245, position.intY() + 270);
            graphics.drawString(Long.toString(Math.round(player.getStats().getMeleeAttackPower())),position.intX() + 255, position.intY() + 284);
            graphics.drawString((int)(player.getStats().getMinMeleeWeaponDamage() + player.getStats().getMeleeAttackPower()/14*player.getStats().getAttackSpeed()) +
                            " - " + Math.round(player.getStats().getMaxMeleeWeaponDamage() + player.getStats().getMeleeAttackPower()/14*player.getStats().getAttackSpeed())
                    ,position.intX() + 245, position.intY() + 298);

            /** Each space **/
            for (int i = 0; i < CHARACTER_PANEL_SIZE; i++){
                equipmentSlots[i].render(graphics);
            }

            /** Tooltip **/
            for(int i = 0; i < CHARACTER_PANEL_SIZE; i++){
                if(equipmentSlots[i].mouseIsOver() && equipmentSlots[i].getItem() != null && !equipmentSlots[i].getItem().getItemIcon().isDragged()){
                    tooltipGenerator.generateItemTooltip(equipmentSlots[i].getItem()).render(graphics, equipmentSlots[i].getPosition());
                }
            }
        }
    }

    @Override
    public void toggle(AudioPlayer audioPlayer) {
        if (!opened) {
            opened = true;
            audioPlayer.playSound(AudioLibrary.CHARACTER_SHEET_OPENED_SOUND_EFFECT);
        } else {
            opened = false;
            draggingItemOver = false;
            mouseOverUI = false;
            for (int i = 0; i < equipmentSlots.length; i++){
                equipmentSlots[i].setDraggedOver(false);
            }
        }
    }

    public EquipmentSlot[] getEquipmentSlots() {
        return equipmentSlots;
    }
}
