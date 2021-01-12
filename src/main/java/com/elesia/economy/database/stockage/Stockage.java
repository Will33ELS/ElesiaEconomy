package com.elesia.economy.database.stockage;

import com.elesia.economy.api.ISQLBridge;
import com.elesia.economy.api.IStockage;
import com.elesia.economy.exception.EconomyException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Gestion des rêquetes entre le SGBD et le plugin
 */
public class Stockage implements IStockage {

    private ISQLBridge isqlBridge;
    public Stockage(ISQLBridge isqlBridge){
        this.isqlBridge = isqlBridge;
    }

    @Override
    public Double getBalance(UUID playerUUID) throws EconomyException {
        double amount = 0.0D;
        try{
            PreparedStatement preparedStatement = this.isqlBridge.getConnection().prepareStatement("SELECT amount FROM "+this.isqlBridge.getTablePrefix()+"account WHERE playerUUID = ?");
            preparedStatement.setString(1, playerUUID.toString());
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
    public void removeMoney(UUID playerUUID, double amount) throws EconomyException {
        double playerSolde = getBalance(playerUUID);
        if(amount > playerSolde) throw new EconomyException("Le joueur n'a pas assez d'argent !");
        if(amount <= 0) throw new EconomyException("Le montant retiré doit être supérieur à 0 !");
        try{
            PreparedStatement preparedStatement = this.isqlBridge.getConnection().prepareStatement("UPDATE "+this.isqlBridge.getTablePrefix()+"account SET amount = amount - ? WHERE playerUUID = ?");
            preparedStatement.setDouble(1, amount);
            preparedStatement.setString(2, playerUUID.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException err){
            err.printStackTrace();
        }
    }

    @Override
    public void addMoney(UUID playerUUID, double amount) throws EconomyException {
        if(!isAccountExist(playerUUID)) throw new EconomyException("Ce joueur ne dispose pas de compte !");
        if(amount <= 0) throw new EconomyException("Le montant à ajouté doit être supérieur à 0 !");
        try{
            PreparedStatement preparedStatement = this.isqlBridge.getConnection().prepareStatement("UPDATE "+this.isqlBridge.getTablePrefix()+"account SET amount = amount + ? WHERE playerUUID = ?");
            preparedStatement.setDouble(1, amount);
            preparedStatement.setString(2, playerUUID.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException err){
            err.printStackTrace();
        }
    }

    @Override
    public void setMoney(UUID playerUUID, double amount) throws EconomyException {
        if(!isAccountExist(playerUUID)) throw new EconomyException("Ce joueur ne dispose pas de compte !");
        if(amount < 0) throw new EconomyException("Le montant à définir doit être supérieur à 0 !");
        try{
            PreparedStatement preparedStatement = this.isqlBridge.getConnection().prepareStatement("UPDATE "+this.isqlBridge.getTablePrefix()+"account SET amount = ? WHERE playerUUID = ?");
            preparedStatement.setDouble(1, amount);
            preparedStatement.setString(1, playerUUID.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException err){
            err.printStackTrace();
        }
    }

    @Override
    public boolean isAccountExist(UUID playerUUID) {
        boolean accountExist = false;
        try{
            PreparedStatement preparedStatement = this.isqlBridge.getConnection().prepareStatement("SELECT amount FROM "+this.isqlBridge.getTablePrefix()+"account WHERE playerUUID = ?");
            preparedStatement.setString(1, playerUUID.toString());
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
    public void createAccount(UUID playerUUID) throws EconomyException {
        if(isAccountExist(playerUUID)) throw new EconomyException("Ce joueur dispose déjà d'un compte !");
        try{
            PreparedStatement preparedStatement = this.isqlBridge.getConnection().prepareStatement("INSERT INTO "+this.isqlBridge.getTablePrefix()+"account (playerUUID) VALUES (?)");
            preparedStatement.setString(1, playerUUID.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException err){
            err.printStackTrace();
        }
    }

    @Override
    public void deleteAccount(UUID playerUUID) throws EconomyException {
        if(!isAccountExist(playerUUID)) throw new EconomyException("Ce joueur ne dispose pas de compte !");
        try{
            PreparedStatement preparedStatement = this.isqlBridge.getConnection().prepareStatement("DELETE FROM "+this.isqlBridge.getTablePrefix()+"account WHERE playerUUID = ?");
            preparedStatement.setString(1, playerUUID.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException err){
            err.printStackTrace();
        }
    }

    @Override
    public List<UUID> getAccounts() {
        List<UUID> accounts = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = this.isqlBridge.getConnection().prepareStatement("SELECT playerUUID FROM "+this.isqlBridge.getTablePrefix()+"account");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                accounts.add(UUID.fromString(resultSet.getString("playerUUID")));
            }
            resultSet.close();
            preparedStatement.close();
        }catch (SQLException err){
            err.printStackTrace();
        }
        return accounts;
    }
}
