package com.elesia.economy.commands.subcommands;

import com.elesia.economy.ElesiaEconomy;
import com.elesia.economy.api.AbstractCommand;
import com.elesia.economy.api.IStockage;
import com.elesia.economy.exception.EconomyException;
import com.elesia.economy.manager.EconomyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MoneyCreateCommand extends AbstractCommand {

    private final IStockage stockage = ElesiaEconomy.getInstance().getSqlBridge().getStockage();

    public MoneyCreateCommand(Map<String, AbstractCommand> mainCommand){
        super(mainCommand, "create");
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] arguments) {
        if(!commandSender.hasPermission("money.create")){
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.NO_PERMISSION));
        }else{
            if(arguments.length != 2){
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.CREATE_COMMAND_USAGE));
            }else{
                Player target = Bukkit.getPlayer(arguments[1]);
                if(target == null){ // Le target n'est pas connecté
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.PLAYER_IS_NOT_ONLINE));
                }else{
                    if(stockage.isAccountExist(target.getUniqueId())){
                        // Le target a déjà un compte
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.PLAYER_HAS_ACCOUNT));
                    }else{
                        try{
                            stockage.createAccount(target.getUniqueId()); // Création du compte
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.CREATE_ACCOUNT_TO
                                    .replace("{targetName}", target.getName())));
                        }catch (EconomyException err){
                            // Une erreur est survenue
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.INTERNAL_ERROR));
                            err.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<String> getTabCompleter(CommandSender commandSender, String[] arguments) {
        return Collections.emptyList();
    }
}
