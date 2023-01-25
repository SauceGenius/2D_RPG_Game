package ui;

import audio.AudioPlayer;
import core.Position;
import core.Size;
import gameobject.Player;
import character.Character;

import java.awt.*;

public class CharacterPanelUI extends UI {
    
    private Size equipmentSize;
    private Character character;
    private Player player;

    public CharacterPanelUI(Character character){
        opened = false;
        position = new Position(20,200);
        equipmentSize = new Size(310, 390);
        this.character = character;
        player = (Player)character.getGameObject();
    }

    @Override
    public void update() {}

    @Override
    public void render(Graphics graphics) {
        if (opened) {
            //Background
            graphics.setColor(new Color(70, 70, 70));
            graphics.fillRect(position.intX(), position.intY(), equipmentSize.getWidth(), equipmentSize.getHeight());
            graphics.setColor(new Color(0, 0, 0));
            graphics.drawRect(position.intX(), position.intY(), equipmentSize.getWidth(), equipmentSize.getHeight());

            //equipment.Equipment top
            graphics.setColor(new Color(70, 70, 70));
            graphics.fillRect( position.intX() + 37, position.intY() + 5, equipmentSize.getWidth() - 72, 19);
            graphics.setColor(Color.black);
            graphics.drawRect(position.intX() + 37, position.intY() + 5, equipmentSize.getWidth() - 72, 19);

            graphics.setColor(Color.white);
            graphics.setFont(new Font("TimesRoman", Font.BOLD, 12));
            graphics.drawString(player.getName(),  position.intX() + 120, position.intY() + 18);
            graphics.setColor(Color.yellow);
            graphics.setFont(new Font("TimesRoman", Font.BOLD, 11));
            graphics.drawString("Level " + player.getLevel() + " Human " + character.getGameClassId().toString(),  position.intX() + 100, position.intY() + 36);

            //Stats name
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

            //Stats value
            graphics.setColor(Color.white);
            graphics.setFont(new Font("TimesRoman", Font.BOLD, 11));
            graphics.drawString(Integer.toString(player.getStats().getStat("strength")),position.intX() + 135, position.intY() + 270);
            graphics.drawString(Integer.toString(player.getStats().getStat("agility")),position.intX() + 135, position.intY() + 284);
            graphics.drawString(Integer.toString(player.getStats().getStat("stamina")),position.intX() + 135, position.intY() + 298);
            graphics.drawString(Integer.toString(player.getStats().getStat("intellect")),position.intX() + 135, position.intY() + 312);
            graphics.drawString(Integer.toString(player.getStats().getStat("spirit")),position.intX() + 135, position.intY() + 326);
            graphics.drawString(Integer.toString(player.getStats().getArmor()),position.intX() + 135, position.intY() + 340);

            graphics.drawString("Skill",position.intX() + 245, position.intY() + 270);
            graphics.drawString(Long.toString(Math.round(player.getStats().getMeleeAttackPower())),position.intX() + 255, position.intY() + 284);
            graphics.drawString((int)(player.getStats().getMinMeleeWeaponDamage() + player.getStats().getMeleeAttackPower()/14*player.getStats().getAttackSpeed()) +
                            " - " + Math.round(player.getStats().getMaxMeleeWeaponDamage() + player.getStats().getMeleeAttackPower()/14*player.getStats().getAttackSpeed())
                    ,position.intX() + 245, position.intY() + 298);

            // Each space
            for (int x = 0; x < 2; x++) {
                for (int y = 0; y < 8; y++) {
                    graphics.setColor(new Color(70, 70, 70));
                    graphics.fillRect(x * 268 + position.intX() + 5, y * 36 + position.intY() + 70, 32, 32);
                    graphics.setColor(new Color(0, 0, 0));
                    graphics.drawRect(x * 268 + position.intX() + 5, y * 36 + position.intY() + 70, 32, 32);
                    graphics.setColor(Color.white);
                    graphics.drawString(Integer.toString(x + y * 2 + 3), x * 268 + position.intX()+ 5, y * 36 + position.intY() + 102);
                    if (player.getEquipment().getEquipment()[(x + y * 2 + 3)] != null) {
                        graphics.drawImage(player.getEquipment().getItem(x + y * 2 + 3).getIconSprite(), x * 268 + position.intX() + 5, y * 32 + position.intY() + 70, null);
                    }
                }
            }

            graphics.setColor(new Color(70, 70, 70));
            graphics.fillRect(position.intX() + 100, position.intY() + 350, 32, 32);
            graphics.setColor(new Color(0, 0, 0));
            graphics.drawRect(position.intX() + 100, position.intY() + 350, 32, 32);
            graphics.drawRect(position.intX() + 136, position.intY() + 350, 32, 32);
            graphics.drawRect(position.intX() + 172, position.intY() + 350, 32, 32);
            if(player.getEquipment().getItem(0) != null){
                graphics.drawImage(player.getEquipment().getItem(0).getIconSprite(), position.intX() + 100, position.intY() + 350, null);
            }
            if(player.getEquipment().getItem(1) != null){
                graphics.drawImage(player.getEquipment().getItem(0).getIconSprite(), position.intX() + 134, position.intY() + 350, null);
            }
            if(player.getEquipment().getItem(2) != null){
                graphics.drawImage(player.getEquipment().getItem(0).getIconSprite(), position.intX() + 138, position.intY() + 350, null);
            }
        }
    }

    @Override
    public void toggle(AudioPlayer audioPlayer) {
        if (!opened) {
            opened = true;
            audioPlayer.playSound("CharacterSheetOpen.wav");
        } else opened = false;
    }
}
