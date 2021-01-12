package com.elesia.economy.manager;

import com.elesia.economy.ElesiaEconomy;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Classe qui comporte tous les settings du plugin défini dans la configuration
 */
public class EconomySetup {

    private static FileConfiguration config = ElesiaEconomy.getInstance().getConfig();

    public static final String CURRENCY_NAME = config.getString("config.currencyName");
    public static final String CURRENCY_SIGN = config.getString("config.currencySign");

}
