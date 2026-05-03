
package com.bernardomg.association.guest.test.configuration.factory;

import java.util.List;
import java.util.Set;

import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.guest.domain.model.Guest.ContactMethod;
import com.bernardomg.association.guest.domain.model.GuestProfile;
import com.bernardomg.association.guest.domain.model.GuestProfile.ContactChannel;
import com.bernardomg.association.guest.domain.model.GuestProfile.Name;

public final class GuestProfiles {

    public static final GuestProfile valid() {
        final Name name;

        name = new Name(GuestConstants.FIRST_NAME, GuestConstants.LAST_NAME);
        return new GuestProfile(GuestConstants.IDENTIFIER, GuestConstants.NUMBER, name, GuestConstants.BIRTH_DATE,
            List.of(), GuestConstants.ADDRESS, GuestConstants.COMMENTS, Set.of(Guest.PROFILE_TYPE));
    }

    public static final GuestProfile withEmail() {
        final Name           name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new Name(GuestConstants.FIRST_NAME, GuestConstants.LAST_NAME);
        contactMethod = new ContactMethod(GuestContactMethodConstants.NUMBER, GuestContactMethodConstants.EMAIL);
        contactChannel = new ContactChannel(contactMethod, GuestConstants.EMAIL);
        return new GuestProfile(GuestConstants.IDENTIFIER, GuestConstants.NUMBER, name, GuestConstants.BIRTH_DATE,
            List.of(contactChannel), GuestConstants.ADDRESS, GuestConstants.COMMENTS, Set.of(Guest.PROFILE_TYPE));
    }

    public static final GuestProfile withType(final String type) {
        final Name name;

        name = new Name(GuestConstants.FIRST_NAME, GuestConstants.LAST_NAME);
        return new GuestProfile(GuestConstants.IDENTIFIER, GuestConstants.NUMBER, name, GuestConstants.BIRTH_DATE,
            List.of(), GuestConstants.ADDRESS, GuestConstants.COMMENTS, Set.of(type));
    }

    private GuestProfiles() {
        super();
    }

}
