
package com.bernardomg.association.test.architecture.predicate;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaAnnotation;

public final class SpringCachingAnnotationPredicate extends DescribedPredicate<JavaAnnotation<?>> {

    private static final String PACKAGE = "org.springframework.cache.annotation";

    public SpringCachingAnnotationPredicate() {
        super("caching annotations");
    }

    @Override
    public final boolean test(final JavaAnnotation<?> annotation) {
        return annotation.getRawType()
            .getPackageName()
            .startsWith(PACKAGE);
    }

}
