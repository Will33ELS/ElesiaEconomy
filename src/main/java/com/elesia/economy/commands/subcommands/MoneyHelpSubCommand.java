package com.elesia.economy.commands.subcommands;

import com.elesia.economy.api.AbstractCommand;
import com.elesia.economy.manager.EconomyMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.Map;

public class MoneyHelpSubCommand extends AbstractCommand {

    public MoneyHelpSubCommand(Map<String, AbstractCommand> mainCommand){
        super(mainCommand, "help", "h");
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] arguments) {
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.HELP_HEADER));
        if(commandSender.hasPermission("money.balance"))
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.HELP_BALANCE));
        if(commandSender.hasPermission("money.balance.other"))
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.HELP_BALANCE_OTHER));
        if(commandSender.hasPermission("money.pay"))
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.HELP_PAY));
        if(commandSender.hasPermission("money.give"))
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.HELP_GIVE));
        if(commandSender.hasPermission("money.take"))
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.HELP_TAKE));
        if(commandSender.hasPermission("money.set"))
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.HELP_SET));
        if(commandSender.hasPermission("money.create"))
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.HELP_CREATE));
        if(commandSender.hasPermission("money.delete"))
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.HELP_DELETE));
        if(commandSender instanceof ConsoleCommandSender)
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.HELP_RESET_ALL));
        if(commandSender instanceof ConsoleCommandSender)
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.HELP_DELETE_ALL));
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.HELP_FOOTER));
    }
}
