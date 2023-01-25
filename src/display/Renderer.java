package display;

import core.Log;
import ui.UIController;
import gameobject.GameObject;
import id.GameObjectID;
import gameobject.Player;
import game.state.State;
import map.Tile;
import settings.Settings;

import java.awt.*;

public class Renderer {

    //For testing
    private boolean devKitEnabled = false;

    private Log log;
    private Player player;
    private UIController uiManager;


    public Renderer(Player player, Log log, UIController uiManager) {
        this.log = log;
        this.player = player;
        this.uiManager = uiManager;
    }

    public void render(State state, Graphics graphics) {
        renderMap(state, graphics);
        renderTargetCircle(state, graphics);
        renderGameObjects(state, graphics);
        renderNamePlates(state, graphics);
        renderHealthBar(state, graphics);
        renderFloatingCombatText(state, graphics);
        uiManager.render(graphics);

        if(devKitEnabled) renderDevKit(state, graphics);
    }

    private void renderMap(State state, Graphics graphics) {
        Tile[][] tiles = state.getGameMap().getTiles();
        Camera camera = state.getCamera();
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                graphics.drawImage(
                        tiles[x][y].getSprite(),
                        x * Settings.SPRITE_SIZE_TILE - camera.getPosition().intX(),
                        y * Settings.SPRITE_SIZE_TILE - camera.getPosition().intY(),
                        null);
            }
        }
    }

    private void renderTargetCircle(State state, Graphics graphics) {
        for (int i = 0; i < state.getGameObjects().size(); i++) {
            GameObject tempObject = state.getGameObjects().get(i);

            //Color of the nameplate
            if (tempObject == (GameObject) player.getTarget()) {
                graphics.setColor(new Color(255,0,0,120));
                graphics.fillRoundRect(
                        tempObject.getHitBox().getBounds().x - state.getCamera().getPosition().intX() + 5,
                        tempObject.getHitBox().getBounds().y - state.getCamera().getPosition().intY() + 50,
                        (int) (tempObject.getHitBox().getBounds().getWidth()/2 + 10),
                        tempObject.getHitBox().getBounds().height/4,
                        20,20);

                graphics.setColor(Color.red);
                graphics.drawRoundRect(
                        tempObject.getHitBox().getBounds().x - state.getCamera().getPosition().intX() + 5,
                        tempObject.getHitBox().getBounds().y - state.getCamera().getPosition().intY() + 50,
                        (int) (tempObject.getHitBox().getBounds().getWidth()/2 + 10),
                        tempObject.getHitBox().getBounds().height/4,
                        20,20);
            }
        }
    }

    private void renderGameObjects(State state, Graphics graphics) {
        state.getGameObjects().forEach(gameObject -> graphics.drawImage(
                gameObject.getSprite(),
                gameObject.getPosition().intX() - state.getCamera().getPosition().intX() - gameObject.getSize().getWidth() / 2,
                gameObject.getPosition().intY() - state.getCamera().getPosition().intY() - gameObject.getSize().getHeight() / 2,
                null));
    }

    private void renderNamePlates(State state, Graphics graphics) {
        String level = "Lvl. ";

        //Finding the target
        GameObject target;
        target = (GameObject) player.getTarget();

        if(player.getTarget() != null){
            target = (GameObject) player.getTarget();
        }

        for (int i = 0; i < state.getGameObjects().size(); i++) {
            GameObject tempObject = state.getGameObjects().get(i);

            //Color of the nameplate
            /*if (tempObject.getGameObjectID() == GameObjectID.player) {
                graphics.setColor(Color.blue);
                graphics.setFont(new Font("FrizQuadrataStd", Font.BOLD, 20));
                graphics.drawString(
                        tempObject.getName(),
                        (int) (tempObject.getPosition().getX() - state.getCamera().getPosition().getX() - 30),
                        (int) (tempObject.getPosition().getY() - state.getCamera().getPosition().getY() - 40));
            }

            else*/ if (tempObject.getGameObjectID() == GameObjectID.NPC) {
                int x = (int) (tempObject.getPosition().getX() - state.getCamera().getPosition().getX() - 10);
                int y = (int) (tempObject.getPosition().getY() - state.getCamera().getPosition().getY() - 45);

                graphics.setFont(new Font("FrizQuadrataStd-Bold", Font.PLAIN, 22));

                //shadow
                graphics.setColor(new Color(0,0,0,175));
                graphics.drawString(tempObject.getName(), x + 2, y - 2);

                if(tempObject.isDead()) {
                    graphics.setColor(Color.gray);
                } else if(player.getStatus().isInCombat() && tempObject == player.getTarget()){
                    graphics.setColor(Color.red);
                } else graphics.setColor(Color.yellow);

                //NPC Name
                graphics.drawString(tempObject.getName(), x, y);
            }
        }
    }

    private void renderHealthBar(State state, Graphics graphics) {
        for (int i = 0; i < state.getGameObjects().size(); i++) {
            GameObject tempObject = state.getGameObjects().get(i);
            GameObject target;
            target = (GameObject) player.getTarget();

            if (tempObject.getGameObjectID() == GameObjectID.NPC && tempObject == target) {
                if(!tempObject.isDead()) {

                    int x = (int) (tempObject.getPosition().getX() - state.getCamera().getPosition().getX() - 40);
                    int y = (int) (tempObject.getPosition().getY() - state.getCamera().getPosition().getY() - 38);
                    int w = 100;
                    int h = 14;
                    int arcW = 8;
                    int arcH = 10;

                    //Health bar
                    graphics.setColor(new Color(60,63,68, 150));
                    graphics.fillRoundRect(x, y, w, h, arcW,arcH);
                    graphics.setColor(Color.yellow);
                    graphics.fillRoundRect(x, y, (int) (tempObject.getCurrentHpValue() / tempObject.getMaxHpValue() * w), h, arcW, arcH);
                    graphics.setColor(Color.black);
                    graphics.drawRoundRect(x, y, w, h,arcW,arcH);

                    //Level panel
                    graphics.setColor(new Color(0,0,0,175));
                    graphics.setColor(new Color(25,65,200));
                    graphics.fillRoundRect(x + 88 + 10, y - 2, 29, h + 4, arcW,arcH);

                    graphics.setColor(Color.yellow);;
                    graphics.fillRoundRect(x + 90 + 10, y, 25, h, arcW,arcH);

                    graphics.setColor(new Color(175,150,0));;
                    graphics.fillRoundRect(x + 91 + 10, y + 1, 23, h-2, arcW,arcH);

                    //Level text
                    graphics.setColor(Color.black);
                    graphics.setFont(new Font("TimesRoman", Font.BOLD, 14));
                    graphics.drawString(Integer.toString(tempObject.getLevel()), x + 1 + w + 9, y + 12);

                    graphics.setColor(Color.yellow);
                    graphics.setFont(new Font("TimesRoman", Font.BOLD, 14));
                    graphics.drawString(Integer.toString(tempObject.getLevel()), x + 1 + w + 9 - 1, y + 11);
                }
            }
        }
    }

    private void renderFloatingCombatText(State state, Graphics graphics) {
        int x;
        int y;

        //Auto attack critical damage floating text
        if (log.getCriticalDamageFloatingText()[0][0] != null && log.getCriticalDamageFloatingText()[0][4].equals("Critical"))
        {
            x = Integer.parseInt(log.getCriticalDamageFloatingText()[0][2]) - state.getCamera().getPosition().intX() + 10;
            y = Integer.parseInt(log.getCriticalDamageFloatingText()[0][3]) - state.getCamera().getPosition().intY() - 45;

            graphics.setFont(new Font("TimesRoman", Font.BOLD, 40));
            if (Integer.parseInt(log.getCriticalDamageFloatingText()[0][5]) >= 10) {
                x -= 15;
            }
            graphics.setColor(new Color(0,0,0,175));
            graphics.drawString(log.getCriticalDamageFloatingText()[0][5], x + 2, y - 2);
            graphics.setColor(Color.white);
            graphics.drawString(log.getCriticalDamageFloatingText()[0][5], x, y);

        }

        //Auto attack normal damage floating text
        for(int i = 0; i < log.getDamageFloatingText().length; i++) {
            if (log.getDamageFloatingText()[i] != null && log.getDamageFloatingText()[i][2] != null && log.getDamageFloatingText()[i][3] != null && log.getDamageFloatingText()[i][5] != null){

                x = Integer.parseInt(log.getDamageFloatingText()[i][2]) - state.getCamera().getPosition().intX() + 14;
                y = Integer.parseInt(log.getDamageFloatingText()[i][3]) - state.getCamera().getPosition().intY() - 35;

                graphics.setFont(new Font("TimesRoman", Font.BOLD, 26));
                graphics.setColor(new Color(0,0,0,175));
                graphics.drawString(log.getDamageFloatingText()[i][5], x + 2, y - i - 2);
                graphics.setColor(Color.white);
                graphics.drawString(log.getDamageFloatingText()[i][5], x, y - i);
            }
        }

        if(log.getDamageFloatingTextTimer().timeIsUp()){log.clearDamageFloatingText();}
    }

    // Dev mode //
    private void renderDevKit(State state, Graphics graphics) {
        Camera camera = state.getCamera();
        renderHitBox(camera, state, graphics);
        renderCollisionBox(camera, state, graphics);
        renderDetectionBox(camera, state, graphics);
        renderInteractionBox(camera, graphics);
        renderCombatInfo(graphics);
        renderMousePointer(camera, graphics);
    }

    private void renderHitBox(Camera camera, State state, Graphics graphics) {
        for (int i = 0; i < state.getGameObjects().size(); i++) {
            GameObject tempObject = state.getGameObjects().get(i);

            //Color of the nameplate
            if (tempObject.getGameObjectID() == GameObjectID.NPC) {
                graphics.setColor(Color.red);
                graphics.drawRect(
                        tempObject.getHitBox().getBounds().x - camera.getPosition().intX(),
                        tempObject.getHitBox().getBounds().y - camera.getPosition().intY(),
                        (int) tempObject.getHitBox().getBounds().getWidth(),
                        tempObject.getHitBox().getBounds().height);
            }
        }
    }

    private void renderDetectionBox(Camera camera, State state, Graphics graphics) {
        for (int i = 0; i < state.getGameObjects().size(); i++) {
            GameObject tempObject = state.getGameObjects().get(i);

            //Color of the nameplate
            if (tempObject.getGameObjectID() == GameObjectID.NPC) {
                graphics.setColor(Color.orange);
                graphics.drawRect(
                        tempObject.getDetectionBox().getBounds().x - camera.getPosition().intX(),
                        tempObject.getDetectionBox().getBounds().y - camera.getPosition().intY(),
                        (int) tempObject.getDetectionBox().getBounds().getWidth(),
                        tempObject.getDetectionBox().getBounds().height);
            }
        }
    }

    private void renderInteractionBox(Camera camera, Graphics graphics){
        graphics.setColor(Color.blue);
        graphics.drawRect(player.getInteractionBox().getBounds().x - camera.getPosition().intX(),
                player.getInteractionBox().getBounds().y-camera.getPosition().intY(),
                player.getInteractionBox().getBounds().width,
                player.getInteractionBox().getBounds().height);
    }

    private void renderCollisionBox(Camera camera, State state ,Graphics graphics){
        graphics.setColor(Color.gray);
        state.getGameObjects().forEach(gameObject -> graphics.drawRect(gameObject.getCollisionBox().getBounds().x - camera.getPosition().intX(),
                gameObject.getCollisionBox().getBounds().y - camera.getPosition().intY(),
                gameObject.getCollisionBox().getBounds().width,
                gameObject.getCollisionBox().getBounds().height));
    }

    private void renderCombatInfo(Graphics graphics){
        int x = 20;
        int y = 900;
        int spacing = 15;

        graphics.setFont(new Font("Times New Roman", Font.BOLD, 12));
        graphics.setColor(Color.darkGray);
        graphics.fillRect(x, y, 170, 120);

        graphics.setColor(Color.white);
        graphics.drawString("In combat: " + player.getStatus().isInCombat(), x + 10, y + 20);
        graphics.drawString("Target in reach: " + player.getStatus().hasTargetInReach(), x + 10, y + spacing + 20);
        graphics.drawString("Auto attacking: " + player.getStatus().isAutoAttacking(), x + 10, y + spacing * 2 + 20);
        graphics.drawString("Playing att animation: " + player.getAnimationManager().isPlayingAutoAttackAnimation(), x + 10, y + spacing * 3 + 20);

        graphics.drawString("Auto attack timer up: " + player.getAutoAttackTimer().timeIsUp(), x + 10, y + spacing * 5 + 20);
        graphics.drawString("Auto attack cooldown: " + (int)(player.getAutoAttackTimer().getSecondsCountDown()), x + 10, y + spacing * 6 + 20);
    }

    private void renderMousePointer(Camera camera, Graphics graphics) {
        graphics.setColor(Color.gray);
        graphics.drawRect(player.getPlayerController().getMousePosition().intX() - 10 - camera.getPosition().intX(),
                player.getPlayerController().getMousePosition().intY() - 10 - camera.getPosition().intY(),
                20, 20);
    }
}
