
package com.bernardomg.association.inventory.test.config.factory;

import com.bernardomg.association.inventory.domain.model.Donor;

public final class Donors {

    public static final Donor emptyName() {
        return Donor.builder()
            .withName(" ")
            .build();
    }

    public static final Donor valid() {
        return Donor.builder()
            .withName(DonorConstants.NAME)
            .build();
    }

}
