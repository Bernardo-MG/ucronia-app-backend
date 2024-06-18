
package com.bernardomg.jpa.converter.test;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.jpa.converter.YearMonthDateAttributeConverter;

@ExtendWith(MockitoExtension.class)
@DisplayName("YearMonthDateAttributeConverter - convertToEntityAttribute")
public class YearMonthDateAttributeConverterFromDbTest {

    private final YearMonthDateAttributeConverter converter = new YearMonthDateAttributeConverter();

    @Test
    @DisplayName("When receiving a month it is converted")
    void testConvertToEntityAttribute() {
        final Date      date;
        final YearMonth month;

        // GIVEN
        date = Date.valueOf(LocalDate.now());

        // WHEN
        month = converter.convertToEntityAttribute(date);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(month.getYear())
                .as("converted date year")
                .isEqualTo(date.toLocalDate()
                    .getYear());
            softly.assertThat(month.getMonthValue())
                .as("converted date month")
                .isEqualTo(date.toLocalDate()
                    .getMonthValue());
        });
    }

    @Test
    @DisplayName("When the value is null a null is returned")
    void testConvertToEntityAttribute_Null() {
        final Date      date;
        final YearMonth month;

        // GIVEN
        date = null;

        // WHEN
        month = converter.convertToEntityAttribute(date);

        // THEN
        Assertions.assertThat(month)
            .as("converted date")
            .isNull();
    }

}
