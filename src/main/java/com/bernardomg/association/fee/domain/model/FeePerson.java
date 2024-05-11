
package com.bernardomg.association.fee.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public final class FeePerson {

    private final String fullName;

    private final long   number;

}
