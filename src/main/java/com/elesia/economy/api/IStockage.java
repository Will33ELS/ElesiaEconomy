package com.elesia.economy.api;

import com.elesia.economy.exception.EconomyException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IStockage {

    /**
     * Retourne le solde du compte du joueur
     * @param playerUUID UUID du joueur
     * @return Le solde du compte
     * @throws EconomyException
     */
    Double getBalance(UUID playerUUID) throws EconomyException;

    /**
     * Retirer de l'argent du solde du joueur
     * @param playerUUID UUID du joueur
     * @param amount Montant à retirer
     * @throws EconomyException
     */
    void removeMoney(UUID playerUUID, double amount) throws EconomyException;

    /**
     * Ajouter de l'argent du solde du joueur
     * @param playerUUID UUID du joueur
     * @param amount Montant à ajouter
     * @throws EconomyException
     */
    void addMoney(UUID playerUUID, double amount) throws EconomyException;

    /**
     * Définir le solde du compte d'un joueur
     * @param playerUUID UUID du joueur
     * @param amount Montant à définir
     * @throws EconomyException
     */
    void setMoney(UUID playerUUID, double amount) throws EconomyException;

    /**
     * Vérifie si le compte du joueur existe
     * @param playerUUID UUID du joueur
     * @return S'il existe
     */
    boolean isAccountExist(UUID playerUUID);

    /**
     * Créer le compte du joueur
     * @param playerUUID UUID du joueur
     * @throws EconomyException
     */
    void createAccount(UUID playerUUID) throws EconomyException;

    /**
     * Supprimer le compte du joueur
     * @param playerUUID UUID du joueur
     * @throws EconomyException
     */
    void deleteAccount(UUID playerUUID) throws EconomyException;

    /**
     * Récupérer la liste des comptes existants
     * @return
     */
    List<UUID> getAccounts();

    /**
     * Récupérer le top des joueurs les plus riches
     * @param maxEntry Nombre maximum d'entrée
     * @return
     */
    Map<UUID, Double> getTop(int maxEntry);
}
