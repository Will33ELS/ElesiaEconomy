package com.elesia.economy.database;

import com.elesia.economy.ElesiaEconomy;
import com.elesia.economy.api.ISQLBridge;

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
    }


    /* CONNEXION A LA BDD PUIS CREER LES TABLES SI ELLES N'EXISTENT PAS */
    private void setup(){
        if(!this.isConnected()){
            try{
                this.connection = DriverManager.getConnection("jdbc:sqlite:" + sqlFile.toAbsolutePath());

                //TODO CREATION DES TABLES

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

    /* FERMETURE DE LA CONNEXION */
    private void shutdownDataSource() throws Exception{
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new Exception("Erreur: "+e.getMessage());
        }
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
