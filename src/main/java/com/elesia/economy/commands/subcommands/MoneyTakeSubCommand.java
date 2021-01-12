package com.elesia.economy.commands.subcommands;

import com.elesia.economy.ElesiaEconomy;
import com.elesia.economy.api.AbstractCommand;
import com.elesia.economy.api.IStockage;
import com.elesia.economy.exception.EconomyException;
import com.elesia.economy.manager.EconomyMessage;
import com.elesia.economy.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class MoneyTakeSubCommand extends AbstractCommand {

    private final IStockage stockage = ElesiaEconomy.getInstance().getSqlBridge().getStockage();

    public MoneyTakeSubCommand(Map<String, AbstractCommand> mainCommand){
        super(mainCommand, "take");
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] arguments) {
        if(!commandSender.hasPermission("money.take")){
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.NO_PERMISSION));
        }else{
            if(arguments.length != 3){
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.TAKE_COMMAND_USAGE));
            }else{
                OfflinePlayer target = Bukkit.getOfflinePlayer(arguments[1]); // Récupération du target
                if(!stockage.isAccountExist(target.getUniqueId())){ // On vérifie que le compte existe
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.PLAYER_NO_ACCOUNT));
                }else{
                    // On vérifie si le montant est bien un nombre
                    try{
                        double montant = Double.parseDouble(arguments[2]);
                        if(montant <= 0){
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.AMOUNT_INCORRECT));
                        }else{
                            try{
                                if(stockage.getBalance(target.getUniqueId()) < montant){
                                    // Le target n'a pas assez d'argent sur son compte
                                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.PLAYER_INSUFFICIENT_BALANCE));
                                }else {
                                    stockage.removeMoney(target.getUniqueId(), montant); // On retire le montant sur le compte du target
                                    if (target.getPlayer() != null) {
                                        // Le target est connecté
                                        target.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.YOU_LOST.replace("{money}", FormatUtils.formatCurrency(montant))));
                                    }
                                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.YOU_TAKE_TO
                                            .replace("{money}", FormatUtils.formatCurrency(montant)
                                                    .replace("{targetName}", target.getName() == null ? "&cInconnu" : target.getName()))));
                                }
                            }catch (EconomyException err){
                                // Une erreur est survenue
                                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.INTERNAL_ERROR));
                                err.printStackTrace();
                            }
                        }
                    }catch (NumberFormatException err){
                        // Le montant n'est pas un nombre
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.AMOUNT_IS_NOT_A_NUMBER));
                    }
                }
            }
        }
    }
}
