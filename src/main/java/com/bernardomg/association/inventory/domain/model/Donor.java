
package com.bernardomg.association.inventory.domain.model;

import com.bernardomg.association.member.domain.model.Member;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public final class Donor {

    private final Member member;

    private final String name;

    @Builder.Default
    private final long   number = -1L;

}
