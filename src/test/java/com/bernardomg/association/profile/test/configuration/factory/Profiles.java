
package com.bernardomg.association.profile.test.configuration.factory;

import java.util.List;
import java.util.Set;

import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.domain.model.Profile.ContactChannel;
import com.bernardomg.association.profile.domain.model.ProfileName;

public final class Profiles {

    public static final Profile alternative() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.ALTERNATIVE_FIRST_NAME, ProfileConstants.ALTERNATIVE_LAST_NAME);
        return new Profile(ProfileConstants.ALTERNATIVE_IDENTIFIER, ProfileConstants.ALTERNATIVE_NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(), ProfileConstants.COMMENTS, Set.of());
    }

    public static final Profile created() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Profile(ProfileConstants.IDENTIFIER, 1L, name, ProfileConstants.BIRTH_DATE, List.of(),
            ProfileConstants.COMMENTS, Set.of());
    }

    public static final Profile createdWithEmail() {
        final ProfileName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new Profile(ProfileConstants.IDENTIFIER, 1L, name, ProfileConstants.BIRTH_DATE, List.of(contactChannel),
            ProfileConstants.COMMENTS, Set.of());
    }

    public static final Profile emptyName() {
        final ProfileName name;

        name = new ProfileName(" ", " ");
        return new Profile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(), "", Set.of());
    }

    public static final Profile nameChange() {
        final ProfileName name;

        name = new ProfileName("Profile 123", "Last name");
        return new Profile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(), "", Set.of());
    }

    public static final Profile nameChangePatch() {
        final ProfileName name;

        name = new ProfileName("Profile 123", "Last name");
        return new Profile(null, ProfileConstants.NUMBER, name, null, List.of(), "", Set.of());
    }

    public static final Profile noIdentifier() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Profile("", ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE, List.of(), "", Set.of());
    }

    public static final Profile padded() {
        final ProfileName name;

        name = new ProfileName(" " + ProfileConstants.FIRST_NAME + " ", " " + ProfileConstants.LAST_NAME + " ");
        return new Profile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(), " " + ProfileConstants.COMMENTS + " ", Set.of());
    }

    public static final Profile toCreate() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Profile(ProfileConstants.IDENTIFIER, 0L, name, ProfileConstants.BIRTH_DATE, List.of(),
            ProfileConstants.COMMENTS, Set.of());
    }

    public static final Profile toCreateNoIdentifier() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Profile("", 0L, name, ProfileConstants.BIRTH_DATE, List.of(), "", Set.of());
    }

    public static final Profile toCreateWithEmail() {
        final ProfileName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new Profile(ProfileConstants.IDENTIFIER, 0L, name, ProfileConstants.BIRTH_DATE, List.of(contactChannel),
            ProfileConstants.COMMENTS, Set.of());
    }

    public static final Profile valid() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Profile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(), ProfileConstants.COMMENTS, Set.of());
    }

    public static final Profile validNew() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Profile("", ProfileConstants.NUMBER, name, null, List.of(), "", Set.of());
    }

    public static final Profile withEmail() {
        final ProfileName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new Profile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(contactChannel), ProfileConstants.COMMENTS, Set.of());
    }

    public static final Profile withEmailAndPhone() {
        final ProfileName    name;
        final ContactChannel contactChannelA;
        final ContactChannel contactChannelB;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactChannelA = new ContactChannel(ContactMethods.email(), ProfileConstants.EMAIL);
        contactChannelB = new ContactChannel(ContactMethods.phone(), ProfileConstants.PHONE);
        return new Profile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(contactChannelA, contactChannelB), ProfileConstants.COMMENTS, Set.of());
    }

    public static final Profile withTwoEmails() {
        final ProfileName    name;
        final ContactChannel contactChannelA;
        final ContactChannel contactChannelB;
        final ContactMethod  contactMethod;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannelA = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        contactChannelB = new ContactChannel(contactMethod, ProfileConstants.ALTERNATIVE_EMAIL);
        return new Profile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(contactChannelA, contactChannelB), ProfileConstants.COMMENTS, Set.of());
    }

    public static final Profile withType(final String type) {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Profile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(), ProfileConstants.COMMENTS, Set.of(type));
    }

}
