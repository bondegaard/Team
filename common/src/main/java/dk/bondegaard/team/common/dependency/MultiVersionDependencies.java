package dk.bondegaard.team.common.dependency;

import dk.bondegaard.team.common.dependency.dependencies.fawe.FaweDependency;
import org.jetbrains.annotations.NotNull;

public interface MultiVersionDependencies {

    @NotNull
    DependencyProvider<FaweDependency> getFaweDependencyProvider();

}
