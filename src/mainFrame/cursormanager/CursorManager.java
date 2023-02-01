package mainFrame.cursormanager;

import mainFrame.MainFrame;

import java.awt.*;

public class CursorManager {
    public static final int INDEX_DEFAULT = 0;
    public static final int INDEX_DEFAULT_CLICKED = 1;

    protected MainFrame mainFrame;
    protected Cursor defaultCursor;
    protected Cursor defaultClickedCursor;

    public CursorManager(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        initCursors();
        changeCursor(CursorManager.INDEX_DEFAULT);
    }


    public void update(){}

    public void changeCursor(int cursorType) {
        switch (cursorType) {
            case INDEX_DEFAULT -> mainFrame.setCursor(defaultCursor);
            case INDEX_DEFAULT_CLICKED -> mainFrame.setCursor(defaultClickedCursor);
        }
    }

    public void initCursors(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        //Default cursor
        Image image = toolkit.getImage("resources/sprites/ui/DefaultCursor.png");
        defaultCursor = toolkit.createCustomCursor(image,new Point(0,0),"defaultCursor");
        image = toolkit.getImage("resources/sprites/ui/DefaultCursor.png");
        defaultClickedCursor = toolkit.createCustomCursor(image,new Point(0,0),"defaultCursor");
    }
}
