package common.dependency.dependencies.fawe;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.util.EditSessionBuilder;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.blocks.BlockID;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import dk.bondegaard.team.common.dependency.dependencies.fawe.FaweDependency;
import dk.bondegaard.team.common.util.WeightedMaterial;
import dk.bondegaard.team.common.util.compmaterial.CompMaterial;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FaweDependency_v1_8_R3 extends FaweDependency {

    public FaweDependency_v1_8_R3(String pluginName) {
        super(pluginName);
    }

    private static CuboidRegion toRegion(final @NotNull Location minPoint, final @NotNull Location maxPoint) {
        return new CuboidRegion(toVector(minPoint), toVector(maxPoint));
    }

    @NotNull
    private static Vector toVector(final @NotNull Location location) {
        return new Vector(location.getX(), location.getY(), location.getZ());
    }

    @NotNull
    private static Set<Vector> locationsToVectors(final @NotNull Collection<Location> locations) {
        final Set<Vector> vectorSet = new HashSet<>(locations.size());

        locations.forEach(location -> vectorSet.add(toVector(location)));

        return vectorSet;
    }

    @NotNull
    private static Set<Vector> blocksToVectors(final @NotNull Collection<Block> blocks) {
        final Set<Vector> vectorSet = new HashSet<>(blocks.size());

        blocks.forEach(block -> vectorSet.add(toVector(block.getLocation())));

        return vectorSet;
    }

    private static EditSession buildEditSession(final @NotNull World bukkitWorld) {
        return buildEditSession(bukkitWorld.getName());
    }

    private static EditSession buildEditSession(final @NotNull String worldName) {
        return new EditSessionBuilder(FaweAPI.getWorld(worldName))
                .fastmode(true)
                .build();
    }

    private static int setBlocksFromVectorsTo(final @NotNull World world, final @NotNull Pattern pattern, final @NotNull Set<Vector> vectors) {
        final EditSession session = buildEditSession(world.getName());

        final int changed = session.setBlocks(vectors, pattern);
        session.flushQueue();

        return changed;
    }

    private static int setBlocksInRegionTo(final @NotNull World world, final @NotNull Pattern pattern, final @NotNull Region region) {
        final EditSession session = buildEditSession(world);

        final int changed = session.setBlocks(region, pattern);
        session.flushQueue();

        return changed;
    }

    private static RandomPattern toRandomPattern(final @NotNull Collection<WeightedMaterial> weightedMaterials) {
        final RandomPattern randomFill = new RandomPattern();

        weightedMaterials.forEach(weightedItem -> {
            final Pattern blockPattern = new BaseBlock(weightedItem.getCompMaterial().getId(), weightedItem.getCompMaterial().getData());
            randomFill.add(blockPattern, weightedItem.getChance());
        });

        return randomFill;
    }

    @Override
    public int removeBlocksBetween(@NotNull Location minPoint, @NotNull Location maxPoint) {
        return setBlocks(minPoint, maxPoint, CompMaterial.AIR);
    }

    public int removeBlocksFromLocations(@NotNull World world, @NotNull Collection<Location> locations) {
        return setBlocksFromVectorsTo(world, new BaseBlock(BlockID.AIR), locationsToVectors(locations));
    }

    @Override
    public int removeBlocks(@NotNull World world, @NotNull Collection<Block> blocks) {
        return setBlocksFromVectorsTo(world, new BaseBlock(BlockID.AIR), blocksToVectors(blocks));
    }

    @Override
    public int setBlocks(@NotNull Location minPoint, @NotNull Location maxPoint, @NotNull CompMaterial material) {
        final Region region = toRegion(minPoint, maxPoint);
        final Pattern blockPattern = new BaseBlock(material.getId(), material.getData());

        return setBlocksInRegionTo(minPoint.getWorld(), blockPattern, region);
    }

    @Override
    public int setWeightedBlocks(@NotNull Location minPoint, @NotNull Location maxPoint, @NotNull Collection<WeightedMaterial> weightedMaterials) {
        final Region region = toRegion(minPoint, maxPoint);
        final Pattern blockPattern = toRandomPattern(weightedMaterials);

        return setBlocksInRegionTo(minPoint.getWorld(), blockPattern, region);
    }

}
