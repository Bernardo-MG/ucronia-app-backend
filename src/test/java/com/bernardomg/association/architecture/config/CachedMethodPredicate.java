
package com.bernardomg.association.architecture.config;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.AccessTarget.MethodCallTarget;

public final class CachedMethodPredicate extends DescribedPredicate<MethodCallTarget> {

    public static final CachedMethodPredicate areCachedMethod() {
        return new CachedMethodPredicate();
    }

    private CachedMethodPredicate() {
        super("cached methods");
    }

    @Override
    public final boolean test(final MethodCallTarget method) {
        return method.isAnnotatedWith(Caching.class) || method.isAnnotatedWith(Cacheable.class)
                || method.isAnnotatedWith(CachePut.class) || method.isAnnotatedWith(CacheEvict.class);
    }

}
