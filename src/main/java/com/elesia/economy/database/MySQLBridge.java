package com.elesia.economy.database;

import com.elesia.economy.api.ISQLBridge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLBridge implements ISQLBridge {
    private final String host;
    private final String name;
    private final String username;
    private final String password;
    private Connection connection;

    /* DEFINITION DE LA CLASS */
    public MySQLBridge(String host, String name, String username, String password) {
        this.host = host;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    /* CONNEXION A LA BDD PUIS CREER LES TABLES SI ELLES N'EXISTENT PAS */
    public void setup() {
        if (!isConnected()) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + name + "?autoreconnect=true&useUnicode=true&characterEncoding=utf-8&useSSL=false", username, password);
            } catch (SQLException e) {
                e.printStackTrace();
                setup();
            }
        }
    }

    /* RECUPERATION DE L'INSTANCE DE CONNEXION */
    public Connection getConnection() {
        if (!isConnected()) {
            try {
                shutdownDataSource();
            }catch (Exception err){
                err.printStackTrace();
            }
            setup();
            return connection;
        } else
            return this.connection;
    }

    /* FERMETURE DE LA CONNEXION */
    public void shutdownDataSource() throws Exception{
        if(connection == null) return;
        try {
            connection.close();
        } catch (SQLException e) {
            throw new Exception("Erreur: "+e.getMessage());
        }
    }

    /* VERIFICATION DE LA CONNEXION*/
    private boolean isConnected() {
        try {
            return (connection != null) && (!connection.isClosed()) && connection.isValid(1000);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
