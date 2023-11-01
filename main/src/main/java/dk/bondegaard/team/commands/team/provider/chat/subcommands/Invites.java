package dk.bondegaard.team.commands.team.provider.chat.subcommands;

import dk.bondegaard.team.Main;
import dk.bondegaard.team.commands.team.provider.chat.TeamChatSubCommand;
import dk.bondegaard.team.teams.TeamUtils;
import dk.bondegaard.team.teams.objects.Team;
import dk.bondegaard.team.teams.objects.TeamInvite;
import dk.bondegaard.team.utils.PlayerUtil;
import org.bukkit.entity.Player;

import java.util.List;

public class Invites extends TeamChatSubCommand {
    public Invites(String command) {
        super(command);
    }

    @Override
    public void execute(Player player, List<String> args) {
        List<TeamInvite> teamInvites = Main.getInstance().getTeamInviteHandler().getInvites(player);

        if (teamInvites.size() < 1) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou do not have any team invites!");
            return;
        }

        PlayerUtil.sendMessage(player, Main.getPrefix() + " §c§lTeam Invites:");
        for (TeamInvite teamInvite : teamInvites) {
            Team team = TeamUtils.getTeam(teamInvite.getTeamId());
            if (team == null) continue;
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §4* §c" + team.getName());
        }
    }
}
