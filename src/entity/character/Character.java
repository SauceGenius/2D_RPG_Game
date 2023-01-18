package entity.character;

import audio.AudioPlayer;
import controller.PlayerController;
import core.Log;
import entity.Equipment;
import entity.GameObject;
import entity.Inventory;
import entity.Player;
import entity.stats.Stats;
import gfx.SpriteLibrary;
import input.Input;

public class Character {

    private Log log;
    private int characterId;
    private String userName;
    private RaceId raceId;
    private GameClassId gameClassId;
    private Stats stats;
    private PlayerController playerController;
    private GameObject gameObject;
    private Inventory inventory;
    private Equipment equipment;
    //private Spells spells;
    //private Talents talents;

    //Constructor for new Character;
    public Character(int characterId, String userName, RaceId raceId, GameClassId gameClassId, Input input, AudioPlayer audioPlayer, SpriteLibrary spriteLibrary, Log log){
        this.log = log;
        this.characterId = characterId;
        this.userName = userName;
        this.raceId = raceId;
        this.gameClassId = gameClassId;
        this.stats = new Stats(raceId, gameClassId);
        this.playerController = new PlayerController(input);
        this.inventory = new Inventory(playerController, spriteLibrary);
        this.equipment = new Equipment(playerController, spriteLibrary);
        this.gameObject = new Player(userName, playerController, audioPlayer, stats, inventory, equipment, spriteLibrary, log);
    }

    public void update(){
        log.update();
    }

    // Getters
    public int getCharacterId() {return characterId;}
    public String getUserName() {return userName;}
    public RaceId getRaceId() {return raceId;}
    public GameClassId getGameClassId() {return gameClassId;}
    public Stats getStats() {return stats;}
    public PlayerController getPlayerController() {return playerController;}
    public GameObject getGameObject() {return gameObject;}
    public Inventory getInventory() {return inventory;}
    public Equipment getEquipment() {return equipment;}
}
