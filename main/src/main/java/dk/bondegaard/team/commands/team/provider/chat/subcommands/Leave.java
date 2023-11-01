package dk.bondegaard.team.commands.team.provider.chat.subcommands;

import dk.bondegaard.team.Main;
import dk.bondegaard.team.commands.team.provider.chat.TeamChatSubCommand;
import dk.bondegaard.team.teams.TeamUtils;
import dk.bondegaard.team.teams.events.TeamLeaveEvent;
import dk.bondegaard.team.teams.objects.Team;
import dk.bondegaard.team.teams.objects.TeamMember;
import dk.bondegaard.team.teams.objects.TeamRole;
import dk.bondegaard.team.utils.PlayerUtil;
import org.bukkit.entity.Player;

import java.util.List;

public class Leave extends TeamChatSubCommand {
    public Leave(String command) {
        super(command);
        setRequireTeam(true);
    }

    @Override
    public void execute(Player player, List<String> args) {
        Team team = this.getTeam();

        TeamMember teamMember = TeamUtils.getPlayerTeamMember(player, team);
        if (teamMember == null) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cCould not find your team role!");
            return;
        }

        if (teamMember.getRole() == TeamRole.LEADER) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou cannot leave your team as the leader!");
            return;
        }

        TeamLeaveEvent teamLeaveEvent = new TeamLeaveEvent(team, teamMember);
        teamLeaveEvent.call();

        if (teamLeaveEvent.isCancelled()) return;

        team.getMembers().remove(teamMember);
        team.sendMessageToTeam(Main.getPrefix() + "§c"+player.getName()+" decided to leave your team by!");
    }
}
