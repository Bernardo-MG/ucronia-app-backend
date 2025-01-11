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

package com.bernardomg.association.fee.domain.model;

import java.time.YearMonth;
import java.util.Collection;

import com.bernardomg.association.person.domain.model.PersonName;
import com.fasterxml.jackson.annotation.JsonProperty;

public record FeeCalendar(Person person, Collection<FeeCalendarMonth> months, Integer year) {

    public record FeeCalendarMonth(YearMonth month, Boolean paid) {

        @JsonProperty("monthNumber")
        public int monthNumber() {
            // TODO: the frontend should be able to get this value
            return month.getMonthValue();
        }
    }

    public static record Person(Long number, PersonName name, Membership membership) {

        public record Membership(Boolean active) {

        }

    }

}
