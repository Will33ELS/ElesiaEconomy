package com.elesia.economy.manager;

import com.elesia.economy.ElesiaEconomy;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Classe qui comporte les messages du plugin
 */
public class EconomyMessage {

    private static FileConfiguration config = ElesiaEconomy.getInstance().getConfig();

    public static final String PREFIX = config.getString("messages.prefix");
    public static final String ACCOUNT_CANT_CREATE = config.getString("messages.accountCantCreate");
}
