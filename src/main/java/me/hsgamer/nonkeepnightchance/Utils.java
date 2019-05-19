package me.hsgamer.nonkeepnightchance;

import TitleAPI.Title;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {
    static void setKeepInventory(World world, boolean keep) {
        if (isLegacy()) {
            world.setGameRuleValue("keepInventory", Boolean.toString(keep));
        } else {
            world.setGameRule(GameRule.KEEP_INVENTORY, keep);
        }
    }

    static boolean getKeepInventory(World world) {
        if (isLegacy()) {
            return Boolean.parseBoolean(world.getGameRuleValue("keepInventory"));
        } else {
            return world.getGameRuleValue(GameRule.KEEP_INVENTORY);
        }
    }

    static boolean isLegacy() {
        return NonKeepNightChance.legacy;
    }

    static Object getValueFromConfig(Config config) {
        return NonKeepNightChance.configFile.getConfig().get(config.getPath(), config.getDef());
    }

    static void sendMessage(Player player, Config config) {
        sendMessage(player, (String) getValueFromConfig(config));
    }

    static void sendMessage(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', getValueFromConfig(Config.PREFIX) + message));
    }

    static void sendMessage(CommandSender sender, Config config) {
        sendMessage(sender, (String) getValueFromConfig(config));
    }

    static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getValueFromConfig(Config.PREFIX) + message));
    }

    static void sendTitle(Config title, Config subtitle, Player... players) {
        if (!NonKeepNightChance.isTitleEnabled) return;

        int fadein = (int) getValueFromConfig(Config.TITLE_FADEIN);
        int stay = (int) getValueFromConfig(Config.TITLE_STAY);
        int fadeout = (int) getValueFromConfig(Config.TITLE_FADEOUT);
        String stitle = ChatColor.translateAlternateColorCodes('&', (String) getValueFromConfig(title));
        String ssubtitle = ChatColor.translateAlternateColorCodes('&', (String) getValueFromConfig(subtitle));

        if (NonKeepNightChance.legacy) {
            if (NonKeepNightChance.isTitleAPIEnabled) {
                Title title1 = new Title();
                title1.setTitle(stitle);
                title1.setSubTitle(ssubtitle);
                title1.setFadeIn(fadein);
                title1.setStay(stay);
                title1.setFadeOut(fadeout);
                title1.sendTitle(players);
            }
        } else {
            for (Player player : players) {
                player.sendTitle(stitle, ssubtitle, fadein, stay, fadeout);
            }
        }
    }
}
