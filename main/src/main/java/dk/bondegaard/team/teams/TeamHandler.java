package dk.bondegaard.team.teams;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import dk.bondegaard.team.Main;
import dk.bondegaard.team.teams.objects.Team;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

        saveTeam(team);
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


    public void saveTeams() {
        String directoryPath = Main.getInstance().getDataFolder().getAbsolutePath() + "/teams/";


        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        Gson gson = new Gson();

        for (Team team : teams) {
            JsonObject teamJson = team.serialize();

            String filename = team.getTeamID() + ".json";
            File teamFile = new File(directory, filename);

            try (FileWriter fileWriter = new FileWriter(teamFile)) {
                gson.toJson(teamJson, fileWriter);
            } catch (IOException e) {
                Main.getInstance().getLogger().warning("Error saving team-" + team.getTeamID() + " to file: " + e.getMessage());
            }
        }
        Main.getInstance().getLogger().info("Saved all teams!");
    }

    public void saveTeam(Team team) {
        String directoryPath = Main.getInstance().getDataFolder().getAbsolutePath() + "/teams/";


        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        Gson gson = new Gson();
        JsonObject teamJson = team.serialize();

        String filename = team.getTeamID() + ".json";
        File teamFile = new File(directory, filename);

        try (FileWriter fileWriter = new FileWriter(teamFile)) {
            gson.toJson(teamJson, fileWriter);
        } catch (IOException e) {
            Main.getInstance().getLogger().warning("Error saving team-" + team.getTeamID() + " to file: " + e.getMessage());
        }
    }

    public void loadTeams() {
        File directory = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/teams/");

        if (!directory.exists() || !directory.isDirectory()) {
            directory.mkdirs();
        }

        Gson gson = new Gson();

        File[] jsonFiles = directory.listFiles((dir, name) -> name.endsWith(".json"));

        if (jsonFiles == null) {
            return;
        }

        for (File jsonFile : jsonFiles) {
            try (FileReader fileReader = new FileReader(jsonFile)) {
                JsonParser jsonParser = new JsonParser();
                JsonObject teamJson = jsonParser.parse(fileReader).getAsJsonObject();
                loadTeam(teamJson);
            } catch (IOException | JsonSyntaxException e) {
                Main.getInstance().getLogger().warning("Error loading team from file: " + e.getMessage());
            }
        }
    }

    public boolean loadTeam(JsonObject teamObject) {
        if (!teamObject.has("id")) return false;
        int id = teamObject.get("id").getAsInt();

        if (TeamUtils.existTeam(id)) return false;

        teams.add(new Team(id).loadTeam(teamObject));
        return true;
    }
}
