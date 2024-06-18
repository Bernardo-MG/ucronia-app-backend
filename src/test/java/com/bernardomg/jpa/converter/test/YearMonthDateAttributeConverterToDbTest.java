
package com.bernardomg.jpa.converter.test;

import java.sql.Date;
import java.time.YearMonth;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.jpa.converter.YearMonthDateAttributeConverter;

@ExtendWith(MockitoExtension.class)
@DisplayName("YearMonthDateAttributeConverter - convertToDatabaseColumn")
public class YearMonthDateAttributeConverterToDbTest {

    private final YearMonthDateAttributeConverter converter = new YearMonthDateAttributeConverter();

    @Test
    @DisplayName("When receiving a month it is converted")
    void testConvertToDatabaseColumn() {
        final Date      date;
        final YearMonth month;

        // GIVEN
        month = YearMonth.now();

        // WHEN
        date = converter.convertToDatabaseColumn(month);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(date.toLocalDate()
                .getYear())
                .as("converted date year")
                .isEqualTo(month.getYear());
            softly.assertThat(date.toLocalDate()
                .getMonthValue())
                .as("converted date month")
                .isEqualTo(month.getMonthValue());
        });
    }

    @Test
    @DisplayName("When the value is null a null is returned")
    void testConvertToDatabaseColumn_Null() {
        final Date      date;
        final YearMonth month;

        // GIVEN
        month = null;

        // WHEN
        date = converter.convertToDatabaseColumn(month);

        // THEN
        Assertions.assertThat(date)
            .as("converted date")
            .isNull();
    }

}
