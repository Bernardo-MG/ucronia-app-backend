
package com.bernardomg.association.sponsor.test.configuration.factory;

import java.util.List;
import java.util.Set;

import com.bernardomg.association.sponsor.domain.model.Sponsor;
import com.bernardomg.association.sponsor.domain.model.Sponsor.ContactMethod;
import com.bernardomg.association.sponsor.domain.model.SponsorProfile;
import com.bernardomg.association.sponsor.domain.model.SponsorProfile.ContactChannel;
import com.bernardomg.association.sponsor.domain.model.SponsorProfile.Name;

public final class SponsorProfiles {

    public static final SponsorProfile valid() {
        final Name name;

        name = new Name(SponsorConstants.FIRST_NAME, SponsorConstants.LAST_NAME);
        return new SponsorProfile(SponsorConstants.IDENTIFIER, SponsorConstants.NUMBER, name,
            SponsorConstants.BIRTH_DATE, List.of(), SponsorConstants.ADDRESS, SponsorConstants.COMMENTS,
            Set.of(Sponsor.PROFILE_TYPE));
    }

    public static final SponsorProfile withEmail() {
        final Name           name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new Name(SponsorConstants.FIRST_NAME, SponsorConstants.LAST_NAME);
        contactMethod = new ContactMethod(SponsorContactMethodConstants.NUMBER, SponsorContactMethodConstants.EMAIL);
        contactChannel = new ContactChannel(contactMethod, SponsorConstants.EMAIL);
        return new SponsorProfile(SponsorConstants.IDENTIFIER, SponsorConstants.NUMBER, name,
            SponsorConstants.BIRTH_DATE, List.of(contactChannel), SponsorConstants.ADDRESS, SponsorConstants.COMMENTS,
            Set.of(Sponsor.PROFILE_TYPE));
    }

    public static final SponsorProfile withType(final String type) {
        final Name name;

        name = new Name(SponsorConstants.FIRST_NAME, SponsorConstants.LAST_NAME);
        return new SponsorProfile(SponsorConstants.IDENTIFIER, SponsorConstants.NUMBER, name,
            SponsorConstants.BIRTH_DATE, List.of(), SponsorConstants.ADDRESS, SponsorConstants.COMMENTS, Set.of(type));
    }

    private SponsorProfiles() {
        super();
    }

}
