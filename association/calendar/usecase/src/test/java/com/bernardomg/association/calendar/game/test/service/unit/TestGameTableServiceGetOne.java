
package com.bernardomg.association.calendar.game.test.service.unit;

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

import com.bernardomg.association.calendar.domain.exception.MissingGameTableException;
import com.bernardomg.association.calendar.game.domain.model.GameTable;
import com.bernardomg.association.calendar.game.domain.repository.GameTableRepository;
import com.bernardomg.association.calendar.game.test.configuration.factory.GameTableConstants;
import com.bernardomg.association.calendar.game.test.configuration.factory.GameTables;
import com.bernardomg.association.calendar.game.usecase.service.DefaultGameTableService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGameTableService - getOne")
class TestGameTableServiceGetOne {

    @Mock
    private GameTableRepository     gameTableRepository;

    @InjectMocks
    private DefaultGameTableService service;

    @Test
    @DisplayName("When there is data, then it is returned")
    void testGetOne() {
        final Optional<GameTable> gameTable;

        // GIVEN
        given(gameTableRepository.findOne(GameTableConstants.NUMBER)).willReturn(Optional.of(GameTables.valid()));

        // WHEN
        gameTable = service.getOne(GameTableConstants.NUMBER);

        // THEN
        Assertions.assertThat(gameTable)
            .as("game table")
            .contains(GameTables.valid());
    }

    @Test
    @DisplayName("When there is no data, then an exception is thrown")
    void testGetOne_NoData() {
        final ThrowingCallable action;

        // GIVEN
        given(gameTableRepository.findOne(GameTableConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.getOne(GameTableConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is no data, then an exception is thrown")
            .isInstanceOf(MissingGameTableException.class);
    }

}
