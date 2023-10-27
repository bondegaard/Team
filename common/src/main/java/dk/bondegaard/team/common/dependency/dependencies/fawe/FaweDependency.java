package dk.bondegaard.team.common.dependency.dependencies.fawe;

import dk.bondegaard.team.common.dependency.PluginDependency;
import dk.bondegaard.team.common.util.WeightedMaterial;
import dk.bondegaard.team.common.util.compmaterial.CompMaterial;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public abstract class FaweDependency extends PluginDependency {

    public FaweDependency(String pluginName) {
        super(pluginName);
    }

    /**
     * @return Amount of blocks modified in the operation.
     */
    public abstract int removeBlocksBetween(@NotNull Location minPoint, @NotNull Location maxPoint);

    /**
     * @return Amount of blocks modified in the operation.
     */
    public abstract int removeBlocksFromLocations(@NotNull World world, @NotNull Collection<Location> locations);

    /**
     * @return Amount of blocks modified in the operation.
     */
    public abstract int removeBlocks(@NotNull World world, @NotNull Collection<Block> blocks);

    /**
     * @return Amount of blocks modified in the operation.
     */
    public abstract int setBlocks(@NotNull Location minPoint, @NotNull Location maxPoint, @NotNull CompMaterial material);

    /**
     * @return Amount of blocks modified in the operation.
     */
    public abstract int setWeightedBlocks(@NotNull Location minPoint, @NotNull Location maxPoint, @NotNull Collection<WeightedMaterial> weightedMaterials);

}
