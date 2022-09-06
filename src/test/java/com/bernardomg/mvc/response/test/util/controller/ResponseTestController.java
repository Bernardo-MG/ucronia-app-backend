
package com.bernardomg.mvc.response.test.util.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ResponseTestController.PATH)
public class ResponseTestController {

    public static final String PATH = "/test/response";

    public ResponseTestController() {
        super();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> read() {
        return Arrays.asList("abc");
    }

}
