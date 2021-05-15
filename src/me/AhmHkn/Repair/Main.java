package me.AhmHkn.Repair;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.command.CommandSender;




public final class Main extends JavaPlugin {

    public static Main plugin;
    public static String locale;
    public static Economy econ = null;

    @Override
    public void onEnable() {
        plugin = this;
        locale = Main.plugin.getConfig().getString("settings.locale");
        if (!locale.equalsIgnoreCase("tr") || !locale.equalsIgnoreCase("en")) {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "Unable locale. Only supports: 'tr' and 'en'");
        }
        this.saveDefaultConfig();
        this.getCommand("tamir").setExecutor(new Repair());
        this.getCommand("repair").setExecutor(new Repair());
        getServer().getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "TamirEt enabled.");

    }
    public void onDisable() {
        this.saveConfig();
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[TamirEt] Config saved.");
    }
    public static void msg(Player p, String cfg) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(cfg)));
    }
    public static void msgc(CommandSender s, String cfg) {
        s.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(cfg)));
    }
    public static Boolean armors() {
        return plugin.getConfig().getBoolean("settings.armors");
    }
    public static Integer handrepaircost() {
        return plugin.getConfig().getInt("settings.handrepaircost");
    }
    public static Integer allrepaircost() {
        return plugin.getConfig().getInt("settings.allrepaircost");
    }

    public boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }



}
