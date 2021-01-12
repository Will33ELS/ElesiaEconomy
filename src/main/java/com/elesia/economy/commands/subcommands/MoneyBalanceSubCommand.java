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
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MoneyBalanceSubCommand extends AbstractCommand {

    private final IStockage stockage = ElesiaEconomy.getInstance().getSqlBridge().getStockage();

    public MoneyBalanceSubCommand(Map<String, AbstractCommand> mainCommand){
        super(mainCommand, "balance");
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] arguments) {
        if(arguments.length <= 1){
            sendMoney(commandSender);
        }else{
            //On vérifie si le joueur à la permission de regarder le solde des autres joueurs
            if(commandSender.hasPermission("money.balance.other")){
                //Il a la permission, on envoie le solde du compte target
                OfflinePlayer target = Bukkit.getOfflinePlayer(arguments[1]);
                if(!stockage.isAccountExist(target.getUniqueId())){ // Le compte du joueur n'existe pas
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.PLAYER_NO_ACCOUNT));
                }else{
                    try{
                        // Envoie du solde du compte du joueur visé
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX +
                                EconomyMessage.SOLDE_OTHER_PLAYER
                                        .replace("{playerName}", target.getName() == null ? "&cInconnu" : target.getName())
                                        .replace("{money}", FormatUtils.formatCurrency(stockage.getBalance(target.getUniqueId())))
                        ));
                    }catch (EconomyException err){
                        // Une erreur à eu lieu
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.INTERNAL_ERROR));
                        err.printStackTrace();
                    }
                }
            }else{
                sendMoney(commandSender);
            }
        }
    }

    @Override
    public List<String> getTabCompleter(CommandSender commandSender, String[] arguments) {
        if(commandSender.hasPermission("money.balance.other")){
            List<String> playersName = new ArrayList<>();
            Bukkit.getOnlinePlayers().forEach(player -> playersName.add(player.getName()));
            return playersName;
        }
        return Collections.emptyList();
    }

    /**
     * Envoie du message contenant le solde du compte de l'exécuteur
     * @param commandSender Exécuteur
     */
    private void sendMoney(CommandSender commandSender){
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            try{
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.SOLDE_PLAYER
                        .replace("{money}", FormatUtils.formatCurrency(stockage.getBalance(player.getUniqueId())))
                ));
            }catch (EconomyException err){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.INTERNAL_ERROR ));
                err.printStackTrace();
            }
        }else{
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.NO_POSSIBLE));
        }
    }
}
