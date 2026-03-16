
package com.bernardomg.test.pagination;

import java.util.Objects;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

/**
 * TOOD: why is this annotation needed? Seems to be a problem with transactions
 *
 * @param <T>
 */
@IntegrationTest
public abstract class AbstractPaginationIT<T> {

    private final Optional<String> sortField;

    private final int              totalElements;

    public AbstractPaginationIT(final int total) {
        super();

        totalElements = Objects.requireNonNull(total);
        sortField = Optional.empty();
    }

    public AbstractPaginationIT(final int total, final String field) {
        super();

        totalElements = Objects.requireNonNull(total);
        sortField = Optional.of(field);
    }

    protected abstract Page<T> read(final Pagination pagination, final Sorting sorting);

    protected void testPageData(final int page, final T expected) {
        final Page<T>    data;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(page, 1);
        sorting = sortField.map(Sorting::asc)
            .orElse(Sorting.unsorted());

        // WHEN
        data = read(pagination, sorting);

        // THEN
        Assertions.assertThat(data)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("paged data")
            .containsExactly(expected);
    }

    @Test
    @DisplayName("When a page is returned, it returns the correct page data")
    void testReadPaged_PageData() {
        final Page<T>    data;
        final Pagination pagination;
        final Sorting    sorting;
        final int        page;

        // GIVEN
        page = 1;
        pagination = new Pagination(page, 1);
        sorting = sortField.map(Sorting::asc)
            .orElse(Sorting.unsorted());

        // WHEN
        data = read(pagination, sorting);

        // THEN

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(data.elementsInPage())
                .as("number of elements in page")
                .isEqualTo(1);
            softly.assertThat(data.totalElements())
                .as("total number of elements")
                .isEqualTo(totalElements);
            softly.assertThat(data.page())
                .as("page number")
                .isEqualTo(page);
            softly.assertThat(data.totalPages())
                .as("total number of pages")
                .isEqualTo(totalElements);
        });
    }

    @Test
    @DisplayName("When a page request is received, the response size is the same as the page size")
    void testReadPaged_PageMax() {
        final Page<T>    data;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 1);
        sorting = sortField.map(Sorting::asc)
            .orElse(Sorting.unsorted());

        // WHEN
        data = read(pagination, sorting);

        // THEN
        Assertions.assertThat(data)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("paged data")
            .hasSize(1);
    }

}
