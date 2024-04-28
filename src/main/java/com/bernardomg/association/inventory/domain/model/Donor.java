
package com.bernardomg.association.inventory.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public final class Donor {

    private final long   member;

    private final String name;

    private final long   number;

}
