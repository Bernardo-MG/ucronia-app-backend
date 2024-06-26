
package com.bernardomg.association.inventory.test.config.factory;

import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.inventory.domain.model.DonorName;
import com.bernardomg.association.person.test.config.factory.PersonConstants;

public final class Donors {

    public static final Donor emptyName() {
        final DonorName name;

        name = DonorName.builder()
            .withFirstName(" ")
            .withLastName(PersonConstants.LAST_NAME)
            .build();
        return Donor.builder()
            .withNumber(DonorConstants.NUMBER)
            .withName(name)
            .build();
    }

    public static final Donor valid() {
        final DonorName name;

        name = DonorName.builder()
            .withFirstName(PersonConstants.NAME)
            .withLastName(PersonConstants.LAST_NAME)
            .build();
        return Donor.builder()
            .withNumber(DonorConstants.NUMBER)
            .withName(name)
            .build();
    }

}
