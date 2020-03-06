package sk.jakubvanko.betterbeacons.actions;

import org.bukkit.block.Beacon;
import org.bukkit.entity.Player;
import sk.jakubvanko.betterbeacons.BBEventArguments;
import sk.jakubvanko.betterbeacons.EffectTimeManager;
import sk.jakubvanko.commoncore.ClickAction;
import sk.jakubvanko.commoncore.MessageManager;

import java.util.HashMap;
import java.util.Map;

public class BeaconInfo extends ClickAction<BBEventArguments> {

    public BeaconInfo(Map<String, Object> arguments) {
        super(arguments);
    }

    @Override
    public void onSlotClicked(BBEventArguments eventArguments) {
        Beacon beacon = eventArguments.getBeacon();
        MessageManager messageManager = eventArguments.getMessageManager();
        EffectTimeManager effectTimeManager = eventArguments.getEffectTimeManager();
        Player player = eventArguments.getPlayer();
        Map<String, String> abbreviations = new HashMap<>();
        abbreviations.put("%x", "" + beacon.getX());
        abbreviations.put("%y", "" + beacon.getY());
        abbreviations.put("%z", "" + beacon.getZ());
        abbreviations.put("%bt", "" + beacon.getTier());
        abbreviations.put("%pd", effectTimeManager.getEffectTimeRemaining(beacon, true));
        abbreviations.put("%sd", effectTimeManager.getEffectTimeRemaining(beacon, false));
        String primaryEffectName = "NONE";
        if (beacon.getPrimaryEffect() != null) primaryEffectName = beacon.getPrimaryEffect().getType().getName();
        abbreviations.put("%pe", primaryEffectName);
        String secondaryEffectName = "NONE";
        if (beacon.getSecondaryEffect() != null) secondaryEffectName = beacon.getSecondaryEffect().getType().getName();
        abbreviations.put("%se", secondaryEffectName);
        messageManager.sendMessage("beacon_info", player, abbreviations);
    }
}
