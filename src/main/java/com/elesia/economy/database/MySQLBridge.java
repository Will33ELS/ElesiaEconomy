package com.elesia.economy.database;

import com.elesia.economy.api.ISQLBridge;
import com.elesia.economy.api.IStockage;
import com.elesia.economy.database.stockage.Stockage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLBridge implements ISQLBridge {
    private final String host;
    private final String name;
    private final String username;
    private final String password;
    private final String tablePrefix;
    private Connection connection;
    private IStockage iStockage;

    /* DEFINITION DE LA CLASS */
    public MySQLBridge(String host, String name, String username, String password, String tablePrefix) {
        this.host = host;
        this.name = name;
        this.username = username;
        this.password = password;
        this.tablePrefix = tablePrefix;
        this.setup();
        this.iStockage = new Stockage(this);
    }

    /* CONNEXION A LA BDD PUIS CREER LES TABLES SI ELLES N'EXISTENT PAS */
    private void setup() {
        if (!isConnected()) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + name + "?autoreconnect=true&useUnicode=true&characterEncoding=utf-8&useSSL=false", username, password);

                // CREATION DE LA TABLE {PREFIX}_account
                PreparedStatement createTable = this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS ? (" +
                        "playerUUID VARCHAR(255) PRIMARY KEY, " +
                        "amount DOUBLE DEFAULT 0)");
                createTable.setString(1, this.getTablePrefix()+"_account");
                createTable.executeUpdate();
                createTable.close();

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

    @Override
    public String getTablePrefix() {
        return this.tablePrefix;
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

    /* RECUPERATION DE L'INSTANCE DE LA CLASSE STOCKAGE */
    @Override
    public IStockage getStockage() {
        return this.iStockage;
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
