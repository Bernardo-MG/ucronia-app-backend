
package com.bernardomg.association.test.architecture.test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.bernardomg.association.test.architecture.rule.SecurityRules;
import com.bernardomg.framework.testing.architecture.predicates.Predicates;
import com.bernardomg.framework.testing.architecture.rule.CacheRules;
import com.bernardomg.framework.testing.architecture.rule.CodingRules;
import com.bernardomg.framework.testing.architecture.rule.ConfigurationRules;
import com.bernardomg.framework.testing.architecture.rule.ControllerRules;
import com.bernardomg.framework.testing.architecture.rule.DependencyRules;
import com.bernardomg.framework.testing.architecture.rule.JpaEntityRules;
import com.bernardomg.framework.testing.architecture.rule.RepositoryRules;
import com.bernardomg.framework.testing.architecture.rule.ServiceRules;
import com.bernardomg.framework.testing.architecture.rule.TransactionalRules;
import com.bernardomg.framework.testing.architecture.rule.ValidationRules;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
        packages = { "com.bernardomg.association", "com.bernardomg.async", "com.bernardomg.configuration",
                "com.bernardomg.email", "com.bernardomg.exception", "com.bernardomg.jpa" },
        importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureRulesTest {

    @ArchTest
    static final ArchTests cacheRules                                                    = ArchTests
        .in(CacheRules.class);

    @ArchTest
    static final ArchTests codingRules                                                   = ArchTests
        .in(CodingRules.class);

    @ArchTest
    static final ArchTests configurationRules                                            = ArchTests
        .in(ConfigurationRules.class);

    @ArchTest
    static final ArchTests controllerRules                                               = ArchTests
        .in(ControllerRules.class);

    @ArchTest
    static final ArchTests dependencyRules                                               = ArchTests
        .in(DependencyRules.class);

    @ArchTest
    static final ArchRule  domain_repository_interfaces_should_not_depend_on_spring_data = noClasses()
        .that(Predicates.areRepositoryClasses())
        .and()
        .areInterfaces()
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage("org.springframework.data.domain..");

    @ArchTest
    static final ArchTests jpaEntityRules                                                = ArchTests
        .in(JpaEntityRules.class);

    @ArchTest
    static final ArchTests repositoryRules                                               = ArchTests
        .in(RepositoryRules.class);

    @ArchTest
    static final ArchTests securityRules                                                 = ArchTests
        .in(SecurityRules.class);

    @ArchTest
    static final ArchTests serviceRules                                                  = ArchTests
        .in(ServiceRules.class);

    @ArchTest
    static final ArchRule  services_should_not_depend_on_spring_data                     = noClasses()
        .that(Predicates.areServiceClasses())
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage("org.springframework.data.domain..");

    @ArchTest
    static final ArchTests transactionalRules                                            = ArchTests
        .in(TransactionalRules.class);

    @ArchTest
    static final ArchTests validationRules                                               = ArchTests
        .in(ValidationRules.class);

}
