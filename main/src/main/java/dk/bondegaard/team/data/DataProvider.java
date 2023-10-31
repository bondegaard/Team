package dk.bondegaard.team.data;

import com.google.gson.JsonObject;
import dk.bondegaard.team.teams.objects.Team;
import dk.bondegaard.team.teams.objects.TeamInvite;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface DataProvider {

    void init();

    void saveTeams(@NotNull List<Team> teams);

    void saveTeam(@NotNull Team team);

    void loadTeams();

    boolean loadTeam(JsonObject teamObject);

    boolean deleteTeam(@NotNull Team team);

    void saveInvites();

    void loadInvites();

    void deleteInvite(@NotNull TeamInvite teamInvite);
}
