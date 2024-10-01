
package com.bernardomg.association.inventory.test.configuration.factory;

import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.inventory.domain.model.DonorName;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;

public final class Donors {

    public static final Donor emptyName() {
        final DonorName name;

        name = DonorName.builder()
            .withFirstName(" ")
            .withLastName(PersonConstants.LAST_NAME)
            .build();
        return new Donor(DonorConstants.NUMBER, name);
    }

    public static final Donor valid() {
        final DonorName name;

        name = DonorName.builder()
            .withFirstName(PersonConstants.FIRST_NAME)
            .withLastName(PersonConstants.LAST_NAME)
            .build();
        return new Donor(DonorConstants.NUMBER, name);
    }

}
