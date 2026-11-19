
package com.bernardomg.association.calendar.game.test.service.unit;

import static org.mockito.BDDMockito.given;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.calendar.game.domain.model.GameTable;
import com.bernardomg.association.calendar.game.domain.repository.GameTableRepository;
import com.bernardomg.association.calendar.game.test.configuration.factory.GameTables;
import com.bernardomg.association.calendar.game.usecase.service.DefaultGameTableService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGameTableService - create")
class TestGameTableServiceCreate {

    @Mock
    private GameTableRepository     gameTableRepository;

    @InjectMocks
    private DefaultGameTableService service;

    @Test
    @DisplayName("When creating a game table, then the persisted data is returned")
    void testCreate() {
        final GameTable gameTable;
        final GameTable toCreate;

        // GIVEN
        toCreate = GameTables.valid();
        given(gameTableRepository.save(toCreate)).willReturn(GameTables.valid());

        // WHEN
        gameTable = service.create(toCreate);

        // THEN
        Assertions.assertThat(gameTable)
            .as("game table")
            .isEqualTo(GameTables.valid());
    }

}
