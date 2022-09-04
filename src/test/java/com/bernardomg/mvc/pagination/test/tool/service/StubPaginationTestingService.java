
package com.bernardomg.mvc.pagination.test.tool.service;

import java.util.Collections;

import org.springframework.stereotype.Service;

import com.bernardomg.mvc.pagination.model.Pagination;
import com.bernardomg.mvc.pagination.model.Sort;

@Service
public final class StubPaginationTestingService implements PaginationTestingService {

    @Override
    public final Iterable<String> getAll(final Pagination pagination, final Sort sort) {
        return Collections.emptyList();
    }

}
