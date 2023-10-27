package dk.bondegaard.team.utils;

import org.bukkit.ChatColor;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public final class StringUtil {

    private final static NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY);

    static {
        numberFormat.setMaximumFractionDigits(5);
    }

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> colorize(List<String> messages) {
        return messages.stream()
                .map(StringUtil::colorize)
                .collect(Collectors.toList());
    }

    public static String formatNum(double input) {
        return numberFormat.format(input);
    }

    public static String formatNum(BigDecimal input) {
        return numberFormat.format(input);
    }

}
