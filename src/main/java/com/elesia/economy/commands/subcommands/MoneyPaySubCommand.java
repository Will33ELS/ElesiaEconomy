package com.elesia.economy.commands.subcommands;

import com.elesia.economy.ElesiaEconomy;
import com.elesia.economy.api.AbstractCommand;
import com.elesia.economy.api.IStockage;
import com.elesia.economy.exception.EconomyException;
import com.elesia.economy.manager.EconomyMessage;
import com.elesia.economy.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MoneyPaySubCommand extends AbstractCommand {

    private final IStockage stockage = ElesiaEconomy.getInstance().getSqlBridge().getStockage();

    public MoneyPaySubCommand(Map<String, AbstractCommand> mainCommand){
        super(mainCommand, "pay");
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] arguments) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            if(!player.hasPermission("money.pay")){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.NO_PERMISSION));
            }else {
                if (arguments.length != 3) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.PAY_COMMAND_USAGE));
                } else {
                    Player target = Bukkit.getPlayer(arguments[1]);
                    if (target == null) { // Le joueur n'est pas connecté
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.PLAYER_IS_NOT_ONLINE));
                    } else {
                        // On vérifie si le montant est bien un nombre
                        try {
                            double montant = Double.parseDouble(arguments[2]);
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
                                                .replace("{money}", FormatUtils.formatCurrency(montant))
                                                        .replace("{fromName}", player.getName())));
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
    }

    @Override
    public List<String> getTabCompleter(CommandSender commandSender, String[] arguments) {
        List<String> playersName = new ArrayList<>();
        if(arguments.length == 2){
            Bukkit.getOnlinePlayers().forEach(player -> playersName.add(player.getName()));
        }
        return playersName;
    }
}
