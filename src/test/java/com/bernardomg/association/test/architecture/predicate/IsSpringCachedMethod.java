
package com.bernardomg.association.test.architecture.predicate;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.AccessTarget.MethodCallTarget;

public final class IsSpringCachedMethod extends DescribedPredicate<MethodCallTarget> {

    private final IsSpringCacheAnnotation isSpringCacheAnnotation = new IsSpringCacheAnnotation();

    public IsSpringCachedMethod() {
        super("Spring cached methods");
    }

    @Override
    public final boolean test(final MethodCallTarget method) {
        return method.isAnnotatedWith(isSpringCacheAnnotation);
    }

}
