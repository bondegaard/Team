package dk.bondegaard.team.utils;

import com.google.common.collect.Lists;
import org.bukkit.*;
import org.bukkit.block.Block;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
    public static String no_filled = "§8[§c✗§8]§7 ";
    public static String yes_filled = "§8[§2✔§8]§7 ";

    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "B");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "Q");
        suffixes.put(1_000_000_000_000_000_000L, "S");
    }

    public static String locationToString(Location loc) {
        return loc.getWorld().getName() + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getYaw() + ";" + loc.getPitch();
    }

    public static Location stringToLocation(String string) {
        String[] parts = string.split(";");
        if (parts.length != 6) return new Location(Bukkit.getWorld("world"), 0, 0, 0);

        World world = Bukkit.getWorld(parts[0]);
        float x = Float.parseFloat(parts[1]);
        float y = Float.parseFloat(parts[2]);
        float z = Float.parseFloat(parts[3]);
        float yaw = Float.parseFloat(parts[4]);
        float pitch = Float.parseFloat(parts[5]);

        return new Location(world, x, y, z, yaw, pitch);
    }

    public static String ProgressBar(double progress, double max, int size) {
        String returnString = "§c";
        for (int i = 0; i < size; i++)
            returnString = returnString + ((i >= progress / max * size) ? "§c▌" : "§a▌");
        return returnString;
    }

    public static String moneyFormat(Long d) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#.##", otherSymbols);
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        return decimalFormat.format(d) + "$";
    }

    public static Location getLocationInCircle(Location loc, double angle, double radius) {
        double x = (loc.getX() + radius * Math.cos(angle * Math.PI / 180));
        double z = (loc.getZ() + radius * Math.sin(angle * Math.PI / 180));

        return new Location(loc.getWorld(), x, loc.getY(), z);
    }

    public static List<Location> getCircleAroundPoint(int points, double radius, Location location) {
        List<Location> locations = new ArrayList<>();

        for (int i = 0; i < 360; i += 360 / points) {
            double angle = (i * Math.PI / 180);
            double x = radius * Math.cos(angle);
            double z = radius * Math.sin(angle);
            locations.add(location.clone().add(x, 0, z));
        }
        return locations;
    }

    public static boolean chance(double percent) {
        return Math.random() < (percent / 100);
    }

    public static Pair<Location, Location> pointsToMinMax(World world, Location pointOne, Location pointTwo) {
        double minX = Math.min(pointOne.getX(), pointTwo.getX()),
                minY = Math.min(pointOne.getY(), pointTwo.getY()),
                minZ = Math.min(pointOne.getZ(), pointTwo.getZ()),
                maxX = Math.max(pointOne.getX(), pointTwo.getX()),
                maxY = Math.max(pointOne.getY(), pointTwo.getY()),
                maxZ = Math.max(pointOne.getZ(), pointTwo.getZ());

        Location minLoc = new Location(world, minX, minY, minZ);
        Location maxLoc = new Location(world, maxX, maxY, maxZ);

        return new Pair<>(minLoc, maxLoc);
    }

    public static boolean locationWithinBounds(Location location, Location min, Location max) {
        if (location.getWorld() != min.getWorld()) return false;

        return numIsBetween(location.getX(), min.getX(), max.getX()) &&
                numIsBetween(location.getY(), min.getY(), max.getY()) &&
                numIsBetween(location.getZ(), min.getZ(), max.getZ());
    }

    public static boolean numIsBetween(double target, double min, double max) {
        return target >= min && target <= max;
    }

    public static List<Chunk> getChunksBetween(Chunk minChunk, Chunk maxChunk) {
        World world = minChunk.getWorld();
        List<Chunk> chunks = Lists.newArrayList();

        for (int chunkX = minChunk.getX(); chunkX <= maxChunk.getX(); chunkX++)
            for (int chunkZ = minChunk.getZ(); chunkZ <= maxChunk.getZ(); chunkZ++)
                chunks.add(world.getChunkAt(chunkX, chunkZ));

        return chunks;
    }

    public static Location toBlockLocation(Location location) {
        return location.getBlock().getLocation();
    }

    public static Location generateSafeLocation(int radius, int minimumRadius, World world) {

        Location location = new Location(world, 0, 0, 0);

        while (location.equals(new Location(world, 0, 0, 0))) {

            Location randomLoc = randomLocation(radius, radius, world);
            if (location.distance(randomLoc) >= minimumRadius) location = randomLoc;
        }

        for (int y = 255; y > 0; y--) {
            location.setY(y);
            Material levelBlock = world.getBlockAt(location).getType();
            if (!levelBlock.equals(Material.AIR)) {
                location.add(0, 1, 0);
                break;
            }
        }

        return location;
    }

    public static Location randomLocation(int radiusX, int radiusZ, World world) {

        Location centerLocation = new Location(world, 0, 0, 0);
        int
                xPointPostive = radiusX,
                xPointNegative = -radiusX,
                zPointPostive = radiusZ,
                zPointNegative = -radiusZ;

        int
                xRandom = ThreadLocalRandom.current().nextInt(xPointNegative, xPointPostive),
                zRandom = ThreadLocalRandom.current().nextInt(zPointNegative, zPointPostive);

        Location returnLocation = new Location(world, xRandom, 0, zRandom);

        return returnLocation;
    }

    public static List<Block> blocksInRadius(Location loc, int radius) {
        World world = loc.getWorld();
        List<Block> returnBlocks = new ArrayList<>();
        int
                minX = loc.getBlockX() - radius,
                minY = loc.getBlockY() - radius,
                minZ = loc.getBlockZ() - radius,
                maxX = loc.getBlockX() + radius,
                maxY = loc.getBlockY() + radius,
                maxZ = loc.getBlockZ() + radius;

        for (int x = minX; x <= maxX; x++)
            for (int y = minY; y <= maxY; y++)
                for (int z = minZ; z <= maxZ; z++) returnBlocks.add(world.getBlockAt(x, y, z));
        return returnBlocks;
    }

    public static String formatNumber(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return formatNumber(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + formatNumber(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }
}