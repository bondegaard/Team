package dk.bondegaard.team;

import common.VersionSupport_v1_12_R1;
import common.VersionSupport_v1_8_R3;
import dk.bondegaard.team.common.VersionSupport;
import dk.bondegaard.team.utils.NmsVersion;
import org.jetbrains.annotations.NotNull;

public class VersionSupportResolver {

    public static VersionSupport versionSupportFrom(final @NotNull NmsVersion nmsVersion) throws IllegalArgumentException {
        switch (nmsVersion) {
            case v1_8_R3:
                return new VersionSupport_v1_8_R3();
            case v1_12_R1:
                return new VersionSupport_v1_12_R1();
            default:
                throw new IllegalArgumentException(
                        String.format("Unsupported version. Could not find compatible %s implementation for %s %s.",
                                VersionSupport.class.getName(),
                                nmsVersion.getClass().getName(),
                                nmsVersion.name())
                );
        }
    }

}
