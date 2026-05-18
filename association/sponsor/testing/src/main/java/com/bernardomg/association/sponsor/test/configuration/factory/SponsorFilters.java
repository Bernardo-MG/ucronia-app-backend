
package com.bernardomg.association.sponsor.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.association.sponsor.domain.filter.SponsorFilter;

public final class SponsorFilters {

    public static final SponsorFilter alternativeFirstName() {
        return new SponsorFilter(Optional.of(SponsorConstants.ALTERNATIVE_FIRST_NAME));
    }

    public static final SponsorFilter empty() {
        return new SponsorFilter(Optional.empty());
    }

    public static final SponsorFilter firstName() {
        return new SponsorFilter(Optional.of(SponsorConstants.FIRST_NAME));
    }

    public static final SponsorFilter fullName() {
        return new SponsorFilter(Optional.of(SponsorConstants.FULL_NAME));
    }

    public static final SponsorFilter lastName() {
        return new SponsorFilter(Optional.of(SponsorConstants.LAST_NAME));
    }

    public static final SponsorFilter partialName() {
        return new SponsorFilter(
            Optional.of(SponsorConstants.FIRST_NAME.substring(0, SponsorConstants.FIRST_NAME.length() - 2)));
    }

    private SponsorFilters() {
        super();
    }

}
