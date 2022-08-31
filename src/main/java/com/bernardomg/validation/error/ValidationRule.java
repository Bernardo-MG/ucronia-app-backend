
package com.bernardomg.validation.error;

import java.util.Collection;

public interface ValidationRule<T> {

    public Collection<ValidationFailure> test(final T value);

}
