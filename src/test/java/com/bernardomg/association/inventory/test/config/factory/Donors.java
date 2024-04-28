
package com.bernardomg.association.inventory.test.config.factory;

import com.bernardomg.association.inventory.domain.model.Donor;

public final class Donors {

    public static final Donor emptyName() {
        return Donor.builder()
            .withName(" ")
            .withMember(DonorConstants.MEMBER)
            .withNumber(DonorConstants.NUMBER)
            .build();
    }

    public static final Donor valid() {
        return Donor.builder()
            .withName(DonorConstants.NAME)
            .withMember(DonorConstants.MEMBER)
            .withNumber(DonorConstants.NUMBER)
            .build();
    }

}
