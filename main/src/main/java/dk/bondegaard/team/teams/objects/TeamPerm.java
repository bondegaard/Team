package dk.bondegaard.team.teams.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamPerm {

    private final String name;
    private Boolean allow;

    public TeamPerm(String n, Boolean p) {
        this.allow = p;
        this.name = n;
    }


    public void togglePerm() {
        this.allow = !this.allow;
    }
}
