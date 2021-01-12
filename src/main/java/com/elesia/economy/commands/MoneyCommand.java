package com.elesia.economy.commands;

import com.elesia.economy.api.AbstractCommand;
import com.elesia.economy.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe de la commande principale /money [args]...
 */
public class MoneyCommand implements CommandExecutor, TabCompleter {

    /*
        =====================================================================================================================

        /money -> Retourne le solde du compte du joueur
        /money balance [joueur] -> Retourne le solde du compte joueur ou d'un autre compte si l'argument est renseigné
        /money pay <joueur> <montant> -> Donner de l'argent à un joueur mais retiré de son compte
        /money give <joueur> <montant> -> Donner de l'argent à un joueur
        /money take <joueur> <montant> -> Retirer de l'argent à un joueur
        /money set <joueur> <montant> -> Définir l'argent d'un joueur
        /money create <joueur> -> Créer le compte d'un joueur manuellement
        /money reset <joueur> -> Reset le compte d'un joueur manuellement
        /money delete <joueur> -> Supprimer le compte d'un joueur manuellement
        /money resetAll -> Reset l'intégralité des comptes joueurs (CONSOLE ONLY)
        /money deleteAll -> Supprimer l'intégralité des comptes joueurs (CONSOLE ONLY)
        /money help -> Commande d'aide

        permissions:
          money.<subcommand>

        =====================================================================================================================
     */
    private final Map<String, AbstractCommand> subCommands = new HashMap<>();
    public MoneyCommand(){
        new MoneyBalanceSubCommand(subCommands);
        new MoneyCreateCommand(subCommands);
        new MoneyGiveSubCommand(subCommands);
        new MoneyPaySubCommand(subCommands);
        new MoneySetSubCommand(subCommands);
        new MoneyTakeSubCommand(subCommands);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
