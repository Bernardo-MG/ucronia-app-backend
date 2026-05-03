
package com.bernardomg.association.sponsor.test.configuration.factory;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.bernardomg.association.sponsor.domain.model.Sponsor;
import com.bernardomg.association.sponsor.domain.model.Sponsor.Name;

public final class Sponsors {

    public static final Sponsor created() {
        final Name name;

        name = new Name(SponsorConstants.FIRST_NAME, SponsorConstants.LAST_NAME);
        return new Sponsor(SponsorConstants.IDENTIFIER, 1L, name, SponsorConstants.BIRTH_DATE, List.of(),
            List.of(SponsorConstants.YEAR), SponsorConstants.ADDRESS, SponsorConstants.COMMENTS,
            Set.of(Sponsor.PROFILE_TYPE));
    }

    public static final Sponsor firstNameChange() {
        final Name name;

        name = new Name(SponsorConstants.CHANGED_FIRST_NAME, SponsorConstants.LAST_NAME);
        return new Sponsor(SponsorConstants.IDENTIFIER, SponsorConstants.NUMBER, name, SponsorConstants.BIRTH_DATE,
            List.of(), List.of(SponsorConstants.YEAR), SponsorConstants.ADDRESS, SponsorConstants.COMMENTS,
            Set.of(Sponsor.PROFILE_TYPE));
    }

    public static final Sponsor forNumber(final long number) {
        final Name name;

        name = new Name("Name " + number, "Last name " + number);
        return new Sponsor(Objects.toString(number * 10), number * 10, name, SponsorConstants.BIRTH_DATE, List.of(),
            List.of(SponsorConstants.YEAR), SponsorConstants.ADDRESS, SponsorConstants.COMMENTS,
            Set.of(Sponsor.PROFILE_TYPE));
    }

    public static final Sponsor nameChange() {
        final Name name;

        name = new Name("Name 123", "Last name");
        return new Sponsor(SponsorConstants.IDENTIFIER, SponsorConstants.NUMBER, name, SponsorConstants.BIRTH_DATE,
            List.of(), List.of(SponsorConstants.YEAR), SponsorConstants.ADDRESS, SponsorConstants.COMMENTS,
            Set.of(Sponsor.PROFILE_TYPE));
    }

    public static final Sponsor nameChangePatch() {
        final Name name;

        name = new Name("Name 123", "Last name");
        return new Sponsor(SponsorConstants.IDENTIFIER, SponsorConstants.NUMBER, name, SponsorConstants.BIRTH_DATE,
            List.of(), List.of(SponsorConstants.YEAR), SponsorConstants.ADDRESS, SponsorConstants.COMMENTS,
            Set.of(Sponsor.PROFILE_TYPE));
    }

    public static final Sponsor padded() {
        final Name name;

        name = new Name(" " + SponsorConstants.FIRST_NAME + " ", " " + SponsorConstants.LAST_NAME + " ");
        return new Sponsor(SponsorConstants.IDENTIFIER, SponsorConstants.NUMBER, name, SponsorConstants.BIRTH_DATE,
            List.of(), List.of(SponsorConstants.YEAR), SponsorConstants.ADDRESS, SponsorConstants.COMMENTS,
            Set.of(Sponsor.PROFILE_TYPE));
    }

    public static final Sponsor valid() {
        final Name name;

        name = new Name(SponsorConstants.FIRST_NAME, SponsorConstants.LAST_NAME);
        return new Sponsor(SponsorConstants.IDENTIFIER, SponsorConstants.NUMBER, name, SponsorConstants.BIRTH_DATE,
            List.of(), List.of(SponsorConstants.YEAR), SponsorConstants.ADDRESS, SponsorConstants.COMMENTS,
            Set.of(Sponsor.PROFILE_TYPE));
    }

    public static final Sponsor withEmail() {
        final Name                   name;
        final Sponsor.ContactChannel contactChannel;
        final Sponsor.ContactMethod  contactMethod;

        name = new Name(SponsorConstants.FIRST_NAME, SponsorConstants.LAST_NAME);
        contactMethod = email();
        contactChannel = new Sponsor.ContactChannel(contactMethod, SponsorConstants.EMAIL);
        return new Sponsor(SponsorConstants.IDENTIFIER, SponsorConstants.NUMBER, name, SponsorConstants.BIRTH_DATE,
            List.of(contactChannel), List.of(SponsorConstants.YEAR), SponsorConstants.ADDRESS,
            SponsorConstants.COMMENTS, Set.of(Sponsor.PROFILE_TYPE));
    }

    public static final Sponsor withoutType() {
        final Name name;

        name = new Name(SponsorConstants.FIRST_NAME, SponsorConstants.LAST_NAME);
        return new Sponsor(SponsorConstants.IDENTIFIER, SponsorConstants.NUMBER, name, SponsorConstants.BIRTH_DATE,
            List.of(), List.of(SponsorConstants.YEAR), SponsorConstants.ADDRESS, SponsorConstants.COMMENTS, Set.of());
    }

    private static final Sponsor.ContactMethod email() {
        return new Sponsor.ContactMethod(SponsorContactMethodConstants.NUMBER, SponsorContactMethodConstants.EMAIL);
    }

}
