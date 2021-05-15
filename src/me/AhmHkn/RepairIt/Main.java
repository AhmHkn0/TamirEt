package me.AhmHkn.RepairIt;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.CommandSender;



public final class Main extends JavaPlugin {

    public static Main plugin;
    public static String locale;

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        locale = Main.plugin.getConfig().getString("settings.locale");
        if (!locale.equalsIgnoreCase("tr") || !locale.equalsIgnoreCase("en")) {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "Unable locale. Only supports: 'tr' and 'en'");
        }
        this.getCommand("tamir").setExecutor(new RepairIt());
        this.getCommand("repair").setExecutor(new RepairIt());
        getServer().getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "TamirEt enabled.");

    }
    public static void msg(Player p, String cfg) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(cfg)));
    }
    public static void msgc(CommandSender s, String cfg) {
        s.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(cfg)));
    }
    public static Integer handrepaircost() {
        return plugin.getConfig().getInt("settings.handrepaircost");
    }
    public static Integer allrepaircost() {
        return plugin.getConfig().getInt("settings.allrepaircost");
    }



}
