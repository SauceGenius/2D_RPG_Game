package login;

import character.Character;
import input.InputObserver;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class AccountController implements InputObserver {

    private Account account;

    public AccountController(){
        account = new Account();

    }

    public void createAccount(String username, String password){

    }

    public void authenticate(String username, String password){
        //if(authenticated){
            logIn(username);
        //}
    }

    public void logIn(String username){
        AccountLoader accountLoader = new AccountLoader();
        //account = AccountLoader.loadAccount(username);
    }

    public void createCharacter(){

    }

    public void selectCharacter(Character character){

    }

    public void deleteCharacter(Character character){

    }


    @Override
    public void notifyKeyPressed(KeyEvent keyPressed) {

    }

    @Override
    public void notifyMouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void notifyMousePressed(MouseEvent mouseEvent) {
        // if username panel selected:

    }

    @Override
    public void notifyMouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void notifyMouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void notifyMouseMoved(MouseEvent mouseEvent) {

    }
}
