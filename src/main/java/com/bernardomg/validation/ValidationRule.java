
package com.bernardomg.validation;

import java.util.Optional;

public interface ValidationRule<T> {

    public Optional<ValidationError> test(final T value);

}
