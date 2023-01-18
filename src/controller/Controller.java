package controller;

import java.awt.event.KeyEvent;

public interface Controller {

    boolean isRequestingUp();
    boolean isRequestingDown();
    boolean isRequestingLeft();
    boolean isRequestingRight();
    boolean isRequestingAttack();
    boolean isRequestingSprint();
}
