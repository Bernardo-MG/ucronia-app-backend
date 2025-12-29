
package com.bernardomg.association.sponsor.test.configuration.factory;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.model.Profile.ContactChannel;
import com.bernardomg.association.profile.domain.model.ProfileName;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethods;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntityConstants;
import com.bernardomg.association.sponsor.domain.model.Sponsor;

public final class Sponsors {

    public static final Sponsor created() {
        final ProfileName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new Sponsor(ProfileConstants.IDENTIFIER, 1L, name, ProfileConstants.BIRTH_DATE, List.of(contactChannel),
            List.of(SponsorConstants.YEAR), ProfileConstants.COMMENTS, Set.of(SponsorEntityConstants.CONTACT_TYPE));
    }

    public static final Sponsor forNumber(final long number) {
        final ProfileName name;

        name = new ProfileName("Profile " + number, "Last name " + number);
        return new Sponsor(Objects.toString(number * 10), number * 10, name, ProfileConstants.BIRTH_DATE, List.of(),
            List.of(SponsorConstants.YEAR), ProfileConstants.COMMENTS, Set.of(SponsorEntityConstants.CONTACT_TYPE));
    }

    public static final Sponsor nameChange() {
        final ProfileName name;

        name = new ProfileName("Profile 123", "Last name");
        return new Sponsor(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(), List.of(SponsorConstants.YEAR), ProfileConstants.COMMENTS,
            Set.of(SponsorEntityConstants.CONTACT_TYPE));
    }

    public static final Sponsor nameChangePatch() {
        final ProfileName name;

        name = new ProfileName("Profile 123", "Last name");
        return new Sponsor(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(), List.of(SponsorConstants.YEAR), ProfileConstants.COMMENTS,
            Set.of(SponsorEntityConstants.CONTACT_TYPE));
    }

    public static final Sponsor noContactChannel() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Sponsor(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(), List.of(SponsorConstants.YEAR), ProfileConstants.COMMENTS,
            Set.of(SponsorEntityConstants.CONTACT_TYPE));
    }

    public static final Sponsor padded() {
        final ProfileName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ProfileName(" " + ProfileConstants.FIRST_NAME + " ", " " + ProfileConstants.LAST_NAME + " ");
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new Sponsor(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(contactChannel), List.of(SponsorConstants.YEAR), ProfileConstants.COMMENTS,
            Set.of(SponsorEntityConstants.CONTACT_TYPE));
    }

    public static final Sponsor toConvert() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Sponsor(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(), List.of(), ProfileConstants.COMMENTS, Set.of());
    }

    public static final Sponsor toCreate() {
        final ProfileName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new Sponsor(ProfileConstants.IDENTIFIER, 0L, name, ProfileConstants.BIRTH_DATE, List.of(contactChannel),
            List.of(SponsorConstants.YEAR), ProfileConstants.COMMENTS, Set.of(SponsorEntityConstants.CONTACT_TYPE));
    }

    public static final Sponsor valid() {
        final ProfileName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new Sponsor(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(contactChannel), List.of(SponsorConstants.YEAR), ProfileConstants.COMMENTS,
            Set.of(SponsorEntityConstants.CONTACT_TYPE));
    }

    public static final Sponsor withoutType() {
        final ProfileName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new Sponsor(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(contactChannel), List.of(SponsorConstants.YEAR), ProfileConstants.COMMENTS, Set.of());
    }

}
