package dk.bondegaard.team.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import dk.bondegaard.team.Main;
import dk.bondegaard.team.teams.TeamUtils;
import dk.bondegaard.team.teams.objects.Team;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonData implements DataProvider {

    @Override
    public void init() {
    }

    @Override
    public void saveTeams(@NotNull List<Team> teams) {
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

    @Override
    public void saveTeam(@NotNull Team team) {
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

    @Override
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

    @Override
    public boolean loadTeam(JsonObject teamObject) {
        if (!teamObject.has("id")) return false;
        int id = teamObject.get("id").getAsInt();

        if (TeamUtils.existTeam(id)) return false;

        Main.getInstance().getTeamHandler().getTeams().add(new Team(id).loadTeam(teamObject));
        return true;
    }
}
