
package com.bernardomg.association.library.usecase.validation;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.library.domain.model.BookType;
import com.bernardomg.association.library.domain.repository.BookTypeRepository;
import com.bernardomg.validation.AbstractValidator;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CreateBookTypeValidator extends AbstractValidator<BookType> {

    private final BookTypeRepository bookTypeRepository;

    public CreateBookTypeValidator(final BookTypeRepository bookTypeRepo) {
        super();

        bookTypeRepository = bookTypeRepo;
    }

    @Override
    protected final void checkRules(final BookType bookType, final Collection<FieldFailure> failures) {
        FieldFailure failure;

        if (StringUtils.isBlank(bookType.getName())) {
            log.error("Empty name");
            failure = FieldFailure.of("name", "empty", bookType.getName());
            failures.add(failure);
        }

        if (bookTypeRepository.exists(bookType.getName())) {
            log.error("Existing name {}", bookType.getName());
            failure = FieldFailure.of("name", "existing", bookType.getName());
            failures.add(failure);
        }
    }

}
