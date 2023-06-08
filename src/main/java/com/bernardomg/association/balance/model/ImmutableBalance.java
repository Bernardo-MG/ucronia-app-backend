
package com.bernardomg.association.balance.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class ImmutableBalance implements Balance {

    private final Float amount;

}
