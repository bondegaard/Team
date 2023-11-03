package dk.bondegaard.team.teams;

import dk.bondegaard.team.Main;
import dk.bondegaard.team.teams.objects.Team;
import dk.bondegaard.team.teams.objects.TeamMember;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

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
        return teamHandler.getTeams().stream().filter(team -> team.getTeamID() == id).findFirst().orElse(null);
    }

    public static Team getTeam(String name) {
        return teamHandler.getTeams().stream().filter(team -> team.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static Team getTeam(Team t) {
        return teamHandler.getTeams().stream().filter(team -> team.getTeamID() == t.getTeamID()).findFirst().orElse(null);
    }

    public static @Nullable Team getPlayerTeam(Player player) {
        return teamHandler.getTeams().stream()
                .filter(team -> team.getMembers()
                        .stream()
                        .anyMatch(teamMember -> teamMember.getUuid().equals(player.getUniqueId().toString())))
                .findFirst().orElse(null);
    }

    public static @Nullable TeamMember getPlayerTeamMember(Player player, Team team) {
       return team.getMembers().stream().filter(teamMember -> teamMember.getUuid().equalsIgnoreCase(player.getUniqueId().toString())).findFirst().orElse(null);
    }

    public static @Nullable TeamMember getOfflinePlayerTeamMember(OfflinePlayer player, Team team) {
        return team.getMembers().stream().filter(teamMember -> teamMember.getUuid().equalsIgnoreCase(player.getUniqueId().toString())).findFirst().orElse(null);
    }

    public static boolean isPlayerInTeam(Player player) {
        return getPlayerTeam(player) != null;
    }
}
