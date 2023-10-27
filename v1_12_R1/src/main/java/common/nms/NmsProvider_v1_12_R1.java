package common.nms;

import dk.bondegaard.team.common.nms.NmsProvider;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NmsProvider_v1_12_R1 implements NmsProvider {

    @Override
    public void sendActionBar(@NotNull Player player, @NotNull String message) {
        BaseComponent textComponent = new TextComponent(message);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, textComponent);
    }

    @Override
    public void sendTitle(@NotNull Player player, @NotNull String title, @NotNull String subTitle) {
        sendTitle(player, title, subTitle, 10, 70, 20);
    }

    @Override
    public void sendTitle(@NotNull Player player, @NotNull String title, @NotNull String subTitle, int fadeInTicks, int durationTicks, int fadeOutTicks) {
        player.sendTitle(title, subTitle, fadeInTicks, durationTicks, fadeOutTicks);
    }

}
