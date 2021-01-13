package com.elesia.economy;

import com.elesia.economy.api.IConvertEconomyPlugin;
import com.elesia.economy.api.ISQLBridge;
import com.elesia.economy.commands.MoneyCommand;
import com.elesia.economy.commands.PayCommand;
import com.elesia.economy.convert.ConvertCraftConomy;
import com.elesia.economy.database.MySQLBridge;
import com.elesia.economy.database.SQLLiteBridge;
import com.elesia.economy.listener.ListenerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ElesiaEconomy extends JavaPlugin {

    private static ElesiaEconomy INSTANCE;
    private ISQLBridge sqlBridge;
    private IConvertEconomyPlugin convertEconomyPlugin;

    @Override
    public void onEnable() {
        INSTANCE = this;

        saveDefaultConfig();

        // CONFIGURATION DE LA BASE DE DONNEES
        if(getConfig().getBoolean("sqlLite.enable")){
            sqlBridge = new SQLLiteBridge(getConfig().getString("sqlLite.file"));
        }else if(getConfig().getBoolean("mysql.enable")){
            sqlBridge = new MySQLBridge(getConfig().getString("mysql.host"), getConfig().getString("mysql.bdd"), getConfig().getString("mysql.username"), getConfig().getString("mysql.password"), getConfig().getString("mysql.tablePrefix"));
        }else{
            getLogger().warning("Aucune base de données configuré !");
            getServer().shutdown();
        }

        // ENREGISTREMENT DE LA COMMANDE /MONEY
        MoneyCommand moneyCommand = new MoneyCommand();
        getCommand("money").setExecutor(moneyCommand);
        getCommand("money").setTabCompleter(moneyCommand);

        // ENREGISTREMENT DE LA COMMANDE /PAY
        PayCommand payCommand = new PayCommand();
        getCommand("pay").setExecutor(payCommand);
        getCommand("pay").setTabCompleter(payCommand);

        //REGISTER DES LISTENERS
        new ListenerManager().registerListener(this);

        //CONVERTISSEUR PLUGIN
        if(Bukkit.getServer().getPluginManager().getPlugin("CraftConomy3") != null) {
            this.convertEconomyPlugin = new ConvertCraftConomy();
            this.convertEconomyPlugin.convert();
        }
    }

    @Override
    public void onDisable() {
        // DECONNEXION DE LA BASE DE DONNEES
        try {
            this.sqlBridge.shutdownDataSource();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retourne l'instance de la base de données
     * @return
     */
    public ISQLBridge getSqlBridge() { return this.sqlBridge; }

    /**
     * Retourne l'instance de la convertion en cours
     * @return
     */
    public IConvertEconomyPlugin getConvertEconomyPlugin() { return convertEconomyPlugin; }

    /**
     * Retourne l'instance du plugin
     * @return
     */
    public static ElesiaEconomy getInstance() { return INSTANCE; }
}
