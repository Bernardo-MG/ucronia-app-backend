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

package com.bernardomg.association.activity.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.bernardomg.association.activity.adapter.inbound.jpa.repository.ActivitySpringRepository;
import com.bernardomg.association.activity.adapter.inbound.jpa.repository.JpaActivityRepository;
import com.bernardomg.association.activity.domain.repository.ActivityRepository;
import com.bernardomg.association.activity.usecase.service.ActivityService;
import com.bernardomg.association.activity.usecase.service.DefaultActivityService;

@AutoConfiguration
@ComponentScan({ "com.bernardomg.association.activity.adapter.outbound.rest.controller",
        "com.bernardomg.association.activity.adapter.inbound.jpa" })
public class AssociationActivityAutoConfiguration {

    @Bean("activityRepository")
    public ActivityRepository getActivityRepository(final ActivitySpringRepository activitySpringRepository) {
        return new JpaActivityRepository(activitySpringRepository);
    }

    @Bean("activityService")
    public ActivityService getActivityService(final ActivityRepository activityRepository) {
        return new DefaultActivityService(activityRepository);
    }

}
