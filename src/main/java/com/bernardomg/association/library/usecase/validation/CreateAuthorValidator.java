
package com.bernardomg.association.library.usecase.validation;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.library.domain.model.Author;
import com.bernardomg.association.library.domain.repository.AuthorRepository;
import com.bernardomg.validation.AbstractValidator;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CreateAuthorValidator extends AbstractValidator<Author> {

    private final AuthorRepository authorRepository;

    public CreateAuthorValidator(final AuthorRepository authorRepo) {
        super();

        authorRepository = authorRepo;
    }

    @Override
    protected final void checkRules(final Author author, final Collection<FieldFailure> failures) {
        FieldFailure failure;

        if (StringUtils.isBlank(author.getName())) {
            log.error("Empty name");
            failure = FieldFailure.of("name", "empty", author.getName());
            failures.add(failure);
        }

        if (authorRepository.exists(author.getName())) {
            log.error("Existing name {}", author.getName());
            failure = FieldFailure.of("name", "existing", author.getName());
            failures.add(failure);
        }
    }

}
