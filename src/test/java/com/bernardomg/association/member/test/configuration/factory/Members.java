
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;

public final class Members {

    public static final Member active() {
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        return new Member(PersonConstants.NUMBER, PersonConstants.IDENTIFIER, name, true, PersonConstants.PHONE);
    }

    public static final Member active(final int index) {
        final PersonName name;

        name = new PersonName("Person " + index, "Last name " + index);
        return new Member(PersonConstants.NUMBER, PersonConstants.IDENTIFIER, name, true, PersonConstants.PHONE);
    }

    public static final Member emptyName() {
        final PersonName name;

        name = new PersonName(" ", PersonConstants.LAST_NAME);
        return new Member(PersonConstants.NUMBER, PersonConstants.IDENTIFIER, name, true, PersonConstants.PHONE);
    }

    public static final Member forNumber(final long number, final boolean active) {
        final PersonName name;

        name = new PersonName("Person " + number, "Last name " + number);
        return new Member(number * 10, String.valueOf(6788 + number), name, active, String.valueOf(12344 + number));
    }

    public static final Member inactive() {
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        return new Member(PersonConstants.NUMBER, PersonConstants.IDENTIFIER, name, false, PersonConstants.PHONE);
    }

    public static final Member inactive(final int index) {
        final PersonName name;

        name = new PersonName("Person " + index, "Last name " + index);
        return new Member(PersonConstants.NUMBER, PersonConstants.IDENTIFIER, name, false, PersonConstants.PHONE);
    }

    public static final Member inactiveAlternative() {
        final PersonName name;

        name = new PersonName(PersonConstants.ALTERNATIVE_FIRST_NAME, PersonConstants.ALTERNATIVE_LAST_NAME);
        return new Member(PersonConstants.NUMBER, PersonConstants.IDENTIFIER, name, false, PersonConstants.PHONE);
    }

    public static final Member inactiveWithNumber(final long number) {
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        return new Member(number, PersonConstants.IDENTIFIER, name, false, PersonConstants.PHONE);
    }

    public static final Member missingLastName() {
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, "");
        return new Member(PersonConstants.NUMBER, PersonConstants.IDENTIFIER, name, false, PersonConstants.PHONE);
    }

    public static final Member missingName() {
        final PersonName name;

        name = new PersonName("", PersonConstants.LAST_NAME);
        return new Member(PersonConstants.NUMBER, PersonConstants.IDENTIFIER, name, false, PersonConstants.PHONE);
    }

    public static final Member nameChange() {
        final PersonName name;

        name = new PersonName("Person 123", "Last name");
        return new Member(PersonConstants.NUMBER, PersonConstants.IDENTIFIER, name, true, PersonConstants.PHONE);
    }

    public static final Member nameChangeActive() {
        final PersonName name;

        name = new PersonName("Person 123", "Last name");
        return new Member(PersonConstants.NUMBER, PersonConstants.IDENTIFIER, name, true, PersonConstants.PHONE);
    }

    public static final Member nameChangePatch() {
        final PersonName name;

        name = new PersonName("Person 123", "Last name");
        return new Member(PersonConstants.NUMBER, null, name, null, null);
    }

    public static final Member paddedWithWhitespaces() {
        final PersonName name;

        name = new PersonName(" " + PersonConstants.FIRST_NAME + " ", " " + PersonConstants.LAST_NAME + " ");
        return new Member(PersonConstants.NUMBER, PersonConstants.IDENTIFIER, name, true, PersonConstants.PHONE);
    }

}
