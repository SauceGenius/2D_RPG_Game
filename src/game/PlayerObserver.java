package game;

import character.Character;

public interface PlayerObserver {

    void notifyCharacterEnteringWorld(Character character);
    void notifyCharacterLeavingWorld(Character character);
}
