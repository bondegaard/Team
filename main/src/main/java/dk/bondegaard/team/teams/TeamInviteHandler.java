package dk.bondegaard.team.teams;

import dk.bondegaard.team.teams.objects.Team;
import dk.bondegaard.team.teams.objects.TeamInvite;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeamInviteHandler {

    @Getter
    private List<TeamInvite> invites = new ArrayList<>();


    public boolean inviteExist(int inviteID) {
        return invites.stream().anyMatch(teamInvite -> teamInvite.getInviteID() == inviteID);
    }

    public boolean inviteExist(TeamInvite ti) {
        return invites.stream().anyMatch(teamInvite -> teamInvite.getInviteID() == ti.getInviteID());
    }

    public boolean hasInvite(OfflinePlayer player) {
        return invites.stream().anyMatch(teamInvite -> teamInvite.getTargetUuid().equalsIgnoreCase(player.getUniqueId().toString()));
    }

    public boolean hasInvite(OfflinePlayer player, Team team) {
        return invites.stream().filter(teamInvite -> teamInvite.getTeamId() == team.getTeamID()).anyMatch(teamInvite -> teamInvite.getTargetUuid().equalsIgnoreCase(player.getUniqueId().toString()));
    }

    public boolean hasInvite(OfflinePlayer player, String teamName) {
        Team team = TeamUtils.getTeam(teamName);
        if (team == null) return false;
        return hasInvite(player, team);
    }

    public TeamInvite getInvite(int inviteID) {
        return invites.stream().filter(teamInvite -> teamInvite.getInviteID() == inviteID).findFirst().orElse(null);
    }

    public List<TeamInvite> getInvites(OfflinePlayer player) {
        return invites.stream().filter(teamInvite -> teamInvite.getTargetUuid().equalsIgnoreCase(player.getUniqueId().toString())).collect(Collectors.toList());
    }

    public @Nullable TeamInvite getInvite(OfflinePlayer player, Team team) {
        return invites.stream().filter(teamInvite -> teamInvite.getTeamId() == team.getTeamID()).filter(teamInvite -> teamInvite.getTargetUuid().equalsIgnoreCase(player.getUniqueId().toString())).findFirst().orElse(null);
    }
    public @Nullable TeamInvite getInvite(OfflinePlayer player, String teamName) {
        Team team = TeamUtils.getTeam(teamName);
        if (team == null) return null;
        return getInvite(player, team);
    }

    public int getNewInviteID() {
        return invites.stream()
                .mapToInt(TeamInvite::getInviteID)
                .max()
                .orElse(0) + 1;
    }
}
