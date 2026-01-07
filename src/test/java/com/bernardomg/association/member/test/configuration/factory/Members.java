
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.profile.domain.model.ProfileName;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class Members {

    public static final Member active() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Member(ProfileConstants.NUMBER, name, true, true);
    }

    public static final Member activeNoRenew() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Member(ProfileConstants.NUMBER, name, true, false);
    }

    public static final Member alternativeActive() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.ALTERNATIVE_FIRST_NAME, ProfileConstants.ALTERNATIVE_LAST_NAME);
        return new Member(ProfileConstants.ALTERNATIVE_NUMBER, name, true, true);
    }

    public static final Member alternativeInactive() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.ALTERNATIVE_FIRST_NAME, ProfileConstants.ALTERNATIVE_LAST_NAME);
        return new Member(ProfileConstants.ALTERNATIVE_NUMBER, name, false, true);
    }

    public static final Member created() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Member(1L, name, true, true);
    }

    public static final Member forNumber(final long number) {
        final ProfileName name;

        name = new ProfileName("Profile " + number, "Last name " + number);
        return new Member(number * 10, name, true, true);
    }

    public static final Member inactive() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Member(ProfileConstants.NUMBER, name, false, true);
    }

    public static final Member inactiveNoRenew() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Member(ProfileConstants.NUMBER, name, false, false);
    }

    public static final Member nameChange() {
        final ProfileName name;

        name = new ProfileName("Profile 123", "Last name");
        return new Member(ProfileConstants.NUMBER, name, true, true);
    }

    public static final Member nameChangePatch() {
        final ProfileName name;

        name = new ProfileName("Profile 123", "Last name");
        return new Member(ProfileConstants.NUMBER, name, null, null);
    }

    public static final Member padded() {
        final ProfileName name;

        name = new ProfileName(" " + ProfileConstants.FIRST_NAME + " ", " " + ProfileConstants.LAST_NAME + " ");
        return new Member(ProfileConstants.NUMBER, name, true, true);
    }

    public static final Member toCreate() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Member(0L, name, true, true);
    }

}
