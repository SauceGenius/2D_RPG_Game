package display;

import core.Log;
import core.Position;
import core.Size;
import display.ui.InventoryUI;
import display.ui.LogBoxUI;
import display.ui.UI;
import entity.GameObject;
import entity.GameObjectID;
import entity.Player;
import game.Game;
import game.state.State;
import map.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Renderer {

    private Log log;
    private Player player;
    private ArrayList<UI> uis;


    public Renderer(Player player, Log log, ArrayList<UI> uis) {
        this.log = log;
        this.player = player;
        this.uis = uis;
    }

    public void render(State state, Graphics graphics) {
        Camera camera = state.getCamera();
        renderMap(state, graphics);

        renderTargetCircle(state, graphics);
        renderGameObjects(state, graphics);
        renderNamePlates(state, graphics);
        renderHealthBar(state, graphics);
        renderFloatingCombatText(state, graphics);
        renderInteractionOption(state, graphics);

        renderUI(state, graphics);

        //renderDevKit(state, graphics);
    }

    private void renderMap(State state, Graphics graphics) {
        Tile[][] tiles = state.getGameMap().getTiles();
        Camera camera = state.getCamera();
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                graphics.drawImage(
                        tiles[x][y].getSprite(),
                        x * Game.SPRITE_SIZE_TILE - camera.getPosition().intX(),
                        y * Game.SPRITE_SIZE_TILE - camera.getPosition().intY(),
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
                } else {
                    graphics.setColor(Color.yellow);
                }

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

    private void renderInteractionOption(State state, Graphics graphics) {
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 12));
        for (int i = 0; i < state.getGameObjects().size(); i++) {
            GameObject tempObject = state.getGameObjects().get(i);
            if (tempObject.getGameObjectID() == GameObjectID.NPC) {
                if(tempObject.isDead() == true && tempObject.isInReach() == true) {
                    graphics.setColor(Color.gray);
                    graphics.fillRect(
                            (int) (tempObject.getPosition().getX() - state.getCamera().getPosition().getX()) + 5,
                            (int) (tempObject.getPosition().getY() - state.getCamera().getPosition().getY() - 12),
                            36, 14);
                    graphics.setColor(Color.black);
                    graphics.drawRect(
                            (int) (tempObject.getPosition().getX() - state.getCamera().getPosition().getX() + 5),
                            (int) (tempObject.getPosition().getY() - state.getCamera().getPosition().getY() - 12),
                            36, 14);

                    graphics.setColor(Color.white);
                    graphics.drawString("Loot",
                            (int)(tempObject.getPosition().getX() - state.getCamera().getPosition().getX() + 10),
                            (int)(tempObject.getPosition().getY() - state.getCamera().getPosition().getY()));
                }
            }
        }
    }

    // UI //
    private void renderUI(State state, Graphics graphics) {
        renderEquipment(graphics);
        renderUnitFrames(state, graphics);
        renderExpBar(graphics);
        renderSpellBar(graphics);

        LogBoxUI logBoxUI = (LogBoxUI) uis.get(0);
        logBoxUI.render(graphics, log);
        InventoryUI bagUI = (InventoryUI) uis.get(1);
        bagUI.render(graphics);
    }

    private void renderEquipment(Graphics graphics) {
        if (player.getEquipment().isOpen())
        {
            Position equipmentPosition = new Position(20,200);
            Size equipmentSize = new Size(310, 390);

            //Background
            graphics.setColor(new Color(70, 70, 70));
            graphics.fillRect(equipmentPosition.intX(), equipmentPosition.intY(), equipmentSize.getWidth(), equipmentSize.getHeight());
            graphics.setColor(new Color(0, 0, 0));
            graphics.drawRect(equipmentPosition.intX(), equipmentPosition.intY(), equipmentSize.getWidth(), equipmentSize.getHeight());

            //Equipment top
            graphics.setColor(new Color(70, 70, 70));
            graphics.fillRect( equipmentPosition.intX() + 37, equipmentPosition.intY() + 5, equipmentSize.getWidth() - 72, 19);
            graphics.setColor(Color.black);
            graphics.drawRect(equipmentPosition.intX() + 37, equipmentPosition.intY() + 5, equipmentSize.getWidth() - 72, 19);

            graphics.setColor(Color.white);
            graphics.setFont(new Font("TimesRoman", Font.BOLD, 12));
            graphics.drawString(player.getName(),  equipmentPosition.intX() + 120, equipmentPosition.intY() + 18);
            graphics.setColor(Color.yellow);
            graphics.setFont(new Font("TimesRoman", Font.BOLD, 11));
            graphics.drawString("Level " + player.getLevel() + " Human Warrior",  equipmentPosition.intX() + 100, equipmentPosition.intY() + 36);

            //Stats name
            graphics.setColor(Color.yellow);
            graphics.setFont(new Font("TimesRoman", Font.BOLD, 11));
            graphics.drawString("Strength: ",equipmentPosition.intX() + 50, equipmentPosition.intY() + 270);
            graphics.drawString("Agility: ",equipmentPosition.intX() + 50, equipmentPosition.intY() + 284);
            graphics.drawString("Stamina: ",equipmentPosition.intX() + 50, equipmentPosition.intY() + 298);
            graphics.drawString("Intellect:  ",equipmentPosition.intX() + 50, equipmentPosition.intY() + 312);
            graphics.drawString("Spirit: ",equipmentPosition.intX() + 50, equipmentPosition.intY() + 326);
            graphics.drawString("Armor: ",equipmentPosition.intX() + 50, equipmentPosition.intY() + 340);

            graphics.drawString("Melee Attack: ",equipmentPosition.intX() + 160, equipmentPosition.intY() + 270);
            graphics.drawString("Power: ",equipmentPosition.intX() + 164, equipmentPosition.intY() + 284);
            graphics.drawString("Damage: ",equipmentPosition.intX() + 164, equipmentPosition.intY() + 298);

            //Stats value
            graphics.setColor(Color.white);
            graphics.setFont(new Font("TimesRoman", Font.BOLD, 11));
            graphics.drawString(Integer.toString(player.getStats().getStat("strength")),equipmentPosition.intX() + 135, equipmentPosition.intY() + 270);
            graphics.drawString(Integer.toString(player.getStats().getStat("agility")),equipmentPosition.intX() + 135, equipmentPosition.intY() + 284);
            graphics.drawString(Integer.toString(player.getStats().getStat("stamina")),equipmentPosition.intX() + 135, equipmentPosition.intY() + 298);
            graphics.drawString(Integer.toString(player.getStats().getStat("intellect")),equipmentPosition.intX() + 135, equipmentPosition.intY() + 312);
            graphics.drawString(Integer.toString(player.getStats().getStat("spirit")),equipmentPosition.intX() + 135, equipmentPosition.intY() + 326);
            graphics.drawString(Integer.toString(player.getStats().getArmor()),equipmentPosition.intX() + 135, equipmentPosition.intY() + 340);

            graphics.drawString("Skill",equipmentPosition.intX() + 245, equipmentPosition.intY() + 270);
            graphics.drawString(Long.toString(Math.round(player.getStats().getMeleeAttackPower())),equipmentPosition.intX() + 255, equipmentPosition.intY() + 284);
            graphics.drawString((int)(player.getStats().getMinMeleeWeaponDamage() + player.getStats().getMeleeAttackPower()/14*player.getStats().getAttackSpeed()) +
                            " - " + Math.round(player.getStats().getMaxMeleeWeaponDamage() + player.getStats().getMeleeAttackPower()/14*player.getStats().getAttackSpeed())
                    ,equipmentPosition.intX() + 245, equipmentPosition.intY() + 298);

            // Each space
            for (int x = 0; x < 2; x++) {
                for (int y = 0; y < 8; y++) {
                    graphics.setColor(new Color(70, 70, 70));
                    graphics.fillRect(x * 268 + equipmentPosition.intX() + 5, y * 36 + equipmentPosition.intY() + 70, 32, 32);
                    graphics.setColor(new Color(0, 0, 0));
                    graphics.drawRect(x * 268 + equipmentPosition.intX() + 5, y * 36 + equipmentPosition.intY() + 70, 32, 32);
                    graphics.setColor(Color.white);
                    graphics.drawString(Integer.toString(x + y * 2 + 3), x * 268 + equipmentPosition.intX()+ 5, y * 36 + equipmentPosition.intY() + 102);
                    if (player.getEquipment().getEquipment()[(x + y * 2 + 3)] != null) {
                        graphics.drawImage(player.getEquipment().getItem(x + y * 2 + 3).getIconSprite(), x * 268 + equipmentPosition.intX() + 5, y * 32 + equipmentPosition.intY() + 70, null);
                    }
                }
            }

            graphics.setColor(new Color(70, 70, 70));
            graphics.fillRect(equipmentPosition.intX() + 100, equipmentPosition.intY() + 350, 32, 32);
            graphics.setColor(new Color(0, 0, 0));
            graphics.drawRect(equipmentPosition.intX() + 100, equipmentPosition.intY() + 350, 32, 32);
            graphics.drawRect(equipmentPosition.intX() + 136, equipmentPosition.intY() + 350, 32, 32);
            graphics.drawRect(equipmentPosition.intX() + 172, equipmentPosition.intY() + 350, 32, 32);
            if(player.getEquipment().getItem(0) != null){
                graphics.drawImage(player.getEquipment().getItem(0).getIconSprite(), equipmentPosition.intX() + 100, equipmentPosition.intY() + 350, null);
            }
            if(player.getEquipment().getItem(1) != null){
                graphics.drawImage(player.getEquipment().getItem(0).getIconSprite(), equipmentPosition.intX() + 134, equipmentPosition.intY() + 350, null);
            }
            if(player.getEquipment().getItem(2) != null){
                graphics.drawImage(player.getEquipment().getItem(0).getIconSprite(), equipmentPosition.intX() + 138, equipmentPosition.intY() + 350, null);
            }
        }
    }

    private void renderUnitFrames(State state, Graphics graphics) {
        int x = 20; int y = 30; int w = 150; int h = 10;
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 11));

        //Player
        graphics.setColor(new Color(70,70,70, 150));
        graphics.fillArc(x,y - 10,60,60,360,360);
        graphics.setColor(Color.black);
        graphics.drawArc(x,y - 10,60,60,360,360);
        graphics.setColor(new Color(70,70,70,150));
        graphics.fillRect(x + 60,y,w,40);
        graphics.setColor(Color.yellow);
        graphics.drawString(player.getName(), x + 100, y + 14);
        graphics.setColor(Color.green);
        graphics.fillRect(x + 60,y + 20,(int)(player.getStats().getCurrentHpValue()/player.getStats().getMaxHpValue() * w), h);
        graphics.setColor(Color.black);
        graphics.drawRect(x + 60,y,w,40);
        graphics.drawRect(x + 60,y + 20,w,10);
        graphics.setColor(Color.black);
        graphics.drawString(Integer.toString((int)player.getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)player.getMaxHpValue())),60 + 79,y + 29);
        graphics.drawString(Integer.toString((int)player.getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)player.getMaxHpValue())),60 + 81,y + 29);
        graphics.drawString(Integer.toString((int)player.getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)player.getMaxHpValue())),60 + 79,y + 31);
        graphics.drawString(Integer.toString((int)player.getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)player.getMaxHpValue())),60 + 81,y + 31);
        graphics.setColor(Color.white);
        graphics.drawString(Integer.toString((int)player.getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)player.getMaxHpValue())),60 + 80,y + 30);

        BufferedImage bufferedImage = (BufferedImage) state.getSpriteLibrary().getUnit("player").get("Idle");
        graphics.drawImage(bufferedImage.getSubimage(15,298,96,48).getScaledInstance(127,64,1),x - 10,y - 14,null);

        graphics.setColor(new Color(70,70,70, 230));
        graphics.fillArc(x,y + 30,20,20,360,360);
        graphics.setColor(Color.black);
        graphics.drawArc(x,y + 30,20,20,360,360);
        graphics.setColor(Color.yellow);
        graphics.drawString(Integer.toString(player.getLevel()), x + 8, y + 45);;

        //Target
        if(player.getTarget() != null){
            graphics.setColor(new Color(70,70,70,150));
            graphics.fillRect(x + w + 120,y,w,40);
            graphics.setColor(Color.yellow);
            graphics.drawString(player.getTarget().getName(), x + w + 180, y + 14);
            graphics.setColor(Color.green);
            graphics.fillRect(x + w + 120,y + 20,(int)(player.getTarget().getStats().getCurrentHpValue()/player.getTarget().getStats().getMaxHpValue() * w), h);
            graphics.setColor(Color.black);
            graphics.drawRect(x + w + 120,y,w,40);
            graphics.drawRect(x + w + 120,y + 20,w,10);
            if(!player.getTarget().isDead()) {
                graphics.setColor(Color.black);
                graphics.drawString(Integer.toString((int)player.getTarget().getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)player.getTarget().getMaxHpValue())),x + w + 179,y + 29);
                graphics.drawString(Integer.toString((int)player.getTarget().getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)player.getTarget().getMaxHpValue())),x + w + 179,y + 31);
                graphics.drawString(Integer.toString((int)player.getTarget().getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)player.getTarget().getMaxHpValue())),x + w + 181,y + 29);
                graphics.drawString(Integer.toString((int)player.getTarget().getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)player.getTarget().getMaxHpValue())),x + w + 181,y + 31);
                graphics.setColor(Color.white);
                graphics.drawString(Integer.toString((int)player.getTarget().getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)player.getTarget().getMaxHpValue())),x + w + 180,y + 30);
            } else {
                graphics.setColor(Color.black);
                graphics.drawString("Dead",x + w + 179,y + 29);
                graphics.drawString("Dead",x + w + 179,y + 31);
                graphics.drawString("Dead",x + w + 181,y + 29);
                graphics.drawString("Dead",x + w + 181,y + 31);
                graphics.setColor(Color.yellow);
                graphics.drawString("Dead",x + w + 180,y + 30);
            }

            graphics.setColor(new Color(70,70,70, 150));
            graphics.fillArc(x + 421,y - 10,60,60,360,360);
            graphics.setColor(Color.black);
            graphics.drawArc(x + 421,y - 10,60,60,360,360);
            graphics.setColor(new Color(70,70,70, 230));
            graphics.fillArc(x + 461,y + 30,20,20,360,360);
            graphics.setColor(Color.black);
            graphics.drawArc(x + 461,y + 30,20,20,360,360);
            graphics.setColor(Color.yellow);
            graphics.drawString(Integer.toString(player.getTarget().getLevel()), x + 469, y + 45);
        }
    }

    private void renderExpBar(Graphics graphics) {
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

    private void renderSpellBar(Graphics graphics) {
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
