package dk.bondegaard.team.common.dependency;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

public abstract class DependencyProvider<T extends PluginDependency> {

    protected static <T extends PluginDependency> T createDependencyIfFound(final @NotNull Function<@NotNull String, @NotNull T> dependencyCreate, final @NotNull String... potentialPlugins) {
        final String foundPlugin = findFirstActivePlugin(potentialPlugins);

        if (foundPlugin == null)
            return null;

        return Bukkit.getPluginManager().isPluginEnabled(foundPlugin) ?
                dependencyCreate.apply(foundPlugin) :
                null;
    }

    @Nullable
    private static String findFirstActivePlugin(final @NotNull String... pluginNames) {
        return Arrays.stream(pluginNames)
                .map(Bukkit.getPluginManager()::getPlugin)
                .filter(Objects::nonNull)
                .map(Plugin::getName)
                .findFirst()
                .orElse(null);
    }

    @Nullable
    public abstract T findActiveDependency();

    public abstract Class<T> getDependencyClass();

}
