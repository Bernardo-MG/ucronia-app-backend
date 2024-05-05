
package com.bernardomg.association.member.usecase.validation;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.member.domain.model.Guest;
import com.bernardomg.validation.AbstractValidator;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CreateGuestValidator extends AbstractValidator<Guest> {

    public CreateGuestValidator() {
        super();
    }

    @Override
    protected final void checkRules(final Guest guest, final Collection<FieldFailure> failures) {
        FieldFailure failure;

        if (StringUtils.isBlank(guest.getName())) {
            log.error("Empty name");
            failure = FieldFailure.of("name", "empty", guest.getName());
            failures.add(failure);
        }
    }

}
