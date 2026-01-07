
package com.bernardomg.association.member.test.configuration.factory;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.model.Profile.ContactChannel;
import com.bernardomg.association.profile.domain.model.ProfileName;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethods;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class MemberProfiles {

    public static final MemberProfile active() {
        final ProfileName           name;
        final ContactChannel        contactChannel;
        final ContactMethod         contactMethod;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER);

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(contactChannel), ProfileConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile activeNoRenew() {
        final ProfileName           name;
        final ContactChannel        contactChannel;
        final ContactMethod         contactMethod;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER);

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(contactChannel), ProfileConstants.COMMENTS, true, false, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile alternativeActive() {
        final ProfileName           name;
        final ContactChannel        contactChannel;
        final ContactMethod         contactMethod;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER);

        name = new ProfileName(ProfileConstants.ALTERNATIVE_FIRST_NAME, ProfileConstants.ALTERNATIVE_LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.ALTERNATIVE_EMAIL);
        return new MemberProfile(ProfileConstants.ALTERNATIVE_IDENTIFIER, ProfileConstants.ALTERNATIVE_NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(contactChannel), ProfileConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile alternativeInactive() {
        final ProfileName           name;
        final ContactChannel        contactChannel;
        final ContactMethod         contactMethod;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER);

        name = new ProfileName(ProfileConstants.ALTERNATIVE_FIRST_NAME, ProfileConstants.ALTERNATIVE_LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.ALTERNATIVE_EMAIL);
        return new MemberProfile(ProfileConstants.ALTERNATIVE_IDENTIFIER, ProfileConstants.ALTERNATIVE_NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(contactChannel), ProfileConstants.COMMENTS, false, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile created() {
        final ProfileName           name;
        final ContactChannel        contactChannel;
        final ContactMethod         contactMethod;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER);

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new MemberProfile(ProfileConstants.IDENTIFIER, 1L, name, ProfileConstants.BIRTH_DATE,
            List.of(contactChannel), ProfileConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile forNumber(final long number) {
        final ProfileName           name;
        final ContactChannel        contactChannel;
        final ContactMethod         contactMethod;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER);

        name = new ProfileName("Profile " + number, "Last name " + number);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new MemberProfile(Objects.toString(number * 10), number * 10, name, ProfileConstants.BIRTH_DATE,
            List.of(contactChannel), ProfileConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile inactive() {
        final ProfileName           name;
        final ContactChannel        contactChannel;
        final ContactMethod         contactMethod;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER);

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(contactChannel), ProfileConstants.COMMENTS, false, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile inactiveNoRenew() {
        final ProfileName           name;
        final ContactChannel        contactChannel;
        final ContactMethod         contactMethod;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER);

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(contactChannel), ProfileConstants.COMMENTS, false, false, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile nameChange() {
        final ProfileName           name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER);

        name = new ProfileName("Profile 123", "Last name");
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(), ProfileConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile nameChangePatch() {
        final ProfileName           name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER);

        name = new ProfileName("Profile 123", "Last name");
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(), ProfileConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile noContactChannel() {
        final ProfileName           name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER);

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(), ProfileConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile noGames() {
        final ProfileName           name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER);

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(), ProfileConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile padded() {
        final ProfileName           name;
        final ContactChannel        contactChannel;
        final ContactMethod         contactMethod;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER);

        name = new ProfileName(" " + ProfileConstants.FIRST_NAME + " ", " " + ProfileConstants.LAST_NAME + " ");
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(contactChannel), ProfileConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile toCreate() {
        final ProfileName           name;
        final ContactChannel        contactChannel;
        final ContactMethod         contactMethod;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER);

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new MemberProfile(ProfileConstants.IDENTIFIER, 0L, name, ProfileConstants.BIRTH_DATE,
            List.of(contactChannel), ProfileConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final MemberProfile withoutType() {
        final ProfileName           name;
        final ContactChannel        contactChannel;
        final ContactMethod         contactMethod;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(FeeConstants.FEE_TYPE_NUMBER);

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ProfileConstants.EMAIL);
        return new MemberProfile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name,
            ProfileConstants.BIRTH_DATE, List.of(contactChannel), ProfileConstants.COMMENTS, true, true, feeType,
            Set.of());
    }

}
