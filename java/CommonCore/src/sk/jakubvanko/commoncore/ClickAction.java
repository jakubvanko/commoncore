package sk.jakubvanko.commoncore;

import java.util.List;
import java.util.Map;

/**
 * Represents a click action
 */
public abstract class ClickAction<T extends EventArguments> {

    protected Map<String, Object> arguments;

    /**
     * Gets a map containing all argument names and their specifications for this click action
     *
     * @return Map of action arguments
     */
    public Map<String, Object> getArguments() {
        return arguments;
    }

    /**
     * Creates a click action with the given arguments
     *
     * @param arguments Map of action arguments
     */
    public ClickAction(Map<String, Object> arguments) {
        this.arguments = arguments;
    }

    /**
     * A function that will be called when the correct inventory slot is clicked
     *
     * @param eventArguments Additional arguments passed at runtime
     */
    public abstract void onSlotClicked(T eventArguments);

    /**
     * Adds an arguments
     *
     * @param key   Name of the argument
     * @param value Argument data
     */
    public void addArgument(String key, Object value) {
        arguments.put(key, value);
    }

    /**
     * Gets the cast string argument by its name
     *
     * @param key           Name of the argument
     * @param defaultString Default argument
     * @return Cast argument or default value
     */
    public String getArgumentString(String key, String defaultString) {
        Object argument = arguments.get(key);
        if (argument instanceof String) {
            return (String) argument;
        }
        return defaultString;
    }

    /**
     * Gets the cast integer argument by its name
     *
     * @param key            Name of the argument
     * @param defaultInteger Default argument
     * @return Cast argument or default value
     */
    public Integer getArgumentInt(String key, Integer defaultInteger) {
        Object argument = arguments.get(key);
        if (argument instanceof Integer) {
            return (Integer) argument;
        }
        return defaultInteger;
    }

    /**
     * Gets the cast boolean argument by its name
     *
     * @param key            Name of the argument
     * @param defaultBoolean Default argument
     * @return Cast argument or default value
     */
    public Boolean getArgumentBool(String key, Boolean defaultBoolean) {
        Object argument = arguments.get(key);
        if (argument instanceof Boolean) {
            return (Boolean) argument;
        }
        return defaultBoolean;
    }

    /**
     * Gets the cast list argument by its name
     *
     * @param key         Name of the argument
     * @param defaultList Default argument
     * @return Cast argument or default value
     */
    @SuppressWarnings("unchecked")
    public List<String> getArgumentList(String key, List<String> defaultList) {
        Object argument = arguments.get(key);
        if (argument instanceof List) {
            return (List) argument;
        }
        return defaultList;
    }
}
