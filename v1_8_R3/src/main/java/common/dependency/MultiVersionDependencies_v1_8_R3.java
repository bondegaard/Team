package common.dependency;

import common.dependency.dependencies.fawe.FaweDependency_v1_8_R3;
import dk.bondegaard.team.common.dependency.DependencyProvider;
import dk.bondegaard.team.common.dependency.MultiVersionDependencies;
import dk.bondegaard.team.common.dependency.dependencies.fawe.FaweDependency;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MultiVersionDependencies_v1_8_R3 implements MultiVersionDependencies {

    @Override
    public @NotNull DependencyProvider<FaweDependency> getFaweDependencyProvider() {
        return new DependencyProvider<FaweDependency>() {
            @Override
            public @Nullable FaweDependency findActiveDependency() {
                return createDependencyIfFound(FaweDependency_v1_8_R3::new, "FastAsyncWorldEdit");
            }

            @Override
            public Class<FaweDependency> getDependencyClass() {
                return FaweDependency.class;
            }
        };
    }

}
