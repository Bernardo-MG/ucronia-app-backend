
package com.bernardomg.association.member.test.configuration.factory;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.Member.FeeType;
import com.bernardomg.association.profile.domain.model.ContactChannel;
import com.bernardomg.association.profile.domain.model.Name;
import com.bernardomg.association.profile.test.configuration.factory.ContactChannels;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class Members {

    public static final Member active() {
        final Name    name;
        final FeeType feeType;

        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new Member(Optional.of(MemberConstants.IDENTIFIER), MemberConstants.NUMBER, name,
            Optional.of(MemberConstants.BIRTH_DATE), List.of(), Optional.of(MemberConstants.ADDRESS),
            Optional.of(MemberConstants.COMMENTS), true, true, feeType, new HashSet<>(List.of(Member.PROFILE_TYPE)));
    }

    public static final Member activeNoRenew() {
        final Name    name;
        final FeeType feeType;

        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new Member(Optional.of(MemberConstants.IDENTIFIER), MemberConstants.NUMBER, name,
            Optional.of(MemberConstants.BIRTH_DATE), List.of(), Optional.of(MemberConstants.ADDRESS),
            Optional.of(MemberConstants.COMMENTS), true, false, feeType, new HashSet<>(List.of(Member.PROFILE_TYPE)));
    }

    public static final Member alternativeActive() {
        final Name    name;
        final FeeType feeType;

        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.ALTERNATIVE_FIRST_NAME, ProfileConstants.ALTERNATIVE_LAST_NAME,
            Optional.empty());
        return new Member(Optional.of(MemberConstants.ALTERNATIVE_IDENTIFIER), MemberConstants.ALTERNATIVE_NUMBER, name,
            Optional.of(MemberConstants.BIRTH_DATE), List.of(), Optional.of(MemberConstants.ADDRESS),
            Optional.of(MemberConstants.COMMENTS), true, true, feeType, new HashSet<>(List.of(Member.PROFILE_TYPE)));
    }

    public static final Member alternativeInactive() {
        final Name    name;
        final FeeType feeType;

        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.ALTERNATIVE_FIRST_NAME, ProfileConstants.ALTERNATIVE_LAST_NAME,
            Optional.empty());
        return new Member(Optional.of(MemberConstants.ALTERNATIVE_IDENTIFIER), MemberConstants.ALTERNATIVE_NUMBER, name,
            Optional.of(MemberConstants.BIRTH_DATE), List.of(), Optional.of(MemberConstants.ADDRESS),
            Optional.of(MemberConstants.COMMENTS), false, true, feeType, new HashSet<>(List.of(Member.PROFILE_TYPE)));
    }

    public static final Member alternativeWithKey() {
        final Name    name;
        final FeeType feeType;

        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.ALTERNATIVE_FIRST_NAME, ProfileConstants.ALTERNATIVE_LAST_NAME,
            Optional.empty());
        return new Member(Optional.of(MemberConstants.ALTERNATIVE_IDENTIFIER), MemberConstants.ALTERNATIVE_NUMBER, name,
            Optional.of(MemberConstants.BIRTH_DATE), List.of(), Optional.of(MemberConstants.ADDRESS),
            Optional.of(MemberConstants.COMMENTS), true, true, Optional.ofNullable(KeyConstants.NUMBER), feeType,
            new HashSet<>(List.of(Member.PROFILE_TYPE)));
    }

    public static final Member created() {
        final Name    name;
        final FeeType feeType;

        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new Member(Optional.of(MemberConstants.IDENTIFIER), 1L, name, Optional.of(MemberConstants.BIRTH_DATE),
            List.of(), Optional.of(MemberConstants.ADDRESS), Optional.of(MemberConstants.COMMENTS), true, true, feeType,
            new HashSet<>(List.of(Member.PROFILE_TYPE)));
    }

    public static final Member emptyFeeType() {
        final Name    name;
        final FeeType feeType;

        feeType = new FeeType(0L, "", 0F);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new Member(Optional.of(MemberConstants.IDENTIFIER), MemberConstants.NUMBER, name,
            Optional.of(MemberConstants.BIRTH_DATE), List.of(), Optional.of(MemberConstants.ADDRESS),
            Optional.of(MemberConstants.COMMENTS), true, true, feeType, new HashSet<>(List.of(Member.PROFILE_TYPE)));
    }

    public static final Member firstNameChange() {
        final Name    name;
        final FeeType feeType;

        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.CHANGED_FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new Member(Optional.of(MemberConstants.IDENTIFIER), MemberConstants.NUMBER, name,
            Optional.of(MemberConstants.BIRTH_DATE), List.of(), Optional.of(MemberConstants.ADDRESS),
            Optional.of(MemberConstants.COMMENTS), true, true, feeType, new HashSet<>(List.of(Member.PROFILE_TYPE)));
    }

    public static final Member forNumber(final long number) {
        final Name    name;
        final FeeType feeType;

        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name("Name " + number, "Last name " + number, Optional.empty());
        return new Member(Optional.of(Objects.toString(number * 10)), number * 10, name,
            Optional.of(MemberConstants.BIRTH_DATE), List.of(), Optional.of(MemberConstants.ADDRESS),
            Optional.of(MemberConstants.COMMENTS), true, true, feeType, new HashSet<>(List.of(Member.PROFILE_TYPE)));
    }

    public static final Member inactive() {
        final Name    name;
        final FeeType feeType;

        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new Member(Optional.of(MemberConstants.IDENTIFIER), MemberConstants.NUMBER, name,
            Optional.of(MemberConstants.BIRTH_DATE), List.of(), Optional.of(MemberConstants.ADDRESS),
            Optional.of(MemberConstants.COMMENTS), false, true, feeType, new HashSet<>(List.of(Member.PROFILE_TYPE)));
    }

    public static final Member inactiveNoRenew() {
        final Name    name;
        final FeeType feeType;

        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new Member(Optional.of(MemberConstants.IDENTIFIER), MemberConstants.NUMBER, name,
            Optional.of(MemberConstants.BIRTH_DATE), List.of(), Optional.of(MemberConstants.ADDRESS),
            Optional.of(MemberConstants.COMMENTS), false, false, feeType, new HashSet<>(List.of(Member.PROFILE_TYPE)));
    }

    public static final Member nameChange() {
        final Name    name;
        final FeeType feeType;

        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name("Name 123", "Last name", Optional.empty());
        return new Member(Optional.of(MemberConstants.IDENTIFIER), MemberConstants.NUMBER, name,
            Optional.of(MemberConstants.BIRTH_DATE), List.of(), Optional.of(MemberConstants.ADDRESS),
            Optional.of(MemberConstants.COMMENTS), true, true, feeType, new HashSet<>(List.of(Member.PROFILE_TYPE)));
    }

    public static final Member nameChangePatch() {
        final Name    name;
        final FeeType feeType;

        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name("Name 123", "Last name", Optional.empty());
        return new Member(Optional.of(MemberConstants.IDENTIFIER), MemberConstants.NUMBER, name,
            Optional.of(MemberConstants.BIRTH_DATE), List.of(), Optional.of(MemberConstants.ADDRESS),
            Optional.of(MemberConstants.COMMENTS), true, true, feeType, new HashSet<>(List.of(Member.PROFILE_TYPE)));
    }

    public static final Member noContactChannel() {
        final Name    name;
        final FeeType feeType;

        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new Member(Optional.of(MemberConstants.IDENTIFIER), MemberConstants.NUMBER, name,
            Optional.of(MemberConstants.BIRTH_DATE), List.of(), Optional.of(MemberConstants.ADDRESS),
            Optional.of(MemberConstants.COMMENTS), true, true, feeType, new HashSet<>(List.of(Member.PROFILE_TYPE)));
    }

    public static final Member noGames() {
        final Name    name;
        final FeeType feeType;

        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new Member(Optional.of(MemberConstants.IDENTIFIER), MemberConstants.NUMBER, name,
            Optional.of(MemberConstants.BIRTH_DATE), List.of(), Optional.of(MemberConstants.ADDRESS),
            Optional.of(MemberConstants.COMMENTS), true, true, feeType, new HashSet<>(List.of(Member.PROFILE_TYPE)));
    }

    public static final Member padded() {
        final Name    name;
        final FeeType feeType;

        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(" " + ProfileConstants.FIRST_NAME + " ", " " + ProfileConstants.LAST_NAME + " ",
            Optional.empty());
        return new Member(Optional.of(MemberConstants.IDENTIFIER), MemberConstants.NUMBER, name,
            Optional.of(MemberConstants.BIRTH_DATE), List.of(), Optional.of(MemberConstants.ADDRESS),
            Optional.of(MemberConstants.COMMENTS), true, true, feeType, new HashSet<>(List.of(Member.PROFILE_TYPE)));
    }

    public static final Member toCreate() {
        final Name    name;
        final FeeType feeType;

        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, "", 0f);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new Member(Optional.of(MemberConstants.IDENTIFIER), MemberConstants.NUMBER, name,
            Optional.of(MemberConstants.BIRTH_DATE), List.of(), Optional.of(MemberConstants.ADDRESS),
            Optional.of(MemberConstants.COMMENTS), true, true, feeType, new HashSet<>(List.of(Member.PROFILE_TYPE)));
    }

    public static final Member toCreateWithEmail() {
        final Name           name;
        final FeeType        feeType;
        final ContactChannel contactChannel;

        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, "", 0f);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        contactChannel = ContactChannels.withEmail();
        return new Member(Optional.of(MemberConstants.IDENTIFIER), MemberConstants.NUMBER, name,
            Optional.of(MemberConstants.BIRTH_DATE), List.of(contactChannel), Optional.of(MemberConstants.ADDRESS),
            Optional.of(MemberConstants.COMMENTS), true, true, feeType, new HashSet<>());
    }

    public static final Member withEmail() {
        final Name           name;
        final ContactChannel contactChannel;
        final FeeType        feeType;

        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        contactChannel = ContactChannels.withEmail();
        return new Member(Optional.of(MemberConstants.IDENTIFIER), MemberConstants.NUMBER, name,
            Optional.of(MemberConstants.BIRTH_DATE), List.of(contactChannel), Optional.of(MemberConstants.ADDRESS),
            Optional.of(MemberConstants.COMMENTS), true, true, feeType, new HashSet<>(List.of(Member.PROFILE_TYPE)));
    }

    public static final Member withEmailAndEmptyFeeType() {
        final Name           name;
        final ContactChannel contactChannel;
        final FeeType        feeType;

        feeType = new FeeType(0L, "", 0F);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        contactChannel = ContactChannels.withEmail();
        return new Member(Optional.of(MemberConstants.IDENTIFIER), MemberConstants.NUMBER, name,
            Optional.of(MemberConstants.BIRTH_DATE), List.of(contactChannel), Optional.of(MemberConstants.ADDRESS),
            Optional.of(MemberConstants.COMMENTS), true, true, feeType, new HashSet<>(List.of(Member.PROFILE_TYPE)));
    }

    public static final Member withKey() {
        final Name    name;
        final FeeType feeType;

        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new Member(Optional.of(MemberConstants.IDENTIFIER), MemberConstants.NUMBER, name,
            Optional.of(MemberConstants.BIRTH_DATE), List.of(), Optional.of(MemberConstants.ADDRESS),
            Optional.of(MemberConstants.COMMENTS), true, true, Optional.ofNullable(KeyConstants.NUMBER), feeType,
            new HashSet<>(List.of(Member.PROFILE_TYPE)));
    }

    public static final Member withoutType() {
        final Name    name;
        final FeeType feeType;

        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new Member(Optional.of(MemberConstants.IDENTIFIER), MemberConstants.NUMBER, name,
            Optional.of(MemberConstants.BIRTH_DATE), List.of(), Optional.of(MemberConstants.ADDRESS),
            Optional.of(MemberConstants.COMMENTS), true, true, feeType, new HashSet<>());
    }

}
