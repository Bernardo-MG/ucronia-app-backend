
package com.bernardomg.pagination.test.tool.service;

import com.bernardomg.mvc.pagination.model.Pagination;
import com.bernardomg.mvc.pagination.model.Sort;

public interface PaginationTestingService {

    public Iterable<String> getAll(final Pagination pagination, final Sort sort);

}
