package com.elesia.economy.convert;

import com.elesia.economy.ElesiaEconomy;
import com.elesia.economy.api.IConvertEconomyPlugin;
import com.elesia.economy.api.IStockage;
import com.elesia.economy.exception.EconomyException;
import com.greatmancode.craftconomy3.Common;
import com.greatmancode.craftconomy3.account.Account;
import com.greatmancode.craftconomy3.account.Balance;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class ConvertCraftConomy implements IConvertEconomyPlugin {

    private final IStockage stockage = ElesiaEconomy.getInstance().getSqlBridge().getStockage();

    @Override
    public void convert() {
        //Suppression des comptes d'ElesiaEconomy pour cloné par la suite les données de craftconomy
        this.stockage.getAccounts().forEach(uuid -> {
            try {
                this.stockage.deleteAccount(uuid);
            } catch (EconomyException economyException) {
                economyException.printStackTrace();
            }
        });

        //Récupération des comptes CraftConomy
        for (String accountName : Common.getInstance().getAccountManager().getAllAccounts(false)) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(accountName);
            Account account = Common.getInstance().getAccountManager().getAccount(accountName, false);
            double amount = account.getAllBalance().stream().mapToDouble(Balance::getBalance).sum();
            try {
                Bukkit.getLogger().info("Transfére "+target.getUniqueId()+" montant "+ amount);
                if (!this.stockage.isAccountExist(target.getUniqueId()))
                    this.stockage.createAccount(target.getUniqueId());
                this.stockage.setMoney(target.getUniqueId(), amount);
            }catch (EconomyException err){
                err.printStackTrace();
            }
        }
    }
}
