
package com.bernardomg.association.member.test.configuration.factory;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.Member.ContactChannel;
import com.bernardomg.association.member.domain.model.Member.ContactMethod;
import com.bernardomg.association.member.domain.model.Member.Name;

public final class Members {

    public static final Member active() {
        final Name           name;
        final Member.FeeType feeType;

        feeType = new Member.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new Member(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final Member activeNoRenew() {
        final Name           name;
        final Member.FeeType feeType;

        feeType = new Member.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new Member(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, false, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final Member alternativeActive() {
        final Name           name;
        final Member.FeeType feeType;

        feeType = new Member.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.ALTERNATIVE_FIRST_NAME, MemberConstants.ALTERNATIVE_LAST_NAME);
        return new Member(MemberConstants.ALTERNATIVE_IDENTIFIER, MemberConstants.ALTERNATIVE_NUMBER, name,
            MemberConstants.BIRTH_DATE, List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true,
            feeType, Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final Member alternativeInactive() {
        final Name           name;
        final Member.FeeType feeType;

        feeType = new Member.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.ALTERNATIVE_FIRST_NAME, MemberConstants.ALTERNATIVE_LAST_NAME);
        return new Member(MemberConstants.ALTERNATIVE_IDENTIFIER, MemberConstants.ALTERNATIVE_NUMBER, name,
            MemberConstants.BIRTH_DATE, List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, false, true,
            feeType, Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final Member created() {
        final Name           name;
        final Member.FeeType feeType;

        feeType = new Member.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new Member(MemberConstants.IDENTIFIER, 1L, name, MemberConstants.BIRTH_DATE, List.of(),
            MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final Member firstNameChange() {
        final Name           name;
        final Member.FeeType feeType;

        feeType = new Member.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.CHANGED_FIRST_NAME, MemberConstants.LAST_NAME);
        return new Member(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final Member forNumber(final long number) {
        final Name           name;
        final Member.FeeType feeType;

        feeType = new Member.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name("Name " + number, "Last name " + number);
        return new Member(Objects.toString(number * 10), number * 10, name, MemberConstants.BIRTH_DATE, List.of(),
            MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final Member inactive() {
        final Name           name;
        final Member.FeeType feeType;

        feeType = new Member.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new Member(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, false, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final Member inactiveNoRenew() {
        final Name           name;
        final Member.FeeType feeType;

        feeType = new Member.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new Member(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, false, false, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final Member nameChange() {
        final Name           name;
        final Member.FeeType feeType;

        feeType = new Member.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name("Name 123", "Last name");
        return new Member(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final Member nameChangePatch() {
        final Name           name;
        final Member.FeeType feeType;

        feeType = new Member.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name("Name 123", "Last name");
        return new Member(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final Member noContactChannel() {
        final Name           name;
        final Member.FeeType feeType;

        feeType = new Member.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new Member(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final Member noGames() {
        final Name           name;
        final Member.FeeType feeType;

        feeType = new Member.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new Member(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final Member padded() {
        final Name           name;
        final Member.FeeType feeType;

        feeType = new Member.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(" " + MemberConstants.FIRST_NAME + " ", " " + MemberConstants.LAST_NAME + " ");
        return new Member(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final Member toCreate() {
        final Name           name;
        final Member.FeeType feeType;

        feeType = new Member.FeeType(FeeConstants.FEE_TYPE_NUMBER, "", 0f);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new Member(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType, Set.of());
    }

    public static final Member toCreateWithEmail() {
        final Name           name;
        final Member.FeeType feeType;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        feeType = new Member.FeeType(FeeConstants.FEE_TYPE_NUMBER, "", 0f);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        contactMethod = email();
        contactChannel = new ContactChannel(contactMethod, MemberConstants.EMAIL);
        return new Member(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(contactChannel), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType, Set.of());
    }

    public static final Member withEmail() {
        final Name           name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;
        final Member.FeeType feeType;

        feeType = new Member.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        contactMethod = email();
        contactChannel = new ContactChannel(contactMethod, MemberConstants.EMAIL);
        return new Member(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(contactChannel), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType,
            Set.of(MemberEntityConstants.PROFILE_TYPE));
    }

    public static final Member withoutType() {
        final Name           name;
        final Member.FeeType feeType;

        feeType = new Member.FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new Member(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, true, true, feeType, Set.of());
    }

    private static final ContactMethod email() {
        return new ContactMethod(MemberContactMethodConstants.NUMBER, MemberContactMethodConstants.EMAIL);
    }

}
