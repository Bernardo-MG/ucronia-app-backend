
package com.bernardomg.association.test.architecture.predicate;

public class Predicates {

    public static final SpringCachedMethodPredicate areCachedMethod() {
        return new SpringCachedMethodPredicate();
    }

    public static final SpringCachingAnnotationPredicate areCachingAnnotation() {
        return new SpringCachingAnnotationPredicate();
    }

    public static final SpringConfigurationClassPredicate areConfigurationClasses() {
        return new SpringConfigurationClassPredicate();
    }

    public static final SpringControllerClassPredicate areControllerClasses() {
        return new SpringControllerClassPredicate();
    }

    public static final JpaAnnotationPredicate areJpaAnnotation() {
        return new JpaAnnotationPredicate();
    }

    public static final JpaEntityClassPredicate areJpaEntitiesClasses() {
        return new JpaEntityClassPredicate();
    }

    public static final NotSpringRepositoryClassPredicate areRepositoryClasses() {
        return new NotSpringRepositoryClassPredicate();
    }

    public static final ServiceClassPredicate areServiceClasses() {
        return new ServiceClassPredicate();
    }

    public static final SpringRepositoryClassPredicate areSpringRepositoryClasses() {
        return new SpringRepositoryClassPredicate();
    }

    public static final ValidatorClassPredicate areValidatorClasses() {
        return new ValidatorClassPredicate();
    }

}
