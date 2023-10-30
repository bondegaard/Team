package dk.bondegaard.team.utils;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class PlayerUtil {

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(StringUtil.colorize(message));
    }

    public static void sendMessages(CommandSender sender, List<String> messages) {
        for (String message: messages) {
            sendMessage(sender, message);
        }
    }

    public static void sendTextComponent(Player player, TextComponent textComponent) {
        textComponent.setText(StringUtil.colorize(textComponent.getText()));
        player.spigot().sendMessage(textComponent);
    }

    public static void sendParsedPlaceholder(CommandSender sender, PlaceholderString placeholderString) {
        sender.sendMessage(placeholderString.parse());
    }

    public static List<Player> playersWithinBounds(Collection<? extends Player> players, Location min, Location max) {
        return players.stream()
                .filter(player -> Utils.locationWithinBounds(player.getLocation(), min, max))
                .collect(Collectors.toList());
    }

}
