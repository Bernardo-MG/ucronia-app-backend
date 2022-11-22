
package com.bernardomg.mvc.error.test.util.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.validation.exception.ValidationException;

@RestController
@RequestMapping(ValidationExceptionTestController.PATH)
public class ValidationExceptionTestController {

    public static final String PATH                  = "/test/error/validation";

    public static final String PATH_FIELD_VALIDATION = PATH + "/field";

    public static final String PATH_VALIDATION       = PATH + "/generic";

    public ValidationExceptionTestController() {
        super();
    }

    @GetMapping(path = "/field", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> exceptionFieldValidation() {
        final FieldFailure        failure;
        final Collection<Failure> failures;

        failure = FieldFailure.of("Error message", "field", "value");

        failures = new ArrayList<>();
        failures.add(failure);

        throw new ValidationException(failures);
    }

    @GetMapping(path = "/generic", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> exceptionValidation() {
        final Failure             failure;
        final Collection<Failure> failures;

        failure = Failure.of("Error message");

        failures = new ArrayList<>();
        failures.add(failure);

        throw new ValidationException(failures);
    }

}
