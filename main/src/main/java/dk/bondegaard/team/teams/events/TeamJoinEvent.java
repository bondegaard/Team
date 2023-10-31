package dk.bondegaard.team.teams.events;

import dk.bondegaard.team.teams.objects.Team;
import dk.bondegaard.team.teams.objects.TeamInvite;

import dk.bondegaard.team.utils.events.CancellableEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor @Getter
public class TeamJoinEvent extends CancellableEvent {

    private final Player player;

    private final Team team;

    private final TeamInvite teamInvite;
}
