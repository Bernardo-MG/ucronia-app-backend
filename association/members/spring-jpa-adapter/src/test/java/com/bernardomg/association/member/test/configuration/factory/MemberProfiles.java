
package com.bernardomg.association.member.test.configuration.factory;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.model.MemberProfile.ContactChannel;
import com.bernardomg.association.member.domain.model.MemberProfile.ContactMethod;
import com.bernardomg.association.member.domain.model.MemberProfile.Name;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethodConstants;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class MemberProfiles {

    public static final MemberProfile active() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS, true, true,
            feeType, Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile activeNoRenew() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS, true, false,
            feeType, Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile alternativeActive() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.ALTERNATIVE_FIRST_NAME, ProfileConstants.ALTERNATIVE_LAST_NAME);
        return new MemberProfile(ProfileConstants.ALTERNATIVE_IDENTIFIER, ProfileConstants.ALTERNATIVE_NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS, true, true,
            feeType, Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile alternativeInactive() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.ALTERNATIVE_FIRST_NAME, ProfileConstants.ALTERNATIVE_LAST_NAME);
        return new MemberProfile(ProfileConstants.ALTERNATIVE_IDENTIFIER, ProfileConstants.ALTERNATIVE_NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS, false, true,
            feeType, Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile created() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new MemberProfile(ProfileConstants.IDENTIFIER, 1L, name, ProfileConstants.BIRTH_DATE, List.of(),
            ProfileConstants.ADDRESS, ProfileConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile firstNameChange() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.CHANGED_FIRST_NAME, ProfileConstants.LAST_NAME);
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS, true, true,
            feeType, Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile forNumber(final long number) {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name("Name " + number, "Last name " + number);
        return new MemberProfile(Objects.toString(number * 10), number * 10, name, ProfileConstants.BIRTH_DATE,
            List.of(), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile inactive() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS, false, true,
            feeType, Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile inactiveNoRenew() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS, false, false,
            feeType, Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile nameChange() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name("Name 123", "Last name");
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS, true, true,
            feeType, Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile nameChangePatch() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name("Name 123", "Last name");
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS, true, true,
            feeType, Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile noContactChannel() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS, true, true,
            feeType, Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile noGames() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS, true, true,
            feeType, Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile padded() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(" " + ProfileConstants.FIRST_NAME + " ", " " + ProfileConstants.LAST_NAME + " ");
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS, true, true,
            feeType, Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile toCreate() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, "", 0f);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS, true, true,
            feeType, Set.of());
    }

    public static final MemberProfile toCreateWithEmail() {
        final Name                  name;
        final MemberProfile.FeeType feeType;
        final ContactChannel        contactChannel;
        final ContactMethod         contactMethod;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, "", 0f);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactMethod = email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(contactChannel), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS,
            true, true, feeType, Set.of());
    }

    public static final MemberProfile withEmail() {
        final Name                  name;
        final ContactChannel        contactChannel;
        final ContactMethod         contactMethod;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactMethod = email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(contactChannel), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS,
            true, true, feeType, Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile withoutType() {
        final Name                  name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(), ProfileConstants.ADDRESS, ProfileConstants.COMMENTS, true, true,
            feeType, Set.of());
    }

    private static final ContactMethod email() {
        return new ContactMethod(ContactMethodConstants.NUMBER, ContactMethodConstants.EMAIL);
    }

}
