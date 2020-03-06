package sk.jakubvanko.ultrachest;

import sk.jakubvanko.commoncore.ClickAction;
import sk.jakubvanko.commoncore.IClickActionFactory;
import sk.jakubvanko.commoncore.actions.CloseInventory;
import sk.jakubvanko.commoncore.actions.PlaySound;
import sk.jakubvanko.commoncore.actions.ReplaceItem;
import sk.jakubvanko.ultrachest.actions.UltraChestStart;

import java.util.Map;

public class UCClickActionFactory implements IClickActionFactory {

    @Override
    public ClickAction getClickAction(String actionName, Map<String, Object> argumentMap) {
        switch (actionName) {
            case "CLOSE_INVENTORY":
                return new CloseInventory(argumentMap);
            case "PLAY_SOUND":
                return new PlaySound(argumentMap);
            case "REPLACE_ITEM":
                return new ReplaceItem(argumentMap);
            case "ULTRACHEST_START":
                return new UltraChestStart(argumentMap);
        }
        throw new IllegalArgumentException();
    }
}
