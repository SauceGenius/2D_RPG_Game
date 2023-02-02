package mainFrame;

import character.Character;
import core.Log;
import game.state.GameState;
import game.state.MainMenuState;
import gameobject.LivingObject;
import gameobject.NPC;
import login.AccountController;
import ui.UIController;
import gameobject.GameObject;
import id.GameObjectID;
import gameobject.Player;
import game.state.State;
import map.Tile;
import settings.Settings;

import java.awt.*;

public class Renderer {

    /** Developer Kit for testing**/
    private boolean devKitEnabled = false;

    private Camera camera;
    private Log log;
    private Player player;


    public Renderer(Log log) {
        this.log = log;
    }

    public void update(State currentState){
        if(currentState instanceof GameState){
            for (int i = 0; i < ((GameState) currentState).getCharacters().size(); i++) {
                GameObject tempObject = ((GameState) currentState).getCharacters().get(i).getGameObject();
                if(tempObject == player){
                    player = (Player)tempObject;
                }
            }

            camera = ((GameState)currentState).getCamera();
        }
    }

    public void render(State currentState, Graphics graphics) {
        if(currentState instanceof GameState && ((GameState)currentState).getCharacters().get(0).getGameObject() != null && player == null){
            player = (Player) ((GameState)currentState).getCharacters().get(0).getGameObject();
        }
        if(currentState instanceof GameState && player != null){
            renderMap(currentState, graphics);
            renderTargetCircle(currentState, graphics);
            renderGameObjects(currentState, graphics);
            renderNamePlates(currentState, graphics);
            renderHealthBar(currentState, graphics);
            renderFloatingCombatText(graphics);
            ((GameState)currentState).getUiController().render(graphics);
            if(devKitEnabled) renderDevKit(currentState, graphics);
        } else if(currentState instanceof MainMenuState){

        }
    }

