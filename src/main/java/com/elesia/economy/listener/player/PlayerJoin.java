package com.elesia.economy.listener.player;

import com.elesia.economy.ElesiaEconomy;
import com.elesia.economy.event.EconomyPlayerJoinEvent;
import com.elesia.economy.exception.EconomyException;
import com.elesia.economy.manager.EconomyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    /**
     * Listener appelé en dernier, utilisé pour la définition de l'ID du joueur
     * @param event
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        EconomyPlayerJoinEvent economyPlayerJoinEvent = new EconomyPlayerJoinEvent(player.getUniqueId());
        Bukkit.getPluginManager().callEvent(economyPlayerJoinEvent);
        if(economyPlayerJoinEvent.getPlayerID() != -1){
            //L'ID retourné par l'événement ajouté dans la map afin de faire la relation entre l'UUID du joueur et l'ID unique généré par la plateforme utilisant le plugin
            ElesiaEconomy.getInstance().getPlayerIDs().put(player.getUniqueId(), economyPlayerJoinEvent.getPlayerID());

            //Si le joueur n'a pas de compte, on le créer
            try{
                if(!ElesiaEconomy.getInstance().getSqlBridge().getStockage().isAccountExist(economyPlayerJoinEvent.getPlayerID()))
                    ElesiaEconomy.getInstance().getSqlBridge().getStockage().createAccount(economyPlayerJoinEvent.getPlayerID());
            }catch (EconomyException err){
                //Un incident a eu lieu lors de la création du compte
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',EconomyMessage.PREFIX + EconomyMessage.ACCOUNT_CANT_CREATE));
                err.printStackTrace();
            }
        }
    }

}
