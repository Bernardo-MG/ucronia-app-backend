
package com.bernardomg.association.architecture.rule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.bernardomg.association.architecture.predicate.Predicates;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

public class RepositoryRules {

    @ArchTest
    static final ArchRule repositories_interfaces_should_be_in_domain_repository_package = classes()
        .that(Predicates.areRepositoryClasses())
        .and()
        .areInterfaces()
        .should()
        .resideInAPackage("..domain.repository..");

    @ArchTest
    static final ArchRule spring_repositories_should_be_in_jpa_repository_package            = classes()
        .that(Predicates.areSpringRepositoryClasses())
        .should()
        .resideInAPackage("..adapter.inbound.jpa.repository..");

    @ArchTest
    static final ArchRule spring_repositories_should_be_suffixed                         = classes()
        .that(Predicates.areSpringRepositoryClasses())
        .should()
        .haveSimpleNameEndingWith("SpringRepository");

}
