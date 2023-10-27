package dk.bondegaard.team.common;

import dk.bondegaard.team.common.dependency.MultiVersionDependencies;
import dk.bondegaard.team.common.nms.NmsProvider;
import org.jetbrains.annotations.NotNull;

public interface VersionSupport {

    @NotNull
    NmsProvider getNmsProvider();

    @NotNull
    MultiVersionDependencies getDependencies();

}
