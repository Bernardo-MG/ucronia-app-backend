
package com.bernardomg.association.library.gamesystem.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public final class GameSystem {

    private String name;

}
