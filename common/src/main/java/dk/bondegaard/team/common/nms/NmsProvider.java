package dk.bondegaard.team.common.nms;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface NmsProvider {

    void sendActionBar(@NotNull Player player, @NotNull String message);

    void sendTitle(@NotNull Player player, @NotNull String title, @NotNull String subTitle);

    void sendTitle(@NotNull Player player, @NotNull String title, @NotNull String subTitle, int fadeInTicks, int durationTicks, int fadeOutTicks);

}
