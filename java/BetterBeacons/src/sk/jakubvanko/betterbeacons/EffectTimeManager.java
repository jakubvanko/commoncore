package sk.jakubvanko.betterbeacons;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Beacon;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import sk.jakubvanko.commoncore.ConfigData;

import java.io.File;
import java.io.IOException;

public class EffectTimeManager extends BukkitRunnable {

    private ConfigData configData;
    private File effectTimeFile;
    private int defaultDuration;
    private int durationToSubtract;

    public EffectTimeManager(ConfigData configData) {
        this.configData = configData;
        defaultDuration = configData.getConfig().getInt("general.effect_duration.duration");
        durationToSubtract = configData.getConfig().getInt("general.effect_duration.subtraction_time");
        effectTimeFile = new File("plugins/BetterBeacons/effect_time.yml");
        if (!effectTimeFile.exists()) {
            try {
                effectTimeFile.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getLogger().severe("Error: Unable to create data file effect_time.yml");
                e.printStackTrace();
            }
        }
    }

    public void addEffectDuration(Beacon beacon, PotionEffectType effectType, boolean primary, boolean secondary) {
        FileConfiguration effectTimeData = YamlConfiguration.loadConfiguration(effectTimeFile);
        // If both effects should be set, call this function again (because it can only set one of them)
        if (primary && secondary) addEffectDuration(beacon, effectType, false, true);
        String mode = primary ? "PRIMARY" : "SECONDARY";
        String uniqueID = getBeaconID(beacon, mode);
        int effectDuration = defaultDuration;
        if (effectTimeData.contains(uniqueID)) {
            String oldEffectName = effectTimeData.getString(uniqueID + ".effect");
            if (effectType.getName().equals(oldEffectName)) {
                int oldDuration = effectTimeData.getInt(uniqueID + ".duration");
                effectDuration += oldDuration;
            } else {
                effectTimeData.set(uniqueID + ".effect", effectType.getName());
            }
            effectTimeData.set(uniqueID + ".duration", effectDuration);
        } else {
            effectTimeData.set(uniqueID + ".x", beacon.getLocation().getBlockX());
            effectTimeData.set(uniqueID + ".y", beacon.getLocation().getBlockY());
            effectTimeData.set(uniqueID + ".z", beacon.getLocation().getBlockZ());
            effectTimeData.set(uniqueID + ".world", beacon.getLocation().getWorld().getName());
            effectTimeData.set(uniqueID + ".mode", mode);
            effectTimeData.set(uniqueID + ".effect", effectType.getName());
            effectTimeData.set(uniqueID + ".duration", effectDuration);
        }
        saveData(effectTimeData);
    }

    private void saveData(FileConfiguration effectTimeData) {
        try {
            effectTimeData.save(effectTimeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBeaconID(Beacon beacon, String mode) {
        // If world name is "world", it needs to be an empty string for backwards compatibility
        String worldName = beacon.getLocation().getWorld().getName();
        if (worldName.equals("world")) {
            worldName = "";
        }

        return "" +
                beacon.getLocation().getBlockX() +
                beacon.getLocation().getBlockY() +
                beacon.getLocation().getBlockZ() +
                worldName +
                mode;
    }

    @Override
    public void run() {
        FileConfiguration effectTimeData = YamlConfiguration.loadConfiguration(effectTimeFile);
        for (String uniqueID : effectTimeData.getKeys(false)) {
            int oldDuration = effectTimeData.getInt(uniqueID + ".duration");
            int effectDuration = oldDuration - durationToSubtract;
            if (effectDuration <= 0) {
                removeBeaconEffect(effectTimeData, uniqueID);
            } else {
                effectTimeData.set(uniqueID + ".duration", effectDuration);
            }
        }
        saveData(effectTimeData);
    }

    private void removeBeaconEffect(FileConfiguration effectTimeData, String uniqueID) {
        Beacon beacon = getBeacon(effectTimeData, uniqueID);
        if (beacon == null) return;
        String mode = effectTimeData.getString(uniqueID + ".mode");
        if (mode.equals("PRIMARY")) {
            beacon.setPrimaryEffect(null);
        } else {
            beacon.setSecondaryEffect(null);
        }
        beacon.update();
        effectTimeData.set(uniqueID, null);
    }

    public void removeBeaconEffect(Beacon beacon, boolean primary, boolean secondary) {
        FileConfiguration effectTimeData = YamlConfiguration.loadConfiguration(effectTimeFile);
        if (primary) {
            removeBeaconEffect(effectTimeData, getBeaconID(beacon, "PRIMARY"));
        }
        if (secondary) {
            removeBeaconEffect(effectTimeData, getBeaconID(beacon, "SECONDARY"));
        }
        saveData(effectTimeData);
    }

    private Beacon getBeacon(FileConfiguration effectTimeData, String uniqueID) {
        int beaconX = effectTimeData.getInt(uniqueID + ".x");
        int beaconY = effectTimeData.getInt(uniqueID + ".y");
        int beaconZ = effectTimeData.getInt(uniqueID + ".z");
        String beaconWorldName = effectTimeData.getString(uniqueID + ".world", "world");
        Location beaconLocation = new Location(Bukkit.getWorld(beaconWorldName), beaconX, beaconY, beaconZ);
        BlockState blockState = beaconLocation.getBlock().getState();
        if (blockState instanceof Beacon) {
            return (Beacon) blockState;
        } else {
            return null;
        }
    }

    public void loadBeaconEffects() {
        FileConfiguration effectTimeData = YamlConfiguration.loadConfiguration(effectTimeFile);
        for (String uniqueID : effectTimeData.getKeys(false)) {
            String effect = effectTimeData.getString(uniqueID + ".effect");
            String mode = effectTimeData.getString(uniqueID + ".mode");
            PotionEffectType effectType = PotionEffectType.getByName(effect);
            Beacon beacon = getBeacon(effectTimeData, uniqueID);
            if (beacon != null) {
                if (mode.equals("PRIMARY")) {
                    beacon.setPrimaryEffect(effectType);
                } else {
                    beacon.setSecondaryEffect(effectType);
                }
            }
        }
    }

    public String getEffectTimeRemaining(Beacon beacon, boolean primary) {
        FileConfiguration effectTimeData = YamlConfiguration.loadConfiguration(effectTimeFile);
        if (!configData.getConfig().getBoolean("general.effect_duration.enabled")) {
            return "INFINITE";
        }
        String mode = primary ? "PRIMARY" : "SECONDARY";
        if (effectTimeData.contains(getBeaconID(beacon, mode))) {
            return effectTimeData.getString(getBeaconID(beacon, mode) + ".duration");
        } else {
            return "0";
        }
    }
}
