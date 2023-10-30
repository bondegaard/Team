package dk.bondegaard.team.teams.objects;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class TeamStats {

    private int kills;

    private int deaths;

    private int blocksBroken;

    private int blocksPlaced;

    private Timestamp created;

    private Timestamp lastJoin;

    private Timestamp lastLoaded;

    public TeamStats(int kills, int deaths, int blocksBroken, int blocksPlaced, Timestamp created, Timestamp lastJoin) {
        this.kills = kills;
        this.deaths = deaths;
        this.blocksBroken = blocksBroken;
        this.blocksPlaced = blocksPlaced;
        this.created = created;
        this.lastJoin = lastJoin;
    }
}
