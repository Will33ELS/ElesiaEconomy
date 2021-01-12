package com.elesia.economy.commands;

import com.elesia.economy.api.AbstractCommand;
import com.elesia.economy.commands.subcommands.*;
import org.bukkit.command.*;

import java.util.*;

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
        new MoneyDeleteAllSubCommand(subCommands);
        new MoneyDeleteSubCommand(subCommands);
        new MoneyGiveSubCommand(subCommands);
        new MoneyHelpSubCommand(subCommands);
        new MoneyPaySubCommand(subCommands);
        new MoneyResetAllSubCommand(subCommands);
        new MoneySetSubCommand(subCommands);
        new MoneyTakeSubCommand(subCommands);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 0){
            subCommands.get("balance").onCommand(commandSender, strings);
        }else{
            if(subCommands.containsKey(strings[0].toLowerCase())){
                subCommands.get(strings[0].toLowerCase()).onCommand(commandSender, strings);
            }else{
                subCommands.get("help").onCommand(commandSender, strings);
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> args = new ArrayList<>();
        if(strings.length == 1){
            if(commandSender.hasPermission("money.balance"))
                args.add("balance");
            if(commandSender.hasPermission("money.create"))
                args.add("create");
            if(commandSender.hasPermission("money.delete"))
                args.add("delete");
            if(commandSender.hasPermission("money.give"))
                args.add("give");
            args.add("help");
            if(commandSender.hasPermission("money.pay"))
                args.add("pay");
            if(commandSender.hasPermission("money.set"))
                args.add("set");
            if(commandSender.hasPermission("money.take"))
                args.add("take");
            if(commandSender instanceof ConsoleCommandSender){
                args.add("resetall");
                args.add("deleteall");
            }
        }else if(strings.length > 1)
            args = subCommands.containsKey(strings[0].toLowerCase()) ? subCommands.get(strings[0].toLowerCase()).getTabCompleter(commandSender, strings) : Collections.emptyList();
        return args;
    }
}
