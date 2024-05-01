
package com.bernardomg.association.inventory.test.config.factory;

import com.bernardomg.association.inventory.domain.model.Donor;

public final class Donors {

    public static final Donor emptyName() {
        return Donor.builder()
            .withName(" ")
            .withMember(DonorConstants.MEMBER_NOT_EXISTING)
            .withNumber(DonorConstants.NUMBER)
            .build();
    }

    public static final Donor noMember() {
        return Donor.builder()
            .withName(DonorConstants.NAME)
            .withMember(DonorConstants.MEMBER_NOT_EXISTING)
            .withNumber(DonorConstants.NUMBER)
            .build();
    }

}
