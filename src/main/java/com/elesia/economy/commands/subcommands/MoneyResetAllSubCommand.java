package com.elesia.economy.commands.subcommands;

import com.elesia.economy.ElesiaEconomy;
import com.elesia.economy.api.AbstractCommand;
import com.elesia.economy.api.IStockage;
import com.elesia.economy.exception.EconomyException;
import com.elesia.economy.manager.EconomyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MoneyResetAllSubCommand extends AbstractCommand {

    private final IStockage stockage = ElesiaEconomy.getInstance().getSqlBridge().getStockage();

    public MoneyResetAllSubCommand(Map<String, AbstractCommand> mainCommand){
        super(mainCommand, "resetall");
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] arguments) {
        if(!(commandSender instanceof ConsoleCommandSender)){ // Seul la console peut faire cette commande !
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.CONSOLE_ONLY));
        }else{
            // Il s'agit de la console
            int numberAccountReset = 0, error = 0;
            //Récupération de la liste des comptes
            for(UUID uuid : this.stockage.getAccounts()){
                OfflinePlayer target = Bukkit.getOfflinePlayer(uuid);
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.RESET_ACCOUNT_TO
                .replace("{targetName}", target.getName() == null ? "&cInconnu" : target.getName())));
                try{
                    this.stockage.setMoney(uuid, 0); //Définition du solde du joueur à 0
                    numberAccountReset ++;
                }catch (EconomyException err){
                    error ++;
                    err.printStackTrace();
                }
            }
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.TOTAL_ACCOUNT_RESET
            .replace("{success}", String.valueOf(numberAccountReset))
            .replace("{error}", String.valueOf(error))));
        }
    }

    @Override
    public List<String> getTabCompleter(CommandSender commandSender, String[] arguments) {
        return Collections.emptyList();
    }
}
