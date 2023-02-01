package ui;

import item.Item;

public interface UIManagerObserver {

    void notifyRightClickedOnItemInInventory(Item item, int indexFrom);

    void notifyRightClickedOnItemInCharacterPanel(Item item, int index);

    void notifySwapItemsInInventory(int indexFrom, Item itemFrom, int index, Item itemTo);

    void notifyDraggedItemOutsideInventory(int indexFrom);
}
