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

import java.util.Map;
import java.util.UUID;

public class MoneyDeleteAllSubCommand extends AbstractCommand {

    private final IStockage stockage = ElesiaEconomy.getInstance().getSqlBridge().getStockage();

    public MoneyDeleteAllSubCommand(Map<String, AbstractCommand> mainCommand){
        super(mainCommand, "deleteall");
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] arguments) {
        if(!(commandSender instanceof ConsoleCommandSender)){ // Seul la console peut faire cette commande !
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.CONSOLE_ONLY));
        }else{
            // Il s'agit de la console
            int numberAccountDelete = 0, error = 0;
            //Récupération de la liste des comptes
            for(UUID uuid : this.stockage.getAccounts()){
                OfflinePlayer target = Bukkit.getOfflinePlayer(uuid);
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.YOU_DELETE_ACCOUNT_TO
                        .replace("{targetName}", target.getName() == null ? "&cInconnu" : target.getName())));
                try{
                    if(target.getPlayer() != null){
                        // Le joueur est connecté, on le kick
                        target.getPlayer().kickPlayer(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.KICK_PLAYER_DELETE));
                    }
                    this.stockage.deleteAccount(uuid); //Suppression du compte
                    numberAccountDelete ++;
                }catch (EconomyException err){
                    error ++;
                    err.printStackTrace();
                }
            }
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.TOTAL_ACCOUNT_DELETE
                    .replace("{success}", String.valueOf(numberAccountDelete))
                    .replace("{error}", String.valueOf(error))));
        }
    }
}
