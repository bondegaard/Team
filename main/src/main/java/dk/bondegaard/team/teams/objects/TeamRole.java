package dk.bondegaard.team.teams.objects;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TeamRole {

    LEADER(1, "leder", "§c§lLeder"),
    COLEADER(2, "coleder", "§c§lCo-Leder"),
    MANAGER(3, "manager", "§e§lManager"),
    MEMBER(4, "member", "§7§lMember");


    private final int id;
    private final String name;
    private final String displayName;

    public static TeamRole byID(int id) {
        for (TeamRole teamRole : TeamRole.values()) {
            if (teamRole.id == id)
                return teamRole;
        }
        return TeamRole.MEMBER;
    }
}
