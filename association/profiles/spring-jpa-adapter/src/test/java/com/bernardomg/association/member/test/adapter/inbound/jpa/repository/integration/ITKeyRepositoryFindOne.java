/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martinez Garrido
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

package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.member.domain.model.Key;
import com.bernardomg.association.member.domain.repository.KeyRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.AlternativeMemberWithKey;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("KeyRepository - find one")
class ITKeyRepositoryFindOne {

    @Autowired
    private KeyRepository repository;

    @Test
    @DisplayName("With a key, it is returned")
    @PositiveFeeType
    @AlternativeMemberWithKey
    void testFindOne() {
        final Optional<Key> key;

        // WHEN
        key = repository.findOne(MemberConstants.KEY);

        // THEN
        Assertions.assertThat(key)
            .contains(new Key(MemberConstants.KEY, false, "Main entrance key"));
    }

    @Test
    @DisplayName("With no key, nothing is returned")
    void testFindOne_NoData() {
        final Optional<Key> key;

        // WHEN
        key = repository.findOne(MemberConstants.KEY);

        // THEN
        Assertions.assertThat(key)
            .isEmpty();
    }

}
