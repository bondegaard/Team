package dk.bondegaard.team.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import dk.bondegaard.team.Main;
import dk.bondegaard.team.teams.TeamUtils;
import dk.bondegaard.team.teams.objects.Team;
import dk.bondegaard.team.teams.objects.TeamInvite;
import dk.bondegaard.team.utils.GsonUtil;
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

    @Override
    public boolean deleteTeam(@NotNull Team team) {
        if (!TeamUtils.existTeam(team)) return false;

        File teamFile = new File(Main.getInstance().getDataFolder(), "teams/" + team.getTeamID() + ".json");
        if (teamFile.exists() && !teamFile.isDirectory()) {
            teamFile.delete();
        }

        Main.getInstance().getTeamHandler().getTeams().remove(team);
        return true;
    }

    @Override
    public void saveInvites() {
        File directory = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/invites/");

        if (!directory.exists() || !directory.isDirectory()) {
            directory.mkdirs();
        }

        Gson gson = new Gson();

        for (TeamInvite teamInvite : Main.getInstance().getTeamInviteHandler().getInvites()) {
            JsonObject teamJson = GsonUtil.serialize(teamInvite);

            String filename = teamInvite.getInviteID() + ".json";
            File teamFile = new File(directory, filename);

            try (FileWriter fileWriter = new FileWriter(teamFile)) {
                gson.toJson(teamJson, fileWriter);
            } catch (IOException e) {
                Main.getInstance().getLogger().warning("Error saving invite-" + teamInvite.getInviteID() + " to file: " + e.getMessage());
            }
        }
        Main.getInstance().getLogger().info("Saved all invites!");
    }

    @Override
    public void loadInvites() {
        File directory = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/invites/");

        if (!directory.exists() || !directory.isDirectory()) {
            directory.mkdirs();
        }

        File[] jsonFiles = directory.listFiles((dir, name) -> name.endsWith(".json"));

        if (jsonFiles == null) {
            return;
        }

        for (File jsonFile : jsonFiles) {
            try (FileReader fileReader = new FileReader(jsonFile)) {
                JsonParser jsonParser = new JsonParser();
                JsonObject inviteJson = jsonParser.parse(fileReader).getAsJsonObject();
                TeamInvite teamInvite = GsonUtil.deserialize(inviteJson, TeamInvite.class);
                if (Main.getInstance().getTeamInviteHandler().inviteExist(teamInvite.getInviteID())) continue;
                Main.getInstance().getTeamInviteHandler().getInvites().add(teamInvite);

            } catch (IOException | JsonSyntaxException e) {
                Main.getInstance().getLogger().warning("Error loading team from file: " + e.getMessage());
            }
        }
    }

    @Override
    public void deleteInvite(@NotNull TeamInvite teamInvite) {
        if (!Main.getInstance().getTeamInviteHandler().inviteExist(teamInvite)) return;

        File teamFile = new File(Main.getInstance().getDataFolder(), "/invites/" + teamInvite.getInviteID() + ".json");
        if (teamFile.exists() && !teamFile.isDirectory()) {
            teamFile.delete();
        }

        Main.getInstance().getTeamInviteHandler().getInvites().remove(teamInvite);
        return;
    }
}
