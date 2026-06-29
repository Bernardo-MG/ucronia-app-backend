
package com.bernardomg.association.sponsor.test.configuration.factory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.bernardomg.association.profile.domain.model.ContactChannel;
import com.bernardomg.association.profile.domain.model.Name;
import com.bernardomg.association.profile.test.configuration.factory.ContactChannels;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.sponsor.domain.model.Sponsor;

public final class Sponsors {

    public static final Sponsor created() {
        final Name name;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Sponsor(Optional.of(ProfileConstants.IDENTIFIER), 1L, name, Optional.of(ProfileConstants.BIRTH_DATE),
            List.of(), List.of(SponsorConstants.YEAR), Optional.of(ProfileConstants.ADDRESS),
            Optional.of(ProfileConstants.COMMENTS), Set.of(Sponsor.PROFILE_TYPE));
    }

    public static final Sponsor firstNameChange() {
        final Name name;

        name = new Name(ProfileConstants.CHANGED_FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Sponsor(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(), List.of(SponsorConstants.YEAR),
            Optional.of(ProfileConstants.ADDRESS), Optional.of(ProfileConstants.COMMENTS),
            Set.of(Sponsor.PROFILE_TYPE));
    }

    public static final Sponsor forNumber(final long number) {
        final Name name;

        name = new Name("Name " + number, "Last name " + number);
        return new Sponsor(Optional.ofNullable(Objects.toString(number * 10)), number * 10, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(), List.of(SponsorConstants.YEAR),
            Optional.of(ProfileConstants.ADDRESS), Optional.of(ProfileConstants.COMMENTS),
            Set.of(Sponsor.PROFILE_TYPE));
    }

    public static final Sponsor nameChange() {
        final Name name;

        name = new Name("Name 123", "Last name");
        return new Sponsor(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(), List.of(SponsorConstants.YEAR),
            Optional.of(ProfileConstants.ADDRESS), Optional.of(ProfileConstants.COMMENTS),
            Set.of(Sponsor.PROFILE_TYPE));
    }

    public static final Sponsor nameChangePatch() {
        final Name name;

        name = new Name("Name 123", "Last name");
        return new Sponsor(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(), List.of(SponsorConstants.YEAR),
            Optional.of(ProfileConstants.ADDRESS), Optional.of(ProfileConstants.COMMENTS),
            Set.of(Sponsor.PROFILE_TYPE));
    }

    public static final Sponsor padded() {
        final Name name;

        name = new Name(" " + ProfileConstants.FIRST_NAME + " ", " " + ProfileConstants.LAST_NAME + " ");
        return new Sponsor(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(), List.of(SponsorConstants.YEAR),
            Optional.of(ProfileConstants.ADDRESS), Optional.of(ProfileConstants.COMMENTS),
            Set.of(Sponsor.PROFILE_TYPE));
    }

    public static final Sponsor valid() {
        final Name name;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Sponsor(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(), List.of(SponsorConstants.YEAR),
            Optional.of(ProfileConstants.ADDRESS), Optional.of(ProfileConstants.COMMENTS),
            Set.of(Sponsor.PROFILE_TYPE));
    }

    public static final Sponsor withEmail() {
        final Name           name;
        final ContactChannel contactChannel;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactChannel = ContactChannels.withEmail();
        return new Sponsor(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(contactChannel), List.of(SponsorConstants.YEAR),
            Optional.of(ProfileConstants.ADDRESS), Optional.of(ProfileConstants.COMMENTS),
            Set.of(Sponsor.PROFILE_TYPE));
    }

    public static final Sponsor withoutType() {
        final Name name;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Sponsor(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(), List.of(SponsorConstants.YEAR),
            Optional.of(ProfileConstants.ADDRESS), Optional.of(ProfileConstants.COMMENTS), Set.of());
    }

    public static final Sponsor withoutYear() {
        final Name name;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Sponsor(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(), List.of(), Optional.of(ProfileConstants.ADDRESS),
            Optional.of(ProfileConstants.COMMENTS), Set.of(Sponsor.PROFILE_TYPE));
    }

    private Sponsors() {
        super();
    }

}
