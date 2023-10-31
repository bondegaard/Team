package dk.bondegaard.team.teams.events;

import dk.bondegaard.team.teams.objects.Team;
import dk.bondegaard.team.teams.objects.TeamMember;
import dk.bondegaard.team.utils.events.CancellableEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@RequiredArgsConstructor @Getter
public class TeamKickEvent extends CancellableEvent {

    private final TeamMember targetmember;
    private final OfflinePlayer target;

    private final TeamMember playerMember;
    private final Player player;

    private final Team team;
}
