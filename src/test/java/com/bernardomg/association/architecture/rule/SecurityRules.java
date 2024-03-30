
package com.bernardomg.association.architecture.rule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

import com.bernardomg.association.architecture.predicate.Predicates;
import com.bernardomg.security.access.RequireResourceAccess;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

public class SecurityRules {

    @ArchTest
    static final ArchRule controllers_methods_should_be_secured = methods().that()
        .areDeclaredInClassesThat(Predicates.areControllerClasses())
        .and()
        .arePublic()
        .should()
        .beAnnotatedWith(RequireResourceAccess.class);

    @ArchTest
    static final ArchRule service_methods_should_not_be_secured = methods().that()
        .areDeclaredInClassesThat(Predicates.areServiceClasses())
        .and()
        .arePublic()
        .should()
        .notBeAnnotatedWith(RequireResourceAccess.class);

}
