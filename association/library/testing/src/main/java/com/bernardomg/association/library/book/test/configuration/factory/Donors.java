
package com.bernardomg.association.library.book.test.configuration.factory;

import com.bernardomg.association.library.book.domain.model.Donor;
import com.bernardomg.association.library.book.domain.model.Donor.Name;

public final class Donors {

    public static final Donor valid() {
        final Name name;

        name = new Name(DonorConstants.FIRST_NAME, DonorConstants.LAST_NAME);
        return new Donor(DonorConstants.NUMBER, name);
    }

    private Donors() {
        super();
    }

}
