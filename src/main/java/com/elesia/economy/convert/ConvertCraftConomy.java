package com.elesia.economy.convert;

import com.elesia.economy.ElesiaEconomy;
import com.elesia.economy.api.IConvertEconomyPlugin;
import com.elesia.economy.api.IStockage;
import com.elesia.economy.exception.EconomyException;
import com.greatmancode.craftconomy3.Common;
import com.greatmancode.craftconomy3.account.Account;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class ConvertCraftConomy implements IConvertEconomyPlugin {

    private final IStockage stockage = ElesiaEconomy.getInstance().getSqlBridge().getStockage();

    @Override
    public void convert() {
        Bukkit.getScheduler().runTaskAsynchronously(ElesiaEconomy.getInstance(), () -> {
            //Récupération des comptes CraftConomy
            for (String accountName : Common.getInstance().getAccountManager().getAllAccounts(false)) {
                if(accountName.isEmpty()) continue;
                OfflinePlayer target = Bukkit.getOfflinePlayer(accountName);
                Account account = Common.getInstance().getAccountManager().getAccount(accountName, false);
                double amount = account.getBalance("world", Common.getInstance().getCurrencyManager().getDefaultCurrency().getName());
                try {
                    Bukkit.getLogger().info("Transfére "+target.getUniqueId()+" montant "+ amount);
                    if (!this.stockage.isAccountExist(target.getUniqueId()))
                        this.stockage.createAccount(target.getUniqueId());
                    this.stockage.setMoney(target.getUniqueId(), amount);
                    Common.getInstance().getAccountManager().delete(accountName, false);
                }catch (EconomyException err){
                    err.printStackTrace();
                }
            }
            Bukkit.getLogger().warning("Vous devez maintenant supprimer le plugin CraftConomy");
            Bukkit.getServer().shutdown();
        });
    }
}
