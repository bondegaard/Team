package dk.bondegaard.team.commands.team.provider.chat.subcommands;

import dk.bondegaard.team.Main;
import dk.bondegaard.team.commands.team.provider.chat.TeamChatSubCommand;
import dk.bondegaard.team.teams.objects.Team;
import dk.bondegaard.team.teams.objects.TeamMember;
import dk.bondegaard.team.utils.PlayerUtil;
import org.bukkit.entity.Player;

import java.util.List;

public class Info extends TeamChatSubCommand {
    public Info(String command) {
        super(command);
        setRequireTeam(true);
    }

    @Override
    public void execute(Player player, List<String> args) {
        Team team = this.getTeam();
        PlayerUtil.sendMessage(player, Main.getPrefix() + " §c§lTeam Information:");
        PlayerUtil.sendMessage(player, Main.getPrefix() + " §4* §cID §8- §e" + team.getTeamID());
        PlayerUtil.sendMessage(player, Main.getPrefix() + " §4* §cName §8- §e" + team.getName());
        PlayerUtil.sendMessage(player, Main.getPrefix() + " §4* §cLevel §8- §e" + team.getLevel());
        PlayerUtil.sendMessage(player, Main.getPrefix() + " §4* §cMembers:");
        for (TeamMember teamMember : team.getMembers()) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §4* §8- §e" + teamMember.getName() + " (" + teamMember.getRole().getName() + ")");
        }
        PlayerUtil.sendMessage(player, team.serialize().toString());
    }
}
