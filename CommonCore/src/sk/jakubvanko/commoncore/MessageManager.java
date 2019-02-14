package sk.jakubvanko.commoncore;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Map;

/**
 * Represents a message manager used to send and log messages
 */
public class MessageManager {

    protected Map<String, String> messageMap;
    protected String logo;

    /**
     * Gets the map of message identifier linked to their interpretations
     *
     * @return Map of message identifiers linked to their interpretations
     */
    public Map<String, String> getMessageMap() {
        return messageMap;
    }

    /**
     * Sets the map of message identifiers linked to their interpretations
     *
     * @param messageMap Map of message identifiers linked to their interpretations
     */
    public void setMessageMap(Map<String, String> messageMap) {
        this.messageMap = messageMap;
    }

    /**
     * Creates a message manager
     *
     * @param messageMap Map of message identifiers linked to their interpretations
     */
    public MessageManager(Map<String, String> messageMap) {
        this.messageMap = messageMap;
    }

    /**
     * Gets the logo of the plugin
     *
     * @return String representing the logo of the plugin
     */
    public String getLogo() {
        if (logo == null) {
            String logo = "";
            if (messageMap.containsKey("logo")) {
                String logoString = messageMap.get("logo");
                if (logoString != null) {
                    this.logo = logoString;
                }
            }
        }
        return this.logo;
    }

    /**
     * Replaces abbreviations in a message with their substitutes
     *
     * @param message       Message to be edited
     * @param abbreviations Map of abbreviations and their substitutes
     * @return Formatted message
     */
    private String formatAbbreviations(String message, Map<String, String> abbreviations) {
        if (abbreviations != null) {
            for (String abbreviation : abbreviations.keySet()) {
                message = message.replace(abbreviation, abbreviations.get(abbreviation));
            }
        }
        return message;
    }

    /**
     * Sends formatted message to the specified target
     * Abbreviation "%logo" will be always replaced with the plugin logo
     *
     * @param messageIdentifier Identifier of the message to be sent
     * @param target            Target to send the message to
     * @param abbreviations     Map of abbreviations and their substitutes
     */
    public void sendMessage(String messageIdentifier, CommandSender target, Map<String, String> abbreviations) {
        String messageString = messageMap.get(messageIdentifier);
        if (messageString == null) return;
        messageString = messageString.replace("%logo", getLogo());
        messageString = formatAbbreviations(messageString, abbreviations);
        target.sendMessage(messageString);
    }

    /**
     * Sends the text of the message to the specified target
     * Abbreviation "%logo" will be always replaced with the plugin logo
     *
     * @param message Message to be sent
     * @param target  Target to send the message to
     */
    public void sendMessage(String message, CommandSender target) {
        if (message != null) {
            message = message.replace("%logo", getLogo());
            target.sendMessage(message);
        }
    }

    /**
     * Logs the text of the message to console
     * Abbreviation "%logo" will be always replaced with the plugin logo
     *
     * @param message Message to be logged
     */
    public void logMessage(String message) {
        if (message != null) {
            String logo = getLogo().replaceAll("§.", "");
            message = logo + " " + message;
            Bukkit.getServer().getLogger().info(message);
        }
    }
}
