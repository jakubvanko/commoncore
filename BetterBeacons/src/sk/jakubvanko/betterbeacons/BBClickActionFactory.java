package sk.jakubvanko.betterbeacons;

import sk.jakubvanko.betterbeacons.actions.*;
import sk.jakubvanko.commoncore.ClickAction;
import sk.jakubvanko.commoncore.IClickActionFactory;
import sk.jakubvanko.commoncore.actions.CloseInventory;
import sk.jakubvanko.commoncore.actions.OpenInventory;

import java.util.Map;

public class BBClickActionFactory implements IClickActionFactory {

    @Override
    public ClickAction getClickAction(String actionName, Map<String, Object> argumentMap) {
        switch (actionName){
            case "OPEN_INVENTORY":
                return new OpenInventory(argumentMap);
            case "REMOVE_BEACON_EFFECT":
                return new RemoveBeaconEffect(argumentMap);
            case "BEACON_INFO":
                return new BeaconInfo(argumentMap);
            case "CLOSE_INVENTORY":
                return new CloseInventory(argumentMap);
            case "SET_EFFECT":
                return new SetBeaconEffect(argumentMap);
        }
        throw new IllegalArgumentException();
    }
}
