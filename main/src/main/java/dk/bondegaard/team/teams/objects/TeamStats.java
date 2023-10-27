package dk.bondegaard.team.teams.objects;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TeamStats {

    private final int kills;

    private final int deaths;

    private final int blocksBroken;

    private final int blocksPlaced;

    private final Timestamp created;

    private final Timestamp lastJoin;
}
