package common;

import common.dependency.MultiVersionDependencies_v1_12_R1;
import common.nms.NmsProvider_v1_12_R1;
import dk.bondegaard.team.common.VersionSupport;
import dk.bondegaard.team.common.dependency.MultiVersionDependencies;
import dk.bondegaard.team.common.nms.NmsProvider;
import org.jetbrains.annotations.NotNull;

public class VersionSupport_v1_12_R1 implements VersionSupport {

    private final NmsProvider nmsProvider = new NmsProvider_v1_12_R1();

    private final MultiVersionDependencies dependencies = new MultiVersionDependencies_v1_12_R1();

    @Override
    public @NotNull NmsProvider getNmsProvider() {
        return this.nmsProvider;
    }

    @Override
    public @NotNull MultiVersionDependencies getDependencies() {
        return this.dependencies;
    }

}
