package sk.jakubvanko.betterbeacons.actions;

import org.bukkit.block.Beacon;
import sk.jakubvanko.betterbeacons.BBEventArguments;
import sk.jakubvanko.betterbeacons.EffectTimeManager;
import sk.jakubvanko.commoncore.ClickAction;

import java.util.Map;

public class RemoveBeaconEffect extends ClickAction<BBEventArguments> {

    public RemoveBeaconEffect(Map<String, Object> arguments) {
        super(arguments);
    }

    @Override
    public void onSlotClicked(BBEventArguments eventArguments) {
        Beacon beacon = eventArguments.getBeacon();
        EffectTimeManager effectTimeManager = eventArguments.getEffectTimeManager();
        Boolean primary = getArgumentBool("PRIMARY", false);
        Boolean secondary = getArgumentBool("SECONDARY", false);
        if (primary) beacon.setPrimaryEffect(null);
        if (secondary) beacon.setSecondaryEffect(null);
        beacon.update();
        effectTimeManager.removeBeaconEffect(beacon, primary, secondary);
    }
}
