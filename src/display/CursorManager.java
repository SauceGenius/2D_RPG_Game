package display;

import gameobject.GameObject;
import id.GameObjectID;

import java.awt.*;
import java.util.List;

public class CursorManager {

    public static final int DEFAULT = 0;
    public static final int ATTACK = 1;
    public static final int CAST = 2;
    public static final int LOOT = 3;
    public static final int INTERACT = 4;
    public static final int FLIGHT = 5;
    private Cursor defaultCursor;
    private Cursor attackCursor;
    private Cursor castNoTargetCursor;
    private Cursor lootCursor;
    private Cursor interactCursor;
    private Cursor flightCursor;
    private Display display;

    public CursorManager(Display display){
        this.display = display;
        initCursors();
        changeCursor(CursorManager.DEFAULT);
    }

    public void update(List<GameObject> mouseOverObjects){
        if(mouseOverObjects.size() > 0){
            mouseOverObjects.forEach(mouseOverObject -> {
                if(mouseOverObject.getGameObjectID() == GameObjectID.NPC && !mouseOverObject.isDead()) {
                    changeCursor(CursorManager.ATTACK);
                } else if(mouseOverObject.getGameObjectID() == GameObjectID.NPC && mouseOverObject.isDead() && !mouseOverObject.hasBeenLooted()){
                    changeCursor(CursorManager.LOOT);
                } else changeCursor(CursorManager.DEFAULT);
            });
        } else changeCursor(CursorManager.DEFAULT);
    }

    public void changeCursor(int cursorType) {
        switch (cursorType) {
            case 0 -> display.setCursor(defaultCursor);
            case 1 -> display.setCursor(attackCursor);
            case 2 -> display.setCursor(castNoTargetCursor);
            case 3 -> display.setCursor(lootCursor);
            case 4 -> display.setCursor(interactCursor);
            case 5 -> display.setCursor(flightCursor);
        }
    }

    public void initCursors(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        //Default cursor
        Image image = toolkit.getImage("resources/sprites/ui/DefaultCursor.png");
        defaultCursor = toolkit.createCustomCursor(image,new Point(0,0),"defaultCursor");
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
