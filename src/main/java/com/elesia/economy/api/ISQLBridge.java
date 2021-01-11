package com.elesia.economy.api;

import java.sql.Connection;

public interface ISQLBridge {

    /**
     * Récupération de la connexion à la base de données
     * @return
     */
    Connection getConnection();

    /**
     * Déconnexion de la base de données
     */
    void shutdownDataSource() throws Exception;

}
