package ui;

import audio.AudioPlayer;
import character.Character;

import java.awt.*;

public class ExpBarUI extends UI {

    private Character character;

    public ExpBarUI(Character character){
        this.character = character;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics graphics) {
        int x = 600; int y = 1000; int w = 800; int h = 10;
        graphics.setColor(new Color(70,70,70,150));
        graphics.fillRect(x, y, w, h);

        graphics.setColor(new Color(90,0,140));
        graphics.fillRect(x, y, (int)((double) character.getStats().getLevel().getExp()/(double) character.getStats().getLevel().getExpToNextLevel() * w), h);

        graphics.setColor(Color.black);
        graphics.drawRect(x, y, w, h);
        for (int i = 0; i < 20; i++){
            graphics.drawRect(x + i * 40, y, 40,h);
        }
        graphics.setFont(new Font("TimesRoman", Font.BOLD,12));
    }

    @Override
    public void toggle(AudioPlayer audioPlayer) {}
}
