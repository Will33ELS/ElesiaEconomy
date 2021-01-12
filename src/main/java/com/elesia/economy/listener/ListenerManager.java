package com.elesia.economy.listener;

import com.elesia.economy.listener.player.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ListenerManager {

    /**
     * Enregistrement des classes Listeners
     * @param plugin
     */
    public void registerListener(JavaPlugin plugin){

        PluginManager pluginManager = Bukkit.getPluginManager();

        //PLAYER
        pluginManager.registerEvents(new PlayerJoin(), plugin);

    }

}
