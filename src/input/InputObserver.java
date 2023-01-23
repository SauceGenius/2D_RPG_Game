package input;

import java.awt.event.KeyEvent;

public interface InputObserver {

    public void notifyKeyPressed(KeyEvent keyPressed);
}
