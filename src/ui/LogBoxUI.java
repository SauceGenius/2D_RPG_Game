package ui;

import audio.AudioPlayer;
import core.CollisionBox;
import core.Log;
import core.Position;
import settings.Settings;
import ui.button.ButtonObserver;
import ui.button.CButton;

import java.awt.*;

public class LogBoxUI extends UI implements ButtonObserver {

    private Log log;
    private Dimension dimension = new Dimension(510,200);
    private static final Color BACKGROUND_COLOR = new Color(70,70,70,150);
    private static final Color TEXT_COLOR_TITLE = Color.yellow;;
    private Color TEXT_COLOR_DEFAULT = Color.black;
    private Color colorEXP = Color.blue;
    private Color colorLoot = Color.green;
    private Color colorLevelUp = Color.yellow;
    private static final Font LOG_FONT = new Font("Arial Narrow", Font.BOLD, 20);
    private static final Font LOG_BUTTON_FONT = new Font("TimesRoman", Font.BOLD, 12);
    private boolean logBoxGeneralIsOpen = true;
    private boolean logBoxCombatMyActionsIsOpen = false;
    private boolean isTyping = false;

    public LogBoxUI(Log log){
        opened = true;
        this.log = log;
        position = new Position(20, Settings.WINDOW_HEIGHT - dimension.height - 136);

        initButton();
    }

    @Override
    public void update() {

    }


    @Override
    public void render(Graphics graphics) {

        renderButtons(graphics);

        int logX = position.intX();
        int logY = position.intY();
        int logH = dimension.height;
        int logW = dimension.width;

        /** Grey background **/
        graphics.setColor(BACKGROUND_COLOR);
        graphics.fillRect(logX,logY + 31, logW, logH - 30);
        new Border().render(graphics, new Position(logX, logY + 31), new Dimension(logW, logH - 30));

        /** General **/
        graphics.setFont(LOG_FONT);
        if(logBoxGeneralIsOpen){
            for (int i = 0; i < 7; i++){
                if (log.getGeneral()[i][1] != null){
                    String txt = log.getGeneral()[i][1];

                    graphics.setColor(TEXT_COLOR_DEFAULT);
                    graphics.drawString(txt, logX + 11, logY + logH - 7 - i * 20);

                    if (log.getGeneral()[i][0].equals("Exp")) {graphics.setColor(colorEXP);}
                    else if (log.getGeneral()[i][0].equals("Loot")) {graphics.setColor(colorLoot);}
                    else if (log.getGeneral()[i][0].equals("LevelUp")) {graphics.setColor(colorLevelUp);}

                    graphics.drawString(txt, logX + 10, logY + logH - 8 - i * 20);
                }
            }
        }

        /** Combat log box - My actions **/
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

                    graphics.setColor(TEXT_COLOR_DEFAULT);
                    graphics.drawString(txt, logX + 11, logY + logH - 7 - i * 20);
                    graphics.setColor(Color.white);
                    graphics.drawString(txt, logX + 10, logY + logH - 8 - i * 20);
                }
            }
        }
    }

    private void initButton(){
        //General Button
        int x = position.intX() + 10;
        int y = position.intY();
        int w = 80;
        int h = 30;

        CButton buttonGeneral = new CButton();
        buttonGeneral.setText("General");
        buttonGeneral.setTextColor(TEXT_COLOR_TITLE);
        buttonGeneral.setTextFont(LOG_BUTTON_FONT);
        buttonGeneral.setBackgroundColor(BACKGROUND_COLOR);
        buttonGeneral.setPosition(x,y);
        buttonGeneral.setDimension(w,h);
        buttonGeneral.setSelected(true);
        buttonGeneral.addObserver(this);

        buttons.add(buttonGeneral);

        //Combat Log Button
        x = position.intX() + 98;
        y = position.intY();
        w = 115;
        h = 30;

        CButton buttonCombatLog = new CButton();
        buttonCombatLog.setText("Combat Log");
        buttonCombatLog.setTextColor(TEXT_COLOR_TITLE);
        buttonCombatLog.setTextFont(LOG_BUTTON_FONT);
        buttonCombatLog.setBackgroundColor(BACKGROUND_COLOR);
        buttonCombatLog.setPosition(x,y);
        buttonCombatLog.setDimension(w,h);
        buttonCombatLog.addObserver(this);

        buttons.add(buttonCombatLog);
    }

    private void openGeneral(){
        if(!logBoxGeneralIsOpen){
            logBoxGeneralIsOpen = true;
            buttons.get(0).setSelected(true);
            logBoxCombatMyActionsIsOpen = false;
            buttons.get(1).setSelected(false);
        }
    }

    private void openCombatLog(){
        if(!logBoxCombatMyActionsIsOpen){
            logBoxCombatMyActionsIsOpen = true;
            buttons.get(1).setSelected(true);
            logBoxGeneralIsOpen = false;
            buttons.get(0).setSelected(false);
        }
    }

    @Override
    public void toggle(AudioPlayer audioPlayer) {}


    @Override
    public void notifyButtonClicked(CButton buttonClicked) {
        if(buttonClicked == buttons.get(0)){
            openGeneral();
        } else if(buttonClicked == buttons.get(1)){
            openCombatLog();
        }
    }
}
