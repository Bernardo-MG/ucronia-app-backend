
package com.bernardomg.association.test.architecture.rule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

import com.bernardomg.framework.testing.architecture.predicates.IsInServicePackage;
import com.bernardomg.framework.testing.architecture.predicates.springframework.IsSpringControllerClass;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.access.Unsecured;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

public class SecurityRules {

    @ArchTest
    static final ArchRule controllers_methods_should_be_secured = methods().that()
        .areDeclaredInClassesThat(new IsSpringControllerClass())
        .and()
        .arePublic()
        .should()
        .beAnnotatedWith(RequireResourceAccess.class)
        .orShould()
        .beAnnotatedWith(Unsecured.class);

    @ArchTest
    static final ArchRule service_methods_should_not_be_secured = methods().that()
        .areDeclaredInClassesThat(new IsInServicePackage())
        .and()
        .arePublic()
        .should()
        .notBeAnnotatedWith(RequireResourceAccess.class);

}
