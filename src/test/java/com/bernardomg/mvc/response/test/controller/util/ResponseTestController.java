
package com.bernardomg.mvc.response.test.controller.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ResponseTestController.PATH)
public class ResponseTestController {

    public static final String PATH            = "/test/response";

    public static final String PATH_COLLECTION = PATH + "/collection";

    public static final String PATH_PAGE       = PATH + "/page";

    public ResponseTestController() {
        super();
    }

    @GetMapping(path = "/collection", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> readCollection() {
        return Arrays.asList("abc");
    }

    @GetMapping(path = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<String> readPage() {
        final List<String> content;
        final Pageable     pageable;
        final long         total;

        content = Arrays.asList("abc", "abc", "abc", "abc", "abc");
        pageable = Pageable.ofSize(5);
        total = 20;

        return new PageImpl<>(content, pageable, total);
    }

}
