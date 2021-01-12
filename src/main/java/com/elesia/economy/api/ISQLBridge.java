package com.elesia.economy.api;

import java.sql.Connection;

public interface ISQLBridge {

    /**
     * Récupération de la connexion à la base de données
     * @return
     */
    Connection getConnection();

    /**
     * Retourne le préfix des tables utilisés par le plugin
     * @return
     */
    String getTablePrefix();

    /**
     * Déconnexion de la base de données
     */
    void shutdownDataSource() throws Exception;

    /**
     * Retourne l'instance de la classe de la gestion du stockage
     * @return
     */
    IStockage getStockage();

}
