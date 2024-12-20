package dk.bondegaard.team.teams.objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dk.bondegaard.team.utils.GsonUtil;
import dk.bondegaard.team.utils.PlayerUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Team {

    private final int teamID;
    private final List<TeamMember> members = new ArrayList<>();
    private TeamPerms teamPerms;
    private TeamStats teamStats;

    private String name = "";
    private int level = 1;
    @Setter
    private long balance = 0;

    private int maxMembers = 3;


    public Team(int teamID) {
        this.teamID = teamID;
    }

    public Team(int teamID, String name, Player creator) {
        this.teamID = teamID;
        this.name = name;
        this.teamStats = new TeamStats(0, 0, 0, 0, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        this.teamPerms = new TeamPerms();

        // Add owner to team
        TeamMember owner = new TeamMember(creator.getUniqueId().toString(), creator.getName());
        owner.setRole(TeamRole.LEADER);
        members.add(owner);
    }

    public Team loadTeam(JsonObject teamObject) {
        this.deserialize(teamObject);
        this.teamStats.setLastJoin(new Timestamp(System.currentTimeMillis()));
        return this;
    }

    public TeamMember getTeamMember(OfflinePlayer player) {
        return members.stream().filter(teamMember -> teamMember.getUuid().equals(player.getUniqueId().toString())).findFirst().orElse(null);
    }

    public boolean isTeamMember(OfflinePlayer player) {
        return members.stream().anyMatch(teamMember -> teamMember.getUuid().equals(player.getUniqueId().toString()));
    }



    public void sendMessageToTeam(String message) {
        for (String memberUUID: members.stream().map(TeamMember::getUuid).collect(Collectors.toList())) {
            Player player = Bukkit.getPlayer(memberUUID);
            if (player == null) continue;
            PlayerUtil.sendMessage(player, message);
        }
    }

    public void sendMessageToTeam(List<String> messages) {
        for (String memberUUID: members.stream().map(TeamMember::getUuid).collect(Collectors.toList())) {
            Player player = Bukkit.getPlayer(memberUUID);
            if (player == null) continue;
            PlayerUtil.sendMessages(player, messages);
        }
    }


    public JsonObject serialize() {
        JsonObject teamObject = new JsonObject();
        teamObject.addProperty("id", teamID);
        teamObject.addProperty("name", name);
        teamObject.addProperty("level", level);
        teamObject.addProperty("max-members", maxMembers);
        teamObject.addProperty("balance", balance);
        teamObject.add("stats", GsonUtil.serialize(teamStats));

        JsonArray membersObject = new JsonArray();
        for (TeamMember teamMember : members) {
            membersObject.add(teamMember.serialize());
        }

        teamObject.add("members", membersObject);

        return teamObject;
    }

    private void deserialize(JsonObject teamObject) {
        int id = teamObject.get("id").getAsInt();
        if (id != teamID) {
            // Error: wrong team... somehow???
            return;
        }

        this.name = teamObject.get("name").getAsString();
        this.level = teamObject.has("level") ? teamObject.get("level").getAsInt() : 1;
        this.balance = teamObject.has("balance") ? teamObject.get("balance").getAsLong() : 0;
        this.maxMembers = teamObject.has("max-members") ? teamObject.get("max-members").getAsInt() : 3;
        this.teamStats = teamObject.has("stats") ? GsonUtil.deserialize(teamObject.get("stats").getAsJsonObject(), TeamStats.class) : new TeamStats(0, 0, 0, 0, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        this.teamPerms = teamObject.has("perms") ? GsonUtil.deserialize(teamObject.get("perms").getAsJsonObject(), TeamPerms.class) : new TeamPerms();


        // Load members
        JsonArray membersArray = teamObject.get("members").getAsJsonArray();
        for (JsonElement memberElement : membersArray) {
            if (memberElement.isJsonObject()) {
                JsonObject memberObject = memberElement.getAsJsonObject();

                if (!memberObject.has("uuid") || !memberObject.has("name")) continue;

                String uuid = memberObject.get("uuid").getAsString();
                String memberName = memberObject.get("name").getAsString();

                int roleId = memberObject.has("role") ? memberObject.get("role").getAsInt() : -1;
                TeamRole role = TeamRole.byID(roleId);

                TeamMember teamMember = new TeamMember(uuid, memberName, role);
                // Assuming you have a list to store members in your Team class
                members.add(teamMember);
            }
        }
    }

}
