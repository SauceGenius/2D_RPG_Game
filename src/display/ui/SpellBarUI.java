package display.ui;

import audio.AudioPlayer;
import entity.Player;

import java.awt.*;

public class SpellBarUI extends UI {

    private Player player;

    public SpellBarUI(Player player){
        this.player = player;
    }


    @Override
    public void render(Graphics graphics) {
        int x = 600; int y = 1012; int w = 410; int h = 36;
        graphics.setColor(new Color(70,70,70));
        graphics.fillRect(x,y,w,h);
        graphics.setColor(Color.black);
        for(int i = 0; i < 12; i++){
            graphics.drawRect(x + i * 34 + 2, y + 2, 32, 32);
        }
        graphics.drawRect(x,y,w,h);

        graphics.setColor(new Color(60,60,60, 150));
        graphics.fillRect(x + 3, y + 3, (int)(31 * player.getAutoAttackTimer().getUpdatesCountDown() / (player.getStats().getAttackSpeed() * 60)), 31);
    }

    @Override
    public void update() {

    }

    @Override
    public void open(AudioPlayer audioPlayer) {

    }
}
