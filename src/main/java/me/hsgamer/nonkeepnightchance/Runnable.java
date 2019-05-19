package me.hsgamer.nonkeepnightchance;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Runnable extends BukkitRunnable {
    private World world;
    private boolean isNightWorld;
    private BukkitTask task;
    private double chance;
    private boolean isKeepWorld;

    Runnable(Plugin plugin, String world, double chance, long delay, boolean async) {
        this.world = Bukkit.getWorld(world);
        this.chance = chance;
        this.isNightWorld = !(this.world.getTime() >= 0L && this.world.getTime() < 13700L);
        this.isKeepWorld = Utils.getKeepInventory(this.world);
        if (async) {
            this.task = runTaskTimerAsynchronously(plugin, delay, delay);
        } else {
            this.task = runTaskTimer(plugin, delay, delay);
        }
    }

    @Override
    public void run() {
        if (isNightWorld && world.getTime() >= 0L && world.getTime() < 13700L) {
            Utils.setKeepInventory(world, true);
            isKeepWorld = true;
            isNightWorld = false;
            for (Player c : world.getPlayers()) {
                Utils.sendMessage(c, Config.KEEP_DAY);
                Utils.sendTitle(Config.KEEP_DAY_TITLE, Config.KEEP_DAY_SUBTITLE, c);
            }
            Utils.sendMessage(Bukkit.getConsoleSender(), ChatColor.RED + "[" + world.getName() + "] " + ChatColor.translateAlternateColorCodes('&', (String) Utils.getValueFromConfig(Config.KEEP_DAY)));
        } else {

            if (Math.random() <= chance) {

                Utils.setKeepInventory(world, false);
                isKeepWorld = false;
                for (Player c : world.getPlayers()) {
                    Utils.sendMessage(c, Config.NON_KEEP_NIGHT);
                    Utils.sendTitle(Config.NON_KEEP_NIGHT_TITLE, Config.NON_KEEP_NIGHT_SUBTITLE, c);
                }
                Utils.sendMessage(Bukkit.getConsoleSender(), ChatColor.RED + "[" + world.getName() + "] " + ChatColor.translateAlternateColorCodes('&', (String) Utils.getValueFromConfig(Config.NON_KEEP_NIGHT)));
            } else {
                for (Player c : world.getPlayers()) {
                    Utils.setKeepInventory(world, true);
                    Utils.sendMessage(c, Config.KEEP_NIGHT);
                    Utils.sendTitle(Config.KEEP_NIGHT_TITLE, Config.KEEP_NIGHT_SUBTITLE, c);
                }
                Utils.sendMessage(Bukkit.getConsoleSender(), ChatColor.RED + "[" + world.getName() + "] " + ChatColor.translateAlternateColorCodes('&', (String) Utils.getValueFromConfig(Config.KEEP_NIGHT)));
            }

            isNightWorld = true;
        }
    }

    public boolean isKeepWorld() {
        return isKeepWorld;
    }

    public boolean isNightWorld() {
        return isNightWorld;
    }

    public BukkitTask getTask() {
        return task;
    }
}
