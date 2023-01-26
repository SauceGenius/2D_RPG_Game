package world;

import character.Character;
import gameobject.GameObject;
import id.ZoneId;

import java.util.ArrayList;

public class Zone {

    private ZoneId zoneId;
    private ArrayList<GameObject> gameObjects;
    private ArrayList<Character> characters;

    public Zone(ZoneId zoneId){
        this.zoneId = zoneId;
        this.gameObjects = new ArrayList<>();
        this.characters = new ArrayList<>();

    }

    public void update(){
        //gameObjects.forEach(gameObject -> gameObject.update());
        //characters.forEach(character -> character.update());
    }

    public void characterEntersZone(Character character){
        characters.add(character);
    }

    public void characterLeavesZone(Character character){
        characters.remove(character);
    }

    public ZoneId getZoneId() {
        return zoneId;
    }
}

