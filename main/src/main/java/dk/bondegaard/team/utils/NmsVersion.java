package dk.bondegaard.team.utils;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
public enum NmsVersion {
    UNKNOWN(-1),
    v1_8_R3(8.8),
    v1_9(9),
    v1_12_R1(12.2),
    v1_16_R3(16.5),
    v1_17_R1(17.1),
    v1_18_R2(18.2),
    v1_19_R2(19.3),
    v1_19_R3(19.4),
    ;

    private final double versionNumber;

    @NotNull
    public static NmsVersion currentNmsVersion() {
        // Assumes format: org.bukkit.craftbukkit.v1_8_R3
        String[] packages = Bukkit.getServer().getClass().getName().split("\\.");

        if (packages.length < 4)
            return UNKNOWN;

        String versionName = packages[3];

        try {
            return valueOf(versionName);
        } catch (IllegalArgumentException exc) {
            return UNKNOWN;
        }
    }

    @Nullable
    public static String craftBukkitVersion() {
        String[] packages = Bukkit.getServer().getClass().getName().split("\\.");

        if (packages.length < 4)
            return null;

        return packages[3];
    }

    public boolean isSameAs(NmsVersion otherVersion) {
        VersionRelation relation = this.relationTo(otherVersion);
        return relation == VersionRelation.SAME_VERSION;
    }

    public boolean isSameOrLater(NmsVersion otherVersion) {
        VersionRelation relation = this.relationTo(otherVersion);
        return relation == VersionRelation.SAME_VERSION || relation == VersionRelation.RELEASED_AFTER;
    }

    public boolean isSameOrEarlier(NmsVersion otherVersion) {
        VersionRelation relation = this.relationTo(otherVersion);
        return relation == VersionRelation.SAME_VERSION || relation == VersionRelation.RELEASED_BEFORE;
    }

    public VersionRelation relationTo(NmsVersion otherVersion) {
        if (this.versionNumber < otherVersion.versionNumber)
            return VersionRelation.RELEASED_BEFORE;

        if (this.versionNumber > otherVersion.versionNumber)
            return VersionRelation.RELEASED_AFTER;

        return VersionRelation.SAME_VERSION;
    }

    public enum VersionRelation {
        RELEASED_BEFORE,
        SAME_VERSION,
        RELEASED_AFTER
    }

}
