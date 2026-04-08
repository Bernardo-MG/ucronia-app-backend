
package com.bernardomg.association.library.book.test.configuration.factory;

import com.bernardomg.association.library.book.domain.model.Donor;
import com.bernardomg.association.library.book.domain.model.DonorName;

public final class Donors {

    public static final Donor valid() {
        final DonorName name;

        name = new DonorName(DonorConstants.FIRST_NAME, DonorConstants.LAST_NAME);
        return new Donor(DonorConstants.NUMBER, name);
    }

    private Donors() {
        super();
    }

}
