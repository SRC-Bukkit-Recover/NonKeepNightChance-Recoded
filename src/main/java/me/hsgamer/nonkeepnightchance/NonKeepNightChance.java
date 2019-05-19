package me.hsgamer.nonkeepnightchance;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public final class NonKeepNightChance extends JavaPlugin {
    static boolean legacy;
    static ConfigFile configFile;
    static boolean isTitleAPIEnabled;
    static HashMap<String, Runnable> worlds = new HashMap<>();

    @Override
    public void onEnable() {
        legacy = !(getServer().getVersion().contains("1.13") || getServer().getVersion().contains("1.14"));
        configFile = new ConfigFile(this);
        isTitleAPIEnabled = Bukkit.getPluginManager().isPluginEnabled("TitleAPI") && Bukkit.getPluginManager().getPlugin("TitleAPI").getDescription().getAuthors().contains("Phloxz");

        int delay = (int) Utils.getValueFromConfig(Config.DELAY);
        boolean async = (boolean) Utils.getValueFromConfig(Config.ASYNC);
        for (String string : (List<String>) Utils.getValueFromConfig(Config.WORLDS)) {
            String world = string.split(":")[0].trim();
            double chance = Double.parseDouble(string.split(":")[1].trim());
            if (Bukkit.getWorld(world) != null) {
                worlds.put(world, new Runnable(this, world, chance, delay, async));
                Utils.sendMessage(getServer().getConsoleSender(), "&aRegistered &f" + world);
            } else {
                Utils.sendMessage(getServer().getConsoleSender(), "&cIgnored &f" + world);
            }
        }
    }
}
