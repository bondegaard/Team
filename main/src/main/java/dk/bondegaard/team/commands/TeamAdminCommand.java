package dk.bondegaard.team.commands;

import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Default;
import dk.bondegaard.team.Main;
import dk.bondegaard.team.utils.PlayerUtil;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class TeamAdminCommand extends BaseCommand {
    private final String prefix;

    public TeamAdminCommand() {
        super("teamadmin", Arrays.asList("teama"));
        this.prefix = Main.getInstance().getConfig().getString("lang.prefix");
    }


    @Default
    public void execute(Player player) {
        if (!player.hasPermission("team.admin")) return;

        PlayerUtil.sendMessage(player, prefix + " §c§lTeam commands:");
        PlayerUtil.sendMessage(player, prefix + " §4* §c/team");
        PlayerUtil.sendMessage(player, prefix + " §4* §c/team create <name>");
    }
}
