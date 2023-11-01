package dk.bondegaard.team.commands.team.provider.chat.subcommands;

import dk.bondegaard.team.Main;
import dk.bondegaard.team.commands.team.provider.chat.TeamChatSubCommand;
import dk.bondegaard.team.teams.TeamUtils;
import dk.bondegaard.team.teams.events.TeamKickEvent;
import dk.bondegaard.team.teams.objects.Team;
import dk.bondegaard.team.teams.objects.TeamMember;
import dk.bondegaard.team.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class Kick extends TeamChatSubCommand {
    public Kick(String command) {
        super(command);
        setRequireTeam(true);
    }

    @Override
    public void execute(Player player, List<String> args) {
        Team team = this.getTeam();

        if (args.size() < 1) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou need to choose a player /team kick <player>!");
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args.get(0));
        if (target.getUniqueId() == null) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cThe player that you selected has never played before!");
            return;
        }
        if (target.getUniqueId().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou are not allowed to kick yourself from your team!");
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cUse /team leave!");
            return;
        }

        TeamMember teamMember = TeamUtils.getPlayerTeamMember(player, team);
        if (teamMember == null) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cCould not find your team role!");
            return;
        }

        if (!team.isTeamMember(target)) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cThe player that you selected is not in your team!");
            return;
        }

        if (team.getTeamPerms().getKickPlayers().getId() < teamMember.getRole().getId()) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou are not allowed to kick players from your team!");
            return;
        }

        TeamMember targetMember = team.getTeamMember(target);

        if (targetMember.getRole().getId() >= teamMember.getRole().getId()) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou are not allowed to kick players with higher or the same team role as you!");
            return;
        }
        TeamKickEvent teamKickEvent = new TeamKickEvent(targetMember, target, teamMember, player, team);
        teamKickEvent.call();

        if(teamKickEvent.isCancelled()) return;

        team.getMembers().remove(targetMember);
        team.sendMessageToTeam(Main.getPrefix() + "§c"+target.getName()+" has been kicked from your team by  " + player.getName()+"!");
    }
}
