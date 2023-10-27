package dk.bondegaard.team.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;

public class CustomBook {
    public static void openBook(ItemStack book, Player p) {
        int slot = p.getInventory().getHeldItemSlot();
        ItemStack old = p.getInventory().getItem(slot);
        p.getInventory().setItem(slot, book);

        ByteBuf buf = Unpooled.buffer(256);
        buf.setByte(0, (byte) 0);
        buf.writerIndex(1);

        PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload("MC|BOpen", new PacketDataSerializer(buf));
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        p.getInventory().setItem(slot, old);
    }

    /*
    public static void openBook(ItemStack book, Player p) throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException {
        int slot = p.getInventory().getHeldItemSlot();
        ItemStack old = p.getInventory().getItem(slot);
        p.getInventory().setItem(slot, book);

        ByteBuf buf = Unpooled.buffer(256);
        buf.setByte(0, (byte)0);
        buf.writerIndex(1);

        Object packet = getNMSClass("PacketPlayOutCustomPayload")
                .getConstructor(
                        String.class,
                        getNMSClass("PacketDataSerializer")
                ).newInstance("MC|BOpen", getNMSClass("PacketDataSerializer").getConstructor(ByteBuf.class).newInstance(buf));
        Object craftPlayer = p.getClass().getMethod("getHandle").invoke(p);
        Field f = craftPlayer.getClass().getDeclaredField("playerConnection");
        f.setAccessible(true);
        Object playerConnection = f.get(craftPlayer);
        f.getType().getDeclaredMethod("sendPacket", getNMSClass("Packet"))
                .invoke(playerConnection, packet);
        p.getInventory().setItem(slot, old);
    }
     */

    public static ItemStack book(String title, String author, String[] pages) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        ItemStack is = new ItemStack(Material.WRITTEN_BOOK, 1);
        Class craftItemStack = getCBClass("inventory.CraftItemStack");
        net.minecraft.server.v1_8_R3.ItemStack cis = CraftItemStack.asNMSCopy(is);
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("title", title);
        nbt.setString("author", author);
        NBTTagList list = new NBTTagList();
        for (String text : pages) {
            list.add(new NBTTagString(text));
        }
        cis.setTag(nbt);
        is = CraftItemStack.asBukkitCopy(cis);
        return is;
    }

    public static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    private static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + getVersion() + "." + name);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static Class<?> getCBClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
