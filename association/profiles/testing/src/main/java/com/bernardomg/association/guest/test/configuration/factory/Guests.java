
package com.bernardomg.association.guest.test.configuration.factory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.profile.domain.model.ContactChannel;
import com.bernardomg.association.profile.domain.model.Name;
import com.bernardomg.association.profile.test.configuration.factory.ContactChannels;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class Guests {

    public static final Instant DATE = LocalDate.of(2025, Month.JANUARY, 1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Guest created() {
        final Name name;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new Guest(Optional.of(ProfileConstants.IDENTIFIER), 1L, name, Optional.of(ProfileConstants.BIRTH_DATE),
            List.of(), List.of(), Optional.of(ProfileConstants.ADDRESS), Optional.of(ProfileConstants.COMMENTS),
            Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest firstNameChange() {
        final Name name;

        name = new Name(ProfileConstants.CHANGED_FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new Guest(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(), List.of(Guests.DATE),
            Optional.of(ProfileConstants.ADDRESS), Optional.of(ProfileConstants.COMMENTS), Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest forNumber(final long number) {
        final Name name;

        name = new Name("Name " + number, "Last name " + number, Optional.empty());
        return new Guest(Optional.of(Objects.toString(number * 10)), number * 10, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(), List.of(Guests.DATE),
            Optional.of(ProfileConstants.ADDRESS), Optional.of(ProfileConstants.COMMENTS), Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest nameChangePatch() {
        final Name name;

        name = new Name(ProfileConstants.CHANGED_FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new Guest(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(), List.of(Guests.DATE),
            Optional.of(ProfileConstants.ADDRESS), Optional.of(ProfileConstants.COMMENTS), Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest noGames() {
        final Name name;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new Guest(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(), List.of(), Optional.of(ProfileConstants.ADDRESS),
            Optional.of(ProfileConstants.COMMENTS), Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest padded() {
        final Name name;

        name = new Name(" " + ProfileConstants.FIRST_NAME + " ", " " + ProfileConstants.LAST_NAME + " ",
            Optional.empty());
        return new Guest(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(), List.of(Guests.DATE),
            Optional.of(" " + ProfileConstants.ADDRESS + " "), Optional.of(" " + ProfileConstants.COMMENTS + " "),
            Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest withEmail() {
        final Name           name;
        final ContactChannel contactChannel;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        contactChannel = ContactChannels.withEmail();
        return new Guest(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(contactChannel), List.of(Guests.DATE),
            Optional.of(ProfileConstants.ADDRESS), Optional.of(ProfileConstants.COMMENTS), Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest withGames() {
        final Name name;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new Guest(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(), List.of(Guests.DATE),
            Optional.of(ProfileConstants.ADDRESS), Optional.of(ProfileConstants.COMMENTS), Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest withoutType() {
        final Name name;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new Guest(Optional.of(ProfileConstants.IDENTIFIER), ProfileConstants.NUMBER, name,
            Optional.of(ProfileConstants.BIRTH_DATE), List.of(), List.of(Guests.DATE),
            Optional.of(ProfileConstants.ADDRESS), Optional.of(ProfileConstants.COMMENTS), Set.of());
    }

}
