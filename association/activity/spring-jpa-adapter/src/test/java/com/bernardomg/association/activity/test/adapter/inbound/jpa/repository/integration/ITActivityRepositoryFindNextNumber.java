/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
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

package com.bernardomg.association.activity.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.activity.TestApplication;
import com.bernardomg.association.activity.domain.repository.ActivityRepository;
import com.bernardomg.association.activity.test.configuration.data.annotation.ValidActivity;
import com.bernardomg.association.activity.test.configuration.factory.ActivityConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ActivityRepository - find next number")
class ITActivityRepositoryFindNextIndex {

    @Autowired
    private ActivityRepository repository;

    @Test
    @DisplayName("When there is a activity the next number is correct")
    @ValidActivity
    void testFindNextNumber() {
        final long number;

        // WHEN
        number = repository.findNextNumber();

        // THEN
        Assertions.assertThat(number)
            .as("number")
            .isEqualTo(ActivityConstants.NEXT_NUMBER);
    }

    @Test
    @DisplayName("When there is no activity the next number is correct")
    void testFindNextNumber_NoData() {
        final long number;

        // WHEN
        number = repository.findNextNumber();

        // THEN
        Assertions.assertThat(number)
            .as("number")
            .isEqualTo(1);
    }

}
