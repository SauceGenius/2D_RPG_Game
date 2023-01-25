package character;

import Inventory.Inventory;
import audio.AudioPlayer;
import controller.PlayerController;
import core.Log;
import equipment.Equipment;
import gameobject.GameObject;
import gameobject.Player;
import stats.Stats;
import gfx.SpriteLibrary;
import id.GameClassId;
import id.RaceId;
import input.Input;
import quest.QuestLog;

public class Character {

    private int characterId;
    private String userName;
    private RaceId raceId;
    private GameClassId gameClassId;
    private Stats stats;
    private PlayerController playerController;
    private GameObject gameObject;
    private Inventory inventory;
    private Equipment equipment;
    private QuestLog questLog;
    private Log log;
    //private Spells spells;
    //private Talents talents;

    //Constructor for new Character;
    public Character(int characterId, String userName, RaceId raceId, GameClassId gameClassId, Input input, AudioPlayer audioPlayer, SpriteLibrary spriteLibrary){
        this.characterId = characterId;
        this.userName = userName;
        this.raceId = raceId;
        this.gameClassId = gameClassId;
        this.stats = new Stats(raceId, gameClassId);
        this.playerController = new PlayerController(input);
        this.inventory = new Inventory();
        this.equipment = new Equipment(playerController, spriteLibrary);
        this.gameObject = new Player(userName, playerController, audioPlayer, stats, inventory, equipment, spriteLibrary, log);
        this.questLog = new QuestLog();
        this.log = new Log();
    }

    public void update(){
        log.update();
    }

    // Getters
    public Log getLog() {return log;}
    public int getCharacterId() {return characterId;}
    public String getUserName() {return userName;}
    public RaceId getRaceId() {return raceId;}
    public GameClassId getGameClassId() {return gameClassId;}
    public Stats getStats() {return stats;}
    public PlayerController getPlayerController() {return playerController;}
    public GameObject getGameObject() {return gameObject;}
    public Inventory getInventory() {return inventory;}
    public Equipment getEquipment() {return equipment;}
    public QuestLog getQuestLog() {
        return questLog;
    }
}
