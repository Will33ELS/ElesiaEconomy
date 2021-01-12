package com.elesia.economy.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class FormatUtils {

    /**
     * Retourne le format de la money
     * @param balance
     * @return
     */
    public static String formatCurrency(double balance) {
        StringBuilder string = new StringBuilder();

        String[] theAmount = BigDecimal.valueOf(balance).toPlainString().split("\\.");
        DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols();
        unusualSymbols.setGroupingSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("###,###", unusualSymbols);
        String amount;
        try {
            amount = decimalFormat.format(Double.parseDouble(theAmount[0]));
        } catch (NumberFormatException e) {
            amount = theAmount[0];
        }
        string.append(amount).append(" ");
        return string.toString();
    }

}
