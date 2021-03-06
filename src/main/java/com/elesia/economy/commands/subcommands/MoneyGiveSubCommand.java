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
import java.util.List;
import java.util.Map;

public class MoneyGiveSubCommand extends AbstractCommand {

    private final IStockage stockage = ElesiaEconomy.getInstance().getSqlBridge().getStockage();

    public MoneyGiveSubCommand(Map<String, AbstractCommand> mainCommand){
        super(mainCommand, "give");
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] arguments) {
        if((commandSender instanceof Player) && !commandSender.hasPermission("money.give")){
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.NO_PERMISSION));
        }else{
            if(arguments.length != 3){
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.GIVE_COMMAND_USAGE));
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
                                stockage.addMoney(target.getUniqueId(), montant); // On ajoute le montant sur le compte du target
                                if(target.getPlayer() != null){
                                    // Le target est connecté
                                    target.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.YOU_RECEIVED.replace("{money}", FormatUtils.formatCurrency(montant))));
                                }
                                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.YOU_GIVE_TO
                                        .replace("{money}", FormatUtils.formatCurrency(montant))
                                                .replace("{targetName}", target.getName() == null ? "&cInconnu" : target.getName())));
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

    @Override
    public List<String> getTabCompleter(CommandSender commandSender, String[] arguments) {
        List<String> playersName = new ArrayList<>();
        if(arguments.length == 2){
            Bukkit.getOnlinePlayers().forEach(player -> playersName.add(player.getName()));
        }
        return playersName;
    }
}
