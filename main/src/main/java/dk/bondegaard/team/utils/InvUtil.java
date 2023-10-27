package dk.bondegaard.team.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

@UtilityClass
public final class InvUtil {

    public static boolean hasSpaceFor(Inventory inventory, ItemStack matchItem) {
        return hasSpaceFor(inventory, matchItem, matchItem.getAmount());
    }

    public static boolean hasSpaceFor(Inventory inventory, ItemStack matchItem, int amount) {
        return availableSpaceFor(inventory, matchItem) >= amount;
    }

    public static int availableSpaceFor(Inventory inventory, ItemStack matchItem) {
        ItemStack singleMatchItem = matchItem.clone();
        singleMatchItem.setAmount(1);

        return Arrays.stream(inventory.getContents())
                .mapToInt(invItem ->
                        singleMatchItem.getMaxStackSize() - (invItem == null ? 0 : invItem.getAmount())
                ).sum();
    }

    public static boolean hasAtLeast(Inventory inventory, ItemStack matchItem, int amount) {
        return amountOfItems(inventory, matchItem) >= amount;
    }

    public static int amountOfItems(Inventory inventory, ItemStack matchItem) {
        ItemStack singleMatchItem = matchItem.clone();
        singleMatchItem.setAmount(1);

        return Arrays.stream(inventory.getContents())
                .filter(singleMatchItem::isSimilar)
                .mapToInt(ItemStack::getAmount)
                .sum();
    }

}
