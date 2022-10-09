
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

import com.bernardomg.mvc.error.model.DtoFieldFailure;
import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.test.util.model.ErrorTestObject;
import com.bernardomg.validation.exception.ValidationException;

@RestController
@RequestMapping(ErrorTestController.PATH)
public class ErrorTestController {

    public static final String PATH                  = "/test/error";

    public static final String PATH_FIELD_VALIDATION = PATH + "/fieldValidation";

    public static final String PATH_METHOD_ARG       = PATH + "/methodArg";

    public static final String PATH_VALIDATION       = PATH + "/validation";

    public ErrorTestController() {
        super();
    }

    @GetMapping(path = "/fieldValidation", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> exceptionFieldValidation() {
        final DtoFieldFailure     failure;
        final Collection<Failure> failures;

        failure = new DtoFieldFailure();
        failure.setMessage("Error message");
        failure.setField("field");
        failure.setObject("object");
        failure.setValue("value");

        failures = new ArrayList<>();
        failures.add(failure);

        throw new ValidationException(failures);
    }

    @PostMapping(path = "/methodArg", produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorTestObject exceptionMethodArg(@Valid @RequestBody final ErrorTestObject arg)
            throws MethodArgumentNotValidException {
        return arg;
    }

    @GetMapping(path = "/validation", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> exceptionValidation() {
        final Failure             failure;
        final Collection<Failure> failures;

        failure = Failure.of("Error message");

        failures = new ArrayList<>();
        failures.add(failure);

        throw new ValidationException(failures);
    }

}
