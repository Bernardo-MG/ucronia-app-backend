
package com.bernardomg.association.library.book.test.configuration.factory;

import com.bernardomg.association.library.lending.domain.model.BookLending.Borrower;
import com.bernardomg.association.profile.domain.model.ProfileName;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class Borrowers {

    public static final Borrower alternative() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.ALTERNATIVE_FIRST_NAME, ProfileConstants.ALTERNATIVE_LAST_NAME);
        return new Borrower(ProfileConstants.ALTERNATIVE_NUMBER, name);
    }

    public static final Borrower valid() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Borrower(ProfileConstants.NUMBER, name);
    }

    private Borrowers() {
        super();
    }

}
