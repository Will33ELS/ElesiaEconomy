package com.elesia.economy.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

/**
 * Evénement appelé lors de la connexion du joueur, il permet à la plateforme utilisant ce plugin de définir l'ID unique du joueur
 */
public class EconomyPlayerJoinEvent extends Event {
    private final HandlerList handlerList = new HandlerList();

    private UUID playerUUID;
    private int playerID;
    public EconomyPlayerJoinEvent(UUID playerUUID){
        super(!Bukkit.isPrimaryThread());
        this.playerUUID = playerUUID;
        this.playerID = -1; //CETTE VALEUR DOIT ÊTRE DEFINI PAR LA PLATEFORME UTILISANT LE PLUGIN
    }

    /**
     * Retourne l'UUID du joueur
     * @return
     */
    public UUID getPlayerUUID() { return playerUUID; }

    /**
     * Retourne l'ID du joueur qui doit être défini par la plateforme utilisant le plugin
     * @return
     */
    public int getPlayerID() { return playerID; }

    /**
     * Définition de l'ID du joueur
     * @param playerID
     */
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    @Override
    public HandlerList getHandlers() {
        return this.handlerList;
    }
}
