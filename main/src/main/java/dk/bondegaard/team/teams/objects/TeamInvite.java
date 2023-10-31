package dk.bondegaard.team.teams.objects;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TeamInvite {

    private final int inviteID;

    private final int teamId;

    private final String senderUuid;
    private final String senderName;

    private final String targetUuid;
    private final String targetName;

    private final Timestamp timeSend;
}
