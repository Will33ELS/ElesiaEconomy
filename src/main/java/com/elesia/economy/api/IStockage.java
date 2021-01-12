package com.elesia.economy.api;

import com.elesia.economy.exception.EconomyException;

public interface IStockage {

    /**
     * Retourne le solde du compte du joueur
     * @param playerID ID unique du joueur
     * @return Le solde du compte
     * @throws EconomyException
     */
    Double getBalance(int playerID) throws EconomyException;

    /**
     * Retirer de l'argent du solde du joueur
     * @param playerID ID unique du joueur
     * @param amount Montant à retirer
     * @throws EconomyException
     */
    void removeMoney(int playerID, double amount) throws EconomyException;

    /**
     * Ajouter de l'argent du solde du joueur
     * @param playerID ID unique du joueur
     * @param amount Montant à ajouter
     * @throws EconomyException
     */
    void addMoney(int playerID, double amount) throws EconomyException;

    /**
     * Définir le solde du compte d'un joueur
     * @param playerID ID unique du joueur
     * @param amount Montant à définir
     * @throws EconomyException
     */
    void setMoney(int playerID, double amount) throws EconomyException;

    /**
     * Vérifie si le compte du joueur existe
     * @param playerID ID unique du joueur
     * @return S'il existe
     */
    boolean isAccountExist(int playerID);

    /**
     * Créer le compte du joueur
     * @param playerID ID unique du joueur
     * @throws EconomyException
     */
    void createAccount(int playerID) throws EconomyException;

    /**
     * Supprimer le compte du joueur
     * @param playerID ID unique du joueur
     * @throws EconomyException
     */
    void deleteAccount(int playerID) throws EconomyException;
}
