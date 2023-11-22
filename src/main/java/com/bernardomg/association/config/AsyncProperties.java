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

package com.bernardomg.association.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

/**
 * Async configuration properties.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Validated
@Data
@ConfigurationProperties(prefix = "async")
public final class AsyncProperties {

    @Validated
    @Data
    public final class ExecutorProperties {

        @PositiveOrZero
        private Integer corePoolSize     = 1;

        @NotEmpty
        private String  groupName        = "AsyncGroup";

        @PositiveOrZero
        private Integer maxPoolSize      = 5;

        @PositiveOrZero
        private Integer queueCapacity    = 10;

        @NotEmpty
        private String  threadNamePrefix = "AsyncExecutor-";

    }

    @Validated
    @Data
    public final class SchedulerProperties {

        @NotEmpty
        private String  groupName        = "SchedulerGroup";

        @PositiveOrZero
        private Integer poolSize         = 2;

        @NotEmpty
        private String  threadNamePrefix = "AsyncScheduler-";

    }

    @NotNull
    private ExecutorProperties  executor  = new ExecutorProperties();

    @NotNull
    private SchedulerProperties scheduler = new SchedulerProperties();

}
