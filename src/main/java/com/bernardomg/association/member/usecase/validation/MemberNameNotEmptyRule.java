
package com.bernardomg.association.member.usecase.validation;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Checks the donor has a name.
 */
@Slf4j
public final class MemberNameNotEmptyRule implements FieldRule<Member> {

    public MemberNameNotEmptyRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final Member member) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (StringUtils.isBlank(member.getName()
            .getFirstName())) {
            log.error("Empty name");
            fieldFailure = FieldFailure.of("name", "empty", member.getName());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
