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

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.activity.TestApplication;
import com.bernardomg.association.activity.domain.model.Activity;
import com.bernardomg.association.activity.domain.repository.ActivityRepository;
import com.bernardomg.association.activity.test.configuration.data.annotation.ValidActivity;
import com.bernardomg.association.activity.test.configuration.factory.Activities;
import com.bernardomg.association.activity.test.configuration.factory.ActivityConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ActivityRepository - find one")
class ITActivityRepositoryFindOne {

    @Autowired
    private ActivityRepository repository;

    @Test
    @DisplayName("With an existing activity, it is returned")
    @ValidActivity
    void testFindOne() {
        final Optional<Activity> activityOptional;

        // WHEN
        activityOptional = repository.findOne(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThat(activityOptional)
            .contains(Activities.valid());
    }

    @Test
    @DisplayName("With no activity, nothing is returned")
    void testFindOne_NoData() {
        final Optional<Activity> activityOptional;

        // WHEN
        activityOptional = repository.findOne(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThat(activityOptional)
            .isEmpty();
    }

}
