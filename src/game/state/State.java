package game.state;

import audio.AudioPlayer;
import core.Log;
import core.Position;
import core.Size;
import display.Camera;
import display.CursorManager;
import ui.UIController;
import gameobject.GameObject;
import gameobject.Player;
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

    protected Log log;
    protected UIController uiManager;
    protected AudioPlayer audioPlayer;
    protected GameMap gameMap;
    protected List<GameObject> gameObjects;
    protected Character character;
    protected Player player;
    protected SpriteLibrary spriteLibrary;
    protected Input input;
    protected Camera camera;
    protected Time time;
    protected List<Time> respawnTimer;
    protected CursorManager cursorManager;

    public State(Size windowSize, Input input, Character character, Player player, AudioPlayer audioPlayer, SpriteLibrary spriteLibrary, Log log, CursorManager cursorManager) {
        camera = new Camera(windowSize);
        this.log = log;
        this.audioPlayer = audioPlayer;
        this.input = input;
        this.character = character;
        this.player = player;
        this.spriteLibrary = spriteLibrary;
        gameObjects = new ArrayList<>();
        time = new Time();
        respawnTimer = new ArrayList<>();
        this.cursorManager = cursorManager;
    }

    public void update(){
        sortObjectsByPosition();
        gameObjects.forEach(gameObject -> gameObject.update(this));
        camera.update(this);
        log.update();
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

    //Getters
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }
    public GameMap getGameMap() {
        return gameMap;
    }
    public Camera getCamera() {
        return camera;
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
