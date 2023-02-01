package mainFrame.cursormanager;

import gameobject.GameObject;
import id.GameObjectID;
import mainFrame.MainFrame;

import java.awt.*;
import java.util.List;

public class CursorManagerGameState extends CursorManager{
    public static final int INDEX_ATTACK = 2;
    public static final int INDEX_CAST = 3;
    public static final int INDEX_LOOT = 4;
    public static final int INDEX_INTERACT = 5;
    public static final int INDEX_FLIGHT = 6;

    private Cursor attackCursor;
    private Cursor castNoTargetCursor;
    private Cursor lootCursor;
    private Cursor interactCursor;
    private Cursor flightCursor;

    public CursorManagerGameState(MainFrame mainFrame){
        super(mainFrame);
        changeCursor(CursorManager.INDEX_DEFAULT);
    }

    public void update(List<GameObject> mouseOverObjects){
        if(mouseOverObjects.size() > 0){
            mouseOverObjects.forEach(mouseOverObject -> {
                if(mouseOverObject.getGameObjectID() == GameObjectID.NPC && !mouseOverObject.isDead()) {
                    changeCursor(INDEX_ATTACK);
                } else if(mouseOverObject.getGameObjectID() == GameObjectID.NPC && mouseOverObject.isDead() && !mouseOverObject.hasBeenLooted()){
                    changeCursor(INDEX_LOOT);
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
            case INDEX_INTERACT -> mainFrame.setCursor(interactCursor);
            case INDEX_FLIGHT -> mainFrame.setCursor(flightCursor);
        }
    }

    public void initCursors(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        //Default cursor
        Image image = toolkit.getImage("resources/sprites/ui/DefaultCursor.png");
        defaultCursor = toolkit.createCustomCursor(image,new Point(0,0),"defaultCursor");
        image = toolkit.getImage("resources/sprites/ui/DefaultCursor.png");
        defaultClickedCursor = toolkit.createCustomCursor(image,new Point(0,0),"defaultCursor");
        //Attack cursor
        image = toolkit.getImage("resources/sprites/ui/AttackCursor.png");
        attackCursor = toolkit.createCustomCursor(image,new Point(0,0),"attackCursor");
        //Cast no target cursor
        image = toolkit.getImage("resources/sprites/ui/CastNoTargetCursor.png");
        castNoTargetCursor = toolkit.createCustomCursor(image,new Point(0,0),"castNoTargetCursor");
        //Loot cursor
        image = toolkit.getImage("resources/sprites/ui/LootCursor.png");
        lootCursor = toolkit.createCustomCursor(image,new Point(0,0),"lootCursor");
        //Interact cursor
        image = toolkit.getImage("resources/sprites/ui/InteractCursor.png");
        interactCursor = toolkit.createCustomCursor(image,new Point(0,0),"interactCursor");
        //Flight cursor
        image = toolkit.getImage("resources/sprites/ui/FlightCursor.png");
        flightCursor = toolkit.createCustomCursor(image,new Point(0,0),"flightCursor");
    }
}
