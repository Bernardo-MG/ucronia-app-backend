
package com.bernardomg.association.member.adapter.inbound.jpa.model;

import java.time.Instant;

public interface PaidMembership {

    public Instant getJoined();

    public Long getNumber();

    public long getTotalMonths();

}
