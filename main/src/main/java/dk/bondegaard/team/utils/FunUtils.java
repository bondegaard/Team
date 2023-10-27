package dk.bondegaard.team.utils;

import dk.bondegaard.team.Main;
import dk.bondegaard.team.utils.item.ItemBuilder;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class FunUtils {


    public static void spawnBlood(Location location, int despawnTime) {

        List<Item> itemList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ItemStack itemStack = new ItemBuilder(Material.INK_SACK, 1, (short) 1)
                    .name("blood" + i)
                    .addNbt("basic", "blood", String.valueOf(i))
                    .build();
            Item item = location.getWorld().dropItem(location, itemStack);
            item.setPickupDelay(Integer.MAX_VALUE);
            itemList.add(item);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Item item : itemList)
                    item.remove();
            }
        }.runTaskLater(Main.getInstance(), despawnTime);
    }


    public static void sendRedstoneParticle(Player player, Location location, int amount) {
        ParticleEffect.REDSTONE.display(0f, 0f, 0f, 1f, amount, location, Collections.singletonList(player));
    }

    public static void spawnHarmlessLightning(Location loc) {
        loc.getWorld().strikeLightningEffect(loc);
    }

    public static void playFakeExplosion(Location loc) {
        ParticleEffect.EXPLOSION_NORMAL.display(0f, 0f, 0f, 1f, 1, loc);
        loc.getWorld().playSound(loc, Sound.EXPLODE, 1f, 1f);
    }

    public static void playSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 1f, 0.6f);
    }
}
