
package com.bernardomg.association.library.gamesystem.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.library.gamesystem.domain.exception.MissingGameSystemException;
import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystemConstants;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystems;
import com.bernardomg.association.library.gamesystem.usecase.service.DefaultGameSystemService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGameSystemService - getOne")
class TestGameSystemServiceGetOne {

    @Mock
    private GameSystemRepository     gameSystemRepository;

    @InjectMocks
    private DefaultGameSystemService service;

    @Test
    @DisplayName("When there is a game system, then it is returned")
    void testGetOne() {
        final Optional<GameSystem> gameSystem;

        // GIVEN
        given(gameSystemRepository.findOne(GameSystemConstants.NUMBER)).willReturn(Optional.of(GameSystems.valid()));

        // WHEN
        gameSystem = service.getOne(GameSystemConstants.NUMBER);

        // THEN
        Assertions.assertThat(gameSystem)
            .as("When there is a game system, then it is returned")
            .contains(GameSystems.valid());
    }

    @Test
    @DisplayName("When there are no game systems, then an exception is thrown")
    void testGetOne_NotExisting() {
        final ThrowingCallable action;

        // GIVEN
        given(gameSystemRepository.findOne(GameSystemConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.getOne(GameSystemConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there are no game systems, then an exception is thrown")
            .isInstanceOf(MissingGameSystemException.class);
    }

}
