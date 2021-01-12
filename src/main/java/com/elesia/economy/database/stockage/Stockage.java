package com.elesia.economy.database.stockage;

import com.elesia.economy.api.ISQLBridge;
import com.elesia.economy.api.IStockage;
import com.elesia.economy.exception.EconomyException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Gestion des rêquetes entre le SGBD et le plugin
 */
public class Stockage implements IStockage {

    private ISQLBridge isqlBridge;
    public Stockage(ISQLBridge isqlBridge){
        this.isqlBridge = isqlBridge;
    }

    @Override
    public Double getBalance(int playerID) throws EconomyException {
        double amount = 0.0D;
        try{
            PreparedStatement preparedStatement = this.isqlBridge.getConnection().prepareStatement("SELECT amount FROM "+this.isqlBridge.getTablePrefix()+"_account WHERE playerID = ?");
            preparedStatement.setInt(1, playerID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) throw new EconomyException("Ce joueur ne dispose pas de compte !");
            else{
                amount = resultSet.getDouble("amount");
            }
            resultSet.close();
            preparedStatement.close();
        }catch (SQLException err){
            err.printStackTrace();
        }
        return amount;
    }

    @Override
    public void removeMoney(int playerID, double amount) throws EconomyException {
        double playerSolde = getBalance(playerID);
        if(amount > playerSolde) throw new EconomyException("Le joueur n'a pas assez d'argent !");
        if(amount <= 0) throw new EconomyException("Le montant retiré doit être supérieur à 0 !");
        try{
            PreparedStatement preparedStatement = this.isqlBridge.getConnection().prepareStatement("UPDATE "+this.isqlBridge.getTablePrefix()+"_account SET amount = amount - ? WHERE playerID = ?");
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, playerID);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException err){
            err.printStackTrace();
        }
    }

    @Override
    public void addMoney(int playerID, double amount) throws EconomyException {
        if(!isAccountExist(playerID)) throw new EconomyException("Ce joueur ne dispose pas de compte !");
        if(amount <= 0) throw new EconomyException("Le montant à ajouté doit être supérieur à 0 !");
        try{
            PreparedStatement preparedStatement = this.isqlBridge.getConnection().prepareStatement("UPDATE "+this.isqlBridge.getTablePrefix()+"_account SET amount = amount + ? WHERE playerID = ?");
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, playerID);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException err){
            err.printStackTrace();
        }
    }

    @Override
    public void setMoney(int playerID, double amount) throws EconomyException {
        if(!isAccountExist(playerID)) throw new EconomyException("Ce joueur ne dispose pas de compte !");
        if(amount < 0) throw new EconomyException("Le montant à définir doit être supérieur à 0 !");
        try{
            PreparedStatement preparedStatement = this.isqlBridge.getConnection().prepareStatement("UPDATE "+this.isqlBridge.getTablePrefix()+"_account SET amount = ? WHERE playerID = ?");
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(1, playerID);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException err){
            err.printStackTrace();
        }
    }

    @Override
    public boolean isAccountExist(int playerID) {
        boolean accountExist = false;
        try{
            PreparedStatement preparedStatement = this.isqlBridge.getConnection().prepareStatement("SELECT amount FROM "+this.isqlBridge.getTablePrefix()+"_account WHERE playerID = ?");
            preparedStatement.setInt(1, playerID);
            ResultSet resultSet = preparedStatement.executeQuery();
            accountExist = resultSet.next();
            resultSet.close();
            preparedStatement.close();
        }catch (SQLException err){
            err.printStackTrace();
        }
        return accountExist;
    }

    @Override
    public void createAccount(int playerID) throws EconomyException {
        if(isAccountExist(playerID)) throw new EconomyException("Ce joueur dispose déjà d'un compte !");
        try{
            PreparedStatement preparedStatement = this.isqlBridge.getConnection().prepareStatement("INSERT INTO "+this.isqlBridge.getTablePrefix()+"_account (playerID) VALUES (?)");
            preparedStatement.setInt(1, playerID);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException err){
            err.printStackTrace();
        }
    }

    @Override
    public void deleteAccount(int playerID) throws EconomyException {
        if(!isAccountExist(playerID)) throw new EconomyException("Ce joueur ne dispose pas de compte !");
        try{
            PreparedStatement preparedStatement = this.isqlBridge.getConnection().prepareStatement("DELETE FROM "+this.isqlBridge.getTablePrefix()+"_account WHERE playerID = ?");
            preparedStatement.setInt(1, playerID);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException err){
            err.printStackTrace();
        }
    }
}
