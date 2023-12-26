
package com.bernardomg.association.membership.test.fee.util.model;

import com.bernardomg.association.membership.fee.model.MemberFee;

public final class MemberFees {

    public static final MemberFee noSurname() {
        return MemberFee.builder()
            .memberId(1L)
            .memberName("Member 1")
            .date(Fees.DATE)
            .paid(true)
            .build();
    }

    public static final MemberFee paid() {
        return MemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(Fees.DATE)
            .paid(true)
            .build();
    }

    private MemberFees() {
        super();
    }

}
