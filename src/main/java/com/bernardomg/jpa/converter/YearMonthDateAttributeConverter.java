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

package com.bernardomg.jpa.converter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

import jakarta.persistence.AttributeConverter;

/**
 * Converts between {@link YearMonth} and {@link java.sql.Date Date}.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public final class YearMonthDateAttributeConverter implements AttributeConverter<YearMonth, java.sql.Date> {

    public YearMonthDateAttributeConverter() {
        super();
    }

    @Override
    public java.sql.Date convertToDatabaseColumn(final YearMonth attribute) {
        final java.sql.Date date;

        if (attribute != null) {
            date = java.sql.Date.valueOf(attribute.atDay(1));
        } else {
            date = null;
        }

        return date;
    }

    @Override
    public final YearMonth convertToEntityAttribute(final java.sql.Date dbData) {
        final YearMonth month;
        final LocalDate localDate;

        if (dbData != null) {
            localDate = Instant.ofEpochMilli(dbData.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
            month = YearMonth.from(localDate);
        } else {
            month = null;
        }

        return month;
    }

}
