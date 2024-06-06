
package com.bernardomg.association.test.architecture.predicate;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaAnnotation;

/**
 * Checks if an annotation is a JPA annotation. This is done by checking the annotation is in the Jakarta persistence
 * package.
 */
public final class IsJpaAnnotation extends DescribedPredicate<JavaAnnotation<?>> {

    private static final String PACKAGE = "jakarta.persistence";

    public IsJpaAnnotation() {
        super("JPA annotations");
    }

    @Override
    public final boolean test(final JavaAnnotation<?> annotation) {
        return annotation.getRawType()
            .getPackageName()
            .startsWith(PACKAGE);
    }

}
