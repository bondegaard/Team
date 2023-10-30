package dk.bondegaard.team.teams;

import dk.bondegaard.team.Main;
import dk.bondegaard.team.teams.objects.Team;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TeamHandler {

    private final List<Team> teams = new ArrayList<>();

    public boolean createTeam(Player creator, String teamName) {
        if (TeamUtils.existTeam(teamName)) return false;
        if (TeamUtils.isPlayerInTeam(creator)) return false;

        int newTeamID = getNewTeamID();
        Team team = new Team(newTeamID, teamName, creator);

        Main.getInstance().getDataProvider().saveTeam(team);
        teams.add(team);
        return true;
    }

    public boolean deleteTeam(Team team) {
        if (!TeamUtils.existTeam(team)) return false;

        File teamFile = new File(Main.getInstance().getDataFolder(), "teams/" + team.getTeamID() + ".json");
        if (teamFile.exists() && !teamFile.isDirectory()) {
            teamFile.delete();
        }

        teams.remove(team);
        return true;
    }

    private int getNewTeamID() {
        return teams.stream()
                .mapToInt(Team::getTeamID)
                .max()
                .orElse(0) + 1;
    }
}
