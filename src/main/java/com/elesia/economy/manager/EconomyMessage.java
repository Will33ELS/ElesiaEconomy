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
    public static final String NO_PERMISSION = config.getString("messages.noPermission");
    public static final String NO_POSSIBLE = config.getString("messages.noPossible");
    public static final String INTERNAL_ERROR = config.getString("messages.internalError");
    public static final String SOLDE_PLAYER = config.getString("messages.soldePlayer").replace("{currencyName}", EconomySetup.CURRENCY_NAME).replace("{currencySign}", EconomySetup.CURRENCY_SIGN);
    public static final String SOLDE_OTHER_PLAYER = config.getString("messages.soldeOtherPlayer").replace("{currencyName}", EconomySetup.CURRENCY_NAME).replace("{currencySign}", EconomySetup.CURRENCY_SIGN);
    public static final String PLAYER_NO_ACCOUNT = config.getString("messages.playerNoAccount");
    public static final String PLAYER_IS_NOT_ONLINE = config.getString("messages.playerIsNotOnline");
    public static final String AMOUNT_IS_NOT_A_NUMBER = config.getString("messages.amountIsNotANumber");
    public static final String AMOUNT_INCORRECT = config.getString("messages.amountIncorrect");
    public static final String INSUFFICIENT_BALANCE = config.getString("messages.insufficientBalance");
    public static final String PAY_COMMAND_USAGE = config.getString("messages.payCommandUsage");
    public static final String PAYMENT_SENT_TO = config.getString("messages.paymentSentTo").replace("{currencyName}", EconomySetup.CURRENCY_NAME).replace("{currencySign}", EconomySetup.CURRENCY_SIGN);
    public static final String PAYMENT_RECEIVED_FROM = config.getString("messages.paymentReceivedFrom").replace("{currencyName}", EconomySetup.CURRENCY_NAME).replace("{currencySign}", EconomySetup.CURRENCY_SIGN);
    public static final String GIVE_COMMAND_USAGE = config.getString("messages.giveCommandUsage");
    public static final String YOU_RECEIVED = config.getString("messages.youReceived").replace("{currencyName}", EconomySetup.CURRENCY_NAME).replace("{currencySign}", EconomySetup.CURRENCY_SIGN);
    public static final String YOU_GIVE_TO = config.getString("messages.youGiveTo").replace("{currencyName}", EconomySetup.CURRENCY_NAME).replace("{currencySign}", EconomySetup.CURRENCY_SIGN);

}
