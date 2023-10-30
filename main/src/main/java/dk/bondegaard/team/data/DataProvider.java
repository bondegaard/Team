package dk.bondegaard.team.data;

import com.google.gson.JsonObject;
import dk.bondegaard.team.teams.objects.Team;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface DataProvider {

    void init();

    void saveTeams(@NotNull List<Team> teams);

    void saveTeam(@NotNull Team team);

    void loadTeams();

    boolean loadTeam(JsonObject teamObject);
}
