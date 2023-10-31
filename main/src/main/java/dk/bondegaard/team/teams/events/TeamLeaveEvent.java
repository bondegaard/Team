package dk.bondegaard.team.teams.events;

import dk.bondegaard.team.teams.objects.Team;
import dk.bondegaard.team.teams.objects.TeamMember;
import dk.bondegaard.team.utils.events.CancellableEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public class TeamLeaveEvent extends CancellableEvent {

    private final Team team;

    private final TeamMember teamMember;

}
