
package com.bernardomg.association.membership.fee.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class FeeMember {

    private final String fullName;

    private final long   number;

}
