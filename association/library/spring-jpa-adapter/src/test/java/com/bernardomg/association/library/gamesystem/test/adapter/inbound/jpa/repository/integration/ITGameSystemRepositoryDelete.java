
package com.bernardomg.association.library.gamesystem.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullGameBook;
import com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.repository.GameSystemSpringRepository;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.gamesystem.test.configuration.data.annotation.ValidGameSystem;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystemConstants;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GameSystemRepository - delete")
class ITGameSystemRepositoryDelete {

    @Autowired
    private GameSystemRepository       repository;

    @Autowired
    private GameSystemSpringRepository springRepository;

    @Test
    @DisplayName("When there is a game system, then it is deleted")
    @ValidGameSystem
    void testDelete() {
        // WHEN
        repository.delete(GameSystemConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("game systems")
            .isZero();
    }

    @Test
    @DisplayName("When the game system is assigned to a book, then it is deleted")
    @ValidProfile
    @FullGameBook
    void testDelete_InBook() {
        // WHEN
        repository.delete(GameSystemConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("game systems")
            .isZero();
    }

    @Test
    @DisplayName("When there is no data, then nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(GameSystemConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("game systems")
            .isZero();
    }

}
