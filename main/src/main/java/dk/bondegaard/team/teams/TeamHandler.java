package dk.bondegaard.team.teams;

import dk.bondegaard.team.Main;
import dk.bondegaard.team.teams.objects.Team;
import lombok.Getter;
import org.bukkit.entity.Player;

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

    private int getNewTeamID() {
        return teams.stream()
                .mapToInt(Team::getTeamID)
                .max()
                .orElse(0) + 1;
    }
}
