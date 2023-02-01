package game.state;

import character.Character;

public interface StateObserver {

    void notifyCharacterEntersGame(Character character);
}
