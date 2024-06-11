
package com.bernardomg.association.member.usecase.validation;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.AbstractValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CreateMemberValidator extends AbstractValidator<Member> {

    public CreateMemberValidator() {
        super();
    }

    @Override
    protected final void checkRules(final Member member, final Collection<FieldFailure> failures) {
        FieldFailure failure;

        if (StringUtils.isBlank(member.getName()
            .getFirstName())) {
            log.error("Empty name");
            failure = FieldFailure.of("name", "empty", member.getName());
            failures.add(failure);
        }
    }

}
