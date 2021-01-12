package com.elesia.economy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

/**
 * Classe de la commande principale /money [args]...
 */
public class MoneyCommand implements CommandExecutor, TabCompleter {

    /*
        ================================================================================================

        /money -> Retourne le solde du compte du joueur
        /money pay <joueur> <montant> -> Donner de l'argent à un joueur mais retiré de son compte
        /money give <joueur> <montant> -> Donner de l'argent à un joueur
        /money take <joueur> <montant> -> Retirer de l'argent à un joueur
        /money create <joueur> -> Créer le compte d'un joueur manuellement
        /money reset <joueur> -> Reset le compte d'un joueur manuellement
        /money delete <joueur> -> Supprimer le compte d'un joueur manuellement
        /money resetAll -> Reset l'intégralité des comptes joueurs (CONSOLE ONLY)
        /money deleteAll -> Supprimer l'intégralité des comptes joueurs (CONSOLE ONLY)

        ================================================================================================
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
