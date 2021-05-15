package me.AhmHkn.RepairIt;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getServer;

public class RepairIt implements CommandExecutor {
    public static Economy econ = null;



    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        econ = rsp.getProvider();
        if (s.equalsIgnoreCase("tamir") || s.equalsIgnoreCase("tamiret:tamir") || s.equalsIgnoreCase("repair") || s.equalsIgnoreCase("tamiret:repair")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                {
                    if (args.length == 0) {
                        if (Main.plugin.getConfig().getString("settings.locale").equalsIgnoreCase("tr")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2TamirEt Plugini v" + Main.plugin.getDescription().getVersion() + " yapımcı: &6" + Main.plugin.getDescription().getAuthors()));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Komutlar:"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/tamir et&7|&6hand&7|&6el &a" + Main.plugin.getConfig().getInt("settings.handrepaircost") + " TL"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/tamir hepsi&7|&6all &a" + Main.plugin.getConfig().getInt("settings.allrepaircost") + " TL"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/tamir reload &7&oSadece yetkililer"));
                        }else if (Main.plugin.getConfig().getString("settings.locale").equalsIgnoreCase("en")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2TamirEt Plugin v" + Main.plugin.getDescription().getVersion() + " Author: &6" + Main.plugin.getDescription().getAuthors()));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Commands:"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/repair et&7|&6hand&7|&6el &a$" + Main.plugin.getConfig().getInt("settings.handrepaircost")));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/repair hepsi&7|&6all &a$" + Main.plugin.getConfig().getInt("settings.allrepaircost")));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/repair reload &7&oOnly Operators"));
                        }
                    } else if (args[0].equalsIgnoreCase("el") || args[0].equalsIgnoreCase("hand") || args[0].equalsIgnoreCase("et")){
                        if (p.getItemInHand().getType().getMaxDurability() != 0) {
                            if (p.getItemInHand().getDurability() > 0) {
                                EconomyResponse r = econ.withdrawPlayer(p, Main.handrepaircost());
                                if (r.transactionSuccess()) {
                                    p.getInventory().getItemInHand().setDurability((short) 0);
                                    Main.msg(p, Main.locale + ".messages.tamiredildi");
                                } else {
                                    Main.msg(p, Main.locale + ".messages.yetersizbakiye");
                                }
                            } else {
                                Main.msg(p, Main.locale + ".messages.hasarsiz");
                            }
                        } else {
                            Main.msg(p, Main.locale + ".messages.tamiredilemez");
                            return true;
                        }
                }else if (args[0].equalsIgnoreCase("hepsi") || args[0].equalsIgnoreCase("all")){
                        EconomyResponse r = econ.withdrawPlayer(p, Main.allrepaircost());
                        if (r.transactionSuccess()) {
                            if (!Main.plugin.getConfig().getBoolean("settings.armors")) {
                                Main.msg(p, Main.locale + ".messages.fulltamiredildi");
                                for (int i = 0; i <= 35; i++) {
                                    try {
                                        if (p.getInventory().getItem(i).getType().getMaxDurability() != 0) {
                                            p.getInventory().getItem(i).setDurability((short) 0);
                                        }
                                    } catch (Exception ignored) {

                                    }
                                }
                            } else if (Main.plugin.getConfig().getBoolean("settings.armors")) {
                                Main.msg(p, Main.locale + ".messages.fulltamiredildigiyilenzirhlar");
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
                            Main.msg(p, Main.locale + ".messages.yetersizbakiye");
                        }
                    }else if (args[0].equalsIgnoreCase("reload")) {
                        if (p.isOp()) {
                            Main.plugin.reloadConfig();
                            Main.locale = Main.plugin.getConfig().getString("settings.locale");
                            if (!Main.locale.equalsIgnoreCase("tr") && !Main.locale.equalsIgnoreCase("en")) {
                                getServer().getConsoleSender().sendMessage(ChatColor.RED + "[TamirEt] Unable locale. Only supports: 'tr' and 'en'");
                                sender.sendMessage((ChatColor.RED + "[TamirEt] Unable locale. Only supports: 'tr' and 'en'"));
                                return false;
                            }
                            Main.msgc(sender, Main.locale + ".messages.reload");
                            getServer().getConsoleSender().sendMessage("[TamirEt] Eklenti ayarları yeniden yüklendi!");
                        }else if (!p.isOp()){
                            Main.msg(p, Main.locale + ".messages.yetkisiz");
                        }
                    }else{
                        if (Main.plugin.getConfig().getString("settings.locale").equalsIgnoreCase("tr")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2TamirEt Plugini v" + Main.plugin.getDescription().getVersion() + " yapımcı: &6" + Main.plugin.getDescription().getAuthors()));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Komutlar:"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/tamir et&7|&6hand&7|&6el &a" + Main.plugin.getConfig().getInt("settings.handrepaircost") + " TL"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/tamir hepsi&7|&6all &a" + Main.plugin.getConfig().getInt("settings.allrepaircost") + " TL"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/tamir reload &7&oSadece yetkililer"));
                        }else if (Main.plugin.getConfig().getString("settings.locale").equalsIgnoreCase("en")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2TamirEt Plugin v" + Main.plugin.getDescription().getVersion() + " Author: &6" + Main.plugin.getDescription().getAuthors()));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Commands:"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/repair et&7|&6hand&7|&6el &a$" + Main.plugin.getConfig().getInt("settings.handrepaircost")));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/repair hepsi&7|&6all &a$" + Main.plugin.getConfig().getInt("settings.allrepaircost")));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/repair reload &7&oOnly Operators"));
                        }
                    }
                }
            }else if(args.length > 0){
                if (args[0].equalsIgnoreCase("reload")){
                    Main.plugin.reloadConfig();
                    Main.locale = Main.plugin.getConfig().getString("settings.locale");
                    if (!Main.locale.equalsIgnoreCase("tr") && !Main.locale.equalsIgnoreCase("en")) {
                        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[TamirEt] Unable locale. Only supports: 'tr' and 'en'");
                        sender.sendMessage((ChatColor.RED + "[TamirEt] Unable locale. Only supports: 'tr' and 'en'"));
                        return false;
                    }
                    Main.msgc(sender, Main.locale + ".messages.reload");
                    getServer().getConsoleSender().sendMessage("[TamirEt] Eklenti ayarları yeniden yüklendi!");
                }else {
                    sender.sendMessage(ChatColor.RED + "[TamirEt] Bu komut konsoldan kullanilamaz!");
                }
            }
        }
        return false;
    }

}
