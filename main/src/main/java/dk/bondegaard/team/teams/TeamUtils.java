package dk.bondegaard.team.teams;

import dk.bondegaard.team.Main;
import dk.bondegaard.team.teams.objects.Team;
import org.bukkit.entity.Player;

import java.util.Optional;

public class TeamUtils {

    private static final TeamHandler teamHandler = Main.getInstance().getTeamHandler();


    public static boolean existTeam(int id) {
        return teamHandler.getTeams().stream().anyMatch(team -> team.getTeamID() == id);
    }

    public static boolean existTeam(String name) {
        return teamHandler.getTeams().stream().anyMatch(team -> team.getName().equalsIgnoreCase(name));
    }

    public static boolean existTeam(Team t) {
        return teamHandler.getTeams().stream().anyMatch(team -> team.getTeamID() == t.getTeamID());
    }

    public static Team getTeam(int id) {
        return teamHandler.getTeams().stream().filter(team -> team.getTeamID() == id).findFirst().get();
    }

    public static Team getTeam(String name) {
        return teamHandler.getTeams().stream().filter(team -> team.getName().equalsIgnoreCase(name)).findFirst().get();
    }

    public static Team getTeam(Team t) {
        return teamHandler.getTeams().stream().filter(team -> team.getTeamID() == t.getTeamID()).findFirst().get();
    }

    public static Optional<Team> getPlayerTeam(Player player) {
        return teamHandler.getTeams().stream()
                .filter(team -> team.getMembers()
                        .stream()
                        .anyMatch(teamMember -> teamMember.getUuid().equals(player.getUniqueId().toString())))
                .findFirst();
    }

    public static boolean isPlayerInTeam(Player player) {
        return getPlayerTeam(player).isPresent();
    }
}