package mainFrame.cursormanager;

import character.Character;
import gameobject.GameObject;
import gameobject.LivingObject;
import gameobject.NPC;
import id.GameObjectID;
import mainFrame.MainFrame;

import java.awt.*;
import java.util.List;

public class CursorManagerGameState extends CursorManager{
    public static final int INDEX_ATTACK = 2;
    public static final int INDEX_CAST = 3;
    public static final int INDEX_LOOT = 4;
    public static final int INDEX_LOOT_OUT_OF_RANGE = 5;
    public static final int INDEX_INTERACT = 6;
    public static final int INDEX_FLIGHT = 7;

    private Cursor attackCursor;
    private Cursor castNoTargetCursor;
    private Cursor lootCursor;
    private Cursor lootOutOfRangerCursor;
    private Cursor interactCursor;
    private Cursor flightCursor;
    private Character character;

    public CursorManagerGameState(MainFrame mainFrame, Character character){
        super(mainFrame);
        this.character = character;
        changeCursor(CursorManager.INDEX_DEFAULT);
    }

    public void update(List<GameObject> mouseOverObjects){
        if(mouseOverObjects.size() > 0){
            mouseOverObjects.forEach(mouseOverObject -> {
                if(mouseOverObject instanceof NPC && !((LivingObject)mouseOverObject).isDead()) {
                    changeCursor(INDEX_ATTACK);
                } else if(mouseOverObject instanceof NPC && ((LivingObject)mouseOverObject).isDead() && !((LivingObject)mouseOverObject).hasBeenLooted()){
                    if(character.getGameObject().meleeRangeCollidesWith(mouseOverObject)){
                        changeCursor(INDEX_LOOT);
                    } else changeCursor(INDEX_LOOT_OUT_OF_RANGE);
                } else changeCursor(INDEX_DEFAULT);
            });
        } else changeCursor(CursorManager.INDEX_DEFAULT);
    }

    public void changeCursor(int cursorType) {
        switch (cursorType) {
            case INDEX_DEFAULT -> mainFrame.setCursor(defaultCursor);
            case INDEX_DEFAULT_CLICKED -> mainFrame.setCursor(defaultClickedCursor);
            case INDEX_ATTACK -> mainFrame.setCursor(attackCursor);
            case INDEX_CAST -> mainFrame.setCursor(castNoTargetCursor);
            case INDEX_LOOT -> mainFrame.setCursor(lootCursor);
            case INDEX_LOOT_OUT_OF_RANGE -> mainFrame.setCursor(lootOutOfRangerCursor);
            case INDEX_INTERACT -> mainFrame.setCursor(interactCursor);
            case INDEX_FLIGHT -> mainFrame.setCursor(flightCursor);
        }
    }

    public void initCursors(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        //Default Cursor
        Image image = toolkit.getImage("resources/sprites/ui/DefaultCursor.png");
        defaultCursor = toolkit.createCustomCursor(image,new Point(0,0),"defaultCursor");
        image = toolkit.getImage("resources/sprites/ui/DefaultCursor.png");
        defaultClickedCursor = toolkit.createCustomCursor(image,new Point(0,0),"defaultCursor");
        //Attack Cursor
        image = toolkit.getImage("resources/sprites/ui/AttackCursor.png");
        attackCursor = toolkit.createCustomCursor(image,new Point(0,0),"attackCursor");
        //Cast no target Cursor
        image = toolkit.getImage("resources/sprites/ui/CastNoTargetCursor.png");
        castNoTargetCursor = toolkit.createCustomCursor(image,new Point(0,0),"castNoTargetCursor");
        //Loot Cursor
        image = toolkit.getImage("resources/sprites/ui/LootCursor.png");
        lootCursor = toolkit.createCustomCursor(image,new Point(0,0),"lootCursor");
        //Loot out of range Cursor
        image = toolkit.getImage("resources/sprites/ui/LootOutOfRangeCursor.png");
        lootOutOfRangerCursor = toolkit.createCustomCursor(image,new Point(0,0),"lootOutOfRangeCursor");
        //Interact Cursor
        image = toolkit.getImage("resources/sprites/ui/InteractCursor.png");
        interactCursor = toolkit.createCustomCursor(image,new Point(0,0),"interactCursor");
        //Flight Cursor
        image = toolkit.getImage("resources/sprites/ui/FlightCursor.png");
        flightCursor = toolkit.createCustomCursor(image,new Point(0,0),"flightCursor");
    }
}
