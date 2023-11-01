package dk.bondegaard.team.commands.team.provider.chat.subcommands;

import dk.bondegaard.team.Main;
import dk.bondegaard.team.commands.team.provider.chat.TeamChatSubCommand;
import dk.bondegaard.team.teams.TeamUtils;
import dk.bondegaard.team.teams.objects.Team;
import dk.bondegaard.team.teams.objects.TeamInvite;
import dk.bondegaard.team.teams.objects.TeamMember;
import dk.bondegaard.team.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.List;

public class Invite extends TeamChatSubCommand {
    public Invite(String command) {
        super(command);
        setRequireTeam(true);
    }

    @Override
    public void execute(Player player, List<String> args) {
        Team team = this.getTeam();

        if (args.size() < 1) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou need to choose a player /team invite <player>!");
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args.get(0));
        if (target.getUniqueId() == null) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cThe player that you selected has never played before!");
            return;
        }
        if (target.getUniqueId().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou are not allowed to invite yourself to your team!");
            return;
        }

        TeamMember teamMember = TeamUtils.getPlayerTeamMember(player, team);
        if (teamMember == null) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cCould not find your team role!");
            return;
        }

        if (team.isTeamMember(target)) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cThe player that you selected is already in your team!");
            return;
        }

        if (team.getTeamPerms().getInvitePlayers().getId() < teamMember.getRole().getId()) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou are not allowed to invite players to your team!");
            return;
        }

        if (Main.getInstance().getTeamInviteHandler().hasInvite(target, team)) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cThe selected player already has an invite to your team!");
            return;
        }

        TeamInvite teamInvite = new TeamInvite(Main.getInstance().getTeamInviteHandler().getNewInviteID(), team.getTeamID(), player.getUniqueId().toString(), player.getName(), target.getUniqueId().toString(), target.getName(), new Timestamp(System.currentTimeMillis()));
        Main.getInstance().getTeamInviteHandler().getInvites().add(teamInvite);

        PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou invited "+target.getName()+" to your team!");
    }
}
