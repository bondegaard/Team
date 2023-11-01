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

import java.util.List;

public class RemoveInvite extends TeamChatSubCommand {
    public RemoveInvite(String command) {
        super(command);
        setRequireTeam(true);
    }

    @Override
    public void execute(Player player, List<String> args) {
        Team team = this.getTeam();

        if (args.size() < 1) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou need to choose a player /team removeinvite <player>!");
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args.get(0));
        if (target.getUniqueId() == null) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cThe player that you selected is not online!");
            return;
        }

        TeamMember teamMember = TeamUtils.getPlayerTeamMember(player, team);
        if (teamMember == null) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cCould not find your team role!");
            return;
        }

        if (team.getTeamPerms().getInvitePlayers().getId() < teamMember.getRole().getId()) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou are not allowed to remove invites for your team!");
            return;
        }

        if (team.isTeamMember(target)) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cThe player that you selected is apart of your team!");
            return;
        }

        if (!Main.getInstance().getTeamInviteHandler().hasInvite(target, team)) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cThe selected player doesn't have an invite to your team!");
            return;
        }
        TeamInvite teamInvite = Main.getInstance().getTeamInviteHandler().getInvite(target, team);
        if (teamInvite == null) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cCould not find the invite!");
            return;
        }
        PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou removed "+teamInvite.getTargetName()+" invite to your team, created by "+teamInvite.getSenderName()+"!");
        Main.getInstance().getDataProvider().deleteInvite(teamInvite);
    }
}
