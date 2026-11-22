
package com.bernardomg.association.library.gamesystem.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystemConstants;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystems;
import com.bernardomg.association.library.gamesystem.usecase.service.DefaultGameSystemService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGameSystemService - delete")
class TestGameSystemServiceDelete {

    @Mock
    private GameSystemRepository     gameSystemRepository;

    @InjectMocks
    private DefaultGameSystemService service;

    @Test
    @DisplayName("When deleting a game system, then the repository is called")
    void testDelete_CallsRepository() {
        // GIVEN
        given(gameSystemRepository.findOne(GameSystemConstants.NUMBER)).willReturn(Optional.of(GameSystems.valid()));

        // WHEN
        service.delete(GameSystemConstants.NUMBER);

        // THEN
        verify(gameSystemRepository).delete(GameSystemConstants.NUMBER);
    }

    @Test
    @DisplayName("When the game system doesn't exist, then an exception is thrown")
    void testDelete_NotExisting_Exception() {
        final ThrowingCallable action;

        // GIVEN
        given(gameSystemRepository.findOne(GameSystemConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.delete(GameSystemConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the game system doesn't exist, then an exception is thrown")
            .isInstanceOf(MissingGameSystemException.class);
    }

}
