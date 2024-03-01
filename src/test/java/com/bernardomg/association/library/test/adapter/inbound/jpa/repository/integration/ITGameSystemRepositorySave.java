/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.library.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.library.adapter.inbound.jpa.repository.GameSystemSpringRepository;
import com.bernardomg.association.library.domain.model.GameSystem;
import com.bernardomg.association.library.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.test.config.data.annotation.ValidBookType;
import com.bernardomg.association.library.test.config.factory.GameSystemEntities;
import com.bernardomg.association.library.test.config.factory.GameSystems;
import com.bernardomg.association.member.test.config.data.annotation.ValidMember;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("GameSystemRepository - save")
class ITGameSystemRepositorySave {

    @Autowired
    private GameSystemRepository       repository;

    @Autowired
    private GameSystemSpringRepository springRepository;

    @Test
    @DisplayName("When saving, an author is persisted")
    @ValidBookType
    @ValidMember
    void testSave_Persisted() {
        final GameSystem gameSystem;

        // GIVEN
        gameSystem = GameSystems.valid();

        // WHEN
        repository.save(gameSystem);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("game systems")
            .contains(GameSystemEntities.valid());
    }

    @Test
    @DisplayName("When saving, the persisted author is returned")
    @ValidBookType
    @ValidMember
    void testSave_Returned() {
        final GameSystem gameSystem;
        final GameSystem created;

        // GIVEN
        gameSystem = GameSystems.valid();

        // WHEN
        created = repository.save(gameSystem);

        // THEN
        Assertions.assertThat(created)
            .as("game system")
            .isEqualTo(GameSystems.valid());
    }

}
