package com.elesia.economy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

/**
 * Classe de la commande /pay (Raccourci de la commande /money pay)
 */
public class PayCommand implements CommandExecutor, TabCompleter {

    /*
            /pay <joueur> <montant> -> Donner de l'argent à un joueur
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
