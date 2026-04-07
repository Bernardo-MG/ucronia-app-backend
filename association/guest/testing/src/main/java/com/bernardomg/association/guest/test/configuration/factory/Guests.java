
package com.bernardomg.association.guest.test.configuration.factory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.guest.domain.model.GuestConstants;
import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.model.Profile.ContactChannel;
import com.bernardomg.association.profile.domain.model.ProfileName;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethods;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class Guests {

    public static final Instant DATE = LocalDate.of(2025, Month.JANUARY, 1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Guest created() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Guest(ProfileConstants.IDENTIFIER, 1L, name, ProfileConstants.BIRTH_DATE, List.of(), List.of(),
            ProfileConstants.ADDRESS, ProfileConstants.COMMENTS, Set.of(GuestConstants.PROFILE_TYPE));
    }

    public static final Guest firstNameChange() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.CHANGED_FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Guest(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(), List.of(Guests.DATE), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS,
            Set.of(GuestConstants.PROFILE_TYPE));
    }

    public static final Guest forNumber(final long number) {
        final ProfileName name;

        name = new ProfileName("Profile " + number, "Last name " + number);
        return new Guest(Objects.toString(number * 10), number * 10, name, ProfileConstants.BIRTH_DATE, List.of(),
            List.of(Guests.DATE), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS,
            Set.of(GuestConstants.PROFILE_TYPE));
    }

    public static final Guest nameChangePatch() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.CHANGED_FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Guest(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(), List.of(Guests.DATE), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS,
            Set.of(GuestConstants.PROFILE_TYPE));
    }

    public static final Guest noGames() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Guest(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(), List.of(), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS,
            Set.of(GuestConstants.PROFILE_TYPE));
    }

    public static final Guest padded() {
        final ProfileName name;

        name = new ProfileName(" " + ProfileConstants.FIRST_NAME + " ", " " + ProfileConstants.LAST_NAME + " ");
        return new Guest(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(), List.of(Guests.DATE), " " + ProfileConstants.ADDRESS + " ",
            " " + ProfileConstants.COMMENTS + " ", Set.of(GuestConstants.PROFILE_TYPE));
    }

    public static final Guest withEmail() {
        final ProfileName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new Guest(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(contactChannel), List.of(Guests.DATE), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS,
            Set.of(GuestConstants.PROFILE_TYPE));
    }

    public static final Guest withGames() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Guest(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(), List.of(Guests.DATE), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS,
            Set.of(GuestConstants.PROFILE_TYPE));
    }

    public static final Guest withoutType() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Guest(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name, ProfileConstants.BIRTH_DATE,
            List.of(), List.of(Guests.DATE), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS, Set.of());
    }

}
