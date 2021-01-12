package com.elesia.economy.database;

import com.elesia.economy.ElesiaEconomy;
import com.elesia.economy.api.ISQLBridge;
import com.elesia.economy.api.IStockage;
import com.elesia.economy.database.stockage.Stockage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLLiteBridge implements ISQLBridge {
    private final Path sqlFile;
    private Connection connection;
    private IStockage iStockage;

    /* CREATION DU FICHIER SQL S'IL N'EXISTE PAS */
    public SQLLiteBridge(String fileName){
        sqlFile = Paths.get(ElesiaEconomy.getInstance().getDataFolder().toString(), fileName);
        try{
            if(!Files.exists(this.sqlFile))
                Files.createFile(this.sqlFile);
        }catch (IOException err){
            err.printStackTrace();
        }
        this.setup();
        this.iStockage = new Stockage(this);
    }


    /* CONNEXION A LA BDD PUIS CREER LES TABLES SI ELLES N'EXISTENT PAS */
    private void setup(){
        if(!this.isConnected()){
            try{
                this.connection = DriverManager.getConnection("jdbc:sqlite:" + sqlFile.toAbsolutePath());

                // CREATION DE LA TABLE {PREFIX}_account
                PreparedStatement createTable = this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS ? (" +
                        "playerUUID VARCHAR(255) PRIMARY KEY, " +
                        "amount DOUBLE DEFAULT 0)");
                createTable.setString(1, this.getTablePrefix()+"_account");
                createTable.executeUpdate();
                createTable.close();

            }catch (SQLException err){
                err.printStackTrace();
            }
        }
    }

    /* RECUPERATION DE L'INSTANCE DE CONNEXION */
    public Connection getConnection() {
        if (!this.isConnected()) {
            try {
                this.shutdownDataSource();
            }catch (Exception err){
                err.printStackTrace();
            }
            this.setup();
            return this.connection;
        }

        return this.connection;
    }

    /* RETOURNE UN CHAMP VIDE CAR LA BASE DE DONNEES ET UNIQUEMENT UTILISEE PAR LE PLUGIN */
    @Override
    public String getTablePrefix() {
        return "";
    }

    /* FERMETURE DE LA CONNEXION */
    public void shutdownDataSource() throws Exception{
        try {
            this.connection.close();
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
            return (this.connection != null) && (!this.connection.isClosed()) && this.connection.isValid(1000);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
