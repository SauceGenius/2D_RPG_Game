package game.state;

import audio.AudioPlayer;
import core.Log;
import core.Position;
import mainFrame.Camera;
import mainFrame.cursormanager.CursorManager;
import login.Account;
import ui.UIController;
import gameobject.GameObject;
import character.Character;
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
    protected List<GameObject> gameObjects;

    //protected ArrayList<Character> characters;
    protected Account account;

    protected SpriteLibrary spriteLibrary;
    protected Input input;
    protected Camera camera;
    protected Time time;
    protected List<Time> respawnTimer;
    protected CursorManager cursorManager;
    protected ArrayList<StateObserver> observers;

    public State(Input input, AudioPlayer audioPlayer, SpriteLibrary spriteLibrary, CursorManager cursorManager) {
        this.audioPlayer = audioPlayer;
        this.input = input;
        this.spriteLibrary = spriteLibrary;
        this.gameObjects = new ArrayList<>();
        //this.characters = new ArrayList<>();
        this.time = new Time();
        this.respawnTimer = new ArrayList<>();
        this.cursorManager = cursorManager;
        observers = new ArrayList<>();
    }

    public void update(){
        sortObjectsByPosition();
        gameObjects.forEach(gameObject -> gameObject.update(this));
        //characters.forEach(character -> character.update(this));
    }

    protected abstract void handleMouseInput();

    private void sortObjectsByPosition() {
        gameObjects.sort(Comparator.comparing(gameObjects -> gameObjects.getPosition().getY()));
    }

    public List<GameObject> getCollidingObjects(GameObject gameObject){
        return gameObjects.stream().filter(other -> other.collidesWith(gameObject)).collect(Collectors.toList());
    }
    public List<GameObject> getAttackedObjects(GameObject gameObject) {
        return gameObjects.stream().filter(other -> other.attackCollidesWith(gameObject)).collect(Collectors.toList());
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

    //Getters
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
