package dk.bondegaard.team.commands.team.provider;

import dk.bondegaard.team.Main;
import dk.bondegaard.team.teams.TeamHandler;
import dk.bondegaard.team.teams.TeamUtils;
import dk.bondegaard.team.teams.events.TeamJoinEvent;
import dk.bondegaard.team.teams.events.TeamKickEvent;
import dk.bondegaard.team.teams.objects.Team;
import dk.bondegaard.team.teams.objects.TeamInvite;
import dk.bondegaard.team.teams.objects.TeamMember;
import dk.bondegaard.team.teams.objects.TeamRole;
import dk.bondegaard.team.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//todo change class name
public class CommandChatMessages implements TeamCommandProvider {
    @Override
    public void execute(Player player) {
        PlayerUtil.sendMessage(player, Main.getPrefix() + " §c§lTeam commands:");
        PlayerUtil.sendMessage(player, Main.getPrefix() + " §4* §c/team");
        PlayerUtil.sendMessage(player, Main.getPrefix() + " §4* §c/team create <name>");
    }

    @Override
    public void create(Player player, List<String> args) {
        TeamHandler teamHandler = Main.getInstance().getTeamHandler();

        if (args.size() < 1) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §4* §c/team create <name>");
            return;
        }
        String teamName = args.get(0);

        if (TeamUtils.isPlayerInTeam(player)) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou are already a member of a team!");
            return;
        }

        if (TeamUtils.existTeam(teamName)) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cTeam name is already being used!");
            return;
        }

        boolean success = teamHandler.createTeam(player, teamName);
        if (!success) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cFailed to create team!");
            return;
        }

        PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou created a team with the name " + teamName + "!");
    }

    @Override
    public void delete(Player player, List<String> args) {
        TeamHandler teamHandler = Main.getInstance().getTeamHandler();

        if (args.size() < 1) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §4* §c/team delete <name>");
            return;
        }

        String teamName = args.get(0);

        // Check that the player is in a team
        Optional<Team> t = TeamUtils.getPlayerTeam(player);
        if (!t.isPresent()) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou are not in a team!");
            return;
        }
        Team team = t.get();

        // Check if member
        if (!team.isTeamMember(player)) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou are not a member of this team!");
            return;
        }

        // Check that player is the team leader
        if (team.getTeamMember(player).getRole() != TeamRole.LEADER) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cOnly the leader of a team can delete the team!");
            return;
        }

        // Check that the player wrote the correct team
        if (!team.getName().equalsIgnoreCase(teamName)) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou need to write the name of your team!");
            return;
        }

        boolean success = Main.getInstance().getDataProvider().deleteTeam(team);
        if (!success) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cFailed to delete team!");
            return;
        }

        PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou deleted your team with the name " + teamName + "!");
    }

    @Override
    public void info(Player player) {
        // Check that the player is in a team
        Optional<Team> t = TeamUtils.getPlayerTeam(player);
        if (!t.isPresent()) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou are not in a team!");
            return;
        }
        Team team = t.get();
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

    @Override
    public void load(Player player) {
        if (!player.hasPermission("*")) return;
        Main.getInstance().getDataProvider().loadTeams();
    }

    @Override
    public void save(Player player) {
        if (!player.hasPermission("*")) return;
        Main.getInstance().getDataProvider().saveTeams(Main.getInstance().getTeamHandler().getTeams());
    }

    @Override
    public void list(Player player) {
        if (!player.hasPermission("*")) return;

        PlayerUtil.sendMessage(player, Main.getPrefix() + " §c§lTeams:");
        for (String name : Main.getInstance().getTeamHandler().getTeams().stream().map(Team::getName).collect(Collectors.toList())) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §4* §c" + name);
        }
    }

    @Override
    public void invite(Player player, List<String> args) {
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

        // Check that the player is in a team
        Optional<Team> t = TeamUtils.getPlayerTeam(player);
        if (!t.isPresent()) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou are not in a team!");
            return;
        }
        Team team = t.get();

        Optional<TeamMember> tm = TeamUtils.getPlayerTeamMember(player, team);
        if (!tm.isPresent()) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cCould not find your team role!");
            return;
        }
        TeamMember teamMember = tm.get();

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

    @Override
    public void removeInvite(Player player, List<String> args) {
        if (args.size() < 1) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou need to choose a player /team removeinvite <player>!");
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args.get(0));
        if (target.getUniqueId() == null) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cThe player that you selected is not online!");
            return;
        }

        // Check that the player is in a team
        Optional<Team> t = TeamUtils.getPlayerTeam(player);
        if (!t.isPresent()) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou are not in a team!");
            return;
        }
        Team team = t.get();

        Optional<TeamMember> tm = TeamUtils.getPlayerTeamMember(player, team);
        if (!tm.isPresent()) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cCould not find your team role!");
            return;
        }
        TeamMember teamMember = tm.get();

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

    @Override
    public void invites(Player player) {
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

    @Override
    public void join(Player player, List<String> args) {
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

    @Override
    public void kick(Player player, List<String> args) {
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

        // Check that the player is in a team
        Optional<Team> t = TeamUtils.getPlayerTeam(player);
        if (!t.isPresent()) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou are not in a team!");
            return;
        }
        Team team = t.get();

        Optional<TeamMember> tm = TeamUtils.getPlayerTeamMember(player, team);
        if (!tm.isPresent()) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cCould not find your team role!");
            return;
        }
        TeamMember teamMember = tm.get();

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
