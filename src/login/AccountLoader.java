package login;

import javax.swing.text.Document;

public class AccountLoader {

    private Document document;

    public AccountLoader(){

    }

    public Account loadAccount(String username){
        Account account = new Account();

        addCharacter();

        return account;
    }

    private void addCharacter(){

    }

}