    private void renderMap(State currentState, Graphics graphics) {
        Tile[][] tiles = currentState.getGameMap().getTiles();
        Camera camera = ((GameState)currentState).getCamera();
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

    private void renderTargetCircle(State currentState, Graphics graphics) {
        for (int i = 0; i < currentState.getGameObjects().size(); i++) {
            GameObject tempObject = currentState.getGameObjects().get(i);

            //Color of the target circle
            if (tempObject == (GameObject) player.getTarget()) {
                graphics.setColor(new Color(255,0,0,120));
                graphics.fillRoundRect(
                        tempObject.getPosition().intX() - camera.getPosition().intX() - tempObject.getSize().getWidth() / 4,
                        tempObject.getPosition().intY() - camera.getPosition().intY() + tempObject.getSize().getHeight() / 4,
                        tempObject.getSize().getWidth() / 2,
                        tempObject.getSize().getHeight() / 4,
                        180,180);

                graphics.setColor(Color.red);
                graphics.drawRoundRect(
                        tempObject.getPosition().intX() - camera.getPosition().intX() - tempObject.getSize().getWidth() / 4,
                        tempObject.getPosition().intY() - camera.getPosition().intY() + tempObject.getSize().getHeight() / 4,
                        tempObject.getSize().getWidth() / 2,
                        tempObject.getSize().getHeight() / 4,
                        180,180);
            }
        }
    }

    private void renderGameObjects(State currentState, Graphics graphics) {
        currentState.getGameObjects().forEach(gameObject -> graphics.drawImage(
                gameObject.getSprite(),
                gameObject.getPosition().intX() - camera.getPosition().intX() - gameObject.getSize().getWidth() / 2,
                gameObject.getPosition().intY() - camera.getPosition().intY() - gameObject.getSize().getHeight() / 2,
                null));
    }

    private void renderNamePlates(State state, Graphics graphics) {

        for (int i = 0; i < state.getGameObjects().size(); i++) {
            GameObject tempObject = state.getGameObjects().get(i);
            Font namePlateFont = new Font("FrizQuadrataStd", Font.BOLD, 20);
            graphics.setFont(namePlateFont);

            if (tempObject instanceof NPC) {
                FontMetrics metrics = graphics.getFontMetrics(namePlateFont);
                int nameTextWidth = metrics.stringWidth(tempObject.getName());

                int x = (int) (tempObject.getPosition().getX() - camera.getPosition().getX() - nameTextWidth/2);
                int y = (int) (tempObject.getPosition().getY() - camera.getPosition().getY() - tempObject.getSize().getHeight() / 2 - 22);

                //graphics.setFont(new Font("FrizQuadrataStd-Bold", Font.PLAIN, 22));

                /** Shadow **/
                graphics.setColor(new Color(0,0,0,175));
                graphics.drawString(tempObject.getName(), x + 2, y - 2);

                /** Color of the nameplate **/
                if(((LivingObject)tempObject).isDead()) {
                    graphics.setColor(Color.gray);
                } else if(((LivingObject)tempObject).getStatus().isAggressiveTowardTarget()){
                    graphics.setColor(Color.red);
                } else graphics.setColor(Color.yellow);

                /** NPC Name String **/
                graphics.drawString(tempObject.getName(), x, y);
            }
        }
    }

    private void renderHealthBar(State state, Graphics graphics) {
        for (int i = 0; i < state.getGameObjects().size(); i++) {
            GameObject tempObject = state.getGameObjects().get(i);
            GameObject target;
            target = player.getTarget();

            if (tempObject instanceof NPC && tempObject == target) {
                if(!((LivingObject)tempObject).isDead()) {

                    int w = 100;
                    int h = 14;
                    int x = (int) (tempObject.getPosition().getX() - camera.getPosition().getX() - ((w + 26) / 2));
                    int y = (int) (tempObject.getPosition().getY() - camera.getPosition().getY() - tempObject.getSize().getHeight() / 2 - h - 2);
                    int arcW = 8;
                    int arcH = 10;

                    //Health bar
                    graphics.setColor(new Color(60,63,68, 150));
                    graphics.fillRoundRect(x, y, w, h, arcW,arcH);
                    if (((LivingObject)tempObject).getStatus().isAggressiveTowardTarget()){
                        graphics.setColor(Color.red);
                    } else {
                        graphics.setColor(Color.yellow);
                    }
                    graphics.fillRoundRect(x, y, (int) (((LivingObject)tempObject).getStats().getCurrentHpValue() / ((LivingObject)tempObject).getStats().getMaxHpValue() * w), h, arcW, arcH);
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
                    graphics.drawString(Integer.toString(((LivingObject)tempObject).getLevel()), x + 1 + w + 9, y + 12);

                    graphics.setColor(Color.yellow);
                    graphics.setFont(new Font("TimesRoman", Font.BOLD, 14));
                    graphics.drawString(Integer.toString(((LivingObject)tempObject).getLevel()), x + 1 + w + 9 - 1, y + 11);
                }
            }
        }
    }

    private void renderFloatingCombatText(Graphics graphics) {
        int x;
        int y;

        //Auto attack critical damage floating text
        if (log.getCriticalDamageFloatingText()[0][0] != null && log.getCriticalDamageFloatingText()[0][4].equals("Critical"))
        {
            x = Integer.parseInt(log.getCriticalDamageFloatingText()[0][2]) - camera.getPosition().intX() - 10;
            y = Integer.parseInt(log.getCriticalDamageFloatingText()[0][3]) - camera.getPosition().intY() - 55;

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

                x = Integer.parseInt(log.getDamageFloatingText()[i][2]) - camera.getPosition().intX() - 8;
                y = Integer.parseInt(log.getDamageFloatingText()[i][3]) - camera.getPosition().intY() - 45;

                graphics.setFont(new Font("TimesRoman", Font.BOLD, 26));
                graphics.setColor(new Color(0,0,0,175));
                graphics.drawString(log.getDamageFloatingText()[i][5], x + 2, y - i - 2);
                graphics.setColor(Color.white);
                graphics.drawString(log.getDamageFloatingText()[i][5], x, y - i);
            }
        }

        if(log.getDamageFloatingTextTimer().timeIsUp()){log.clearDamageFloatingText();}
    }

