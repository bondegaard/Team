package dk.bondegaard.team.teams.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamPerms {

    private TeamRole invitePlayers = TeamRole.LEADER;
    private TeamRole kickPlayers = TeamRole.LEADER;
    private TeamRole sendTeamMessages = TeamRole.MEMBER;
    private TeamRole withdrawFromBank = TeamRole.LEADER;
    private TeamRole canPromoteAndDemote = TeamRole.LEADER;
}
