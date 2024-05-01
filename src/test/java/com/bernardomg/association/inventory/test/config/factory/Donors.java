
package com.bernardomg.association.inventory.test.config.factory;

import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.test.config.factory.Members;

public final class Donors {

    public static final Donor emptyName() {
        return Donor.builder()
            .withName(" ")
            .withMember(Members.active())
            .withNumber(DonorConstants.NUMBER)
            .build();
    }

    public static final Donor noMember() {
        return Donor.builder()
            .withName(DonorConstants.NAME)
            .withMember(Member.builder()
                .build())
            .withNumber(DonorConstants.NUMBER)
            .build();
    }

}