    /** Dev mode **/
    private void renderDevKit(State currentState, Graphics graphics) {
        renderPosition(currentState, graphics);
        renderHitBox(currentState, graphics);
        renderCollisionBox(currentState, graphics);
        renderRangedRangeBox(currentState, graphics);
        renderDetectionBox(currentState, graphics);
        renderInteractionBox(graphics);
        renderCombatInfo(graphics);
        renderMousePointer(graphics);
    }

    private void renderPosition(State currentState, Graphics graphics){
        graphics.setColor(Color.red);

        for (int i = 0; i < currentState.getGameObjects().size(); i++) {
            GameObject tempObject = currentState.getGameObjects().get(i);
            graphics.fillRect(tempObject.getPosition().intX() - camera.getPosition().intX(), tempObject.getPosition().intY() - camera.getPosition().intY(), 5 ,5);

        }
    }

    private void renderHitBox(State currentState, Graphics graphics) {
        for (int i = 0; i < currentState.getGameObjects().size(); i++) {
            GameObject tempObject = currentState.getGameObjects().get(i);

            //Color of the nameplate
            if (tempObject instanceof NPC) {
                graphics.setColor(Color.red);
                graphics.drawRect(
                        tempObject.getHitBox().getBounds().x - camera.getPosition().intX(),
                        tempObject.getHitBox().getBounds().y - camera.getPosition().intY(),
                        (int) tempObject.getHitBox().getBounds().getWidth(),
                        tempObject.getHitBox().getBounds().height);
            }
        }
    }

    private void renderRangedRangeBox(State currentState, Graphics graphics) {
        for (int i = 0; i < currentState.getGameObjects().size(); i++) {
            GameObject tempObject = currentState.getGameObjects().get(i);

            //Color of the nameplate
            if (tempObject instanceof NPC && ((NPC) tempObject).getStatus().isRanged()) {
                graphics.setColor(Color.pink);
                graphics.drawRect(
                        ((NPC) tempObject).getRangedRangeBox().getBounds().x - camera.getPosition().intX(),
                        ((NPC) tempObject).getRangedRangeBox().getBounds().y - camera.getPosition().intY(),
                        (int) ((NPC) tempObject).getRangedRangeBox().getBounds().getWidth(),
                        ((NPC) tempObject).getRangedRangeBox().getBounds().height);
            }
        }
    }

    private void renderDetectionBox(State currentState, Graphics graphics) {
        for (int i = 0; i < currentState.getGameObjects().size(); i++) {
            GameObject tempObject = currentState.getGameObjects().get(i);

            //Color of the nameplate
            if (tempObject instanceof NPC) {
                graphics.setColor(Color.orange);
                graphics.drawRect(
                        tempObject.getDetectionBox().getBounds().x - camera.getPosition().intX(),
                        tempObject.getDetectionBox().getBounds().y - camera.getPosition().intY(),
                        (int) tempObject.getDetectionBox().getBounds().getWidth(),
                        tempObject.getDetectionBox().getBounds().height);
            }
        }
    }

    private void renderInteractionBox(Graphics graphics){
        graphics.setColor(Color.blue);
        graphics.drawRect(player.getInteractionBox().getBounds().x - camera.getPosition().intX(),
                player.getInteractionBox().getBounds().y-camera.getPosition().intY(),
                player.getInteractionBox().getBounds().width,
                player.getInteractionBox().getBounds().height);
    }

    private void renderCollisionBox(State state ,Graphics graphics){
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

    private void renderMousePointer(Graphics graphics) {
        graphics.setColor(Color.gray);
        graphics.drawRect(player.getPlayerController().getMousePosition().intX() - 2 - camera.getPosition().intX(),
                player.getPlayerController().getMousePosition().intY() - 2 - camera.getPosition().intY(),
                4, 4);
    }
}
