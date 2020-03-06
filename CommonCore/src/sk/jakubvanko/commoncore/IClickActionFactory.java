package sk.jakubvanko.commoncore;

import java.util.Map;

/**
 * Represents a click action factory
 */
public interface IClickActionFactory {
    /**
     * Creates a click action according to a given name
     *
     * @param actionName  Name of the click action to be created
     * @param argumentMap Arguments for the click action
     * @return A click action of a given type
     */
    ClickAction getClickAction(String actionName, Map<String, Object> argumentMap);
}
