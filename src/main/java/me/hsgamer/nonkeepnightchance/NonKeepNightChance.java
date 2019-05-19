package me.hsgamer.nonkeepnightchance;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.rmi.CORBA.Util;
import java.util.HashMap;
import java.util.List;

public final class NonKeepNightChance extends JavaPlugin {
    static boolean legacy;
    static ConfigFile configFile;
    static boolean isTitleAPIEnabled;
    static boolean isTitleEnabled;
    static HashMap<String, Runnable> worlds = new HashMap<>();

    @Override
    public void onEnable() {
        legacy = !(getServer().getVersion().contains("1.13") || getServer().getVersion().contains("1.14"));
        configFile = new ConfigFile(this);
        isTitleAPIEnabled = Bukkit.getPluginManager().isPluginEnabled("TitleAPI") && Bukkit.getPluginManager().getPlugin("TitleAPI").getDescription().getAuthors().contains("Phloxz");
        isTitleEnabled = (boolean) Utils.getValueFromConfig(Config.TITLE_ENABLED);

        if (legacy && isTitleEnabled && !isTitleAPIEnabled) {
            Utils.sendMessage(getServer().getConsoleSender(), "&cYou are using a legacy version of bukkit, but TitleAPI seems to be unavailable. Ignored");
            Utils.sendMessage(getServer().getConsoleSender(), "&bLink: &fhttps://www.spigotmc.org/resources/titleapi-1-8-1-13.40201/");
            isTitleEnabled = false;
        }

        int delay = (int) Utils.getValueFromConfig(Config.DELAY);
        boolean async = (boolean) Utils.getValueFromConfig(Config.ASYNC);
        for (String string : (List<String>) Utils.getValueFromConfig(Config.WORLDS)) {
            if (string.split(":").length != 2) {
                Utils.sendMessage(getServer().getConsoleSender(), "&cInvalid form: &f" + string);
                Utils.sendMessage(getServer().getConsoleSender(), "&eShould be '<world>:<chance>'");
                continue;
            }
            String world = string.split(":")[0].trim();
            double chance = Double.parseDouble(string.split(":")[1].trim());
            if (Bukkit.getWorld(world) != null) {
                worlds.put(world, new Runnable(this, world, chance, delay, async));
                Utils.sendMessage(getServer().getConsoleSender(), "&aRegistered &f" + world);
            } else {
                Utils.sendMessage(getServer().getConsoleSender(), "&cInvalid world: &f" + world);
            }
        }
    }
}
