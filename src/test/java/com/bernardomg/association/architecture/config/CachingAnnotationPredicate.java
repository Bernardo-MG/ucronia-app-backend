
package com.bernardomg.association.architecture.config;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaAnnotation;

public final class CachingAnnotationPredicate extends DescribedPredicate<JavaAnnotation<?>> {

    public static final CachingAnnotationPredicate areCachingAnnotation() {
        return new CachingAnnotationPredicate();
    }

    private CachingAnnotationPredicate() {
        super("caching annotations");
    }

    @Override
    public final boolean test(final JavaAnnotation<?> annotation) {
        return annotation.getRawType()
            .isEquivalentTo(Caching.class)
                || annotation.getRawType()
                    .isEquivalentTo(Cacheable.class)
                || annotation.getRawType()
                    .isEquivalentTo(CachePut.class)
                || annotation.getRawType()
                    .isEquivalentTo(CacheEvict.class);
    }

}
