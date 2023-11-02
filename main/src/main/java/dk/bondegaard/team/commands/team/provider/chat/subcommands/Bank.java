package dk.bondegaard.team.commands.team.provider.chat.subcommands;

import dk.bondegaard.team.Main;
import dk.bondegaard.team.commands.team.provider.chat.TeamChatSubCommand;
import dk.bondegaard.team.teams.objects.Team;
import dk.bondegaard.team.utils.PlayerUtil;
import dk.bondegaard.team.utils.Utils;
import org.bukkit.entity.Player;

import java.util.List;

public class Bank extends TeamChatSubCommand {
    public Bank(String command) {
        super(command);
        setRequireTeam(true);
    }

    @Override
    public void execute(Player player, List<String> args) {
        if (Main.getInstance().getEconomy() == null) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + "§cTeam bank is disabled!");
            return;
        }

        Team team = this.getTeam();

        PlayerUtil.sendMessage(player, Main.getPrefix() + "§cYour team bank is "+ Utils.formatNumber(team.getBalance())+"$");
    }
}
