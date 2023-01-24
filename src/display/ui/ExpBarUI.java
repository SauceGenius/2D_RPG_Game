package display.ui;

import audio.AudioPlayer;
import entity.Player;

import java.awt.*;

public class ExpBarUI extends UI {

    private Player player;

    public ExpBarUI(Player player){
        this.player = player;
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
        graphics.fillRect(x, y, (int)((double)player.getExp()/(double)player.getExpToNextLevel() * w), h);

        graphics.setColor(Color.black);
        graphics.drawRect(x, y, w, h);
        for (int i = 0; i < 20; i++){
            graphics.drawRect(x + i * 40, y, 40,h);
        }
        graphics.setFont(new Font("TimesRoman", Font.BOLD,12));
    }

    @Override
    public void open(AudioPlayer audioPlayer) {}
}
