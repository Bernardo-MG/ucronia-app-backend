
package com.bernardomg.association.test.architecture.predicate;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.AccessTarget.MethodCallTarget;

/**
 * Checks if a method is annotated with a Spring cache annotation. This is delegated to {@link IsSpringCacheAnnotation},
 * which will check the annotations in the class.
 */
public final class IsSpringCachedMethod extends DescribedPredicate<MethodCallTarget> {

    private final IsSpringCacheAnnotation isSpringCacheAnnotation = new IsSpringCacheAnnotation();

    public IsSpringCachedMethod() {
        super("Spring cached methods");
    }

    @Override
    public final boolean test(final MethodCallTarget method) {
        return method.isMetaAnnotatedWith(isSpringCacheAnnotation);
    }

}
