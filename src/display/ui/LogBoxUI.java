package display.ui;

import audio.AudioPlayer;
import core.Log;
import core.Position;
import settings.Settings;

import java.awt.*;

public class LogBoxUI extends UI {

    private Log log;
    private Dimension dimension = new Dimension(510,200);
    private Color colorBackground= new Color(70,70,70,150);
    private Color colorLogMenuTitle = Color.yellow;;
    private Color colorText = Color.black;
    private Color colorEXP = Color.blue;
    private Color colorLoot = Color.green;
    private Color colorLevelUp = Color.yellow;
    private Font fontLog = new Font("Arial Narrow", Font.BOLD, 20);
    private boolean logBoxGeneralIsOpen = false;
    private boolean logBoxCombatMyActionsIsOpen = true;
    private boolean isTyping = false;

    public LogBoxUI(Log log){
        enabled = true;
        this.log = log;
        position = new Position(20, Settings.WINDOW_HEIGHT - dimension.height - 35);
    }

    @Override
    public void update(AudioPlayer audioPlayer) {
    }

    @Override
    public void render(Graphics graphics) {

        int logX = position.intX();
        int logY = position.intY();
        int logH = dimension.height;
        int logW = dimension.width;

        /// Grey background ///
        graphics.setColor(colorBackground);
        graphics.fillRect(logX + 10, logY, 80, 30);
        graphics.fillRect(logX + 10 + 80 + 10, logY, 115, 30);
        graphics.setColor(colorLogMenuTitle);
        graphics.setFont(fontLog);
        graphics.drawString("General",logX + 18, logY + 20);
        graphics.drawString("Combat Log",logX + 18 + 80 + 10, logY + 20);
        graphics.setColor(colorBackground);
        graphics.fillRect(logX,logY + 32, logW, logH - 30);

        /// General ///
        if(logBoxGeneralIsOpen){
            for (int i = 0; i < 7; i++){
                if (log.getGeneral()[i][1] != null){
                    String txt = log.getGeneral()[i][1];

                    graphics.setColor(colorText);
                    graphics.drawString(txt, logX + 11, logY + logH - 7 - i * 20);

                    if (log.getGeneral()[i][0].equals("Exp")) {graphics.setColor(colorEXP);}
                    else if (log.getGeneral()[i][0].equals("Loot")) {graphics.setColor(colorLoot);}
                    else if (log.getGeneral()[i][0].equals("LevelUp")) {graphics.setColor(colorLevelUp);}

                    graphics.drawString(txt, logX + 10, logY + logH - 8 - i * 20);
                }
            }
        }

        /// Combat log box - My actions ///
        if(logBoxCombatMyActionsIsOpen) {
            for(int i = 0; i < 7; i++) {
                if(log.getDamageLog()[i][0] != null) {
                    String txt;
                    if (log.getDamageLog()[i][4].equals("Slain")) {
                        txt = log.getDamageLog()[i][0].concat(" has slain ").concat(log.getDamageLog()[i][1]).concat("!");
                    } else if (log.getDamageLog()[i][4].equals("Miss")) {
                        txt = log.getDamageLog()[i][0].concat("'s melee swing misses ").concat(log.getDamageLog()[i][1]).concat(".");
                    } else if (log.getDamageLog()[i][4].equals("Dodge")) {
                        txt = log.getDamageLog()[i][0].concat("'s melee swing was dodged by ").concat(log.getDamageLog()[i][1]).concat(".");
                    } else if (log.getDamageLog()[i][4].equals("Critical")) {
                        txt = log.getDamageLog()[i][0].concat("'s melee swing hits ").concat(log.getDamageLog()[i][1]).concat(" for ").concat(log.getDamageLog()[i][5].concat(" ").concat(log.getDamageLog()[i][6]).concat(". (Critical)"));
                    } else txt = log.getDamageLog()[i][0].concat("'s melee swing hits ").concat(log.getDamageLog()[i][1]).concat(" for ").concat(log.getDamageLog()[i][5].concat(" ").concat(log.getDamageLog()[i][6]).concat("."));

                    graphics.setColor(colorText);
                    graphics.drawString(txt, logX + 11, logY + logH - 7 - i * 20);
                    graphics.setColor(Color.white);
                    graphics.drawString(txt, logX + 10, logY + logH - 8 - i * 20);
                }
            }
        }
    }

    @Override
    public void open() {

    }
}
