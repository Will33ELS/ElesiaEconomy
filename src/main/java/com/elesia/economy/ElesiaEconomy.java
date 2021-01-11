package com.elesia.economy;

import com.elesia.economy.api.ISQLBridge;
import com.elesia.economy.database.MySQLBridge;
import com.elesia.economy.database.SQLLiteBridge;
import org.bukkit.plugin.java.JavaPlugin;

public class ElesiaEconomy extends JavaPlugin {

    private static ElesiaEconomy INSTANCE;
    private ISQLBridge sqlBridge;

    @Override
    public void onEnable() {
        INSTANCE = this;

        saveDefaultConfig();

        // CONFIGURATION DE LA BASE DE DONNEES
        if(getConfig().getBoolean("sqlLite.enable")){
            sqlBridge = new SQLLiteBridge(getConfig().getString("sqlLite.file"));
        }else if(getConfig().getBoolean("mysql.enable")){
            sqlBridge = new MySQLBridge(getConfig().getString("mysql.host"), getConfig().getString("mysql.bdd"), getConfig().getString("mysql.username"), getConfig().getString("mysql.password"));
        }else{
            getLogger().warning("Aucune base de données configuré !");
            getServer().shutdown();
        }
    }

    @Override
    public void onDisable() {

    }

    /**
     * Retourne l'instance de la base de données
     * @return
     */
    public ISQLBridge getSqlBridge() { return this.sqlBridge; }

    /**
     * Retourne l'instance du plugin
     * @return
     */
    public static ElesiaEconomy getInstance() { return INSTANCE; }
}
