
package com.bernardomg.association.fee.domain.repository;

public interface ActiveMemberRepository {

    public boolean isActive(final long number);

    public boolean isActivePreviousMonth(final long number);

}
