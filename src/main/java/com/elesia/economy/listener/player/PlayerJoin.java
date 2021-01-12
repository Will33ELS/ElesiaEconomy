package com.elesia.economy.listener.player;

import com.elesia.economy.ElesiaEconomy;
import com.elesia.economy.exception.EconomyException;
import com.elesia.economy.manager.EconomyMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    /**
     * Listener appelé pour la création du compte joueur
     * @param event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        try {
            //Si le joueur ne dispose pas de compte, on le créer
            if (!ElesiaEconomy.getInstance().getSqlBridge().getStockage().isAccountExist(player.getUniqueId()))
                ElesiaEconomy.getInstance().getSqlBridge().getStockage().createAccount(player.getUniqueId());
        }catch (EconomyException err){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', EconomyMessage.PREFIX + EconomyMessage.ACCOUNT_CANT_CREATE));
            err.printStackTrace();
        }
    }

}
