package dk.bondegaard.team.utils.item;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;

public class ItemUtils {
    public static String toBase64(ItemStack item) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            //Save the object
            dataOutput.writeObject(item);

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static ItemStack fromBase64(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            ItemStack item = (ItemStack) dataInput.readObject();

            dataInput.close();
            return item;

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return new ItemStack(Material.AIR);
    }

    /*
    public static String convertItemStackToString(ItemStack what){
        // serialize the object
        String serializedObject = "";
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(what);
            so.flush();
            serializedObject = bo.toString();
            return serializedObject;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    public static ItemStack convertStringToItemStack(String data){
        // deserialize the object
        try {
            byte b[] = data.getBytes();
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            ItemStack obj = (ItemStack) si.readObject();
            return obj;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
     */


    /**
     * @param what - The ItemStack to be converted into a string
     * @return The String that contains the ItemStack (will return null if anything goes wrong)
     */
    public static String convertItemStackArrayToString(ItemStack[] what) {
        // serialize the object
        String serializedObject = "";
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(what);
            so.flush();
            serializedObject = bo.toString();
            return serializedObject;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * @param data - The String to be converted into an ItemStack Array
     * @return The ItemStack Array obtained from the string (will return void should anything go wrong)
     */
    public static ItemStack[] convertStringToItemStackArray(String data) {
        // deserialize the object
        try {
            byte[] b = data.getBytes();
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            ItemStack[] obj = (ItemStack[]) si.readObject();
            return obj;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static ItemStack getConfigItem(String path, ConfigurationSection config) {

        ConfigurationSection section = config.getConfigurationSection(path);

        //If the item is a custom skull
        if (section.getBoolean("skull")) {
            ItemBuilder itemBuilder = new ItemBuilder(SkullCreator.itemFromBase64(section.getString("skullTexture")));
            itemBuilder.name(section.getString("name"));
            itemBuilder.addLore(section.getStringList("lore"));
            itemBuilder.addItemFlag(ItemFlag.HIDE_ATTRIBUTES);
            return itemBuilder.build();

            //If the item is a regular item
        } else {
            String[] itemID = section.getString("type").split(":");
            try {

                ItemBuilder itemBuilder = new ItemBuilder(
                        /* Material */ Material.getMaterial(Integer.parseInt(itemID[0])),
                        /* Amount */ 1,
                        /* Data */ Short.parseShort(itemID[1])
                );

                itemBuilder.name(section.getString("name"));
                itemBuilder.addLore(section.getStringList("lore"));
                itemBuilder.addItemFlag(ItemFlag.HIDE_ATTRIBUTES);
                if (section.getBoolean("glowing")) itemBuilder.makeGlowing();

                return itemBuilder.build();

            } catch (Exception err) {
            }
        }
        return null;
    }

    public static ItemStack cloneWithNbt(ItemStack item, String compound, String key, String value) {
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.addCompound(compound)
                .setString(key, value);
        return nbtItem.getItem();
    }

    public static boolean hasNbtKey(ItemStack item, String key) {
        NBTItem nbtItem = new NBTItem(item);
        return nbtItem.hasNBTData() && nbtItem.hasKey(key);
    }

    public static String getNbtValue(ItemStack item, String compound, String key) {
        NBTItem nbtItem = new NBTItem(item);
        NBTCompound nbtCompound = nbtItem.getCompound(compound);
        return nbtCompound.getString(key);
    }

}
