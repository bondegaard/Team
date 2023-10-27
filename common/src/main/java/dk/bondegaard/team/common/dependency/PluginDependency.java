package dk.bondegaard.team.common.dependency;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public abstract class PluginDependency {

    @Getter
    private final Plugin plugin;

    public PluginDependency(String pluginName) {
        this.plugin = Bukkit.getPluginManager().getPlugin(pluginName);
    }

    public boolean isDependencyActive() {
        return this.plugin != null && this.plugin.isEnabled();
    }

}
