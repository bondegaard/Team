package dk.bondegaard.team.commands.team.provider.chat.subcommands;

import dk.bondegaard.team.Main;
import dk.bondegaard.team.commands.team.provider.chat.TeamChatSubCommand;
import dk.bondegaard.team.teams.TeamUtils;
import dk.bondegaard.team.teams.events.TeamJoinEvent;
import dk.bondegaard.team.teams.objects.Team;
import dk.bondegaard.team.teams.objects.TeamInvite;
import dk.bondegaard.team.teams.objects.TeamMember;
import dk.bondegaard.team.utils.PlayerUtil;
import org.bukkit.entity.Player;

import java.util.List;

public class Join extends TeamChatSubCommand {
    public Join(String command) {
        super(command);
    }

    @Override
    public void execute(Player player, List<String> args) {
        if (args.size() < 1) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou need to choose a team /team join <team>!");
            return;
        }
        String joinTeam = args.get(0);

        TeamInvite teamInvite = Main.getInstance().getTeamInviteHandler().getInvite(player, joinTeam);
        if (teamInvite == null) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou do not have an invite to this team!");
            return;
        }
        if (TeamUtils.isPlayerInTeam(player)) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou are already a member of a team!");
            return;
        }
        Team team = TeamUtils.getTeam(teamInvite.getTeamId());
        if (team == null) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cCould not find the team you are trying to join :(");
            return;
        }

        if (team.getMembers().size() >= team.getMaxMembers()) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cThe team does not have space for any new members!");
            return;
        }


        TeamJoinEvent teamJoinEvent = new TeamJoinEvent(player, team, teamInvite);
        teamJoinEvent.call();

        if (teamJoinEvent.isCancelled()) {return;}

        PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou have joined the team "+team.getName()+"!");
        team.sendMessageToTeam(Main.getPrefix() + "§c"+player.getName()+" har joined your team with an invite from " + teamInvite.getSenderName()+"!");
        team.getMembers().add(new TeamMember(player.getUniqueId().toString(), player.getName()));
        Main.getInstance().getDataProvider().deleteInvite(teamInvite);

    }
}
