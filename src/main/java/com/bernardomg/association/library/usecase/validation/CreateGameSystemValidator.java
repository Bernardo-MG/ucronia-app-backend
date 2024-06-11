
package com.bernardomg.association.library.usecase.validation;

import java.util.Collection;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.library.domain.model.GameSystem;
import com.bernardomg.association.library.domain.repository.GameSystemRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.AbstractValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CreateGameSystemValidator extends AbstractValidator<GameSystem> {

    private final GameSystemRepository gameSystemRepository;

    public CreateGameSystemValidator(final GameSystemRepository gameSystemRepo) {
        super();

        gameSystemRepository = Objects.requireNonNull(gameSystemRepo);
    }

    @Override
    protected final void checkRules(final GameSystem gameSystem, final Collection<FieldFailure> failures) {
        FieldFailure failure;

        if (StringUtils.isBlank(gameSystem.getName())) {
            log.error("Empty name");
            failure = FieldFailure.of("name", "empty", gameSystem.getName());
            failures.add(failure);
        }

        if ((!StringUtils.isBlank(gameSystem.getName())) && (gameSystemRepository.exists(gameSystem.getName()))) {
            log.error("Existing name {}", gameSystem.getName());
            failure = FieldFailure.of("name", "existing", gameSystem.getName());
            failures.add(failure);
        }
    }

}
