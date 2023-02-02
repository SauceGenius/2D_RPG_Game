package game.state;

import audio.AudioPlayer;
import core.Position;
import mainFrame.cursormanager.CursorManager;
import login.Account;
import gameobject.GameObject;
import core.Time;
import gfx.SpriteLibrary;
import input.Input;
import map.GameMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class State {

    //protected UIController uiManager;
    protected AudioPlayer audioPlayer;
    protected GameMap gameMap;

    protected ArrayList<GameObject> gameObjects;
    protected ArrayList<GameObject> pendingGameObjects;
    protected ArrayList<GameObject> toRemoveGameObjects;

    //protected ArrayList<Character> characters;
    protected Account account;

    protected SpriteLibrary spriteLibrary;
    protected Input input;
    protected Time time;
    protected List<Time> respawnTimer;
    protected CursorManager cursorManager;
    protected ArrayList<StateObserver> observers;

    public State(Input input, AudioPlayer audioPlayer, SpriteLibrary spriteLibrary, CursorManager cursorManager) {
        this.audioPlayer = audioPlayer;
        this.input = input;
        this.spriteLibrary = spriteLibrary;
        this.gameObjects = new ArrayList<>();
        this.pendingGameObjects = new ArrayList<>();
        this.toRemoveGameObjects = new ArrayList<>();
        //this.characters = new ArrayList<>();
        this.time = new Time();
        this.respawnTimer = new ArrayList<>();
        this.cursorManager = cursorManager;
        observers = new ArrayList<>();
    }

    public void update(){
        sortObjectsByPosition();

        for(int i = 0; i < toRemoveGameObjects.size(); i++){
            for (int y = 0; y < gameObjects.size(); y++){
                if(gameObjects.get(y) == toRemoveGameObjects.get(i)){
                    gameObjects.remove(y);
                    y--;
                }
            }
        }

        for(int i = 0; i < pendingGameObjects.size(); i++){
            gameObjects.add(pendingGameObjects.get(i));
            pendingGameObjects.remove(i);
            i--;
        }

        gameObjects.forEach(gameObject -> gameObject.update(this));
        //characters.forEach(character -> character.update(this));
    }

    public void addGameObject(GameObject gameObject){
        pendingGameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject){
        toRemoveGameObjects.add(gameObject);
    }

    protected abstract void handleMouseInput();

    private void sortObjectsByPosition() {
        gameObjects.sort(Comparator.comparing(gameObjects -> gameObjects.getPosition().getY()));
    }

    /** Collision **/
    public List<GameObject> getCollidingObjects(GameObject gameObject){
        return gameObjects.stream().filter(other -> other.collidesWith(gameObject)).collect(Collectors.toList());
    }
    public List<GameObject> getInMeleeRangeObjects(GameObject gameObject) {
        return gameObjects.stream().filter(other -> other.meleeRangeCollidesWith(gameObject)).collect(Collectors.toList());
    }
    public List<GameObject> getClickedObjects(GameObject gameObject) {
        return gameObjects.stream().filter(other -> other.mouseCollidesWith(gameObject)).collect(Collectors.toList());
    }
    public List<GameObject> getDetectedObjects(GameObject gameObject) {
        return gameObjects.stream().filter(other -> other.detectionCollidesWith(gameObject)).collect(Collectors.toList());
    }

    public void addObserver(StateObserver observer){
        observers.add(observer);
    }

    public void removeObserver(StateObserver observer){
        observers.remove(observer);
    }

    /** Getters **/
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public Time getTime() {
        return time;
    }
    public Position getRandomPosition() {
        return gameMap.getRandomPosition();
    }

    public SpriteLibrary getSpriteLibrary() {
        return spriteLibrary;
    }
}
