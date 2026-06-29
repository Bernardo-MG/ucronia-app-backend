
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
import com.bernardomg.association.guest.domain.model.Guest.ContactChannel;
import com.bernardomg.association.guest.domain.model.Guest.ContactMethod;
import com.bernardomg.association.guest.domain.model.Guest.Name;

public final class Guests {

    public static final Instant DATE = LocalDate.of(2025, Month.JANUARY, 1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Guest created() {
        final Name name;

        name = new Name(GuestConstants.FIRST_NAME, GuestConstants.LAST_NAME);
        return new Guest(Optional.of(GuestConstants.IDENTIFIER), 1L, name, Optional.of(GuestConstants.BIRTH_DATE),
            List.of(), List.of(), Optional.of(GuestConstants.ADDRESS), Optional.of(GuestConstants.COMMENTS),
            Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest firstNameChange() {
        final Name name;

        name = new Name(GuestConstants.CHANGED_FIRST_NAME, GuestConstants.LAST_NAME);
        return new Guest(Optional.of(GuestConstants.IDENTIFIER), GuestConstants.NUMBER, name,
            Optional.of(GuestConstants.BIRTH_DATE), List.of(), List.of(Guests.DATE),
            Optional.of(GuestConstants.ADDRESS), Optional.of(GuestConstants.COMMENTS), Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest forNumber(final long number) {
        final Name name;

        name = new Name("Name " + number, "Last name " + number);
        return new Guest(Optional.of(Objects.toString(number * 10)), number * 10, name,
            Optional.of(GuestConstants.BIRTH_DATE), List.of(), List.of(Guests.DATE),
            Optional.of(GuestConstants.ADDRESS), Optional.of(GuestConstants.COMMENTS), Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest nameChangePatch() {
        final Name name;

        name = new Name(GuestConstants.CHANGED_FIRST_NAME, GuestConstants.LAST_NAME);
        return new Guest(Optional.of(GuestConstants.IDENTIFIER), GuestConstants.NUMBER, name,
            Optional.of(GuestConstants.BIRTH_DATE), List.of(), List.of(Guests.DATE),
            Optional.of(GuestConstants.ADDRESS), Optional.of(GuestConstants.COMMENTS), Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest noGames() {
        final Name name;

        name = new Name(GuestConstants.FIRST_NAME, GuestConstants.LAST_NAME);
        return new Guest(Optional.of(GuestConstants.IDENTIFIER), GuestConstants.NUMBER, name,
            Optional.of(GuestConstants.BIRTH_DATE), List.of(), List.of(), Optional.of(GuestConstants.ADDRESS),
            Optional.of(GuestConstants.COMMENTS), Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest noType() {
        final Name name;

        name = new Name(GuestConstants.FIRST_NAME, GuestConstants.LAST_NAME);
        return new Guest(Optional.of(GuestConstants.IDENTIFIER), GuestConstants.NUMBER, name,
            Optional.of(GuestConstants.BIRTH_DATE), List.of(), List.of(), Optional.of(GuestConstants.ADDRESS),
            Optional.of(GuestConstants.COMMENTS), Set.of());
    }

    public static final Guest padded() {
        final Name name;

        name = new Name(" " + GuestConstants.FIRST_NAME + " ", " " + GuestConstants.LAST_NAME + " ");
        return new Guest(Optional.of(GuestConstants.IDENTIFIER), GuestConstants.NUMBER, name,
            Optional.of(GuestConstants.BIRTH_DATE), List.of(), List.of(Guests.DATE),
            Optional.of(" " + GuestConstants.ADDRESS + " "), Optional.of(" " + GuestConstants.COMMENTS + " "),
            Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest withEmail() {
        final Name           name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new Name(GuestConstants.FIRST_NAME, GuestConstants.LAST_NAME);
        contactMethod = new ContactMethod(GuestContactMethodConstants.NUMBER, GuestContactMethodConstants.EMAIL);
        contactChannel = new ContactChannel(contactMethod, GuestConstants.EMAIL);
        return new Guest(Optional.of(GuestConstants.IDENTIFIER), GuestConstants.NUMBER, name,
            Optional.of(GuestConstants.BIRTH_DATE), List.of(contactChannel), List.of(Guests.DATE),
            Optional.of(GuestConstants.ADDRESS), Optional.of(GuestConstants.COMMENTS), Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest withEmailNoGames() {
        final Name           name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new Name(GuestConstants.FIRST_NAME, GuestConstants.LAST_NAME);
        contactMethod = new ContactMethod(GuestContactMethodConstants.NUMBER, GuestContactMethodConstants.EMAIL);
        contactChannel = new ContactChannel(contactMethod, GuestConstants.EMAIL);
        return new Guest(Optional.of(GuestConstants.IDENTIFIER), GuestConstants.NUMBER, name,
            Optional.of(GuestConstants.BIRTH_DATE), List.of(contactChannel), List.of(),
            Optional.of(GuestConstants.ADDRESS), Optional.of(GuestConstants.COMMENTS), Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest withGames() {
        final Name name;

        name = new Name(GuestConstants.FIRST_NAME, GuestConstants.LAST_NAME);
        return new Guest(Optional.of(GuestConstants.IDENTIFIER), GuestConstants.NUMBER, name,
            Optional.of(GuestConstants.BIRTH_DATE), List.of(), List.of(Guests.DATE),
            Optional.of(GuestConstants.ADDRESS), Optional.of(GuestConstants.COMMENTS), Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest withoutType() {
        final Name name;

        name = new Name(GuestConstants.FIRST_NAME, GuestConstants.LAST_NAME);
        return new Guest(Optional.of(GuestConstants.IDENTIFIER), GuestConstants.NUMBER, name,
            Optional.of(GuestConstants.BIRTH_DATE), List.of(), List.of(Guests.DATE),
            Optional.of(GuestConstants.ADDRESS), Optional.of(GuestConstants.COMMENTS), Set.of());
    }

}
