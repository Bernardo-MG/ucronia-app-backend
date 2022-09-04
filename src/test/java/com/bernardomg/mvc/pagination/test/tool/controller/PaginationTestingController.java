
package com.bernardomg.mvc.pagination.test.tool.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.mvc.pagination.model.Pagination;
import com.bernardomg.mvc.pagination.model.Sort;
import com.bernardomg.mvc.pagination.test.tool.service.PaginationTestingService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(PaginationTestingController.URL)
@AllArgsConstructor
public class PaginationTestingController {

    public static final String             URL = "/pagination";

    private final PaginationTestingService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<? extends String> readAll(final Pagination pagination, final Sort sort) {
        return service.getAll(pagination, sort);
    }

    @GetMapping(path = "/unpaged", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<? extends String> readAllUnpaged() {
        return service.getAll(Pagination.disabled(), Sort.disabled());
    }

}
