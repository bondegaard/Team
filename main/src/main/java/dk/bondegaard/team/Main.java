package dk.bondegaard.team;

import dk.bondegaard.team.commands.CommandManagerWrapper;
import dk.bondegaard.team.commands.TeamAdminCommand;
import dk.bondegaard.team.commands.TeamCommand;
import dk.bondegaard.team.common.VersionSupport;
import dk.bondegaard.team.teams.TeamHandler;
import dk.bondegaard.team.utils.NmsVersion;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    private Economy economy;

    private NmsVersion nmsVersion;

    private VersionSupport versionSupport;

    private CommandManagerWrapper commandManager;

    private TeamHandler teamHandler;

    @Override
    public void onEnable() {
        instance = this;

        this.setupVersionSupport();

        this.saveDefaultConfig();
        this.setupEconomy();

        this.initCommands();
        this.initHandlers();


        this.teamHandler.loadTeams();
    }

    @Override
    public void onDisable() {
        this.teamHandler.saveTeams();
        this.versionSupport = null;
    }

    private void setupVersionSupport() {
        this.nmsVersion = NmsVersion.currentNmsVersion();

        this.versionSupport = VersionSupportResolver.versionSupportFrom(this.nmsVersion);

        this.getLogger().info("Using integration for Spigot " + this.nmsVersion.name());
    }

    private void initCommands() {
        this.commandManager = new CommandManagerWrapper(this);
        this.commandManager.loadMessages();

        commandManager.register(new TeamCommand());
        commandManager.register(new TeamAdminCommand());
    }

    private void initHandlers() {
        this.teamHandler = new TeamHandler();
    }

    private void setupEconomy() {
        final RegisteredServiceProvider<Economy> economyService = this.getServer().getServicesManager().getRegistration(Economy.class);

        if (economyService == null)
            return;

        this.economy = economyService.getProvider();
    }

}
