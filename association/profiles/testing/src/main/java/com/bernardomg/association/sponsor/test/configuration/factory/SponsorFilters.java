
package com.bernardomg.association.sponsor.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.sponsor.domain.filter.SponsorFilter;

public final class SponsorFilters {

    public static final SponsorFilter alternativeFirstName() {
        return new SponsorFilter(Optional.of(ProfileConstants.ALTERNATIVE_FIRST_NAME));
    }

    public static final SponsorFilter empty() {
        return new SponsorFilter(Optional.empty());
    }

    public static final SponsorFilter firstName() {
        return new SponsorFilter(Optional.of(ProfileConstants.FIRST_NAME));
    }

    public static final SponsorFilter fullName() {
        return new SponsorFilter(Optional.of(ProfileConstants.FULL_NAME));
    }

    public static final SponsorFilter lastName() {
        return new SponsorFilter(Optional.of(ProfileConstants.LAST_NAME));
    }

    public static final SponsorFilter partialName() {
        return new SponsorFilter(
            Optional.of(ProfileConstants.FIRST_NAME.substring(0, ProfileConstants.FIRST_NAME.length() - 2)));
    }

    private SponsorFilters() {
        super();
    }

}
