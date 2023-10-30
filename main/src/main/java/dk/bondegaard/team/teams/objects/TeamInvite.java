package dk.bondegaard.team.teams.objects;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.Timestamp;

public class TeamInvite {

    private Team team;

    private OfflinePlayer sender;

    private OfflinePlayer target;

    private Timestamp timeSend;
}
