package ui;

import audio.AudioPlayer;
import core.Position;
import character.Character;
import quest.QuestLog;

import java.awt.*;

public class QuestLogUI extends UI{

    private QuestLog questLog;

    public QuestLogUI(Character character){
        opened = false;
        questLog = character.getQuestLog();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image bagImage = toolkit.getImage("resources/sprites/ui/QuestLog.png");
        images.add(bagImage);
        position = new Position(50,200);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics graphics) {
        if(opened){
            if(questLog.getQuestList()[0] == null){
                graphics.drawImage(images.get(0), position.intX(), position.intY(), null);
            }
        }
    }

    @Override
    public void toggle(AudioPlayer audioPlayer) {
        if (!opened) {
            opened = true;
            //audioPlayer.playSound("OpenInventory.wav");
        } else {
            opened = false;
            //audioPlayer.playSound("CloseInventory.wav");
        }
    }
}
