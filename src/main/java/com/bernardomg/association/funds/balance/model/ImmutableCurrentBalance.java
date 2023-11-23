
package com.bernardomg.association.funds.balance.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class ImmutableCurrentBalance implements CurrentBalance {

    private Float results;

    private Float total;

}
