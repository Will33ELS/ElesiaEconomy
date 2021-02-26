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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MoneyDeleteSubCommand extends AbstractCommand {

    private final IStockage stockage = ElesiaEconomy.getInstance().getSqlBridge().getStockage();

    public MoneyDeleteSubCommand(Map<String, AbstractCommand> mainCommand){
        super(mainCommand, "delete");
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] arguments) {
        if(!commandSender.hasPermission("money.delete")){
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.NO_PERMISSION));
        }else{
            if(arguments.length != 2){
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.DELETE_COMMAND_USAGE));
            }else{
                OfflinePlayer target = Bukkit.getOfflinePlayer(arguments[1]); // Récupération du target
                if(!stockage.isAccountExist(target.getUniqueId())){ // On vérifie que le compte existe
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.PLAYER_NO_ACCOUNT));
                }else{
                    try{
                        if(target.getPlayer() != null){
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.DELETE_ACCOUNT_IMPOSSIBLE));
                        }else{
                            stockage.deleteAccount(target.getUniqueId()); // On supprime le compte du target
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.YOU_DELETE_ACCOUNT_TO
                            .replace("{targetName}", target.getName() == null ? "&cInconnu" : target.getName())));
                        }
                    }catch (EconomyException err){
                        // Une erreur est survenue
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.INTERNAL_ERROR));
                        err.printStackTrace();
                    }
                }
            }
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
