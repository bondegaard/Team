package common.nms;

import dk.bondegaard.team.common.nms.NmsProvider;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NmsProvider_v1_8_R3 implements NmsProvider {

    @Override
    public void sendActionBar(@NotNull Player player, @NotNull String message) {
        IChatBaseComponent chatComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message + "\"}");
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(chatComponent, (byte) 2);

        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        nmsPlayer.playerConnection.sendPacket(packetPlayOutChat);
    }

    @Override
    public void sendTitle(@NotNull Player player, @NotNull String title, @NotNull String subTitle) {
        sendTitle(player, title, subTitle, 10, 70, 20);
    }

    @Override
    public void sendTitle(@NotNull Player player, @NotNull String title, @NotNull String subTitle, int fadeInTicks, int durationTicks, int fadeOutTicks) {
        IChatBaseComponent titleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + title + "\"}");
        IChatBaseComponent subtitleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + subTitle + "\"}");

        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleComponent, fadeInTicks, durationTicks, fadeOutTicks);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleComponent, fadeInTicks, durationTicks, fadeOutTicks);

        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
        playerConnection.sendPacket(titlePacket);
        playerConnection.sendPacket(subtitlePacket);
    }
}
