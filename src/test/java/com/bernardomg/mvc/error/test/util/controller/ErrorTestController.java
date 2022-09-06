
package com.bernardomg.mvc.error.test.util.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.mvc.error.test.util.model.ErrorTestObject;
import com.bernardomg.validation.error.DefaultValidationFailure;
import com.bernardomg.validation.error.ValidationFailure;
import com.bernardomg.validation.exception.ValidationException;

@RestController
@RequestMapping(ErrorTestController.PATH)
public class ErrorTestController {

    public static final String PATH            = "/test/error";

    public static final String PATH_METHOD_ARG = PATH + "/methodArg";

    public static final String PATH_VALIDATION = PATH + "/validation";

    public ErrorTestController() {
        super();
    }

    @PostMapping(path = "/methodArg", produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorTestObject exceptionMethodArg(@Valid @RequestBody final ErrorTestObject arg)
            throws MethodArgumentNotValidException {
        return arg;
    }

    @GetMapping(path = "/validation", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> exceptionValidation() {
        final DefaultValidationFailure      failure;
        final Collection<ValidationFailure> failures;

        failure = new DefaultValidationFailure();
        failure.setError("Error message");

        failures = new ArrayList<>();
        failures.add(failure);

        throw new ValidationException(failures);
    }

}
