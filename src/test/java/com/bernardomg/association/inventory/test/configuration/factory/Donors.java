
package com.bernardomg.association.inventory.test.configuration.factory;

import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;

public final class Donors {

    public static final Donor emptyName() {
        final PersonName name;

        name = new PersonName(" ", PersonConstants.LAST_NAME);
        return new Donor(DonorConstants.NUMBER, name);
    }

    public static final Donor valid() {
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        return new Donor(DonorConstants.NUMBER, name);
    }

}
