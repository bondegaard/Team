package dk.bondegaard.team.teams.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamPerms {

    private TeamRole invitePlayers = TeamRole.LEADER;
    private TeamRole sendTeamMessages = TeamRole.MEMBER;

}
