package sk.jakubvanko.commoncore.actions;

import com.cryptomorin.xseries.XSound;
import sk.jakubvanko.commoncore.ClickAction;
import sk.jakubvanko.commoncore.EventArguments;

import java.util.Map;
import java.util.Optional;

/**
 * Represents a play sound click action
 */
public class PlaySound extends ClickAction<EventArguments> {
    /**
     * Creates a play sound click action with the given arguments
     *
     * @param arguments Map of action arguments
     */
    public PlaySound(Map<String, Object> arguments) {
        super(arguments);
    }

    /**
     * Plays sound to the player that triggered the event
     *
     * @param eventArguments Additional arguments passed at runtime
     */
    @Override
    public void onSlotClicked(EventArguments eventArguments) {
        String soundName = getArgumentString("NAME", "BLOCK_NOTE_BLOCK_HARP");
        int volume = getArgumentInt("VOLUME", 1);
        int pitch = getArgumentInt("PITCH", 1);
        Optional<XSound> optionalXSound = XSound.matchXSound(soundName);
        optionalXSound.get().play(eventArguments.getPlayer(), volume, pitch);
    }
}
