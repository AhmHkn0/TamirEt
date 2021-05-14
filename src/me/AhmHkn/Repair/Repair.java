package me.AhmHkn.Repair;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getServer;

public class Repair implements CommandExecutor {
    public static Economy econ = null;



    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        econ = rsp.getProvider();
        if (s.equalsIgnoreCase("tamir")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                {
                    if (args.length == 0) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2TamirEt Plugini v" + Main.plugin.getDescription().getVersion() + " yapımcı: &6" + Main.plugin.getDescription().getAuthors()));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Komutlar:"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/tamir et&7|&6hand&7|&6el"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/tamir hepsi&7|&6all"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/tamir reload &7&oSadece yetkililer"));
                    } else if (args[0].equalsIgnoreCase("el") || args[0].equalsIgnoreCase("hand") || args[0].equalsIgnoreCase("et")){
                        if (p.getItemInHand().getType().getMaxDurability() != 0) {
                            if (p.getItemInHand().getDurability() > 0) {
                                EconomyResponse r = econ.withdrawPlayer(p, Main.eltamircost());
                                if (r.transactionSuccess()) {
                                    p.getInventory().getItemInHand().setDurability((short) 0);
                                    Main.msg(p, "mesajlar.tamiredildi");
                                } else {
                                    Main.msg(p, "mesajlar.yetersizbakiye");
                                }
                            } else {
                                Main.msg(p, "mesajlar.hasarsiz");
                            }
                        } else {
                            Main.msg(p, "mesajlar.tamiredilemez");
                            return true;
                        }
                }else if (args[0].equalsIgnoreCase("hepsi") || args[0].equalsIgnoreCase("all")){
                        EconomyResponse r = econ.withdrawPlayer(p, Main.fulltamircost());
                        if (r.transactionSuccess()) {
                            if (!Main.plugin.getConfig().getBoolean("ayarlar.giyilenzirhlar")) {
                                Main.msg(p, "mesajlar.fulltamiredildi");
                                for (int i = 0; i <= 35; i++) {
                                    try {
                                        if (p.getInventory().getItem(i).getType().getMaxDurability() != 0) {
                                            p.getInventory().getItem(i).setDurability((short) 0);
                                        }
                                    } catch (Exception ignored) {

                                    }
                                }
                            } else if (Main.plugin.getConfig().getBoolean("ayarlar.giyilenzirhlar")) {
                                Main.msg(p, "mesajlar.fulltamiredildigiyilenzirhlar");
                                for (int i = 0; i <= 39; i++) {
                                    try {
                                        if (p.getInventory().getItem(i).getType().getMaxDurability() != 0) {
                                            p.getInventory().getItem(i).setDurability((short) 0);
                                        }
                                    } catch (Exception ignored) {

                                    }
                                }
                            }
                        }else{
                            Main.msg(p, "mesajlar.yetersizbakiye");
                        }
                    }else if (args[0].equalsIgnoreCase("reload")) {
                        if (p.isOp()) {
                            Main.plugin.reloadConfig();
                            Main.msgc(sender, "mesajlar.reload");
                            getServer().getConsoleSender().sendMessage("[TamirEt] Eklenti ayarları yeniden yüklendi!");
                        }else if (!p.isOp()){
                            Main.msg(p, "mesajlar.yetkisiz");
                        }
                    }else{
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2TamirEt Plugini v" + Main.plugin.getDescription().getVersion() + " yapımcı: &6" + Main.plugin.getDescription().getAuthors()));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Komutlar:"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/tamir et&7|&6hand&7|&6el"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/tamir hepsi&7|&6all"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/tamir reload &7&oSadece yetkililer"));
                    }
                }
            }else if(args.length > 0){
                if (args[0].equalsIgnoreCase("reload")){
                    Main.plugin.reloadConfig();
                    Main.msgc(sender, "mesajlar.reload");
                    getServer().getConsoleSender().sendMessage("[TamirEt] Eklenti ayarları yeniden yüklendi!");
                }else {
                    sender.sendMessage(ChatColor.RED + "[TamirEt] Bu komut konsoldan kullanilamaz!");
                }
            }
        }
        return false;
    }

}
