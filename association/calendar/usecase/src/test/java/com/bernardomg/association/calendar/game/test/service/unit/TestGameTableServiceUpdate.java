
package com.bernardomg.association.calendar.game.test.service.unit;

import static org.mockito.BDDMockito.given;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.calendar.domain.exception.MissingGameTableException;
import com.bernardomg.association.calendar.game.domain.model.GameTable;
import com.bernardomg.association.calendar.game.domain.repository.GameTableRepository;
import com.bernardomg.association.calendar.game.test.configuration.factory.GameTableConstants;
import com.bernardomg.association.calendar.game.test.configuration.factory.GameTables;
import com.bernardomg.association.calendar.game.usecase.service.DefaultGameTableService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGameTableService - update")
class TestGameTableServiceUpdate {

    @Mock
    private GameTableRepository     gameTableRepository;

    @InjectMocks
    private DefaultGameTableService service;

    @Test
    @DisplayName("When updating an existing game table, then the updated data is returned")
    void testUpdate() {
        final GameTable gameTable;
        final GameTable toUpdate;

        // GIVEN
        toUpdate = GameTables.nameChange();
        given(gameTableRepository.exists(GameTableConstants.NUMBER)).willReturn(true);
        given(gameTableRepository.save(toUpdate)).willReturn(GameTables.nameChange());

        // WHEN
        gameTable = service.update(toUpdate);

        // THEN
        Assertions.assertThat(gameTable)
            .as("game table")
            .isEqualTo(GameTables.nameChange());
    }

    @Test
    @DisplayName("When updating a not existing game table, then an exception is thrown")
    void testUpdate_NotExisting() {
        final ThrowingCallable action;

        // GIVEN
        given(gameTableRepository.exists(GameTableConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.update(GameTables.valid());

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When updating a not existing game table, then an exception is thrown")
            .isInstanceOf(MissingGameTableException.class);
    }

}
