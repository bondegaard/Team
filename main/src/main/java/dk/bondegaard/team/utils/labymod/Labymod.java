package dk.bondegaard.team.utils.labymod;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dk.bondegaard.team.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Labymod {


    public static void startCinematic(Player player, List<Location> locations, long duration, List<Integer> tilts) {
        JsonObject cinematic = new JsonObject();
        JsonArray points = new JsonArray();
        int num = 0;
        for (Location loc : locations) {
            JsonObject point = new JsonObject();
            point.addProperty("x", loc.getX());
            point.addProperty("y", loc.getY());
            point.addProperty("z", loc.getZ());
            point.addProperty("yaw", loc.getYaw());
            point.addProperty("pitch", loc.getPitch());
            point.addProperty("tilt", tilts.get(num));
            points.add(point);
            num++;
        }
        cinematic.add("points", points);
        cinematic.addProperty("clear_chat", true);
        cinematic.addProperty("duration", duration);
        player.teleport(locations.get(0));
        LabyModProtocol.sendLabyModMessage(player, "cinematic", cinematic);
    }

    public static void sendCineScope(Player player, int coveragePercent, long duration, long delay) {
        JsonObject object = new JsonObject();
        object.addProperty("coverage", coveragePercent);
        object.addProperty("duration", duration);
        new BukkitRunnable() {
            public void run() {
                LabyModProtocol.sendLabyModMessage(player, "cinescopes", object);
            }
        }.runTaskLater(Main.getInstance(), (delay / 1000) * 20);
    }

    public void sendServerBanner(Player player, String imageUrl) {
        JsonObject object = new JsonObject();
        object.addProperty("url", imageUrl); // Url of the image
        LabyModProtocol.sendLabyModMessage(player, "server_banner", object);
    }
}
