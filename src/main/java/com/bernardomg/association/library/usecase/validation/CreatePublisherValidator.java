
package com.bernardomg.association.library.usecase.validation;

import java.util.Collection;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.library.domain.model.Publisher;
import com.bernardomg.association.library.domain.repository.PublisherRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.AbstractValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CreatePublisherValidator extends AbstractValidator<Publisher> {

    private final PublisherRepository publisherRepository;

    public CreatePublisherValidator(final PublisherRepository publisherRepo) {
        super();

        publisherRepository = Objects.requireNonNull(publisherRepo);
    }

    @Override
    protected final void checkRules(final Publisher publisher, final Collection<FieldFailure> failures) {
        FieldFailure failure;

        if (StringUtils.isBlank(publisher.getName())) {
            log.error("Empty name");
            failure = FieldFailure.of("name", "empty", publisher.getName());
            failures.add(failure);
        }

        if ((!StringUtils.isBlank(publisher.getName())) && (publisherRepository.exists(publisher.getName()))) {
            log.error("Existing name {}", publisher.getName());
            failure = FieldFailure.of("name", "existing", publisher.getName());
            failures.add(failure);
        }
    }

}
