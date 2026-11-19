
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

import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystemConstants;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystems;
import com.bernardomg.association.library.gamesystem.usecase.service.DefaultGameSystemService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGameSystemService - create")
class TestGameSystemServiceCreate {

    @Mock
    private GameSystemRepository     gameSystemRepository;

    @InjectMocks
    private DefaultGameSystemService service;

    @Test
    @DisplayName("When there is a game system with an empty name, then an exception is thrown")
    void testCreate_EmptyName() {
        final ThrowingCallable action;
        final GameSystem       gameSystem;

        // GIVEN
        gameSystem = GameSystems.emptyName();

        // WHEN
        action = () -> service.create(gameSystem);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("empty", "name", ""));
    }

    @Test
    @DisplayName("When there is a game system with an existing name, then an exception is thrown")
    void testCreate_ExistingName() {
        final ThrowingCallable action;
        final GameSystem       gameSystem;

        // GIVEN
        gameSystem = GameSystems.toCreate();

        given(gameSystemRepository.existsByName(GameSystemConstants.NAME)).willReturn(true);

        // WHEN
        action = () -> service.create(gameSystem);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("existing", "name", GameSystemConstants.NAME));
    }

    @Test
    @DisplayName("When there is a game system with padded name, then the game system is persisted")
    void testCreate_Padded_PersistedData() {
        final GameSystem gameSystem;

        // GIVEN
        gameSystem = GameSystems.padded();

        // WHEN
        service.create(gameSystem);

        // THEN
        verify(gameSystemRepository).save(GameSystems.toCreate());
    }

    @Test
    @DisplayName("When there is a valid game system, then the game system is persisted")
    void testCreate_PersistedData() {
        final GameSystem gameSystem;

        // GIVEN
        gameSystem = GameSystems.toCreate();

        // WHEN
        service.create(gameSystem);

        // THEN
        verify(gameSystemRepository).save(GameSystems.toCreate());
    }

    @Test
    @DisplayName("When there is a valid game system, then the created game system is returned")
    void testCreate_ReturnedData() {
        final GameSystem gameSystem;
        final GameSystem created;

        // GIVEN
        gameSystem = GameSystems.toCreate();

        given(gameSystemRepository.save(GameSystems.toCreate())).willReturn(GameSystems.valid());

        // WHEN
        created = service.create(gameSystem);

        // THEN
        Assertions.assertThat(created)
            .as("When there is a valid game system, then the created game system is returned")
            .isEqualTo(GameSystems.valid());
    }

}
