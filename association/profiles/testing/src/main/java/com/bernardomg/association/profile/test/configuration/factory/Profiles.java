
package com.bernardomg.association.profile.test.configuration.factory;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.domain.model.Profile.ContactChannel;
import com.bernardomg.association.profile.domain.model.Profile.Name;

public final class Profiles {

    public static final Profile created() {
        final Name name;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Profile(Optional.of(ProfileConstants.IDENTIFIER), 1L, name, Optional.of(ProfileConstants.BIRTH_DATE),
            List.of(), Optional.of(ProfileConstants.ADDRESS), Optional.of(ProfileConstants.COMMENTS), Set.of());
    }

    public static final Profile createdWithEmail() {
        final Name           name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new Profile(Optional.of(ProfileConstants.IDENTIFIER), 1L, name, Optional.of(ProfileConstants.BIRTH_DATE),
            List.of(contactChannel), Optional.of(ProfileConstants.ADDRESS), Optional.of(ProfileConstants.COMMENTS),
            Set.of());
    }

    public static final Profile firstNameChange() {
        final Name name;

        name = new Name(ProfileConstants.CHANGED_FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Profile(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(), Optional.of(ProfileConstants.ADDRESS),
            Optional.of(ProfileConstants.COMMENTS), Set.of());
    }

    public static final Profile nameChange() {
        final Name name;

        name = new Name("Name 123", "Last name");
        return new Profile(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(), Optional.empty(), Optional.empty(), Set.of());
    }

    public static final Profile nameChangePatch() {
        final Name name;

        name = new Name("Name 123", "Last name");
        return new Profile(Optional.empty(), ProfileConstants.NUMBER, name, Optional.empty(), List.of(),
            Optional.empty(), Optional.empty(), Set.of());
    }

    public static final Profile padded() {
        final Name name;

        name = new Name(" " + ProfileConstants.FIRST_NAME + " ", " " + ProfileConstants.LAST_NAME + " ");
        return new Profile(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(),
            Optional.of(" " + Optional.of(ProfileConstants.ADDRESS) + " "),
            Optional.of(" " + Optional.of(ProfileConstants.COMMENTS) + " "), Set.of());
    }

    public static final Profile valid() {
        final Name name;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Profile(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(), Optional.of(ProfileConstants.ADDRESS),
            Optional.of(ProfileConstants.COMMENTS), Set.of());
    }

    public static final Profile withEmail() {
        final Name           name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new Profile(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(contactChannel), Optional.of(ProfileConstants.ADDRESS),
            Optional.of(ProfileConstants.COMMENTS), Set.of());
    }

    public static final Profile withEmailAndPhone() {
        final Name           name;
        final ContactChannel contactChannelA;
        final ContactChannel contactChannelB;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactChannelA = new ContactChannel(ContactMethods.email(), ProfileConstants.EMAIL);
        contactChannelB = new ContactChannel(ContactMethods.phone(), ProfileConstants.PHONE);
        return new Profile(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(contactChannelA, contactChannelB),
            Optional.of(ProfileConstants.ADDRESS), Optional.of(ProfileConstants.COMMENTS), Set.of());
    }

    public static final Profile withTwoEmails() {
        final Name           name;
        final ContactChannel contactChannelA;
        final ContactChannel contactChannelB;
        final ContactMethod  contactMethod;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannelA = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        contactChannelB = new ContactChannel(contactMethod, ProfileConstants.ALTERNATIVE_EMAIL);
        return new Profile(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(contactChannelA, contactChannelB),
            Optional.of(ProfileConstants.ADDRESS), Optional.of(ProfileConstants.COMMENTS), Set.of());
    }

    public static final Profile withType(final String type) {
        final Name name;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Profile(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(), Optional.of(ProfileConstants.ADDRESS),
            Optional.of(ProfileConstants.COMMENTS), Set.of(type));
    }

    private Profiles() {
        super();
    }

}
