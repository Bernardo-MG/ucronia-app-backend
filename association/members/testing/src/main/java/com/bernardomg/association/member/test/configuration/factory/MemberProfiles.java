
package com.bernardomg.association.member.test.configuration.factory;

import java.util.List;
import java.util.Set;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.model.MemberProfile.ContactChannel;
import com.bernardomg.association.member.domain.model.MemberProfile.ContactMethod;
import com.bernardomg.association.member.domain.model.MemberProfile.Name;

public final class MemberProfiles {

    public static final MemberProfile valid() {
        final Name name;

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new MemberProfile(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, Set.of(Member.PROFILE_TYPE));
    }

    public static final MemberProfile withEmail() {
        final Name           name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        contactMethod = new ContactMethod(ContactMethodConstants.NUMBER, ContactMethodConstants.EMAIL);
        contactChannel = new ContactChannel(contactMethod, MemberConstants.EMAIL);
        return new MemberProfile(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(contactChannel), MemberConstants.ADDRESS, MemberConstants.COMMENTS, Set.of(Member.PROFILE_TYPE));
    }

    public static final MemberProfile withoutType() {
        final Name name;

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new MemberProfile(MemberConstants.IDENTIFIER, MemberConstants.NUMBER, name, MemberConstants.BIRTH_DATE,
            List.of(), MemberConstants.ADDRESS, MemberConstants.COMMENTS, Set.of());
    }

    private MemberProfiles() {
        super();
    }

}
