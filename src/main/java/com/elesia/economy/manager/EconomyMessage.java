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
    public static final String CONSOLE_ONLY = config.getString("messages.consoleOnly");
    public static final String INTERNAL_ERROR = config.getString("messages.internalError");
    public static final String SOLDE_PLAYER = config.getString("messages.soldePlayer").replace("{currencyName}", EconomySetup.CURRENCY_NAME).replace("{currencySign}", EconomySetup.CURRENCY_SIGN);
    public static final String SOLDE_OTHER_PLAYER = config.getString("messages.soldeOtherPlayer").replace("{currencyName}", EconomySetup.CURRENCY_NAME).replace("{currencySign}", EconomySetup.CURRENCY_SIGN);
    public static final String PLAYER_NO_ACCOUNT = config.getString("messages.playerNoAccount");
    public static final String PLAYER_HAS_ACCOUNT = config.getString("messages.playerHasAccount");
    public static final String PLAYER_IS_NOT_ONLINE = config.getString("messages.playerIsNotOnline");
    public static final String PLAYER_INSUFFICIENT_BALANCE = config.getString("messages.playerInsufficientBalance");
    public static final String AMOUNT_IS_NOT_A_NUMBER = config.getString("messages.amountIsNotANumber");
    public static final String AMOUNT_INCORRECT = config.getString("messages.amountIncorrect");
    public static final String INSUFFICIENT_BALANCE = config.getString("messages.insufficientBalance");
    public static final String COMMAND_PAY_USAGE = config.getString("messages.commandPayUsage");
    public static final String PAY_COMMAND_USAGE = config.getString("messages.payCommandUsage");
    public static final String PAYMENT_SENT_TO = config.getString("messages.paymentSentTo").replace("{currencyName}", EconomySetup.CURRENCY_NAME).replace("{currencySign}", EconomySetup.CURRENCY_SIGN);
    public static final String PAYMENT_RECEIVED_FROM = config.getString("messages.paymentReceivedFrom").replace("{currencyName}", EconomySetup.CURRENCY_NAME).replace("{currencySign}", EconomySetup.CURRENCY_SIGN);
    public static final String GIVE_COMMAND_USAGE = config.getString("messages.giveCommandUsage");
    public static final String YOU_RECEIVED = config.getString("messages.youReceived").replace("{currencyName}", EconomySetup.CURRENCY_NAME).replace("{currencySign}", EconomySetup.CURRENCY_SIGN);
    public static final String YOU_GIVE_TO = config.getString("messages.youGiveTo").replace("{currencyName}", EconomySetup.CURRENCY_NAME).replace("{currencySign}", EconomySetup.CURRENCY_SIGN);
    public static final String TAKE_COMMAND_USAGE = config.getString("messages.takeCommandUsage");
    public static final String YOU_LOST = config.getString("messages.youLost").replace("{currencyName}", EconomySetup.CURRENCY_NAME).replace("{currencySign}", EconomySetup.CURRENCY_SIGN);
    public static final String YOU_TAKE_TO = config.getString("messages.youTakeTo").replace("{currencyName}", EconomySetup.CURRENCY_NAME).replace("{currencySign}", EconomySetup.CURRENCY_SIGN);
    public static final String CREATE_COMMAND_USAGE = config.getString("messages.createCommandUsage");
    public static final String CREATE_ACCOUNT_TO = config.getString("messages.createAccountTo");
    public static final String SET_COMMAND_USAGE = config.getString("messages.setCommandUsage");
    public static final String YOU_SET = config.getString("messages.youSet").replace("{currencyName}", EconomySetup.CURRENCY_NAME).replace("{currencySign}", EconomySetup.CURRENCY_SIGN);
    public static final String YOU_SET_TO = config.getString("messages.youSetTo").replace("{currencyName}", EconomySetup.CURRENCY_NAME).replace("{currencySign}", EconomySetup.CURRENCY_SIGN);
    public static final String DELETE_COMMAND_USAGE = config.getString("messages.deleteCommandUsage");
    public static final String DELETE_ACCOUNT_IMPOSSIBLE = config.getString("messages.deleteAccountImpossible");
    public static final String YOU_DELETE_ACCOUNT_TO = config.getString("messages.youDeleteAccountTo");
    public static final String RESET_ACCOUNT_TO = config.getString("messages.resetAccountTo");
    public static final String TOTAL_ACCOUNT_RESET = config.getString("messages.totalAccountReset");
    public static final String TOTAL_ACCOUNT_DELETE = config.getString("messages.totalAccountDelete");
    public static final String KICK_PLAYER_DELETE = config.getString("messages.kickPlayerDelete");
    public static final String HELP_HEADER = config.getString("messages.helpCommand.header");
    public static final String HELP_BALANCE = config.getString("messages.helpCommand.balanceHelp");
    public static final String HELP_BALANCE_OTHER = config.getString("messages.helpCommand.balanceOtherHelp");
    public static final String HELP_PAY = config.getString("messages.helpCommand.payHelp");
    public static final String HELP_GIVE = config.getString("messages.helpCommand.giveHelp");
    public static final String HELP_TAKE = config.getString("messages.helpCommand.takeHelp");
    public static final String HELP_SET = config.getString("messages.helpCommand.setHelp");
    public static final String HELP_CREATE = config.getString("messages.helpCommand.createHelp");
    public static final String HELP_DELETE = config.getString("messages.helpCommand.deleteHelp");
    public static final String HELP_RESET_ALL = config.getString("messages.helpCommand.resetAllHelp");
    public static final String HELP_DELETE_ALL = config.getString("messages.helpCommand.deleteAllHelp");
    public static final String HELP_FOOTER = config.getString("messages.helpCommand.footer");
}
