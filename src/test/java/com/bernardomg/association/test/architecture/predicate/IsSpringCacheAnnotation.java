
package com.bernardomg.association.test.architecture.predicate;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaAnnotation;

/**
 * Checks if an annotation is a Spring cache annotation. This is done by checking the annotation is in the Spring cache
 * annotation package.
 */
public final class IsSpringCacheAnnotation extends DescribedPredicate<JavaAnnotation<?>> {

    private static final String PACKAGE = "org.springframework.cache.annotation";

    public IsSpringCacheAnnotation() {
        super("Spring cache annotations");
    }

    @Override
    public final boolean test(final JavaAnnotation<?> annotation) {
        return annotation.getRawType()
            .getPackageName()
            .startsWith(PACKAGE);
    }

}
