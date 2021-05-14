package me.AhmHkn.Repair;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.command.CommandSender;




public final class Main extends JavaPlugin {

    public static Main plugin;
    public static Economy econ = null;

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        this.getCommand("tamir").setExecutor(new Repair());
        getServer().getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "TamirEt eklentisi kuruldu.");

    }
    public void onDisable() {
        this.saveConfig();
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[TamirEt] Config kaydedildi.");
    }
    public static void msg(Player p, String cfg) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("mesajlar.prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(cfg)));
    }
    public static void msgc(CommandSender s, String cfg) {
        s.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("mesajlar.prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(cfg)));
    }
    public static Boolean giyilenZirhlar() {
        return plugin.getConfig().getBoolean("ayarlar.giyilenzirhlar");
    }
    public static Integer eltamircost() {
        return plugin.getConfig().getInt("ayarlar.eltamircost");
    }
    public static Integer fulltamircost() {
        return plugin.getConfig().getInt("ayarlar.fulltamircost");
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
