package dk.bondegaard.team.utils.item;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Getter
public class SkullBuilder {

    private final ItemStack item;

    @Setter
    private String skullTexture;

    public SkullBuilder(String skullTexture) {
        this.item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        this.skullTexture = skullTexture;
    }

    public static ItemStack of(String skullTexture) {
        return new SkullBuilder(skullTexture).build();
    }

    public static ItemStack buildSkull(ItemStack item, String skullTexture) {
        if (skullTexture == null) throw new IllegalStateException("Skull Texture cannot be null!");

        NBTItem nbtItem = new NBTItem(item);

        NBTCompound skullOwnerCompound = nbtItem.addCompound("SkullOwner");

        String uuid = UUID.randomUUID().toString();
        skullOwnerCompound.setString("Id", uuid);

        skullOwnerCompound.addCompound("Properties").getCompoundList("textures").addCompound().setString("Value", skullTexture);

        return nbtItem.getItem();
    }

    public ItemStack build() {
        return buildSkull(this.item, this.skullTexture);
    }

}
