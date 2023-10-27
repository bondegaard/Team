package dk.bondegaard.team.teams.objects;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TeamMember {

    private final String uuid;

    private final String name;

    private TeamRole role;

    private List<TeamPerm> perms;


    public TeamMember(String uuid, String name, TeamRole role, List<TeamPerm> perms) {
        this.uuid = uuid;
        this.name = name;
        this.role = role;
        this.perms = perms;
    }

    public TeamMember(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.role = TeamRole.MEMBER;
        this.perms = new ArrayList<>();
    }

    public JsonObject serialize() {
        JsonObject teamMemberObject = new JsonObject();
        teamMemberObject.addProperty("uuid", uuid);
        teamMemberObject.addProperty("name", name);
        teamMemberObject.addProperty("role", role.getId());

        // Perms
        JsonArray serializedPerms = new JsonArray();
        for (TeamPerm teamPerm : perms) {
            JsonObject permObject = new JsonObject();
            permObject.addProperty("name", teamPerm.getName());
            permObject.addProperty("allow", teamPerm.getAllow());
            serializedPerms.add(permObject);
        }
        teamMemberObject.add("perms", serializedPerms);
        return teamMemberObject;
    }

}
