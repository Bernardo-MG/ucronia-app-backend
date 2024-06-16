
package com.bernardomg.association.library.usecase.validation;

import java.util.List;

import com.bernardomg.association.library.domain.model.GameSystem;
import com.bernardomg.association.library.domain.repository.GameSystemRepository;
import com.bernardomg.validation.validator.AbstractFieldRuleValidator;

public final class CreateGameSystemValidator extends AbstractFieldRuleValidator<GameSystem> {

    public CreateGameSystemValidator(final GameSystemRepository gameSystemRepository) {
        super(List.of(new GameSystemNameNotEmptyRule(), new GameSystemNameNotExistingRule(gameSystemRepository)));
    }

}
