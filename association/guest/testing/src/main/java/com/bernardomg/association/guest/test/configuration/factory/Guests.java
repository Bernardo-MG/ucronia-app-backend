
package com.bernardomg.association.guest.test.configuration.factory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.bernardomg.association.guest.domain.model.Guest;

public final class Guests {

    public static final Instant DATE = LocalDate.of(2025, Month.JANUARY, 1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Guest created() {
        final Guest.Name name;

        name = new Guest.Name(GuestConstants.FIRST_NAME, GuestConstants.LAST_NAME);
        return new Guest(GuestConstants.IDENTIFIER, 1L, name, GuestConstants.BIRTH_DATE, List.of(), List.of(),
            GuestConstants.ADDRESS, GuestConstants.COMMENTS, Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest firstNameChange() {
        final Guest.Name name;

        name = new Guest.Name(GuestConstants.CHANGED_FIRST_NAME, GuestConstants.LAST_NAME);
        return new Guest(GuestConstants.IDENTIFIER, GuestConstants.NUMBER, name, GuestConstants.BIRTH_DATE, List.of(),
            List.of(Guests.DATE), GuestConstants.ADDRESS, GuestConstants.COMMENTS, Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest forNumber(final long number) {
        final Guest.Name name;

        name = new Guest.Name("Name " + number, "Last name " + number);
        return new Guest(Objects.toString(number * 10), number * 10, name, GuestConstants.BIRTH_DATE, List.of(),
            List.of(Guests.DATE), GuestConstants.ADDRESS, GuestConstants.COMMENTS, Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest nameChangePatch() {
        final Guest.Name name;

        name = new Guest.Name(GuestConstants.CHANGED_FIRST_NAME, GuestConstants.LAST_NAME);
        return new Guest(GuestConstants.IDENTIFIER, GuestConstants.NUMBER, name, GuestConstants.BIRTH_DATE, List.of(),
            List.of(Guests.DATE), GuestConstants.ADDRESS, GuestConstants.COMMENTS, Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest noGames() {
        final Guest.Name name;

        name = new Guest.Name(GuestConstants.FIRST_NAME, GuestConstants.LAST_NAME);
        return new Guest(GuestConstants.IDENTIFIER, GuestConstants.NUMBER, name, GuestConstants.BIRTH_DATE, List.of(),
            List.of(), GuestConstants.ADDRESS, GuestConstants.COMMENTS, Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest padded() {
        final Guest.Name name;

        name = new Guest.Name(" " + GuestConstants.FIRST_NAME + " ", " " + GuestConstants.LAST_NAME + " ");
        return new Guest(GuestConstants.IDENTIFIER, GuestConstants.NUMBER, name, GuestConstants.BIRTH_DATE, List.of(),
            List.of(Guests.DATE), " " + GuestConstants.ADDRESS + " ", " " + GuestConstants.COMMENTS + " ",
            Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest withEmail() {
        final Guest.Name           name;
        final Guest.ContactChannel contactChannel;
        final Guest.ContactMethod  contactMethod;

        name = new Guest.Name(GuestConstants.FIRST_NAME, GuestConstants.LAST_NAME);
        contactMethod = new Guest.ContactMethod(GuestContactMethodConstants.NUMBER, GuestContactMethodConstants.EMAIL);
        contactChannel = new Guest.ContactChannel(contactMethod, GuestConstants.EMAIL);
        return new Guest(GuestConstants.IDENTIFIER, GuestConstants.NUMBER, name, GuestConstants.BIRTH_DATE,
            List.of(contactChannel), List.of(Guests.DATE), GuestConstants.ADDRESS, GuestConstants.COMMENTS,
            Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest withGames() {
        final Guest.Name name;

        name = new Guest.Name(GuestConstants.FIRST_NAME, GuestConstants.LAST_NAME);
        return new Guest(GuestConstants.IDENTIFIER, GuestConstants.NUMBER, name, GuestConstants.BIRTH_DATE, List.of(),
            List.of(Guests.DATE), GuestConstants.ADDRESS, GuestConstants.COMMENTS, Set.of(Guest.PROFILE_TYPE));
    }

    public static final Guest withoutType() {
        final Guest.Name name;

        name = new Guest.Name(GuestConstants.FIRST_NAME, GuestConstants.LAST_NAME);
        return new Guest(GuestConstants.IDENTIFIER, GuestConstants.NUMBER, name, GuestConstants.BIRTH_DATE, List.of(),
            List.of(Guests.DATE), GuestConstants.ADDRESS, GuestConstants.COMMENTS, Set.of());
    }

}
