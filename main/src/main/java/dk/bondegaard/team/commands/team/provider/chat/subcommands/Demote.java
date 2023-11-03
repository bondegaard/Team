package dk.bondegaard.team.commands.team.provider.chat.subcommands;

import dk.bondegaard.team.Main;
import dk.bondegaard.team.commands.team.provider.chat.TeamChatSubCommand;
import dk.bondegaard.team.teams.TeamUtils;
import dk.bondegaard.team.teams.objects.Team;
import dk.bondegaard.team.teams.objects.TeamMember;
import dk.bondegaard.team.teams.objects.TeamRole;
import dk.bondegaard.team.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class Demote extends TeamChatSubCommand {
    public Demote(String command) {
        super(command);
        setRequireTeam(true);
    }

    @Override
    public void execute(Player player, List<String> args) {
        Team team = this.getTeam();

        OfflinePlayer target = Bukkit.getOfflinePlayer(args.get(0));
        if (target.getUniqueId() == null) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cThe player that you selected has never played before!");
            return;
        }
        if (target.getUniqueId().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou are not allowed to demote yourself!");
            return;
        }

        TeamMember targetMember = TeamUtils.getOfflinePlayerTeamMember(target, team);
        if (targetMember == null) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §c"+target.getName()+" is not apart of your team!");
            return;
        }

        TeamMember teamMember = TeamUtils.getPlayerTeamMember(player, team);
        if (teamMember == null) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cCould not find your team role!");
            return;
        }

        if (team.getTeamPerms().getCanPromoteAndDemote().getId() > targetMember.getRole().getId()) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou are not allowed to demote players!");
            return;
        }

        if (targetMember.getRole().getId() >= teamMember.getRole().getId()) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou are not allowed to demote "+target.getName()+"!");
            return;
        }

        if (targetMember.getRole() == TeamRole.MEMBER) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §c"+target.getName()+" already has the lowest rank!");
            return;
        }

        targetMember.setRole(TeamRole.byID(targetMember.getRole().getId()+1));
        PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou have demoted "+target.getName()+"!");
    }
}
