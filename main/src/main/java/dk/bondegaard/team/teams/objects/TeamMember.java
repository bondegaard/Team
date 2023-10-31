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


    /**
     * @param uuid of the player
     * @param name of the player
     * @param role in the team
     */
    public TeamMember(String uuid, String name, TeamRole role) {
        this.uuid = uuid;
        this.name = name;
        this.role = role;
    }

    /**
     * @param uuid of the player
     * @param name of the player
     */
    public TeamMember(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.role = TeamRole.MEMBER;
    }

    public JsonObject serialize() {
        JsonObject teamMemberObject = new JsonObject();
        teamMemberObject.addProperty("uuid", uuid);
        teamMemberObject.addProperty("name", name);
        teamMemberObject.addProperty("role", role.getId());

        return teamMemberObject;
    }

}
