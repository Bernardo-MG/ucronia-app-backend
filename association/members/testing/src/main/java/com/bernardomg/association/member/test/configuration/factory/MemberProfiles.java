
package com.bernardomg.association.member.test.configuration.factory;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.model.MemberProfile.ContactChannel;
import com.bernardomg.association.member.domain.model.MemberProfile.ContactMethod;
import com.bernardomg.association.member.domain.model.MemberProfile.Name;

public final class MemberProfiles {

    public static final MemberProfile active() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new MemberProfile(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType,
            new HashSet<>(List.of(MemberProfile.PROFILE_TYPE)));
    }

    public static final MemberProfile activeNoRenew() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new MemberProfile(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, false, feeType,
            new HashSet<>(List.of(MemberProfile.PROFILE_TYPE)));
    }

    public static final MemberProfile alternativeActive() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.ALTERNATIVE_FIRST_NAME, MemberConstants.ALTERNATIVE_LAST_NAME);
        return new MemberProfile(MemberConstants.ALTERNATIVE_IDENTIFIER, MemberConstants.ALTERNATIVE_NUMBER, name,
            MemberConstants.BIRTH_DATE, List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true,
            feeType, new HashSet<>(List.of(MemberProfile.PROFILE_TYPE)));
    }

    public static final MemberProfile alternativeInactive() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.ALTERNATIVE_FIRST_NAME, MemberConstants.ALTERNATIVE_LAST_NAME);
        return new MemberProfile(MemberConstants.ALTERNATIVE_IDENTIFIER, MemberConstants.ALTERNATIVE_NUMBER, name,
            MemberConstants.BIRTH_DATE, List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, false, true,
            feeType, new HashSet<>(List.of(MemberProfile.PROFILE_TYPE)));
    }

    public static final MemberProfile created() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new MemberProfile(MemberConstants.IDENTIFIER, 1L, name, MemberConstants.BIRTH_DATE, List.of(),
            MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType,
            new HashSet<>(List.of(MemberProfile.PROFILE_TYPE)));
    }

    public static final MemberProfile firstNameChange() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.CHANGED_FIRST_NAME, MemberConstants.LAST_NAME);
        return new MemberProfile(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType,
            new HashSet<>(List.of(MemberProfile.PROFILE_TYPE)));
    }

    public static final MemberProfile forNumber(final long number) {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name("Name " + number, "Last name " + number);
        return new MemberProfile(Objects.toString(number * 10), number * 10, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType,
            new HashSet<>(List.of(MemberProfile.PROFILE_TYPE)));
    }

    public static final MemberProfile inactive() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new MemberProfile(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, false, true, feeType,
            new HashSet<>(List.of(MemberProfile.PROFILE_TYPE)));
    }

    public static final MemberProfile inactiveNoRenew() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new MemberProfile(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, false, false, feeType,
            new HashSet<>(List.of(MemberProfile.PROFILE_TYPE)));
    }

    public static final MemberProfile nameChange() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name("Name 123", "Last name");
        return new MemberProfile(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType,
            new HashSet<>(List.of(MemberProfile.PROFILE_TYPE)));
    }

    public static final MemberProfile nameChangePatch() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name("Name 123", "Last name");
        return new MemberProfile(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType,
            new HashSet<>(List.of(MemberProfile.PROFILE_TYPE)));
    }

    public static final MemberProfile noContactChannel() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new MemberProfile(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType,
            new HashSet<>(List.of(MemberProfile.PROFILE_TYPE)));
    }

    public static final MemberProfile noGames() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new MemberProfile(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType,
            new HashSet<>(List.of(MemberProfile.PROFILE_TYPE)));
    }

    public static final MemberProfile padded() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(" " + MemberConstants.FIRST_NAME + " ", " " + MemberConstants.LAST_NAME + " ");
        return new MemberProfile(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType,
            new HashSet<>(List.of(MemberProfile.PROFILE_TYPE)));
    }

    public static final MemberProfile toCreate() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, "", 0f);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new MemberProfile(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType, new HashSet<>());
    }

    public static final MemberProfile toCreateWithEmail() {
        final Name                  name;
        final MemberProfile.FeeType feeType;
        final ContactChannel        contactChannel;
        final ContactMethod         contactMethod;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, "", 0f);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        contactMethod = email();
        contactChannel = new ContactChannel(contactMethod, MemberConstants.EMAIL);
        return new MemberProfile(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(contactChannel), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType,
            new HashSet<>());
    }

    public static final MemberProfile withEmail() {
        final Name                  name;
        final ContactChannel        contactChannel;
        final ContactMethod         contactMethod;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        contactMethod = email();
        contactChannel = new ContactChannel(contactMethod, MemberConstants.EMAIL);
        return new MemberProfile(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(contactChannel), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType,
            new HashSet<>(List.of(MemberProfile.PROFILE_TYPE)));
    }

    public static final MemberProfile withoutType() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new MemberProfile(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType, new HashSet<>());
    }

    private static final ContactMethod email() {
        return new ContactMethod(ContactMethodConstants.NUMBER, ContactMethodConstants.EMAIL);
    }

}
