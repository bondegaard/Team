package dk.bondegaard.team.common.util;

import dk.bondegaard.team.common.util.compmaterial.CompMaterial;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public class WeightedMaterial {

    @NotNull
    private final CompMaterial compMaterial;

    private final double chance;

}
