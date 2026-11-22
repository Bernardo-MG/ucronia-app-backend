
package com.bernardomg.association.library.gamesystem.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGameSystemService - update")
class TestGameSystemServiceUpdate {

    @Mock
    private GameSystemRepository     gameSystemRepository;

    @InjectMocks
    private DefaultGameSystemService service;

    @Test
    @DisplayName("When there is a game system with an empty name, then an exception is thrown")
    void testUpdate_EmptyName() {
        final ThrowingCallable action;
        final GameSystem       gameSystem;

        // GIVEN
        gameSystem = GameSystems.emptyName();

        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);

        // WHEN
        action = () -> service.update(gameSystem);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("empty", "name", ""));
    }

    @Test
    @DisplayName("When there is a game system with a name existing for another user, then an exception is thrown")
    void testUpdate_ExistsForAnother() {
        final GameSystem       gameSystem;
        final ThrowingCallable action;

        // GIVEN
        gameSystem = GameSystems.valid();

        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.existsByNameForAnother(GameSystemConstants.NAME, GameSystemConstants.NUMBER))
            .willReturn(true);

        // WHEN
        action = () -> service.update(gameSystem);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("existing", "name", GameSystemConstants.NAME));
    }

    @Test
    @DisplayName("When there is a not existing game system, then an exception is thrown")
    void testUpdate_NotExisting() {
        final GameSystem       gameSystem;
        final ThrowingCallable action;

        // GIVEN
        gameSystem = GameSystems.valid();

        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.update(gameSystem);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing game system, then an exception is thrown")
            .isInstanceOf(MissingGameSystemException.class);
    }

    @Test
    @DisplayName("When there is a valid game system, then the game system is persisted")
    void testUpdate_PersistedData() {
        final GameSystem gameSystem;

        // GIVEN
        gameSystem = GameSystems.valid();

        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(gameSystem);

        // THEN
        verify(gameSystemRepository).save(GameSystems.valid());
    }

    @Test
    @DisplayName("When there is a valid game system, then the created game system is returned")
    void testUpdate_ReturnedData() {
        final GameSystem gameSystem;
        final GameSystem created;

        // GIVEN
        gameSystem = GameSystems.valid();

        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);

        given(gameSystemRepository.save(GameSystems.valid())).willReturn(GameSystems.valid());

        // WHEN
        created = service.update(gameSystem);

        // THEN
        Assertions.assertThat(created)
            .as("When there is a valid game system, then the created game system is returned")
            .isEqualTo(GameSystems.valid());
    }

}
