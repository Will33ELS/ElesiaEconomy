package com.elesia.economy.api;

import org.bukkit.command.CommandSender;

import java.util.Map;

public abstract class AbstractCommand {

    /**
     * Enregistrement d'une subcommand
     * @param subCommands Le tableau contenant les subcommands de la commande Main
     * @param subCommand La valeur de la subCommand
     * @param aliases Les aliases possibles de la subCommand
     */
    public AbstractCommand(Map<String, AbstractCommand> subCommands, String subCommand, String... aliases){
        subCommands.put(subCommand, this);
        for(String aliase : aliases)
            subCommands.put(aliase, this);
    }

    /**
     * Fonction appelé lors de l'exécution de la subCommand ou de ses aliases
     * @param commandSender L'exécuteur de la commande
     * @param arguments Les arguments de la commande
     */
    public abstract void onCommand(CommandSender commandSender, String[] arguments);

}
