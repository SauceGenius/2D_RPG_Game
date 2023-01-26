package ui;

import item.Item;

public interface UIManagerObserver {

    void notifyPlayerRightClickedOnItem(Item item, int indexFrom);
}
