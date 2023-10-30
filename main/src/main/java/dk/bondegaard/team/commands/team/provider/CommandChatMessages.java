package dk.bondegaard.team.commands.team.provider;

import dk.bondegaard.team.Main;
import dk.bondegaard.team.teams.TeamHandler;
import dk.bondegaard.team.teams.TeamUtils;
import dk.bondegaard.team.teams.objects.Team;
import dk.bondegaard.team.teams.objects.TeamMember;
import dk.bondegaard.team.teams.objects.TeamRole;
import dk.bondegaard.team.utils.PlayerUtil;
import org.bukkit.entity.Player;

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

        boolean success = teamHandler.deleteTeam(team);
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
}
