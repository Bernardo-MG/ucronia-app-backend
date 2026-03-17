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

package com.bernardomg.association.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Async configuration properties.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Validated
@ConfigurationProperties(prefix = "async")
public final record AsyncProperties(@NotNull ExecutorProperties executor, @NotNull SchedulerProperties scheduler) {

    public AsyncProperties(final ExecutorProperties executor, final SchedulerProperties scheduler) {
        if (executor == null) {
            this.executor = new ExecutorProperties(null, null, null, null, null);
        } else {
            this.executor = executor;
        }
        if (scheduler == null) {
            this.scheduler = new SchedulerProperties(null, null, null);
        } else {
            this.scheduler = scheduler;
        }
    }

    @Validated
    public final record ExecutorProperties(@PositiveOrZero Integer corePoolSize, @NotEmpty String groupName,
            @PositiveOrZero Integer maxPoolSize, @PositiveOrZero Integer queueCapacity,
            @NotEmpty String threadNamePrefix) {

        public ExecutorProperties(final Integer corePoolSize, final String groupName, final Integer maxPoolSize,
                final Integer queueCapacity, final String threadNamePrefix) {
            if (corePoolSize == null) {
                this.corePoolSize = 1;
            } else {
                this.corePoolSize = corePoolSize;
            }
            if (groupName == null) {
                this.groupName = "AsyncGroup";
            } else {
                this.groupName = groupName;
            }
            if (maxPoolSize == null) {
                this.maxPoolSize = 5;
            } else {
                this.maxPoolSize = maxPoolSize;
            }
            if (queueCapacity == null) {
                this.queueCapacity = 10;
            } else {
                this.queueCapacity = queueCapacity;
            }
            if (threadNamePrefix == null) {
                this.threadNamePrefix = "AsyncExecutor-";
            } else {
                this.threadNamePrefix = threadNamePrefix;
            }
        }

    }

    @Validated
    public final record SchedulerProperties(@NotEmpty String groupName, @PositiveOrZero Integer poolSize,
            @NotEmpty String threadNamePrefix) {

        public SchedulerProperties(final String groupName, final Integer poolSize, final String threadNamePrefix) {
            if (groupName == null) {
                this.groupName = "SchedulerGroup";
            } else {
                this.groupName = groupName;
            }
            if (poolSize == null) {
                this.poolSize = 2;
            } else {
                this.poolSize = poolSize;
            }
            if (threadNamePrefix == null) {
                this.threadNamePrefix = "AsyncScheduler-";
            } else {
                this.threadNamePrefix = threadNamePrefix;
            }
        }

    }

}
