
package com.bernardomg.association.library.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public final class System {

    private String name;

}
