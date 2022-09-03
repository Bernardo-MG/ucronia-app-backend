
package com.bernardomg.pagination.test.unit.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import com.bernardomg.mvc.pagination.model.Pagination;
import com.bernardomg.mvc.pagination.model.Sort;
import com.bernardomg.mvc.pagination.utils.Paginations;

@DisplayName("Pagination utils - Pagination to Spring model - Pagination values")
public class TestPaginationsToSpringPagination {

    public TestPaginationsToSpringPagination() {
        super();
    }

    @Test
    @DisplayName("With disabled pagination the result is disabled")
    public void testPagination_Disabled_Unpaged() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.disabled();
        sort = Sort.disabled();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertFalse(result.isPaged());
    }

    @Test
    @DisplayName("Applies the correct values for the first page")
    public void testPagination_FirstPage_Values() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(0, 10);
        sort = Sort.disabled();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertEquals(0, result.getOffset());
        Assertions.assertEquals(0, result.getPageNumber());
        Assertions.assertEquals(10, result.getPageSize());
    }

    @Test
    @DisplayName("With the smalles pagination values the result is enabled")
    public void testPagination_Minimal_Paged() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(0, 1);
        sort = Sort.disabled();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertTrue(result.isPaged());
    }

    @Test
    @DisplayName("With negative pagination page the result is disabled")
    public void testPagination_NegativePage_Unpaged() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(-1, 1);
        sort = Sort.disabled();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertFalse(result.isPaged());
    }

    @Test
    @DisplayName("With negative pagination size the result is enabled")
    public void testPagination_NegativeSize_Paged() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(0, -1);
        sort = Sort.disabled();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertTrue(result.isPaged());
    }

    @Test
    @DisplayName("Applies the correct values for negative pagination size")
    public void testPagination_NegativeSize_Values() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(1, -1);
        sort = Sort.disabled();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertEquals(Pagination.DEFAULT_SIZE.longValue(), result.getOffset());
        Assertions.assertEquals(1, result.getPageNumber());
        Assertions.assertEquals(Pagination.DEFAULT_SIZE, result.getPageSize());
    }

    @Test
    @DisplayName("Applies the correct values for the second page")
    public void testPagination_SecondPage_Values() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(1, 10);
        sort = Sort.disabled();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertEquals(10, result.getOffset());
        Assertions.assertEquals(1, result.getPageNumber());
        Assertions.assertEquals(10, result.getPageSize());
    }

    @Test
    @DisplayName("With zero pagination size the result is enabled")
    public void testPagination_ZeroSize_Paged() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(0, 0);
        sort = Sort.disabled();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertTrue(result.isPaged());
    }

    @Test
    @DisplayName("Applies the correct values for zero pagination size")
    public void testPagination_ZeroSize_Values() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(1, 0);
        sort = Sort.disabled();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertEquals(Pagination.DEFAULT_SIZE.longValue(), result.getOffset());
        Assertions.assertEquals(1, result.getPageNumber());
        Assertions.assertEquals(Pagination.DEFAULT_SIZE, result.getPageSize());
    }

}
