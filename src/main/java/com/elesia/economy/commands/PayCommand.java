package com.elesia.economy.commands;

import com.elesia.economy.ElesiaEconomy;
import com.elesia.economy.api.IStockage;
import com.elesia.economy.exception.EconomyException;
import com.elesia.economy.manager.EconomyMessage;
import com.elesia.economy.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de la commande /pay (Raccourci de la commande /money pay)
 */
public class PayCommand implements CommandExecutor, TabCompleter {

    private final IStockage stockage = ElesiaEconomy.getInstance().getSqlBridge().getStockage();

    /*
            /pay <joueur> <montant> -> Donner de l'argent à un joueur
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            if(!player.hasPermission("money.pay")){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.NO_PERMISSION));
            }else {
                if (strings.length != 2) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.COMMAND_PAY_USAGE));
                } else {
                    Player target = Bukkit.getPlayer(strings[0]);
                    if (target == null) { // Le joueur n'est pas connecté
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.PLAYER_IS_NOT_ONLINE));
                    } else {
                        // On vérifie si le montant est bien un nombre
                        try {
                            double montant = Double.parseDouble(strings[1]);
                            if (montant <= 0) { // Le montant est inférieur ou égal à 0
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.AMOUNT_INCORRECT));
                            } else {
                                try {
                                    if (stockage.getBalance(player.getUniqueId()) < montant) { // Le solde du joueur est inférieur au montant qu'il souhaite donner
                                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.INSUFFICIENT_BALANCE));
                                    } else {
                                        stockage.addMoney(target.getUniqueId(), montant); // Ajout du montant sur le solde du target
                                        stockage.removeMoney(player.getUniqueId(), montant); // Retrait du montant sur le solde du joueur

                                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.PAYMENT_SENT_TO
                                                .replace("{money}", FormatUtils.formatCurrency(montant)
                                                        .replace("{targetName}", target.getName()))));
                                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.PAYMENT_RECEIVED_FROM
                                                .replace("{money}", FormatUtils.formatCurrency(montant)
                                                        .replace("{targetName}", player.getName()))));
                                    }
                                } catch (EconomyException err) {
                                    // Une erreur est survenue
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.INTERNAL_ERROR));
                                    err.printStackTrace();
                                }
                            }
                        } catch (NumberFormatException err) {
                            // Le montant n'est pas un nombre
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.AMOUNT_IS_NOT_A_NUMBER));
                        }
                    }
                }
            }
        }else{
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.NO_POSSIBLE));
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> playersName = new ArrayList<>();
        if(strings.length == 1){
            Bukkit.getOnlinePlayers().forEach(player -> playersName.add(player.getName()));
        }
        return playersName;
    }
}
