package dk.bondegaard.team;

import dk.bondegaard.team.commands.CommandManagerWrapper;
import dk.bondegaard.team.commands.TeamAdminCommand;
import dk.bondegaard.team.commands.team.TeamCommand;
import dk.bondegaard.team.common.VersionSupport;
import dk.bondegaard.team.data.DataProvider;
import dk.bondegaard.team.data.JsonData;
import dk.bondegaard.team.teams.TeamHandler;
import dk.bondegaard.team.teams.TeamInviteHandler;
import dk.bondegaard.team.utils.NmsVersion;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Getter
    private static String prefix;

    private Economy economy;

    private NmsVersion nmsVersion;

    private VersionSupport versionSupport;

    private CommandManagerWrapper commandManager;

    private DataProvider dataProvider;

    private TeamHandler teamHandler;

    private TeamInviteHandler teamInviteHandler;


    @Override
    public void onEnable() {
        instance = this;

        this.setupVersionSupport();

        this.saveDefaultConfig();
        this.setupEconomy();
        this.setupPrefix();
        this.setupDataProvider();

        this.initCommands();
        this.initHandlers();


        this.dataProvider.loadTeams();
    }

    @Override
    public void onDisable() {
        this.dataProvider.saveTeams(teamHandler.getTeams());
        this.versionSupport = null;
    }

    private void setupPrefix() {
        prefix = this.getConfig().contains("lang.prefix") ? this.getConfig().getString("lang.prefix") : "";
    }

    private void setupVersionSupport() {
        this.nmsVersion = NmsVersion.currentNmsVersion();

        this.versionSupport = VersionSupportResolver.versionSupportFrom(this.nmsVersion);

        this.getLogger().info("Using integration for Spigot " + this.nmsVersion.name());
    }

    private void setupDataProvider() {
        String dataChoosen = getConfig().contains("data") ? getConfig().getString("data") : "";

        switch (dataChoosen) {
            default:
                this.dataProvider = new JsonData();
        }
        this.dataProvider.init();
    }

    private void initCommands() {
        this.commandManager = new CommandManagerWrapper(this);
        this.commandManager.loadMessages();

        commandManager.register(new TeamCommand());
        commandManager.register(new TeamAdminCommand());
    }

    private void initHandlers() {
        this.teamHandler = new TeamHandler();
        this.teamInviteHandler = new TeamInviteHandler();
    }

    private void setupEconomy() {
        final RegisteredServiceProvider<Economy> economyService = this.getServer().getServicesManager().getRegistration(Economy.class);

        if (economyService == null)
            return;

        this.economy = economyService.getProvider();
    }

}
